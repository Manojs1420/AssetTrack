package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.vo.CountryVO;

@Component
public class CountryMapper {
	
	public CountryVO getVoFromEntity(Country country) {

		CountryVO countryVo = null;
		ModelMapper countryVoModelMapper = new ModelMapper();

		PropertyMap<Country, CountryVO> countryEntityToVOPropertyMap = new PropertyMap<Country, CountryVO>() {
			@Override
			protected void configure() {
				map().setCountryId(source.getCountryId());
				map().setCountryName(source.getCountryName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdatedDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
			}
		};

		countryVoModelMapper.addMappings(countryEntityToVOPropertyMap);
		countryVo = countryVoModelMapper.map(country, CountryVO.class);
		return countryVo;
	}

	public Country getEntityFromVo(CountryVO countryVo) {

		ModelMapper countryModelMapper = new ModelMapper();
		Country country = null;

		PropertyMap<CountryVO, Country> countryVOToEntityPropertyMap = new PropertyMap<CountryVO, Country>() {

			@Override
			protected void configure() {
				map().setCountryName(source.getCountryName());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdatedDate());

			}
		};

		countryModelMapper.addMappings(countryVOToEntityPropertyMap);
		country = countryModelMapper.map(countryVo, Country.class);
		return country;
	}


}
