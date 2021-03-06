/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.dao.SysTEmployeeDAO;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 08-27-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="personinfo" path="/page/system/personinfo.jsp"
 */
public class PersoninfoinitAction extends Action {
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
		HttpSession hsession = request.getSession();
		Session session = SessionFactory.getSession();
		SysTEmployee employee = (SysTEmployee) hsession
				.getAttribute("employee");
		Long empid = employee.getEmployeeId();
		SysTEmployeeDAO empDAO = new SysTEmployeeDAO();
		employee = empDAO.findById(empid);
		// 民族
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		Log4jApp.logger("字典表xml:"+dictionary.asXML());
		DictionaryHandle dh = new DictionaryHandle();
		String nation = "";
		if (employee.getSysTEmpext().getNation() == null) {
			nation = "";
		} else {
			nation = employee.getSysTEmpext().getNation().toString();
		}
		Log4jApp.logger(dh.getDictsortToOption(dictionary, "120", nation));
		request.setAttribute("nations",  dh.getDictsortToOption(dictionary, "120", nation));
		if (employee.getSysTEmpext().getSex() == null) {
			nation = "";
		} else {
			nation = employee.getSysTEmpext().getSex().toString();
		}
		Log4jApp.logger(dh.getDictsortToOption(dictionary, "1", nation));
		request.setAttribute("sexs",  dh.getDictsortToOption(dictionary, "1", nation));
		// 民族
		session.flush();
		session.close();
		request.setAttribute("employee", employee);
		return mapping.findForward("personinfo");
	}
}