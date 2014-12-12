package com.mingda.entity;

import java.util.Date;

/**
 * BizTFamilymove entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BizTFamilymove implements java.io.Serializable {

	// Fields

	private Long familymoveId;
	private Long familyId;
	private String ofamilyno;
	private Long oorgid;
	private Long ooptperson;
	private Date oopttime;
	private String nfamilyno;
	private Long norgid;
	private Long noptperson;
	private Date nopttime;
	private String isover;

	// Constructors

	/** default constructor */
	public BizTFamilymove() {
	}

	/** full constructor */
	public BizTFamilymove(Long familyId, String ofamilyno, Long oorgid,
			Long ooptperson, Date oopttime, String nfamilyno, Long norgid,
			Long noptperson, Date nopttime, String isover) {
		this.familyId = familyId;
		this.ofamilyno = ofamilyno;
		this.oorgid = oorgid;
		this.ooptperson = ooptperson;
		this.oopttime = oopttime;
		this.nfamilyno = nfamilyno;
		this.norgid = norgid;
		this.noptperson = noptperson;
		this.nopttime = nopttime;
		this.isover = isover;
	}

	// Property accessors

	public Long getFamilymoveId() {
		return this.familymoveId;
	}

	public void setFamilymoveId(Long familymoveId) {
		this.familymoveId = familymoveId;
	}

	public Long getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public String getOfamilyno() {
		return this.ofamilyno;
	}

	public void setOfamilyno(String ofamilyno) {
		this.ofamilyno = ofamilyno;
	}

	public Long getOorgid() {
		return this.oorgid;
	}

	public void setOorgid(Long oorgid) {
		this.oorgid = oorgid;
	}

	public Long getOoptperson() {
		return this.ooptperson;
	}

	public void setOoptperson(Long ooptperson) {
		this.ooptperson = ooptperson;
	}

	public Date getOopttime() {
		return this.oopttime;
	}

	public void setOopttime(Date oopttime) {
		this.oopttime = oopttime;
	}

	public String getNfamilyno() {
		return this.nfamilyno;
	}

	public void setNfamilyno(String nfamilyno) {
		this.nfamilyno = nfamilyno;
	}

	public Long getNorgid() {
		return this.norgid;
	}

	public void setNorgid(Long norgid) {
		this.norgid = norgid;
	}

	public Long getNoptperson() {
		return this.noptperson;
	}

	public void setNoptperson(Long noptperson) {
		this.noptperson = noptperson;
	}

	public Date getNopttime() {
		return this.nopttime;
	}

	public void setNopttime(Date nopttime) {
		this.nopttime = nopttime;
	}

	public String getIsover() {
		return this.isover;
	}

	public void setIsover(String isover) {
		this.isover = isover;
	}

}