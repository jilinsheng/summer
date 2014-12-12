/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system.menu;

import java.util.Iterator;
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
import com.mingda.dao.SysTPrivilegeDAO;
import com.mingda.entity.SysTPrivilege;

/**
 * MyEclipse Struts Creation date: 09-12-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="menutree" path="/page/system/menu/menutree.jsp"
 */
public class MenutreeAction extends Action {
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
		String menuid = request.getParameter("menuid");
		Session session = SessionFactory.getSession();
		SysTPrivilegeDAO privilege = new SysTPrivilegeDAO();
		Transaction tx = session.beginTransaction();
		try {
			List list = privilege.findAll();
			String html = "<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" border=\"0\">";
			html += "<tr> <td colspan=\"2\"><div class=\"dtree\"><script type=\"text/javascript\">"
					+ "d = new dTree('d');d.add(0,-1,'<input type=\"radio\" name=\"item\" onclick=\"ctlchild(0,-2,1)\"/>�˵���Ŀ');";
			Iterator it = list.iterator();
			while (it.hasNext()) {
				SysTPrivilege privi = (SysTPrivilege) it.next();
				String id = privi.getPrivilegeId().toString();
				String pid = privi.getParentprivilegeid().toString();
				String displayname = privi.getDisplayname();
				String checked = "checked=\"checked\"";
				if (null == menuid) {
					checked = "";
				} else if (!id.equals(menuid)) {
					checked = "";
				}
				html += "d.add(" + id + "," + pid + ",'<input id=\"" + pid
						+ "\" type=\"radio\" name=\"item\" " + checked
						+ " value=\"" + id + "\" onclick=\"ctlchild(" + id
						+ "," + pid + ",0)\"/>" + displayname + "');";
			}
			html += " document.write(d);</script></div></td></tr><table>";
			request.setAttribute("html", html);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		return mapping.findForward("menutree");
	}
}