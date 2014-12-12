package com.mingda.entity;

import java.math.BigDecimal;


/**
 * BizTRepoptcheckchild entity. @author MyEclipse Persistence Tools
 */

public class BizTRepoptcheckchild  implements java.io.Serializable {


    // Fields    

     private BigDecimal repoptcheckchildId;
     private BigDecimal optaccId;
     private BigDecimal optcheckchildId;
     private BigDecimal optcheckId;
     private BigDecimal familyId;
     private Double checkchildmoney;
     private Double checkchildmoney1;
     private Double checkchildmoney2;
     private Double checkchildmoney3;
     private Double checkchildmoney4;
     private Double checkchildmoney5;


    // Constructors

    /** default constructor */
    public BizTRepoptcheckchild() {
    }

    
    /** full constructor */
    public BizTRepoptcheckchild(BigDecimal optaccId, BigDecimal optcheckchildId, BigDecimal optcheckId, BigDecimal familyId, Double checkchildmoney, Double checkchildmoney1, Double checkchildmoney2, Double checkchildmoney3, Double checkchildmoney4, Double checkchildmoney5) {
        this.optaccId = optaccId;
        this.optcheckchildId = optcheckchildId;
        this.optcheckId = optcheckId;
        this.familyId = familyId;
        this.checkchildmoney = checkchildmoney;
        this.checkchildmoney1 = checkchildmoney1;
        this.checkchildmoney2 = checkchildmoney2;
        this.checkchildmoney3 = checkchildmoney3;
        this.checkchildmoney4 = checkchildmoney4;
        this.checkchildmoney5 = checkchildmoney5;
    }

   
    // Property accessors

    public BigDecimal getRepoptcheckchildId() {
        return this.repoptcheckchildId;
    }
    
    public void setRepoptcheckchildId(BigDecimal repoptcheckchildId) {
        this.repoptcheckchildId = repoptcheckchildId;
    }

    public BigDecimal getOptaccId() {
        return this.optaccId;
    }
    
    public void setOptaccId(BigDecimal optaccId) {
        this.optaccId = optaccId;
    }

    public BigDecimal getOptcheckchildId() {
        return this.optcheckchildId;
    }
    
    public void setOptcheckchildId(BigDecimal optcheckchildId) {
        this.optcheckchildId = optcheckchildId;
    }

    public BigDecimal getOptcheckId() {
        return this.optcheckId;
    }
    
    public void setOptcheckId(BigDecimal optcheckId) {
        this.optcheckId = optcheckId;
    }

    public BigDecimal getFamilyId() {
        return this.familyId;
    }
    
    public void setFamilyId(BigDecimal familyId) {
        this.familyId = familyId;
    }

    public Double getCheckchildmoney() {
        return this.checkchildmoney;
    }
    
    public void setCheckchildmoney(Double checkchildmoney) {
        this.checkchildmoney = checkchildmoney;
    }

    public Double getCheckchildmoney1() {
        return this.checkchildmoney1;
    }
    
    public void setCheckchildmoney1(Double checkchildmoney1) {
        this.checkchildmoney1 = checkchildmoney1;
    }

    public Double getCheckchildmoney2() {
        return this.checkchildmoney2;
    }
    
    public void setCheckchildmoney2(Double checkchildmoney2) {
        this.checkchildmoney2 = checkchildmoney2;
    }

    public Double getCheckchildmoney3() {
        return this.checkchildmoney3;
    }
    
    public void setCheckchildmoney3(Double checkchildmoney3) {
        this.checkchildmoney3 = checkchildmoney3;
    }

    public Double getCheckchildmoney4() {
        return this.checkchildmoney4;
    }
    
    public void setCheckchildmoney4(Double checkchildmoney4) {
        this.checkchildmoney4 = checkchildmoney4;
    }

    public Double getCheckchildmoney5() {
        return this.checkchildmoney5;
    }
    
    public void setCheckchildmoney5(Double checkchildmoney5) {
        this.checkchildmoney5 = checkchildmoney5;
    }
   








}