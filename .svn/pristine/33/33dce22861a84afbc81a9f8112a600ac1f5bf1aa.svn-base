package com.titan.irgs.webRole.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.role.model.OperationTypeEnum;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "opertionTypeId", scope = Long.class)
public class OpertionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long opertionTypeId;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="webRoleId",nullable=true)
	private WebRole webRole;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Enumerated(EnumType.STRING)
    private OperationTypeEnum opertionType;

	

	public Long getOpertionTypeId() {
		return opertionTypeId;
	}

	public void setOpertionTypeId(Long opertionTypeId) {
		this.opertionTypeId = opertionTypeId;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public OperationTypeEnum getOpertionType() {
		return opertionType;
	}

	public void setOpertionType(OperationTypeEnum opertionType) {
		this.opertionType = opertionType;
	}

   
    
    

}
