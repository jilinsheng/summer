package com.mingda.entity;

import java.math.BigDecimal;

/**
 * DoperTStandarddept entity. @author MyEclipse Persistence Tools
 */

public class DoperTStandarddept implements java.io.Serializable {

	// Fields

	private BigDecimal standarddeptId;
	private SysTOrganization sysTOrganization;
	private DoperTStandard doperTStandard;
	private Double checkmoney;
	private String checkdesc;
	private String accexp;
	private String accdesc;
	private Boolean flag;
	private String accexpphysql;
	private String accexplocsql;

	// Constructors

	/** default constructor */
	public DoperTStandarddept() {
	}

	/** full constructor */
	public DoperTStandarddept(SysTOrganization sysTOrganization,
			DoperTStandard doperTStandard, Double checkmoney, String checkdesc,
			String accexp, String accdesc, Boolean flag, String accexpphysql,
			String accexplocsql) {
		this.sysTOrganization = sysTOrganization;
		this.doperTStandard = doperTStandard;
		this.checkmoney = checkmoney;
		this.checkdesc = checkdesc;
		this.accexp = accexp;
		this.accdesc = accdesc;
		this.flag = flag;
		this.accexpphysql = accexpphysql;
		this.accexplocsql = accexplocsql;
	}

	// Property accessors

	public BigDecimal getStandarddeptId() {
		return this.standarddeptId;
	}

	public void setStandarddeptId(BigDecimal standarddeptId) {
		this.standarddeptId = standarddeptId;
	}

	public SysTOrganization getSysTOrganization() {
		return this.sysTOrganization;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	public DoperTStandard getDoperTStandard() {
		return this.doperTStandard;
	}

	public void setDoperTStandard(DoperTStandard doperTStandard) {
		this.doperTStandard = doperTStandard;
	}

	public Double getCheckmoney() {
		return this.checkmoney;
	}

	public void setCheckmoney(Double checkmoney) {
		this.checkmoney = checkmoney;
	}

	public String getCheckdesc() {
		return this.checkdesc;
	}

	public void setCheckdesc(String checkdesc) {
		this.checkdesc = checkdesc;
	}

	public String getAccexp() {
		return this.accexp;
	}

	public void setAccexp(String accexp) {
		this.accexp = accexp;
	}

	public String getAccdesc() {
		return this.accdesc;
	}

	public void setAccdesc(String accdesc) {
		this.accdesc = accdesc;
	}

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getAccexpphysql() {
		return this.accexpphysql;
	}

	public void setAccexpphysql(String accexpphysql) {
		this.accexpphysql = accexpphysql;
	}

	public String getAccexplocsql() {
		return this.accexplocsql;
	}

	public void setAccexplocsql(String accexplocsql) {
		this.accexplocsql = accexplocsql;
	}

}