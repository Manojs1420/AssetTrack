package com.titan.irgs.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.serviceRequest.controller.Mail;

public interface AmcInventoryService {

	

	public AmcInventory saveAmcInventory(AmcInventory amcInventory);

	public AmcInventory updateAmc(AmcInventory amcInventories);

	List<AmcInventory> findByAmcId(Long id);
	AmcInventory findById(Long id);

	Mail templeteMail(AmcInventory amcInventory);
	Mail templeteMailAlert(AmcInventory amcInventory);


	List<AmcInventory> getAllAmc(String businessVerticalTypeName, String amcStatus,String stringStoreIds,
			String maintainancePeriod, String maintainanceStartDate, String maintainanceEndDate,
			String minMaintainanceGap, String maintainanceValidity, String numberOfService, String contractNumber,
			String vendorCode, String storeCode, String assetName, String erNo, String farNo,Long vendorId, Pageable pageable);

	Long count(String businessVerticalTypeName, String amcStatus,String stringStoreIds,
			String maintainancePeriod, String maintainanceStartDate, String maintainanceEndDate,
			String minMaintainanceGap, String maintainanceValidity, String numberOfService, String contractNumber,
			String vendorCode, String storeCode, String assetName, String erNo, String farNo,Long vendorId);


	Page<AmcInventory> getAllAmcInventories(String businessVerticalName,String amcStatus, String maintainanceType,Long maintainancePeriod, String maintainanceStartDate,
			String maintainanceEndDate, Long minMaintainanceGap, String maintainanceValidity,Long numberOfService,String contractNumber,String vendorCode, Boolean activeStatus,
			String storeCode, String assetName,String erNo,String farNo,String modelName,List<Long> region,String VerticalName,String installationDate,Long fromYear, Long toYear, String department, Pageable pageable);


	public List<AmcInventory> getAmcByAssetId(Long id);

}
