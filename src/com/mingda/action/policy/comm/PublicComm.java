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
	 * 格式化日期
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
	 * 格式化时间
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
	 * 获取系统时间并格式化成日期
	 * 
	 * @return
	 */
	public String getSysTimeFormatDate() {
		String srep = "", stime = "2009-12-10 00:00:01";
		// 定义SQL语句
		String sql = "select sysdate from dual";
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stime = rs.getString("sysdate");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			//DBUtils.close(conn); // 关闭连接
		}
		// 格式化系统日期
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
	 * 获取业务选择下拉框
	 * 
	 * @param sname
	 * @return
	 */
	public StringBuffer getPolicySelect(String sname, String mode) {
		StringBuffer shtml = new StringBuffer("");
		//
		shtml.append("<select id=\"" + sname + "\" style = \"font-size:12px\">");
		// 定义SQL语句
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
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = ConnectionManager.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
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
			//ConnectionManager.close(rs); // 关闭结果集
			//ConnectionManager.close(pstmt); // 关闭PreparedStatement
			//DBUtils.close(conn); // 关闭连接
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
		}
		shtml.append("</select>");
		return shtml;
	}

	/**
	 * 获取查询记录数
	 * 
	 * @param sql
	 * @return
	 */
	public String getResultCount(String sql) {
		String srep = "0";
		// 定义SQL语句
		sql = "select count(*) rowcount from (" + sql + ")";
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			//conn = DBUtils.getConnection(); // 获取数据库连接
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("rowcount");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			//DBUtils.close(rs); // 关闭结果集
			//DBUtils.close(pstmt); // 关闭PreparedStatement
			//DBUtils.close(conn); // 关闭连接
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
		}
		return srep;
	}

	/**
	 * 执行用户SQL语句
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

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			//conn = DBUtils.getConnection(); // 获取数据库连接
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);// 根据sql创建PreparedStatement
			 log.debug("更新保障单exe"+sql);
			pstmt.execute(); // 执行
			conn.commit(); // 关闭
			//
			srep = "操作成功!";

		} catch (SQLException e) {
			try {
				srep = "操作失败!";
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			//DBUtils.close(pstmt); // 关闭PreparedStatement
			///DBUtils.close(conn); // 关闭连接
			//ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
		}
		return srep;
	}
}
