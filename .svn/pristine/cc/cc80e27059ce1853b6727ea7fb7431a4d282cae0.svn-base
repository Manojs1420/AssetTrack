package com.titan.irgs.master.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "owner_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownerTypeId", scope = Long.class)
public class OwnerType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ownerTypeId;

	private String ownerTypeName;
	
    private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public Long getOwnerTypeId() {
		return ownerTypeId;
	}

	public void setOwnerTypeId(Long ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}

	public String getOwnerTypeName() {
		return ownerTypeName;
	}

	public void setOwnerTypeName(String ownerTypeName) {
		this.ownerTypeName = ownerTypeName;
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
	
	
	
	

}
