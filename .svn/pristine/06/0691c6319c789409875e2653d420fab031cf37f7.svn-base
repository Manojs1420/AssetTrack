package com.titan.irgs.accessPolicy.domain;

import java.util.Date;

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

import com.titan.irgs.webRole.domain.WebRole;

@Entity
public class AccesspolicyDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accesspolicyId;
	private String accesspolicyName;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;
	
	private long createdBy;

	private long updatedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="webRoleId",nullable=true)
	private WebRole webRole;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subModuleId",nullable=true)
	private SubModule subModule;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="moduleId",nullable=true)
	private Module module;

	public Long getAccesspolicyId() {
		return accesspolicyId;
	}

	public void setAccesspolicyId(Long accesspolicyId) {
		this.accesspolicyId = accesspolicyId;
	}

	public String getAccesspolicyName() {
		return accesspolicyName;
	}

	public void setAccesspolicyName(String accesspolicyName) {
		this.accesspolicyName = accesspolicyName;
	}

	

	
	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	

	public SubModule getSubModule() {
		return subModule;
	}

	public void setSubModule(SubModule subModule) {
		this.subModule = subModule;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	
	
	
	
	
	}
