package com.titan.irgs.serviceRequest.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.titan.irgs.serviceRequest.domain.ServiceRequest;
@EnableJpaRepositories
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long>,JpaSpecificationExecutor<ServiceRequest>{

	@Query(value = "SELECT TOP 1 cast(substring(service_request_code,len(service_request_code)-3,len(service_request_code)) as bigint)+1"
			+ " as val FROM service_request order by service_request_id desc",nativeQuery = true)
	Long getIncrementedServiceRequestCodeLastRow();

	
	
	
	
	@Query(value = "EXEC [dbo].[filterServiceRequest] @srNumber =:srNumber,@businessVerticalTypeName = :businessVerticalTypeName,@storeId=:userId,"
			+ "@StVal =:start_page,@Endval =:end_page,@assetCode = :assetCode,"
			+ "@erNumber = :erNumber,@vendorName = :vendorName,@breakDownType = :breakDownType,"
			+ "@vendorId=:vendorId,@urgency = :urgency,@service_request_type_name= :serviceRequestType,"
			+ "@ticketStatus= :ticketStatus,@runningStatus=:runningStatus,@serviceRequestDate=:serviceRequestDate,"
			+ "@serviceRequestClosedDate=:serviceRequestClosedDate,@vendorCode=:vendorCode,@serviceDocumentUploaded=:serviceDocumentUploaded,@department=:department",nativeQuery = true)
	
	List<ServiceRequest> getFilteredUsingProcedure(@Param("srNumber")String srNumber,@Param("businessVerticalTypeName") String businessVerticalTypeName,
			@Param("userId") String storeIds,@Param("assetCode") String assetCode,@Param("erNumber")String erNumber, @Param("vendorName")String vendorName, 
			@Param("breakDownType")String breakDownType, @Param("urgency")String urgency,@Param("vendorId")Long vendorId,@Param("serviceRequestType") String serviceRequestType,
			@Param("ticketStatus") String ticketStatus,@Param("runningStatus") String runningStatus,@Param("serviceRequestDate") String serviceRequestDate, 
			@Param("serviceRequestClosedDate")String serviceRequestClosedDate, @Param("start_page")int start_page,@Param("end_page") int end_page,
			@Param("vendorCode")String vendorCode,@Param("serviceDocumentUploaded")String serviceDocumentUploaded,@Param("department") String department);



	@Query(value = "EXEC [dbo].[filterServiceRequest_count] @srNumber =:srNumber,"
			+ "@businessVerticalTypeName = :businessVerticalTypeName,@storeId=:storeId,"
			+ "@assetCode = :assetCode,@erNumber = :erNumber,@vendorName = :vendorName,"
			+ "@breakDownType = :breakDownType,"
			+ "@vendorId=:vendorId,@urgency = :urgency,@service_request_type_name=:serviceRequestType,"
			+ "@ticketStatus=:ticketStatus,@runningStatus=:runningStatus,@serviceRequestDate=:serviceRequestDate,"
			+ "@serviceRequestClosedDate=:serviceRequestClosedDate,@department=:department",nativeQuery = true)

  Long count(@Param("srNumber")String srNumber,@Param("businessVerticalTypeName") String businessVerticalTypeName,
			@Param("storeId") String storeId,@Param("assetCode") String assetCode,@Param("erNumber")String erNumber, @Param("vendorName")String vendorName, 
			@Param("breakDownType")String breakDownType,@Param("vendorId")Long vendorId, @Param("urgency")String urgency,@Param("serviceRequestType") String serviceRequestType,
			@Param("ticketStatus") String ticketStatus,@Param("runningStatus") String runningStatus,@Param("serviceRequestDate") String serviceRequestDate, 
			@Param("serviceRequestClosedDate")String serviceRequestClosedDate,@Param("department") String department);




	@Query(value = "if exists(SELECT 1 FROM service_request where er_number =:erNumber) "
						+ "begin "
							+ "select case when exists (SELECT 1 FROM service_request "
							+ "where (er_number=:erNumber and ticket_status is  null) or (er_number=:erNumber and ticket_status = 'open'))"
							+ "then cast(1 as bit) else cast(0 as bit) end as dataExist;"
						+ "end "
				+ "else "
						+ "begin "
							+ "select cast(0 as bit) as dataExist;"
						+ " end",nativeQuery = true)
	boolean checkIfErnumberAndStatusClosed(@Param("erNumber")String erNumber);


	ServiceRequest findByErNumber(String erNumber);



	
	//@Query(value="select q1.service_request_id,q1.escalation_level,q1.EscDate,dbo.ConcatenateToEmail(q1.sremailescalation_id) as TOemail,dbo.ConcatenateCCEmail(q1.sremailescalation_id) as CCEmail from (select sr.service_request_id,sr.service_request_code,sreml.escalation_level,sreml.sremailescalation_id ,sr.asset_web_master_id,service_request_date,DATEADD(day, sreml.days, convert(date,service_request_date)) as EscDate, FORMAT (getdate(), 'yyyy-MM-dd') as today,ROW_NUMBER() OVER (PARTITION BY sr.service_request_id Order by sreml.sremailescalation_id desc ) as Seq    from service_request sr lEFT JOIN sremailescalation sreml on sreml.vertical_id = sr.asset_web_master_id where (ticket_status = 'OPEN' or ticket_status ='REOPEN') and convert(date, DATEADD(day, sreml.days, service_request_date)) <= convert(date,getdate())) as q1 where q1.Seq = 1 and q1.EscDate = today",nativeQuery = true)
	/*
	 * @Query(
	 * value="select q1.service_request_id,q1.escalation_level,q1.EscDate," +
	 * "dbo.ConcatenateToEmail(q1.sremailescalation_id,q1.user_id) as TOemail,"
	 * +
	 * "dbo.ConcatenateCCEmail(q1.sremailescalation_id,q1.user_id) as CCEmail ,q1.service_request_code,q1.service_request_date, q1.asset_web_master_id,q1.today,q1.sremailescalation_id"
	 * +
	 * "from (select sr.service_request_id,sr.service_request_code,sreml.escalation_level,sreml.sremailescalation_id ,sd.user_id,"
	 * + "sr.asset_web_master_id,service_request_date," +
	 * "DATEADD(day, sreml.days-1, convert(date,service_request_date)) as EscDate, FORMAT (getdate(), 'yyyy-MM-dd') as today,"
	 * +
	 * "ROW_NUMBER() OVER (PARTITION BY sr.service_request_id Order by sreml.sremailescalation_id desc ) as Seq    from service_request sr"
	 * +
	 * "left join service_request_deatil sd on sr.service_request_id = sd.service_request_id"
	 * +
	 * "lEFT JOIN sremailescalation sreml on sreml.vertical_id = sr.asset_web_master_id where (ticket_status = 'OPEN' or ticket_status ='REOPEN')"
	 * +
	 * "and convert(date, DATEADD(day, sreml.days-1, service_request_date)) <= convert(date,getdate())) as q1 where q1.Seq = 1 and q1.EscDate = today"
	 * + "",nativeQuery = true)
	 */
@Query(value="select q1.service_request_id,q1.escalation_level,q1.EscDate, " + 
		"dbo.ConcatenateToEmail(q1.sremailescalation_id,q1.user_id,q1.service_request_id) as TOemail," + 
		"dbo.ConcatenateCCEmail(q1.sremailescalation_id,q1.user_id,q1.service_request_id) as CCEmail " + 
		"from (select sr.service_request_id,sr.service_request_code,sreml.escalation_level,sreml.sremailescalation_id ,sd.user_id," + 
		"sr.asset_web_master_id,service_request_date," + 
		"DATEADD(day, sreml.days-1, convert(date,service_request_date)) as EscDate, FORMAT (getdate(), 'yyyy-MM-dd') as today," + 
		"ROW_NUMBER() OVER (PARTITION BY sr.service_request_id Order by sreml.sremailescalation_id desc ) as Seq    from service_request sr" + 
		"left join service_request_deatil sd on sr.service_request_id = sd.service_request_id" + 
		"lEFT JOIN sremailescalation sreml on sreml.vertical_id = sr.asset_web_master_id where (ticket_status = 'OPEN' or ticket_status ='REOPEN')" + 
		"and convert(date, DATEADD(day, sreml.days-1, service_request_date)) <= convert(date,getdate())) as q1 where q1.Seq = 1 and q1.EscDate = today" + 
		" ",nativeQuery = true)
	List<Object[]> getValidServiceRequests();
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Notification Creation' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> getValidServiceNotification(long serviceRequestId);

	@Query(value="select web_master.web_master_name from web_master where web_master_id=:id",nativeQuery = true)
	String getwebmasterName(@Param("id")long id);

	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Advice For Payment' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> adviceForPayment(long serviceRequestId);

	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Assigning Vendor' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> AssigningVendor(long serviceRequestId);

	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Assigning Engineer' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> AssigningEngineer(long serviceRequestId);
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='PO Approval/Reject' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> POStatus(long serviceRequestId);
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='PO Approval/Reject' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> POApprovalReject(long serviceRequestId);
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Uploading the Invoice' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> UploadingtheInvoice(long serviceRequestId);
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Uploading PO' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> UploadingPO(long serviceRequestId);
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Re-Open Service' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> ReopenService(long serviceRequestId);
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Service Closed By Store' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> Servicecomplete(long serviceRequestId);

	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Uploading Service Document' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> UploadingServiceDocument(long serviceRequestId);
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Uploading Quotation' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> UploadingQuotation(long serviceRequestId);
	
	@Query(value="select q1.srnotificationemail_id, q1.activity_name,q1.service_request_id,   " + 
			"		dbo.ConcatenateToEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as TOemail,  " + 
			"		dbo.ConcatenateCCEmails(q1.srnotificationemail_id,q1.user_id,q1.service_request_id) as CCEmail   " + 
			"		from (select sr.service_request_id,sreml.activity_name,sreml.srnotificationemail_id ,sd.user_id,  " + 
			"		sr.asset_web_master_id    from service_request sr  " + 
			"		left join service_request_deatil sd on sr.service_request_id = sd.service_request_id  " + 
			"		lEFT JOIN sr_notification_email sreml on sreml.vertical_id = sr.asset_web_master_id) as q1 where q1.activity_name='Service Closed By Department Head' and q1.service_request_id=?1" + 
			"" + 
			"",nativeQuery = true)
	List<Object[]> ServiceclosedbyDepartmentHead(long serviceRequestId);

	@Query(value ="EXEC [dbo].[filterServiceRequestForReport] @fromDate =:fromDate, @toDate =:toDate, @status =:status,"
			+ " @breakDownType =:breakDownType, @stringStoreIds =:stringStoreIds, "
			+ "@businessVerticalTypeName =:businessVerticalTypeName, @vendorId =:vendorId,@department=:department",nativeQuery = true)
	List<Object[]> getAllForExportServiceRequest(@Param("fromDate")String fromDate,@Param("toDate") String toDate, @Param("status")String status,@Param("breakDownType") String breakDownType, @Param("stringStoreIds")String stringStoreIds, 
			@Param("businessVerticalTypeName") String businessVerticalTypeName,  @Param("vendorId") Long vendorId,@Param("department") String department);


	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "			count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "			count(s.service_request_id) as totalCount\r\n"
			+ "			from service_request s\r\n"
			+ "			inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "			inner join store st on st.store_id=s.store_id\r\n"
			+ "			inner join region r on r.region_id=st.region_id  \r\n"
			+ "			where wm.web_master_name=?1 OR st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)",nativeQuery=true)
	List<Object[]> getAllSrCountS(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "			count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "			count(s.service_request_id) as totalCount\r\n"
			+ "			from service_request s\r\n"
			+ "			inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "			inner join store st on st.store_id=s.store_id\r\n"
			+ "			inner join region r on r.region_id=st.region_id  \r\n"
			+ "			where wm.web_master_name=?1 AND st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)",nativeQuery=true)
	List<Object[]> getAllSrCountings(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "			count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "			count(s.service_request_id) as totalCount\r\n"
			+ "			from service_request s\r\n"
			+ "			inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "			inner join store st on st.store_id=s.store_id\r\n"
			+ "			inner join region r on r.region_id=st.region_id where wm.web_master_name=?1 ",nativeQuery=true)
	List<Object[]> getAllSrCount(String businessVerticalTypeName);
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "			count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "			count(s.service_request_id) as totalCount\r\n"
			+ "			from service_request s\r\n"
			+ "			inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "			inner join store st on st.store_id=s.store_id\r\n"
			+ "			inner join region r on r.region_id=st.region_id ",nativeQuery=true)
	List<Object[]> getAllSrCount();
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "					count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "						count(s.service_request_id) as totalCount,\r\n"
			+ "					v.vendor_id,v.vendor_name\r\n"
			+ "						from service_request s\r\n"
			+ "						inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "						inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "						where wm.web_master_name=?1\r\n"
			+ "						group by v.vendor_id,v.vendor_name",nativeQuery = true)
	List<Object[]> getSrCountForAllVendors(String businessVerticalTypeName);
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "					count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "						count(s.service_request_id) as totalCount,\r\n"
			+ "					v.vendor_id,v.vendor_name\r\n"
			+ "						from service_request s\r\n"
			+ "						inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "						inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "						group by v.vendor_id,v.vendor_name",nativeQuery = true)
	List<Object[]> getSrCountForAllVendors();
	
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "					count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "					count(s.service_request_id) as totalCount,\r\n"
			+ "						v.vendor_id,v.vendor_name\r\n"
			+ "						from service_request s\r\n"
			+ "						inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "						inner join store st on st.store_id=s.store_id\r\n"
			+ "						inner join region r on r.region_id=st.region_id  \r\n"
			+ "						inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "						where wm.web_master_name=?1 AND st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)\r\n"
			+ "						group by v.vendor_id,v.vendor_name",nativeQuery=true)
	List<Object[]> getSrCountForAllVendors(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "					count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "					count(s.service_request_id) as totalCount,\r\n"
			+ "						v.vendor_id,v.vendor_name\r\n"
			+ "						from service_request s\r\n"
			+ "						inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "						inner join store st on st.store_id=s.store_id\r\n"
			+ "						inner join region r on r.region_id=st.region_id  \r\n"
			+ "						inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "						where wm.web_master_name=?1 OR st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)\r\n"
			+ "						group by v.vendor_id,v.vendor_name",nativeQuery=true)
	List<Object[]> getSrCountForAllVendor(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
	@Query(value="select sum(sub.openCount) as openCount,sum(sub.closeCount) as CloseCount, sum(sub.totalCount) as Totalcount from ( select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "count(s.service_request_id) as totalCount\r\n"
			+ "from service_request s\r\n"
			+ "inner join web_master wm on wm.web_master_id=s.web_role_id \r\n"
			+ "union \r\n"
			+ "select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "count(m.maintainance_id) as totalCount\r\n"
			+ "from maintainance m) as sub",nativeQuery=true)
	List<Object[]> getTotalSrAndAmcSrCount();
	
	
	@Query(value ="EXEC [dbo].[filterSRCount]"
			+ "@storeCode =:storeCode, "
			+ "@businessVerticalTypeName =:businessVerticalTypeName, @vendorId =:vendorId,@region =:region",nativeQuery = true)
	List<Object[]> getAllSrCount(@Param("storeCode")String storeCode, @Param("businessVerticalTypeName") String businessVerticalTypeName,  @Param("vendorId") Long vendorId,@Param("region") List<Long> region);




	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "								r.region_id,r.region_name\r\n"
			+ "							from service_request s\r\n"
			+ "								inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "								inner join store st on st.store_id=s.store_id\r\n"
			+ "								inner join region r on r.region_id=st.region_id\r\n"
			+ "								where wm.web_master_name=?1\r\n"
			+ "							group by r.region_id,r.region_name",nativeQuery = true)
	List<Object[]> getAllSrCountForAllRegion(String businessVerticalTypeName);


	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "								r.region_id,r.region_name\r\n"
			+ "							from service_request s\r\n"
			+ "								inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "								inner join store st on st.store_id=s.store_id\r\n"
			+ "								inner join region r on r.region_id=st.region_id\r\n"
			+ "							group by r.region_id,r.region_name",nativeQuery = true)
	List<Object[]> getAllSrCountForAllRegion();

	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "								e.equipment_id,e.equipment_name\r\n"
			+ "								from service_request s\r\n"
			+ "								inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "								inner join asset a on a.asset_id=s.asset_id\r\n"
			+ "								inner join equipment e on e.equipment_id=a.equipment_id\r\n"
			+ "								where wm.web_master_name=?1\r\n"
			+ "								group by e.equipment_id,e.equipment_name",nativeQuery=true)
	List<Object[]> getAllSrCountForAllEquipment(String businessVerticalTypeName);
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "								e.equipment_id,e.equipment_name\r\n"
			+ "								from service_request s\r\n"
			+ "								inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "								inner join asset a on a.asset_id=s.asset_id\r\n"
			+ "								inner join equipment e on e.equipment_id=a.equipment_id\r\n"
			+ "								group by e.equipment_id,e.equipment_name",nativeQuery=true)
	List<Object[]> getAllSrCountForAllEquipment();
	
	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "							e.equipment_id,e.equipment_name\r\n"
			+ "							from service_request s\r\n"
			+ "							inner join asset a on a.asset_id=s.asset_id\r\n"
			+ "							inner join equipment e on e.equipment_id=a.equipment_id\r\n"
			+ "							inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "							inner join store st on st.store_id=s.store_id\r\n"
			+ "							inner join region r on r.region_id=st.region_id \r\n"
			+ "							inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "							where wm.web_master_name=?1 AND st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)\r\n"
			+ "							group by e.equipment_id,e.equipment_name",nativeQuery=true)
	List<Object[]> getAllSrCountForAllEquipment(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);

	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "							e.equipment_id,e.equipment_name\r\n"
			+ "							from service_request s\r\n"
			+ "							inner join asset a on a.asset_id=s.asset_id\r\n"
			+ "							inner join equipment e on e.equipment_id=a.equipment_id\r\n"
			+ "							inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "							inner join store st on st.store_id=s.store_id\r\n"
			+ "							inner join region r on r.region_id=st.region_id \r\n"
			+ "							inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "							where wm.web_master_name=?1 OR st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)\r\n"
			+ "							group by e.equipment_id,e.equipment_name",nativeQuery=true)
	List<Object[]> getAllSrCountForAllEquipments(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);

	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "				count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "					count(s.service_request_id) as totalCount,\r\n"
			+ "					r.region_id,r.region_name\r\n"
			+ "					from service_request s\r\n"
			+ "					inner join store st on st.store_id=s.store_id\r\n"
			+ "					inner join region r on r.region_id=st.region_id\r\n"
			+ "					group by r.region_id,r.region_name",nativeQuery = true)
	List<Object[]> getAllSrCountForAllRegion(@Nullable Specification<ServiceRequest> specification);

	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "								r.region_id,r.region_name\r\n"
			+ "								from service_request s\r\n"
			+ "								inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "								inner join store st on st.store_id=s.store_id\r\n"
			+ "								inner join region r on r.region_id=st.region_id \r\n"
			+ "								inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "								where wm.web_master_name=?1 AND st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)\r\n"
			+ "								group by r.region_id,r.region_name",nativeQuery=true)
	List<Object[]> getAllSrCountForAllRegion(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);

	@Query(value="select count(case when s.ticket_status='OPEN' then 1 end) as openCount,\r\n"
			+ "							count(case when s.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
			+ "								count(s.service_request_id) as totalCount,\r\n"
			+ "								r.region_id,r.region_name\r\n"
			+ "								from service_request s\r\n"
			+ "								inner join web_master wm on wm.web_master_id=s.asset_web_master_id\r\n"
			+ "								inner join store st on st.store_id=s.store_id\r\n"
			+ "								inner join region r on r.region_id=st.region_id \r\n"
			+ "								inner join vendor v on v.vendor_id=s.service_vendor_id\r\n"
			+ "								where wm.web_master_name=?1 OR st.store_code=?2 OR s.service_vendor_id=?3 OR r.region_id IN (?4)\r\n"
			+ "								group by r.region_id,r.region_name",nativeQuery=true)
	List<Object[]> getAllSrCountForAllRegions(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);


	@Query(value="SELECT * FROM service_request where ticket_status='CLOSE'",nativeQuery = true)
	List<ServiceRequest> getClosedServiceRequests();

	@Query(value = "EXEC [dbo].[serviceRequestCountForDashboard] @businessVerticalTypeName = :businessVerticalTypeName,"
			+ "@user1=:user1,@department=:department,@vendorCode=:vendorCode",nativeQuery = true)
	List<Object[]> getServiceRequestCountForDashboard(@Param("businessVerticalTypeName")String businessVerticalTypeName, @Param("user1") String user1, @Param("department") String department, @Param("vendorCode") String vendorCode);

}





