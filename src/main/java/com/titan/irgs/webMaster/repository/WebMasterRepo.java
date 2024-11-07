package com.titan.irgs.webMaster.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.titan.irgs.webMaster.domain.WebMaster;



public interface WebMasterRepo extends JpaRepository<WebMaster, Long>{

	WebMaster findByWebMasterId(Long webMasterId);

	@Query(value = "select * from web_master where web_master_name=:businessVerticalName",nativeQuery = true)
	WebMaster findByWebMasterName(@Param("businessVerticalName")String businessVerticalName);

	static String getWebMasterName() {
		// TODO Auto-generated method stub
		return null;
	} 
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO [dbo].[web_role] VALUES (1,NULL,'True',1,NULL,3,3,:webMasterId)",nativeQuery = true)
	void insertByVerticalId(@Param("webMasterId")Long webMasterId);

	@Query(value = "select * from web_master where web_master_id=?1",nativeQuery = true)
	List<WebMaster> findByWebMasterIds(Long long1);
	
}
