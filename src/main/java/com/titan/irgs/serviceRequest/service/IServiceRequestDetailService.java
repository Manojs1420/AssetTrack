package com.titan.irgs.serviceRequest.service;

import com.titan.irgs.serviceRequest.domain.ServiceRequestDeatil;

public interface IServiceRequestDetailService {

	ServiceRequestDeatil save(ServiceRequestDeatil serviceRequestDeatil);

	ServiceRequestDeatil update(ServiceRequestDeatil serviceRequestDeatil);

	ServiceRequestDeatil getbyid(Long serviceRequestDetailId);

	
	
	
	
}
