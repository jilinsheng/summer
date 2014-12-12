package com.yanding.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 *管理类JavaWebDBManag支持对一个或多个由属性文件定义的数据库连接 池的访问.客户程序可以调用getInstance()方法访问本类的唯一实例.
 */
public class DBConnectionManage {
	static Logger log = Logger.getLogger(DBConnectionManage.class);
	static private DBConnectionManage instance; // 唯一实例
	// 该计数代表引用DBConnectionManager唯一实例的客户程序总数，
	// 它将被用于控制连接池的关闭操作
	static private int clientscount; // 连接数目

	private Vector driversvector = new Vector(); // 保存所有已经注册的驱动

	// 实现连接池名字到连接池对象之间的映射，此处以连接池名字为键，连接池对象为值
	private Hashtable poolstable = new Hashtable(); // 所有连接池

	/*****************************************************************************
	 * 功能/function: 返回唯一实例.如果是第一次调用此方法,则创建实例 参数/parameter: N/A 返回/return:
	 * DBConnectionManage:唯一实例 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ****************************************************************************/
	static synchronized public DBConnectionManage getInstance() {
		if (instance == null) {
			instance = new DBConnectionManage(); // 新建实例
		}
		clientscount++; // 增加连接实例数目
		return instance; // 返回实例
	}

