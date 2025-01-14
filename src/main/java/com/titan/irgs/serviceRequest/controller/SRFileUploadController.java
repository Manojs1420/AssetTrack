
package com.titan.irgs.serviceRequest.controller;
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
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.serviceRequest.domain.QuotationApprovalMetrix;
import com.titan.irgs.serviceRequest.domain.SRFileUpload;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.model.SRFileUploadVO;
import com.titan.irgs.serviceRequest.repository.QuotationApprovalMetrixRepo;
import com.titan.irgs.serviceRequest.repository.SRFileUploadRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

  @RestController
  @RequestMapping(value = WebConstantUrl.SR_FILE_UPLOAD_BASE_URL) 
  public class SRFileUploadController {
	  
	  @Value("${mail.status}")
	  private Boolean mailStatus;
	  
	  
	private static final Logger logger = LoggerFactory.getLogger(SRFileUploadController.class);

	  
	 @Autowired
	 ImagePath imagePath;
	  
	  @Autowired
	  SRFileUploadRepository sRFileUploadRepository;
	  
	  @Autowired
	  IServiceRequestService iServiceRequestService;
	  
	  @Autowired
	  UserRepo userRepo;
	  
	  @Autowired
	  ClusterRepository clusterRepository;
	  
	  @Autowired
	  EmailServiceImpl emailServiceImpl;
	  
	  @Autowired
	  QuotationApprovalMetrixRepo quotationApprovalMetrixRepo;
	
	  @Autowired
		ServiceRequestRepository serviceRequestRepository;	  
	  
	  
	 @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	 @PostMapping(WebConstantUrl.quatationUpload)
	   
	 public ResponseEntity<?> upload( @RequestPart(required = true)@RequestParam(value = "file") MultipartFile file,
	    		@RequestParam(value = "srfileInfo") String srfileInfo,Principal principal){
		

		User user=userRepo.findByUsername(principal.getName());
		 
		 
		 ObjectMapper mapper=new ObjectMapper();
		 Map<String,String> map=new HashMap<>();
		 SRFileUploadVO sRFileUploadVO=new SRFileUploadVO(); 
		  try 
		  {
			  sRFileUploadVO=mapper.readValue(srfileInfo,SRFileUploadVO.class); 
		  
		  }
		  catch (Exception e)
		  { 
			  System.out.println(e.getLocalizedMessage()); 
			  
		  }
			
		  String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

		    if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
		      return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 
		    
		    }

		    String fileName = this.imagePath.saveImageOnDesk(file, "quation");
		     ServiceRequest serviceRequest=iServiceRequestService.getById(sRFileUploadVO.getServiceRequestId());
		     serviceRequest.setRunningStatus("Quatation uploaded");

		  if (!fileName.isEmpty()) {
			  SRFileUpload sRFileUpload=new SRFileUpload();
		      BeanUtils.copyProperties(sRFileUploadVO, sRFileUpload);
		      sRFileUpload.setEndingPath(str);
		      sRFileUpload.setSrFileploadPath(fileName);
		      sRFileUpload.setServiceRequest(serviceRequest);
		      sRFileUpload= sRFileUploadRepository.save(sRFileUpload);
		      map.put("msg", "created successfully");
		  
		  }
		 
		  
		  
		  
		  //sending mail....
		  
		  if(user.getRole().getRole().getRoleName().equalsIgnoreCase("Vendor") || 
				  user.getRole().getRole().getRoleName().equalsIgnoreCase("REGENGINEER")) {
	
				List<Object[]> serviceRequestList2 =serviceRequestRepository.UploadingQuotation(serviceRequest.getServiceRequestId());
				for (Object[] rows : serviceRequestList2) {
					ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
							String mailSubjects = "";			
							String emailCcValue=(String) rows[4];
							List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str1->!str1.isEmpty()).collect(Collectors.toList());			
				            System.out.println(emailCC);
							String emailTOValue=(String) rows[3];
							List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str1->!str1.isEmpty()).collect(Collectors.toList());
							System.out.println(emailTO);
							  Mail mail = iServiceRequestService.templeteMail(serviceRequests);
							  mailSubjects = "Uploading Quotation";
							  mail.setMailTo(emailTO);
							  mail.setMailCC(emailCC); 
							  mail.setMailSubject(mailSubjects);
							  if (mailStatus) { try {
								  emailServiceImpl.sendMultiPartEmail(mail);
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} } }

							
								
								System.out.println("Uploading Quotation is completed");


			/*
			 * //taking store user User
			 * storeUser=userRepo.findByUsername(serviceRequest.getStore().getStoreCode());
			 * 
			 * 
			 * String
			 * busssinessVerticalName=storeUser.getRole().getWebMaster().getWebMasterName();
			 * String
			 * region=clusterRepository.getClustersByUsingUserId(storeUser.getId()).get(0).
			 * getRegion().getRegionName();
			 * 
			 * List<User> users=userRepo.findByUsersUsingBussinessVerticalAndRegion(
			 * busssinessVerticalName,region);
			 * 
			 * 
			 * 
			 * List<String> mailTo=users.stream()
			 * .filter(userMail->userMail.getRole().getRole().getRoleName().
			 * contains("REGIONAL BUSINESS HEAD")) .map(email->email.getEmail())
			 * .collect(Collectors.toList());
			 * 
			 * List<String> cc=users.stream()
			 * .filter(userMail->userMail.getRole().getRole().getRoleName().
			 * contains("Area business Manager") ||
			 * userMail.getRole().getRole().getRoleName().contains("IRSG SOURCING"))
			 * .map(email->email.getEmail()) .collect(Collectors.toList());
			 * 
			 * 
			 * try { if (mailTo != null) { Mail mail =
			 * iServiceRequestService.templeteMail(serviceRequest); mail.setMailTo(mailTo);
			 * mail.setMailCC(cc); if(mailStatus) emailServiceImpl.sendMultiPartEmail(mail);
			 * }
			 * 
			 * } catch (Exception e) { System.out.println(e.getLocalizedMessage());
			 * 
			 * return new ResponseEntity<>(map, HttpStatus.OK);
			 * 
			 * }
			 * 
			 */		
			  }
		  
		 return new ResponseEntity<>(map,HttpStatus.OK);
	    	
	    	}
	 
	 
	 @GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
	   
		 SRFileUpload sRFileUpload= sRFileUploadRepository.getOne(id);
		 
		 Resource resource = this.imagePath.getImageByFileName("quation", sRFileUpload.getSrFileploadPath());
	    	
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
	  public ResponseEntity<List<SRFileUploadVO>> getByServiceRequestId(@PathVariable("id") Long id) {
	     List<SRFileUploadVO> SRFileUploadVOs=new ArrayList<>();
		 List<SRFileUpload> sRFileUploads= sRFileUploadRepository.getSrFileByServiceRequestId(id);
		 sRFileUploads.forEach(srf->{
		     SRFileUploadVO SRFileUploadVO=new SRFileUploadVO();
		     BeanUtils.copyProperties(srf, SRFileUploadVO);
		     
		     SRFileUploadVO.setServiceRequestId(srf.getServiceRequest().getServiceRequestId());
		     SRFileUploadVOs.add(SRFileUploadVO);
			 });
		 
		 return new ResponseEntity<>(SRFileUploadVOs,HttpStatus.OK);
	 }
	  
	 @PutMapping({"/updateForStatus"})
	  public ResponseEntity<?> updateSStatusForSrfile(@RequestBody SRFileUploadVO sRFileUploadVO,Principal principal) {
		User user=userRepo.findByUsername(principal.getName());
		QuotationApprovalMetrix quotationApprovalMetrix=quotationApprovalMetrixRepo.findByWebRoleWebRoleId(user.getRole().getWebRoleId());
		
		if((Double.parseDouble(sRFileUploadVO.getSpareCharges())>quotationApprovalMetrix.getQuotationApprovalFrom()
		   || (Double.parseDouble(sRFileUploadVO.getSpareCharges())==0 && quotationApprovalMetrix.getQuotationApprovalFrom()==0))
				                                  &&
		   Double.parseDouble(sRFileUploadVO.getSpareCharges())<=quotationApprovalMetrix.getQuotationApprovalTo())
		{
			
			 SRFileUpload sRFileUpload= sRFileUploadRepository.findById(sRFileUploadVO.getSRFileUploadId())
					 .orElseThrow(()-> new  ResourceNotFoundException("The find id {} not found"+sRFileUploadVO.getSRFileUploadId()));
			 	 sRFileUpload.setStatus(sRFileUploadVO.getStatus());
			 	 sRFileUpload=sRFileUploadRepository.save(sRFileUpload);
			 	 BeanUtils.copyProperties(sRFileUpload, sRFileUploadVO);
			 	return new ResponseEntity<>(sRFileUploadVO,HttpStatus.OK);
			
		}
		else {
			
			return new ResponseEntity<>("spare charges not in between QuotationApproval",HttpStatus.BAD_REQUEST);
		}
		 
		
		 }
	  
	  
	  
  }
 