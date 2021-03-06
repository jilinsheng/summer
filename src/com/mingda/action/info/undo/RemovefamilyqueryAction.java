/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.undo;

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
import org.hibernate.Session;

import com.mingda.common.Pager;
import com.mingda.common.SessionFactory;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 12-05-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="removefamilyquery"
 *                        path="/page/info/undo/removefamilyquery.jsp"
 */
public class RemovefamilyqueryAction extends Action {
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
	static Logger log = Logger.getLogger(RemovefamilyqueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession hsession = request.getSession();
		Session session = SessionFactory.getSession();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String curpage = request.getParameter("cur_page");
		String sql = "";
		String type = "";
		String value = "";
		try {
			conn = session.connection();
			int totalrow = 0;
			Pager page = null;
			if (curpage == null || curpage.equals("")) {
				SysTEmployee employee = (SysTEmployee) hsession
						.getAttribute("employee");

				value = request.getParameter("value");
				type = request.getParameter("type");

				sql = "select * from info_t_family fam where "
						+ "fam.status ='0' and fam.masterid is not null and fam.organization_id like '"
						+ employee.getSysTOrganization().getOrganizationId()
						+ "%'";

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

				log.debug("�˵���ѯ���:" + sql);
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
			page.setUrl("removefamilyquery.do");
			String html = "";
			while (rs.next()) {
				html += "<tr><td>" + rs.getString("familyno") + "</td><td>"
						+ rs.getString("mastername") + "</td><td>"
						+ rs.getString("masterpaperid")
						+ "</td><td> <a href=\"removefamilyundo.do?familyid="
						+ rs.getString("family_id")
						+ " \" target=\"_blank\">�ָ�</a></td></tr>";
			}
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
		return mapping.findForward("removefamilyquery");
	}
}