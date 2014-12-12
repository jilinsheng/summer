package com.mingda.common.myjdbc;

import java.sql.SQLException;

public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(ConnectionManager.getConnection().hashCode());
			System.out.println(ConnectionManager.getConnection().hashCode());
			System.out.println(ConnectionManager.getConnection().hashCode());
			System.out.println(ConnectionManager.getConnection().hashCode());
			System.out.println(ConnectionManager.getConnection().hashCode());
			System.out.println(ConnectionManager.getConnection().hashCode());
			ConnectionManager.closeQuietly();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
