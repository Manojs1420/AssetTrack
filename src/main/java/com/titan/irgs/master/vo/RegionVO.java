package com.titan.irgs.master.vo;

import java.util.Date;
import java.util.List;

public class RegionVO {

	private Long regionId;

	private String regionName;


	private long createdBy;

	private long updatedBy;

	private Date createdDate;

	private Date updatedDate;
	
	private boolean activeRegion;
	
	private List<RegionDetailsVO> regionInfo;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	
	public boolean isActiveRegion() {
		return activeRegion;
	}

	public void setActiveRegion(boolean activeRegion) {
		this.activeRegion = activeRegion;
	}

	public List<RegionDetailsVO> getRegionInfo() {
		return regionInfo;
	}

	public void setRegionInfo(List<RegionDetailsVO> regionInfo) {
		this.regionInfo = regionInfo;
	}
	
	
	

}
