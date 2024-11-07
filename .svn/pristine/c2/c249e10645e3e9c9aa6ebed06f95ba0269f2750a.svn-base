package com.titan.irgs.accessPolicy.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.accessPolicy.domain.SubModule;
import com.titan.irgs.accessPolicy.model.SubModuleVo;
import com.titan.irgs.accessPolicy.repository.ModuleRepo;
import com.titan.irgs.accessPolicy.repository.SubModuleRepo;
import com.titan.irgs.customException.ResourceNotFoundException;

@Component
public class SubModuleMapper {
	
	@Autowired
	SubModuleRepo subModuleRepo;
	
	@Autowired
	ModuleRepo moduleRepo;
	
	
	public SubModule convertModeltoDomain(SubModuleVo subModuleVo) {
		SubModule submodel = new SubModule();
		BeanUtils.copyProperties(subModuleVo, submodel);
		submodel.setModule(moduleRepo.findById(subModuleVo.getModuleId()).orElseThrow(()->new ResourceNotFoundException("ModelId not found")));
		return submodel;
	}

	public SubModuleVo convertDomaintoModel(SubModule subModule) {
		SubModuleVo subModuleVo= new SubModuleVo();
		BeanUtils.copyProperties(subModule, subModuleVo);
		subModuleVo.setModuleId(subModule.getModule().getModuleId());
		subModuleVo.setModuleName(subModule.getModule().getModuleName());
		return subModuleVo;
	}


}
