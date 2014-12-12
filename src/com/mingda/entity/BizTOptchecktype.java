package com.mingda.entity;

import java.math.BigDecimal;


/**
 * BizTOptchecktype entity. @author MyEclipse Persistence Tools
 */

public class BizTOptchecktype  implements java.io.Serializable {


    // Fields    

     private BigDecimal optchecktypeId;
     private BigDecimal optcheckideaId;
     private BigDecimal reqtype;


    // Constructors

    /** default constructor */
    public BizTOptchecktype() {
    }

    
    /** full constructor */
    public BizTOptchecktype(BigDecimal optcheckideaId, BigDecimal reqtype) {
        this.optcheckideaId = optcheckideaId;
        this.reqtype = reqtype;
    }

   
    // Property accessors

    public BigDecimal getOptchecktypeId() {
        return this.optchecktypeId;
    }
    
    public void setOptchecktypeId(BigDecimal optchecktypeId) {
        this.optchecktypeId = optchecktypeId;
    }

    public BigDecimal getOptcheckideaId() {
        return this.optcheckideaId;
    }
    
    public void setOptcheckideaId(BigDecimal optcheckideaId) {
        this.optcheckideaId = optcheckideaId;
    }

    public BigDecimal getReqtype() {
        return this.reqtype;
    }
    
    public void setReqtype(BigDecimal reqtype) {
        this.reqtype = reqtype;
    }
   








}