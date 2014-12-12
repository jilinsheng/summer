package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.classtype.ClassHandle;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.entity.SysTEmployee;

public class FamilyClassInitServlet extends HttpServlet {
	static Logger log = Logger.getLogger(FamilyClassInitServlet.class);

	/**
	 * Constructor of the object.
	 */
	public FamilyClassInitServlet() {
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
		HttpSession httpsession =request.getSession();
		SysTEmployee employee =(SysTEmployee) httpsession.getAttribute("employee");
		Long empid=employee.getEmployeeId();
		String nodeId = request.getParameter("nodeId");
		TreeHandle treeHandle = new TreeHandleImpl();
		ClassHandle ch = new ClassHandle();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		log.debug("nodeId" + nodeId);
		if(nodeId==null||nodeId.equals("")){
			return ;
		}
		Document doc = treeHandle.selectEntities("FAMILYCLASS", (nodeId == null
				|| nodeId.equals("") || nodeId.equals("null")) ? null
				: new Long(nodeId));
		try {
			doc = ch.getClassType(doc, nodeId, "FAMILYCLASS");
		} catch (Exception e) {
			e.printStackTrace();
		}
		treeHandle.saveEntity(doc,empid);
		List list = doc.selectNodes("//FAMILYCLASS/property");
		Iterator it = list.iterator();
		out
				.println("<table width=\"100\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\"><caption>分类信息</caption>");
		while (it.hasNext()) {
			Element element = (Element) it.next();
			if (element.attributeValue("isprimary") == null
					&& element.attributeValue("isforeign") == null) {
				String temp = element.getText();
				if (temp.equals("1")) {
					out.println("<tr><td>");
					out.println(element.attributeValue("title"));
					out.println("</td><td>");
					out
							.println("<img width=\"20\" height=\"20\" border=\"0\" src=\"/db/page/images/quest.gif\" alt=\"确认\" style=\"cursor:hand\" onclick=\"ClassCommit(this,'"
									+ nodeId
									+ "','"
									+ element.attributeValue("column")
									+ "','FAMILYCLASS')\"/>");
					out.println("</td></tr>");

				} else if (temp.equals("2")) {
					out.println("<tr><td>");
					out.println(element.attributeValue("title"));
					out.println("</td><td>");
					out
							.println("<img width=\"20\" height=\"20\" border=\"0\" src=\"/db/page/images/right.gif\" alt=\"是\"/>");
					out.println("</td></tr>");
				} else {

				}

			}
		}
		out.println("</table>");
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
		HttpSession httpsession =request.getSession();
		SysTEmployee employee =(SysTEmployee) httpsession.getAttribute("employee");
		Long empid=employee.getEmployeeId();
		String nodeId = request.getParameter("nodeId");
		String nodeName = request.getParameter("nodeName");
		ClassHandle ch = new ClassHandle();
		TreeHandle treeHandle = new TreeHandleImpl();
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");
		PrintWriter out = response.getWriter();
		Document doc = treeHandle
				.selectEntities(nodeName,
						(nodeId == null || nodeId.equals("")) ? null
								: new Long(nodeId));
		try {
			doc = ch.getClassType(doc, nodeId, nodeName);
			treeHandle.saveEntity(doc,empid);
			List list = doc.selectNodes("//" + nodeName + "/property");
			Iterator it = list.iterator();
			out
					.println("<table width=\"100\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\"><caption>分类信息</caption>");
			while (it.hasNext()) {
				Element element = (Element) it.next();
				if (element.attributeValue("isprimary") == null
						&& element.attributeValue("isforeign") == null) {
					String temp = element.getText();
					if (temp.equals("1")) {
						out.println("<tr><td>");
						out.println(element.attributeValue("title"));
						out.println("</td><td>");
						out
								.println("<img width=\"20\" height=\"20\" border=\"0\" src=\"/db/page/images/quest.gif\" alt=\"确认\" style=\"cursor:hand\" onclick=\"ClassCommit(this,'"
										+ nodeId
										+ "','"
										+ element.attributeValue("column")
										+ "','" + nodeName + "')\"/>");
						out.println("</td></tr>");

					} else if (temp.equals("2")) {
						out.println("<tr><td>");
						out.println(element.attributeValue("title"));
						out.println("</td><td>");
						out
								.println("<img width=\"20\" height=\"20\" border=\"0\" src=\"/db/page/images/right.gif\" alt=\"是\"/>");
						out.println("</td></tr>");
					} else {

					}

				}
			}
			out.println("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.flush();
		out.close();
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
