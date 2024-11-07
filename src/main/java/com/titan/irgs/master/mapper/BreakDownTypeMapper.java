package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.titan.irgs.master.vo.BreakDownTypeVO;
import com.titan.irgs.serviceRequest.domain.BreakDownType;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

/**
 * Model Mapper class(i.e, ModelMapper) which is responsible for converting
 * VO(i.e BreakDownTypeVO) -> DO(i.e BreakDownTypem) and vice versia
 * 
 * @author birendra
 *
 */
@Component
public class BreakDownTypeMapper {

	@Autowired
	WebMasterService webMasterService;

	
	public BreakDownTypeVO getVoFromEntity(BreakDownType breakDownType) {

		BreakDownTypeVO breakDownTypeVO = null;
		ModelMapper breakDownTypeVOModelMapper = new ModelMapper();

		PropertyMap<BreakDownType, BreakDownTypeVO> brandEntityToVOPropertyMap = new PropertyMap<BreakDownType, BreakDownTypeVO>() {
			@Override
			protected void configure() {
				
				map().setBreakdownId(source.getBreakDownTypeId());
				map().setBreakdownName(source.getBreakDownTypeName());
//				map().setCreatedBy(source.get());
//				map().setUpdatedBy(source.getUpdatedBy());
//				map().setUpdatedOn(source.getUpdatedOn());
//				map().setCreatedOn(source.getCreatedOn());
				map().setWebMasterId(source.getWebMaster().getWebMasterId());
				map().setWebMasterName(source.getWebMaster().getWebMasterName());

			}
		};

		breakDownTypeVOModelMapper.addMappings(brandEntityToVOPropertyMap);
		breakDownTypeVO = breakDownTypeVOModelMapper.map(breakDownType, BreakDownTypeVO.class);

		return breakDownTypeVO;

	}

	public BreakDownType getEntityFromVo(BreakDownTypeVO breakDownTypeVO) {

		BreakDownType breakDownTypem = null;
		ModelMapper brandModelMapper = new ModelMapper();
		webMasterService.getById(breakDownTypeVO.getWebMasterId());


		PropertyMap<BreakDownTypeVO, BreakDownType> brandVOToEntityPropertyMap = new PropertyMap<BreakDownTypeVO, BreakDownType>() {
			@Override
			protected void configure() {
				
				map().setBreakDownTypeId(source.getBreakdownId());
				map().setBreakDownTypeName(source.getBreakdownName());

				
			}
		};

		brandModelMapper.addMappings(brandVOToEntityPropertyMap);
		breakDownTypem = brandModelMapper.map(breakDownTypeVO, BreakDownType.class);
		

		WebMaster webMaster = webMasterService.getById(breakDownTypem.getWebMaster().getWebMasterId());
		breakDownTypeVO.setWebMasterId(webMaster.getWebMasterId());
		breakDownTypeVO.setWebMasterName(webMaster.getWebMasterName());
		
		return breakDownTypem;

	}

}
