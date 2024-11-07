package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.StoreAlloted;
import com.titan.irgs.master.vo.StoreAllotedVO;

@Component
public class StoreAllotedMapper {
	
	public StoreAllotedVO getVoFromEntity(StoreAlloted StoreAlloted) {

		StoreAllotedVO StoreAllotedVo = null;
		ModelMapper StoreAllotedVoModelMapper = new ModelMapper();

		PropertyMap<StoreAlloted, StoreAllotedVO> StoreAllotedEntityToVOPropertyMap = new PropertyMap<StoreAlloted, StoreAllotedVO>() {
			@Override
			protected void configure() {
				map().setStoreAllotedId(source.getStoreAllotedId());
				map().setStoreAllotedType(source.getStoreAllotedType());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());

			}
		};

		StoreAllotedVoModelMapper.addMappings(StoreAllotedEntityToVOPropertyMap);
		StoreAllotedVo = StoreAllotedVoModelMapper.map(StoreAlloted, StoreAllotedVO.class);

		return StoreAllotedVo;

	}

	public StoreAlloted getEntityFromVo(StoreAllotedVO StoreAllotedVo) {

		StoreAlloted StoreAlloted = null;
		ModelMapper StoreAllotedModelMapper = new ModelMapper();

		PropertyMap<StoreAllotedVO, StoreAlloted> StoreAllotedVOToEntityPropertyMap = new PropertyMap<StoreAllotedVO, StoreAlloted>() {
			@Override
			protected void configure() {
				
				map().setStoreAllotedId(source.getStoreAllotedId());
				map().setStoreAllotedType(source.getStoreAllotedType());
				
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
			}
		};

		StoreAllotedModelMapper.addMappings(StoreAllotedVOToEntityPropertyMap);
		StoreAlloted = StoreAllotedModelMapper.map(StoreAllotedVo, StoreAlloted.class);

		return StoreAlloted;

	}


}
