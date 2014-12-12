package com.mingda.entity;

import java.math.BigDecimal;

/**
 * ImplTBill entity. @author MyEclipse Persistence Tools
 */

public class ImplTBill implements java.io.Serializable {

	// Fields

	private BigDecimal billId;
	private ImplTMonth implTMonth;
	private BigDecimal familyId;
	private String implmonth;
	private Double money;
	private String familyno;
	private String mastername;
	private String paperid;
	private BigDecimal orgno;
	private BigDecimal population;
	private String accouts;
	private String bankcode;
	private String familyaddress;

	// Constructors

	/** default constructor */
	public ImplTBill() {
	}

	/** full constructor */
	public ImplTBill(ImplTMonth implTMonth, BigDecimal familyId,
			String implmonth, Double money, String familyno, String mastername,
			String paperid, BigDecimal orgno, BigDecimal population,
			String accouts, String bankcode, String familyaddress) {
		this.implTMonth = implTMonth;
		this.familyId = familyId;
		this.implmonth = implmonth;
		this.money = money;
		this.familyno = familyno;
		this.mastername = mastername;
		this.paperid = paperid;
		this.orgno = orgno;
		this.population = population;
		this.accouts = accouts;
		this.bankcode = bankcode;
		this.familyaddress = familyaddress;
	}

	// Property accessors

	public BigDecimal getBillId() {
		return this.billId;
	}

	public void setBillId(BigDecimal billId) {
		this.billId = billId;
	}

	public ImplTMonth getImplTMonth() {
		return this.implTMonth;
	}

	public void setImplTMonth(ImplTMonth implTMonth) {
		this.implTMonth = implTMonth;
	}

	public BigDecimal getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(BigDecimal familyId) {
		this.familyId = familyId;
	}

	public String getImplmonth() {
		return this.implmonth;
	}

	public void setImplmonth(String implmonth) {
		this.implmonth = implmonth;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getFamilyno() {
		return this.familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getMastername() {
		return this.mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public String getPaperid() {
		return this.paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public BigDecimal getOrgno() {
		return this.orgno;
	}

	public void setOrgno(BigDecimal orgno) {
		this.orgno = orgno;
	}

	public BigDecimal getPopulation() {
		return this.population;
	}

	public void setPopulation(BigDecimal population) {
		this.population = population;
	}

	public String getAccouts() {
		return this.accouts;
	}

	public void setAccouts(String accouts) {
		this.accouts = accouts;
	}

	public String getBankcode() {
		return this.bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getFamilyaddress() {
		return this.familyaddress;
	}

	public void setFamilyaddress(String familyaddress) {
		this.familyaddress = familyaddress;
	}

}