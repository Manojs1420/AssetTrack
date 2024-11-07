package com.titan.irgs.webRole.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.titan.irgs.webRole.domain.WebRole;

public interface WebRoleRepo extends JpaRepository<WebRole, Long>,JpaSpecificationExecutor<WebRole>{

	@Query(value ="SELECT * FROM web_role where access_master_id=:id",nativeQuery =true )
	List<WebRole> getAlRolesUsingBussinessverticalId(@Param("id")Long id);

    WebRole findByWebRoleId(Long id);

    WebRole findByRoleRoleId(Long roleId);
    
    WebRole findByRoleRoleIdAndWebMasterWebMasterId(Long roleId,Long webMasterId);

	@Query(value ="SELECT * FROM web_role where role_id=:roleId and access_master_id=:webMasterId",nativeQuery =true )
	WebRole getByWebRoleUsingRoleIdAndVerticalId(@Param("roleId")Long roleId, @Param("webMasterId")Long webMasterId);

	@Query(value ="SELECT wr.* FROM [web_role] wr "
			+ "inner join web_master wm on wm.web_master_id=wr.access_master_id  "
			+ "inner join role r on r.role_id=wr.role_id "
			+ "where wm.web_master_id=:webMasterId and r.role_name='store'; ",nativeQuery =true)
	WebRole getstorewebroleusingvertical(@Param("webMasterId")Long webMasterId);

	@Query(value ="select rd.* from role_wise_departments rd\r\n"
			+ "inner join web_role wr on wr.web_role_id=rd.web_role_id\r\n"
			+ "inner join role r on r.role_id=wr.role_id\r\n"
			+ "inner join department d on d.department_id=rd.department_ids\r\n"
			+ "inner join web_master wm on wm.web_master_id=d.web_master_id\r\n"
			+ "where r.role_name='inventory user' and d.department_id=:departmentId and wm.web_master_id=:webMasterId",nativeQuery =true)
	WebRole getstorewebroleusingverticalAndDepartment(@Param("webMasterId")Long webMasterId,@Param("departmentId")Long departmentId);	
	
	@Query(value ="EXEC [dbo].[webRoleFilter] @roleName =:roleName,@businessVerticalTypeName =:businessVerticalTypeName,"
			+ "@ReportingTo =:reportingTo,@OperationType =:operationType,@StVal =:start_page, @Endval =:end_page",nativeQuery =true)
	
	List<WebRole> filterWebRoles(@Param("roleName")String roleName, @Param("businessVerticalTypeName")String businessVerticalTypeName,
			@Param("reportingTo") String reportingTo,@Param("operationType")String operationType, 
			@Param("start_page")int start_page, @Param("end_page")int end_page);
	
	@Query(value ="EXEC [dbo].[webRoleFilterCount] @roleName =:roleName,@businessVerticalTypeName =:businessVerticalTypeName,"
			+ "@ReportingTo =:reportingTo,@OperationType =:operationType",nativeQuery =true)
	Long  filterWebRolesCount(@Param("roleName")String roleName, @Param("businessVerticalTypeName")String businessVerticalTypeName,
			@Param("reportingTo") String reportingTo,@Param("operationType")String operationType);

	@Query(value="SELECT wr.* FROM web_role wr\r\n"
			+ "inner join role_wise_departments rd on rd.web_role_id=wr.web_role_id inner join role r on r.role_id=wr.role_id\r\n"
			+ "inner join department d on d.department_id=rd.department_ids\r\n"
			+ " inner join web_master wm on wm.web_master_id=d.web_master_id"
			+ " where wm.web_master_id=:verticalId and d.department_id=:departmentId",nativeQuery = true)
	List<WebRole> getstorewebroleusingdepartment(@Param("verticalId") Long verticalId,@Param("departmentId") Long departmentId);


}
