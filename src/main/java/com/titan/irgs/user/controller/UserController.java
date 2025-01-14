package com.titan.irgs.user.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
/**
 * 
 * 
 */
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.application.util.Status;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.master.domain.ClusterUser;
import com.titan.irgs.master.mapper.StoreMapper;
import com.titan.irgs.master.repository.GroupBusinessVerticalMasterRepo;
import com.titan.irgs.master.repository.OwnerTypeRepository;
import com.titan.irgs.master.repository.StoreAllotedDetailsRepository;
import com.titan.irgs.master.repository.StoreBusinessServiceTypeRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.StoreServiceTypeRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IClusterService;
import com.titan.irgs.master.service.IClusterUserService;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.serviceImpl.OwnerTypeService;
import com.titan.irgs.master.serviceImpl.StoreAllotedService;
import com.titan.irgs.master.serviceImpl.StoreBusinessServiceTypeService;
import com.titan.irgs.master.serviceImpl.StoreServiceTypeService;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;
import com.titan.irgs.security.configuration.JwtProvider;
import com.titan.irgs.serviceRequest.controller.EmailServiceImpl;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.mapper.UserMapper;
import com.titan.irgs.user.model.JwtResponse;
import com.titan.irgs.user.model.LoginForm;
import com.titan.irgs.user.model.UserModel;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@RestController
@RequestMapping(WebConstantUrl.USER)
public class UserController {

	@Autowired
	EmailServiceImpl mailService;

	@Value("${mail.status}")
	private Boolean mailStatus;

	@Autowired
	IUserService iUserService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	IClusterService iClusterService;

	@Autowired
	IClusterUserService iClusterUserService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	StoreRepository storeRepository;
	@Autowired
	WebMasterRepo webMasterRepo;
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	GroupBusinessVerticalMasterRepo groupBusinessVerticalMasterRepo;
	
	@Autowired
	InventoryRepository inventoryRepository;

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
	StoreMapper storeMapper;
	
	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;
	
	@Autowired
	WebRoleRepo webRoleRepo;

	private static final String superAdmin = "superadmin";
	private final static String ITAdmin="ITAdmin";
	private static final String MANAGEMENT = "MANAGEMENT";
	private final static String VENDOR = "Vendor";
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping(WebConstantUrl.save)
	public ResponseEntity<?> save(@RequestBody UserModel userModel, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		User existUser = iUserService.findByUserName(userModel.getUsername());
		if (existUser != null) {
			throw new ResourceAlreadyExitException(userModel.getUsername() + " already exists");
		}

		User user = userMapper.convertModeltoDomain(userModel);
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		/*
		if(userModel.getIsgroupBusiness().equalsIgnoreCase("true") || userModel.getIsgroupBusiness()=="true")
		{
			user.setGroupBusinessVerticalMaster(groupBusinessVerticalMasterRepo.findById(userModel.getGroupBusinessMasterId()).orElseThrow(()-> new ResourceNotFoundException("The group business Id not found")));
		}*/
			
	/*	if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {
			RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.getInventoryUserwebroleusingverticalAndDepartment(userModel.getVerticleRoleId(),userModel.getDepartmentId());
			user.setRoleWiseDepartments(roleWiseDepartments);
			user.setRole(roleWiseDepartments.getWebRole());
			user = iUserService.save(user);
		}else {*/
			user = iUserService.save(user);
	//	}
		
		for (Long regionId : userModel.getRegionIds()) {
			Cluster cluster = iClusterService.findByRegionRegionIdAndWebRoleWebRoleId(regionId,
					userModel.getWebRoleId());
			if (cluster == null) {
				throw new ResourceNotFoundException("The region id" + regionId + " is not attached with role");
			}
			ClusterUser clusterUser = new ClusterUser();
			clusterUser.setCluster(cluster);
			clusterUser.setCreatedDate(new Date());
			clusterUser.setUserId(user.getId());
			clusterUser.setUserActive(Status.ACTIVE.name());
			clusterUser.setUserName(user.getUsername());
			iClusterUserService.saveClusterUser(clusterUser);
		}
		
		/*
		if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {
			StoreVO storeVO=new StoreVO();
			//StoreVO storeVO=userModel.getStoreVO();
			storeVO.setOwnerTypeId(userModel.getOwnerTypeId());
			storeVO.setStoreServiceTypeId(userModel.getStoreServiceTypeId());
			storeVO.setReportingToId(userModel.getReportingToId());
			storeVO.setWebMasterId(userModel.getVerticleRoleId());
			RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(userModel.getWebRoleId(), userModel.getDepartmentId());
			storeVO.setRoleWiseDepartmentId(roleWiseDepartments.getRoleWiseDepartmentsId());
			
			Store store = new Store();

			storeVO.setStoreCode(user.getUsername());
			storeVO.setStoreName(user.getFirstName());
			
			BeanUtils.copyProperties(storeVO, store);
			
			if (storeVO.getOwnerTypeId() != null) {
				store.setOwnerType(ownerTypeService.getOwnerTypeById(storeVO.getOwnerTypeId()));
			}
			
			if (storeVO.getStoreServiceTypeId() != null) {
				store.setStoreServiceType(
						storeServiceTypeService.getStoreServiceTypeById(storeVO.getStoreServiceTypeId()));
			}
			if (storeVO.getStoreBusinessServiceTypeId() != null) {
				store.setStoreBusinessServiceType(storeBusinessServiceTypeService
						.getStoreBusinessServiceTypeById(storeVO.getStoreBusinessServiceTypeId()));
			}
			
			store=storeService.saveStore(store,storeVO.getReportingToId());
			
		//	List<StoreAllotedDetailsVO> storeAllotedDetailsVOs = new ArrayList<StoreAllotedDetailsVO>();
			if (storeVO.getStoreAllotedDetailInfo()!=null && !storeVO.getStoreAllotedDetailInfo().isEmpty()) {
				for (StoreAllotedDetailsVO storeAllotedDetailInfoVO : storeVO.getStoreAllotedDetailInfo()) {

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

					//		storeAllotedDetailsVOs.add(storeAllotedDetailsVO);

						}
					}
				}
			}

		//	storeVO.setStoreAllotedDetailInfo(storeAllotedDetailsVOs);


		}*/
		
		userModel = userMapper.convertDomaintoModel(user);
		return new ResponseEntity<>(userModel, HttpStatus.OK);

	}

