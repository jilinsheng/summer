/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.organ;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.organ.OrganEditHandle;

/**
 * MyEclipse Struts Creation date: 12-06-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class OrganeditcommitAction extends Action {
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
		String[] oorgids = request.getParameterValues("oorgid");
		String type = request.getParameter("type");
		String norgid = request.getParameter("norgid");
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			if (null != oorgids && 0 < oorgids.length) {
				String oorgid = "";
				for (int i = 0; i < oorgids.length; i++) {
					oorgid = oorgids[i] + ",";
				}
				if (!"".equals(oorgid)) {
					OrganEditHandle o = new OrganEditHandle();
					List<String[]> blist = o.getOrganInfo(oorgid + norgid);
					if ("1".equals(type)) {
						o.commitFamilyNewOrgid(norgid, oorgid.substring(0,
								oorgid.length() - 1));
						request.setAttribute("resultstr", "����Ǩ����ȷ��");
					} else {
						o.undoFamilyNewOrgid(oorgid.substring(0, oorgid
								.length() - 1), norgid);
						request.setAttribute("resultstr", "����Ǩ���Ѿ��ָ�");
					}
					List<String[]> elist = o.getOrganInfo(oorgid + norgid);
					request.setAttribute("blist", blist);
					request.setAttribute("elist", elist);

				}
			}
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping.findForward("organinfo");
	}
}