package com.titan.irgs.master.vo;

import java.util.Date;

import com.titan.irgs.master.enums.VendorStatus;

/**
 * This is Vendor Value Object class which map the Vendor releated data which are
 * comming from client side/web page
 * 
 * @author 
 *
 */
public class VendorVO {

	private Long vendorId;

	private String vendorCode;
	
	private Long vendorTypeId;
	
	private String vendorTypeName;

	private Long cityId;

	private String cityName;
	
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

	private Date createdOn;

	private Date updatedOn;
	
    private Long webMasterId;
	
	private String webMasterName;
	
	private Long stateId;
	
	private String stateName;

	private Long currencyId;
	
	private String currencyName;
	
	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
    
	public Long getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(Long vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public String getVendorTypeName() {
		return vendorTypeName;
	}

	public void setVendorTypeName(String vendorTypeName) {
		this.vendorTypeName = vendorTypeName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	

}
