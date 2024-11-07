package com.titan.irgs.master.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.State;




/**
 * This is StateRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on state table
 * @author birendra
 *
 */

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	State findByStateName(String string);
	
	@Async
	@Query("FROM State s where s.country.countryId =:countryId")
	List<State> findByCountryId(@Param("countryId") Long countryId);
	
	List<State> findByCountryCountryIdIn(List<Long> countryId);
	
	State findByCountryCountryId(Long countryId);
	
	List<State>findByStateIdIn(List<Long> stateIds);

	State findByStateId(Long stateId);

	@Query(value = "SELECT  state_name,c.country_name FROM state s "
			+ "inner join country c on c.country_id =s.country_id "
			+ "order by s.state_name",nativeQuery = true)
	List<Object[]> getAllForExcel();

}