package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.vo.OwnerTypeVO;

@Component
public class OwnerTypeMapper {
	
	
	public OwnerTypeVO getVoFromEntity(OwnerType OwnerType) {
		OwnerTypeVO OwnerTypeVo = null;

		ModelMapper OwnerTypeVoModelMapper = new ModelMapper();

		PropertyMap<OwnerType, OwnerTypeVO> OwnerTypeEntityToVOPropertyMap = new PropertyMap<OwnerType, OwnerTypeVO>() {
			@Override
			protected void configure() {
				map().setOwnerTypeId(source.getOwnerTypeId());
				map().setOwnerTypeName(source.getOwnerTypeName());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
			}
		};
		OwnerTypeVoModelMapper.addMappings(OwnerTypeEntityToVOPropertyMap);
		OwnerTypeVo = OwnerTypeVoModelMapper.map(OwnerType, OwnerTypeVO.class);
		return OwnerTypeVo;
	}

	public OwnerType getEntityFromVo(OwnerTypeVO OwnerTypeVo) {
		
		ModelMapper OwnerTypeModelMapper = new ModelMapper();
		OwnerType OwnerType = null;

		PropertyMap<OwnerTypeVO, OwnerType> OwnerTypeVoToEntityPropertyMap = new PropertyMap<OwnerTypeVO, OwnerType>() {
			@Override
			protected void configure() {
				map().setOwnerTypeName(source.getOwnerTypeName());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());

			}
		};
		OwnerTypeModelMapper.addMappings(OwnerTypeVoToEntityPropertyMap);
		OwnerType = OwnerTypeModelMapper.map(OwnerTypeVo, OwnerType.class);
		return OwnerType;
	}

}
