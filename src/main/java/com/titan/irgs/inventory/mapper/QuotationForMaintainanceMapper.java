package com.titan.irgs.inventory.mapper;

	import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.inventory.domain.QuotationForMaintainance;
import com.titan.irgs.inventory.vo.QuotationForMaintainanceVo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

	@Component
	public class QuotationForMaintainanceMapper {
		@Autowired
		WebMasterRepo webMasterRepo;
		@Autowired
		WebMasterService webMasterService;
		@Autowired
		WebRoleRepo webRoleRepo;


		public QuotationForMaintainance convertModeltoDomain(QuotationForMaintainanceVo quotationForMaintainanceVo) {
			QuotationForMaintainance quotationForMaintainance=new QuotationForMaintainance();
			
			BeanUtils.copyProperties(quotationForMaintainanceVo, quotationForMaintainance);
			quotationForMaintainance.setWebMaster(webMasterRepo.findById(quotationForMaintainanceVo.getBussinessVerticalId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
			quotationForMaintainance.setWebRole(webRoleRepo.findById(quotationForMaintainanceVo.getWebRoleId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));

			
			return quotationForMaintainance;
		}

		public QuotationForMaintainanceVo convertDomaintoModel(QuotationForMaintainance quotationForMaintainance) {
			QuotationForMaintainanceVo quotationForMaintainanceVo=new QuotationForMaintainanceVo();
			BeanUtils.copyProperties(quotationForMaintainance, quotationForMaintainanceVo);
			WebMaster webMaster = webMasterService.getById(quotationForMaintainance.getWebMaster().getWebMasterId());
			quotationForMaintainanceVo.setBussinessVerticalId(webMaster.getWebMasterId());
			quotationForMaintainanceVo.setBussinessVerticalName(webMaster.getWebMasterName());

			WebRole webRole = webRoleRepo.findByWebRoleId(quotationForMaintainance.getWebRole().getWebRoleId());
			quotationForMaintainanceVo.setWebRoleId(webRole.getWebRoleId());
			quotationForMaintainanceVo.setRoleId(webRole.getRole().getRoleId());
			quotationForMaintainanceVo.setRoleName(webRole.getRole().getRoleName());
			
			return quotationForMaintainanceVo;
		}


		

	}


