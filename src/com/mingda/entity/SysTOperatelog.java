package com.mingda.entity;

import java.util.Date;

/**
 * SysTOperatelog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTOperatelog implements java.io.Serializable {

	// Fields

	private Long operatelogId;
	private SysTEmployee sysTEmployee;
	private String logsort;
	private String content;
	private Date operatetime;
	private String ipaddress;
	private String serverip;

	// Constructors

	/** default constructor */
	public SysTOperatelog() {
	}

	/** full constructor */
	public SysTOperatelog(SysTEmployee sysTEmployee, String logsort,
			String content, Date operatetime, String ipaddress) {
		this.sysTEmployee = sysTEmployee;
		this.logsort = logsort;
		this.content = content;
		this.operatetime = operatetime;
		this.ipaddress = ipaddress;
	}

	// Property accessors

	public Long getOperatelogId() {
		return this.operatelogId;
	}

	public void setOperatelogId(Long operatelogId) {
		this.operatelogId = operatelogId;
	}

	public SysTEmployee getSysTEmployee() {
		return this.sysTEmployee;
	}

	public void setSysTEmployee(SysTEmployee sysTEmployee) {
		this.sysTEmployee = sysTEmployee;
	}

	public String getLogsort() {
		return this.logsort;
	}

	public void setLogsort(String logsort) {
		this.logsort = logsort;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public String getServerip() {
		return serverip;
	}

}