package com.titan.irgs.serviceRequest.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.serviceRequest.domain.ServceRequestType;
import com.titan.irgs.serviceRequest.model.ServiceRequestTypeModel;



@Component
public class ServiceRequestTypeMapper {

	public ServceRequestType convertModeltoDomain(ServiceRequestTypeModel serviceRequestTypeModel) {
		ServceRequestType servceRequestType=new ServceRequestType();
		BeanUtils.copyProperties(serviceRequestTypeModel, servceRequestType);
		
		return servceRequestType;
	}

	public ServiceRequestTypeModel convertDomaintoModel(ServceRequestType servceRequestType) {
		ServiceRequestTypeModel serviceRequestTypeModel=new ServiceRequestTypeModel();
		BeanUtils.copyProperties(servceRequestType, serviceRequestTypeModel);
		
		return serviceRequestTypeModel;
	}

}
