package com.mingda.action.policy.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;

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

@SuppressWarnings("unchecked")
public class BatchAction extends Action {
	static Logger log = Logger.getLogger(BatchAction.class);
	@SuppressWarnings("rawtypes")
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

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		BigDecimal a = new BigDecimal(year);
		String jyear = new String(a.toString());
		a = new BigDecimal(month);
		String jmonth = new String(a.toString());
		String sa = request.getParameter("sa");
		//sa = new String(sa.getBytes("UTF-8"), "GB18030");
		log.debug(sa);
		String jdesc = sa;
		String jbegdt = request.getParameter("jbegdt");
		String jenddt = request.getParameter("jenddt");

		String jmoneyflag = "0";
		String jmoney = "0";

		HashMap hashmap = new HashMap();
		hashmap.put("jempid", jempid);
		hashmap.put("jdeptid", jdeptid);
		hashmap.put("jdepth", jdepth);
		hashmap.put("jpid", jpid);
		hashmap.put("jyear", jyear);
		hashmap.put("jmonth", jmonth);
		hashmap.put("jdesc", jdesc);
		hashmap.put("jbegdt", jbegdt);
		hashmap.put("jenddt", jenddt);
		hashmap.put("jmoneyflag", jmoneyflag);
		hashmap.put("jmoney", jmoney);
		Accountpage accountpage = new Accountpage();
		log.debug(accountpage.addPolicyNowAcc(hashmap));
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
