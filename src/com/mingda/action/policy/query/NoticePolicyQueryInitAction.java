/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.query;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;
import com.mingda.common.implemention.BillmonthHandle;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 02-27-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class NoticePolicyQueryInitAction extends Action {
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

		String policyId = request.getParameter("policyId");

		HttpSession httpsession = request.getSession();
		Session session = SessionFactory.getSession();

		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");

		BillmonthHandle billmonthhandle = new BillmonthHandle();

		try {

			String orgid = employee.getSysTOrganization().getOrganizationId();
			SysTOrganizationDAO organdao = new SysTOrganizationDAO();

			HashMap<String, String> map = billmonthhandle.getNoticeStat(orgid,
					policyId);

			String str = "当前业务在保户：" + map.get("1") + "户    "
					+ map.get("2") + "人    " + map.get("3")
					+ "元";

			request.setAttribute("str", str);
			request.setAttribute("orglist", organdao.findByParentId(orgid));
			request.setAttribute("policyId", policyId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return mapping.findForward("noticepolicyquery");
	}
}