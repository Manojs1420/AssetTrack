package com.titan.irgs.user.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.application.util.Status;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.domain.StoreAlloted;
import com.titan.irgs.master.domain.StoreAllotedDetails;
import com.titan.irgs.master.domain.StoreBusinessServiceType;
import com.titan.irgs.master.domain.StoreServiceType;
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
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.model.UserModel;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.serviceImpl.UserServiceImpl;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;
import com.titan.irgs.webRole.repository.WebRoleRepo;
import com.titan.irgs.webRole.service.IWebRoleService;


@Component
public class UserMapper {

	@Autowired
	WebRoleRepo webRoleRepo;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	IWebRoleService webRoleService;
	
	@Autowired
	UserRepo userRepo;
	
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
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
	IStoreService storeService;
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
	WebMasterService webMasterService;

	@Autowired
	WebMasterRepo webMasterRepo;

	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	CountryService countryService;
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;
	
	public User convertModeltoDomain(UserModel userModel) {
		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		user.setAddress(userModel.getAddress());
		user.setAccountNonExpired(true);
		user.setCreatedBy(1l);
		
		user.setIsgroupBusiness(userModel.getIsgroupBusiness());
		
		user.setInventoryUser(userModel.getInventoryUser());
		
		if(userModel.getStatus()!=null && userModel.getStatus().equals(Status.INACTIVE)) {
			user.setAccountNonLocked(false);
		}else {
			
			user.setAccountNonLocked(true);

		}
		
		user.setRole(webRoleRepo.findById(userModel.getWebRoleId()).orElseThrow(()->new ResourceNotFoundException("The webRole not found")));
		
		Department department=departmentRepo.findById(userModel.getDepartmentId()).orElseThrow(()-> new ResourceNotFoundException("The Department not found"));
		
		if(department!=null) {
			RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(userModel.getWebRoleId(),department.getDepartmentId());
			user.setRoleWiseDepartments(roleWiseDepartments);
		}
		
		user.setStoreServiceType(storeServiceTypeRepository.findById(userModel.getStoreServiceTypeId()).orElseThrow(()->new ResourceNotFoundException("The User Service Type Id  not found")));
		user.setOwnerType(ownerTypeRepository.findById(userModel.getOwnerTypeId()).orElseThrow(()->new ResourceNotFoundException("The Owner Type Id  not found")));
		
		
		return user;
	}

