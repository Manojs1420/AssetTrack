package com.titan.irgs.serviceRequest.model;

import java.util.Date;

public class ServiceRequestTypeModel {
	
    private Long serviceRequesTypetId;

	private String serviceRequestTypeName;
	
	private Date serviceRequestTypeCreatedDate;

	

	public Long getServiceRequesTypetId() {
		return serviceRequesTypetId;
	}

	public void setServiceRequesTypetId(Long serviceRequesTypetId) {
		this.serviceRequesTypetId = serviceRequesTypetId;
	}

	public String getServiceRequestTypeName() {
		return serviceRequestTypeName;
	}

	public void setServiceRequestTypeName(String serviceRequestTypeName) {
		this.serviceRequestTypeName = serviceRequestTypeName;
	}

	public Date getServiceRequestTypeCreatedDate() {
		return serviceRequestTypeCreatedDate;
	}

	public void setServiceRequestTypeCreatedDate(Date serviceRequestTypeCreatedDate) {
		this.serviceRequestTypeCreatedDate = serviceRequestTypeCreatedDate;
	}
	
	
	



}
