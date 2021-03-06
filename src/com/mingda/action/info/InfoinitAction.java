/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;

/**
 * MyEclipse Struts Creation date: 04-28-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class InfoinitAction extends Action {
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
		TreeHandle treeHandle = new TreeHandleImpl();
		Document dictionary = (Document) servlet.getServletContext()
				.getAttribute("dictionary");
		PageView pv = new PageView();
		String nodeId = request.getParameter("nodeId");
		String nodeName = request.getParameter("nodeName");
		response.setCharacterEncoding("GB18030");
		response.setContentType("text/html");
		Document doc = null;
		try {
			doc = treeHandle.selectSingleEntity(nodeName, new Long(nodeId));
			PrintWriter out = response.getWriter();
			Element root = doc.getRootElement();
			Iterator rit = root.elementIterator();
			out.write("<br/><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			while (rit.hasNext()) {
				Element element = (Element) rit.next();
				Iterator it = element.elementIterator();
				while (it.hasNext()) {
					Element currentElement = (Element) it.next();
					String[] s = pv.writeTextByXML(currentElement, dictionary);
					if (s[0] == null || s[0].equals("")) {

					} else {
						out.write("<tr>");
						out.write("<th>");
						out.write(s[0]);
						out.write("</th>");
						out.write("<td>");
						out.write(s[1]);
						out.write("</td>");
						out.write("</tr>");
					}
				}
			}
			out.write("</table>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}