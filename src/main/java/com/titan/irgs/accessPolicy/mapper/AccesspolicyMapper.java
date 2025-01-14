package com.titan.irgs.accessPolicy.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.accessPolicy.domain.AccesspolicyDomain;
import com.titan.irgs.accessPolicy.model.AccesspolicyModel;
import com.titan.irgs.accessPolicy.repository.ModuleRepo;
import com.titan.irgs.accessPolicy.repository.SubModuleRepo;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.webRole.service.IWebRoleService;

@Component
public class AccesspolicyMapper {
	
	@Autowired
	SubModuleRepo subModuleRepo;
	
	@Autowired
	ModuleRepo moduleRepo;
	
	@Autowired
	IWebRoleService webRoleService;
	
	public AccesspolicyDomain convertModeltoDomain(AccesspolicyModel accesspolicyModel) {
		AccesspolicyDomain accesspolicyDomain = new AccesspolicyDomain();
		BeanUtils.copyProperties(accesspolicyModel, accesspolicyDomain);
		accesspolicyDomain.setModule(moduleRepo.findById(accesspolicyModel.getModuleId()).orElseThrow(()->new ResourceNotFoundException("moduleId not found")));
		accesspolicyDomain.setSubModule(accesspolicyModel.getSubModuleId()==null?null:subModuleRepo.findById(accesspolicyModel.getSubModuleId()).orElseThrow(()->new ResourceNotFoundException("subModuleId not found")));
		accesspolicyDomain.setWebRole(webRoleService.getById(accesspolicyModel.getWebRoleId()));
		return accesspolicyDomain;
	}

	public AccesspolicyModel convertDomaintoModel(AccesspolicyDomain accesspolicyDomain) {
		AccesspolicyModel accesspolicyModel= new AccesspolicyModel();
		BeanUtils.copyProperties(accesspolicyDomain, accesspolicyModel);
		accesspolicyModel.setModuleId(accesspolicyDomain.getModule().getModuleId());
		accesspolicyModel.setModuleName(accesspolicyDomain.getModule().getModuleName());
		accesspolicyModel.setSubModuleId(accesspolicyDomain.getSubModule()==null?null:accesspolicyDomain.getSubModule().getSubModuleId());
		accesspolicyModel.setSubModuleName(accesspolicyDomain.getSubModule()==null?null:accesspolicyDomain.getSubModule().getSubModuleName());
		accesspolicyModel.setWebRoleId(accesspolicyDomain.getWebRole().getWebRoleId());
		accesspolicyModel.setWebRoleName(accesspolicyDomain.getWebRole().getRole().getRoleName());
		accesspolicyModel.setRoleName(accesspolicyDomain.getWebRole().getRole().getRoleName());
		accesspolicyModel.setVerticalName(accesspolicyDomain.getWebRole().getWebMaster().getWebMasterName());
		return accesspolicyModel;
	}
	
	

}
