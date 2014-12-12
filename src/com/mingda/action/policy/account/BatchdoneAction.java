package com.mingda.action.policy.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.myjdbc.ConnectionManager;
import com.mingda.entity.SysTEmployee;

public class BatchdoneAction extends Action {

	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
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

		String jempid = empid.toString();
		String jdeptid = request.getParameter("oid");
		String jdepth = "3";
		String jpid = "21";
		String jaccid = request.getParameter("jaccid");
		System.out.print(jaccid);
		System.out.print(jdeptid+">>>>"+jaccid);
		HashMap hashmap = new HashMap();
		hashmap.put("jempid", jempid);
		hashmap.put("jdeptid", jdeptid);
		hashmap.put("jdepth", jdepth);
		hashmap.put("jpid", jpid);
		hashmap.put("jaccid", jaccid);
		Accountpage accountpage = new Accountpage();
		StringBuffer a =accountpage.accPolicyNowAcc(hashmap);
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");
		try {
			PrintWriter out = response.getWriter();
			System.out.print(a);
			out.write(jdeptid+","+a);
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
