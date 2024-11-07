package com.titan.irgs.master.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.StoreServiceType;

public interface IStoreServiceTypeService {
	
	Page<StoreServiceType> getAllStoreServiceType(String storeServiceTypeName, String slaNo, Pageable page);

	StoreServiceType getStoreServiceTypeById(Long storeServiceTypeId);

	StoreServiceType saveStoreServiceType(StoreServiceType StoreServiceType);

	StoreServiceType updateStoreServiceType(StoreServiceType StoreServiceType);

	void deleteStoreServiceTypeById(Long storeServiceTypeId);
	
	StoreServiceType findByStoreServiceTypeName(String StoreServiceTypeName);
	
	boolean checkIfStoreServiceTypeNameIsExit(String StoreServiceTypeName);

}
