package com.mingda.action.policy.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.common.myjdbc.ConnectionManager;

public class PublicComm {
	static Logger log = Logger.getLogger(PublicComm.class);
	/**
	 * ��ʽ������
	 * 
	 * @param stime
	 * @return
	 */
	public String getformatdt(String sdt) {
		String srep = "";
		Date date = null;
		SimpleDateFormat oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (sdt == null || sdt.equals("")) {
		} else {
			try {
				date = (Date) oformat.parse(sdt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			oformat = new SimpleDateFormat("yyyy-MM-dd");
			srep = oformat.format(date);
		}
		return srep;
	}

	/**
	 * ��ʽ��ʱ��
	 * 
	 * @param stime
	 * @return
	 */
	public String getformattime(String stime) {
		String srep = "";
		Date date = null;
		SimpleDateFormat oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (stime == null || stime.equals("")) {
		} else {
			try {
				date = (Date) oformat.parse(stime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			srep = oformat.format(date);
		}
		return srep;
	}

	/**
	 * ��ȡϵͳʱ�䲢��ʽ��������
	 * 
	 * @return
	 */
	public String getSysTimeFormatDate() {
		String srep = "", stime = "2009-12-10 00:00:01";
		// ����SQL���
		String sql = "select sysdate from dual";
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stime = rs.getString("sysdate");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
		}
		// ��ʽ��ϵͳ����
		Date date = null;
		SimpleDateFormat oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = (Date) oformat.parse(stime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oformat = new SimpleDateFormat("yyyy-MM-dd");
		srep = oformat.format(date);
		return srep;
	}

	/**
	 * ��ȡҵ��ѡ��������
	 * 
	 * @param sname
	 * @return
	 */
	public StringBuffer getPolicySelect(String sname, String mode) {
		StringBuffer shtml = new StringBuffer("");
		//
		shtml.append("<select id=\"" + sname + "\" style = \"font-size:12px\">");
		// ����SQL���
		String sql = "";
		if ("new".equals(mode)) {
			sql = "select policy_id,name " + "from doper_t_policy "
					+ "where flag = '1' and policy_id <>'21' "
					+ "order by sequence ";
		} else {
			sql = "select policy_id,name " + "from doper_t_policy "
					+ "where flag = '1' " + "order by sequence ";
		}
		log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = ConnectionManager.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String tempid = rs.getString("policy_id");
				String tempname = rs.getString("name");
				shtml.append("<option value=\"" + tempid + "\">" + tempname
						+ "</option>");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			//ConnectionManager.close(rs); // �رս����
			//ConnectionManager.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
		}
		shtml.append("</select>");
		return shtml;
	}

	/**
	 * ��ȡ��ѯ��¼��
	 * 
	 * @param sql
	 * @return
	 */
	public String getResultCount(String sql) {
		String srep = "0";
		// ����SQL���
		sql = "select count(*) rowcount from (" + sql + ")";
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			//conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("rowcount");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			//DBUtils.close(rs); // �رս����
			//DBUtils.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
		}
		return srep;
	}

	/**
	 * ִ���û�SQL���
	 * 
	 * @param sql
	 * @return
	 */
	public String ExeSQLFromUserSQL(String sql) {
		String srep = "";
		//
		if ("".equals(sql)) {
			return srep;
		}
		// log.debug(sql);

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			//conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);// ����sql����PreparedStatement
			 log.debug("���±��ϵ�exe"+sql);
			pstmt.execute(); // ִ��
			conn.commit(); // �ر�
			//
			srep = "�����ɹ�!";

		} catch (SQLException e) {
			try {
				srep = "����ʧ��!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			//DBUtils.close(pstmt); // �ر�PreparedStatement
			///DBUtils.close(conn); // �ر�����
			//ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
		}
		return srep;
	}
}
