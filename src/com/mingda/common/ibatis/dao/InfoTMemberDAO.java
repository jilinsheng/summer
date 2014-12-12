package com.mingda.common.ibatis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.data.InfoTFamily;
import com.mingda.common.ibatis.data.InfoTMember;

public class InfoTMemberDAO {
	static Logger log = Logger.getLogger(InfoTMemberDAO.class);
	private static SqlMapClient sqlMapper;

	public InfoTMemberDAO(SqlMapClient sqlMapper) {
		this.sqlMapper = sqlMapper;
	};

	public InfoTMember selectMemberById(String memberId) throws SQLException {
		return (InfoTMember) sqlMapper.queryForObject(
				"InfoTMember.selectMemberById", new Integer(memberId));
	}

	public InfoTMember selectMaster(String familyId) throws SQLException {
		InfoTMember infoTMember = new InfoTMember();
		if (null == familyId || "".equals(familyId)) {
			log.debug("没有家庭表id");
		} else {
			infoTMember.setFamilyId(new Integer(familyId));
			infoTMember.setRelmaster(new Long("112"));
			infoTMember = (InfoTMember) sqlMapper.queryForObject(
					"InfoTMember.selectMaster", infoTMember);
		}

		return infoTMember;
	}

	public Object updateMember(InfoTMember member) throws SQLException {
		return sqlMapper.update("InfoTMember.updateMember", member);
	}

	public Object insertmember(InfoTMember member) throws SQLException {
		return sqlMapper.insert("InfoTMember.insertMember", member);
	}

	public Object updateMemberPicpath(InfoTMember member) throws SQLException {
		return sqlMapper.update("InfoTMember.updateMember", member);
	}

	public List<InfoTMember> getMembersByFamilyId(String familyId)
			throws SQLException {
		return sqlMapper.queryForList("InfoTMember.selectMemberByFamilyId",
				new Integer(familyId));
	}

	public void deleteMember(String memberId) throws NumberFormatException,
			SQLException {
		sqlMapper.delete("InfoTMember.deleteMember", new Integer(memberId));
	}

	public void deleteMemberclass(String memberId) throws SQLException {

		sqlMapper.delete("InfoTMember.deleteMemberByPrimaryKey", new Integer(
				memberId));
	}

	public String getMemberPK() throws SQLException {
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT hibernate_sequence.nextval AS memberId FROM DUAL");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return "0";
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
			rs.close();
		}
	}

	public void getFamilyCount(Integer familyId) throws SQLException {
		sqlMapper.update("InfoTMember.updateFamilyCount", familyId);
	}

	public boolean isRemove(String memberId) throws SQLException {
		boolean cnt = true;
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("select count(*) from BIZ_T_OPTCHECK c where  c.member_id='"
					+ memberId + "'");
			rs = ps.executeQuery();
			if (rs.next()) {
				cnt = false;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
			rs.close();
		}
		return cnt;
	}

	public void deleteOptcheckForMember(String memberId, String familyId)
			throws SQLException {
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql = "update BIZ_T_OPTCHECK c set c.member_id =(select masterid "
					+ "from info_t_family fam where fam.family_id ='"
					+ familyId + "')  " + "where c.family_id='" + familyId
					+ "' and c.member_id= '" + memberId + "'";
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
	}

	public void removeMember(String memberId, Long employeeId,
			String delreason, String familyId) throws SQLException {
		Connection con = sqlMapper.getCurrentConnection();
		PreparedStatement ps = null;
		con.setAutoCommit(false);
		String sql = "insert into info_t_memberbak "
				+ "(member_id, family_id, relmaster, membername, papertype, paperid, birthday, sex, "
				+ "nation, ismarriage, political, rprkind, rprtype, rpraddress, degreesort, policy, ftap, sicken, deformity, "
				+ "oldandinfirm, workability, picpath, student, health, otherreason, indi_id, opertime, empid, delreason)"
				+ "select member_id, family_id, relmaster, membername, papertype, paperid, birthday, sex, "
				+ "nation, ismarriage, political, rprkind, rprtype, rpraddress, degreesort, policy, ftap, sicken, deformity, "
				+ "oldandinfirm, workability, picpath, student, health, otherreason, indi_id,sysdate,'"
				+ employeeId + "','" + delreason + "'"
				+ " from info_t_member where member_id =" + memberId;
		try {
			ps = con.prepareStatement(sql);
			ps.execute();
			sql = "update BIZ_T_OPTCHECK c set c.member_id =(select masterid "
					+ "from info_t_family fam where fam.family_id ='"
					+ familyId + "')  " + "where c.family_id='" + familyId
					+ "' and c.member_id= '" + memberId + "'";
			ps = con.prepareStatement(sql);
			ps.execute();
			sql = "delete info_t_memberclass where member_id ='" + memberId
					+ "'";
			ps = con.prepareStatement(sql);
			ps.execute();
			sql = "delete info_t_member where member_id ='" + memberId + "'";
			ps = con.prepareStatement(sql);
			ps.execute();

			sql = " update info_t_family fam "
					+ " set fam.population  = (select count(1) "
					+ "     from info_t_member m "
					+ "    where m.family_id = '" + familyId + "'), "
					+ "fam.ensurecount = (select count(1) "
					+ "      from info_t_member m "
					+ "    where m.family_id ='" + familyId + "' "
					+ "      and m.rprtype = 261) "
					+ " where fam.family_id = '" + familyId + "'";
			ps = con.prepareStatement(sql);
			ps.execute();

			sql = " update info_t_family fam  "
					+ " set fam.saltype = (select case "
					+ " when saltype > 0 then "
					+ " decode(cnt, 0, 879, 1, 876, 2, 877, 878) " + " else "
					+ " decode(cnt, 0, 879, 1, 873, 2, 874, 875) " + " end "
					+ " from (select mem.family_id, " + " sum((case "
					+ " when mem.sicken in "
					+ " (820, 821, 822, 823, 824, 825, 826, 827, 828) then "
					+ " 1 " + " when deformity in "
					+ " (829, 830, 831, 832, 833, 834, 835) and "
					+ " workability = 222 then " + " 1 "
					+ " when oldandinfirm in "
					+ " (838, 839, 840, 841, 842) and "
					+ " workability = 222 then " + " 1 "
					+ " when student in (843, 844, 845) then " + " 1 "
					+ " else " + " 0 " + " end)) saltype, "
					+ " count(1) as cnt " + " from info_t_member mem "
					+ " group by mem.family_id) inf "
					+ " where inf.family_id = fam.family_id)  "
					+ " WHERE FAMILY_ID='"+familyId+"'";
			ps = con.prepareStatement(sql);
			ps.execute();

			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			ps.close();
		}
	}
}
