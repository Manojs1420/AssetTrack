package com.titan.irgs.serviceRequest.model;

import java.util.Date;

/**
 * @author Nikith
 *
 */
public class SRCommentUploadVo {
	
    private Long srCommentUploadId;
	private Long serviceRequestId;
	private Date createdOn;
	private String comment;
	private Long userId;
	private String userName;
	private String fileUploadLocation;
	private String fileType;
	private String uploadedFileName;
	
	public Long getSrCommentUploadId() {
		return srCommentUploadId;
	}
	public void setSrCommentUploadId(Long srCommentUploadId) {
		this.srCommentUploadId = srCommentUploadId;
	}
	public Long getServiceRequestId() {
		return serviceRequestId;
	}
	public void setServiceRequestId(Long serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFileUploadLocation() {
		return fileUploadLocation;
	}
	public void setFileUploadLocation(String fileUploadLocation) {
		this.fileUploadLocation = fileUploadLocation;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	
}
