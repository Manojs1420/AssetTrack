package com.titan.irgs.serviceRequest.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.serviceRequest.domain.ServiceRequestDeatil;
import com.titan.irgs.serviceRequest.model.ServiceRequestDetailsVO;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webRole.service.IWebRoleService;

@Component
public class ServiceRequestDetailsMapper {
	
	@Autowired
	IUserService iUserService;
	
	@Autowired
	IWebRoleService iWebRoleService;
	
	@Autowired
	IServiceRequestService iServiceRequestService;
	
	
	
	
	public ServiceRequestDeatil convertModeltoDomain(ServiceRequestDetailsVO serviceRequestDetailsVO) {
		 ServiceRequestDeatil serviceRequestDeatil = new ServiceRequestDeatil();
		 BeanUtils.copyProperties(serviceRequestDetailsVO, serviceRequestDeatil);
		 serviceRequestDeatil.setServiceRequest(iServiceRequestService.getById(serviceRequestDetailsVO.getServiceRequestId()));
		 serviceRequestDeatil.setUser(iUserService.getById(serviceRequestDetailsVO.getUserId()));
		 serviceRequestDeatil.setWebRole(iWebRoleService.getById(serviceRequestDetailsVO.getWebRoleId()));
		return serviceRequestDeatil;
	}

	public ServiceRequestDetailsVO convertDomaintoModel(ServiceRequestDeatil serviceRequestDeatil) {
		ServiceRequestDetailsVO serviceRequestDetailsVO=new ServiceRequestDetailsVO();
		BeanUtils.copyProperties(serviceRequestDeatil, serviceRequestDetailsVO);
		serviceRequestDetailsVO.setCommants(serviceRequestDeatil.getCommants());
		serviceRequestDetailsVO.setUserName(serviceRequestDeatil.getUser().getUsername());
		serviceRequestDetailsVO.setWebRoleName(serviceRequestDeatil.getWebRole().getWebMaster().getWebMasterName());
	  return serviceRequestDetailsVO;
	}
	
	

}
