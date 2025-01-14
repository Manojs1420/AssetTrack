package com.titan.irgs.serviceRequest.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.ImagePath;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.serviceImpl.AmcInventoryServiceImp;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.inventory.serviceImpl.MaintainanceServiceImpl;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.EngineerRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.vo.DashboardVO;
import com.titan.irgs.master.vo.ServiceRequestCountForDashboardVO;
import com.titan.irgs.serviceRequest.domain.SRCommentsUpdate;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.domain.ServiceRequestCountDto;
import com.titan.irgs.serviceRequest.domain.ServiceRequestDeatil;
import com.titan.irgs.serviceRequest.mapper.ServiceRequestMapper;
import com.titan.irgs.serviceRequest.model.SRCommentUploadVo;
import com.titan.irgs.serviceRequest.model.ServiceRequestDetailsVO;
import com.titan.irgs.serviceRequest.model.ServiceRequestVO;
import com.titan.irgs.serviceRequest.repository.SRCommentsUpdateRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestUploadRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestDetailService;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Service.SrNotificationEmailService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@RestController
@Configuration
@RequestMapping(value = WebConstantUrl.SERVICE_REQUEST)
public class ServiceRequestController {

	@Value("${mail.status}")
	private Boolean mailStatus;
	

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ServiceRequestMapper serviceRequestMapper;

	@Autowired
	IServiceRequestService iServiceRequestService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	WebRoleRepo webRoleRepo;

	@Autowired
	IWebMaster iWebMaster;

	@Autowired
	WebMasterRepo webMasterRepo;

	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	@Autowired
	EmailServiceImpl1 emailServiceImpl1;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	AssetRepository assetRepository;

	@Autowired
	EngineerRepository engineerRepository;

	@Autowired
	ClusterRepository clusterRepository;

	@Autowired
	IServiceRequestDetailService iServiceRequestDetailService;
	
	@Autowired
	SRCommentsUpdateRepository srCommentsUpdateRepository;
	
	@Autowired
	ImagePath imagePath;
	
	@Autowired
	SrNotificationEmailService srNotificationEmailService;
	
	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	
	@Autowired
	MaintainanceServiceImpl maintainanceServiceImpl;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	AmcInventoryServiceImp amcInventoryServiceImp;
	
	@Autowired
	ServiceRequestUploadRepository serviceRequestUploadRepository;
	
	@Autowired
	MaintainanceRepository maintainanceRepository;
	
	@Autowired
	EmailServiceImplimentation mailService;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	AmcInventoryRepository amcInventoryRepository;

	private final static String superAdmin = "superadmin";

	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";


	private final static String ITAdmin="ITAdmin";

	private final static String STORE = "STORE";

	private final static String VENDOR = "Vendor";

	private final static String OPEN = "OPEN";

	private final static String CLOSE = "CLOSE";

	private static final String REOPEN = "REOPEN";
	private static final String MANAGEMENT = "MANAGEMENT";
	
	@PostMapping(WebConstantUrl.save)
	public ResponseEntity<?> save(@RequestBody ServiceRequestVO serviceRequestVO, HttpServletRequest request,
			Principal principal) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Map<String, Object> map = new HashMap<String, Object>();

		User user = userRepo.findByUsername(principal.getName());
	//	List<String> emailcc = new ArrayList<String>();
	//	List<User> usersEmails = new ArrayList<User>();
	//	String mailSubject;

