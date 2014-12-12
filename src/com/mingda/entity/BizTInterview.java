package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * BizTInterview entity. @author MyEclipse Persistence Tools
 */

public class BizTInterview  implements java.io.Serializable {


    // Fields    

     private BigDecimal interviewId;
     private SysTOrganization sysTOrganization;
     private InfoTFamily infoTFamily;
     private String person;
     private String type;
     private String result;
     private Date viewdt;
     private BigDecimal operid;
     private Date operdt;


    // Constructors

    /** default constructor */
    public BizTInterview() {
    }

    
    /** full constructor */
    public BizTInterview(SysTOrganization sysTOrganization, InfoTFamily infoTFamily, String person, String type, String result, Date viewdt, BigDecimal operid, Date operdt) {
        this.sysTOrganization = sysTOrganization;
        this.infoTFamily = infoTFamily;
        this.person = person;
        this.type = type;
        this.result = result;
        this.viewdt = viewdt;
        this.operid = operid;
        this.operdt = operdt;
    }

   
    // Property accessors

    public BigDecimal getInterviewId() {
        return this.interviewId;
    }
    
    public void setInterviewId(BigDecimal interviewId) {
        this.interviewId = interviewId;
    }

    public SysTOrganization getSysTOrganization() {
        return this.sysTOrganization;
    }
    
    public void setSysTOrganization(SysTOrganization sysTOrganization) {
        this.sysTOrganization = sysTOrganization;
    }

    public InfoTFamily getInfoTFamily() {
        return this.infoTFamily;
    }
    
    public void setInfoTFamily(InfoTFamily infoTFamily) {
        this.infoTFamily = infoTFamily;
    }

    public String getPerson() {
        return this.person;
    }
    
    public void setPerson(String person) {
        this.person = person;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public Date getViewdt() {
        return this.viewdt;
    }
    
    public void setViewdt(Date viewdt) {
        this.viewdt = viewdt;
    }

    public BigDecimal getOperid() {
        return this.operid;
    }
    
    public void setOperid(BigDecimal operid) {
        this.operid = operid;
    }

    public Date getOperdt() {
        return this.operdt;
    }
    
    public void setOperdt(Date operdt) {
        this.operdt = operdt;
    }
   








}