package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * BizTMasterinstead entity. @author MyEclipse Persistence Tools
 */

public class BizTMasterinstead  implements java.io.Serializable {


    // Fields    

     private BigDecimal masterinsteadId;
     private InfoTFamily infoTFamily;
     private String omastername;
     private String omasterpaperid;
     private String nmastername;
     private String nmasterpaperid;
     private Date opttime;


    // Constructors

    /** default constructor */
    public BizTMasterinstead() {
    }

    
    /** full constructor */
    public BizTMasterinstead(InfoTFamily infoTFamily, String omastername, String omasterpaperid, String nmastername, String nmasterpaperid, Date opttime) {
        this.infoTFamily = infoTFamily;
        this.omastername = omastername;
        this.omasterpaperid = omasterpaperid;
        this.nmastername = nmastername;
        this.nmasterpaperid = nmasterpaperid;
        this.opttime = opttime;
    }

   
    // Property accessors

    public BigDecimal getMasterinsteadId() {
        return this.masterinsteadId;
    }
    
    public void setMasterinsteadId(BigDecimal masterinsteadId) {
        this.masterinsteadId = masterinsteadId;
    }

    public InfoTFamily getInfoTFamily() {
        return this.infoTFamily;
    }
    
    public void setInfoTFamily(InfoTFamily infoTFamily) {
        this.infoTFamily = infoTFamily;
    }

    public String getOmastername() {
        return this.omastername;
    }
    
    public void setOmastername(String omastername) {
        this.omastername = omastername;
    }

    public String getOmasterpaperid() {
        return this.omasterpaperid;
    }
    
    public void setOmasterpaperid(String omasterpaperid) {
        this.omasterpaperid = omasterpaperid;
    }

    public String getNmastername() {
        return this.nmastername;
    }
    
    public void setNmastername(String nmastername) {
        this.nmastername = nmastername;
    }

    public String getNmasterpaperid() {
        return this.nmasterpaperid;
    }
    
    public void setNmasterpaperid(String nmasterpaperid) {
        this.nmasterpaperid = nmasterpaperid;
    }

    public Date getOpttime() {
        return this.opttime;
    }
    
    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }
   








}