package com.titan.irgs.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.inventory.controller.InventoryQRCodeUpload;

public interface InventoryQRUploadRepo extends JpaRepository<InventoryQRCodeUpload, Long>,JpaSpecificationExecutor<InventoryQRCodeUpload>{

	@Query(value="SELECT * FROM inventoryqrcode_upload where inventory_id=?1",nativeQuery=true)
	InventoryQRCodeUpload findByInventoryId(Long id);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM inventoryqrcode_upload where inventoryqrcode_upload=?1",nativeQuery=true)
	void deleteInventoryQRCodeUploadId(Long id);
}
