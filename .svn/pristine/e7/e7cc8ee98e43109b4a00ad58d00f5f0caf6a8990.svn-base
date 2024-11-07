package com.titan.irgs.master.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.domain.StoreEngineer;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.vo.StoreEngineerVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class StoreEngineerMapper {

	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	IStoreService storeService;
	
	public StoreEngineer getEntityFromVo(StoreEngineerVO storeEngineerVO) {
		StoreEngineer storeEngineer = new StoreEngineer();
		BeanUtils.copyProperties(storeEngineerVO, storeEngineer);
		
		storeEngineer.setWebMaster(webMasterRepo.findById(storeEngineerVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
		storeEngineer.setStore(storeRepository.findById(storeEngineerVO.getStoreId()).orElseThrow(()->new ResourceNotFoundException("The Store Id  not found")));
		
	//	inventory.setUser(iUserService.getById(inventoryVO.getUserId()));

		return storeEngineer;
	}

	public StoreEngineerVO getVoFromEntity(StoreEngineer storeEngineer) {
		StoreEngineerVO storeEngineerVO= new StoreEngineerVO();
		BeanUtils.copyProperties(storeEngineer, storeEngineerVO);
		WebMaster webMaster = webMasterService.getById(storeEngineer.getWebMaster().getWebMasterId());
		storeEngineerVO.setWebMasterId(webMaster.getWebMasterId());
		storeEngineerVO.setWebMasterName(webMaster.getWebMasterName());
		
		Store store = storeService.getStoreById(storeEngineer.getStore().getStoreId());
		
		storeEngineerVO.setStoreCode(store.getStoreCode());
		storeEngineerVO.setStoreId(store.getStoreId());
		storeEngineerVO.setRegionName(store.getRegion().getRegionName());
	
//		User user = iUserService.getById(inventory.getUser().getId());
	
		return storeEngineerVO;
	}
}
