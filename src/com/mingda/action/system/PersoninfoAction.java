/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTEmployeeDAO;
import com.mingda.entity.SysTEmpext;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 08-27-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class PersoninfoAction extends Action {
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
		Long empextId = new Long(request.getParameter("empextId"));
		Long employeeId = new Long(request.getParameter("employeeId"));
		String workno = request.getParameter("workno");
		String name = request.getParameter("name");
		String paperid = request.getParameter("paperid");
		Long sex = new Long(request.getParameter("sex"));
		Long nation = new Long(request.getParameter("nation"));
		String address = request.getParameter("address");
		String officephone1 = request.getParameter("officephone1");
		String officephone2 = request.getParameter("officephone2");
		String homephone = request.getParameter("homephone");
		String mobilephone = request.getParameter("mobilephone");
		String email = request.getParameter("email");
		String status = request.getParameter("status");
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			SysTEmployeeDAO ext = new SysTEmployeeDAO();
			SysTEmployee emp = ext.findById(employeeId);
			
			SysTEmpext empext = emp.getSysTEmpext();
			empext.setAddress(address);
			empext.setEmail(email);
			empext.setEmpextId(empextId);
			empext.setHomephone(homephone);
			empext.setMobilephone(mobilephone);
			empext.setName(name);
			empext.setNation(nation);
			empext.setOfficephone1(officephone1);
			empext.setOfficephone2(officephone2);
			empext.setPaperid(paperid);
			empext.setSex(sex);
			empext.setStatus(status);
			empext.setWorkno(workno);

			emp.setSysTEmpext(empext);
			ext.save(emp);
			session.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");
		try {
			PrintWriter out = response.getWriter();
			out.write("�������");
			//out.write("<script>window.location.reload('personinfoinit.do');</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapping.findForward("");
	}
}