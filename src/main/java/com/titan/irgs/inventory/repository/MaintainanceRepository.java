package com.titan.irgs.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.inventory.domain.Maintainance;

@Repository
public interface MaintainanceRepository extends JpaRepository<Maintainance, Long>,JpaSpecificationExecutor<Maintainance> {

	
	
	 Maintainance findByMaintainanceId(Long maintainanceId);
		@Query(value = "SELECT TOP 1 cast(substring(service_request_code,len(service_request_code)-3,len(service_request_code)) as bigint)+1"
				+ " as val FROM maintainance order by maintainance_id desc",nativeQuery = true)
		Long getIncrementedServiceRequestCodeLastRow();
		
		
		Maintainance findByWarrantyId(Long warrantyId);
		
		@Query(value="SELECT *\r\n"
				+ "  FROM maintainance where amc_id=?1",nativeQuery=true)
		Maintainance findByAmcId(Long amcId);
		
		@Query(value="SELECT *\r\n"
				+ "  FROM maintainance where amc_id=?1",nativeQuery=true)
		List<Maintainance> findByAmcIds(Long amcId);


		@Query(value = "EXEC [dbo].[filterMaintainance] @serviceRequestCode =:serviceRequestCode,@businessVerticalTypeName = :businessVerticalTypeName,@storeId=:storeId,"
				+ "@StVal =:start_page,@Endval =:end_page,@assetCode = :assetCode,"
				+ "@erNo = :erNo,@vendorName = :vendorName,@farNo = :farNo,"
				+ "@vendorId=:vendorCode,@service_request_type_name= :serviceRequestType,"
				+ "@ticketStatus= :ticketStatus,@runningStatus=:runningStatus,@serviceRequestDate=:serviceRequestDate,"
				+ "@serviceRequestClosedDate=:serviceRequestClosedDate",nativeQuery = true)
		List<Maintainance> getFilteredUsingProcedure(@Param("serviceRequestCode")String serviceRequestCode,@Param("businessVerticalTypeName") String businessVerticalTypeName,
				@Param("storeId") String storeIds,@Param("assetCode")  String assetCode,@Param("erNo") String erNo,@Param("vendorName") String vendorName,
				@Param("farNo") String farNo,
				@Param("serviceRequestType") String serviceRequestType,@Param("vendorCode") String vendorCode,@Param("ticketStatus") String ticketStatus,
				@Param("runningStatus") String runningStatus,
				@Param("serviceRequestDate") String serviceRequestDate,@Param("serviceRequestClosedDate") String serviceRequestClosedDate,@Param("start_page") int start_page,@Param("end_page") int end_page);
		
		@Query(value =  "EXEC [dbo].[filterMaintainance_count] @serviceRequestCode =:serviceRequestCode,@businessVerticalTypeName = :businessVerticalTypeName,@storeId=:storeId,"
				+ "@assetCode = :assetCode,"
				+ "@erNo = :erNo,@vendorName = :vendorName,@farNo = :farNo,"
				+ "@vendorId=:vendorCode,@service_request_type_name= :serviceRequestType,"
				+ "@ticketStatus= :ticketStatus,@runningStatus=:runningStatus,@serviceRequestDate=:serviceRequestDate,"
				+ "@serviceRequestClosedDate=:serviceRequestClosedDate",nativeQuery = true)


		Long count(@Param("serviceRequestCode")String serviceRequestCode,@Param("businessVerticalTypeName") String businessVerticalTypeName,
				@Param("storeId") String storeIds,@Param("assetCode")  String assetCode,@Param("erNo") String erNo,@Param("vendorName") String vendorName,
				@Param("farNo") String farNo,
				@Param("serviceRequestType") String serviceRequestType,@Param("vendorCode") String vendorCode,@Param("ticketStatus") String ticketStatus,
				@Param("runningStatus") String runningStatus,
				@Param("serviceRequestDate") String serviceRequestDate,@Param("serviceRequestClosedDate") String serviceRequestClosedDate);
		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Notification Creation'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> NotificationCreation(long maintainanceId);

		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Service Closed By RE'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> ServiceClosedByRE(long maintainanceId);

		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Service Closed By Store'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> ServiceClosedByStore(long maintainanceId);

		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Service Closed By Vendor'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> ServiceClosedByVendor(long maintainanceId);

		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Assigning Engineer'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> AssigningEngineer(long maintainanceId);

		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Uploading Service Document'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> UploadingServiceDocument(long maintainanceId);

		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Uploading Quotation Document'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> UploadingQuotationDocument(long maintainanceId);
		
