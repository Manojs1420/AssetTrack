package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.domain.Model;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.master.vo.ModelVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

/**
 * Model Mapper class(i.e, ModelMapper) which is responsible for converting
 * VO(i.e ModelVO) -> DO(i.e Model) and vice versia
 * 
 * @author 
 *
 */
@Component
public class ModelMappers {
	
	@Autowired
	private IBrandService brandService;
	
	@Autowired
	WebMasterService webMasterService;

	public ModelVO getVoFromEntity(Model model) {

		ModelVO modelVo = null;
		ModelMapper modelVoModelMapper = new ModelMapper();

		PropertyMap<Model, ModelVO> modelEntityToVOPropertyMap = new PropertyMap<Model, ModelVO>() {
			@Override
			protected void configure() {
				
				map().setModelId(source.getModelId());
				map().setModelName(source.getModelName());
				map().setModelNo(source.getModelNo());
				map().setBrandId(source.getBrand().getBrandId());
				map().setBrandName(source.getBrand().getBrandName());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setWebMasterId(source.getWebMaster().getWebMasterId());
				map().setWebMasterName(source.getWebMaster().getWebMasterName());
				map().setRemarks(source.getRemarks());

			}
		};

		//modelVoModelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelVoModelMapper.addMappings(modelEntityToVOPropertyMap);
		modelVo = modelVoModelMapper.map(model, ModelVO.class);

		return modelVo;

	}

	public Model getEntityFromVo(ModelVO modelVo) {

		Model model = null;
		ModelMapper modelModelMapper = new ModelMapper();
		webMasterService.getById(modelVo.getWebMasterId());

		
		brandService.getBrandById(modelVo.getBrandId());

		PropertyMap<ModelVO, Model> modelVOToEntityPropertyMap = new PropertyMap<ModelVO, Model>() {
			@Override
			protected void configure() {
				
				map().setModelName(source.getModelName());
				map().setModelNo(source.getModelNo());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setRemarks(source.getRemarks());
			}
		};

		//modelModelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelModelMapper.addMappings(modelVOToEntityPropertyMap);
		model = modelModelMapper.map(modelVo, Model.class);
		
		Brand brand = null;
		
		if(modelVo.getBrandId() == null) {
			
			brand = brandService.findByBrandName(modelVo.getBrandName());
			
		} else {
			
			brand = brandService.getBrandById(modelVo.getBrandId());
		}
		
		model.setBrand(brand);
		

		WebMaster webMaster = webMasterService.getById(model.getWebMaster().getWebMasterId());
		modelVo.setWebMasterId(webMaster.getWebMasterId());
		modelVo.setWebMasterName(webMaster.getWebMasterName());
		return model;
	}


}
