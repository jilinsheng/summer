package com.mingda.action.info.editor;
import java.util.Iterator;
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
import com.mingda.common.ConstantDefine;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTEmployee;
public class AddFamilyBaseAction extends Action {
	
	static Logger log = Logger.getLogger(AddFamilyBaseAction.class);

	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String rname = (String) request.getParameter("rname");
		String paperid = (String) request.getParameter("paperid");
		String pt = (String) request.getParameter("pt");
		Document dictionary = (Document) servlet.getServletContext()
				.getAttribute("dictionary");
		HttpSession session = request.getSession();

		SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
		String organizationid = employee.getSysTOrganization()
				.getOrganizationId();
		String fullname = employee.getSysTOrganization().getFullname();

		// 从服务器中取出结构xml
		Document treedoc = (Document) servlet.getServletContext().getAttribute(
				"tree");
		TreeHandle tree = new TreeHandleImpl(treedoc);

		PageView pv = new PageView();

		Document family = tree.selectSingleEntity("FAMILY", null);
		log.debug(family.asXML());
		// 删除家庭中计算字段
		Iterator famit = family.selectNodes("//FAMILY/property").iterator();
		while (famit.hasNext()) {
			Element el = (Element) famit.next();
			// log.debug(el.asXML());
			if (el.attribute("computeflag") != null
					&& "1".equals(el.attributeValue("computeflag"))) {
				el.getParent().remove(el);
			}
		}
		// 家庭相关机构的信息
		Element o = (Element) family
				.selectSingleNode("//FAMILY/property[@column='ORGANIZATION_ID']");
		o.setText(organizationid);

		o = (Element) family
				.selectSingleNode("//FAMILY/property[@column='RPRADDRESS']");
		o.setText(fullname);

		o = (Element) family
				.selectSingleNode("//FAMILY/property[@column='FAMADDRESS']");
		o.setText(fullname);

		o = (Element) family
				.selectSingleNode("//FAMILY/property[@column='STATUS']");
		o.setText("1");
		// 家庭相关机构的信息
		family = pv.writeFormbyXML(family, dictionary, 1);
		Element tr = family.getRootElement().addElement("tr");
		tr.addElement("th").setText("户主姓名");
		tr.addElement("td").addElement("input").addAttribute("type", "text")
				.addAttribute("name", "MEMBER/MEMBERNAME").addAttribute(
						"value", rname).addAttribute("style", "width:160px");
		tr = family.getRootElement().addElement("tr");
		tr.addElement("th").setText("证件号");
		tr.addElement("td").addElement("input").addAttribute("type", "text")
				.addAttribute("name", "MEMBER/PAPERID").addAttribute(
						"readonly", "true").addAttribute("value", paperid)
				.addAttribute("style", "width:160px");
		tr = family.getRootElement().addElement("input").addAttribute("type",
				"hidden").addAttribute("value", "").addAttribute("name",
				"MEMBER/FAMILY_ID");
		tr = family.getRootElement().addElement("input").addAttribute("type",
				"hidden").addAttribute("value", ConstantDefine.RELMASTER)
				.addAttribute("name", "MEMBER/RELMASTER");
		tr = family.getRootElement().addElement("input").addAttribute("type",
				"hidden").addAttribute("value", pt).addAttribute("id",
				"MEMBER/PAPERTYPE").addAttribute("name", "MEMBER/PAPERTYPE");
		if (pt.equals(ConstantDefine.CARDID)) {
			tr = family.getRootElement().addElement("input").addAttribute(
					"type", "hidden").addAttribute("value",
					pv.convertPaperidToBirth(paperid)).addAttribute("id",
					"MEMBER/BIRTHDAY").addAttribute("name", "MEMBER/BIRTHDAY");
			tr = family.getRootElement().addElement("input").addAttribute(
					"type", "hidden").addAttribute("value",
					new Long(pv.getAgeByIdCard(paperid)).toString())
					.addAttribute("id", "MEMBER/AGE").addAttribute("name",
							"MEMBER/AGE");
		}
		request.setAttribute("familyhtml", family.getRootElement().asXML());
		// SessionFactory.closeSession();
		return mapping.findForward("familysave");
	}
}