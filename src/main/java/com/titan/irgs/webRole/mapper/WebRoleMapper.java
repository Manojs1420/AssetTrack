package com.titan.irgs.webRole.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.model.WebRoleModel;

@Component
public class WebRoleMapper {

	public WebRole convertModeltoDomain(WebRoleModel webRoleModel) {
		WebRole webRole = new WebRole();
		BeanUtils.copyProperties(webRoleModel, webRole);
		webRole.setEnabledStatus(true);
		webRole.setCreatedBy(1l);
		webRole.setUpdatedBy(1l);
		return webRole;
	}

	public WebRoleModel convertDomaintoModel(WebRole webRole) {
		WebRoleModel webRoleModel= new WebRoleModel();
		BeanUtils.copyProperties(webRole, webRoleModel);
		List<Long> regionIds=new ArrayList<>();
		for(Cluster cluster:webRole.getCluster()) {
			regionIds.add(cluster.getRegion().getRegionId());
		}
		webRoleModel.setRegionIds(regionIds);
		if(webRole.getWebMaster()!=null) {
		webRoleModel.setBussinessVerticaLId(webRole.getWebMaster().getWebMasterId());
		webRoleModel.setBussinessVerticalName(webRole.getWebMaster().getWebMasterName());
		}
		
		if(webRole.getRoleWiseDepartments()!=null) {
			List<RoleWiseDepartments> departmentIds=new ArrayList<RoleWiseDepartments>();
			for(RoleWiseDepartments roleWiseDepartments:webRole.getRoleWiseDepartments()) {
				RoleWiseDepartments roleWiseDepartment=new RoleWiseDepartments();
				roleWiseDepartment.setRoleWiseDepartmentsId(roleWiseDepartments.getRoleWiseDepartmentsId());
				roleWiseDepartment.setDepartment(roleWiseDepartments.getDepartment());
				roleWiseDepartment.setCreatedOn(roleWiseDepartments.getCreatedOn());
				departmentIds.add(roleWiseDepartment);
			}
			webRoleModel.setRoleWiseDepartments(departmentIds);
		}
		
		webRoleModel.setRoleId(webRole.getRole().getRoleId());
		webRoleModel.setRoleName(webRole.getRole().getRoleName());
		webRoleModel.setReportingId(webRole.getReporting().getRoleId());
		webRoleModel.setReportingName(webRole.getReporting().getRoleName());
		webRoleModel.setOperationType(webRole.getOpertionType()==null?null:webRole.getOpertionType().getOpertionType());
		return webRoleModel;
	}

}



