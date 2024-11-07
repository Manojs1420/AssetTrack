package com.titan.irgs.accessPolicy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.accessPolicy.domain.BackEndApis;
import com.titan.irgs.accessPolicy.model.BackEndApisModel;
import com.titan.irgs.accessPolicy.model.PermissionModel;
import com.titan.irgs.accessPolicy.service.FeatureService;

@Component
public class BackendApiMapper {
	
	@Autowired
	FeatureService featureService;

	public BackEndApis convertModeltoDomain(BackEndApisModel backEndApisModel) {
		BackEndApis backEndApis = new BackEndApis();
		BeanUtils.copyProperties(backEndApisModel, backEndApis);
		backEndApis.setFeature(featureService.getById(backEndApisModel.getFeatureId()));
		return backEndApis;
	
	}
	
	public BackEndApisModel convertDomainToModel(BackEndApis backEndApis) {
		List<PermissionModel>permissionModels=new ArrayList<PermissionModel>();
		BackEndApisModel backEndApisModel = new BackEndApisModel();
		BeanUtils.copyProperties(backEndApis, backEndApisModel);
		backEndApisModel.setFeatureId(backEndApis.getFeature()==null?null:backEndApis.getFeature().getFeatureId());
		backEndApisModel.setFeatureName(backEndApis.getFeature()==null?null:backEndApis.getFeature().getFeatureName());
		backEndApis.getPermission().forEach(backEndApi->{
			
			PermissionModel PermissionModel=new PermissionModel();
			PermissionModel.setWebRoleId(backEndApi.getWebRole().getWebRoleId());
			PermissionModel.setCreatedOn(backEndApi.getCreatedOn());
			PermissionModel.setVerticalName(backEndApi.getVerticalName()==null?backEndApi.getWebRole().getWebMaster().getWebMasterName():backEndApi.getVerticalName());
			PermissionModel.setBackEndApiId(backEndApi.getBackEndApis().getBackEndApiId());
			PermissionModel.setPermissionId(backEndApi.getPermissionId());
			PermissionModel.setRoleName(backEndApi.getRoleName()==null?backEndApi.getWebRole().getRole().getRoleName():backEndApi.getRoleName());
			permissionModels.add(PermissionModel);
		});
		backEndApisModel.setPermissions(permissionModels);
		return backEndApisModel;
	
	}

}
