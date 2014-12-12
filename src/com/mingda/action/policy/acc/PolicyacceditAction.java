/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.acc;

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
 * Creation date: 11-29-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class PolicyacceditAction extends Action {
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
			if(action.equals("getPolicyAllCheckAccNoOverFlag")){
				//��ȡ�Ƿ���ڵ�ǰҵ������������ڴ����ı�ʶ
				String pid = request.getParameter("pid");
		        String empid = request.getParameter("empid");
		        Policyaccedit policyaccedit = new Policyaccedit();
		        out.print(policyaccedit.getPolicyAllCheckAccNoOverFlag(empid,pid));
			}else if(action.equals("setPolicyAllAccFlag")){
				//���õ�ǰҵ����������ʶ(���������߳����������)
				String pid = request.getParameter("pid");
		        String empid = request.getParameter("empid");	        
		        String mode = request.getParameter("mode");
		        Policyaccedit policyaccedit = new Policyaccedit();
		        out.print(policyaccedit.setPolicyAllAccFlag(empid,pid,mode));
			}else if(action.equals("setPolicyAllCheckAccItems")){
				//������ǰҵ�����������ݺ��·�
				String pid = request.getParameter("pid");
		        String empid = request.getParameter("empid");	
		        String tyear = request.getParameter("tyear");
		        String tmonth = request.getParameter("tmonth");
		        Policyaccedit policyaccedit = new Policyaccedit();
		        out.print(policyaccedit.setPolicyAllCheckAccItems(pid,empid,tyear,tmonth));
			}else if(action.equals("deletePolicyAllCheckAccItems")){
				//ɾ����ǰҵ�����������ݺ��·�
				String pid = request.getParameter("pid");
		        String empid = request.getParameter("empid");	
		        String tyear = request.getParameter("tyear");
		        String tmonth = request.getParameter("tmonth");
		        Policyaccedit policyaccedit = new Policyaccedit();
		        out.print(policyaccedit.deletePolicyAllCheckAccItems(pid,empid,tyear,tmonth));
			}else if(action.equals("getPolicyAllCheckAccYearItems")){
				//ȡ�õ�ǰҵ������������
				String pid = request.getParameter("pid");
		        String empid = request.getParameter("empid");
		        Policyaccedit policyaccedit = new Policyaccedit();
		        out.print(policyaccedit.getPolicyAllCheckAccYearItems(pid,empid));
			}else if(action.equals("getPolicyAllCheckAccMonthItems")){
				//ȡ�õ�ǰҵ����������·�
				String pid = request.getParameter("pid");
		        String empid = request.getParameter("empid");
		        Policyaccedit policyaccedit = new Policyaccedit();
		        out.print(policyaccedit.getPolicyAllCheckAccMonthItems(pid,empid));
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