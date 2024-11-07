package com.titan.irgs.master.vo;

import java.util.Date;

public class RegionDetailsVO {
	
	private Long regionDetailsId;
	
	private Long regionId;
	
	private String regionName;
	
	private Long stateId;
	
	private String stateName;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private Long createdBy;
	
	private Long updatedBy;

	public Long getRegionDetailsId() {
		return regionDetailsId;
	}

	public void setRegionDetailsId(Long regionDetailsId) {
		this.regionDetailsId = regionDetailsId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	
	
	
	
	

}
