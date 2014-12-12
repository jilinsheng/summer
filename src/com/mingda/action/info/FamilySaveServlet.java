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
import org.dom4j.Node;

import com.mingda.common.SessionFactory;
import com.mingda.common.classtype.ClassHandle;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.entity.SysTEmployee;

public class FamilySaveServlet extends HttpServlet {
	static Logger log = Logger.getLogger(FamilySaveServlet.class);
	/**
	 * Constructor of the object.
	 */
	public FamilySaveServlet() {
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
		HttpSession httpsession =request.getSession();
		SysTEmployee employee =(SysTEmployee) httpsession.getAttribute("employee");
		Long empid=employee.getEmployeeId();
		log.debug("生成页面表单：家庭信息");
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
		Document tree =(Document)this.getServletContext().getAttribute("tree");
		String result = "保存失败";
		TreeHandle treehandle = new TreeHandleImpl(tree);
		try {
			/*
			 * 保存家庭
			 */
			Document tempdoc = DocumentHelper.createDocument();
			tempdoc.setXMLEncoding("GB18030");
			Element temproot = tempdoc.addElement("root");
			Iterator it = root.elementIterator("FAMILY");
			while (it.hasNext()) {
				Element eml = (Element) it.next();
				temproot.add((Node) eml.clone());
			}
			// 取家庭id
			log.debug(tempdoc.asXML());
			String familyid = tempdoc.selectSingleNode(
					"//FAMILY/property[@column='FAMILY_ID']").getText();
			
			String type = "1";//1：修改 0:新建
			if (familyid == null || familyid.equals("")) {
				type = "0";
			}
			
			tempdoc = treehandle.saveEntity(tempdoc,empid);
			familyid = (tempdoc
					.selectSingleNode("//FAMILY/property[@column='FAMILY_ID']")
					.getText());
			/*
			 * 保存成员
			 */
			Document tempdoc1 = DocumentHelper.createDocument();
			tempdoc1.setXMLEncoding("GB18030");
			Element temproot1 = tempdoc1.addElement("root");
			// 放入外键 家庭id

			it = root.elementIterator("MEMBER");
			while (it.hasNext()) {
				Element eml = (Element) it.next();
				temproot1.add((Node) eml.clone());
				log.debug("member property:   "+eml.asXML());
			}
			
			temproot1
					.selectSingleNode("//MEMBER/property[@column='FAMILY_ID']")
					.setText(familyid);
			log.debug(temproot1.asXML());
			tempdoc1 = treehandle.saveEntity(tempdoc1,empid);
			result = "保存成功";
			response.setContentType("text/html");
			response.setCharacterEncoding("GB18030");
			PrintWriter out = response.getWriter();
			out.println(result + "," + familyid + "," + type);
			// 计算分类;
			ClassHandle ch = new ClassHandle();
			Document fdoc = treehandle.selectEntities("FAMILYCLASS", new Long(
					familyid));
			treehandle.saveEntity(ch
					.getClassType(fdoc, familyid, "FAMILYCLASS"),empid);
			/*Document mdoc = treehandle.selectEntities("MEMBERCLASS", new Long(
					temproot1
					.selectSingleNode("//MEMBER/property[@column='MEMBER_ID']")
					.getText()));
			treehandle.saveEntity(ch.getClassType(mdoc, temproot1
					.selectSingleNode("//MEMBER/property[@column='MEMBER_ID']")
					.getText(), "MEMBERCLASS"));*/
			// 计算分类
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			SessionFactory.closeSession();
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
