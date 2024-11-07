package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.EquipmentType;

@Repository
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long>,JpaSpecificationExecutor<EquipmentType>  {

	EquipmentType findByEquipmentTypeName(String equipmentTypeName);

	@Query(value = "SELECT w.web_master_name,e.equipment_type_name FROM equipment_type e\r\n" + 
			"inner join web_master w on w.web_master_id=e.web_master_id where e.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAllForExcel(@Param("id")Long id);

	@Query(value = "SELECT w.web_master_name,e.equipment_type_name FROM equipment_type e\r\n" + 
			"inner join web_master w on w.web_master_id=e.web_master_id ",nativeQuery = true)
	List<Object[]> getAllForExcel();

	@Query(value="SELECT e.* FROM equipment_type e where e.web_master_id=:id",nativeQuery=true)
	List<EquipmentType> getEquipmentByTypeVerticalId(@Param("id")Long id);
	
}
