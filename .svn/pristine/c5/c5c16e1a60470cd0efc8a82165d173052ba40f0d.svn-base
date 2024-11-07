package com.titan.irgs.serviceRequest.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.titan.irgs.user.domain.User;
import com.titan.irgs.webRole.domain.WebRole;

@Entity
public class ServiceRequestDeatil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequestDetailId;
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "UserId",referencedColumnName = "id",nullable = false)
	private User user;
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "webRoleId",nullable = false)
	private WebRole webRole;
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "serviceRequestId",nullable = false)
	private ServiceRequest serviceRequest;
	
	
	
	private String commants;

	public Long getServiceRequestDetailId() {
		return serviceRequestDetailId;
	}

	public void setServiceRequestDetailId(Long serviceRequestDetailId) {
		this.serviceRequestDetailId = serviceRequestDetailId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public String getCommants() {
		return commants;
	}

	public void setCommants(String commants) {
		this.commants = commants;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}
	
	
	
	
	
	

}
