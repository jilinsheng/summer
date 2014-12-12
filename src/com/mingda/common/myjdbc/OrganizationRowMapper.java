package com.mingda.common.myjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mingda.entity.SysTOrganization;

public class OrganizationRowMapper implements RowMapper<SysTOrganization> {

	@Override
	public SysTOrganization map(ResultSet rs) throws SQLException {
		SysTOrganization e = new SysTOrganization();
		e.setFullname(rs.getString("FULLNAME"));
		e.setOrganizationId(rs.getString("ORGANIZATION_ID"));
		e.setOrgname(rs.getString("ORGNAME"));
		e.setSerialnumber(rs.getString("SERIALNUMBER"));
		e.setParentorgid(rs.getString("PARENTORGID"));
		e.setDepth(rs.getBigDecimal("DEPTH"));
		e.setStatus(rs.getString("STATUS"));
		return e;
	}

}
