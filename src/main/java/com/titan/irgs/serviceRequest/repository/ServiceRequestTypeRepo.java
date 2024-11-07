package com.titan.irgs.serviceRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.irgs.serviceRequest.domain.ServceRequestType;

public interface ServiceRequestTypeRepo extends JpaRepository<ServceRequestType, Long>{

	ServceRequestType findByServiceRequestTypeName(String serviceRequestTypeName);

}
