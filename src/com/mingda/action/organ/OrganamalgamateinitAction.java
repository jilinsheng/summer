/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.organ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;
import com.mingda.entity.SysTOrganization;

/**
 * 机构合并 初始化页面信息 MyEclipse Struts Creation date: 12-01-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="organamalgamate"
 *                        path="/page/organ/organamalgamate.jsp"
 */
public class OrganamalgamateinitAction extends Action {
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
		SysTEmployee employee = (SysTEmployee) hsession
				.getAttribute("employee");
		SysTOrganization organ = employee.getSysTOrganization();
		String organid = organ.getOrganizationId();
		SysTOrganizationDAO organdao = new SysTOrganizationDAO();
		request.setAttribute("childlist", organdao.findByParentorgid(organid));
		request.setAttribute("local", organ);
		session.close();
		return mapping.findForward("organamalgamate");
	}
}