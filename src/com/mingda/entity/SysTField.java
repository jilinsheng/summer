package com.mingda.entity;

/**
 * SysTField entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTField implements java.io.Serializable {

	// Fields

	private Long fieldId;
	private SysTTable sysTTable;
	private String logicname;
	private String physicalname;
	private Long fieldtype;
	private Long length;
	private Long digit;
	private String isprimary;
	private String isforeign;
	private String defults;
	private Long dicsort;
	private String isexpand;
	private String readonly;
	private Long control;
	private Long proofrule;
	private Long sequence;
	private String explains;
	private String status;
	private String isempty;
	private String visible;
	private String computeflag;
	private String computesql;
	private String computefields;
	private String iscaption;
	private String islist;

	// Constructors

	/** default constructor */
	public SysTField() {
	}

	/** full constructor */
	public SysTField(SysTTable sysTTable, String logicname,
			String physicalname, Long fieldtype, Long length, Long digit,
			String isprimary, String isforeign, String defults, Long dicsort,
			String isexpand, String readonly, Long control, Long proofrule,
			Long sequence, String explains, String status, String isempty,
			String visible, String computeflag, String computesql,
			String computefields, String iscaption, String islist) {
		this.sysTTable = sysTTable;
		this.logicname = logicname;
		this.physicalname = physicalname;
		this.fieldtype = fieldtype;
		this.length = length;
		this.digit = digit;
		this.isprimary = isprimary;
		this.isforeign = isforeign;
		this.defults = defults;
		this.dicsort = dicsort;
		this.isexpand = isexpand;
		this.readonly = readonly;
		this.control = control;
		this.proofrule = proofrule;
		this.sequence = sequence;
		this.explains = explains;
		this.status = status;
		this.isempty = isempty;
		this.visible = visible;
		this.computeflag = computeflag;
		this.computesql = computesql;
		this.computefields = computefields;
		this.iscaption = iscaption;
		this.islist = islist;
	}

	// Property accessors

	public Long getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public SysTTable getSysTTable() {
		return this.sysTTable;
	}

	public void setSysTTable(SysTTable sysTTable) {
		this.sysTTable = sysTTable;
	}

	public String getLogicname() {
		return this.logicname;
	}

	public void setLogicname(String logicname) {
		this.logicname = logicname;
	}

	public String getPhysicalname() {
		return this.physicalname;
	}

	public void setPhysicalname(String physicalname) {
		this.physicalname = physicalname;
	}

	public Long getFieldtype() {
		return this.fieldtype;
	}

	public void setFieldtype(Long fieldtype) {
		this.fieldtype = fieldtype;
	}

	public Long getLength() {
		return this.length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Long getDigit() {
		return this.digit;
	}

	public void setDigit(Long digit) {
		this.digit = digit;
	}

	public String getIsprimary() {
		return this.isprimary;
	}

	public void setIsprimary(String isprimary) {
		this.isprimary = isprimary;
	}

	public String getIsforeign() {
		return this.isforeign;
	}

	public void setIsforeign(String isforeign) {
		this.isforeign = isforeign;
	}

	public String getDefults() {
		return this.defults;
	}

	public void setDefults(String defults) {
		this.defults = defults;
	}

	public Long getDicsort() {
		return this.dicsort;
	}

	public void setDicsort(Long dicsort) {
		this.dicsort = dicsort;
	}

	public String getIsexpand() {
		return this.isexpand;
	}

	public void setIsexpand(String isexpand) {
		this.isexpand = isexpand;
	}

	public String getReadonly() {
		return this.readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public Long getControl() {
		return this.control;
	}

	public void setControl(Long control) {
		this.control = control;
	}

	public Long getProofrule() {
		return this.proofrule;
	}

	public void setProofrule(Long proofrule) {
		this.proofrule = proofrule;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getExplains() {
		return this.explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsempty() {
		return this.isempty;
	}

	public void setIsempty(String isempty) {
		this.isempty = isempty;
	}

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getComputeflag() {
		return this.computeflag;
	}

	public void setComputeflag(String computeflag) {
		this.computeflag = computeflag;
	}

	public String getComputesql() {
		return this.computesql;
	}

	public void setComputesql(String computesql) {
		this.computesql = computesql;
	}

	public String getComputefields() {
		return this.computefields;
	}

	public void setComputefields(String computefields) {
		this.computefields = computefields;
	}

	public String getIscaption() {
		return this.iscaption;
	}

	public void setIscaption(String iscaption) {
		this.iscaption = iscaption;
	}

	public String getIslist() {
		return this.islist;
	}

	public void setIslist(String islist) {
		this.islist = islist;
	}

}