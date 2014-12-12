package com.mingda.entity;

import java.math.BigDecimal;

/**
 * DoperTPolicymenu entity. @author MyEclipse Persistence Tools
 */

public class DoperTPolicymenu implements java.io.Serializable {

	// Fields

	private BigDecimal policymenuId;
	private DoperTPolicy doperTPolicy;
	private SysTPrivilege sysTPrivilege;

	// Constructors

	/** default constructor */
	public DoperTPolicymenu() {
	}

	/** full constructor */
	public DoperTPolicymenu(DoperTPolicy doperTPolicy,
			SysTPrivilege sysTPrivilege) {
		this.doperTPolicy = doperTPolicy;
		this.sysTPrivilege = sysTPrivilege;
	}

	// Property accessors

	public BigDecimal getPolicymenuId() {
		return this.policymenuId;
	}

	public void setPolicymenuId(BigDecimal policymenuId) {
		this.policymenuId = policymenuId;
	}

	public DoperTPolicy getDoperTPolicy() {
		return this.doperTPolicy;
	}

	public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
		this.doperTPolicy = doperTPolicy;
	}

	public SysTPrivilege getSysTPrivilege() {
		return this.sysTPrivilege;
	}

	public void setSysTPrivilege(SysTPrivilege sysTPrivilege) {
		this.sysTPrivilege = sysTPrivilege;
	}

}