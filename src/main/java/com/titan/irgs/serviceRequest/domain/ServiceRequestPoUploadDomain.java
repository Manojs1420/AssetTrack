package com.titan.irgs.serviceRequest.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ServiceRequestPoUploadDomain {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequestPoUServiceRequestPoUploadId;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "serviceRequestId",nullable = false)
	private ServiceRequest serviceRequest;
	
	private String serviceRequestPoUServiceRequestPoUploadPath;

	
	private String endingPath;
	
	private String purchaseOrderNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	private Long totalCost;
	
	private String documentDescription;
 
	private String status;

	private Boolean approved;
	

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	

	

	public String getServiceRequestPoUServiceRequestPoUploadPath() {
		return serviceRequestPoUServiceRequestPoUploadPath;
	}

	public void setServiceRequestPoUServiceRequestPoUploadPath(String serviceRequestPoUServiceRequestPoUploadPath) {
		this.serviceRequestPoUServiceRequestPoUploadPath = serviceRequestPoUServiceRequestPoUploadPath;
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

	

	public Long getServiceRequestPoUServiceRequestPoUploadId() {
		return serviceRequestPoUServiceRequestPoUploadId;
	}

	public void setServiceRequestPoUServiceRequestPoUploadId(Long serviceRequestPoUServiceRequestPoUploadId) {
		this.serviceRequestPoUServiceRequestPoUploadId = serviceRequestPoUServiceRequestPoUploadId;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Long totalCost) {
		this.totalCost = totalCost;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	
	
	
	





}
