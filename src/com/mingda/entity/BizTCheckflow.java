package com.mingda.entity;

import java.math.BigDecimal;


/**
 * BizTCheckflow entity. @author MyEclipse Persistence Tools
 */

public class BizTCheckflow  implements java.io.Serializable {


    // Fields    

     private BigDecimal checkflowId;
     private DoperTPolicy doperTPolicy;
     private String accdept;
     private String useraccflag;
     private String checkflag;
     private BigDecimal alllimit;
     private String appstate1;
     private BigDecimal limit1;
     private String appstate2;
     private BigDecimal limit2;
     private String appstate3;
     private BigDecimal limit3;
     private String appstate4;
     private BigDecimal limit4;
     private String appstate5;
     private BigDecimal limit5;


    // Constructors

    /** default constructor */
    public BizTCheckflow() {
    }

    
    /** full constructor */
    public BizTCheckflow(DoperTPolicy doperTPolicy, String accdept, String useraccflag, String checkflag, BigDecimal alllimit, String appstate1, BigDecimal limit1, String appstate2, BigDecimal limit2, String appstate3, BigDecimal limit3, String appstate4, BigDecimal limit4, String appstate5, BigDecimal limit5) {
        this.doperTPolicy = doperTPolicy;
        this.accdept = accdept;
        this.useraccflag = useraccflag;
        this.checkflag = checkflag;
        this.alllimit = alllimit;
        this.appstate1 = appstate1;
        this.limit1 = limit1;
        this.appstate2 = appstate2;
        this.limit2 = limit2;
        this.appstate3 = appstate3;
        this.limit3 = limit3;
        this.appstate4 = appstate4;
        this.limit4 = limit4;
        this.appstate5 = appstate5;
        this.limit5 = limit5;
    }

   
    // Property accessors

    public BigDecimal getCheckflowId() {
        return this.checkflowId;
    }
    
    public void setCheckflowId(BigDecimal checkflowId) {
        this.checkflowId = checkflowId;
    }

    public DoperTPolicy getDoperTPolicy() {
        return this.doperTPolicy;
    }
    
    public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
        this.doperTPolicy = doperTPolicy;
    }

    public String getAccdept() {
        return this.accdept;
    }
    
    public void setAccdept(String accdept) {
        this.accdept = accdept;
    }

    public String getUseraccflag() {
        return this.useraccflag;
    }
    
    public void setUseraccflag(String useraccflag) {
        this.useraccflag = useraccflag;
    }

    public String getCheckflag() {
        return this.checkflag;
    }
    
    public void setCheckflag(String checkflag) {
        this.checkflag = checkflag;
    }

    public BigDecimal getAlllimit() {
        return this.alllimit;
    }
    
    public void setAlllimit(BigDecimal alllimit) {
        this.alllimit = alllimit;
    }

    public String getAppstate1() {
        return this.appstate1;
    }
    
    public void setAppstate1(String appstate1) {
        this.appstate1 = appstate1;
    }

    public BigDecimal getLimit1() {
        return this.limit1;
    }
    
    public void setLimit1(BigDecimal limit1) {
        this.limit1 = limit1;
    }

    public String getAppstate2() {
        return this.appstate2;
    }
    
    public void setAppstate2(String appstate2) {
        this.appstate2 = appstate2;
    }

    public BigDecimal getLimit2() {
        return this.limit2;
    }
    
    public void setLimit2(BigDecimal limit2) {
        this.limit2 = limit2;
    }

    public String getAppstate3() {
        return this.appstate3;
    }
    
    public void setAppstate3(String appstate3) {
        this.appstate3 = appstate3;
    }

    public BigDecimal getLimit3() {
        return this.limit3;
    }
    
    public void setLimit3(BigDecimal limit3) {
        this.limit3 = limit3;
    }

    public String getAppstate4() {
        return this.appstate4;
    }
    
    public void setAppstate4(String appstate4) {
        this.appstate4 = appstate4;
    }

    public BigDecimal getLimit4() {
        return this.limit4;
    }
    
    public void setLimit4(BigDecimal limit4) {
        this.limit4 = limit4;
    }

    public String getAppstate5() {
        return this.appstate5;
    }
    
    public void setAppstate5(String appstate5) {
        this.appstate5 = appstate5;
    }

    public BigDecimal getLimit5() {
        return this.limit5;
    }
    
    public void setLimit5(BigDecimal limit5) {
        this.limit5 = limit5;
    }
   








}