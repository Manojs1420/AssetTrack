//package com.titan.irgs.master.domain;
//import java.util.Date;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import com.titan.irgs.webMaster.domain.WebMaster;
//
///**
// * This is Brand domain class which map the brand table
// * @author birendra
// *
// */
//@Entity
////@Table(name = "breakdownTypem")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "breakdownId", scope = Long.class)
//public class BreakDownTypem {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long breakdownId;
//
//	private String breakdownName;
//	
//	private long createdBy;
//
//	private long updatedBy;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date createdOn;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date updatedOn;
//	
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//	@JoinColumn(name = "WebMasterId", nullable = true)
//	private WebMaster webMaster;
//
//	public long getCreatedBy() {
//		return createdBy;
//	}
//
//	public Long getBreakdownId() {
//		return breakdownId;
//	}
//
//	public void setBreakdownId(Long breakdownId) {
//		this.breakdownId = breakdownId;
//	}
//
//	public String getBreakdownName() {
//		return breakdownName;
//	}
//
//	public void setBreakdownName(String breakdownName) {
//		this.breakdownName = breakdownName;
//	}
//
//	public void setCreatedBy(long createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public long getUpdatedBy() {
//		return updatedBy;
//	}
//
//	public void setUpdatedBy(long updatedBy) {
//		this.updatedBy = updatedBy;
//	}
//
//	public Date getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(Date createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public Date getUpdatedOn() {
//		return updatedOn;
//	}
//
//	public void setUpdatedOn(Date updatedOn) {
//		this.updatedOn = updatedOn;
//	}
//
//	public WebMaster getWebMaster() {
//		return webMaster;
//	}
//
//	public void setWebMaster(WebMaster webMaster) {
//		this.webMaster = webMaster;
//	}
//	
//    
//}
