package com.titan.irgs.serviceRequest.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;

public interface IServiceRequestService 
{

	ServiceRequest save(ServiceRequest serviceRequest);

	List<ServiceRequest> getAll(String srNumber, String businessVerticalTypeName, String stringStoreIds, String assetCode, 
			String erNumber, String vendorName, String breakDownType, String urgency, Long vendorId, String serviceRequestType, String ticketStatus, String runningStatus, String serviceRequestDate, String serviceRequestClosedDate, String vendorCode,String serviceDocumentUploaded,String department, Pageable pageable);

	ServiceRequest getById(Long id);

	ServiceRequest update(ServiceRequest serviceRequest);

	Long count(String srNumber, String businessVerticalTypeName, String stringStoreIds, String assetCode, String erNumber,
			String vendorName, String breakDownType, Long vendorId, String urgency, String serviceRequestType, String ticketStatus, String runningStatus, String serviceRequestDate, String serviceRequestClosedDate, String department);
	
	Mail templeteMail(ServiceRequest serviceRequest);

	boolean checkIfErnumberAndStatusClosed(String erNumber);

	ServiceRequest findByErNumber(String erNumber);
	
	//  batchEscalation(long countdays);
	
	List<Object[]> getAllForExportServiceRequest(String fromDate, String toDate, String status, String breakDownType, String stringStoreIds, String businessVerticalTypeName, Long vendorId, String department);

	List<Object[]> getAllSrCount(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);

	
	List<Object[]> getSrCountForAllVendors(String businessVerticalTypeName, String storeCode,
			Long vendorId, List<Long> region);

	List<Object[]> getAllSrCountForAllRegion(String businessVerticalTypeName, String storeCode,
			Long vendorId, List<Long> region);

	List<Object[]> getAllSrCountForAllEquipment(String businessVerticalTypeName, String storeCode,
			Long vendorId, List<Long> region);

	List<Object[]> getTotalSrAndAmcSrCount(String businessVerticalTypeName, String storeCode, Long vendorId,
			List<Long> region);

	List<ServiceRequest> getClosedServiceRequests(String serviceRequestCode);

	List<Object[]> getServiceRequestCountForDashboard(String businessVerticalType, String user1, String department, String vendorCode);
	
	
}
