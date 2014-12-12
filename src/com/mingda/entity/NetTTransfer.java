package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * NetTTransfer entity. @author MyEclipse Persistence Tools
 */

public class NetTTransfer implements java.io.Serializable {

	// Fields

	private Long transferId;
	private String title;
	private BigDecimal owner;
	private String content;
	private BigDecimal receiver;
	private String flag;
	private Date transfertime;

	// Constructors

	/** default constructor */
	public NetTTransfer() {
	}

	/** full constructor */
	public NetTTransfer(String title, BigDecimal owner, String content,
			BigDecimal receiver, String flag, Date transfertime) {
		this.title = title;
		this.owner = owner;
		this.content = content;
		this.receiver = receiver;
		this.flag = flag;
		this.transfertime = transfertime;
	}

	// Property accessors

	public Long getTransferId() {
		return this.transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getOwner() {
		return this.owner;
	}

	public void setOwner(BigDecimal owner) {
		this.owner = owner;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getReceiver() {
		return this.receiver;
	}

	public void setReceiver(BigDecimal receiver) {
		this.receiver = receiver;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getTransfertime() {
		return this.transfertime;
	}

	public void setTransfertime(Date transfertime) {
		this.transfertime = transfertime;
	}

}