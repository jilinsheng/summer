package com.mingda.action.info.neweditor;

import java.util.Date;

public class AnnexDTO {
	private String anId;

	private String anName;

	private String anType;

	private Date anUpdatetime;

	private String ffamilyid;

	private String anFilename;

	public String getAnId() {
		return anId;
	}

	public void setAnId(String anId) {
		this.anId = anId;
	}

	public String getAnName() {
		return anName;
	}

	public void setAnName(String anName) {
		this.anName = anName;
	}

	public String getAnType() {
		return anType;
	}

	public void setAnType(String anType) {
		this.anType = anType;
	}

	public Date getAnUpdatetime() {
		return anUpdatetime;
	}

	public void setAnUpdatetime(Date anUpdatetime) {
		this.anUpdatetime = anUpdatetime;
	}

	public String getFfamilyid() {
		return ffamilyid;
	}

	public void setFfamilyid(String ffamilyid) {
		this.ffamilyid = ffamilyid;
	}

	public String getAnFilename() {
		return anFilename;
	}

	public void setAnFilename(String anFilename) {
		this.anFilename = anFilename;
	}

}
