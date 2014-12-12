package com.mingda.entity;

import java.util.Date;

/**
 * SysTInfolog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTInfolog implements java.io.Serializable {

	// Fields

	private Long infologId;
	private SysTEmployee sysTEmployee;
	private Date logtime;
	private Long familyid;
	private String code;
	private Long entityid;
	private String log;

	// Constructors

	/** default constructor */
	public SysTInfolog() {
	}

	/** full constructor */
	public SysTInfolog(SysTEmployee sysTEmployee, Date logtime, Long familyid,
			String code, Long entityid, String log) {
		this.sysTEmployee = sysTEmployee;
		this.logtime = logtime;
		this.familyid = familyid;
		this.code = code;
		this.entityid = entityid;
		this.log = log;
	}

	// Property accessors

	public Long getInfologId() {
		return this.infologId;
	}

	public void setInfologId(Long infologId) {
		this.infologId = infologId;
	}

	public SysTEmployee getSysTEmployee() {
		return this.sysTEmployee;
	}

	public void setSysTEmployee(SysTEmployee sysTEmployee) {
		this.sysTEmployee = sysTEmployee;
	}

	public Date getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	public Long getFamilyid() {
		return this.familyid;
	}

	public void setFamilyid(Long familyid) {
		this.familyid = familyid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getEntityid() {
		return this.entityid;
	}

	public void setEntityid(Long entityid) {
		this.entityid = entityid;
	}

	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}