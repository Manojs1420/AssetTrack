package com.titan.irgs.webMaster.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.titan.irgs.master.domain.Currency;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WebMaster implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long webMasterId;

	@Column(unique = true, nullable = false)
	private String webMasterName;

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

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "currencyId", nullable = true)
	private Currency currency;
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
	}

	public String getWebMasterName() {
		return webMasterName;
	}

	public void setWebMasterName(String webMasterName) {
		this.webMasterName = webMasterName;
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
	
	

}
