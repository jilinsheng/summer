package com.mingda.common.ibatis.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.InfoTFamilyincome;
import java.sql.SQLException;

public class InfoTFamilyincomeDAO {
	private static SqlMapClient sqlMapper;

	public InfoTFamilyincomeDAO(SqlMapClient sqlMapper) {
		this.sqlMapper = sqlMapper;
	};

	public InfoTFamilyincome insert(InfoTFamilyincome infoTFamilyincome)
			throws SQLException {
		return (InfoTFamilyincome) sqlMapper.insert("InfoTFamilyincome.insertFamilyincome",
				infoTFamilyincome);

	}

	public int update(InfoTFamilyincome infoTFamilyincome) throws SQLException {
		return sqlMapper.update("InfoTFamilyincome.updateFamilyincomeByPrimaryKey",
				infoTFamilyincome);
	}

	public int updateSelective(InfoTFamilyincome infoTFamilyincome)
			throws SQLException {
		return sqlMapper.update("InfoTFamilyincome.updateFamilyincomeByPrimaryKeySelective",
				infoTFamilyincome);
	}

	public InfoTFamilyincome selectByPrimaryKey(
			InfoTFamilyincome infoTFamilyincome) throws SQLException {
		return (InfoTFamilyincome) sqlMapper.queryForObject(
				"InfoTFamilyincome.selectIncomeByPrimaryKey", infoTFamilyincome);
	}

	public InfoTFamilyincome selectByFamilyId(
			InfoTFamilyincome infoTFamilyincome) throws SQLException {
		return (InfoTFamilyincome) sqlMapper.queryForObject(
				"InfoTFamilyincome.selectIncomeByFamilyId", infoTFamilyincome);
	}
}