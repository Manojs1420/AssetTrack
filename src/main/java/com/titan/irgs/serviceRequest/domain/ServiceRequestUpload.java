package com.titan.irgs.serviceRequest.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ServiceRequestUpload {
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequestUpload;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "serviceRequestId",nullable = false)
	private ServiceRequest serviceRequest;
	
	private String srFileploadPath;
	
	private String endingPath;
	
	
	
	private String documentDescription;
 

	

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	public String getSrFileploadPath() {
		return srFileploadPath;
	}

	public void setSrFileploadPath(String srFileploadPath) {
		this.srFileploadPath = srFileploadPath;
	}

	

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public String getEndingPath() {
		return endingPath;
	}

	public void setEndingPath(String endingPath) {
		this.endingPath = endingPath;
	}

	public Long getServiceRequestUpload() {
		return serviceRequestUpload;
	}

	public void setServiceRequestUpload(Long serviceRequestUpload) {
		this.serviceRequestUpload = serviceRequestUpload;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}
	
	
	
	
	



}
