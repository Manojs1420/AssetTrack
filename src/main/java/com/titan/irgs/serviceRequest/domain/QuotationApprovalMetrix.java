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

import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webRole.domain.WebRole;

@Entity
public class QuotationApprovalMetrix {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quotationApprovalMetrixId;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "webRoleId",nullable = false)
	private WebRole webRole;
	
	private Double quotationApprovalFrom;
	
	private Double quotationApprovalTo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "WebMasterId", nullable = true)
	private WebMaster webMaster;

	public Long getQuotationApprovalMetrixId() {
		return quotationApprovalMetrixId;
	}

	public void setQuotationApprovalMetrixId(Long quotationApprovalMetrixId) {
		this.quotationApprovalMetrixId = quotationApprovalMetrixId;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public Double getQuotationApprovalFrom() {
		return quotationApprovalFrom;
	}

	public void setQuotationApprovalFrom(Double quotationApprovalFrom) {
		this.quotationApprovalFrom = quotationApprovalFrom;
	}

	public Double getQuotationApprovalTo() {
		return quotationApprovalTo;
	}

	public void setQuotationApprovalTo(Double quotationApprovalTo) {
		this.quotationApprovalTo = quotationApprovalTo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}
	
	

	

}
