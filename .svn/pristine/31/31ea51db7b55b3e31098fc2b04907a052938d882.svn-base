package com.titan.irgs.accessPolicy.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.accessPolicy.domain.Feature;
import com.titan.irgs.accessPolicy.model.FeatureVo;

@Component
public class FeatureMapper {
	
	
	/*
	 * @Autowired SubModuleRepo subModuleRepo;
	 */
	public Feature convertModeltoDomain(FeatureVo featureVo) {
		Feature feature = new Feature();
		BeanUtils.copyProperties(featureVo, feature);
		
		return feature;
	}

	public FeatureVo convertDomaintoModel(Feature feature) {
		FeatureVo featureVo= new FeatureVo();
		BeanUtils.copyProperties(feature, featureVo);
		return featureVo;
	}

}
