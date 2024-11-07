package com.titan.irgs.serviceRequest.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.titan.irgs.webMaster.domain.WebMaster;

@Entity
public class BreakDownType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long breakDownTypeId;
	
	private String breakDownTypeName;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "WebMasterId", nullable = true)
	private WebMaster webMaster;
	
	
	public Long getBreakDownTypeId() {
		return breakDownTypeId;
	}
	public void setBreakDownTypeId(Long breakDownTypeId) {
		this.breakDownTypeId = breakDownTypeId;
	}
	public String getBreakDownTypeName() {
		return breakDownTypeName;
	}
	public void setBreakDownTypeName(String breakDownTypeName) {
		this.breakDownTypeName = breakDownTypeName;
	}
	public WebMaster getWebMaster() {
		return webMaster;
	}
	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}
	
	
	

}
