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

import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;

public class MemberChildGetFormServlet extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(MemberChildGetFormServlet.class);

	/**
	 * Constructor of the object.
	 */
	public MemberChildGetFormServlet() {
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

		String code = request.getParameter("code");
		String childcode = request.getParameter("childcode");
		String type = request.getParameter("type");
		String nodeId = request.getParameter("nodeId");
		Document dictionary = (Document) getServletContext().getAttribute(
				"dictionary");

		PageView pageView = new PageView();
		TreeHandle treeHandle = new TreeHandleImpl();
		DictionaryHandle dh = new DictionaryHandle();

		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");
		PrintWriter out = response.getWriter();

		Document doc = null;
		// 编辑当前节点
		if (type.equals("edit")) {
			doc = treeHandle.selectSingleEntity(code, (nodeId == null || nodeId
					.equals("")) ? null : new Long(nodeId));
			// 新建子结点
		} else if (type.equals("add")) {
			doc = treeHandle.selectSingleEntity(childcode, null);
			Element other = (Element) doc
					.selectSingleNode("//property[@isforeign='true']");
			other.setText(nodeId);
		} else {
			out.println("进行什么操作");
			return;
		}

		log.debug(doc.asXML());
		out.write("<form id=\"inputForm\">");
		out
				.write("<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table5\">");
		Element root = doc.getRootElement();
		Iterator first = root.elementIterator();
		if (first.hasNext()) {
			Element firstchild = (Element) first.next();
			Iterator its = firstchild.elementIterator();
			while (its.hasNext()) {
				Element element = (Element) its.next();

				String[] a = pageView.writeHtmlByXML(element, firstchild
						.getName(), dictionary);
				if (a[0].equals("h")) {
					out.write(a[1]);
				} else {
					out.write("<tr>");
					out.write("<th>");
					out.write(a[0]);
					out.write("</th>");
					out.write("<td>");
					out.write(a[1]);
					out.write("</td>");
					out.write("</tr>");
				}
			}
		}
		out.write("</table>");
		out
				.write("<input type=\"button\" value=\"保存信息\" onclick=\"memberchildSave()\"/>");
		out.write("</form>");
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
