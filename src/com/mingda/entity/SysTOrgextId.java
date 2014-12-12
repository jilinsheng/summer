package com.mingda.entity;

import java.math.BigDecimal;

/**
 * SysTOrgextId entity. @author MyEclipse Persistence Tools
 */

public class SysTOrgextId implements java.io.Serializable {

	// Fields

	private SysTOrganization sysTOrganization;
	private BigDecimal infomationtype;

	// Constructors

	/** default constructor */
	public SysTOrgextId() {
	}

	/** full constructor */
	public SysTOrgextId(SysTOrganization sysTOrganization,
			BigDecimal infomationtype) {
		this.sysTOrganization = sysTOrganization;
		this.infomationtype = infomationtype;
	}

	// Property accessors

	public SysTOrganization getSysTOrganization() {
		return this.sysTOrganization;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	public BigDecimal getInfomationtype() {
		return this.infomationtype;
	}

	public void setInfomationtype(BigDecimal infomationtype) {
		this.infomationtype = infomationtype;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysTOrgextId))
			return false;
		SysTOrgextId castOther = (SysTOrgextId) other;

		return ((this.getSysTOrganization() == castOther.getSysTOrganization()) || (this
				.getSysTOrganization() != null
				&& castOther.getSysTOrganization() != null && this
				.getSysTOrganization().equals(castOther.getSysTOrganization())))
				&& ((this.getInfomationtype() == castOther.getInfomationtype()) || (this
						.getInfomationtype() != null
						&& castOther.getInfomationtype() != null && this
						.getInfomationtype().equals(
								castOther.getInfomationtype())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSysTOrganization() == null ? 0 : this
						.getSysTOrganization().hashCode());
		result = 37
				* result
				+ (getInfomationtype() == null ? 0 : this.getInfomationtype()
						.hashCode());
		return result;
	}

}