package com.titan.irgs.role.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.role.domain.RoleWiseDepartments;

@Repository
@Transactional
public interface RoleWiseDepartmentsRepo extends JpaRepository<RoleWiseDepartments, Long>,JpaSpecificationExecutor<RoleWiseDepartments>{

	RoleWiseDepartments findByRoleWiseDepartmentsId(Long roleWiseDepartmentsId);
	
	@Query(value="SELECT department_ids FROM role_wise_departments where web_role_id=?1",nativeQuery = true)
	List<Long> findDepartmentsIdsByWebRoleId(Long webRoleId);

	@Modifying
	@Query(value = "delete FROM role_wise_departments where web_role_id=:webRoleId and department_ids=:departmentid",nativeQuery = true)
	void deleteRoleWiseDepartmentsByUsingWebRoleIdAndDepartmentId(@Param("webRoleId") Long webRoleId,@Param("departmentid") Long departmentid);

	@Query(value="SELECT * FROM role_wise_departments where web_role_id=?1",nativeQuery = true)
	List<RoleWiseDepartments> getByWebRoleId(Long webRoleId);
	
	@Query(value="SELECT * FROM role_wise_departments where web_role_id=:webRoleId and department_ids=:departmentid",nativeQuery = true)
	RoleWiseDepartments findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(@Param("webRoleId") Long webRoleId,@Param("departmentid") Long departmentid);
	
	@Query(value ="select rd.* from role_wise_departments rd\r\n"
			+ "inner join web_role wr on wr.web_role_id=rd.web_role_id\r\n"
			+ "inner join role r on r.role_id=wr.role_id\r\n"
			+ "inner join department d on d.department_id=rd.department_ids\r\n"
			+ "inner join web_master wm on wm.web_master_id=d.web_master_id\r\n"
			+ "where r.role_name='inventory user' and wm.web_master_id=:webMasterId and d.department_id=:departmentId",nativeQuery =true)
	RoleWiseDepartments getInventoryUserwebroleusingverticalAndDepartment(@Param("webMasterId")Long webMasterId,@Param("departmentId")Long departmentId);	
	

}
