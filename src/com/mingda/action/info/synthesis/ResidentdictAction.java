/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.synthesis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.page.PageView;

/**
 * MyEclipse Struts Creation date: 12-18-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ResidentdictAction extends Action {
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

		response.setCharacterEncoding("gb18030");
		try {
			PrintWriter out = response.getWriter();

			Document dictionary = (Document) this.servlet.getServletContext()
					.getAttribute("dictionary");
			Document tree = (Document) this.servlet.getServletContext()
					.getAttribute("tree");
			PageView pv = new PageView();
			String column = request.getParameter("column");
			if (!"".equals(column)) {
				String[] p = column.split("_");
				Element ele = (Element) tree.selectSingleNode("//" + p[0]
						+ "/property[@column='" + p[1] + "']");
				if (null != ele) {
					String dicsort = "";
					if (null != ele.attributeValue("dicsort")) {
						dicsort = ele.attributeValue("dicsort");
						out.println("<select name=\"value\">");
						out.println(pv.getDictionartHandle()
								.getDictsortToOption(dictionary, dicsort, ""));
						out.println("</select>");
					} else {
						out.println("<input type=\"text\" name=\"value\"/>");
					}
				}
			} else {
				out.println("<input type=\"text\" name=\"value\"/>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}