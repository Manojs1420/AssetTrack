package com.titan.irgs.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.VendorEquipment;


@Repository
public interface VendorEquipmentRepository extends JpaRepository<VendorEquipment, Long> {
	
	
	@Query("from VendorEquipment e where e.equipmentId=:equipmentId")
	List<VendorEquipment> findByEquipmentId(@Param("equipmentId")Long equipmentId);

	VendorEquipment findByVendorEquipmentId(Long vendorEquipmentId);

	VendorEquipment findByEquipmentIdAndVendorVendorId(Long equipmentId,Long vendorId);

	@Modifying
	@Transactional
	void deleteByEquipmentId(Long equipmentId);

	
	
	@Modifying
	@Transactional
	@Query(value = "delete from vendor_equipment where [vendor_equipment_id] in"
			+ "(SELECT [vendor_equipment_id] FROM vendor_equipment where equipment_id=:equipmentId and vendor_id not in (:vendorIds));",
			nativeQuery = true)
	void deleteVendors(List<Long> vendorIds, Long equipmentId);

}


