package com.titan.irgs.master.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.vo.DepartmentVO;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class DepartmentMapper {

	@Autowired
	WebMasterRepo webMasterRepo;
	
	public DepartmentVO getVoFromEntity(Department department) {

		DepartmentVO departmentVO = new DepartmentVO();
		BeanUtils.copyProperties(department, departmentVO);
		
		departmentVO.setWebMasterId(department.getWebMaster().getWebMasterId());
		departmentVO.setWebMasterName(department.getWebMaster().getWebMasterName());
		
		return departmentVO;
	}

	public Department getEntityFromVo(DepartmentVO departmentVO) {

		Department department = new Department();
		BeanUtils.copyProperties(departmentVO, department);
		department.setWebMaster(webMasterRepo.findById(departmentVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
		
		return department;
	}

}
