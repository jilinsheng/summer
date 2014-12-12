package com.mingda.action.info.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.myjdbc.ConnectionManager;

/**
 * 表信息管理 1、逻辑条件和物理条件转换 2、默认构成查询显示结果 3、信息分类管理 4、信息和业务查询条件组合管理 5、条件表达式解析管理
 * 
 * @author xiu
 * 
 */
public class TableInfoPhySQL {
	static Logger log = Logger.getLogger(TableInfoPhySQL1.class);

	// 物理查询语句部分
	private String physelect = "";// 物理条件选择表[select]参数
	private String phyfrom = "";// 物理条件选择表[from]参数
	private String phywhererep = "";// 物理条件关系[where]强关系部分
	//
	private String phypartid = "";// 循环的父表主键字段编号
	private String phychildid = "";// 循环的子表外键字段编号
	private String phychildexp = "";// 循环的子表外键表达式
	private String phypartexp = "";// 循环的父节点主键表达式

	//
	// ======================================基本信息表操作BEG=================================
	/**
	 * 取得物理SQL语句
	 * 
	 * @param tselect
	 * @param tfrom
	 * @param twhere
	 * @param tmode
	 *            [0表示查询返回记录数][1表示返回sql语句]
	 * @param tbegpage
	 * @param tendpage
	 * @param conn
	 * @param tgroupmode
	 *            [0]不分组[1]分组按查询结果分组
	 * @param tordermode
	 *            [0]不排序[1]排序FAMILYNO家庭编号
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer getphysql(String tselect, String tfrom, String twhere,
			String tmode, String tbegpage, String tendpage, String tgroupmode,
			String tordermode, Connection conn) {
		log.debug(tselect + "," + tfrom + "," + twhere + "," + tmode + ","
				+ tbegpage + "," + tendpage + "," + tgroupmode + ","
				+ tordermode + "," + tordermode);
		StringBuffer shtml = new StringBuffer("");
		String ssql = "";
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		String tname = "";
		String locigselect = tselect;
		String locigfrom = tfrom;
		String locigwhere = twhere;
		String locigmode = tmode; // 查询或者取得SQL语句[在常量定义]
		String locigbegpage = tbegpage;
		String locigendpage = tendpage;
		String lociggroupmode = tgroupmode;
		String locigordermode = tordermode;
		//
		// 初始化
		setPhyselect("");
		setPhyfrom("");
		setPhywhererep("");

		// 单引号转义过来后去掉左右空格
		locigwhere = locigwhere.replace("' ", "'");
		locigwhere = locigwhere.replace(" '", "'");

		// 打印测试
		// log.debug("xiu逻辑条件初始SQL语句:" + locigselect + " from " + locigfrom +
		// " where "+ locigwhere);

		// 无查询字段
		if (locigselect == null || locigselect.equals("")) {
			shtml.append("-1");
			return shtml;
		}
		// 无查询表
		if (locigfrom == null || locigfrom.equals("")) {
			shtml.append("-1");
			return shtml;
		}
		// 由逻辑表构建表关系
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = ilength - 1; r >= 0; r--) {
			tname = atname[r].trim();
			// log.debug("ahahahha:"+tname);
			// 是否已经存在且处理关系了
			boolean exitsflag = false;
			int iexits = getPhyfrom().indexOf(tname);// 出现位置
			if (iexits >= 0) {
				exitsflag = true;
			}
			// 表已经存在
			if (!exitsflag) {
				//
				boolean existtable = false; // 表存在标识
				//
				String sql = "select table_id,super_table_id,logicname,depth "
						+ "from sys_t_table where status = '1' and logicname = '"
						+ tname + "'"; // 定义SQL语句

				log.debug(sql);

				// Connection conn = null; //声明Connection对象
				PreparedStatement pstmt = null; // 声明PreparedStatement对象
				ResultSet rs = null; // 声明ResultSet对象
				try {
					 conn = ConnectionManager.getConnection(); //获取数据库连接
					pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
					// 设置参数
					rs = pstmt.executeQuery();

					while (rs.next()) {
						String tid = rs.getString("table_id");
						String parentid = rs.getString("super_table_id");
						String logicname = rs.getString("logicname");
						String depth = rs.getString("depth");
						//
						setPhypartid(parentid);// 用作循环
						setPhychildid(tid);// 用作循环

						int i = Integer.parseInt(depth);
						// 根节点
						if (i == 0) {
							// 本身
							if (getPhyfrom().equals("") || getPhyfrom() == null) {
								setPhyfrom(logicname);
							} else {
								int ibeg = getPhyfrom().indexOf(logicname);// 第一次出现位置
								if (ibeg < 0) {
									setPhyfrom(getPhyfrom() + "," + logicname);
								}
							}
						} else {
							// 有父节点
							for (int j = i; j > 0; j--) {
								if (i == j) {
									// 本身
									if (getPhyfrom().equals("")
											|| getPhyfrom() == null) {
										setPhyfrom(logicname);
									} else {
										int ibeg = getPhyfrom().indexOf(
												logicname);// 第一次出现位置
										if (ibeg < 0) {
											setPhyfrom(getPhyfrom() + ","
													+ logicname);
										}
									}
								}
								// 取得父子关系
								setpartchildrepfromid(getPhypartid(),
										getPhychildid());
							}
						}
						// 表存在标识
						existtable = true;
					}
					// 表存在标识
					if (!existtable) {// 表改名或者物理名称[现存的表逻辑名称改变语句肯定出错]
						// 检验出错
						if (getPhyfrom().equals("") || getPhyfrom() == null) {
							setPhyfrom(tname);
						} else {
							int ibeg = getPhyfrom().indexOf(tname);// 第一次出现位置
							if (ibeg < 0) {
								setPhyfrom(getPhyfrom() + "," + tname);
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					ConnectionManager.closeQuietly(rs); // 关闭结果集
					ConnectionManager.closeQuietly(pstmt); // 关闭PreparedStatement
					// ConnectionManager.closeQuietly(); //关闭连接
				}
			}
		}
		//
		// 1、查询结果字段=============================================================
		setPhyselect(locigselect);
		// 2、查询表==================================================================
		if (getPhyfrom().length() <= 0) {
			setPhyfrom(locigfrom);
		}
		// 3、查询条件================================================================
		if (locigwhere.length() > 0) {
			if (getPhywhererep().length() == 0) {
				setPhywhererep(locigwhere);
			} else {
				setPhywhererep(getPhywhererep() + " and " + locigwhere);
			}
		}

		// 打印测试
		// log.debug("xiu逻辑条件结束SQL语句:select " + getPhyselect() + " from " +
		// getPhyfrom() + " where " + getPhywhererep());

		// 解析字典值[有☆11]解析成'11'
		String[] atstr = getPhywhererep().split("]");
		int itlen = atstr.length;
		if (itlen >= 1) {
			for (int i = 0; i < itlen; i++) {
				setPhywhererep(replaceDisc(getPhywhererep()));
			}
		}
		// log.debug(getPhywhererep());
		//
		//
		// 转换查询字段和条件
		replacelogicexp(getPhyselect(), getPhyfrom(), getPhywhererep());
		// 转换查询分组语句
		lociggroupmode = replacelogicgroupby(getPhyfrom(), lociggroupmode);
		// 转换查询表
		replacelogicfrom(getPhyfrom());
		//

		// 整理重复的物理查询字段和查询表
		String snewselect = "", snewsflag = "F";
		int itochar = getPhyselect().indexOf("to_char");// 日期等转换字符时出现位置
		if (itochar < 0) {
			String[] asselect = getPhyselect().split(",");
			int itslen = asselect.length;
			for (int x = 0; x < itslen; x++) {
				String sselect = asselect[x].trim();
				// 防止表字段名有相同特征INFO_T_FAMILY.ID,INFO_T_FAMILY.ID2
				// 相同特征为INFO_T_FAMILY.ID
				String[] anewselect = snewselect.split(",");
				int inewslen = anewselect.length;
				// 初始化
				snewsflag = "F";
				for (int p = 0; p < inewslen; p++) {
					String stselect = anewselect[p].trim();
					if (stselect.equals(sselect)) {
						snewsflag = "T";
					}
				}
				// 不存在相同
				if (snewsflag.equals("F")) {
					if (snewselect.equals("")) {
						snewselect = sselect;
					} else {
						snewselect += "," + sselect;
					}
				}
			}
			// 新查询字段
			setPhyselect(snewselect);
		}
		//
		String snewfrom = "", snewtflag = "F";
		String[] asfrom = getPhyfrom().split(",");
		int itflen = asfrom.length;
		for (int y = 0; y < itflen; y++) {
			String sfrom = asfrom[y].trim();
			// 防止表名有相同特征INFO_T_FAMILYCLASS,INFO_T_FAMILY
			// 相同特征为INFO_T_FAMILY
			String[] anewfrom = snewfrom.split(",");
			int inewtlen = anewfrom.length;
			// 初始化
			snewtflag = "F";
			for (int j = 0; j < inewtlen; j++) {
				String stfrom = anewfrom[j].trim();
				if (stfrom.equals(sfrom)) {
					snewtflag = "T";
				}
			}
			// 不存在相同
			if (snewtflag.equals("F")) {
				if (snewfrom.equals("")) {
					snewfrom = sfrom;
				} else {
					snewfrom += "," + sfrom;
				}
			}
		}
		// 新查询表======================================================
		setPhyfrom(snewfrom);
		//

		// 物理条件是否为空
		if (getPhywhererep().equals("") || getPhywhererep() == null) {
			ssql = "select " + getPhyselect() + " from " + getPhyfrom();
		} else {
			ssql = "select " + getPhyselect() + " from " + getPhyfrom()
					+ " where " + getPhywhererep();
		}
		// 打印测试
		// log.debug("xiu物理SQL语句:"+ssql);
		//
		//
		/*
		 * ======================================================================
		 * ================
		 * =====================================默认SQL语句结束========
		 * =============================
		 * ========================================
		 * ===============================================
		 */
		//
		// ========================分组==================
		if (lociggroupmode.equals("1")) {// 查询字段分组
			ssql += " group by " + getPhyselect();
		} else if (!lociggroupmode.equals("0")) {// 特殊字段分组(排除集合字段)
			ssql += " group by " + lociggroupmode;
		}
		// ========================分组==================
		// ========================排序==================
		if (locigordermode.equals("1")) { // 家庭编号排序[家庭编号]
			ssql += " order by nlssort(FAMILYNO, 'NLS_SORT=SCHINESE_PINYIN_M') ";
		} else { // 参数排序[locigordermode]
			int ibeg = 0; // 排序字段出现位置
			int iabeg = locigordermode.indexOf(" asc"); // 升序字段出现位置
			int idbeg = locigordermode.indexOf(" desc"); // 降序字段出现位置
			if (iabeg > 0) {
				ibeg = iabeg;
			} else if (idbeg > 0) {
				ibeg = idbeg;
			}
			//
			if (ibeg > 0) { // 有设定排序
				String ordername = locigordermode.substring(0, ibeg);
				String ordertype = locigordermode.substring(ibeg,
						locigordermode.length());
				ordername = ordername.trim();
				int itbeg = ordername.indexOf(".");// 第一次出现位置
				if (itbeg >= 0) {
					String stname = ordername.substring(0, itbeg);
					String sfname = ordername.substring(itbeg + 1,
							ordername.length());
					String stemptn = tableinfoquery
							.gettableidfromphysic(stname);
					if (stemptn.equals("") || stemptn == null) {
						ssql += " order by " + locigordermode;
					} else {
						String sftype = tableinfoquery
								.getfieldtypefromphysicname(stname, sfname);
						if (sftype.equals("0")) {
							// 字符串类型
							ssql += " order by nlssort(" + ordername
									+ ", 'NLS_SORT=SCHINESE_PINYIN_M') "
									+ ordertype;
						} else {
							ssql += " order by " + ordername + " " + ordertype;
						}
					}
				} else {
					ssql += " order by " + locigordermode;
				}
			}
		}
		// ========================排序==================
		// ========================分页==================
		// 分页查询["0"]和["0"]
		if (!"0".equals(locigbegpage) || !"0".equals(locigendpage)) { // 查询分页
			// 分页
			ssql = "select * from (select mytab.*, rownum row_num from ("
					+ ssql + ") mytab) where row_num between " + locigbegpage
					+ " and  " + locigendpage;
		}
		// ========================分页==================
		//
		/*
		 * ======================================================================
		 * ================
		 * =====================================构造SQL语句结束========
		 * =============================
		 * ========================================
		 * ===============================================
		 */
		//
		// ======================================检验sql语句合法性=========================
		String tmpcount = validationfromsql(ssql);
		if ("-1".equals(tmpcount)) { // 错误语句
			// 返回-1为表示查询语句不合法
			shtml.append("-1");
			log.debug("xiu错误物理SQL语句:" + ssql);
			return shtml;
		} else if ("0".equals(tmpcount)) { // 无查询结果记录
			if (locigmode.equals(constantdefine.REPLACESQL_EXE)) { // 执行查询返回0为无结果
				// 返回0为表示查询语句合法,但是结果为0条记录
				shtml.append("0");
				return shtml;
			}
		}
		// ======================================检验sql语句合法性=========================
		if(ssql.indexOf("INFO_T_FAMILYCLASS.CLASSTYPE")>0){
			String bb =ssql.substring(0,ssql.lastIndexOf("where")+5)+" INFO_T_MEMBER.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and INFO_T_FAMILYCLASS.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and 1=1 and  "+ssql.substring(ssql.lastIndexOf("where")+5);
			ssql=bb;
		}
		shtml.append(ssql);
		// log.debug("xiu物理SQL语句:"+ssql);
		// 打印测试
		Log4jApp.logger("xiu物理SQL语句:" + ssql);
		log.debug("严重" + ssql);

