package com.titan.irgs.master.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.domain.StoreAlloted;
import com.titan.irgs.master.domain.StoreAllotedDetails;
import com.titan.irgs.master.domain.StoreBusinessServiceType;
import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.master.mapper.StoreMapper;
import com.titan.irgs.master.repository.CityRepository;
import com.titan.irgs.master.repository.CountryRepository;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.OwnerTypeRepository;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.repository.StateRepository;
import com.titan.irgs.master.repository.StoreAllotedDetailsRepository;
import com.titan.irgs.master.repository.StoreBusinessServiceTypeRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.StoreServiceTypeRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.serviceImpl.CityService;
import com.titan.irgs.master.serviceImpl.CountryService;
import com.titan.irgs.master.serviceImpl.OwnerTypeService;
import com.titan.irgs.master.serviceImpl.RegionService;
import com.titan.irgs.master.serviceImpl.StateService;
import com.titan.irgs.master.serviceImpl.StoreAllotedService;
import com.titan.irgs.master.serviceImpl.StoreBusinessServiceTypeService;
import com.titan.irgs.master.serviceImpl.StoreServiceTypeService;
import com.titan.irgs.master.vo.StoreAllotedDetailsVO;
import com.titan.irgs.master.vo.StoreVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(value = WebConstantUrl.STORE_BASE_URL)
public class StoreController {

	@Autowired
	IStoreService storeService;

	@Autowired
	StoreMapper storeMapper;

	@Autowired
	IWebRoleService webRoleService;

	@Autowired
	StoreAllotedDetailsRepository storeAllotedDetailsRepository;

	@Autowired
	StoreAllotedService storeAllotedService;

	@Autowired
	OwnerTypeService ownerTypeService;

	@Autowired
	OwnerTypeRepository ownerTypeRepository;

	@Autowired
	StoreServiceTypeService storeServiceTypeService;

	@Autowired
	StoreServiceTypeRepository storeServiceTypeRepository;

	@Autowired
	StoreBusinessServiceTypeService storeBusinessServiceTypeService;

	@Autowired
	StoreBusinessServiceTypeRepository storeBusinessServiceTypeRepository;

	@Autowired
	CityService cityService;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	RegionService regionService;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	StateService stateService;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	WebMasterService webMasterService;

	@Autowired
	WebMasterRepo webMasterRepo;

	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	WebRoleRepo webRoleRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	private static final String superadmin="superadmin"; 
		private static final String MANAGEMENT = "MANAGEMENT";


	

	@GetMapping(value = WebConstantUrl.GET_ALL_STORE)
	@ResponseBody
	public ResponseEntity<?> getAllStore(@RequestParam(required=false) String assetName,
			@RequestParam(required=false) String storeName,
			@RequestParam(required=false) String storeCode,
			@RequestParam(required=false) String webMasterName,
			@RequestParam(required=false) String ownrType,
			@RequestParam(required=false) String emailId,
			@RequestParam(required=false) String cityName,
			@RequestParam(required=false) String storeStatus,
			@RequestParam(required=false) String regionName,
			Pageable pageable,Principal principal) {
		
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();
		
		User user=userRepo.findByUsername(principal.getName());
		
		
		//setting bussiness verticle with respective login user
		if(!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin) && 
				!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			webMasterName=user.getRole().getWebMaster().getWebMasterName();
					
		}
				
		
		
