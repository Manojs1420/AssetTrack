package com.titan.irgs.fileupload.processor;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.GroupBusinessVerticalMasterRepo;
import com.titan.irgs.master.repository.RegionDetailsRepository;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.serviceImpl.OwnerTypeService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.master.serviceImpl.StoreServiceTypeService;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.role.repository.RoleRepository;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;
import com.titan.irgs.role.serviceImpl.RoleService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@Component
public class UserProcessor extends UploadedDocProcessor {



	public static Logger logger = LoggerFactory.getLogger(StoreProcessor.class);

	@Autowired
	private OwnerTypeService ownerTypeService;

	@Autowired
	private WebMasterService webMasterService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreServiceTypeService storeServiceTypeService;

	@Autowired
	private WebMasterRepo webMasterRepo;

	@Autowired
	IUserService iUserService;

	@Autowired
	private WebRoleRepo webRoleRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private RegionDetailsRepository regionDetailsRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;


	@Autowired
	GroupBusinessVerticalMasterRepo groupBusinessVerticalMasterRepo;

	public UserProcessor() 
	{

	}

	public UserProcessor(String templateName) 
	{
		super(templateName);
	}

	@SuppressWarnings("unused")
	public List<String> importStoreRecords(InputStream inp) throws Exception 
	{

		List<String> errors = new ArrayList<>();
		Workbook wb;
		Sheet sheet;
		String errorInRow = null;

		String storeId = null;
		String storeName = null;
		String businessVerticalName = null;
		String departmentName=null;
		String storeCode = null;
		String costCentre = null;
		String ownerType = null;
		String emailId = null;
		String phoneNo = null;
		String  reportingToRole= null;
		String  reportingToUser= null;
		String firstName=null;
		String lastName=null;
		String userName=null;
		String password=null;
		String address=null;
		String storeType = null;
		String isGroupBusiness=null;
		String isInventoryUser=null;
		String description=null;
		User reportinguser =null;
		OwnerType ownerTypeObj = null;

		String userRole=null;
		User user = null;
		WebMaster webMaster = null;
		StoreServiceType storeServiceType = null;
		WebRole webRole = null;
		Role role = null;
		Department department=null;
		int rowCount=1;
		WebRole webRole1=null;
		WebMaster webMasterStore=null;
		wb = WorkbookFactory.create(inp);
		sheet = wb.getSheetAt(0);

		for (Row currRow : sheet) {
			if (currRow.getPhysicalNumberOfCells() == 0)
				return errors;
			errorInRow = null;

			if (currRow.getRowNum() != 0) {
				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				storeId = getValueBasedOnType(currRow.getCell(0));

			/*	if (storeId != null && storeId.isEmpty()) {
					storeId = null;
				}
				if (storeId == null) {
					errorInRow = "storeId can not be empty.Row " + currRow.getRowNum();
					errors.add(errorInRow);
					return errors;
				}
			*/

				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				businessVerticalName = getValueBasedOnType(currRow.getCell(0));

				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				departmentName = getValueBasedOnType(currRow.getCell(1));

				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				userRole = getValueBasedOnType(currRow.getCell(2));

				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				reportingToRole = getValueBasedOnType(currRow.getCell(3));

				currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				firstName = getValueBasedOnType(currRow.getCell(4));

				currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				lastName = getValueBasedOnType(currRow.getCell(5));

				currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				userName = getValueBasedOnType(currRow.getCell(6));

				currRow.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				password = getValueBasedOnType(currRow.getCell(7));


				currRow.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				emailId = getValueBasedOnType(currRow.getCell(8));

				currRow.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				phoneNo = getValueBasedOnType(currRow.getCell(9));

				currRow.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				address = getValueBasedOnType(currRow.getCell(10));

				currRow.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				storeType = getValueBasedOnType(currRow.getCell(11));

				currRow.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				ownerType = getValueBasedOnType(currRow.getCell(12));

				/*
				currRow.getCell(13, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				isGroupBusiness = getValueBasedOnType(currRow.getCell(13));

				currRow.getCell(14, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				isInventoryUser = getValueBasedOnType(currRow.getCell(14));
*/
				currRow.getCell(15, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				description = getValueBasedOnType(currRow.getCell(15));

				if(businessVerticalName == null) {
					errors.add("businessVerticalName Column cannot be empty at line "+rowCount);

				}

				if(departmentName == null) {
					errors.add("departmentName Column cannot be empty at line "+rowCount);

				}

				if(reportingToRole == null) {
					errors.add("reportingToRole Column cannot be empty at line "+rowCount);

				}

				if(userRole == null) {
					errors.add("user role Column cannot be empty at line "+rowCount);

				}

				if(firstName == null) {
					errors.add("firstName Column cannot be empty at line "+rowCount);

				}
				if(userName == null) {
					errors.add("userName Column cannot be empty at line "+rowCount);

				}

				if(password == null) {
					errors.add("password Column cannot be empty at line "+rowCount);

				}

				if(emailId == null) {
					errors.add("emailId Column cannot be empty at line "+rowCount);

				}

				if(storeType == null) {
					errors.add("user Service Type Column cannot be empty at line "+rowCount);

				}

				if(ownerType == null) {
					errors.add("ownerType Column cannot be empty at line "+rowCount);

				}
/*
				if(isGroupBusiness == null) {
					errors.add("isGroupBusiness Column cannot be empty at line "+rowCount);

				}

				if(isInventoryUser == null) {
					errors.add("isInventoryUser Column cannot be empty at line "+rowCount);

				}
*/

				
				role = roleService.findByRoleNameIgnoreCase(userRole);
				if(role == null) {

					errors.add("role name "+userRole+" not available in Role Master");
					break;

				}

				if( role != null){

					 webMasterStore = webMasterRepo.findByWebMasterName(businessVerticalName);

					if(webMasterStore != null) {
						webRole = webRoleRepo.findByRoleRoleIdAndWebMasterWebMasterId(role.getRoleId(),webMasterStore.getWebMasterId());
					}
					if(webRole == null) {

						errors.add("Web Role for the User not available in WebRole Master");
						break;

					}

				}

				department=departmentRepo.findByWebMasterWebMasterIdAndDepartmentName(webMasterStore.getWebMasterId(),departmentName);

				if(department == null) {

					errors.add("department "+departmentName+" not available in department Master");
					break;

				}


				if(!ownerType.equals("") ) {
					ownerTypeObj = ownerTypeService.findByOwnerTypeName(ownerType);



					if(ownerTypeObj == null) {
						errors.add("OwnerType " +ownerType + "is not available in the master dB  ");
						break;

					}
				}  

				if(!storeType.equals("")) {
					storeServiceType = storeServiceTypeService.findByStoreServiceTypeName(storeType);
				}


				if( storeServiceType == null) {
					errors.add("user service Type "+ storeType+" is not available in the master dB  ");
					break;
				}



				if(!businessVerticalName.equals("")) {

					webMaster = webMasterRepo.findByWebMasterName(businessVerticalName);
				}

				if( webMaster == null) {
					errors.add("BusinessVerticalName " +businessVerticalName+" is not available in the master dB  ");
					break;
				}


				/*
				if(!reportingToUser.equals("")) {

					 reportinguser = userRepo.findByUsername(reportingToUser);
				}


				if(reportinguser == null) {

					errors.add("Reporting to User "+ reportingToUser+"  is not available in the User dB  ");
					break;

				}*/

				if(reportingToRole!=null) {
					Role role1=roleRepository.findByRoleName(reportingToRole);
					webRole1=webRoleRepo.findByRoleRoleIdAndWebMasterWebMasterId(role1.getRoleId(),webMaster.getWebMasterId());
				}

			

			User user1=userRepo.findByUsername(userName);

			if(user1==null) {

				user=new User();

				if(department!=null) {
					RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(webRole.getWebRoleId(),department.getDepartmentId());
					user.setRoleWiseDepartments(roleWiseDepartments);

				}

				user.setPassword(passwordEncoder.encode(password));
				/*	
				if(isGroupBusiness.equalsIgnoreCase("true") || isGroupBusiness=="true")
				{
					user.setGroupBusinessVerticalMaster(groupBusinessVerticalMasterRepo.findById(userModel.getGroupBusinessMasterId()).orElseThrow(()-> new ResourceNotFoundException("The group business Id not found")));
				}
				 */
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setUsername(userName);
				user.setEmail(emailId);
				user.setPhoneNo(phoneNo);
				user.setAddress(address);
				user.setDiscription(description);

				user.setAccountNonExpired(true);
				user.setCreatedBy(1l);

				/*
				if(isGroupBusiness.equalsIgnoreCase("false")){
					user.setIsgroupBusiness("false");
				}else {
					user.setIsgroupBusiness(isGroupBusiness);
				}

				if(isInventoryUser.equalsIgnoreCase("true")) {
					user.setInventoryUser("true");
				}else {
					user.setInventoryUser(isInventoryUser);
				}*/
				
				user.setAccountNonLocked(true);
				user.setOwnerType(ownerTypeObj);
				user.setStoreServiceType(storeServiceType);
				user.setRole(webRole);

				user = iUserService.save(user);
				/*
				if(isInventoryUser!=null && isInventoryUser.equalsIgnoreCase("true")) {
					RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.getInventoryUserwebroleusingverticalAndDepartment(webMaster.getWebMasterId(),department.getDepartmentId());
					user.setRoleWiseDepartments(roleWiseDepartments);
					user.setRole(roleWiseDepartments.getWebRole());
					user = iUserService.save(user);
				}else {
					user = iUserService.save(user);
				}

				
				if(user.getInventoryUser()!=null && user.getInventoryUser().equalsIgnoreCase("true")) {
					StoreVO storeVO=new StoreVO();

					storeVO.setOwnerTypeId(ownerTypeObj.getOwnerTypeId());
					storeVO.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
					storeVO.setReportingToId(reportinguser.getId());
					storeVO.setWebMasterId(webMaster.getWebMasterId());
					RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(webRole1.getWebRoleId(), department.getDepartmentId());
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


					store=storeService.saveStore(store,storeVO.getReportingToId());
				}*/

			}else {
				
				if(department!=null) {
					RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(webRole.getWebRoleId(),department.getDepartmentId());
					user1.setRoleWiseDepartments(roleWiseDepartments);

				}

				user1.setPassword(passwordEncoder.encode(password));
				/*	
				if(isGroupBusiness.equalsIgnoreCase("true") || isGroupBusiness=="true")
				{
					user.setGroupBusinessVerticalMaster(groupBusinessVerticalMasterRepo.findById(userModel.getGroupBusinessMasterId()).orElseThrow(()-> new ResourceNotFoundException("The group business Id not found")));
				}
				 */
				user1.setFirstName(firstName);
				user1.setLastName(lastName);
				user1.setUsername(userName);
				user1.setEmail(emailId);
				user1.setPhoneNo(phoneNo);
				user1.setAddress(address);
				user1.setDiscription(description);

				user1.setAccountNonExpired(true);
				user1.setCreatedBy(1l);
				/*
				if(isGroupBusiness.equalsIgnoreCase("false")){
					user1.setIsgroupBusiness("false");
				}else {
					user1.setIsgroupBusiness(isGroupBusiness);
				}

				
				if(isInventoryUser.equalsIgnoreCase("true")) {
					user1.setInventoryUser("true");
				}else {
					user1.setInventoryUser(isInventoryUser);
				}*/

				user1.setAccountNonLocked(true);

				user1.setRole(webRole);
				user1.setOwnerType(ownerTypeObj);
				user1.setStoreServiceType(storeServiceType);
				user1 = iUserService.update(user1);
				
				/*
				if(isInventoryUser!=null && isInventoryUser.equalsIgnoreCase("true")) {
					RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.getInventoryUserwebroleusingverticalAndDepartment(webMaster.getWebMasterId(),department.getDepartmentId());
					user1.setRoleWiseDepartments(roleWiseDepartments);
					user1.setRole(roleWiseDepartments.getWebRole());
					user1 = iUserService.update(user1);
				}else {
					user1 = iUserService.update(user1);
				}

				if(user1.getInventoryUser()!=null && user1.getInventoryUser().equalsIgnoreCase("true")) {
					StoreVO storeVO=new StoreVO();
					storeVO.setOwnerTypeId(ownerTypeObj.getOwnerTypeId());
					storeVO.setStoreServiceTypeId(storeServiceType.getStoreServiceTypeId());
					storeVO.setReportingToId(reportinguser.getId());
					storeVO.setWebMasterId(webMaster.getWebMasterId());
					RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(webRole1.getWebRoleId(), department.getDepartmentId());
					storeVO.setRoleWiseDepartmentId(roleWiseDepartments.getRoleWiseDepartmentsId());

					storeVO.setStoreCode(user1.getUsername());
					storeVO.setStoreName(user1.getFirstName());
					
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
					
						
						store = storeService.saveStore(store,store.getReportingToId());
						
					
						
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
						
						store1 = storeService.updateStore(store1);
									
					}

				}
				 */

			}

			}

			rowCount++;
		}
		return errors; 

	}

}
