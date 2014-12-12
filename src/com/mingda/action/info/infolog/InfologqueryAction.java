/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.infolog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.Pager;
import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;
import com.mingda.entity.SysTOrganization;

/**
 * MyEclipse Struts Creation date: 08-29-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="infologquery"
 *                        path="/page/info/infolog/infologquery.jsp"
 */
public class InfologqueryAction extends Action {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession hsession = request.getSession();
		Session session = SessionFactory.getSession();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String curpage = request.getParameter("cur_page");
		String btime = "";
		String etime = "";
		String empid = "";
		String sql = "";
		String type="";
		String value="";
		String organizationId = "";
		try {
			conn = session.connection();
			int totalrow = 0;
			Pager page = null;
			if (curpage == null || curpage.equals("")) {
				btime = request.getParameter("btime");
				etime = request.getParameter("etime");
				empid = request.getParameter("empid");
				type=request.getParameter("type");
				value=request.getParameter("value");
				organizationId = request.getParameter("organizationId");
				sql = "select infolog_id, employee_id, logtime, entityid, log  ,tab.* ,fam.mastername ,fam.familyno from sys_t_infolog t, sys_t_table tab  ,info_t_family fam"
						+ " where tab.code = t.code and  fam.family_id = familyid and fam.organization_id like '"
						+ organizationId + "%' ";
				// order by t.logtime desc ,t.code";

				if (null == empid || "".equals(empid)) {

				} else {
					sql = sql + "  and t.employee_id='" + empid + "' ";
				}

				if (btime.equals("") || etime.equals("") || btime == null
						|| etime == null) {

				} else {
					sql = sql
							+ " and t.logtime between to_date('"
							+ btime
							+ "', 'yyyy-mm-dd') and"
							+ " to_date('"
							+ etime
							+ "', 'yyyy-mm-dd') ";
				}
				
				if (type != null) {
					if (type.equals("mastername")) {
						sql += " and  fam.mastername like '%" + value + "%'";
					} else if (type.equals("paperid")) {
						sql += " and fam.masterpaperid='" + value+"'";
					} else if (type.equals("famno")) {
						sql += " and fam.familyno='" + value+"'";
					}
				}
				sql =sql+ " order by t.logtime desc ,t.code";
				
				Log4jApp.logger("��Ϣά����־��ѯ���:" + sql);
				pstmt = conn.prepareStatement("select count(1) from ( " + sql
						+ " )");
				hsession.setAttribute("sql", sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					totalrow = rs.getInt(1);
				}
				page = new Pager(totalrow, new Long(1).intValue(), "0");
				pstmt = conn
						.prepareStatement("select * from (select mytab.*, rownum row_num from ("
								+ sql
								+ ") mytab) where row_num between "
								+ page.getBeiginrow()
								+ " and "
								+ page.getEndrow());
				rs = pstmt.executeQuery();
			} else {
				sql = (String) hsession.getAttribute("sql");
				pstmt = conn.prepareStatement("select count(1) from ( " + sql
						+ " )");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					totalrow = rs.getInt(1);
				}
				page = new Pager(totalrow, new Long(curpage).intValue(), "0");
				pstmt = conn
						.prepareStatement("select * from (select mytab.*, rownum row_num from ("
								+ sql
								+ ") mytab) where row_num between "
								+ page.getBeiginrow()
								+ " and "
								+ page.getEndrow());
				rs = pstmt.executeQuery();
			}
			request.setAttribute("organizationId", organizationId);
			page.setUrl("infologquery.do?organizationId=" + organizationId);
			String html = "";
			while (rs.next()) {
				html += "<tr><td>"
						+ rs.getString("logtime")
						+ "</td><td>"
						+ rs.getString("familyno")
						+ "</td><td>"+rs.getString("mastername")+"</td><td>"
						+ rs.getString("logicname")
						+ "</td><td><span style=\"cursor:hand\" onclick=\"view('"
						+ rs.getString("infolog_id") + "' ,'"
						+ rs.getString("entityid") + "','"
						+ rs.getString("code") + "')\"> �鿴��ϸ</span></td></tr>";
			}

			SysTOrganizationDAO sysTOrganizationDAO = new SysTOrganizationDAO();
			SysTOrganization sysTOrganization = sysTOrganizationDAO
					.findById(organizationId);
			Set emps = sysTOrganization.getSysTEmployees();
			Iterator<SysTEmployee> empit = emps.iterator();
			String opt = "";
			while (empit.hasNext()) {
				SysTEmployee sysTEmployee = empit.next();
				opt += "<option value=\"" + sysTEmployee.getEmployeeId()
						+ "\">" + sysTEmployee.getSysTEmpext().getName()
						+ "</option>";
			}

			request.setAttribute("opt", opt);
			request.setAttribute("html", html);
			request.setAttribute("toolsmenu", page.getToolsMenu());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.close();

		}

		return mapping.findForward("infologquery");
	}
}