		// log.debug(shtml);
		return shtml;
	}

	/**
	 * 由表父编号和子编号 计算出表的父表和父表关系
	 * 
	 * @param id
	 */
	void setpartchildrepfromid(String parid, String childid) {
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		String sql = "select table_id,super_table_id,logicname,depth "
				+ "from sys_t_table where status = '1' and table_id = '"
				+ parid + "'"; // 定义SQL语句

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String tid = rs.getString("table_id");
				String parentid = rs.getString("super_table_id");
				String logicname = rs.getString("logicname");

				setPhypartid(parentid);// 用作循环
				setPhychildid(tid);// 用作循环

				setPhychildexp(tableinfoquery.getfkexpvaluefromid(childid));// 用作循环
				setPhypartexp(tableinfoquery.getpkexpvaluefromid(parid));// 用作循环
				//

				// 父级
				if (getPhyfrom().equals("") || getPhyfrom() == null) {
					setPhyfrom(logicname);
				} else {
					int ibeg = getPhyfrom().indexOf(logicname);// 第一次出现位置
					if (ibeg < 0) {
						setPhyfrom(getPhyfrom() + "," + logicname);
					}
				}
				// 父子关系
				if (getPhywhererep().length() == 0) {
					if (getPhychildexp().length() > 0
							&& getPhypartexp().length() > 0) {
						setPhywhererep(getPhychildexp() + " = "
								+ getPhypartexp());
					}
				} else {
					if (getPhychildexp().length() > 0
							&& getPhypartexp().length() > 0) {
						setPhywhererep(getPhywhererep() + " and "
								+ getPhychildexp() + " = " + getPhypartexp());
					}
				}
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		// Log4jApp.logger(sql);
	}

	/**
	 * 解析字典值
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String replaceDisc(String str) {
		String srep = "", stbegstr = "", stendstr = "", ststr = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String replacechr = constantdefine.REPLACEEXP_CHR;

		int ibeg = 0, iend = 0;
		// Log4jApp.logger("str:"+str);
		// 字典[有☆11]
		ibeg = str.indexOf("[");// 第一次出现位置
		iend = str.indexOf("]");// 第一次出现位置
		if (ibeg == -1 || iend == -1) {
			return str;
		}
		// 待解析[有☆11]
		ststr = str.substring(ibeg, iend + 1);
		// Log4jApp.logger("ststr:"+ststr);

		// 解析前拆分
		stbegstr = str.substring(0, ibeg);
		stendstr = str.substring(iend + 1, str.length());
		// Log4jApp.logger("stbegstr:"+stbegstr);
		// Log4jApp.logger("stendstr:"+stendstr);

		// 解析成'11'
		String[] atstr = ststr.split(replacechr);
		ststr = atstr[1];
		ststr = ststr.substring(0, ststr.length() - 1);
		ststr = "'" + ststr + "'";
		// Log4jApp.logger("ststr:"+ststr);

		// 解析后合并
		srep = stbegstr + ststr + stendstr;
		// Log4jApp.logger("srep:"+srep);
		return srep;
	}

	/**
	 * 转换 逻辑条件 逻辑查询字段 为物理条件 物理查询字段
	 * 
	 * @param logicselect
	 * @param locigfrom
	 * @param logicwhere
	 */
	public void replacelogicexp(String logicselect, String locigfrom,
			String logicwhere) {
		//
		String logicname = "", logicexp = "", phyexp = "";
		// 由逻辑表构建表关系
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = 0; r < ilength; r++) {
			logicname = atname[r].trim();

			// 转换字段和条件
			String sql = "select a.logicname || '.'|| b.logicname as logicexp,"
					+ "a.physicalname || '.'|| b.physicalname as phyexp  "
					+ "from sys_t_table a,sys_t_field b  "
					+ "where a.table_id = b.table_id and a.logicname = '"
					+ logicname + "'"; // 定义SQL语句
			// Log4jApp.logger(sql);
			Connection conn = null; // 声明Connection对象
			PreparedStatement pstmt = null; // 声明PreparedStatement对象
			ResultSet rs = null; // 声明ResultSet对象
			try {
				conn = DBUtils.getConnection(); // 获取数据库连接
				pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
				// 设置参数
				rs = pstmt.executeQuery();
				while (rs.next()) {
					logicexp = rs.getString("logicexp");
					phyexp = rs.getString("phyexp");

					logicselect = logicselect.replace(logicexp, phyexp);
					logicwhere = logicwhere.replace(logicexp, phyexp);
				}
			} catch (SQLException e) {
				Log4jApp.logger(e.toString());
			} finally {
				DBUtils.close(rs); // 关闭结果集
				DBUtils.close(pstmt); // 关闭PreparedStatement
				DBUtils.close(conn); // 关闭连接
			}
		}
		// 转换为物理语句
		setPhyselect(logicselect);
		// Log4jApp.logger("物理查询字段:"+ getPhyselect());
		setPhywhererep(logicwhere);
		// Log4jApp.logger("物理查询条件:"+ getPhywhererep());
	}

	/**
	 * 转换 逻辑分组语句
	 * 
	 * @param locigfrom
	 * @param logicgroupby
	 * @return
	 */
	public String replacelogicgroupby(String locigfrom, String logicgroupby) {
		//
		String srep = "";
		String logicname = "", logicexp = "", phyexp = "";
		// 由逻辑表构建表关系
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = 0; r < ilength; r++) {
			logicname = atname[r].trim();

			// 转换字段和条件
			String sql = "select a.logicname || '.'|| b.logicname as logicexp,"
					+ "a.physicalname || '.'|| b.physicalname as phyexp  "
					+ "from sys_t_table a,sys_t_field b  "
					+ "where a.table_id = b.table_id and a.logicname = '"
					+ logicname + "'"; // 定义SQL语句
			// Log4jApp.logger(sql);
			Connection conn = null; // 声明Connection对象
			PreparedStatement pstmt = null; // 声明PreparedStatement对象
			ResultSet rs = null; // 声明ResultSet对象
			try {
				conn = DBUtils.getConnection(); // 获取数据库连接
				pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
				// 设置参数
				rs = pstmt.executeQuery();
				while (rs.next()) {
					logicexp = rs.getString("logicexp");
					phyexp = rs.getString("phyexp");

					logicgroupby = logicgroupby.replace(logicexp, phyexp);
				}
			} catch (SQLException e) {
				Log4jApp.logger(e.toString());
			} finally {
				DBUtils.close(rs); // 关闭结果集
				DBUtils.close(pstmt); // 关闭PreparedStatement
				DBUtils.close(conn); // 关闭连接
			}
		}
		// 转换为物理语句
		srep = logicgroupby;
		// Log4jApp.logger("物理查询分组语句:"+ srep);
		return srep;
	}

	/**
	 * 转换 逻辑查询表 物理查询表
	 * 
	 * @param locigfrom
	 */
	public void replacelogicfrom(String locigfrom) {
		//
		String logicname = "", logicexp = "", phyexp = "";
		// 由逻辑表构建表关系
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = 0; r < ilength; r++) {
			logicname = atname[r].trim();
			// 转换字段和条件
			String sql = "select logicname ,physicalname  from sys_t_table where logicname = '"
					+ logicname + "'"; // 定义SQL语句

			Connection conn = null; // 声明Connection对象
			PreparedStatement pstmt = null; // 声明PreparedStatement对象
			ResultSet rs = null; // 声明ResultSet对象
			try {
				conn = DBUtils.getConnection(); // 获取数据库连接
				pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
				// 设置参数
				rs = pstmt.executeQuery();
				while (rs.next()) {
					logicexp = rs.getString("logicname");
					phyexp = rs.getString("physicalname");
					locigfrom = locigfrom.replace(logicexp, phyexp);
				}
			} catch (SQLException e) {
				Log4jApp.logger(e.toString());
			} finally {
				DBUtils.close(rs); // 关闭结果集
				DBUtils.close(pstmt); // 关闭PreparedStatement
				DBUtils.close(conn); // 关闭连接
			}
		}
		// 转换为物理语句
		setPhyfrom(locigfrom);
		// Log4jApp.logger("物理查询表:"+ getPhyfrom());
	}

	/**
	 * 由sql语句查询统计数 进行校验sql语句的有效性
	 * 
	 * @param ssql
	 * @return
	 */
	/*
	 * public String validationfromsql(String ssql) { String sresult = "0",sql =
	 * "";
	 * 
	 * sql = "select count(*) as testcount from ("+ ssql +")";
	 * //log.debug("xiu校验SQL语句:"+sql); //Log4jApp.logger(sql); Connection conn =
	 * null; //声明Connection对象 PreparedStatement pstmt = null;
	 * //声明PreparedStatement对象 ResultSet rs = null; //声明ResultSet对象 try { conn =
	 * DBUtils.getConnection(); //获取数据库连接 pstmt = conn.prepareStatement(sql);
	 * //根据sql创建PreparedStatement //设置参数 rs = pstmt.executeQuery(); while
	 * (rs.next()) { sresult = rs.getString("testcount"); } } catch
	 * (SQLException e) { sresult = "-1"; Log4jApp.logger(e.toString()); }
	 * finally { DBUtils.close(rs); //关闭结果集 DBUtils.close(pstmt);
	 * //关闭PreparedStatement DBUtils.close(conn); //关闭连接 } return sresult; }
	 */
	public String validationfromsql(String ssql) {
		log.debug("validationfromsql:" + ssql);
		return "0";
	}

	/**
	 * 有SQL语句获取记录总数
	 * 
	 * @param tsql
	 * @return
	 */
	public String getcountfromsql(String tsql) {
		String srep = "";

		String sql = "select count(*) as count from (" + tsql + ")"; // 定义SQL语句
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("count");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	//
	//
	private String getPhyselect() {
		return physelect;
	}

	private void setPhyselect(String physelect) {
		this.physelect = physelect;
	}

	private String getPhyfrom() {
		return phyfrom;
	}

	private void setPhyfrom(String phyfrom) {
		this.phyfrom = phyfrom;
	}

	private String getPhywhererep() {
		return phywhererep;
	}

	private void setPhywhererep(String phywhererep) {
		this.phywhererep = phywhererep;
	}

	private String getPhypartid() {
		return phypartid;
	}

	private void setPhypartid(String phypartid) {
		this.phypartid = phypartid;
	}

	private String getPhychildid() {
		return phychildid;
	}

	private void setPhychildid(String phychildid) {
		this.phychildid = phychildid;
	}

	private String getPhychildexp() {
		return phychildexp;
	}

	private void setPhychildexp(String phychildexp) {
		this.phychildexp = phychildexp;
	}

	private String getPhypartexp() {
		return phypartexp;
	}

	private void setPhypartexp(String phypartexp) {
		this.phypartexp = phypartexp;
	}
	
	public static void main(String[] args) throws Exception {
		String name = "select INFO_T_FAMILY.ENSURECOUNT, INFO_T_FAMILY.AVGINCOME, INFO_T_FAMILYCLASS.CLASSTYPE860 from INFO_T_MEMBER, INFO_T_FAMILY, INFO_T_FAMILYCLASS where INFO_T_FAMILY.FAMILY_ID ='6405579' group by INFO_T_FAMILY.ENSURECOUNT, INFO_T_FAMILY.AVGINCOME, INFO_T_FAMILYCLASS.CLASSTYPE860";
	System.out.println(name.indexOf("INFO_T_FAMILYCLASS.CLASSTYPE"));
	System.out.println(name.substring(0,name.lastIndexOf("where")+5)+" INFO_T_MEMBER.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and INFO_T_FAMILYCLASS.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and 1=1 and  "+name.substring(name.lastIndexOf("where")+5));
	}
}
