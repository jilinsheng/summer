/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTOrganization;

/**
 * MyEclipse Struts Creation date: 04-21-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class OrganizationGetChildAction extends Action {
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
		try {
			String parentorgid = request.getParameter("parentid");
			/* 格式 组合空格 */
			Integer l = new Integer(request.getParameter("l"));
			int j = l.intValue() + 1;
			String tempstr = "&nbsp;";
			for (int i = 0; i < j; i++) {
				tempstr = tempstr + "&nbsp;&nbsp;&nbsp;";
			}
			Log4jApp.logger(tempstr);
			/* 设置响应头 */
			response.setContentType("text/xml;charset=UTF-8");
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			/* 设置响应头 */
			Transaction tx = SessionFactory.getSession().beginTransaction();
			List orglist = null;
			String xml = "";/* 页面显示用的字符串 */
			SysTOrganizationDAO sysTOrganizationDAO = new SysTOrganizationDAO();
			orglist = sysTOrganizationDAO.findByParentorgid(parentorgid);
			tx.commit();
			Iterator it = orglist.iterator();
			while (it.hasNext()) {
				SysTOrganization sysTOrganization = (SysTOrganization) it
						.next();
				xml = xml
						+ "<div style=\"cursor:hand\" id=\"f"
						+ sysTOrganization.getOrganizationId()
						+ "\">"
						+ tempstr
						+ "<img alt=\"展开\" id=\"img"
						+ sysTOrganization.getOrganizationId()
						+ "\"  src=\"/db/page/images/plus.png\"  onClick=\"showhidden("
						+ sysTOrganization.getOrganizationId()
						+ ","
						+ j
						+ ")\"></img><span style=\"cursor:hand\" onclick=\"readyURL(this,"
						+ sysTOrganization.getOrganizationId() + ")\">"
						+ sysTOrganization.getOrgname()
						+ "</span></div><span style=\"cursor:hand\" id=\"c"
						+ sysTOrganization.getOrganizationId() + "\"></span>";
			}
			PrintWriter out = response.getWriter();
			if (xml == null || xml.equals("")) {
				out.write("empty");
			} else {
				out.write(xml.trim());
			}
			out.flush();
			out.close();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		return null;
	}
}