	@SuppressWarnings("unlikely-arg-type")
	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody UserModel userModel, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

	//	User existUser = iUserService.findByUserName(userModel.getUsername());
	 
		User user = userMapper.convertModeltoDomain(userModel);
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		
		if(userModel.getIsgroupBusiness()!=null)
		{
			if(userModel.getIsgroupBusiness().equalsIgnoreCase("true") || userModel.getIsgroupBusiness()=="true") {
			user.setGroupBusinessVerticalMaster(groupBusinessVerticalMasterRepo.findById(userModel.getGroupBusinessMasterId()).orElseThrow(()-> new ResourceNotFoundException("The group business Id not found")));
		
			}
		}
		
	/*	if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {
			RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.getInventoryUserwebroleusingverticalAndDepartment(userModel.getVerticleRoleId(),userModel.getDepartmentId());
			user.setRoleWiseDepartments(roleWiseDepartments);
			user.setRole(roleWiseDepartments.getWebRole());

			user = iUserService.update(user);
		}else {
*/
			user = iUserService.update(user);
	//	}
		

		if (userModel.getStatus().equals(Status.INACTIVE)) {
			List<ClusterUser> clusterUsers = iClusterUserService.getClusterByUserById(user.getId());
			for (ClusterUser clusterUser : clusterUsers) {
				clusterUser.setUserActive(Status.INACTIVE.name());
				iClusterUserService.saveClusterUser(clusterUser);

			}

		}

		if (userModel.getRegionIds() != null || !userModel.getRegionIds().isEmpty()) {
			List<Cluster> clusters = iClusterService.getClustersByUsingUserId(userModel.getId());
			List<Long> userRegionIdsFromDB = clusters.stream().map(m -> m.getRegion().getRegionId())
					.collect(Collectors.toList());
			List<Long> userRegionIdsFromDBandVoCopy = new ArrayList<Long>();
			List<Long> userRegionIdsFromDbCopy = new ArrayList<Long>();
			userRegionIdsFromDbCopy.addAll(userRegionIdsFromDB);
			userRegionIdsFromDbCopy.removeAll(userModel.getRegionIds());

			if (!userRegionIdsFromDbCopy.isEmpty()) {

				for (Long id : userRegionIdsFromDbCopy) {
					Cluster cluster = clusters.stream().filter(c -> c.getRegion().getRegionId() == id).findAny().get();
					iClusterUserService.deleteClusterUserByUserIdAndClusterIdIn(user.getId(), cluster.getClusterId());
				}

			}

			userRegionIdsFromDBandVoCopy.addAll(userRegionIdsFromDB);
			userRegionIdsFromDBandVoCopy.addAll(userModel.getRegionIds());
			userRegionIdsFromDBandVoCopy.removeAll(userRegionIdsFromDB);
			if (!userRegionIdsFromDBandVoCopy.isEmpty()) {
				for (Long regionId : userRegionIdsFromDBandVoCopy) {
					Cluster cluster = iClusterService.findByRegionRegionIdAndWebRoleWebRoleId(regionId,
							userModel.getWebRoleId());
					ClusterUser clusterUser = new ClusterUser();
					clusterUser.setCluster(cluster);
					clusterUser.setCreatedDate(new Date());
					clusterUser.setUserId(user.getId());
					clusterUser.setUserName(user.getUsername());
					clusterUser.setUserActive("ACTIVE");
					iClusterUserService.saveClusterUser(clusterUser);
				}
			}

		}
		
