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

public class TableInfoQuery1 extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(TableInfoQuery1.class);

	/**
	 * �ɱ���ȡ�������ֶα��ʽ
	 * 
	 * @param tableid
	 * @return
	 */
	public String getpkexpvaluefromid(String tableid) {
		String sresult = "";

		String sql = "select a.logicname || '.' || b.logicname as expvalue "
				+ "from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and b.status = '1' "
				+ "and b.isprimary = '1' and a.table_id = '" + tableid + "'"; // ����SQL���

		// log.debug("getpkexpvaluefromid:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sresult = rs.getString("expvalue");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		return sresult;
	}

	/**
	 * �ɱ���ȡ������ֶα��ʽ
	 * 
	 * @param tableid
	 * @return
	 */
	public String getfkexpvaluefromid(String tableid) {
		String sresult = "";

		String sql = "select a.logicname || '.' || b.logicname as expvalue "
				+ "from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and b.status = '1' "
				+ "and b.isforeign = '1' and a.table_id = '" + tableid + "'"; // ����SQL���

		// log.debug("getfkexpvaluefromid:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sresult = rs.getString("expvalue");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		return sresult;
	}

	/**
	 * ��sql����ѯͳ���� ����У��sql������Ч��
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
	 * ����-1Ϊ��ʾ��ѯ��䲻�Ϸ� return sresult; } // sql =
	 * "select count(*) as testcount from (" + ssql + ")"; //
	 * Log4jApp.logger(sql); //Connection conn = null; // ����Connection����
	 * PreparedStatement pstmt = null; // ����PreparedStatement���� ResultSet rs =
	 * null; // ����ResultSet���� try { //conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
	 * pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement // ���ò���
	 * rs = pstmt.executeQuery(); while (rs.next()) {
	 * sresult.append(rs.getString("testcount")); } } catch (SQLException e) {
	 * sresult.append("-1"); // ����-1Ϊ��ʾ��ѯ��䲻�Ϸ� Log4jApp.logger(e.toString()); }
	 * finally { ConnectionManager.closeQuietly(rs); // �رս����
	 * ConnectionManager.closeQuietly(pstmt); // �ر�PreparedStatement
	 * //DBUtils.close(conn); // �ر����� } return sresult; }
	 */

	/**
	 * ������SQL�������¼����
	 * 
	 * @param ssql
	 * @return
	 */
	public String getresultcountfromsql(String ssql) {
		String sresult = "0", sql = "";
		//
		sql = "select count(*) as count_num from (" + ssql + ")";

		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sresult = rs.getString("count_num");
			}
		} catch (SQLException e) {
			sresult = "-1";
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		return sresult;
	}

	/**
	 * ���������ȡ���߼�����
	 * 
	 * @param pname
	 * @return
	 */
	public String gettablelocicfromphysic(String pname) {
		//
		String logicname = "";

		// ת���ֶκ�����
		String sql = "select logicname ,physicalname  from sys_t_table where physicalname = '"
				+ pname + "'"; // ����SQL���
		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				logicname = rs.getString("logicname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(logicname);
		return logicname;
	}

	/**
	 * �������ֶ���ȡ���߼��ֶ���
	 * 
	 * @param pname
	 * @return
	 */
	public String getfieldlocicfromphysic(String tname, String fname) {
		//
		String logicname = "";

		// ת���ֶκ�����
		String sql = "select b.logicname from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id " + "and b.physicalname = '"
				+ fname + "' " + "and a.physicalname = '" + tname + "' "; // ����SQL���
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				logicname = rs.getString("logicname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(logicname);
		return logicname;
	}

	/**
	 * �ɱ��߼����ƺ��ֶ��߼�����ȡ���ֶ���������
	 * 
	 * @param tname
	 * @param lname
	 * @return
	 */
	public String getfieldphysicfromlocic(String tname, String lname) {
		//
		String logicname = "";

		// ת���ֶκ�����
		String sql = "select b.physicalname from sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id " + "and b.logicname = '"
				+ lname + "' " + "and a.logicname = '" + tname + "' "; // ����SQL���
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				logicname = rs.getString("physicalname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(logicname);
		return logicname;
	}

	/**
	 * ����������ȡ�ñ��
	 * 
	 * @param pname
	 * @return
	 */
	public String gettableidfromphysic(String pname) {
		//
		String tid = "";

		// ת���ֶκ�����
		String sql = "select table_id  from sys_t_table where physicalname = '"
				+ pname + "'"; // ����SQL���

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tid = rs.getString("table_id");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(tid);
		return tid;
	}

	/**
	 * �ɱ��߼���ȡ�����������
	 * 
	 * @param lname
	 * @return
	 */
	public String gettablephysicfromlogic(String lname) {
		//
		String tname = "";

		// ת���ֶκ�����
		String sql = "select physicalname  from sys_t_table where logicname = '"
				+ lname + "'"; // ����SQL���

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tname = rs.getString("physicalname");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(tid);
		return tname;
	}

	/**
	 * �ɱ��߼����ƺ��ֶ��߼�����ȡ���ֶ�����
	 * 
	 * @param tname
	 * @param fname
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getfieldtypefromlogicname(String tname, String fname) {
		//
		String ttype = "", temp = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		// ת���ֶκ�����
		String sql = "select b.physicalname,b.fieldtype from  sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and a.logicname = '"
				+ tname
				+ "' and b.logicname = '" + fname + "'"; // ����SQL���

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				temp = rs.getString("physicalname");
				if ("ORGANIZATION_ID".equals(temp)) {
					ttype = constantdefine.FIELDTYPE_DEPT;// ����
				} else {
					ttype = rs.getString("fieldtype");
				}
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(tid);
		return ttype;
	}

	/**
	 * �ɱ��������ƺ��ֶ���������ȡ���ֶ�����
	 * 
	 * @param tname
	 * @param fname
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getfieldtypefromphysicname(String tname, String fname) {
		//
		String ttype = "", temp = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		// ת���ֶκ�����
		String sql = "select b.physicalname,b.fieldtype from  sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and a.physicalname = '"
				+ tname + "' and b.physicalname = '" + fname + "'"; // ����SQL���
		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				temp = rs.getString("physicalname");
				if ("ORGANIZATION_ID".equals(temp)) {
					ttype = constantdefine.FIELDTYPE_DEPT;// ����
				} else {
					ttype = rs.getString("fieldtype");
				}
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(tid);
		return ttype;
	}

	/**
	 * �ɱ��߼����ƺ��ֶ��߼�����ȡ���ֶ��ֵ�ֵ
	 * 
	 * @param tname
	 * @param fname
	 * @return
	 */
	public String getdiscfromlogicname(String tname, String fname) {
		//
		String tdicsort = "";

		// ת���ֶκ�����
		String sql = "select b.dicsort from  sys_t_table a,sys_t_field b "
				+ "where a.table_id = b.table_id and a.logicname = '" + tname
				+ "' and b.logicname = '" + fname + "'"; // ����SQL���

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tdicsort = rs.getString("dicsort");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}

		// Log4jApp.logger(tid);
		return tdicsort;
	}

	/**
	 * ���ֵ����ͱ��ȡ���ֵ���������
	 * 
	 * @param tid
	 * @return
	 */
	public String getdiscfromdiscid(String tid) {
		String srep = "";
		String sql = "select dictsort_id, name, description from sys_t_dictsort where dictsort_id = '"
				+ tid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("name");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���ֵ���ȡ���ֵ�����
	 * 
	 * @param tid
	 * @return
	 */
	public String getdiscionaryfromid(String tid) {
		String srep = "";
		String sql = "select item from sys_t_dictionary where dictionary_id =  '"
				+ tid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("item");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * �ɼ�ͥIDȡ�ü�ͥ���
	 * 
	 * @param tid
	 * @return
	 */
	public String getfamilynofromid(String tid) {
		String srep = "";
		String sql = "select familyno from info_t_family where family_id = '"
				+ tid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("familyno");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ������ȡ�÷����б�
	 * 
	 * @param ttype
	 * @return
	 */
	public StringBuffer getCalssListsUl(String ttype) {
		StringBuffer shtml = new StringBuffer("");
		String sql = "", sdivid = "", sid = "", sdivname = "", sname = "", sflag = "", sfid = "";
		String spardivid = "";
		if (ttype.equals("family")) {
			// ��ͥ����
			sql = "select a.classtype_id,b.logicname,a.status,b.field_id "
					+ "from sys_t_classtype a,sys_t_field b,sys_t_table c "
					+ "where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_FAMILYCLASS'"; // ����SQL���
		} else {
			// ��Ա����
			sql = "select a.classtype_id,b.logicname,a.status,b.field_id "
					+ "from sys_t_classtype a,sys_t_field b,sys_t_table c "
					+ "where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_MEMBERCLASS'"; // ����SQL���
		}
		//
		shtml.append("<ul id=\"arrangableNodes\">");
		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
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
				sdivname = "��" + sname;
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
							+ sid + "','" + sfid + "')\">[����]</span>");
					shtml.append("<span class='operation status" + sflag
							+ "' >[������]</span>");
				} else {
					shtml.append("<span class='operation pointer' onclick=\"delClass('"
							+ sid + "','" + sfid + "')\">[ͣ��]</span>");
					shtml.append("<span class='operation pointer' onclick=\"renameClass('"
							+ sid
							+ "','"
							+ sfid
							+ "','"
							+ spardivid
							+ "','"
							+ sname + "')\">[������]</span>");
				}
				shtml.append("</div>");
				shtml.append("</li>");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		//
		shtml.append("</ul>");
		//
		return shtml;
	}

	/**
	 * ȡ�÷�������
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	String getCalssItem(String id) {
		JSONArray array = new JSONArray(); // ����JSON����
		// ����SQL���
		String sql = "select field_id,logicsql,physql,explains from sys_t_classtype where classtype_id = '"
				+ id + "'";
		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			// �������������JSON�����м���JSONObject
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
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return array.toString();
	}

	/**
	 * ���·���
	 * 
	 * @param calsstype
	 * @return
	 */
	@SuppressWarnings("static-access")
	String addCalssType(String calsstype, String classname, String classdesc) {
		String srep = "", seq = "", tid = "", tablename = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		// [210]�ֵ�״̬defaultֵ[280]��[281]��[282]δ��ʵ
		String sid = constantdefine.CLASSSTATUS_ID;
		String snoid = constantdefine.CLASSSTATUS_NO;
		// ����SQL���
		String sql1 = "", sql2 = "", sql3 = "";
		// �Ƿ����
		boolean bclass = false;

		if (calsstype.equals("family")) {
			// ��ͥ�����
			tid = gettableidfromphysic("INFO_T_FAMILYCLASS");
			tablename = "INFO_T_FAMILYCLASS";
		} else {
			// ��Ա�����
			tid = gettableidfromphysic("INFO_T_MEMBERCLASS");
			tablename = "INFO_T_MEMBERCLASS";
		}
		// �÷����Ƿ����
		bclass = existsCalssType(classname, tid);
		if (bclass) {
			srep = "�����Ѿ�����!";
			return srep;
		}
		// �ֶα�����
		seq = getseqfromname("xfield_id");

		// ����ֶ�
		sql1 = "insert into sys_t_field "
				+ "(field_id,table_id,logicname,physicalname,fieldtype,length,defults,dicsort,digit,isprimary,isforeign,isexpand,readonly,control,proofrule,explains,status,isempty,visible) "
				+ "values " + "(" + seq + "," + tid + ",'" + classname
				+ "','CLASSTYPE" + seq + "','4','4','" + snoid + "','" + sid
				+ "','0','0','0','0','0','1','9','" + classdesc
				+ "','1','0','1')";
		// ��ӷ���
		sql2 = "insert into sys_t_classtype (classtype_id, field_id,explains,viewname,status) values "
				+ "(xclasstype_id.nextval,'"
				+ seq
				+ "','"
				+ classdesc
				+ "','SYS_V_CLASS" + seq + "','1')";
		// �޸ı�����ֶ�
		sql3 = "alter table " + tablename + " add CLASSTYPE" + seq
				+ " number default " + snoid + " ";

		// Log4jApp.logger(sql1);
		// Log4jApp.logger(sql2);
		// Log4jApp.logger(sql3);

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			pstmt = conn.prepareStatement(sql2); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			pstmt = conn.prepareStatement(sql3); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "�·�����Ӳ����ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "�·�����Ӳ���ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ͣ�÷���
	 * 
	 * @param id
	 * @return
	 */
	String delCalssType(String id, String fid) {
		String srep = "";
		// ����SQL���
		String sql1 = "update sys_t_classtype set status = '0' where classtype_id = '"
				+ id + "'";
		String sql2 = "update sys_t_field set status = '0' where field_id = '"
				+ fid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			pstmt = conn.prepareStatement(sql2); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "ͣ�÷�������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "ͣ�÷������ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���÷���
	 * 
	 * @param id
	 * @param fid
	 * @return
	 */
	String reeditClass(String id, String fid) {
		String srep = "";
		// ����SQL���
		String sql1 = "update sys_t_classtype set status = '1' where classtype_id = '"
				+ id + "'";
		String sql2 = "update sys_t_field set status = '1' where field_id = '"
				+ fid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			pstmt = conn.prepareStatement(sql2); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "���÷�������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "���÷������ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���·�������
	 * 
	 * @param sid
	 * @param fid
	 * @param name
	 * @param desc
	 * @return
	 */
	String updateCalss(String sid, String fid, String name, String desc) {
		String srep = "";
		// ����SQL���
		String sql1 = "update sys_t_field set logicname = '" + name
				+ "',explains = '" + desc + "'  where field_id = '" + fid + "'";
		String sql2 = "update sys_t_classtype set explains = '" + desc
				+ "' where classtype_id = '" + sid + "'";
		// Log4jApp.logger(sql1);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			pstmt = conn.prepareStatement(sql2); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "���·������Բ����ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "���·������Բ���ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ��ȡ�����׼SQL
	 * 
	 * @param id
	 * @param mode
	 * @return
	 */
	public String getClassSqlItem(String id, String mode) {
		String srep = "";
		String sql = "select classtype_id, field_id,physql, logicsql from sys_t_classtype where classtype_id = '"
				+ id + "'"; // ����SQL���
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
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
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���·����׼SQL
	 * 
	 * @param Id
	 * @param PhySql
	 * @param LocSql
	 * @return
	 */
	public String updateClassSql(String Id, String PhySql, String LocSql) {
		String srep = "";
		// ������ת��ȥ�����ҿո�
		PhySql = PhySql.replace("'", "''");
		LocSql = LocSql.replace("'", "''");

		String sql = "update sys_t_classtype " + "set logicsql = '" + LocSql
				+ "',physql = '" + PhySql + "' " + "where classtype_id = '"
				+ Id + "'"; // ���µ���״̬SQL

		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);// ����sql����PreparedStatement
			pstmt.executeUpdate(); // ִ��
			conn.commit(); // �ر�
			srep = "���±�׼��������ɹ�!";
		} catch (SQLException e) {
			srep = "���±�׼�������ʧ��!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���·���
	 * 
	 * @param id
	 * @return
	 */
	String updateCalssTypeName(String id, String fid, String name) {
		String srep = "";
		// ����SQL���
		String sql = "update sys_t_field set logicname = '" + name
				+ "' where field_id = '" + fid + "'";
		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "���·�������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "���·������ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���·�����ϸ����
	 * 
	 * @param id
	 * @param fid
	 * @param desc
	 * @return
	 */
	String updateCalssTypeDesc(String id, String fid, String desc) {
		String srep = "";
		// ����SQL���
		String sql1 = "update sys_t_field set explains = '" + desc
				+ "' where field_id = '" + fid + "'";
		String sql2 = "update sys_t_classtype set explains = '" + desc
				+ "' where classtype_id = '" + id + "'";
		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			pstmt = conn.prepareStatement(sql2); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "���·�����ϸ���������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "���·�����ϸ��������ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���·���Sql���
	 * 
	 * @param id
	 * @param logicsql
	 * @param physql
	 * @return
	 */
	String updateCalssTypeSql(String id, String logicsql, String physql) {
		String srep = "", slocsql = "", sphysql = "";
		// ����SQL���
		// ������ת��ȥ�����ҿո�
		slocsql = logicsql.replace("'", "''");
		sphysql = physql.replace("'", "''");

		String sql = "update sys_t_classtype " + "set logicsql = '" + slocsql
				+ "',physql = '" + sphysql + "' " + "where classtype_id = '"
				+ id + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			srep = "���·������������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "���·�����������ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * �����Ѿ�����
	 * 
	 * @param classname
	 * @param tid
	 * @return
	 */
	boolean existsCalssType(String classname, String tid) {
		boolean brep = false;
		// ת���ֶκ�����
		String sql = "select a.classtype_id from sys_t_classtype a,sys_t_field b "
				+ "where a.field_id = b.field_id and b.logicname = '"
				+ classname + "' and b.table_id = '" + tid + "'"; // ����SQL���

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ����
				brep = true;
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}

		return brep;
	}

	/**
	 * ȡ�����к�
	 * 
	 * @param tname
	 * @return
	 */
	public String getseqfromname(String tname) {

		String sql = "select " + tname + ".nextval  from dual"; // ����SQL���

		BigDecimal name = (BigDecimal) getSession().createSQLQuery(sql)
				.uniqueResult();

		return name.toString();
	}

	// ======================================������Ϣ�����END=================================
	/**
	 * �����������ѡ���б�
	 */
	@SuppressWarnings("static-access")
	public StringBuffer getexpselect(String tfieldtype) {
		StringBuffer shtml = new StringBuffer("");
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		shtml.append("<select  name=\"sqlsexp\" size=\"5\" id=\"sqlsexp\" ondblclick=\"selectclickexp()\">");
		// �������(��ѯ����)
		if (tfieldtype.equals(constantdefine.FIELDTYPE_EXP)) {
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"a\">����</option>");
			shtml.append("<option value=\"b\">С��</option>");
			shtml.append("<option value=\"<>\">������</option>");
			shtml.append("<option value=\">=\">���ڵ���</option>");
			shtml.append("<option value=\"<=\">С�ڵ���</option>");
			shtml.append("<option value=\"likel\">��ƥ��</option>");
			shtml.append("<option value=\"liker\">��ƥ��</option>");
			shtml.append("<option value=\"likec\">�м�ƥ��</option>");
			shtml.append("<option value=\"is null\">��ֵ</option>");
		}
		// ����
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_DEPT)) {
			shtml.append("<option value=\"and\">����</option>");
			shtml.append("<option value=\"or\">����</option>");
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"likel\">��ƥ��</option>");
			shtml.append("<option value=\"likec\">�м�ƥ��</option>");
		}
		// ������
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_DATE)) {
			shtml.append("<option value=\"and\">����</option>");
			shtml.append("<option value=\"or\">����</option>");
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"a\">����</option>");
			shtml.append("<option value=\"b\">С��</option>");
			shtml.append("<option value=\"<>\">������</option>");
			shtml.append("<option value=\">=\">���ڵ���</option>");
			shtml.append("<option value=\"<=\">С�ڵ���</option>");
		}
		// ��ֵ��
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_NUM)) {
			shtml.append("<option value=\"and\">����</option>");
			shtml.append("<option value=\"or\">����</option>");
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"a\">����</option>");
			shtml.append("<option value=\"b\">С��</option>");
			shtml.append("<option value=\"<>\">������</option>");
			shtml.append("<option value=\">=\">���ڵ���</option>");
			shtml.append("<option value=\"<=\">С�ڵ���</option>");
			shtml.append("<option value=\"+\">��</option>");
			shtml.append("<option value=\"-\">��</option>");
			shtml.append("<option value=\"*\">����</option>");
			shtml.append("<option value=\"/\">����</option>");
			shtml.append("<option value=\"sum()\">���</option>");
			shtml.append("<option value=\"c\">������</option>");
			shtml.append("<option value=\"d\">˫����</option>");
			shtml.append("<option value=\",\">����</option>");
			shtml.append("<option value=\"()\">����</option>");
			shtml.append("<option value=\"is null\">��ֵ</option>");
			shtml.append("<option value=\"is not null\">����</option>");
		}
		// ����
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_INT)) {
			shtml.append("<option value=\"and\">����</option>");
			shtml.append("<option value=\"or\">����</option>");
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"a\">����</option>");
			shtml.append("<option value=\"b\">С��</option>");
			shtml.append("<option value=\"<>\">������</option>");
			shtml.append("<option value=\">=\">���ڵ���</option>");
			shtml.append("<option value=\"<=\">С�ڵ���</option>");
			shtml.append("<option value=\"+\">��</option>");
			shtml.append("<option value=\"-\">��</option>");
			shtml.append("<option value=\"*\">����</option>");
			shtml.append("<option value=\"/\">����</option>");
			shtml.append("<option value=\"sum()\">���</option>");
			shtml.append("<option value=\"c\">������</option>");
			shtml.append("<option value=\"d\">˫����</option>");
			shtml.append("<option value=\",\">����</option>");
			shtml.append("<option value=\"()\">����</option>");
			shtml.append("<option value=\"is null\">��ֵ</option>");
			shtml.append("<option value=\"is not null\">����</option>");
		}
		// �ַ���
		else if (tfieldtype.equals(constantdefine.FIELDTYPE_CHR)) {
			shtml.append("<option value=\"and\">����</option>");
			shtml.append("<option value=\"or\">����</option>");
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"<>\">������</option>");
			shtml.append("<option value=\"likel\">��ƥ��</option>");
			shtml.append("<option value=\"liker\">��ƥ��</option>");
			shtml.append("<option value=\"likec\">�м�ƥ��</option>");
			shtml.append("<option value=\"count(*)\">ͳ��</option>");
			shtml.append("<option value=\"c\">������</option>");
			shtml.append("<option value=\"d\">˫����</option>");
			shtml.append("<option value=\",\">����</option>");
			shtml.append("<option value=\"()\">����</option>");
			shtml.append("<option value=\"is null\">��ֵ</option>");
			shtml.append("<option value=\"is not null\">����</option>");
			shtml.append("<option value=\"group by\">����</option>");
			shtml.append("<option value=\"order by\">����</option>");
		}
		// �ֵ���
		else {
			shtml.append("<option value=\"and\">����</option>");
			shtml.append("<option value=\"or\">����</option>");
			shtml.append("<option value=\"=\">����</option>");
			shtml.append("<option value=\"<>\">������</option>");
			shtml.append("<option value=\"c\">������</option>");
			shtml.append("<option value=\"d\">˫����</option>");
			shtml.append("<option value=\",\">����</option>");
			shtml.append("<option value=\"()\">����</option>");
			shtml.append("<option value=\"count(*)\">ͳ��</option>");
			shtml.append("<option value=\"is null\">��ֵ</option>");
		}
		shtml.append("</select>");
		return shtml;
	}

	/**
	 * ȡ���û��Ļ������� 1����2�ֵ�3����4��(��)5ʡ��
	 * 
	 * @param empid
	 * @return
	 */
	public String getempdepth(String empid) {
		String srep = "0";
		//
		String sql = "select depth from sys_t_employee a,sys_t_organization b "
				+ "where a.organization_id = b.organization_id and employee_id = '"
				+ empid + "'"; // ����SQL���
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("depth");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���û�ȡ����������ID
	 * 
	 * @param empid
	 * @return
	 */
	public String getempdepid(String empid) {
		String srep = "";

		String sql = "select organization_id from sys_t_employee  where employee_id = '"
				+ empid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("organization_id");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * �ɼ�ͥ���ȡ�ü�ͥ��������
	 * 
	 * @param fmid
	 * @return
	 */
	public String getfamilydepid(String fmid) {
		String srep = "";

		String sql = "select organization_id from info_t_family where family_id = '"
				+ fmid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("organization_id");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * �ɼ�ͥ���ȡ�ü�ͥ����ID
	 * 
	 * @param fmid
	 * @return
	 */
	public String getfamilymasterid(String fmid) {
		String srep = "";

		String sql = "select masterid from info_t_family where family_id = '"
				+ fmid + "'";
		// log.debug("sql:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("masterid");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * �ɼ�ͥ���ȡ�ü�ͥ״̬
	 * 
	 * @param fmid
	 * @return
	 */
	public String getfamilystarus(String fmid) {
		String srep = "";

		String sql = "select flag from biz_t_familystatus where family_id = '"
				+ fmid + "'";

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("flag");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ���û�IDȡ���û�����
	 * 
	 * @param empid
	 * @return
	 */
	public String getempname(String empid) {
		String srep = "";

		String sql = "select name from sys_t_empext where employee_id = '"
				+ empid + "'";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {

				srep = rs.getString("name");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	/**
	 * ��������ҳ������������
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
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		//
		// ��ӱ����˿ڡ��������롢���ھ������ⷢ������(�ֳ��С�ũ��)
		// ����ҵ���ѯ������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		sptype = policymanagecheckquery.getPolicyObjTypeFromId(pid);
		//
		//
		//
		shtml.append("<select id=\"" + sname + "\" style = \"font-size:12px\">");
		// ��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";// ��ͥ��
		tfamily = gettablelocicfromphysic(tftable);
		//
		//
		tfield = "FAMILYNO";// ��ͥ���
		familyid = getfieldlocicfromphysic(tftable, tfield);
		//
		stemp = "INFO_T_FAMILY.FAMILYNO";
		shtml.append("<option value=\"" + stemp + "\">" + familyid
				+ "</option>");

		tfield = "MASTERNAME";// ��������
		mastername = getfieldlocicfromphysic(tftable, tfield);
		//
		stemp = "INFO_T_FAMILY.MASTERNAME";
		shtml.append("<option value=\"" + stemp + "\">" + mastername
				+ "</option>");

		tfield = "ORGANIZATION_ID";// ��ͥ����
		deptid = getfieldlocicfromphysic(tftable, tfield);
		//
		stemp = "INFO_T_FAMILY.ORGANIZATION_ID";
		shtml.append("<option value=\"" + stemp + "\">" + deptid + "</option>");
		//
		//
		if (sptype.equals("0")) {// ���б���
			stemp = "INFO_T_FAMILY.ENSURECOUNT";
			shtml.append("<option value=\"" + stemp + "\">�����˿�</option>");
			stemp = "INFO_T_FAMILY.CONSULTINCOME";
			shtml.append("<option value=\"" + stemp + "\">��������</option>");
		} else if (sptype.equals("0")) {// ũ�屣��
			stemp = "INFO_T_FAMILY.ENSURECOUNT2";
			shtml.append("<option value=\"" + stemp + "\">�����˿�</option>");
			stemp = "INFO_T_FAMILY.CONSULTINCOME2";
			shtml.append("<option value=\"" + stemp + "\">��������</option>");
		}
		//
		/*
		 * stemp = "BIZ_T_OPTCHECK.RECHECKMONEY";
		 * shtml.append("<option value=\""+stemp+"\">���ھ�����</option>"); stemp =
		 * "BIZ_T_OPTCHECK.COUNTMONEY";
		 * shtml.append("<option value=\""+stemp+"\">�ⷢ������</option>");
		 */
		// ����ɻ����ֶ�
		stemp = "RECHECKMONEY";
		shtml.append("<option value=\"" + stemp + "\">���ھ�����</option>");
		stemp = "COUNTMONEY";
		shtml.append("<option value=\"" + stemp + "\">�ⷢ������</option>");

		//
		empth = getempdepth(empid);
		//
		if (empth.equals(constantdefine.POLICYQUERYCODE_5)) {// ����
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG1";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_4)) {// �ֵ�
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG2";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_3)) {// ����
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG3";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_2)) {// �о�
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG4";
		} else if (empth.equals(constantdefine.POLICYQUERYCODE_1)) {// ʡ��
			stemp = "BIZ_T_OPTCHECK.CHECKFLAG5";
		}
		shtml.append("<option value=\"" + stemp + "\">������</option>");
		shtml.append("</select>");

		return shtml;
	}

	/**
	 * ȡ����������
	 * 
	 * @param sql
	 * @param pid
	 * @return
	 */
	public String getsumfieldfromsql(String sql, String pid) {
		String srep = "0", sptype = "", stemp = "";
		// ��ӱ����˿ڡ��������롢���ھ������ⷢ������(�ֳ��С�ũ��)
		// ����ҵ���ѯ������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		sptype = policymanagecheckquery.getPolicyObjTypeFromId(pid);
		if (sptype.equals("0")) {// ���б���
			stemp = "count(*),sum(ENSURECOUNT),sum(CONSULTINCOME),sum(RECHECKMONEY),sum(COUNTMONEY)";
		} else if (sptype.equals("1")) {// ũ�屣��
			stemp = "count(*),sum(ENSURECOUNT2),sum(CONSULTINCOME2),sum(RECHECKMONEY),sum(COUNTMONEY)";
		}
		sql = "select " + stemp + " from (" + sql + ")"; // ����SQL���
		// log.debug("sumfieldsql:"+sql);
		// Log4jApp.logger("sql:"+sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
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
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}

		return srep;
	}

	/**
	 * �������� ��ѯ����SQL��� SQL��¼�� ����XML
	 * 
	 * @param xmlth
	 * @param sql
	 * @param sqlresultcount
	 * @return
	 */
	public String getInfoResultForXml(String xmlth, String sql,
			String sqlresultcount) {
		String srep = "-1";// �޲�ѯ������ߴ���
		String columname = xmlth;

		// Log4jApp.logger(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����

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
		// ����¼����
		Element eCount = data.addElement("count");
		Element eCountChild = eCount.addElement("num");
		eCountChild.setText(sqlresultcount);
		//
		//
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			// ���
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
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		//

		return srep;
	}
}
