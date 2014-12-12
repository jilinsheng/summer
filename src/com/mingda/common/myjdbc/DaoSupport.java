package com.mingda.common.myjdbc;

import java.util.List;

public abstract class DaoSupport {
	/**
	 * 
	 * {��ѯ���󷽷�}
	 * 
	 * @param sql
	 * @param rowMapper
	 * @return
	 * @author:LJ
	 */
	@SuppressWarnings({ "rawtypes" })
	protected abstract List query(String sql, RowMapper rowMapper);

	/**
	 * 
	 * {��������ѯ���󷽷�}
	 * 
	 * @param sql
	 * @param objs
	 * @param rowMapper
	 * @return
	 * @author:LJ
	 */
	@SuppressWarnings({ "rawtypes" })
	protected abstract List query(String sql, Object[] objs, RowMapper rowMapper);

	/**
	 * 
	 * {���³��󷽷�}
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 * @author:LJ
	 */
	protected abstract int update(final String sql, Object[] objs);

	/**
	 * 
	 * {���������³��󷽷�}
	 * 
	 * @param sql
	 * @return
	 * @author:LJ
	 */
	protected abstract int update(final String sql);

	/**
	 * 
	 * {��������󷽷�}
	 * 
	 * @param sql
	 * @return
	 * @author:LJ
	 */
	protected abstract int[] batchUpdate(String[] sql);

	/**
	 * 
	 * {����������������}
	 * 
	 * @param sql
	 * @param pss
	 * @return
	 * @author:LJ
	 */
	protected abstract int[] batchUpdate(final String sql,
			final BatchPreparedStatementSetter pss);
}
