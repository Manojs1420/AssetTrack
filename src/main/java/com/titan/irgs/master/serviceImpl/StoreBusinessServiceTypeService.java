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

import com.titan.irgs.master.domain.StoreBusinessServiceType;
import com.titan.irgs.master.repository.StoreBusinessServiceTypeRepository;
import com.titan.irgs.master.service.IStoreBusinessServiceTypeService;

@Service
public class StoreBusinessServiceTypeService implements IStoreBusinessServiceTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StoreBusinessServiceTypeRepository StoreBusinessServiceTypeRepository;

	/**
	 * getAllStoreBusinessServiceType -> Method
	 * @param ->none
	 * @return list of saved StoreBusinessServiceType entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<StoreBusinessServiceType> getAllStoreBusinessServiceType(String storeBusinessServiceTypeName, Pageable page) {

		return StoreBusinessServiceTypeRepository.findAll(new Specification<StoreBusinessServiceType>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<StoreBusinessServiceType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (storeBusinessServiceTypeName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("storeBusinessServiceTypeName"),"%" + storeBusinessServiceTypeName + "%")));

				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}

	/**
	 * getStoreBusinessServiceTypeById -> Method
	 * @param StoreBusinessServiceTypeId
	 * @return saved StoreBusinessServiceType entity
	 */
	@Override
	public StoreBusinessServiceType getStoreBusinessServiceTypeById(Long StoreBusinessServiceTypeId) {
		StoreBusinessServiceType StoreBusinessServiceType = StoreBusinessServiceTypeRepository.findById(StoreBusinessServiceTypeId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreBusinessServiceTypeId + " not found"));
		
		return StoreBusinessServiceType;
	}

	/**
	 * saveStoreBusinessServiceType ->Method
	 * @param StoreBusinessServiceType entity
	 * @return saved StoreBusinessServiceType entity
	 */
	@Override
	public StoreBusinessServiceType saveStoreBusinessServiceType(StoreBusinessServiceType StoreBusinessServiceType) {
		StoreBusinessServiceType.setCreatedOn(new Date());
		StoreBusinessServiceType.setUpdatedOn(null);
		return StoreBusinessServiceTypeRepository.save(StoreBusinessServiceType);
	}

	/**
	 * updateStoreBusinessServiceType ->Method
	 * @param StoreBusinessServiceType entity
	 * @return updated StoreBusinessServiceType entity
	 */
	@Override
	public StoreBusinessServiceType updateStoreBusinessServiceType(StoreBusinessServiceType StoreBusinessServiceType) {
		StoreBusinessServiceType.setUpdatedOn(new Date());
		StoreBusinessServiceType StoreBusinessServiceType1 = StoreBusinessServiceTypeRepository.findById(StoreBusinessServiceType.getStoreBusinessServiceTypeId()).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreBusinessServiceType.getStoreBusinessServiceTypeId() + " not found"));
		if (StoreBusinessServiceType1 == null) {
			logger.error("StoreBusinessServiceType with StoreBusinessServiceTypeId {} not found", StoreBusinessServiceType.getStoreBusinessServiceTypeId());
			throw new EntityNotFoundException("StoreBusinessServiceType with StoreBusinessServiceTypeId " + StoreBusinessServiceType.getStoreBusinessServiceTypeId() + " not found");
		}
		return StoreBusinessServiceTypeRepository.save(StoreBusinessServiceType);
	}

	/**
	 * deleteStoreBusinessServiceTypeById ->Method
	 * @param StoreBusinessServiceTypeId
	 * @return none
	 */
	@Override
	public void deleteStoreBusinessServiceTypeById(Long StoreBusinessServiceTypeId) {
		StoreBusinessServiceType StoreBusinessServiceType = StoreBusinessServiceTypeRepository.findById(StoreBusinessServiceTypeId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreBusinessServiceTypeId + " not found"));
		
		StoreBusinessServiceTypeRepository.deleteById(StoreBusinessServiceTypeId);

	}

	@Override
	public StoreBusinessServiceType findByStoreBusinessServiceTypeName(String StoreBusinessServiceTypeName) {
		StoreBusinessServiceType StoreBusinessServiceType = StoreBusinessServiceTypeRepository.findByStoreBusinessServiceTypeName(StoreBusinessServiceTypeName);
		if (StoreBusinessServiceType == null) {
			logger.error("StoreBusinessServiceType with StoreBusinessServiceTypeName {} not found", StoreBusinessServiceTypeName);
			throw new EntityNotFoundException("StoreBusinessServiceType with StoreBusinessServiceTypeName " + StoreBusinessServiceTypeName + " not found");
		}
		return StoreBusinessServiceType;
	}

	@Override
	public boolean checkIfStoreBusinessServiceTypeNameIsExit(String StoreBusinessServiceTypeName) {
		StoreBusinessServiceType StoreBusinessServiceType = StoreBusinessServiceTypeRepository.findByStoreBusinessServiceTypeName(StoreBusinessServiceTypeName);
		if (StoreBusinessServiceType != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	

}