	public UserModel convertDomaintoModel(User user) {
		UserModel userModel= new UserModel();
		user.setAccountNonExpired(user.getAccountNonExpired());
		user.setAccountNonLocked(user.getAccountNonLocked());
		user.setCredentialsNonExpired(user.getCredentialsNonExpired());
		user.setIsEnabled(user.getIsEnabled());
		
		BeanUtils.copyProperties(user, userModel);
		userModel.setAddress(user.getAddress());
		userModel.setId(user.getId());
			
		userModel.setWebRoleName(user.getRole().getRole().getRoleName());
		userModel.setVerticleRoleName(user.getRole().getWebMaster().getWebMasterName());
		userModel.setWebRoleId(user.getRole().getWebRoleId());
		userModel.setVerticleRoleId(user.getRole().getWebMaster().getWebMasterId());	
		if(user.getRoleWiseDepartments()!=null) {
			userModel.setDepartmentId(user.getRoleWiseDepartments().getDepartment().getDepartmentId());
			userModel.setDepartmentName(user.getRoleWiseDepartments().getDepartment().getDepartmentName());
			userModel.setRoleWiseDepartmentsId(user.getRoleWiseDepartments().getRoleWiseDepartmentsId());
			userModel.setReportingToName(user.getRole().getReporting().getRoleName());
			
		}
		/*
		if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {

			Store store=storeRepository.findByStoreCode(user.getUsername());
			RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findByRoleWiseDepartmentsId(store.getRoleWiseDepartmentId());
			userModel.setWebRoleName(roleWiseDepartments.getWebRole().getRole().getRoleName());
			userModel.setWebRoleId(roleWiseDepartments.getWebRole().getWebRoleId());
			userModel.setVerticleRoleName(roleWiseDepartments.getWebRole().getWebMaster().getWebMasterName());
			userModel.setVerticleRoleId(roleWiseDepartments.getWebRole().getWebMaster().getWebMasterId());	
			if(user.getRoleWiseDepartments()!=null) {
				userModel.setDepartmentId(roleWiseDepartments.getDepartment().getDepartmentId());
				userModel.setDepartmentName(roleWiseDepartments.getDepartment().getDepartmentName());
				userModel.setRoleWiseDepartmentsId(store.getRoleWiseDepartmentId());
				User user2=userRepo.getByUserId(store.getReportingToId());
				userModel.setReportingToName(user2.getUsername());
				
			}
		}else {
			userModel.setWebRoleName(user.getRole().getRole().getRoleName());
			userModel.setVerticleRoleName(user.getRole().getWebMaster().getWebMasterName());
			userModel.setWebRoleId(user.getRole().getWebRoleId());
			userModel.setVerticleRoleId(user.getRole().getWebMaster().getWebMasterId());	
			if(user.getRoleWiseDepartments()!=null) {
				userModel.setDepartmentId(user.getRoleWiseDepartments().getDepartment().getDepartmentId());
				userModel.setDepartmentName(user.getRoleWiseDepartments().getDepartment().getDepartmentName());
				userModel.setRoleWiseDepartmentsId(user.getRoleWiseDepartments().getRoleWiseDepartmentsId());
				userModel.setReportingToName(user.getRole().getReporting().getRoleName());
				
			}
		}
		*/
		 List<String> regionList = userRepo.getAllRegionForUserId(user.getId());
		 String regionData = "";
		 for(String regionName : regionList) {
			 
			 if(regionList.get(0).equals(regionName)) {
				 regionData = regionData+regionName;
			 } else {
				 regionData = regionData+", "+regionName;
			 }
			 
		 }
		 userModel.setRegionTypeName(regionData);
		 
		  
		  User user2 = userRepo.findByIdAndRoleWebRoleId(user.getId(), user.getRole().getWebRoleId());
		Long  roleId = user2.getRole().getReporting().getRoleId();
		 // User user1 = userServiceImpl.getById(roleId);
		 //userModel.setReportingToName(user1.getUsername());
		/*
		 * userModel.setOperationTypeName(user.getRole().getOpertionType().
		 * getOpertionType().toString());
		 */
		 
		userModel.setOperationTypeName(user2.getRole().getOpertionType().
				  getOpertionType().toString());
		if(user.getAccountNonLocked()) {
			userModel.setStatus(Status.ACTIVE);
		}
		else {
			userModel.setStatus(Status.INACTIVE);
		}
		
		if(user.getGroupBusinessVerticalMaster()!=null) {
		userModel.setGroupBusinessMasterId(user.getGroupBusinessVerticalMaster().getId());
		userModel.setGroupBusinessMasterName(user.getGroupBusinessVerticalMaster().getGroupBusinessVerticalName());
		userModel.setIsgroupBusiness(user.getIsgroupBusiness());
		}
		
		if(user.getStoreServiceType()!=null) {

			StoreServiceType storeServiceType=storeServiceTypeRepository.findByStoreServiceTypeId(user.getStoreServiceType().getStoreServiceTypeId());
			userModel.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
		}

		if(user.getOwnerType()!=null) {
			OwnerType ownerType=ownerTypeRepository.findByOwnerTypeId(user.getOwnerType().getOwnerTypeId());
			userModel.setOwnerTypeId(ownerType.getOwnerTypeId());
		}
		/*
		if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {
			Store store=storeRepository.findByStoreCode(user.getUsername());
			if(store!=null) {
				StoreVO storeVO=new StoreVO();
				BeanUtils.copyProperties(store, storeVO);
				
				if(store.getOwnerType()!=null) {
					storeVO.setOwnerTypeId(store.getOwnerType().getOwnerTypeId());
				}
				if(store.getStoreServiceType()!=null) {
					storeVO.setStoreServiceTypeId(store.getStoreServiceType().getStoreServiceTypeId());
				}
				storeVO.setReportingToId(store.getReportingToId());
				
				storeVO=getstoredata(store,storeVO);
				userModel.setStoreVO(storeVO);
			}
		}*/
		userModel.setIsgroupBusiness("false");
		return userModel;
	}
	
