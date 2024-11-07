package com.titan.irgs.master.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "region")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "regionId", scope = Long.class)
public class Region {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long regionId;
	
	@Column(nullable = false,unique = true)
	private String regionName;
	
	private long createdBy;
	
	private long updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "region")
	private List<RegionDetails> regionDetails;
	
	
	
	/*@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	@JoinColumn(name="cityId",nullable=true)
	private City city;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	@JoinColumn(name="stateId",nullable=true)
	private State state;*/
	
	
    
	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}
	

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<RegionDetails> getRegionDetails() {
		return regionDetails;
	}

	public void setRegionDetails(List<RegionDetails> regionDetails) {
		this.regionDetails = regionDetails;
	}

	
	


}
