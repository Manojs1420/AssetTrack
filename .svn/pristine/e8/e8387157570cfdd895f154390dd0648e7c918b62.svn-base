package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Engineer;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.service.IVendorService;
import com.titan.irgs.master.vo.EngineerVO;

/**
 * Model Mapper class(i.e, ModelMapper) which is responsible for converting
 * VO(i.e EngineerVO) -> DO(i.e Engineer) and vice versia
 * 
 * @author 
 *
 */
@Component
public class EngineerMapper {
	
	
	@Autowired
	IVendorService vendorService;
	
	public EngineerVO getVoFromEntity(Engineer engineer) {

		EngineerVO engineerVo = null;
		ModelMapper engineerVoModelMapper = new ModelMapper();

		PropertyMap<Engineer, EngineerVO> engineerEntityToVOPropertyMap = new PropertyMap<Engineer, EngineerVO>() {
			@Override
			protected void configure() {
				
				map().setEngineerId(source.getEngineerId());
				map().setEngineerName(source.getEngineerName());
				map().setEmailId(source.getEmailId());
				map().setMobileNo(source.getMobileNo());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
				map().setVendorId(source.getVendorId());
                map().setVendorName(source.getVendorName());
			}
		};

		engineerVoModelMapper.addMappings(engineerEntityToVOPropertyMap);
		engineerVo = engineerVoModelMapper.map(engineer, EngineerVO.class);
		engineerVo.setVendorCode(vendorService.getVendorById(engineer.getVendorId()).getVendorCode());
		return engineerVo;

	}

	public Engineer getEntityFromVo(EngineerVO engineerVo) {

		Engineer engineer = null;
		ModelMapper engineerModelMapper = new ModelMapper();

		PropertyMap<EngineerVO, Engineer> engineerVOToEntityPropertyMap = new PropertyMap<EngineerVO, Engineer>() {
			@Override
			protected void configure() {
				
				map().setEngineerName(source.getEngineerName());
				map().setEmailId(source.getEmailId());
				map().setMobileNo(source.getMobileNo());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
			}
		};

		engineerModelMapper.addMappings(engineerVOToEntityPropertyMap);
		engineer = engineerModelMapper.map(engineerVo, Engineer.class);
		
		if(engineerVo.getVendorId() != 0){
			
			Vendor vendor = vendorService.getVendorById(engineerVo.getVendorId());
			engineer.setVendorId(vendor.getVendorId());
			engineer.setVendorName(vendor.getVendorName());
		}
		
		return engineer;

	}

}
