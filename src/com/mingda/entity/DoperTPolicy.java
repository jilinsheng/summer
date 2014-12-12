package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DoperTPolicy entity. @author MyEclipse Persistence Tools
 */

public class DoperTPolicy implements java.io.Serializable {

	// Fields

	private BigDecimal policyId;
	private BigDecimal objtype;
	private String name;
	private String descr;
	private BigDecimal type;
	private BigDecimal acctu;
	private BigDecimal cycle;
	private BigDecimal cycnum;
	private Boolean isprint;
	private Date begdt;
	private String note;
	private Boolean flag;
	private Boolean menutype;
	private Boolean acctype;
	private Set doperTStandards = new HashSet(0);
	private Set bizTFamilystatuses = new HashSet(0);
	private Set implTAccoptchecks = new HashSet(0);
	private Set doperTPolicymenus = new HashSet(0);
	private Set bizTOptchecks = new HashSet(0);
	private Set bizTCheckflows = new HashSet(0);
	private Set bizTOptaccs = new HashSet(0);
	private Set bizTCheckpowers = new HashSet(0);

	// Constructors

	/** default constructor */
	public DoperTPolicy() {
	}

	/** full constructor */
	public DoperTPolicy(BigDecimal objtype, String name, String descr,
			BigDecimal type, BigDecimal acctu, BigDecimal cycle,
			BigDecimal cycnum, Boolean isprint, Date begdt, String note,
			Boolean flag, Boolean menutype, Boolean acctype,
			Set doperTStandards, Set bizTFamilystatuses, Set implTAccoptchecks,
			Set doperTPolicymenus, Set bizTOptchecks, Set bizTCheckflows,
			Set bizTOptaccs, Set bizTCheckpowers) {
		this.objtype = objtype;
		this.name = name;
		this.descr = descr;
		this.type = type;
		this.acctu = acctu;
		this.cycle = cycle;
		this.cycnum = cycnum;
		this.isprint = isprint;
		this.begdt = begdt;
		this.note = note;
		this.flag = flag;
		this.menutype = menutype;
		this.acctype = acctype;
		this.doperTStandards = doperTStandards;
		this.bizTFamilystatuses = bizTFamilystatuses;
		this.implTAccoptchecks = implTAccoptchecks;
		this.doperTPolicymenus = doperTPolicymenus;
		this.bizTOptchecks = bizTOptchecks;
		this.bizTCheckflows = bizTCheckflows;
		this.bizTOptaccs = bizTOptaccs;
		this.bizTCheckpowers = bizTCheckpowers;
	}

	// Property accessors

	public BigDecimal getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(BigDecimal policyId) {
		this.policyId = policyId;
	}

	public BigDecimal getObjtype() {
		return this.objtype;
	}

	public void setObjtype(BigDecimal objtype) {
		this.objtype = objtype;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public BigDecimal getType() {
		return this.type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}

	public BigDecimal getAcctu() {
		return this.acctu;
	}

	public void setAcctu(BigDecimal acctu) {
		this.acctu = acctu;
	}

	public BigDecimal getCycle() {
		return this.cycle;
	}

	public void setCycle(BigDecimal cycle) {
		this.cycle = cycle;
	}

	public BigDecimal getCycnum() {
		return this.cycnum;
	}

	public void setCycnum(BigDecimal cycnum) {
		this.cycnum = cycnum;
	}

	public Boolean getIsprint() {
		return this.isprint;
	}

	public void setIsprint(Boolean isprint) {
		this.isprint = isprint;
	}

	public Date getBegdt() {
		return this.begdt;
	}

	public void setBegdt(Date begdt) {
		this.begdt = begdt;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Boolean getMenutype() {
		return this.menutype;
	}

	public void setMenutype(Boolean menutype) {
		this.menutype = menutype;
	}

	public Boolean getAcctype() {
		return this.acctype;
	}

	public void setAcctype(Boolean acctype) {
		this.acctype = acctype;
	}

	public Set getDoperTStandards() {
		return this.doperTStandards;
	}

	public void setDoperTStandards(Set doperTStandards) {
		this.doperTStandards = doperTStandards;
	}

	public Set getBizTFamilystatuses() {
		return this.bizTFamilystatuses;
	}

	public void setBizTFamilystatuses(Set bizTFamilystatuses) {
		this.bizTFamilystatuses = bizTFamilystatuses;
	}

	public Set getImplTAccoptchecks() {
		return this.implTAccoptchecks;
	}

	public void setImplTAccoptchecks(Set implTAccoptchecks) {
		this.implTAccoptchecks = implTAccoptchecks;
	}

	public Set getDoperTPolicymenus() {
		return this.doperTPolicymenus;
	}

	public void setDoperTPolicymenus(Set doperTPolicymenus) {
		this.doperTPolicymenus = doperTPolicymenus;
	}

	public Set getBizTOptchecks() {
		return this.bizTOptchecks;
	}

	public void setBizTOptchecks(Set bizTOptchecks) {
		this.bizTOptchecks = bizTOptchecks;
	}

	public Set getBizTCheckflows() {
		return this.bizTCheckflows;
	}

	public void setBizTCheckflows(Set bizTCheckflows) {
		this.bizTCheckflows = bizTCheckflows;
	}

	public Set getBizTOptaccs() {
		return this.bizTOptaccs;
	}

	public void setBizTOptaccs(Set bizTOptaccs) {
		this.bizTOptaccs = bizTOptaccs;
	}

	public Set getBizTCheckpowers() {
		return this.bizTCheckpowers;
	}

	public void setBizTCheckpowers(Set bizTCheckpowers) {
		this.bizTCheckpowers = bizTCheckpowers;
	}

}