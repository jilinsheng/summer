package com.mingda.common.ibatis.dao;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.InfoTAsset;

public class InfoTAssetDAO {
	
	private static SqlMapClient sqlMapper;

	public InfoTAssetDAO(SqlMapClient sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	public InfoTAsset insert(InfoTAsset infoTAsset) throws SQLException {
		return (InfoTAsset) sqlMapper.insert("InfoTAsset.insertAsset", infoTAsset);
	}

	public int update(InfoTAsset infoTAsset) throws SQLException {
		return sqlMapper.update("InfoTAsset.updateAssetByPrimaryKey", infoTAsset);
	}

	public InfoTAsset selectByPrimaryId(InfoTAsset infoTAsset)
			throws SQLException {
		return (InfoTAsset) sqlMapper.queryForObject("InfoTAsset.selectAssetByFamilyId",
				infoTAsset);

	}
}
