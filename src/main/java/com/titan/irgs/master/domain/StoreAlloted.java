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
@Table(name = "store_alloted")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "storeAllotedId", scope = Long.class)
public class StoreAlloted {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeAllotedId;

	private String storeAllotedType;

	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public Long getStoreAllotedId() {
		return storeAllotedId;
	}

	public void setStoreAllotedId(Long storeAllotedId) {
		this.storeAllotedId = storeAllotedId;
	}

	public String getStoreAllotedType() {
		return storeAllotedType;
	}

	public void setStoreAllotedType(String storeAllotedType) {
		this.storeAllotedType = storeAllotedType;
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
