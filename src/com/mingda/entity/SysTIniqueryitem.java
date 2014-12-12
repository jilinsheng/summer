package com.mingda.entity;

import java.math.BigDecimal;

/**
 * SysTIniqueryitem entity. @author MyEclipse Persistence Tools
 */

public class SysTIniqueryitem implements java.io.Serializable {

	// Fields

	private BigDecimal iniqueryitemId;
	private SysTIniquery sysTIniquery;
	private String fieldnameloc;
	private String matchexp;
	private String logicexp;
	private BigDecimal status;
	private BigDecimal sequence;

	// Constructors

	/** default constructor */
	public SysTIniqueryitem() {
	}

	/** full constructor */
	public SysTIniqueryitem(SysTIniquery sysTIniquery, String fieldnameloc,
			String matchexp, String logicexp, BigDecimal status,
			BigDecimal sequence) {
		this.sysTIniquery = sysTIniquery;
		this.fieldnameloc = fieldnameloc;
		this.matchexp = matchexp;
		this.logicexp = logicexp;
		this.status = status;
		this.sequence = sequence;
	}

	// Property accessors

	public BigDecimal getIniqueryitemId() {
		return this.iniqueryitemId;
	}

	public void setIniqueryitemId(BigDecimal iniqueryitemId) {
		this.iniqueryitemId = iniqueryitemId;
	}

	public SysTIniquery getSysTIniquery() {
		return this.sysTIniquery;
	}

	public void setSysTIniquery(SysTIniquery sysTIniquery) {
		this.sysTIniquery = sysTIniquery;
	}

	public String getFieldnameloc() {
		return this.fieldnameloc;
	}

	public void setFieldnameloc(String fieldnameloc) {
		this.fieldnameloc = fieldnameloc;
	}

	public String getMatchexp() {
		return this.matchexp;
	}

	public void setMatchexp(String matchexp) {
		this.matchexp = matchexp;
	}

	public String getLogicexp() {
		return this.logicexp;
	}

	public void setLogicexp(String logicexp) {
		this.logicexp = logicexp;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getSequence() {
		return this.sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}

}