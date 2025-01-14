
package com.titan.irgs.serviceRequest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.domain.ServiceRequestUpload;

@Repository 
 public interface ServiceRequestUploadRepository extends JpaRepository<ServiceRequestUpload, Long>,JpaSpecificationExecutor<ServiceRequestUpload> {

	
	
	@Query("from ServiceRequestUpload s where s.serviceRequest.serviceRequestId=:id")
	List<ServiceRequestUpload> getSrUploadByServiceRequestId(@PathVariable("id")Long id);
	
	
	@Query("from ServiceRequestUpload s where s.serviceRequest.serviceRequestId=:id")
	ServiceRequestUpload getByServiceRequestId(@PathVariable("id")Long id);
	
	List<ServiceRequestUpload> findByServiceRequest(ServiceRequest serviceRequest);
}
		 