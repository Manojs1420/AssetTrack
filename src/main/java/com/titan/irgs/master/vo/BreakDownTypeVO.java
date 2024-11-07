package com.titan.irgs.master.vo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.titan.irgs.webMaster.domain.WebMaster;

/**
 * This is Brand Value Object class which map the Brand releated data which are
 * comming from client side/web page
 * 
 * @author 
 *
 */
public class BreakDownTypeVO {
	
	private Long breakdownId;

	private String breakdownName;
	
	private Long webMasterId;
	
	private String webMasterName;

	private long createdBy;

	private long updatedBy;

	private Date createdOn;

	private Date updatedOn;



	public Long getBreakdownId() {
		return breakdownId;
	}

	public void setBreakdownId(Long breakdownId) {
		this.breakdownId = breakdownId;
	}



	public String getBreakdownName() {
		return breakdownName;
	}

	public void setBreakdownName(String breakdownName) {
		this.breakdownName = breakdownName;
	}

	public long getCreatedBy() {
		return createdBy;
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

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
	}

	public String getWebMasterName() {
		return webMasterName;
	}

	public void setWebMasterName(String webMasterName) {
		this.webMasterName = webMasterName;
	}

}
