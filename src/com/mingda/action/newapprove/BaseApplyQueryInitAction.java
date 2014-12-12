package com.mingda.action.newapprove;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.mingda.common.myjdbc.OrganizaitonDAO;
import com.mingda.common.myjdbc.OrganizationRowMapper;
import com.mingda.common.myjdbc.PolicyApproveDAO;
import com.mingda.entity.SysTEmployee;

public class BaseApplyQueryInitAction extends Action {
	static Logger log = Logger.getLogger(BaseApplyQueryInitAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		Connection con = null;
		PreparedStatement ps = null;
		String onno = employee.getSysTOrganization().getOrganizationId();
		String fatherid = onno.substring(0, 6);
		Long empid = employee.getEmployeeId();
		String policyid = "21";
		try {
			String sql = " insert into biz_t_optcheck "
					+ " (optcheck_id, "
					+ " policy_id, "
					+ " family_id, "
					+ " member_id, "
					+ " acctype, "
					+ " countmoney, "
					+ " moneyflag, "
					+ " checkmoney, recheckmoney ,"
					+ " checkflag1, "
					+ " checkflag2, "
					+ " checkflag3, "
					+ " checkflag4, "
					+ " checkflag5, "
					+ " checkchildmoney, "
					+ " adjustmoney, "
					+ " ifover, "
					+ " result, "
					+ " optoper, "
					+ " optdt) "
					+ " select xoptcheck_id.nextval, "
					+ " '"+policyid+"', "
					+ " fam.family_id, "
					+ " null, "
					+ " 0, "
					+ " (   decode( sign(  fam.ensurecount-3),1,3,fam.ensurecount ) *b.planmoney), "
					+ " decode(fs.flag, '2', decode (sign ((  decode( sign(  fam.ensurecount-3),1,3,fam.ensurecount ) *b.planmoney)-fs.money) ,1,3,0,2,-1,4), '3', 6, '1'  ) as c , "
					+ " fs.money , fs.money , " + " 1, " + " 1, "
					+ " 1, " + " 1, " + " 1, " + " 0, " + " 0, " + " 5, "
					+ " 1, " + " "
					+ empid
					+ ", "
					+ " sysdate "
					+ " from info_t_family fam "
					+ " left join (select * from biz_t_familystatus s where s.policy_id = '"
					+ policyid
					+ "') fs "
					+ " on fs.family_id = fam.family_id "
					+ " left join (select * "
					+ " from doper_t_standard_bak sbak "
					+ " where sbak.organization_id = '"
					+ fatherid
					+ "') b "
					+ " on b.organization_id = substr(fam.organization_id, 1, 6) "
					+ " and fam.saltype = b.saltype "
					+ " where fam.status = '1' "
					+ " and fam.familyno like '"
					+ onno
					+ "%' "
					+ " and not exists "
					+ " (select 1 "
					+ " from biz_t_optcheck ck "
					+ " where ck.family_id in "
					+ " (select fam.family_id "
					+ " from info_t_family fam "
					+ " where fam.organization_id like '"
					+ onno
					+ "%') "
					+ " and ck.family_id = fam.family_id)";
			log.debug("村级生成审批列表：" + sql);
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			if (onno.length() == 10) {
				ps = con.prepareStatement(sql);
				ps.execute();
			}
			con.commit();
			PolicyApproveDAO padao= new PolicyApproveDAO();
			request.setAttribute("str", padao.queryApproveStat(policyid, onno));
			sql = "select * from sys_t_organization org  where org.parentorgid = ?  or org.organization_id =? order by org.organization_id";
			OrganizaitonDAO odao = new OrganizaitonDAO();
			String[] objs = { onno, onno };
			request.setAttribute("orglist",
					odao.query(sql, objs, new OrganizationRowMapper()));
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly(ps);
			ConnectionManager.closeQuietly();
		}
		return mapping.findForward("baseapplyquery");
	}
}
