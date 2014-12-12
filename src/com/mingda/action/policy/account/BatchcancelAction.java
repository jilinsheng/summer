package com.mingda.action.policy.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.myjdbc.ConnectionManager;
import com.mingda.entity.SysTEmployee;

public class BatchcancelAction extends Action {
	static Logger log = Logger.getLogger(BatchcancelAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		Long empid = employee.getEmployeeId();

		String jdeptid = request.getParameter("oid");
		String jpid = "21";
		String jaccid = request.getParameter("jaccid");

		Accountpage accountpage = new Accountpage();
		log.debug(accountpage
				.cancelPolicyNowAcc(jdeptid, jaccid, jpid));
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");
		try {
			PrintWriter out = response.getWriter();
			out.write(jdeptid);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			ConnectionManager.closeQuietly();
		}

		return null;
	}
}
