package com.titan.irgs.master.vo;

import java.util.Date;

public class OwnerTypeVO {

	private Long ownerTypeId;

	private String ownerTypeName;

	private long createdBy;

	private long updatedBy;

	private Date createdOn;

	private Date updatedOn;

	public Long getOwnerTypeId() {
		return ownerTypeId;
	}

	public void setOwnerTypeId(Long ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}

	public String getOwnerTypeName() {
		return ownerTypeName;
	}

	public void setOwnerTypeName(String ownerTypeName) {
		this.ownerTypeName = ownerTypeName;
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
