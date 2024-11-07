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
public class AssetPOUpload {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assetPoUploadId;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetId",nullable = false)
	private Asset asset;
	
	private String assetPoUploadPath;
	
	private String endingPath;
	
	private String documentDescription;

	public Long getAssetPoUploadId() {
		return assetPoUploadId;
	}

	public void setAssetPoUploadId(Long assetPoUploadId) {
		this.assetPoUploadId = assetPoUploadId;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getAssetPoUploadPath() {
		return assetPoUploadPath;
	}

	public void setAssetPoUploadPath(String assetPoUploadPath) {
		this.assetPoUploadPath = assetPoUploadPath;
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
 

	

	}
