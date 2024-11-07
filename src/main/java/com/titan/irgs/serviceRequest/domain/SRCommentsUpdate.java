package com.titan.irgs.serviceRequest.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * This is Service Request Comment Upload Map Table
 * @author Nikith
 *
 */
@Entity
@Table(name = "sr_comment_upload")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "srCommentUploadId", scope = Long.class)
public class SRCommentsUpdate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long srCommentUploadId;
	private Long serviceRequestId;
	private String comment;
	private Long userId;
	private String userName;
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
