package com.titan.irgs.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.AmcWarranty;

public interface AmcWarrantyService {

	 void saveAmcExtension(AmcInventory amcInventory);

	Page<AmcWarranty> getAllAmcExtension(String vendorCode, String businessVerticalName, Boolean activeStatus,
			String extendDate, String maintainanceDates, String maintainancePeriod, String maintainanceStartDate,
			String maintainanceEndDate, String minMaintainanceGap, Pageable page);
	List<AmcWarranty> findWarrantyByAmcId(Long id);

//	AmcWarranty updateWarrantyAmc(AmcWarranty amcWarranty);

	void updateWarrantyAmc(AmcInventory amcInventory);

	//void saveAmcValidation(AmcInventory amcInventory);

	//void saveAmcValidation(AmcInventoryVO amcInventory);

	void saveAmcValidation(AmcWarranty amcWarranty);

	List<AmcWarranty> findWarrantyByAssetId(Long id);
}
