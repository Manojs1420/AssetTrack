/*package com.titan.irgs.inventory.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.inventory.domain.InventoryDetail;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.inventory.vo.InventoryDetailVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.serviceImpl.AssetService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class InventoryDetailMapper {
	
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	IStoreService storeService;
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	
	
	public InventoryDetail getEntityFromVo(InventoryDetailVO inventoryDetailVO) {
		InventoryDetail inventoryDetail = new InventoryDetail();
		BeanUtils.copyProperties(inventoryDetailVO, inventoryDetail);
		
		//InventoryDetail.setWebMaster(webMasterRepo.findById(InventoryDetailVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
	
	   inventoryDetail.setAsset(assetRepository.findById(inventoryDetailVO.getAssetId()).orElseThrow(()->new ResourceNotFoundException("The Asset Id  not found")));
	   inventoryDetail.setInventory(inventoryRepository.findById(inventoryDetailVO.getInventoryId()).orElseThrow(()->new ResourceNotFoundException("The inventory Id not found" )));
		
		return inventoryDetail;
	}

	public InventoryDetailVO getVoFromEntity(InventoryDetail inventoryDetail) {
		InventoryDetailVO inventoryDetailVO= new InventoryDetailVO();
		BeanUtils.copyProperties(inventoryDetail, inventoryDetailVO);
		
 		
		
		Asset asset = assetService.getAssetById(inventoryDetail.getAsset().getAssetId());
		inventoryDetailVO.setAssetId(asset.getAssetId());
		inventoryDetailVO.setAssetName(asset.getAssetName());
		inventoryDetailVO.setBrandId(asset.getModel().getBrand().getBrandId());
		inventoryDetailVO.setBrandName(asset.getModel().getBrand().getBrandName());
		inventoryDetailVO.setModelId(asset.getModel().getModelId());
		inventoryDetailVO.setModelName(asset.getModel().getModelName());
        inventoryDetailVO.setVendorId(inventoryDetail.getVendorId());
	    inventoryDetailVO.setVendorName(inventoryDetail.getVendorName());
			
	
		
		
		
		return inventoryDetailVO;
	}


}
*/