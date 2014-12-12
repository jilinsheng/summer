package com.mingda.entity;

/**
 * SysTEmproleId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTEmproleId implements java.io.Serializable {

	// Fields

	private SysTEmployee sysTEmployee;
	private SysTRole sysTRole;

	// Constructors

	/** default constructor */
	public SysTEmproleId() {
	}

	/** full constructor */
	public SysTEmproleId(SysTEmployee sysTEmployee, SysTRole sysTRole) {
		this.sysTEmployee = sysTEmployee;
		this.sysTRole = sysTRole;
	}

	// Property accessors

	public SysTEmployee getSysTEmployee() {
		return this.sysTEmployee;
	}

	public void setSysTEmployee(SysTEmployee sysTEmployee) {
		this.sysTEmployee = sysTEmployee;
	}

	public SysTRole getSysTRole() {
		return this.sysTRole;
	}

	public void setSysTRole(SysTRole sysTRole) {
		this.sysTRole = sysTRole;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysTEmproleId))
			return false;
		SysTEmproleId castOther = (SysTEmproleId) other;

		return ((this.getSysTEmployee() == castOther.getSysTEmployee()) || (this
				.getSysTEmployee() != null
				&& castOther.getSysTEmployee() != null && this
				.getSysTEmployee().equals(castOther.getSysTEmployee())))
				&& ((this.getSysTRole() == castOther.getSysTRole()) || (this
						.getSysTRole() != null
						&& castOther.getSysTRole() != null && this
						.getSysTRole().equals(castOther.getSysTRole())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSysTEmployee() == null ? 0 : this.getSysTEmployee()
						.hashCode());
		result = 37 * result
				+ (getSysTRole() == null ? 0 : this.getSysTRole().hashCode());
		return result;
	}

}