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
 *������JavaWebDBManag֧�ֶ�һ�������������ļ���������ݿ����� �صķ���.�ͻ�������Ե���getInstance()�������ʱ����Ψһʵ��.
 */
public class DBConnectionManage {
	static Logger log = Logger.getLogger(DBConnectionManage.class);
	static private DBConnectionManage instance; // Ψһʵ��
	// �ü�����������DBConnectionManagerΨһʵ���Ŀͻ�����������
	// ���������ڿ������ӳصĹرղ���
	static private int clientscount; // ������Ŀ

	private Vector driversvector = new Vector(); // ���������Ѿ�ע�������

	// ʵ�����ӳ����ֵ����ӳض���֮���ӳ�䣬�˴������ӳ�����Ϊ�������ӳض���Ϊֵ
	private Hashtable poolstable = new Hashtable(); // �������ӳ�

	/*****************************************************************************
	 * ����/function: ����Ψһʵ��.����ǵ�һ�ε��ô˷���,�򴴽�ʵ�� ����/parameter: N/A ����/return:
	 * DBConnectionManage:Ψһʵ�� ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ****************************************************************************/
	static synchronized public DBConnectionManage getInstance() {
		if (instance == null) {
			instance = new DBConnectionManage(); // �½�ʵ��
		}
		clientscount++; // ��������ʵ����Ŀ
		return instance; // ����ʵ��
	}

