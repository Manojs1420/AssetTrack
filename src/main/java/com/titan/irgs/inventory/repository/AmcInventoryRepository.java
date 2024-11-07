package com.titan.irgs.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.inventory.domain.AmcInventory;

@Repository
public interface AmcInventoryRepository extends JpaRepository<AmcInventory, Long>,JpaSpecificationExecutor<AmcInventory> {
	@Query(value="SELECT amc_id FROM amcinventory",nativeQuery=true)
	List<Long> getAllAmcIds();
	
	@Query(value = "SELECT a.* FROM amcinventory a where amc_id=:id",nativeQuery = true)
	List<AmcInventory> findByAmcId(@Param("id")Long id);	
	
	@Query(value = "SELECT a.* FROM amcinventory a where amc_id=:id",nativeQuery = true)
	AmcInventory findByAmcId1(@Param("id")Long id);
	
	@Query(value = "select * from amcinventory a\r\n" + 
			"inner join asset i on a.asset_id=i.asset_id where i.far_no=:id",nativeQuery = true)
	AmcInventory FindByFarNo(@Param("id")String id);


	@Query(value = "SELECT a.* FROM amcinventory a where a.inventory_id=:id",nativeQuery = true)
	AmcInventory FindAmcByInventoryId(@Param("id")Long id);
	
	

	@Query(value = "EXEC [dbo].[filterAmcInventory] @businessVerticalName=:businessVerticalName,@amcStatus=:amcStatus,"
			+ "@StVal=:start_page,@Endval=:end_page,@stringStoreIds=:stringStoreIds,"
			+ "@maintainancePeriod=:maintainancePeriod,@maintainanceStartDate=:maintainanceStartDate,@maintainanceEndDate=:maintainanceEndDate,"
			+ "@minMaintainanceGap=:minMaintainanceGap,@maintainanceValidity=:maintainanceValidity,"
			+ "@numberOfService=:numberOfService,@contractNumber=:contractNumber,@vendorCode=:vendorCode,"
			+ "@storeCode=:storeCode,@assetName=:assetName,@erNo=:erNo,@farNo=:farNo,@vendorId=:vendorId",nativeQuery = true)
	List<AmcInventory> getFilteredUsingProcedure(@Param("businessVerticalName")String businessVerticalName,@Param("amcStatus") String amcStatus,
			@Param("stringStoreIds")String stringStoreIds,@Param("maintainancePeriod") String maintainancePeriod,@Param("maintainanceStartDate") String maintainanceStartDate,@Param("maintainanceEndDate") String maintainanceEndDate,
			@Param("minMaintainanceGap")String minMaintainanceGap,@Param("maintainanceValidity") String maintainanceValidity,@Param("numberOfService") String numberOfService,@Param("contractNumber") String contractNumber,
			@Param("vendorCode")String vendorCode,@Param("storeCode") String storeCode,@Param("assetName") String assetName,@Param("erNo") String erNo,
			@Param("farNo")String farNo,@Param("vendorId")Long vendorId,
			@Param("start_page") int start_page,@Param("end_page") int end_page);


	@Query(value = "EXEC [dbo].[filterAmcInventory_count] @businessVerticalName=:businessVerticalName,"
			+ "@stringStoreIds=:stringStoreIds,@vendorCode=:vendorCode,@storeCode=:storeCode,@assetName=:assetName,@erNo=:erNo,@farNo=:farNo,@vendorId=:vendorId", nativeQuery = true)
	Long count(@Param("businessVerticalName")String businessVerticalTypeName,
			@Param("stringStoreIds")String stringStoreIds,
			@Param("vendorCode")String vendorCode,@Param("storeCode") String storeCode,@Param("assetName") String assetName,@Param("erNo") String erNo,
			@Param("farNo")String farNo,@Param("vendorId")Long vendorId);

	

	@Query(value = "select a.* from amcinventory a\r\n" + 
			"left join store s on s.store_id=a.store_id\r\n" + 
			"left join region r on r.region_id=s.region_id where s.region_id=:id",nativeQuery = true)
	List<AmcInventory> FindAmcByRegionId(@Param("id")Long id);
	
