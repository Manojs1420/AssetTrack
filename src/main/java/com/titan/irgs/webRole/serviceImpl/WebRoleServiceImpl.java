package com.titan.irgs.webRole.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;
import com.titan.irgs.webRole.service.IWebRoleService;

@Service
public class WebRoleServiceImpl implements IWebRoleService{

	@Autowired
	WebRoleRepo webRoleRepo;
	
	
	@Override
	public WebRole save(WebRole webRole) {
		// TODO Auto-generated method stub
		return webRoleRepo.save(webRole);
	}

	@SuppressWarnings("serial")
	@Override
	public List<WebRole> getAll(String roleName, String businessVerticalTypeName, String reportingTo, String operationType,Pageable page) {
		// TODO Auto-generated method stub
		int end_page=page.getPageNumber() * page.getPageSize();
		int start_page=end_page-(page.getPageSize()-1);
		
		
		return webRoleRepo.filterWebRoles(roleName,businessVerticalTypeName,reportingTo,operationType,start_page,end_page);
	}

	@Override
	public WebRole getById(Long id) {
		// TODO Auto-generated method stub
		
		return webRoleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("The request id not found"));
	}

	@Override
	public List<WebRole> getAlRolesUsingBussinessverticalId(Long id) {
		// TODO Auto-generated method stub
		return webRoleRepo.getAlRolesUsingBussinessverticalId(id);
	}

	@Override
	public WebRole getByWebRoleUsingRoleIdAndVerticalId(Long roleId, Long webMasterId) {
		// TODO Auto-generated method stub
		return webRoleRepo.getByWebRoleUsingRoleIdAndVerticalId(roleId,webMasterId);
	}

	@Override
	public Long count(String roleName, String businessVerticalTypeName, String reportingTo, String operationType) {
		// TODO Auto-generated method stub
		return webRoleRepo.filterWebRolesCount(roleName,businessVerticalTypeName,reportingTo,operationType);
	}

	@Override
	public List<WebRole> getAlRolesUsingBussinessverticalIdAndDepartmentId(Long verticalId, Long departmentId) {
		// TODO Auto-generated method stub
		return webRoleRepo.getstorewebroleusingdepartment(verticalId,departmentId);
	}

}
