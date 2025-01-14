package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Equipment;
/**
 * This is EquipmentRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on 'equipment' table
 * @author 
 *
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>,JpaSpecificationExecutor<Equipment> {
	
	Equipment findByEquipmentCode(String equipmentCode);
	
	Equipment findByEquipmentName(String equipmentName);
	
	List<Equipment> findByEquipmentIdNotIn(List<Long> ids);

	
	@Query(value = "select w.web_master_name,d.department_name,e.equipment_name,e.equipment_code,e.equipment_cost,\r\n"
			+ "et.equipment_type_name from equipment e \r\n"
			+ "						inner join equipment_type et on e.equipment_type_id=et.equipment_type_id \r\n"
			+ "						inner join web_master w on e.web_master_id =w.web_master_id\r\n"
			+ "						inner join equipment_wise_departments ed on ed.equipment_id=e.equipment_id\r\n"
			+ "						inner join department d on d.department_id=ed.department_ids\r\n",nativeQuery = true)
	List<Object[]> getAllExcel();
	
	@Query(value="select w.web_master_name,d.department_name,e.equipment_name,e.equipment_code,e.equipment_cost,\r\n"
			+ "et.equipment_type_name from equipment e \r\n"
			+ "						inner join equipment_type et on e.equipment_type_id=et.equipment_type_id \r\n"
			+ "						inner join web_master w on e.web_master_id =w.web_master_id\r\n"
			+ "						inner join equipment_wise_departments ed on ed.equipment_id=e.equipment_id\r\n"
			+ "						inner join department d on d.department_id=ed.department_ids\r\n"
			+ "						 where e.web_master_id=:id",nativeQuery=true)
	List<Object[]> getAllExcel(@Param("id")Long id);
	
	@Query(value="SELECT a.* FROM equipment a where web_master_id=:id",nativeQuery=true)
	List<Equipment> getEquipmentByVerticalId(@Param("id")Long id);

	
	@Query(value="select e.*,d.* from equipment e \r\n"
			+ "inner join equipment_wise_departments ed on ed.equipment_id=e.equipment_id\r\n"
			+ "inner join department d on d.department_id=ed.department_ids \r\n"
			+ "inner join web_master wm on wm.web_master_id=d.web_master_id\r\n"
			+ "where wm.web_master_id=:verticalId and  d.department_id=:departmentId",nativeQuery = true)
	List<Equipment> getEquipmentByVerticalIdAndDepartmentId(@Param("verticalId") Long verticalId,@Param("departmentId") Long departmentId);
}
