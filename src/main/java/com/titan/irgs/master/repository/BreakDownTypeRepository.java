package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.titan.irgs.serviceRequest.domain.BreakDownType;

/**
 * This is BreakDownTypeRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on brand table
 * 
 *
 */
@Repository
public interface BreakDownTypeRepository extends JpaRepository<BreakDownType, Long>,JpaSpecificationExecutor<BreakDownType> {
	
	BreakDownType findByBreakDownTypeName(String brandName);
	
//	BreakDownTypem findByBreakDownTypeCode(String brandCode);

	@Query(value = "SELECT w.web_master_name,b.brand_code,b.brand_name FROM brand b inner join web_master w on w.web_master_id=b.web_master_id where b.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAll(@Param("id")Long id);

	@Query(value = "SELECT a.* FROM break_down_typem a where web_master_id=:id",nativeQuery = true)
	List<BreakDownType> findByWebmasterId(@Param("id")Long id);

	@Query(value = "SELECT w.web_master_name,b.brand_code,b.brand_name FROM brand b inner join web_master w on w.web_master_id=b.web_master_id ",nativeQuery = true)
	List<Object[]> getAll();
	
	@Query(value ="SELECT a.* FROM brand a where web_master_id=:id",nativeQuery=true)
	List<BreakDownType> getBreakDownTypeByWebMasterId(@Param("id")Long id);

	BreakDownType findByBreakDownTypeNameAndWebMasterWebMasterId(String brandCode, Long webMasterId);
	//BreakDownTypem findByBreakDownNameAndWebMasterWebMasterId(String brandName, Long webMasterId);
}
