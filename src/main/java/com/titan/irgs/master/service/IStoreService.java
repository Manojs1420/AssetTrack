package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Store;

public interface IStoreService {
	
	Page<Store> getAllStore(String storeName, String storeCode, String webMasterName, String ownerType, String emailId, String cityName, String storeStatus,String regionName, Pageable pageable);

	Store getStoreById(Long StoreId);

	Store saveStore(Store Store,Long reportingToId);
	
	Store saveStoreUpload(Store store, Long webRoleId, Long userId);

	Store updateStore(Store Store);

	void deleteStoreById(Long StoreId);
	
	boolean checkIfStoreCodeIsExit(String StoreCode);
	
	Store findByStoreCode(String StoreCode);

	Store findByCostcentre(String costcentre);

	List<Store> getAllStoreNotPresentInUser(Long webroleid, Long webmasterid);

	List<Store> getStoreByBussinessId(Long id);

	List<Object[]> getAllForExcel();

	List<Object[]> getAllForExcel(Long id);

	

}
