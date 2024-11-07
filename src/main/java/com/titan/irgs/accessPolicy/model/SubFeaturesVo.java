package com.titan.irgs.accessPolicy.model;

import java.util.Date;



public class SubFeaturesVo {

	
	private Long subFeatureId;

	private String subFeatureName;

	private String subFeatureURL;
	
	private String iconName;

	private Date createdOn;
	
	
	private Long featureId;
	
	
	private Date updatedOn;
	private long createdBy;

	private long updatedBy;

	private boolean enabledStatus;

	public Long getSubFeatureId() {
		return subFeatureId;
	}

	public void setSubFeatureId(Long subFeatureId) {
		this.subFeatureId = subFeatureId;
	}

	public String getSubFeatureName() {
		return subFeatureName;
	}

	public void setSubFeatureName(String subFeatureName) {
		this.subFeatureName = subFeatureName;
	}

	public String getSubFeatureURL() {
		return subFeatureURL;
	}

	public void setSubFeatureURL(String subFeatureURL) {
		this.subFeatureURL = subFeatureURL;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
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

	public boolean isEnabledStatus() {
		return enabledStatus;
	}

	public void setEnabledStatus(boolean enabledStatus) {
		this.enabledStatus = enabledStatus;
	}

	

	

	













	
}
