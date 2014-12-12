/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.relief;

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
 * Creation date: 04-13-2009
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RelieflistAction extends Action {
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
		//log.debug(action);
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
			//
			if("updateListMonery".equals(action)){
				//
				String jempid = request.getParameter("jempid");
				String jdeptid = request.getParameter("jdeptid");
				String jdepth = request.getParameter("jdepth");
				String jpid = request.getParameter("jpid");
				String jfmid = request.getParameter("jfmid");
				//
            	HashMap hashmap = new HashMap();
            	hashmap.put("jempid", jempid);
            	hashmap.put("jdeptid", jdeptid);
            	hashmap.put("jdepth", jdepth);
            	hashmap.put("jpid", jpid);
            	hashmap.put("jfmid", jfmid);
				Relieflist relieflist = new Relieflist();
		        out.print(relieflist.updateListMonery(hashmap));
			}else if(action.equals("")){

			}			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			ConnectionManager.closeQuietly();
		}
		return null;
	}
}