	public StoreVO getstoredata(Store store,StoreVO storeVo) {
		
		if (storeVo.getOwnerTypeId() != null) {
			OwnerType ownerType = ownerTypeService.getOwnerTypeById(storeVo.getOwnerTypeId());
			storeVo.setOwnerTypeId(ownerType.getOwnerTypeId());
			storeVo.setOwnerTypeName(ownerType.getOwnerTypeName());
		}

		if (storeVo.getStoreServiceTypeId() != null) {
			StoreServiceType storeServiceType = storeServiceTypeService
					.getStoreServiceTypeById(storeVo.getStoreServiceTypeId());
			storeVo.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
			storeVo.setStoreServiceTypeName(storeServiceType.getStoreServiceTypeName());
		}

		if (storeVo.getStoreBusinessServiceTypeId() != null) {
			StoreBusinessServiceType storeBusinessServiceType = storeBusinessServiceTypeService
					.getStoreBusinessServiceTypeById(
							storeVo.getStoreBusinessServiceTypeId());
			storeVo.setStoreBusinessServiceTypeId(storeBusinessServiceType.getStoreBusinessServiceTypeId());
			storeVo.setStoreServiceTypeName(storeBusinessServiceType.getStoreBusinessServiceTypeName());
		}

		if (storeVo.getWebMasterId() != null) {
			WebMaster webMaster = webMasterService.getById(storeVo.getWebMasterId());
			storeVo.setWebMasterId(webMaster.getWebMasterId());
			storeVo.setWebMasterName(webMaster.getWebMasterName());
		}
/*
		if (storeVo.getCountryId() != null) {
			Country country = countryService.getCountryById(storeVo.getCountryId());
			storeVo.setCountryId(country.getCountryId());
			storeVo.setCountryName(country.getCountryName());
		}

		if (storeVo.getStateId() != null) {
			State state = stateService.getStateById(storeVo.getStateId());
			storeVo.setStateId(state.getStateId());
			storeVo.setStateName(state.getStateName());
		}

		if (storeVo.getCityId() != null) {
			City city = cityService.getCityById(storeVo.getCityId());
			storeVo.setCityId(city.getCityId());
			storeVo.setCityName(city.getCityName());
		}

		if (storeVo.getRegionId() != null) {
			Region region = regionService.getRegionById(storeVo.getRegionId());
			storeVo.setRegionId(region.getRegionId());
			storeVo.setRegionName(region.getRegionName());
		}
*/
		List<StoreAllotedDetailsVO> storeAllotedDetailsVOs = new ArrayList<StoreAllotedDetailsVO>();
		if (storeVo.getStoreAllotedDetailInfo()!=null && !storeVo.getStoreAllotedDetailInfo().isEmpty()) {
			for (StoreAllotedDetailsVO storeAllotedDetailInfoVO : storeVo.getStoreAllotedDetailInfo()) {

				
				StoreAllotedDetails storeAllotedDetail = storeAllotedDetailsRepository.findByStoreAllotedStoreAllotedIdAndStoreStoreId(storeAllotedDetailInfoVO.getStoreAllotedId(), storeVo.getStoreId());
				
				if (storeAllotedDetail != null) {

				            storeAllotedDetail.setStore(store);
							storeAllotedDetail.setStoreAlloted(storeAllotedDetail.getStoreAlloted());

							StoreAllotedDetailsVO storeAllotedDetailsVO = new StoreAllotedDetailsVO();
							storeAllotedDetailsVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
							storeAllotedDetailsVO.setStoreAllotedId(storeAllotedDetail.getStoreAlloted().getStoreAllotedId());
							storeAllotedDetailsVO.setStoreAllotedType(storeAllotedDetail.getStoreAlloted().getStoreAllotedType());
						    storeAllotedDetailsRepository.save(storeAllotedDetail);
						    
						    storeAllotedDetailsVOs.add(storeAllotedDetailsVO);
							
				}else {
							storeAllotedDetail = new StoreAllotedDetails();
							StoreAlloted storeAlloted = storeAllotedService.getStoreAllotedById(storeAllotedDetailInfoVO.getStoreAllotedId());
							storeAllotedDetail.setStore(store);
							storeAllotedDetail.setStoreAlloted(storeAlloted);

							StoreAllotedDetailsVO storeAllotedDetailsVO = new StoreAllotedDetailsVO();
							storeAllotedDetailsVO.setStoreAllotedDetailId(storeAllotedDetail.getStoreAllotedDetailsId());
							storeAllotedDetailsVO.setStoreAllotedId(storeAllotedDetailInfoVO.getStoreAllotedId());
							storeAllotedDetailsVO.setStoreAllotedType(storeAllotedDetailInfoVO.getStoreAllotedType());
							storeAllotedDetailsRepository.save(storeAllotedDetail);

							storeAllotedDetailsVOs.add(storeAllotedDetailsVO);
						}
					}
				}
					
		Set<StoreAllotedDetailsVO> primesWithoutDuplicates1 = storeAllotedDetailsVOs.stream().collect(
				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(StoreAllotedDetailsVO::getStoreAllotedId))));
		
		storeAllotedDetailsVOs.clear();
		
		storeAllotedDetailsVOs.addAll(primesWithoutDuplicates1);

		storeVo.setStoreAllotedDetailInfo(storeAllotedDetailsVOs);
		
		return storeVo;
	}

	

}



