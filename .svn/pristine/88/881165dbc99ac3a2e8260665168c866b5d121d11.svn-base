package com.titan.irgs.accessPolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.titan.irgs.accessPolicy.domain.BackEndApis;

public interface BackEndApisRepo extends JpaRepository<BackEndApis, Long>,JpaSpecificationExecutor<BackEndApis>{

	@Query(value = "SELECT * FROM back_end_apis where back_end_api_id_url like :uri%",nativeQuery = true)
	BackEndApis findByUrlLike(@Param("uri")String uri);
	
	@Query(value = "SELECT * FROM back_end_apis where back_end_api_id_url like :uri",nativeQuery = true)
	BackEndApis findByUrl(@Param("uri")String uri);
	


	BackEndApis findByBackEndApiIdUrl(String backEndApiIdUrl);

}