	/************************************************************************************
	 * ����/function: ��������˽���Է�ֹ�������󴴽�����ʵ�� ����/parameter: N/A ����/return: N/A
	 * ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	private DBConnectionManage() {
		init(); // ��ʼ��
	}

	/************************************************************************************
	 * ����/function: �����Ӷ��󷵻ظ�������ָ�������ӳ� ����/parameter: name�������ļ��ж�������ӳ����� con���Ӷ���
	 * ����/return: N/A ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) poolstable.get(name); // �����ӷ��ظ����ӳ�

		if (pool != null) {
			pool.freeConnection(con); // ����conn�����ӳ�
		}
	}

	/************************************************************************************
	 * ����/function: ���һ�����õ�(���е�)����.���û�п�������, ������������С���������������,�򴴽�������������
	 * ����/parameter: name�������ļ��ж�������ӳ����� Connection�������ӻ�null ����/return: N/A
	 * ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	public Connection getConnection(String name) {
		// �Ӹ������ɵõ����ӳر��е����ӳ�
		DBConnectionPool pool = (DBConnectionPool) poolstable.get(name);
		if (pool != null) {
			return pool.getConnection(); // �õ�����
		}
		return null;
	}

	/************************************************************************************
	 * ����/function: ���һ����������.��û�п�������,������������С��������� ������,�򴴽�������������.����,��ָ����ʱ���ڵȴ�����
	 * �߳��ͷ�����. ����/parameter: name���ӳ����� time�Ժ���Ƶĵȴ�ʱ�� ����/return:
	 * Connection�������ӻ�null ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	public Connection getConnection(String name, long time) {
		// �Ӹ������ɵõ����ӳر��е����ӳ�
		DBConnectionPool pool = (DBConnectionPool) poolstable.get(name);

		if (pool != null) {
			return pool.getConnection(time); //�õ���������
		}
		return null;
	}

	/************************************************************************************
	 * ����/function: �ر���������,�������������ע�� ����/parameter: N/A ����/return: N/A ����/steps :
	 * �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	protected synchronized void release() {
		// �ȴ�ֱ�����һ���ͻ��������
		if (--clientscount != 0) {
			return;
		}
		// ���е�l���Ӿ��Ͽ���ر�����
		// �õ����е����ӳ�
		Enumeration allPools = poolstable.elements();
		while (allPools.hasMoreElements()) { // �������
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement(); // �õ����ӳ���
			// �ر����ӳ�������
			pool.release();
		}
		// �õ���������
		Enumeration allDrivers = driversvector.elements();
		while (allDrivers.hasMoreElements()) { // �������
			Driver driver = (Driver) allDrivers.nextElement(); // �������еõ�����
			try {
				DriverManager.deregisterDriver(driver); // ����������ע��
				log.debug("����JDBC��������" + driver.getClass().getName()
						+ "��ע��");
			} catch (SQLException e) {
				log.debug("�޷���������JDBC���������ע��: "
						+ driver.getClass().getName());
			}
		} // edn of while
		//com.mingda.common.SessionFactory.closeSession();
	}

	private void log(String errormessage) {
		log.debug(errormessage);
	}

	/************************************************************************************
	 * ����/function: ����ָ�����Դ������ӳ�ʵ��. ����/parameter: props���ӳ����� ����/return: N/A
	 * ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	private void createPools(Properties props) {
		Enumeration propNames = props.propertyNames();// �õ������ļ���Ԫ��
		while (propNames.hasMoreElements())// ѭ��
		{
			String name = (String) propNames.nextElement();// ���εõ�
			if (name.endsWith(".url"))// �õ���׺��.url---���ݿ��JDBC URL
			{
				String poolName = name.substring(0, name.lastIndexOf("."));// �õ����ӳ�����
				String url = props.getProperty(poolName + ".url");// �õ����ݿ�JDBC
																	// url
				if (url == null) {
					log("û��Ϊ���ӳ�" + poolName + "ָ��URL");
					continue;
				}// end of if url == null
				String user = props.getProperty(poolName + ".user");// �õ��û���
				String password = props.getProperty(poolName + ".password");// �õ�����
				String maxconn = props.getProperty(poolName + ".maxconn", "");// �õ����������
				// ת����Ϊ����
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				}// end of try
				catch (NumberFormatException e) {
					log("������������������: " + maxconn + " .���ӳ�: " + poolName);
					max = 0;
				}
				// �õ��µ����ӳ�
				DBConnectionPool pool = new DBConnectionPool(poolName, url,
						user, password, max);
				// ���µ����ӳط����ϵ����
				poolstable.put(poolName, pool);
			}// end if (name.endsWith(".url"))
		}// end of while (propNames.hasMoreElements())
	}

	/************************************************************************************
	 * ����/function: ��ȡ������ɳ�ʼ�� ����/parameter: N/A ����/return: N/A ����/steps :
	 * �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	private void init() {
		InputStream is = getClass().getResourceAsStream(
				"/dbconnections.properties");// ��ȡ���ݿ������ļ�
		Properties dbProps = new Properties();// �½���
		try {
			dbProps.load(is);// Load �ļ�
		} catch (Exception e) {
			System.err.println("���ܶ�ȡ�����ļ�. "
					+ "��ȷ��dbconnections.properties��CLASSPATHָ����·����");
			return;
		}
		// װ������
		loadDrivers(dbProps);
		createPools(dbProps);
	}

	/************************************************************************************
	 * ����/function: װ�غ�ע������JDBC�������� ����/parameter: props���� ����/return: N/A
	 * ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
	 ************************************************************************************/
	private void loadDrivers(Properties props) {
		String driverClasses = props.getProperty("drivers"); // �õ���������class����
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) { // ѭ�������������
			String driverClassName = st.nextToken().trim(); // ���εõ���������
			log("׼��װ�����ݿ�������" + driverClassName);
			try {
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance(); // �����������Ƶõ�����ʵ��
				DriverManager.registerDriver(driver); // ע������
				driversvector.addElement(driver); // ����������������
				log("�ɹ�ע��JDBC��������" + driverClassName);
			} catch (Exception e) { // �쳣����
				log("�޷�ע��JDBC��������: " + driverClassName + ",����: " + e);
			}
		}
	}

	/************************************************************************************
	 * ����/function: ���ڲ��ඨ����һ�����ӳ�.���ܹ�����Ҫ�󴴽�������, ֱ��Ԥ�������������Ϊֹ.�ڷ������Ӹ��ͻ�����֮ǰ,
	 * ���ܹ���֤���ӵ���Ч��. ����/parameter: N/A ����/return: N/A ����/steps : �޸ļ�¼/Revision ����
	 * �޸��� ����
	 ************************************************************************************/
	class DBConnectionPool {
		private int alreadyconnectcount; // �Ѿ���ʹ�õ�������Ŀ
		private Vector freeConnections = new Vector(); // ��ǰ���ӳ��ڵ����п�������
		private int maxConn; // ���������
		private String poolname; // ���ӳ�����
		private String password; // ����
		private String URL; // ����JDBC
		private String user; // �û�

		/*****************************************************************************
		 * ����/function: �����µ����ӳصĹ��캯�� ����/parameter: URL���ݿ��JDBC URL
		 * user���ݿ��ʺ�,��null password����,��null maxConn�����ӳ������������������ ����/return: N/A
		 * ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
		 *****************************************************************************/
		protected DBConnectionPool(String name, String URL, String user,
				String password, int maxConn) {
			// �����������
			this.poolname = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/*****************************************************************************
		 * ����/function: ������ʹ�õ����ӷ��ظ����ӳ� ����/parameter: con�ͻ������ͷŵ����� ����/return: N/A
		 * ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
		 *****************************************************************************/
		protected synchronized void freeConnection(Connection con) {
			log("����conn�����ӳ�");
			// ��ָ�����Ӽ��뵽����ĩβ���������ӣ�
			freeConnections.addElement(con);
			// ���Ѿ����ӵ���Ŀ����
			alreadyconnectcount--;
			notifyAll(); // �������еȴ��߳�
		}

		/*****************************************************************************
		 * ����/function: �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������ С���������������,�򴴽�������.��ԭ���Ǽ�Ϊ���õ�����
		 * ������Ч,�������ɾ��֮,Ȼ��ݹ�����Լ��Գ����µĿ�������. ����/parameter: con�ͻ������ͷŵ����� ����/return:
		 * N/A ����/steps : �޸ļ�¼/Revision ���� �޸��� ����
		 *****************************************************************************/
		protected synchronized Connection getConnection() { // �������ݿ��������Ϣ
			long startTime = new Date().getTime();
			// ����
			Connection con = null;

			// ������ӳ����п�������
			if (freeConnections.size() > 0) {
				// ��ȡ�����е�һ����������
				con = (Connection) freeConnections.firstElement();
				// ɾ���Ѿ�ʹ�õ�����
				freeConnections.removeElementAt(0);
				// �ж������Ƿ���Ȼ��Ч
				try {
					if (con.isClosed()) {
						log("�����ӳ�" + poolname + "ɾ��һ����Ч���� ");
						// �ݹ�����Լ�,�����ٴλ�ȡ��������
						con = getConnection();
					}
				} catch (SQLException e) {
					log("�����ӳ�" + poolname + "ɾ��һ����Ч����");
					// �ݹ�����Լ�,�����ٴλ�ȡ��������
					con = getConnection();
				}
			} // end if
			// ������ӳ���û�����������ĵ�����
			else if (maxConn == 0 || alreadyconnectcount < maxConn) {
				con = newConnection();
			}
			// �Ѿ������ȥ�����ӱ��
			if (con != null) {
				log("�Ѿ��õ�һ������");
				// ����Ѿ����ӵ���������
				alreadyconnectcount++;
			}
			return con;
		}

		/*****************************************************************************
		 * ����/function: �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ�� �μ�ǰһ��getConnection()����.
		 * ����/parameter: timeout�Ժ���Ƶĵȴ�ʱ������ ����/return: N/A ����/steps :
		 * �޸ļ�¼/Revision ���� �޸��� ����
		 *****************************************************************************/
		protected synchronized Connection getConnection(long timeout) {
			// ��ʼ�õ����ӵĳ�ʼʱ��
			long startTime = new Date().getTime();
			// ����
			Connection con = null;

			// ѭ��ֱ���Ѿ����conn
			// ����getConnection()�õ���������
			while ((con = getConnection()) == null) {
				// �ȴ�
				try {
					wait(timeout);
				} catch (InterruptedException e) {
				}
				// �ȴ��̱߳�����
				// ��������ԭ��
				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()���ص�ԭ���ǳ�ʱ
					log("���ӳ�ʱ��");
					return null;
				}
				// ���ǳ�ʱ��ѭ������getconnect()
			}
			return con;
		}

		/*****************************************************************************
		 * ����/function: �ر��������� ����/parameter: N/A ����/return: N/A ����/steps :
		 * �޸ļ�¼/Revision ���� �޸��� ����
		 *****************************************************************************/
		protected synchronized void release() {
			Enumeration allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) { // �������
				Connection con = (Connection) allConnections.nextElement(); // �õ�����
				try {
					con.close(); // �ر�����
					log("�ر����ӳ�" + poolname + "�е�һ������");
				} catch (SQLException e) {
					log("�޷��ر����ӳ�" + poolname + "�е����ӣ�" + e);
				}
			}
			freeConnections.removeAllElements();
		}

		/*****************************************************************************
		 * ����/function: �����µ����� ����/parameter: N/A ����/return: N/A ����/steps :
		 * �޸ļ�¼/Revision ���� �޸��� ����
		 *****************************************************************************/
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL); // �õ��µ�����
				} else {
					con = DriverManager.getConnection(URL, user, password); // �õ��µ�����
				}
				log("���ӳ�" + poolname + "����һ���µ�����");
			} // end of try
			catch (SQLException e) {
				log("�޷���������URL������: " + URL + "����" + e);
				return null;
			}
			return con;
		}
	}
}