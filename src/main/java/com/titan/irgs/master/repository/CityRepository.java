package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	City findByCityName(String string);
	
	List<City> findByStateStateIdIn(List<Long> stateIds);
	
	List<City>findByStateStateId(Long stateId);
	
	List<City>findByCityIdIn(List<Long> cityIds);
	
	@Async
	@Query("FROM City c where c.cityId =:cityId")
	City getById(@Param("cityId") Long cityId);

	City findByCityId(Long cityId);

	@Query(value = "SELECT city_name FROM city",nativeQuery = true)
	List<Object[]> getAllCitysForExcel();

}