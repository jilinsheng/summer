package com.mingda.common.ibatis.data;

import java.math.BigDecimal;

public class InfoTFamilyincome {

	private BigDecimal familyincomeId;

	private BigDecimal familyId;

	private Double income1 = new Double("0.0");

	private Double income2 = new Double("0.0");

	private Double income3 = new Double("0.0");

	private Double income4 = new Double("0.0");

	private Double income5 = new Double("0.0");

	private Double allincome = new Double("0.0");

	private Double avgincome = new Double("0.0");

	public BigDecimal getFamilyId() {
		return familyId;
	}

	public BigDecimal getFamilyincomeId() {
		return familyincomeId;
	}

	public void setFamilyincomeId(BigDecimal familyincomeId) {
		this.familyincomeId = familyincomeId;
	}

	public void setFamilyId(BigDecimal familyId) {
		this.familyId = familyId;
	}

	public Double getIncome1() {
		return income1;
	}

	public void setIncome1(Double income1) {
		this.income1 = income1;
	}

	public Double getIncome2() {
		return income2;
	}

	public void setIncome2(Double income2) {
		this.income2 = income2;
	}

	public Double getIncome3() {
		return income3;
	}

	public void setIncome3(Double income3) {
		this.income3 = income3;
	}

	public Double getIncome4() {
		return income4;
	}

	public void setIncome4(Double income4) {
		this.income4 = income4;
	}

	public Double getIncome5() {
		return income5;
	}

	public void setIncome5(Double income5) {
		this.income5 = income5;
	}

	public Double getAllincome() {
		return allincome;
	}

	public void setAllincome(Double allincome) {
		this.allincome = allincome;
	}

	public Double getAvgincome() {
		return avgincome;
	}

	public void setAvgincome(Double avgincome) {
		this.avgincome = avgincome;
	}


}