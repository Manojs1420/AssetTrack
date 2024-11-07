package com.titan.irgs.accessPolicy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.accessPolicy.domain.AccesspolicyDomain;
import com.titan.irgs.accessPolicy.repository.AccesspolicyRepo;
import com.titan.irgs.accessPolicy.service.AccesspolicyService;
import com.titan.irgs.customException.ResourceNotFoundException;

@Service
public class AccesspolicyServiceImpl implements AccesspolicyService{
    @Autowired
    AccesspolicyRepo accesspolicyRepo;
	
	@Override
	public AccesspolicyDomain save(AccesspolicyDomain accesspolicyDomain) {
		
		return accesspolicyRepo.save(accesspolicyDomain);
	}

	@Override
	public List<AccesspolicyDomain> getAll(String businessVerticalName, String moduleName,String roleName) {
		return accesspolicyRepo.findAll((root,query,criteriaBuilder)-> {
				List<Predicate> predicates = new ArrayList<>();

				if (businessVerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webRole").join("webMaster").get("webMasterName"),"%" + businessVerticalName + "%")));

					}
				if (moduleName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("module").get("moduleName"),"%" + moduleName + "%")));

				}
				
				if (roleName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webRole").join("role").get("roleName"),"%" + roleName + "%")));

					}
					
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			});
	}

	@Override
	public AccesspolicyDomain getById(Long id) {
		// TODO Auto-generated method stub
		return accesspolicyRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("The id not found"));
	}

	@Override
	public List<AccesspolicyDomain> getAccesspolicyByUsingRoleId(Long id) {
		// TODO Auto-generated method stub
		return accesspolicyRepo.getAccesspolicyByUsingRoleId(id);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		 accesspolicyRepo.deleteById(id);

	}

	@Override
	public AccesspolicyDomain update(AccesspolicyDomain accesspolicyDomain) {
		// TODO Auto-generated method stub
		if(accesspolicyRepo.getOne(accesspolicyDomain.getAccesspolicyId())==null)
		  {
			throw new ResourceNotFoundException("The requested id not found.");
		  }
		return accesspolicyRepo.save(accesspolicyDomain);
	}

	@Override
	public AccesspolicyDomain getByAccessPolicyByModuleIdAndSubModuleIdAndWebRoleId(Long moduleId, Long subModuleId,
			Long webRoleId) {
		// TODO Auto-generated method stub
		return accesspolicyRepo.getByAccessPolicyByModuleIdAndSubModuleIdAndWebRoleId(moduleId,subModuleId,webRoleId);
	}

	@Override
	public List<AccesspolicyDomain> getByModuleIdAndWebRoleId(Long moduleId, Long webRoleId) {
		// TODO Auto-generated method stub
		return accesspolicyRepo.getByModuleIdAndWebRoleId(moduleId,webRoleId);
	}

	

}
