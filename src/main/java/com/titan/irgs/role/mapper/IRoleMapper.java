package com.titan.irgs.role.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.model.RoleModel;

/**
 * 
 * @author hari.k
 *
 */

@Component
public class IRoleMapper {


	public Role convertModeltoDomain(RoleModel roleModel) {
		Role role = new Role();
		BeanUtils.copyProperties(roleModel, role);

		return role;
	}

	public RoleModel convertDomaintoModel(Role roles) {

		RoleModel roleModel=new RoleModel();
		BeanUtils.copyProperties(roles, roleModel);
		/*
		if(roles.getRoleWiseDepartments().size()!=0){
			List<RoleWiseDepartmentsVO> roleWiseDepartmentsVOs=new ArrayList<RoleWiseDepartmentsVO>();

			roles.getRoleWiseDepartments().forEach(roleWiseDepartments->{
				RoleWiseDepartmentsVO roleWiseDepartmentsVO=new RoleWiseDepartmentsVO();
				roleWiseDepartmentsVO.setCreatedOn(roleWiseDepartments.getCreatedOn());
				roleWiseDepartmentsVO.setDepartmentId(roleWiseDepartments.getDepartment().getDepartmentId());
				roleWiseDepartmentsVO.setDepartmentName(roleWiseDepartments.getDepartment().getDepartmentName());
				roleWiseDepartmentsVO.setRoleId(roleWiseDepartments.getRole().getRoleId());
				roleWiseDepartmentsVO.setRoleWiseDepartmentsId(roleWiseDepartments.getRoleWiseDepartmentsId());
				roleWiseDepartmentsVO.setUpdatedOn(roleWiseDepartments.getUpdatedOn());
				roleWiseDepartmentsVO.setRoleName(roleWiseDepartments.getRole().getRoleName());

				roleWiseDepartmentsVOs.add(roleWiseDepartmentsVO);
			});
			roleModel.setRoleWiseDepartmentsVOs(roleWiseDepartmentsVOs);
		}
		*/
		return roleModel;
	}

}
