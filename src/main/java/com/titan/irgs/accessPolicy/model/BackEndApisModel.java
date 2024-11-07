package com.titan.irgs.accessPolicy.model;

import java.util.Date;
import java.util.List;


public class BackEndApisModel {
	

	private Long backEndApiId;
	
	private String backEndApiIdUrl;

	private Date createdOn;
	
	private String description;
	
	private Long featureId;
	
	private String featureName;

	
	private List<PermissionModel> permissions;
	
	private Long verticalId;

	public Long getBackEndApiId() {
		return backEndApiId;
	}

	

	public void setBackEndApiId(Long backEndApiId) {
		this.backEndApiId = backEndApiId;
	}



	public String getBackEndApiIdUrl() {
		return backEndApiIdUrl;
	}



	public void setBackEndApiIdUrl(String backEndApiIdUrl) {
		this.backEndApiIdUrl = backEndApiIdUrl;
	}



	public Date getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public List<PermissionModel> getPermissions() {
		return permissions;
	}


	public void setPermissions(List<PermissionModel> permissions) {
		this.permissions = permissions;
	}



	public Long getVerticalId() {
		return verticalId;
	}



	public void setVerticalId(Long verticalId) {
		this.verticalId = verticalId;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Long getFeatureId() {
		return featureId;
	}



	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}



	public String getFeatureName() {
		return featureName;
	}



	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

    

	

	
}
