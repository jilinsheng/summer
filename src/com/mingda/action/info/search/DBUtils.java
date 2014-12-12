package com.mingda.action.info.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import com.mingda.common.SessionFactory;
import com.mingda.common.myjdbc.ConnectionManager;

public class DBUtils {
	static Logger log = Logger.getLogger(DBUtils.class);

	public static Connection getConnection() throws SQLException {
		/*
		 * DataSource ds = null;//声明DataSource对象 Connection conn =
		 * null;//声明Connection对象 try{ Context initCtx = new InitialContext();
		 * Context envCtx = (Context)initCtx.lookup("java:comp/env"); ds =
		 * (DataSource)envCtx.lookup("jdbc/book"); if(ds!=null){ conn =
		 * ds.getConnection(); log.debug("[系统信息]:数据源连接完成"); }else{
		 * log.debug("[系统信息]:数据源连接失败"); } }catch(Exception ex){
		 * log.debug("[系统异常]:"+ex); } if (conn == null) { throw new
		 * SQLException("ajax.DBUtils: Cannot get connection."); } return conn;
		 */
		// 声明Connection对象
		// Session session = SessionFactory.getSession();
		// Connection conn = session.connection();
		return ConnectionManager.getConnection();
	}

	public static void close(Connection conn) {
		ConnectionManager.closeQuietly();
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			log.debug("ajax.DBUtils: Cannot close statement.");
		}

	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			log.debug("ajax.DBUtils: Cannot close resultset.");
		}
	}
}
