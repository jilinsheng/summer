package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * BizTValidateterm entity. @author MyEclipse Persistence Tools
 */

public class BizTValidateterm implements java.io.Serializable {

	// Fields

	private Long validatetermId;
	private String name;
	private String itemdesc;
	private String physql;
	private String locsql;
	private String status;
	private Long type;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	private Set bizTValidatelogs = new HashSet(0);

	// Constructors

	/** default constructor */
	public BizTValidateterm() {
	}

	/** full constructor */
	public BizTValidateterm(String name, String itemdesc, String physql,
			String locsql, String status, Set bizTValidatelogs) {
		this.name = name;
		this.itemdesc = itemdesc;
		this.physql = physql;
		this.locsql = locsql;
		this.status = status;
		this.bizTValidatelogs = bizTValidatelogs;
	}

	// Property accessors

	public Long getValidatetermId() {
		return this.validatetermId;
	}

	public void setValidatetermId(Long validatetermId) {
		this.validatetermId = validatetermId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemdesc() {
		return this.itemdesc;
	}

	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}

	public String getPhysql() {
		return this.physql;
	}

	public void setPhysql(String physql) {
		this.physql = physql;
	}

	public String getLocsql() {
		return this.locsql;
	}

	public void setLocsql(String locsql) {
		this.locsql = locsql;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set getBizTValidatelogs() {
		return this.bizTValidatelogs;
	}

	public void setBizTValidatelogs(Set bizTValidatelogs) {
		this.bizTValidatelogs = bizTValidatelogs;
	}

}