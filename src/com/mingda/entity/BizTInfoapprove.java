package com.mingda.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BizTInfoapprove entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BizTInfoapprove implements java.io.Serializable {

	// Fields

	private Long infoapproveId;
	private InfoTFamily infoTFamily;
	private String status;
	private Long appstate1;
	private String appcontent1;
	private Long appperson1;
	private Date apptime1;
	private Long appstate2;
	private String appcontent2;
	private Long appperson2;
	private Date apptime2;
	private Long appstate3;
	private String appcontent3;
	private Long appperson3;
	private Date apptime3;
	private Long appstate4;
	private String appcontent4;
	private Long appperson4;
	private Date apptime4;
	private Long appstate5;
	private String appcontent5;
	private Long appperson5;
	private Date apptime5;
	private Set bizTInfoinsteads = new HashSet(0);

	// Constructors

	/** default constructor */
	public BizTInfoapprove() {
	}

	/** full constructor */
	public BizTInfoapprove(InfoTFamily infoTFamily, String status,
			Long appstate1, String appcontent1, Long appperson1, Date apptime1,
			Long appstate2, String appcontent2, Long appperson2, Date apptime2,
			Long appstate3, String appcontent3, Long appperson3, Date apptime3,
			Long appstate4, String appcontent4, Long appperson4, Date apptime4,
			Long appstate5, String appcontent5, Long appperson5, Date apptime5,
			Set bizTInfoinsteads) {
		this.infoTFamily = infoTFamily;
		this.status = status;
		this.appstate1 = appstate1;
		this.appcontent1 = appcontent1;
		this.appperson1 = appperson1;
		this.apptime1 = apptime1;
		this.appstate2 = appstate2;
		this.appcontent2 = appcontent2;
		this.appperson2 = appperson2;
		this.apptime2 = apptime2;
		this.appstate3 = appstate3;
		this.appcontent3 = appcontent3;
		this.appperson3 = appperson3;
		this.apptime3 = apptime3;
		this.appstate4 = appstate4;
		this.appcontent4 = appcontent4;
		this.appperson4 = appperson4;
		this.apptime4 = apptime4;
		this.appstate5 = appstate5;
		this.appcontent5 = appcontent5;
		this.appperson5 = appperson5;
		this.apptime5 = apptime5;
		this.bizTInfoinsteads = bizTInfoinsteads;
	}

	// Property accessors

	public Long getInfoapproveId() {
		return this.infoapproveId;
	}

	public void setInfoapproveId(Long infoapproveId) {
		this.infoapproveId = infoapproveId;
	}

	public InfoTFamily getInfoTFamily() {
		return this.infoTFamily;
	}

	public void setInfoTFamily(InfoTFamily infoTFamily) {
		this.infoTFamily = infoTFamily;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAppstate1() {
		return this.appstate1;
	}

	public void setAppstate1(Long appstate1) {
		this.appstate1 = appstate1;
	}

	public String getAppcontent1() {
		return this.appcontent1;
	}

	public void setAppcontent1(String appcontent1) {
		this.appcontent1 = appcontent1;
	}

	public Long getAppperson1() {
		return this.appperson1;
	}

	public void setAppperson1(Long appperson1) {
		this.appperson1 = appperson1;
	}

	public Date getApptime1() {
		return this.apptime1;
	}

	public void setApptime1(Date apptime1) {
		this.apptime1 = apptime1;
	}

	public Long getAppstate2() {
		return this.appstate2;
	}

	public void setAppstate2(Long appstate2) {
		this.appstate2 = appstate2;
	}

	public String getAppcontent2() {
		return this.appcontent2;
	}

	public void setAppcontent2(String appcontent2) {
		this.appcontent2 = appcontent2;
	}

	public Long getAppperson2() {
		return this.appperson2;
	}

	public void setAppperson2(Long appperson2) {
		this.appperson2 = appperson2;
	}

	public Date getApptime2() {
		return this.apptime2;
	}

	public void setApptime2(Date apptime2) {
		this.apptime2 = apptime2;
	}

	public Long getAppstate3() {
		return this.appstate3;
	}

	public void setAppstate3(Long appstate3) {
		this.appstate3 = appstate3;
	}

	public String getAppcontent3() {
		return this.appcontent3;
	}

	public void setAppcontent3(String appcontent3) {
		this.appcontent3 = appcontent3;
	}

	public Long getAppperson3() {
		return this.appperson3;
	}

	public void setAppperson3(Long appperson3) {
		this.appperson3 = appperson3;
	}

	public Date getApptime3() {
		return this.apptime3;
	}

	public void setApptime3(Date apptime3) {
		this.apptime3 = apptime3;
	}

	public Long getAppstate4() {
		return this.appstate4;
	}

	public void setAppstate4(Long appstate4) {
		this.appstate4 = appstate4;
	}

	public String getAppcontent4() {
		return this.appcontent4;
	}

	public void setAppcontent4(String appcontent4) {
		this.appcontent4 = appcontent4;
	}

	public Long getAppperson4() {
		return this.appperson4;
	}

	public void setAppperson4(Long appperson4) {
		this.appperson4 = appperson4;
	}

	public Date getApptime4() {
		return this.apptime4;
	}

	public void setApptime4(Date apptime4) {
		this.apptime4 = apptime4;
	}

	public Long getAppstate5() {
		return this.appstate5;
	}

	public void setAppstate5(Long appstate5) {
		this.appstate5 = appstate5;
	}

	public String getAppcontent5() {
		return this.appcontent5;
	}

	public void setAppcontent5(String appcontent5) {
		this.appcontent5 = appcontent5;
	}

	public Long getAppperson5() {
		return this.appperson5;
	}

	public void setAppperson5(Long appperson5) {
		this.appperson5 = appperson5;
	}

	public Date getApptime5() {
		return this.apptime5;
	}

	public void setApptime5(Date apptime5) {
		this.apptime5 = apptime5;
	}

	public Set getBizTInfoinsteads() {
		return this.bizTInfoinsteads;
	}

	public void setBizTInfoinsteads(Set bizTInfoinsteads) {
		this.bizTInfoinsteads = bizTInfoinsteads;
	}

}