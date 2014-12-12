package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * BizTOptcheckchildidea entity. @author MyEclipse Persistence Tools
 */

public class BizTOptcheckchildidea  implements java.io.Serializable {


    // Fields    

     private BigDecimal optcheckchildideaId;
     private BigDecimal optcheckchilditemId;
     private BigDecimal policychildId;
     private BigDecimal depth;
     private Double childmoney;
     private Date childapptime;
     private String childnote;
     private BigDecimal childcheckoper;
     private Date childcheckdt;
     private String status;


    // Constructors

    /** default constructor */
    public BizTOptcheckchildidea() {
    }

    
    /** full constructor */
    public BizTOptcheckchildidea(BigDecimal optcheckchilditemId, BigDecimal policychildId, BigDecimal depth, Double childmoney, Date childapptime, String childnote, BigDecimal childcheckoper, Date childcheckdt, String status) {
        this.optcheckchilditemId = optcheckchilditemId;
        this.policychildId = policychildId;
        this.depth = depth;
        this.childmoney = childmoney;
        this.childapptime = childapptime;
        this.childnote = childnote;
        this.childcheckoper = childcheckoper;
        this.childcheckdt = childcheckdt;
        this.status = status;
    }

   
    // Property accessors

    public BigDecimal getOptcheckchildideaId() {
        return this.optcheckchildideaId;
    }
    
    public void setOptcheckchildideaId(BigDecimal optcheckchildideaId) {
        this.optcheckchildideaId = optcheckchildideaId;
    }

    public BigDecimal getOptcheckchilditemId() {
        return this.optcheckchilditemId;
    }
    
    public void setOptcheckchilditemId(BigDecimal optcheckchilditemId) {
        this.optcheckchilditemId = optcheckchilditemId;
    }

    public BigDecimal getPolicychildId() {
        return this.policychildId;
    }
    
    public void setPolicychildId(BigDecimal policychildId) {
        this.policychildId = policychildId;
    }

    public BigDecimal getDepth() {
        return this.depth;
    }
    
    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    public Double getChildmoney() {
        return this.childmoney;
    }
    
    public void setChildmoney(Double childmoney) {
        this.childmoney = childmoney;
    }

    public Date getChildapptime() {
        return this.childapptime;
    }
    
    public void setChildapptime(Date childapptime) {
        this.childapptime = childapptime;
    }

    public String getChildnote() {
        return this.childnote;
    }
    
    public void setChildnote(String childnote) {
        this.childnote = childnote;
    }

    public BigDecimal getChildcheckoper() {
        return this.childcheckoper;
    }
    
    public void setChildcheckoper(BigDecimal childcheckoper) {
        this.childcheckoper = childcheckoper;
    }

    public Date getChildcheckdt() {
        return this.childcheckdt;
    }
    
    public void setChildcheckdt(Date childcheckdt) {
        this.childcheckdt = childcheckdt;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}