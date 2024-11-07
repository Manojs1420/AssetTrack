package com.titan.irgs.fileupload.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.fileupload.enums.FileUploadType;


@Entity
@Table(name = "uploaded_document")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uploadedDocumentId", scope = Long.class)
public class UploadedDocument 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uploadedDocumentId;

    private String name;

    private String description;

    private String filename;
    
    private Long uploadedUserId;

    private String uploadedUserName;

    private FileUploadType fileUploadType;

    @JsonIgnore
    @Transient
    private byte[] content;

    private String contentType;

    @Temporal(TemporalType.DATE)
    private Date uploadedDate;

    @Column(name="time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedTime;

    private boolean processed;
    
    private Long roleId;

    /*@Column(columnDefinition = "text")
    private String base64EncodedData;*/

    @Transient
    private String pathName;

    public long getUploadedDocumentId() {
                    return uploadedDocumentId;
    }

    public void setUploadedDocumentId(long uploadedDocumentId) {
                    this.uploadedDocumentId = uploadedDocumentId;
    }

    public String getName() {
                    return name;
    }

    public void setName(String name) {
                    this.name = name;
    }

    public String getDescription() {
                    return description;
    }

    public void setDescription(String description) {
                    this.description = description;
    }

    public String getFilename() {
                    return filename;
    }

    public void setFilename(String filename) {
                    this.filename = filename;
    }

    public byte[] getContent() {
                    return content;
    }

    public void setContent(byte[] content) {
                    this.content = content;
    }

    public String getContentType() {
                    return contentType;
    }

    public void setContentType(String contentType) {
                    this.contentType = contentType;
    }

    public Date getUploadedTime() {
                    return uploadedTime;
    }

    public void setUploadedTime(Date uploadedTime) {
                    this.uploadedTime = uploadedTime;
    }

    public boolean isProcessed() {
                    return processed;
    }

    public void setProcessed(boolean processed) {
                    this.processed = processed;
    }

    /*public String getBase64EncodedData() {
                    return base64EncodedData;
    }

    public void setBase64EncodedData(String base64EncodedData) {
                    this.base64EncodedData = base64EncodedData;
    }*/

    public FileUploadType getFileUploadType() {
                    return fileUploadType;
    }

    public void setFileUploadType(FileUploadType fileUploadType) {
                    this.fileUploadType = fileUploadType;
    }

    public String getPathName() {
                    return pathName;
    }

    public void setPathName(String pathName) {
                    this.pathName = pathName;
    }

    public Date getUploadedDate() {
                    return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
                    this.uploadedDate = uploadedDate;
    }

    public Long getRoleId() {
                    return roleId;
    }

    public void setRoleId(Long roleId) {
                    this.roleId = roleId;
    }

    public Long getUploadedUserId() {
                    return uploadedUserId;
    }

    public void setUploadedUserId(Long uploadedUserId) {
                    this.uploadedUserId = uploadedUserId;
    }

    public String getUploadedUserName() {
                    return uploadedUserName;
    }

    public void setUploadedUserName(String uploadedUserName) {
                    this.uploadedUserName = uploadedUserName;
    }



}
