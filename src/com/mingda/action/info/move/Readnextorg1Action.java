/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.move;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;

/**
 * MyEclipse Struts Creation date: 08-23-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class Readnextorg1Action extends Action {
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

		String orgid = request.getParameter("orgid");
		String oldid =request.getParameter("oldid");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String html="";
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			con = session.connection();
			sql = "select * from sys_t_organization org where org.parentorgid = substr('"
				+ orgid
				+ "', 1, 4) and org. organization_id <> substr('"
				+ oldid + "', 1, 6) order by org.organization_id";
			Log4jApp.logger(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			html += "<option value=\"\">δѡ��...</option>";
			while (rs.next()) {
				html += "<option value=\"" + rs.getString("organization_id")
						+ "\">" + rs.getString("orgname") + "</option>";
			}
			html+="";
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
				session.close();
			} catch (SQLException e) {
			}
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");

		try {
			PrintWriter out = response.getWriter();
			out.write(html);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}