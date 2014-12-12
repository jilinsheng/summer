package com.mingda.common.ibatis.data;

import java.math.BigDecimal;

/**
 * InfoTFamilyclass entity. @author MyEclipse Persistence Tools
 */

public class InfoTFamilyclass implements java.io.Serializable {

	// Fields

	private BigDecimal classId;
	private InfoTFamily infoTFamily;

	// Constructors

	/** default constructor */
	public InfoTFamilyclass() {
	}

	/** minimal constructor */
	public InfoTFamilyclass(BigDecimal classId) {
		this.classId = classId;
	}

	/** full constructor */
	public InfoTFamilyclass(BigDecimal classId, InfoTFamily infoTFamily) {
		this.classId = classId;
		this.infoTFamily = infoTFamily;
	}

	// Property accessors

	public BigDecimal getClassId() {
		return this.classId;
	}

	public void setClassId(BigDecimal classId) {
		this.classId = classId;
	}

	public InfoTFamily getInfoTFamily() {
		return this.infoTFamily;
	}

	public void setInfoTFamily(InfoTFamily infoTFamily) {
		this.infoTFamily = infoTFamily;
	}

}