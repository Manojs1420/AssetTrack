package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.StoreBusinessServiceType;
import com.titan.irgs.master.vo.StoreBusinessServiceTypeVO;

@Component
public class StoreBusinessServiceTypeMapper {
	
	public StoreBusinessServiceTypeVO getVoFromEntity(StoreBusinessServiceType StoreBusinessServiceType) {

		StoreBusinessServiceTypeVO StoreBusinessServiceTypeVo = null;
		ModelMapper StoreBusinessServiceTypeVoModelMapper = new ModelMapper();

		PropertyMap<StoreBusinessServiceType, StoreBusinessServiceTypeVO> StoreBusinessServiceTypeEntityToVOPropertyMap = new PropertyMap<StoreBusinessServiceType, StoreBusinessServiceTypeVO>() {
			@Override
			protected void configure() {
				map().setStoreBusinessServiceTypeId(source.getStoreBusinessServiceTypeId());
				map().setStoreBusinessServiceTypeName(source.getStoreBusinessServiceTypeName());
				
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());

			}
		};

		StoreBusinessServiceTypeVoModelMapper.addMappings(StoreBusinessServiceTypeEntityToVOPropertyMap);
		StoreBusinessServiceTypeVo = StoreBusinessServiceTypeVoModelMapper.map(StoreBusinessServiceType, StoreBusinessServiceTypeVO.class);

		return StoreBusinessServiceTypeVo;

	}

	public StoreBusinessServiceType getEntityFromVo(StoreBusinessServiceTypeVO StoreBusinessServiceTypeVo) {

		StoreBusinessServiceType StoreBusinessServiceType = null;
		ModelMapper StoreBusinessServiceTypeModelMapper = new ModelMapper();

		PropertyMap<StoreBusinessServiceTypeVO, StoreBusinessServiceType> StoreBusinessServiceTypeVOToEntityPropertyMap = new PropertyMap<StoreBusinessServiceTypeVO, StoreBusinessServiceType>() {
			@Override
			protected void configure() {
				
				
				map().setStoreBusinessServiceTypeId(source.getStoreBusinessServiceTypeId());
				map().setStoreBusinessServiceTypeName(source.getStoreBusinessServiceTypeName());
				
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
			}
		};

		StoreBusinessServiceTypeModelMapper.addMappings(StoreBusinessServiceTypeVOToEntityPropertyMap);
		StoreBusinessServiceType = StoreBusinessServiceTypeModelMapper.map(StoreBusinessServiceTypeVo, StoreBusinessServiceType.class);

		return StoreBusinessServiceType;

	}


}
