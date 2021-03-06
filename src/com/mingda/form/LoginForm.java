/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * MyEclipse Struts Creation date: 04-14-2008
 * 
 * XDoclet definition:
 * 
 * @struts.form name="loginForm"
 */
public class LoginForm extends ActionForm {
	/*
	 * Generated fields
	 */

	private String user;

	/** pass property */
	private String pass;

	/*
	 * Generated Methods
	 */
	private String select;

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (user == null || user.trim().equals("")
				|| user.trim().indexOf("'") >= 0
				|| user.trim().indexOf("\"") >= 0) {
			errors.add("account", new ActionMessage("login.usernamenull"));
		} else if (pass == null || pass.trim().equals("")
				|| pass.trim().indexOf("'") >= 0
				|| pass.trim().indexOf("\"") >= 0) {
			errors.add("password", new ActionMessage("login.passwordnull"));
		}
		return errors;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.user = null;
		this.pass = null;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * Returns the password.
	 * 
	 * @return String
	 */

	/**
	 * Set the password.
	 * 
	 * @param password
	 *            The password to set
	 */
	

	/**
	 * Returns the username.
	 * 
	 * @return String
	 */
	

	/**
	 * Set the username.
	 * 
	 * @param username
	 *            The username to set
	 */
	
}