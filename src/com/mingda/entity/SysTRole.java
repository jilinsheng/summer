package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysTRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTRole implements java.io.Serializable {

	// Fields

	private Long roleId;
	private String code;
	private String name;
	private String detail;
	private Long sequence;
	private String status;
	private Set sysTEmployees = new HashSet(0);
	private Set sysTPrivileges = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysTRole() {
	}

	/** full constructor */
	public SysTRole(String code, String name, String detail, Long sequence,
			String status, Set sysTEmployees, Set sysTPrivileges) {
		this.code = code;
		this.name = name;
		this.detail = detail;
		this.sequence = sequence;
		this.status = status;
		this.sysTEmployees = sysTEmployees;
		this.sysTPrivileges = sysTPrivileges;
	}

	// Property accessors

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set getSysTEmployees() {
		return sysTEmployees;
	}

	public void setSysTEmployees(Set sysTEmployees) {
		this.sysTEmployees = sysTEmployees;
	}

	public Set getSysTPrivileges() {
		return sysTPrivileges;
	}

	public void setSysTPrivileges(Set sysTPrivileges) {
		this.sysTPrivileges = sysTPrivileges;
	}

}