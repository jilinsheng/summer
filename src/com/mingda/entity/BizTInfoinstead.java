package com.mingda.entity;

import java.util.Date;

/**
 * BizTInfoinstead entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BizTInfoinstead implements java.io.Serializable {

	// Fields

	private Long infoinsteadId;
	private BizTInfoapprove bizTInfoapprove;
	private SysTField sysTField;
	private Long optperson;
	private Date opttime;
	private Long valtype;
	private Long pkfield;
	private Long pdvalue;
	private Long oldvalenumerate;
	private String oldvalstring;
	private Date oldvaldate;
	private Double oldvalfloat;
	private Long newvalenumerate;
	private String newvalstring;
	private Date newvaldate;
	private Double newvalfloat;

	// Constructors

	/** default constructor */
	public BizTInfoinstead() {
	}

	/** full constructor */
	public BizTInfoinstead(BizTInfoapprove bizTInfoapprove,
			SysTField sysTField, Long optperson, Date opttime, Long valtype,
			Long pkfield, Long pdvalue, Long oldvalenumerate,
			String oldvalstring, Date oldvaldate, Double oldvalfloat,
			Long newvalenumerate, String newvalstring, Date newvaldate,
			Double newvalfloat) {
		this.bizTInfoapprove = bizTInfoapprove;
		this.sysTField = sysTField;
		this.optperson = optperson;
		this.opttime = opttime;
		this.valtype = valtype;
		this.pkfield = pkfield;
		this.pdvalue = pdvalue;
		this.oldvalenumerate = oldvalenumerate;
		this.oldvalstring = oldvalstring;
		this.oldvaldate = oldvaldate;
		this.oldvalfloat = oldvalfloat;
		this.newvalenumerate = newvalenumerate;
		this.newvalstring = newvalstring;
		this.newvaldate = newvaldate;
		this.newvalfloat = newvalfloat;
	}

	// Property accessors

	public Long getInfoinsteadId() {
		return this.infoinsteadId;
	}

	public void setInfoinsteadId(Long infoinsteadId) {
		this.infoinsteadId = infoinsteadId;
	}

	public BizTInfoapprove getBizTInfoapprove() {
		return this.bizTInfoapprove;
	}

	public void setBizTInfoapprove(BizTInfoapprove bizTInfoapprove) {
		this.bizTInfoapprove = bizTInfoapprove;
	}

	public SysTField getSysTField() {
		return this.sysTField;
	}

	public void setSysTField(SysTField sysTField) {
		this.sysTField = sysTField;
	}

	public Long getOptperson() {
		return this.optperson;
	}

	public void setOptperson(Long optperson) {
		this.optperson = optperson;
	}

	public Date getOpttime() {
		return this.opttime;
	}

	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

	public Long getValtype() {
		return this.valtype;
	}

	public void setValtype(Long valtype) {
		this.valtype = valtype;
	}

	public Long getPkfield() {
		return this.pkfield;
	}

	public void setPkfield(Long pkfield) {
		this.pkfield = pkfield;
	}

	public Long getPdvalue() {
		return this.pdvalue;
	}

	public void setPdvalue(Long pdvalue) {
		this.pdvalue = pdvalue;
	}

	public Long getOldvalenumerate() {
		return this.oldvalenumerate;
	}

	public void setOldvalenumerate(Long oldvalenumerate) {
		this.oldvalenumerate = oldvalenumerate;
	}

	public String getOldvalstring() {
		return this.oldvalstring;
	}

	public void setOldvalstring(String oldvalstring) {
		this.oldvalstring = oldvalstring;
	}

	public Date getOldvaldate() {
		return this.oldvaldate;
	}

	public void setOldvaldate(Date oldvaldate) {
		this.oldvaldate = oldvaldate;
	}

	public Double getOldvalfloat() {
		return this.oldvalfloat;
	}

	public void setOldvalfloat(Double oldvalfloat) {
		this.oldvalfloat = oldvalfloat;
	}

	public Long getNewvalenumerate() {
		return this.newvalenumerate;
	}

	public void setNewvalenumerate(Long newvalenumerate) {
		this.newvalenumerate = newvalenumerate;
	}

	public String getNewvalstring() {
		return this.newvalstring;
	}

	public void setNewvalstring(String newvalstring) {
		this.newvalstring = newvalstring;
	}

	public Date getNewvaldate() {
		return this.newvaldate;
	}

	public void setNewvaldate(Date newvaldate) {
		this.newvaldate = newvaldate;
	}

	public Double getNewvalfloat() {
		return this.newvalfloat;
	}

	public void setNewvalfloat(Double newvalfloat) {
		this.newvalfloat = newvalfloat;
	}

}