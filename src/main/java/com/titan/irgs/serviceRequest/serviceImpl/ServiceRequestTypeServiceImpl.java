package com.titan.irgs.serviceRequest.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.domain.ServceRequestType;
import com.titan.irgs.serviceRequest.repository.ServiceRequestTypeRepo;
import com.titan.irgs.serviceRequest.service.ServiceRequestTypeService;



@Service
public class ServiceRequestTypeServiceImpl implements ServiceRequestTypeService{
	
	@Autowired
	ServiceRequestTypeRepo serviceRequestTypeRepo;

	@Override
	public ServceRequestType save(ServceRequestType servceRequestType) {
		// TODO Auto-generated method stub
		return serviceRequestTypeRepo.save(servceRequestType);
	}

	@Override
	public List<ServceRequestType> getAll() {
		// TODO Auto-generated method stub
		return serviceRequestTypeRepo.findAll();
	}

	@Override
	public ServceRequestType getById(Long id) {
		// TODO Auto-generated method stub
		return serviceRequestTypeRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("The id not found"));
	}

	@Override
	public ServceRequestType update(ServceRequestType servceRequestType) {
		// TODO Auto-generated method stub
		ServceRequestType exists=serviceRequestTypeRepo.getOne(servceRequestType.getServiceRequesTypetId());
		if(exists==null) {
			throw new ResourceNotFoundException("The id not found");
		}
		
		
		return serviceRequestTypeRepo.save(servceRequestType);
	}

}