		/*
		if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {
			StoreVO storeVO=new StoreVO();
			//StoreVO storeVO=userModel.getStoreVO();
			storeVO.setOwnerTypeId(userModel.getOwnerTypeId());
			storeVO.setStoreServiceTypeId(userModel.getStoreServiceTypeId());
			storeVO.setReportingToId(userModel.getReportingToId());
			storeVO.setWebMasterId(userModel.getVerticleRoleId());
			RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(userModel.getWebRoleId(), userModel.getDepartmentId());
			storeVO.setRoleWiseDepartmentId(roleWiseDepartments.getRoleWiseDepartmentsId());
	
			storeVO.setStoreCode(user.getUsername());
			storeVO.setStoreName(user.getFirstName());
				
			Store store1=storeRepository.findByStoreCode(storeVO.getStoreCode());
			
			
			if(store1==null) {
				Store store = new Store();
				
				BeanUtils.copyProperties(storeVO, store);
				
				if (storeVO.getOwnerTypeId() != null) {
					store.setOwnerType(ownerTypeService.getOwnerTypeById(storeVO.getOwnerTypeId()));
				}
				
				if (storeVO.getStoreServiceTypeId() != null) {
					store.setStoreServiceType(
							storeServiceTypeService.getStoreServiceTypeById(storeVO.getStoreServiceTypeId()));
				}
				if (storeVO.getStoreBusinessServiceTypeId() != null) {
					store.setStoreBusinessServiceType(storeBusinessServiceTypeService
							.getStoreBusinessServiceTypeById(storeVO.getStoreBusinessServiceTypeId()));
				}
				
				store = storeService.saveStore(store,store.getReportingToId());
				
				if (storeVO.getStoreAllotedDetailInfo()!=null && !storeVO.getStoreAllotedDetailInfo().isEmpty()) {
					for (StoreAllotedDetailsVO storeAllotedDetailInfoVO : storeVO.getStoreAllotedDetailInfo()) {

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

						//		storeAllotedDetailsVOs.add(storeAllotedDetailsVO);

							}
						}
					}
				}
				
			}else {
				storeVO.setStoreId(store1.getStoreId());
				
				BeanUtils.copyProperties(storeVO, store1);
				
				if (storeVO.getOwnerTypeId() != null) {
					store1.setOwnerType(ownerTypeService.getOwnerTypeById(storeVO.getOwnerTypeId()));
				}
				
				if (storeVO.getStoreServiceTypeId() != null) {
					store1.setStoreServiceType(
							storeServiceTypeService.getStoreServiceTypeById(storeVO.getStoreServiceTypeId()));
				}
				if (storeVO.getStoreBusinessServiceTypeId() != null) {
					store1.setStoreBusinessServiceType(storeBusinessServiceTypeService
							.getStoreBusinessServiceTypeById(storeVO.getStoreBusinessServiceTypeId()));
				}
				
				store1 = storeService.updateStore(store1);
				
				List<StoreAllotedDetailsVO> storeAllotedDetailsVOs = new ArrayList<StoreAllotedDetailsVO>();
				if (storeVO.getStoreAllotedDetailInfo()!=null && !storeVO.getStoreAllotedDetailInfo().isEmpty()) {
					for (StoreAllotedDetailsVO storeAllotedDetailInfoVO : storeVO.getStoreAllotedDetailInfo()) {

						
						StoreAllotedDetails storeAllotedDetail = storeAllotedDetailsRepository.findByStoreAllotedStoreAllotedIdAndStoreStoreId(storeAllotedDetailInfoVO.getStoreAllotedId(), storeVO.getStoreId());
						
						if (storeAllotedDetail != null) {

						            storeAllotedDetail.setStore(store1);
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
									storeAllotedDetail.setStore(store1);
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
							
			}

		}*/
		
