package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Vendor;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, Vendor domain class).
 * This is VendorService interface(i.e, custom interface) which has CRUD method specification for Vendor domain class.
 * It is responsible to provide service(i.e Vendor releated data) to Vendor web page and vice-versa
 * 
 * @author 
 *
 */
public interface IVendorService
{
	
	Page<Vendor> getAllVendor(String vendorName, String vendorCode, String vendorType,String stateName, String cityName, String contactNumber, String vendorStatus, String webMasterName, Pageable page);

	Vendor getVendorById(Long vendorId);

	Vendor saveVendor(Vendor vendor,Long roleId);

	Vendor updateVendor(Vendor vendor);

	void deleteVendorById(Long vendorId);
	
	boolean checkIfVendorCodeIsExit(String vendorCode);


	Vendor findByVendorName(String vendorName);

	Vendor findByVendorCode(String vendorCode);
	
	Vendor findByVendorBillingEmailId(String billingEmailId);

	List<Vendor> findByVendorIdNotIn(List<Long> ids);

	List<Vendor> getAllVendors();

	List<Object[]> getAllForExcel();
	List<Object[]> getAllForExcel(Long id);

	List<Vendor> getVendorByTypeVerticalId(Long id);

	List<Vendor> getAllVendorsByUsingAssetId(Long id);


}
