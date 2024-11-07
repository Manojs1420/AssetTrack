package com.titan.irgs.accessPolicy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.accessPolicy.domain.SubModule;
import com.titan.irgs.accessPolicy.repository.SubModuleRepo;
import com.titan.irgs.accessPolicy.service.SubModuleService;
import com.titan.irgs.customException.ResourceNotFoundException;

@Service
public class SubModuleServiceImpl implements SubModuleService{

	@Autowired
	SubModuleRepo subModuleRepo;
	
	@Override
	public List<SubModule> getAll() {
		// TODO Auto-generated method stub
		return subModuleRepo.findAll();
	}

	@Override
	public SubModule save(SubModule subModule) {
		// TODO Auto-generated method stub
		return subModuleRepo.save(subModule);
	}

	@Override
	public SubModule getById(Long id) {
		// TODO Auto-generated method stub
		return subModuleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("The id is not found"));
	}

	@Override
	public SubModule update(SubModule subModule) {
		// TODO Auto-generated method stub
		subModuleRepo.findById(subModule.getSubModuleId()).orElseThrow(()->new ResourceNotFoundException("The id is not found"));
		return subModuleRepo.save(subModule);
	}

}
