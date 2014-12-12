/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.organ;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTDictsortDAO;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTDictionary;
import com.mingda.entity.SysTDictsort;

/**
 * MyEclipse Struts Creation date: 12-03-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="organdetail" path="/page/organ/organdetail.jsp"
 */
public class OrgandetailAction extends Action {
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
		String organizationId = request.getParameter("organizationId");
		Session session = SessionFactory.getSession();

		SysTOrganizationDAO organdao = new SysTOrganizationDAO();
		SysTDictsortDAO dictdao = new SysTDictsortDAO();

		StringBuffer sb = new StringBuffer();

		SysTDictsort sysTDictsort = dictdao.findById(new Long(361));
		Set<SysTDictionary> set = sysTDictsort.getSysTDictionaries();
		Iterator<SysTDictionary> it = set.iterator();
		while (it.hasNext()) {
			SysTDictionary sysTDictionary = it.next();
			if (!"9004".equals(sysTDictionary.getDictionaryId().toString())) {
				sb.append("<tr><th>"
						+ sysTDictionary.getItem()
						+ "</th><td>"
						+ organdao.getOrgExtValue(sysTDictionary
								.getDictionaryId().toString(), organizationId)
						+ "</td></tr>");
			}
		}
		request.setAttribute("html", sb.toString());

		request.setAttribute("organ", organdao.findById(organizationId));
		session.close();
		return mapping.findForward("organdetail");
	}
}