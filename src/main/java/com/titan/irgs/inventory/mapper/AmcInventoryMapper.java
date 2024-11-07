package com.titan.irgs.inventory.mapper;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.inventory.vo.AmcInventoryVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.serviceImpl.AssetService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class AmcInventoryMapper {

	@Autowired
	VendorService vendorService;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	IStoreService storeService;
	
	@Autowired
	AssetService assetService;
	@Autowired
	IUserService iUserService;
	@Autowired
	UserRepo userRepo;
	
	
	public AmcInventory getEntityFromVo(AmcInventoryVO amcInventoryVO) {
		AmcInventory amcInventory=new AmcInventory();
		BeanUtils.copyProperties(amcInventoryVO, amcInventory);
		amcInventory.setVendor(vendorRepository.findByVendorId(amcInventoryVO.getVendorId()));
		amcInventory.setWebMaster(webMasterRepo.findById(amcInventoryVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
	//	amcInventory.setStore(storeRepository.findById(amcInventoryVO.getStoreId()).orElseThrow(()->new ResourceNotFoundException("The Store Id  not found")));
		amcInventory.setAsset(assetRepository.findById(amcInventoryVO.getAssetId()).orElseThrow(()->new ResourceNotFoundException("The Asset Id  not found")));
		amcInventory.setUser(userRepo.findById(amcInventoryVO.getUserId()).get());
		amcInventory.setAssetWebMaster(webMasterRepo.findById(amcInventoryVO.getAssetWebMasterId()).get());

	//	amcInventory.setInventory(inventoryRepository.findByInventoryId(amcInventoryVO.getInventoryId()));
		return amcInventory;
	}
	
	public AmcInventoryVO getVoFromEntity(AmcInventory amcInventory) {
		AmcInventoryVO amcInventoryVO=new AmcInventoryVO();
		BeanUtils.copyProperties(amcInventory, amcInventoryVO);
		Vendor vendor = vendorService.getVendorById(amcInventory.getVendor().getVendorId());
		amcInventoryVO.setVendorId(vendor.getVendorId());
		amcInventoryVO.setVendorName(vendor.getVendorName());
		amcInventoryVO.setVendorCode(vendor.getVendorCode());
		
		
		Asset asset=assetService.getAssetById(amcInventory.getAsset().getAssetId());
		amcInventoryVO.setAssetId(asset.getAssetId());
		amcInventoryVO.setAssetName(asset.getAssetName());
		amcInventoryVO.setModelId(asset.getModel().getModelId());
		amcInventoryVO.setModelName(asset.getModel().getModelName());
		amcInventoryVO.setFarNo(asset.getFarNo());	
		
		WebMaster webmaster=webMasterService.getById(amcInventory.getWebMaster().getWebMasterId());
		amcInventoryVO.setWebMasterId(webmaster.getWebMasterId());
		amcInventoryVO.setWebMasterName(webmaster.getWebMasterName());
		
		WebMaster assetwebmaster=webMasterService.getById(amcInventory.getWebMaster().getWebMasterId());
		amcInventoryVO.setAssetWebMasterId(assetwebmaster.getWebMasterId());
			
	User user = userRepo.findById(amcInventory.getUser().getId()).get();
	amcInventoryVO.setUserId(user.getId());

			return amcInventoryVO;
	}
}
