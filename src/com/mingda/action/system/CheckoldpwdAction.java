/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 08-26-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class CheckoldpwdAction extends Action {
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
		String oldpwd = request.getParameter("oldpwd");
		HttpSession hsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) hsession.getAttribute("employee");	
		String flag="0";
		if (employee.getPassword().toString().equals(oldpwd)) {
			flag="1";
		} else {
			flag="0";
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");

		try {
			PrintWriter out = response.getWriter();
			out.write(flag);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}