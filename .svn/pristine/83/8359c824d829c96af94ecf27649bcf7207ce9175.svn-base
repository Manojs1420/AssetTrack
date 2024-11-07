package com.titan.irgs.inventory.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.inventory.domain.SRFileUploadMaintainance;

@Repository 
 public interface SRFileUploadMaintainanceRepo extends JpaRepository<SRFileUploadMaintainance, Long> {

	
	
	@Query(value="SELECT m.* FROM srfile_upload_maintainance m where m.maintainance_id=:id",nativeQuery = true)
	List<SRFileUploadMaintainance> getSrFileByMaintainanceId(@PathVariable("id")Long id);
	
	
	
}
		 


