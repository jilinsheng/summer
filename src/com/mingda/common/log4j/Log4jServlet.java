package com.mingda.common.log4j;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;

public class Log4jServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Log4jServlet() {
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
		out
				.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/db/page/css/style.css\">");
		out.println("  <BODY>");
		DictionaryHandle dictionaryHandle = new DictionaryHandle();
		getServletContext().setAttribute("dictionary",
				dictionaryHandle.createDictionaryXML());
		TreeHandle tree = new TreeHandleImpl();
		getServletContext().setAttribute("tree", tree.loadTree());
		out
				.println("<p>&nbsp;</p><p align=\"center\" >系统数据更新完毕</p>");
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
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");
		if (file != null) {
			//DOMConfigurator.configure(prefix + file);
			 PropertyConfigurator.configure(prefix+file);
		}
		DictionaryHandle dictionaryHandle = new DictionaryHandle();
		getServletContext().setAttribute("dictionary",
				dictionaryHandle.createDictionaryXML());
		TreeHandle tree = new TreeHandleImpl();
		getServletContext().setAttribute("tree", tree.loadTree());
		
		System.out.println("*******************************************************************");
		System.out.println("**                                                               **");
		System.out.println("**                                                               **");
		System.out.println("**                 服务器启动成功                                                                                        **");
		System.out.println("**                                                               **");
		System.out.println("**                                                               **");
		System.out.println("*******************************************************************");
	}
}
