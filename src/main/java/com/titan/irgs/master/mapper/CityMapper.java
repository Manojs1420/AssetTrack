package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.vo.CityVO;

@Component
public class CityMapper {
/*	
	@Autowired
	IRegionService regionService;
*/
	
	@Autowired
	IStateService stateService;
	
	public CityVO getVoFromEntity(City city) {
		
		CityVO cityVo = null;
		ModelMapper cityVoModelMapper = new ModelMapper();

		PropertyMap<City, CityVO> cityEntityToVOPropertyMap = new PropertyMap<City, CityVO>() {
			@Override
			protected void configure() {
				map().setCityId(source.getCityId());
				map().setCityName(source.getCityName());
				map().setStateId(source.getState().getStateId());
				map().setStateName(source.getState().getStateName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdatedDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());

			}
		};
		
		cityVoModelMapper.addMappings(cityEntityToVOPropertyMap);
		cityVo = cityVoModelMapper.map(city, CityVO.class);

		return cityVo;

	}

	public City getEntityFromVo(CityVO cityVo) {
		
		City city = null;
		ModelMapper cityModelMapper = new ModelMapper();
		
	//	Region region = regionService.getRegionById(cityVo.getRegionId());

		PropertyMap<CityVO, City> cityVOToEntityPropertyMap = new PropertyMap<CityVO, City>() {
			@Override
			protected void configure() {
				map().setCityId(source.getCityId());
				map().setCityName(source.getCityName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdatedDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
			}
		};
		
		cityModelMapper.addMappings(cityVOToEntityPropertyMap);
		city = cityModelMapper.map(cityVo, City.class);
	//	city.setRegion(region);
		
		if(cityVo.getStateId() !=0) {
		State state = stateService.getStateById(cityVo.getStateId());
		
		city.setState(state);
		}else {
			
			city.setState(null);
		}
		return city;

	}

}