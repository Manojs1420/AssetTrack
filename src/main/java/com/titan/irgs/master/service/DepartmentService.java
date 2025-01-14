package com.titan.irgs.master.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.vo.DepartmentVO;

public interface DepartmentService {

	Page<Department> getAllDepartment(DepartmentVO departmentVO, Pageable page);

	Department getDepartmentById(Long departmentId);

	Department saveDepartment(Department department);

	Department updateDepartment(Department department);

	void deleteDepartmentById(Long departmentId);

	Page<Department> getDepartmentByWebMasterId(Long webMasterId, Pageable page);

}
