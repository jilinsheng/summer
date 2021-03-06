/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTPrivilegeDAO;
import com.mingda.entity.SysTPrivilege;

/**
 * MyEclipse Struts Creation date: 09-12-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="menuedit" path="/page/system/menu/menuedit.jsp"
 */
public class MenuinitAction extends Action {
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
		Session session = SessionFactory.getSession();
		SysTPrivilegeDAO privilege = new SysTPrivilegeDAO();
		String menuid = request.getParameter("menuid");
		String pid = request.getParameter("pid");
		SysTPrivilege privi = null;
		try {
			if ("-2".equals(pid)) {
				privi = new SysTPrivilege();
				privi.setParentprivilegeid(new Long(menuid));
			} else {
				privi = privilege.findById(new Long(menuid));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		request.setAttribute("privi", privi);
		return mapping.findForward("menuedit");
	}
}