		// CHECKING IF ERNUMBER IS EXIST AND IT SHOULD BE CLOSE..
		if (serviceRequestVO.getErNumber() != null) {
			boolean checkIfErnumber = iServiceRequestService
					.checkIfErnumberAndStatusClosed(serviceRequestVO.getErNumber());

			if (checkIfErnumber) {
				map.put("msg", "The attached ER number for service request is open");
				map.put("status_code", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
			}

		}

		ServiceRequest serviceRequest = serviceRequestMapper.convertModeltoDomain(serviceRequestVO);
		// checking asset code belong to respective bussiness verticle
		if (serviceRequest.getAssetCode() != null) {
			serviceRequest
					.setAssetWebMaster(assetRepository.findByAssetCode(serviceRequest.getAssetCode()).getWebMaster());
		}
		

		serviceRequest.setTicketStatus(OPEN);
		serviceRequest.setRunningStatus("SR created");
		serviceRequest.setCreatedOn(new Date());
		serviceRequest.setServiceRequestDate(new Date());
		serviceRequest = iServiceRequestService.save(serviceRequest);
		ServiceRequestDeatil serviceRequestDeatil = new ServiceRequestDeatil();

		serviceRequestDeatil.setServiceRequest(serviceRequest);
		serviceRequestDeatil.setUser(user);
		serviceRequestDeatil.setCommants(serviceRequestVO.getComments());
		serviceRequestDeatil.setWebRole(user.getRole());

		serviceRequestDeatil = iServiceRequestDetailService.save(serviceRequestDeatil);
		// Sr-Notification email Trigger start--------------->
		
		
		Mail mail = iServiceRequestService.templeteMail(serviceRequest);
		Long id= serviceRequest.getAssetWebMaster().getWebMasterId();
		String mailSubjects = "Rim Asset  "+serviceRequestRepository.getwebmasterName(id)+" " + serviceRequest.getServiceRequestCode()+"– Notification Creation";

		Asset asset=assetRepository.findByAssetId(serviceRequest.getAssetId());
		
		List<String> usersemails=userRepo.getEmailForAssetBasedDepartmentHeads(asset.getAssetId()); // CC mail for respective asset department head will get
		if(!usersemails.isEmpty()) {
			System.out.println(usersemails);
			mail.setMailCC(usersemails); 
		}

		String emailTOValue=serviceRequest.getUser().getEmail(); //Service request created user Id
		if(emailTOValue!=null)
		{
			List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
			System.out.println(emailTo);
			mail.setMailTo(emailTo);
		}
		
		mail.setMailSubject(mailSubjects);
		if (mailStatus) { 
			try {

				try {
					emailServiceImpl1.sendMultiPartEmail(mail);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*List<Object[]> serviceRequestList1 =serviceRequestRepository.getValidServiceNotification(serviceRequest.getServiceRequestId());
for (Object[] rows : serviceRequestList1) {
		
	ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
			String mailSubjects = "";			
			String emailCcValue=(String) rows[4];

			Mail mail = iServiceRequestService.templeteMail(serviceRequests);
			
			if(emailCcValue!=null) {
				List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
				System.out.println(emailCc);
				mail.setMailCC(emailCc); 
			}

			String emailTOValue=(String) rows[3];
			if(emailTOValue!=null)
			{
				List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
				System.out.println(emailTo);
				mail.setMailTo(emailTo);
			}

		  Long id= serviceRequests.getAssetWebMaster().getWebMasterId();
		  mailSubjects = "Rim Asset  "+serviceRequestRepository.getwebmasterName(id)+" " + serviceRequest.getServiceRequestCode()+"– Notification Creation";
		
		  mail.setMailSubject(mailSubjects);
		  if (mailStatus) { try {
			  emailServiceImpl1.sendMultiPartEmail(mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} } 

		}*/
			
			System.out.println("the task is completed");

// Sr-Notification Email TriggerEnd    <-------------------------
		


		serviceRequestVO = serviceRequestMapper.convertDomaintoModel(serviceRequest);
		serviceRequestVO.setComments(serviceRequestDeatil.getCommants());
		logger.debug("converting entity to vo ==> " + serviceRequestVO);
		
		return new ResponseEntity<>(serviceRequestVO, HttpStatus.OK);

	}

	@GetMapping(WebConstantUrl.getAll)
	public ResponseEntity<?> getAll(@RequestParam(required = false) String srNumber,
			@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String assetCode, @RequestParam(required = false) String erNumber,
			@RequestParam(required = false) String vendorName, @RequestParam(required = false) String breakDownType,
			@RequestParam(required = false) String urgency, @RequestParam(required = false) String serviceRequestType,
			@RequestParam(required = false) String ticketStatus, @RequestParam(required = false) String runningStatus,
			@RequestParam(required = false) int page, @RequestParam(required = false) int size,
			@RequestParam(required = false) String serviceRequestDate,
			@RequestParam(required = false) String serviceRequestClosedDate,
			@RequestParam(required = false) String vendorCode,
			@RequestParam(required = false) String VerticalName,
			@RequestParam(required = false) String serviceDocumentUploaded,
			 Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Map<String, Object> map = new HashMap<>();
		Pageable pages = PageRequest.of(page,size==0 ? Integer.MAX_VALUE : size);
		
		Long vendorId = null;
		List<Long> storeIds = null;
		String stringStoreIds = "";
		List<ServiceRequestVO> serviceRequestVOs = new ArrayList<>();
		User user = userRepo.findByUsername(principal.getName());
		
		String department=null;
		// setting bussiness verticle with respective login user
		if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR) ) {

			if(user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
			}
			else if ((!user.getRole().getRole().getRoleName().contains(MANAGER))) {

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
		
		
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {

			vendorId = vendorRepository.findVendoIDbyCode(user.getUsername());

		}

		// to filter data using procedure.
		List<ServiceRequest> serviceRequests = iServiceRequestService.getAll(srNumber, businessVerticalTypeName,
				stringStoreIds, assetCode, erNumber, vendorName, breakDownType, urgency, vendorId, serviceRequestType,
				ticketStatus, runningStatus, serviceRequestDate, serviceRequestClosedDate,vendorCode,serviceDocumentUploaded,department,pages);
		// sorting on date
		// serviceRequests =
		// serviceRequests.stream().sorted(Comparator.comparing(ServiceRequest::getServiceRequestDate).reversed()).collect(Collectors.toList());
		// getting number of records.
		Long count = iServiceRequestService.count(srNumber, businessVerticalTypeName, stringStoreIds, assetCode,
				erNumber, vendorName, breakDownType, vendorId, urgency, serviceRequestType, ticketStatus, runningStatus,
				serviceRequestDate, serviceRequestClosedDate,department);

		if (serviceRequests.size() == 0) {

			map.put("serviceRequestVOs", serviceRequestVOs);
			map.put("total_pages", 0);
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", count);
			new ResponseEntity<>(map, HttpStatus.OK);

		}
		else {
			serviceRequestVOs = serviceRequests.stream().map(serviceRequestMapper::convertDomaintoModel)
					.collect(Collectors.toList());
			
			/*
			serviceRequests.forEach(serviceRequest -> {
				
		serviceRequestVOs = serviceRequests.stream().map(serviceRequestMapper::convertDomaintoModel)
				.collect(Collectors.toList());
				ServiceRequestVO serviceRequestVO=serviceRequestMapper.convertDomaintoModel(serviceRequest);
				ServiceRequestUpload serviceRequestUpload=serviceRequestUploadRepository.getByServiceRequestId(serviceRequest.getServiceRequestId());
				
			if(serviceRequestUpload!=null) {
				serviceRequestVO.setServiceDocumentUploaded(true);
			}else
			{
				serviceRequestVO.setServiceDocumentUploaded(false);
			}
			serviceRequestVOs.add(serviceRequestVO);
			});*/
		}
		// calculating page

	//	int page = (int) Math.ceil((double) count / (double) pageable.getPageSize());

		map.put("serviceRequestVOs", serviceRequestVOs);
		map.put("total_pages", pages);
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", count);
		return new ResponseEntity<>(map, HttpStatus.OK);
	
	}

	@GetMapping(WebConstantUrl.getById)
	public ResponseEntity<?> getById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		ServiceRequest serviceRequest = iServiceRequestService.getById(id);

		ServiceRequestVO serviceRequestVO = serviceRequestMapper.convertDomaintoModel(serviceRequest);

		return new ResponseEntity<>(serviceRequestVO, HttpStatus.OK);

	}
	
	@PostMapping(WebConstantUrl.getClosedServiceRequests)
	public ResponseEntity<?> getClosedServiceRequests(@RequestBody ServiceRequestVO serviceRequestVo,HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		List<ServiceRequest> serviceRequests = iServiceRequestService.getClosedServiceRequests(serviceRequestVo.getServiceRequestCode());

		List<ServiceRequestVO> serviceRequestVOs = new ArrayList<>();

		for(ServiceRequest serviceRequest:serviceRequests)	{
			ServiceRequestVO serviceRequestVO = new ServiceRequestVO();

			serviceRequestVO.setServiceRequestCode(serviceRequest.getServiceRequestCode());
			serviceRequestVO.setServiceRequestId(serviceRequest.getServiceRequestId());
			serviceRequestVO.setInventoryId(serviceRequest.getInventory().getInventoryId());

			serviceRequestVOs.add(serviceRequestVO);
		}

		return new ResponseEntity<>(serviceRequestVOs, HttpStatus.OK);

	}
	

	@SuppressWarnings("unlikely-arg-type")
	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody ServiceRequestVO serviceRequestVO, HttpServletRequest request,
			Principal principal) throws MessagingException, IOException {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
	//	iServiceRequestService.batchEscalation(serviceRequestVO.getCountServiceRequestdays());

		boolean vendorAssign = false;
		boolean ticketStatus = false;
		boolean poStatus = false;
		boolean engineerStatus = false;
		boolean isAdviceForPayment = false;
		User user = userRepo.findByUsername(principal.getName());
		new ArrayList<>();

		// mail part...........
		// login user is REGENGINEER or store user for mail
		ServiceRequest serviceRequestForMail = iServiceRequestService.getById(serviceRequestVO.getServiceRequestId());

		// checking service request already closed...

		if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)
				|| user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

			User storeUser = userRepo.findByUsername(serviceRequestForMail.getUser().getUsername());
			// String
			// region=clusterRepository.getClustersByUsingUserId(storeUser.getId()).get(0).getRegion().getRegionName();
		//	String region = storeRepository.findByStoreCodeIgnoreCase(storeUser.getUsername()).getRegion().getRegionName();

			// checking asset belong to irsg..if so need to assign IRSG bussinessVertical
			
				storeUser.getRole().getWebMaster().getWebMasterName();

			// fetching hirarical wise role users using busssinessVerticalName and region
		//	usersEmails = userRepo.findByUsersUsingBussinessVerticalAndRegion(busssinessVerticalName, region);

			// login user Department head below conditions applied
			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {
				// vendor assign
				// vendor assign for initially
				if (serviceRequestVO.getServiceVendorId() != null
						&& serviceRequestForMail.getServiceVendorId() == null) {
					vendorAssign = true;
					serviceRequestVO.setRunningStatus("vendor assign");

					List<Object[]> serviceRequestList1 =serviceRequestRepository.AssigningVendor(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList1) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						  Mail mail = iServiceRequestService.templeteMail(serviceRequests);
								String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
					            mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								if(emailTOValue!=null) {
								
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								 mail.setMailTo(emailTO);
								}
								
								  serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects = " VENDOR ASSIGN ";
								 
								 
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (vendorAssign == true) {
										  emailServiceImpl1.sendMultiPartEmail(mail); }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } 
					}
								
									
									System.out.println("the task is completed");

						// Sr-Notification Email TriggerEnd    <-------------------------

					
					/*
					 * // adding emailTO
					 * emailTO.add(vendorRepository.getOne(serviceRequestVO.getServiceVendorId()).
					 * getServiceEmailId1()); // adding eamilCC
					 * emailCC.addAll(Arrays.asList(serviceRequestForMail.getStore().getEmailId(),
					 * user.getEmail()));
					 */

				}
				// vendor changed
				else if (serviceRequestVO.getServiceVendorId() != null
						&& serviceRequestVO.getServiceVendorId() != serviceRequestForMail.getServiceVendorId()) {

 					vendorAssign = true;
					serviceRequestVO.setRunningStatus("vendor assign");
					List<Object[]> serviceRequestList1 =serviceRequestRepository.AssigningVendor(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList1) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						 Mail mail = iServiceRequestService.templeteMail(serviceRequests);
								String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
					            mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								
								if(emailTOValue!=null) {
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								  mail.setMailTo(emailTO);
								
								}
								
								 
								  serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects = " VENDOR ASSIGN ";								
								 
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (vendorAssign == true) {
										  emailServiceImpl1.sendMultiPartEmail(mail); }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } }

								
									
