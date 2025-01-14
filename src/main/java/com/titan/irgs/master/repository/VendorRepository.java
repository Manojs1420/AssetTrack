package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Vendor;

/**
 * This is VendorRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on vendor table
 * @author 
 *
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>,JpaSpecificationExecutor<Vendor> {
	
	//Vendor findByVendorName(String vendorName);
	
	
	Vendor findByVendorCode(String venderCode);

	Vendor findByVendorName(String vendorName);

	Vendor findByBillingEmailId(String billingEmailId);
	
	List<Vendor> findByVendorIdNotIn(List<Long> ids);

      Vendor findByVendorId(Long vendorId);

	
      
      
      @Query(value ="SELECT v.* FROM vendor v inner join vendor_equipment ve on ve.vendor_id=v.vendor_id \r\n"
      		+ "      		inner join asset a on a.equipment_id=ve.equipment_id \r\n"
      		+ "      		where a.asset_id=:id" ,nativeQuery = true)
      List<Vendor> getAllVendorsByUsingAssetId(@Param("id")Long id);
      
      @Query(value ="SELECT vendor_name,vendor_code,vt.vendor_type_name,c.city_name,v.pin_code,v.contact_person,"
      		+ "v.contact_number,w.web_master_name,v.billing_address,v.billing_email_id,v.service_address1,v.service_email_id1,"
      		+ "v.vendor_status FROM vendor v inner join vendor_type vt on vt.vendor_type_id=v.vendor_type_id "
      		+ "inner join city c on c.city_id=v.city_id "
      		+ "inner join web_master w on w.web_master_id=v.web_master_id" ,nativeQuery = true)
        List<Object[]> getAllForExcel();
        @Query(value ="SELECT vendor_name,vendor_code,vt.vendor_type_name,c.city_name,v.pin_code,v.contact_person,"
          		+ "v.contact_number,w.web_master_name,v.billing_address,v.billing_email_id,v.service_address1,v.service_email_id1,"
          		+ "v.vendor_status FROM vendor v inner join vendor_type vt on vt.vendor_type_id=v.vendor_type_id "
          		+ "inner join city c on c.city_id=v.city_id "
          		+ "inner join web_master w on w.web_master_id=v.web_master_id where v.web_master_id=:id" ,nativeQuery = true)
            List<Object[]> getAllForExcel(@Param("id")Long id);
            @Query(value="SELECT e.* FROM vendor e where e.web_master_id=:id\r\n" ,nativeQuery=true)
		List<Vendor> getVendorByTypeVerticalId(@Param("id")Long id);
   
            @Query(value="SELECT e.vendor_code FROM vendor e where e.vendor_id=?1" ,nativeQuery=true)    
            String findVendoCodebyId(Long vendorId);
            
            @Query(value="SELECT e.vendor_id FROM vendor e where e.vendor_code=?1" ,nativeQuery=true)    
            Long findVendoIDbyCode(String vendorCode);
 
}
