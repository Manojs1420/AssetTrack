package com.titan.irgs.serviceRequest.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
import com.titan.irgs.application.util.Status;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.domain.ServiceRequestPoUploadDomain;
import com.titan.irgs.serviceRequest.model.ServiceRequestPoUploadVo;
import com.titan.irgs.serviceRequest.repository.ServiceRequestPoUploadRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping(WebConstantUrl.SERVICE_REQUEST_PO_UPLOAD)
public class ServiceRequestPoUploadController {

   
private static final Logger logger = LoggerFactory.getLogger(ServiceRequestPoUploadController.class);

@Value("${mail.status}")
private Boolean mailStatus;
 
 @Autowired
 ImagePath imagePath;
 
 @Autowired
 ServiceRequestPoUploadRepository ServiceRequestPoUploadRepository;
 
 @Autowired
 IServiceRequestService iServiceRequestService;
 
 @Autowired
 UserRepo userRepo;
 
 @Autowired
 ClusterRepository clusterRepository;
 
 @Autowired
 EmailServiceImpl emailServiceImpl;
	
 @Autowired
 ServiceRequestRepository serviceRequestRepository;
 
 @Autowired
 VendorRepository vendorRepository;
 
 private final static String REGENGINEER="REGENGINEER";
 
 private final static String CENTRALHEAD="CENTRALHEAD";
	
 private final static String SOURCING="SOURCING";

 private final static String superAdmin = "superadmin";
 
 private final static String HOD="HOD";
 private final static String REGIONHEAD="REGIONHEAD";
 
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
@PostMapping({"/save"})
   public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file,
   		@RequestParam("srfileInfo") String srfileInfo,HttpServletRequest request,Principal principal){
	 
	 ObjectMapper mapper=new ObjectMapper();
	 Map<String,String> map=new HashMap<>();
	 ServiceRequestPoUploadVo serviceRequestPoUploadVo=new ServiceRequestPoUploadVo(); 
	User user=userRepo.findByUsername(principal.getName());
	
		/*
		 * if(!user.getRole().getRole().getRoleName().contains(SOURCING) ||
		 * !user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) {
		 * 
		 * throw new AccessDeniedException("Unauthorized.."); }
		 */
	

	  try 
	  {
		  serviceRequestPoUploadVo=mapper.readValue(srfileInfo,ServiceRequestPoUploadVo.class); 
	  
	  }
	  catch (Exception e)
	  { 
		  System.out.println(e.getLocalizedMessage()); 
	  }
	  

		ServiceRequest serviceRequest=iServiceRequestService.getById(serviceRequestPoUploadVo.getServiceRequestId());
		
		  if(serviceRequest.getServiceVendorId()==null) {
			  throw new ResourceNotFoundException("vendor is not assigned to service request");
			  
			  
		  }
		
	  String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

	    if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
	      return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 
	    
	    }

	    String fileName = this.imagePath.saveImageOnDesk(file, "serviceRequestPoUpload");
	    
	
	
	    
	    
	    
	    
	    
	    
	    if (!fileName.isEmpty()) {
		  ServiceRequestPoUploadDomain serviceRequestPoUploadDomain=new ServiceRequestPoUploadDomain();
	      
	      BeanUtils.copyProperties(serviceRequestPoUploadVo, serviceRequestPoUploadDomain);
	      serviceRequestPoUploadDomain.setEndingPath(str);
	      serviceRequestPoUploadDomain.setCreatedDate(new Date());
	      serviceRequestPoUploadDomain.setServiceRequestPoUServiceRequestPoUploadPath(fileName);
	      serviceRequestPoUploadDomain.setServiceRequest(serviceRequest);
	      serviceRequestPoUploadDomain.setStatus(Status.ACTIVE.toString());
	      serviceRequestPoUploadDomain= ServiceRequestPoUploadRepository.save(serviceRequestPoUploadDomain);
	      map.put("msg", "created successfully");
	  
	  }
	  
	    serviceRequest.setRunningStatus("PO file uploaded");
	    //serviceRequestRepository.save(serviceRequest);

	  
	  
	  
	  // sending mail...
		List<Object[]> serviceRequestList2 =serviceRequestRepository.UploadingPO(serviceRequest.getServiceRequestId());
		for (Object[] rows : serviceRequestList2) {
			ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
					String mailSubjects = "";			
					String emailCcValue=(String) rows[4];
					List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(string->!string.isEmpty()).collect(Collectors.toList());			
		            System.out.println(emailCC);
					String emailTOValue=(String) rows[3];
					List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(string->!string.isEmpty()).collect(Collectors.toList());
					System.out.println(emailTO);
					  Mail mail = iServiceRequestService.templeteMail(serviceRequests);
					  Long id= serviceRequests.getAssetWebMaster().getWebMasterId();
					  mailSubjects = "PoUpload";
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
						return new ResponseEntity<>(map, HttpStatus.OK);
					} } }

					
						
						System.out.println("Po Upload is completed");





		/*
		 * //taking store user User
		 * storeUser=userRepo.findByUsername(serviceRequest.getStore().getStoreCode());
		 * 
		 * 
		 * String
		 * busssinessVerticalName=storeUser.getRole().getWebMaster().getWebMasterName();
		 * //String
		 * region=clusterRepository.getClustersByUsingUserId(storeUser.getId()).get(0).
		 * getRegion().getRegionName();
		 * 
		 * String region=serviceRequest.getStore().getRegion().getRegionName();
		 * List<User> users=userRepo.findByUsersUsingBussinessVerticalAndRegion(
		 * busssinessVerticalName,region);
		 * 
		 * 
		 * //adding List<String>
		 * mailTo=Arrays.asList(vendorRepository.findByVendorId(serviceRequest.
		 * getServiceVendorId()).getServiceEmailId1());
		 * 
		 * List<String> cc=users.stream()
		 * .filter(f->f.getRole().getRole().getRoleName().contains(REGENGINEER)
		 * ||f.getRole().getRole().getRoleName().contains(CENTRALHEAD)
		 * ||f.getRole().getRole().getRoleName().contains(REGIONHEAD)
		 * ||f.getRole().getRole().getRoleName().contains(HOD))
		 * .map(email->email.getEmail()) .collect(Collectors.toList());
		 * cc.add(user.getEmail()==null?"":user.getEmail());
		 * 
		 * 
		 * try {
		 * 
		 * if (mailTo != null) { Mail mail =
		 * iServiceRequestService.templeteMail(serviceRequest);
		 * mail.setMailSubject("PoUpload"); mail.setMailTo(mailTo); mail.setMailCC(cc);
		 * 
		 * if(mailStatus) emailServiceImpl.sendMultiPartEmail(mail); }
		 * 
		 * } catch (Exception e) { System.out.println(e.getLocalizedMessage());
		 * 
		 * return new ResponseEntity<>(map, HttpStatus.OK);
		 * 
		 * }
		 */	  
	  
	
	 
		
		return new ResponseEntity<>(map,HttpStatus.OK);
   	
   	}


