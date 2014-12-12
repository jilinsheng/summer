package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * NetTNotice entity. @author MyEclipse Persistence Tools
 */

public class NetTNotice implements java.io.Serializable {

	// Fields

	private Long noticeId;
	private String title;
	private String content;
	private String author;
	private String organ;
	private Date issuetime;
	private String state;
	private BigDecimal auditor;
	private BigDecimal noticetype;
	private Set netTReceives = new HashSet(0);

	// Constructors

	/** default constructor */
	public NetTNotice() {
	}

	/** full constructor */
	public NetTNotice(String title, String content, String author,
			String organ, Date issuetime, String state, BigDecimal auditor,
			BigDecimal noticetype, Set netTReceives) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.organ = organ;
		this.issuetime = issuetime;
		this.state = state;
		this.auditor = auditor;
		this.noticetype = noticetype;
		this.netTReceives = netTReceives;
	}

	// Property accessors

	

	public String getTitle() {
		return this.title;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public Date getIssuetime() {
		return this.issuetime;
	}

	public void setIssuetime(Date issuetime) {
		this.issuetime = issuetime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getAuditor() {
		return this.auditor;
	}

	public void setAuditor(BigDecimal auditor) {
		this.auditor = auditor;
	}

	public BigDecimal getNoticetype() {
		return this.noticetype;
	}

	public void setNoticetype(BigDecimal noticetype) {
		this.noticetype = noticetype;
	}

	public Set getNetTReceives() {
		return this.netTReceives;
	}

	public void setNetTReceives(Set netTReceives) {
		this.netTReceives = netTReceives;
	}

}