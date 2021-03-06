/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.oa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 12-23-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="noticeview" path="/page/oa/noticeview.jsp"
 */
public class NoticeviewinitAction extends Action {
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
		SysTEmployee employee = (SysTEmployee) hsession
				.getAttribute("employee");
		SysTOrganizationDAO orgdao = new SysTOrganizationDAO();
		request.setAttribute("organlist", orgdao.findByParentorgid(employee
				.getSysTOrganization().getOrganizationId()));
		return mapping.findForward("noticeview");
	}
}