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

import com.titan.irgs.webRole.domain.WebRole;

@Entity
public class Permission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissionId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="backEndApiId",nullable=true)
	private BackEndApis backEndApis;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="webRoleId",nullable=true)
	private WebRole webRole;
	
	private String RoleName;
	
	private String verticalName;
	

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	

	public BackEndApis getBackEndApis() {
		return backEndApis;
	}

	public void setBackEndApis(BackEndApis backEndApis) {
		this.backEndApis = backEndApis;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	
	
	

}
