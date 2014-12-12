package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.page.PageView;

public class FamilyChildInitServlet extends HttpServlet {
	static Logger log = Logger.getLogger(FamilyChildInitServlet.class);
	/**
	 * Constructor of the object.
	 */
	public FamilyChildInitServlet() {
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
		PageView pv = new PageView();
		String nodeId = request.getParameter("nodeId");
		String nodeName = request.getParameter("nodeName");
		// 取出资产信息列表
		Document familychildDoc = pv.extractElementByNode(nodeName, nodeId);
		log.debug("AA " + familychildDoc.asXML());
		Element root = familychildDoc.getRootElement();
		Iterator rootit = root.elementIterator();
		out.println("<br/><div id =\"p00\" oncontextmenu=\"contextMenu('','"
				+ nodeName + "','" + nodeId + "'); return false;\">");
		out.println("家庭信息");
		out.println("</div>");
		out.println("<div id =\"c00\" style=\"display:block\">");
		while (rootit.hasNext()) {
			Element parent = (Element) rootit.next();
			if (!parent.attributeValue("pk").equals("")) {
				if (parent.attributeValue("cn") == null
						|| parent.attributeValue("cn").equals("null")
						|| parent.attributeValue("cn").equals("")) {
					out.println("<div style=\"cursor:hand\" id=\"p"
							+ parent.getName() + parent.attributeValue("pk")
							+ "\" oncontextmenu=\"contextMenu('"
							+ parent.getName() + "','"
							+ parent.attributeValue("cn") + "','"
							+ parent.attributeValue("pk")
							+ "'); return false;\">&nbsp;");
					out
							.write("<img alt=\"收缩\" id =\"img"
									+ parent.getName()
									+ parent.attributeValue("pk")
									+ "\" src=\"/db/page/images/plus.png\" width=\"9\" height=\"9\" border=\"0\" onclick=\"display('"
									+ parent.getName()
									+ parent.attributeValue("pk") + "','"
									+ parent.attributeValue("cn") + "',"
									+ parent.attributeValue("pk") + ",1)\"/>");
					out.println("&nbsp;<span onclick=\"viewFamilyChild('"+parent.attributeValue("pk")+"','"+parent.getName()+"')\">" + parent.attributeValue("visible")+"</span>");
					out.println("</div>");
					out.println("<div style=\"display : none\" id=\"c"
							+ parent.getName() + parent.attributeValue("pk")
							+ "\" >");
					out.println("</div>");
				} else {
					out.println("<div style=\"cursor:hand\" id=\"p"
							+ parent.getName() + parent.attributeValue("pk")
							+ "\"  oncontextmenu=\"contextMenu('"
							+ parent.getName() + "','"
							+ parent.attributeValue("cn") + "','"
							+ parent.attributeValue("pk")
							+ "'); return false;\">&nbsp;");
					out
							.write("<img alt=\"展开\" id =\"img"
									+ parent.getName()
									+ parent.attributeValue("pk")
									+ "\" src=\"/db/page/images/plus.png\" width=\"9\" height=\"9\" border=\"0\" onclick=\"display('"
									+ parent.getName()
									+ parent.attributeValue("pk") + "','"
									+ parent.attributeValue("cn") + "',"
									+ parent.attributeValue("pk") + ",1)\"/>");
					out.println("&nbsp;<span onclick=\"viewFamilyChild('"+parent.attributeValue("pk")+"','"+parent.getName()+"')\">" + parent.attributeValue("visible")+"</span>");
					out.println("</div>");
					out
							.println("<div style=\"cursor:hand\" style=\"display : none\" id=\"c"
									+ parent.getName()
									+ parent.attributeValue("pk") + "\" >");
					out.println("</div>");
				}
			}
		}
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
