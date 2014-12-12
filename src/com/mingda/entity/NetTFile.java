package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * NetTFile entity. @author MyEclipse Persistence Tools
 */

public class NetTFile implements java.io.Serializable {

	// Fields

	private Long fileId;
	private String filename;
	private String filetype;
	private String filepath;
	private Date uploadtime;
	private String reltable;
	private BigDecimal relid;

	// Constructors

	/** default constructor */
	public NetTFile() {
	}

	/** full constructor */
	public NetTFile(String filename, String filetype, String filepath,
			Date uploadtime, String reltable, BigDecimal relid) {
		this.filename = filename;
		this.filetype = filetype;
		this.filepath = filepath;
		this.uploadtime = uploadtime;
		this.reltable = reltable;
		this.relid = relid;
	}

	// Property accessors


	public String getFilename() {
		return this.filename;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getReltable() {
		return this.reltable;
	}

	public void setReltable(String reltable) {
		this.reltable = reltable;
	}

	public BigDecimal getRelid() {
		return this.relid;
	}

	public void setRelid(BigDecimal relid) {
		this.relid = relid;
	}

}