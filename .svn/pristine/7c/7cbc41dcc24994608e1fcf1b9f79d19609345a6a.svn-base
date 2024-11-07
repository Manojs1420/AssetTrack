
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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.irgs.application.util.ImagePath;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.domain.ServiceRequestUpload;
import com.titan.irgs.serviceRequest.model.ServiceRequestUploadVO;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestUploadRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

  @RestController
  @RequestMapping( {"/serviceRequestUpload"}) 
  public class ServiceRequestUploadController {
	  
	  @Value("${mail.status}")
	 private Boolean mailStatus;
	  
	 @Autowired
	 ImagePath imagePath;
	  
	  @Autowired
	  ServiceRequestUploadRepository serviceRequestUploadRepositoryUpload;
	  
	  @Autowired
	  IServiceRequestService iServiceRequestService;
	  
	  @Autowired
	  UserRepo userRepo;
	  
	  @Autowired
	  EmailServiceImpl emailServiceImpl;
	  
	  @Autowired
	  EmailServiceImpl1 emailServiceImpl1;
	 
	  @Autowired
	  ClusterRepository clusterRepository;
	  
	  @Autowired
	  VendorRepository vendorRepository;
	  
		@Autowired
		ServiceRequestRepository serviceRequestRepository;
	  
	 @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	 @PostMapping({"/save"})
	    public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file, 
	    		@RequestParam("srfileInfo") String srfileInfo,Principal principal){
		 
		 	ObjectMapper mapper=new ObjectMapper();
		 	Map<String,String> map=new HashMap<>();
			User user=userRepo.findByUsername(principal.getName());

		 	ServiceRequestUploadVO serviceRequestUploadVO=new ServiceRequestUploadVO(); 
		  try {
			  serviceRequestUploadVO=mapper.readValue(srfileInfo,ServiceRequestUploadVO.class); 
		  
		   }
		  catch (Exception e)
		  { 
			  System.out.println(e.getLocalizedMessage()); 
		  }
			
		  String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
				  file.getOriginalFilename().length());

		    if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
		      return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 
		    
		    }

		    String fileName = this.imagePath.saveImageOnDesk(file, "serviceRequestFile");
		    ServiceRequest serviceRequest=iServiceRequestService.getById(serviceRequestUploadVO.getServiceRequestId());
		  if (!fileName.isEmpty()) {
			  ServiceRequestUpload serviceRequestUpload=new ServiceRequestUpload();
		      BeanUtils.copyProperties(serviceRequestUploadVO, serviceRequestUpload);
		      serviceRequestUpload.setEndingPath(str);
		      serviceRequestUpload.setSrFileploadPath(fileName);
		      serviceRequestUpload.setServiceRequest(serviceRequest);
		      serviceRequestUpload= serviceRequestUploadRepositoryUpload.save(serviceRequestUpload);
		      
		      ServiceRequest serviceRequest1=iServiceRequestService.getById(serviceRequestUpload.getServiceRequest().getServiceRequestId());
		      
		      if(serviceRequestUpload.getSrFileploadPath()!=null) {
		      
		    	  serviceRequest1.setServiceUpload("true");
		    	  serviceRequest1.setUpdatedOn(new Date());
		      iServiceRequestService.update(serviceRequest1);
		      }else{
		    	  serviceRequest1.setServiceUpload("false");
		    	  serviceRequest1.setUpdatedOn(new Date());
		      iServiceRequestService.update(serviceRequest1);
		      }
		      map.put("msg", "created successfully");
		  
		  }
		 
		  // sending mail...

		  if(user.getRole().getRole().getRoleName().equalsIgnoreCase("Vendor") || 
				  user.getRole().getRole().getRoleName().equalsIgnoreCase("REGENGINEER")) { 
		  
			  
				List<Object[]> serviceRequestList2 =serviceRequestRepository.UploadingServiceDocument(serviceRequest.getServiceRequestId());
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
							  mailSubjects = "Service report";
							  mail.setMailTo(emailTO);
							  mail.setMailCC(emailCC); 
							  mail.setMailSubject(mailSubjects);
							  if (mailStatus) { try {
								  emailServiceImpl1.sendMultiPartEmail(mail);
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} } }

							
								
								System.out.println("Service report is completed");

				
			  
		  //taking store user
			/*
			 * User
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
			 * //adding List<String> mailTo=users.stream()
			 * .filter(userMail->userMail.getRole().getRole().getRoleName().contains(
			 * "REGENGINEER")) .map(email->email.getEmail()) .collect(Collectors.toList());
			 */		  
			/*
			 * List<String> cc=users.stream()
			 * .filter(userMail->userMail.getRole().getRole().getRoleName().
			 * contains("REGIONAL BUSINESS HEAD")) .map(email->email.getEmail())
			 * .collect(Collectors.toList());
			 */ 
		 //adding vendor email..
			/*
			 * if(serviceRequest.getServiceVendorId()!=null)
			 * cc.add(vendorRepository.findByVendorId(serviceRequest.getServiceVendorId()).
			 * getServiceEmailId1());
			 */ 
