package com.titan.irgs.master.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.StoreBusinessServiceType;

public interface IStoreBusinessServiceTypeService {
	
	Page<StoreBusinessServiceType> getAllStoreBusinessServiceType(String storeBusinessServiceTypeName, Pageable page);

	StoreBusinessServiceType getStoreBusinessServiceTypeById(Long StoreBusinessServiceTypeId);

	StoreBusinessServiceType saveStoreBusinessServiceType(StoreBusinessServiceType StoreBusinessServiceType);

	StoreBusinessServiceType updateStoreBusinessServiceType(StoreBusinessServiceType StoreBusinessServiceType);

	void deleteStoreBusinessServiceTypeById(Long StoreBusinessServiceTypeId);
	
	StoreBusinessServiceType findByStoreBusinessServiceTypeName(String StoreBusinessServiceTypeName);
	
	boolean checkIfStoreBusinessServiceTypeNameIsExit(String StoreBusinessServiceTypeName);

}
