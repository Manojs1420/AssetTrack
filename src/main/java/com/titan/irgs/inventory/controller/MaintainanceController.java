package com.titan.irgs.inventory.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
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
import com.titan.irgs.configuration.AutoTrigger;
import com.titan.irgs.configuration.Configuration1;
import com.titan.irgs.configuration.ConfigurationService;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.domain.AutoAmcInventoryLog;
import com.titan.irgs.inventory.domain.Maintainance;
//import com.titan.irgs.inventory.domain.MaintainanceDetail;
import com.titan.irgs.inventory.mapper.MaintainanceMapper;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.AutoAmcInventoryLogRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.repository.MaintainanceUploadRepository;
//import com.titan.irgs.inventory.service.MaintainanceDetailService;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.inventory.serviceImpl.AmcInventoryServiceImp;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.inventory.vo.MaintainanceDto;
import com.titan.irgs.inventory.vo.MaintainanceVO;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.serviceImpl.AssetService;
import com.titan.irgs.master.serviceImpl.EngineerService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.master.vo.DashboardVO;
import com.titan.irgs.master.vo.ServiceRequestCountForDashboardVO;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.serviceRequest.domain.ServiceRequestCountDto;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping(value = WebConstantUrl.AMCInventory)
public class MaintainanceController {
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
    private ConfigurationService configurationService;
	

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
	AutoAmcInventoryLogRepository autoAmcInventoryLogRepository;
	
	@Autowired
	MaintainanceUploadRepository maintainanceUploadRepository;

	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String Vendor = "VENDOR";
	private final static String REGENGINEER = "REGENGINEER";
	
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";
	
	private final static String ITAdmin="ITAdmin";

	private final static String BREAKDOWN = "Breakdown";

	private final static String OPEN = "OPEN";
	private final static String STORE = "STORE";

	private final static String CLOSE = "CLOSE";

	private static final String REOPEN = "REOPEN";

	private static final Logger logger = LoggerFactory.getLogger(MaintainanceController.class);

