package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;

public class MemberListServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MemberListServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String nodeId = request.getParameter("nodeId");
		TreeHandle treeHandle = new TreeHandleImpl();
		Document memberlist = treeHandle.selectEntities("MEMBER", new Long(
				nodeId));
		Element root = memberlist.getRootElement();
		Iterator memberItr = root.elementIterator();
		out
				.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.write("<caption>成员列表</caption>");
		while (memberItr.hasNext()) {
			Element member = (Element) memberItr.next();
			String memberId = "";
			String mastername = "";
			Iterator itp = member.elementIterator();
			out.write("<tr>");
			while (itp.hasNext()) {
				Element element = (Element) itp.next();
				if (element.attributeValue("column").equals("MEMBER_ID")) {
					memberId = element.getText();
				}
				if (element.attributeValue("column").equals("MEMBERNAME")) {
					mastername = element.getText();
				}
			}

			out
					.write("<td><input name=\"radio\" type=\"radio\"  value=\"\" onclick=\"initmember('"
							+ memberId + "')\"></td>");
			out.write("<td><span id =\"MEMBERNAME"+memberId+"\">" + mastername + "</span></td>");
			out.write("</tr>");
		}
		out.write("</table>");
		out.write("<p>");
		out
				.write("<input value=\"添加成员\" type=\"button\" class=\"btn\" onclick=\"addmember('','"
						+ nodeId + "')\" />");
		out.write("</p>");
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String nodeId = request.getParameter("nodeId");
		TreeHandle treeHandle = new TreeHandleImpl();
		Document memberlist = treeHandle.selectEntities("MEMBER", new Long(
				nodeId));
		Element root = memberlist.getRootElement();
		Iterator memberItr = root.elementIterator();
		out
				.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.write("<caption>成员列表</caption>");
		while (memberItr.hasNext()) {
			Element member = (Element) memberItr.next();
			String memberId = "";
			String mastername = "";
			Iterator itp = member.elementIterator();
			out.write("<tr>");
			while (itp.hasNext()) {
				Element element = (Element) itp.next();
				if (element.attributeValue("column").equals("MEMBER_ID")) {
					memberId = element.getText();
				}
				if (element.attributeValue("column").equals("MEMBERNAME")) {
					mastername = element.getText();
				}
			}

			out
					.write("<td><input name=\"radio\" type=\"radio\"  value=\"\" onclick=\"initmember('"
							+ memberId + "')\"></td>");
			out.write("<td>" + mastername + "</td>");
			out.write("</tr>");
		}
		out.write("</table>");
		out.write("<p>");
		out
				.write("<input value=\"添加成员\" type=\"button\" class=\"btn\" onclick=\"addmember('','"
						+ nodeId + "')\" />");
		out.write("</p>");
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
