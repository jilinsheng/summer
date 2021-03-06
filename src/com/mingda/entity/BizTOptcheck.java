package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * BizTOptcheck entity. @author MyEclipse Persistence Tools
 */

public class BizTOptcheck  implements java.io.Serializable {


    // Fields    

     private BigDecimal optcheckId;
     private DoperTPolicy doperTPolicy;
     private InfoTFamily infoTFamily;
     private InfoTMember infoTMember;
     private String acctype;
     private Double countmoney;
     private String moneyaout;
     private BigDecimal moneyflag;
     private Double checkmoney;
     private String checkflag1;
     private String checkflag2;
     private String checkflag3;
     private String checkflag4;
     private String checkflag5;
     private Double checkchildmoney;
     private Double adjustmoney;
     private String ifover;
     private String result;
     private BigDecimal resultoper;
     private Date resultdt;
     private Date rebegdt;
     private Date reenddt;
     private Double recheckmoney;
     private BigDecimal optoper;
     private Date optdt;


    // Constructors

    /** default constructor */
    public BizTOptcheck() {
    }

    
    /** full constructor */
    public BizTOptcheck(DoperTPolicy doperTPolicy, InfoTFamily infoTFamily, InfoTMember infoTMember, String acctype, Double countmoney, String moneyaout, BigDecimal moneyflag, Double checkmoney, String checkflag1, String checkflag2, String checkflag3, String checkflag4, String checkflag5, Double checkchildmoney, Double adjustmoney, String ifover, String result, BigDecimal resultoper, Date resultdt, Date rebegdt, Date reenddt, Double recheckmoney, BigDecimal optoper, Date optdt) {
        this.doperTPolicy = doperTPolicy;
        this.infoTFamily = infoTFamily;
        this.infoTMember = infoTMember;
        this.acctype = acctype;
        this.countmoney = countmoney;
        this.moneyaout = moneyaout;
        this.moneyflag = moneyflag;
        this.checkmoney = checkmoney;
        this.checkflag1 = checkflag1;
        this.checkflag2 = checkflag2;
        this.checkflag3 = checkflag3;
        this.checkflag4 = checkflag4;
        this.checkflag5 = checkflag5;
        this.checkchildmoney = checkchildmoney;
        this.adjustmoney = adjustmoney;
        this.ifover = ifover;
        this.result = result;
        this.resultoper = resultoper;
        this.resultdt = resultdt;
        this.rebegdt = rebegdt;
        this.reenddt = reenddt;
        this.recheckmoney = recheckmoney;
        this.optoper = optoper;
        this.optdt = optdt;
    }

   
    // Property accessors

    public BigDecimal getOptcheckId() {
        return this.optcheckId;
    }
    
    public void setOptcheckId(BigDecimal optcheckId) {
        this.optcheckId = optcheckId;
    }

    public DoperTPolicy getDoperTPolicy() {
        return this.doperTPolicy;
    }
    
    public void setDoperTPolicy(DoperTPolicy doperTPolicy) {
        this.doperTPolicy = doperTPolicy;
    }

    public InfoTFamily getInfoTFamily() {
        return this.infoTFamily;
    }
    
    public void setInfoTFamily(InfoTFamily infoTFamily) {
        this.infoTFamily = infoTFamily;
    }

    public InfoTMember getInfoTMember() {
        return this.infoTMember;
    }
    
    public void setInfoTMember(InfoTMember infoTMember) {
        this.infoTMember = infoTMember;
    }

    public String getAcctype() {
        return this.acctype;
    }
    
    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public Double getCountmoney() {
        return this.countmoney;
    }
    
    public void setCountmoney(Double countmoney) {
        this.countmoney = countmoney;
    }

    public String getMoneyaout() {
        return this.moneyaout;
    }
    
    public void setMoneyaout(String moneyaout) {
        this.moneyaout = moneyaout;
    }

    public BigDecimal getMoneyflag() {
        return this.moneyflag;
    }
    
    public void setMoneyflag(BigDecimal moneyflag) {
        this.moneyflag = moneyflag;
    }

    public Double getCheckmoney() {
        return this.checkmoney;
    }
    
    public void setCheckmoney(Double checkmoney) {
        this.checkmoney = checkmoney;
    }

    public String getCheckflag1() {
        return this.checkflag1;
    }
    
    public void setCheckflag1(String checkflag1) {
        this.checkflag1 = checkflag1;
    }

    public String getCheckflag2() {
        return this.checkflag2;
    }
    
    public void setCheckflag2(String checkflag2) {
        this.checkflag2 = checkflag2;
    }

    public String getCheckflag3() {
        return this.checkflag3;
    }
    
    public void setCheckflag3(String checkflag3) {
        this.checkflag3 = checkflag3;
    }

    public String getCheckflag4() {
        return this.checkflag4;
    }
    
    public void setCheckflag4(String checkflag4) {
        this.checkflag4 = checkflag4;
    }

    public String getCheckflag5() {
        return this.checkflag5;
    }
    
    public void setCheckflag5(String checkflag5) {
        this.checkflag5 = checkflag5;
    }

    public Double getCheckchildmoney() {
        return this.checkchildmoney;
    }
    
    public void setCheckchildmoney(Double checkchildmoney) {
        this.checkchildmoney = checkchildmoney;
    }

    public Double getAdjustmoney() {
        return this.adjustmoney;
    }
    
    public void setAdjustmoney(Double adjustmoney) {
        this.adjustmoney = adjustmoney;
    }

    public String getIfover() {
        return this.ifover;
    }
    
    public void setIfover(String ifover) {
        this.ifover = ifover;
    }

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public BigDecimal getResultoper() {
        return this.resultoper;
    }
    
    public void setResultoper(BigDecimal resultoper) {
        this.resultoper = resultoper;
    }

    public Date getResultdt() {
        return this.resultdt;
    }
    
    public void setResultdt(Date resultdt) {
        this.resultdt = resultdt;
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

    public Double getRecheckmoney() {
        return this.recheckmoney;
    }
    
    public void setRecheckmoney(Double recheckmoney) {
        this.recheckmoney = recheckmoney;
    }

    public BigDecimal getOptoper() {
        return this.optoper;
    }
    
    public void setOptoper(BigDecimal optoper) {
        this.optoper = optoper;
    }

    public Date getOptdt() {
        return this.optdt;
    }
    
    public void setOptdt(Date optdt) {
        this.optdt = optdt;
    }
   








}