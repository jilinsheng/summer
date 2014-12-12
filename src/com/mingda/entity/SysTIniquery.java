package com.mingda.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SysTIniquery entity. @author MyEclipse Persistence Tools
 */

public class SysTIniquery implements java.io.Serializable {

	// Fields

	private BigDecimal iniqueryId;
	private String name;
	private BigDecimal status;
	private BigDecimal employeeId;
	private String code;
	private String qxml;
	private Set sysTIniqueryitems = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysTIniquery() {
	}

	/** full constructor */
	public SysTIniquery(String name, BigDecimal status, BigDecimal employeeId,
			String code, String qxml, Set sysTIniqueryitems) {
		this.name = name;
		this.status = status;
		this.employeeId = employeeId;
		this.code = code;
		this.qxml = qxml;
		this.sysTIniqueryitems = sysTIniqueryitems;
	}

	// Property accessors

	public BigDecimal getIniqueryId() {
		return this.iniqueryId;
	}

	public void setIniqueryId(BigDecimal iniqueryId) {
		this.iniqueryId = iniqueryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQxml() {
		return this.qxml;
	}

	public void setQxml(String qxml) {
		this.qxml = qxml;
	}

	public Set getSysTIniqueryitems() {
		return this.sysTIniqueryitems;
	}

	public void setSysTIniqueryitems(Set sysTIniqueryitems) {
		this.sysTIniqueryitems = sysTIniqueryitems;
	}

}