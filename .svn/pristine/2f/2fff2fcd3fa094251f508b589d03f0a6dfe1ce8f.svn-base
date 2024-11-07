package com.titan.irgs.serviceRequest.mapper;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.serviceRequest.domain.BreakDownTracking;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.model.ServiceRequestVO;
import com.titan.irgs.serviceRequest.repository.BreakDownRepo;
import com.titan.irgs.serviceRequest.repository.MiscellaneousTypeRepo;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.repository.ServiceRequestTypeRepo;
import com.titan.irgs.serviceRequest.repository.UrgencyRepo;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class ServiceRequestMapper {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	BreakDownRepo breakDownRepo;
	
	@Autowired
	UrgencyRepo urgencyRepo;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	ServiceRequestDetailsMapper serviceRequestDetailsMapper;
	
	@Autowired
	ServiceRequestTypeRepo serviceRequestTypeRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	MiscellaneousTypeRepo miscellaneousTypeRepo;
	
	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Autowired
	UserRepo userRepo;
	
	private final static String CLOSE = "CLOSE";

	private static final String OPEN = "OPEN";

	
	public ServiceRequest convertModeltoDomain(ServiceRequestVO serviceRequestVO) {
		 ServiceRequest serviceRequest = new ServiceRequest();
		 
		 BeanUtils.copyProperties(serviceRequestVO, serviceRequest);
		 serviceRequest.setWebMaster(webMasterRepo.findByWebMasterName(serviceRequestVO.getBussinessVerticleName()));
		 
		 if(serviceRequestVO.getBreakDownType()!=null) {
		 serviceRequest.setBreakDownType(breakDownRepo.findByBreakDownTypeName(serviceRequestVO.getBreakDownType()));
		 }
		 
		 serviceRequest.setClosedBy(serviceRequestVO.getClosedBy());
		 
		 if(serviceRequestVO.getUrgency()!=null)
		 {
			 serviceRequest.setUrgency(urgencyRepo.findByUrgencyName(serviceRequestVO.getUrgency()));
		 }
		 
		 serviceRequest.setServceRequestType(serviceRequestTypeRepo.findByServiceRequestTypeName(serviceRequestVO.getServiceRequestTypeName()));

		 if(serviceRequestVO.getStoreId()!=null) {
			 serviceRequest.setStore(storeRepository.findById(serviceRequestVO.getStoreId()).get());
		 }

		 if(serviceRequestVO.getUserId()!=null) {
			 serviceRequest.setUser(userRepo.findById(serviceRequestVO.getUserId()).get());
		 }
		 
		 if(serviceRequestVO.getMiscellaneousTypeId()!=null) {
			 serviceRequest.setMiscellaneousTypeDomain(miscellaneousTypeRepo.findById(serviceRequestVO.getMiscellaneousTypeId()).get());
		 }
		 if(serviceRequestVO.getInventoryId()!=null) {
			 serviceRequest.setInventory(inventoryRepository.findById(serviceRequestVO.getInventoryId()).get());
		 }
		 if(serviceRequestVO.getBreakDownTracking()!=null && serviceRequestVO.getBreakDownTracking()!="") {
			 serviceRequest.setBreakDownTracking(BreakDownTracking.valueOf(serviceRequestVO.getBreakDownTracking()));
		 }
		 
		return serviceRequest;
	}

	
	
	
	public ServiceRequestVO convertDomaintoModel(ServiceRequest serviceRequest) {
		Long diffIndays=null;
		ServiceRequestVO serviceRequestVO=new ServiceRequestVO();
		BeanUtils.copyProperties(serviceRequest, serviceRequestVO);
		
		if(serviceRequest.getBreakDownType()!=null) {
			serviceRequestVO.setBreakDownType(serviceRequest.getBreakDownType().getBreakDownTypeName());
		}
		
		if(serviceRequest.getUrgency()!=null) {
			serviceRequestVO.setUrgency(serviceRequest.getUrgency().getUrgencyName());
		}
		
		if(serviceRequest.getStore()!=null) {

			serviceRequestVO.setStoreId(serviceRequest.getStore().getStoreId());
		}
		
		if(serviceRequest.getUser()!=null) {

			serviceRequestVO.setUserId(serviceRequest.getUser().getId());
			serviceRequestVO.setUserName(serviceRequest.getUser().getFirstName());
		}
		
		serviceRequestVO.setBussinessVerticleName(serviceRequest.getWebMaster().getWebMasterName());
		serviceRequestVO.setServiceDocumentUploaded(serviceRequest.getServiceUpload());
		if(serviceRequest.getServiceVendorId()!=null) 
		{
			serviceRequestVO.setServiceVendorId(serviceRequest.getServiceVendorId());
			serviceRequestVO.setServiceVendorName(serviceRequest.getServiceVendorName());
			serviceRequestVO.setServiceVendorCode(vendorRepository.getOne(serviceRequest.getServiceVendorId()).getVendorCode());
			
		}
		if(serviceRequest.getInventory()!=null) {

			 serviceRequestVO.setInventoryId(serviceRequest.getInventory().getInventoryId());
		 }
		 if(serviceRequest.getMiscellaneousTypeDomain()!=null) {
			 serviceRequestVO.setMiscellaneousTypeId(serviceRequest.getMiscellaneousTypeDomain().getMiscellaneousTypeId());
			 serviceRequestVO.setMiscellaneousTypeCode(serviceRequest.getMiscellaneousTypeDomain().getMiscellaneousTypeCode());
			 serviceRequestVO.setMiscellaneousTypeName(serviceRequest.getMiscellaneousTypeDomain().getMiscellaneousTypeName());
		 }
		 
		 
		//serviceRequestVO.setInventoryName(serviceRequest.getInventory().get);
		if(serviceRequest.getServiceRequestDeatils()!=null) {
		
		serviceRequestVO.setServiceRequestDetailsVO(serviceRequest.getServiceRequestDeatils()
				.stream().map(serviceRequestDetailsMapper::convertDomaintoModel).collect(Collectors.toList()));
		
		}
		
		 if(serviceRequest.getBreakDownTracking()!=null) {
			 serviceRequestVO.setBreakDownTracking(serviceRequest.getBreakDownTracking().toString());
		 }
		
		 // days counting....
		 if(serviceRequest.getServiceRequestDate()!=null) {
			 
			ServiceRequestVO serviceRequestVO1= this.getDiffDays(serviceRequest);
		     
			
			 
			 serviceRequestVO.setCountServiceRequestdays(serviceRequestVO1.getCountServiceRequestdays());
			 serviceRequestVO.setClouserFourmExpire(serviceRequestVO1.isClouserFourmExpire());
		 }
		 
		 if(serviceRequest.getBaseServiceRequestId()!=null) {
			 ServiceRequest serviceRequest2=serviceRequestRepository.findById(serviceRequest.getBaseServiceRequestId()).get();
			 serviceRequestVO.setBaseServiceRequestCode(serviceRequest2.getServiceRequestCode());
		 }
		 
		return serviceRequestVO;
	}




	public ServiceRequestVO getDiffDays(ServiceRequest serviceRequest) {
		// TODO Auto-generated method stub
		
        Long diffIndays=null;
        ServiceRequestVO serviceRequestVO=new ServiceRequestVO();
	     
		 if(serviceRequest.getTicketStatus()==null || serviceRequest.getTicketStatus().equalsIgnoreCase(OPEN)) 
		 {
			 diffIndays = ChronoUnit.DAYS.between(
	    		 serviceRequest.getServiceRequestDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
	    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;
		 }
		 else if(serviceRequest.getServiceRequestClosedDate()!=null && serviceRequest.getTicketStatus().equalsIgnoreCase(CLOSE)) 
		 {

			 diffIndays = ChronoUnit.DAYS.between(
	    		 serviceRequest.getServiceRequestDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
	    		 serviceRequest.getServiceRequestClosedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;
			 
			 long days = ChronoUnit.DAYS.between(
		    		 serviceRequest.getServiceRequestClosedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
		    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;

		 if(days >3) {
			 serviceRequestVO.setClouserFourmExpire(true);
		 
		 }
	   }
		 
		 serviceRequestVO.setCountServiceRequestdays(diffIndays);
	 
		
		
		
		return serviceRequestVO;
	}
	
	
	
	
}
