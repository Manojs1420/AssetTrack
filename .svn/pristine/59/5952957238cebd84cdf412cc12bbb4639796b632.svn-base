package com.titan.irgs.master.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.domain.RegionDetails;
import com.titan.irgs.master.service.ICityService;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.vo.RegionDetailsVO;
import com.titan.irgs.master.vo.RegionVO;

@Component
public class RegionMapper {	
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private IStateService stateService;
 
	public RegionVO getVoFromEntity(Region region) {
		
		RegionVO regionVo = null;
		ModelMapper regionVoModelMapper = new ModelMapper();
		List<RegionDetailsVO> RegionDetailsVOs=new ArrayList<>();
		PropertyMap<Region, RegionVO> regionEntityToVOPropertyMap = new PropertyMap<Region, RegionVO>() {
			@Override
			protected void configure() {
				map().setRegionId(source.getRegionId());
				map().setRegionName(source.getRegionName());
				//map().setCityId(source.getCity().getCityId());
				//map().setCityName(source.getCity().getCityName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdatedDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				//map().setStateId(source.getState().getStateId());
				//map().setStateName(source.getState().getStateName());
			}
		};

		regionVoModelMapper.addMappings(regionEntityToVOPropertyMap);
		regionVo = regionVoModelMapper.map(region, RegionVO.class);
		if(region.getRegionDetails()!=null) {
			for(RegionDetails regionDetails:region.getRegionDetails()) {
				RegionDetailsVO regionDetailsVO=new RegionDetailsVO();
				regionDetailsVO.setRegionDetailsId(regionDetails.getRegionDetailsId());
				regionDetailsVO.setStateName(regionDetails.getState().getStateName());
				regionDetailsVO.setStateId(regionDetails.getState().getStateId());
				RegionDetailsVOs.add(regionDetailsVO);
			}
		}
		regionVo.setRegionInfo(RegionDetailsVOs);
		return regionVo;
	}

	public Region getEntityFromVo(RegionVO regionVo) {
		
		ModelMapper regionModelMapper = new ModelMapper();
		Region region = null;
		
		
		
		PropertyMap<RegionVO, Region> regionVOToEntityPropertyMap = new PropertyMap<RegionVO, Region>() {
			
			@Override
			protected void configure() {
				map().setRegionName(source.getRegionName());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdatedDate());
			}
		};

		regionModelMapper.addMappings(regionVOToEntityPropertyMap);
		region = regionModelMapper.map(regionVo, Region.class);
		
		/*if(regionVo.getCityId() != 0) {
			City city = cityService.getCityById(regionVo.getCityId());
			region.setCity(city);
		}else {
			region.setCity(null);
		}
		
	
		
		State state = stateService.getStateById(regionVo.getStateId());
		region.setState(state);*/
		
		return region;
	}
}
