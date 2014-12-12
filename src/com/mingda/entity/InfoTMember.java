package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InfoTMember entity. @author MyEclipse Persistence Tools
 */

public class InfoTMember implements java.io.Serializable {

	// Fields

	private Integer memberId;
	private InfoTFamily infoTFamily;
	private BigDecimal nation;
	private Double personincome;
	private BigDecimal relmaster;
	private BigDecimal sex;
	private String membername;
	private String paperid;
	private BigDecimal ismarriage;
	private BigDecimal political;
	private BigDecimal cultural;
	private String inchurl;
	private String labor;
	private BigDecimal registeredtype;
	private BigDecimal registeredkind;
	private BigDecimal mainworkplace;
	private BigDecimal papertype;
	private Short age;
	private String remarks;
	private BigDecimal deformitystatus;
	private BigDecimal healthstatus;
	private BigDecimal workstatus;
	private Date birthday;
	// Constructors

	/** default constructor */
	public InfoTMember() {
	}

	/** full constructor */
	// Property accessors

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public InfoTFamily getInfoTFamily() {
		return this.infoTFamily;
	}

	public void setInfoTFamily(InfoTFamily infoTFamily) {
		this.infoTFamily = infoTFamily;
	}

	public BigDecimal getNation() {
		return this.nation;
	}

	public void setNation(BigDecimal nation) {
		this.nation = nation;
	}

	public Double getPersonincome() {
		return this.personincome;
	}

	public void setPersonincome(Double personincome) {
		this.personincome = personincome;
	}

	public BigDecimal getRelmaster() {
		return this.relmaster;
	}

	public void setRelmaster(BigDecimal relmaster) {
		this.relmaster = relmaster;
	}

	public BigDecimal getSex() {
		return this.sex;
	}

	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}

	public String getMembername() {
		return this.membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getPaperid() {
		return this.paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public BigDecimal getIsmarriage() {
		return this.ismarriage;
	}

	public void setIsmarriage(BigDecimal ismarriage) {
		this.ismarriage = ismarriage;
	}

	public BigDecimal getPolitical() {
		return this.political;
	}

	public void setPolitical(BigDecimal political) {
		this.political = political;
	}

	public BigDecimal getCultural() {
		return this.cultural;
	}

	public void setCultural(BigDecimal cultural) {
		this.cultural = cultural;
	}

	public String getInchurl() {
		return this.inchurl;
	}

	public void setInchurl(String inchurl) {
		this.inchurl = inchurl;
	}

	public String getLabor() {
		return this.labor;
	}

	public void setLabor(String labor) {
		this.labor = labor;
	}

	public BigDecimal getRegisteredtype() {
		return this.registeredtype;
	}

	public void setRegisteredtype(BigDecimal registeredtype) {
		this.registeredtype = registeredtype;
	}

	public BigDecimal getRegisteredkind() {
		return this.registeredkind;
	}

	public void setRegisteredkind(BigDecimal registeredkind) {
		this.registeredkind = registeredkind;
	}

	public BigDecimal getMainworkplace() {
		return this.mainworkplace;
	}

	public void setMainworkplace(BigDecimal mainworkplace) {
		this.mainworkplace = mainworkplace;
	}

	public BigDecimal getPapertype() {
		return this.papertype;
	}

	public void setPapertype(BigDecimal papertype) {
		this.papertype = papertype;
	}

	public Short getAge() {
		return this.age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getDeformitystatus() {
		return this.deformitystatus;
	}

	public void setDeformitystatus(BigDecimal deformitystatus) {
		this.deformitystatus = deformitystatus;
	}

	public BigDecimal getHealthstatus() {
		return this.healthstatus;
	}

	public void setHealthstatus(BigDecimal healthstatus) {
		this.healthstatus = healthstatus;
	}

	public BigDecimal getWorkstatus() {
		return this.workstatus;
	}

	public void setWorkstatus(BigDecimal workstatus) {
		this.workstatus = workstatus;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}