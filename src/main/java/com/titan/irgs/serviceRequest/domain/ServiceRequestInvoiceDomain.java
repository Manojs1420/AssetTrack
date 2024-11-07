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
public class ServiceRequestInvoiceDomain 
{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequestInvoiceDomainId;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "serviceRequestId",nullable = false)
	private ServiceRequest serviceRequest;
	
	private String invoicePath;
	
	private String endingPath;
	
	private String invoiceNumber;
	
	private String invoiceType;
	
	private String charges;
	
	private String documentDescription;
	
	

	

	public Long getServiceRequestInvoiceDomainId() {
		return serviceRequestInvoiceDomainId;
	}

	public void setServiceRequestInvoiceDomainId(Long serviceRequestInvoiceDomainId) {
		this.serviceRequestInvoiceDomainId = serviceRequestInvoiceDomainId;
	}

	public String getInvoicePath() {
		return invoicePath;
	}

	public void setInvoicePath(String invoicePath) {
		this.invoicePath = invoicePath;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	

	

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	public String getDocumentDescription() {
		return documentDescription;
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
	
	
	
	
	



}




	

	

