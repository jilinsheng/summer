package com.mingda.action.info.editor;

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

import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;

public class IdcarditeranceAction extends Action {
	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberid = request.getParameter("memberid");
		String paperid = request.getParameter("paperid");
		Log4jApp.logger("…Ì∑›÷§∫≈   " + memberid);
		Session session = SessionFactory.getSession();
		Connection con = session.connection();
		String temp = "";
		try {
			if (memberid == null || memberid.equals("")) {
			} else {
				temp = " mem.member_id <>'" + memberid + "' and ";

			}
			response.setContentType("text/html");
			response.setCharacterEncoding("GB18030");
			PrintWriter out = response.getWriter();
			ResultSet rs = null;
			PreparedStatement ps = con
					.prepareStatement("select count(1) from info_t_family fam , info_t_member mem  where fam.status=1 and mem.family_id= fam.family_id and  "
							+ temp + "  mem.paperid='" + paperid + "'");
			rs = ps.executeQuery();
			if (rs.next()) {
				out.println(rs.getString(1));
			}
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				session.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}