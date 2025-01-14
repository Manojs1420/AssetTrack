package com.titan.irgs.inventory.mapper;


import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.inventory.domain.AutoAmcInventoryLog;
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.AutoCreateAmcService;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.inventory.vo.AutoAmcServiceVO;
import com.titan.irgs.inventory.vo.MaintainanceVO;
import com.titan.irgs.master.repository.EngineerRepository;
import com.titan.irgs.master.serviceImpl.EngineerService;

@Component
public class AutoAMCserviceMapper {
	
		@Autowired
		AmcInventoryService amcInventoryService;

		@Autowired
		MaintainanceService maintainanceService;
		
		@Autowired
		AutoCreateAmcService autoCreateAmcService;
		
		@Autowired
		AmcWarrantyRepository amcWarrantyRepository;
		
		@Autowired
		AmcInventoryRepository amcInventoryRepository;
		
		@Autowired
		MaintainanceRepository maintainanceRepository;
		/*
		 * @Autowired MaintainanceDetailMapper maintainanceDetailMapper;
		 */
		@Autowired
		EngineerRepository engineerRepository;
		@Autowired
		EngineerService engineerService;

		public AutoAmcInventoryLog getEntityFromVo(AutoAmcServiceVO maintainanceVO) {
			AutoAmcInventoryLog maintainance = new AutoAmcInventoryLog();
			BeanUtils.copyProperties(maintainanceVO, maintainance);
		//	maintainance.setMaintainance(maintainanceRepository.findById(maintainanceVO.getAmcId()).get());
		//	maintainance.setAmcWarranty(amcWarrantyRepository.findById(maintainanceVO.getAmcId()).get());

		// maintainance.setEngineer(engineerRepository.findById(maintainanceVO.getEngineerId()).get());
			return maintainance;
		}

		public AutoAmcServiceVO getVoFromEntity(AutoAmcInventoryLog maintainance) {
			AutoAmcServiceVO maintainanceVO = new AutoAmcServiceVO();
			BeanUtils.copyProperties(maintainance, maintainanceVO);
		//	AutoAmcInventoryLog amcInventory = autoCreateAmcService.findById(maintainance.getMaintainance().getAmcInventory().getAmcId());
			AutoAmcInventoryLog amcInventory = autoCreateAmcService.findById(maintainance.getAmcLogId());
			
			maintainanceVO.setAmcId(amcInventory.getAmcInventory().getAmcId());
//			maintainanceVO.setAmcWarrantydetailId(amcInventory.getAmcWarranty().getWarrantyId());
	//		maintainanceVO.setMaintainanceId(amcInventory.getMaintainance().getMaintainanceId());
				/*
			 * if(maintainance.getMaintainanceDetail()!=null) {
			 * 
			 * maintainanceVO.setMaintainanceDetailsVo(maintainance.getMaintainanceDetail()
			 * .stream().map(maintainanceDetailMapper::convertDomaintoModel).collect(
			 * Collectors.toList()));
			 * 
			 * }
			 */		
			/*
			 * Engineer engineer =
			 * engineerService.getEngineerById(maintainance.getEngineer().getEngineerId());
			 * maintainanceVO.setEngineerId(engineer.getEngineerId());
			 */
			return maintainanceVO;

		}

		public MaintainanceVO getDiffDays(Maintainance serviceRequest) {
			// TODO Auto-generated method stub
			
	        Long diffIndays=null;
	        MaintainanceVO serviceRequestVO=new MaintainanceVO();
		     
			 if(serviceRequest.getTicketStatus()==null || serviceRequest.getTicketStatus().equalsIgnoreCase("OPEN")) 
			 {
				 diffIndays = ChronoUnit.DAYS.between(
		    		 serviceRequest.getMaintainanceDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
		    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;
			 }
			 else if(serviceRequest.getServiceRequestcloseDate()!=null && serviceRequest.getTicketStatus().equalsIgnoreCase("CLOSE")) 
			 {

				 diffIndays = ChronoUnit.DAYS.between(
		    		 serviceRequest.getMaintainanceDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
		    		 serviceRequest.getServiceRequestcloseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;
				 
				 long days = ChronoUnit.DAYS.between(
			    		 serviceRequest.getServiceRequestcloseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
			    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;

			 if(days >3) {
				 serviceRequestVO.setClouserFourmExpire(true);
			 
			 }
		   }
			 
			 serviceRequestVO.setCountServiceRequestdays(diffIndays);
		 
			
			
			
			return serviceRequestVO;
		}
		
		

}
