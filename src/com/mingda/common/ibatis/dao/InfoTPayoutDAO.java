package com.mingda.common.ibatis.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.InfoTPayout;
import java.sql.SQLException;

public class InfoTPayoutDAO {
	private static SqlMapClient sqlMapper;

	public InfoTPayoutDAO(SqlMapClient sqlMapper) {
		this.sqlMapper = sqlMapper;
	};

	public InfoTPayout insert(InfoTPayout infoTPayout)
			throws SQLException {
		return (InfoTPayout) sqlMapper.insert("InfoTPayout.insertPayout", infoTPayout);
	}

	public int update(InfoTPayout infoTPayout)
			throws SQLException {
		return sqlMapper.update("InfoTPayout.updatePayoutByPrimaryKey",
				infoTPayout);
	}

	public int updateSelective(InfoTPayout infoTPayout)
			throws SQLException {
		return sqlMapper.update("InfoTPayout.updatePayoutByPrimaryKeySelective",
				infoTPayout);
	}

	public InfoTPayout selectByPrimaryKey(
			InfoTPayout infoTPayout) throws SQLException {
		return (InfoTPayout) sqlMapper.queryForObject(
				"InfoTPayout.selectPayoutByPrimaryKey", infoTPayout);
	}

	public InfoTPayout selectByFamilyId(
			InfoTPayout infoTPayout) throws SQLException {
		return (InfoTPayout) sqlMapper.queryForObject("InfoTPayout.selectPayoutByFamilyId",
				infoTPayout);
	}
}