	@Query(value="select q1.amcnotification_id, q1.activity_name,q1.amc_id,    \r\n" + 
			"					dbo.AmcInventoryEmailTO(q1.amcnotification_id,q1.user_id,q1.amc_id) as TOemail,   \r\n" + 
			"					dbo.AmcInventoryEmailCC(q1.amcnotification_id,q1.user_id,q1.amc_id) as CCEmail    \r\n" + 
			"					from (select sr.amc_id,sreml.activity_name,sreml.amcnotification_id ,sr.user_id,   \r\n" + 
			"					sr.web_master_id    from amcinventory sr   \r\n" + 
			"					left JOIN amc_notification sreml on sreml.vertical_id =sr.asset_web_master_id" + 
			"					) as q1 where q1.activity_name='AMC Maintenance Activation'  and q1.amc_id=?1",nativeQuery = true)
	List<Object[]> AMCMaintenanceActivation(long amcId);
	@Query(value="select q1.amcnotification_id, q1.activity_name,q1.amc_id,    \r\n" + 
			"					dbo.AmcInventoryEmailTO(q1.amcnotification_id,q1.user_id,q1.amc_id) as TOemail,   \r\n" + 
			"					dbo.AmcInventoryEmailCC(q1.amcnotification_id,q1.user_id,q1.amc_id) as CCEmail    \r\n" + 
			"					from (select sr.amc_id,sreml.activity_name,sreml.amcnotification_id ,sr.user_id,   \r\n" + 
			"					sr.web_master_id    from amcinventory sr   \r\n" + 
			"					left JOIN amc_notification sreml on sreml.vertical_id =sr.asset_web_master_id" + 
			"					) as q1 where q1.activity_name='AMC Maintenance Deactivation'  and q1.amc_id=?1",nativeQuery = true)
	List<Object[]> AMCMaintenanceDeactivation(long amcId);
	
	@Query(value="select q1.amcnotification_id, q1.activity_name,q1.amc_id,    \r\n" + 
			"					dbo.AmcInventoryEmailTO(q1.amcnotification_id,q1.user_id,q1.amc_id) as TOemail,   \r\n" + 
			"					dbo.AmcInventoryEmailCC(q1.amcnotification_id,q1.user_id,q1.amc_id) as CCEmail    \r\n" + 
			"					from (select sr.amc_id,sreml.activity_name,sreml.amcnotification_id ,sr.user_id,   \r\n" + 
			"					sr.web_master_id    from amcinventory sr   \r\n" + 
			"					left JOIN amc_notification sreml on sreml.vertical_id =sr.asset_web_master_id" + 
			"					) as q1 where q1.activity_name='AMC Validation'  and q1.amc_id=?1",nativeQuery = true)
	List<Object[]> AMCValidation(long amcId);

