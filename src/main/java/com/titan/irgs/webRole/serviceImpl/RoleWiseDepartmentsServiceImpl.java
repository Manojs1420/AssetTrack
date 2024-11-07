package com.titan.irgs.webRole.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.service.RoleWiseDepartmentsService;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;

@Service
public class RoleWiseDepartmentsServiceImpl implements RoleWiseDepartmentsService{
	
	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;

	@Override
	public List<Long> getAllDepartmentIdsInRoleWiseUsingWebRoleId(Long webRoleId) {
		// TODO Auto-generated method stub
		return roleWiseDepartmentsRepo.findDepartmentsIdsByWebRoleId(webRoleId);
	}

	@Override
	public void deleteRoleWiseDepartmentsByUsingWebRoleIdAndDepartmentId(Long webRoleId, Long departmentid) {
		roleWiseDepartmentsRepo.deleteRoleWiseDepartmentsByUsingWebRoleIdAndDepartmentId(webRoleId,departmentid);
	}

}
