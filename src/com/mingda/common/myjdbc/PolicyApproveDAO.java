package com.mingda.common.myjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicyApproveDAO {
	public String queryApproveStat(String policyId, String organizationId) {
		String result = "";
		String sql = "select count(1) as hs , sum(fam.ensurecount) as rs  ,sum(chk.countmoney) as zm "
				+ "  from  (select bz.family_id, "
				+ " sum(decode(bz.result, 2, bz.checkmoney, recheckmoney)) as countmoney, "
				+ " sum(bz.checkmoney) as checkmoney, "
				+ " sum(bz.checkchildmoney) as checkchildmoney "
				+ " from biz_t_optcheck bz "
				+ " where bz.policy_id = '"
				+ policyId
				+ "' "
				+ " and ((bz.result = 2 and bz.moneyflag = 1) or "
				+ " (bz.moneyflag in (2, 3, 4, 7) and bz.result <> 8)) "
				+ " group by bz.family_id) chk, "
				+ "  info_t_family      fam, "
				+ "   sys_t_organization org, "
				+ "  sys_t_dictionary   dic "
				+ " where "
				+ " fam.family_id = chk.family_id "
				+ "  and fam.organization_id = org.organization_id "
				+ "  and fam.dessaltype = dic.dictionary_id(+) "
				+ " and fam.status = '1' "
				+ " "
				+ "  and fam.familyno like '"
				+ organizationId + "%'";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			if (rs.next()) {
				result = "当前业务在保户：" + rs.getString(1) + "户    "
						+ rs.getString(2) + "人    " + rs.getString(3) + "元";

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(ps);
		}

		return result;

	}

	public String savePolicyDAO(String familyId, String checkflag,
			String optcheckId, String onno, String policyId, String money) {
		String ifover = "";
		String result = "";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			String jwhere = "";

			/*
			 * checkflag 社区审批标识((1、审批中2、同意救助3、重新核查4、渐退5、延续6、暂停7、恢复8、终止))ifover
			 * 审批进度标识(5、社区审批4、街道审批3、区审批2、市审批1、省审批T、已终审F、待确定的待审名单)result
			 * 终审结果((1、审批中2、同意救助3、重新核查4、渐退5、延续6、暂停7、恢复8、终止9、不同意救助))
			 * test="${pa.checkflag1 =='1'}">审批中</c:if> <c:if
			 * test="${pa.checkflag1 =='2'}">同意救助</c:if> <c:if
			 * test="${pa.checkflag1 =='3'}">重新核查</c:if> <c:if
			 * test="${pa.checkflag1 =='4'}">渐退</c:if> <c:if
			 * test="${pa.checkflag1 =='5'}">延续</c:if> <c:if
			 * test="${pa.checkflag1 =='6'}">暂停</c:if> <c:if
			 * test="${pa.checkflag1 =='7'}">恢复</c:if> <c:if
			 * test="${pa.checkflag1 =='8'}">终止</c:if>
			 */

			if ("3".equals(checkflag)) {
				ifover = "5";
				result = "1";
			} else {
				ifover = "" + ((onno.length() / 2) - 1);
				result = checkflag;
			}
			if (onno.length() == 10) {
				jwhere = " t.checkflag1='" + checkflag + "',  t.ifover='"
						+ ifover + "',t.result='1',";
				String sql = "update Biz_t_Optcheck t set " + jwhere
						+ " t.optdt=sysdate ,t.checkmoney ='" + money + "' "
						+ "where t.optcheck_id='" + optcheckId + "'";
				ps = con.prepareStatement(sql);
				ps.execute();
			}
			if (onno.length() == 8) {
				jwhere = " t.checkflag2='" + checkflag + "',  t.ifover='"
						+ ifover + "',t.result='1',";
				String sql = "update Biz_t_Optcheck t set " + jwhere
						+ " t.optdt=sysdate ,t.checkmoney ='" + money + "' "
						+ "where t.optcheck_id='" + optcheckId + "'";
				ps = con.prepareStatement(sql);
				ps.execute();
			}
			if (onno.length() == 6) {
				jwhere = " t.checkflag3='" + checkflag + "',  t.ifover='"
						+ ifover + "',t.result='" + result + "',";
				String sql = "update Biz_t_Optcheck t set " + jwhere
						+ " t.optdt=sysdate ,t.checkmoney ='" + money + "' "
						+ "where t.optcheck_id='" + optcheckId + "'";
				ps = con.prepareStatement(sql);
				ps.execute();

				String ssflag = "";
				if ("2".equals(result) || "3".equals(result)
						|| "4".equals(result) || "5".equals(result)
						|| "7".equals(result)) {
					ssflag = "2";
				} else {
					ssflag = "3";
				}

				sql = "insert into biz_t_familystatus "
						+ " (familystatus_id, family_id, policy_id, begdt, money, enddt, flag) "
						+ " select xfamilystatus_id.nextval, '" + familyId
						+ "', '" + policyId + "', sysdate, 0, sysdate, '1' "
						+ " from info_t_family fam "
						+ " left join biz_t_familystatus sss "
						+ " on sss.family_id = fam.family_id "
						+ " and sss.policy_id = '" + policyId + "' "
						+ " where fam.family_id = '" + familyId + "' "
						+ " and sss.familystatus_id is null";
				ps = con.prepareStatement(sql);
				ps.execute();

				sql = "update biz_t_familystatus sss set sss.begdt=sysdate,  sss.enddt=sysdate, sss.flag  = '"
						+ ssflag
						+ "', "
						+ " sss.money = (select ck.checkmoney from biz_t_optcheck ck "
						+ " where ck.optcheck_id = '"
						+ optcheckId
						+ "')  where sss.family_id = '"
						+ familyId
						+ "' "
						+ " and sss.policy_id = '" + policyId + "'";
				ps = con.prepareStatement(sql);
				ps.execute();

			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly(ps);
		}

		return "";
	}
}
