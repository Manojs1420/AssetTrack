package com.titan.irgs.master.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.titan.irgs.master.enums.StoreStatus;
import com.titan.irgs.webMaster.domain.WebMaster;

/**
 * This is ( Store) domain class which map the store table
 * 
 * @author 
 *
 */
@Entity
@Table(name = "store")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "storeId", scope = Long.class)
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeId;

	@Column(unique = true, nullable = false)
	private String storeCode;

	private String storeName;

	private String fax1;

	private String fax2;

	private String storeLocality;
	
	private String stateGST;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cityId", nullable = true)
	private City city;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "stateId", nullable = true)
	private State state;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "countryId", nullable = true)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "regionId", nullable = true)
	private Region region;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ownerTypeId", nullable = true)
	private OwnerType ownerType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "storeServiceTypeId", nullable = true)
	private StoreServiceType storeServiceType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "storeBusinessServiceTypeId", nullable = true)
	private StoreBusinessServiceType storeBusinessServiceType;

	@Column(unique = true, nullable = true)
	private String costcentre;

	private String ownerName;

	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	private String reportingTo;
	
	private boolean storeFlag;
	
	private Long reportingToId;

	@Enumerated(EnumType.STRING)
	private StoreStatus storeStatus;

	private boolean starFlag;

	private Long roleWiseDepartmentId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "webMasterId", nullable = true)
	private WebMaster webMaster;
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getRoleWiseDepartmentId() {
		return roleWiseDepartmentId;
	}

	public void setRoleWiseDepartmentId(Long roleWiseDepartmentId) {
		this.roleWiseDepartmentId = roleWiseDepartmentId;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCostcentre() {
		return costcentre;
	}

	public void setCostcentre(String costcentre) {
		this.costcentre = costcentre;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public boolean isStoreFlag() {
		return storeFlag;
	}

	public void setStoreFlag(boolean storeFlag) {
		this.storeFlag = storeFlag;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public StoreStatus getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(StoreStatus storeStatus) {
		this.storeStatus = storeStatus;
	}

	public boolean isStarFlag() {
		return starFlag;
	}

	public void setStarFlag(boolean starFlag) {
		this.starFlag = starFlag;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public StoreServiceType getStoreServiceType() {
		return storeServiceType;
	}

	public void setStoreServiceType(StoreServiceType storeServiceType) {
		this.storeServiceType = storeServiceType;
	}

	public StoreBusinessServiceType getStoreBusinessServiceType() {
		return storeBusinessServiceType;
	}

	public void setStoreBusinessServiceType(StoreBusinessServiceType storeBusinessServiceType) {
		this.storeBusinessServiceType = storeBusinessServiceType;
	}

	public String getFax1() {
		return fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	public String getFax2() {
		return fax2;
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	public String getStoreLocality() {
		return storeLocality;
	}

	public void setStoreLocality(String storeLocality) {
		this.storeLocality = storeLocality;
	}

	public Long getReportingToId() {
		return reportingToId;
	}

	public void setReportingToId(Long reportingToId) {
		this.reportingToId = reportingToId;
	}

	public String getStateGST() {
		return stateGST;
	}

	public void setStateGST(String stateGST) {
		this.stateGST = stateGST;
	}
	
	
	

}