		userModel = userMapper.convertDomaintoModel(user);
		return new ResponseEntity<>(userModel, HttpStatus.OK);

	}

	@PutMapping(WebConstantUrl.CHANGE_PASSWORD)
	public ResponseEntity<?> changePassword(@RequestBody UserModel userModel, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		User user = iUserService.getById(userModel.getId());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));

		user = iUserService.update(user);

		userModel = userMapper.convertDomaintoModel(user);
		return new ResponseEntity<>(userModel, HttpStatus.OK);

	}

	@PutMapping(WebConstantUrl.FORGOT_PASSWORD)
	public ResponseEntity<?> forgotPassword(@RequestBody UserModel userModel, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Map<String, Object> map = new HashMap<>();
		User user = iUserService.findByUserName(userModel.getUsername());
		if (user == null) {
			map.put("msg", "userName not exists");

			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
		if (!user.getEmail().equals(userModel.getEmail())) {
			map.put("msg", "email doesnt match");

			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
		user.setPassword(passwordEncoder.encode("INIT@123"));

		if (mailStatus) {
			try {
				Mail mail = new Mail();
				mail.setMailTo(Arrays.asList(user.getEmail()));
				mail.setMailSubject("Forgot Password");
				mail.setMailContent("HI " + userModel.getUsername()
						+ " Your password updated password will be INIT@123  click for login https://nunxtwav.titan.in");
				mailService.sendMultiPartEmail(mail);
				user = iUserService.update(user);

			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}

		map.put("msg", "Please check your mail");
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@GetMapping(WebConstantUrl.getAll)
	public ResponseEntity<?> getAll(@RequestParam(required = false) String username,
			@RequestParam(required = false) String roleName,
			@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String firstName, @RequestParam(required = false) String emailId,
			@RequestParam(required = false) String mobileNo,
			@RequestParam(required = false) String departmentName,
			Pageable pageable, Principal principal,
			HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		
		//Pageable page = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();
		Long vendorId = null;
		List<Object[]> a =null;
		String stringStoreNames="";
		List<String> storeNames = null;
		List<Long> webStoreIds=null;
		
		

		User user = userRepo.findByUsername(principal.getName());
		
		String department=null;
		String user1=null;
		
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if( (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) && (!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD)) ) {
				user1 = user.getUsername();
			}else {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}
		
		/*
			if(!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
					
					) {
				// store name and username are same
				
				

					businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				//	storeNames = storeRepository.getStoreNamesByUsingUserId(user.getId());

				

			//	stringStoreNames = storeNames.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));

			//	businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				//roleName=STORE;
				//	storeNames = storeRepository.getStoreNamesByUsingUserId(user.getId());
	            //	stringStoreNames = storeNames.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));
				


			}
			if((user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin) ||
				(user.getRole().getRole().getRoleName().equalsIgnoreCase(ITAdmin))) )
			{
			//	businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
			//	webStoreIds =userRepo.getAllStores();
				
			}
		/*
		 * {
		 * 
		 * //roleName=STORE; businessVerticalTypeName =
		 * user.getRole().getWebMaster().getWebMasterName(); }
		 */
		
		List<UserModel> userModels=new ArrayList<>();

		  
		Page<User> users = iUserService.getAll(username, roleName, businessVerticalTypeName, firstName, emailId,
				mobileNo, stringStoreNames,webStoreIds,departmentName,department,page); 
		

		if (users.getContent().size() == 0) {
			map.put("userModels", userModels);
			map.put("total_pages", users.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", users.getTotalElements());
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		userModels = users.stream().map(userMapper::convertDomaintoModel).collect(Collectors.toList());
		map.put("userModels", userModels);
		map.put("total_pages", users.getTotalPages());
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", users.getTotalElements());

		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@GetMapping(WebConstantUrl.getAllUsersByWebRoleId)
	public ResponseEntity<?> getAllUsersByWebRoleId(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<UserModel> userMOdels = userRepo.findByRoleWebRoleId(id).stream().map(userMapper::convertDomaintoModel)
				.collect(Collectors.toList());
		return new ResponseEntity<>(userMOdels, HttpStatus.OK);

	}
	
	@GetMapping(WebConstantUrl.getAllUsersByWebRoleIdAndDepartmentId)
	public ResponseEntity<?> getAllUsersByWebRoleIdAndDepartmentId(@RequestParam(required = true) Long webRoleID,
			@RequestParam(required = true) Long departmentId, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		List<UserModel> userMOdels = userRepo.findByWebRoleIdAndDepartmentId(webRoleID,departmentId).stream().map(userMapper::convertDomaintoModel)
				.collect(Collectors.toList());
		return new ResponseEntity<>(userMOdels, HttpStatus.OK);

	}
	
	@GetMapping(WebConstantUrl.getAllUsersByDepartmentId)
	public ResponseEntity<?> getAllUsersByDepartmentId(@RequestParam(required = false) Long departmentId, HttpServletRequest request,Principal principal) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		User user=userRepo.findByUsername(principal.getName());
		
		
		/*
		 * List<UserModel> userMOdels =
		 * userRepo.getAllInventoryUsersByDepartmentId(user.getRole().getWebMaster().
		 * getWebMasterId(),departmentId).stream().map(userMapper::convertDomaintoModel)
		 * .collect(Collectors.toList());
		 */
		List<UserModel> userMOdels = userRepo.getAllInventoryUsersByVerticalId(user.getRole().getWebMaster().getWebMasterId()).stream().map(userMapper::convertDomaintoModel)
				.collect(Collectors.toList());
		return new ResponseEntity<>(userMOdels, HttpStatus.OK);

	}
	
	@GetMapping(WebConstantUrl.getAllUsersByLoginUserName)
	public ResponseEntity<?> getAllUsersByLoginUserName(Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		User user=userRepo.findByUsername(principal.getName());
		List<UserModel> userMOdels=null;
		
		//if(user.getInventoryUser().equalsIgnoreCase("true")) {
			userMOdels = userRepo.findByInventoryUser(user.getUsername()).stream().map(userMapper::convertDomaintoModel)
					.collect(Collectors.toList());
	//	}
		
		return new ResponseEntity<>(userMOdels, HttpStatus.OK);

	}

	@GetMapping(WebConstantUrl.getById)
	public ResponseEntity<?> getById(@PathVariable("id") Long id, HttpServletRequest request) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		User user = iUserService.getById(id);
		UserModel userModel = userMapper.convertDomaintoModel(user);

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase("STORE")) {
			userModel.setStoreId(storeRepository.findByStoreCode(user.getUsername()).getStoreId());
		}
		return new ResponseEntity<>(userModel, HttpStatus.OK);

	}

	@PostMapping(value = WebConstantUrl.SIGNIN)
	public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		try {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateJwtToken(authentication);
			User user = iUserService.findByUserName(authentication.getName());

			// sending badrequest for user is inactive
			if (!user.getAccountNonLocked()) {

				map.put("msg", "User status is inActive..! please contact administrator to make active");
				map.put("status", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

			}
			
			String departmentName=null;
			Long departmentId = null;
			if(user.getRoleWiseDepartments()!=null) {
				departmentName=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
				departmentId=user.getRoleWiseDepartments().getDepartment().getDepartmentId();
			}
			
			if(user.getRole().getRole().getRoleName().equalsIgnoreCase("STORE")) {
				
				List<Long> assetWbMasterIds=inventoryRepository.getAssetWebMasterIdBasedonUserName(user.getUsername());
				List<String> assetwebMasterName=new ArrayList<>();
				for(Long assetWbMasterId:assetWbMasterIds) {
					WebMaster webMaster=webMasterRepo.findByWebMasterId(assetWbMasterId);
					assetwebMasterName.add(webMaster.getWebMasterName());
				}
				
				return new ResponseEntity<>(new JwtResponse(jwt, user.getId(), user.getRole().getWebRoleId(),
						user.getUsername(), user.getRole().getRole().getRoleName(),assetWbMasterIds,assetwebMasterName), HttpStatus.OK);
			}

			else {
				return new ResponseEntity<>(new JwtResponse(jwt, user.getId(), user.getRole().getWebRoleId(),
						user.getUsername(), user.getRole().getRole().getRoleName(),departmentName,
						departmentId), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "Bad Creadiantials");
			map.put("status", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/exportExcel/{id}")
	public void exportToExcel(HttpServletResponse response, @PathVariable("id") Long id,Principal principal) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users.xlsx";
		response.setHeader(headerKey, headerValue);

		MasterHeaders masterHeaders = new MasterHeaders();
		// List<Object[]>users=iUserService.getAllForExcel();
		List<Object[]> users = null;
		WebMaster name = webMasterRepo.findByWebMasterId(id);
		User user=userRepo.findByUsername(principal.getName());
		if(!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
				users = iUserService.getAllForExcel(id);
		} else {
			users = iUserService.getAllForExcel();
		}
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.users, users);

		excelExporter.export(response);
	}

}
