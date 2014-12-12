package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ImplTPaperrecord entity. @author MyEclipse Persistence Tools
 */

public class ImplTPaperrecord implements java.io.Serializable {

	// Fields

	private Long paperrecordId;
	private ImplTPapertype implTPapertype;
	private Long familyId;
	private String reason;
	private Date opttime;
	private BigDecimal operator;
	private String flag;
	private Set implTPaperinfo1s = new HashSet(0);
	private Set implTPaperinfo2s = new HashSet(0);

	// Constructors

	/** default constructor */
	public ImplTPaperrecord() {
	}

	/** full constructor */
	public ImplTPaperrecord(ImplTPapertype implTPapertype, Long familyId, Date opttime, BigDecimal operator, String flag,
			Set implTPaperinfo1s, Set implTPaperinfo2s) {
		this.implTPapertype = implTPapertype;
		this.familyId = familyId;
		this.reason = reason;
		this.opttime = opttime;
		this.operator = operator;
		this.flag = flag;
		this.implTPaperinfo1s = implTPaperinfo1s;
		this.implTPaperinfo2s = implTPaperinfo2s;
	}

	// Property accessors

	public Long getPaperrecordId() {
		return this.paperrecordId;
	}

	public void setPaperrecordId(Long paperrecordId) {
		this.paperrecordId = paperrecordId;
	}

	public ImplTPapertype getImplTPapertype() {
		return this.implTPapertype;
	}

	public void setImplTPapertype(ImplTPapertype implTPapertype) {
		this.implTPapertype = implTPapertype;
	}

	public Long getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getOpttime() {
		return this.opttime;
	}

	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

	public BigDecimal getOperator() {
		return this.operator;
	}

	public void setOperator(BigDecimal operator) {
		this.operator = operator;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Set getImplTPaperinfo1s() {
		return this.implTPaperinfo1s;
	}

	public void setImplTPaperinfo1s(Set implTPaperinfo1s) {
		this.implTPaperinfo1s = implTPaperinfo1s;
	}

	public Set getImplTPaperinfo2s() {
		return this.implTPaperinfo2s;
	}

	public void setImplTPaperinfo2s(Set implTPaperinfo2s) {
		this.implTPaperinfo2s = implTPaperinfo2s;
	}

}