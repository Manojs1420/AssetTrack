package com.titan.irgs.serviceRequest.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.inventory.domain.Inventory;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.webMaster.domain.WebMaster;

@Entity
@Table(name = "service_request")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "serviceRequestId", scope = Long.class)
public class ServiceRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequestId;

	private String serviceRequestCode;

	private String problemStatement;

	private String problemDescription;

	private Long serviceVendorId;

	private Long serviceEngineerId;

	private String serviceEngineerCommants;

	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceEngineerVisitDate;

	private String serviceEngineerName;
	
	private String miscellaneousTypeName;
	
	
	private String miscellaneousTypeCode;

	private String clousureProblem;

	private String clousureDescription;

	private String serviceVendorName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceRequestDate;

	private long assetId;

	private String assetCode;

	private String srStatus;
	
	private Long closedBy;

	private String erNumber;
	
    private String poStatus;
   
    private Boolean adviceForPayment;
    
    private Date serviceRequestClosedDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private BreakDownTracking breakDownTracking;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "breakDownTypeId", nullable = true)
	private BreakDownType breakDownType;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "storeId", nullable = true)
	private Store store;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "userId", nullable = true)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "serviceRequesTypetId", nullable = true)
	private ServceRequestType servceRequestType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "urgencyId", nullable = true)
	private Urgency urgency;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "inventoryId", nullable = true)
	private Inventory inventory;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "miscellaneousTypeId", nullable = true)
	private MiscellaneousTypeDomain miscellaneousTypeDomain;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "webRoleId", nullable = false)
	private WebMaster webMaster;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetWebMasterId", nullable = true)
	private WebMaster assetWebMaster;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceRequest")
	private List<ServiceRequestDeatil> serviceRequestDeatils;

	private Long srCreatedUserId;

	private String problemSolvedBy;

	private String vendorClosure;

	private String vendorClosureRemarks;

	private String currentSrStatus;

	private boolean quoteFlag;
	
	private String runningStatus;

	private boolean poFlag;

	private boolean sericeReportFlag;
	
	private Boolean reOpenStatus;

	private String regionForOpen;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reOpenDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reClosedDate;
	
	private String reClosedDiscription;
	
	private String reClosedComment;


	private long createdBy;

	private long updatedBy;

	private String requestType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	private String ticketStatus;

	private String serviceUpload;
	
	
	private Long baseServiceRequestId;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getBaseServiceRequestId() {
		return baseServiceRequestId;
	}

	public void setBaseServiceRequestId(Long baseServiceRequestId) {
		this.baseServiceRequestId = baseServiceRequestId;
	}

	public String getServiceUpload() {
		return serviceUpload;
	}

	public void setServiceUpload(String serviceUpload) {
		this.serviceUpload = serviceUpload;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getErNumber() {
		return erNumber;
	}

	public void setErNumber(String erNumber) {
		this.erNumber = erNumber;
	}

	public BreakDownType getBreakDownType() {
		return breakDownType;
	}

	public void setBreakDownType(BreakDownType breakDownType) {
		this.breakDownType = breakDownType;
	}

	public String getSrStatus() {
		return srStatus;
	}

	public void setSrStatus(String srStatus) {
		this.srStatus = srStatus;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Long getSrCreatedUserId() {
		return srCreatedUserId;
	}

	public void setSrCreatedUserId(Long srCreatedUserId) {
		this.srCreatedUserId = srCreatedUserId;
	}

	public String getProblemSolvedBy() {
		return problemSolvedBy;
	}

	public void setProblemSolvedBy(String problemSolvedBy) {
		this.problemSolvedBy = problemSolvedBy;
	}

	public String getVendorClosure() {
		return vendorClosure;
	}

	public void setVendorClosure(String vendorClosure) {
		this.vendorClosure = vendorClosure;
	}

	public String getVendorClosureRemarks() {
		return vendorClosureRemarks;
	}

	public void setVendorClosureRemarks(String vendorClosureRemarks) {
		this.vendorClosureRemarks = vendorClosureRemarks;
	}

	public String getCurrentSrStatus() {
		return currentSrStatus;
	}

	public void setCurrentSrStatus(String currentSrStatus) {
		this.currentSrStatus = currentSrStatus;
	}

	public boolean isQuoteFlag() {
		return quoteFlag;
	}

	public void setQuoteFlag(boolean quoteFlag) {
		this.quoteFlag = quoteFlag;
	}

	public boolean isPoFlag() {
		return poFlag;
	}

	public void setPoFlag(boolean poFlag) {
		this.poFlag = poFlag;
	}

	public boolean isSericeReportFlag() {
		return sericeReportFlag;
	}

	public void setSericeReportFlag(boolean sericeReportFlag) {
		this.sericeReportFlag = sericeReportFlag;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getServiceRequestId() {
		return serviceRequestId;
	}

	public void setServiceRequestId(Long serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	public String getServiceRequestCode() {
		return serviceRequestCode;
	}

	public void setServiceRequestCode(String serviceRequestCode) {
		this.serviceRequestCode = serviceRequestCode;
	}

	public String getProblemStatement() {
		return problemStatement;
	}

	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public Long getServiceVendorId() {
		return serviceVendorId;
	}

	public void setServiceVendorId(Long serviceVendorId) {
		this.serviceVendorId = serviceVendorId;
	}

	public String getServiceVendorName() {
		return serviceVendorName;
	}

	public void setServiceVendorName(String serviceVendorName) {
		this.serviceVendorName = serviceVendorName;
	}

	public Date getServiceRequestDate() {
		return serviceRequestDate;
	}

	public void setServiceRequestDate(Date serviceRequestDate) {
		this.serviceRequestDate = serviceRequestDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Urgency getUrgency() {
		return urgency;
	}

	public void setUrgency(Urgency urgency) {
		this.urgency = urgency;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	public List<ServiceRequestDeatil> getServiceRequestDeatils() {
		return serviceRequestDeatils;
	}

	public void setServiceRequestDeatils(List<ServiceRequestDeatil> serviceRequestDeatils) {
		this.serviceRequestDeatils = serviceRequestDeatils;
	}

	public Long getServiceEngineerId() {
		return serviceEngineerId;
	}

	public void setServiceEngineerId(Long serviceEngineerId) {
		this.serviceEngineerId = serviceEngineerId;
	}

	public String getServiceEngineerName() {
		return serviceEngineerName;
	}

	public void setServiceEngineerName(String serviceEngineerName) {
		this.serviceEngineerName = serviceEngineerName;
	}

	public String getServiceEngineerCommants() {
		return serviceEngineerCommants;
	}

	public void setServiceEngineerCommants(String serviceEngineerCommants) {
		this.serviceEngineerCommants = serviceEngineerCommants;
	}

	public Date getServiceEngineerVisitDate() {
		return serviceEngineerVisitDate;
	}

	public void setServiceEngineerVisitDate(Date serviceEngineerVisitDate) {
		this.serviceEngineerVisitDate = serviceEngineerVisitDate;
	}

	public String getClousureProblem() {
		return clousureProblem;
	}

	public void setClousureProblem(String clousureProblem) {
		this.clousureProblem = clousureProblem;
	}

	public String getClousureDescription() {
		return clousureDescription;
	}

	public void setClousureDescription(String clousureDescription) {
		this.clousureDescription = clousureDescription;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public ServceRequestType getServceRequestType() {
		return servceRequestType;
	}

	public void setServceRequestType(ServceRequestType servceRequestType) {
		this.servceRequestType = servceRequestType;
	}

	public String getMiscellaneousTypeName() {
		return miscellaneousTypeName;
	}

	public void setMiscellaneousTypeName(String miscellaneousTypeName) {
		this.miscellaneousTypeName = miscellaneousTypeName;
	}

	public String getMiscellaneousTypeCode() {
		return miscellaneousTypeCode;
	}

	public void setMiscellaneousTypeCode(String miscellaneousTypeCode) {
		this.miscellaneousTypeCode = miscellaneousTypeCode;
	}

	public MiscellaneousTypeDomain getMiscellaneousTypeDomain() {
		return miscellaneousTypeDomain;
	}

	public void setMiscellaneousTypeDomain(MiscellaneousTypeDomain miscellaneousTypeDomain) {
		this.miscellaneousTypeDomain = miscellaneousTypeDomain;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public Boolean isAdviceForPayment() {
		return adviceForPayment;
	}

	public void setAdviceForPayment(Boolean adviceForPayment) {
		this.adviceForPayment = adviceForPayment;
	}

	public BreakDownTracking getBreakDownTracking() {
		return breakDownTracking;
	}

	public void setBreakDownTracking(BreakDownTracking breakDownTracking) {
		this.breakDownTracking = breakDownTracking;
	}

	public Boolean getAdviceForPayment() {
		return adviceForPayment;
	}

	public Date getServiceRequestClosedDate() {
		return serviceRequestClosedDate;
	}

	public void setServiceRequestClosedDate(Date serviceRequestClosedDate) {
		this.serviceRequestClosedDate = serviceRequestClosedDate;
	}

	public String getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}



	public String getRegionForOpen() {
		return regionForOpen;
	}

	public void setRegionForOpen(String regionForOpen) {
		this.regionForOpen = regionForOpen;
	}

	public Date getReOpenDate() {
		return reOpenDate;
	}

	public void setReOpenDate(Date reOpenDate) {
		this.reOpenDate = reOpenDate;
	}

	public Boolean getReOpenStatus() {
		return reOpenStatus;
	}

	public void setReOpenStatus(Boolean reOpenStatus) {
		this.reOpenStatus = reOpenStatus;
	}

	public Date getReClosedDate() {
		return reClosedDate;
	}

	public void setReClosedDate(Date reClosedDate) {
		this.reClosedDate = reClosedDate;
	}

	public String getReClosedDiscription() {
		return reClosedDiscription;
	}

	public void setReClosedDiscription(String reClosedDiscription) {
		this.reClosedDiscription = reClosedDiscription;
	}

	public String getReClosedComment() {
		return reClosedComment;
	}

	public void setReClosedComment(String reClosedComment) {
		this.reClosedComment = reClosedComment;
	}

	public Long getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(Long closedBy) {
		this.closedBy = closedBy;
	}

	public WebMaster getAssetWebMaster() {
		return assetWebMaster;
	}

	public void setAssetWebMaster(WebMaster assetWebMaster) {
		this.assetWebMaster = assetWebMaster;
	}
	
	
	
	
	
	
	

}
