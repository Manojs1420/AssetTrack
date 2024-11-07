package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.service.ICityService;
import com.titan.irgs.master.service.ICountryService;
import com.titan.irgs.master.service.IRegionService;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.vo.ClusterVO;

@Component
public class ClusterMapper {
	@Autowired
	IStateService stateService;
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ICityService cityService;
	
	@Autowired
	IRegionService regionService;
	
	public ClusterVO getVoFromEntity(Cluster cluster) {
		
		ClusterVO clusterVo = null;
		ModelMapper clusterVoModelMapper = new ModelMapper();

		PropertyMap<Cluster, ClusterVO> clusterEntityToVOPropertyMap = new PropertyMap<Cluster, ClusterVO>() {
			@Override
			protected void configure() {
				map().setClusterId(source.getClusterId());
				map().setClusterName(source.getClusterName());
				map().setStateId(source.getState().getStateId());
				map().setStateName(source.getState().getStateName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setCityId(source.getCity().getCityId());
				map().setCityName(source.getCity().getCityName());
				map().setCountryId(source.getCountry().getCountryId());
				map().setCountryName(source.getCountry().getCountryName());
				map().setRegionId(source.getRegion().getRegionId());
				map().setRegionName(source.getRegion().getRegionName());
				map().setWebAppId(source.getWebAppId());

			}
		};
		
		clusterVoModelMapper.addMappings(clusterEntityToVOPropertyMap);
		clusterVo = clusterVoModelMapper.map(cluster, ClusterVO.class);

		return clusterVo;

	}

	public Cluster getEntityFromVo(ClusterVO clusterVo) {
		
		Cluster cluster = null;
		ModelMapper clusterModelMapper = new ModelMapper();
		
	

		PropertyMap<ClusterVO, Cluster> clusterVOToEntityPropertyMap = new PropertyMap<ClusterVO, Cluster>() {
			@Override
			protected void configure() {
				map().setClusterId(source.getClusterId());
				map().setClusterName(source.getClusterName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setWebAppId(source.getWebAppId());
			}
		};
		
		clusterModelMapper.addMappings(clusterVOToEntityPropertyMap);
		cluster = clusterModelMapper.map(clusterVo, Cluster.class);
		
		
		State state = stateService.getStateById(clusterVo.getStateId());
		
	      City city = cityService.getCityById(clusterVo.getCityId());
		
		Country country = countryService.getCountryById(clusterVo.getCountryId());
		
		Region region = regionService.getRegionById(clusterVo.getRegionId());
		
		
		cluster.setState(state);
		cluster.setRegion(region);
		cluster.setCountry(country);
		cluster.setCity(city);
		
		return cluster;

	}


}
