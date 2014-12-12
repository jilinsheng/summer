package com.mingda.entity;

import java.math.BigDecimal;


/**
 * BizTRequestidea entity. @author MyEclipse Persistence Tools
 */

public class BizTRequestidea  implements java.io.Serializable {


    // Fields    

     private BigDecimal requestideaId;
     private BizTRequest bizTRequest;
     private BigDecimal reqtype;
     private String flag;


    // Constructors

    /** default constructor */
    public BizTRequestidea() {
    }

    
    /** full constructor */
    public BizTRequestidea(BizTRequest bizTRequest, BigDecimal reqtype, String flag) {
        this.bizTRequest = bizTRequest;
        this.reqtype = reqtype;
        this.flag = flag;
    }

   
    // Property accessors

    public BigDecimal getRequestideaId() {
        return this.requestideaId;
    }
    
    public void setRequestideaId(BigDecimal requestideaId) {
        this.requestideaId = requestideaId;
    }

    public BizTRequest getBizTRequest() {
        return this.bizTRequest;
    }
    
    public void setBizTRequest(BizTRequest bizTRequest) {
        this.bizTRequest = bizTRequest;
    }

    public BigDecimal getReqtype() {
        return this.reqtype;
    }
    
    public void setReqtype(BigDecimal reqtype) {
        this.reqtype = reqtype;
    }

    public String getFlag() {
        return this.flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag;
    }
   








}