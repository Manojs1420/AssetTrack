package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.master.vo.StoreServiceTypeVO;

@Component
public class StoreServiceTypeMapper {
	
	public StoreServiceTypeVO getVoFromEntity(StoreServiceType StoreServiceType) {

		StoreServiceTypeVO StoreServiceTypeVo = null;
		ModelMapper StoreServiceTypeVoModelMapper = new ModelMapper();

		PropertyMap<StoreServiceType, StoreServiceTypeVO> StoreServiceTypeEntityToVOPropertyMap = new PropertyMap<StoreServiceType, StoreServiceTypeVO>() {
			@Override
			protected void configure() {
				map().setStoreServiceTypeId(source.getStoreServiceTypeId());
				map().setStoreServiceTypeName(source.getStoreServiceTypeName());
				map().setSla(source.getSla());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());

			}
		};

		StoreServiceTypeVoModelMapper.addMappings(StoreServiceTypeEntityToVOPropertyMap);
		StoreServiceTypeVo = StoreServiceTypeVoModelMapper.map(StoreServiceType, StoreServiceTypeVO.class);

		return StoreServiceTypeVo;

	}

	public StoreServiceType getEntityFromVo(StoreServiceTypeVO StoreServiceTypeVo) {

		StoreServiceType StoreServiceType = null;
		ModelMapper StoreServiceTypeModelMapper = new ModelMapper();

		PropertyMap<StoreServiceTypeVO, StoreServiceType> StoreServiceTypeVOToEntityPropertyMap = new PropertyMap<StoreServiceTypeVO, StoreServiceType>() {
			@Override
			protected void configure() {
				
				map().setStoreServiceTypeId(source.getStoreServiceTypeId());
				map().setStoreServiceTypeName(source.getStoreServiceTypeName());
				map().setSla(source.getSla());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
			}
		};

		StoreServiceTypeModelMapper.addMappings(StoreServiceTypeVOToEntityPropertyMap);
		StoreServiceType = StoreServiceTypeModelMapper.map(StoreServiceTypeVo, StoreServiceType.class);

		return StoreServiceType;

	}

}
