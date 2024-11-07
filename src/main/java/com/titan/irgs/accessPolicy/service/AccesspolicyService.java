package com.titan.irgs.accessPolicy.service;

import java.util.List;

import com.titan.irgs.accessPolicy.domain.AccesspolicyDomain;

public interface AccesspolicyService {

	AccesspolicyDomain save(AccesspolicyDomain accesspolicyDomain);

	List<AccesspolicyDomain> getAll(String businessVerticalName, String moduleName,String roleName);

	AccesspolicyDomain getById(Long id);

	List<AccesspolicyDomain> getAccesspolicyByUsingRoleId(Long id);

	void deleteById(Long id);

	AccesspolicyDomain update(AccesspolicyDomain accesspolicyDomain);

	AccesspolicyDomain getByAccessPolicyByModuleIdAndSubModuleIdAndWebRoleId(Long moduleId, Long subModuleId,
			Long webRoleId);

	List<AccesspolicyDomain> getByModuleIdAndWebRoleId(Long moduleId, Long webRoleId);

}
