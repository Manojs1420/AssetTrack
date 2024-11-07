package com.titan.irgs.serviceRequest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Urgency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long urgencyId;
	private String urgencyName;
	public Long getUrgencyId() {
		return urgencyId;
	}
	public void setUrgencyId(Long urgencyId) {
		this.urgencyId = urgencyId;
	}
	public String getUrgencyName() {
		return urgencyName;
	}
	public void setUrgencyName(String urgencyName) {
		this.urgencyName = urgencyName;
	}
	
	
	
	
	

}
