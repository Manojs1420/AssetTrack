package com.titan.irgs.accessPolicy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.accessPolicy.domain.Module;
import com.titan.irgs.accessPolicy.repository.ModuleRepo;
import com.titan.irgs.accessPolicy.service.ModuleService;
import com.titan.irgs.customException.ResourceNotFoundException;

@Service
public class ModuleServiceImpl implements ModuleService{

	@Autowired
	ModuleRepo modelRepo;
	
	
	@Override
	public Module save(Module model) {
		// TODO Auto-generated method stub
		return modelRepo.save(model);
	}

	@Override
	public List<Module> getAll() {
		// TODO Auto-generated method stub
		return modelRepo.findAll();
	}

	@Override
	public Module getById(Long id) {
		// TODO Auto-generated method stub
		return modelRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("the request id not found"));

	}

}
