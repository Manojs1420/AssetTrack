package com.titan.irgs.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Cluster;

@Repository
@Transactional
public interface ClusterRepository extends JpaRepository<Cluster, Long> {

	@Query(value = "SELECT region_id FROM cluster where web_role_id=:webRoleId",nativeQuery = true)
	List<Long> getAllRegionIdsInClusterUsingWebRoleId(@Param("webRoleId")Long webRoleId);

	@Modifying
	@Query(value = "delete FROM cluster where web_role_id=:webRoleId and region_id=:regionid",nativeQuery = true)
	void deleteClusterByUsingWebRoleIdAndRegionId(@Param("webRoleId")Long webRoleId,@Param("regionid") Long regionid);

	Cluster findByRegionRegionIdAndWebRoleWebRoleId(Long regionId, Long webRoleId);

	
	@Query(value = "SELECT c.* FROM cluster_user cu "
			+ "inner join cluster c on c.cluster_id=cu.cluster_id "
			+ "where cu.user_id=:id",nativeQuery = true)
	List<Cluster> getClustersByUsingUserId(@Param("id")Long id);
	
	@Query(value = "SELECT c.region_id FROM cluster_user cu "
			+ "inner join cluster c on c.cluster_id=cu.cluster_id "
			+ "where cu.user_id=:id",nativeQuery = true)
	List<Long> getClusterRegionIdByUsingUserId(@Param("id")Long id);

	Cluster findByRegionRegionId(Long regionId);

}



