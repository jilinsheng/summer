package com.mingda.entity;

/**
 * SysTClasstype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTClasstype implements java.io.Serializable {

	// Fields

	private Long classtypeId;
	private Long fieldId;
	private String physql;
	private String logicsql;
	private String explains;
	private String viewname;
	private Long ischeck;
	private Long lev5;
	private Long lev4;
	private Long lev3;
	private Long lev2;
	private Long lev1;
	private Long status;

	// Constructors

	/** default constructor */
	public SysTClasstype() {
	}

	/** full constructor */
	public SysTClasstype(Long fieldId, String physql, String logicsql,
			String explains, String viewname, Long ischeck, Long lev5,
			Long lev4, Long lev3, Long lev2, Long lev1, Long status) {
		this.fieldId = fieldId;
		this.physql = physql;
		this.logicsql = logicsql;
		this.explains = explains;
		this.viewname = viewname;
		this.ischeck = ischeck;
		this.lev5 = lev5;
		this.lev4 = lev4;
		this.lev3 = lev3;
		this.lev2 = lev2;
		this.lev1 = lev1;
		this.status = status;
	}

	// Property accessors

	public Long getClasstypeId() {
		return this.classtypeId;
	}

	public void setClasstypeId(Long classtypeId) {
		this.classtypeId = classtypeId;
	}

	public Long getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getPhysql() {
		return this.physql;
	}

	public void setPhysql(String physql) {
		this.physql = physql;
	}

	public String getLogicsql() {
		return this.logicsql;
	}

	public void setLogicsql(String logicsql) {
		this.logicsql = logicsql;
	}

	public String getExplains() {
		return this.explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getViewname() {
		return this.viewname;
	}

	public void setViewname(String viewname) {
		this.viewname = viewname;
	}

	public Long getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(Long ischeck) {
		this.ischeck = ischeck;
	}

	public Long getLev5() {
		return this.lev5;
	}

	public void setLev5(Long lev5) {
		this.lev5 = lev5;
	}

	public Long getLev4() {
		return this.lev4;
	}

	public void setLev4(Long lev4) {
		this.lev4 = lev4;
	}

	public Long getLev3() {
		return this.lev3;
	}

	public void setLev3(Long lev3) {
		this.lev3 = lev3;
	}

	public Long getLev2() {
		return this.lev2;
	}

	public void setLev2(Long lev2) {
		this.lev2 = lev2;
	}

	public Long getLev1() {
		return this.lev1;
	}

	public void setLev1(Long lev1) {
		this.lev1 = lev1;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}