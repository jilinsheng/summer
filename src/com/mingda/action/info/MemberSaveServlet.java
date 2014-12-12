package com.mingda.action.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mingda.common.classtype.ClassHandle;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.entity.SysTEmployee;

public class MemberSaveServlet extends HttpServlet {
	static Logger log = Logger.getLogger(MemberSaveServlet.class);
	/**
	 * Constructor of the object.
	 */
	public MemberSaveServlet() {
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
		HttpSession httpsession =request.getSession();
		SysTEmployee employee =(SysTEmployee) httpsession.getAttribute("employee");
		Long empid=employee.getEmployeeId();
		DictionaryHandle dh = new DictionaryHandle();
		Document dictionary = (Document) getServletContext().getAttribute(
				"dictionary");
		Log4jApp.logger("保存成员信息");
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
		/*
		 * 回显页面
		 */
		String mastername = root.selectSingleNode(
				root.getPath() + "/MEMBER/property[@column='MEMBERNAME']")
				.getText();
		String relmaster = dh.getDictsortToValue(dictionary, root
				.selectSingleNode(
						root.getPath()
								+ "/MEMBER/property[@column='RELMASTER']")
				.getText());
		String paperid = root.selectSingleNode(
				root.getPath() + "/MEMBER/property[@column='PAPERID']")
				.getText();
		String sex = dh.getDictsortToValue(dictionary, root.selectSingleNode(
				root.getPath() + "/MEMBER/property[@column='SEX']").getText());
		String memberid = root.selectSingleNode(
				root.getPath() + "/MEMBER/property[@column='MEMBER_ID']")
				.getText();

		/**
		 * 显示执行情况结果信息
		 */
		String result = "保存失败";
		TreeHandle treehandle = new TreeHandleImpl();
		try {
			doc=treehandle.saveEntity(doc,empid);
			//取出成员主键
			 	memberid = doc.selectSingleNode(
					root.getPath() + "/MEMBER/property[@column='MEMBER_ID']")
					.getText();
			//取出成员主键
			result = "保存成功";
			response.setContentType("text/html");
			response.setCharacterEncoding("GB18030");
			PrintWriter out = response.getWriter();
			out.println(result + "," + mastername + "," + paperid + ","
					+ relmaster + "," + sex);
			//保存分类
			ClassHandle ch =new ClassHandle();
			treehandle.saveEntity(ch.getClassType(treehandle.selectEntities("MEMBERCLASS", new Long(memberid)), memberid, "MEMBERCLASS"),empid);			
			//保存分类
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
