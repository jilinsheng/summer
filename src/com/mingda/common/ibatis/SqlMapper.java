package com.mingda.common.ibatis;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapper {
	private static SqlMapClient sqlMapper;
	//private static DaoManager daoManager;
	static {
		try {
			Reader reader = Resources
					.getResourceAsReader("SqlMapConfig.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			//daoManager = DaoManagerBuilder.buildDaoManager(reader);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(
					"Something bad happened while building the SqlMapClient instance."
							+ e, e);
		}
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}

	/*public static DaoManager getDaoManager() {
		return daoManager;
	}*/
}
