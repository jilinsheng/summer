/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.card;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;

import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;

/**
 * MyEclipse Struts Creation date: 06-23-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class GetMemberBaseAction extends Action {
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

		Document dictionary = (Document) servlet.getServletContext()
				.getAttribute("dictionary");
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");

		String nodeName = request.getParameter("nodeName");
		String nodeId = request.getParameter("nodeId");

		PageView pv = new PageView();
		
		Document treedoc = (Document) servlet.getServletContext()
		.getAttribute("tree");
		TreeHandle tree = new TreeHandleImpl(treedoc);

		try {

			PrintWriter out = response.getWriter();
			out.write(pv.writeTablebyXML(
					tree.selectSingleEntity(nodeName, new Long(nodeId)),
					dictionary, 1).getRootElement().asXML());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}