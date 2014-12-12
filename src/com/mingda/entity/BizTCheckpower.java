package com.mingda.entity;

import java.math.BigDecimal;


/**
 * BizTCheckpower entity. @author MyEclipse Persistence Tools
 */

public class BizTCheckpower  implements java.io.Serializable {


    // Fields    

     private BigDecimal checkpowerId;
     private SysTPost sysTPost;
     private DoperTPolicy doperTPolicy;
     private String checkflag;
     private String reportflag;
     private String checkmoreflag;


    // Constructors

    /** default constructor */
    public BizTCheckpower() {
    }

    
    /** full constructor */
    public BizTCheckpower(SysTPost sysTPost, DoperTPolicy doperTPolicy, String checkflag, String reportflag, String checkmoreflag) {
        this.sysTPost = sysTPost;
        this.doperTPolicy = doperTPolicy;
        this.checkflag = checkflag;
        this.reportflag = reportflag;
        this.checkmoreflag = checkmoreflag;
    }

   
    // Property accessors

    public BigDecimal getCheckpowerId() {
        return this.checkpowerId;
    }
    
    public void setCheckpowerId(BigDecimal checkpowerId) {
        this.checkpowerId = checkpowerId;
    }

    public SysTPost getSysTPost() {
        return this.sysTPost;
    }
    
    public void setSysTPost(SysTPost sysTPost) {
        this.sysTPost = sysTPost;
    }

    public DoperTPolicy getDoperTPolicy() {
        return this.doperTPolicy;
    }
    
    public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
        this.doperTPolicy = doperTPolicy;
    }

    public String getCheckflag() {
        return this.checkflag;
    }
    
    public void setCheckflag(String checkflag) {
        this.checkflag = checkflag;
    }

    public String getReportflag() {
        return this.reportflag;
    }
    
    public void setReportflag(String reportflag) {
        this.reportflag = reportflag;
    }

    public String getCheckmoreflag() {
        return this.checkmoreflag;
    }
    
    public void setCheckmoreflag(String checkmoreflag) {
        this.checkmoreflag = checkmoreflag;
    }
   








}