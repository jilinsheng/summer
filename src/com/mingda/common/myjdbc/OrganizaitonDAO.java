package com.mingda.common.myjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mingda.entity.SysTOrganization;

public class OrganizaitonDAO extends DaoSupport {

	@SuppressWarnings("rawtypes")
	public List<SysTOrganization> query(String sql, RowMapper rowMapper) {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SysTOrganization> query(String sql, Object[] objs,
			RowMapper rowMapper) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SysTOrganization> list = new ArrayList<SysTOrganization>();
		try {
			con = ConnectionManager.getConnection();
			preparedStatement = con.prepareStatement(sql);
			if (objs != null && objs.length > 0) {
				for (int i = 0, n = objs.length; i < n; i++) {
					preparedStatement.setObject(i + 1, objs[i]);
				}
			}
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				list.add((SysTOrganization) rowMapper.map(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(preparedStatement);
		}
		return list;
	}

	@Override
	public int update(String sql, Object[] objs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] batchUpdate(String[] sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
		// TODO Auto-generated method stub
		return null;
	}

}
