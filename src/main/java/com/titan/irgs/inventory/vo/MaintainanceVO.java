package com.titan.irgs.inventory.vo;

import java.util.Date;

public class MaintainanceVO {
	private Long maintainanceId;
	private Long amcId;
	private Long warrantyId;
	private Date proposedVisitDate;
	private String servicedBy;
	private String servicedByPhone;
	private String visitAcceptedBy;
	private Date visitAcceptedDateTime;
	private String approvedBy;
	private Date approvedDateTime;
	private Date maintainanceDateTime;
	private String reportRef;
	private String sparesInvoiceRef;
	private Long engineerId;
	private String storeClosureBy;
	private Date storeClosureDate;
	private String vendorClosureby;
	private Date vendorClosureDate;
	private String ongoingMRStatus;
	private String quoteInvoiceRef;
	private String poInvoiceRef;
	private String serviceRequestCode;
	private String engineerComment;
	private String ticketStatus;	
	private String runningStatus;
	private String AssetCode;
	private String erNumber;
	private String serviceVendorCode;
	private String serviceVendorName;
	private Long serviceRequestTypeId;
	
	private Long closedBy;
	private String clousureProblem;
	private String clousureDescription;
	private Long vendorId;
	 private Boolean adviceForPayment;

	 private Date serviceRequestcloseDate;
	 private String ServiceDocumentUploaded;
	 
	private Long assetId;
	 
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getServiceDocumentUploaded() {
		return ServiceDocumentUploaded;
	}
	public void setServiceDocumentUploaded(String serviceDocumentUploaded) {
		ServiceDocumentUploaded = serviceDocumentUploaded;
	}
	private Long countServiceRequestdays;
		private boolean clouserFourmExpire;
 
