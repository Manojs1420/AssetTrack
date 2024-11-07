package com.titan.irgs.webRole.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.titan.irgs.webRole.domain.WebRole;

public interface IWebRoleService {

	WebRole save(WebRole webRole);

	List<WebRole> getAll(String roleName, String businessVerticalTypeName, String reportingTo, String operationType, Pageable pageable);

	WebRole getById(Long id);

	List<WebRole> getAlRolesUsingBussinessverticalId(Long id);

	WebRole getByWebRoleUsingRoleIdAndVerticalId(Long roleId, Long webMasterId);

	Long count(String roleName, String businessVerticalTypeName, String reportingTo, String operationType);

	List<WebRole> getAlRolesUsingBussinessverticalIdAndDepartmentId(Long verticalId, Long departmentId);

}
