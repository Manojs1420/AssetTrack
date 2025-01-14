package com.titan.irgs.inventory.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "maintainance")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maintainanceId", scope = Long.class)
public class Maintainance implements Serializable{
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long maintainanceId;
	private Long warrantyId;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "amcId", nullable = true)
	private AmcInventory amcInventory;
	

	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "maintainance") private
	 * List<MaintainanceDetail> maintainanceDetail;
	 */
	@Column(name ="proposedVisitDate",nullable = true )
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
	/*
	 * @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	 * 
	 * @JoinColumn(name = "engineerId", nullable = true) private Engineer engineer;
	 */
	private Long engineerId;

	private String storeClosureBy;
	private Date storeClosureDate;
	private String vendorClosureby;
	private Date vendorClosureDate;
	private String ticketStatus;	
	private String runningStatus;
	private String ongoingMRStatus;
	private String quoteInvoiceRef;
	private String poInvoiceRef;
	private String serviceRequestCode;
	private String engineerComment;
	
	private Long serviceRequesTypetId;
	private Long closedBy;
	private String clousureProblem;
	private String clousureDescription;
    private Boolean adviceForPayment;
    private Date serviceRequestcloseDate;
	private Long vendorId;
	
	private String maintainanceUpload;
	
	
	
	public String getMaintainanceUpload() {
		return maintainanceUpload;
	}
	public void setMaintainanceUpload(String maintainanceUpload) {
		this.maintainanceUpload = maintainanceUpload;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Boolean getAdviceForPayment() {
		return adviceForPayment;
	}
	public void setAdviceForPayment(Boolean adviceForPayment) {
		this.adviceForPayment = adviceForPayment;
	}
	public Maintainance() {
		// TODO Auto-generated constructor stub
	}
	public Long getMaintainanceId() {
		return maintainanceId;
	}
	public void setMaintainanceId(Long maintainanceId) {
		this.maintainanceId = maintainanceId;
	}
	public AmcInventory getAmcInventory() {
		return amcInventory;
	}
	public void setAmcInventory(AmcInventory amcInventory) {
		this.amcInventory = amcInventory;
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

	public Long getServiceRequesTypetId() {
		return serviceRequesTypetId;
	}
	public void setServiceRequesTypetId(Long serviceRequesTypetId) {
		this.serviceRequesTypetId = serviceRequesTypetId;
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
	/*
	 * public List<MaintainanceDetail> getMaintainanceDetail() { return
	 * maintainanceDetail; } public void
	 * setMaintainanceDetail(List<MaintainanceDetail> maintainanceDetail) {
	 * this.maintainanceDetail = maintainanceDetail; }
	 */
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