									System.out.println("the task is completed");

						//Vendor Assign Email TriggerEnd    <-------------------------

					/*
					 * // adding emailTO
					 * emailTO.add(vendorRepository.getOne(serviceRequestVO.getServiceVendorId()).
					 * getServiceEmailId1()); // adding eamilCC
					 * emailCC.addAll(Arrays.asList(serviceRequestForMail.getStore().getEmailId(),
					 * user.getEmail()));
					 */
				}
				// adviceForPayment
				if (serviceRequestVO.isAdviceForPayment() != null && serviceRequestVO.isAdviceForPayment()) {
					isAdviceForPayment = true;
					serviceRequestVO.setRunningStatus("AdviceForPayment");
					List<Object[]> serviceRequestList2 =serviceRequestRepository.adviceForPayment(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList2) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						 Mail mail = iServiceRequestService.templeteMail(serviceRequests);
								String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
					            mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								if(emailTOValue!=null) {
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								mail.setMailTo(emailTO);
								}
								 
								  mailSubjects = "ADVICE FOR PAYMENT";
								  
								
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (isAdviceForPayment) {
										  emailServiceImpl1.sendMultiPartEmail(mail);
										 
										 }								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } 

								
									
									System.out.println("Advice for Payment is completed");
					/*
					 * // adding emailTO emailTO.addAll(usersEmails.stream() .filter(f ->
					 * f.getRole().getRole().getRoleName().contains(regionalCommercialOfficer))
					 * .map(email -> email.getEmail()).collect(Collectors.toList())); // adding
					 * eamilCC emailCC.addAll(usersEmails.stream() .filter(f ->
					 * f.getRole().getRole().getRoleName().contains(HOD) ||
					 * f.getRole().getRole().getRoleName().contains(REGENGINEER) ||
					 * f.getRole().getRole().getRoleName().contains(REGIONHEAD)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList()));
					 */				}

			}
				}

			if (serviceRequestVO.getTicketStatus() != null
					&& serviceRequestVO.getTicketStatus().equalsIgnoreCase(CLOSE))

			{

				ticketStatus = true;
				serviceRequestVO.setServiceRequestClosedDate(new Date());
				serviceRequestVO.setRunningStatus(CLOSE);
				// if login user is Department Head if ticketStatus
				serviceRequestVO.setClosedBy(user.getId());
				if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {
			
					serviceRequestForMail.setServiceRequestClosedDate(new Date());
					serviceRequestForMail.setRunningStatus(CLOSE);
					serviceRequestForMail.setTicketStatus(CLOSE);
					serviceRequestForMail.setClosedBy(user.getId());;
					serviceRequestForMail.setClousureDescription(serviceRequestVO.getClousureDescription());
					serviceRequestForMail.setClousureProblem(serviceRequestVO.getClousureProblem());
					serviceRequestForMail.setServiceEngineerCommants(serviceRequestForMail.getServiceEngineerCommants());
			
					Mail mail = iServiceRequestService.templeteMail(serviceRequestForMail);
					Long id= serviceRequestForMail.getAssetWebMaster().getWebMasterId();
					String mailSubjects = "Rim Asset  "+serviceRequestRepository.getwebmasterName(id)+" " + serviceRequestForMail.getServiceRequestCode()+"– Service Completion";

					Asset asset=assetRepository.findByAssetId(serviceRequestForMail.getAssetId());
					
					List<String> usersemails=userRepo.getEmailForAssetBasedDepartmentHeads(asset.getAssetId()); // CC mail for respective asset department head will get
					if(!usersemails.isEmpty()) {
						System.out.println(usersemails);
						mail.setMailCC(usersemails); 
					}

					String emailTOValue=serviceRequestForMail.getUser().getEmail(); //Service request created user Id
					if(emailTOValue!=null)
					{
						List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
						System.out.println(emailTo);
						mail.setMailTo(emailTo);
					}
					
					mail.setMailSubject(mailSubjects);
					if (mailStatus) { 
						try {

							try {
								emailServiceImpl1.sendMultiPartEmail(mail);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					/*
					List<Object[]> serviceRequestList2 =serviceRequestRepository.ServiceclosedbyDepartmentHead(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList2) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						serviceRequests.setServiceRequestClosedDate(new Date());
						serviceRequests.setRunningStatus(CLOSE);
						serviceRequests.setTicketStatus(CLOSE);
						serviceRequests.setClosedBy(user.getId());;
						serviceRequests.setClousureDescription(serviceRequestVO.getClousureDescription());
						serviceRequests.setClousureProblem(serviceRequestVO.getClousureProblem());
						serviceRequests.setServiceEngineerCommants(serviceRequestForMail.getServiceEngineerCommants());
						Mail mail = iServiceRequestService.templeteMail(serviceRequests);
					
						String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
					            mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								
								if(emailTOValue!=null) {
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								 mail.setMailTo(emailTO);
								}
								  Long id= serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects =serviceRequestRepository.getwebmasterName(id)+" " + serviceRequestVO.getServiceRequestCode()+"-Service Completion";
								 
								  
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (ticketStatus == true) {
											 emailServiceImpl.sendMultiPartEmail(mail);										
									  }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } }
*/
								
									
									System.out.println("closed Service is completed");

					
					/*
					 * // adding emailTO emailTO.add(serviceRequestForMail.getStore().getEmailId());
					 * // adding eamilCC emailCC.addAll(usersEmails.stream() .filter(f ->
					 * f.getRole().getRole().getRoleName().contains(HOD) ||
					 * f.getRole().getRole().getRoleName().contains(areaBusinessManager) ||
					 * f.getRole().getRole().getRoleName().contains(REGENGINEER) ||
					 * f.getRole().getRole().getRoleName().contains(REGIONHEAD) ||
					 * f.getRole().getRole().getRoleName().contains(CENTRALHEAD)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList())); if
					 * (serviceRequestVO.getServiceVendorId() != null)
					 * emailCC.add(vendorRepository.getOne(serviceRequestForMail.getServiceVendorId(
					 * )) .getServiceEmailId1());
					 */

				}
				// if login user is STORE if ticketStatus
				if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {
					
					List<Object[]> serviceRequestList2 =serviceRequestRepository.Servicecomplete(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList2) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						serviceRequests.setServiceRequestClosedDate(new Date());
						serviceRequests.setRunningStatus(CLOSE);
						serviceRequests.setClosedBy(user.getId());;
						serviceRequests.setClousureDescription(serviceRequestVO.getClousureDescription());
						serviceRequests.setClousureProblem(serviceRequestVO.getClousureProblem());
						serviceRequests.setServiceEngineerCommants(serviceRequestForMail.getServiceEngineerCommants());
						 Mail mail = iServiceRequestService.templeteMail(serviceRequests);

						String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
					            mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								
								if(emailTOValue!=null) {
									List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
									System.out.println(emailTO);
									 mail.setMailTo(emailTO);
								}
								
								  Long id= serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects =serviceRequestRepository.getwebmasterName(id)+" " + serviceRequestVO.getServiceRequestCode()+"-Service Completion";
								 
								
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (ticketStatus == true) {
											 emailServiceImpl.sendMultiPartEmail(mail);
									
									  }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } }

								
									
									System.out.println("Closed Service is completed");
// adding emailTO
					/*
					 * emailTO.addAll( usersEmails.stream().filter(f ->
					 * f.getRole().getRole().getRoleName().contains(REGENGINEER)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList()));
					 * 
					 * emailCC.addAll(usersEmails.stream() .filter(f ->
					 * f.getRole().getRole().getRoleName().contains(HOD) ||
					 * f.getRole().getRole().getRoleName().contains(areaBusinessManager) ||
					 * f.getRole().getRole().getRoleName().contains(REGIONHEAD) ||
					 * f.getRole().getRole().getRoleName().contains(CENTRALHEAD)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList()));
					 * emailCC.add(user.getEmail());
					 */
				}
			}

			if (serviceRequestVO.getTicketStatus() != null
					&& serviceRequestVO.getTicketStatus().equalsIgnoreCase(REOPEN))

			{

				if (!serviceRequestForMail.getTicketStatus().equalsIgnoreCase(REOPEN)) {
					ticketStatus = true;
					serviceRequestVO.setRunningStatus("Reopen service");
					List<Object[]> serviceRequestList2 =serviceRequestRepository.ReopenService(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList2) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						 Mail mail = iServiceRequestService.templeteMail(serviceRequests);
								String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
					            mail.setMailCC(emailCC);
								}
								
								String emailTOValue=(String) rows[3];
								
								if(emailTOValue!=null) {
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								mail.setMailTo(emailTO);
								}
								  Long id= serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects =serviceRequestRepository.getwebmasterName(id)+" " + serviceRequestVO.getServiceRequestCode()+"-Service Completion";
								  
								   
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (ticketStatus == true) {
											
											
											 emailServiceImpl.sendMultiPartEmail(mail);
									 
										
									  }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } }

								
									
									System.out.println("Reopen Service is completed");


					
					
					
					/*
					 * // adding emailTO emailTO.addAll(usersEmails.stream() .filter(f ->
					 * f.getRole().getRole().getRoleName().contains(HOD) ||
					 * f.getRole().getRole().getRoleName().contains(REGENGINEER) ||
					 * f.getRole().getRole().getRoleName().contains(REGIONHEAD) ||
					 * f.getRole().getRole().getRoleName().contains(CENTRALHEAD)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList()));
					 */
				}

			}

		}
		

		// vendor login to check
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)
				|| user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {

			if (serviceRequestVO.getServiceEngineerName() != null) {
/*				if (!(serviceRequestForMail.getServiceEngineerName())
						.equalsIgnoreCase(serviceRequestVO.getServiceEngineerName())) {
*/
					engineerStatus = true;
					serviceRequestVO.setRunningStatus("Assign Engineer");
					List<Object[]> serviceRequestList2 =serviceRequestRepository.AssigningEngineer(serviceRequestForMail.getServiceRequestId());
					for (Object[] rows : serviceRequestList2) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						  Mail mail = iServiceRequestService.templeteMail(serviceRequests);
								String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {
								List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					            System.out.println(emailCC);
								 mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								
								if(emailTOValue!=null) {
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								mail.setMailTo(emailTO);
								}
								  serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects = "engineer assigned";
								  
								 
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
										  if (engineerStatus) { 
										  emailServiceImpl1.sendMultiPartEmail(mail); }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } }

								
									
									System.out.println("Assigning Engineer is completed");


					/*
					 * // adding emailTO emailTO.addAll(Arrays.asList(engineerRepository
					 * .findByEngineerName(serviceRequestVO.getServiceEngineerName()).getEmailId(),
					 * user.getEmail())); // adding eamilCC emailCC.addAll(usersEmails.stream()
					 * .filter(f -> f.getRole().getRole().getRoleName().contains(REGENGINEER) ||
					 * f.getRole().getRole().getRoleName().contains(REGIONHEAD)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList()));
					 * emailCC.add(serviceRequestForMail.getStore().getEmailId());
					 */
	//			}

			}

		}

		// for po status mail...
		if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {
			// need to check two condition
			// 1. from vo postatus should not be null
			// 2. from vo and entity postatus not be same
			if (serviceRequestVO.getPoStatus() != null) {
				if (!serviceRequestForMail.getPoStatus().equalsIgnoreCase(serviceRequestVO.getPoStatus())) {

					poStatus = true;
					serviceRequestVO.setRunningStatus("PO Approval");
					List<Object[]> serviceRequestList2 =serviceRequestRepository.POStatus(serviceRequestVO.getServiceRequestId());
					for (Object[] rows : serviceRequestList2) {
						ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
						 Mail mail = iServiceRequestService.templeteMail(serviceRequests);
								String mailSubjects = "";			
								String emailCcValue=(String) rows[4];
								
								if(emailCcValue!=null) {

									List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
						            System.out.println(emailCC);
						            mail.setMailCC(emailCC); 
								}
								
								String emailTOValue=(String) rows[3];
								
								if(emailTOValue!=null) {
								List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
								System.out.println(emailTO);
								mail.setMailTo(emailTO);
								}
								 
								  serviceRequests.getAssetWebMaster().getWebMasterId();
								  mailSubjects = "PO Approval";
								  
								
								  mail.setMailSubject(mailSubjects);
								  if (mailStatus) { try {
									  if (poStatus) {
										  emailServiceImpl1.sendMultiPartEmail(mail); }
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} } }

								
									
									System.out.println("PO Status is completed");



					/*
					 * // adding emailTO emailTO.addAll( usersEmails.stream().filter(f ->
					 * f.getRole().getRole().getRoleName().contains(REGIONHEAD)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList())); // adding eamilCC
					 * emailCC.addAll( usersEmails.stream().filter(f ->
					 * f.getRole().getRole().getRoleName().contains(REGENGINEER)) .map(email ->
					 * email.getEmail()).collect(Collectors.toList())); emailCC.add(
					 * vendorRepository.getOne(serviceRequestForMail.getServiceVendorId()).
					 * getServiceEmailId1());
					 */
				}

			}
		}

		ServiceRequest serviceRequest = serviceRequestMapper.convertModeltoDomain(serviceRequestVO);
		serviceRequest.setUpdatedBy(user.getId());
		serviceRequest.setUpdatedOn(new Date());
		serviceRequest.setAssetWebMaster(serviceRequestForMail.getAssetWebMaster());
		serviceRequest.setServiceRequestClosedDate(serviceRequestVO.getServiceRequestClosedDate());
		serviceRequest = iServiceRequestService.update(serviceRequest);
		


		for (ServiceRequestDetailsVO serviceRequestDetailsVO : serviceRequestVO.getServiceRequestDetailsVO()) {
			ServiceRequestDeatil serviceRequestDeatil = iServiceRequestDetailService
					.getbyid(serviceRequestDetailsVO.getServiceRequestDetailId());

			// serviceRequestDeatil.setServiceRequest(serviceRequest);
			// serviceRequestDeatil.setUser(user);
			serviceRequestDeatil.setCommants(serviceRequestVO.getComments());
			// serviceRequestDeatil.setWebRole(user.getRole());
			serviceRequestDeatil = iServiceRequestDetailService.update(serviceRequestDeatil);

			serviceRequestVO = serviceRequestMapper.convertDomaintoModel(serviceRequest);
			serviceRequestVO.setComments(serviceRequestDeatil.getCommants());
		}

		/*
		 * if (mailStatus) {
		 * 
		 * try { Mail mail = iServiceRequestService.templeteMail(serviceRequest);
		 * 
		 * mail.setMailTo(emailTO); mail.setMailCC(emailCC);
		 * 
		 * if (vendorAssign == true) { mail.setMailSubject("VENDOR ASSIGN");
		 * emailServiceImpl.sendMultiPartEmail(mail); } if (ticketStatus == true) {
		 * mail.setMailSubject(IRSG + " – " + serviceRequest.getServiceRequestCode() +
		 * "-Service Completion");
		 * 
		 * emailServiceImpl.sendMultiPartEmail(mail);
		 * 
		 * } if (isAdviceForPayment) { mail.setMailSubject("ADVICE FOR PAYMENT");
		 * emailServiceImpl.sendMultiPartEmail(mail);
		 * 
		 * } if (poStatus) { mail.setMailSubject("PO Approval");
		 * emailServiceImpl.sendMultiPartEmail(mail); }
		 * 
		 * if (engineerStatus) { mail.setMailSubject("engineer assigned");
		 * emailServiceImpl.sendMultiPartEmail(mail); }
		 * 
		 * } catch (Exception e) { System.out.println(e.getLocalizedMessage()); return
		 * new ResponseEntity<>(serviceRequestVO, HttpStatus.OK); } }
		 */
		return new ResponseEntity<>(serviceRequestVO, HttpStatus.OK);
	}
	
	
	///Service Request Comment Updates
	
	@PutMapping(WebConstantUrl.UPDATE_COMMENTS_FOR_SERVICE_REQUEST)
	public ResponseEntity<?> updateCommentsForServiceRequest(@RequestBody ServiceRequestVO serviceRequestVo,Principal principal) 
	{
		 User user=userRepo.findByUsername(principal.getName());
		 
		 if (serviceRequestVo == null) 
			{
				
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
				SRCommentsUpdate srCommentsUpdate = null;
				
				try {
					
					if(serviceRequestVo!=null) {
						
						srCommentsUpdate = new SRCommentsUpdate();
						srCommentsUpdate.setCreatedOn(serviceRequestVo.getUpdatedOn());
						srCommentsUpdate.setServiceRequestId(serviceRequestVo.getServiceRequestId());
						srCommentsUpdate.setComment(serviceRequestVo.getComments());
						srCommentsUpdate.setUserId(user.getId());
						srCommentsUpdate.setUserName(user.getUsername());
					
						srCommentsUpdateRepository.save(srCommentsUpdate);
							
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			return new ResponseEntity<>(serviceRequestVo, HttpStatus.OK);
	
	}
	
	
	@PostMapping(WebConstantUrl.UPDATE_SR_COMMENTS_FILE_UPLOAD)
	public ResponseEntity<?> updatePhotoForServiceRequest(@RequestPart(required = true) @RequestParam("file") MultipartFile multipartFile,	@RequestParam("sr-file-upload-info") String json,Principal principal)  {
		
		ObjectMapper mapper = new ObjectMapper();
		ServiceRequestVO serviceRequestVO = new ServiceRequestVO();
		
		try 
		  {
			serviceRequestVO = mapper.readValue(json,ServiceRequestVO.class);
		  
		  }
		  catch (Exception e)
		  { 
			  System.out.println(e.getLocalizedMessage()); 
			  
		  }
		User user=userRepo.findByUsername(principal.getName());
		
		String filePath = this.imagePath.saveImageOnDesk(multipartFile, "comments");
		String[] fileName=filePath.split("/");
			
			if(filePath.length()>0) 
			{
				
				SRCommentsUpdate srCommentsUpdate = new SRCommentsUpdate();
				srCommentsUpdate.setUserId(user.getId());
				srCommentsUpdate.setUserName(user.getUsername());
				srCommentsUpdate.setCreatedOn(new Date());
				srCommentsUpdate.setServiceRequestId(serviceRequestVO.getServiceRequestId());
				srCommentsUpdate.setFileType(multipartFile.getContentType());
				srCommentsUpdate.setFileUploadLocation(filePath);
				srCommentsUpdate.setUploadedFileName(fileName[0]);

				srCommentsUpdate = srCommentsUpdateRepository.save(srCommentsUpdate);
			}
		 return new ResponseEntity<>(serviceRequestVO, HttpStatus.OK);
	}
	
	
	@GetMapping(WebConstantUrl.GET_SR_COMMENTS_SERVICE_REQUEST_ID)
	public ResponseEntity<List<SRCommentUploadVo>> getCommentsServiceRequestId(@PathVariable(value = "serviceRequestId") Long serviceRequestId,Principal principal) {
		
	   List<SRCommentUploadVo> sRCommentUploadVos = new ArrayList<SRCommentUploadVo>();
		
		List<SRCommentsUpdate> srCommentsUpdates = srCommentsUpdateRepository.findByServiceRequestId(serviceRequestId);
		
		try {
			
			if (serviceRequestId == null) {
				return new ResponseEntity<List<SRCommentUploadVo>>(HttpStatus.NOT_FOUND);
			}
			
			if (srCommentsUpdates == null || srCommentsUpdates.isEmpty()) {
				
				return new ResponseEntity<List<SRCommentUploadVo>>(HttpStatus.NO_CONTENT);
			}
			
			srCommentsUpdates.forEach(srCommentsUpdate -> {
				SRCommentUploadVo srCommentUploadVo = new SRCommentUploadVo();
				srCommentUploadVo.setSrCommentUploadId(srCommentsUpdate.getSrCommentUploadId());
				srCommentUploadVo.setServiceRequestId(serviceRequestId);
				srCommentUploadVo.setCreatedOn(srCommentsUpdate.getCreatedOn());
				srCommentUploadVo.setComment(srCommentsUpdate.getComment());
				srCommentUploadVo.setUserId(srCommentsUpdate.getUserId());
				srCommentUploadVo.setUserName(srCommentsUpdate.getUserName());
				srCommentUploadVo.setFileType(srCommentsUpdate.getFileType());
				srCommentUploadVo.setFileUploadLocation(srCommentsUpdate.getFileUploadLocation());
				srCommentUploadVo.setUploadedFileName(srCommentsUpdate.getUploadedFileName());
				
				
				
				sRCommentUploadVos.add(srCommentUploadVo);
			});
			return new ResponseEntity<List<SRCommentUploadVo>>(sRCommentUploadVos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<SRCommentUploadVo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(WebConstantUrl.GET_SERVICE_REQUEST_COMMENTS_UPLOAD)
	public HttpEntity<byte[]> getSRCommentsUploadFiles(@PathVariable String fileName) {

		HttpHeaders headers = null;

		if (fileName == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {

			byte[] fileResourceAsByte = imagePath.getFilesResourceOnFileName(fileName, "comments");

			try {

				if (fileResourceAsByte == null) {

					return new ResponseEntity<>(HttpStatus.NO_CONTENT);

				} else {
					
					if (fileName.toLowerCase().contains(".pdf".toLowerCase())) {
						headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);

					}

					if (fileName.toLowerCase().contains(".txt".toLowerCase())) {
						
						headers = new HttpHeaders();
						headers.setContentType(MediaType.TEXT_PLAIN);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);
					}

					if (fileName.toLowerCase().contains(".jpg".toLowerCase())) {
						headers = new HttpHeaders();
						headers.setContentType(MediaType.IMAGE_JPEG);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);
					}

					if (fileName.toLowerCase().contains(".PNG".toLowerCase())) {
						
						headers = new HttpHeaders();
						headers.setContentType(MediaType.IMAGE_PNG);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);
					}

					if (fileName.toLowerCase().contains(".xlsx".toLowerCase())) {
						
						headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);

					}

					if (fileName.toLowerCase().contains(".xls".toLowerCase())) {
						
						headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);
					}

					if (fileName.toLowerCase().contains(".doc".toLowerCase())) {
						
						headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);

					}

					if (fileName.toLowerCase().contains(".docx".toLowerCase())) {
						
						headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
						headers.setContentDispositionFormData("attachment", "" + fileName);
						headers.setContentLength(fileResourceAsByte.length);

					}
				}
				return new HttpEntity<byte[]>(fileResourceAsByte, headers);
				
			} catch (Exception e) {
				
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_SERVICE_REQUEST_ON_START_DATE_AND_END_DATE_AND_REQUEST_TYPE)
    public void exportToExcel(HttpServletResponse response, @RequestParam(required = true) Long userId, @RequestParam(required = true) String fromDate,@RequestParam(required = true) String toDate, @RequestParam(required = true) String breakDownType, @RequestParam(required = true) String serviceRequestType) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        List<String> statusList = new ArrayList<String>();
        String status = null;
        if(serviceRequestType.equalsIgnoreCase("OPEN")) {
        	statusList.add("OPEN");
        	statusList.add("REOPEN");
        } else if(serviceRequestType.equalsIgnoreCase("CLOSE")) {
        	statusList.add("CLOSE");
        } else if(serviceRequestType.equalsIgnoreCase("ALL")) {
        	statusList.add("OPEN");
        	statusList.add("CLOSE");
        	statusList.add("REOPEN");
        }
        
        status = statusList.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));
        
        Long vendorId = null;
		List<Long> storeIds = null;
		String stringStoreIds = null;
		String businessVerticalTypeName = null;

		User user = userRepo.getByUserId(userId);

		String department=null;

		if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR) ) {

			if(user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
			}
			else if (!user.getRole().getRole().getRoleName().contains(MANAGER)) {

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

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {

			vendorId = vendorRepository.findByVendorCode(user.getUsername()).getVendorId();

		}
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ServiceRequestReport.xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<Object[]> serviceRequestList=iServiceRequestService.getAllForExportServiceRequest(fromDate,toDate, status, breakDownType, stringStoreIds, businessVerticalTypeName, vendorId,department);
        for (Object[] objects : serviceRequestList) {
			
        	 Long diffIndays=null;
     	     
     		 if(objects[13].equals(OPEN)) 
     		 {
     			 diffIndays = ChronoUnit.DAYS.between(
     					((Date) objects[9]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), 
     	    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+1;
     		 }
     		 else if((Date) objects[10]!=null && objects[13].equals(CLOSE)) 
     		 {

     			 diffIndays = ChronoUnit.DAYS.between(
     					((Date) objects[9]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), 
     					((Date) objects[10]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+1;
     		 
     		 }
     		 
     		objects[11] = diffIndays;
     	   }
        
    //    ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.servicerequest,serviceRequestList);
        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.servicerequestReport,serviceRequestList);
        
        excelExporter.export(response);    
    }
	
	@PostMapping(value = WebConstantUrl.GET_SERVICE_REQUEST_COUNT_FOR_DASHBOARD)
	@ResponseBody
	public ResponseEntity<?> getServiceRequestCountForDashboard(
			@RequestBody(required = false) DashboardVO dashboardVO,Principal principal)
	{

		new HashMap<>();

		String businessVerticalType=null;

		User user=userRepo.findByUsername(principal.getName());
		
		String department=null;
		String user1=null;
		String vendorCode=null;
		
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if(user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
			}
			else if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if(!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD) ) {
				user1 = user.getUsername();
			}else {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}

		if (user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			businessVerticalType = dashboardVO.getWebMasterName();

		}
		
		if(user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)){			
			user1=null;
			vendorCode=user.getUsername();

		}


		/*
		if(user.getRoleWiseDepartments()!=null){
			departmentName = user.getRoleWiseDepartments().getDepartment().getDepartmentName();
		}
		 */
		List<Object[]> sLists = iServiceRequestService.getServiceRequestCountForDashboard(businessVerticalType,user1,department,vendorCode);
		if (sLists == null || sLists.isEmpty()) {
			return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

		}
		
		ServiceRequestCountForDashboardVO serviceRequestCountForDashboardVO =new ServiceRequestCountForDashboardVO();
		
		List<Long> totalServices=new ArrayList<>();
		List<Long> openCounts=new ArrayList<>();
		List<Long> closedCounts=new ArrayList<>();
		List<String> webmasternames=new ArrayList<>();
		List<String> departmentNames=new ArrayList<>();
		for (Object[] tuple : sLists) {
			Long totalService=0L;
			if(tuple[0]!=null) {
				Integer totalasset=(Integer)tuple[0];
				totalService=totalasset.longValue();
			}else {
				totalService=0L;
			}
			
			Long openService=0L;
			if(tuple[1]!=null) {
				Integer totalasset=(Integer)tuple[1];
				openService=totalasset.longValue();
			}else {
				openService=0L;
			}
			
			Long closedService=0L;
			if(tuple[2]!=null) {
				Integer totalasset=(Integer)tuple[2];
				closedService=totalasset.longValue();
			}else {
				closedService=0L;
			}

			String webmastername="";

			if(tuple[4]!=null) {
				webmastername=(String)tuple[4];
			}

			String departmentName="";

			if(tuple[6]!=null) {
				departmentName=(String)tuple[6];
			}
			
			totalServices.add(totalService);
			openCounts.add(openService);
			closedCounts.add(closedService);
			webmasternames.add(webmastername);
			departmentNames.add(departmentName);
			
		}
		serviceRequestCountForDashboardVO.setVertical(webmasternames);
		serviceRequestCountForDashboardVO.setDepartmentName(departmentNames);
		serviceRequestCountForDashboardVO.setTotalServices(totalServices);
		serviceRequestCountForDashboardVO.setOpenCount(openCounts);
		serviceRequestCountForDashboardVO.setCloseCount(closedCounts);
		
		return new ResponseEntity<>(serviceRequestCountForDashboardVO, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_SERVICE_REQUEST_COUNT)
	@ResponseBody
	public ResponseEntity<List<ServiceRequestCountDto>> getAllServiceRequestCount(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(ITAdmin)
				) {
			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {

				// region=user.getRole().getRole().getRoleId();

				region = clusterRepository.getClusterRegionIdByUsingUserId(user.getId());

			} else if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(ITAdmin)
				
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
			// store name and username are same
			vendorCode = user.getUsername();
		vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
		//	vendorId=user.getId();;
		}

		
		List<ServiceRequestCountDto> serviceRequestDtos = new ArrayList<ServiceRequestCountDto>();
		List<Object[]> objArrList=null;
		List<Object[]> objArrList1 =null;
		List<Object[]> objArrList3=null;
		List<Object[]> objArrList4=null;
		if(region!=null || storeCode!=null) {
			objArrList = iServiceRequestService.getAllSrCount(businessVerticalTypeName,storeCode,vendorId,region);
			objArrList1 = maintainanceServiceImpl.getAllAmcSrCount(businessVerticalTypeName,storeCode,vendorId,region);
			objArrList3 = inventoryService.getTotalInventory(businessVerticalTypeName,storeCode,vendorId,region);
			objArrList4 = amcInventoryServiceImp.getTotalAmcInventory(businessVerticalTypeName,storeCode,vendorId,region);
				
		}
		else if(vendorId!=null){
			objArrList = serviceRequestRepository.getAllSrCountings(businessVerticalTypeName,storeCode,vendorId,region);
			objArrList1=maintainanceRepository.getAllAmcSrCount(businessVerticalTypeName,storeCode,vendorId,region);
			objArrList3=inventoryRepository.getAllInventory(businessVerticalTypeName, storeCode, region);
			objArrList4=amcInventoryRepository.getTotalAmcInventory(businessVerticalTypeName, storeCode, vendorId, region);
		
		}else if(businessVerticalTypeName!=null){
			
			 objArrList = serviceRequestRepository.getAllSrCount(businessVerticalTypeName);
			 objArrList1 = maintainanceRepository.getAllAmcSrCount(businessVerticalTypeName);
			 objArrList3=inventoryRepository.getAllInventory(businessVerticalTypeName);
			 objArrList4=amcInventoryRepository.getTotalAmcInventory(businessVerticalTypeName);
		}else {
			objArrList = serviceRequestRepository.getAllSrCount();
			 objArrList1 = maintainanceRepository.getAllAmcSrCount();
			 objArrList3=inventoryRepository.getAllInventory();
			 objArrList4=amcInventoryRepository.getTotalAmcInventory();
		}
		
		List<Object[]> objArrList2 = iServiceRequestService.getTotalSrAndAmcSrCount(businessVerticalTypeName,storeCode,vendorId,region);
		
		ServiceRequestCountDto srDto = new ServiceRequestCountDto();
		try {
			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}


			if (objArrList1 == null || objArrList1.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}

			if (objArrList2 == null || objArrList2.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}
			if (objArrList3 == null || objArrList3.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}
			if (objArrList4 == null || objArrList4.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}
			//for All Sr count
       for(Object[] tuple:objArrList) {
				
				
				
				Long openCount = 0L;
				if(tuple[0]!=null) {
					Integer biopenCount = (Integer) tuple[0];
					openCount = biopenCount.longValue();
					
				}else {
					openCount = 0L;
				}
				
				Long closeCount = 0L;
				if(tuple[1]!=null) {
					Integer bicloseCount = (Integer) tuple[1];
					closeCount = bicloseCount.longValue();
					
				}else {
					
					closeCount = 0L;
				}
				
				Long serviceRequestCount = 0L;
				if(tuple[2]!=null) {
					Integer biserviceRequestCount = (Integer) tuple[2];
					serviceRequestCount = biserviceRequestCount.longValue();
					
				}else {
					serviceRequestCount = 0L;
				}
				
			
				srDto.setOpenSrCount(openCount);
				srDto.setCloseSrCount(closeCount);
				srDto.setServiceRequestCount(serviceRequestCount);
			
				
			  //  serviceRequestDtos.add(srDto);
				
			}
       
     //for All AmcSr count
       for(Object[] tuple:objArrList1) {
			
			
			
			Long openCount = 0L;
			if(tuple[0]!=null) {
				Integer biopenCount = (Integer) tuple[0];
				openCount = biopenCount.longValue();
				
			}else {
				openCount = 0L;
			}
			
			Long closeCount = 0L;
			if(tuple[1]!=null) {
				Integer bicloseCount = (Integer) tuple[1];
				closeCount = bicloseCount.longValue();
				
			}else {
				
				closeCount = 0L;
			}
			
			Long serviceRequestCount = 0L;
			if(tuple[2]!=null) {
				Integer biserviceRequestCount = (Integer) tuple[2];
				serviceRequestCount = biserviceRequestCount.longValue();
				
			}else {
				serviceRequestCount = 0L;
			}
			
			
			srDto.setOpenAmcSrCount(openCount);
			srDto.setCloseAmcSrCount(closeCount);
			srDto.setAmcSrCount(serviceRequestCount);
		
			
		}
       
     //for All count
       for(Object[] tuple:objArrList2) {
			
			Long serviceRequestCount = 0L;
			if(tuple[2]!=null) {
				Integer biserviceRequestCount = (Integer) tuple[2];
				serviceRequestCount = biserviceRequestCount.longValue();
				
			}else {
				serviceRequestCount = 0L;
			}
			
			srDto.setTotalCount(serviceRequestCount);
		
			
		//    serviceRequestDtos.add(srDto);
			
		}
       //for total Inventories
       for(Object[] tuple:objArrList3) {
			
			Long serviceRequestCount = 0L;
			if(tuple[0]!=null) {
				Integer biserviceRequestCount = (Integer) tuple[0];
				serviceRequestCount = biserviceRequestCount.longValue();
				
			}else {
				serviceRequestCount = 0L;
			}
			
			srDto.setTotalInventories(serviceRequestCount);
	
			
		}
     //for total AmcInventories
       for(Object[] tuple:objArrList4) {
			
			Long serviceRequestCount = 0L;
			if(tuple[0]!=null) {
				Integer biserviceRequestCount = (Integer) tuple[0];
				serviceRequestCount = biserviceRequestCount.longValue();
				
			}else {
				serviceRequestCount = 0L;
			}
			
			srDto.setTotalAmcInventories(serviceRequestCount);
		
			
		//    serviceRequestDtos.add(srDto);
			
		}
       serviceRequestDtos.add(srDto);
       
			return new ResponseEntity<List<ServiceRequestCountDto>>(serviceRequestDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_SERVICE_REQUEST_COUNT_FOR_VENDOR)
	@ResponseBody
	public ResponseEntity<List<ServiceRequestCountDto>> getServiceRequestCountForAllVendors(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {

				// region=user.getRole().getRole().getRoleId();

				region = clusterRepository.getClusterRegionIdByUsingUserId(user.getId());

			} else if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
			// store name and username are same
			vendorCode = user.getUsername();
			vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
			//vendorId=user.getId();
		}
		
		List<ServiceRequestCountDto> serviceRequestDtos = new ArrayList<ServiceRequestCountDto>();
		List<Object[]> objArrList=null;
		if(region!=null || storeCode!=null) {
			objArrList = iServiceRequestService.getSrCountForAllVendors(businessVerticalTypeName,storeCode,vendorId,region);
			
		}
		else if(vendorId!=null){
			objArrList = serviceRequestRepository.getSrCountForAllVendors(businessVerticalTypeName,storeCode,vendorId,region);
			
		}else if(businessVerticalTypeName!=null){
			
			 objArrList = serviceRequestRepository.getSrCountForAllVendors(businessVerticalTypeName);
			
		}
		else {
			
			 objArrList = serviceRequestRepository.getSrCountForAllVendors();
			
		}
     
		List<Long> ids = new ArrayList<Long>(); 
		
		try {
			
			

			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}

			
			for(Object[] tuple:objArrList) {
				
				
				
				Long openCount = 0L;
				if(tuple[0]!=null) {
					Integer biopenCount = (Integer) tuple[0];
					openCount = biopenCount.longValue();
					
				}else {
					openCount = 0L;
				}
				
				Long closeCount = 0L;
				if(tuple[1]!=null) {
					Integer bicloseCount = (Integer) tuple[1];
					closeCount = bicloseCount.longValue();
					
				}else {
					
					 closeCount = 0L;
				}
				
				Long serviceRequestCount = 0L;
				if(tuple[2]!=null) {
					Integer biserviceRequestCount = (Integer) tuple[2];
					serviceRequestCount = biserviceRequestCount.longValue();
					
				}else {
					
					serviceRequestCount = 0L;
				}
				
				
				
				BigInteger bivendorId = (BigInteger) tuple[3];
				Long vendorId1 = bivendorId.longValue();
				
				ids.add(vendorId1);
				
				String vendorName = "";
				if(tuple[4]!=null) {
					vendorName = (String) tuple[4];
				}
				
				ServiceRequestCountDto srDto = new ServiceRequestCountDto();
				srDto.setOpenSrCount(openCount);
				srDto.setCloseSrCount(closeCount);
				srDto.setServiceRequestCount(serviceRequestCount);
				srDto.setVendorId(vendorId1);
				srDto.setVendorName(vendorName);
								
			    serviceRequestDtos.add(srDto);
				
			}

			return new ResponseEntity<List<ServiceRequestCountDto>>(serviceRequestDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = WebConstantUrl.GET_ALL_SERVICE_REQUEST_COUNT_FOR_REGION)
	@ResponseBody
	public ResponseEntity<List<ServiceRequestCountDto>> getAllSrCountForAllRegion(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {

				// region=user.getRole().getRole().getRoleId();

				region = clusterRepository.getClusterRegionIdByUsingUserId(user.getId());

			} else if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
			// store name and username are same
			vendorCode = user.getUsername();
			vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
			//vendorId=user.getId();
		}
		
		List<ServiceRequestCountDto> serviceRequestDtos = new ArrayList<ServiceRequestCountDto>();
		
     	
     	List<Object[]> objArrList=null;
		if(storeCode!=null) {
			objArrList = iServiceRequestService.getAllSrCountForAllRegion(businessVerticalTypeName,storeCode,vendorId,region);
			
		}
		else if(region!=null || vendorId!=null){
			objArrList = serviceRequestRepository.getAllSrCountForAllRegion(businessVerticalTypeName,storeCode,vendorId,region);
			
		}else if(businessVerticalTypeName!=null){
			
			 objArrList = serviceRequestRepository.getAllSrCountForAllRegion(businessVerticalTypeName);
			
		}else {
			
			 objArrList = serviceRequestRepository.getAllSrCountForAllRegion();
			
		}
		
		List<Long> ids = new ArrayList<Long>(); 
		
		try {
			
			

			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}

			
			for(Object[] tuple:objArrList) {
				
				
				
				Long openCount = 0L;
				if(tuple[0]!=null) {
					Integer biopenCount = (Integer) tuple[0];
					openCount = biopenCount.longValue();
					
				}else {
					openCount = 0L;
				}
				
				Long closeCount = 0L;
				if(tuple[1]!=null) {
					Integer bicloseCount = (Integer) tuple[1];
					closeCount = bicloseCount.longValue();
					
				}else {
					
					 closeCount = 0L;
				}
				
				Long serviceRequestCount = 0L;
				if(tuple[2]!=null) {
					Integer biserviceRequestCount = (Integer) tuple[2];
					serviceRequestCount = biserviceRequestCount.longValue();
					
				}else {
					
					serviceRequestCount = 0L;
				}
				
				
				
				BigInteger biregionId = (BigInteger) tuple[3];
				Long regionId1 = biregionId.longValue();
				
				ids.add(regionId1);
				
				String regionName = "";
				if(tuple[4]!=null) {
					regionName = (String) tuple[4];
				}
				
				ServiceRequestCountDto srDto = new ServiceRequestCountDto();
				srDto.setOpenSrCount(openCount);
				srDto.setCloseSrCount(closeCount);
				srDto.setServiceRequestCount(serviceRequestCount);
				srDto.setRegionalOfficerId(regionId1);
				srDto.setRegionalOfficerName(regionName);
								
			    serviceRequestDtos.add(srDto);
				
			}

			return new ResponseEntity<List<ServiceRequestCountDto>>(serviceRequestDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = WebConstantUrl.GET_ALL_SERVICE_REQUEST_COUNT_FOR_EQUIPMENT)
	@ResponseBody
	public ResponseEntity<List<ServiceRequestCountDto>> getAllSrCountForAllEquipment(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {

				// region=user.getRole().getRole().getRoleId();

				region = clusterRepository.getClusterRegionIdByUsingUserId(user.getId());

			} else if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
			// store name and username are same
			vendorCode = user.getUsername();
			vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
			//vendorId=user.getId();
		}
		
		List<ServiceRequestCountDto> serviceRequestDtos = new ArrayList<ServiceRequestCountDto>();
		
		List<Object[]> objArrList=null;
		if(region!=null || storeCode!=null) {
			objArrList = iServiceRequestService.getAllSrCountForAllEquipment(businessVerticalTypeName,storeCode,vendorId,region);
			
		}
		else if(vendorId!=null){
			objArrList = serviceRequestRepository.getAllSrCountForAllEquipment(businessVerticalTypeName,storeCode,vendorId,region);
			
		}else if(businessVerticalTypeName!=null){
			
			 objArrList = serviceRequestRepository.getAllSrCountForAllEquipment(businessVerticalTypeName);
			
		}
		else {
			
			 objArrList = serviceRequestRepository.getAllSrCountForAllEquipment();
			
		}
     	 
		List<Long> ids = new ArrayList<Long>(); 
		
		try {
			
			

			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

			}

			
			for(Object[] tuple:objArrList) {
				
				
				
				Long openCount = 0L;
				if(tuple[0]!=null) {
					Integer biopenCount = (Integer) tuple[0];
					openCount = biopenCount.longValue();
					
				}else {
					openCount = 0L;
				}
				
				Long closeCount = 0L;
				if(tuple[1]!=null) {
					Integer bicloseCount = (Integer) tuple[1];
					closeCount = bicloseCount.longValue();
					
				}else {
					
					 closeCount = 0L;
				}
				
				Long serviceRequestCount = 0L;
				if(tuple[2]!=null) {
					Integer biserviceRequestCount = (Integer) tuple[2];
					serviceRequestCount = biserviceRequestCount.longValue();
					
				}else {
					
					serviceRequestCount = 0L;
				}
				
				
				
				BigInteger biregionId = (BigInteger) tuple[3];
				Long equipmentId1 = biregionId.longValue();
				
				ids.add(equipmentId1);
				
				String equipmentName = "";
				if(tuple[4]!=null) {
					equipmentName = (String) tuple[4];
				}
				
				ServiceRequestCountDto srDto = new ServiceRequestCountDto();
				srDto.setOpenSrCount(openCount);
				srDto.setCloseSrCount(closeCount);
				srDto.setServiceRequestCount(serviceRequestCount);
				srDto.setEquipmentId(equipmentId1);
				srDto.setEquipmentName(equipmentName);
								
			    serviceRequestDtos.add(srDto);
				
			}

			return new ResponseEntity<List<ServiceRequestCountDto>>(serviceRequestDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
}
