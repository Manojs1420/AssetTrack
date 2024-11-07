package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>,JpaSpecificationExecutor<Region> {
	@Query("FROM Region r where r.regionName=:regionName")
	List<Region> getByRegionName(@Param("regionName")String regionName);
	
	/*@Query("FROM Region r where r.regionName=:regionName")
	Region getByRegionName(@Param("regionName")String regionName);*/


	/*@Async
	@Query("FROM Region r where r.regionName=:regionName and r.city.cityId=:c")
	Region findByRegionNameAndCityId(@Param("regionName")String regionName, @Param("c")Long c);
	
	@Async
	@Query("FROM Region r where r.regionName=:regionName and r.state.stateId=:s")
	Region findByRegionNameAndStateId(@Param("regionName")String regionName, @Param("s")Long s);*/
	
	//List<Region> findByCityCityId(Long cityId);

	//List<Region> findByCityCityIdIn(List<Long> cityIds);
	
	List<Region>findByRegionIdIn(List<Long> regionIds);

//	List<Region> findByCityCityId(Long cityId);

	Region findByRegionNameIgnoreCase(String regionName);

	//Region getByRegionName(String regionName);

	
	
	List<Region> findByRegionIdNotIn(List<Long> regionsIds);

	
	
	@Query(value = "SELECT r.* FROM region r "
			+ "inner join cluster c on r.region_id=c.region_id "
			+ "where web_role_id=:webRoleId",nativeQuery = true)

	List<Region> getAllRegionsInClusterUsingWebRoleId(@Param("webRoleId")Long webRoleId);

	
	/*
	 * @Query(value = "SELECT distinct r.* ," +
	 * "case when cu.cluster_id is null then 0 " +
	 * "when cu.user_active='INACTIVE' then 0 " +
	 * "when cu.cluster_id is not null then 1 " + "end as active FROM region r " +
	 * "left join cluster c on r.region_id=c.region_id " +
	 * "left join cluster_user cu on cu.cluster_id=c.cluster_id " +
	 * "where web_role_id=:id",nativeQuery = true) List<Object[]>
	 * getAllRegionForUserCreationUsingWebRoleId(@Param("id")Long id);
	 */
	
	
	@Query(value = "select b.region_id,b.created_by,b.created_date,b.region_name,b.update_date,b.updated_by,b.active "
			+ "from("
			+ "select a.region_id,a.created_by,a.created_date,a.region_name,a.update_date,a.updated_by,a.active,"
			+ "ROW_NUMBER() OVER (PARTITION BY [region_name] ORDER BY active desc) as num "
			+ "from ("
			+ "SELECT distinct r.* ,case when cu.cluster_id is null then 0 "
			+ "when cu.user_active='INACTIVE' then 0 "
			+ "when cu.cluster_id is not null then 1 end as active "
			+ "FROM region r "
			+ "left join cluster c on r.region_id=c.region_id "
			+ "left join cluster_user cu on cu.cluster_id=c.cluster_id "
			+ "where web_role_id=:id) as  a) as b where b.num=1 order by b.region_name",nativeQuery = true)
	List<Object[]> getAllRegionForUserCreationUsingWebRoleId(@Param("id")Long id);


	
	@Query(value ="SELECT r.* FROM region r "
			+ "inner join cluster c on r.region_id=c.region_id "
			+ "inner join cluster_user cu on cu.cluster_id=c.cluster_id "
			+ "where cu.user_id=:id",nativeQuery = true)
	List<Region> getAllReagionsInClusterUsingUserId(@Param("id")Long id);

	Region findByRegionId(Long regionId);

	
	@Query(value ="SELECT   r.region_id ,r.region_name,case when c.region_id IS NULL  then 'false' else 'true' end as activeRegion "
			+ " FROM region r  "
			+ "left  join(SELECT   c.region_id FROM  cluster c "
			+ "inner join cluster_user cu on cu.cluster_id=c.cluster_id "
			+ "where c.web_role_id=:id  group by region_id) as c "
			+ "on c.region_id=r.region_id",nativeQuery = true)
	List<Object[]> getAllRegionsInCluster(@Param("id")Long id);

	
	@Query(value ="SELECT r.region_name,s.state_name FROM region_details rd "
			+ "inner join region r on r.region_id=rd.region_id "
			+ "inner join state s on s.state_id=rd.state_id "
			+ "order by r.region_id",nativeQuery = true)
	
	List<Object[]> getAllForExcel();

	//Region findByRegionIdAndStateStateId(Long regionId, Long stateId);
}