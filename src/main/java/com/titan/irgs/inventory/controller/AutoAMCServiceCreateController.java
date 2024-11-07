package com.titan.irgs.inventory.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.domain.AutoAmcInventoryLog;
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.mapper.AutoAMCserviceMapper;
import com.titan.irgs.inventory.mapper.MaintainanceMapper;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.AutoAmcInventoryLogRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.service.AutoCreateAmcService;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.inventory.serviceImpl.AmcInventoryServiceImp;
import com.titan.irgs.inventory.serviceImpl.AmcWarrantyServiceImp;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.inventory.vo.AutoAmcServiceVO;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.serviceImpl.AssetService;
import com.titan.irgs.master.serviceImpl.EngineerService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping(value = WebConstantUrl.AutoAMCService)
public class AutoAMCServiceCreateController {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoAMCServiceCreateController.class);

	
	@Autowired
	MaintainanceService maintainanceService;
	/*
	 * @Autowired MaintainanceDetailService maintainanceDetailService;
	 */
	@Value("${mail.status}")
	private Boolean mailStatus;
	@Autowired
	MaintainanceEmailImpl emailServiceImpl;
	@Autowired
	MaintainanceEmailImpl1 emailServiceImpl1;
	@Autowired
	MaintainanceAlertEmailImpl maintainaceAlertImpl;
	
	@Autowired
	AutoAMCserviceMapper autoAMCserviceMapper;
	
	@Autowired
	AutoCreateAmcService autoCreateAmcService;
	
	@Autowired
	AutoAmcInventoryLogRepository autoAmcInventoryLogRepository1;

	@Autowired
	MaintainanceMapper maintainanceMapper;
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;
	@Autowired
	ClusterRepository clusterRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	AssetRepository assetRepository;
	@Autowired
	StoreRepository storeRepository;

	@Autowired
	UserRepo userRepo;
	@Autowired
	MaintainanceRepository maintainanceRepository;

	@Autowired
	AmcInventoryRepository amcInventoryRepository;

	@Autowired
	AmcInventoryServiceImp amcInventoryServiceImp;

	@Autowired
	AssetService assetService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	EngineerService engineerService;

	@Autowired
	StoreService storeService;

	@Autowired
	AmcWarrantyServiceImp amcWarrantyServiceImp;
	
	@Autowired
	AutoAmcInventoryLogRepository autoAmcInventoryLogRepository;

	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String Store = "STORE";
	private static final String Vendor = "VENDOR";
	private final static String superAdmin = "superadmin";
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";


	private final static String BREAKDOWN = "Breakdown";

	private final static String OPEN = "OPEN";

	private final static String CLOSE = "CLOSE";

	private static final String REOPEN = "REOPEN";

	
	@SuppressWarnings("static-access")
	@GetMapping(value = WebConstantUrl.GetAllMaintainanceAutoLog)
	@ResponseBody
	public ResponseEntity<?> getAllMaintainance(@RequestParam(required = false) String serviceRequestCode,
			@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String isMailSent, @RequestParam(required = false) String erno,
			@RequestParam(required = false) String serviceRequestDate,
			@RequestParam(required = false) String serviceRequestClosedDate,
			@RequestParam(required = false) String storeCode,@RequestParam(required = false) List<Long> region,
			@RequestParam(required = false) String serviceCreationStatus,@RequestParam(required = false) Long numberOfService,
			@RequestParam(required = false) String maintainanceStartDate, @RequestParam(required = false) String maintainanceEndDate,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate,
			Pageable pageable, Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();

		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		String  department=null;
		if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor) ) {

			if ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin) &&
			(!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD))) {

				storeCode = user.getUsername();
				
			}else if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			}

			else {

				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				/*
				 * storeIds = storeRepository.getStoreIdsByUsingUserId(user.getId());
				 * stringStoreIds =
				 * storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'",
				 * "'"));
				 */
			}


		}
		

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
			// store name and username are same
			vendorCode = user.getUsername();

		}
	
		List<AutoAmcServiceVO> maintainanceVOs = new ArrayList<>();
		Page<AutoAmcInventoryLog> maintainances = autoCreateAmcService.getAllAutoAmcInventoryLog(businessVerticalTypeName,serviceRequestCode,
				erno,vendorCode,storeCode, region,serviceRequestDate,maintainanceStartDate,maintainanceEndDate,serviceCreationStatus,numberOfService,isMailSent, fromDate,  toDate,department,page);

		if (maintainances.getContent().size() == 0) {
			map.put("maintainanceVOs", maintainanceVOs);
			map.put("total_pages", maintainances.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", maintainances.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);

		} else {
			maintainances.forEach(amcinventory -> {	
					//maintainanceVOs.add(autoAMCserviceMapper.getVoFromEntity(amcinventory));
				
				AutoAmcServiceVO amc= autoAMCserviceMapper.getVoFromEntity(amcinventory);
				
			//	Maintainance maintainance=maintainanceRepository.findByMaintainanceId(amcinventory.getMaintainance().getMaintainanceId());
				
				AmcInventory amcInventory= amcInventoryServiceImp.findById(amcinventory.getAmcInventory().getAmcId());
				
			//	Inventory inventory=inventoryService.getInventoryById(amcInventory.getInventory().getInventoryId());
				
				List<AmcWarranty> amcWarranty=amcWarrantyRepository.findWarrantyByAmcId(amcInventory.getAmcId());
				
			/*	for(int i=0;i<=amcWarranty.size()-1;i++) {
					if(amcWarranty.get(i).getTicketStatus().equalsIgnoreCase("OPEN") || amcWarranty.get(i).getTicketStatus().equalsIgnoreCase("CLOSE")) {
						amc.setMaintainanceStartDate(amcWarranty.get(i).getWarrantyFrom());
						amc.setMaintainanceEndDate(amcWarranty.get(i).getWarrantyTo());
					}
				}
				*/
				amc.setMaintainanceStartDate(amcInventory.getMaintainanceStartDate());
				amc.setMaintainanceEndDate(amcInventory.getMaintainanceEndDate());
		//		amc.setErno(inventory.getErNo());
				 List<Maintainance> maintainance=maintainanceRepository.findByAmcIds(amcinventory.getAmcInventory().getAmcId());
				 
				 for(int K=0;K<=maintainance.size()-1;K++) {
				// if(maintainance.get(K).getTicketStatus().equalsIgnoreCase("OPEN")) {
					 amc.setServiceRequestCode(maintainance.get(K).getServiceRequestCode());
					//	}
				 }
			//	amc.setSrCode(maintainance.getServiceRequestCode());
				amc.setNumberOfService(amcInventory.getNumberOfService());
				
			/*	for(int i=0;i<=amcWarranty.size()-1;i++) {
				amc.setMaintainanceStartDate(amcWarranty.get(i).getWarrantyFrom());
				amc.setMaintainanceEndDate(amcWarranty.get(i).getWarrantyTo());
				}
				amc.setNumberOfService(amcInventory.getNumberOfService());*/
			/*	
				List<AmcWarranty> amcWarrantyss = amcWarrantyRepository.findAll();
				LocalDate date = LocalDate.now();
				for (int i = 0; i <= amcWarrantyss.size() - 1; i++) {
					if ((amcWarrantyss.get(i).getWarrantyFrom().equals(date.now())
							|| amcWarrantyss.get(i).getWarrantyFrom() == date.now())
							&& (!amcWarrantyss.get(i).getTicketStatus().equalsIgnoreCase("OPEN") || !amcWarrantyss.get(i).getTicketStatus().equalsIgnoreCase("CLOSE" )))
					{
						
						amc.setMaintainanceStartDate(amcWarrantyss.get(i).getWarrantyFrom());
						amc.setMaintainanceEndDate(amcWarrantyss.get(i).getWarrantyTo());
						amc.setErno(inventory.getErNo());
						amc.setSrCode("");
						amc.setIsMailSent("");
						amc.setServiceCreationStatus("");
						amc.setCreatedOn(null);
						amc.setNumberOfService(amcInventory.getNumberOfService());
					}else if ((amcWarrantyss.get(i).getWarrantyFrom().equals(date.now())
							|| amcWarrantyss.get(i).getWarrantyFrom() == date.now())){
						amc.setMaintainanceStartDate(amcWarranty.get(i).getWarrantyFrom());
						amc.setMaintainanceEndDate(amcWarranty.get(i).getWarrantyTo());
					}
				}
			*/	
				maintainanceVOs.add(amc);
			});

			map.put("maintainanceVOs", maintainanceVOs);
			map.put("total_pages", maintainances.getTotalPages());
			map.put("status_code", HttpStatus.OK);
			map.put("total_records", maintainances.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@GetMapping(value = WebConstantUrl.GetAllMaintainance)
	@ResponseBody
	public ResponseEntity<?> getAllMaintainance(){
		List<AutoAmcInventoryLog> autoAmcInventoryLog= autoCreateAmcService.getAllmaintainance();
		return ResponseEntity.ok(autoAmcInventoryLog);
	}

	@GetMapping(WebConstantUrl.GetAllMaintainanceAutoLogWithPagination)
	@ResponseBody
	public ResponseEntity<?> getAllMaintainanceAutoLogWithPagination(Pageable pageable) {
		
		    Map<String, Object> map = new HashMap<>();
		    Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
		pageable.getPageSize());
		
       List<AutoAmcServiceVO> designationMasterModel = new ArrayList<AutoAmcServiceVO>(0);
		Page<AutoAmcInventoryLog> designationMaster = autoCreateAmcService.getAllMaintainanceAutoLogWithPagination(page);
		
		if (designationMaster.getContent().size() == 0) {
		 map.put("status_code", HttpStatus.NO_CONTENT);
		 map.put("total_records", designationMaster.getTotalElements());
	     map.put("total_pages", designationMaster.getTotalPages());
		 map.put("designationMasterModel", designationMasterModel);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
		 } else {
		 designationMaster.forEach(slas -> {
		 designationMasterModel.add(autoAMCserviceMapper.getVoFromEntity(slas));
		 });
		}
		  map.put("status_code", HttpStatus.OK);
		  map.put("total_records", designationMaster.getTotalElements());
		  map.put("total_pages", designationMaster.getTotalPages());
		  map.put("designationMasterModel", designationMasterModel);
		  return new ResponseEntity<>(map, HttpStatus.OK);
		}

	@GetMapping(value = WebConstantUrl.GET_ALL_AMC_SERVICE_REQUEST_AUTO_CREATED_Export)
	public ResponseEntity<?> exportToExcelAutoCreated(@RequestParam(required = true) Long userId,
			@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate,Pageable page) throws IOException {
	//	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

//		List<String> statusList = new ArrayList<String>();
		// String status = null;
		/*
		 * if(serviceCreationStatus) { statusList.add("OPEN"); statusList.add("REOPEN");
		 * } else if(serviceRequestType.equalsIgnoreCase("CLOSE")) {
		 * statusList.add("CLOSE"); } else
		 * if(serviceRequestType.equalsIgnoreCase("ALL")) { statusList.add("OPEN");
		 * statusList.add("CLOSE"); statusList.add("REOPEN"); }
		 */
		// status =
		// statusList.stream().map(String::valueOf).collect(Collectors.joining("','",
		// "'", "'"));
		String vendorId = null;
		List<Long> storeIds = null;
		String stringStoreIds = null;
		String businessVerticalTypeName = null;
		List<Long> regionids = null;
		String region=null;
		
		User user = userRepo.getByUserId(userId);

		String department=null;
		// setting bussiness verticle with respective login user
		if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor) ) {

			if ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin) &&
			(!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD))) {

				storeIds = userRepo.findByUserId(user.getId());
				stringStoreIds =storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));
				
			}else if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			}

			else {

				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				/*
				 * storeIds = storeRepository.getStoreIdsByUsingUserId(user.getId());
				 * stringStoreIds =
				 * storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'",
				 * "'"));
				 */
			}


		}
		/*
		 * if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin) &&
		 * !user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor) &&
		 * !user.getRole().getRole().getRoleName().equalsIgnoreCase(ITAdmin)) {
		 * 
		 * 
		 * if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Store)) {
		 * storeIds =
		 * Arrays.asList(storeRepository.findByStoreCode(user.getUsername()).getStoreId(
		 * )); } else { businessVerticalTypeName =
		 * user.getRole().getWebMaster().getWebMasterName(); storeIds =
		 * storeRepository.getStoreIdsByUsingUserId(user.getId()); } stringStoreIds =
		 * storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'",
		 * "'")); }
		 * 
		 */
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {

			vendorId = vendorRepository.findByVendorCode(user.getUsername()).getVendorCode();

		}
	//	String headerKey = "Content-Disposition";
	//	String headerValue = "attachment; filename=AutoAMCServiceRequestLog.xlsx";
	//	response.setHeader(headerKey, headerValue);

		List<AutoAmcInventoryLog> serviceRequestList = autoCreateAmcService.getAllForExportAutoAMCServiceRequest(fromDate, toDate, stringStoreIds, businessVerticalTypeName, vendorId, regionids,department);
		// ExcelExporter excelExporter = new
		// ExcelExporter(MasterHeaders.servicerequest,serviceRequestList);
	
	//	Excellxporter1 excelExporter = new Excellxporter1(MasterHeaders.AMCservicerequestReport, serviceRequestList);

	//	excelExporter.export(response);
		 try{

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				    Workbook wb = new Workbook(out, "MyApplication", "2.0");
				    Worksheet ws = wb.newWorksheet("Sheet 1");
				    ws.value(0, 0, "SRN Number");
				    ws.style(0, 0).bold().set();
				    ws.value(0, 1, "FAR Number");
				    ws.style(0, 1).bold().set();
				    ws.value(0, 2,  "SR Created");
				    ws.style(0, 2).bold().set();
			
				    ws.value(0, 3,  "Mail Sent");
				    ws.style(0, 3).bold().set();
				    ws.value(0, 4,  "Maintainance From");
				    ws.style(0, 4).bold().set();
				    ws.value(0, 5,  "Maintainance To");
				    ws.style(0, 5).bold().set();
			   	    ws.value(0, 6, "No Of service");
				    ws.style(0, 6).bold().set();  
				      
				    ws.value(0, 7, "Created Date");
				    ws.style(0, 7).bold().set();  
				    ws.value(0, 8, "Remarks");
				    ws.style(0, 8).bold().set();  
			//	    ws.value(0, 9, "Successfull");
			//	    ws.style(0, 9).bold().set();  
				   // ws.value(0, 10, "mobile no");
				   // ws.style(0, 10).bold().set();  
				    
//				    wb.finish();
				    int k=0;
				   for(AutoAmcInventoryLog autolog:serviceRequestList){
				      k=k+1;
				    	 for(int j=0;j<10;j++){
				    		  switch(j){
				    			 case 0:
				    				/* if(autolog.getServiceCreationStatus().equalsIgnoreCase("yes")) {
				    				 Maintainance maintainance=maintainanceRepository.findByAmcId(autolog.getAmcInventory().getAmcId());
				    				 ws.value(k, j, maintainance.getServiceRequestCode());
				    				 }
				    				 */
				    				 List<Maintainance> maintainance=maintainanceRepository.findByAmcIds(autolog.getAmcInventory().getAmcId());
				    				 
				    				 for(int K=0;K<=maintainance.size()-1;K++) {
				    				 if((autolog.getServiceCreationStatus().equalsIgnoreCase("yes"))) {
				    					 ws.value(k, j, maintainance.get(K).getServiceRequestCode());
				    						}
				    				 }
				    				 break;
				    			 case 1:
				    				 ws.value(k, j, autolog.getAmcInventory().getAsset().getFarNo());
				    				 break;
				    			 case 2:
				    				 
				    				 ws.value(k, j, autolog.getServiceCreationStatus());
				    				 
				    				 break;
					
				    				 
                              case 3:
				    				 
				    				 ws.value(k, j, autolog.getIsMailSent());
				    				 
				    				 break;
				    				 
                              case 4:
                            /*	  List<AmcWarranty> amcWarranty=amcWarrantyRepository.findWarrantyByAmcId(autolog.getAmcInventory().getAmcId());
                  				
                  				for(int i=0;i<=amcWarranty.size()-1;i++) {
                  					if(amcWarranty.get(i).getTicketStatus().equalsIgnoreCase("OPEN") || amcWarranty.get(i).getTicketStatus().equalsIgnoreCase("CLOSE")) {
                  						 ws.value(k, j, amcWarranty.get(i).getWarrantyFrom());
                                     	 ws.style(k, j).format("dd-MM-yyyy").set();
                  							}
                  				}
                             	*/
                            	  ws.value(k, j, autolog.getAmcInventory().getMaintainanceStartDate());
                              	 ws.style(k, j).format("dd-MM-yyyy").set();
                             	 break;
                             	 
                              case 5:
                            /*	  List<AmcWarranty> amcWarranty1=amcWarrantyRepository.findWarrantyByAmcId(autolog.getAmcInventory().getAmcId());
                    				
                    				for(int i=0;i<=amcWarranty1.size()-1;i++) {
                    					if(amcWarranty1.get(i).getTicketStatus().equalsIgnoreCase("OPEN") || amcWarranty1.get(i).getTicketStatus().equalsIgnoreCase("CLOSE")) {
                    						 ws.value(k, j, amcWarranty1.get(i).getWarrantyTo());
                                       	 ws.style(k, j).format("dd-MM-yyyy").set();
                    							}
                    				}
                    				*/
                             	 ws.value(k, j, autolog.getAmcInventory().getMaintainanceEndDate());
                             	 ws.style(k, j).format("dd-MM-yyyy").set();
                             	 break;
                              case 6:
                              	 
                              	 ws.value(k, j, autolog.getAmcInventory().getNumberOfService());
 	 
                              	 break;
                            
                                case 7:
                             	   ws.value(k, j, (Date)autolog.getCreatedOn());
					    				 ws.style(k, j).format("dd-MM-yyyy").set();
					    				 
                             	/*  String date= String.valueOf(complaint.getCreatedDate().getDate());
                             	  String month= String.valueOf(complaint.getCreatedDate().getMonth());
                             	  String year= String.valueOf(complaint.getCreatedDate().getYear());
                             	  String createdDate=year+"-"+month+"-"+date;
                             	   ws.value(k, j, createdDate);
                           		 */
                             	 break; 
                                case 8:
                                 	 
                                 	 ws.value(k, j, autolog.getRemarks());
    	 
                                 	 break;
                               
                               
                              			    			 default: 
				    				 ws.value(k, j, null);  
				    		 }
				    		
				    		 
				    	 }
				    }
		  
				    wb.finish();
				  
				 return ResponseEntity.ok()
						 .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AutoAMCServiceLog.xls").
						 body(out.toByteArray());
				
				  
		 }catch(Exception e){
		   e.printStackTrace();
		   logger.error(e.getLocalizedMessage());
		   return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);	    
		 }

	}

}
