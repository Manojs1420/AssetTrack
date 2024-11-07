package com.titan.irgs.master.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "store_alloted_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "storeAllotedDetailsId", scope = Long.class)
public class StoreAllotedDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeAllotedDetailsId;

	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "storeAllotedId", nullable = true)
	private StoreAlloted storeAlloted;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "storeId", nullable = true)
	private Store store;

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

	public Long getStoreAllotedDetailsId() {
		return storeAllotedDetailsId;
	}

	public void setStoreAllotedDetailsId(Long storeAllotedDetailsId) {
		this.storeAllotedDetailsId = storeAllotedDetailsId;
	}

	public StoreAlloted getStoreAlloted() {
		return storeAlloted;
	}

	public void setStoreAlloted(StoreAlloted storeAlloted) {
		this.storeAlloted = storeAlloted;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	

}
