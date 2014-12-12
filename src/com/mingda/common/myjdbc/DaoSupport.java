package com.mingda.common.myjdbc;

import java.util.List;

public abstract class DaoSupport {
	/**
	 * 
	 * {查询抽象方法}
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
	 * {带参数查询抽象方法}
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
	 * {更新抽象方法}
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 * @author:LJ
	 */
	protected abstract int update(final String sql, Object[] objs);

	/**
	 * 
	 * {带参数更新抽象方法}
	 * 
	 * @param sql
	 * @return
	 * @author:LJ
	 */
	protected abstract int update(final String sql);

	/**
	 * 
	 * {批处理抽象方法}
	 * 
	 * @param sql
	 * @return
	 * @author:LJ
	 */
	protected abstract int[] batchUpdate(String[] sql);

	/**
	 * 
	 * {带参数批量处理方法}
	 * 
	 * @param sql
	 * @param pss
	 * @return
	 * @author:LJ
	 */
	protected abstract int[] batchUpdate(final String sql,
			final BatchPreparedStatementSetter pss);
}
