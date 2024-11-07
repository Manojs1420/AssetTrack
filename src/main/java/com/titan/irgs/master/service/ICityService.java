package com.titan.irgs.master.service;

import java.util.List;

import com.titan.irgs.master.domain.City;

public interface ICityService {

	List<City> getAllCity();

	City getCityById(Long cityId);

	City saveCity(City city);

	City updateCity(City city);

	void deleteCityById(Long cityId);
	
	boolean checkIfCityNameIsExit(String cityName);
	
	City findByCityName(String cityName);
	
	List<City> findByStateStateId(List<Long> stateIds);
	
	List<City>findByStateStateId(Long stateId);
	
	List<City>findByCityId(List<Long> cityIds);

	List<Object[]> getAllCitysForExcel();

	
//	List<City> getAllCityOnRegionId(Long regionId);

}