	/************************************************************************************
	 * 功能/function: 建构函数私有以防止其它对象创建本类实例 参数/parameter: N/A 返回/return: N/A
	 * 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	private DBConnectionManage() {
		init(); // 初始化
	}

	/************************************************************************************
	 * 功能/function: 将连接对象返回给由名字指定的连接池 参数/parameter: name在属性文件中定义的连接池名字 con连接对象
	 * 返回/return: N/A 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) poolstable.get(name); // 将连接返回给连接池

		if (pool != null) {
			pool.freeConnection(con); // 返回conn给连接池
		}
	}

	/************************************************************************************
	 * 功能/function: 获得一个可用的(空闲的)连接.如果没有可用连接, 且已有连接数小于最大连接数限制,则创建并返回新连接
	 * 参数/parameter: name在属性文件中定义的连接池名字 Connection可用连接或null 返回/return: N/A
	 * 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	public Connection getConnection(String name) {
		// 从根据名成得到连接池表中的连接池
		DBConnectionPool pool = (DBConnectionPool) poolstable.get(name);
		if (pool != null) {
			return pool.getConnection(); // 得到连接
		}
		return null;
	}

	/************************************************************************************
	 * 功能/function: 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接 数限制,则创建并返回新连接.否则,在指定的时间内等待其它
	 * 线程释放连接. 参数/parameter: name连接池名字 time以毫秒计的等待时间 返回/return:
	 * Connection可用连接或null 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	public Connection getConnection(String name, long time) {
		// 从根据名成得到连接池表中的连接池
		DBConnectionPool pool = (DBConnectionPool) poolstable.get(name);

		if (pool != null) {
			return pool.getConnection(time); //得到可用连接
		}
		return null;
	}

	/************************************************************************************
	 * 功能/function: 关闭所有连接,撤销驱动程序的注册 参数/parameter: N/A 返回/return: N/A 步骤/steps :
	 * 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	protected synchronized void release() {
		// 等待直到最后一个客户程序调用
		if (--clientscount != 0) {
			return;
		}
		// 所有的l连接均断开则关闭驱动
		// 得到所有的连接池
		Enumeration allPools = poolstable.elements();
		while (allPools.hasMoreElements()) { // 逐个处理
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement(); // 得到连接池类
			// 关闭连接池中连接
			pool.release();
		}
		// 得到所有驱动
		Enumeration allDrivers = driversvector.elements();
		while (allDrivers.hasMoreElements()) { // 逐个处理
			Driver driver = (Driver) allDrivers.nextElement(); // 从向量中得到驱动
			try {
				DriverManager.deregisterDriver(driver); // 撤销驱动的注册
				log.debug("撤销JDBC驱动程序" + driver.getClass().getName()
						+ "的注册");
			} catch (SQLException e) {
				log.debug("无法撤销下列JDBC驱动程序的注册: "
						+ driver.getClass().getName());
			}
		} // edn of while
		//com.mingda.common.SessionFactory.closeSession();
	}

	private void log(String errormessage) {
		log.debug(errormessage);
	}

	/************************************************************************************
	 * 功能/function: 根据指定属性创建连接池实例. 参数/parameter: props连接池属性 返回/return: N/A
	 * 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	private void createPools(Properties props) {
		Enumeration propNames = props.propertyNames();// 得到配置文件的元素
		while (propNames.hasMoreElements())// 循环
		{
			String name = (String) propNames.nextElement();// 依次得到
			if (name.endsWith(".url"))// 得到后缀是.url---数据库的JDBC URL
			{
				String poolName = name.substring(0, name.lastIndexOf("."));// 得到连接池名称
				String url = props.getProperty(poolName + ".url");// 得到数据库JDBC
																	// url
				if (url == null) {
					log("没有为连接池" + poolName + "指定URL");
					continue;
				}// end of if url == null
				String user = props.getProperty(poolName + ".user");// 得到用户名
				String password = props.getProperty(poolName + ".password");// 得到口令
				String maxconn = props.getProperty(poolName + ".maxconn", "");// 得到最大连接数
				// 转换成为整形
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				}// end of try
				catch (NumberFormatException e) {
					log("错误的最大连接数限制: " + maxconn + " .连接池: " + poolName);
					max = 0;
				}
				// 得到新的连接池
				DBConnectionPool pool = new DBConnectionPool(poolName, url,
						user, password, max);
				// 将新的连接池放入哈系表中
				poolstable.put(poolName, pool);
			}// end if (name.endsWith(".url"))
		}// end of while (propNames.hasMoreElements())
	}

	/************************************************************************************
	 * 功能/function: 读取属性完成初始化 参数/parameter: N/A 返回/return: N/A 步骤/steps :
	 * 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	private void init() {
		InputStream is = getClass().getResourceAsStream(
				"/dbconnections.properties");// 读取数据库配置文件
		Properties dbProps = new Properties();// 新建类
		try {
			dbProps.load(is);// Load 文件
		} catch (Exception e) {
			System.err.println("不能读取属性文件. "
					+ "请确保dbconnections.properties在CLASSPATH指定的路径中");
			return;
		}
		// 装入驱动
		loadDrivers(dbProps);
		createPools(dbProps);
	}

	/************************************************************************************
	 * 功能/function: 装载和注册所有JDBC驱动程序 参数/parameter: props属性 返回/return: N/A
	 * 步骤/steps : 修改记录/Revision 日期 修改人 描述
	 ************************************************************************************/
	private void loadDrivers(Properties props) {
		String driverClasses = props.getProperty("drivers"); // 得到所有驱动class名称
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) { // 循环获得所有驱动
			String driverClassName = st.nextToken().trim(); // 依次得到驱动名称
			log("准备装载数据库驱动：" + driverClassName);
			try {
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance(); // 根据驱动名称得到驱动实例
				DriverManager.registerDriver(driver); // 注册驱动
				driversvector.addElement(driver); // 将驱动加入向量中
				log("成功注册JDBC驱动程序" + driverClassName);
			} catch (Exception e) { // 异常处理
				log("无法注册JDBC驱动程序: " + driverClassName + ",错误: " + e);
			}
		}
	}

	/************************************************************************************
	 * 功能/function: 此内部类定义了一个连接池.它能够根据要求创建新连接, 直到预定的最大连接数为止.在返回连接给客户程序之前,
	 * 它能够验证连接的有效性. 参数/parameter: N/A 返回/return: N/A 步骤/steps : 修改记录/Revision 日期
	 * 修改人 描述
	 ************************************************************************************/
	class DBConnectionPool {
		private int alreadyconnectcount; // 已经在使用的连接数目
		private Vector freeConnections = new Vector(); // 当前连接池内的所有可用连接
		private int maxConn; // 最大连接数
		private String poolname; // 连接池名称
		private String password; // 口令
		private String URL; // 连接JDBC
		private String user; // 用户

		/*****************************************************************************
		 * 功能/function: 创建新的连接池的构造函数 参数/parameter: URL数据库的JDBC URL
		 * user数据库帐号,或null password密码,或null maxConn此连接池允许建立的最大连接数 返回/return: N/A
		 * 步骤/steps : 修改记录/Revision 日期 修改人 描述
		 *****************************************************************************/
		protected DBConnectionPool(String name, String URL, String user,
				String password, int maxConn) {
			// 保存基本参数
			this.poolname = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/*****************************************************************************
		 * 功能/function: 将不再使用的连接返回给连接池 参数/parameter: con客户程序释放的连接 返回/return: N/A
		 * 步骤/steps : 修改记录/Revision 日期 修改人 描述
		 *****************************************************************************/
		protected synchronized void freeConnection(Connection con) {
			log("返回conn给连接池");
			// 将指定连接加入到向量末尾（可用连接）
			freeConnections.addElement(con);
			// 将已经连接的数目减少
			alreadyconnectcount--;
			notifyAll(); // 唤醒所有等待线程
		}

		/*****************************************************************************
		 * 功能/function: 从连接池获得一个可用连接.如没有空闲的连接且当前连接数 小于最大连接数限制,则创建新连接.如原来登记为可用的连接
		 * 不再有效,则从向量删除之,然后递归调用自己以尝试新的可用连接. 参数/parameter: con客户程序释放的连接 返回/return:
		 * N/A 步骤/steps : 修改记录/Revision 日期 修改人 描述
		 *****************************************************************************/
		protected synchronized Connection getConnection() { // 返回数据库的连接信息
			long startTime = new Date().getTime();
			// 连接
			Connection con = null;

			// 如果连接池中有可用连接
			if (freeConnections.size() > 0) {
				// 获取向量中第一个可用连接
				con = (Connection) freeConnections.firstElement();
				// 删除已经使用的连接
				freeConnections.removeElementAt(0);
				// 判断连接是否仍然有效
				try {
					if (con.isClosed()) {
						log("从连接池" + poolname + "删除一个无效连接 ");
						// 递归调用自己,尝试再次获取可用连接
						con = getConnection();
					}
				} catch (SQLException e) {
					log("从连接池" + poolname + "删除一个无效连接");
					// 递归调用自己,尝试再次获取可用连接
					con = getConnection();
				}
			} // end if
			// 如果连接池内没有连接则建立心得连接
			else if (maxConn == 0 || alreadyconnectcount < maxConn) {
				con = newConnection();
			}
			// 已经分配出去的连接标记
			if (con != null) {
				log("已经得到一个连接");
				// 标记已经连接的连接数据
				alreadyconnectcount++;
			}
			return con;
		}

		/*****************************************************************************
		 * 功能/function: 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
		 * 参数/parameter: timeout以毫秒计的等待时间限制 返回/return: N/A 步骤/steps :
		 * 修改记录/Revision 日期 修改人 描述
		 *****************************************************************************/
		protected synchronized Connection getConnection(long timeout) {
			// 开始得到连接的初始时间
			long startTime = new Date().getTime();
			// 连接
			Connection con = null;

			// 循环直到已经获得conn
			// 调用getConnection()得到可用连接
			while ((con = getConnection()) == null) {
				// 等待
				try {
					wait(timeout);
				} catch (InterruptedException e) {
				}
				// 等待线程被唤醒
				// 分析唤醒原因
				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()返回的原因是超时
					log("连接超时！");
					return null;
				}
				// 不是超时则循环调用getconnect()
			}
			return con;
		}

		/*****************************************************************************
		 * 功能/function: 关闭所有连接 参数/parameter: N/A 返回/return: N/A 步骤/steps :
		 * 修改记录/Revision 日期 修改人 描述
		 *****************************************************************************/
		protected synchronized void release() {
			Enumeration allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) { // 逐个处理
				Connection con = (Connection) allConnections.nextElement(); // 得到连接
				try {
					con.close(); // 关闭连接
					log("关闭连接池" + poolname + "中的一个连接");
				} catch (SQLException e) {
					log("无法关闭连接池" + poolname + "中的连接：" + e);
				}
			}
			freeConnections.removeAllElements();
		}

		/*****************************************************************************
		 * 功能/function: 创建新的连接 参数/parameter: N/A 返回/return: N/A 步骤/steps :
		 * 修改记录/Revision 日期 修改人 描述
		 *****************************************************************************/
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL); // 得到新的连接
				} else {
					con = DriverManager.getConnection(URL, user, password); // 得到新的连接
				}
				log("连接池" + poolname + "创建一个新的连接");
			} // end of try
			catch (SQLException e) {
				log("无法创建下列URL的连接: " + URL + "错误：" + e);
				return null;
			}
			return con;
		}
	}
}