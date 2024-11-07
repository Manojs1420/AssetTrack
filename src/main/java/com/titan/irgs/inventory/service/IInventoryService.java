package com.titan.irgs.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.inventory.domain.Inventory;

public interface IInventoryService {
	
	Page<Inventory> getAllInventory(String store,String storeCode, String storeName, String businessVerticalName,String VerticalName, String assetName, String farNo, 
			String model,String serialNumber,String brandName, String erNo,String amcStatus,String equipmentName ,String regionName, List<Long> region,String vendorCode,String departmentName,String department, Pageable pageable);

	Inventory getInventoryById(Long inventoryId);

	Inventory saveInventory(Inventory inventory);

	Inventory updateInventory(Inventory inventory);

	void deleteInventoryById(Long inventoryId);

	Inventory getAllVendorsByUsingInventoryById(Long id);

	List<Inventory> getInventoryByBssinessId(Long id);

	List<Object[]> getAllExcel(String businessVerticalType, String department);

	List<Object[]> getAllExcel(String user1);
	
	List<Object[]> getAllStoreCode(String storecode);
	
	Long getWebmasterByInventoryId(Long id);

	Inventory updateInventory1(Inventory inventory);

	List<Object[]> getInventoryCountForAssignedAssetList(String businessVerticalType, String department, String user1);

	List<Object[]> getAllExcelForVertical(String businessVerticalType);

	List<Object[]> getAllExcel();




}
