package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * BizTRequest entity. @author MyEclipse Persistence Tools
 */

public class BizTRequest  implements java.io.Serializable {


    // Fields    

     private BigDecimal requestId;
     private InfoTFamily infoTFamily;
     private String reqidea;
     private BigDecimal reqmantype;
     private String reqman;
     private String reqmanpaperid;
     private String reqmantel;
     private BigDecimal reqdeptmanid;
     private Date reqdt;
     private String repflag;
     private BigDecimal operid;
     private Date operdt;
     private Set bizTRequestideas = new HashSet(0);


    // Constructors

    /** default constructor */
    public BizTRequest() {
    }

    
    /** full constructor */
    public BizTRequest(InfoTFamily infoTFamily, String reqidea, BigDecimal reqmantype, String reqman, String reqmanpaperid, String reqmantel, BigDecimal reqdeptmanid, Date reqdt, String repflag, BigDecimal operid, Date operdt, Set bizTRequestideas) {
        this.infoTFamily = infoTFamily;
        this.reqidea = reqidea;
        this.reqmantype = reqmantype;
        this.reqman = reqman;
        this.reqmanpaperid = reqmanpaperid;
        this.reqmantel = reqmantel;
        this.reqdeptmanid = reqdeptmanid;
        this.reqdt = reqdt;
        this.repflag = repflag;
        this.operid = operid;
        this.operdt = operdt;
        this.bizTRequestideas = bizTRequestideas;
    }

   
    // Property accessors

    public BigDecimal getRequestId() {
        return this.requestId;
    }
    
    public void setRequestId(BigDecimal requestId) {
        this.requestId = requestId;
    }

    public InfoTFamily getInfoTFamily() {
        return this.infoTFamily;
    }
    
    public void setInfoTFamily(InfoTFamily infoTFamily) {
        this.infoTFamily = infoTFamily;
    }

    public String getReqidea() {
        return this.reqidea;
    }
    
    public void setReqidea(String reqidea) {
        this.reqidea = reqidea;
    }

    public BigDecimal getReqmantype() {
        return this.reqmantype;
    }
    
    public void setReqmantype(BigDecimal reqmantype) {
        this.reqmantype = reqmantype;
    }

    public String getReqman() {
        return this.reqman;
    }
    
    public void setReqman(String reqman) {
        this.reqman = reqman;
    }

    public String getReqmanpaperid() {
        return this.reqmanpaperid;
    }
    
    public void setReqmanpaperid(String reqmanpaperid) {
        this.reqmanpaperid = reqmanpaperid;
    }

    public String getReqmantel() {
        return this.reqmantel;
    }
    
    public void setReqmantel(String reqmantel) {
        this.reqmantel = reqmantel;
    }

    public BigDecimal getReqdeptmanid() {
        return this.reqdeptmanid;
    }
    
    public void setReqdeptmanid(BigDecimal reqdeptmanid) {
        this.reqdeptmanid = reqdeptmanid;
    }

    public Date getReqdt() {
        return this.reqdt;
    }
    
    public void setReqdt(Date reqdt) {
        this.reqdt = reqdt;
    }

    public String getRepflag() {
        return this.repflag;
    }
    
    public void setRepflag(String repflag) {
        this.repflag = repflag;
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

    public Set getBizTRequestideas() {
        return this.bizTRequestideas;
    }
    
    public void setBizTRequestideas(Set bizTRequestideas) {
        this.bizTRequestideas = bizTRequestideas;
    }
   








}