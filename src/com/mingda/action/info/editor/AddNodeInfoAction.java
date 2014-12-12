/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.editor;

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

import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;

/**
 * MyEclipse Struts Creation date: 06-30-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class AddNodeInfoAction extends Action {
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
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		String code = request.getParameter("code");
		String pid = request.getParameter("pid");
		
		// 从服务器中取出结构xml
		Document treedoc = (Document) servlet.getServletContext().getAttribute(
				"tree");
		TreeHandle tree = new TreeHandleImpl(treedoc);
		
		PageView pv = new PageView();
		String str = "";
		Document doc = tree.selectSingleEntity(code, null);
		Element element = (Element) doc.selectSingleNode("//" + code
				+ "/property[@isforeign='true']");
		element.setText(pid);
		doc = pv.writeFormbyXML(doc, dictionary, 1);
		if (code.equals("PAPERS")) {
			// 处理原始证件
			str = "<iframe frameborder=\"0\" id=\"upload\" name=\"upload\" height=\"100%\" width=\"100%\" src=\"/db/page/info/editor/uploadinit.do?pid="
					+ pid + "&code=" + code + "\"></iframe>";
		} else if (code.equals("MEMBER")) {
			// 删除多余的与户主关系
			Element relmaster = (Element) doc.selectSingleNode(
					"//tr/td/select/option[@value='112']");
			Element relmasters = (Element) doc
					.selectSingleNode("//tr/td/select[@name='MEMBER/RELMASTER']");
			relmasters.remove(relmaster);
			// 删除多余的与户主关系
			str = "<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "<tr><td>"
					+ "<form action=\"/db/page/info/familychild/FamilyChildSaveServlet\"  id=\"inputForm\" method=\"post\">"
					+ "<table class=\"table1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
					+ doc.asXML()
					+ "</table><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "<span id=\"savebnt\"><input type=\"button\" value=\"保存\" onclick=\"save('inputForm')\" /></span>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"divResult\" style=\"color:red\"></span>"
					+ "</p></form></td><td valign=\"top\" align=\"left\" width=\"100\">"
					+ "<iframe id=\"inch\" name=\"inch\" scrolling=\"no\" src=\"/db/page/info/editor/inch.jsp?memberId="
					+ "\" width=\"110\" height=\"180\" frameborder=\"0\"></iframe>"
					+ "</td></tr></table>";

		} else if (code.equals("ANNEX")) {
			// 处理多媒体信息
			str = "<iframe frameborder=\"0\" id=\"upload\" name=\"upload\" height=\"100%\" width=\"100%\" src=\"/db/page/info/editor/uploadinit.do?pid="
					+ pid + "&code=" + code + "\"></iframe>";
		} else {
			str = "<form action=\"/db/page/info/familychild/FamilyChildSaveServlet\" enctype=\"multipart/form-data\" id=\"inputForm\" method=\"post\">" +
					"<table class=\"table1\" width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
					+ doc.getRootElement().asXML()
					+ "</table><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "<span id=\"savebnt\"><input type=\"button\" value=\"保存\" onclick=\"save('inputForm')\" /></span>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"divResult\" style=\"color:red\"></span>"
					+ "</p></form>"
					+ "<iframe style=\"display:none\" id=\"fileiframe\" name=\"fileiframe\"></iframe>";
		}
		try {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("GB18030");
			response.setContentType("text/html");
			out.write(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}