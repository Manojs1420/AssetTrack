package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long>,JpaSpecificationExecutor<Department>{

	Department findByWebMasterWebMasterIdAndDepartmentName(Long webMasterId, String departmentName);

	Department findByDepartmentId(Long departmentId);

	@Query(value="select * from department d where d.web_master_id=:webMasterId",nativeQuery = true)
	Department findByWebMasterWebMasterId1(@Param("webMasterId")Long webMasterId);
	
	List<Department> findByWebMasterWebMasterId(Long webMasterId);

	@Query(value="select * from department d where d.web_master_id=:webMasterId /*#pageable*/",countQuery="SELECT count(*) FROM department d where d.web_master_id=:webMasterId",nativeQuery = true)
	Page<Department> findByWebMasterId(@Param("webMasterId")Long webMasterId, Pageable page);

	Department findByDepartmentName(String departmentName);

}
