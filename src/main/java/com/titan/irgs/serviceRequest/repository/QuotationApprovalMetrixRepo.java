package com.titan.irgs.serviceRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.irgs.serviceRequest.domain.QuotationApprovalMetrix;

public interface QuotationApprovalMetrixRepo extends JpaRepository<QuotationApprovalMetrix, Long>,JpaSpecificationExecutor<QuotationApprovalMetrix>{

	
	
	
	QuotationApprovalMetrix findByWebRoleWebRoleId(Long webRoleId);

}

