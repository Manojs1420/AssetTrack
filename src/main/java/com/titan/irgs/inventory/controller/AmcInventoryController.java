package com.titan.irgs.inventory.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
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
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.inventory.mapper.AmcInventoryMapper;
import com.titan.irgs.inventory.mapper.AmcWarrantyMapper;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.AmcWarrantyService;
import com.titan.irgs.inventory.vo.AmcInventoryVO;
import com.titan.irgs.inventory.vo.AmcWarrantyVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.ClusterUserRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@EnableScheduling
@RequestMapping(value = WebConstantUrl.AMCInventory)
public class AmcInventoryController {
	@Value("${mail.status}")
	private Boolean mailStatus;

	@Autowired
	private AmcInventoryService amcInventoryService;
	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ClusterUserRepository clusterUserRepo;
	@Autowired
	AmcInventoryEmailImpl emailServiceImpl;

	@Autowired
	MaintainanceAlertEmailImpl maintainanceAlertEmailImpl;

	@Autowired
	MaintainanceAlertEmailImpl1 maintainanceAlertEmailImpl1;

	@Autowired
	AmcWarrantyService amcWarrantyService ;
	@Autowired
	AmcWarrantyMapper amcWarrantyMapper;

	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;

	@Autowired
	AmcInventoryMapper amcInventoryMapper;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	VendorRepository vendorRepository;
	@Autowired
	ClusterRepository clusterRepository;
	@Autowired
	AmcInventoryRepository amcInventoryRepository;

	@Autowired
	AssetRepository assetRepository;

	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";

	private static final String Vendor="VENDOR"; 
	
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";


	private static final Logger logger = LoggerFactory.getLogger(AmcInventoryController.class);


	// get all
	@SuppressWarnings("null")
	// Create a Amc inventory
	@PostMapping(value = WebConstantUrl.SaveAmcInventory)
	@ResponseBody
	public ResponseEntity<AmcInventoryVO> saveAmcInventory(@RequestBody AmcInventoryVO amcInventoryVO,Principal principal) {
		User user = userRepo.findByUsername(principal.getName());

		AmcInventory amcInventory=amcInventoryMapper.getEntityFromVo(amcInventoryVO);
		amcInventory=amcInventoryService.saveAmcInventory(amcInventory);
		//AmcWarranty amcWarranty=new AmcWarranty();
		amcWarrantyService.saveAmcExtension(amcInventory);

		/*
		 * AmcInventoryDetail amcInventoryDetail=new AmcInventoryDetail();
		 * amcInventoryDetail.setAmcInventory(amcInventory);
		 * amcInventoryDetail.setUser(user);
		 * amcInventoryDetail.setCommants(amcInventoryVO.getDescription());
		 * amcInventoryDetail.setWebRole(user.getRole());
		 * amcInventoryDetail=amcInventoryDetailService.save(amcInventoryDetail);
		 */
		// Sr-Notification email Trigger start--------------->
		List<Object[]> amcInventoryList =amcInventoryRepository.AMCMaintenanceActivation(amcInventory.getAmcId());
		for (Object[] rows : amcInventoryList) {

			/*
			 * srNotificationEmail_id:- System.out.println(rows[0]);
			 * activity_Name:-  System.out.println(rows[1]);
			 * service_request_id:- System.out.println(rows[2]);
			 * TOemail:-  System.out.println(rows[3]);
			 * CCEmail:- System.out.println(rows[4]);
			 */
			AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
			Mail mail = amcInventoryService.templeteMail(amcInventories);
			String mailSubjects = "";			
			String emailCcValue=(String) rows[4];
			if(emailCcValue!=null) {
				List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
				System.out.println(emailCc);
				mail.setMailCC(emailCc); 
			}


			String emailTOValue=(String) rows[3];
			if(emailTOValue!=null) {
				List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
				System.out.println(emailTo);
				mail.setMailTo(emailTo);
			}
			
			
			Long id= amcInventories.getWebMaster().getWebMasterId();
			mailSubjects = "Rim Asset  "+"– AMC Asset Activation";


			mail.setMailSubject(mailSubjects);
			if (mailStatus) { try {
				emailServiceImpl.sendMultiPartEmail(mail);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} } 

		}

		System.out.println("the task is completed");

		//By madhu for mail triggering if maintainanceFromDate < 5 days
		/*	amcInventory=amcInventoryRepository.FindAmcByInventoryId(amcInventory.getInventory().getInventoryId());
			if(amcInventory!=null) {
				Date date3 = null;
			Date date1 = java.sql.Date.valueOf(amcInventory.getMaintainanceStartDate());

			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String s=df.format(amcInventory.getInventory().getUpdatedOn());
			try {
				 date3=df.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(date3.equals(date1)|| date3 == date1 || date3.before(date1))
			{
				maintainanceAlert(amcInventory);
			}
			if(date3.after(date1)) {
				maintainanceAlertMorethanDate(amcInventory);
			}
			}*/

		// Sr-Notification Email TriggerEnd    <-------------------------

		return new ResponseEntity<AmcInventoryVO>(amcInventoryMapper.getVoFromEntity(amcInventory),HttpStatus.CREATED);
	}



