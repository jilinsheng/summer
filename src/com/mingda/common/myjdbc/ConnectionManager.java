package com.mingda.common.myjdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionManager {
	static Logger log = Logger.getLogger(ConnectionManager.class);
	private static String jdbcDriver = null;
	private static String jdbcUrl = null;
	private static String jdbcUser = null;
	private static String jdbcPasswd = null;

	@SuppressWarnings("rawtypes")
	private static ThreadLocal connectionHolder = new ThreadLocal();

	/**
	 * 取得Connection
	 * 
	 * @return
	 * @throws IOException
	 */
	private static void getDbinfo() {
		InputStream is = null;
		is = ConnectionManager.class.getClassLoader().getResourceAsStream(
				"database.properties");// 读取流
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbcDriver = p.getProperty("jdbcDriver");
		jdbcUrl = p.getProperty("jdbcUrl");
		jdbcUser = p.getProperty("jdbcUser");
		jdbcPasswd = p.getProperty("jdbcPasswd");
	}

	@SuppressWarnings("unchecked")
	public static Connection getConnection() throws  SQLException   {

		Connection conn = (Connection) connectionHolder.get();
		if (conn == null) {
			try {
				getDbinfo();
				Class.forName(jdbcDriver);
				conn = DriverManager.getConnection(jdbcUrl, jdbcUser,
						jdbcPasswd);
				log.debug("打开数据库连接hashcode="+conn.hashCode());
				connectionHolder.set(conn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return conn;
	}

	/**
	 * 关闭Connection
	 */
	public static void closeQuietly() {
		Connection conn = (Connection) connectionHolder.get();
		if (conn != null) {
			try {
				conn.close();
				connectionHolder.remove();
				// connectionHolder.set(null);
				log.debug("关闭数据库连接hashcode="+conn.hashCode());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeQuietly(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * {关闭结果集}
	 * 
	 * @param rs
	 * @author:LJ
	 */
	public static void closeQuietly(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}