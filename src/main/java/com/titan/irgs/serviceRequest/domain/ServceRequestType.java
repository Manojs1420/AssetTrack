package com.titan.irgs.serviceRequest.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class ServceRequestType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequesTypetId;

	private String serviceRequestTypeName;
	
	@Temporal(TemporalType.TIMESTAMP)
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
