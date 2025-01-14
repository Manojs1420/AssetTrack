package com.titan.irgs.inventory.mapper;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.vo.MaintainanceVO;
import com.titan.irgs.master.repository.EngineerRepository;
import com.titan.irgs.master.serviceImpl.EngineerService;

@Component
public class MaintainanceMapper {

	@Autowired
	AmcInventoryService amcInventoryService;

	@Autowired
	AmcInventoryRepository amcInventoryRepository;
	/*
	 * @Autowired MaintainanceDetailMapper maintainanceDetailMapper;
	 */
	@Autowired
	EngineerRepository engineerRepository;
	@Autowired
	EngineerService engineerService;

	public Maintainance getEntityFromVo(MaintainanceVO maintainanceVO) {
		Maintainance maintainance = new Maintainance();
		BeanUtils.copyProperties(maintainanceVO, maintainance);
		maintainance.setAmcInventory(amcInventoryRepository.findById(maintainanceVO.getAmcId()).get());
	// maintainance.setEngineer(engineerRepository.findById(maintainanceVO.getEngineerId()).get());
		return maintainance;
	}

	public MaintainanceVO getVoFromEntity(Maintainance maintainance) {
		MaintainanceVO maintainanceVO = new MaintainanceVO();
		BeanUtils.copyProperties(maintainance, maintainanceVO);
		AmcInventory amcInventory = amcInventoryService.findById(maintainance.getAmcInventory().getAmcId());
		maintainanceVO.setAmcId(amcInventory.getAmcId());
		maintainanceVO.setAssetId(amcInventory.getAsset().getAssetId());
		maintainanceVO.setAssetCode(amcInventory.getAsset().getAssetCode());
	//	maintainanceVO.setErNumber(amcInventory.getInventory().getErNo());
		maintainanceVO.setServiceVendorCode(amcInventory.getVendor().getVendorCode());
		maintainanceVO.setServiceVendorName(amcInventory.getVendor().getVendorName());
		maintainanceVO.setServiceDocumentUploaded(maintainance.getMaintainanceUpload());
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
		
		// days counting....
				 if(maintainance.getMaintainanceDateTime()!=null) {
					 
					 MaintainanceVO maintainanceVO1= this.getDiffDays(maintainance);
				     
					
					 
					 maintainanceVO.setCountServiceRequestdays(maintainanceVO1.getCountServiceRequestdays());
					 maintainanceVO.setClouserFourmExpire(maintainanceVO1.isClouserFourmExpire());
				 }
				 
		return maintainanceVO;

	}

	public MaintainanceVO getDiffDays(Maintainance maintainance) {
		// TODO Auto-generated method stub
		
        Long diffIndays=null;
        MaintainanceVO maintainanceVO=new MaintainanceVO();
	     
		 if(maintainance.getTicketStatus()==null || maintainance.getTicketStatus().equalsIgnoreCase("OPEN")) 
		 {
			 diffIndays = ChronoUnit.DAYS.between(
					 maintainance.getMaintainanceDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
	    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;
		 }
		 else if(maintainance.getServiceRequestcloseDate()!=null && maintainance.getTicketStatus().equalsIgnoreCase("CLOSE")) 
		 {

			 diffIndays = ChronoUnit.DAYS.between(
					 maintainance.getMaintainanceDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
					 maintainance.getServiceRequestcloseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;
			 
			 long days = ChronoUnit.DAYS.between(
					 maintainance.getServiceRequestcloseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
		    		 new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())+1;

		 if(days >3) {
			 maintainanceVO.setClouserFourmExpire(true);
		 
		 }
	   }
		 
		 maintainanceVO.setCountServiceRequestdays(diffIndays);
	 
		
		
		
		return maintainanceVO;
	}
	
	

}
