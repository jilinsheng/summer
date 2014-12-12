package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysTDictsort entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTDictsort implements java.io.Serializable {

	// Fields

	private Long dictsortId;
	private String name;
	private String description;
	private Long sequence;
	private String status;
	private String isread;
	private String code;
	private Set sysTDictionaries = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysTDictsort() {
	}

	/** full constructor */
	public SysTDictsort(String name, String description, Long sequence,
			String status, String isread, Set sysTDictionaries) {
		this.name = name;
		this.description = description;
		this.sequence = sequence;
		this.status = status;
		this.isread = isread;
		this.sysTDictionaries = sysTDictionaries;
	}

	// Property accessors

	public Long getDictsortId() {
		return this.dictsortId;
	}

	public void setDictsortId(Long dictsortId) {
		this.dictsortId = dictsortId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsread() {
		return this.isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public Set getSysTDictionaries() {
		return this.sysTDictionaries;
	}

	public void setSysTDictionaries(Set sysTDictionaries) {
		this.sysTDictionaries = sysTDictionaries;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}