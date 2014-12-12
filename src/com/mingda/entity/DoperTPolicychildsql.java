package com.mingda.entity;

import java.math.BigDecimal;

/**
 * DoperTPolicychildsql entity. @author MyEclipse Persistence Tools
 */

public class DoperTPolicychildsql implements java.io.Serializable {

	// Fields

	private BigDecimal policychildsqlId;
	private BigDecimal policychildId;
	private String name;
	private String sqldesc;
	private String physql;
	private String locsql;
	private String status;

	// Constructors

	/** default constructor */
	public DoperTPolicychildsql() {
	}

	/** full constructor */
	public DoperTPolicychildsql(BigDecimal policychildId, String name,
			String sqldesc, String physql, String locsql, String status) {
		this.policychildId = policychildId;
		this.name = name;
		this.sqldesc = sqldesc;
		this.physql = physql;
		this.locsql = locsql;
		this.status = status;
	}

	// Property accessors

	public BigDecimal getPolicychildsqlId() {
		return this.policychildsqlId;
	}

	public void setPolicychildsqlId(BigDecimal policychildsqlId) {
		this.policychildsqlId = policychildsqlId;
	}

	public BigDecimal getPolicychildId() {
		return this.policychildId;
	}

	public void setPolicychildId(BigDecimal policychildId) {
		this.policychildId = policychildId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSqldesc() {
		return this.sqldesc;
	}

	public void setSqldesc(String sqldesc) {
		this.sqldesc = sqldesc;
	}

	public String getPhysql() {
		return this.physql;
	}

	public void setPhysql(String physql) {
		this.physql = physql;
	}

	public String getLocsql() {
		return this.locsql;
	}

	public void setLocsql(String locsql) {
		this.locsql = locsql;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}