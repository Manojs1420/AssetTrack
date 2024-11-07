package com.titan.irgs.master.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.repository.CountryRepository;
import com.titan.irgs.master.service.ICountryService;


@Service
public class CountryService implements ICountryService{
	
	
	  private Logger logger = LoggerFactory.getLogger(this.getClass());
		
		@Autowired
		private CountryRepository countryRepository;
	    
		/**
		 * getAllCountry -> Method
		 * @param ->none
		 * @return list of saved country entity
		 */
		@Override
		public List<Country> getAllCountry() {
			return countryRepository.findAll();
		}
	    
		/**
		 * getCountryById -> Method
		 * @param countryId
		 * @return saved country entity
		 */
		@Override
		public Country getCountryById(Long countryId) {
			Country country = countryRepository.getOne(countryId);
			if(country == null)
			{
				logger.error("Country with countryId {} not found", countryId);
	            throw new EntityNotFoundException("Country with countryId " + countryId + " not found");
			}
			return country;
		}
	    
		/**
		 * saveCountry ->Method
		 * @param country entity
		 * @return saved country entity
		 */
		@Override
		public Country saveCountry(Country country) {
			country.setCreatedDate(new Date());
			return countryRepository.save(country);
		}
	    
		/**
		 * updateCountry ->Method
		 * @param country entity
		 * @return updated country entity
		 */
		@Override
		public Country updateCountry(Country country) {
			Country country1 = countryRepository.getOne(country.getCountryId());
			if(country1 == null)
			{
				logger.error("Country with countryId {} not found", country.getCountryId());
	            throw new EntityNotFoundException("Country with countryId " + country.getCountryId() + " not found");
			}
			
			return countryRepository.save(country);
		}
	    
		/**
		 * deleteCountryById ->Method
		 * @param countryId
		 * @return none
		 */
		@Override
		public void deleteCountryById(Long countryId) {
			Country country1 = countryRepository.getOne(countryId);
			if(country1 == null)
			{
				logger.error("Country with countryId {} not found", countryId);
	            throw new EntityNotFoundException("Country with countryId " + countryId + " not found");
			}
			countryRepository.deleteById(countryId);

		}
	    
		@Override
		public boolean checkIfCountryNameIsExit(String countryName) {
			Country country = countryRepository.findByCountryName(countryName);
			if (country != null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Country findByCountryName(String countryName) {
			Country country = countryRepository.findByCountryName(countryName);
			
			/*if (brand == null) {
				logger.error("brand with brandName {} not found", brandName);
				throw new EntityNotFoundException("brand with brandName " + brandName + " not found");
			}*/
			return country;
		}

		@Override
		public List<Country> findByCountryIdIn(List<Long> countryIds) {
			
			return countryRepository.findByCountryIdIn(countryIds);
		}

}
