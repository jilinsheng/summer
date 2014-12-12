package com.mingda.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysTTable entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTTable implements java.io.Serializable {

	// Fields

	private Long tableId;
	private Long superTableId;
	private String logicname;
	private String physicalname;
	private String explains;
	private String code;
	private Long depth;
	private Long sequence;
	private String isexpand;
	private String status;
	private String path;
	private Set sysTFields = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysTTable() {
	}

	/** full constructor */
	public SysTTable(Long superTableId, String logicname, String physicalname,
			String explains, String code, Long depth, Long sequence,
			String isexpand, String status, String path, Set sysTFields) {
		this.superTableId = superTableId;
		this.logicname = logicname;
		this.physicalname = physicalname;
		this.explains = explains;
		this.code = code;
		this.depth = depth;
		this.sequence = sequence;
		this.isexpand = isexpand;
		this.status = status;
		this.path = path;
		this.sysTFields = sysTFields;
	}

	// Property accessors

	public Long getTableId() {
		return this.tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getSuperTableId() {
		return this.superTableId;
	}

	public void setSuperTableId(Long superTableId) {
		this.superTableId = superTableId;
	}

	public String getLogicname() {
		return this.logicname;
	}

	public void setLogicname(String logicname) {
		this.logicname = logicname;
	}

	public String getPhysicalname() {
		return this.physicalname;
	}

	public void setPhysicalname(String physicalname) {
		this.physicalname = physicalname;
	}

	public String getExplains() {
		return this.explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getDepth() {
		return this.depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getIsexpand() {
		return this.isexpand;
	}

	public void setIsexpand(String isexpand) {
		this.isexpand = isexpand;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Set getSysTFields() {
		return this.sysTFields;
	}

	public void setSysTFields(Set sysTFields) {
		this.sysTFields = sysTFields;
	}

}