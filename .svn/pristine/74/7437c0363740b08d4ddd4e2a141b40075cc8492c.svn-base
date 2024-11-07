package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.VendorType;
import com.titan.irgs.master.vo.VendorTypeVO;

/**
 * Model Mapper class(i.e, VendorTypeMapper) which is responsible for converting VO(i.e VendorTypeVO) -> DO(i.e VendorType) and vice versia
 * @author 
 *
 */
@Component
public class VendorTypeMapper {

	public VendorTypeVO getVoFromEntity(VendorType vendorType) {
		VendorTypeVO vendorTypeVo = null;

		ModelMapper vendorTypeVoModelMapper = new ModelMapper();

		PropertyMap<VendorType, VendorTypeVO> vendorTypeEntityToVOPropertyMap = new PropertyMap<VendorType, VendorTypeVO>() {
			@Override
			protected void configure() {
				map().setVendorTypeId(source.getVendorTypeId());
				map().setVendorTypeName(source.getVendorTypeName());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
			}
		};
		vendorTypeVoModelMapper.addMappings(vendorTypeEntityToVOPropertyMap);
		vendorTypeVo = vendorTypeVoModelMapper.map(vendorType, VendorTypeVO.class);
		return vendorTypeVo;
	}

	public VendorType getEntityFromVo(VendorTypeVO vendorTypeVo) {
		
		ModelMapper vendorTypeModelMapper = new ModelMapper();
		VendorType vendorType = null;

		PropertyMap<VendorTypeVO, VendorType> vendorTypeVoToEntityPropertyMap = new PropertyMap<VendorTypeVO, VendorType>() {
			@Override
			protected void configure() {
				map().setVendorTypeName(source.getVendorTypeName());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());

			}
		};
		vendorTypeModelMapper.addMappings(vendorTypeVoToEntityPropertyMap);
		vendorType = vendorTypeModelMapper.map(vendorTypeVo, VendorType.class);
		return vendorType;
	}
}
