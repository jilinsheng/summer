/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.organ;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.dao.BizTOptreviewpersonDAO;
import com.mingda.entity.BizTOptreviewperson;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 04-17-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class OperpersoninitAction extends Action {
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");

		DictionaryHandle dh = new DictionaryHandle();

		String type = request.getParameter("type");
		String personid = request.getParameter("personid");

		HttpSession httpsession = request.getSession();

		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		

		Session session = SessionFactory.getSession();

		Transaction tx = session.beginTransaction();

		BizTOptreviewpersonDAO bizTOptreviewpersonDAO = new BizTOptreviewpersonDAO();
		BizTOptreviewperson bizTOptreviewperson = new BizTOptreviewperson();

		Long optreviewpersonId = null;
		String name = "";
		BigDecimal sex = null;
		BigDecimal face = null;
		String officephone = "";
		String officename = "";
		String post = "";
		String address = "";
		String title= "";
		if(employee.getSysTOrganization().getOrganizationId().length()==10){
			title="������";
		}else{
			title="������";
		}
		
		try {
			if (null == personid || "".equals(personid)) {
				request.setAttribute("str", "�½�"+title+"Ա");
			} else {
				if ("M".equals(type)) {
					bizTOptreviewperson = bizTOptreviewpersonDAO
							.findById(new Long(personid));
					request.setAttribute("str", "��ǰ�޸ĵ�"+title+"ԱΪ  "
							+ bizTOptreviewperson.getName() + "");
					request.setAttribute("optreviewpersonId", bizTOptreviewperson.getOptreviewpersonId());
				} else if ("D".equals(type)) {
					bizTOptreviewperson.setOptreviewpersonId(new Long(
							personid));
					bizTOptreviewpersonDAO.delete(bizTOptreviewperson);
				} else {
					request.setAttribute("str", "�½�"+title+"Ա");
				}
			}

			Element dict = null;

			optreviewpersonId = bizTOptreviewperson.getOptreviewpersonId();
			name = bizTOptreviewperson.getName();
			sex = bizTOptreviewperson.getSex();
			face = bizTOptreviewperson.getFace();
			officephone = bizTOptreviewperson.getOfficephone();
			officename = bizTOptreviewperson.getOfficename();
			post = bizTOptreviewperson.getPost();
			address = bizTOptreviewperson.getAddress();

			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("table")
					.addAttribute("class", "table8").addAttribute(
							"cellpadding", "0")
					.addAttribute("cellspacing", "0").addAttribute("border",
							"0");

			Element tr1 = root.addElement("tr");
			tr1.addElement("th").addAttribute("width", "120").setText("����");
			tr1.addElement("td").addAttribute("width", "200").addElement("input").addAttribute("name",
					"name").addAttribute("type", "text").addAttribute("value",
					name);
			tr1.addElement("th").addAttribute("width", "120").setText("�Ա�");
			dict = dh.getDictsortToXML(dictionary, "1", sex);
			dict.addAttribute("name", "sex");
			tr1.addElement("td").addAttribute("width", "200").add(
					(Element) dict.clone());

			Element tr2 = root.addElement("tr");
			tr2.addElement("th").setText("������ò");
			dict = dh.getDictsortToXML(dictionary, "182", face);
			dict.addAttribute("name", "face");
			tr2.addElement("td").addAttribute("width", "160").add(
					(Element) dict.clone());
			tr2.addElement("th").addAttribute("width", "100").setText("��λ");
			dict = dh.getDictsortToXML(dictionary, "362", post);
			dict.addAttribute("name", "post");
			tr2.addElement("td").addAttribute("width", "160").add(
					(Element) dict.clone());

			Element tr3 = root.addElement("tr");
			tr3.addElement("th").setText("��λ����");
			tr3.addElement("td").addElement("input").addAttribute("name",
					"officename").addAttribute("type", "text").addAttribute(
					"value", officename);
			tr3.addElement("th").addAttribute("width", "100").setText("�칫�绰");
			tr3.addElement("td").addElement("input").addAttribute("name",
					"officephone").addAttribute("type", "text").addAttribute("value",
							officephone);
			
			//Element tr4 = root.addElement("tr");
			//tr4.addElement("th").setText("��ַ");
			//tr4.addElement("td").addAttribute("colspan", "3").addElement("input").addAttribute("name",
				//	"address").addAttribute("type", "text").addAttribute(
				//	"value", address);

			List<BizTOptreviewperson> list = bizTOptreviewpersonDAO
					.findByOrganizationId(employee.getSysTOrganization()
							.getOrganizationId());
			request.setAttribute("operpesons", list);
			request.setAttribute("pagehtml",root.asXML());
			request.setAttribute("title",title);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();

		}
		return mapping.findForward("operperson");
	}
}