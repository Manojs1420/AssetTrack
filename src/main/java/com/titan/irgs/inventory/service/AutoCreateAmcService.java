package com.titan.irgs.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.inventory.domain.AutoAmcInventoryLog;

public interface AutoCreateAmcService {

	AutoAmcInventoryLog findById(Long amcId);
	
	 List<AutoAmcInventoryLog> getAllmaintainance();
	Page<AutoAmcInventoryLog> getAllAutoAmcInventoryLog(String businessVerticalTypeName,String serviceRequestCode, String erno,
			 String vendorCode, String storeCode, List<Long> region, String serviceRequestDate,String maintainanceStartDate, String maintainanceEndDate,
			 String serviceCreationStatus,Long numberOfService,String isMailSent,String fromDate, String toDate,String department, Pageable pageable);

	Page<AutoAmcInventoryLog> getAllMaintainanceAutoLogWithPagination(Pageable p);

	List<AutoAmcInventoryLog> getAllForExportAutoAMCServiceRequest(String fromDate, String toDate,String stringStoreIds,
			String businessVerticalTypeName,  String vendorCode, List<Long> region, String department);
	
}
