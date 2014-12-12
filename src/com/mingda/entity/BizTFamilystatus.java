package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BizTFamilystatus entity. @author MyEclipse Persistence Tools
 */

public class BizTFamilystatus implements java.io.Serializable {

	// Fields

	private BigDecimal familystatusId;
	private DoperTPolicy doperTPolicy;
	private InfoTFamily infoTFamily;
	private Date begdt;
	private Double money;
	private Date enddt;
	private String flag;
	private Set bizTDemurrals = new HashSet(0);

	// Constructors

	/** default constructor */
	public BizTFamilystatus() {
	}

	/** full constructor */
	public BizTFamilystatus(DoperTPolicy doperTPolicy, InfoTFamily infoTFamily,
			Date begdt, Double money, Date enddt, String flag, Set bizTDemurrals) {
		this.doperTPolicy = doperTPolicy;
		this.infoTFamily = infoTFamily;
		this.begdt = begdt;
		this.money = money;
		this.enddt = enddt;
		this.flag = flag;
		this.bizTDemurrals = bizTDemurrals;
	}

	// Property accessors

	public BigDecimal getFamilystatusId() {
		return this.familystatusId;
	}

	public void setFamilystatusId(BigDecimal familystatusId) {
		this.familystatusId = familystatusId;
	}

	public DoperTPolicy getDoperTPolicy() {
		return this.doperTPolicy;
	}

	public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
		this.doperTPolicy = doperTPolicy;
	}

	public InfoTFamily getInfoTFamily() {
		return this.infoTFamily;
	}

	public void setInfoTFamily(InfoTFamily infoTFamily) {
		this.infoTFamily = infoTFamily;
	}

	public Date getBegdt() {
		return this.begdt;
	}

	public void setBegdt(Date begdt) {
		this.begdt = begdt;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getEnddt() {
		return this.enddt;
	}

	public void setEnddt(Date enddt) {
		this.enddt = enddt;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Set getBizTDemurrals() {
		return this.bizTDemurrals;
	}

	public void setBizTDemurrals(Set bizTDemurrals) {
		this.bizTDemurrals = bizTDemurrals;
	}

}