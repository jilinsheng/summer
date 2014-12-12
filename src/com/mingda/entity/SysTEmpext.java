package com.mingda.entity;

/**
 * SysTEmpext entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTEmpext implements java.io.Serializable {

	// Fields

	private Long empextId;
	private SysTEmployee sysTEmployee;
	private String workno;
	private String name;
	private String paperid;
	private Long sex;
	private Long nation;
	private String address;
	private String officephone1;
	private String officephone2;
	private String homephone;
	private String mobilephone;
	private String email;
	private String status;

	// Constructors

	/** default constructor */
	public SysTEmpext() {
	}

	/** full constructor */
	public SysTEmpext(SysTEmployee sysTEmployee, String workno, String name,
			String paperid, Long sex, Long nation, String address,
			String officephone1, String officephone2, String homephone,
			String mobilephone, String email, String status) {
		this.sysTEmployee = sysTEmployee;
		this.workno = workno;
		this.name = name;
		this.paperid = paperid;
		this.sex = sex;
		this.nation = nation;
		this.address = address;
		this.officephone1 = officephone1;
		this.officephone2 = officephone2;
		this.homephone = homephone;
		this.mobilephone = mobilephone;
		this.email = email;
		this.status = status;
	}

	// Property accessors

	public Long getEmpextId() {
		return this.empextId;
	}

	public void setEmpextId(Long empextId) {
		this.empextId = empextId;
	}

	public SysTEmployee getSysTEmployee() {
		return this.sysTEmployee;
	}

	public void setSysTEmployee(SysTEmployee sysTEmployee) {
		this.sysTEmployee = sysTEmployee;
	}

	public String getWorkno() {
		return this.workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaperid() {
		return this.paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getNation() {
		return this.nation;
	}

	public void setNation(Long nation) {
		this.nation = nation;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOfficephone1() {
		return this.officephone1;
	}

	public void setOfficephone1(String officephone1) {
		this.officephone1 = officephone1;
	}

	public String getOfficephone2() {
		return this.officephone2;
	}

	public void setOfficephone2(String officephone2) {
		this.officephone2 = officephone2;
	}

	public String getHomephone() {
		return this.homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}