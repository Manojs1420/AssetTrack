package com.titan.irgs.webRole.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.webMaster.domain.WebMaster;

@Entity
public class WebRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long webRoleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="roleId",nullable=true)
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reportingId", referencedColumnName="roleId",nullable=true)
	private Role reporting;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accessMasterId",nullable=true)
	private WebMaster webMaster;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "webRole")
	private OpertionType opertionType;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "webRole")
	private List<Cluster> Cluster;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "webRole")
	private List<RoleWiseDepartments> roleWiseDepartments;
	
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

	
	public List<RoleWiseDepartments> getRoleWiseDepartments() {
		return roleWiseDepartments;
	}

	public void setRoleWiseDepartments(List<RoleWiseDepartments> roleWiseDepartments) {
		this.roleWiseDepartments = roleWiseDepartments;
	}

	public Long getWebRoleId() {
		return webRoleId;
	}

	public void setWebRoleId(Long webRoleId) {
		this.webRoleId = webRoleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getReporting() {
		return reporting;
	}

	public void setReporting(Role reporting) {
		this.reporting = reporting;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
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

	public OpertionType getOpertionType() {
		return opertionType;
	}

	public void setOpertionType(OpertionType opertionType) {
		this.opertionType = opertionType;
	}

	public List<Cluster> getCluster() {
		return Cluster;
	}

	public void setCluster(List<Cluster> cluster) {
		Cluster = cluster;
	} 
	
	
	

}