		@Query(value="select q1.amcnotification_id, q1.activity_name,q1.maintainance_id,    \r\n" + 
				"					dbo.MaintainanceEmailTO(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as TOemail,   \r\n" + 
				"					dbo.MaintainanceEmailCC(q1.amcnotification_id,q1.user_id,q1.maintainance_id) as CCEmail    \r\n" + 
				"					from (select sr.maintainance_id,sreml.activity_name,sreml.amcnotification_id ,a.user_id,   \r\n" + 
				"					a.web_master_id    from maintainance sr \r\n" + 
				"					left join amcinventory a on a.amc_id=sr.amc_id  \r\n" + 
				"					left JOIN amc_notification sreml on sreml.vertical_id = a.asset_web_master_id\r\n" + 
				"					) as q1 where q1.activity_name='Maintainance Alert'  and q1.maintainance_id=?1",nativeQuery = true)
		List<Object[]> MaintainanceAlert(long maintainanceId);
		
		@Query(value="SELECT m.* FROM maintainance m where m.warranty_id=?1",nativeQuery=true)
		Maintainance getMaintainanceByWarranyId(Long warrantyId);
		
		@Query(value="SELECT warranty_id FROM amc_warranty_details where inventory_id=?1 and ticket_status!='OPEN' and ticket_status!='CLOSE'",nativeQuery=true)
		List<Long> getWarrantyNotOpen(Long inventoryId);
		
		@Query(value ="EXEC [dbo].[filterAMCServiceRequestForReport] @fromDate =:fromDate, @toDate =:toDate, @status =:status,"
				+ " @breakDownType =:breakDownType, @stringStoreIds =:stringStoreIds, "
				+ "@businessVerticalTypeName =:businessVerticalTypeName, @vendorId =:vendorId,@region =:region",nativeQuery = true)
		List<Object[]> getAllForExportAMCServiceRequest(@Param("fromDate")String fromDate,@Param("toDate") String toDate, @Param("status")String status,@Param("breakDownType") String breakDownType, @Param("stringStoreIds")String stringStoreIds, @Param("businessVerticalTypeName") String businessVerticalTypeName,  @Param("vendorId") Long vendorId,@Param("region") String region);

		
		
	
			
