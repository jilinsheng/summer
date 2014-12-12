package com.mingda.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * DoperTStandard entity. @author MyEclipse Persistence Tools
 */

public class DoperTStandard implements java.io.Serializable {

	// Fields

	private BigDecimal standardId;
	private DoperTPolicy doperTPolicy;
	private String descr;
	private String physql;
	private String locsql;
	private Double planmoney;
	private String plandesc;
	private Boolean flag;
	private String superpolicy;
	private Set doperTStandarddepts = new HashSet(0);

	// Constructors

	/** default constructor */
	public DoperTStandard() {
	}

	/** full constructor */
	public DoperTStandard(DoperTPolicy doperTPolicy, String descr,
			String physql, String locsql, Double planmoney, String plandesc,
			Boolean flag, String superpolicy, Set doperTStandarddepts) {
		this.doperTPolicy = doperTPolicy;
		this.descr = descr;
		this.physql = physql;
		this.locsql = locsql;
		this.planmoney = planmoney;
		this.plandesc = plandesc;
		this.flag = flag;
		this.superpolicy = superpolicy;
		this.doperTStandarddepts = doperTStandarddepts;
	}

	// Property accessors

	public BigDecimal getStandardId() {
		return this.standardId;
	}

	public void setStandardId(BigDecimal standardId) {
		this.standardId = standardId;
	}

	public DoperTPolicy getDoperTPolicy() {
		return this.doperTPolicy;
	}

	public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
		this.doperTPolicy = doperTPolicy;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getPhysql() {
		return this.physql;
	}

	public void setPhysql(String physql) {
		this.physql = physql;
	}

	public String getLocsql() {
		return this.locsql;
	}

	public void setLocsql(String locsql) {
		this.locsql = locsql;
	}

	public Double getPlanmoney() {
		return this.planmoney;
	}

	public void setPlanmoney(Double planmoney) {
		this.planmoney = planmoney;
	}

	public String getPlandesc() {
		return this.plandesc;
	}

	public void setPlandesc(String plandesc) {
		this.plandesc = plandesc;
	}

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getSuperpolicy() {
		return this.superpolicy;
	}

	public void setSuperpolicy(String superpolicy) {
		this.superpolicy = superpolicy;
	}

	public Set getDoperTStandarddepts() {
		return this.doperTStandarddepts;
	}

	public void setDoperTStandarddepts(Set doperTStandarddepts) {
		this.doperTStandarddepts = doperTStandarddepts;
	}

}