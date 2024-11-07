package com.titan.irgs.serviceRequest.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.domain.MiscellaneousTypeDomain;
import com.titan.irgs.serviceRequest.repository.MiscellaneousTypeRepo;
import com.titan.irgs.serviceRequest.service.IMiscellaneousTypeService;

@Service
public class MiscellaneousTypeServiceImpl implements IMiscellaneousTypeService{
	
	@Autowired
	MiscellaneousTypeRepo miscellaneousTypeRepo;

	@Override
	public MiscellaneousTypeDomain save(MiscellaneousTypeDomain miscellaneousTypeDomain) {
		// TODO Auto-generated method stub
		return miscellaneousTypeRepo.save(miscellaneousTypeDomain);
	}

	@Override
	public List<MiscellaneousTypeDomain> getAll() {
		// TODO Auto-generated method stub
		return miscellaneousTypeRepo.findAll();
	}

	@Override
	public MiscellaneousTypeDomain getById(Long id) {
		// TODO Auto-generated method stub
		return miscellaneousTypeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("the requested id {} not found)" +id));
	}

	@Override
	public MiscellaneousTypeDomain update(MiscellaneousTypeDomain miscellaneousTypeDomain) {
		MiscellaneousTypeDomain exists=miscellaneousTypeRepo.getOne(miscellaneousTypeDomain.getMiscellaneousTypeId());
		if(exists==null) {
			throw new ResourceNotFoundException("The id not found");
		}
		
		return miscellaneousTypeRepo.save(miscellaneousTypeDomain);
	}

}