@GetMapping({"/download/{id}"})
 public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
  
	ServiceRequestPoUploadDomain serviceRequestPoUploadDomain= ServiceRequestPoUploadRepository.getOne(id);
	 
	 Resource resource = this.imagePath.getImageByFileName("serviceRequestPoUpload", serviceRequestPoUploadDomain.getServiceRequestPoUServiceRequestPoUploadPath());
   	
   if(serviceRequestPoUploadDomain.getEndingPath().equalsIgnoreCase(".jpg")) {
   return ResponseEntity.ok()
     .contentType(MediaType.parseMediaType("image/jpg"))
     
     .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
   }
   else if(serviceRequestPoUploadDomain.getEndingPath().equalsIgnoreCase(".jpeg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpeg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
	    }
   else if(serviceRequestPoUploadDomain.getEndingPath().equalsIgnoreCase(".png")) {
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
 public ResponseEntity<List<ServiceRequestPoUploadVo>> getByServiceRequestId(@PathVariable("id") Long id) {
    List<ServiceRequestPoUploadVo> serviceRequestPoUploadVos=new ArrayList<>();
	 List<ServiceRequestPoUploadDomain> serviceRequestPoUploadDomains= ServiceRequestPoUploadRepository.getServiceRequestPoByServiceRequestId(id);
	 serviceRequestPoUploadDomains.forEach(serviceRequestPoUploadDomain->{
		 ServiceRequestPoUploadVo serviceRequestPoUploadVo=new ServiceRequestPoUploadVo();
	     BeanUtils.copyProperties(serviceRequestPoUploadDomain, serviceRequestPoUploadVo);
	     serviceRequestPoUploadVo.setServiceRequestId(serviceRequestPoUploadDomain.getServiceRequest().getServiceRequestId());
	     serviceRequestPoUploadVo.setStatus(Status.valueOf(serviceRequestPoUploadDomain.getStatus()));

	     serviceRequestPoUploadVos.add(serviceRequestPoUploadVo);
		 });
	 
	 return new ResponseEntity<>(serviceRequestPoUploadVos,HttpStatus.OK);
}



@PutMapping({"/updateForStatus"})
public ResponseEntity<?> updateSStatusForSrfile(@RequestBody ServiceRequestPoUploadVo serviceRequestPoUploadVo,Principal principal) {
	
	 ServiceRequestPoUploadDomain serviceRequestPoUploadDomain= ServiceRequestPoUploadRepository.findById(serviceRequestPoUploadVo.getServiceRequestPoUServiceRequestPoUploadId())
			 .orElseThrow(()-> new  ResourceNotFoundException("The find id {} not found"+serviceRequestPoUploadVo.getServiceRequestPoUServiceRequestPoUploadId()));
	    serviceRequestPoUploadDomain.setStatus(serviceRequestPoUploadVo.getStatus().toString());
	    serviceRequestPoUploadDomain.setApproved(serviceRequestPoUploadVo.getApproved());
	 	serviceRequestPoUploadDomain=ServiceRequestPoUploadRepository.save(serviceRequestPoUploadDomain);
	 	List<Object[]> serviceRequestList2 =serviceRequestRepository.POStatus(serviceRequestPoUploadDomain.getServiceRequest().getServiceRequestId());
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
					  mailSubjects = "PO Status";
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

					
						
						System.out.println("PO Status is completed");

 	 

	 	BeanUtils.copyProperties(serviceRequestPoUploadDomain, serviceRequestPoUploadVo);
	 return new ResponseEntity<>(serviceRequestPoUploadVo,HttpStatus.OK);
	 }




















}
