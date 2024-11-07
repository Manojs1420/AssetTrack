package com.titan.irgs.inventory.vo;

public class AssetPOUploadVO {

	private Long assetPoUploadId;
	
	private Long assetId;
	
	private String assetPoUploadPath;
	
	private String endingPath;
	
	private String documentDescription;

	public Long getAssetPoUploadId() {
		return assetPoUploadId;
	}

	public void setAssetPoUploadId(Long assetPoUploadId) {
		this.assetPoUploadId = assetPoUploadId;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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
