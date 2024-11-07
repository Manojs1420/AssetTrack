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
import javax.servlet.http.HttpServletRequest;

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
import com.titan.irgs.serviceRequest.domain.ServiceRequestInvoiceDomain;
import com.titan.irgs.serviceRequest.model.ServiceRequestInvoiceVo;
import com.titan.irgs.serviceRequest.repository.ServiceRequestInvoiceRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping({"/serviceRequestInvoice"})
public class ServiceRequestInvoiceController {
	
	 @Value("${mail.status}")
	 private Boolean mailStatus;
	  
	  @Autowired
	  ImagePath imagePath;
	  
	  @Autowired
	  ServiceRequestInvoiceRepository serviceRequestInvoiceRepository;
	  
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
	  
	 @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	 @PostMapping({"/save"})
	    public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file,
	    		@RequestParam("srfileInfo") String srfileInfo,HttpServletRequest request,Principal principal){
		 
		 ObjectMapper mapper=new ObjectMapper();
		 Map<String,String> map=new HashMap<>();
		 ServiceRequestInvoiceVo serviceRequestInvoiceVo=new ServiceRequestInvoiceVo(); 
		User user=userRepo.findByUsername(principal.getName());

		  try 
		  {
			  serviceRequestInvoiceVo=mapper.readValue(srfileInfo,ServiceRequestInvoiceVo.class); 
		  
		  }
		  catch (Exception e)
		  { 
			  System.out.println(e.getLocalizedMessage()); 
		  }
			
		  String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

		    if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
		      return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 
		    
		    }

		    String fileName = this.imagePath.saveImageOnDesk(file, "serviceRequestInvoice");
		    ServiceRequest serviceRequest=iServiceRequestService.getById(serviceRequestInvoiceVo.getServiceRequestId());
		    serviceRequest.setRunningStatus("invoice uploaded");
		    serviceRequestRepository.save(serviceRequest);

		  if (!fileName.isEmpty()) {
			  ServiceRequestInvoiceDomain serviceRequestInvoiceDomain=new ServiceRequestInvoiceDomain();
		      
		      BeanUtils.copyProperties(serviceRequestInvoiceVo, serviceRequestInvoiceDomain);
		      serviceRequestInvoiceDomain.setEndingPath(str);
		      serviceRequestInvoiceDomain.setInvoicePath(fileName);
		      serviceRequestInvoiceDomain.setServiceRequest(serviceRequest);
		      serviceRequestInvoiceDomain= serviceRequestInvoiceRepository.save(serviceRequestInvoiceDomain);
		      map.put("msg", "created successfully");
		  
		  }
		  
		
		  
		  
		  
		  // sending mail...

		  if(user.getRole().getRole().getRoleName().equalsIgnoreCase("Vendor") || 
				  user.getRole().getRole().getRoleName().equalsIgnoreCase("REGENGINEER")) 
		  { 
				List<Object[]> serviceRequestList2 =serviceRequestRepository.UploadingtheInvoice(serviceRequest.getServiceRequestId());
				for (Object[] rows : serviceRequestList2) {
					ServiceRequest serviceRequests = iServiceRequestService.getById(((BigInteger) rows[2]).longValue());
							String mailSubjects = "";			
							String emailCcValue=(String) rows[4];
							List<String> emailCC = Arrays.asList(emailCcValue.split(",")).stream().filter(str1->!str.isEmpty()).collect(Collectors.toList());			
				            System.out.println(emailCC);
							String emailTOValue=(String) rows[3];
							List<String> emailTO = Arrays.asList(emailTOValue.split(",")).stream().filter(str1->!str.isEmpty()).collect(Collectors.toList());
							System.out.println(emailTO);
							  Mail mail = iServiceRequestService.templeteMail(serviceRequests);
							  Long id= serviceRequests.getAssetWebMaster().getWebMasterId();
							  mailSubjects = serviceRequestInvoiceVo.getInvoiceType();
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

							
								
								System.out.println("Upload Invoice is completed");



		  
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
			 * //adding List<String> mailTo=users.stream()
			 * .filter(f->f.getRole().getRole().getRoleName().contains("REGENGINEER"))
			 * .map(email->email.getEmail()) .collect(Collectors.toList());
			 * 
			 * List<String> cc=users.stream()
			 * .filter(f->f.getRole().getRole().getRoleName().
			 * contains("REGIONAL BUSINESS HEAD")) .map(email->email.getEmail())
			 * .collect(Collectors.toList());
			 * 
			 * //adding vendor email.. if(serviceRequest.getServiceVendorId()!=null)
			 * cc.add(vendorRepository.findByVendorId(serviceRequest.getServiceVendorId()).
			 * getServiceEmailId1());
			 * 
			 * try {
			 * 
			 * if (mailTo != null) { Mail mail =
			 * iServiceRequestService.templeteMail(serviceRequest);
			 * mail.setMailSubject(serviceRequestInvoiceVo.getInvoiceType());
			 * mail.setMailTo(mailTo); mail.setMailCC(cc);
			 * 
			 * if(mailStatus) emailServiceImpl.sendMultiPartEmail(mail); }
			 * 
			 * } catch (Exception e) { System.out.println(e.getLocalizedMessage());
			 * 
			 * return new ResponseEntity<>(map, HttpStatus.OK);
			 * 
			 * }
			 */
		  
		  }
		
		 
			
			return new ResponseEntity<>(map,HttpStatus.OK);
	    	
	    	}
	 
	 
	 @GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
	   
		 ServiceRequestInvoiceDomain serviceRequestInvoiceDomain= serviceRequestInvoiceRepository.getOne(id);
		 
		 Resource resource = this.imagePath.getImageByFileName("serviceRequestInvoice", serviceRequestInvoiceDomain.getInvoicePath());
	    	
	    if(serviceRequestInvoiceDomain.getEndingPath().equalsIgnoreCase(".jpg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
	    }
	    else if(serviceRequestInvoiceDomain.getEndingPath().equalsIgnoreCase(".jpeg")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/jpeg"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else if(serviceRequestInvoiceDomain.getEndingPath().equalsIgnoreCase(".png")) {
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
	  public ResponseEntity<List<ServiceRequestInvoiceVo>> getByServiceRequestId(@PathVariable("id") Long id) {
	     List<ServiceRequestInvoiceVo> serviceRequestInvoiceVos=new ArrayList<>();
		 List<ServiceRequestInvoiceDomain> serviceRequestUploads= serviceRequestInvoiceRepository.getSrInvoiceByServiceRequestId(id);
		 serviceRequestUploads.forEach(srf->{
			 ServiceRequestInvoiceVo serviceRequestInvoiceVo=new ServiceRequestInvoiceVo();
		     BeanUtils.copyProperties(srf, serviceRequestInvoiceVo);
		     serviceRequestInvoiceVo.setServiceRequestId(srf.getServiceRequest().getServiceRequestId());
		     serviceRequestInvoiceVos.add(serviceRequestInvoiceVo);
			 });
		 
		 return new ResponseEntity<>(serviceRequestInvoiceVos,HttpStatus.OK);
	 }
	  
	  
	  
 
	
	

}
