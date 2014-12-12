package com.mingda.common.ibatis.data;

import java.math.BigDecimal;

/**
 * InfoTAsset entity. @author MyEclipse Persistence Tools
 */

public class InfoTAsset implements java.io.Serializable {

	private BigDecimal assetId;

	private BigDecimal familyId;

	private BigDecimal farmland;

	private BigDecimal paddyfield;

	private BigDecimal glebe;

	private BigDecimal ownerhouse;

	private BigDecimal roomcnt;

	private BigDecimal buildarea;

	private BigDecimal struct;

	private String repose;

	private String other;

	private BigDecimal producergoods;

	private BigDecimal animal;

	private BigDecimal farmmachine;

	private BigDecimal estimation;

	public BigDecimal getEstimation() {
		return estimation;
	}

	public void setEstimation(BigDecimal estimation) {
		this.estimation = estimation;
	}

	public BigDecimal getAssetId() {
		return assetId;
	}

	public void setAssetId(BigDecimal assetId) {
		this.assetId = assetId;
	}

	public BigDecimal getFamilyId() {
		return familyId;
	}

	public void setFamilyId(BigDecimal familyId) {
		this.familyId = familyId;
	}

	public BigDecimal getFarmland() {
		return farmland;
	}

	public void setFarmland(BigDecimal farmland) {
		this.farmland = farmland;
	}

	public BigDecimal getPaddyfield() {
		return paddyfield;
	}

	public void setPaddyfield(BigDecimal paddyfield) {
		this.paddyfield = paddyfield;
	}

	public BigDecimal getGlebe() {
		return glebe;
	}

	public void setGlebe(BigDecimal glebe) {
		this.glebe = glebe;
	}

	public BigDecimal getOwnerhouse() {
		return ownerhouse;
	}

	public void setOwnerhouse(BigDecimal ownerhouse) {
		this.ownerhouse = ownerhouse;
	}

	public BigDecimal getRoomcnt() {
		return roomcnt;
	}

	public void setRoomcnt(BigDecimal roomcnt) {
		this.roomcnt = roomcnt;
	}

	public BigDecimal getBuildarea() {
		return buildarea;
	}

	public void setBuildarea(BigDecimal buildarea) {
		this.buildarea = buildarea;
	}

	public BigDecimal getStruct() {
		return struct;
	}

	public void setStruct(BigDecimal struct) {
		this.struct = struct;
	}

	public String getRepose() {
		return repose;
	}

	public void setRepose(String repose) {
		this.repose = repose;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public BigDecimal getProducergoods() {
		return producergoods;
	}

	public void setProducergoods(BigDecimal producergoods) {
		this.producergoods = producergoods;
	}

	public BigDecimal getAnimal() {
		return animal;
	}

	public void setAnimal(BigDecimal animal) {
		this.animal = animal;
	}

	public BigDecimal getFarmmachine() {
		return farmmachine;
	}

	public void setFarmmachine(BigDecimal farmmachine) {
		this.farmmachine = farmmachine;
	}
}