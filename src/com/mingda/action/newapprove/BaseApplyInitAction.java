package com.mingda.action.newapprove;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.myjdbc.ConnectionManager;
import com.mingda.common.myjdbc.PolicyApprove;
import com.mingda.common.myjdbc.PolicyApproveRowMapper;
import com.mingda.common.myjdbc.RowMapper;
import com.mingda.entity.SysTEmployee;

public class BaseApplyInitAction extends Action {
	static Logger log = Logger.getLogger(BaseApplyInitAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String onno = employee.getSysTOrganization().getOrganizationId();
		String fatherid = onno.substring(0, 6);
		String optcheckId = request.getParameter("optcheckId");
		String policyId = request.getParameter("policyId");
		String sql = "select k.optcheck_id, k.policy_id, k.checkflag1, "
				+ " k.checkflag2,  k.checkflag3,   k.moneyflag, k.ifover, "
				+ " k.result,  fam.family_id,  fam.familyno, fam.organization_id, "
				+ " fam.ensurecount,  fam.dessaltype,  fam.saltype, "
				+ " fs.flag,  fs.money,k.checkmoney, (  decode( sign(  fam.ensurecount-3),1,3,fam.ensurecount ) * b.planmoney) as countmoney, "
				+ " fam.avgincome,  b.linemoney, fam.masterpaperid,  fam.mastername ,b.planmoney "
				+ " from info_t_family fam  left join biz_t_optcheck k  on k.family_id = fam.family_id "
				+ " and k.policy_id = '"
				+ policyId
				+ "' "
				+ " left join (select * from biz_t_familystatus s where s.policy_id = '"
				+ policyId + "') fs " + " on fs.family_id = fam.family_id "
				+ " left join (select *  from doper_t_standard_bak sbak "
				+ " where sbak.organization_id = '" + fatherid + "') b "
				+ " on b.organization_id = substr(fam.organization_id, 1, 6) "
				+ " and fam.saltype = b.saltype where fam.status = '1' "
				+ " and fam.organization_id like '" + onno
				+ "%' and k.optcheck_id='" + optcheckId + "'";
		log.error(sql);
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			RowMapper<PolicyApprove> rowMapper = new PolicyApproveRowMapper();
			PolicyApprove e = null;
			if (rs.next()) {
				e = (PolicyApprove) rowMapper.map(rs);
			}
			request.setAttribute("pa", e);
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(ps);
			ConnectionManager.closeQuietly();
		}
		return mapping.findForward("baseapply");
	}

}
