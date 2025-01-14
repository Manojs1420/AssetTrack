package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.StoreEngineer;

public interface IStoreEngineerService {

	StoreEngineer saveInventory(StoreEngineer storeEngineer);

	StoreEngineer updateInventory(StoreEngineer storeEngineer);

	void deleteStoreEngineerById(Long id);

	
	StoreEngineer getStoreEngineerById(Long id);

	Page<StoreEngineer> getAllStoreEngineer(String businessVerticalTypeName, String businessVerticalTypeName1,
			String storeCode, List<Long> regions, Long storeId, String engineerName, String engineerCode,
			String emailId, String mobileNo, String status, String region, Pageable page);

}
