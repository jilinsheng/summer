package com.mingda.entity;

import java.util.Date;

/**
 * BizTValidatelog entity. @author MyEclipse Persistence Tools
 */

public class BizTValidatelog implements java.io.Serializable {

	// Fields

	private Long validatelogId;
	private BizTValidateterm bizTValidateterm;
	private String content;
	private Date valtime;
	private String status;
	private Long familyId;

	// Constructors

	/** default constructor */
	public BizTValidatelog() {
	}

	/** full constructor */
	public BizTValidatelog(BizTValidateterm bizTValidateterm, String content,
			Date valtime, String status, Long familyId) {
		this.bizTValidateterm = bizTValidateterm;
		this.content = content;
		this.valtime = valtime;
		this.status = status;
		this.familyId = familyId;
	}

	// Property accessors

	public Long getValidatelogId() {
		return this.validatelogId;
	}

	public void setValidatelogId(Long validatelogId) {
		this.validatelogId = validatelogId;
	}

	public BizTValidateterm getBizTValidateterm() {
		return this.bizTValidateterm;
	}

	public void setBizTValidateterm(BizTValidateterm bizTValidateterm) {
		this.bizTValidateterm = bizTValidateterm;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getValtime() {
		return this.valtime;
	}

	public void setValtime(Date valtime) {
		this.valtime = valtime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

}