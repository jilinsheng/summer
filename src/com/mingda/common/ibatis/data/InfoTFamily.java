package com.mingda.common.ibatis.data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * InfoTFamily entity. @author MyEclipse Persistence Tools
 */

public class InfoTFamily implements java.io.Serializable {

	// Fields

	private Integer familyId;
	private Long organizationId;
	private String familyno;
	private Long population;
	private String mastername;
	private Long ensurecount;
	private Long masterid;
	private Long assetworth;
	private Long consultincome;
	private Long masterpapertype;
	private String masterpaperid;
	private String rpraddress;
	private String famaddress;
	private String linkmode;
	private String status = "1";
	private Double avgincome;
	private String malcondition;
	private Long onlychild;
	private Double allpayout;
	private String tohtml ="1";

	public String getTohtml() {
		return tohtml;
	}

	public void setTohtml(String tohtml) {
		this.tohtml = tohtml;
	}

	public Integer getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public String getMastername() {
		return mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public Long getEnsurecount() {
		return ensurecount;
	}

	public void setEnsurecount(Long ensurecount) {
		this.ensurecount = ensurecount;
	}

	public Long getMasterid() {
		return masterid;
	}

	public void setMasterid(Long masterid) {
		this.masterid = masterid;
	}

	public Long getAssetworth() {
		return assetworth;
	}

	public void setAssetworth(Long assetworth) {
		this.assetworth = assetworth;
	}

	public Long getConsultincome() {
		return consultincome;
	}

	public void setConsultincome(Long consultincome) {
		this.consultincome = consultincome;
	}

	public Long getMasterpapertype() {
		return masterpapertype;
	}

	public void setMasterpapertype(Long masterpapertype) {
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

	public String getMalcondition() {
		return malcondition;
	}

	public void setMalcondition(String malcondition) {
		this.malcondition = malcondition;
	}

	public Long getOnlychild() {
		return onlychild;
	}

	public void setOnlychild(Long onlychild) {
		this.onlychild = onlychild;
	}

	public Double getAllpayout() {
		return allpayout;
	}

	public void setAllpayout(Double allpayout) {
		this.allpayout = allpayout;
	}
}