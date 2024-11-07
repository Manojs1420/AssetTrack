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
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.domain.MaintainanceUpload;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.repository.MaintainanceUploadRepository;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.inventory.vo.MaintainanceUploadVo;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

  @RestController
  @RequestMapping( {"/maintainanceUpload"}) 
  public class MaintainanceUploadController {
	  
	  @Value("${mail.status}")
	 private Boolean mailStatus;
		@Autowired
		MaintainanceEmailImpl emailServiceImpl;
		@Autowired
		MaintainanceEmailImpl1 emailServiceImpl1;
	 @Autowired
	 ImagePath imagePath;
	  
	  @Autowired
	  MaintainanceUploadRepository maintainanceUploadRepository;
	  
	  @Autowired
	  MaintainanceService maintainanceService;
	  
	  @Autowired
	  UserRepo userRepo;
	  @Autowired
	  MaintainanceRepository maintainanceRepository;
	
	  
		@Autowired
		ServiceRequestRepository serviceRequestRepository;
	  
	 @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	 @PostMapping({"/save"})
	    public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file, 
	    		@RequestParam("srfileInfo") String srfileInfo,Principal principal){
		 
		 	ObjectMapper mapper=new ObjectMapper();
		 	Map<String,String> map=new HashMap<>();
			User user=userRepo.findByUsername(principal.getName());

		 	MaintainanceUploadVo maintainanceUploadVo=new MaintainanceUploadVo(); 
		  try {
			  maintainanceUploadVo=mapper.readValue(srfileInfo,MaintainanceUploadVo.class); 
		  
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

		    String fileName = this.imagePath.saveImageOnDesk(file, "maintainanceFile");
		    Maintainance maintainance=maintainanceService.getByMaintainanceId(maintainanceUploadVo.getMaintainanceId());
		    maintainance.setRunningStatus("Service Document uploaded");
		    
		  if (!fileName.isEmpty()) {
			  MaintainanceUpload maintainanceUpload=new MaintainanceUpload();
		      BeanUtils.copyProperties(maintainanceUploadVo, maintainanceUpload);
		      maintainanceUpload.setEndingPath(str);
		      maintainanceUpload.setSrFileploadPath(fileName);
		      maintainanceUpload.setMaintainance(maintainance);
		      maintainanceUpload= maintainanceUploadRepository.save(maintainanceUpload);
		      
		      Maintainance maintainance1=maintainanceService.getByMaintainanceId(maintainanceUpload.getMaintainance().getMaintainanceId());
			   
		      if(maintainanceUpload.getSrFileploadPath()!=null) {
			      
		    	  maintainance1.setMaintainanceUpload("true");
		    	 
		    	  maintainanceService.updateMaintainanceForFileUpload(maintainance1);
			      }else{
			    	  
			    	  maintainance1.setMaintainanceUpload("false");
				    	 
			    	  maintainanceService.updateMaintainanceForFileUpload(maintainance1);
			      }
		      
		      map.put("msg", "created successfully");
		  
		  }
		  if (user.getRole().getRole().getRoleName().equalsIgnoreCase("Vendor")
					|| user.getRole().getRole().getRoleName().equalsIgnoreCase("REGENGINEER")) {

			  maintainance.setRunningStatus("Uploading Service Document");
						// Sr-Notification email Trigger start--------------->
						List<Object[]> amcInventoryList =maintainanceRepository.UploadingServiceDocument(maintainance.getMaintainanceId());
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
						  mailSubjects = "Nu-Nxtwav  "+ maintainance.getServiceRequestCode()+"– Uploading Service Document";
						  mail.setMailTo(emailTo);
						  mail.setMailCC(emailCc); 
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

						}
							

				// Sr-Notification Email TriggerEnd    <-------------------------									
										System.out.println("Uploading Service Document is completed");


				}

			

		 
			return new ResponseEntity<>(map,HttpStatus.OK);
	    	
	    	}
	 
	 
	 @GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
	   
		 MaintainanceUpload maintainanceUpload= maintainanceUploadRepository.getOne(id);
		 
		 Resource resource = this.imagePath.getImageByFileName("maintainanceFile", maintainanceUpload.getSrFileploadPath());
	    	
	    if(maintainanceUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
	    }
	    else if(maintainanceUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/jpeg"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else if(maintainanceUpload.getEndingPath().equalsIgnoreCase(".png")) {
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
	  
	  
	 @GetMapping({"/getByMaintainanceId/{id}"})
	  public ResponseEntity<List<MaintainanceUploadVo>> getByServiceRequestId(@PathVariable("id") Long id) {
	     List<MaintainanceUploadVo> maintainanceUploadVos=new ArrayList<>();
		 List<MaintainanceUpload> maintainanceUploads= maintainanceUploadRepository.getSrUploadByMaintainanceId(id);

			  maintainanceUploads.forEach(srf->{ MaintainanceUploadVo
			  maintainanceUploadVo=new MaintainanceUploadVo();
			  BeanUtils.copyProperties(srf, maintainanceUploadVo);
			  
			  maintainanceUploadVo.setMaintainanceId(srf.getMaintainance().
			  getMaintainanceId()); maintainanceUploadVos.add(maintainanceUploadVo); });
			 
		 
		 return new ResponseEntity<>(maintainanceUploadVos,HttpStatus.OK);
	 }
	 
	 @DeleteMapping("/delete/{id}")
		public void deleteUploadById(@PathVariable(value = "id") Long id) {
		 MaintainanceUpload maintainanceUpload=maintainanceUploadRepository.findById(id).get();
		 maintainanceUploadRepository.deleteById(id);
		 
		 Maintainance maintainance=maintainanceRepository.findById(maintainanceUpload.getMaintainance().getMaintainanceId()).get();
		List<MaintainanceUpload> maintainanceUploads=maintainanceUploadRepository.findByMaintainance(maintainance);
		
		if(maintainanceUploads.size()==0) {
			maintainance.setMaintainanceUpload("false"); 
			maintainanceService.updateMaintainanceForFileUpload(maintainance);
		}
	 
	 }
	  
	  
  }
 


