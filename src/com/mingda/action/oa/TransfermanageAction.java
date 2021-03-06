/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.oa;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;

/** 
 * MyEclipse Struts
 * Creation date: 12-31-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 * @struts.action-forward name="transfermanage" path="/page/oa/transfermanage.jsp"
 */
public class TransfermanageAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	static Logger log = Logger.getLogger(TransfermanageAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String reltable = request.getParameter("reltable");
		String relid = request.getParameter("relid");
		log.debug(reltable + "    " + relid);

		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();

		try {
			FileUpload fu = new FileUpload();
			fu.delOaInfo(session.connection(), reltable, relid, false);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		response.setCharacterEncoding("gb18030");
		try {
			PrintWriter out = response.getWriter();
			out
					.println("<script type=\"text/javascript\">"
							+ "function winclose(){"
							+ " window.opener.location.href = window.opener.location.href; "
							+ " if (window.opener.progressWindow) {"
							+ "  window.opener.progressWindow.close();"
							+ "  }window.close(); }</script>");
			out.println("<p align =\"center\"><button onclick=\"winclose()\">�ر�</button></p>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}