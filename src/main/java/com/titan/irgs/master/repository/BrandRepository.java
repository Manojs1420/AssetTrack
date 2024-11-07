package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Brand;

/**
 * This is BrandRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on brand table
 * 
 *
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>,JpaSpecificationExecutor<Brand> {
	
	Brand findByBrandName(String brandName);
	
	Brand findByBrandCode(String brandCode);

	@Query(value = "SELECT w.web_master_name,b.brand_code,b.brand_name FROM brand b inner join web_master w on w.web_master_id=b.web_master_id where b.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAll(@Param("id")Long id);

	@Query(value = "SELECT a.* FROM brand a where web_master_id=:id",nativeQuery = true)
	List<Brand> findByWebmasterId(@Param("id")Long id);

	@Query(value = "SELECT w.web_master_name,b.brand_code,b.brand_name FROM brand b inner join web_master w on w.web_master_id=b.web_master_id ",nativeQuery = true)
	List<Object[]> getAll();
	
	@Query(value ="SELECT a.* FROM brand a where web_master_id=:id",nativeQuery=true)
	List<Brand> getBrandByWebMasterId(@Param("id")Long id);

	Brand findByBrandCodeAndWebMasterWebMasterId(String brandCode, Long webMasterId);
	Brand findByBrandNameAndWebMasterWebMasterId(String brandName, Long webMasterId);
}
