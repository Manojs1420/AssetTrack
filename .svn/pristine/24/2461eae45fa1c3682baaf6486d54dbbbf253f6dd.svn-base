package com.titan.irgs.master.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.repository.CityRepository;
import com.titan.irgs.master.service.ICityService;

@Service
public class CityService implements ICityService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CityRepository cityRepository;
    
	/**
	 * getAllCity -> Method
	 * @param ->none
	 * @return list of saved city entity
	 */
	@Override
	public List<City> getAllCity() {
		return cityRepository.findAll();
	}
    
	/**
	 * getCityById -> Method
	 * @param cityId
	 * @return saved city entity
	 */
	@Override
	public  City getCityById(Long cityId) {
	City city = cityRepository.getById(cityId);
		if (city == null) {
			logger.error("city with cityId {} not found", cityId);
			throw new EntityNotFoundException("city with cityId " + cityId + " not found");
		}
		return city;
	}
    
	/**
	 * saveCity ->Method
	 * @param city entity
	 * @return saved city entity
	 */
	@Override
	public City saveCity(City city) {
		city.setCreatedDate(new Date());
		return cityRepository.save(city);
	}
    
	/**
	 * updateCity ->Method
	 * @param city entity
	 * @return updated city entity
	 */
	@Override
	public City updateCity(City city) {
		Optional<City> city1 = cityRepository.findById(city.getCityId());
		if (city1 == null) {
			logger.error("city with cityId {} not found", city.getCityId());
			throw new EntityNotFoundException("city with cityId " + city.getCityId() + " not found");
		}

		return cityRepository.save(city);
	}
    
	/**
	 * deleteCityById ->Method
	 * @param cityId
	 * @return none
	 */
	@Override
	public void deleteCityById(Long cityId) {
		Optional<City> city = cityRepository.findById(cityId);
		if (city == null) {
			logger.error("city with cityId {} not found", cityId);
			throw new EntityNotFoundException("city with cityId " + cityId + " not found");
		}
		cityRepository.deleteById(cityId);
	}
    
	/**
	 * checkIfCityNameIsExit ->Method
	 * @param cityName
	 * @return boolean
	 */
	@Override
	public boolean checkIfCityNameIsExit(String cityName) {
		City city = cityRepository.findByCityName(cityName);
		if (city != null) {
			return true;
		} else {
			return false;
		}
	}
/*
	@Override
	public List<City> getAllCityOnRegionId(Long regionId) {
		Region region = regionRepository.findOne(regionId);
		if (region == null) {
			logger.error("region with regionId {} not found", regionId);
			throw new EntityNotFoundException("region with regionId " + regionId + " not found");
		}
		return region.getCities();
	}
	
	*/
	
	@Override
	public City findByCityName(String cityName) {
		City city = cityRepository.findByCityName(cityName);
		
		/*if (city == null) {
			logger.error("city with cityName {} not found", cityName);
			throw new EntityNotFoundException("city with cityName " + cityName + " not found");
		}*/
		return city;
	}

@Override
public List<City> findByStateStateId(List<Long> stateIds) {
	
	return cityRepository.findByStateStateIdIn(stateIds);
}

@Override
public List<City> findByStateStateId(Long stateId) {
	
	return cityRepository.findByStateStateId(stateId);
}

@Override
public List<City> findByCityId(List<Long> cityIds) {
	
	return cityRepository.findByCityIdIn(cityIds);
}

@Override
public List<Object[]> getAllCitysForExcel() {
	// TODO Auto-generated method stub
	return cityRepository.getAllCitysForExcel();
}




	

	
}

