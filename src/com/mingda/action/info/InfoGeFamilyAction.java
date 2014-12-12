/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;

/**
 * MyEclipse Struts Creation date: 04-29-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class InfoGeFamilyAction extends Action {
	static Logger log = Logger.getLogger(InfoGeFamilyAction.class);
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
		Session session = null;
		session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		String familyId = request.getParameter("familyId");
		String tabPrimary = request.getParameter("tabPrimary");
		try {
			/*
			 * SysTTablestructDAO sysTTablestructDAO = new SysTTablestructDAO();
			 * sysTTablestructDAO.findById(new Long(tabPrimary));
			 */
			String sql = "from SysTTablestruct ts where exists (select 1 from SysTTablerelation tr where tr.parentid='101' and tr.childid=ts.primary)";
			Query query = session.createQuery(sql);
			List list = query.list();
			log.debug(list.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("");
	}
}