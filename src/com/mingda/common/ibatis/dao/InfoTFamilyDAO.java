package com.mingda.common.ibatis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.InfoTFamily;

public class InfoTFamilyDAO {

	private static SqlMapClient sqlMapper;

	public InfoTFamilyDAO(SqlMapClient sqlMapper) {
		this.sqlMapper = sqlMapper;
	};

	public List getFamilyForList() throws SQLException {
		return sqlMapper.queryForList("InfoTFamily.selectAllFamily");
	}

	public Object insertFamily(InfoTFamily family) throws SQLException {
		return sqlMapper.insert("InfoTFamily.insertFamily", family);
	}

	public int updateFamily(InfoTFamily family) throws SQLException {
		return sqlMapper.update("InfoTFamily.updateFamily", family);
	}

	public int updateFamilySelective(InfoTFamily family) throws SQLException {
		return sqlMapper.update("InfoTFamily.updateByPrimaryKeySelective",
				family);
	}

	public void deleteFamily(String familyId) throws SQLException {
		sqlMapper.delete(familyId);
	}

	public void updateFamilySalType(Integer  familyId) throws SQLException {
		sqlMapper.update("InfoTFamily.updateFamilySalType", familyId); 
	}

	public InfoTFamily selectFamilyById(String familyId) throws SQLException {
		return (InfoTFamily) sqlMapper.queryForObject(
				"InfoTFamily.selectFamilyById", new Integer(familyId));
	}

	public String getSalReason(String familyId) throws SQLException {
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select '有患危重病:' as reason1, mem.sicken as reason2 "
				+ " from info_t_member mem" + " where mem.family_id = '"
				+ familyId
				+ "'"
				+ " and sicken in (820, 821, 822, 823, 824, 825, 826, 827, 828)"
				+ " union all"
				+ " select '有重度残疾:', mem.deformity"
				+ " from info_t_member mem"
				+ " where mem.family_id = '"
				+ familyId
				+ "'"
				+ " and mem.deformity in (829, 830, 831, 832, 833, 834, 835)"
				+ " and workability = 222"
				+ " union"
				+ " select '有年老体弱:', mem.oldandinfirm"
				+ " from info_t_member mem"
				+ " where mem.family_id = '"
				+ familyId
				+ "'"
				+ " and oldandinfirm in (838, 839, 840, 841, 842)"
				+ " and workability = 222"
				+ " union"
				+ " select '有正就读学生', mem.student"
				+ " from info_t_member mem"
				+ " where mem.family_id = '"
				+ familyId
				+ "'"
				+ " and student in (843, 844, 845)";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		String a = "";
		while (rs.next()) {
			a = rs.getString(1) + a;
		}
		ps.close();
		rs.close();
		return a;
	}

	public List<String[]> valRemoveUndo(String familyId) throws SQLException {
		List<String[]> list = new ArrayList<String[]>();
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select fm.membername , "
				+ "( select dict.item from sys_t_dictionary dict where dict.dictionary_id  = fm.papertype ) papertype ,"
				+ " fm.paperid from info_t_member fm  where fm.family_id = '"
				+ familyId
				+ "' and exists (select 1 from info_t_member m  , info_t_family fam "
				+ " where m.papertype = fm.papertype "
				+ " and m.paperid = fm.paperid " + " and m.family_id <> '"
				+ familyId
				+ "' and fam.family_id =m.family_id and fam.status ='1' )";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String[] ol = new String[3];
			ol[0] = rs.getString("membername");
			ol[1] = rs.getString("papertype");
			ol[2] = rs.getString("paperid");
			list.add(ol);
		}
		ps.close();
		rs.close();
		return list;
	}

	public boolean isRemove(String familyId) throws SQLException {
		boolean flag = true;
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from BIZ_T_OPTCHECK c where   c.family_id='"
				+ familyId + "'";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			flag = false;
		}
		ps.close();
		rs.close();
		return flag;
	}

	public void deleteOptcheck(String familyId) throws SQLException { 
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		try {
			String sql = "delete from BIZ_T_OPTCHECKIDEA where optcheck_id in"
					+ " ( select c.optcheck_id from BIZ_T_OPTCHECK c where c.family_id='"
					+ familyId + "')";
			ps = con.prepareStatement(sql);
			ps.execute();

			sql = "delete from BIZ_T_OPTCHECK c where c.family_id='" + familyId
					+ "'";
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			ps.close();
		}
	}
}
