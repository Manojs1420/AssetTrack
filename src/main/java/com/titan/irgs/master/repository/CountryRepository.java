package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	
	Country findByCountryName(String countryName);
	
	List<Country> findByCountryIdIn(List<Long> countryIds);

	Country findByCountryId(Long countryId);

}
