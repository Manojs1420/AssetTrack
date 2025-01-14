
package com.titan.irgs.inventory.vo;

import java.time.LocalDate;
import java.util.Date;

public class AmcWarrantyVO {

	private Long warrantyId;
	private Long amcId;
	private Long inventoryId;

	private Long maintainancePeriod;

	private LocalDate maintainanceStartDate;

	private LocalDate maintainanceEndDate;

	private Long minMaintainanceGap;

	private LocalDate warrantyFrom;

	private LocalDate warrantyTo;

	private boolean activeStatus;
	private Date createdDate;
	
	private Date closedDate;
	
	private String ticketStatus;
	
	private Long vendorId;


	public Long getAmcId() {
		return amcId;
	}

	public void setAmcId(Long amcId) {
		this.amcId = amcId;
	}

	public Long getMaintainancePeriod() {
		return maintainancePeriod;
	}

	public void setMaintainancePeriod(Long maintainancePeriod) {
		this.maintainancePeriod = maintainancePeriod;
	}

	public Long getMinMaintainanceGap() {
		return minMaintainanceGap;
	}

	public void setMinMaintainanceGap(Long minMaintainanceGap) {
		this.minMaintainanceGap = minMaintainanceGap;
	}

	public Long getWarrantyId() {
		return warrantyId;
	}

	public void setWarrantyId(Long warrantyId) {
		this.warrantyId = warrantyId;
	}

	public LocalDate getMaintainanceStartDate() {
		return maintainanceStartDate;
	}

	public void setMaintainanceStartDate(LocalDate maintainanceStartDate) {
		this.maintainanceStartDate = maintainanceStartDate;
	}

	public LocalDate getMaintainanceEndDate() {
		return maintainanceEndDate;
	}

	public void setMaintainanceEndDate(LocalDate maintainanceEndDate) {
		this.maintainanceEndDate = maintainanceEndDate;
	}

	public LocalDate getWarrantyFrom() {
		return warrantyFrom;
	}

	public void setWarrantyFrom(LocalDate warrantyFrom) {
		this.warrantyFrom = warrantyFrom;
	}

	public LocalDate getWarrantyTo() {
		return warrantyTo;
	}

	public void setWarrantyTo(LocalDate warrantyTo) {
		this.warrantyTo = warrantyTo;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

}
