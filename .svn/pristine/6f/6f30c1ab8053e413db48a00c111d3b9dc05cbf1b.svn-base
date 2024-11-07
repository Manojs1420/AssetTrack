package com.titan.irgs.inventory.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.titan.irgs.master.domain.Asset;

@Entity
public class VendorInvoiceFileUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorInvoiceUploadId;
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetId",nullable = false)
	private Asset asset;
	
	private String vendorInvoiceUploadPath;
	
	private String endingPath;
	
	private String documentDescription;
	
	private Long vendorId;

	public Long getVendorInvoiceUploadId() {
		return vendorInvoiceUploadId;
	}

	public void setVendorInvoiceUploadId(Long vendorInvoiceUploadId) {
		this.vendorInvoiceUploadId = vendorInvoiceUploadId;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getVendorInvoiceUploadPath() {
		return vendorInvoiceUploadPath;
	}

	public void setVendorInvoiceUploadPath(String vendorInvoiceUploadPath) {
		this.vendorInvoiceUploadPath = vendorInvoiceUploadPath;
	}

	public String getEndingPath() {
		return endingPath;
	}

	public void setEndingPath(String endingPath) {
		this.endingPath = endingPath;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
