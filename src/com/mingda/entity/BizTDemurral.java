package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BizTDemurral entity. @author MyEclipse Persistence Tools
 */

public class BizTDemurral implements java.io.Serializable {

	// Fields

	private Long demurralId;
	private BizTFamilystatus bizTFamilystatus;
	private BigDecimal publisher;
	private Date publishdate;
	private String approve;
	private String approvetype;
	private String reason;
	private String content;

	// Constructors

	/** default constructor */
	public BizTDemurral() {
	}

	/** full constructor */
	public BizTDemurral(BizTFamilystatus bizTFamilystatus,
			BigDecimal publisher, Date publishdate, String approve,
			String approvetype, String reason, String content) {
		this.bizTFamilystatus = bizTFamilystatus;
		this.publisher = publisher;
		this.publishdate = publishdate;
		this.approve = approve;
		this.approvetype = approvetype;
		this.reason = reason;
		this.content = content;
	}

	// Property accessors

	
	public BizTFamilystatus getBizTFamilystatus() {
		return this.bizTFamilystatus;
	}

	public Long getDemurralId() {
		return demurralId;
	}

	public void setDemurralId(Long demurralId) {
		this.demurralId = demurralId;
	}

	public void setBizTFamilystatus(BizTFamilystatus bizTFamilystatus) {
		this.bizTFamilystatus = bizTFamilystatus;
	}

	public BigDecimal getPublisher() {
		return this.publisher;
	}

	public void setPublisher(BigDecimal publisher) {
		this.publisher = publisher;
	}

	public Date getPublishdate() {
		return this.publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	public String getApprove() {
		return this.approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getApprovetype() {
		return this.approvetype;
	}

	public void setApprovetype(String approvetype) {
		this.approvetype = approvetype;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}