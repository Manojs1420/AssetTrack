package com.titan.irgs.master.vo;

import java.util.Date;

public class GroupBusinessVerticalVO {

	private Long id;
	
	private Long groupBusinessVerticalId;
	
	private Long webMasterId;
	
	private String webMasterName;
	

	
	private Date createdOn;
	
	private Date updatedOn;
	
	private Long storeId;
	
	private String regionName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupBusinessVerticalId() {
		return groupBusinessVerticalId;
	}

	public void setGroupBusinessVerticalId(Long groupBusinessVerticalId) {
		this.groupBusinessVerticalId = groupBusinessVerticalId;
	}

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
	}

	public String getWebMasterName() {
		return webMasterName;
	}

	public void setWebMasterName(String webMasterName) {
		this.webMasterName = webMasterName;
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

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	
}
