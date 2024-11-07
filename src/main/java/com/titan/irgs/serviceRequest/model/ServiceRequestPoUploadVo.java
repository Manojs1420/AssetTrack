package com.titan.irgs.serviceRequest.model;

import java.util.Date;

import com.titan.irgs.application.util.Status;



public class ServiceRequestPoUploadVo {
	

	
   
	private Long serviceRequestPoUServiceRequestPoUploadId;
	
	private Long serviceRequestId;

	private String serviceRequestPoUServiceRequestPoUploadPath;
	
	private String endingPath;
	
	private String purchaseOrderNumber;
	
	private Boolean approved;

	private Date createdDate;
	
	private Long totalCost;
	
	private String documentDescription;
	
	private Status status;
 

	

	

	

	

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

	public Long getServiceRequestId() {
		return serviceRequestId;
	}

	public void setServiceRequestId(Long serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	
	
	
	
	






	
	

}
