/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.check;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.myjdbc.ConnectionManager;

/** 
 * MyEclipse Struts
 * Creation date: 11-14-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class PolicycheckideafamilyAction extends Action {
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
		//
		// 获取action
		String action = request.getParameter("action");
		//
		response.setContentType("text/html; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PrintWriter out = response.getWriter();
			//
			if(action.equals("getFamilyCheckItemsTable")){
				//成员审批处理类
				Policycheckideafamily policycheckideafamily = new Policycheckideafamily();
				String pid = request.getParameter("pid");
				String fmids = request.getParameter("fmids");
				String mode = request.getParameter("mode");
				String empid = request.getParameter("empid");
				out.print(policycheckideafamily.getFamilyCheckItemsTable(pid,fmids,mode,empid));
			}else if(action.equals("")){
				
			}else{
				out.print("xiuxiu");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			ConnectionManager.closeQuietly();
		}
		
		
		return null;
	}
}