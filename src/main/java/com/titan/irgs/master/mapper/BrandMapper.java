package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.vo.BrandVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

/**
 * Model Mapper class(i.e, ModelMapper) which is responsible for converting
 * VO(i.e BrandVO) -> DO(i.e Brand) and vice versia
 * 
 * @author birendra
 *
 */
@Component
public class BrandMapper {

	@Autowired
	WebMasterService webMasterService;

	
	public BrandVO getVoFromEntity(Brand brand) {

		BrandVO brandVo = null;
		ModelMapper brandVoModelMapper = new ModelMapper();

		PropertyMap<Brand, BrandVO> brandEntityToVOPropertyMap = new PropertyMap<Brand, BrandVO>() {
			@Override
			protected void configure() {
				
				map().setBrandId(source.getBrandId());
				map().setBrandCode(source.getBrandCode());
				map().setBrandName(source.getBrandName());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
				map().setWebMasterId(source.getWebMaster().getWebMasterId());
				map().setWebMasterName(source.getWebMaster().getWebMasterName());

			}
		};

		brandVoModelMapper.addMappings(brandEntityToVOPropertyMap);
		brandVo = brandVoModelMapper.map(brand, BrandVO.class);

		return brandVo;

	}

	public Brand getEntityFromVo(BrandVO brandVo) {

		Brand brand = null;
		ModelMapper brandModelMapper = new ModelMapper();
		webMasterService.getById(brandVo.getWebMasterId());


		PropertyMap<BrandVO, Brand> brandVOToEntityPropertyMap = new PropertyMap<BrandVO, Brand>() {
			@Override
			protected void configure() {
				
				map().setBrandCode(source.getBrandCode());
				map().setBrandName(source.getBrandName());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedOn(source.getCreatedOn());
				
			}
		};

		brandModelMapper.addMappings(brandVOToEntityPropertyMap);
		brand = brandModelMapper.map(brandVo, Brand.class);
		

		WebMaster webMaster = webMasterService.getById(brand.getWebMaster().getWebMasterId());
		brandVo.setWebMasterId(webMaster.getWebMasterId());
		brandVo.setWebMasterName(webMaster.getWebMasterName());
		
		return brand;

	}

}
