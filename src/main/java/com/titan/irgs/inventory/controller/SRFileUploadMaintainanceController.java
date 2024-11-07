package com.titan.irgs.inventory.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ImagePath;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.domain.QuotationForMaintainance;
import com.titan.irgs.inventory.domain.SRFileUploadMaintainance;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.repository.QuotationForMaintainanaceRepo;
import com.titan.irgs.inventory.repository.SRFileUploadMaintainanceRepo;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.inventory.vo.SRFileUploadMaintainanceVo;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

  @RestController
  @RequestMapping(value = WebConstantUrl.SR_FILE_UPLOAD_MAINTAINANCE) 
  public class SRFileUploadMaintainanceController {
	  
	  @Value("${mail.status}")
	  private Boolean mailStatus;
	  
	  
	private static final Logger logger = LoggerFactory.getLogger(SRFileUploadMaintainanceController.class);

	  
	 @Autowired
	 ImagePath imagePath;
	  
	  @Autowired
	  SRFileUploadMaintainanceRepo srFileUploadMaintainanceRepo;
	  
	  @Autowired
	  MaintainanceService maintainanceService;
	  
	  @Autowired
	  UserRepo userRepo;
	  
	  
	  @Autowired
	  QuotationForMaintainanaceRepo quotationForMaintainanaceRepo;
	  @Autowired
	  MaintainanceRepository maintainanceRepository;
	  
	  @Autowired
		MaintainanceEmailImpl emailServiceImpl;
	
	  
	 @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	 @PostMapping(WebConstantUrl.quatationUpload)
	   
	 public ResponseEntity<?> upload( @RequestPart(required = true)@RequestParam(value = "file") MultipartFile file,
	    		@RequestParam(value = "srfileInfo") String srfileInfo,Principal principal){
		

		User user=userRepo.findByUsername(principal.getName());
		 
		 
		 ObjectMapper mapper=new ObjectMapper();
		 Map<String,String> map=new HashMap<>();
		 SRFileUploadMaintainanceVo sRFileUploadMaintainanceVo=new 		 SRFileUploadMaintainanceVo(); 
		  try 
		  {
			  sRFileUploadMaintainanceVo=mapper.readValue(srfileInfo, SRFileUploadMaintainanceVo.class); 
		  
		  }
		  catch (Exception e)
		  { 
			  System.out.println(e.getLocalizedMessage()); 
			  
		  }
			
		  String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

		    if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
		      return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 
		    
		    }

		    String fileName = this.imagePath.saveImageOnDesk(file, "quationMaintainance");
		     Maintainance maintainance=maintainanceService.getByMaintainanceId(sRFileUploadMaintainanceVo.getMaintainanceId());
		     maintainance.setRunningStatus("Quatation uploaded");

		  if (!fileName.isEmpty()) {
			  SRFileUploadMaintainance sRFileUploadMaintainance=new SRFileUploadMaintainance();
		      BeanUtils.copyProperties(sRFileUploadMaintainanceVo, sRFileUploadMaintainance);
		      sRFileUploadMaintainance.setEndingPath(str);
		      sRFileUploadMaintainance.setSrFileploadPath(fileName);
		      sRFileUploadMaintainance.setMaintainance(maintainance);
		      sRFileUploadMaintainance= srFileUploadMaintainanceRepo.save(sRFileUploadMaintainance);
		      map.put("msg", "created successfully");
		  
		  }
		  if (user.getRole().getRole().getRoleName().equalsIgnoreCase("Vendor")
					|| user.getRole().getRole().getRoleName().equalsIgnoreCase("REGENGINEER")) {

			  maintainance.setRunningStatus("Uploading Quotation");
						// Sr-Notification email Trigger start--------------->
						List<Object[]> amcInventoryList =maintainanceRepository.UploadingQuotationDocument(maintainance.getMaintainanceId());
				for (Object[] rows : amcInventoryList) {
							
							/*
							 * srNotificationEmail_id:- System.out.println(rows[0]);
							 * activity_Name:-  System.out.println(rows[1]);
							 * service_request_id:- System.out.println(rows[2]);
							 * TOemail:-  System.out.println(rows[3]);
							 * CCEmail:- System.out.println(rows[4]);
							 */
					Maintainance amcInventories = maintainanceService.getByMaintainanceId(((BigInteger) rows[2]).longValue());
							String mailSubjects = "";			
							String emailCcValue=(String) rows[4];
							List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str1->!str1.isEmpty()).collect(Collectors.toList());			
				            System.out.println(emailCc);
							String emailTOValue=(String) rows[3];
							List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str1->!str1.isEmpty()).collect(Collectors.toList());
							System.out.println(emailTo);
					 

						  Mail mail = maintainanceService.templeteMail(amcInventories);
						  Long id= amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
						  mailSubjects = "Nu-Nxtwav  "+ maintainance.getServiceRequestCode()+"– Uploading Quotation";
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
							

				// Sr-Notification Email TriggerEnd    <-------------------------									
										System.out.println("Uploading Quotation is completed");


				}

			


		  
		  
		  		  
		 return new ResponseEntity<>(map,HttpStatus.OK);
	    	
	    	}
	 
	 
	 @GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
	   
		 SRFileUploadMaintainance sRFileUpload= srFileUploadMaintainanceRepo.getOne(id);
		 
		 Resource resource = this.imagePath.getImageByFileName("quationMaintainance", sRFileUpload.getSrFileploadPath());
	    	
	    if(sRFileUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
	    }
	    else if(sRFileUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/jpeg"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else if(sRFileUpload.getEndingPath().equalsIgnoreCase(".png")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/png"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else{
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("application/pdf"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	 
	 }
	  
	  
	 @GetMapping({"/getByServiceRequestId/{id}"})
	  public ResponseEntity<List<SRFileUploadMaintainanceVo>> getByServiceRequestId(@PathVariable("id") Long id) {
	     List<SRFileUploadMaintainanceVo> SRFileUploadVOs=new ArrayList<>();
		 List<SRFileUploadMaintainance> sRFileUploads= srFileUploadMaintainanceRepo.getSrFileByMaintainanceId(id);
		 sRFileUploads.forEach(srf->{
		     SRFileUploadMaintainanceVo SRFileUploadVO=new SRFileUploadMaintainanceVo();
		     BeanUtils.copyProperties(srf, SRFileUploadVO);
		     
		     SRFileUploadVO.setMaintainanceId(srf.getMaintainance().getMaintainanceId());
		     SRFileUploadVOs.add(SRFileUploadVO);
			 });
		 
		 return new ResponseEntity<>(SRFileUploadVOs,HttpStatus.OK);
	 }
	  
	 @PutMapping({"/updateForStatus"})
	  public ResponseEntity<?> updateSStatusForSrfile(@RequestBody SRFileUploadMaintainanceVo sRFileUploadVO,Principal principal) {
		User user=userRepo.findByUsername(principal.getName());
		QuotationForMaintainance quotationApprovalMetrix=quotationForMaintainanaceRepo.findByWebRoleWebRoleId(user.getRole().getWebRoleId());
		
		if((Double.parseDouble(sRFileUploadVO.getSpareCharges())>quotationApprovalMetrix.getQuotationApprovalFrom()
		   || (Double.parseDouble(sRFileUploadVO.getSpareCharges())==0 && quotationApprovalMetrix.getQuotationApprovalFrom()==0))
				                                  &&
		   Double.parseDouble(sRFileUploadVO.getSpareCharges())<=quotationApprovalMetrix.getQuotationApprovalTo())
		{
			
			 SRFileUploadMaintainance sRFileUpload= srFileUploadMaintainanceRepo.findById(sRFileUploadVO.getSRFileUploadId())
					 .orElseThrow(()-> new  ResourceNotFoundException("The find id {} not found"+sRFileUploadVO.getSRFileUploadId()));
			 	 sRFileUpload.setStatus(sRFileUploadVO.getStatus());
			 	 sRFileUpload=srFileUploadMaintainanceRepo.save(sRFileUpload);
			 	 BeanUtils.copyProperties(sRFileUpload, sRFileUploadVO);
			 	return new ResponseEntity<>(sRFileUploadVO,HttpStatus.OK);
			
		}
		else {
			
			return new ResponseEntity<>("spare charges not in between QuotationApproval",HttpStatus.BAD_REQUEST);
		}
		 
		
		 }
	  
	  
	  
  }
 


