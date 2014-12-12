package com.mingda.action.newapprove;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.Pager;
import com.mingda.common.myjdbc.ConnectionManager;
import com.mingda.common.myjdbc.OrganizaitonDAO;
import com.mingda.common.myjdbc.OrganizationRowMapper;
import com.mingda.common.myjdbc.PolicyApprove;
import com.mingda.common.myjdbc.PolicyApproveDAO;
import com.mingda.common.myjdbc.PolicyApproveRowMapper;
import com.mingda.common.myjdbc.RowMapper;
import com.mingda.entity.SysTEmployee;

public class BaseApplyQueryAction extends Action {
	static Logger log = Logger.getLogger(BaseApplyQueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String sql = "";
		String sqlcount = "";
		HttpSession httpsession = request.getSession();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		String onno1 = employee.getSysTOrganization().getOrganizationId();
		String onno = request.getParameter("onno");
		String fatherid = onno.substring(0, 6);
		String policyid = "21";
		String curpage = request.getParameter("cur_page");
		String totalrows = request.getParameter("totalrows");
		String type = "";
		String value = "";
		String sal = "";
		String moneyflag="";
		List<PolicyApprove> pas = new ArrayList<PolicyApprove>();
		try {
			con = ConnectionManager.getConnection();
			int totalrow = 0;
			Pager page = null;
			sql = "select k.optcheck_id, "
					+ " k.policy_id,"
					+ " k.checkflag1,"
					+ " k.checkflag2,"
					+ " k.checkflag3,"
					+ " k.moneyflag,"
					+ " k.ifover,"
					+ " k.result,"
					+ " fam.family_id,"
					+ " fam.familyno,"
					+ " fam.organization_id,"
					+ " fam.ensurecount,"
					+ " fam.dessaltype,"
					+ " fam.saltype,"
					+ " fs.flag,"
					+ " fs.money, b.planmoney, "
					+ " ( decode( sign(  fam.ensurecount-3),1,3,fam.ensurecount ) * b.planmoney) as countmoney,"
					+ " fam.avgincome,"
					+ " b.linemoney,k.checkmoney,"
					+ " fam.masterpaperid,"
					+ " fam.mastername"
					+ " from info_t_family fam"
					+ " left join biz_t_optcheck k"
					+ " on k.family_id = fam.family_id"
					+ " and k.policy_id = '"
					+ policyid
					+ "'"
					+ " left join (select s.flag,s.money ,s.family_id  from biz_t_familystatus s where s.policy_id = '"
					+ policyid
					+ "') fs"
					+ " on fs.family_id = fam.family_id"
					+ " left join (select sbak.organization_id,sbak.planmoney,sbak.linemoney,sbak.saltype "
					+ " from doper_t_standard_bak sbak"
					+ " where sbak.organization_id = '"
					+ fatherid
					+ "') b"
					+ " on b.organization_id = substr(fam.organization_id, 1, 6)"
					+ " and fam.saltype = b.saltype"
					+ " where fam.status = '1'"
					+ " and fam.familyno like '" + onno + "%'" + " and k.optcheck_id is not null ";
			
			if (null == curpage || "".equals(curpage)) {
				value = request.getParameter("value");
				type = request.getParameter("type");
				sal = request.getParameter("sal");
				moneyflag=request.getParameter("moneyflag");
				if("".equals(moneyflag)){
					
				}else if ("7".equals(moneyflag)){
					sql= sql+" and fam.avgincome >b.linemoney ";
					
				}else {
					sql=sql+" and k.moneyflag='"+moneyflag+"' ";
				}
				
				if ("".equals(sal) || null == sal) {
				} else {
					sal = "	fam.dessaltype = " + sal + "  and";
				}
				if (type != null) {
					if (type.equals("mastername")) {
						sql += " and  fam.mastername like '%" + value + "%'";
					} else if (type.equals("paperid")) {
						sql += " and fam.masterpaperid='" + value + "'";
					} else if (type.equals("famno")) {
						sql += " and fam.familyno='" + value + "'";
					}
				}
				sql = sql + " order by fam.familyno ";
				log.debug(sql);
				httpsession.setAttribute("sql", sql);
				sqlcount = "select count(1)    "+sql.substring(sql.indexOf("from"));
				log.debug(sqlcount);
				ps = con.prepareStatement(sqlcount);
				rs = ps.executeQuery();
				if (rs.next()) {
					totalrow = rs.getInt(1);
				}
				totalrows = new Long(totalrow).toString();
				page = new Pager(totalrow, new Long(1).intValue(), "0");
				sql = "	SELECT * FROM  ( SELECT A.*, ROWNUM RN  FROM (" + sql
						+ ") A  WHERE ROWNUM <= " + page.getEndrow()
						+ " ) WHERE RN >= " + page.getBeiginrow() + " ";
			} else {
				page = new Pager(new BigDecimal(totalrows).intValue(),
						new Long(curpage).intValue(), "0");
				sql = (String) httpsession.getAttribute("sql");
				sql = "	SELECT * FROM  ( SELECT A.*, ROWNUM RN  FROM (" + sql
						+ ") A  WHERE ROWNUM <= " + page.getEndrow()
						+ " ) WHERE RN >= " + page.getBeiginrow() + " ";
			}
			page.setUrl("baseapplyquery.do?onno=" + onno + "&policyid="
					+ policyid + "&totalrows=" + totalrows);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			RowMapper<PolicyApprove> rowMapper = new PolicyApproveRowMapper();
			while (rs.next()) {
				pas.add((PolicyApprove) rowMapper.map(rs));
			}
			sql = "select * from sys_t_organization org  where org.parentorgid = ?  "
					+ "or org.organization_id =? order by org.organization_id";
			OrganizaitonDAO odao = new OrganizaitonDAO();
			String[] objs = { onno1, onno1 };
			request.setAttribute("orglist",
					odao.query(sql, objs, new OrganizationRowMapper()));
			request.setAttribute("toolsmenu", page.getToolsMenu());
			request.setAttribute("pas", pas);
			PolicyApproveDAO padao= new PolicyApproveDAO();
			request.setAttribute("str", padao.queryApproveStat(policyid, onno));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(ps);
			ConnectionManager.closeQuietly();
		}

		return mapping.findForward("baseapplyquery");
	}
}
