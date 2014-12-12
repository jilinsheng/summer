package com.mingda.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ImplTInterfacedefine entity. @author MyEclipse Persistence Tools
 */
public class ImplTInterfacedefine implements java.io.Serializable {

	// Fields

	private Long interfacedefineId;
	private String defname;
	private Date defdate;
	private Long organizationId;
	private Long employeeId;
	private String filetype;
	private String template;

	// Constructors

	/** default constructor */
	public ImplTInterfacedefine() {
	}
	// Property accessors

	public String getDefname() {
		return this.defname;
	}

	public Long getInterfacedefineId() {
		return interfacedefineId;
	}

	public void setInterfacedefineId(Long interfacedefineId) {
		this.interfacedefineId = interfacedefineId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public void setDefname(String defname) {
		this.defname = defname;
	}

	public Date getDefdate() {
		return this.defdate;
	}

	public void setDefdate(Date defdate) {
		this.defdate = defdate;
	}


	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}