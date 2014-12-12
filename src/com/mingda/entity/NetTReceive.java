package com.mingda.entity;

import java.math.BigDecimal;

/**
 * NetTReceive entity. @author MyEclipse Persistence Tools
 */

public class NetTReceive implements java.io.Serializable {

	// Fields

	private Long receiveId;
	private SysTOrganization sysTOrganization;
	private NetTNotice netTNotice;
	private String flag;
	private String content;
	private BigDecimal person;

	// Constructors

	/** default constructor */
	public NetTReceive() {
	}

	/** full constructor */
	public NetTReceive(SysTOrganization sysTOrganization,
			NetTNotice netTNotice, String flag, String content,
			BigDecimal person) {
		this.sysTOrganization = sysTOrganization;
		this.netTNotice = netTNotice;
		this.flag = flag;
		this.content = content;
		this.person = person;
	}

	// Property accessors

	

	public SysTOrganization getSysTOrganization() {
		return this.sysTOrganization;
	}

	public Long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	public NetTNotice getNetTNotice() {
		return this.netTNotice;
	}

	public void setNetTNotice(NetTNotice netTNotice) {
		this.netTNotice = netTNotice;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getPerson() {
		return this.person;
	}

	public void setPerson(BigDecimal person) {
		this.person = person;
	}

}