	// get By Id from AmcInventory
	@GetMapping(value = WebConstantUrl.GetByAmcId)
	@ResponseBody
	public ResponseEntity<?> getByAmcId(@PathVariable(value = "id") Long id) {


		List<AmcInventory> amcinventories = amcInventoryService.findByAmcId(id);

		if (amcinventories.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<AmcInventoryVO> amcinventoryVOs=amcinventories.stream().map(amcInventoryMapper::getVoFromEntity).collect(Collectors.toList());

		return new ResponseEntity<>(amcinventoryVOs, HttpStatus.OK);
	}
	// Update AmcInventory

	@PutMapping(value = WebConstantUrl.UpdateAmc)
	@ResponseBody
	public ResponseEntity<AmcInventoryVO> updateAmc(@RequestBody AmcInventoryVO amcInventoryVO, HttpServletRequest request,
			Principal principal) throws MessagingException, IOException {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		boolean deactivate = false;
		AmcInventory serviceRequestForMail = amcInventoryService.findById(amcInventoryVO.getAmcId());
		//Email for Deactivation

		if (amcInventoryVO.getAmcStatus().equals(AmcStatus.INACTIVE)) {
			deactivate = true;

			List<Object[]> amcInventoryList =amcInventoryRepository.AMCMaintenanceDeactivation(amcInventoryVO.getAmcId());
			for (Object[] rows : amcInventoryList) {

				/*
				 * srNotificationEmail_id:- System.out.println(rows[0]);
				 * activity_Name:-  System.out.println(rows[1]);
				 * service_request_id:- System.out.println(rows[2]);
				 * TOemail:-  System.out.println(rows[3]);
				 * CCEmail:- System.out.println(rows[4]);
				 */
				AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
				String mailSubjects = "";			
				String emailCcValue=(String) rows[4];
				List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
				System.out.println(emailCc);
				String emailTOValue=(String) rows[3];
				List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
				System.out.println(emailTo);


				Mail mail = amcInventoryService.templeteMail(amcInventories);
				Long id= amcInventories.getWebMaster().getWebMasterId();
				mailSubjects = "Rim Asset "+"– AMC Asset Deactivation";
				mail.setMailTo(emailTo);
				mail.setMailCC(emailCc); 
				mail.setMailSubject(mailSubjects);
				if (mailStatus) { try {
					emailServiceImpl.sendMultiPartEmail(mail);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} } 

			}

			System.out.println("the task is completed");
		}



		AmcInventory amcInventory = amcInventoryMapper.getEntityFromVo(amcInventoryVO);

		amcInventory = amcInventoryService.updateAmc(amcInventory);

		//By madhu for mail triggering if maintainanceFromDate < 5 days
		//	amcInventory=amcInventoryRepository.FindAmcByInventoryId(amcInventory.getInventory().getInventoryId());
		if(amcInventory!=null) {
			Date date3 = null;
			Date date1 = java.sql.Date.valueOf(amcInventory.getMaintainanceStartDate());

			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String s=df.format(amcInventory.getAsset().getUpdatedOn());
			try {
				date3=df.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(date3.equals(date1)|| date3 == date1 || date3.before(date1))
			{
				maintainanceAlert(amcInventory);
			}
			if(date3.after(date1)) {
				maintainanceAlertMorethanDate(amcInventory);
			}
		}

		amcInventoryVO=amcInventoryMapper.getVoFromEntity(amcInventory);
		return new ResponseEntity<AmcInventoryVO>(amcInventoryVO, HttpStatus.OK);
	}		

	@GetMapping(value = WebConstantUrl.GET_AMC_BY_Asset_ID)
	@ResponseBody
	public ResponseEntity<?> getAmcByAssetId(@PathVariable(value = "id") Long id) {


		List<AmcInventory> amcinventories = amcInventoryService.getAmcByAssetId(id);

		if (amcinventories.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<AmcInventoryVO> amcinventoryVOs=amcinventories.stream().map(amcInventoryMapper::getVoFromEntity).collect(Collectors.toList());

		return new ResponseEntity<>(amcinventoryVOs, HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GetAllAMCInventories)
	@ResponseBody
	public ResponseEntity<?> getAllAmcInventories(
			@RequestParam(required=false) String maintainanceType,
			@RequestParam(required=false) Long maintainancePeriod,
			@RequestParam(required=false) String maintainanceStartDate,
			@RequestParam(required=false) String maintainanceEndDate,
			@RequestParam(required=false) Long minMaintainanceGap,
			@RequestParam(required=false) String maintainanceValidity,
			@RequestParam(required=false) Long numberOfService,
			@RequestParam(required=false) String contractNumber,
			@RequestParam(required=false) String amcStatus,
			@RequestParam(required=false) Boolean activeStatus,
			@RequestParam(required=false) String businessVerticalName,
			@RequestParam(required=false) String vendorCode,
			@RequestParam(required=false) String storeCode,
			@RequestParam(required=false) String assetName,
			@RequestParam(required=false) String erNo,
			@RequestParam(required=false) String farNo,
			@RequestParam(required=false) String modelName,
			@RequestParam(required=false) List<Long> region,
			@RequestParam(required=false) String VerticalName,
			@RequestParam(required=false) String installationDate,
			@RequestParam(required=false) Long fromYear,
			@RequestParam(required=false) Long toYear,


			Pageable pageable,Principal principal,HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));


		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();

		User user=userRepo.findByUsername(principal.getName());

		String department=null;
		
		if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor) ) {

			if ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin) &&
			(!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD))) {

				storeCode=user.getUsername();
				
			}else if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				VerticalName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			}

			else {

				VerticalName = user.getRole().getWebMaster().getWebMasterName();
				/*
				 * storeIds = storeRepository.getStoreIdsByUsingUserId(user.getId());
				 * stringStoreIds =
				 * storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'",
				 * "'"));
				 */
			}


		}


		if(user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)){			
			//store name and username are same
			vendorCode=user.getUsername();

		}


		List<AmcInventoryVO> amcInventoryVOs = new ArrayList<AmcInventoryVO>();
		Page<AmcInventory> amcInventories = amcInventoryService.getAllAmcInventories(businessVerticalName,amcStatus,maintainanceType,
				maintainancePeriod, maintainanceStartDate, maintainanceEndDate, minMaintainanceGap, maintainanceValidity,numberOfService, 
				contractNumber,vendorCode,activeStatus, storeCode,assetName, erNo,farNo,modelName,region,VerticalName,installationDate,fromYear,toYear,department,page);

		if (amcInventories.getContent().size() == 0) {
			map.put("amcInventoryVOs", amcInventoryVOs);
			map.put("total_pages", amcInventories.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", amcInventories.getTotalElements());

			return new ResponseEntity<>(map,HttpStatus.OK);

		} else {
			amcInventories.forEach(amcinventory -> {

				amcInventoryVOs.add(amcInventoryMapper.getVoFromEntity(amcinventory));
			});

			map.put("amcInventoryVOs", amcInventoryVOs);
			map.put("total_pages", amcInventories.getTotalPages());
			map.put("status_code",  HttpStatus.OK);
			map.put("total_records", amcInventories.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}


	@PostMapping(value = WebConstantUrl.SaveAmcExtendValidation)
	@ResponseBody
	public ResponseEntity<AmcWarrantyVO> saveAmcExtendValidation(@RequestBody AmcWarrantyVO amcWarrantyVO,Principal principal) {
		User user = userRepo.findByUsername(principal.getName());

		AmcWarranty amcWarranty=amcWarrantyMapper.getEntityFromVo(amcWarrantyVO);

		Asset asset=assetRepository.getOne(amcWarranty.getAssetId());

		List<AmcWarranty> amcWarrantys=amcWarrantyRepository.FindByAssetId(asset.getAssetId());
		List<AmcWarranty> amcWarrantyTicketStatusOpen=amcWarrantys.stream().filter(i->i.getTicketStatus().equalsIgnoreCase("OPEN")).collect(Collectors.toList());

		if(!amcWarrantyTicketStatusOpen.isEmpty()) {
			throw new ResourceAlreadyExitException("Amc Service is opened,Can't extend until Close the Service");
		}

		amcWarrantyService.saveAmcValidation(amcWarranty);

		// Sr-Notification email Trigger start--------------->
		List<Object[]> amcInventoryList =amcInventoryRepository.AMCValidation(amcWarranty.getAmcId());
		for (Object[] rows : amcInventoryList) {

			/*
			 * srNotificationEmail_id:- System.out.println(rows[0]);
			 * activity_Name:-  System.out.println(rows[1]);
			 * service_request_id:- System.out.println(rows[2]);
			 * TOemail:-  System.out.println(rows[3]);
			 * CCEmail:- System.out.println(rows[4]);
			 */
			AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
			String mailSubjects = "";			
			String emailCcValue=(String) rows[4];
			List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
			System.out.println(emailCc);
			String emailTOValue=(String) rows[3];
			List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
			System.out.println(emailTo);


			Mail mail = amcInventoryService.templeteMail(amcInventories);
			Long id= amcInventories.getWebMaster().getWebMasterId();
			mailSubjects = "Nu-Nxtwav  "+"– AMC Extend Validity";
			mail.setMailTo(emailTo);
			mail.setMailCC(emailCc); 
			mail.setMailSubject(mailSubjects);
			if (mailStatus) { try {
				emailServiceImpl.sendMultiPartEmail(mail);
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

		return new ResponseEntity<AmcWarrantyVO>(amcWarrantyMapper.getVoFromEntity(amcWarranty),HttpStatus.CREATED);
	}

	@PostMapping(value = WebConstantUrl.SaveExtendAmc)
	@ResponseBody
	public ResponseEntity<AmcInventoryVO> saveExtendAmc(@RequestBody AmcInventoryVO amcInventoryVO,Principal principal) {


		AmcInventory amcInventory=amcInventoryMapper.getEntityFromVo(amcInventoryVO);

		Asset asset=assetRepository.getOne(amcInventory.getAsset().getAssetId());

		List<AmcWarranty> amcWarranty=amcWarrantyRepository.FindByAssetId(asset.getAssetId());
		List<AmcWarranty> amcWarrantyTicketStatusOpen=amcWarranty.stream().filter(i->i.getTicketStatus().equalsIgnoreCase("OPEN")).collect(Collectors.toList());
		if(amcWarrantyTicketStatusOpen.isEmpty()) {

			amcInventory=amcInventoryService.saveAmcInventory(amcInventory);
			amcWarrantyService.saveAmcExtension(amcInventory);

			if(amcInventory.getVendor().getVendorId()!=asset.getVendor().getVendorId()) {

				List<Object[]> amcInventoryList =amcInventoryRepository.VendorChange(amcInventory.getAmcId());
				for (Object[] rows : amcInventoryList) {

					/*
					 * srNotificationEmail_id:- System.out.println(rows[0]);
					 * activity_Name:-  System.out.println(rows[1]);
					 * service_request_id:- System.out.println(rows[2]);
					 * TOemail:-  System.out.println(rows[3]);
					 * CCEmail:- System.out.println(rows[4]);
					 */
					AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
					String mailSubjects = "";			
					String emailCcValue=(String) rows[4];
					List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					System.out.println(emailCc);
					String emailTOValue=(String) rows[3];
					List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
					System.out.println(emailTo);


					Mail mail = amcInventoryService.templeteMail(amcInventories);
					Long id= amcInventories.getWebMaster().getWebMasterId();
					mailSubjects = "Nu-Nxtwav  "+"– Vendor Changed";
					mail.setMailTo(emailTo);
					mail.setMailCC(emailCc); 
					mail.setMailSubject(mailSubjects);
					if (mailStatus) { try {
						emailServiceImpl.sendMultiPartEmail(mail);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} } 

				}

				System.out.println("the task is completed");

			}

			// Sr-Notification email Trigger start--------------->
			List<Object[]> amcInventoryList =amcInventoryRepository.AMCExtension(amcInventory.getAmcId());
			for (Object[] rows : amcInventoryList) {

				/*
				 * srNotificationEmail_id:- System.out.println(rows[0]);
				 * activity_Name:-  System.out.println(rows[1]);
				 * service_request_id:- System.out.println(rows[2]);
				 * TOemail:-  System.out.println(rows[3]);
				 * CCEmail:- System.out.println(rows[4]);
				 */
				AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
				String mailSubjects = "";			
				String emailCcValue=(String) rows[4];
				List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
				System.out.println(emailCc);
				String emailTOValue=(String) rows[3];
				List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
				System.out.println(emailTo);


				Mail mail = amcInventoryService.templeteMail(amcInventories);
				Long id= amcInventories.getWebMaster().getWebMasterId();
				mailSubjects = "Nu-Nxtwav  "+"– AMC Inventory Extension";
				mail.setMailTo(emailTo);
				mail.setMailCC(emailCc); 
				mail.setMailSubject(mailSubjects);
				if (mailStatus) { try {
					emailServiceImpl.sendMultiPartEmail(mail);
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

			return new ResponseEntity<AmcInventoryVO>(amcInventoryMapper.getVoFromEntity(amcInventory),HttpStatus.CREATED);
		}else {

			throw new ResourceAlreadyExitException("Amc Service is opened,Can't extend until Close the Service");
		}

	}



	@GetMapping(value = WebConstantUrl.GET_Warrany_BY_Year)
	@ResponseBody
	public ResponseEntity<?> getWarrantyByYear(Long fromYear,Long toYear) {
		//AmcWarranty amcWarranty=amcInventoryRepository.findAllById(ids);		
		List<AmcWarranty> amcWarranties = new ArrayList<AmcWarranty>();

		List<Long> amcids=amcInventoryRepository.FindAmcByByYear(fromYear, toYear);
		for (Iterator iterator2 = amcids.iterator(); iterator2.hasNext();) {
			Long long1 = (Long) iterator2.next();
			List<AmcWarranty> amcWarranty=amcWarrantyRepository.FindByAmcId(long1);
			amcWarranties.addAll(amcWarranty);
		}
		return new ResponseEntity<>(amcWarranties, HttpStatus.OK);
	}


	//if maintainanceFromDate is lessthan 5 days
	@SuppressWarnings("null")
	public void maintainanceAlert(AmcInventory amcInventory) {
		//maintainance.getAmcInventory().getMaintainanceStartDate()
		LocalDate date=LocalDate.now();

		LocalDate date2=amcInventory.getMaintainanceStartDate().minusDays(4);
		LocalDate date3=amcInventory.getMaintainanceStartDate().minusDays(3);
		LocalDate date4=amcInventory.getMaintainanceStartDate().minusDays(2);
		LocalDate date5=amcInventory.getMaintainanceStartDate().minusDays(1);
		LocalDate date6=amcInventory.getMaintainanceStartDate().minusDays(0);

		if(date2.equals(date) || date3.equals(date) || date4.equals(date)
				|| date5.equals(date) || date6.equals(date))
		{
			// Sr-Notification email Trigger start--------------->
			List<Object[]> amcInventoryList =amcInventoryRepository.AmcMaintainanceAlert(amcInventory.getAmcId());
			for (Object[] rows : amcInventoryList) {

				/*
				 * srNotificationEmail_id:- System.out.println(rows[0]);
				 * activity_Name:-  System.out.println(rows[1]);
				 * service_request_id:- System.out.println(rows[2]);
				 * TOemail:-  System.out.println(rows[3]);
				 * CCEmail:- System.out.println(rows[4]);
				 */
				AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
				Mail mail = amcInventoryService.templeteMailAlert(amcInventories);
				String mailSubjects = "";			
				String emailCcValue=(String) rows[4];
				if(emailCcValue!=null) {
					List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					System.out.println(emailCc);
					mail.setMailCC(emailCc); 
				}
				String emailTOValue=(String) rows[3];

				if(emailTOValue!=null) {
					List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
					System.out.println(emailTo);
					mail.setMailTo(emailTo);
				}

				Long id= amcInventories.getWebMaster().getWebMasterId();
				mailSubjects = "Service Due Alert for the Equipment as per the AMC";


				mail.setMailSubject(mailSubjects);
				if (mailStatus) { try {
					maintainanceAlertEmailImpl.sendMultiPartEmail(mail);
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



		}



	}

	//if after maintainanceFromDate 
	@SuppressWarnings("null")
	public void maintainanceAlertMorethanDate(AmcInventory amcInventory) {
		List<Long> maintainaceIds=amcInventoryRepository.getAllAmcIds();

		//maintainance.getAmcInventory().getMaintainanceStartDate()
		LocalDate date=LocalDate.now();

		LocalDate date2=amcInventory.getMaintainanceStartDate();

		if(date.isAfter(date2))
		{
			// Sr-Notification email Trigger start--------------->
			List<Object[]> amcInventoryList =amcInventoryRepository.AmcMaintainanceAlert(amcInventory.getAmcId());
			for (Object[] rows : amcInventoryList) {

				/*
				 * srNotificationEmail_id:- System.out.println(rows[0]);
				 * activity_Name:-  System.out.println(rows[1]);
				 * service_request_id:- System.out.println(rows[2]);
				 * TOemail:-  System.out.println(rows[3]);
				 * CCEmail:- System.out.println(rows[4]);
				 */
				AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
				Mail mail = amcInventoryService.templeteMailAlert(amcInventories);
				String mailSubjects = "";			
				String emailCcValue=(String) rows[4];
				if(emailCcValue!=null) {
					List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
					System.out.println(emailCc);
					mail.setMailCC(emailCc); 
				}
				String emailTOValue=(String) rows[3];
				if(emailTOValue!=null) {
					List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
					System.out.println(emailTo);
					mail.setMailTo(emailTo);
				}

				Long id= amcInventories.getWebMaster().getWebMasterId();
				mailSubjects = "Service Due Alert for the Equipment as per the AMC";


				mail.setMailSubject(mailSubjects);
				if (mailStatus) { try {
					maintainanceAlertEmailImpl1.sendMultiPartEmail(mail);
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



		}



	}


}
