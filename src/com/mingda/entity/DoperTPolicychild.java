package com.mingda.entity;

import java.math.BigDecimal;

/**
 * DoperTPolicychild entity. @author MyEclipse Persistence Tools
 */

public class DoperTPolicychild implements java.io.Serializable {

	// Fields

	private BigDecimal policychildId;
	private BigDecimal policyId;
	private String name;
	private String policydesc;
	private String sqltype;
	private String status;
	private String moneytype;

	// Constructors

	/** default constructor */
	public DoperTPolicychild() {
	}

	/** full constructor */
	public DoperTPolicychild(BigDecimal policyId, String name,
			String policydesc, String sqltype, String status, String moneytype) {
		this.policyId = policyId;
		this.name = name;
		this.policydesc = policydesc;
		this.sqltype = sqltype;
		this.status = status;
		this.moneytype = moneytype;
	}

	// Property accessors

	public BigDecimal getPolicychildId() {
		return this.policychildId;
	}

	public void setPolicychildId(BigDecimal policychildId) {
		this.policychildId = policychildId;
	}

	public BigDecimal getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(BigDecimal policyId) {
		this.policyId = policyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPolicydesc() {
		return this.policydesc;
	}

	public void setPolicydesc(String policydesc) {
		this.policydesc = policydesc;
	}

	public String getSqltype() {
		return this.sqltype;
	}

	public void setSqltype(String sqltype) {
		this.sqltype = sqltype;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMoneytype() {
		return this.moneytype;
	}

	public void setMoneytype(String moneytype) {
		this.moneytype = moneytype;
	}

}