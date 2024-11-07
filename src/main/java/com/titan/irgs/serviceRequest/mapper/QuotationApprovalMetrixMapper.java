package com.titan.irgs.serviceRequest.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.domain.QuotationApprovalMetrix;
import com.titan.irgs.serviceRequest.model.QuotationApprovalMetrixVo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@Component
public class QuotationApprovalMetrixMapper {
	@Autowired
	WebMasterRepo webMasterRepo;
	@Autowired
	WebMasterService webMasterService;
	@Autowired
	WebRoleRepo webRoleRepo;


	public QuotationApprovalMetrix convertModeltoDomain(QuotationApprovalMetrixVo quotationApprovalMetrixVo) {
		QuotationApprovalMetrix quotationApprovalMetrix=new QuotationApprovalMetrix();
		
		BeanUtils.copyProperties(quotationApprovalMetrixVo, quotationApprovalMetrix);
		quotationApprovalMetrix.setWebMaster(webMasterRepo.findById(quotationApprovalMetrixVo.getBussinessVerticalId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
		quotationApprovalMetrix.setWebRole(webRoleRepo.findById(quotationApprovalMetrixVo.getWebRoleId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));

		
		return quotationApprovalMetrix;
	}

	public QuotationApprovalMetrixVo convertDomaintoModel(QuotationApprovalMetrix quotationApprovalMetrix) {
		QuotationApprovalMetrixVo quotationApprovalMetrixVo=new QuotationApprovalMetrixVo();
		BeanUtils.copyProperties(quotationApprovalMetrix, quotationApprovalMetrixVo);
		WebMaster webMaster = webMasterService.getById(quotationApprovalMetrix.getWebMaster().getWebMasterId());
		quotationApprovalMetrixVo.setBussinessVerticalId(webMaster.getWebMasterId());
		quotationApprovalMetrixVo.setBussinessVerticalName(webMaster.getWebMasterName());

		WebRole webRole = webRoleRepo.findByWebRoleId(quotationApprovalMetrix.getWebRole().getWebRoleId());
		quotationApprovalMetrixVo.setWebRoleId(webRole.getWebRoleId());
		quotationApprovalMetrixVo.setRoleId(webRole.getRole().getRoleId());
		quotationApprovalMetrixVo.setRoleName(webRole.getRole().getRoleName());
		/*
		 * quotationApprovalMetrixVo.setWebRoleId(quotationApprovalMetrix.getWebRole().
		 * getWebRoleId());
		 * quotationApprovalMetrixVo.setBussinessVerticalId(quotationApprovalMetrix.
		 * getWebRole().getWebMaster().getWebMasterId());
		 * quotationApprovalMetrixVo.setBussinessVerticalName(quotationApprovalMetrix.
		 * getWebRole().getWebMaster().getWebMasterName());
		 * quotationApprovalMetrixVo.setRoleName(quotationApprovalMetrix.getWebRole().
		 * getRole().getRoleName());
		 * quotationApprovalMetrixVo.setRoleId(quotationApprovalMetrix.getWebRole().
		 * getRole().getRoleId());
		 */	return quotationApprovalMetrixVo;
	}


	

}
