package com.mingda.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * ImplTPapertype entity. @author MyEclipse Persistence Tools
 */

public class ImplTPapertype implements java.io.Serializable {

	// Fields

	private Long papertypeId;
	private String papername;
	private String paperxml;
	private String flag;
	private String policyId;
	private Set implTPaperrecords = new HashSet(0);

	// Constructors

	/** default constructor */
	public ImplTPapertype() {
	}

	/** full constructor */
	public ImplTPapertype(String papername, String paperxml, String flag,
			String policyId, Set implTPaperrecords) {
		this.papername = papername;
		this.paperxml = paperxml;
		this.flag = flag;
		this.policyId = policyId;
		this.implTPaperrecords = implTPaperrecords;
	}

	// Property accessors

	public Long getPapertypeId() {
		return this.papertypeId;
	}

	public void setPapertypeId(Long papertypeId) {
		this.papertypeId = papertypeId;
	}

	public String getPapername() {
		return this.papername;
	}

	public void setPapername(String papername) {
		this.papername = papername;
	}

	public String getPaperxml() {
		return this.paperxml;
	}

	public void setPaperxml(String paperxml) {
		this.paperxml = paperxml;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Set getImplTPaperrecords() {
		return this.implTPaperrecords;
	}

	public void setImplTPaperrecords(Set implTPaperrecords) {
		this.implTPaperrecords = implTPaperrecords;
	}

}