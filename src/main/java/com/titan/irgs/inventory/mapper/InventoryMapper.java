package com.titan.irgs.inventory.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.enumUtils.InventoryStatus;
import com.titan.irgs.inventory.domain.Inventory;
import com.titan.irgs.inventory.vo.InventoryVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.SerialNumber;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.SerialNumberRepository;
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
public class InventoryMapper {
	
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
	IUserService iUserService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	SerialNumberRepository serialNumberRepository;
	
	public Inventory getEntityFromVo(InventoryVO inventoryVO) {
		Inventory inventory = new Inventory();
		BeanUtils.copyProperties(inventoryVO, inventory);
		
		inventory.setWebMaster(webMasterRepo.findById(inventoryVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
		
	//	Store store=storeRepository.findById(inventoryVO.getStoreId()).orElse(null);
	//	if(store!=null) {
	//		inventory.setStore(store);
	//	}else {
		//	User user=iUserService.getById(inventoryVO.getUserId());
			
	//		if(user!=null) {
			//	 store=storeRepository.findByStoreCode(user.getUsername());
		//		inventory.setUser(user);
		//	}
	//	}

	     inventory.setUser(userRepo.findById(inventoryVO.getUserId()).orElseThrow(()->new ResourceNotFoundException("The User Id  not found")));
			
		
		inventory.setAsset(assetRepository.findById(inventoryVO.getAssetId()).orElseThrow(()->new ResourceNotFoundException("The Asset Id  not found")));
		
		if(inventoryVO.getSerialId()!=null) {
			SerialNumber serialNumber=serialNumberRepository.findById(inventoryVO.getSerialId()).get();
			if(serialNumber!=null) {
				inventory.setSerialNumber(serialNumber);
			}
		}
		
	//	inventory.setUser(iUserService.getById(inventoryVO.getUserId()));

		return inventory;
	}

	public InventoryVO getVoFromEntity(Inventory inventory) {
		InventoryVO inventoryVO= new InventoryVO();
		BeanUtils.copyProperties(inventory, inventoryVO);
		WebMaster webMaster = webMasterService.getById(inventory.getWebMaster().getWebMasterId());
		inventoryVO.setWebMasterId(webMaster.getWebMasterId());
		inventoryVO.setWebMasterName(webMaster.getWebMasterName());
		
		//Store store = storeService.getStoreById(inventory.getStore().getStoreId());
		User user=iUserService.getById(inventory.getUser().getId());
		
		inventoryVO.setStoreCode(user.getUsername());
		inventoryVO.setStoreId(user.getId());
		inventoryVO.setStoreName(user.getFirstName());
		
			
		inventoryVO.setDepartmentName(user.getRoleWiseDepartments().getDepartment().getDepartmentName());
		//inventoryVO.setRegionName(store.getRegion().getRegionName());
		
		Asset asset = assetService.getAssetById(inventory.getAsset().getAssetId());
		inventoryVO.setAssetId(asset.getAssetId());
		inventoryVO.setAssetName(asset.getAssetName());
		inventoryVO.setAssetSpecification(asset.getAssetSpecification().getAssetSpecificationName());
		inventoryVO.setBrandId(asset.getModel().getBrand().getBrandId());
		inventoryVO.setBrandName(asset.getModel().getBrand().getBrandName());
		inventoryVO.setBrandCode(asset.getModel().getBrand().getBrandCode());
		inventoryVO.setModelId(asset.getModel().getModelId());
		inventoryVO.setModelName(asset.getModel().getModelName());
		inventoryVO.setModelNo(asset.getModel().getModelNo());
		inventoryVO.setAssetCode(asset.getAssetCode());
		inventoryVO.setQRCreated(inventory.getQRCreated());
		
		inventoryVO.setRemarks(asset.getModel().getRemarks());
		inventoryVO.setInventoryStatus(inventory.getInventoryStatus().equalsIgnoreCase("INACTIVE")?InventoryStatus.INACTIVE:InventoryStatus.ACTIVE);
		if(inventory.getAllocationEndDate()!=null) {
			//yyyy-mm-dd
			Long diffIndays=null;
			LocalDate today=LocalDate.now();
			if(inventory.getAllocationEndDate().isBefore(today)) {
				diffIndays = ChronoUnit.DAYS.between(inventory.getAllocationEndDate(), today);
				inventoryVO.setOverUsageDays(diffIndays);
			}
		}
		
		if(inventory.getSerialNumber()!=null) {
			SerialNumber serialNumber=serialNumberRepository.findById(inventory.getSerialNumber().getSerialId()).get();

			if(serialNumber!=null) {
				inventoryVO.setSerialId(serialNumber.getSerialId());
				inventoryVO.setSerialNumber(serialNumber.getSerialNumber());
			}
		}
		
//		User user = iUserService.getById(inventory.getUser().getId());
//
//		inventoryVO.setUserName(user.getUsername());

		
		
		return inventoryVO;
	}

}
