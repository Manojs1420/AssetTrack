package com.titan.irgs.serviceRequest.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.domain.ServiceRequestDeatil;
import com.titan.irgs.serviceRequest.repository.ServiceRequestDetailRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestDetailService;

@Service
public class ServiceRequestDetailService implements IServiceRequestDetailService{
	
	@Autowired
	ServiceRequestDetailRepository serviceRequestDetailRepository;

	@Override
	public ServiceRequestDeatil save(ServiceRequestDeatil serviceRequestDeatil) {
		return serviceRequestDetailRepository.save(serviceRequestDeatil);
	}

	@Override
	public ServiceRequestDeatil update(ServiceRequestDeatil serviceRequestDeatil) {
	
		ServiceRequestDeatil serviceRequestDeatil1=serviceRequestDetailRepository.getOne(serviceRequestDeatil.getServiceRequestDetailId());
		
		if(serviceRequestDeatil1==null) {
			throw new ResourceNotFoundException("the requested id {} not found)" +serviceRequestDeatil.getServiceRequestDetailId());
		}

	  return serviceRequestDetailRepository.save(serviceRequestDeatil);
	
	}

	@Override
	public ServiceRequestDeatil getbyid(Long serviceRequestDetailId) {
		// TODO Auto-generated method stub
		return serviceRequestDetailRepository.getOne(serviceRequestDetailId);
	}

	

}
