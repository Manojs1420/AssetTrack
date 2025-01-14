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

import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.master.repository.StoreServiceTypeRepository;
import com.titan.irgs.master.service.IStoreServiceTypeService;

@Service
public class StoreServiceTypeService implements IStoreServiceTypeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StoreServiceTypeRepository StoreServiceTypeRepository;

	/**
	 * getAllStoreServiceType -> Method
	 * @param ->none
	 * @return list of saved StoreServiceType entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<StoreServiceType> getAllStoreServiceType(String storeServiceTypeName, String slaNo, Pageable page) {

		return StoreServiceTypeRepository.findAll(new Specification<StoreServiceType>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<StoreServiceType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (storeServiceTypeName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("storeServiceTypeName"),"%" + storeServiceTypeName + "%")));

				}
				
				if (slaNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("sla"),"%" + slaNo + "%")));

				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}

	/**
	 * getStoreServiceTypeById -> Method
	 * @param StoreServiceTypeId
	 * @return saved StoreServiceType entity
	 */
	@Override
	public StoreServiceType getStoreServiceTypeById(Long storeServiceTypeId) {
		StoreServiceType StoreServiceType = StoreServiceTypeRepository.findById(storeServiceTypeId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + storeServiceTypeId + " not found"));
		
		return StoreServiceType;
	}

	/**
	 * saveStoreServiceType ->Method
	 * @param StoreServiceType entity
	 * @return saved StoreServiceType entity
	 */
	@Override
	public StoreServiceType saveStoreServiceType(StoreServiceType StoreServiceType) {
		StoreServiceType.setCreatedOn(new Date());
		StoreServiceType.setUpdatedOn(null);
		return StoreServiceTypeRepository.save(StoreServiceType);
	}

	/**
	 * updateStoreServiceType ->Method
	 * @param StoreServiceType entity
	 * @return updated StoreServiceType entity
	 */
	@Override
	public StoreServiceType updateStoreServiceType(StoreServiceType StoreServiceType) {
		StoreServiceType.setUpdatedOn(new Date());
		StoreServiceType StoreServiceType1 = StoreServiceTypeRepository.findById(StoreServiceType.getStoreServiceTypeId()).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreServiceType.getStoreServiceTypeId() + " not found"));
		if (StoreServiceType1 == null) {
			logger.error("StoreServiceType with StoreServiceTypeId {} not found", StoreServiceType.getStoreServiceTypeId());
			throw new EntityNotFoundException("StoreServiceType with StoreServiceTypeId " + StoreServiceType.getStoreServiceTypeId() + " not found");
		}
		return StoreServiceTypeRepository.save(StoreServiceType);
	}

	/**
	 * deleteStoreServiceTypeById ->Method
	 * @param StoreServiceTypeId
	 * @return none
	 */
	@Override
	public void deleteStoreServiceTypeById(Long storeServiceTypeId) {
		StoreServiceType StoreServiceType = StoreServiceTypeRepository.findById(storeServiceTypeId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + storeServiceTypeId + " not found"));
		
		StoreServiceTypeRepository.deleteById(storeServiceTypeId);

	}

	@Override
	public StoreServiceType findByStoreServiceTypeName(String StoreServiceTypeName) {
		StoreServiceType StoreServiceType = StoreServiceTypeRepository.findByStoreServiceTypeName(StoreServiceTypeName);
		if (StoreServiceType == null) {
			logger.error("StoreServiceType with StoreServiceTypeName {} not found", StoreServiceTypeName);
			//throw new EntityNotFoundException("StoreServiceType with StoreServiceTypeName " + StoreServiceTypeName + " not found");
		}
		return StoreServiceType;
	}

	@Override
	public boolean checkIfStoreServiceTypeNameIsExit(String StoreServiceTypeName) {
		StoreServiceType StoreServiceType = StoreServiceTypeRepository.findByStoreServiceTypeName(StoreServiceTypeName);
		if (StoreServiceType != null) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
