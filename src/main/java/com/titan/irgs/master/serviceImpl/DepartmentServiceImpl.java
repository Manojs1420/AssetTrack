package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.service.DepartmentService;
import com.titan.irgs.master.vo.DepartmentVO;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public Page<Department> getAllDepartment(DepartmentVO departmentVO, Pageable page) {
		return departmentRepo.findAll(new Specification<Department>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();



				if (departmentVO.getDepartmentName() != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("departmentName"),"%" + departmentVO.getDepartmentName() + "%")));

				}

				if (departmentVO.getWebMasterName() != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"), "%" + departmentVO.getWebMasterName() + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}

	@Override
	public Department getDepartmentById(Long departmentId) {
		Department department = departmentRepo.findById(departmentId).orElseThrow(()->new EntityNotFoundException("department with departmentId " + departmentId + " not found"));

		return department;
	}

	@Override
	public Department saveDepartment(Department department) {
		department.setCreatedOn(new Date());
		department.setUpdatedOn(null);
		return departmentRepo.save(department);
	}

	@Override
	public Department updateDepartment(Department department) {
		department.setUpdatedOn(new Date());
		Department department1 = departmentRepo.findById(department.getDepartmentId()).orElseThrow(()->new EntityNotFoundException("department with departmentId " + department.getDepartmentId() + " not found"));
		/*if(department.getDepartmentName().equalsIgnoreCase(department1.getDepartmentName())) {
		department1.setDepartmentName(department.getDepartmentName());

	}else {
		Department departmentName = departmentRepo.findByDepartmentName(department.getDepartmentName());
		if(departmentName!=null) {
			throw new ResourceAlreadyExitException("Already departmentName is Created");
		}
		else 	department1.setDepartmentName(department.getDepartmentName());

	}
		 */


		return departmentRepo.save(department);
	}

	@Override
	public void deleteDepartmentById(Long departmentId) {
		Department department = departmentRepo.findById(departmentId).orElseThrow(()->new EntityNotFoundException("department with departmentId " + departmentId + " not found"));
		
		departmentRepo.deleteById(departmentId);
	}

	@Override
	public Page<Department> getDepartmentByWebMasterId(Long webMasterId, Pageable page) {
		Page<Department> department = departmentRepo.findByWebMasterId(webMasterId,page);

		return department;
	}

}
