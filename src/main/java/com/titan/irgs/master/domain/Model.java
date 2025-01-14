package com.titan.irgs.master.domain;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.webMaster.domain.WebMaster;

/**
 * This is Model domain class which map the model table
 * @author birendra
 *
 */
@Entity
@Table(name = "model")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "modelId", scope = Long.class)
public class Model implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long modelId;

	private String modelName;
	
	@Column( nullable = true)
	private String modelNo;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "brandId", nullable = true)
	private Brand brand;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "WebMasterId", nullable = true)
	private WebMaster webMaster;
	
	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	private String remarks;
	/*
	 * @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "model") private List<Asset>
	 * assets = new ArrayList<Asset>(0);
	 */

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
    
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
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

	/*
	 * public List<Asset> getAssets() { return assets; }
	 * 
	 * public void setAssets(List<Asset> assets) { this.assets = assets; }
	 */

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	
    
}
