package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ImplTMonth entity. @author MyEclipse Persistence Tools
 */

public class ImplTMonth implements java.io.Serializable {

	// Fields

	private BigDecimal monthId;
	private String monname;
	private String detail;
	private BigDecimal employeeId;
	private Date opttime;
	private Set implTBills = new HashSet(0);
	private Set bizTOptaccs = new HashSet(0);
	private SysTOrganization sysTOrganization;

	// Constructors

	public SysTOrganization getSysTOrganization() {
		return sysTOrganization;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	/** default constructor */
	public ImplTMonth() {
	}

	/** full constructor */
	// Property accessors
	public BigDecimal getMonthId() {
		return this.monthId;
	}

	public void setMonthId(BigDecimal monthId) {
		this.monthId = monthId;
	}

	public String getMonname() {
		return this.monname;
	}

	public void setMonname(String monname) {
		this.monname = monname;
	}
	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public Date getOpttime() {
		return this.opttime;
	}

	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

	public Set getImplTBills() {
		return this.implTBills;
	}

	public void setImplTBills(Set implTBills) {
		this.implTBills = implTBills;
	}

	public Set getBizTOptaccs() {
		return bizTOptaccs;
	}

	public void setBizTOptaccs(Set bizTOptaccs) {
		this.bizTOptaccs = bizTOptaccs;
	}

}