		@Query(value = "EXEC [dbo].[filterMaintainance] @serviceRequestCode =:serviceRequestCode,@businessVerticalTypeName = :businessVerticalTypeName,@storeId=:storeId,"
				+ "@StVal =:start_page,@Endval =:end_page,@assetCode = :assetCode,"
				+ "@erNo = :erNo,@vendorName = :vendorName,@farNo = :farNo,"
				+ "@vendorCode=:vendorCode,@serviceRequestType= :serviceRequestType,"
				+ "@ticketStatus= :ticketStatus,@runningStatus=:runningStatus,@serviceRequestDate=:serviceRequestDate,"
				+ "@serviceRequestClosedDate=:serviceRequestClosedDate,@region=:region,@department=:department",nativeQuery = true)
		List<Maintainance> getAllMaintainanceforauto(@Param("serviceRequestCode")String serviceRequestCode,@Param("businessVerticalTypeName") String businessVerticalTypeName,
				@Param("storeId") String storeIds,@Param("assetCode")  String assetCode,@Param("erNo") String erNo,@Param("vendorName") String vendorName,
				@Param("farNo") String farNo,
				@Param("serviceRequestType") String serviceRequestType,@Param("vendorCode") String vendorCode,@Param("ticketStatus") String ticketStatus,
				@Param("runningStatus") String runningStatus,
				@Param("serviceRequestDate") String serviceRequestDate,@Param("serviceRequestClosedDate") String serviceRequestClosedDate,
				@Param("region")  List<Long> region ,@Param("start_page") int start_page,@Param("end_page") int end_page,@Param("department") String department);


		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "				count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "				count(m.maintainance_id) as totalCount\r\n"
				+ "				from maintainance m\r\n"
				+ "				inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "				inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "				where wm.web_master_name=?1 AND st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)",nativeQuery=true)
		List<Object[]> getAllAmcSrCount(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "				count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "				count(m.maintainance_id) as totalCount\r\n"
				+ "				from maintainance m\r\n"
				+ "				inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "				inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "				where wm.web_master_name=?1 OR st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)",nativeQuery=true)
		List<Object[]> getAllAmcSrCounts(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "				count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "				count(m.maintainance_id) as totalCount\r\n"
				+ "				from maintainance m\r\n"
				+ "				inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "				inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "				where wm.web_master_name=?1",nativeQuery=true)
		List<Object[]> getAllAmcSrCount(String businessVerticalTypeName);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "				count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "				count(m.maintainance_id) as totalCount\r\n"
				+ "				from maintainance m\r\n"
				+ "				inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "				inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n",nativeQuery=true)
		List<Object[]> getAllAmcSrCount();
		
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "							count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "								count(m.maintainance_id) as totalCount,\r\n"
				+ "									v.vendor_id,v.vendor_name\r\n"
				+ "									from maintainance m\r\n"
				+ "									inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "									inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "									inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "									where wm.web_master_name=?1\r\n"
				+ "									group by v.vendor_id,v.vendor_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllVendors(String businessVerticalTypeName);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "							count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "								count(m.maintainance_id) as totalCount,\r\n"
				+ "									v.vendor_id,v.vendor_name\r\n"
				+ "									from maintainance m\r\n"
				+ "									inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "									inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "									inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "									group by v.vendor_id,v.vendor_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllVendors();
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "						count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "								count(m.maintainance_id) as totalCount,\r\n"
				+ "								v.vendor_id,v.vendor_name\r\n"
				+ "								from maintainance m\r\n"
				+ "								inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "								inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								inner join region r on r.region_id=st.region_id\r\n"
				+ "								inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "								where wm.web_master_name=?1 AND st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)\r\n"
				+ "								group by v.vendor_id,v.vendor_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllVendors(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "						count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "								count(m.maintainance_id) as totalCount,\r\n"
				+ "								v.vendor_id,v.vendor_name\r\n"
				+ "								from maintainance m\r\n"
				+ "								inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "								inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								inner join region r on r.region_id=st.region_id\r\n"
				+ "								inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "								where wm.web_master_name=?1 OR st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)\r\n"
				+ "								group by v.vendor_id,v.vendor_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllVendor(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "											count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "											count(m.maintainance_id) as totalCount,\r\n"
				+ "												r.region_id,r.region_name\r\n"
				+ "											from maintainance m\r\n"
				+ "											inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "											inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "											where wm.web_master_name=?1\r\n"
				+ "											group by r.region_id,r.region_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllRegion(String businessVerticalTypeName);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "											count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "											count(m.maintainance_id) as totalCount,\r\n"
				+ "												r.region_id,r.region_name\r\n"
				+ "											from maintainance m\r\n"
				+ "											inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "											inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "											group by r.region_id,r.region_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllRegion();
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "										count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "											count(m.maintainance_id) as totalCount,\r\n"
				+ "												r.region_id,r.region_name\r\n"
				+ "											from maintainance m\r\n"
				+ "								inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "								inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "								where wm.web_master_name=?1 AND st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)\r\n"
				+ "								group by r.region_id,r.region_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllRegion(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "										count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "											count(m.maintainance_id) as totalCount,\r\n"
				+ "												r.region_id,r.region_name\r\n"
				+ "											from maintainance m\r\n"
				+ "								inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								left join region r on r.region_id=st.region_id \r\n"
				+ "								inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "								where wm.web_master_name=?1 OR st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)\r\n"
				+ "								group by r.region_id,r.region_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllRegions(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "											count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "											count(m.maintainance_id) as totalCount,\r\n"
				+ "												e.equipment_id,e.equipment_name\r\n"
				+ "											from maintainance m\r\n"
				+ "											inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "											inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "											inner join asset a on a.asset_id=am.asset_id\r\n"
				+ "											inner join equipment e on e.equipment_id=a.equipment_id\r\n"
				+ "											where wm.web_master_name=?1\r\n"
				+ "											group by e.equipment_id,e.equipment_name",nativeQuery = true)
		List<Object[]> getAllAmcSrCountForAllEquipment(String businessVerticalTypeName);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "											count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "											count(m.maintainance_id) as totalCount,\r\n"
				+ "												e.equipment_id,e.equipment_name\r\n"
				+ "											from maintainance m\r\n"
				+ "											inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "											inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "											inner join asset a on a.asset_id=am.asset_id\r\n"
				+ "											inner join equipment e on e.equipment_id=a.equipment_id\r\n"
				+ "											group by e.equipment_id,e.equipment_name",nativeQuery = true)
		List<Object[]> getAllAmcSrCountForAllEquipment();
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "										count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "									count(m.maintainance_id) as totalCount,\r\n"
				+ "												e.equipment_id,e.equipment_name\r\n"
				+ "										from maintainance m\r\n"
				+ "								inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "								inner join asset a on a.asset_id=am.asset_id\r\n"
				+ "								inner join equipment e on e.equipment_id=a.equipment_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								inner join region r on r.region_id=st.region_id\r\n"
				+ "								inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "								where wm.web_master_name=?1 AND st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)\r\n"
				+ "								group by e.equipment_id,e.equipment_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllEquipment(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);

		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "										count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "									count(m.maintainance_id) as totalCount,\r\n"
				+ "												e.equipment_id,e.equipment_name\r\n"
				+ "										from maintainance m\r\n"
				+ "								inner join amcinventory am on am.amc_id=m.amc_id\r\n"
				+ "								inner join asset a on a.asset_id=am.asset_id\r\n"
				+ "								inner join equipment e on e.equipment_id=a.equipment_id\r\n"
				+ "								inner join users u on u.id=am.user_id\r\n"
				+ "								left join store st on st.store_code=u.username\r\n"
				+ "								inner join region r on r.region_id=st.region_id\r\n"
				+ "								inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
				+ "								inner join vendor v on v.vendor_id=m.vendor_id\r\n"
				+ "								where wm.web_master_name=?1 OR st.store_code=?2 OR m.vendor_id=?3 OR r.region_id IN (?4)\r\n"
				+ "								group by e.equipment_id,e.equipment_name",nativeQuery=true)
		List<Object[]> getAllAmcSrCountForAllEquipments(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
		
		@Query(value="select count(case when m.ticket_status='OPEN' then 1 end) as openCount,\r\n"
				+ "							count(case when m.ticket_status='CLOSE' then 1 end) as closeCount,\r\n"
				+ "							count(m.maintainance_id) as totalCount\r\n"
				+ "							from maintainance m",nativeQuery = true)
		List<Object[]> getAllAmcSrCountwithNew(Specification<Maintainance> specification);
		
		@Query(value = "EXEC [dbo].[amcServiceRequestCountForDashboard] @businessVerticalTypeName = :businessVerticalTypeName,@department=:department,@vendorCode=:vendorCode",nativeQuery = true)
		List<Object[]> getAMCServiceRequestCountForDashboard(@Param("businessVerticalTypeName") String businessVerticalType,@Param("department") String department,@Param("vendorCode") String vendorCode);

}