		List<StoreVO> storeVOs = new ArrayList<StoreVO>(0);
		Page<Store> stores = storeService.getAllStore(storeName,storeCode,webMasterName,ownrType,emailId,cityName,storeStatus,regionName,page);
		StoreVO storeVO1 = null;
		
		
		if (stores.getContent().size() == 0) {
			map.put("storeVOs", storeVOs);
			map.put("total_pages", stores.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", stores.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {

			for (Store store : stores) {
				storeVO1 = new StoreVO();

				List<StoreAllotedDetailsVO> storeAllotedDetailsVO = new ArrayList<StoreAllotedDetailsVO>();
				List<StoreAllotedDetails> storeAllotedDetails = storeAllotedDetailsRepository
						.findByStoreStoreId(store.getStoreId());
				if (!storeAllotedDetails.isEmpty()) {
					for (StoreAllotedDetails storeAllotedDetail : storeAllotedDetails) {
						StoreAllotedDetailsVO storeAllotedDetailVO = new StoreAllotedDetailsVO();
						BeanUtils.copyProperties(storeAllotedDetail, storeAllotedDetailVO);
						StoreAlloted storeAlloted = storeAllotedService
								.getStoreAllotedById(storeAllotedDetail.getStoreAlloted().getStoreAllotedId());
						storeAllotedDetailVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
						storeAllotedDetailVO.setStoreAllotedId(storeAlloted.getStoreAllotedId());
						storeAllotedDetailVO.setStoreAllotedType(storeAlloted.getStoreAllotedType());
						storeAllotedDetailsVO.add(storeAllotedDetailVO);
					}
				}
				
				User reportingToUser = userService.getById(store.getReportingToId());
				if(reportingToUser != null) {
				storeVO1.setReportingToUserName(reportingToUser.getUsername());
				storeVO1.setReportingToRoleName(reportingToUser.getRole().getRole().getRoleName());
				}
				BeanUtils.copyProperties(store, storeVO1);

				 if(store.getOwnerType()  !=null ) {
				OwnerType ownerType = ownerTypeRepository.findByOwnerTypeId(store.getOwnerType().getOwnerTypeId());
				storeVO1.setOwnerTypeId(ownerType.getOwnerTypeId());
				storeVO1.setOwnerTypeName(ownerType.getOwnerTypeName());
				 }

				 if(store.getStoreServiceType() !=null) {
				StoreServiceType storeServiceType = storeServiceTypeRepository
						.findByStoreServiceTypeId(store.getStoreServiceType().getStoreServiceTypeId());
				storeVO1.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
				storeVO1.setStoreServiceTypeName(storeServiceType.getStoreServiceTypeName());
				 }

				 if(store.getStoreBusinessServiceType() !=null ) {
				StoreBusinessServiceType storeBusinessServiceType = storeBusinessServiceTypeRepository
						.findByStoreBusinessServiceTypeId(
								store.getStoreBusinessServiceType().getStoreBusinessServiceTypeId());
				storeVO1.setStoreBusinessServiceTypeId(storeBusinessServiceType.getStoreBusinessServiceTypeId());
				storeVO1.setStoreBusinessServiceTypeName(storeBusinessServiceType.getStoreBusinessServiceTypeName());
			 }

				 if(store.getWebMaster() !=null) {
				WebMaster webMaster = webMasterRepo.findByWebMasterId(store.getWebMaster().getWebMasterId());
				storeVO1.setWebMasterId(webMaster.getWebMasterId());
				storeVO1.setWebMasterName(webMaster.getWebMasterName());
				}

			/*	 if(store.getCountry() != null) {
				Country country = countryRepository.findByCountryId(store.getCountry().getCountryId());
				storeVO1.setCountryId(country.getCountryId());
				storeVO1.setCountryName(country.getCountryName());
				 }

				 if(store.getState() != null) {
				State state = stateRepository.findByStateId(store.getState().getStateId());
				storeVO1.setStateId(state.getStateId());
				storeVO1.setStateName(state.getStateName());
			}

			 if(store.getCity() != null) {
				City city = cityRepository.findByCityId(store.getCity().getCityId());
				storeVO1.setCityId(city.getCityId());
				storeVO1.setCityName(city.getCityName());
				 }

				if(store.getRegion() != null) {
				Region region = regionRepository.findByRegionId(store.getRegion().getRegionId());
				storeVO1.setRegionId(region.getRegionId());
				storeVO1.setRegionName(region.getRegionName());
				 }
*/
				storeVO1.setStoreAllotedDetailInfo(storeAllotedDetailsVO);
				storeVOs.add(storeVO1);

			}
		
		}
		
		map.put("storeVOs", storeVOs);
		map.put("total_pages", stores.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", stores.getTotalElements());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_STORE_NOT_PRESENT_IN_USER_MASTER)
	@ResponseBody
	public ResponseEntity<?> getAllStoreNotPresentInUser(@PathVariable("id") Long id) {
		
		
				
		
		WebRole webRole=webRoleRepo.getOne(id);
		List<StoreVO> storeVOs = new ArrayList<StoreVO>(0);
		List<Store> stores = storeService.getAllStoreNotPresentInUser(id,webRole.getWebMaster().getWebMasterId());
		StoreVO storeVO1 = null;
		
		
		if (stores.size() == 0) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} else {

			for (Store store : stores) {
				storeVO1 = new StoreVO();

				List<StoreAllotedDetailsVO> storeAllotedDetailsVO = new ArrayList<StoreAllotedDetailsVO>();
				List<StoreAllotedDetails> storeAllotedDetails = storeAllotedDetailsRepository
						.findByStoreStoreId(store.getStoreId());
				if (!storeAllotedDetails.isEmpty()) {
					for (StoreAllotedDetails storeAllotedDetail : storeAllotedDetails) {
						StoreAllotedDetailsVO storeAllotedDetailVO = new StoreAllotedDetailsVO();
						BeanUtils.copyProperties(storeAllotedDetail, storeAllotedDetailVO);
						StoreAlloted storeAlloted = storeAllotedService
								.getStoreAllotedById(storeAllotedDetail.getStoreAlloted().getStoreAllotedId());
						storeAllotedDetailVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
						storeAllotedDetailVO.setStoreAllotedId(storeAlloted.getStoreAllotedId());
						storeAllotedDetailVO.setStoreAllotedType(storeAlloted.getStoreAllotedType());
						storeAllotedDetailsVO.add(storeAllotedDetailVO);
					}
				}
				BeanUtils.copyProperties(store, storeVO1);
/*
				 if(store.getOwnerType()  !=null ) {
				OwnerType ownerType = ownerTypeRepository.findByOwnerTypeId(store.getOwnerType().getOwnerTypeId());
				storeVO1.setOwnerTypeId(ownerType.getOwnerTypeId());
				storeVO1.setOwnerTypeName(ownerType.getOwnerTypeName());
				 }
*/
				 if(store.getStoreServiceType() !=null) {
				StoreServiceType storeServiceType = storeServiceTypeRepository
						.findByStoreServiceTypeId(store.getStoreServiceType().getStoreServiceTypeId());
				storeVO1.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
				storeVO1.setStoreServiceTypeName(storeServiceType.getStoreServiceTypeName());
				 }

				 if(store.getStoreBusinessServiceType() !=null ) {
				StoreBusinessServiceType storeBusinessServiceType = storeBusinessServiceTypeRepository
						.findByStoreBusinessServiceTypeId(
								store.getStoreBusinessServiceType().getStoreBusinessServiceTypeId());
				storeVO1.setStoreBusinessServiceTypeId(storeBusinessServiceType.getStoreBusinessServiceTypeId());
				storeVO1.setStoreServiceTypeName(storeBusinessServiceType.getStoreBusinessServiceTypeName());
			 }

				 if(store.getWebMaster() !=null) {
				WebMaster webMaster = webMasterRepo.findByWebMasterId(store.getWebMaster().getWebMasterId());
				storeVO1.setWebMasterId(webMaster.getWebMasterId());
				storeVO1.setWebMasterName(webMaster.getWebMasterName());
				}
/*
				 if(store.getCountry() != null) {
				Country country = countryRepository.findByCountryId(store.getCountry().getCountryId());
				storeVO1.setCountryId(country.getCountryId());
				storeVO1.setCountryName(country.getCountryName());
				 }

				 if(store.getState() != null) {
				State state = stateRepository.findByStateId(store.getState().getStateId());
				storeVO1.setStateId(state.getStateId());
				storeVO1.setStateName(state.getStateName());
			}

			 if(store.getCity() != null) {
				City city = cityRepository.findByCityId(store.getCity().getCityId());
				storeVO1.setCityId(city.getCityId());
				storeVO1.setCityName(city.getCityName());
				 }

				if(store.getRegion() != null) {
				Region region = regionRepository.findByRegionId(store.getRegion().getRegionId());
				storeVO1.setRegionId(region.getRegionId());
				storeVO1.setRegionName(region.getRegionName());
				 }
*/
				storeVO1.setStoreAllotedDetailInfo(storeAllotedDetailsVO);
				storeVOs.add(storeVO1);

			}
		
		}
		
		
		return new ResponseEntity<>(storeVOs, HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GET_STORE_BY_ID)
	@ResponseBody
	public ResponseEntity<StoreVO> getStoreById(@PathVariable Long id) {
		Store store = storeService.getStoreById(id);

		StoreVO storeVO = new StoreVO();

		BeanUtils.copyProperties(store, storeVO);
		if (store == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<StoreAllotedDetailsVO> storeAllotedDetailsVO = new ArrayList<StoreAllotedDetailsVO>();
		List<StoreAllotedDetails> storeAllotedDetails = storeAllotedDetailsRepository
				.findByStoreStoreId(store.getStoreId());
		for (StoreAllotedDetails storeAllotedDetail : storeAllotedDetails) {
			StoreAllotedDetailsVO storeAllotedDetailVO = new StoreAllotedDetailsVO();
			BeanUtils.copyProperties(storeAllotedDetail, storeAllotedDetailVO);
			StoreAlloted storeAlloted = storeAllotedService
					.getStoreAllotedById(storeAllotedDetail.getStoreAlloted().getStoreAllotedId());
			storeAllotedDetailVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
			storeAllotedDetailVO.setStoreAllotedId(storeAlloted.getStoreAllotedId());
			storeAllotedDetailVO.setStoreAllotedType(storeAlloted.getStoreAllotedType());
			storeAllotedDetailsVO.add(storeAllotedDetailVO);
		}

		BeanUtils.copyProperties(store, storeVO);
/*
		 if(store.getOwnerType()  !=null ) {
				OwnerType ownerType = ownerTypeRepository.findByOwnerTypeId(store.getOwnerType().getOwnerTypeId());
				storeVO.setOwnerTypeId(ownerType.getOwnerTypeId());
				storeVO.setOwnerTypeName(ownerType.getOwnerTypeName());
				 }
*/
				 if(store.getStoreServiceType() !=null) {
				StoreServiceType storeServiceType = storeServiceTypeRepository
						.findByStoreServiceTypeId(store.getStoreServiceType().getStoreServiceTypeId());
				storeVO.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
				storeVO.setStoreServiceTypeName(storeServiceType.getStoreServiceTypeName());
				 }

				 if(store.getStoreBusinessServiceType() !=null ) {
				StoreBusinessServiceType storeBusinessServiceType = storeBusinessServiceTypeRepository
						.findByStoreBusinessServiceTypeId(
								store.getStoreBusinessServiceType().getStoreBusinessServiceTypeId());
				storeVO.setStoreBusinessServiceTypeId(storeBusinessServiceType.getStoreBusinessServiceTypeId());
				storeVO.setStoreServiceTypeName(storeBusinessServiceType.getStoreBusinessServiceTypeName());
			 }

				 if(store.getWebMaster() !=null) {
				WebMaster webMaster = webMasterRepo.findByWebMasterId(store.getWebMaster().getWebMasterId());
				storeVO.setWebMasterId(webMaster.getWebMasterId());
				storeVO.setWebMasterName(webMaster.getWebMasterName());
				}

	/*			 if(store.getCountry() != null) {
				Country country = countryRepository.findByCountryId(store.getCountry().getCountryId());
				storeVO.setCountryId(country.getCountryId());
				storeVO.setCountryName(country.getCountryName());
				 }

				 if(store.getState() != null) {
				State state = stateRepository.findByStateId(store.getState().getStateId());
				storeVO.setStateId(state.getStateId());
				storeVO.setStateName(state.getStateName());
			}

			 if(store.getCity() != null) {
				City city = cityRepository.findByCityId(store.getCity().getCityId());
				storeVO.setCityId(city.getCityId());
				storeVO.setCityName(city.getCityName());
				 }

				if(store.getRegion() != null) {
				Region region = regionRepository.findByRegionId(store.getRegion().getRegionId());
				storeVO.setRegionId(region.getRegionId());
				storeVO.setRegionName(region.getRegionName());
				 }
*/
		storeVO.setStoreAllotedDetailInfo(storeAllotedDetailsVO);
		return new ResponseEntity<StoreVO>(storeVO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = WebConstantUrl.GET_STORE_BY_BUSSINESS_ID)
	@ResponseBody
	public ResponseEntity<?> getStoreByBussinessId(@PathVariable Long id) {
		

		List<Store> stores = storeService.getStoreByBussinessId(id);
		
		if(stores.isEmpty()) {
		    return new ResponseEntity<>( HttpStatus.NOT_FOUND);

		}
		
		
		List<StoreVO> storeVOs=stores.stream().map(store->{
			StoreVO storeVO = new StoreVO();
			BeanUtils.copyProperties(store, storeVO);
            return storeVO;
			
		}).collect(Collectors.toList());
		
		
		
		
		
		
		return new ResponseEntity<>(storeVOs, HttpStatus.OK);
	}

	/*
	 * @PostMapping(value = WebConstantUrl.SAVE_STORE)
	 * 
	 * @ResponseBody public ResponseEntity<Map<String,Object>>
	 * saveEpStore(@RequestBody StoreVO storeVo) { Map<String,Object> map = new
	 * HashMap<>();
	 * 
	 * 
	 * boolean epStoretoreCodeStatus =
	 * storeService.checkIfStoreCodeIsExit(storeVo.getStoreCode());
	 * if(epStoretoreCodeStatus) { map.put("status code", "400");
	 * map.put("client status", "Bad Request"); map.put("error msg",
	 * "Eyeplus Store Code " + storeVo.getStoreCode() +
	 * " is already present. So Duplicate entry for 'Eyeplus Store Code' can not be allowed."
	 * ); return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	 * }else{
	 * 
	 * WebRole webRole = webRoleService.getById(storeVo.getWebRoleId()); StoreVO
	 * storeVo1 = storeMapper.getVoFromEntity(storeService.saveStore(storeMapper.
	 * getEntityFromVo(storeVo),webRole.getWebRoleId(),storeVo.getReportingTo()));
	 * 
	 * map.put("storeVO", storeVo1); return new
	 * ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED); } }
	 */

	@PostMapping(value = WebConstantUrl.SAVE_STORE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveEpStore(@RequestBody StoreVO storeVo) {
		Map<String, Object> map = new HashMap<>();
		
		
			User user = userRepo.getByUserId(storeVo.getReportingToId());
			
			if(user == null) {
				
				map.put("status code", 204);
				map.put("error msg", " The given Reporting To User is not available in the user table please select available user.");
			    return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
			}
			
		/*	Store storeCostCentre = storeRepository.findByCostcentre(storeVo.getCostcentre());

         if(storeCostCentre != null) {
				
				map.put("status code", 400);
				map.put("error msg", " The given Cost center is Unique in Store table please select Unique CostCenter.");
			    return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			}
*/
			
		boolean epStoretoreCodeStatus = storeService.checkIfStoreCodeIsExit(storeVo.getStoreCode());
		
		if (epStoretoreCodeStatus) {
			map.put("status code", "400");
			map.put("client status", "Bad Request");
			map.put("error msg", "Eyeplus Store Code " + storeVo.getStoreCode()
					+ " is already present. So Duplicate entry for 'Eyeplus Store Code' can not be allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {

			Store store = new Store();

			BeanUtils.copyProperties(storeVo, store);
			if (storeVo.getOwnerTypeId() != null) {
				store.setOwnerType(ownerTypeService.getOwnerTypeById(storeVo.getOwnerTypeId()));
			}
			
			if (storeVo.getStoreServiceTypeId() != null) {
				store.setStoreServiceType(
						storeServiceTypeService.getStoreServiceTypeById(storeVo.getStoreServiceTypeId()));
			}
			if (storeVo.getStoreBusinessServiceTypeId() != null) {
				store.setStoreBusinessServiceType(storeBusinessServiceTypeService
						.getStoreBusinessServiceTypeById(storeVo.getStoreBusinessServiceTypeId()));
			}

			if (storeVo.getWebMasterId() != null) {
				store.setWebMaster(webMasterService.getById(storeVo.getWebMasterId()));
			}
/*
			if (storeVo.getCountryId() != 0 && storeVo.getCountryId() != null) {
				store.setCountry(countryService.getCountryById(storeVo.getCountryId()));
			}

			if (storeVo.getStateId() != 0 && storeVo.getStateId() != null) {
				store.setState(stateService.getStateById(storeVo.getStateId()));
			}

			if (storeVo.getCityId() != 0 && storeVo.getCityId() != null) {
				store.setCity(cityService.getCityById(storeVo.getCityId()));
			}
			if (storeVo.getRegionId() != 0 && storeVo.getRegionId() != null) {
				store.setRegion(regionService.getRegionById(storeVo.getRegionId()));
			}

			*/
           //SAVING STORE........................................................................................
		   //....................................................................................................
			store = storeService.saveStore(store,storeVo.getReportingToId());  
           //....................................................................................................
			//...................................................................................................
			
			StoreVO storeVO2 = new StoreVO();

			BeanUtils.copyProperties(store, storeVO2);
			if (storeVo.getOwnerTypeId() != null) {
				OwnerType ownerType = ownerTypeService.getOwnerTypeById(store.getOwnerType().getOwnerTypeId());
				storeVO2.setOwnerTypeId(ownerType.getOwnerTypeId());
				storeVO2.setOwnerTypeName(ownerType.getOwnerTypeName());
			}

			if (storeVo.getStoreServiceTypeId() != null) {
				StoreServiceType storeServiceType = storeServiceTypeService
						.getStoreServiceTypeById(store.getStoreServiceType().getStoreServiceTypeId());
				storeVO2.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
				storeVO2.setStoreServiceTypeName(storeServiceType.getStoreServiceTypeName());
			}

			if (storeVo.getStoreBusinessServiceTypeId() != null) {
				StoreBusinessServiceType storeBusinessServiceType = storeBusinessServiceTypeService
						.getStoreBusinessServiceTypeById(
								store.getStoreBusinessServiceType().getStoreBusinessServiceTypeId());
				storeVO2.setStoreBusinessServiceTypeId(storeBusinessServiceType.getStoreBusinessServiceTypeId());
				storeVO2.setStoreServiceTypeName(storeBusinessServiceType.getStoreBusinessServiceTypeName());
			}

			if (storeVo.getWebMasterId() != null) {
				WebMaster webMaster = webMasterService.getById(store.getWebMaster().getWebMasterId());
				storeVO2.setWebMasterId(webMaster.getWebMasterId());
				storeVO2.setWebMasterName(webMaster.getWebMasterName());
			}
/*
			if (storeVo.getCountryId() != 0 && storeVo.getCountryId() != null) {
				Country country = countryService.getCountryById(store.getCountry().getCountryId());
				storeVO2.setCountryId(country.getCountryId());
				storeVO2.setCountryName(country.getCountryName());
			}

			if (storeVo.getStateId() != 0 && storeVo.getStateId() != null) {
				State state = stateService.getStateById(store.getState().getStateId());
				storeVO2.setStateId(state.getStateId());
				storeVO2.setStateName(state.getStateName());
			}

			if (storeVo.getCityId() != 0 && storeVo.getCityId() != null) {
				City city = cityService.getCityById(store.getCity().getCityId());
				storeVO2.setCityId(city.getCityId());
				storeVO2.setCityName(city.getCityName());
			}

			if (storeVo.getRegionId() != 0 && storeVo.getRegionId() != null) {
				Region region = regionService.getRegionById(store.getRegion().getRegionId());
				storeVO2.setRegionId(region.getRegionId());
				storeVO2.setRegionName(region.getRegionName());
			}
*/
			List<StoreAllotedDetailsVO> storeAllotedDetailsVOs = new ArrayList<StoreAllotedDetailsVO>();
			if (!storeVo.getStoreAllotedDetailInfo().isEmpty()) {
				for (StoreAllotedDetailsVO storeAllotedDetailInfoVO : storeVo.getStoreAllotedDetailInfo()) {

					StoreAllotedDetails storeAllotedDetail = new StoreAllotedDetails();

					if (storeAllotedDetailInfoVO.getStoreAllotedId() != 0) {

						StoreAlloted storeAlloted = storeAllotedService
								.getStoreAllotedById(storeAllotedDetailInfoVO.getStoreAllotedId());
						if (storeAlloted != null) {

							storeAllotedDetail.setStore(store);
							storeAllotedDetail.setStoreAlloted(storeAlloted);

							StoreAllotedDetailsVO storeAllotedDetailsVO = new StoreAllotedDetailsVO();

							storeAllotedDetailsVO.setStoreAllotedId(storeAlloted.getStoreAllotedId());
							storeAllotedDetailsVO.setStoreAllotedType(storeAlloted.getStoreAllotedType());
							storeAllotedDetailsRepository.save(storeAllotedDetail);

							storeAllotedDetailsVOs.add(storeAllotedDetailsVO);

						}
					}
				}
			}

			storeVO2.setStoreAllotedDetailInfo(storeAllotedDetailsVOs);
			// StoreVO storeVo1 =
			// storeMapper.getVoFromEntity(storeService.saveStore(storeMapper.getEntityFromVo(storeVo),webRole.getWebRoleId(),storeVo.getReportingTo()));
			map.put("status code", 201);
			map.put("sucess msg", " Store created sucessfully.");
			map.put("storeVO", storeVO2);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}


	
	

	@PutMapping(value = WebConstantUrl.UPDATE_STORE)
	@ResponseBody
	public ResponseEntity<StoreVO> updateEpStore(@RequestBody StoreVO epStoreVo) {
		Store epStore = storeMapper.getEntityFromVo(epStoreVo);
		epStore = storeService.updateStore(epStore);

		if (epStore == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		StoreVO storeVO2 = new StoreVO();
		BeanUtils.copyProperties(epStore, storeVO2);
		
		if(epStore.getOwnerType() != null) {
		OwnerType ownerType = ownerTypeService.getOwnerTypeById(epStore.getOwnerType().getOwnerTypeId());
		storeVO2.setOwnerTypeId(ownerType.getOwnerTypeId());
		storeVO2.setOwnerTypeName(ownerType.getOwnerTypeName());
		}
		
		if(epStore.getStoreServiceType() != null) {
		StoreServiceType storeServiceType = storeServiceTypeService
				.getStoreServiceTypeById(epStore.getStoreServiceType().getStoreServiceTypeId());
		storeVO2.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
		storeVO2.setStoreServiceTypeName(storeServiceType.getStoreServiceTypeName());
		}
		
		if(epStore.getStoreBusinessServiceType() != null) {
		StoreBusinessServiceType storeBusinessServiceType = storeBusinessServiceTypeService
				.getStoreBusinessServiceTypeById(epStore.getStoreBusinessServiceType().getStoreBusinessServiceTypeId());
		storeVO2.setStoreBusinessServiceTypeId(storeBusinessServiceType.getStoreBusinessServiceTypeId());
		storeVO2.setStoreServiceTypeName(storeBusinessServiceType.getStoreBusinessServiceTypeName());
		}
		
		if(epStore.getWebMaster() != null) {
		WebMaster webMaster = webMasterService.getById(epStore.getWebMaster().getWebMasterId());
		storeVO2.setWebMasterId(webMaster.getWebMasterId());
		storeVO2.setWebMasterName(webMaster.getWebMasterName());
		}
		
/*		if(epStore.getCountry() != null) {
		Country country = countryService.getCountryById(epStore.getCountry().getCountryId());
		storeVO2.setCountryId(country.getCountryId());
		storeVO2.setCountryName(country.getCountryName());
		}
		
		if(epStore.getState() != null) {
		State state = stateService.getStateById(epStore.getState().getStateId());
		storeVO2.setStateId(state.getStateId());
		storeVO2.setStateName(state.getStateName());
		}

		if(epStore.getCity() != null) {
		City city = cityService.getCityById(epStore.getCity().getCityId());
		storeVO2.setCityId(city.getCityId());
		storeVO2.setCityName(city.getCityName());
		}

		if(epStore.getRegion() != null) {
		Region region = regionService.getRegionById(epStore.getRegion().getRegionId());
		storeVO2.setRegionId(region.getRegionId());
		storeVO2.setRegionName(region.getRegionName());
		}
*/
		
		List<StoreAllotedDetails> storeAllotedDetails = storeAllotedDetailsRepository.findByStoreStoreId(epStoreVo.getStoreId());
		if(!storeAllotedDetails.isEmpty()) {
			
		for(StoreAllotedDetails storeAllotedDetail :storeAllotedDetails) {
			
			storeAllotedDetailsRepository.deleteById(storeAllotedDetail.getStoreAllotedDetailsId());
		}
			
			
		}
		
		List<StoreAllotedDetailsVO> storeAllotedDetailsVOs = new ArrayList<StoreAllotedDetailsVO>();
		if (!epStoreVo.getStoreAllotedDetailInfo().isEmpty()) {
			for (StoreAllotedDetailsVO storeAllotedDetailInfoVO : epStoreVo.getStoreAllotedDetailInfo()) {

				
				StoreAllotedDetails storeAllotedDetail = storeAllotedDetailsRepository.findByStoreAllotedStoreAllotedIdAndStoreStoreId(storeAllotedDetailInfoVO.getStoreAllotedId(), epStoreVo.getStoreId());
				
				if (storeAllotedDetail != null) {

				            storeAllotedDetail.setStore(epStore);
							storeAllotedDetail.setStoreAlloted(storeAllotedDetail.getStoreAlloted());

							StoreAllotedDetailsVO storeAllotedDetailsVO = new StoreAllotedDetailsVO();
							storeAllotedDetailsVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
							storeAllotedDetailsVO.setStoreAllotedId(storeAllotedDetail.getStoreAlloted().getStoreAllotedId());
							storeAllotedDetailsVO.setStoreAllotedType(storeAllotedDetail.getStoreAlloted().getStoreAllotedType());
						//    storeAllotedDetailsRepository.save(storeAllotedDetail);
						    
						    storeAllotedDetailsVOs.add(storeAllotedDetailsVO);
							
				}else {
							storeAllotedDetail = new StoreAllotedDetails();
							StoreAlloted storeAlloted = storeAllotedService.getStoreAllotedById(storeAllotedDetailInfoVO.getStoreAllotedId());
							storeAllotedDetail.setStore(epStore);
							storeAllotedDetail.setStoreAlloted(storeAlloted);

							StoreAllotedDetailsVO storeAllotedDetailsVO = new StoreAllotedDetailsVO();
							storeAllotedDetailsVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
							storeAllotedDetailsVO.setStoreAllotedId(storeAllotedDetailInfoVO.getStoreAllotedId());
							storeAllotedDetailsVO.setStoreAllotedType(storeAllotedDetailInfoVO.getStoreAllotedType());
						//	storeAllotedDetailsRepository.save(storeAllotedDetail);

							storeAllotedDetailsVOs.add(storeAllotedDetailsVO);
						}
					}
				}
					
		Set<StoreAllotedDetailsVO> primesWithoutDuplicates1 = storeAllotedDetailsVOs.stream().collect(
				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(StoreAllotedDetailsVO::getStoreAllotedId))));
		
		storeAllotedDetailsVOs.clear();
		
		storeAllotedDetailsVOs.addAll(primesWithoutDuplicates1);

			storeVO2.setStoreAllotedDetailInfo(storeAllotedDetailsVOs);

		return new ResponseEntity<StoreVO>(storeVO2, HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_STORE_BY_ID)
	@ResponseBody
	public void deleteStore(@PathVariable Long id) {
		storeService.deleteStoreById(id);
	}
	
	@GetMapping("/exportExcel/{id}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("id") Long id) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=store.xlsx";
        response.setHeader(headerKey, headerValue);
         
         MasterHeaders masterHeaders=new MasterHeaders();
     //   List<Object[]> stores=storeService.getAllForExcel();
 		List<Object[]> stores = null;
		WebMaster name=webMasterRepo.findByWebMasterId(id);
		if(id !=18 && id !=1 ) {
			stores = storeService.getAllForExcel(id);
		}
		else {stores = storeService.getAllForExcel();}
        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.stores,stores);
         
        excelExporter.export(response);    
    }  


}