	@Query(value="select q1.amcnotification_id, q1.activity_name,q1.amc_id,    \r\n" + 
			"					dbo.AmcInventoryEmailTO(q1.amcnotification_id,q1.user_id,q1.amc_id) as TOemail,   \r\n" + 
			"					dbo.AmcInventoryEmailCC(q1.amcnotification_id,q1.user_id,q1.amc_id) as CCEmail    \r\n" + 
			"					from (select sr.amc_id,sreml.activity_name,sreml.amcnotification_id ,sr.user_id,   \r\n" + 
			"					sr.web_master_id    from amcinventory sr   \r\n" + 
			"					left JOIN amc_notification sreml on sreml.vertical_id =sr.asset_web_master_id" + 
			"					) as q1 where q1.activity_name='AMC Extension'  and q1.amc_id=?1",nativeQuery = true)
	List<Object[]> AMCExtension(long amcId);
	@Query(value="select q1.amcnotification_id, q1.activity_name,q1.amc_id,    \r\n" + 
			"					dbo.AmcInventoryEmailTO(q1.amcnotification_id,q1.user_id,q1.amc_id) as TOemail,   \r\n" + 
			"					dbo.AmcInventoryEmailCC(q1.amcnotification_id,q1.user_id,q1.amc_id) as CCEmail    \r\n" + 
			"					from (select sr.amc_id,sreml.activity_name,sreml.amcnotification_id ,sr.user_id,   \r\n" + 
			"					sr.web_master_id    from amcinventory sr   \r\n" + 
			"					left JOIN amc_notification sreml on sreml.vertical_id =sr.asset_web_master_id" + 
			"					) as q1 where q1.activity_name='Maintainance Alert'  and q1.amc_id=?1",nativeQuery = true)
	List<Object[]> AmcMaintainanceAlert(long amcId);
	
	
	@Query(value="select q1.amcnotification_id, q1.activity_name,q1.amc_id,    \r\n" + 
			"					dbo.AmcInventoryEmailTO(q1.amcnotification_id,q1.user_id,q1.amc_id) as TOemail,   \r\n" + 
			"					dbo.AmcInventoryEmailCC(q1.amcnotification_id,q1.user_id,q1.amc_id) as CCEmail    \r\n" + 
			"					from (select sr.amc_id,sreml.activity_name,sreml.amcnotification_id ,sr.user_id,   \r\n" + 
			"					sr.web_master_id    from amcinventory sr   \r\n" + 
			"					left JOIN amc_notification sreml on sreml.vertical_id =sr.asset_web_master_id" + 
			"					) as q1 where q1.activity_name='Vendor Change'  and q1.amc_id=?1",nativeQuery = true)
	List<Object[]> VendorChange(long amcId);
	
	@Query(value = "SELECT a.amc_id FROM amcinventory a where YEAR(a.maintainance_start_date)=?1 and YEAR(a.maintainance_end_date)=?2",nativeQuery = true)
	List<Long> FindAmcByByYear(Long fromYear,Long toYear);
	
	@Query(value = "SELECT count(*) as toalAmcInventory\r\n"
			+ "			  FROM amcinventory i\r\n"
			+ "		 inner join asset a on a.asset_id=i.asset_id\r\n"
			+ "		 inner join web_master wm on wm.web_master_id=a.web_master_id\r\n"
			+ "				where wm.web_master_name=?1",nativeQuery = true)
	List<Object[]> getTotalAmcInventory(String businessVerticalTypeName);
	
	@Query(value = "SELECT count(*) as toalAmcInventory\r\n"
			+ "			  FROM amcinventory i\r\n"
			+ "		 inner join asset a on a.asset_id=i.asset_id\r\n"
			+ "		 inner join web_master wm on wm.web_master_id=a.web_master_id",nativeQuery = true)
	List<Object[]> getTotalAmcInventory();
	
	@Query(value="SELECT count(*) as toalAmcInventory\r\n"
			+ "		  FROM amcinventory am \r\n"
			+ "		  inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
			+ "								inner join users u on u.id=am.user_id\r\n"
			+ "								left join store st on st.store_code=u.username\r\n"
			+ "		  inner join region r on r.region_id=st.region_id\r\n"
			+ "		  where wm.web_master_name=?1 AND st.store_code=?2 OR am.vendor_id=?3 OR r.region_id IN (?4)",nativeQuery=true)
	List<Object[]> getTotalAmcInventory(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
	@Query(value="SELECT count(*) as toalAmcInventory\r\n"
			+ "		  FROM amcinventory am \r\n"
			+ "		  inner join web_master wm on wm.web_master_id=am.asset_web_master_id\r\n"
			+ "								inner join users u on u.id=am.user_id\r\n"
			+ "								left join store st on st.store_code=u.username\r\n"
			+ "		  inner join region r on r.region_id=st.region_id\r\n"
			+ "		  where wm.web_master_name=?1 OR st.store_code=?2 OR am.vendor_id=?3 OR r.region_id IN (?4)",nativeQuery=true)
	List<Object[]> getTotalAmcInventorys(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region);
	
	@Query(value = "SELECT a.* FROM amcinventory a where asset_id=:id",nativeQuery = true)
	List<AmcInventory> getAmcByAssetId(Long id);


}
