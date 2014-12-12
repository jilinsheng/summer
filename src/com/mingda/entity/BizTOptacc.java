package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BizTOptacc entity. @author MyEclipse Persistence Tools
 */

public class BizTOptacc implements java.io.Serializable {

	// Fields

	private BigDecimal optaccId;
	private DoperTPolicy doperTPolicy;
	private SysTOrganization sysTOrganization;
	private String accyear;
	private String accmonth;
	private String accdesc;
	private Date accbegdt;
	private Date accenddt;
	private Date accexedt;
	private BigDecimal accexeoper;
	private Date accexeoverdt;
	private String accflag;
	private String accmoneyflag;
	private Double accmoney;
	private BigDecimal accoper;
	private Date accdt;
	private Set implTMonths =new HashSet(0);

	// Constructors

	public Set getImplTMonths() {
		return implTMonths;
	}

	public void setImplTMonths(Set implTMonths) {
		this.implTMonths = implTMonths;
	}

	/** default constructor */
	public BizTOptacc() {
	}

	/** full constructor */
	public BizTOptacc(DoperTPolicy doperTPolicy,
			SysTOrganization sysTOrganization, String accyear, String accmonth,
			String accdesc, Date accbegdt, Date accenddt, Date accexedt,
			BigDecimal accexeoper, Date accexeoverdt, String accflag,
			String accmoneyflag, Double accmoney, BigDecimal accoper, Date accdt) {
		this.doperTPolicy = doperTPolicy;
		this.sysTOrganization = sysTOrganization;
		this.accyear = accyear;
		this.accmonth = accmonth;
		this.accdesc = accdesc;
		this.accbegdt = accbegdt;
		this.accenddt = accenddt;
		this.accexedt = accexedt;
		this.accexeoper = accexeoper;
		this.accexeoverdt = accexeoverdt;
		this.accflag = accflag;
		this.accmoneyflag = accmoneyflag;
		this.accmoney = accmoney;
		this.accoper = accoper;
		this.accdt = accdt;
	}

	// Property accessors

	public BigDecimal getOptaccId() {
		return this.optaccId;
	}

	public void setOptaccId(BigDecimal optaccId) {
		this.optaccId = optaccId;
	}

	public DoperTPolicy getDoperTPolicy() {
		return this.doperTPolicy;
	}

	public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
		this.doperTPolicy = doperTPolicy;
	}

	public SysTOrganization getSysTOrganization() {
		return this.sysTOrganization;
	}

	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}

	public String getAccyear() {
		return this.accyear;
	}

	public void setAccyear(String accyear) {
		this.accyear = accyear;
	}

	public String getAccmonth() {
		return this.accmonth;
	}

	public void setAccmonth(String accmonth) {
		this.accmonth = accmonth;
	}

	public String getAccdesc() {
		return this.accdesc;
	}

	public void setAccdesc(String accdesc) {
		this.accdesc = accdesc;
	}

	public Date getAccbegdt() {
		return this.accbegdt;
	}

	public void setAccbegdt(Date accbegdt) {
		this.accbegdt = accbegdt;
	}

	public Date getAccenddt() {
		return this.accenddt;
	}

	public void setAccenddt(Date accenddt) {
		this.accenddt = accenddt;
	}

	public Date getAccexedt() {
		return this.accexedt;
	}

	public void setAccexedt(Date accexedt) {
		this.accexedt = accexedt;
	}

	public BigDecimal getAccexeoper() {
		return this.accexeoper;
	}

	public void setAccexeoper(BigDecimal accexeoper) {
		this.accexeoper = accexeoper;
	}

	public Date getAccexeoverdt() {
		return this.accexeoverdt;
	}

	public void setAccexeoverdt(Date accexeoverdt) {
		this.accexeoverdt = accexeoverdt;
	}

	public String getAccflag() {
		return this.accflag;
	}

	public void setAccflag(String accflag) {
		this.accflag = accflag;
	}

	public String getAccmoneyflag() {
		return this.accmoneyflag;
	}

	public void setAccmoneyflag(String accmoneyflag) {
		this.accmoneyflag = accmoneyflag;
	}

	public Double getAccmoney() {
		return this.accmoney;
	}

	public void setAccmoney(Double accmoney) {
		this.accmoney = accmoney;
	}

	public BigDecimal getAccoper() {
		return this.accoper;
	}

	public void setAccoper(BigDecimal accoper) {
		this.accoper = accoper;
	}

	public Date getAccdt() {
		return this.accdt;
	}

	public void setAccdt(Date accdt) {
		this.accdt = accdt;
	}

}