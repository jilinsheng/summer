package com.mingda.common.myjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicyApproveRowMapper implements RowMapper<PolicyApprove> {
	public PolicyApprove map(ResultSet rs) throws SQLException {
		PolicyApprove e = new PolicyApprove();
		e.setAvgincome(rs.getString("AVGINCOME"));
		e.setCheckflag1(rs.getString("CHECKFLAG1"));
		e.setCheckflag2(rs.getString("CHECKFLAG2"));
		e.setCheckflag3(rs.getString("CHECKFLAG3"));
		e.setCountmoney(rs.getString("COUNTMONEY"));
		e.setDessaltype(rs.getString("DESSALTYPE"));
		e.setEnsurecount(rs.getString("ENSURECOUNT"));
		e.setFamilyId(rs.getString("FAMILY_ID"));
		e.setFamilyno(rs.getString("FAMILYNO"));
		e.setFlag(rs.getString("FLAG"));
		e.setIfover(rs.getString("IFOVER"));
		e.setLinemoney(rs.getString("LINEMONEY"));
		e.setMastername(rs.getString("MASTERNAME"));
		e.setMasterpaperid(rs.getString("MASTERPAPERID"));
		e.setMoney(rs.getString("MONEY"));
		e.setMoneyflag(rs.getString("MONEYFLAG"));
		e.setOptcheckId(rs.getString("OPTCHECK_ID"));
		e.setOrganizationId(rs.getString("ORGANIZATION_ID"));
		e.setPolicyId(rs.getString("POLICY_ID"));
		e.setResult(rs.getString("RESULT"));
		e.setSaltype(rs.getString("SALTYPE"));
		e.setPlanmoney(rs.getString("PLANMONEY"));
		e.setCheckmoney(rs.getString("checkmoney"));
		return e;
	}

}
