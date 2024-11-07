package com.titan.irgs.accessPolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.titan.irgs.accessPolicy.domain.Permission;

public interface PermissionRepo extends JpaRepository<Permission, Long>{

	@Query(value = "SELECT p.*,b.back_end_api_id_url FROM permission p "
			+ "inner join back_end_apis b on b.back_end_api_id=p.back_end_api_id "
			+ "where b.back_end_api_id_url =:uri and web_role_id=:roleId",nativeQuery = true)
	Permission checkApiAppendTorole(@Param("uri")String uri,@Param("roleId") Long roleId);

	//Permission checkApiAppendTorole(String uri, DoubleStream mapToDouble);


}
