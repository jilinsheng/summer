/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.editor;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.common.ConstantDefine;
import com.mingda.common.SessionFactory;
import com.mingda.common.classtype.ClassHandle;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 06-28-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */

public class EditorGetNodeListAction extends Action {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	static Logger log = Logger.getLogger(EditorGetNodeListAction.class);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpsession =request.getSession();
		SysTEmployee employee =(SysTEmployee) httpsession.getAttribute("employee");
		Long empid=employee.getEmployeeId();
		String nodeName = request.getParameter("nodeName");
		String nodeId = request.getParameter("nodeId");
		response.setCharacterEncoding("GB18030");
		response.setContentType("text/html");
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");

		// �ӷ�������ȡ���ṹxml
		Document treedoc = (Document) servlet.getServletContext().getAttribute(
				"tree");
		TreeHandle tree = new TreeHandleImpl(treedoc);
		ClassHandle ch =new ClassHandle();

		PageView pv = new PageView();
		Document doc = null;
		try {
			PrintWriter out = response.getWriter();
			if (nodeName.equals("FAMILYCLASS")) {
				Document familyclass = tree.selectEntities("FAMILYCLASS", new Long(nodeId));
				Document famdoc = familyclass;
				familyclass = tree.saveEntity(ch.getClassType(familyclass, nodeId,
						"FAMILYCLASS"),empid);
				familyclass = pv.writeTablebyXML(familyclass, dictionary, 1);
				log.debug("####   "+familyclass.asXML());
				Iterator it = familyclass.getRootElement().elementIterator();
				while (it.hasNext()) {
					Element tr = (Element) it.next();
					Element td = (Element) tr.elements().get(1);
					Element td0 = (Element) tr.elements().get(0);
					Element temp = (Element) famdoc.selectSingleNode("//FAMILYCLASS/property[@title='" + td0.getText() + "']");
					String column = temp.attributeValue("column");
					Element span= (Element) td.elements().get(0);//����ֵ 
					if (span.getText().equals(ConstantDefine.CLASSSTATUS_NOCHN)) {
						familyclass.getRootElement().remove(tr);
					} else if (span.getText().equals(ConstantDefine.CLASSSTATUS_COMMITCHN)) {
						td.remove(span);
						td.addElement("img").addAttribute("style","cursor:hand").addAttribute("src",
								"/db/page/images/quest.gif").addAttribute("alt",
								"δ��ʵ").addAttribute(
								"onclick",
								"ClassCommit(this,'FAMILYCLASS'," + nodeId + ",'"
										+ column + "')");
					} else if (span.getText().equals(ConstantDefine.CLASSSTATUS_YESCHN)) {
						td.remove(span);
						/*td.addElement("img").addAttribute("src",
								"/db/page/images/right.gif").addAttribute("alt",
								"�Ѻ�ʵ");*/
						td.addElement("img").addAttribute("style","cursor:hand").addAttribute("src",
						"/db/page/images/right.gif").addAttribute("alt",
						"�Ѻ�ʵ").addAttribute(
						"onclick",
						"ClassCommit(this,'FAMILYCLASS'," + nodeId + ",'"
								+ column + "')");
					} else {
						familyclass.getRootElement().remove(tr);
					}
				}
				
			out.write("<span>��ͥ������Ϣ</spqn><table width=\"100%\" border=\"1\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\">"
						+ familyclass.getRootElement().asXML() + "</table>");
			Log4jApp.logger("��ͥ����ҳ�����:"+familyclass.getRootElement().asXML() );
			} else if (nodeName.equals("MEMBERCLASS")) {
				Document familyclass = tree.selectEntities("MEMBERCLASS", new Long(nodeId));
				Document famdoc = familyclass;
				familyclass = tree.saveEntity(ch.getClassType(familyclass, nodeId,
						"MEMBERCLASS"),empid);
				familyclass = pv.writeTablebyXML(familyclass, dictionary, 1);
				Iterator it = familyclass.getRootElement().elementIterator();
				while (it.hasNext()) {
					Element tr = (Element) it.next();
					Element td = (Element) tr.elements().get(1);
					Element td0 = (Element) tr.elements().get(0);
					Element temp = (Element) famdoc.selectSingleNode("//MEMBERCLASS/property[@title='" + td0.getText() + "']");
					String column = temp.attributeValue("column");
					Element span= (Element) td.elements().get(0);//����ֵ 
					if (span.getText().equals(ConstantDefine.CLASSSTATUS_NOCHN)) {
						familyclass.getRootElement().remove(tr);
					} else if (span.getText().equals(ConstantDefine.CLASSSTATUS_COMMITCHN)) {
						td.remove(span);
						td.addElement("img").addAttribute("style","cursor:hand").addAttribute("src",
								"/db/page/images/quest.gif").addAttribute("alt",
								"δ��ʵ").addAttribute(
								"onclick",
								"ClassCommit(this,'MEMBERCLASS'," + nodeId + ",'"
										+ column + "')");
					} else if (span.getText().equals(ConstantDefine.CLASSSTATUS_YESCHN)) {
						td.remove(span);
						/*td.addElement("img").addAttribute("src",
								"/db/page/images/right.gif").addAttribute("alt",
								"�Ѻ�ʵ");*/
						td.addElement("img").addAttribute("style","cursor:hand").addAttribute("src",
						"/db/page/images/right.gif").addAttribute("alt",
						"�Ѻ�ʵ").addAttribute(
						"onclick",
						"ClassCommit(this,'MEMBERCLASS'," + nodeId + ",'"
								+ column + "')");
					} else {
						familyclass.getRootElement().remove(tr);
					}
				}
				
			out.write("<span>��Ա������Ϣ</spqn><table width=\"100%\" border=\"1\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\">"
						+ familyclass.getRootElement().asXML() + "</table>");
			Log4jApp.logger("��Ա����ҳ�����:"+familyclass.getRootElement().asXML() );
			/*	doc = tree.selectEntities(nodeName, new Long(nodeId));
				doc = pv.writeListbyXML(doc, dictionary);
				out
						.write("<span>��Ա������Ϣ</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
								+ "<tr><th><input type=\"button\" value=\"ˢ�³�Ա����\" onclick=\"refreshClass('MEMBERCLASS',"
								+ nodeId + ")\"/></th></tr></table>");*/
			} else if (nodeName.equals("FAMILYINCOME")) {
				doc = tree.selectEntities(nodeName, new Long(nodeId));
				doc = pv.writeFormbyXML(doc, dictionary, 1);
				out
						.write("<span>��ͥ������Ϣ</span><form action=\"/db/page/info/familychild/FamilyChildSaveServlet\" enctype=\"multipart/form-data\" id=\"inputForm\" method=\"post\"><table class=\"table1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
								+ doc.getRootElement().asXML()
								+ "</table><p>"
								+ "<span id=\"savebnt\"><input type=\"button\" value=\"����\" onclick=\"save('inputForm')\" />"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;<button onclick=\"parentClose()\">�˳�����������Ϣ</button></span><span id=\"divResult\" style=\"color:red\"></span>"
								+ "</p></form>");
			} else if (nodeName.equals("MEMBERINCOME")) {
				doc = tree.selectEntities(nodeName, new Long(nodeId));
				doc = pv.writeFormbyXML(doc, dictionary, 1);
				out
						.write("<span>��Ա������Ϣ</span><form action=\"/db/page/info/familychild/FamilyChildSaveServlet\" enctype=\"multipart/form-data\" id=\"inputForm\" method=\"post\"><table class=\"table1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
								+ doc.getRootElement().asXML()
								+ "</table><p>"
								+ "<span id=\"savebnt\"><input type=\"button\" value=\"����\" onclick=\"save('inputForm')\" />"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;<button onclick=\"parentClose()\">�˳�����������Ϣ</button></span><span id=\"divResult\" style=\"color:red\"></span>"
								+ "</p></form>");
			}else if (nodeName.equals("PAYOUT")) {
			doc = tree.selectEntities(nodeName, new Long(nodeId));
			doc = pv.writeFormbyXML(doc, dictionary, 1);
			out
					.write("<span>��֧ͥ����Ϣ</span><form action=\"/db/page/info/familychild/FamilyChildSaveServlet\" enctype=\"multipart/form-data\" id=\"inputForm\" method=\"post\"><table class=\"table1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
							+ doc.getRootElement().asXML()
							+ "</table><p>"
							+ "<span id=\"savebnt\"><input type=\"button\" value=\"����\" onclick=\"save('inputForm')\" />"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;<button onclick=\"parentClose()\">�˳�����������Ϣ</button></span><span id=\"divResult\" style=\"color:red\"></span>"
							+ "</p></form>");
		}
			else if (nodeName.equals("PAPERS")) {
				doc = tree.selectEntities(nodeName, new Long(nodeId));
				Element parnet = (Element) doc.getRootElement()
						.selectSingleNode("//" + nodeName);
				String title = parnet.attributeValue("title");
				List list = doc.getRootElement().selectNodes(
						"//" + nodeName + "/property[@column='URL']");
				doc = pv.writeListbyXML(doc, dictionary);
				Iterator it = doc.getRootElement().elementIterator();
				int i = 0;
				while (it.hasNext()) {
					Element tr = (Element) it.next();
					if (i == 0) {
						tr.addElement("th");
					} else {
						Node node = (Node) list.get(i - 1);
						String realpath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() +request.getContextPath()+"/viewpic.do?url=";
						String basePath = request.getScheme() + "://"
								+ request.getServerName() + ":"
								+ request.getServerPort() + "/upload/";
						tr.addElement("td").addElement("a").addAttribute("target", "_blank").addAttribute(
								"href", realpath+basePath + node.getText())
								.setText("�鿴");
					}
					i++;
				}
				if (!doc.getRootElement().elements().isEmpty()) {
					out
							.write("<span>"
									+ title
									+ "</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
									+ doc.getRootElement().asXML()
									+ "</table>"
									+ "<p><input type=\"button\" value=\"����\" onclick=\"addNode()\"/><input type=\"button\" value=\"�޸�\" onclick=\"editNode()\"/><input type=\"button\" value=\"ɾ��\" onclick=\"deleteNode()\"/></p>");
				} else {
					out
							.write("<span>"
									+ title
									+ "</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
									+ "<tr><th>�޼�¼<input type=\"hidden\" name=\"pid\" id=\"pid\" value=\""
									+ nodeId
									+ "\"/><input type=\"hidden\" name=\"nodeName\" id=\"nodeName\" value=\""
									+ nodeName
									+ "\"/></th></tr></table>"
									+ "<p><input type=\"button\" value=\"����\" onclick=\"addNode()\"/><input type=\"button\" value=\"�޸�\" onclick=\"editNode()\"/><input type=\"button\" value=\"ɾ��\" onclick=\"deleteNode()\"/></p>");
				}

			} else if (nodeName.equals("ANNEX")) {
				doc = tree.selectEntities(nodeName, new Long(nodeId));
				Element parnet = (Element) doc.getRootElement()
						.selectSingleNode("//" + nodeName);
				String title = parnet.attributeValue("title");
				List list = doc.getRootElement().selectNodes(
						"//" + nodeName + "/property[@column='URL']");
				List list1 = doc.getRootElement().selectNodes(
						"//" + nodeName + "/property[@column='TYPE']");
				doc = pv.writeListbyXML(doc, dictionary);
				Iterator<Element> it = doc.getRootElement().elementIterator();
				int i = 0;
				while (it.hasNext()) {
					Element tr = (Element) it.next();
					if (i == 0) {
						tr.addElement("th");
					} else {
						Node node = (Node) list.get(i - 1);
						Node node1 = (Node) list1.get(i - 1);
						if (node1.getText().equals("1")) {
							String realpath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort() +request.getContextPath()+"/viewpic.do?url=";
							String basePath = request.getScheme() + "://"
									+ request.getServerName() + ":"
									+ request.getServerPort() + "/upload/";
							tr.addElement("td").addElement("a").addAttribute(
									"href",realpath+ basePath + node.getText())
									.addAttribute("target", "_blank").setText(
											"�鿴");
						} else {
							tr.addElement("td").addElement("a").addAttribute(
									"href",
									"/db/page/info/card/view.do?filename="
											+ node.getText()).addAttribute(
									"target", "_blank").setText("�鿴");
						}
					}
					i++;
				}
				if (!doc.getRootElement().elements().isEmpty()) {
					out
							.write("<span>"
									+ title
									+ "</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\"><caption>"
									+ doc.getRootElement().asXML()
									+ "</table>"
									+ "<p><input type=\"button\" value=\"����\" onclick=\"addNode()\"/><input type=\"button\" value=\"�޸�\" onclick=\"editNode()\"/><input type=\"button\" value=\"ɾ��\" onclick=\"deleteNode()\"/></p>");
				} else {
					out
							.write("<span>"
									+ title
									+ "</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
									+ "<tr><th>�޼�¼<input type=\"hidden\" name=\"pid\" id=\"pid\" value=\""
									+ nodeId
									+ "\"/><input type=\"hidden\" name=\"nodeName\" id=\"nodeName\" value=\""
									+ nodeName
									+ "\"/></th></tr></table>"
									+ "<p><input type=\"button\" value=\"����\" onclick=\"addNode()\"/><input type=\"button\" value=\"�޸�\" onclick=\"editNode()\"/><input type=\"button\" value=\"ɾ��\" onclick=\"deleteNode()\"/></p>");
				}
			}else if(nodeName.equals("ASSET")){
				doc = tree.selectEntities(nodeName, new Long(nodeId));
				doc = pv.writeFormbyXML(doc, dictionary, 1);
				out.write("<span>��ͥ�ʲ���Ϣ</span><form action=\"/db/page/info/familychild/FamilyChildSaveServlet\" enctype=\"multipart/form-data\" id=\"inputForm\" method=\"post\"><table class=\"table1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
								+ doc.getRootElement().asXML()
								+ "</table><p>"
								+ "<span id=\"savebnt\"><input type=\"button\" value=\"����\" onclick=\"save('inputForm')\" />"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;<button onclick=\"parentClose()\">�˳�����������Ϣ</button></span><span id=\"divResult\" style=\"color:red\"></span>"
								+ "</p></form>");
			} else {
				doc = tree.selectEntities(nodeName, new Long(nodeId));
				Element parnet = (Element) doc.getRootElement()
						.selectSingleNode("//" + nodeName);
				String title = parnet.attributeValue("title");
				doc = pv.writeListbyXML(doc, dictionary);
				String bnt = "<input type=\"button\" value=\"����\" onclick=\"addNode()\"/>"
						+ "<input type=\"button\" value=\"�޸�\" onclick=\"editNode()\"/>"
						+ "<input type=\"button\" value=\"ɾ��\" onclick=\"deleteNode()\"/>";
				if (nodeName.equals("FAMILYINCOME")
						|| nodeName.equals("MEMBERINCOME")
						|| nodeName.equals("PAYOUT")||nodeName.equals("ASSET")) {
					bnt = "<input type=\"button\" value=\"�޸�\" onclick=\"editNode()\"/>";
				}

				if (!doc.getRootElement().elements().isEmpty()) {
					out
							.write("<span>"
									+ title
									+ "</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
									+ doc.getRootElement().asXML() + "</table>"
									+ "<p>" + bnt + "</p>");
				} else {
					out
							.write("<span>"
									+ title
									+ "</span><table width=\"100%\" class=\"table1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"
									+ "<tr><th>�޼�¼<input type=\"hidden\" name=\"pid\" id=\"pid\" value=\""
									+ nodeId
									+ "\"/><input type=\"hidden\" name=\"nodeName\" id=\"nodeName\" value=\""
									+ nodeName + "\"/></th></tr></table>"
									+ "<p>" + bnt + "</p>");
				}
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			SessionFactory.closeSession();
		}
		return null;
	}
}