//		  try {
	//			if (mailTo != null) {
		//			Mail mail = iServiceRequestService.templeteMail(serviceRequest);
			//		mail.setMailSubject("Service report");
				//	mail.setMailTo(mailTo);
//					mail.setMailCC(cc);
	//	  			
	//				if(mailStatus)
		//  				emailServiceImpl.sendMultiPartEmail(mail);
			//	}

	//		} catch (Exception e) {
	//			System.out.println(e.getLocalizedMessage());

		//		return new ResponseEntity<>(map, HttpStatus.OK);

//			}
		  
		  }
			
			return new ResponseEntity<>(map,HttpStatus.OK);
	    	
	    	}
	 
	 
	 @GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
	   
		 ServiceRequestUpload serviceRequestUpload= serviceRequestUploadRepositoryUpload.getOne(id);
		 
		 Resource resource = this.imagePath.getImageByFileName("serviceRequestFile", serviceRequestUpload.getSrFileploadPath());
	    	
	    if(serviceRequestUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
	    }
	    else if(serviceRequestUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/jpeg"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else if(serviceRequestUpload.getEndingPath().equalsIgnoreCase(".png")) {
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
	  public ResponseEntity<List<ServiceRequestUploadVO>> getByServiceRequestId(@PathVariable("id") Long id) {
	     List<ServiceRequestUploadVO> SRFileUploadVOs=new ArrayList<>();
		 List<ServiceRequestUpload> serviceRequestUploads= serviceRequestUploadRepositoryUpload.getSrUploadByServiceRequestId(id);
		 serviceRequestUploads.forEach(srf->{
			 ServiceRequestUploadVO serviceRequestUploadVO=new ServiceRequestUploadVO();
		     BeanUtils.copyProperties(srf, serviceRequestUploadVO);
		     
		     serviceRequestUploadVO.setServiceRequestId(srf.getServiceRequest().getServiceRequestId());
		     SRFileUploadVOs.add(serviceRequestUploadVO);
			 });
		 
		 return new ResponseEntity<>(SRFileUploadVOs,HttpStatus.OK);
	 }
	 @DeleteMapping("/delete/{id}")
		public void deleteUploadById(@PathVariable(value = "id") Long id) {

		 ServiceRequestUpload serviceRequestUpload=serviceRequestUploadRepositoryUpload.findById(id).get();
		 serviceRequestUploadRepositoryUpload.deleteById(id);
		 
		 ServiceRequest serviceRequest=serviceRequestRepository.findById(serviceRequestUpload.getServiceRequest().getServiceRequestId()).get();
		 
		 List<ServiceRequestUpload> serviceRequestUploads=serviceRequestUploadRepositoryUpload.findByServiceRequest(serviceRequest);
		if(serviceRequestUploads.size()==0) {
			serviceRequest.setServiceUpload("false");
			serviceRequest.setUpdatedOn(new Date());
			iServiceRequestService.update(serviceRequest);
		}
	 }
	  
	  
  }
 