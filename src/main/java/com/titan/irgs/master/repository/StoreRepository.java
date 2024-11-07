package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>,JpaSpecificationExecutor<Store> {

	Store findByStoreCodeIgnoreCase(String storeCode);

	Store findByStoreCode(String storeCode);

	Store findByCostcentre(String costcentre);

	
	@Query(value = "SELECT  s.* FROM store s where web_master_id=:webmasterid "
			+ "and store_code not in(select u.username from users u "
			+ "where web_role_id=:webroleid)",nativeQuery = true)
	List<Store> getAllStoreNotPresentInUser(@Param("webroleid")Long webroleid,@Param("webmasterid") Long webmasterid);

	
	@Query(value = "SELECT * FROM store where web_master_id=:id",nativeQuery = true)
	List<Store> getStoreByBussinessId(@Param("id")Long id);

	
	@Query(value = "SELECT w.web_master_name,s.store_code,s.costcentre,ot.owner_type_name,"
			+ "st.state_name,c.city_name,r.region_name,s.store_locality,ro.role_name,u.username,"
			+ "sst.store_service_type_name,sbs.store_business_service_type_name,sa.store_alloted_type,s.store_status,"
			+ "s.store_flag FROM store s "
			+ "inner join web_master w on w.web_master_id=s.web_master_id "
			+ "inner join owner_type ot on ot.owner_type_id=s.owner_type_id "
			+ "inner join state st on st.state_id=s.state_id "
			+ "inner join city c on c.city_id=s.city_id "
			+ "inner join region r on r.region_id=s.region_id "
			+ "inner join web_role wr on wr.web_role_id=s.web_role_id "
			+ "inner join dbo.role ro on ro.role_id=wr.role_id "
			+ "inner join users u on u.id=s.reporting_to_id "
			+ "left join store_alloted_details sad on sad.store_id=s.store_id "
			+ "left join store_alloted sa on sa.store_alloted_id=sad.store_alloted_id "
			+ "left join store_business_service_type sbs on sbs.store_business_service_type_id=s.store_business_service_type_id "
			+ "left join store_service_type sst on sst.store_service_type_id=s.store_service_type_id",nativeQuery = true)
	List<Object[]> getAllForExcel();

	
	/*
	
	@Query(value = "SELECT s.store_id FROM cluster_user as cu "
			+ "inner join cluster as c on c.cluster_id=cu.cluster_id "
			+ "inner join store s on s.region_id=c.region_id "
			+ "where cu.user_id=:id",nativeQuery = true)
	List<Long> getStoreIdsByUsingUserId(@Param("id")Long id);
	*/
	
	@Query(value = "SELECT s.store_id FROM Store s left join users u on u.username=s.store_code\r\n"
			+ "where u.id=:id",nativeQuery = true)
	List<Long> getStoreIdsByUsingUserId(@Param("id")Long id);
	
	@Query(value = "SELECT s.store_code FROM cluster_user as cu "
			+ "inner join cluster as c on c.cluster_id=cu.cluster_id "
			+ "inner join store s on s.region_id=c.region_id "
			+ "where cu.user_id=:id",nativeQuery = true)
	List<String> getStoreNamesByUsingUserId(@Param("id")Long id);

	@Query(value = "SELECT w.web_master_name,s.store_code,s.costcentre,ot.owner_type_name,"
			+ "st.state_name,c.city_name,r.region_name,s.store_locality,ro.role_name,u.username,"
			+ "sst.store_service_type_name,sbs.store_business_service_type_name,sa.store_alloted_type,s.store_status,"
			+ "s.store_flag FROM store s "
			+ "inner join web_master w on w.web_master_id=s.web_master_id "
			+ "inner join owner_type ot on ot.owner_type_id=s.owner_type_id "
			+ "inner join state st on st.state_id=s.state_id "
			+ "inner join city c on c.city_id=s.city_id "
			+ "inner join region r on r.region_id=s.region_id "
			+ "inner join web_role wr on wr.web_role_id=s.web_role_id "
			+ "inner join dbo.role ro on ro.role_id=wr.role_id "
			+ "inner join users u on u.id=s.reporting_to_id "
			+ "left join store_alloted_details sad on sad.store_id=s.store_id "
			+ "left join store_alloted sa on sa.store_alloted_id=sad.store_alloted_id "
			+ "left join store_business_service_type sbs on sbs.store_business_service_type_id=s.store_business_service_type_id "
			+ "left join store_service_type sst on sst.store_service_type_id=s.store_service_type_id where s.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAllForExcel(Long id);
	
	@Query(value="SELECT u.id from users u\r\n" + 
			"inner join web_role w on w.web_role_id=u.web_role_id\r\n" + 
			"inner join role r on r.role_id=w.role_id where r.role_id=:id",nativeQuery = true)
	List<Long> getAllStore(@Param("id")Long id);

}
