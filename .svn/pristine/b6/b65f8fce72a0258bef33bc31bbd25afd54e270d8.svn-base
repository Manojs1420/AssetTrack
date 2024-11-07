package com.titan.irgs.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.VendorType;

/**
 * This is VendorTypeRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on vendor_type table
 * @author birendra
 *
 */
@Repository
public interface VendorTypeRepository extends JpaRepository<VendorType, Long> {
	
	VendorType findByVendorTypeName(String vendorTypeName);

}
