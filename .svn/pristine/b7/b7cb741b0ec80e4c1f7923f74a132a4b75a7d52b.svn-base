package com.titan.irgs.accessPolicy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.titan.irgs.accessPolicy.domain.AccesspolicyDomain;

public interface AccesspolicyRepo extends JpaRepository<AccesspolicyDomain, Long>,JpaSpecificationExecutor<AccesspolicyDomain>{

	
	@Query(value = "SELECT * FROM accesspolicy_domain where web_role_id=:id",nativeQuery = true)
	List<AccesspolicyDomain> getAccesspolicyByUsingRoleId(@Param("id")Long id);

	
	@Query(value = "SELECT * FROM accesspolicy_domain a where a.module_id=:moduleId and a.sub_module_id=:subModuleId  and a.web_role_id=:webRoleId",nativeQuery = true)
	AccesspolicyDomain getByAccessPolicyByModuleIdAndSubModuleIdAndWebRoleId(@Param("moduleId")Long moduleId,@Param("subModuleId") Long subModuleId,
			@Param("webRoleId") Long webRoleId);


	@Query(value = "SELECT * FROM accesspolicy_domain a where a.module_id=:moduleId and a.web_role_id=:webRoleId",nativeQuery = true)
	List<AccesspolicyDomain> getByModuleIdAndWebRoleId(@Param("moduleId")Long moduleId,@Param("webRoleId") Long webRoleId);

	@Query(value = "SELECT a.* FROM [accesspolicy_domain] a inner join web_role w on w.web_role_id=a.web_role_id order by w.web_role_id,a.module_id",nativeQuery = true)
	List<AccesspolicyDomain> getAll();
	
	

}
