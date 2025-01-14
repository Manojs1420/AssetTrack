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

import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.repository.EquipmentTypeRepository;
import com.titan.irgs.master.service.IEquipmentTypeService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Service
public class EquipmentTypeService implements IEquipmentTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EquipmentTypeRepository equipmentTypeRepository;
	@Autowired
	private WebMasterRepo webMasterRepo;
    
	/**
	 * getAllEquipmentType -> Method
	 * @param ->none
	 * @return list of saved EquipmentType entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<EquipmentType> getAllEquipmentType(String equipmentType,String webMasterId,String webMasterName, Pageable page) {
		return equipmentTypeRepository.findAll(new Specification<EquipmentType>() {
			
			@Override
			public Predicate toPredicate(Root<EquipmentType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (equipmentType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("equipmentTypeName"),"%" + equipmentType + "%")));

				}
				if (webMasterId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + webMasterId + "%")));

				}
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		},page);
	}
    
	/**
	 * getEquipmentTypeById -> Method
	 * @param EquipmentTypeId
	 * @return saved EquipmentType entity
	 */
	@Override
	public EquipmentType getEquipmentTypeById(Long EquipmentTypeId) {
		EquipmentType EquipmentType = equipmentTypeRepository.findById(EquipmentTypeId).orElseThrow(()->new EntityNotFoundException("EquipmentType with EquipmentTypeId " + EquipmentTypeId + " not found"));
		
		return EquipmentType;
	}
    
	/**
	 * saveEquipmentType ->Method
	 * @param EquipmentType entity
	 * @return saved EquipmentType entity
	 */
	@Override
	public EquipmentType saveEquipmentType(EquipmentType EquipmentType) {
		EquipmentType.setCreatedOn(new Date());
		EquipmentType.setUpdatedOn(null);
		return equipmentTypeRepository.save(EquipmentType);
	}
    
	/**
	 * updateEquipmentType ->Method
	 * @param EquipmentType entity
	 * @return updated EquipmentType entity
	 */
	@Override
	public EquipmentType updateEquipmentType(EquipmentType EquipmentType) {
		EquipmentType EquipmentType1 = equipmentTypeRepository.findById(EquipmentType.getEquipmentTypeId()).orElseThrow(()->new EntityNotFoundException("EquipmentType with EquipmentTypeId " + EquipmentType.getEquipmentTypeId() + " not found"));
		WebMaster webMaster = webMasterRepo.findByWebMasterId(EquipmentType.getWebMaster().getWebMasterId());
		EquipmentType1.setWebMaster(webMaster);
		EquipmentType1.setEquipmentTypeName(EquipmentType.getEquipmentTypeName());
		return equipmentTypeRepository.save(EquipmentType1);
	}
    
	/**
	 * deleteEquipmentTypeById ->Method
	 * @param EquipmentTypeId
	 * @return none
	 */
	@Override
	public void deleteEquipmentTypeById(Long EquipmentTypeId) {
		EquipmentType EquipmentType = equipmentTypeRepository.findById(EquipmentTypeId).orElseThrow(()->new EntityNotFoundException("EquipmentType with EquipmentTypeId " + EquipmentTypeId + " not found"));
	
		equipmentTypeRepository.deleteById(EquipmentTypeId);
	}
    
	/**
	 * checkIfEquipmentTypeCodeIsExit ->Method
	 * @param EquipmentTypeCode
	 * @return true -> Exit , false -> Not Exit
	 */
	@Override
	public boolean checkIfEquipmentTypeNameIsExit(String EquipmentTypeName) 
	{
		EquipmentType EquipmentType = equipmentTypeRepository.findByEquipmentTypeName(EquipmentTypeName);
		if (EquipmentType != null) 
		{
			return true;
		} else 
		{
			return false;
		}
	}


	@Override
	public EquipmentType findByEquipmentTypeName(String EquipmentTypeName)
	{

		EquipmentType EquipmentType = equipmentTypeRepository.findByEquipmentTypeName(EquipmentTypeName);
		/*if (EquipmentType == null)
		{
			logger.error("EquipmentType with EquipmentTypeName {} not found", EquipmentTypeName);
			throw new EntityNotFoundException("EquipmentType with EquipmentTypeName " + EquipmentTypeName + " not found");
		}*/
	return EquipmentType;
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return equipmentTypeRepository.getAllForExcel();
	}

	@Override
	public List<Object[]> getAllForExcel(Long id) {
		// TODO Auto-generated method stub
		return equipmentTypeRepository.getAllForExcel(id);
	}

	@Override
	public List<EquipmentType> getEquipmentByTypeVerticalId(Long id) {
		// TODO Auto-generated method stub
		return equipmentTypeRepository.getEquipmentByTypeVerticalId(id);
	}
	
	

}
