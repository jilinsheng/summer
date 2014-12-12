/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.implemention;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.implemention.BillmonthHandle;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 11-17-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class BillmonthsaveAction extends Action {
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
		if (!this.isTokenValid(request)) {
			return mapping.findForward("error");
		}
		this.resetToken(request);
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		String monthname = request.getParameter("monthname");
		String monthhidden = request.getParameter("monthhidden");
		String monthid = request.getParameter("monthid");
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			BillmonthHandle bh = new BillmonthHandle();
			String monthtemp = "";
			monthname = monthname.substring(0, 4) + monthname.substring(5, 7);
			String[] monthids = null;
			if (null == monthhidden || "".equals(monthhidden)) {
			} else {
				monthids = monthhidden.split(",");
				for (int i = 0; i < monthids.length; i++) {
					monthtemp = monthtemp + "'" + monthids[i] + "'";
					if (i == monthids.length - 1) {
					} else {
						monthtemp = monthtemp + ",";
					}
				}
				// 新建实施月份
				if (null == monthid || "".equals(monthid)) {
					monthid =bh.addMonth(employee, monthname, monthid, monthtemp);
				}
			}
			tx.commit();
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		ActionForward forward =new ActionForward("/page/implemention/billcreatefileview.do?monthid="+monthid);
		return forward;
	}
}