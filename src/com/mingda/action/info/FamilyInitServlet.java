package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTEmployee;

public class FamilyInitServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FamilyInitServlet() {
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
		Document dictionary = (Document) getServletContext().getAttribute(
				"dictionary");
		/*
		 * 取机构id
		 */
		HttpSession session = request.getSession();
		SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
		String organizationid = employee.getSysTOrganization()
				.getOrganizationId();
		String nodeId = request.getParameter("nodeId");
		PageView pageView = new PageView();
		TreeHandle treeHandle = new TreeHandleImpl();
		PrintWriter out = response.getWriter();
		out.println("<div id =\"middle\"><form name=\"familyForm\">");
		out
				.println("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\">");
		out.println("<caption>");
		out.println("家庭信息");
		out.println("</caption>");
		try {
			Document doc = treeHandle.selectSingleEntity("FAMILY",
					(nodeId == null || nodeId.equals("")) ? null : new Long(
							nodeId));
			Log4jApp.logger(doc.asXML());
			Element root = doc.getRootElement();
			/*
			 * 单个实体的页面解析
			 */
			Iterator first = root.elementIterator();
			String masterid = "";
			if (first.hasNext()) {
				Element firstchild = (Element) first.next();
				Iterator its = firstchild.elementIterator();
				while (its.hasNext()) {
					Element element = (Element) its.next();
					if (element.attributeValue("column").equals("MASTERID")) {
						masterid = element.getText();
					}
					if (element.attributeValue("column").equals(
							"ORGANIZATION_ID")) {
						element.setText(organizationid);
					}
					String[] a = pageView.writeHtmlByXML(element, firstchild
							.getName(), dictionary);
					if (a[0].equals("h")) {
						out.println(a[1]);
					} else {
						out.println("<tr>");
						out.println("<th>");
						out.println(a[0]);
						out.println("</th>");
						out.println("<td>");
						out.println(a[1]);
						out.println("</td>");
						out.println("</tr>");
					}
				}
			}
			Document memdoc = treeHandle.selectSingleEntity("MEMBER",
					(masterid == null || masterid.equals("")) ? null
							: new Long(masterid));
			Element memroot = memdoc.getRootElement();
			Iterator masterit = memroot.elementIterator();
			Log4jApp.logger(memdoc.asXML());
			if (masterit.hasNext()) {
				Element master = (Element) masterit.next();
				Iterator its = master.elementIterator();
				while (its.hasNext()) {
					Element element = (Element) its.next();
					String[] a = pageView.writeHtmlByXML(element, master
							.getName(), dictionary);
					if (a[0].equals("h")) {
						out.println(a[1]);
					} else {
						out.println("<tr>");
						out.println("<th algin=\"center\">");
						out.println(a[0]);
						out.println("</th>");
						out.println("<td algin=\"center\">");
						out.println(a[1]);
						out.println("</td>");
						out.println("</tr>");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("</table>");
		out
				.println("<input class=\"btn\" type=\"button\" value=\"保存家庭信息\" onClick=\"saveFamily('familyForm')\" rule=\"9\"/>");
		out.println("</form></div>");
		// 家庭其他信息按钮
		if (nodeId == null || nodeId.equals("")) {
			out
					.println("<div id =\"bntzone\" style=\"backgroud-color:#ffffff\">");
			out.println("&nbsp;&nbsp;</div>");
		} else {
			out.println("<div id =\"butzone\">");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"分类\" onClick=\"initFamilyClass('"
							+ nodeId + "','FAMILYCLASS')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"居住\" onClick=\"initFamilyChild('"
							+ nodeId + "','HOUSING')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"资产\" onClick=\"initFamilyChild('"
							+ nodeId + "','ASSET')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"收入\" onClick=\"initFamilyChild('"
							+ nodeId + "','FAMILYINCOME')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"支出\" onClick=\"initFamilyChild('"
							+ nodeId + "','FAMILYPAYOUT')\" rule=\"9\"/>");
			out.println("</div>");
		}
		out.flush();
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
		Document dictionary = (Document) getServletContext().getAttribute(
				"dictionary");
		/*
		 * 取机构id
		 */
		HttpSession session = request.getSession();
		SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
		String organizationid = employee.getSysTOrganization()
				.getOrganizationId();
		String nodeId = request.getParameter("nodeId");
		PageView pageView = new PageView();
		TreeHandle treeHandle = new TreeHandleImpl();
		PrintWriter out = response.getWriter();
		out.println("<div id =\"middle\"><form name=\"familyForm\">");
		out
				.println("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\">");
		out.println("<caption>");
		out.println("家庭信息");
		out.println("</caption>");
		try {
			Document doc = treeHandle.selectSingleEntity("FAMILY",
					(nodeId == null || nodeId.equals("")) ? null : new Long(
							nodeId));
			Log4jApp.logger(doc.asXML());
			Element root = doc.getRootElement();
			/*
			 * 单个实体的页面解析
			 */
			Iterator first = root.elementIterator();
			String masterid = "";
			if (first.hasNext()) {
				Element firstchild = (Element) first.next();
				Iterator its = firstchild.elementIterator();
				while (its.hasNext()) {
					Element element = (Element) its.next();
					if (element.attributeValue("column").equals("MASTERID")) {
						masterid = element.getText();
					}
					if (element.attributeValue("column").equals(
							"ORGANIZATION_ID")) {
						element.setText(organizationid);
					}
					String[] a = pageView.writeHtmlByXML(element, firstchild
							.getName(), dictionary);
					if (a[0].equals("h")) {
						out.println(a[1]);
					} else {
						out.println("<tr>");
						out.println("<th>");
						out.println(a[0]);
						out.println("</th>");
						out.println("<td>");
						out.println(a[1]);
						out.println("</td>");
						out.println("</tr>");
					}
				}
			}
			Document memdoc = treeHandle.selectSingleEntity("MEMBER",
					(masterid == null || masterid.equals("")) ? null
							: new Long(masterid));
			Element memroot = memdoc.getRootElement();
			Iterator masterit = memroot.elementIterator();
			Log4jApp.logger(memdoc.asXML());
			if (masterit.hasNext()) {
				Element master = (Element) masterit.next();
				Iterator its = master.elementIterator();
				while (its.hasNext()) {
					Element element = (Element) its.next();
					String[] a = pageView.writeHtmlByXML(element, master
							.getName(), dictionary);
					if (a[0].equals("h")) {
						out.println(a[1]);
					} else {
						out.println("<tr>");
						out.println("<th>");
						out.println(a[0]);
						out.println("</th>");
						out.println("<td>");
						out.println(a[1]);
						out.println("</td>");
						out.println("</tr>");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("</table>");
		out
				.println("<input class=\"btn\" type=\"button\" value=\"保存家庭信息\" onClick=\"saveFamily('familyForm')\" rule=\"9\"/>");
		out.println("</form></div>");
		// 家庭其他信息按钮
		if (nodeId == null || nodeId.equals("")) {
			out
					.println("<div id =\"bntzone\" style=\"backgroud-color:#ffffff\">");
			out.println("&nbsp;&nbsp;</div>");
		} else {
			out.println("<div id =\"butzone\">");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"分类\" onClick=\"initFamilyClass('"
							+ nodeId + "','FAMILYCLASS')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"居住\" onClick=\"initFamilyChild('"
							+ nodeId + "','HOUSING')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"资产\" onClick=\"initFamilyChild('"
							+ nodeId + "','ASSET')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"收入\" onClick=\"initFamilyChild('"
							+ nodeId + "','FAMILYINCOME')\" rule=\"9\"/>");
			out
					.println("<input class=\"btn\" type=\"button\" value=\"支出\" onClick=\"initFamilyChild('"
							+ nodeId + "','FAMILYPAYOUT')\" rule=\"9\"/>");
			out.println("</div>");
		}
		out.flush();
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
