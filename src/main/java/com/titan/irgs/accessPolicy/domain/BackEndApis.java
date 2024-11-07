package com.titan.irgs.accessPolicy.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class BackEndApis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long backEndApiId;

	@Column(unique = true, nullable = false)
	private String backEndApiIdUrl;


	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="featureId",nullable=true)
	private Feature feature;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "backEndApis")
	private List<Permission> permission;

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



	public List<Permission> getPermission() {
		return permission;
	}



	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Feature getFeature() {
		return feature;
	}


	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
	
    
	
}
