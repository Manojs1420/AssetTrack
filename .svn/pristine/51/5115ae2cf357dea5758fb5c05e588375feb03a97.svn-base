package com.titan.irgs.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfigurationRepository  extends JpaRepository<Configuration1, Long>{
	
	Configuration1 findByConfigValue(String configValue);
	
	@Query(value = "SELECT * FROM scheduler where configuration_id=:configurationId",nativeQuery = true)
	Configuration1 findByID(@Param("configurationId")Long configurationId);
	
}
