package com.titan.irgs.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.ClusterUser;

@Repository
@Transactional
public interface ClusterUserRepository  extends JpaRepository<ClusterUser, Long> {

	@Modifying
	@Query(value = "delete FROM cluster_user where user_id=:id and cluster_id=:clusterId",nativeQuery = true)
	void deleteClusterUserByUserIdAndClusterIdIn(@Param("id")Long id,@Param("clusterId") Long clusterId);

	@Query(value = "select * FROM cluster_user where user_id=:id",nativeQuery = true)
	List<ClusterUser> getClusterByUserById(@Param("id")Long id);
    
	@Query(value = "SELECT cu.* FROM cluster_user cu "
			+ "inner join cluster c on cu.cluster_id=c.cluster_id "
			+ "inner join users u on u.web_role_id=c.web_role_id "
			+ "where u.id=:id and user_active='ACTIVE'",nativeQuery = true)
	List<ClusterUser> checkUserIsactiveInClusterUser(@Param("id") Long id);
	

	@Query(value=" select * from cluster_user u where u.user_name=:username",nativeQuery = true)
	ClusterUser findByUsername(@Param("username")String username);


}
