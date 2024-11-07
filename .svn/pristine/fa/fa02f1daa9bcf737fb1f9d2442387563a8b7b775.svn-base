package com.titan.irgs.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.EquipmentWiseDepartments;

@Repository
@Transactional
public interface EquipmentWiseDepartmentsRepo extends JpaRepository<EquipmentWiseDepartments, Long>,JpaSpecificationExecutor<EquipmentWiseDepartments>{

	@Query(value="select * from equipment_wise_departments where equipment_id=?1",nativeQuery = true)
	List<EquipmentWiseDepartments> getByEquipmentId(Long equipmentId);

	@Query(value="SELECT department_ids FROM equipment_wise_departments where equipment_id=?1",nativeQuery = true)
	List<Long> getAllDepartmentIdsInEquipmentWiseUsingEquipmentId(Long equipmentId);

	@Modifying
	@Query(value = "delete FROM equipment_wise_departments where equipment_id=:equipmentId and department_ids=:departmentid",nativeQuery = true)
	void deleteEquipmentWiseDepartmentsByUsingEquipmentIdAndDepartmentId(@Param("equipmentId")Long equipmentId,@Param("departmentid") Long departmentid);

}
