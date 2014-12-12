package com.mingda.common.ibatis.data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * InfoTMember entity. @author MyEclipse Persistence Tools
 */

public class InfoTMember implements java.io.Serializable {

	// Fields

	private Integer memberId;
	private Integer familyId;
	private Long relmaster;
	private String membername;
	private Long papertype;
	private String paperid;
	private Date birthday;
	private Long sex;
	private Long nation;
	private Long ismarriage;
	private Long political;
	private Long rprkind;
	private Long rprtype;
	private String rpraddress;
	private Long degreesort;
	private Long policy;
	private Long ftap;
	private Long sicken;
	private Long deformity;
	private Long oldandinfirm;
	private Long workability;
	private String picpath;
	private Long student;
	private Long health;
	private String otherreason;
	private String indiId;
	private Long ftaplevel;

	public Long getFtaplevel() {
		return ftaplevel;
	}

	public void setFtaplevel(Long ftaplevel) {
		this.ftaplevel = ftaplevel;
	}

	public Long getHealth() {
		return health;
	}

	public void setHealth(Long health) {
		this.health = health;
	}

	public String getOtherreason() {
		return otherreason;
	}

	public void setOtherreason(String otherreason) {
		this.otherreason = otherreason;
	}

	public Long getStudent() {
		return student;
	}

	public void setStudent(Long student) {
		this.student = student;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public Long getRelmaster() {
		return relmaster;
	}

	public void setRelmaster(Long relmaster) {
		this.relmaster = relmaster;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public Long getPapertype() {
		return papertype;
	}

	public void setPapertype(Long papertype) {
		this.papertype = papertype;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getNation() {
		return nation;
	}

	public void setNation(Long nation) {
		this.nation = nation;
	}

	public Long getIsmarriage() {
		return ismarriage;
	}

	public void setIsmarriage(Long ismarriage) {
		this.ismarriage = ismarriage;
	}

	public Long getPolitical() {
		return political;
	}

	public void setPolitical(Long political) {
		this.political = political;
	}

	public Long getRprkind() {
		return rprkind;
	}

	public void setRprkind(Long rprkind) {
		this.rprkind = rprkind;
	}

	public Long getRprtype() {
		return rprtype;
	}

	public void setRprtype(Long rprtype) {
		this.rprtype = rprtype;
	}

	public String getRpraddress() {
		return rpraddress;
	}

	public void setRpraddress(String rpraddress) {
		this.rpraddress = rpraddress;
	}

	public Long getDegreesort() {
		return degreesort;
	}

	public void setDegreesort(Long degreesort) {
		this.degreesort = degreesort;
	}

	public Long getPolicy() {
		return policy;
	}

	public void setPolicy(Long policy) {
		this.policy = policy;
	}

	public Long getFtap() {
		return ftap;
	}

	public void setFtap(Long ftap) {
		this.ftap = ftap;
	}

	public Long getSicken() {
		return sicken;
	}

	public void setSicken(Long sicken) {
		this.sicken = sicken;
	}

	public Long getDeformity() {
		return deformity;
	}

	public void setDeformity(Long deformity) {
		this.deformity = deformity;
	}

	public Long getOldandinfirm() {
		return oldandinfirm;
	}

	public void setOldandinfirm(Long oldandinfirm) {
		this.oldandinfirm = oldandinfirm;
	}

	public Long getWorkability() {
		return workability;
	}

	public void setWorkability(Long workability) {
		this.workability = workability;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public void setIndiId(String indiId) {
		this.indiId = indiId;
	}

	public String getIndiId() {
		return indiId;
	}

}