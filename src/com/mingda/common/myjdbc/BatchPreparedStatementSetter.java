package com.mingda.common.myjdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface BatchPreparedStatementSetter {
	/**
	 * 
	 * {设置参数值}
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
	 * {获取批处理个数}
	 * 
	 * @return
	 * @author:LJ
	 */
	public int getBatchSize();
}