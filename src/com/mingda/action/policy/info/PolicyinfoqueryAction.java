/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.info;

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
import org.dom4j.Document;

/** 
 * MyEclipse Struts
 * Creation date: 11-27-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class PolicyinfoqueryAction extends Action {
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
			e.printStackTrace();
		}
		
		//
		try {
			PrintWriter out = response.getWriter();
			
			if(action.equals("getCheckPolicyInfosHtml")){
				Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
				String pid = request.getParameter("pid");
				String fmid = request.getParameter("fmid");
				String memid = request.getParameter("memid");
				HashMap hashmap = new HashMap();
				hashmap.put("pid",pid);
				hashmap.put("fmid",fmid);
				hashmap.put("memid",memid);
				hashmap.put("dictionary",dictionary);				
				Policyinfoquery policyinfoquery = new Policyinfoquery();
				out.print(policyinfoquery.getCheckPolicyInfosHtml(hashmap));
			}else if(action.equals("getCheckPolicySqlsHtml")){
				String checkid = request.getParameter("checkid");
				Policyinfoquery policyinfoquery = new Policyinfoquery();
				out.print(policyinfoquery.getCheckPolicySqlsHtml(checkid));
			}else if(action.equals("getCheckPolicyAccsHtml")){
				String checkid = request.getParameter("checkid");
				Policyinfoquery policyinfoquery = new Policyinfoquery();
				out.print(policyinfoquery.getCheckPolicyAccsHtml(checkid));
			}else if(action.equals("getCheckPolicyChildSqlsHtml")){
				String checkid = request.getParameter("checkid");
				Policyinfoquery policyinfoquery = new Policyinfoquery();
				out.print(policyinfoquery.getCheckPolicyChildSqlsHtml(checkid));
			}else{
				out.print("xiuxiu");
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}