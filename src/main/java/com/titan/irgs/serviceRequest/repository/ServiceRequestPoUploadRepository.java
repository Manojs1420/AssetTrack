
package com.titan.irgs.serviceRequest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.serviceRequest.domain.ServiceRequestPoUploadDomain;

 @Repository 
 public interface ServiceRequestPoUploadRepository extends JpaRepository<ServiceRequestPoUploadDomain, Long> {

	
	
	@Query("from ServiceRequestPoUploadDomain s where s.serviceRequest.serviceRequestId=:id")
	List<ServiceRequestPoUploadDomain> getServiceRequestPoByServiceRequestId(@PathVariable("id")Long id);

	
	
	
	
}
		 