	// Create a maintainance
	@SuppressWarnings({ "rawtypes", "static-access" })
	@PostMapping(value = WebConstantUrl.SaveMaintainance)
	@ResponseBody
	public ResponseEntity<?> saveMaintainance(@RequestBody MaintainanceVO maintainanceVO,Long amcId, Principal principal) {

	//	User user = userRepo.findByUsername(principal.getName());
		Maintainance maintainance=null;
	//	Maintainance maintainance = maintainanceMapper.getEntityFromVo(maintainanceVO);
		List<AmcWarranty> amcWarranty=null;
		if(amcId!=null) {
			amcWarranty = amcWarrantyRepository
					.FindByAmcId(amcId);
		}else if(maintainanceVO.getAmcId()!=null) {
			amcWarranty = amcWarrantyRepository
					.FindByAmcId(maintainanceVO.getAmcId());
			amcId=maintainanceVO.getAmcId();
		}
		
		for (Iterator iterator = amcWarranty.iterator(); iterator.hasNext();) {
			AmcWarranty amcWarranty2 = (AmcWarranty) iterator.next();
			LocalDate to = LocalDate.now();
			LocalDate from = amcWarranty2.getWarrantyTo();
			long result = ChronoUnit.DAYS.between(from, to);
			if (result <= 0) {
				LocalDate date = LocalDate.now();
				if (maintainanceRepository.getMaintainanceByWarranyId(amcWarranty2.getWarrantyId()) == null) {
					if((amcWarranty2.getWarrantyFrom().equals(date.now()) || amcWarranty2.getWarrantyFrom().isBefore(date.now()) ) 
							&& ( amcWarranty2.getWarrantyTo().equals(date.now()) || amcWarranty2.getWarrantyTo().isAfter(date.now()))){
						maintainance=new Maintainance();
						maintainance.setWarrantyId(amcWarranty2.getWarrantyId());
						maintainance.setVendorId(amcWarranty2.getVendorId());
						AmcInventory amcInventory=amcInventoryRepository.findByAmcId1(amcId);
						if(amcInventory!=null) {
							maintainance.setAmcInventory(amcInventory);
							try {
								maintainance = maintainanceService.saveMaintainance(maintainance);
							}catch(Exception e) {
								AutoAmcInventoryLog autoAmcInventoryLog = new AutoAmcInventoryLog();


								autoAmcInventoryLog.setAmcInventory(amcInventory);

								//	List<AmcWarranty> amcWarranty=amcWarrantyRepository.findWarrantyByAmcId(amcInventory.getAmcId());
								//autoAmcInventoryLog.setTicketStatus(amcWarrantys.getTicketStatus());
								autoAmcInventoryLog.setServiceCreationStatus("No");
								autoAmcInventoryLog.setIsMailSent("No");
								LocalDate datenow = LocalDate.now();   
								autoAmcInventoryLog.setCreatedOn(new Date());
								autoAmcInventoryLog.setRemarks("service not created::"+e.toString());
								autoAmcInventoryLog.setCreatedDate(datenow);

								autoAmcInventoryLogRepository.save(autoAmcInventoryLog);
							}

							AmcWarranty amcWarrantys = amcWarrantyRepository.getOne(maintainance.getWarrantyId());
							amcWarrantys.setCreatedDate(maintainance.getMaintainanceDateTime());
							amcWarrantys.setTicketStatus(maintainance.getTicketStatus());
							amcWarrantyRepository.save(amcWarrantys);

							//AMC Log creation start
							AutoAmcInventoryLog autoAmcInventoryLog = new AutoAmcInventoryLog();


							autoAmcInventoryLog.setAmcInventory(amcInventory);

							//	List<AmcWarranty> amcWarranty=amcWarrantyRepository.findWarrantyByAmcId(amcInventory.getAmcId());
							//autoAmcInventoryLog.setTicketStatus(amcWarrantys.getTicketStatus());
							autoAmcInventoryLog.setServiceCreationStatus("Yes");
							autoAmcInventoryLog.setIsMailSent("Yes");
							LocalDate datenow = LocalDate.now();    
							autoAmcInventoryLog.setRemarks("service created");
							autoAmcInventoryLog.setCreatedOn(new Date());
							autoAmcInventoryLog.setTicketStatus(maintainance.getTicketStatus());
							autoAmcInventoryLog.setServiceRequestCode(maintainance.getServiceRequestCode());
							autoAmcInventoryLog.setCreatedDate(datenow);

							autoAmcInventoryLogRepository.save(autoAmcInventoryLog);

							// amc log creation end

							// Sr-Notification email Trigger start--------------->
							List<Object[]> amcInventoryList = maintainanceRepository.NotificationCreation(maintainance.getMaintainanceId());
							try {
								for (Object[] rows : amcInventoryList) {


									Maintainance amcInventories = maintainanceService.getByMaintainanceId(((BigInteger) rows[2]).longValue());
									Mail mail = maintainanceService.templeteMail(amcInventories);
									String mailSubjects = "";
									String emailCcValue = (String) rows[4];
									if(emailCcValue!=null) {
										List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str -> !str.isEmpty())
												.collect(Collectors.toList());
										System.out.println(emailCc);
										mail.setMailCC(emailCc);
									}
									String emailTOValue = (String) rows[3];
									if(emailTOValue!=null) {
										List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str -> !str.isEmpty())
												.collect(Collectors.toList());
										System.out.println(emailTo);
										mail.setMailTo(emailTo);
									}
									Long id = amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
									mailSubjects = "Rim Asset  " + maintainance.getServiceRequestCode() + "– AMC Notification Details";


									mail.setMailSubject(mailSubjects);
									if (mailStatus) {
										try {
											emailServiceImpl1.sendMultiPartEmail(mail);
										} catch (MessagingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}


								}
							}catch(Exception e) {
								System.out.println("Mail not sent:" +e);
							}

							System.out.println("the task is completed");


							break;
						}
					}
				}

				throw new ResourceAlreadyExitException("Already Service is Created");

			}
			
		}
		return new ResponseEntity<>(maintainanceMapper.getVoFromEntity(maintainance), HttpStatus.CREATED);
	}
	
	@Transactional
	//@Scheduled(cron = "4 * * ? * *")
	@Scheduled(cron = "1 * * * * *")
	@SuppressWarnings("static-access")
	public void CreateMaintainaceByScheduler() {

		Configuration1 c = configurationService.findByConfigValue(AutoTrigger.AUTO_AMCSR_Escalation_Scheduler.toString());
		if(c!=null) {
			String s = c.getTimeFrom();
			Date date2=new Date();

			SimpleDateFormat d1=new SimpleDateFormat("HH:mm");
			String date1=d1.format(date2);



			if (date1.equalsIgnoreCase(s)) {
				System.out.print("scheduler started:"+s);
				List<AmcWarranty> amcWarrantyss = amcWarrantyRepository.findAll();
				LocalDate date = LocalDate.now();
				for (int i = 0; i <= amcWarrantyss.size() - 1; i++) {

					if((amcWarrantyss.get(i).getWarrantyFrom().equals(date.now()) || amcWarrantyss.get(i).getWarrantyFrom().isBefore(date.now()) ) 
							&& ( amcWarrantyss.get(i).getWarrantyTo().equals(date.now()) || amcWarrantyss.get(i).getWarrantyTo().isAfter(date.now())) 
							)
					{
						List<String> ticketStatus = amcWarrantyRepository.findTicketStatusByAmcID(amcWarrantyss.get(i).getAmcId());
						List<String> NULLstatus =  ticketStatus.stream()  
								.filter(p ->p==null).collect(Collectors.toList());

						if(NULLstatus.isEmpty()) {
							List<String> status =  ticketStatus.stream()  
									.filter(p ->p.equalsIgnoreCase("OPEN")).collect(Collectors.toList());

							List<String> status1 =  ticketStatus.stream()  
									.filter(p ->p.equalsIgnoreCase("CLOSE")).collect(Collectors.toList());

							if(!status.isEmpty()) {

							}

							else {
								try {
									saveMaintainance(null,amcWarrantyss.get(i).getAmcId(), null);
									//	SaveMaintainanceServiceRequest(amcWarrantyss.get(i).getAmcId());
								}catch(Exception e) {
									System.out.println(e);
								}

							}
						}
					}

				}

			}
		}
	}



	@Transactional
	//@Scheduled(cron = "4 * * ? * *")
	@Scheduled(cron = "1 * * * * *")
	@SuppressWarnings("static-access")
	public void CreateMaintainaceByNotCreate() {

		Configuration1 c = configurationService.findByConfigValue(AutoTrigger.AUTO_AMCSR_Escalation_Scheduler.toString());
		if(c!=null) {
			String s = c.getTimeFrom();
			Date date2=new Date();

			SimpleDateFormat d1=new SimpleDateFormat("HH:mm");
			String date1=d1.format(date2);


			LocalDate date = LocalDate.now();

			if (date1.equalsIgnoreCase(s)) {


				System.out.print("scheduler started:"+s);

				List<Long> amcWarrantys1 = autoAmcInventoryLogRepository.getAmcIdByNotCreateStatus();

				for (int i = 0; i <= amcWarrantys1.size() - 1; i++) {


					List<String> ticketStatus = amcWarrantyRepository.findTicketStatusByAmcID(amcWarrantys1.get(i));
					List<String> NULLstatus =  ticketStatus.stream()  
							.filter(p ->p==null).collect(Collectors.toList());

					if(NULLstatus.isEmpty()) {
						List<String> status =  ticketStatus.stream()  
								.filter(p ->p.equalsIgnoreCase("OPEN")).collect(Collectors.toList());

						List<String> status1 =  ticketStatus.stream()  
								.filter(p ->p.equalsIgnoreCase("CLOSE")).collect(Collectors.toList());

						if(status.isEmpty()) {
							try {
								saveMaintainance(null,amcWarrantys1.get(i), null);
								//SaveMaintainanceServiceRequest(amcWarrantys1.get(i));
								AutoAmcInventoryLog autoAmcInventoryLog = autoAmcInventoryLogRepository.getAmcLogIdByAmcId(amcWarrantys1.get(i));

								autoAmcInventoryLog.setCreatedOn(new Date());
								List<AmcInventory> amcInventory=amcInventoryRepository.findByAmcId(amcWarrantys1.get(i));

								for(int j=0;j<=amcInventory.size()-1;j++) {

									autoAmcInventoryLog.setAmcInventory(amcInventory.get(j));

								}

								autoAmcInventoryLog.setServiceCreationStatus("Yes");
								autoAmcInventoryLog.setIsMailSent("Yes");
								LocalDate datenow = LocalDate.now();    
								autoAmcInventoryLog.setRemarks("service created");
								autoAmcInventoryLog.setCreatedDate(datenow);
								List<Maintainance> maintainance=maintainanceRepository.findByAmcIds(autoAmcInventoryLog.getAmcInventory().getAmcId());
								for(int K=0;K<=maintainance.size()-1;K++) {
									if(maintainance.get(K).getTicketStatus().equalsIgnoreCase("OPEN")) {
										autoAmcInventoryLog.setServiceRequestCode(maintainance.get(K).getServiceRequestCode());
										autoAmcInventoryLog.setTicketStatus(maintainance.get(K).getTicketStatus());
									}
								}
								autoAmcInventoryLogRepository.save(autoAmcInventoryLog);
								//	}
								//	}
							}catch(Exception e) {
								System.out.println(e);
							}

						}
					}


				}

			}
		}
	}
	
	
	/*
	@SuppressWarnings({ "static-access", "null" })
	public void SaveMaintainanceServiceRequest(Long amcId){

			List<AmcWarranty> amcWarranty = amcWarrantyRepository
					.FindByAmcId(amcId);
			for (Iterator iterator = amcWarranty.iterator(); iterator.hasNext();) {
				AmcWarranty amcWarranty2 = (AmcWarranty) iterator.next();
				LocalDate to = LocalDate.now();
				LocalDate from = amcWarranty2.getWarrantyTo();
				long result = ChronoUnit.DAYS.between(from, to);
				if (result <= 0) {
					LocalDate date = LocalDate.now();
					if (maintainanceRepository.getMaintainanceByWarranyId(amcWarranty2.getWarrantyId()) == null) {
						if((amcWarranty2.getWarrantyFrom().equals(date.now()) || amcWarranty2.getWarrantyFrom().isBefore(date.now()) ) 
								&& ( amcWarranty2.getWarrantyTo().equals(date.now()) || amcWarranty2.getWarrantyTo().isAfter(date.now()))){
						Maintainance maintainance=new Maintainance();
						maintainance.setWarrantyId(amcWarranty2.getWarrantyId());
						maintainance.setVendorId(amcWarranty2.getVendorId());
						AmcInventory amcInventory=amcInventoryRepository.findByAmcId1(amcId);
						if(amcInventory!=null) {
						maintainance.setAmcInventory(amcInventory);
						try {
						maintainance = maintainanceService.saveMaintainance(maintainance);
						}catch(Exception e) {
							AutoAmcInventoryLog autoAmcInventoryLog = new AutoAmcInventoryLog();

							
							autoAmcInventoryLog.setAmcInventory(amcInventory);
							
						//	List<AmcWarranty> amcWarranty=amcWarrantyRepository.findWarrantyByAmcId(amcInventory.getAmcId());
							//autoAmcInventoryLog.setTicketStatus(amcWarrantys.getTicketStatus());
							autoAmcInventoryLog.setServiceCreationStatus("No");
							autoAmcInventoryLog.setIsMailSent("No");
							LocalDate datenow = LocalDate.now();   
							autoAmcInventoryLog.setCreatedOn(new Date());
							autoAmcInventoryLog.setRemarks("service not created::"+e.toString());
							autoAmcInventoryLog.setCreatedDate(datenow);
						
							autoAmcInventoryLogRepository.save(autoAmcInventoryLog);
						}

						AmcWarranty amcWarrantys = amcWarrantyRepository.getOne(maintainance.getWarrantyId());
						amcWarrantys.setCreatedDate(maintainance.getMaintainanceDateTime());
						amcWarrantys.setTicketStatus(maintainance.getTicketStatus());
						amcWarrantyRepository.save(amcWarrantys);

						//AMC Log creation start
						AutoAmcInventoryLog autoAmcInventoryLog = new AutoAmcInventoryLog();

						
						autoAmcInventoryLog.setAmcInventory(amcInventory);
						
					//	List<AmcWarranty> amcWarranty=amcWarrantyRepository.findWarrantyByAmcId(amcInventory.getAmcId());
						//autoAmcInventoryLog.setTicketStatus(amcWarrantys.getTicketStatus());
						autoAmcInventoryLog.setServiceCreationStatus("Yes");
						autoAmcInventoryLog.setIsMailSent("Yes");
						LocalDate datenow = LocalDate.now();    
						 autoAmcInventoryLog.setRemarks("service created");
						 autoAmcInventoryLog.setCreatedOn(new Date());
						 autoAmcInventoryLog.setTicketStatus(maintainance.getTicketStatus());
						 autoAmcInventoryLog.setServiceRequestCode(maintainance.getServiceRequestCode());
						autoAmcInventoryLog.setCreatedDate(datenow);
					
						autoAmcInventoryLogRepository.save(autoAmcInventoryLog);
						
						// amc log creation end
						
						// Sr-Notification email Trigger start--------------->
						List<Object[]> amcInventoryList = maintainanceRepository.NotificationCreation(maintainance.getMaintainanceId());
						try {
						for (Object[] rows : amcInventoryList) {

							
							Maintainance amcInventories = maintainanceService.getByMaintainanceId(((BigInteger) rows[2]).longValue());
							Mail mail = maintainanceService.templeteMail(amcInventories);
							String mailSubjects = "";
							String emailCcValue = (String) rows[4];
							if(emailCcValue!=null) {
							List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str -> !str.isEmpty())
									.collect(Collectors.toList());
								System.out.println(emailCc);
								mail.setMailCC(emailCc);
							}
							String emailTOValue = (String) rows[3];
							if(emailTOValue!=null) {
							List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str -> !str.isEmpty())
									.collect(Collectors.toList());
								System.out.println(emailTo);
								mail.setMailTo(emailTo);
							}
							Long id = amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
							mailSubjects = "Nu-Nxtwav  " + maintainance.getServiceRequestCode() + "– AMC Notification Details";
							
							
							mail.setMailSubject(mailSubjects);
							if (mailStatus) {
								try {
									emailServiceImpl1.sendMultiPartEmail(mail);
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							

						}
						}catch(Exception e) {
							System.out.println("Mail not sent:" +e);
						}

						System.out.println("the task is completed");


						break;
						}
					}
					}
				}

			}

		
	}
	*/


	@PutMapping(value = WebConstantUrl.UpdateMaintainance)
	@ResponseBody
	public ResponseEntity<MaintainanceVO> updateAmc(@RequestBody MaintainanceVO maintainanaceVO,
			HttpServletRequest request, Principal principal) throws MessagingException, IOException {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		// iServiceRequestService.batchEscalation(serviceRequestVO.getCountServiceRequestdays());

		boolean vendorAssign = false;
		boolean ticketStatus = false;
		boolean poStatus = false;
		boolean engineerStatus = false;
		boolean isAdviceForPayment = false;

		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();
		// List<String> emailTO = new ArrayList<>();
		// List<String> emailCC = new ArrayList<>();

		// mail part...........
		// login user is REGENGINEER or store user for mail
		Maintainance serviceRequestForMail = maintainanceService
				.getByMaintainanceId(maintainanaceVO.getMaintainanceId());

	//	Maintainance maintainance = maintainanceMapper.getEntityFromVo(maintainanaceVO);
	
	//	maintainance.setServiceRequestcloseDate(maintainanaceVO.getServiceRequestcloseDate());
	//	maintainance = maintainanceService.updateMaintainance(maintainance);

		// vendor login to check
		if (maintainanaceVO.getTicketStatus().equalsIgnoreCase("CLOSE"))

		{

			ticketStatus = true;
			maintainanaceVO.setClosedBy(user.getId());
			maintainanaceVO.setServiceRequestcloseDate(new Date());
			maintainanaceVO.setRunningStatus("CLOSE");
			maintainanaceVO.setTicketStatus("CLOSE");
			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD)) {

				// Sr-Notification email Trigger start--------------->
				List<Object[]> amcInventoryList = maintainanceRepository
						.ServiceClosedByRE(maintainanaceVO.getMaintainanceId());
				for (Object[] rows : amcInventoryList) {

					Maintainance amcInventories = maintainanceService
							.getByMaintainanceId(((BigInteger) rows[2]).longValue());
					
					amcInventories.setClousureDescription(maintainanaceVO.getClousureDescription());
					amcInventories.setClousureProblem(maintainanaceVO.getClousureProblem());
					amcInventories.setServiceRequestcloseDate(maintainanaceVO.getServiceRequestcloseDate());
					amcInventories.setClosedBy(maintainanaceVO.getClosedBy());
					
					Mail mail = maintainanceService.templeteMail(amcInventories);
					String mailSubjects = "";
					String emailCcValue = (String) rows[4];
					if(emailCcValue!=null) {
						List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailCc);
						mail.setMailCC(emailCc);
					}
					
					
					String emailTOValue = (String) rows[3];
					if(emailTOValue!=null) {
						List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailTo);
						mail.setMailTo(emailTo);
					}
					
					
					Long id = amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
					mailSubjects = "Rim Asset  " + serviceRequestForMail.getServiceRequestCode()
							+ "– Service Completion";
					
					
					mail.setMailSubject(mailSubjects);
					if (mailStatus) {
						try {
							emailServiceImpl.sendMultiPartEmail(mail);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				System.out.println("the task is completed by RE");

				// Sr-Notification Email TriggerEnd <-------------------------

			}
			if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
				// Sr-Notification email Trigger start--------------->
				List<Object[]> amcInventoryList = maintainanceRepository
						.ServiceClosedByVendor(maintainanaceVO.getMaintainanceId());
				for (Object[] rows : amcInventoryList) {

					Maintainance amcInventories = maintainanceService
							.getByMaintainanceId(((BigInteger) rows[2]).longValue());
					
					amcInventories.setClousureDescription(maintainanaceVO.getClousureDescription());
					amcInventories.setClousureProblem(maintainanaceVO.getClousureProblem());
					amcInventories.setServiceRequestcloseDate(maintainanaceVO.getServiceRequestcloseDate());
					amcInventories.setClosedBy(maintainanaceVO.getClosedBy());
					
					Mail mail = maintainanceService.templeteMail(amcInventories);
					String mailSubjects = "";
					String emailCcValue = (String) rows[4];
					if(emailCcValue!=null) {
					
						List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
					
						System.out.println(emailCc);
						mail.setMailCC(emailCc);
					}
					String emailTOValue = (String) rows[3];
					
					if(emailTOValue!=null) {
						List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailTo);
						mail.setMailTo(emailTo);
					}
					

					
					Long id = amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
					mailSubjects = "Rim Asset  " + serviceRequestForMail.getServiceRequestCode()
							+ "– Service Completion";
					
					
					mail.setMailSubject(mailSubjects);
					if (mailStatus) {
						try {
							emailServiceImpl.sendMultiPartEmail(mail);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				System.out.println("the task is completed by vendor");

				// Sr-Notification Email TriggerEnd <-------------------------

			}

			// if login user is STORE if ticketStatus
			if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {
				// Sr-Notification email Trigger start--------------->
				List<Object[]> amcInventoryList = maintainanceRepository
						.ServiceClosedByStore(maintainanaceVO.getMaintainanceId());
				for (Object[] rows : amcInventoryList) {

					/*
					 * srNotificationEmail_id:- System.out.println(rows[0]); activity_Name:-
					 * System.out.println(rows[1]); service_request_id:-
					 * System.out.println(rows[2]); TOemail:- System.out.println(rows[3]); CCEmail:-
					 * System.out.println(rows[4]);
					 */
					Maintainance amcInventories = maintainanceService
							.getByMaintainanceId(((BigInteger) rows[2]).longValue());
					
					amcInventories.setClousureDescription(maintainanaceVO.getClousureDescription());
					amcInventories.setClousureProblem(maintainanaceVO.getClousureProblem());
					amcInventories.setServiceRequestcloseDate(maintainanaceVO.getServiceRequestcloseDate());
					amcInventories.setClosedBy(maintainanaceVO.getClosedBy());
					
					Mail mail = maintainanceService.templeteMail(amcInventories);
					
					String mailSubjects = "";
					String emailCcValue = (String) rows[4];
					
					if(emailCcValue!=null) {
						List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailCc);
						mail.setMailCC(emailCc);
					}
					
					
					String emailTOValue = (String) rows[3];
					
					if(emailTOValue!=null) {
						List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailTo);
						mail.setMailTo(emailTo);
					}
					

					
					Long id = amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
					mailSubjects = "Nu-Nxtwav  " + serviceRequestForMail.getServiceRequestCode()
							+ "– Service Completion";
					
				
					mail.setMailSubject(mailSubjects);
					if (mailStatus) {
						try {
							emailServiceImpl.sendMultiPartEmail(mail);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				System.out.println("the task is completed by store");

				// Sr-Notification Email TriggerEnd <-------------------------

			}
		} else if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)
				|| user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {

			if (maintainanaceVO.getEngineerId() != null
					&& maintainanaceVO.getEngineerId() != serviceRequestForMail.getEngineerId()) {
				/*
				 * if (!(serviceRequestForMail.getServiceEngineerName())
				 * .equalsIgnoreCase(serviceRequestVO.getServiceEngineerName())) {
				 */
				engineerStatus = true;
				maintainanaceVO.setRunningStatus("Assigning Engineer");
				// Sr-Notification email Trigger start--------------->
				List<Object[]> amcInventoryList = maintainanceRepository
						.AssigningEngineer(maintainanaceVO.getMaintainanceId());
				for (Object[] rows : amcInventoryList) {

					/*
					 * srNotificationEmail_id:- System.out.println(rows[0]); activity_Name:-
					 * System.out.println(rows[1]); service_request_id:-
					 * System.out.println(rows[2]); TOemail:- System.out.println(rows[3]); CCEmail:-
					 * System.out.println(rows[4]);
					 */
					Maintainance amcInventories = maintainanceService
							.getByMaintainanceId(((BigInteger) rows[2]).longValue());
					Mail mail = maintainanceService.templeteMail(amcInventories);
					String mailSubjects = "";
					String emailCcValue = (String) rows[4];
					
					if(emailCcValue!=null) {
						List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailCc);
						mail.setMailCC(emailCc);
					}
					
					String emailTOValue = (String) rows[3];
					if(emailTOValue!=null) {
						List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str -> !str.isEmpty())
							.collect(Collectors.toList());
						System.out.println(emailTo);
						mail.setMailTo(emailTo);
					}
					
					Long id = amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
					mailSubjects = "Nu-Nxtwav  " + serviceRequestForMail.getServiceRequestCode()
							+ "– Engineer Assigned";
					
					
					mail.setMailSubject(mailSubjects);
					if (mailStatus) {
						try {
							emailServiceImpl.sendMultiPartEmail(mail);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				// Sr-Notification Email TriggerEnd <-------------------------
				System.out.println("Assigning Engineer is completed");

			}

		}

		
		  //commented by madhu
		  
		  Maintainance maintainance1 = maintainanceMapper.getEntityFromVo(maintainanaceVO);
		
		  maintainance1.setServiceRequestcloseDate(maintainanaceVO.getServiceRequestcloseDate());
		  maintainance1 =maintainanceService.updateMaintainance(maintainance1);
		  
		 
		maintainanaceVO = maintainanceMapper.getVoFromEntity(maintainance1);

		return new ResponseEntity<>(maintainanaceVO, HttpStatus.OK);
	}

	/*
	 * public ResponseEntity<MaintainanceVO> updateMaintainance(@RequestBody
	 * MaintainanceVO maintainanceVO) {
	 * 
	 * 
	 * boolean getAdviceForPayment = false; // adviceForPayment if
	 * (maintainanceVO.getAdviceForPayment() != null &&
	 * maintainanceVO.getAdviceForPayment()) { getAdviceForPayment = true;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * Maintainance maintainance =
	 * maintainanceMapper.getEntityFromVo(maintainanceVO);
	 * 
	 * 
	 * maintainance = maintainanceService.updateMaintainance(maintainance); return
	 * new ResponseEntity<MaintainanceVO>(maintainanceMapper.getVoFromEntity(
	 * maintainance), HttpStatus.OK); }
	 */

	// get By Id from Maintainance

	@GetMapping(value = WebConstantUrl.GetByMaintainanceId)
	public ResponseEntity<?> getByMaintainanceId(@PathVariable Long maintainanceId) {
		Maintainance maintainance = maintainanceService.getByMaintainanceId(maintainanceId);
		

		MaintainanceVO maintainanceVO = maintainanceMapper.getVoFromEntity(maintainance);

		return new ResponseEntity<>(maintainanceVO, HttpStatus.OK);
		//return ResponseEntity.ok(maintainance);
	}

	@GetMapping(value = WebConstantUrl.GetAllMaintainance)
	@ResponseBody
	public ResponseEntity<?> getAllMaintainance(@RequestParam(required = false) String srNumber,
			@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String assetCode, @RequestParam(required = false) String erNumber,
			@RequestParam(required = false) String vendorName, @RequestParam(required = false) String farNo,
			@RequestParam(required = false) String serviceRequestType,
			@RequestParam(required = false) String vendorCode, @RequestParam(required = false) String ticketStatus,
			@RequestParam(required = false) String runningStatus,
			@RequestParam(required = false) String serviceRequestDate,
			@RequestParam(required = false) String serviceRequestClosedDate,
			@RequestParam(required = false) String storeCode, @RequestParam(required = false) List<Long> region,
			
			@RequestParam(required = false) String serviceDocumentUploaded,
			Pageable pageable, Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();

		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		String department=null;
		if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor) ) {

			if ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin) &&
			(!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD))) {

				storeCode=user.getUsername();
				
			}else if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			}

			else {

				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				
			}


		}
		

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
			// store name and username are same
			vendorCode = user.getUsername();

		}

		List<MaintainanceVO> maintainanceVOs = new ArrayList<>();
		Page<Maintainance> maintainances = maintainanceService.getAllMaintainance(srNumber,
				businessVerticalTypeName, assetCode, erNumber, vendorName, farNo, serviceRequestType, vendorCode,
				ticketStatus, runningStatus, serviceRequestDate, serviceRequestClosedDate, storeCode, region, serviceDocumentUploaded,department,page);

		if (maintainances.getContent().size() == 0) {
			map.put("maintainanceVOs", maintainanceVOs);
			map.put("total_pages", maintainances.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", maintainances.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);

		} else {
			
			
			maintainances.forEach(amcinventory -> {

				maintainanceVOs.add(maintainanceMapper.getVoFromEntity(amcinventory));
			/*	MaintainanceVO maintainanceVO=maintainanceMapper.getVoFromEntity(amcinventory);
				
				List<MaintainanceUpload> maintainanceUpload=maintainanceUploadRepository.getSrUploadByMaintainanceId(amcinventory.getMaintainanceId());
				
				
				if(maintainanceUpload!=null) {
							maintainanceVO.setServiceDocumentUploaded(true);
					}else
					{
							maintainanceVO.setServiceDocumentUploaded(false);
					}
					maintainanceVOs.add(maintainanceVO);	
				*/	
			});

			map.put("maintainanceVOs", maintainanceVOs);
			map.put("total_pages", maintainances.getTotalPages());
			map.put("status_code", HttpStatus.OK);
			map.put("total_records", maintainances.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@GetMapping(value = WebConstantUrl.GET_ALL_AMC_SERVICE_REQUEST_ON_START_DATE_AND_END_DATE_AND_REQUEST_TYPE)
	public void exportToExcel(HttpServletResponse response, @RequestParam(required = true) Long userId,
			@RequestParam(required = true) String fromDate, @RequestParam(required = true) String toDate,
			@RequestParam(required = true) String breakDownType,
			@RequestParam(required = true) String serviceRequestType) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		List<String> statusList = new ArrayList<String>();
		String status = null;
		if (serviceRequestType.equalsIgnoreCase("OPEN")) {
			statusList.add("OPEN");
			statusList.add("REOPEN");
		} else if (serviceRequestType.equalsIgnoreCase("CLOSE")) {
			statusList.add("CLOSE");
		} else if (serviceRequestType.equalsIgnoreCase("ALL")) {
			statusList.add("OPEN");
			statusList.add("CLOSE");
			statusList.add("REOPEN");
		}

		status = statusList.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));

		Long vendorId = null;
		List<Long> storeIds = null;
		String stringStoreIds = null;
		String businessVerticalTypeName = null;
		List<Long> regionids = null;
String region=null;
		User user = userRepo.getByUserId(userId);

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(ITAdmin)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(REGENGINEER)) {
			

			 if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {
				storeIds = Arrays.asList(storeRepository.findByStoreCode(user.getUsername()).getStoreId());
			} else {
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
				storeIds = storeRepository.getStoreIdsByUsingUserId(user.getId());
			}
			stringStoreIds = storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));
		}
	/*	else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				&& !user.getRole().getRole().getRoleName().equalsIgnoreCase(ITAdmin)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}*/
		
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(REGENGINEER)) {

			// region=user.getRole().getRole().getRoleId();

			regionids = clusterRepository.getClusterRegionIdByUsingUserId(user.getId());
			region= regionids.stream().map(String::valueOf).collect(Collectors.joining("','", "'", "'"));
			
		}
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {

			vendorId = vendorRepository.findByVendorCode(user.getUsername()).getVendorId();

		}
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ServiceRequestReport.xlsx";
		response.setHeader(headerKey, headerValue);

		List<Object[]> serviceRequestList = maintainanceService.getAllForExportAMCServiceRequest(fromDate, toDate,
				status, breakDownType, stringStoreIds, businessVerticalTypeName, vendorId,region);
		
		  for (Object[] objects : serviceRequestList) {
		  
		  Long diffIndays=null;
		  
		  if(objects[12].equals(OPEN)) 
		  { 
			  diffIndays = ChronoUnit.DAYS.between( ((Date)objects[8]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
		  new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+1;
		  }
		  
		  else if((Date) objects[9]!=null && objects[12].equals(CLOSE)) {
		  
		  diffIndays = ChronoUnit.DAYS.between( ((Date)
		  objects[8]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
		  ((Date)
		  objects[9]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+1;
		  
		  }
		  
		  objects[10] = diffIndays; 
		  }
		 

		// ExcelExporter excelExporter = new
		// ExcelExporter(MasterHeaders.servicerequest,serviceRequestList);
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.servicerequestReport, serviceRequestList);

		excelExporter.export(response);
	}

	@GetMapping(value = WebConstantUrl.GET_ALL_AMC_SERVICE_REQUEST_AUTO_CREATED_Export)
	public void exportToExcelAutoCreated(HttpServletResponse response, @RequestParam(required = true) Long userId,
			@RequestParam(required = true) boolean serviceCreationStatus) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		List<String> statusList = new ArrayList<String>();
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
		boolean status = false;
		Long vendorId = null;
		String businessVerticalTypeName = null;

		User user = userRepo.getByUserId(userId);

		
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {

			vendorId = vendorRepository.findByVendorCode(user.getUsername()).getVendorId();

		}
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ServiceRequestReport.xlsx";
		response.setHeader(headerKey, headerValue);

		List<Object[]> serviceRequestList = maintainanceService.getAllForExportAutoAMCServiceRequest(status,
				businessVerticalTypeName, vendorId);

		// ExcelExporter excelExporter = new
		// ExcelExporter(MasterHeaders.servicerequest,serviceRequestList);
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.servicerequestReport, serviceRequestList);

		excelExporter.export(response);
	}
	@GetMapping(value = WebConstantUrl.GetAllMaintainanceAutoLog)
	@ResponseBody
	public ResponseEntity<?> getAllMaintainanceforauto(@RequestParam(required = false) String serviceRequestCode,
			@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String AssetCode, @RequestParam(required = false) String erNumber,
			@RequestParam(required = false) String vendorName, @RequestParam(required = false) String farNo,
			@RequestParam(required = false) String serviceRequestType,
			@RequestParam(required = false) String vendorCode, @RequestParam(required = false) String ticketStatus,
			@RequestParam(required = false) String runningStatus,
			@RequestParam(required = false) String serviceRequestDate,
			@RequestParam(required = false) String serviceRequestClosedDate,
			@RequestParam(required = false) String storeCode, @RequestParam(required = false) List<Long> region,

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

		List<MaintainanceVO> maintainanceVOs = new ArrayList<>();
		// to filter data using procedure.
				List<Maintainance> serviceRequests = maintainanceService.getAllMaintainanceforauto(serviceRequestCode,
						businessVerticalTypeName, AssetCode, erNumber, vendorName, farNo, serviceRequestType, vendorCode,
						ticketStatus, runningStatus, serviceRequestDate, serviceRequestClosedDate, storeCode, region,department, page);
			
				// getting number of records.
		/*		Long count = maintainanceService.count(serviceRequestCode,
						businessVerticalTypeName, AssetCode, erNumber, vendorName, farNo, serviceRequestType, vendorCode,
						ticketStatus, runningStatus, serviceRequestDate, serviceRequestClosedDate, storeCode, region, page);
*/
				if (serviceRequests.size() == 0) {

					map.put("maintainanceVOs", maintainanceVOs);
					map.put("total_pages", 0);
					map.put("status_code", HttpStatus.NO_CONTENT);
		//			map.put("total_records", count);
					new ResponseEntity<>(map, HttpStatus.OK);

				}

				
				// calculating page

				
		 else {
			
			 maintainanceVOs = serviceRequests.stream().map(maintainanceMapper::getVoFromEntity)
							.collect(Collectors.toList());

		//		maintainanceVOs.add(maintainanceMapper.getVoFromEntity(amcinventory));
			
	/*			int page1 = (int) Math.ceil((double) count / (double) pageable.getPageSize());

				map.put("maintainanceVOs", maintainanceVOs);
				map.put("total_pages", page1);
				map.put("status_code", HttpStatus.OK);
				map.put("total_records", count);
*/
				
		
		}
				return new ResponseEntity<>(map, HttpStatus.OK);

	}
	
	@PostMapping(value = WebConstantUrl.GET_AMC_SERVICE_REQUEST_COUNT_FOR_DASHBOARD)
	@ResponseBody
	public ResponseEntity<?> getAMCServiceRequestCountForDashboard(
			@RequestBody(required = false) DashboardVO dashboardVO,Principal principal)
	{

		Map<String,Object> map=new HashMap<>();

		String businessVerticalType=null;

		User user=userRepo.findByUsername(principal.getName());
		
		String department=null;
		String user1=null;
		String vendorCode=null;
		
		//setting bussiness verticle with respective login user
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if(user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)) {
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

		if(user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {	
			businessVerticalType=dashboardVO.getWebMasterName();

		}
		
		if(user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)){			
			user1=null;
			vendorCode=user.getUsername();

		}


		/*
		if(user.getRoleWiseDepartments()!=null){
			departmentName = user.getRoleWiseDepartments().getDepartment().getDepartmentName();
		}
		 */
		List<Object[]> sLists = maintainanceService.getAMCServiceRequestCountForDashboard(businessVerticalType,department,vendorCode);
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
	
	@GetMapping(value = WebConstantUrl.GET_ALL_AMC_SERVICE_REQUEST_COUNT_FOR_VENDOR)
	@ResponseBody
	public ResponseEntity<List<MaintainanceDto>> getAllAmcSrCountForAllVendors(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
			// store name and username are same
			vendorCode = user.getUsername();
			vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
		//	vendorId=user.getId();
		}
		
		List<MaintainanceDto> maintainanceDtos = new ArrayList<MaintainanceDto>();
		
		List<Object[]> objArrList=null;
		if(region!=null || storeCode!=null) {
			objArrList = maintainanceService.getAllAmcSrCountForAllVendors(businessVerticalTypeName,storeCode,vendorId,region);
			
		}
		else if(vendorId!=null){
			objArrList = maintainanceRepository.getAllAmcSrCountForAllVendors(businessVerticalTypeName,storeCode,vendorId,region);
			
		}else if(businessVerticalTypeName!=null){
			
			 objArrList = maintainanceRepository.getAllAmcSrCountForAllVendors(businessVerticalTypeName);
			
		}else {
			
			 objArrList = maintainanceRepository.getAllAmcSrCountForAllVendors();
			
		}
     	
		List<Long> ids = new ArrayList<Long>(); 
		
		try {
			
			

			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<MaintainanceDto>>(HttpStatus.NO_CONTENT);

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
				Long vendorId1 = biregionId.longValue();
				
				ids.add(vendorId1);
				
				String vendorName = "";
				if(tuple[4]!=null) {
					vendorName = (String) tuple[4];
				}
				
				MaintainanceDto AmcsrDto = new MaintainanceDto();
				AmcsrDto.setOpenAmcSrCount(openCount);
				AmcsrDto.setCloseAmcSrCount(closeCount);
				AmcsrDto.setTotalamcSrCount(serviceRequestCount);
				AmcsrDto.setVendorId(vendorId1);
				AmcsrDto.setVendorName(vendorName);
								
				maintainanceDtos.add(AmcsrDto);
				
			}

			return new ResponseEntity<List<MaintainanceDto>>(maintainanceDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<MaintainanceDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = WebConstantUrl.GET_ALL_AMC_SERVICE_REQUEST_COUNT_FOR_REGION)
	@ResponseBody
	public ResponseEntity<List<MaintainanceDto>> getAllAmcSrCountForAllRegion(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
		
			if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
			// store name and username are same
			vendorCode = user.getUsername();
			vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
			//vendorId=user.getId();
		}
		
		List<MaintainanceDto> maintainanceDtos = new ArrayList<MaintainanceDto>();
		
		List<Object[]> objArrList=null;
		if(storeCode!=null) {
			objArrList = maintainanceService.getAllAmcSrCountForAllRegion(businessVerticalTypeName,storeCode,vendorId,region);
			
		}
		else if(region!=null  || vendorId!=null){
			objArrList = maintainanceRepository.getAllAmcSrCountForAllRegion(businessVerticalTypeName,storeCode,vendorId,region);
			
		}else if(businessVerticalTypeName!=null){
			
			 objArrList =  maintainanceRepository.getAllAmcSrCountForAllRegion(businessVerticalTypeName);
			
		}else {
			
			 objArrList =  maintainanceRepository.getAllAmcSrCountForAllRegion();
			
		}
     	
		List<Long> ids = new ArrayList<Long>(); 
		
		try {
			
			

			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<MaintainanceDto>>(HttpStatus.NO_CONTENT);

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
				
				Long regionId1=null;
				if(tuple[3]!=null) {
					
				BigInteger biregionId = (BigInteger) tuple[3];
				 regionId1 = biregionId.longValue();
				
				ids.add(regionId1);
				}
				
				String regionName = "";
				if(tuple[4]!=null) {
					regionName = (String) tuple[4];
				}
				
				MaintainanceDto AmcsrDto = new MaintainanceDto();
				AmcsrDto.setOpenAmcSrCount(openCount);
				AmcsrDto.setCloseAmcSrCount(closeCount);
				AmcsrDto.setTotalamcSrCount(serviceRequestCount);
				AmcsrDto.setRegionalOfficerId(regionId1);
				AmcsrDto.setRegionalOfficerName(regionName);
								
				maintainanceDtos.add(AmcsrDto);
				
			}

			return new ResponseEntity<List<MaintainanceDto>>(maintainanceDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<MaintainanceDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping(value = WebConstantUrl.GET_ALL_AMC_SERVICE_REQUEST_COUNT_FOR_EQUIPMENT)
	@ResponseBody
	public ResponseEntity<List<MaintainanceDto>> getAllAmcSrCountForAllEquipment(@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String storeCode,@RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) List<Long> region,Principal principal, HttpServletRequest request) {
		
		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			
			if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
			// store name and username are same
			vendorCode = user.getUsername();
			vendorId=	vendorRepository.findVendoIDbyCode(vendorCode);
		//	vendorId=user.getId();
		}
		
		List<MaintainanceDto> maintainanceDtos = new ArrayList<MaintainanceDto>();
		
		
		List<Object[]> objArrList=null;
		if(region!=null || storeCode!=null) {
			objArrList = maintainanceService.getAllAmcSrCountForAllEquipment(businessVerticalTypeName,storeCode,vendorId,region);
			
		}
		else if(vendorId!=null){
			objArrList = maintainanceRepository.getAllAmcSrCountForAllEquipment(businessVerticalTypeName,storeCode,vendorId,region);
			
		}else if(businessVerticalTypeName!=null){
			
			 objArrList = maintainanceRepository.getAllAmcSrCountForAllEquipment(businessVerticalTypeName);
			
		}else {
			
			 objArrList = maintainanceRepository.getAllAmcSrCountForAllEquipment();
			
		}
     	
		List<Long> ids = new ArrayList<Long>(); 
		
		try {
			
			

			if (objArrList == null || objArrList.isEmpty()) {
				return new ResponseEntity<List<MaintainanceDto>>(HttpStatus.NO_CONTENT);

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
				
				MaintainanceDto AmcsrDto = new MaintainanceDto();
				AmcsrDto.setOpenAmcSrCount(openCount);
				AmcsrDto.setCloseAmcSrCount(closeCount);
				AmcsrDto.setTotalamcSrCount(serviceRequestCount);
				AmcsrDto.setEquipmentId(equipmentId1);
				AmcsrDto.setEquipmentName(equipmentName);
								
				maintainanceDtos.add(AmcsrDto);
				
			}

			return new ResponseEntity<List<MaintainanceDto>>(maintainanceDtos, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<MaintainanceDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

//		List<ServiceRequestVO> serviceRequestVOs = new ArrayList<>();
/*
 * User user = userRepo.findByUsername(principal.getName());
 * 
 * // setting bussiness verticle with respective login user if
 * (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin) &&
 * !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(
 * MANAGEMENT) &&
 * !user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
 * 
 * if (user.getRole().getRole().getRoleName().equalsIgnoreCase(STORE)) {
 * 
 * storeIds =
 * Arrays.asList(storeRepository.findByStoreCode(user.getUsername()).getStoreId(
 * ));
 * 
 * }
 * 
 * else {
 * 
 * businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
 * storeIds = storeRepository.getStoreIdsByUsingUserId(user.getId());
 * 
 * }
 * 
 * stringStoreIds =
 * storeIds.stream().map(String::valueOf).collect(Collectors.joining("','", "'",
 * "'"));
 * 
 * }
 * 
 * if (user.getRole().getRole().getRoleName().equalsIgnoreCase(IRSG)) {
 * businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();
 * 
 * }
 * 
 * 
 * if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
 * 
 * vendorId =
 * vendorRepository.findByVendorCode(user.getUsername()).getVendorId();
 * 
 * }
 * 
 * 
 * // Page<Maintainance> maintainances = //
 * maintainanceService.getAllMaintainance(serviceRequestCode,AssetCode, //
 * erNo,farNo,vendorCode,ticketStatus,runningStatus,page);
 * 
 * // to filter data using procedure. List<Maintainance> maintainances =
 * maintainanceService.getAll(serviceRequestCode, businessVerticalTypeName,
 * stringStoreIds, AssetCode, erNo, vendorName, farNo, serviceRequestType,
 * vendorCode, ticketStatus, runningStatus, serviceRequestDate,
 * serviceRequestClosedDate, pageable); // sorting on date // serviceRequests =
 * // serviceRequests.stream().sorted(Comparator.comparing(ServiceRequest::
 * getServiceRequestDate).reversed()).collect(Collectors.toList()); // getting
 * number of records. Long count = maintainanceService.count(serviceRequestCode,
 * businessVerticalTypeName, stringStoreIds, AssetCode, erNo, vendorName, farNo,
 * serviceRequestType, vendorCode, ticketStatus, runningStatus,
 * serviceRequestDate, serviceRequestClosedDate);
 * 
 * if (maintainances.size() == 0) {
 * 
 * map.put("maintainanceVOs", maintainanceVOs); map.put("total_pages", 0);
 * map.put("status_code", HttpStatus.NO_CONTENT); map.put("total_records",
 * count); new ResponseEntity<>(map, HttpStatus.OK);
 * 
 * }
 * 
 * maintainanceVOs =
 * maintainances.stream().map(maintainanceMapper::getVoFromEntity).collect(
 * Collectors.toList());
 * 
 * // calculating page
 * 
 * int page = (int) Math.ceil((double) count / (double) pageable.getPageSize());
 * 
 * map.put("maintainanceVOs", maintainanceVOs); map.put("total_pages", page);
 * map.put("status_code", HttpStatus.OK); map.put("total_records", count);
 * 
 * return new ResponseEntity<>(map, HttpStatus.OK); }
 */
/*
 * // get all
 * 
 * @GetMapping(value = WebConstantUrl.GetAllMaintainance) public
 * List<Maintainance> getAllMaintainance() { return
 * maintainanceService.getAllmaintainance(); }
 * 
 * 
 * 
 * @DeleteMapping(value = WebConstantUrl.DeleteByMaintainanceId) public
 * ResponseEntity<Maintainance> deleteByMaintainanceId(@PathVariable Long
 * maintainanceId) { Maintainance maintainance =
 * maintainanceService.deleteByMaintainanceId(maintainanceId); return
 * ResponseEntity.ok(maintainance); }
 */
