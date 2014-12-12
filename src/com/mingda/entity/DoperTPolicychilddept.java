package com.mingda.entity;

import java.math.BigDecimal;

/**
 * DoperTPolicychilddept entity. @author MyEclipse Persistence Tools
 */

public class DoperTPolicychilddept implements java.io.Serializable {

	// Fields

	private BigDecimal policychilddeptId;
	private SysTOrganization sysTOrganization;
	private BigDecimal policychildsqlId;
	private BigDecimal begmatch;
	private BigDecimal endmatch;
	private String status;

	// Constructors

	/** default constructor */
	public DoperTPolicychilddept() {
	}

	/** full constructor */
	public DoperTPolicychilddept(SysTOrganization sysTOrganization,
			BigDecimal policychildsqlId, BigDecimal begmatch,
			BigDecimal endmatch, String status) {
		this.sysTOrganization = sysTOrganization;
		this.policychildsqlId = policychildsqlId;
		this.begmatch = begmatch;
		this.endmatch = endmatch;
		this.status = status;
	}

	// Property accessors

	public BigDecimal getPolicychilddeptId() {
		return this.policychilddeptId;
	}

	public void setPolicychilddeptId(BigDecimal policychilddeptId) {
		this.policychilddeptId = policychilddeptId;
	}

	public SysTOrganization getSysTOrganization() {
		return this.sysTOrganization;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	public BigDecimal getPolicychildsqlId() {
		return this.policychildsqlId;
	}

	public void setPolicychildsqlId(BigDecimal policychildsqlId) {
		this.policychildsqlId = policychildsqlId;
	}

	public BigDecimal getBegmatch() {
		return this.begmatch;
	}

	public void setBegmatch(BigDecimal begmatch) {
		this.begmatch = begmatch;
	}

	public BigDecimal getEndmatch() {
		return this.endmatch;
	}

	public void setEndmatch(BigDecimal endmatch) {
		this.endmatch = endmatch;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}