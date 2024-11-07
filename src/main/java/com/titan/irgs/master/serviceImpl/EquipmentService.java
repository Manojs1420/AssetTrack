package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Equipment;
import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.repository.EquipmentRepository;
import com.titan.irgs.master.service.IEquipmentService;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * Method Implementation for IEquimentService method
 * @author 
 *
 */
@Service
public class EquipmentService implements IEquipmentService
{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EquipmentRepository equipmentRepository;

	@Autowired
	private WebMasterRepo webMasterRepo;
	
	@Autowired
	EquipmentTypeService equipmentTypeService;
	/**
	 * getAllEquipment -> Method
	 * @param ->none
	 * @return list of saved equipment entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Equipment> getAllEquipment(String equipmentName, String equipmentCode, String cost, String equipmentType,String webMasterId,String webMasterName,String vendorCode,
			String department,Pageable page) {
		return equipmentRepository.findAll(new Specification<Equipment>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Equipment> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();


				
				if (equipmentName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("equipmentName"),"%" + equipmentName + "%")));

				}

				if (equipmentCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("equipmentCode"), "%" + equipmentCode + "%")));

				}
				
				if (cost != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("equipmentCost").as(String.class), "%" + cost + "%")));

				}

				if (equipmentType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("equipmentType").get("equipmentTypeName"), "%" + equipmentType + "%")));

				}
				
				if (webMasterId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + webMasterId + "%")));

				}
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

				}
				
				if (department != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("equipmentWiseDepartments").join("department").get("departmentName"),"%" + department + "%")));

				}
				
				/*
				 * if (vendorCode != null) {
				 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster"
				 * ).get("webMasterName"),"%" + vendorCode + "%")));
				 * 
				 * }
				 */
				

				/*
				 * if (distributorName != null) {
				 * predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.join("item").
				 * join("").get(""), frameShapeName)));
				 * 
				 * }
				 */

				

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}
    
	/**
	 * getEquipmentById -> Method
	 * @param equipmentId
	 * @return saved equipment entity
	 */
	@Override
	public Equipment getEquipmentById(Long equipmentId) {
		Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(()->new EntityNotFoundException("Equipment with equipmentId " + equipmentId + " not found"));
		
		return equipment;
	}
    
	/**
	 * saveEquipment ->Method
	 * @param equipment entity
	 * @return saved equipment entity
	 */
	@Override
	public Equipment saveEquipment(Equipment equipment) {
		equipment.setCreatedOn(new Date());
		equipment.setUpdatedOn(null);
		return equipmentRepository.save(equipment);
	}
    
	/**
	 * updateEquipment ->Method
	 * @param equipment entity
	 * @return updated equipment entity
	 */
	@Override
	public Equipment updateEquipment(Equipment equipment) {
		Equipment equipment1 = equipmentRepository.findById(equipment.getEquipmentId()).orElseThrow(()->new EntityNotFoundException("Equipment with equipmentId " + equipment.getEquipmentId() + " not found"));
		equipment1.setEquipmentCode(equipment.getEquipmentCode());
		equipment1.setEquipmentCost(equipment.getEquipmentCost());
		equipment1.setEquipmentName(equipment.getEquipmentName());
		EquipmentType equipmentType=equipmentTypeService.getEquipmentTypeById(equipment.getEquipmentType().getEquipmentTypeId());
		equipment1.setEquipmentType(equipmentType);
		equipment1.setWebMaster(webMasterRepo.findByWebMasterId(equipment.getWebMaster().getWebMasterId()));
    
		return equipmentRepository.save(equipment1);
	}
    
	/**
	 * deleteEquipmentById ->Method
	 * @param equipmentId
	 * @return none
	 */
	@Override
	public void deleteEquipmentById(Long equipmentId) {
		Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(()->new EntityNotFoundException("Equipment with equipmentId " + equipmentId + " not found"));
	
		equipmentRepository.deleteById(equipmentId);
	}
    
	/**
	 * checkIfEquipmentCodeIsExit ->Method
	 * @param equipmentCode
	 * @return true -> Exit , false -> Not Exit
	 */
	@Override
	public boolean checkIfEquipmentCodeIsExit(String equipmentCode) 
	{
		Equipment equipment = equipmentRepository.findByEquipmentCode(equipmentCode);
		if (equipment != null) 
		{
			return true;
		} else 
		{
			return false;
		}
	}


	@Override
	public Equipment findByEquipmentName(String equipmentName)
	{

		Equipment equipment = equipmentRepository.findByEquipmentName(equipmentName);
		/*if (equipment == null)
		{
			logger.error("equipment with equipmentName {} not found", equipmentName);
			throw new EntityNotFoundException("equipment with equipmentName " + equipmentName + " not found");
		}*/
	return equipment;
	}

	@Override
	public List<Equipment> findByEquipmentIdNotIn(List<Long> ids) {
		
		return equipmentRepository.findByEquipmentIdNotIn(ids);
	}

	@Override
	public List<Object[]> getAllExcel() {
		// TODO Auto-generated method stub
		return equipmentRepository.getAllExcel();
	}

	@Override
	public List<Object[]> getAllExcel(Long id) {
		// TODO Auto-generated method stub
		return equipmentRepository.getAllExcel(id);
	}

	@Override
	public List<Equipment> getEquipmentByVerticalId(Long id) {
		// TODO Auto-generated method stub
		return equipmentRepository.getEquipmentByVerticalId(id);
	}

	@Override
	public List<Equipment> getEquipmentByVerticalIdAndDepartmentId(Long verticalId, Long departmentId) {
		// TODO Auto-generated method stub
		return equipmentRepository.getEquipmentByVerticalIdAndDepartmentId(verticalId,departmentId);
	}


}