package com.titan.irgs.serviceRequest.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MiscellaneousTypeDomain {
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long miscellaneousTypeId;

	private String miscellaneousTypeName;
	
	@Column(unique = true)
	private String miscellaneousTypeCode;

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date miscellaneousTypeCreatedDate;


	public Long getMiscellaneousTypeId() {
		return miscellaneousTypeId;
	}


	public void setMiscellaneousTypeId(Long miscellaneousTypeId) {
		this.miscellaneousTypeId = miscellaneousTypeId;
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


	public Date getMiscellaneousTypeCreatedDate() {
		return miscellaneousTypeCreatedDate;
	}


	public void setMiscellaneousTypeCreatedDate(Date miscellaneousTypeCreatedDate) {
		this.miscellaneousTypeCreatedDate = miscellaneousTypeCreatedDate;
	}
	
	

	

	


	
	
	
	



}
