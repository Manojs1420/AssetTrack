package com.titan.irgs.inventory.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "AutoAmcInventoryLog")
public class AutoAmcInventoryLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amcLogId;
	
	private String serviceCreationStatus;
	
	private String isMailSent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	
	private LocalDate createdDate;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "amcId", nullable = true)
	private AmcInventory amcInventory;
	
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//	@JoinColumn(name = "amcWarrantydetailId", nullable = true)
//	private AmcWarranty amcWarranty;
	
//	private Long amcWarrantydetailId;
	
	private String ticketStatus;
	
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//	@JoinColumn(name = "maintainanceId", nullable = true)
//	private Maintainance maintainance;
	
	private String serviceRequestCode;
	
	

	public String getServiceRequestCode() {
		return serviceRequestCode;
	}

	public void setServiceRequestCode(String serviceRequestCode) {
		this.serviceRequestCode = serviceRequestCode;
	}

	public Long getAmcLogId() {
		return amcLogId;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	

	public void setAmcLogId(Long amcLogId) {
		this.amcLogId = amcLogId;
	}
	

	public String getServiceCreationStatus() {
		return serviceCreationStatus;
	}

	public void setServiceCreationStatus(String serviceCreationStatus) {
		this.serviceCreationStatus = serviceCreationStatus;
	}

	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public AmcInventory getAmcInventory() {
		return amcInventory;
	}

	public void setAmcInventory(AmcInventory amcInventory) {
		this.amcInventory = amcInventory;
	}



	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	private String Remarks;



	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	
		
}
