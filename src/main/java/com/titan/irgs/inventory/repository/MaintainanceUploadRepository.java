package com.titan.irgs.inventory.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.domain.MaintainanceUpload;

@Repository 
 public interface MaintainanceUploadRepository extends JpaRepository<MaintainanceUpload, Long>,JpaSpecificationExecutor<MaintainanceUpload> {

	@Query(value="SELECT m.* FROM maintainance_upload m where m.maintainance_id=:id",nativeQuery = true)
	List<MaintainanceUpload> getSrUploadByMaintainanceId(@PathVariable("id")Long id);

	
	@Query(value="SELECT m.* FROM maintainance_upload m where m.maintainance_id=:id",nativeQuery = true)
	MaintainanceUpload getByMaintainanceId(@PathVariable("id")Long id);

	
	List<MaintainanceUpload> findByMaintainance(Maintainance maintainance);
	
}
		 
