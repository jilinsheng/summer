package com.mingda.entity;

import java.math.BigDecimal;


/**
 * BizTRepoptchecktype entity. @author MyEclipse Persistence Tools
 */

public class BizTRepoptchecktype  implements java.io.Serializable {


    // Fields    

     private BigDecimal repoptchecktypeId;
     private BigDecimal optaccId;
     private BigDecimal optchecktypeId;
     private BigDecimal optcheckideaId;
     private BigDecimal reqtype;


    // Constructors

    /** default constructor */
    public BizTRepoptchecktype() {
    }

    
    /** full constructor */
    public BizTRepoptchecktype(BigDecimal optaccId, BigDecimal optchecktypeId, BigDecimal optcheckideaId, BigDecimal reqtype) {
        this.optaccId = optaccId;
        this.optchecktypeId = optchecktypeId;
        this.optcheckideaId = optcheckideaId;
        this.reqtype = reqtype;
    }

   
    // Property accessors

    public BigDecimal getRepoptchecktypeId() {
        return this.repoptchecktypeId;
    }
    
    public void setRepoptchecktypeId(BigDecimal repoptchecktypeId) {
        this.repoptchecktypeId = repoptchecktypeId;
    }

    public BigDecimal getOptaccId() {
        return this.optaccId;
    }
    
    public void setOptaccId(BigDecimal optaccId) {
        this.optaccId = optaccId;
    }

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