/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTEmployeeDAO;

/**
 * MyEclipse Struts Creation date: 08-19-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class CheckaccountAction extends Action {
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
		SysTEmployeeDAO dao = new SysTEmployeeDAO();
		String account = request.getParameter("account");
		List list = dao.findByAccount(account);
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");
		try {
			PrintWriter out = response.getWriter();
			if (list == null || list.size() < 1) {
				out.write("0");
			} else {
				out.write("1");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		return null;
	}
}