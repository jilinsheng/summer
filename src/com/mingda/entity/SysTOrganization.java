package com.mingda.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SysTOrganization entity. @author MyEclipse Persistence Tools
 */

public class SysTOrganization implements java.io.Serializable {

	// Fields

	private String organizationId;
	private String serialnumber;
	private String orgname;
	private String fullname;
	private String parentorgid;
	private BigDecimal depth;
	private String status;
	private Set netTReceives = new HashSet(0);
	private Set infoTFamilies = new HashSet(0);
	private Set doperTStandarddepts = new HashSet(0);
	private Set bizTInterviews = new HashSet(0);
	private Set sysTOrgexts = new HashSet(0);
	private Set sysTEmployees = new HashSet(0);
	private Set bizTOptaccs = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysTOrganization() {
	}

	/** full constructor */
	public SysTOrganization(String serialnumber, String orgname,
			String fullname, String parentorgid, BigDecimal depth,
			String status, Set netTReceives, Set infoTFamilies,
			Set doperTStandarddepts, Set bizTInterviews, Set sysTOrgexts,
			Set sysTEmployees, Set bizTOptaccs) {
		this.serialnumber = serialnumber;
		this.orgname = orgname;
		this.fullname = fullname;
		this.parentorgid = parentorgid;
		this.depth = depth;
		this.status = status;
		this.netTReceives = netTReceives;
		this.infoTFamilies = infoTFamilies;
		this.doperTStandarddepts = doperTStandarddepts;
		this.bizTInterviews = bizTInterviews;
		this.sysTOrgexts = sysTOrgexts;
		this.sysTEmployees = sysTEmployees;
		this.bizTOptaccs = bizTOptaccs;
	}

	// Property accessors

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getParentorgid() {
		return this.parentorgid;
	}

	public void setParentorgid(String parentorgid) {
		this.parentorgid = parentorgid;
	}

	public BigDecimal getDepth() {
		return this.depth;
	}

	public void setDepth(BigDecimal depth) {
		this.depth = depth;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set getNetTReceives() {
		return this.netTReceives;
	}

	public void setNetTReceives(Set netTReceives) {
		this.netTReceives = netTReceives;
	}

	public Set getInfoTFamilies() {
		return this.infoTFamilies;
	}

	public void setInfoTFamilies(Set infoTFamilies) {
		this.infoTFamilies = infoTFamilies;
	}

	public Set getDoperTStandarddepts() {
		return this.doperTStandarddepts;
	}

	public void setDoperTStandarddepts(Set doperTStandarddepts) {
		this.doperTStandarddepts = doperTStandarddepts;
	}

	public Set getBizTInterviews() {
		return this.bizTInterviews;
	}

	public void setBizTInterviews(Set bizTInterviews) {
		this.bizTInterviews = bizTInterviews;
	}

	public Set getSysTOrgexts() {
		return this.sysTOrgexts;
	}

	public void setSysTOrgexts(Set sysTOrgexts) {
		this.sysTOrgexts = sysTOrgexts;
	}

	public Set getSysTEmployees() {
		return this.sysTEmployees;
	}

	public void setSysTEmployees(Set sysTEmployees) {
		this.sysTEmployees = sysTEmployees;
	}

	public Set getBizTOptaccs() {
		return this.bizTOptaccs;
	}

	public void setBizTOptaccs(Set bizTOptaccs) {
		this.bizTOptaccs = bizTOptaccs;
	}

}