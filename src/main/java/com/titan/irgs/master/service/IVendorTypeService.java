package com.titan.irgs.master.service;

import java.util.List;

import com.titan.irgs.master.domain.VendorType;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, VendorType domain class).
 * This is IVendorTypeService interface(i.e, custom interface) which has CRUD method specification for VendorType domain class.
 * It is responsible to provide service(i.e vendor type related data) to vendor type web page and vice-versa
 * 
 * @author 
 *
 */
public interface IVendorTypeService {
	
	List<VendorType> getAllVendorType();
		
	VendorType getVendorTypeById(Long vendorTypeId);

	VendorType saveVendorType(VendorType vendorType);

	VendorType updateVendorType(VendorType vendorType);

	void deleteVendorTypeById(Long vendorTypeId);
	
	boolean checkIfVendorTypeNameIsExit(String vendorTypeName);
	
	VendorType findByVendorTypeName(String vendorTypeName);

}
