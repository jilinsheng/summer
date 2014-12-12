package com.mingda.common.ibatis.data;

import java.math.BigDecimal;

/**
 * InfoTMemberclass entity. @author MyEclipse Persistence Tools
 */

public class InfoTMemberclass implements java.io.Serializable {

	// Fields

	private BigDecimal memberclassId;
	private InfoTMember infoTMember;

	// Constructors

	/** default constructor */
	public InfoTMemberclass() {
	}

	/** minimal constructor */
	public InfoTMemberclass(BigDecimal memberclassId) {
		this.memberclassId = memberclassId;
	}

	/** full constructor */
	public InfoTMemberclass(BigDecimal memberclassId, InfoTMember infoTMember) {
		this.memberclassId = memberclassId;
		this.infoTMember = infoTMember;
	}

	// Property accessors

	public BigDecimal getMemberclassId() {
		return this.memberclassId;
	}

	public void setMemberclassId(BigDecimal memberclassId) {
		this.memberclassId = memberclassId;
	}

	public InfoTMember getInfoTMember() {
		return this.infoTMember;
	}

	public void setInfoTMember(InfoTMember infoTMember) {
		this.infoTMember = infoTMember;
	}

}