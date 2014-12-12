package com.mingda.entity;

import java.math.BigDecimal;
/**
 * InfoTFamily entity. @author MyEclipse Persistence Tools
 */

public class InfoTFamily implements java.io.Serializable {

	// Fields

	private BigDecimal familyId;
	private SysTOrganization sysTOrganization;
	private String familyno;
	private BigDecimal population;
	private String mastername;
	private BigDecimal ensurecount;
	private BigDecimal masterid;
	private BigDecimal assetworth;
	private BigDecimal consultincome;
	private BigDecimal masterpapertype;
	private String masterpaperid;
	private String rpraddress;
	private String famaddress;
	private String linkmode;
	private String status;
	private Double avgincome;
	private BigDecimal malcondition;
	private BigDecimal onlychild;
	private Double allpayout;
	public BigDecimal getFamilyId() {
		return familyId;
	}
	public void setFamilyId(BigDecimal familyId) {
		this.familyId = familyId;
	}
	public SysTOrganization getSysTOrganization() {
		return sysTOrganization;
	}
	public void setSysTOrganization(SysTOrganization sysTOrganization) {
		this.sysTOrganization = sysTOrganization;
	}
	public String getFamilyno() {
		return familyno;
	}
	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}
	public BigDecimal getPopulation() {
		return population;
	}
	public void setPopulation(BigDecimal population) {
		this.population = population;
	}
	public String getMastername() {
		return mastername;
	}
	public void setMastername(String mastername) {
		this.mastername = mastername;
	}
	public BigDecimal getEnsurecount() {
		return ensurecount;
	}
	public void setEnsurecount(BigDecimal ensurecount) {
		this.ensurecount = ensurecount;
	}
	public BigDecimal getMasterid() {
		return masterid;
	}
	public void setMasterid(BigDecimal masterid) {
		this.masterid = masterid;
	}
	public BigDecimal getAssetworth() {
		return assetworth;
	}
	public void setAssetworth(BigDecimal assetworth) {
		this.assetworth = assetworth;
	}
	public BigDecimal getConsultincome() {
		return consultincome;
	}
	public void setConsultincome(BigDecimal consultincome) {
		this.consultincome = consultincome;
	}
	public BigDecimal getMasterpapertype() {
		return masterpapertype;
	}
	public void setMasterpapertype(BigDecimal masterpapertype) {
		this.masterpapertype = masterpapertype;
	}
	public String getMasterpaperid() {
		return masterpaperid;
	}
	public void setMasterpaperid(String masterpaperid) {
		this.masterpaperid = masterpaperid;
	}
	public String getRpraddress() {
		return rpraddress;
	}
	public void setRpraddress(String rpraddress) {
		this.rpraddress = rpraddress;
	}
	public String getFamaddress() {
		return famaddress;
	}
	public void setFamaddress(String famaddress) {
		this.famaddress = famaddress;
	}
	public String getLinkmode() {
		return linkmode;
	}
	public void setLinkmode(String linkmode) {
		this.linkmode = linkmode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getAvgincome() {
		return avgincome;
	}
	public void setAvgincome(Double avgincome) {
		this.avgincome = avgincome;
	}
	public BigDecimal getMalcondition() {
		return malcondition;
	}
	public void setMalcondition(BigDecimal malcondition) {
		this.malcondition = malcondition;
	}
	public BigDecimal getOnlychild() {
		return onlychild;
	}
	public void setOnlychild(BigDecimal onlychild) {
		this.onlychild = onlychild;
	}
	public Double getAllpayout() {
		return allpayout;
	}
	public void setAllpayout(Double allpayout) {
		this.allpayout = allpayout;
	}


}