package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysTPost entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTPost implements java.io.Serializable {

	// Fields

	private Long postId;
	private String code;
	private String name;
	private String detail;
	private Long sequence;
	private String status;
	private Set sysTEmployees = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysTPost() {
	}

	/** full constructor */
	public SysTPost(String code, String name, String detail, Long sequence,
			String status, Set sysTEmployees) {
		this.code = code;
		this.name = name;
		this.detail = detail;
		this.sequence = sequence;
		this.status = status;
		this.sysTEmployees = sysTEmployees;
	}

	// Property accessors

	public Long getPostId() {
		return this.postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public Set getSysTEmployees() {
		return this.sysTEmployees;
	}

	public void setSysTEmployees(Set sysTEmployees) {
		this.sysTEmployees = sysTEmployees;
	}

}