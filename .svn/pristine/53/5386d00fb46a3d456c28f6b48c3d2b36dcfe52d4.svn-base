package com.titan.irgs.accessPolicy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.accessPolicy.domain.Module;
import com.titan.irgs.accessPolicy.domain.SubModule;
import com.titan.irgs.accessPolicy.model.ModuleVo;
import com.titan.irgs.accessPolicy.model.SubModuleVo;
import com.titan.irgs.accessPolicy.repository.ModuleRepo;


@Component
public class ModuleMapper {

	@Autowired
	ModuleRepo modelRepo;
	public Module convertModeltoDomain(ModuleVo modelVo) {
		Module model = new Module();
		BeanUtils.copyProperties(modelVo, model);
		return model;
	}

	public ModuleVo convertDomaintoModel(Module model) {
		ModuleVo modelVo= new ModuleVo();
		BeanUtils.copyProperties(model, modelVo);
		if(model.getSubModules()!=null) {
			List<SubModuleVo> subModuleVos=new ArrayList<>();
			for(SubModule subModule:model.getSubModules()) {
				SubModuleVo subModuleVo=new SubModuleVo();
				BeanUtils.copyProperties(subModule, subModuleVo);
				subModuleVos.add(subModuleVo);
				}
			modelVo.setSubModuleVos(subModuleVos);
			
		}
		return modelVo;
	}

	

}



