package com.titan.irgs.master.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.AssetSpecification;
import com.titan.irgs.master.vo.AssetSpecificationVo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;


@Component
public class AssetSpecificationMapper {
	@Autowired
	WebMasterService webMasterService;
	@Autowired
	WebMasterRepo webMasterRepo;

	public AssetSpecificationVo getVoFromEntity(AssetSpecification assetSpecification) {
			AssetSpecificationVo assetSpecificationVo=new AssetSpecificationVo();
			BeanUtils.copyProperties(assetSpecification, assetSpecificationVo);
			WebMaster webMaster = webMasterService.getById(assetSpecification.getWebMaster().getWebMasterId());
			assetSpecificationVo.setWebMasterId(webMaster.getWebMasterId());
			assetSpecificationVo.setWebMasterName(webMaster.getWebMasterName());

		return assetSpecificationVo;
	}

	public AssetSpecification getEntityFromVo(AssetSpecificationVo assetSpecificationVo) {
		AssetSpecification assetSpecification=new AssetSpecification();
		BeanUtils.copyProperties(assetSpecificationVo, assetSpecification);
		assetSpecification.setWebMaster(webMasterRepo.findById(assetSpecificationVo.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));

	return assetSpecification;
	}

	
	
	
	
	
	
	
	
}
