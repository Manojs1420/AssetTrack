package com.titan.irgs.master.domain;
import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.master.enums.VendorStatus;
import com.titan.irgs.webMaster.domain.WebMaster;

/**
 * This is Vendor domain class which map the vendor table
 * 
 * @author 
 *
 */
@Entity
@Table(name = "vendor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vendorId", scope = Long.class)
public class Vendor implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorId;

	@Column(unique = true, nullable = false)
	private String vendorCode;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "vendorTypeId", nullable = false)
	private VendorType vendorType;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cityId", nullable = true)
	private City city;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "stateId", nullable = true)
	private State state;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "webMasterId",nullable = true)
	private WebMaster webMaster;
	
	@Enumerated(EnumType.STRING)
    private VendorStatus vendorStatus;
	
	private String vendorName;

	private String contactPerson;

	private String billingAddress;

	private String serviceAddress1;

	private String serviceAddress2;
	
	private String pinCode;

	private String billingEmailId;

	private String serviceEmailId1;

	private String serviceEmailId2;

	private String contactNumber;

	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "currencyId", nullable = true)
	private Currency currency;
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    private List<EquipmentDetail> equipmentDetailList = new ArrayList<EquipmentDetail>(0);*/

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
  
	public VendorType getVendorType()
	{
		return vendorType;
	}

	public void setVendorType(VendorType vendorType) {
		this.vendorType = vendorType;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
    
	public VendorStatus getVendorStatus() {
		return vendorStatus;
	}

	public void setVendorStatus(VendorStatus vendorStatus) {
		this.vendorStatus = vendorStatus;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getServiceAddress1() {
		return serviceAddress1;
	}

	public void setServiceAddress1(String serviceAddress1) {
		this.serviceAddress1 = serviceAddress1;
	}

	public String getServiceAddress2() {
		return serviceAddress2;
	}

	public void setServiceAddress2(String serviceAddress2) {
		this.serviceAddress2 = serviceAddress2;
	}
    
	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getBillingEmailId() {
		return billingEmailId;
	}

	public void setBillingEmailId(String billingEmailId) {
		this.billingEmailId = billingEmailId;
	}

	public String getServiceEmailId1() {
		return serviceEmailId1;
	}

	public void setServiceEmailId1(String serviceEmailId1) {
		this.serviceEmailId1 = serviceEmailId1;
	}

	public String getServiceEmailId2() {
		return serviceEmailId2;
	}

	public void setServiceEmailId2(String serviceEmailId2) {
		this.serviceEmailId2 = serviceEmailId2;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
   
	
	
	
}
