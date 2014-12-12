/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.acc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.myjdbc.ConnectionManager;

/** 
 * MyEclipse Struts
 * Creation date: 11-29-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class PolicyaccqueryAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// ��ȡaction
		String action = request.getParameter("action");		
		//
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
		try {
			PrintWriter out = response.getWriter();
			//
			if(action.equals("getPolicyAccXmls")){
				//
				String tbegpage = request.getParameter("tbegpage");
				String tendpage = request.getParameter("tendpage");
				//
				String tdeptid = request.getParameter("tdeptid");
				String tempid = request.getParameter("tempid");
				String tpid = request.getParameter("tpid");
				String tyear = request.getParameter("tyear");
				String tbegmonth = request.getParameter("tbegmonth");
				String tendmonth = request.getParameter("tendmonth");
				String tbegdt = request.getParameter("tbegdt");
				String tenddt = request.getParameter("tenddt");
				//
				HashMap hashmap = new HashMap();
				hashmap.put("tbegpage", tbegpage);
				hashmap.put("tendpage", tendpage);
				hashmap.put("tdeptid", tdeptid);
				hashmap.put("tempid", tempid);
				hashmap.put("tpid", tpid);
				hashmap.put("tyear", tyear);
				hashmap.put("tbegmonth", tbegmonth);
				hashmap.put("tendmonth", tendmonth);
				hashmap.put("tbegdt", tbegdt);
				hashmap.put("tenddt", tenddt);
				//
				Policyaccquery policyaccquery = new Policyaccquery();
		        out.print(policyaccquery.getPolicyAccXmls(hashmap));
			}else if(action.equals("")){
				
			}else{
				out.print("xiuxiu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnectionManager.closeQuietly();
		}
		return null;
	}
}