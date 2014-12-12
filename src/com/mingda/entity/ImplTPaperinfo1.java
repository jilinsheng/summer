package com.mingda.entity;

import java.math.BigDecimal;

/**
 * ImplTPaperinfo1 entity. @author MyEclipse Persistence Tools
 */

public class ImplTPaperinfo1 implements java.io.Serializable {

	// Fields

	private Long paperinfo1Id;
	private ImplTPaperrecord implTPaperrecord;
	private String mastername;
	private String masterpapertye;
	private String masterpapercode;
	private String familyno;
	private String serial;
	private String memsort;
	private String age;
	private String begintime;
	private String paperkind;
	private String paperdate;
	private String population;
	private String ensure;
	private String address;
	private String resiaddr;
	private String masterunit;
	private String masterunittype;
	private String memname1;
	private String memsex1;
	private String memage1;
	private String memrelmaster1;
	private String mempaper1;
	private String memsort1;
	private String memname2;
	private String memsex2;
	private String memage2;
	private String memrelmaster2;
	private String mempaper2;
	private String memsort2;
	private String memname3;
	private String memsex3;
	private String memage3;
	private String memrelmaster3;
	private String mempaper3;
	private String memsort3;
	private String memname4;
	private String memsex4;
	private String memage4;
	private String memrelmaster4;
	private String mempaper4;
	private String memsort4;
	private String memname5;
	private String memsex5;
	private String memage5;
	private String memrelmaster5;
	private String mempaper5;
	private String memsort5;
	private String memunittype1;
	private String memunittype2;
	private String memunittype3;
	private String memunittype4;
	private String memunittype5;
	private String memunitname1;
	private String memunitname2;
	private String memunitname3;
	private String memunitname4;
	private String memunitname5;
	private Long money;
	private String orgname;
	// Constructors

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	/** default constructor */
	public ImplTPaperinfo1() {
	}

	/** full constructor */
	public ImplTPaperinfo1(ImplTPaperrecord implTPaperrecord,
			String mastername, String masterpapertye, String masterpapercode,
			String familyno, String serial, String memsort, String age,
			String begintime, String paperkind, String paperdate,
			String population, String ensure, String address, String resiaddr,
			String masterunit, String memname1, String memsex1, String memage1,
			String memrelmaster1, String mempaper1, String memsort1,
			String memname2, String memsex2, String memage2,
			String memrelmaster2, String mempaper2, String memsort2,
			String memname3, String memsex3, String memage3,
			String memrelmaster3, String mempaper3, String memsort3,
			String memname4, String memsex4, String memage4,
			String memrelmaster4, String mempaper4, String memsort4,
			String memname5, String memsex5, String memage5,
			String memrelmaster5, String mempaper5, String memsort5) {
		this.implTPaperrecord = implTPaperrecord;
		this.mastername = mastername;
		this.masterpapertye = masterpapertye;
		this.masterpapercode = masterpapercode;
		this.familyno = familyno;
		this.serial = serial;
		this.memsort = memsort;
		this.age = age;
		this.begintime = begintime;
		this.paperkind = paperkind;
		this.paperdate = paperdate;
		this.population = population;
		this.ensure = ensure;
		this.address = address;
		this.resiaddr = resiaddr;
		this.masterunit = masterunit;
		this.memname1 = memname1;
		this.memsex1 = memsex1;
		this.memage1 = memage1;
		this.memrelmaster1 = memrelmaster1;
		this.mempaper1 = mempaper1;
		this.memsort1 = memsort1;
		this.memname2 = memname2;
		this.memsex2 = memsex2;
		this.memage2 = memage2;
		this.memrelmaster2 = memrelmaster2;
		this.mempaper2 = mempaper2;
		this.memsort2 = memsort2;
		this.memname3 = memname3;
		this.memsex3 = memsex3;
		this.memage3 = memage3;
		this.memrelmaster3 = memrelmaster3;
		this.mempaper3 = mempaper3;
		this.memsort3 = memsort3;
		this.memname4 = memname4;
		this.memsex4 = memsex4;
		this.memage4 = memage4;
		this.memrelmaster4 = memrelmaster4;
		this.mempaper4 = mempaper4;
		this.memsort4 = memsort4;
		this.memname5 = memname5;
		this.memsex5 = memsex5;
		this.memage5 = memage5;
		this.memrelmaster5 = memrelmaster5;
		this.mempaper5 = mempaper5;
		this.memsort5 = memsort5;
	}

