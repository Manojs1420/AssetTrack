package com.titan.irgs.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.serviceRequest.controller.Mail;

public interface MaintainanceService  {

	public List<Maintainance> getAllmaintainance();

	public Maintainance saveMaintainance(Maintainance maintainance);

	public Maintainance getByMaintainanceId(Long maintainanceId);

	public Maintainance deleteByMaintainanceId(Long maintainanceId);

	public Maintainance updateMaintainance(Maintainance maintainance);

	
	public List<Maintainance> getAll(String serviceRequestCode, String businessVerticalTypeName, String storeIds,
			String assetCode, String erNo, String vendorName, String farNo, String serviceRequestType,
			String vendorCode, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate, Pageable pageable);

	public Long count(String serviceRequestCode, String businessVerticalTypeName, String storeIds,
			String assetCode, String erNo, String vendorName, String farNo, String serviceRequestType,
			String vendorCode, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate);


	Page<Maintainance> getAllMaintainance(String srNumber, String businessVerticalTypeName,
			String assetCode, String erNo, String vendorName, String farNo, String serviceRequestType,
			String vendorCode, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate, String storeCode, List<Long> region,String serviceDocumentUploaded, String department, Pageable pageable);

	Mail templeteMail(Maintainance maintainance);

	List<Object[]> getAllForExportAMCServiceRequest(String fromDate, String toDate, String status, String breakDownType, String stringStoreIds, String businessVerticalTypeName, Long vendorId, String region);
	
	List<Object[]> getAllForExportAutoAMCServiceRequest(boolean status, String businessVerticalTypeName, Long vendorId);

	List<Maintainance> getAllMaintainanceforauto(String serviceRequestCode, String businessVerticalTypeName,
			String assetCode, String erNo, String vendorName, String farNo, String serviceRequestType,
			String vendorCode, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate, String storeCode, List<Long> region, String department, Pageable pageable);

	List<Object[]> getAllAmcSrCount(String businessVerticalTypeName, String storeCode, Long vendorId,
			List<Long> region);

	List<Object[]> getAllAmcSrCountForAllVendors(String businessVerticalTypeName, String storeCode, Long vendorId,
			List<Long> region);

	List<Object[]> getAllAmcSrCountForAllRegion(String businessVerticalTypeName, String storeCode, Long vendorId,
			List<Long> region);

	List<Object[]> getAllAmcSrCountForAllEquipment(String businessVerticalTypeName, String storeCode, Long vendorId,
			List<Long> region);

	Maintainance updateMaintainanceForFileUpload(Maintainance maintainance);

	public List<Object[]> getAMCServiceRequestCountForDashboard(String businessVerticalType, String department, String vendorCode);
	
	
//	public Maintainance updateByMaintainanceId(Long maintainanceId, Maintainance maintainance);

}
