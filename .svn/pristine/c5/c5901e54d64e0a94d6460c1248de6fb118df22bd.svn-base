package com.titan.irgs.serviceRequest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.serviceRequest.domain.SRCommentsUpdate;


/**
 * @author Nikith
 *
 */
@Repository
public interface SRCommentsUpdateRepository extends JpaRepository<SRCommentsUpdate, Long> {
	
	@Query(value = "select * from sr_comment_upload where service_request_id =:serviceRequestId ORDER BY created_on DESC " ,nativeQuery = true)
	List<SRCommentsUpdate> findByServiceRequestId(@Param("serviceRequestId") Long serviceRequestId);
	

}
