package com.titan.irgs.inventory.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.inventory.domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long>,JpaSpecificationExecutor<Inventory> {

	Inventory findByErNo(String erNo);
	
	@Query(value="select * from inventory i inner join asset a on a.asset_id=i.asset_id \r\n"
			+ "where a.asset_code=?1",nativeQuery = true)
	Inventory findByAssetCode(String assetCode);
	
	@Query(value="select * from inventory i inner join asset a on a.asset_id=i.asset_id \r\n"
			+ "where a.asset_code=?1 and (i.inventory_status IS NULL OR i.inventory_status='ACTIVE')",nativeQuery = true)
	List<Inventory> findInventoryByAssetCodes(String assetCode);
	
	
	@Query(value = "SELECT i.* FROM inventory i where web_master_id=:id",nativeQuery = true)
	List<Inventory> getInventoryByBssinessId(@Param("id")Long id);

	@Query(value = "SELECT w.web_master_name,d.department_name,s.store_name,s.store_code,a.asset_name,a.asset_code,a_s.asset_specification_name, \r\n"
			+ "					b.brand_name,b.brand_code,m.model_no,m.model_name,i.far_no,i.er_no,i.quantity,sn.serial_number,i.allocation_start_date,\r\n"
			+ "					i.allotted_period,i.allocation_end_date,i.inventory_status,m.remarks\r\n"
			+ "					FROM inventory i  \r\n"
			+ "					inner join web_master w on w.web_master_id=i.web_master_id  \r\n"
			+ "					inner join store s on s.store_id=i.store_id  \r\n"
			+ "					inner join asset a on a.asset_id=i.asset_id  \r\n"
			+ "					inner join department d on d.department_id=a.department_id\r\n"
			+ "					inner join asset_specification a_s on a_s.asset_specification_id=a.asset_specification_id  \r\n"
			+ "					inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id \r\n"
			+ "					left join serial_number sn on sn.serial_id=i.serial_id ",nativeQuery = true)
	List<Object[]> getAllExcel();
	
    Inventory findByInventoryId(Long inventoryId);
   
	@Query(value = "SELECT w.web_master_name,d.department_name,u.first_name,u.username,a.asset_name,a.asset_code,a_s.asset_specification_name, \r\n"
			+ "								b.brand_name,b.brand_code,m.model_no,m.model_name,i.far_no,i.er_no,i.quantity,sn.serial_number,i.allocation_start_date,\r\n"
			+ "								i.allotted_period,i.allocation_end_date,i.inventory_status,m.remarks\r\n"
			+ "								FROM inventory i  \r\n"
			+ "								inner join web_master w on w.web_master_id=i.web_master_id  \r\n"
			+ "								inner join asset a on a.asset_id=i.asset_id  \r\n"
			+ "								inner join department d on d.department_id=a.department_id\r\n"
			+ "								inner join asset_specification a_s on a_s.asset_specification_id=a.asset_specification_id  \r\n"
			+ "								inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id \r\n"
			+ "								left join serial_number sn on sn.serial_id=i.serial_id \r\n"
			+ "								left join users u on u.id=i.user_id\r\n"
			+ "								where w.web_master_name=:businessVerticalType and d.department_name=:department",nativeQuery = true)
	List<Object[]> getAllExcel(@Param("businessVerticalType") String businessVerticalType,@Param("department") String department);
	
	@Query(value = "SELECT w.web_master_name,d.department_name,u.first_name,u.username,a.asset_name,a.asset_code,a_s.asset_specification_name, \r\n"
			+ "								b.brand_name,b.brand_code,m.model_no,m.model_name,i.far_no,i.er_no,i.quantity,sn.serial_number,i.allocation_start_date,\r\n"
			+ "								i.allotted_period,i.allocation_end_date,i.inventory_status,m.remarks\r\n"
			+ "								FROM inventory i  \r\n"
			+ "								inner join web_master w on w.web_master_id=i.web_master_id  \r\n"
			+ "								inner join asset a on a.asset_id=i.asset_id  \r\n"
			+ "								inner join department d on d.department_id=a.department_id\r\n"
			+ "								inner join asset_specification a_s on a_s.asset_specification_id=a.asset_specification_id  \r\n"
			+ "								inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id \r\n"
			+ "								left join serial_number sn on sn.serial_id=i.serial_id \r\n"
			+ "								left join users u on u.id=i.user_id\r\n"
			+ "								where u.username=:user1",nativeQuery = true)
	List<Object[]> getAllExcel(@Param("user1") String user1);

	@Query(value = "SELECT w.web_master_name,d.department_name,u.first_name,u.username,a.asset_name,a.asset_code,a_s.asset_specification_name, \r\n"
			+ "								b.brand_name,b.brand_code,m.model_no,m.model_name,i.far_no,i.er_no,i.quantity,sn.serial_number,i.allocation_start_date,\r\n"
			+ "								i.allotted_period,i.allocation_end_date,i.inventory_status,m.remarks\r\n"
			+ "								FROM inventory i  \r\n"
			+ "								inner join web_master w on w.web_master_id=i.web_master_id  \r\n"
			+ "								inner join asset a on a.asset_id=i.asset_id  \r\n"
			+ "								inner join department d on d.department_id=a.department_id\r\n"
			+ "								inner join asset_specification a_s on a_s.asset_specification_id=a.asset_specification_id  \r\n"
			+ "								inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id \r\n"
			+ "								left join serial_number sn on sn.serial_id=i.serial_id \r\n"
			+ "								left join users u on u.id=i.user_id\r\n"
			+ "								where w.web_master_name=:businessVerticalType",nativeQuery = true)
	List<Object[]> getAllExcelForVertical(@Param("businessVerticalType") String businessVerticalType);

	
	@Modifying
	  @Transactional
	@Query(value = "SELECT w.web_master_name,s.store_name,s.store_code,a.asset_name,a.asset_code,a_s.asset_specification_name,\r\n" + 
			"			b.brand_name,b.brand_code,m.model_no,m.model_name,i.far_no,i.er_no,i.quantity FROM inventory i \r\n" + 
			"			inner join web_master w on w.web_master_id=i.web_master_id \r\n" + 
			"			inner join store s on s.store_id=i.store_id \r\n" + 
			"			inner join asset a on a.asset_id=i.asset_id \r\n" + 
			"			inner join asset_specification a_s on a_s.asset_specification_id=a.asset_specification_id \r\n" + 
			"			inner join model m on m.model_id=a.model_id inner join brand b on b.brand_id=m.brand_id \r\n" + 
			"			left join vendor v on v.vendor_id=i.vendor_id where  s.store_code=?1 ",nativeQuery = true)
	List<Object[]> getAllStoreCode(String storecode);	

    @Query(value="SELECT i.web_master_id FROM inventory i where i.inventory_id=:id" ,nativeQuery=true)
	Long getWebmasterByInventoryId(@Param("id")Long id);
    

	@Query(value = "select i.* from inventory i\r\n" + 
			"inner join asset a on a.asset_id=i.asset_id\r\n" + 
			"inner join equipment e on e.equipment_id=a.equipment_id\r\n" + 
			"inner join vendor_equipment ve on ve.equipment_id=e.equipment_id\r\n" + 
			"inner join vendor v on v.vendor_id=ve.vendor_id where v.vendor_code=?1",nativeQuery = true)
	Inventory getInventoryByVendorCode(String vendorcode);
    @Query(value="SELECT COUNT(e.vendor_id) FROM vendor_equipment e where e.equipment_id=:id" ,nativeQuery=true)
	Long getVendorByEquipmentId(@Param("id")Long id);
    
    /*
    @Query(value="SELECT installation_date FROM inventory where er_no=?1" ,nativeQuery=true)
	Date getInstallationDateByErno(String erNo);
    
    @Query(value="SELECT warranty_end_date FROM inventory where er_no=?1" ,nativeQuery=true)
	Date getWarrantyDateByErno(String erNo);
    */
    
    @Query(value="SELECT COUNT(*) as totalInventories\r\n"
    		+ "    	 FROM inventory i\r\n"
    		+ "		 inner join asset a on a.asset_id=i.asset_id\r\n"
    		+ "		 inner join web_master wm on wm.web_master_id=a.web_master_id\r\n"
    		+ "				where wm.web_master_name=?1" ,nativeQuery=true)
	List<Object[]> getAllInventory(String businessVerticalTypeName);
	
	@Query(value="SELECT COUNT(*) as totalInventories\r\n"
    		+ "    	 FROM inventory i\r\n"
    		+ "		 inner join asset a on a.asset_id=i.asset_id\r\n"
    		+ "		 inner join web_master wm on wm.web_master_id=a.web_master_id" ,nativeQuery=true)
	List<Object[]> getAllInventory();
	
	@Query(value="SELECT COUNT(*) as totalInventories\r\n"
			+ "    	 FROM inventory i\r\n"
			+ "		 inner join  web_master wm on wm.web_master_id=i.web_master_id\r\n"
			+ "								inner join users u on u.id=i.user_id\r\n"
			+ "								left join store st on st.store_code=u.username\r\n"
			+ "		 inner join region r on r.region_id=st.region_id\r\n"
			+ "		 where wm.web_master_name=?1 AND st.store_code=?2 OR r.region_id IN (?3)",nativeQuery=true)
	List<Object[]> getAllInventory(String businessVerticalTypeName, String storeCode,List<Long> region);

	@Query(value="SELECT COUNT(*) as totalInventories\r\n"
			+ "    	 FROM inventory i\r\n"
			+ "		 inner join  web_master wm on wm.web_master_id=i.web_master_id\r\n"
			+ "								inner join users u on u.id=i.user_id\r\n"
			+ "								left join store st on st.store_code=u.username\r\n"
			+ "		 inner join region r on r.region_id=st.region_id\r\n"
			+ "		 where wm.web_master_name=?1 OR st.store_code=?2 OR r.region_id IN (?3)",nativeQuery=true)
	List<Object[]> getAllInventorys(String businessVerticalTypeName, String storeCode,List<Long> region);

	@Query(value="SELECT a.web_master_id\r\n"
			+ "FROM inventory i \r\n"
			+ "INNER JOIN asset a ON a.asset_id = i.asset_id\r\n"
			+ "inner join store s on s.store_id=i.store_id\r\n"
			+ "inner join web_master wm on wm.web_master_id=a.web_master_id\r\n"
			+ "WHERE s.store_code=?1\r\n"
			+ "GROUP BY a.web_master_id",nativeQuery = true)
	List<Long> getAssetWebMasterIdBasedonUserName(String username);

	@Query(value = "EXEC [dbo].[assignedAssetCountForDashboard] @businessVerticalTypeName = :businessVerticalTypeName, @department = :department,"
			+ " @user1 = :user1",nativeQuery = true)
	List<Object[]> getInventoryCountForAssignedAssetList(@Param("businessVerticalTypeName") String businessVerticalType,@Param("department") String department,
			@Param("user1")String user1);

}
