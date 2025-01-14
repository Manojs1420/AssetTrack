package com.titan.irgs.inventory.repository;

	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.irgs.inventory.domain.QuotationForMaintainance;


	public interface QuotationForMaintainanaceRepo extends JpaRepository<QuotationForMaintainance, Long>,JpaSpecificationExecutor<QuotationForMaintainance>{
		
		QuotationForMaintainance findByWebRoleWebRoleId(Long webRoleId);

	}