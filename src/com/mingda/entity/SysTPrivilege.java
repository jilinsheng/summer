package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysTPrivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTPrivilege implements java.io.Serializable {

	// Fields

	private Long privilegeId;
	private Long parentprivilegeid;
	private String code;
	private String displayname;
	private String detail;
	private String url;
	private String sequence;
	private Long depth;
	private String status;
	private String target;
	private String nav;
	private String isleaf;
	private String ico;
	private Set sysTRoles = new HashSet(0);

	// Constructors

	public Set getSysTRoles() {
		return sysTRoles;
	}

	public void setSysTRoles(Set sysTRoles) {
		this.sysTRoles = sysTRoles;
	}

	/** default constructor */
	public SysTPrivilege() {
	}

	/** full constructor */
	public SysTPrivilege(Long parentprivilegeid, String code,
			String displayname, String detail, String url, String sequence,
			Long depth, String status, String target, String nav,
			String isleaf, Set sysTRoles) {
		this.parentprivilegeid = parentprivilegeid;
		this.code = code;
		this.displayname = displayname;
		this.detail = detail;
		this.url = url;
		this.sequence = sequence;
		this.depth = depth;
		this.status = status;
		this.target = target;
		this.nav = nav;
		this.isleaf = isleaf;
		this.sysTRoles = sysTRoles;
	}

	// Property accessors

	public Long getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Long getParentprivilegeid() {
		return this.parentprivilegeid;
	}

	public void setParentprivilegeid(Long parentprivilegeid) {
		this.parentprivilegeid = parentprivilegeid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayname() {
		return this.displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Long getDepth() {
		return this.depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getNav() {
		return this.nav;
	}

	public void setNav(String nav) {
		this.nav = nav;
	}

	public String getIsleaf() {
		return this.isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

}