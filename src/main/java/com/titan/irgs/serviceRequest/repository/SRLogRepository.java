/*
 * package com.titan.irgs.serviceRequest.repository;
 * 
 * 
 * import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository;
 * 
 * 
 * 
 *//**
	 * This is SRLogRepository(ServiceRequestLogRepository) interface(implemented on
	 * Jpa framework) which are responsible to perform CRUD operation on 'sr_log'
	 * table
	 * 
	 * @author birendra
	 *
	 *//*
		 * @Repository public interface SRLogRepository extends JpaRepository<SRLog,
		 * Long> {
		 * 
		 * @Query(value =
		 * "select * from titan_epd.sr_log where service_request_id =:serviceRequestId ORDER BY created_on DESC "
		 * ,nativeQuery = true) List<SRLog>
		 * findByServiceRequestId(@Param("serviceRequestId") Long serviceRequestId);
		 * 
		 * @Query(value
		 * ="SELECT * FROM sr_log s where s.service_request_id=:serviceRequestId and s.vendor_visit_date is not null "
		 * + "ORDER BY sr_log_id ",nativeQuery = true) List<SRLog>
		 * findByVisitDateServiceRequestId(@Param("serviceRequestId") Long
		 * serviceRequestId);
		 * 
		 * @Query(value =
		 * "from SRLog s where s.serviceRequestId=:serviceRequestId and s.currentSrStatus=:currentSrStatus"
		 * + "ORDER BY s.serviceRequestId,s.vendor_visit_date DESC LIMIT 1") SRLog
		 * getSRLogOnServiceRequestIdAndCurrentSrStatus(@Param("serviceRequestId") Long
		 * serviceRequestId,@Param("currentSrStatus")String currentSrStatus);
		 * 
		 * }
		 * 
		 */