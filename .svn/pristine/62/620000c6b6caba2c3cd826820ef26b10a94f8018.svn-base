package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Equipment;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, Equipment domain class).
 * This is IEquipmentService interface(i.e, custom interface) which has CRUD method specification for Equipment domain class.
 * It is responsible to provide service(i.e Equipment releated data) to equipment web page and vice-versa
 * 
 * @author 
 *
 */
public interface IEquipmentService {
	
	Page<Equipment> getAllEquipment(String equipmentName, String equipmentCode, String cost, String equipmentType, String webMasterId, String webMasterName,String vendorCode, String department, Pageable page);

	Equipment getEquipmentById(Long equipmentId);

	Equipment saveEquipment(Equipment equipment);

	Equipment updateEquipment(Equipment equipment);

	void deleteEquipmentById(Long equipmentId);
	
	boolean checkIfEquipmentCodeIsExit(String equipmentCode);


	
	Equipment findByEquipmentName(String equipmentName);

	List<Equipment> findByEquipmentIdNotIn(List<Long> ids);

	List<Object[]> getAllExcel();

	List<Object[]> getAllExcel(Long id);

	List<Equipment> getEquipmentByVerticalId(Long id);

	List<Equipment> getEquipmentByVerticalIdAndDepartmentId(Long verticalId, Long departmentId);



}
