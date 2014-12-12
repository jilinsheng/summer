package com.mingda.entity;

/**
 * SysTDictionary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTDictionary implements java.io.Serializable {

	// Fields

	private Long dictionaryId;
	private SysTDictsort sysTDictsort;
	private String item;
	private Long superdicid;
	private Long sequence;
	private String status;

	// Constructors

	/** default constructor */
	public SysTDictionary() {
	}

	/** full constructor */
	public SysTDictionary(SysTDictsort sysTDictsort, String item,
			Long superdicid, Long sequence, String status) {
		this.sysTDictsort = sysTDictsort;
		this.item = item;
		this.superdicid = superdicid;
		this.sequence = sequence;
		this.status = status;
	}

	// Property accessors

	public Long getDictionaryId() {
		return this.dictionaryId;
	}

	public void setDictionaryId(Long dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public SysTDictsort getSysTDictsort() {
		return this.sysTDictsort;
	}

	public void setSysTDictsort(SysTDictsort sysTDictsort) {
		this.sysTDictsort = sysTDictsort;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getSuperdicid() {
		return this.superdicid;
	}

	public void setSuperdicid(Long superdicid) {
		this.superdicid = superdicid;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}