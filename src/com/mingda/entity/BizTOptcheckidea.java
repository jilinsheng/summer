package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * BizTOptcheckidea entity. @author MyEclipse Persistence Tools
 */

public class BizTOptcheckidea  implements java.io.Serializable {


    // Fields    

     private BigDecimal optcheckideaId;
     private BigDecimal optcheckId;
     private BigDecimal depth;
     private String appideaman;
     private String apparea;
     private Date rebegdt;
     private Date reenddt;
     private BigDecimal appresult;
     private Date apptime;
     private String note;
     private BigDecimal checkoper;
     private Date checkdt;
     private String status;


    // Constructors

    /** default constructor */
    public BizTOptcheckidea() {
    }

    
    /** full constructor */
    public BizTOptcheckidea(BigDecimal optcheckId, BigDecimal depth, String appideaman, String apparea, Date rebegdt, Date reenddt, BigDecimal appresult, Date apptime, String note, BigDecimal checkoper, Date checkdt, String status) {
        this.optcheckId = optcheckId;
        this.depth = depth;
        this.appideaman = appideaman;
        this.apparea = apparea;
        this.rebegdt = rebegdt;
        this.reenddt = reenddt;
        this.appresult = appresult;
        this.apptime = apptime;
        this.note = note;
        this.checkoper = checkoper;
        this.checkdt = checkdt;
        this.status = status;
    }

   
    // Property accessors

    public BigDecimal getOptcheckideaId() {
        return this.optcheckideaId;
    }
    
    public void setOptcheckideaId(BigDecimal optcheckideaId) {
        this.optcheckideaId = optcheckideaId;
    }

    public BigDecimal getOptcheckId() {
        return this.optcheckId;
    }
    
    public void setOptcheckId(BigDecimal optcheckId) {
        this.optcheckId = optcheckId;
    }

    public BigDecimal getDepth() {
        return this.depth;
    }
    
    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    public String getAppideaman() {
        return this.appideaman;
    }
    
    public void setAppideaman(String appideaman) {
        this.appideaman = appideaman;
    }

    public String getApparea() {
        return this.apparea;
    }
    
    public void setApparea(String apparea) {
        this.apparea = apparea;
    }

    public Date getRebegdt() {
        return this.rebegdt;
    }
    
    public void setRebegdt(Date rebegdt) {
        this.rebegdt = rebegdt;
    }

    public Date getReenddt() {
        return this.reenddt;
    }
    
    public void setReenddt(Date reenddt) {
        this.reenddt = reenddt;
    }

    public BigDecimal getAppresult() {
        return this.appresult;
    }
    
    public void setAppresult(BigDecimal appresult) {
        this.appresult = appresult;
    }

    public Date getApptime() {
        return this.apptime;
    }
    
    public void setApptime(Date apptime) {
        this.apptime = apptime;
    }

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getCheckoper() {
        return this.checkoper;
    }
    
    public void setCheckoper(BigDecimal checkoper) {
        this.checkoper = checkoper;
    }

    public Date getCheckdt() {
        return this.checkdt;
    }
    
    public void setCheckdt(Date checkdt) {
        this.checkdt = checkdt;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}