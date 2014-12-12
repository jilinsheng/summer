package com.mingda.entity;

/**
 * SysTOrgext entity. @author MyEclipse Persistence Tools
 */

public class SysTOrgext implements java.io.Serializable {

	// Fields

	private SysTOrgextId id;
	private String context;

	// Constructors

	/** default constructor */
	public SysTOrgext() {
	}

	/** minimal constructor */
	public SysTOrgext(SysTOrgextId id) {
		this.id = id;
	}

	/** full constructor */
	public SysTOrgext(SysTOrgextId id, String context) {
		this.id = id;
		this.context = context;
	}

	// Property accessors

	public SysTOrgextId getId() {
		return this.id;
	}

	public void setId(SysTOrgextId id) {
		this.id = id;
	}

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}