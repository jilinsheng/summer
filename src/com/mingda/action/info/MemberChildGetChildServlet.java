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

import com.mingda.common.page.PageView;

public class MemberChildGetChildServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MemberChildGetChildServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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

		response.setCharacterEncoding("GB18030");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		PageView pv = new PageView();
		String nodeId = request.getParameter("nodeId");
		String code = request.getParameter("code");
		String level = request.getParameter("level");
		Integer i = new Integer(level);
		String str = "&nbsp;";
		for (int j = 0; j < i.intValue(); j++) {
			str = str + "&nbsp;";
		}
		int m = i.intValue() + 1;
		// 取出资产信息列表
		Document familychildDoc = pv.extractElementByNode(code, nodeId);
		if (familychildDoc != null) {
			Element root = familychildDoc.getRootElement();
			Iterator rootit = root.elementIterator();
			while (rootit.hasNext()) {
				Element parent = (Element) rootit.next();
				if (!parent.attributeValue("pk").equals("")) {
					if (parent.attributeValue("cn") == null
							|| parent.attributeValue("cn").equals("null")
							|| parent.attributeValue("cn").equals("")) {
						out.write("<div style=\"cursor:hand\" id=\"p"
								+ parent.getName()
								+ parent.attributeValue("pk")
								+ "\" oncontextmenu=\"contextMenu('"
								+ parent.getName() + "','"
								+ parent.attributeValue("cn") + "','"
								+ parent.attributeValue("pk")
								+ "'); return false;\">&nbsp;");
						out
								.write(str
										+ "<img alt=\"收缩\" id =\"img"
										+ parent.getName()
										+ parent.attributeValue("pk")
										+ "\" src=\"/db/page/images/minus.png\" width=\"9\" height=\"9\" border=\"0\" onclick=\"display('"
										+ parent.getName()
										+ parent.attributeValue("pk") + "','"
										+ parent.attributeValue("cn") + "',"
										+ parent.attributeValue("pk") + "," + m
										+ ")\"/>");
						out.println("&nbsp;<span onclick=\"viewMemberChild('"+parent.attributeValue("pk")+"','"+parent.getName()+"')\">" + parent.attributeValue("visible")+"</span>");
						out.write("</div>");
						out.write("<div style=\"display:none\" id=\"c"
								+ parent.getName()
								+ parent.attributeValue("pk") + "\" >");
						out.write("</div>");
					} else {
						out.write("<div style=\"cursor:hand\" id=\"p"
								+ parent.getName()
								+ parent.attributeValue("pk")
								+ "\" oncontextmenu=\"contextMenu('"
								+ parent.getName() + "','"
								+ parent.attributeValue("cn") + "','"
								+ parent.attributeValue("pk")
								+ "'); return false;\">&nbsp;");
						out
								.write(str
										+ "<img alt=\"展开\" id =\"img"
										+ parent.getName()
										+ parent.attributeValue("pk")
										+ "\" src=\"/db/page/images/plus.png\" width=\"9\" height=\"9\" border=\"0\" onclick=\"display('"
										+ parent.getName()
										+ parent.attributeValue("pk") + "','"
										+ parent.attributeValue("cn") + "',"
										+ parent.attributeValue("pk") + "," + m
										+ ")\"/>");
						out.println("&nbsp;<span onclick=\"viewMemberChild('"+parent.attributeValue("pk")+"','"+parent.getName()+"')\">" + parent.attributeValue("visible")+"</span>");
						out.write("</div>");
						out.write("<div style=\"display:none\" id=\"c"
								+ parent.getName()
								+ parent.attributeValue("pk") + "\" >");
						out.write("</div>");
					}
				}
			}
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
