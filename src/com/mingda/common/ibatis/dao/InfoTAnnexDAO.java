package com.mingda.common.ibatis.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.InfoTAnnex;

public class InfoTAnnexDAO {

	private static SqlMapClient sqlMapper;

	public InfoTAnnexDAO(SqlMapClient sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	public List<InfoTAnnex> selectByExample(InfoTAnnex annex)
			throws SQLException {
		List<InfoTAnnex> list = sqlMapper.queryForList(
				"InfoTAnnex.ibatorgenerated_select", annex);
		return list;
	}

	public InfoTAnnex selectByPrimary(InfoTAnnex annex) throws SQLException {
	
		return (InfoTAnnex) sqlMapper.queryForObject("InfoTAnnex.ibatorgenerated_selectByPrimaryKey",
				annex);
	}

	public void insert(InfoTAnnex record) throws SQLException {
		sqlMapper.insert("InfoTAnnex.ibatorgenerated_insert", record);
	}

	public int deleteByPrimaryKey(Integer annexId) throws SQLException {
		InfoTAnnex key = new InfoTAnnex();
		key.setAnnexId(annexId);
		int rows = sqlMapper.delete(
				"InfoTAnnex.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}
}
