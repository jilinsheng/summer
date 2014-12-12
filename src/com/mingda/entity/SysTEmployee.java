package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysTEmployee entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTEmployee implements java.io.Serializable {

	// Fields

	private Long employeeId;
	private SysTPost sysTPost;
	private SysTOrganization sysTOrganization;
	private String account;
	private String password;
	private String isadmin;
	private Set sysTOperatelogs = new HashSet(0);
	private Set sysTRoles = new HashSet(0);
	private String line ;
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	private SysTEmpext sysTEmpext;

	// Constructors

	/** default constructor */
	public SysTEmployee() {
	}

	/** full constructor */
	public SysTEmployee(SysTPost sysTPost, SysTOrganization sysTOrganization,
			String account, String password, String isadmin,
			Set sysTOperatelogs, Set sysTRoles, SysTEmpext sysTEmpext) {
		this.sysTPost = sysTPost;
		this.sysTOrganization = sysTOrganization;
		this.account = account;
		this.password = password;
		this.isadmin = isadmin;
		this.sysTOperatelogs = sysTOperatelogs;
		this.sysTRoles = sysTRoles;
		this.sysTEmpext = sysTEmpext;
	}

	// Property accessors

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public SysTPost getSysTPost() {
		return this.sysTPost;
	}

	public void setSysTPost(SysTPost sysTPost) {
		this.sysTPost = sysTPost;
	}

	public SysTOrganization getSysTOrganization() {
		return this.sysTOrganization;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsadmin() {
		return this.isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public Set getSysTOperatelogs() {
		return this.sysTOperatelogs;
	}

	public void setSysTOperatelogs(Set sysTOperatelogs) {
		this.sysTOperatelogs = sysTOperatelogs;
	}

	public Set getSysTRoles() {
		return sysTRoles;
	}

	public void setSysTRoles(Set sysTRoles) {
		this.sysTRoles = sysTRoles;
	}

	public SysTEmpext getSysTEmpext() {
		return sysTEmpext;
	}

	public void setSysTEmpext(SysTEmpext sysTEmpext) {
		this.sysTEmpext = sysTEmpext;
	}
}