	// Property accessors

	public Long getPaperinfo1Id() {
		return this.paperinfo1Id;
	}

	public void setPaperinfo1Id(Long paperinfo1Id) {
		this.paperinfo1Id = paperinfo1Id;
	}

	public ImplTPaperrecord getImplTPaperrecord() {
		return this.implTPaperrecord;
	}

	public void setImplTPaperrecord(ImplTPaperrecord implTPaperrecord) {
		this.implTPaperrecord = implTPaperrecord;
	}

	public String getMastername() {
		return this.mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public String getMasterpapertye() {
		return this.masterpapertye;
	}

	public void setMasterpapertye(String masterpapertye) {
		this.masterpapertye = masterpapertye;
	}

	public String getMasterpapercode() {
		return this.masterpapercode;
	}

	public void setMasterpapercode(String masterpapercode) {
		this.masterpapercode = masterpapercode;
	}

	public String getFamilyno() {
		return this.familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getMemsort() {
		return this.memsort;
	}

	public void setMemsort(String memsort) {
		this.memsort = memsort;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBegintime() {
		return this.begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getPaperkind() {
		return this.paperkind;
	}

	public void setPaperkind(String paperkind) {
		this.paperkind = paperkind;
	}

	public String getPaperdate() {
		return this.paperdate;
	}

	public void setPaperdate(String paperdate) {
		this.paperdate = paperdate;
	}

	public String getPopulation() {
		return this.population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getEnsure() {
		return this.ensure;
	}

	public void setEnsure(String ensure) {
		this.ensure = ensure;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResiaddr() {
		return this.resiaddr;
	}

	public void setResiaddr(String resiaddr) {
		this.resiaddr = resiaddr;
	}

	public String getMasterunit() {
		return this.masterunit;
	}

	public void setMasterunit(String masterunit) {
		this.masterunit = masterunit;
	}

	public String getMemname1() {
		return this.memname1;
	}

	public void setMemname1(String memname1) {
		this.memname1 = memname1;
	}

	public String getMemsex1() {
		return this.memsex1;
	}

	public void setMemsex1(String memsex1) {
		this.memsex1 = memsex1;
	}

	public String getMemage1() {
		return this.memage1;
	}

	public void setMemage1(String memage1) {
		this.memage1 = memage1;
	}

	public String getMemrelmaster1() {
		return this.memrelmaster1;
	}

	public void setMemrelmaster1(String memrelmaster1) {
		this.memrelmaster1 = memrelmaster1;
	}

	public String getMempaper1() {
		return this.mempaper1;
	}

	public void setMempaper1(String mempaper1) {
		this.mempaper1 = mempaper1;
	}

	public String getMemsort1() {
		return this.memsort1;
	}

	public void setMemsort1(String memsort1) {
		this.memsort1 = memsort1;
	}

	public String getMemname2() {
		return this.memname2;
	}

	public void setMemname2(String memname2) {
		this.memname2 = memname2;
	}

	public String getMemsex2() {
		return this.memsex2;
	}

	public void setMemsex2(String memsex2) {
		this.memsex2 = memsex2;
	}

	public String getMemage2() {
		return this.memage2;
	}

	public void setMemage2(String memage2) {
		this.memage2 = memage2;
	}

	public String getMemrelmaster2() {
		return this.memrelmaster2;
	}

	public void setMemrelmaster2(String memrelmaster2) {
		this.memrelmaster2 = memrelmaster2;
	}

	public String getMempaper2() {
		return this.mempaper2;
	}

	public void setMempaper2(String mempaper2) {
		this.mempaper2 = mempaper2;
	}

	public String getMemsort2() {
		return this.memsort2;
	}

	public void setMemsort2(String memsort2) {
		this.memsort2 = memsort2;
	}

	public String getMemname3() {
		return this.memname3;
	}

	public void setMemname3(String memname3) {
		this.memname3 = memname3;
	}

	public String getMemsex3() {
		return this.memsex3;
	}

	public void setMemsex3(String memsex3) {
		this.memsex3 = memsex3;
	}

	public String getMemage3() {
		return this.memage3;
	}

	public void setMemage3(String memage3) {
		this.memage3 = memage3;
	}

	public String getMemrelmaster3() {
		return this.memrelmaster3;
	}

	public void setMemrelmaster3(String memrelmaster3) {
		this.memrelmaster3 = memrelmaster3;
	}

	public String getMempaper3() {
		return this.mempaper3;
	}

	public void setMempaper3(String mempaper3) {
		this.mempaper3 = mempaper3;
	}

	public String getMemsort3() {
		return this.memsort3;
	}

	public void setMemsort3(String memsort3) {
		this.memsort3 = memsort3;
	}

	public String getMemname4() {
		return this.memname4;
	}

	public void setMemname4(String memname4) {
		this.memname4 = memname4;
	}

	public String getMemsex4() {
		return this.memsex4;
	}

	public void setMemsex4(String memsex4) {
		this.memsex4 = memsex4;
	}

	public String getMemage4() {
		return this.memage4;
	}

	public void setMemage4(String memage4) {
		this.memage4 = memage4;
	}

	public String getMemrelmaster4() {
		return this.memrelmaster4;
	}

	public void setMemrelmaster4(String memrelmaster4) {
		this.memrelmaster4 = memrelmaster4;
	}

	public String getMempaper4() {
		return this.mempaper4;
	}

	public void setMempaper4(String mempaper4) {
		this.mempaper4 = mempaper4;
	}

	public String getMemsort4() {
		return this.memsort4;
	}

	public void setMemsort4(String memsort4) {
		this.memsort4 = memsort4;
	}

	public String getMemname5() {
		return this.memname5;
	}

	public void setMemname5(String memname5) {
		this.memname5 = memname5;
	}

	public String getMemsex5() {
		return this.memsex5;
	}

	public void setMemsex5(String memsex5) {
		this.memsex5 = memsex5;
	}

	public String getMemage5() {
		return this.memage5;
	}

	public void setMemage5(String memage5) {
		this.memage5 = memage5;
	}

	public String getMemrelmaster5() {
		return this.memrelmaster5;
	}

	public void setMemrelmaster5(String memrelmaster5) {
		this.memrelmaster5 = memrelmaster5;
	}

	public String getMempaper5() {
		return this.mempaper5;
	}

	public void setMempaper5(String mempaper5) {
		this.mempaper5 = mempaper5;
	}

	public String getMemsort5() {
		return this.memsort5;
	}

	public void setMemsort5(String memsort5) {
		this.memsort5 = memsort5;
	}

	public String getMasterunittype() {
		return masterunittype;
	}

	public void setMasterunittype(String masterunittype) {
		this.masterunittype = masterunittype;
	}

	public String getMemunittype1() {
		return memunittype1;
	}

	public void setMemunittype1(String memunittype1) {
		this.memunittype1 = memunittype1;
	}

	public String getMemunittype2() {
		return memunittype2;
	}

	public void setMemunittype2(String memunittype2) {
		this.memunittype2 = memunittype2;
	}

	public String getMemunittype3() {
		return memunittype3;
	}

	public void setMemunittype3(String memunittype3) {
		this.memunittype3 = memunittype3;
	}

	public String getMemunittype4() {
		return memunittype4;
	}

	public void setMemunittype4(String memunittype4) {
		this.memunittype4 = memunittype4;
	}

	public String getMemunittype5() {
		return memunittype5;
	}

	public void setMemunittype5(String memunittype5) {
		this.memunittype5 = memunittype5;
	}

	public String getMemunitname1() {
		return memunitname1;
	}

	public void setMemunitname1(String memunitname1) {
		this.memunitname1 = memunitname1;
	}

	public String getMemunitname2() {
		return memunitname2;
	}

	public void setMemunitname2(String memunitname2) {
		this.memunitname2 = memunitname2;
	}

	public String getMemunitname3() {
		return memunitname3;
	}

	public void setMemunitname3(String memunitname3) {
		this.memunitname3 = memunitname3;
	}

	public String getMemunitname4() {
		return memunitname4;
	}

	public void setMemunitname4(String memunitname4) {
		this.memunitname4 = memunitname4;
	}

	public String getMemunitname5() {
		return memunitname5;
	}

	public void setMemunitname5(String memunitname5) {
		this.memunitname5 = memunitname5;
	}

}