package com.titan.irgs.inventory.service;



	import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.inventory.domain.QuotationForMaintainance;

	public interface QuotationForMaintainanceService {

		QuotationForMaintainance save(QuotationForMaintainance quotationForMaintainance);


		QuotationForMaintainance getById(Long id);

		QuotationForMaintainance update(QuotationForMaintainance quotationForMaintainance);

		void deleteById(Long id);

		QuotationForMaintainance findByWebRoleWebRoleId(Long webRoleId);

		Page<QuotationForMaintainance> getAll(Double quotationApprovalFrom, Double quotationApprovalTo, String roleName,
				String businessVerticalType, Pageable page);

	}

	
