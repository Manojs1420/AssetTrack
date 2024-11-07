
package com.titan.irgs.serviceRequest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.serviceRequest.domain.SRFileUpload;

@Repository 
 public interface SRFileUploadRepository extends JpaRepository<SRFileUpload, Long> {

	
	
	@Query("from SRFileUpload s where s.serviceRequest.serviceRequestId=:id")
	List<SRFileUpload> getSrFileByServiceRequestId(@PathVariable("id")Long id);
	
	
	
}
		 