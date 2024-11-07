
package com.titan.irgs.serviceRequest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.serviceRequest.domain.ServiceRequestInvoiceDomain;

@Repository 
 public interface ServiceRequestInvoiceRepository extends JpaRepository<ServiceRequestInvoiceDomain, Long> {

	
	
	@Query("from ServiceRequestInvoiceDomain s where s.serviceRequest.serviceRequestId=:id")
	List<ServiceRequestInvoiceDomain> getSrInvoiceByServiceRequestId(@PathVariable("id")Long id);
	
	
	
}
		 