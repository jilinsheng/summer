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

import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.classtype.ClassHandle;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTEmployee;

public class MemberInitServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MemberInitServlet() {
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
		Document dictionary = (Document) getServletContext().getAttribute(
				"dictionary");
		DictionaryHandle dh = new DictionaryHandle();
		String nodeId = request.getParameter("nodeId");
		String parentId = request.getParameter("parentId");
		PageView pageView = new PageView();
		TreeHandle treeHandle = new TreeHandleImpl();
		Document doc = treeHandle
				.selectSingleEntity("MEMBER", (nodeId == null || nodeId
						.equals("")) ? null : new Long(nodeId));
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		Element root = doc.getRootElement();
		root.selectSingleNode("//MEMBER/property[@isforeign='true']").setText(
				parentId);
		String memberId = nodeId;
		String classhtml = "";
		// 取出分类
		/*
		 * Document memberClassDoc = treeHandle.selectEntities("MEMBERCLASS",
		 * new Long(memberId)); List memberClasses = memberClassDoc
		 * .selectNodes("//MEMBERCLASS/property"); Iterator memberClassIt =
		 * memberClasses.iterator(); classhtml += " <table id=\"tfmsort" +
		 * memberId + "\" width=\"100\" border=\"1\" cellpadding=\"0\"
		 * cellspacing=\"0\" class=\"table1\"><caption>分类信息</caption>"; while
		 * (memberClassIt.hasNext()) { Element memberClass = (Element)
		 * memberClassIt.next(); if (memberClass.attributeValue("isprimary") ==
		 * null && memberClass.attributeValue("isforeign") == null) { classhtml += "<tr><td>";
		 * classhtml += memberClass.attributeValue("title"); classhtml += "</td><td>";
		 * classhtml += memberClass.getText().equals("1") ? "是" : "否"; classhtml += "</td></tr>"; } }
		 * classhtml += "</table>";
		 */
		// 取出分类
		Iterator memberItr = root.elementIterator();
		while (memberItr.hasNext()) {
			Element member = (Element) memberItr.next();
			out
					.write("<table width=\"100%\" height=\"300\" border=\"2\" cellpadding=\"0\" cellspacing=\"0\"  bordercolor=\"#DEEFFF\">");
			out.write("<tr>");
			out
					.write("<td width=\"120\" height=\"120\" align=\"center\" valign=\"middle\"><img id=\"img"
							+ memberId
							+ "\" src=\"/db/page/images/imgfolder.gif\"/></td>");
			out.write("<td width=\"250\" rowspan=\"2\">");
			out
					.write("<table id=\"main\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out
					.write("<tr><td bgcolor=\"#FFFFFF\" valign=\"top\" height=\"250\"><div id=\"main"
							+ memberId
							+ "\" style=\"width:250px; height:210px; overflow-y:scroll;\">");
			out
					.write("<form style=\"padding:0; margin:0\" id=\"inputForm"
							+ memberId
							+ "\"><table id =\"ctable"
							+ memberId
							+ "\"  width=\"90%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\"><caption>成员信息</caption>");
			Iterator memberpropertys = member.elementIterator();
			while (memberpropertys.hasNext()) {
				Element element = (Element) memberpropertys.next();
				String[] a = pageView.writeHtmlByXML(element, member.getName(),
						dictionary);
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
			out.write("</table>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"保存成员信息\" onclick=\"memberSave("
							+ memberId + ")\"/>");
			out.write("</form>\r\n");
			out.write("</div></td></tr>");
			out
					.write("<tr><td height=\"40\"><div id=\"bnt\" style=\"width:250px\">");
			/*
			 * out .write("<input class=\"btn\" type=\"button\" value=\"成员分类\"
			 * onclick=\"initMemberClass(" + memberId + ",'MEMBERSORT')\"/>");
			 * out .write("<input class=\"btn\" type=\"button\" value=\"简历信息\"
			 * onclick=\"initMemberChildNodes(" + memberId + ",'RESUME')\"/>");
			 * out .write("<input class=\"btn\" type=\"button\" value=\"被赡抚养\"
			 * onclick=\"initMemberChildNodes(" + memberId + ",'SUPPORT')\"/>");
			 * out .write("<input class=\"btn\" type=\"button\" value=\"技能信息\"
			 * onclick=\"initMemberChildNodes(" + memberId + ",'SKILL')\"/>");
			 * 
			 * out .write("<input class=\"btn\" type=\"button\" value=\"健康信息\"
			 * onclick=\"initMemberChildNodes(" + memberId + ",'HEALTH')\"/>");
			 * out .write("<input class=\"btn\" type=\"button\" value=\"劳动能力\"
			 * onclick=\"initMemberChildNodes(" + memberId + ",'LABOR')\"/>");
			 * out .write("<input class=\"btn\" type=\"button\" value=\"成员收入\"
			 * onclick=\"initMemberChildNodes(" + memberId + ",'INCOME')\"/>");
			 * out .write("<input class=\"btn\" type=\"button\" value=\"成员支出\"
			 * onclick=\"initMemberChildNodes(" + memberId +
			 * ",'PAYMENTS')\"/>");
			 */
			out.write("</div></td></tr>");
			out.write("</table>");
			out.write("</td>");
			out
					.write("<td rowspan=\"2\"><iframe id=\"memberOper"
							+ memberId
							+ "\" width=\"100%\" height=\"300px\" scrolling=\"auto\" src=\"\" frameborder=\"0\"></iframe></td>\r\n");
			out.write("</tr>");
			out.write("<tr>");
			out
					.write("<td> <div id=\"fmsort"
							+ memberId
							+ "\" style=\" width:120px;height:150px; overflow-y:scroll;\">");
			out.write(classhtml);
			out.write("</div></td>");
			out.write("</tr>");
			out.write("</table>");
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
		HttpSession httpsession =request.getSession();
		SysTEmployee employee =(SysTEmployee) httpsession.getAttribute("employee");
		Long empid=employee.getEmployeeId();
		Document dictionary = (Document) getServletContext().getAttribute(
				"dictionary");
		DictionaryHandle dh = new DictionaryHandle();
		String nodeId = request.getParameter("nodeId");
		PageView pageView = new PageView();
		TreeHandle treeHandle = new TreeHandleImpl();
		ClassHandle ch = new ClassHandle();
		Document doc = treeHandle
				.selectSingleEntity("MEMBER", (nodeId == null || nodeId
						.equals("")) ? null : new Long(nodeId));

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		Element root = doc.getRootElement();
		String memberId = nodeId;
		// 取出分类
		Document memberClassDoc = treeHandle.selectEntities("MEMBERCLASS",
				new Long(memberId));
		try {
			memberClassDoc = treeHandle.saveEntity(ch.getClassType(
					memberClassDoc, memberId, "MEMBERCLASS"),empid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List memberClasses = memberClassDoc
				.selectNodes("//MEMBERCLASS/property");
		Iterator memberClassIt = memberClasses.iterator();
		String classhtml = "";
		classhtml += " <table id=\"tfmsort"
				+ memberId
				+ "\"  width=\"100\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\"><caption>分类信息</caption>";
		while (memberClassIt.hasNext()) {
			Element memberClass = (Element) memberClassIt.next();
			if (memberClass.attributeValue("isprimary") == null
					&& memberClass.attributeValue("isforeign") == null) {
				if (!memberClass.getText().equals("0")) {
					classhtml += "<tr><td>";
					classhtml += memberClass.attributeValue("title");
					classhtml += "</td><td>";
					classhtml += "<img border=\"0\" width=\"20\" height=\"20\" src=\"/db/page/images/quest.gif\" alt=\"确认\" style=\"cursor:hand\" onclick=\"ClassCommit(this,'"
							+ memberId
							+ "','"
							+ memberClass.attributeValue("column")
							+ "','MEMBERCLASS')\"/>";
					classhtml += "</td></tr>";
				}
			}
		}
		classhtml += "</table>";
		// 取出分类
		Iterator memberItr = root.elementIterator();
		while (memberItr.hasNext()) {
			Element member = (Element) memberItr.next();
			out
					.write("<table width=\"100%\" height=\"300\" border=\"2\" cellpadding=\"0\" cellspacing=\"0\"  bordercolor=\"#DEEFFF\">");
			out.write("<tr>");
			out
					.write("<td width=\"120\" height=\"120\" align=\"center\" valign=\"middle\"><img id=\"img"
							+ memberId
							+ "\" src=\"/db/page/images/imgfolder.gif\"/></td>");
			out.write("<td width=\"250\" rowspan=\"2\">");
			out
					.write("<table id=\"main\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out
					.write("<tr><td bgcolor=\"#FFFFFF\" valign=\"top\" height=\"220\"><div id=\"main"
							+ memberId
							+ "\" style=\"width:250px; height:210px; overflow-y:scroll;\">");
			out
					.write("<form style=\"padding:0; margin:0\" id=\"inputForm"
							+ memberId
							+ "\"><table id =\"ctable"
							+ memberId
							+ "\"  width=\"90%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"table1\"><caption>成员信息</caption>");
			Iterator memberpropertys = member.elementIterator();
			while (memberpropertys.hasNext()) {
				Element element = (Element) memberpropertys.next();
				String[] a = pageView.writeHtmlByXML(element, member.getName(),
						dictionary);
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
			out.write("</table>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"保存成员信息\" onclick=\"memberSave("
							+ memberId + ")\"/>");
			out.write("</form>\r\n");
			out.write("</div></td></tr>");
			out
					.write("<tr><td height=\"40\"><div id=\"bnt\" style=\"width:250px\">");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"分类\" onclick=\"initMemberClass("
							+ memberId + ",'MEMBERCLASS')\"/>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"简历\" onclick=\"initMemberChildNodes("
							+ memberId + ",'RESUME')\"/>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"赡抚养\" onclick=\"initMemberChildNodes("
							+ memberId + ",'SUPPORT')\"/>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"技能\" onclick=\"initMemberChildNodes("
							+ memberId + ",'SKILL')\"/>");

			out
					.write("<input class=\"btn\" type=\"button\" value=\"健康\" onclick=\"initMemberChildNodes("
							+ memberId + ",'HEALTH')\"/>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"劳动能力\" onclick=\"initMemberChildNodes("
							+ memberId + ",'LABOR')\"/>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"收入\" onclick=\"initMemberChildNodes("
							+ memberId + ",'INCOME')\"/>");
			out
					.write("<input class=\"btn\" type=\"button\" value=\"支出\" onclick=\"initMemberChildNodes("
							+ memberId + ",'PAYMENTS')\"/>");
			out.write("</div></td></tr>");
			out.write("</table>");
			out.write("</td>");
			out
					.write("<td rowspan=\"2\"><iframe id=\"memberOper"
							+ memberId
							+ "\" width=\"100%\" height=\"300px\" scrolling=\"auto\" src=\"\" frameborder=\"0\"></iframe></td>\r\n");
			out.write("</tr>");
			out.write("<tr>");
			out
					.write("<td> <div id=\"fmsort"
							+ memberId
							+ "\" style=\" width:120px;height:150px; overflow-y:scroll;\">");
			out.write(classhtml);
			out.write("</div></td>");
			out.write("</tr>");
			out.write("</table>");
		}
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
