package com.titan.irgs.inventory.controller;

	import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.domain.MaintainanceInvoice;
import com.titan.irgs.inventory.repository.MaintainanceInvoiceRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.inventory.vo.MaintainanceInvoiceVo;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

	@RestController
	@RequestMapping({"/maintainanceInvoice"})
	public class MaintainanceInvoiceController {
		
		 @Value("${mail.status}")
		 private Boolean mailStatus;
		  
		  @Autowired
		  ImagePath imagePath;
		  
		  @Autowired
		  MaintainanceInvoiceRepository serviceRequestInvoiceRepository;
		  
		  
		  @Autowired
		  UserRepo userRepo;
		  @Autowired
		  MaintainanceService maintainanceService;
		  

			
		  @Autowired
		  MaintainanceRepository serviceRequestRepository;
		  
		  
		 @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
		 @PostMapping({"/save"})
		    public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file,
		    		@RequestParam("srfileInfo") String srfileInfo,HttpServletRequest request,Principal principal){
			 
			 ObjectMapper mapper=new ObjectMapper();
			 Map<String,String> map=new HashMap<>();
			 MaintainanceInvoiceVo serviceRequestInvoiceVo=new MaintainanceInvoiceVo(); 
			User user=userRepo.findByUsername(principal.getName());

			  try 
			  {
				  serviceRequestInvoiceVo=mapper.readValue(srfileInfo,MaintainanceInvoiceVo.class); 
			  
			  }
			  catch (Exception e)
			  { 
				  System.out.println(e.getLocalizedMessage()); 
			  }
				
			  String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

			    if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
			      return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 
			    
			    }

			    String fileName = this.imagePath.saveImageOnDesk(file, "maintainanceInvoice");
			    Maintainance serviceRequest=maintainanceService.getByMaintainanceId(serviceRequestInvoiceVo.getMaintainanceId());
			    serviceRequest.setRunningStatus("invoice uploaded");
			    serviceRequestRepository.save(serviceRequest);

			  if (!fileName.isEmpty()) {
				  MaintainanceInvoice serviceRequestInvoiceDomain=new MaintainanceInvoice();
			      
			      BeanUtils.copyProperties(serviceRequestInvoiceVo, serviceRequestInvoiceDomain);
			      serviceRequestInvoiceDomain.setEndingPath(str);
			      serviceRequestInvoiceDomain.setInvoicePath(fileName);
			      serviceRequestInvoiceDomain.setMaintainance(serviceRequest);
			      serviceRequestInvoiceDomain= serviceRequestInvoiceRepository.save(serviceRequestInvoiceDomain);
			      map.put("msg", "created successfully");
			  
			  }
			  
			
			  
			  
			  

			 
				
				return new ResponseEntity<>(map,HttpStatus.OK);
		    	
		    	}
		 
		 
		 @GetMapping({"/download/{id}"})
		  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
		   
			 MaintainanceInvoice serviceRequestInvoiceDomain= serviceRequestInvoiceRepository.getOne(id);
			 
			 Resource resource = this.imagePath.getImageByFileName("maintainanceInvoice", serviceRequestInvoiceDomain.getInvoicePath());
		    	
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
		  
		  
		 @GetMapping({"/getByMaintainanceId/{id}"})
		  public ResponseEntity<List<MaintainanceInvoiceVo>> getByServiceRequestId(@PathVariable("id") Long id) {
		     List<MaintainanceInvoiceVo> serviceRequestInvoiceVos=new ArrayList<>();
			 List<MaintainanceInvoice> serviceRequestUploads= serviceRequestInvoiceRepository.getMaintainanceInvoiceByMaintainanceId(id);
			 serviceRequestUploads.forEach(srf->{
				 MaintainanceInvoiceVo serviceRequestInvoiceVo=new MaintainanceInvoiceVo();
			     BeanUtils.copyProperties(srf, serviceRequestInvoiceVo);
			     serviceRequestInvoiceVo.setMaintainanceId(srf.getMaintainance().getMaintainanceId());
			     serviceRequestInvoiceVos.add(serviceRequestInvoiceVo);
				 });
			 
			 return new ResponseEntity<>(serviceRequestInvoiceVos,HttpStatus.OK);
		 }
		  
		  
		  
	 
		
		

	}



