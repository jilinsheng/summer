package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * BizTRepoptcheckchilditem entity. @author MyEclipse Persistence Tools
 */

public class BizTRepoptcheckchilditem  implements java.io.Serializable {


    // Fields    

     private BigDecimal repoptcheckchilditemId;
     private BigDecimal optaccId;
     private BigDecimal optcheckchilditemId;
     private BigDecimal optcheckchildId;
     private BigDecimal policychildId;
     private Double itemmoney1;
     private Double itemmoney2;
     private Double itemmoney3;
     private Double itemmoney4;
     private Double itemmoney5;
     private Double itemmoney;
     private BigDecimal itemresultoper;
     private Date itemresultdt;


    // Constructors

    /** default constructor */
    public BizTRepoptcheckchilditem() {
    }

    
    /** full constructor */
    public BizTRepoptcheckchilditem(BigDecimal optaccId, BigDecimal optcheckchilditemId, BigDecimal optcheckchildId, BigDecimal policychildId, Double itemmoney1, Double itemmoney2, Double itemmoney3, Double itemmoney4, Double itemmoney5, Double itemmoney, BigDecimal itemresultoper, Date itemresultdt) {
        this.optaccId = optaccId;
        this.optcheckchilditemId = optcheckchilditemId;
        this.optcheckchildId = optcheckchildId;
        this.policychildId = policychildId;
        this.itemmoney1 = itemmoney1;
        this.itemmoney2 = itemmoney2;
        this.itemmoney3 = itemmoney3;
        this.itemmoney4 = itemmoney4;
        this.itemmoney5 = itemmoney5;
        this.itemmoney = itemmoney;
        this.itemresultoper = itemresultoper;
        this.itemresultdt = itemresultdt;
    }

   
    // Property accessors

    public BigDecimal getRepoptcheckchilditemId() {
        return this.repoptcheckchilditemId;
    }
    
    public void setRepoptcheckchilditemId(BigDecimal repoptcheckchilditemId) {
        this.repoptcheckchilditemId = repoptcheckchilditemId;
    }

    public BigDecimal getOptaccId() {
        return this.optaccId;
    }
    
    public void setOptaccId(BigDecimal optaccId) {
        this.optaccId = optaccId;
    }

    public BigDecimal getOptcheckchilditemId() {
        return this.optcheckchilditemId;
    }
    
    public void setOptcheckchilditemId(BigDecimal optcheckchilditemId) {
        this.optcheckchilditemId = optcheckchilditemId;
    }

    public BigDecimal getOptcheckchildId() {
        return this.optcheckchildId;
    }
    
    public void setOptcheckchildId(BigDecimal optcheckchildId) {
        this.optcheckchildId = optcheckchildId;
    }

    public BigDecimal getPolicychildId() {
        return this.policychildId;
    }
    
    public void setPolicychildId(BigDecimal policychildId) {
        this.policychildId = policychildId;
    }

    public Double getItemmoney1() {
        return this.itemmoney1;
    }
    
    public void setItemmoney1(Double itemmoney1) {
        this.itemmoney1 = itemmoney1;
    }

    public Double getItemmoney2() {
        return this.itemmoney2;
    }
    
    public void setItemmoney2(Double itemmoney2) {
        this.itemmoney2 = itemmoney2;
    }

    public Double getItemmoney3() {
        return this.itemmoney3;
    }
    
    public void setItemmoney3(Double itemmoney3) {
        this.itemmoney3 = itemmoney3;
    }

    public Double getItemmoney4() {
        return this.itemmoney4;
    }
    
    public void setItemmoney4(Double itemmoney4) {
        this.itemmoney4 = itemmoney4;
    }

    public Double getItemmoney5() {
        return this.itemmoney5;
    }
    
    public void setItemmoney5(Double itemmoney5) {
        this.itemmoney5 = itemmoney5;
    }

    public Double getItemmoney() {
        return this.itemmoney;
    }
    
    public void setItemmoney(Double itemmoney) {
        this.itemmoney = itemmoney;
    }

    public BigDecimal getItemresultoper() {
        return this.itemresultoper;
    }
    
    public void setItemresultoper(BigDecimal itemresultoper) {
        this.itemresultoper = itemresultoper;
    }

    public Date getItemresultdt() {
        return this.itemresultdt;
    }
    
    public void setItemresultdt(Date itemresultdt) {
        this.itemresultdt = itemresultdt;
    }
   








}