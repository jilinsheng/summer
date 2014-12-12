package com.mingda.entity;

import java.math.BigDecimal;

/**
 * ImplTAccoptcheck entity. @author MyEclipse Persistence Tools
 */

public class ImplTAccoptcheck implements java.io.Serializable {

	// Fields

	private BigDecimal accoptcheckId;
	private DoperTPolicy doperTPolicy;
	private BizTOptacc bizTOptacc;
	private BigDecimal familyId;
	private BigDecimal memberId;
	private Double countmoney;
	private Double checkmoney;
	private Double checkchildmoney;

	// Constructors

	/** default constructor */
	public ImplTAccoptcheck() {
	}

	/** full constructor */
	public ImplTAccoptcheck(DoperTPolicy doperTPolicy, BizTOptacc bizTOptacc,
			BigDecimal familyId, BigDecimal memberId, Double countmoney,
			Double checkmoney, Double checkchildmoney) {
		this.doperTPolicy = doperTPolicy;
		this.bizTOptacc = bizTOptacc;
		this.familyId = familyId;
		this.memberId = memberId;
		this.countmoney = countmoney;
		this.checkmoney = checkmoney;
		this.checkchildmoney = checkchildmoney;
	}

	// Property accessors

	public BigDecimal getAccoptcheckId() {
		return this.accoptcheckId;
	}

	public void setAccoptcheckId(BigDecimal accoptcheckId) {
		this.accoptcheckId = accoptcheckId;
	}

	public DoperTPolicy getDoperTPolicy() {
		return this.doperTPolicy;
	}

	public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
		this.doperTPolicy = doperTPolicy;
	}

	public BizTOptacc getBizTOptacc() {
		return this.bizTOptacc;
	}

	public void setBizTOptacc(BizTOptacc bizTOptacc) {
		this.bizTOptacc = bizTOptacc;
	}

	public BigDecimal getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(BigDecimal familyId) {
		this.familyId = familyId;
	}

	public BigDecimal getMemberId() {
		return this.memberId;
	}

	public void setMemberId(BigDecimal memberId) {
		this.memberId = memberId;
	}

	public Double getCountmoney() {
		return this.countmoney;
	}

	public void setCountmoney(Double countmoney) {
		this.countmoney = countmoney;
	}

	public Double getCheckmoney() {
		return this.checkmoney;
	}

	public void setCheckmoney(Double checkmoney) {
		this.checkmoney = checkmoney;
	}

	public Double getCheckchildmoney() {
		return this.checkchildmoney;
	}

	public void setCheckchildmoney(Double checkchildmoney) {
		this.checkchildmoney = checkchildmoney;
	}

}