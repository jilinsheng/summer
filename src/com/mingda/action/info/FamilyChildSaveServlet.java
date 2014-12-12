package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTEmployee;

public class FamilyChildSaveServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FamilyChildSaveServlet() {
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
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		Long empid = employee.getEmployeeId();
		/**
		 * ajax url传过来的中文编码格式utf-8 request设置编码格式为utf-8
		 */
		request.setCharacterEncoding("utf-8");
		Enumeration enumeration = request.getParameterNames();
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB18030");
		Element root = doc.addElement("ROOT");
		while (enumeration.hasMoreElements()) {
			String tagNmae = enumeration.nextElement().toString();
			String tagValue = request.getParameter(tagNmae);
			// 分解html标签形成xml
			String[] tagNames = tagNmae.split("/");
			if (tagNames.length == 2) {
				String name0 = tagNames[0];
				String name1 = tagNames[1];
				Iterator it = root.elementIterator(name0);
				if (it.hasNext()) {
					Element p = (Element) it.next();
					Element c = p.addElement("property");
					c.addAttribute("column", name1);
					c.setText(tagValue);
				} else {
					Element p = root.addElement(name0);
					Element c = p.addElement("property");
					c.addAttribute("column", name1);
					c.setText(tagValue);
				}
			}
		}
		Document treedoc = (Document) this.getServletContext().getAttribute(
				"tree");
		TreeHandle treehandle = new TreeHandleImpl(treedoc);
		Element efamily = (Element) doc.selectSingleNode("//FAMILY");
		if (efamily != null) {

			String onlychild = doc.selectSingleNode(
					"//FAMILY/property[@column='ONLYCHILD']").getText();
			String malcondition = doc.selectSingleNode(
					"//FAMILY/property[@column='MALCONDITION']").getText();
			String famaddress = doc.selectSingleNode(
					"//FAMILY/property[@column='FAMADDRESS']").getText();
			String linkmode = doc.selectSingleNode(
					"//FAMILY/property[@column='LINKMODE']").getText();

			Element eFamilyId = (Element) doc
					.selectSingleNode("//FAMILY/property[@column='FAMILY_ID']");
			
			doc = treehandle.selectSingleEntity("FAMILY", new Long(eFamilyId
					.getText()));
			
			doc.selectSingleNode("//FAMILY/property[@column='ONLYCHILD']")
					.setText(onlychild);
			doc.selectSingleNode("//FAMILY/property[@column='MALCONDITION']")
					.setText(malcondition);
			doc.selectSingleNode("//FAMILY/property[@column='FAMADDRESS']")
					.setText(famaddress);
			doc.selectSingleNode("//FAMILY/property[@column='LINKMODE']")
					.setText(linkmode);
		}
		Element emember = (Element) doc.selectSingleNode("//MEMBER");
		if (emember != null) {
			/*
			 * PageView pv = new PageView(); Element e3 = (Element) doc
			 * .selectSingleNode("//MEMBER/property[@column='BIRTHDAY']"); long
			 * a = new Long(pv.StrToDate(e3.getText(), "yyyyMMdd"))
			 * .longValue(); long b = new Long(pv.DateToDate(new Date(),
			 * "yyyyMMdd")) .longValue();
			 * emember.addElement("property").addAttribute("column", "AGE")
			 * .setText(new Long((b - a) / 10000).toString());
			 */
		}
		String memberId = "";
		String result = "0," + memberId;

		try {
			if (!doc.getRootElement().elements().isEmpty()) {
				doc = treehandle.saveEntity(doc, empid);
				Node node = doc
						.selectSingleNode("//MEMBER/property[@column='MEMBER_ID']");
				if (node != null) {
					memberId = node.getText();
				} else {
					memberId = "0";
				}
				result = "1," + memberId;
			} else {
				result = "2," + memberId;
			}
			response.setContentType("text/html");
			response.setCharacterEncoding("GB18030");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
