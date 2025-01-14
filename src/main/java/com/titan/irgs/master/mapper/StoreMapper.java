package com.titan.irgs.master.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.repository.CityRepository;
import com.titan.irgs.master.repository.CountryRepository;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.OwnerTypeRepository;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.repository.StateRepository;
import com.titan.irgs.master.repository.StoreBusinessServiceTypeRepository;
import com.titan.irgs.master.repository.StoreServiceTypeRepository;
import com.titan.irgs.master.vo.StoreVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;


@Component
public class StoreMapper {
	
	@Autowired
	OwnerTypeRepository ownerTypeRepository;
	
	@Autowired
	StoreServiceTypeRepository storeServiceTypeRepository;
	
	@Autowired
	StoreBusinessServiceTypeRepository storeBusinessServiceTypeRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	public Store getEntityFromVo(StoreVO storeVO) {
		Store store = new Store();
		BeanUtils.copyProperties(storeVO, store);
		//store.setOwnerType(ownerTypeRepository.findById(storeVO.getOwnerTypeId()).orElseThrow(()->new ResourceNotFoundException("The Owner Type Id  not found")));
		store.setStoreServiceType(storeServiceTypeRepository.findById(storeVO.getStoreServiceTypeId()).orElseThrow(()->new ResourceNotFoundException("The Store Service Type Id  not found")));
		store.setStoreBusinessServiceType(storeBusinessServiceTypeRepository.findById(storeVO.getStoreBusinessServiceTypeId()).orElseThrow(()->new ResourceNotFoundException("The Store Business Service Type Id  not found")));
		store.setWebMaster(webMasterRepo.findById(storeVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
	//	store.setCountry(countryRepository.findById(storeVO.getCountryId()).orElseThrow(()->new ResourceNotFoundException("The Country  Id  not found")));
	//	store.setState(stateRepository.findById(storeVO.getStateId()).orElseThrow(()->new ResourceNotFoundException("The State  Id  not found")));
	//	store.setCity(cityRepository.findById(storeVO.getCityId()).orElseThrow(()->new ResourceNotFoundException("The City  Id  not found")));
	//	store.setRegion(regionRepository.findById(storeVO.getRegionId()).orElseThrow(()->new ResourceNotFoundException("The Region  Id  not found")));
		
		return store;
	}

	public StoreVO getVoFromEntity(Store store) {
		StoreVO storeVO= new StoreVO();
		BeanUtils.copyProperties(store, storeVO);
		WebMaster webMaster = webMasterService.getById(store.getWebMaster().getWebMasterId());
		storeVO.setWebMasterId(webMaster.getWebMasterId());
		storeVO.setWebMasterName(webMaster.getWebMasterName());

		return storeVO;
	}


}
