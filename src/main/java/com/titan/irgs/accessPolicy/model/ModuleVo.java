package com.titan.irgs.accessPolicy.model;

import java.util.Date;
import java.util.List;


public class ModuleVo {
	
	
	private Long moduleId;

	private String moduleName;

	private String moduleURL;
	
	private String iconName;
	
	
	private Date createdOn;
    private Date updatedOn;
	private long createdBy;

	private long updatedBy;

	private boolean enabledStatus;
	
	private List<SubModuleVo> subModuleVos;

	

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleURL() {
		return moduleURL;
	}

	public void setModuleURL(String moduleURL) {
		this.moduleURL = moduleURL;
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

	public List<SubModuleVo> getSubModuleVos() {
		return subModuleVos;
	}

	public void setSubModuleVos(List<SubModuleVo> subModuleVos) {
		this.subModuleVos = subModuleVos;
	}

	
    
	
	
}
