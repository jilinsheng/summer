package com.mingda.common.myjdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface BatchPreparedStatementSetter {
	/**
	 * 
	 * {���ò���ֵ}
	 * 
	 * @param preparedstatement
	 * @param i
	 * @throws SQLException
	 * @author:LJ
	 */
	public void setValues(PreparedStatement preparedstatement, int i)
			throws SQLException;

	/**
	 * 
	 * {��ȡ���������}
	 * 
	 * @return
	 * @author:LJ
	 */
	public int getBatchSize();
}