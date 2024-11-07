package com.titan.irgs.master.service;

import java.util.List;

import com.titan.irgs.master.domain.Country;

public interface ICountryService {
	
	List<Country> getAllCountry();

	Country getCountryById(Long countryId);

	Country saveCountry(Country country);

	Country updateCountry(Country country);

	void deleteCountryById(Long countryId);
	
	boolean checkIfCountryNameIsExit(String countryName);

//	Country findByCountryName(String countryName);

	Country findByCountryName(String countryName);
	
	List<Country> findByCountryIdIn(List<Long> countryIds);

}
