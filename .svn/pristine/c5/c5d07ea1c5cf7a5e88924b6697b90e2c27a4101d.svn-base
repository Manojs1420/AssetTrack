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
@Table(name = "store_service_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "storeServiceTypeId", scope = Long.class)
public class StoreServiceType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeServiceTypeId;

	private String storeServiceTypeName;
	
	private String sla;

	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public Long getStoreServiceTypeId() {
		return storeServiceTypeId;
	}

	public void setStoreServiceTypeId(Long storeServiceTypeId) {
		this.storeServiceTypeId = storeServiceTypeId;
	}

	public String getStoreServiceTypeName() {
		return storeServiceTypeName;
	}

	public void setStoreServiceTypeName(String storeServiceTypeName) {
		this.storeServiceTypeName = storeServiceTypeName;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
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
