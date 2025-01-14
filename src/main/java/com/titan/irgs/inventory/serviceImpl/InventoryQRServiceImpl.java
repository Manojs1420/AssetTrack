package com.titan.irgs.inventory.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.inventory.controller.InventoryQRCodeUpload;
import com.titan.irgs.inventory.repository.InventoryQRUploadRepo;

@Service
@Transactional
public class InventoryQRServiceImpl {

	@Autowired
	InventoryQRUploadRepo inventoryQRUploadRepo;
	
	public InventoryQRCodeUpload updateInventory(InventoryQRCodeUpload inventory) {
	//	InventoryQRCodeUpload Inventory1 = inventoryQRUploadRepo.findByInventoryId(inventory.getInventory().getInventoryId()).orElseThrow(()->new EntityNotFoundException("Inventory with InventoryId " + inventory.getInventory().getInventoryId() + " not found"));
	/*	InventoryQRCodeUpload Inventory1=inventoryQRUploadRepo.getOne(inventory.getInventoryQRCodeUpload());
		if(Inventory1==null) {
			throw new ResourceNotFoundException("the requested object not found)");
		}

		
		try {
			inventoryQRUploadRepo.deleteInventoryQRCodeUploadId(Inventory1.getInventoryQRCodeUpload());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Inventory1.setInventory(inventory.getInventory());
		Inventory1.setEndingPath(inventory.getEndingPath());
		Inventory1.setQrFilePath(inventory.getQrFilePath());
		return inventoryQRUploadRepo.save(Inventory1);*/
		return inventoryQRUploadRepo.save(inventory);
	
	}
}
