package com.mingda.action.info.search;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;

public class TableInfoQuery extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(TableInfoQuery1.class);

	/**
	 * 由表编号取得主键字段表达式
	 * 
	 * @param tableid
	 * @return
	 */
	public String getpkexpvaluefromid(String tableid) {
		String sresult = "";

		String sql = "select a.logicname || '.' || b.logicname as expvalue "
				+ "from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and b.status = '1' "
				+ "and b.isprimary = '1' and a.table_id = '" + tableid + "'"; // 定义SQL语句

		// log.debug("getpkexpvaluefromid:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sresult = rs.getString("expvalue");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}
		return sresult;
	}

	/**
	 * 由表编号取得外键字段表达式
	 * 
	 * @param tableid
	 * @return
	 */
	public String getfkexpvaluefromid(String tableid) {
		String sresult = "";

		String sql = "select a.logicname || '.' || b.logicname as expvalue "
				+ "from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and b.status = '1' "
				+ "and b.isforeign = '1' and a.table_id = '" + tableid + "'"; // 定义SQL语句

		// log.debug("getfkexpvaluefromid:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sresult = rs.getString("expvalue");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}
		return sresult;
	}

	/**
	 * 由sql语句查询统计数 进行校验sql语句的有效性
	 * 
	 * @param ssql
	 * @param conn
	 * @return
	 */
	public StringBuffer validationfromphysql(String ssql, Connection conn) {
		log.debug("validationfromphysql:"+ssql);
		StringBuffer sresult = new StringBuffer("");
		sresult.append("0");
		return sresult;
	}

	/*
	 * public StringBuffer validationfromphysql(String ssql, Connection conn) {
	 * StringBuffer sresult = new StringBuffer(""); String sql = "", stemp = "";
	 * // if (ssql.equals("") || ssql == null) { sresult.append("-1"); //
	 * 返回-1为表示查询语句不合法 return sresult; } // sql =
	 * "select count(*) as testcount from (" + ssql + ")"; //
	 * Log4jApp.logger(sql); //Connection conn = null; // 声明Connection对象
	 * PreparedStatement pstmt = null; // 声明PreparedStatement对象 ResultSet rs =
	 * null; // 声明ResultSet对象 try { //conn = DBUtils.getConnection(); // 获取数据库连接
	 * pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement // 设置参数
	 * rs = pstmt.executeQuery(); while (rs.next()) {
	 * sresult.append(rs.getString("testcount")); } } catch (SQLException e) {
	 * sresult.append("-1"); // 返回-1为表示查询语句不合法 Log4jApp.logger(e.toString()); }
	 * finally { ConnectionManager.closeQuietly(rs); // 关闭结果集
	 * ConnectionManager.closeQuietly(pstmt); // 关闭PreparedStatement
	 * //DBUtils.close(conn); // 关闭连接 } return sresult; }
	 */

	/**
	 * 由物理SQL语句计算记录总数
	 * 
	 * @param ssql
	 * @return
	 */
	public String getresultcountfromsql(String ssql) {
		String sresult = "0", sql = "";
		//
		sql = "select count(*) as count_num from (" + ssql + ")";

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
				sresult = rs.getString("count_num");
			}
		} catch (SQLException e) {
			sresult = "-1";
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}
		return sresult;
	}

	/**
	 * 由物理表名取得逻辑表名
	 * 
	 * @param pname
	 * @return
	 */
	public String gettablelocicfromphysic(String pname) {
		//
		String logicname = "";

		// 转换字段和条件
		String sql = "select logicname ,physicalname  from sys_t_table where physicalname = '"
				+ pname + "'"; // 定义SQL语句
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
				logicname = rs.getString("logicname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(logicname);
		return logicname;
	}

	/**
	 * 由物理字段名取得逻辑字段名
	 * 
	 * @param pname
	 * @return
	 */
	public String getfieldlocicfromphysic(String tname, String fname) {
		//
		String logicname = "";

		// 转换字段和条件
		String sql = "select b.logicname from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id " + "and b.physicalname = '"
				+ fname + "' " + "and a.physicalname = '" + tname + "' "; // 定义SQL语句
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				logicname = rs.getString("logicname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(logicname);
		return logicname;
	}

	/**
	 * 由表逻辑名称和字段逻辑名称取得字段物理名称
	 * 
	 * @param tname
	 * @param lname
	 * @return
	 */
	public String getfieldphysicfromlocic(String tname, String lname) {
		//
		String logicname = "";

		// 转换字段和条件
		String sql = "select b.physicalname from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id " + "and b.logicname = '"
				+ lname + "' " + "and a.logicname = '" + tname + "' "; // 定义SQL语句
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				logicname = rs.getString("physicalname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(logicname);
		return logicname;
	}

	/**
	 * 由物理名称取得编号
	 * 
	 * @param pname
	 * @return
	 */
	public String gettableidfromphysic(String pname) {
		//
		String tid = "";

		// 转换字段和条件
		String sql = "select table_id  from sys_t_table where physicalname = '"
				+ pname + "'"; // 定义SQL语句

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tid = rs.getString("table_id");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(tid);
		return tid;
	}

	/**
	 * 由表逻辑表取得物理表名称
	 * 
	 * @param lname
	 * @return
	 */
	public String gettablephysicfromlogic(String lname) {
		//
		String tname = "";

		// 转换字段和条件
		String sql = "select physicalname  from sys_t_table where logicname = '"
				+ lname + "'"; // 定义SQL语句

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tname = rs.getString("physicalname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(tid);
		return tname;
	}

	/**
	 * 由表逻辑名称和字段逻辑名称取得字段类型
	 * 
	 * @param tname
	 * @param fname
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getfieldtypefromlogicname(String tname, String fname) {
		//
		String ttype = "", temp = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		// 转换字段和条件
		String sql = "select b.physicalname,b.fieldtype from  sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and a.logicname = '"
				+ tname
				+ "' and b.logicname = '" + fname + "'"; // 定义SQL语句

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				temp = rs.getString("physicalname");
				if ("ORGANIZATION_ID".equals(temp)) {
					ttype = constantdefine.FIELDTYPE_DEPT;// 机构
				} else {
					ttype = rs.getString("fieldtype");
				}
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(tid);
		return ttype;
	}

	/**
	 * 由表物理名称和字段物理名称取得字段类型
	 * 
	 * @param tname
	 * @param fname
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getfieldtypefromphysicname(String tname, String fname) {
		//
		String ttype = "", temp = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		// 转换字段和条件
		String sql = "select b.physicalname,b.fieldtype from  sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and a.physicalname = '"
				+ tname + "' and b.physicalname = '" + fname + "'"; // 定义SQL语句
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
				temp = rs.getString("physicalname");
				if ("ORGANIZATION_ID".equals(temp)) {
					ttype = constantdefine.FIELDTYPE_DEPT;// 机构
				} else {
					ttype = rs.getString("fieldtype");
				}
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(tid);
		return ttype;
	}

	/**
	 * 由表逻辑名称和字段逻辑名称取得字段字典值
	 * 
	 * @param tname
	 * @param fname
	 * @return
	 */
	public String getdiscfromlogicname(String tname, String fname) {
		//
		String tdicsort = "";

		// 转换字段和条件
		String sql = "select b.dicsort from  sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and a.logicname = '" + tname
				+ "' and b.logicname = '" + fname + "'"; // 定义SQL语句

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tdicsort = rs.getString("dicsort");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}

		// Log4jApp.logger(tid);
		return tdicsort;
	}

	/**
	 * 有字典类型编号取得字典类型名称
	 * 
	 * @param tid
	 * @return
	 */
	public String getdiscfromdiscid(String tid) {
		String srep = "";
		String sql = "select dictsort_id, name, description from sys_t_dictsort where dictsort_id = '"
				+ tid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("name");
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

	/**
	 * 有字典编号取得字典名称
	 * 
	 * @param tid
	 * @return
	 */
	public String getdiscionaryfromid(String tid) {
		String srep = "";
		String sql = "select item from sys_t_dictionary where dictionary_id =  '"
				+ tid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("item");
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

	/**
	 * 由家庭ID取得家庭编号
	 * 
	 * @param tid
	 * @return
	 */
	public String getfamilynofromid(String tid) {
		String srep = "";
		String sql = "select familyno from info_t_family where family_id = '"
				+ tid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("familyno");
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

	/**
	 * 有类型取得分类列表
	 * 
	 * @param ttype
	 * @return
	 */
	public StringBuffer getCalssListsUl(String ttype) {
		StringBuffer shtml = new StringBuffer("");
		String sql = "", sdivid = "", sid = "", sdivname = "", sname = "", sflag = "", sfid = "";
		String spardivid = "";
		if (ttype.equals("family")) {
			// 家庭分类
			sql = "select a.classtype_id,b.logicname,a.status,b.field_id "
					+ "from sys_t_classtype a,sys_t_field b,sys_t_table c "
					+ "where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_FAMILYCLASS'"; // 定义SQL语句
		} else {
			// 成员分类
			sql = "select a.classtype_id,b.logicname,a.status,b.field_id "
					+ "from sys_t_classtype a,sys_t_field b,sys_t_table c "
					+ "where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_MEMBERCLASS'"; // 定义SQL语句
		}
		//
		shtml.append("<ul id=\"arrangableNodes\">");
		// Log4jApp.logger(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			//
			while (rs.next()) {
				//
				sid = rs.getString("classtype_id");
				sname = rs.getString("logicname");
				sflag = rs.getString("status");
				sfid = rs.getString("field_id");
				//
				sdivid = "class" + sid;
				sdivname = "☆" + sname;
				//
				spardivid = "divclass" + sid;
				//
				shtml.append("<li class='itemstyle licss' id=\"" + sdivid
						+ "\">");
				shtml.append("<div id=\"" + spardivid + "\">");
				shtml.append("<span class='pointer status" + sflag
						+ "'  onclick=\"getCalssItem('" + sid + "','" + sname
						+ "')\">" + sdivname + "</span>");
				if (sflag.equals("0") || sflag == null) {
					shtml.append("<span class='operation pointer' onclick=\"reeditClass('"
							+ sid + "','" + sfid + "')\">[启用]</span>");
					shtml.append("<span class='operation status" + sflag
							+ "' >[重命名]</span>");
				} else {
					shtml.append("<span class='operation pointer' onclick=\"delClass('"
							+ sid + "','" + sfid + "')\">[停用]</span>");
					shtml.append("<span class='operation pointer' onclick=\"renameClass('"
							+ sid
							+ "','"
							+ sfid
							+ "','"
							+ spardivid
							+ "','"
							+ sname + "')\">[重命名]</span>");
				}
				shtml.append("</div>");
				shtml.append("</li>");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		//
		shtml.append("</ul>");
		//
		return shtml;
	}

	/**
	 * 取得分类条件
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	String getCalssItem(String id) {
		JSONArray array = new JSONArray(); // 定义JSON数组
		// 定义SQL语句
		String sql = "select field_id,logicsql,physql,explains from sys_t_classtype where classtype_id = '"
				+ id + "'";
		// Log4jApp.logger(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			// 遍历结果集，给JSON数组中加入JSONObject
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("cfieldid", rs.getString(1));
				obj.put("clogicsql", rs.getString(2));
				obj.put("cphysql", rs.getString(3));
				obj.put("cexplains", rs.getString(4));
				array.add(obj);
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return array.toString();
	}

	/**
	 * 更新分类
	 * 
	 * @param calsstype
	 * @return
	 */
	@SuppressWarnings("static-access")
	String addCalssType(String calsstype, String classname, String classdesc) {
		String srep = "", seq = "", tid = "", tablename = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		// [210]字典状态default值[280]是[281]否[282]未核实
		String sid = constantdefine.CLASSSTATUS_ID;
		String snoid = constantdefine.CLASSSTATUS_NO;
		// 定义SQL语句
		String sql1 = "", sql2 = "", sql3 = "";
		// 是否存在
		boolean bclass = false;

		if (calsstype.equals("family")) {
			// 家庭分类表
			tid = gettableidfromphysic("INFO_T_FAMILYCLASS");
			tablename = "INFO_T_FAMILYCLASS";
		} else {
			// 成员分类表
			tid = gettableidfromphysic("INFO_T_MEMBERCLASS");
			tablename = "INFO_T_MEMBERCLASS";
		}
		// 该分类是否存在
		bclass = existsCalssType(classname, tid);
		if (bclass) {
			srep = "分类已经存在!";
			return srep;
		}
		// 字段表序列
		seq = getseqfromname("xfield_id");

		// 添加字段
		sql1 = "insert into sys_t_field "
				+ "(field_id,table_id,logicname,physicalname,fieldtype,length,defults,dicsort,digit,isprimary,isforeign,isexpand,readonly,control,proofrule,explains,status,isempty,visible) "
				+ "values " + "(" + seq + "," + tid + ",'" + classname
				+ "','CLASSTYPE" + seq + "','4','4','" + snoid + "','" + sid
				+ "','0','0','0','0','0','1','9','" + classdesc
				+ "','1','0','1')";
		// 添加分类
		sql2 = "insert into sys_t_classtype (classtype_id, field_id,explains,viewname,status) values "
				+ "(xclasstype_id.nextval,'"
				+ seq
				+ "','"
				+ classdesc
				+ "','SYS_V_CLASS" + seq + "','1')";
		// 修改表添加字段
		sql3 = "alter table " + tablename + " add CLASSTYPE" + seq
				+ " number default " + snoid + " ";

		// Log4jApp.logger(sql1);
		// Log4jApp.logger(sql2);
		// Log4jApp.logger(sql3);

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			pstmt = conn.prepareStatement(sql2); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			pstmt = conn.prepareStatement(sql3); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "新分类添加操作成功!";
		} catch (SQLException e) {
			try {
				srep = "新分类添加操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 停用分类
	 * 
	 * @param id
	 * @return
	 */
	String delCalssType(String id, String fid) {
		String srep = "";
		// 定义SQL语句
		String sql1 = "update sys_t_classtype set status = '0' where classtype_id = '"
				+ id + "'";
		String sql2 = "update sys_t_field set status = '0' where field_id = '"
				+ fid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			pstmt = conn.prepareStatement(sql2); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "停用分类操作成功!";
		} catch (SQLException e) {
			try {
				srep = "停用分类操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 启用分类
	 * 
	 * @param id
	 * @param fid
	 * @return
	 */
	String reeditClass(String id, String fid) {
		String srep = "";
		// 定义SQL语句
		String sql1 = "update sys_t_classtype set status = '1' where classtype_id = '"
				+ id + "'";
		String sql2 = "update sys_t_field set status = '1' where field_id = '"
				+ fid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			pstmt = conn.prepareStatement(sql2); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "启用分类操作成功!";
		} catch (SQLException e) {
			try {
				srep = "启用分类操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 更新分类属性
	 * 
	 * @param sid
	 * @param fid
	 * @param name
	 * @param desc
	 * @return
	 */
	String updateCalss(String sid, String fid, String name, String desc) {
		String srep = "";
		// 定义SQL语句
		String sql1 = "update sys_t_field set logicname = '" + name
				+ "',explains = '" + desc + "'  where field_id = '" + fid + "'";
		String sql2 = "update sys_t_classtype set explains = '" + desc
				+ "' where classtype_id = '" + sid + "'";
		// Log4jApp.logger(sql1);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			pstmt = conn.prepareStatement(sql2); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "更新分类属性操作成功!";
		} catch (SQLException e) {
			try {
				srep = "更新分类属性操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 获取分类标准SQL
	 * 
	 * @param id
	 * @param mode
	 * @return
	 */
	public String getClassSqlItem(String id, String mode) {
		String srep = "";
		String sql = "select classtype_id, field_id,physql, logicsql from sys_t_classtype where classtype_id = '"
				+ id + "'"; // 定义SQL语句
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (mode.equals("loc")) {
					srep = rs.getString("logicsql");
				} else if (mode.equals("phy")) {
					srep = rs.getString("physql");
				}
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 更新分类标准SQL
	 * 
	 * @param Id
	 * @param PhySql
	 * @param LocSql
	 * @return
	 */
	public String updateClassSql(String Id, String PhySql, String LocSql) {
		String srep = "";
		// 单引号转义去掉左右空格
		PhySql = PhySql.replace("'", "''");
		LocSql = LocSql.replace("'", "''");

		String sql = "update sys_t_classtype " + "set logicsql = '" + LocSql
				+ "',physql = '" + PhySql + "' " + "where classtype_id = '"
				+ Id + "'"; // 更新档次状态SQL

		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);// 根据sql创建PreparedStatement
			pstmt.executeUpdate(); // 执行
			conn.commit(); // 关闭
			srep = "更新标准保存操作成功!";
		} catch (SQLException e) {
			srep = "更新标准保存操作失败!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 更新分类
	 * 
	 * @param id
	 * @return
	 */
	String updateCalssTypeName(String id, String fid, String name) {
		String srep = "";
		// 定义SQL语句
		String sql = "update sys_t_field set logicname = '" + name
				+ "' where field_id = '" + fid + "'";
		// Log4jApp.logger(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "更新分类操作成功!";
		} catch (SQLException e) {
			try {
				srep = "更新分类操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 更新分类详细描述
	 * 
	 * @param id
	 * @param fid
	 * @param desc
	 * @return
	 */
	String updateCalssTypeDesc(String id, String fid, String desc) {
		String srep = "";
		// 定义SQL语句
		String sql1 = "update sys_t_field set explains = '" + desc
				+ "' where field_id = '" + fid + "'";
		String sql2 = "update sys_t_classtype set explains = '" + desc
				+ "' where classtype_id = '" + id + "'";
		// Log4jApp.logger(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			pstmt = conn.prepareStatement(sql2); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "更新分类详细描述操作成功!";
		} catch (SQLException e) {
			try {
				srep = "更新分类详细描述操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 更新分类Sql语句
	 * 
	 * @param id
	 * @param logicsql
	 * @param physql
	 * @return
	 */
	String updateCalssTypeSql(String id, String logicsql, String physql) {
		String srep = "", slocsql = "", sphysql = "";
		// 定义SQL语句
		// 单引号转义去掉左右空格
		slocsql = logicsql.replace("'", "''");
		sphysql = physql.replace("'", "''");

		String sql = "update sys_t_classtype " + "set logicsql = '" + slocsql
				+ "',physql = '" + sphysql + "' " + "where classtype_id = '"
				+ id + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			srep = "更新分类条件操作成功!";
		} catch (SQLException e) {
			try {
				srep = "更新分类条件操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}

	/**
	 * 分类已经存在
	 * 
	 * @param classname
	 * @param tid
	 * @return
	 */
	boolean existsCalssType(String classname, String tid) {
		boolean brep = false;
		// 转换字段和条件
		String sql = "select a.classtype_id from sys_t_classtype a,sys_t_field b "
				+ "where a.field_id = b.field_id and b.logicname = '"
				+ classname + "' and b.table_id = '" + tid + "'"; // 定义SQL语句

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 存在
				brep = true;
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}

		return brep;
	}

	/**
	 * 取得序列号
	 * 
	 * @param tname
	 * @return
	 */
	public String getseqfromname(String tname) {

		String sql = "select " + tname + ".nextval  from dual"; // 定义SQL语句

		BigDecimal name = (BigDecimal) getSession().createSQLQuery(sql)
				.uniqueResult();

		return name.toString();
	}

	// ======================================基本信息表操作END=================================
	/**
	 * 运算符下拉框选择列表
	 */
	@SuppressWarnings("static-access")
	public StringBuffer getexpselect(String tfieldtype) {
		StringBuffer shtml = new StringBuffer("");
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		shtml.append("<select  name=\"sqlsexp\" size=\"5\" id=\"sqlsexp\" ondblclick=\"selectclickexp()\">");
		// 简单运算符(查询条件)
		if (tfieldtype.equals(constantdefine.FIELDTYPE_EXP)) {
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"a\">大于</option>");
			shtml.append("<option value=\"b\">小于</option>");
			shtml.append("<option value=\"<>\">不等于</option>");
			shtml.append("<option value=\">=\">大于等于</option>");
			shtml.append("<option value=\"<=\">小于等于</option>");
			shtml.append("<option value=\"likel\">左匹配</option>");
			shtml.append("<option value=\"liker\">右匹配</option>");
			shtml.append("<option value=\"likec\">中间匹配</option>");
			shtml.append("<option value=\"is null\">空值</option>");
		}
		// 机构
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_DEPT)) {
			shtml.append("<option value=\"and\">并且</option>");
			shtml.append("<option value=\"or\">或者</option>");
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"likel\">左匹配</option>");
			shtml.append("<option value=\"likec\">中间匹配</option>");
		}
		// 日期型
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_DATE)) {
			shtml.append("<option value=\"and\">并且</option>");
			shtml.append("<option value=\"or\">或者</option>");
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"a\">大于</option>");
			shtml.append("<option value=\"b\">小于</option>");
			shtml.append("<option value=\"<>\">不等于</option>");
			shtml.append("<option value=\">=\">大于等于</option>");
			shtml.append("<option value=\"<=\">小于等于</option>");
		}
		// 数值型
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_NUM)) {
			shtml.append("<option value=\"and\">并且</option>");
			shtml.append("<option value=\"or\">或者</option>");
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"a\">大于</option>");
			shtml.append("<option value=\"b\">小于</option>");
			shtml.append("<option value=\"<>\">不等于</option>");
			shtml.append("<option value=\">=\">大于等于</option>");
			shtml.append("<option value=\"<=\">小于等于</option>");
			shtml.append("<option value=\"+\">加</option>");
			shtml.append("<option value=\"-\">减</option>");
			shtml.append("<option value=\"*\">乘以</option>");
			shtml.append("<option value=\"/\">除以</option>");
			shtml.append("<option value=\"sum()\">求和</option>");
			shtml.append("<option value=\"c\">单引号</option>");
			shtml.append("<option value=\"d\">双引号</option>");
			shtml.append("<option value=\",\">逗号</option>");
			shtml.append("<option value=\"()\">括号</option>");
			shtml.append("<option value=\"is null\">空值</option>");
			shtml.append("<option value=\"is not null\">不空</option>");
		}
		// 整型
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_INT)) {
			shtml.append("<option value=\"and\">并且</option>");
			shtml.append("<option value=\"or\">或者</option>");
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"a\">大于</option>");
			shtml.append("<option value=\"b\">小于</option>");
			shtml.append("<option value=\"<>\">不等于</option>");
			shtml.append("<option value=\">=\">大于等于</option>");
			shtml.append("<option value=\"<=\">小于等于</option>");
			shtml.append("<option value=\"+\">加</option>");
			shtml.append("<option value=\"-\">减</option>");
			shtml.append("<option value=\"*\">乘以</option>");
			shtml.append("<option value=\"/\">除以</option>");
			shtml.append("<option value=\"sum()\">求和</option>");
			shtml.append("<option value=\"c\">单引号</option>");
			shtml.append("<option value=\"d\">双引号</option>");
			shtml.append("<option value=\",\">逗号</option>");
			shtml.append("<option value=\"()\">括号</option>");
			shtml.append("<option value=\"is null\">空值</option>");
			shtml.append("<option value=\"is not null\">不空</option>");
		}
		// 字符型
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_CHR)) {
			shtml.append("<option value=\"and\">并且</option>");
			shtml.append("<option value=\"or\">或者</option>");
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"<>\">不等于</option>");
			shtml.append("<option value=\"likel\">左匹配</option>");
			shtml.append("<option value=\"liker\">右匹配</option>");
			shtml.append("<option value=\"likec\">中间匹配</option>");
			shtml.append("<option value=\"count(*)\">统计</option>");
			shtml.append("<option value=\"c\">单引号</option>");
			shtml.append("<option value=\"d\">双引号</option>");
			shtml.append("<option value=\",\">逗号</option>");
			shtml.append("<option value=\"()\">括号</option>");
			shtml.append("<option value=\"is null\">空值</option>");
			shtml.append("<option value=\"is not null\">不空</option>");
			shtml.append("<option value=\"group by\">分组</option>");
			shtml.append("<option value=\"order by\">排序</option>");
		}
		// 字典型
		else {
			shtml.append("<option value=\"and\">并且</option>");
			shtml.append("<option value=\"or\">或者</option>");
			shtml.append("<option value=\"=\">等于</option>");
			shtml.append("<option value=\"<>\">不等于</option>");
			shtml.append("<option value=\"c\">单引号</option>");
			shtml.append("<option value=\"d\">双引号</option>");
			shtml.append("<option value=\",\">逗号</option>");
			shtml.append("<option value=\"()\">括号</option>");
			shtml.append("<option value=\"count(*)\">统计</option>");
			shtml.append("<option value=\"is null\">空值</option>");
		}
		shtml.append("</select>");
		return shtml;
	}

	/**
	 * 取得用户的机构级别 1社区2街道3区县4市(州)5省厅
	 * 
	 * @param empid
	 * @return
	 */
	public String getempdepth(String empid) {
		String srep = "0";
		//
		String sql = "select depth from sys_t_employee a,sys_t_organization b "
				+ "where a.organization_id = b.organization_id and employee_id = '"
				+ empid + "'"; // 定义SQL语句
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("depth");
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

	/**
	 * 由用户取得所属机构ID
	 * 
	 * @param empid
	 * @return
	 */
	public String getempdepid(String empid) {
		String srep = "";

		String sql = "select organization_id from sys_t_employee  where employee_id = '"
				+ empid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("organization_id");
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

	/**
	 * 由家庭编号取得家庭所属机构
	 * 
	 * @param fmid
	 * @return
	 */
	public String getfamilydepid(String fmid) {
		String srep = "";

		String sql = "select organization_id from info_t_family where family_id = '"
				+ fmid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("organization_id");
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

	/**
	 * 由家庭编号取得家庭户主ID
	 * 
	 * @param fmid
	 * @return
	 */
	public String getfamilymasterid(String fmid) {
		String srep = "";

		String sql = "select masterid from info_t_family where family_id = '"
				+ fmid + "'";
		// log.debug("sql:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("masterid");
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

	/**
	 * 由家庭编号取得家庭状态
	 * 
	 * @param fmid
	 * @return
	 */
	public String getfamilystarus(String fmid) {
		String srep = "";

		String sql = "select flag from biz_t_familystatus where family_id = '"
				+ fmid + "'";

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("flag");
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

	/**
	 * 由用户ID取得用户名称
	 * 
	 * @param empid
	 * @return
	 */
	public String getempname(String empid) {
		String srep = "";

		String sql = "select name from sys_t_empext where employee_id = '"
				+ empid + "'";
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("name");
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

	/**
	 * 生成审批页面排序下拉框
	 * 
	 * @param sname
	 * @return
	 */
	@SuppressWarnings({ "unused", "static-access" })
	public StringBuffer getCheckPolicyOrder(String sname, String empid,
			String pid) {
		StringBuffer shtml = new StringBuffer("");
		String tftable, tfield, tfamily;
		String id = "", familyid = "", mastername = "", deptid = "";

		String sptype = "", empth = "", stemp = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		//
		// 添加保障人口、计算收入、上期救助金、拟发救助金(分城市、农村)
		// 政策业务查询处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		sptype = policymanagecheckquery.getPolicyObjTypeFromId(pid);
		//
		//
		//
		shtml.append("<select id=\"" + sname + "\" style = \"font-size:12px\">");
		// 家庭信息
		tftable = "INFO_T_FAMILY";// 家庭表
		tfamily = gettablelocicfromphysic(tftable);
		//
		//
		tfield = "FAMILYNO";// 家庭编号
		familyid = getfieldlocicfromphysic(tftable, tfield);
		//
		stemp = "INFO_T_FAMILY.FAMILYNO";
		shtml.append("<option value=\"" + stemp + "\">" + familyid
				+ "</option>");

		tfield = "MASTERNAME";// 户主姓名
		mastername = getfieldlocicfromphysic(tftable, tfield);
		//
		stemp = "INFO_T_FAMILY.MASTERNAME";
		shtml.append("<option value=\"" + stemp + "\">" + mastername
				+ "</option>");

		tfield = "ORGANIZATION_ID";// 家庭机构
		deptid = getfieldlocicfromphysic(tftable, tfield);
		//
		stemp = "INFO_T_FAMILY.ORGANIZATION_ID";
		shtml.append("<option value=\"" + stemp + "\">" + deptid + "</option>");
		//
		//
		if (sptype.equals("0")) {// 城市保障
			stemp = "INFO_T_FAMILY.ENSURECOUNT";
			shtml.append("<option value=\"" + stemp + "\">保障人口</option>");
			stemp = "INFO_T_FAMILY.CONSULTINCOME";
			shtml.append("<option value=\"" + stemp + "\">计算收入</option>");
		} else if (sptype.equals("0")) {// 农村保障
			stemp = "INFO_T_FAMILY.ENSURECOUNT2";
			shtml.append("<option value=\"" + stemp + "\">保障人口</option>");
			stemp = "INFO_T_FAMILY.CONSULTINCOME2";
			shtml.append("<option value=\"" + stemp + "\">计算收入</option>");
		}
		//
		/*
		 * stemp = "BIZ_T_OPTCHECK.RECHECKMONEY";
		 * shtml.append("<option value=\""+stemp+"\">上期救助金</option>"); stemp =
		 * "BIZ_T_OPTCHECK.COUNTMONEY";
		 * shtml.append("<option value=\""+stemp+"\">拟发救助金</option>");
		 */
		// 处理成汇总字段
		stemp = "RECHECKMONEY";
		shtml.append("<option value=\"" + stemp + "\">上期救助金</option>");
		stemp = "COUNTMONEY";
		shtml.append("<option value=\"" + stemp + "\">拟发救助金</option>");

		//
		empth = getempdepth(empid);
		//
		if (empth.equals(constantdefine.POLICYQUERYCODE_5)) {// 社区
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG1";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG2";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG3";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG4";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG5";
		}
		shtml.append("<option value=\"" + stemp + "\">评议结果</option>");
		shtml.append("</select>");

		return shtml;
	}

	/**
	 * 取得审批汇总
	 * 
	 * @param sql
	 * @param pid
	 * @return
	 */
	public String getsumfieldfromsql(String sql, String pid) {
		String srep = "0", sptype = "", stemp = "";
		// 添加保障人口、计算收入、上期救助金、拟发救助金(分城市、农村)
		// 政策业务查询处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		sptype = policymanagecheckquery.getPolicyObjTypeFromId(pid);
		if (sptype.equals("0")) {// 城市保障
			stemp = "count(*),sum(ENSURECOUNT),sum(CONSULTINCOME),sum(RECHECKMONEY),sum(COUNTMONEY)";
		} else if (sptype.equals("1")) {// 农村保障
			stemp = "count(*),sum(ENSURECOUNT2),sum(CONSULTINCOME2),sum(RECHECKMONEY),sum(COUNTMONEY)";
		}
		sql = "select " + stemp + " from (" + sql + ")"; // 定义SQL语句
		// log.debug("sumfieldsql:"+sql);
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stemp = rs.getString(1);
				if (null == stemp) {
					stemp = "0";
				}
				srep = stemp;
				//
				stemp = rs.getString(2);
				if (null == stemp) {
					stemp = "0";
				}
				srep += "," + stemp;
				//
				stemp = rs.getString(3);
				if (null == stemp) {
					stemp = "0";
				}
				srep += "," + stemp;
				//
				stemp = rs.getString(4);
				if (null == stemp) {
					stemp = "0";
				}
				srep += "," + stemp;
				//
				stemp = rs.getString(5);
				if (null == stemp) {
					stemp = "0";
				}
				srep += "," + stemp;
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

	/**
	 * 由列名称 查询物理SQL语句 SQL记录数 生成XML
	 * 
	 * @param xmlth
	 * @param sql
	 * @param sqlresultcount
	 * @return
	 */
	public String getInfoResultForXml(String xmlth, String sql,
			String sqlresultcount) {
		String srep = "-1";// 无查询结果或者错误
		String columname = xmlth;

		// Log4jApp.logger(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象

		String[] columu_name = null;
		int columnCount = 0;
		//
		columu_name = columname.split(",");
		columnCount = columu_name.length;

		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB18030");
		Element root = doc.addElement("root");
		Element data = root.addElement("data");
		//
		// 表格记录总数
		Element eCount = data.addElement("count");
		Element eCountChild = eCount.addElement("num");
		eCountChild.setText(sqlresultcount);
		//
		//
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			// 表格
			Element eHead = data.addElement("ehead");
			//
			for (int i = 0; i < columnCount; i++) {
				Element eHeadChild = eHead.addElement("cell");
				eHeadChild.setText(columu_name[i]);
				eHeadChild.attributeValue("id", columu_name[i]);
			}
			Element datas = data.addElement("list");
			while (rs.next()) {
				Element entity = datas.addElement("entity");
				for (int i = 0; i < columnCount; i++) {
					Element col = entity.addElement("col");
					String tname = rs.getString(i + 1);
					if (tname == null || tname.length() <= 0) {
						col.setText("0");
					} else {
						col.setText(tname);
					}
				}
			}
			//
			Node node = root.selectSingleNode("/root/data");
			// Log4jApp.logger(node.asXML()+"   :xiuxiuxiuxiux:    "+doc.asXML());
			srep = node.asXML();
			//
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		//

		return srep;
	}
}
