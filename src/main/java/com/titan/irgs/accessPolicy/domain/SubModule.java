package com.titan.irgs.accessPolicy.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class SubModule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subModuleId;

	@Column(unique = true, nullable = false)
	private String subModuleName;
	
	private String subModuleUrl;
	
	private String iconName;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedOn;
	private long createdBy;

	private long updatedBy;

	@Column(nullable = false)
	private boolean enabledStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="moduleId",nullable=true)
	private Module module;
	
	

	public Long getSubModuleId() {
		return subModuleId;
	}

	public void setSubModuleId(Long subModuleId) {
		this.subModuleId = subModuleId;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public String getSubModuleUrl() {
		return subModuleUrl;
	}

	public void setSubModuleUrl(String subModuleUrl) {
		this.subModuleUrl = subModuleUrl;
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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	

	

	

	
	
	
	
	
	
	
	
	
	
	
	



}
