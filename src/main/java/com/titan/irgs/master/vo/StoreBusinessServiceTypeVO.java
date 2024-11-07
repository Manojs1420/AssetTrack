package com.titan.irgs.master.vo;

import java.util.Date;

public class StoreBusinessServiceTypeVO {

	private Long storeBusinessServiceTypeId;

	private String storeBusinessServiceTypeName;

	private long createdBy;

	private long updatedBy;

	private Date createdOn;

	private Date updatedOn;

	public Long getStoreBusinessServiceTypeId() {
		return storeBusinessServiceTypeId;
	}

	public void setStoreBusinessServiceTypeId(Long storeBusinessServiceTypeId) {
		this.storeBusinessServiceTypeId = storeBusinessServiceTypeId;
	}

	public String getStoreBusinessServiceTypeName() {
		return storeBusinessServiceTypeName;
	}

	public void setStoreBusinessServiceTypeName(String storeBusinessServiceTypeName) {
		this.storeBusinessServiceTypeName = storeBusinessServiceTypeName;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	

}
