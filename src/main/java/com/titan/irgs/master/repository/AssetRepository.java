package com.titan.irgs.master.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Asset;

/**
 * This is AssetRepository interface(implemented on Jpa framework) which are
 * responsible to perform CRUD operation on brand table
 * 
 * @author birendra
 *
 */

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
	Asset findByAssetName(String assetName);

	Asset findByAssetCode(String assetCode);

	Asset findByAssetId(Long assetId);

	List<Asset> findByAmcStatus(boolean amcStatus);

	@Async
	@Query("FROM Asset a where a.model.modelId=:modelId")
	List<Asset> findAssetBymodelId(@Param("modelId") Long modelId);

	Asset findByAssetCodeAndOriginalQty(String assetCode, Long itemNo);

	Asset findByFarNo(String farNo);

	@Query(value = "SELECT a.* from asset a\r\n"
			+ "inner join web_master w on a.web_master_id=w.web_master_id where w.web_master_name=?1", nativeQuery = true)
	Asset findByWebMasterName(String webMasterName);

	@Query(value = "SELECT w.web_master_name,d.department_name,a.asset_name,a.asset_code,a.far_no,a.original_qty,b.brand_name, \r\n"
			+ "								b.brand_code,e.equipment_code,e.equipment_name,m.model_name,m.model_no,ae.asset_specification_name,m.remarks\r\n"
			+ "								,v.vendor_name,v.vendor_code,CAST(a.installation_date as date),a.warranty_period,a.warranty_end_date\r\n"
			+ "								,(select count(*) from inventory i inner join asset a1 on a1.asset_id=i.asset_id \r\n"
			+ "								where a1.asset_code=a.asset_code and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')) as assigned,\r\n"
			+ "								(a.original_qty-(select count(*) from inventory i inner join asset a1 on a1.asset_id=i.asset_id \r\n"
			+ "								where a1.asset_code=a.asset_code and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')))as pending_qty,\r\n"
			+ "								 STUFF((SELECT ', ' + s.serial_number FROM serial_number s WHERE s.asset_id = a.asset_id FOR XML PATH('')), 1, 2, '') AS serial_numbers\r\n"
			+ "								FROM asset a  \r\n"
			+ "								inner join equipment e on e.equipment_id=a.equipment_id  \r\n"
			+ "								inner join asset_specification ae on a.asset_specification_id=ae.asset_specification_id  \r\n"
			+ "								inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id  \r\n"
			+ "								inner join web_master w on w.web_master_id=a.web_master_id\r\n"
			+ "								inner join department d on d.department_id=a.department_id\r\n"
			+ "								left join vendor v on v.vendor_id=a.vendor_id order by asset_name", nativeQuery = true)
	List<Object[]> getAllCitysForExcel();

	@Query(value = "SELECT w.web_master_name,d.department_name,a.asset_name,a.asset_code,a.far_no,a.original_qty,b.brand_name, \r\n"
			+ "											b.brand_code,e.equipment_code,e.equipment_name,m.model_name,m.model_no,ae.asset_specification_name,m.remarks\r\n"
			+ "											,v.vendor_name,v.vendor_code,CAST(a.installation_date as date),a.warranty_period,a.warranty_end_date\r\n"
			+ "											,(select count(*) from inventory i inner join asset a1 on a1.asset_id=i.asset_id \r\n"
			+ "											where a1.asset_code=a.asset_code and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')) as assigned,\r\n"
			+ "											(a.original_qty-(select count(*) from inventory i inner join asset a1 on a1.asset_id=i.asset_id \r\n"
			+ "											where a1.asset_code=a.asset_code and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')))as pending_qty,\r\n"
			+ "											 STUFF((SELECT ', ' + s.serial_number FROM serial_number s WHERE s.asset_id = a.asset_id FOR XML PATH('')), 1, 2, '') AS serial_numbers\r\n"
			+ "											FROM asset a  \r\n"
			+ "											inner join equipment e on e.equipment_id=a.equipment_id  \r\n"
			+ "											inner join asset_specification ae on a.asset_specification_id=ae.asset_specification_id  \r\n"
			+ "											inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id  \r\n"
			+ "											inner join web_master w on w.web_master_id=a.web_master_id\r\n"
			+ "											inner join department d on d.department_id=a.department_id\r\n"
			+ "											left join vendor v on v.vendor_id=a.vendor_id\r\n"
			+ "										    where w.web_master_name=:businessVerticalType and d.department_name=:department order by asset_name", nativeQuery = true)
	List<Object[]> getAllCitysForExcel(@Param("businessVerticalType")String businessVerticalType,@Param("department")String department);

	@Query(value = "SELECT w.web_master_name,d.department_name,a.asset_name,a.asset_code,a.far_no,a.original_qty,b.brand_name, \r\n"
			+ "											b.brand_code,e.equipment_code,e.equipment_name,m.model_name,m.model_no,ae.asset_specification_name,m.remarks\r\n"
			+ "											,v.vendor_name,v.vendor_code,CAST(a.installation_date as date),a.warranty_period,a.warranty_end_date\r\n"
			+ "											,(select count(*) from inventory i inner join asset a1 on a1.asset_id=i.asset_id \r\n"
			+ "											where a1.asset_code=a.asset_code and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')) as assigned,\r\n"
			+ "											(a.original_qty-(select count(*) from inventory i inner join asset a1 on a1.asset_id=i.asset_id \r\n"
			+ "											where a1.asset_code=a.asset_code and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')))as pending_qty,\r\n"
			+ "											 STUFF((SELECT ', ' + s.serial_number FROM serial_number s WHERE s.asset_id = a.asset_id FOR XML PATH('')), 1, 2, '') AS serial_numbers\r\n"
			+ "											FROM asset a  \r\n"
			+ "											inner join equipment e on e.equipment_id=a.equipment_id  \r\n"
			+ "											inner join asset_specification ae on a.asset_specification_id=ae.asset_specification_id  \r\n"
			+ "											inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id  \r\n"
			+ "											inner join web_master w on w.web_master_id=a.web_master_id\r\n"
			+ "											inner join department d on d.department_id=a.department_id\r\n"
			+ "											left join vendor v on v.vendor_id=a.vendor_id\r\n"
			+ "										    where w.web_master_name=:businessVerticalType order by asset_name", nativeQuery = true)
	List<Object[]> getAllCitysForExcel(@Param("businessVerticalType")String businessVerticalType);

	
	@Query(value = "select * from asset a where a.asset_id not in(select i.asset_id from inventory i)", nativeQuery = true)
	Page<Asset> getAllAssetnotCreated1(Pageable pageable);

	@Query(value = "select * from asset a where a.asset_name like %?1% and a.asset_id not in(select i.asset_id from inventory i)", nativeQuery = true)
	Page<Asset> getAllAssetnotCreated1(String name, Pageable pageable);

	@Query(value = "select * from asset a where a.asset_code like %?1% and a.asset_id not in(select i.asset_id from inventory i)", nativeQuery = true)
	Page<Asset> getAllAssetnotCreated2(String code, Pageable pageable);

	@Query(value = "EXEC [dbo].[filterAssetsNotCreated] @assetName =:assetName,@assetCode = :assetCode,@fARNo=:fARNo,"
			+ "@StVal =:start_page,@Endval =:end_page,@equipmentName = :equipmentName,"
			+ "@modelName = :modelName,@brandName = :brandName,@businessVerticalType = :businessVerticalType", nativeQuery = true)
	List<Asset> getAllAssetnotCreated(@Param("assetName") String assetName, @Param("assetCode") String assetCode,
			@Param("fARNo") String fARNo, @Param("equipmentName") String equipmentName,
			@Param("modelName") String modelName, @Param("brandName") String brandName,
			@Param("businessVerticalType") String businessVerticalType, @Param("start_page") int start_page,
			@Param("end_page") int end_page);

	@Query(value = "EXEC [dbo].[filterAssetsNotCreated_count] @assetName =:assetName,"
			+ "@assetCode = :assetCode,@fARNo=:fARNo,"
			+ "@equipmentName = :equipmentName,@modelName = :modelName,@brandName = :brandName,"
			+ "@businessVerticalType = :businessVerticalType", nativeQuery = true)
	Long count(@Param("assetName") String assetName, @Param("assetCode") String assetCode, @Param("fARNo") String fARNo,
			@Param("equipmentName") String equipmentName, @Param("modelName") String modelName,
			@Param("brandName") String brandName, @Param("businessVerticalType") String businessVerticalType);

	@Query(value = "select a.asset_code,a.asset_name,a.asset_id,d.department_id,d.department_name,wm.web_master_id,wm.web_master_name \r\n"
			+ "						from asset a\r\n"
			+ "						inner join department d on d.department_id=a.department_id\r\n"
			+ "						inner join web_master wm on wm.web_master_id=d.web_master_id\r\n"
			+ "						where wm.web_master_id=:verticalId and  d.department_id=:departmentId", nativeQuery = true)
	List<Object[]> getAssetsByVerticalIdAndDepartmentId(@Param("verticalId") Long verticalId,
			@Param("departmentId") Long departmentId);

	@Query(value = "SELECT * FROM asset a\r\n"
			+ "left join vendor v on v.vendor_id=a.vendor_id where v.vendor_code=:vendorCode and a.far_no=:farNo", nativeQuery = true)
	Asset findByVendorCodeAndFarNo(@Param("vendorCode") String vendorCode, @Param("farNo") String farNo);

	@Query(value = "SELECT installation_date FROM asset where far_no=?1", nativeQuery = true)
	Date getInstallationDateByFarno(String farNo);

	@Query(value = "SELECT warranty_end_date FROM asset where far_no=?1", nativeQuery = true)
	Date getWarrantyDateByFarno(String farNo);

	@Query(value = "EXEC [dbo].[assetCountForDashboard] @businessVerticalTypeName = :businessVerticalTypeName, @department = :department,"
			+ " @user1 = :user1,@vendorCode=:vendorCode", nativeQuery = true)
	List<Object[]> getAssetCountForDashboard(@Param("businessVerticalTypeName") String businessVerticalTypeName,@Param("department") String department,
			@Param("user1")String user1,@Param("vendorCode") String vendorCode);

	@Query(value = "EXEC [dbo].[unAssignedAssetCountForDashboard] @businessVerticalTypeName = :businessVerticalTypeName, @department = :department,"
			+ " @user1 = :user1", nativeQuery = true)
	List<Object[]> getUnassignedAssetCountForDashboard(@Param("businessVerticalTypeName") String businessVerticalType,@Param("department") String department,
			@Param("user1")String user1);

	@Query(value = "EXEC [dbo].[scrappedAssetCountForDashboard] @businessVerticalTypeName = :businessVerticalTypeName, @department = :department,"
			+ " @user1 = :user1", nativeQuery = true)
	List<Object[]> getScrappedAssetCountForDashboard(@Param("businessVerticalTypeName") String businessVerticalType,@Param("department") String department,
			@Param("user1")String user1);

}