		public Boolean getAdviceForPayment() {
			return adviceForPayment;
		}
		public void setAdviceForPayment(Boolean adviceForPayment) {
			this.adviceForPayment = adviceForPayment;
		}

	
	public Long getMaintainanceId() {
		return maintainanceId;
	}
	public void setMaintainanceId(Long maintainanceId) {
		this.maintainanceId = maintainanceId;
	}

	
	public Long getAmcId() {
		return amcId;
	}
	public void setAmcId(Long amcId) {
		this.amcId = amcId;
	}
	public Date getProposedVisitDate() {
		return proposedVisitDate;
	}
	public void setProposedVisitDate(Date proposedVisitDate) {
		this.proposedVisitDate = proposedVisitDate;
	}
	public String getServicedBy() {
		return servicedBy;
	}
	public void setServicedBy(String servicedBy) {
		this.servicedBy = servicedBy;
	}
	public String getServicedByPhone() {
		return servicedByPhone;
	}
	public void setServicedByPhone(String servicedByPhone) {
		this.servicedByPhone = servicedByPhone;
	}
	public String getVisitAcceptedBy() {
		return visitAcceptedBy;
	}
	public void setVisitAcceptedBy(String visitAcceptedBy) {
		this.visitAcceptedBy = visitAcceptedBy;
	}
	public Date getVisitAcceptedDateTime() {
		return visitAcceptedDateTime;
	}
	public void setVisitAcceptedDateTime(Date visitAcceptedDateTime) {
		this.visitAcceptedDateTime = visitAcceptedDateTime;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDateTime() {
		return approvedDateTime;
	}
	public void setApprovedDateTime(Date approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}
	public Date getMaintainanceDateTime() {
		return maintainanceDateTime;
	}
	public void setMaintainanceDateTime(Date maintainanceDateTime) {
		this.maintainanceDateTime = maintainanceDateTime;
	}
	public String getReportRef() {
		return reportRef;
	}
	public void setReportRef(String reportRef) {
		this.reportRef = reportRef;
	}
	public String getSparesInvoiceRef() {
		return sparesInvoiceRef;
	}
	public void setSparesInvoiceRef(String sparesInvoiceRef) {
		this.sparesInvoiceRef = sparesInvoiceRef;
	}
	
	
	
	public Long getEngineerId() {
		return engineerId;
	}
	public void setEngineerId(Long engineerId) {
		this.engineerId = engineerId;
	}
	public String getStoreClosureBy() {
		return storeClosureBy;
	}
	public void setStoreClosureBy(String storeClosureBy) {
		this.storeClosureBy = storeClosureBy;
	}
	public Date getStoreClosureDate() {
		return storeClosureDate;
	}
	public void setStoreClosureDate(Date storeClosureDate) {
		this.storeClosureDate = storeClosureDate;
	}
	public String getVendorClosureby() {
		return vendorClosureby;
	}
	public void setVendorClosureby(String vendorClosureby) {
		this.vendorClosureby = vendorClosureby;
	}
	public Date getVendorClosureDate() {
		return vendorClosureDate;
	}
	public void setVendorClosureDate(Date vendorClosureDate) {
		this.vendorClosureDate = vendorClosureDate;
	}
	
	public String getOngoingMRStatus() {
		return ongoingMRStatus;
	}
	public void setOngoingMRStatus(String ongoingMRStatus) {
		this.ongoingMRStatus = ongoingMRStatus;
	}
	public String getQuoteInvoiceRef() {
		return quoteInvoiceRef;
	}
	public void setQuoteInvoiceRef(String quoteInvoiceRef) {
		this.quoteInvoiceRef = quoteInvoiceRef;
	}
	public String getPoInvoiceRef() {
		return poInvoiceRef;
	}
	public void setPoInvoiceRef(String poInvoiceRef) {
		this.poInvoiceRef = poInvoiceRef;
	}
	public String getServiceRequestCode() {
		return serviceRequestCode;
	}
	public void setServiceRequestCode(String serviceRequestCode) {
		this.serviceRequestCode = serviceRequestCode;
	}
	public String getEngineerComment() {
		return engineerComment;
	}
	public void setEngineerComment(String engineerComment) {
		this.engineerComment = engineerComment;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getRunningStatus() {
		return runningStatus;
	}
	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}
	public String getAssetCode() {
		return AssetCode;
	}
	public void setAssetCode(String assetCode) {
		AssetCode = assetCode;
	}
	public String getErNumber() {
		return erNumber;
	}
	public void setErNumber(String erNumber) {
		this.erNumber = erNumber;
	}
	public String getServiceVendorCode() {
		return serviceVendorCode;
	}
	public void setServiceVendorCode(String serviceVendorCode) {
		this.serviceVendorCode = serviceVendorCode;
	}
	public String getServiceVendorName() {
		return serviceVendorName;
	}
	public void setServiceVendorName(String serviceVendorName) {
		this.serviceVendorName = serviceVendorName;
	}
	public Long getServiceRequestTypeId() {
		return serviceRequestTypeId;
	}
	public void setServiceRequestTypeId(Long serviceRequestTypeId) {
		this.serviceRequestTypeId = serviceRequestTypeId;
	}
	public Long getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(Long closedBy) {
		this.closedBy = closedBy;
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
	public Long getWarrantyId() {
		return warrantyId;
	}
	public void setWarrantyId(Long warrantyId) {
		this.warrantyId = warrantyId;
	}
	public Date getServiceRequestcloseDate() {
		return serviceRequestcloseDate;
	}
	public void setServiceRequestcloseDate(Date serviceRequestcloseDate) {
		this.serviceRequestcloseDate = serviceRequestcloseDate;
	}
	public Long getCountServiceRequestdays() {
		return countServiceRequestdays;
	}
	public void setCountServiceRequestdays(Long countServiceRequestdays) {
		this.countServiceRequestdays = countServiceRequestdays;
	}
	public boolean isClouserFourmExpire() {
		return clouserFourmExpire;
	}
	public void setClouserFourmExpire(boolean clouserFourmExpire) {
		this.clouserFourmExpire = clouserFourmExpire;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}	

}
