package com.titan.irgs.role.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.role.domain.ApplicationAccess;
import com.titan.irgs.role.repository.ApplicationAccessRepo;
import com.titan.irgs.role.service.IApplicationAccess;

@Service
public class ApplicationAccessImpl implements IApplicationAccess{
	
	
	private static final Logger logger= LoggerFactory.getLogger(ApplicationAccessImpl.class);

	
	@Autowired
	ApplicationAccessRepo applicationAccessRepo;

	@Override
	public ApplicationAccess save(ApplicationAccess applicationAccess) {
		return applicationAccessRepo.save(applicationAccess);
	}

	@Override
	public List<ApplicationAccess> getAll() {
		return applicationAccessRepo.findAll();
	}

	@Override
	public ApplicationAccess getById(Long id) {
		return applicationAccessRepo.findById(id)
							.orElseThrow(()->new ResourceNotFoundException("the request id not found"));
	}

}
