package com.mingda.entity;

import java.math.BigDecimal;

/**
 * BizTOptreviewperson entity. @author MyEclipse Persistence Tools
 */

public class BizTOptreviewperson implements java.io.Serializable {

	// Fields

	private Long optreviewpersonId;
	private String name;
	private BigDecimal sex;
	private BigDecimal face;
	private String officephone;
	private String officename;
	private String post;
	private String address;
	private String organizationId;
	private String status;

	// Constructors

	/** default constructor */
	public BizTOptreviewperson() {
	}


	// Property accessors

	public String getName() {
		return this.name;
	}

	public Long getOptreviewpersonId() {
		return optreviewpersonId;
	}

	public void setOptreviewpersonId(Long optreviewpersonId) {
		this.optreviewpersonId = optreviewpersonId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSex() {
		return this.sex;
	}

	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}

	public BigDecimal getFace() {
		return this.face;
	}

	public void setFace(BigDecimal face) {
		this.face = face;
	}

	public String getOfficephone() {
		return this.officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	public String getOfficename() {
		return this.officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}