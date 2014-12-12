package com.mingda.action.report;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;
import com.mingda.common.implemention.AccMonth;
import com.mingda.common.implemention.BillmonthHandle;

public class JasperInitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Session session = SessionFactory.getSession();
		BillmonthHandle billmonthhandle = new BillmonthHandle();
		try {

			List<AccMonth> list = billmonthhandle.getAccMonth("", "");
			request.setAttribute("monlist", list);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return mapping.findForward("jasperreprot");
	}

}
