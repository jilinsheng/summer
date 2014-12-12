package com.mingda.common.ibatis.data;

import java.math.BigDecimal;

/**
 * InfoTAnnex entity. @author MyEclipse Persistence Tools
 */

public class InfoTAnnex implements java.io.Serializable {

	// Fields

	private Integer annexId;
	private String target;
	private Integer relationid;
	private String annexname;
	private String fileext;
	private String path;
	private String remark;
	private String status;

	// Constructors

	/** default constructor */
	public InfoTAnnex() {
	}

	/** minimal constructor */
	public InfoTAnnex(Integer annexId) {
		this.annexId = annexId;
	}

	/** full constructor */
	public InfoTAnnex(Integer annexId, String target, Integer relationid,
			String annexname, String fileext, String path, String remark,
			String status) {
		this.annexId = annexId;
		this.target = target;
		this.relationid = relationid;
		this.annexname = annexname;
		this.fileext = fileext;
		this.path = path;
		this.remark = remark;
		this.status = status;
	}

	// Property accessors


	public String getTarget() {
		return this.target;
	}

	public Integer getAnnexId() {
		return annexId;
	}

	public void setAnnexId(Integer annexId) {
		this.annexId = annexId;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getRelationid() {
		return this.relationid;
	}

	public void setRelationid(Integer relationid) {
		this.relationid = relationid;
	}

	public String getAnnexname() {
		return this.annexname;
	}

	public void setAnnexname(String annexname) {
		this.annexname = annexname;
	}

	public String getFileext() {
		return this.fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}