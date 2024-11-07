package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Engineer;

/**
 * This is EngineerRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on engineer table
 * @author 
 *
 */
@Repository
public interface EngineerRepository extends JpaRepository<Engineer, Long>,JpaSpecificationExecutor<Engineer>
{
	@Async
	@Query(value ="FROM Engineer e where e.vendorId = :vendorId")
	List<Engineer> findByVendorId(@Param("vendorId")long vendorId);
	
	Engineer findByEngineerName(String engineerName);
	
	Engineer findByEmailId(String emailId);
	
	Engineer findByMobileNo(String mobileNo);

	List<Engineer> findByVendorId(Long id);
	
	

}
