/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.card;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;

/**
 * MyEclipse Struts Creation date: 06-21-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class FamilyInfoCardTreeAction extends Action {
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
	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// 定义页面返回用的Document
		Document doc = DocumentHelper.createDocument();

		// 设置XML编码
		doc.setXMLEncoding("GB18030");
		Element root = doc.addElement("root");
		String code = request.getParameter("code");
		String objtype = request.getParameter("objtype");
		String objid = request.getParameter("objid");
		String id = request.getParameter("id");// 判断是否回显
		// 取出全局字典ID
		Document dictdoc = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		
		Document treedoc = (Document) servlet.getServletContext()
		.getAttribute("tree");
		TreeHandle th = new TreeHandleImpl(treedoc);
		
		DictionaryHandle dh = new DictionaryHandle();
		// 如果操作节点为实体节点
		if (objtype != null && objtype.equals("ENTITY")) {
			// 取出对应的数据实体
			Element entity = (Element) th.selectSingleEntity(code,
					new Long(objid)).getRootElement().selectSingleNode(code);
			root.addElement("img").addAttribute("class", "s").addAttribute(
					"src", "/db/page/images/tree/s.gif").addAttribute(
					"onClick",
					"javascript:ChangeStatus(this,'" + code + "','ENTITY','"
							+ objid + "');");
			// 家庭ID和显示信息卡片在此操作
			/*
			 * root.addElement("a").addAttribute( "href",
			 * "/db/page/info/card/familyinfocard.do?code=" + entity.getName() +
			 * "&entityId=" + objid) .addAttribute("target",
			 * "operatingzone").addAttribute( "oncontextmenu",
			 * "showMenu('ENTITY','" + objid + "')").setText(
			 * entity.attributeValue("caption"));
			 */
			Element a = root.addElement("span");
			a.addAttribute("onclick", "viewNode('" + entity.getName() + "',"
					+ objid + "," + id + ")");
			String caption = "";
			Iterator tempcit = entity.elementIterator();
			while (tempcit.hasNext()) {
				Element tempc = (Element) tempcit.next();
				if (tempc.attribute("iscaption") != null
						&& tempc.attributeValue("iscaption").equals("1")) {
					if (tempc.attribute("dicsort") != null) {
						caption += dh.getDictsortToValue(dictdoc, tempc
								.getText())
								+ " ";
					} else {
						caption += tempc.getText() + " ";
					}
				}
			}
			a.setText(caption);
			Iterator it = null;
			// 取实体节点的属性子节点信息
			/*
			 * it= entity.elementIterator("property"); // 增加属性子点节点信息到XML中 while
			 * (it.hasNext()) { Element element = (Element) it.next();
			 * log.debug("=====================" +
			 * element.attributeValue("dicsort")); if
			 * (element.attributeValue("dicsort") != null &&
			 * !element.attributeValue("dicsort").equals("-1")) {
			 * DictionaryHandle dh = new DictionaryHandle();
			 * element.setText(dh.getDictsortToValue(dictdoc, element
			 * .getText())); } log.debug(element.getText()); Element li =
			 * curNode.addElement("li").addAttribute("class", "Child");
			 * li.addElement("img").addAttribute("class", "s").addAttribute(
			 * "src", "/db/page/images/tree/s.gif").setText(
			 * element.attributeValue("title") + ":" + element.getText()); }
			 */
			// 取实体节点的结构子节点信息
			Document temp = th.getChild(code);
			if (!temp.getRootElement().elements().isEmpty()) {
				Element curNode = root.addElement("ul");
				it = temp.getRootElement().elementIterator();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					Element li = curNode.addElement("li").addAttribute("class",
							"Closed");
					li.addElement("img").addAttribute("class", "s")
							.addAttribute("src", "/db/page/images/tree/s.gif")
							.addAttribute(
									"onClick",
									"javascript:ChangeStatus(this,'"
											+ element.getName()
											+ "','STRUCT','" + objid + "');");
					a = li.addElement("span");
					a
							.addAttribute("onclick", "getCurrentList('"
									+ element.getName() + "'," + objid + ","
									+ id + ")");
					a.setText(element.attributeValue("title"));
					a.addAttribute("style", "cursor:hand");
				}
			}
		}
		// 如果操作节点为结构节点
		if (objtype != null && objtype.equals("STRUCT")) {
			Element struct = th.getBranch(code).getRootElement().element(code);
			root.addElement("img").addAttribute("class", "s").addAttribute(
					"src", "/db/page/images/tree/s.gif").addAttribute(
					"onClick",
					"javascript:ChangeStatus(this,'" + code + "','STRUCT','"
							+ objid + "');");
			Element a = root.addElement("span");
			a.addAttribute("onclick", "getCurrentList('" + struct.getName()
					+ "'," + objid + "," + id + ")");
			a.setText(struct.attributeValue("title"));

			Element docs = th.selectEntities(code, new Long(objid))
					.getRootElement();
			// 结构节点 是否有值
			if (!docs.selectNodes("//" + code + "/@caption").isEmpty()) {
				Element curNode = root.addElement("ul");
				Iterator it = docs.elementIterator();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					Element li = curNode.addElement("li").addAttribute("class",
							"Closed");
					li
							.addElement("img")
							.addAttribute("class", "s")
							.addAttribute("src", "/db/page/images/tree/s.gif")
							.addAttribute(
									"onClick",
									"javascript:ChangeStatus(this,'"
											+ element.getName()
											+ "','ENTITY','"
											+ element
													.selectSingleNode(
															"property[@isprimary=\"true\"]")
													.getText() + "');");
					a = li.addElement("span");
					String caption = "";
					Iterator tempcit = element.elementIterator();
					while (tempcit.hasNext()) {
						Element tempc = (Element) tempcit.next();
						if (tempc.attribute("iscaption") != null
								&& tempc.attributeValue("iscaption")
										.equals("1")) {
							if (tempc.attribute("dicsort") != null) {
								caption += dh.getDictsortToValue(dictdoc, tempc
										.getText())+" ";
							} else {
								caption += tempc.getText()+" ";
							}
						}
					}
					a.setText(caption);
					a.addAttribute("onclick", "viewNode('"
							+ element.getName()
							+ "',"
							+ element.selectSingleNode(
									"property[@isprimary=\"true\"]").getText()
							+ "," + id + ")");
					a.addAttribute("style", "cursor:hand");
				}
			}
			;
			// 取结构节点的实体子节点信息
			// 增加至页面用的XML
		}
		try {
			response.setCharacterEncoding("GB18030");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			out.write(doc.asXML());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// request.getParameter("parentid");
		return null;
	}
}