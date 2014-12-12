package com.mingda.entity;

/**
 * SysTUnit entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTUnit implements java.io.Serializable {

	// Fields

	private Long unitId;
	private String unitname;
	private String address;
	private Long runstate;
	private Long industry;
	private Long unittype;
	private String unitflag;
	private String detail;

	// Constructors

	/** default constructor */
	public SysTUnit() {
	}

	/** full constructor */
	public SysTUnit(String unitname, String address, Long runstate,
			Long industry, Long unittype, String unitflag, String detail) {
		this.unitname = unitname;
		this.address = address;
		this.runstate = runstate;
		this.industry = industry;
		this.unittype = unittype;
		this.unitflag = unitflag;
		this.detail = detail;
	}

	// Property accessors

	public Long getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getRunstate() {
		return this.runstate;
	}

	public void setRunstate(Long runstate) {
		this.runstate = runstate;
	}

	public Long getIndustry() {
		return this.industry;
	}

	public void setIndustry(Long industry) {
		this.industry = industry;
	}

	public Long getUnittype() {
		return this.unittype;
	}

	public void setUnittype(Long unittype) {
		this.unittype = unittype;
	}

	public String getUnitflag() {
		return this.unitflag;
	}

	public void setUnitflag(String unitflag) {
		this.unitflag = unitflag;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}