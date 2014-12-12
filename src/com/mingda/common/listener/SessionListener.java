package com.mingda.common.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mingda.common.log4j.Log4jApp;
import com.mingda.entity.SysTEmployee;

public class SessionListener implements HttpSessionListener,
		HttpSessionAttributeListener {
	private static final String EMPID = "employee";

	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();
		if (EMPID.equals(name)) {
			SysTEmployee employee = (SysTEmployee) event.getSession()
					.getAttribute(EMPID);
			Date date =new Date(event.getSession().getCreationTime());
			StoreFactory.getStore().login(employee ,date,event.getSession().getId());
			Log4jApp.logger("有人登录：" + employee.getAccount());
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		/*
		 * String name = event.getName(); if (EMPID.equals(name)) { SysTEmployee
		 * employee = (SysTEmployee) event.getSession() .getAttribute(EMPID); if
		 * (null != employee) { StoreFactory.getStore().logoff(employee);
		 * Log4jApp.logger("注销：" + employee.getAccount()); } }
		 */

	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		String name = event.getName();
		if (EMPID.equals(name)) {
			SysTEmployee employee = (SysTEmployee) event.getSession()
					.getAttribute(EMPID);
			Date date =new Date(event.getSession().getCreationTime());
			StoreFactory.getStore().login(employee,date ,event.getSession().getId());
			Log4jApp.logger("重新登录：" + employee.getAccount());
		}
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		Log4jApp.logger("Session创建：" + arg0.getSession().getId() + "   创建时间:"
				+ arg0.getSession().getCreationTime());
	}

	/*
	 * session 销毁
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		SysTEmployee employee = (SysTEmployee) arg0.getSession().getAttribute(
				EMPID);
		if (null != employee) {
			Long empid = employee.getEmployeeId();
			Log4jApp.logger("有人登出：" + employee.getSysTEmpext().getName());

			if (empid != null) {
				StoreFactory.getStore().logoff(employee);
			}
		}
	}
}
