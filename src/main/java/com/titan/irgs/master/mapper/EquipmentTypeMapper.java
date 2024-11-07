package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.vo.EquipmentTypeVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class EquipmentTypeMapper {
	@Autowired
	WebMasterService webMasterService;
	
	public EquipmentTypeVO getVoFromEntity(EquipmentType equipmentType) {

		EquipmentTypeVO equipmentTypeVo = null;
		ModelMapper equipmentTypeVoModelMapper = new ModelMapper();

		PropertyMap<EquipmentType, EquipmentTypeVO> equipmentTypeEntityToVOPropertyMap = new PropertyMap<EquipmentType, EquipmentTypeVO>() {
			@Override
			protected void configure() {
				map().setEquipmentTypeName(source.getEquipmentTypeName());
				map().setEquipmentTypeId(source.getEquipmentTypeId());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setWebMasterId(source.getWebMaster().getWebMasterId());
				map().setWebMasterName(source.getWebMaster().getWebMasterName());

			}
		};
		equipmentTypeVoModelMapper.addMappings(equipmentTypeEntityToVOPropertyMap);
		equipmentTypeVo = equipmentTypeVoModelMapper.map(equipmentType, EquipmentTypeVO.class);
		return equipmentTypeVo;

	}

	public EquipmentType getEntityFromVo(EquipmentTypeVO equipmentTypeVo) {

		EquipmentType equipmentType = null;
		ModelMapper equipmentTypeModelMapper = new ModelMapper();
		webMasterService.getById(equipmentTypeVo.getWebMasterId());
		
		PropertyMap<EquipmentTypeVO, EquipmentType> equipmentTypeVOToEntityPropertyMap = new PropertyMap<EquipmentTypeVO, EquipmentType>() {
			@Override
			protected void configure() {
				
				map().setEquipmentTypeName(source.getEquipmentTypeName());
				map().setEquipmentTypeId(source.getEquipmentTypeId());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				
			}
		};
		equipmentTypeModelMapper.addMappings(equipmentTypeVOToEntityPropertyMap);
		equipmentType = equipmentTypeModelMapper.map(equipmentTypeVo, EquipmentType.class);
		
		WebMaster webMaster = webMasterService.getById(equipmentType.getWebMaster().getWebMasterId());
		equipmentTypeVo.setWebMasterId(webMaster.getWebMasterId());
		equipmentTypeVo.setWebMasterName(webMaster.getWebMasterName());
		return equipmentType;
	}

	
	

}
