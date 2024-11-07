package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.EquipmentType;

public interface IEquipmentTypeService {
	
	Page<EquipmentType> getAllEquipmentType(String equipmentType,String webMasterId,String webMasterName, Pageable pageable);

	EquipmentType getEquipmentTypeById(Long EquipmentTypeId);

	EquipmentType saveEquipmentType(EquipmentType EquipmentType);

	EquipmentType updateEquipmentType(EquipmentType EquipmentType);

	void deleteEquipmentTypeById(Long EquipmentTypeId);
	
	boolean checkIfEquipmentTypeNameIsExit(String EquipmentTypeName);

	EquipmentType findByEquipmentTypeName(String EquipmentTypeName);

	List<Object[]> getAllForExcel();

	List<Object[]> getAllForExcel(Long id);

	List<EquipmentType> getEquipmentByTypeVerticalId(Long id);

}
