package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.StoreEngineer;
import com.titan.irgs.master.repository.StoreEngineerRepository;
import com.titan.irgs.master.service.IStoreEngineerService;

@Service
@Transactional
public class StoreEngineerServiceImpl implements IStoreEngineerService{

	
	@Autowired
	StoreEngineerRepository storeEngineerRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public StoreEngineer saveInventory(StoreEngineer storeEngineer) {
		
	//	StoreEngineer storeEngineer1=new StoreEngineer();
		if(storeEngineer.getStatus().equalsIgnoreCase("ACTIVE")) {
			storeEngineer.setStatus("ACTIVE");
		}else {
			storeEngineer.setStatus("INACTIVE");
		}
		
		storeEngineer.setCreatedOn(new Date());
		storeEngineer.setUpdatedOn(null);
		storeEngineer.setEmailId(storeEngineer.getEmailId());
		storeEngineer.setMobileNo(storeEngineer.getMobileNo());
		storeEngineer.setEngineerName(storeEngineer.getEngineerName());
		storeEngineer.setEngineerCode(storeEngineer.getEngineerCode());
	//	storeEngineer = storeEngineerRepository.save(storeEngineer);
		
	//	storeEngineer1.setEngineerCode("STENG-"+storeEngineer1.getStoreEngineerId());
		
		return storeEngineerRepository.save(storeEngineer);
	}

	@Override
	public StoreEngineer updateInventory(StoreEngineer storeEngineer) {
		StoreEngineer storeEngineer1 = storeEngineerRepository.findById(storeEngineer.getStoreEngineerId()).orElseThrow(()->new EntityNotFoundException("storeEngineer with storeEngineerId " + storeEngineer.getStoreEngineerId() + " not found"));
		
		storeEngineer1.setUpdatedOn(new Date());
		storeEngineer1.setEmailId(storeEngineer.getEmailId());
		storeEngineer1.setMobileNo(storeEngineer.getMobileNo());
		storeEngineer1.setEngineerCode(storeEngineer.getEngineerCode());
		storeEngineer1.setStatus(storeEngineer.getStatus());
		storeEngineer1.setEngineerName(storeEngineer.getEngineerName());
			
			return storeEngineerRepository.save(storeEngineer1);
	}

	@Override
	public void deleteStoreEngineerById(Long id) {
		StoreEngineer storeEngineer = storeEngineerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("StoreEngineer with StoreEngineerId " + id + " not found"));
		
		storeEngineerRepository.deleteById(id);
	}
	
	@Override
	public StoreEngineer getStoreEngineerById(Long id) {
		// TODO Auto-generated method stub
		return storeEngineerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("the requested id {} not found)" +id));
	}

	@SuppressWarnings("serial")
	@Override
	public Page<StoreEngineer> getAllStoreEngineer(String businessVerticalTypeName,String businessVerticalTypeName1, String storeCode, List<Long> regions,Long storeId, 
			String engineerName,String engineerCode,String emailId,String mobileNo,String status,String region,Pageable page) {
		return storeEngineerRepository.findAll(new Specification<StoreEngineer>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<StoreEngineer> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
						
					if (businessVerticalTypeName != null) {
									predicates.add(criteriaBuilder.and(criteriaBuilder
											.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

					}
				
					if (businessVerticalTypeName1 != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder
								.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName1 + "%")));

		}
			
				if (storeCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("store").get("storeCode"),
							"%" + storeCode + "%")));

				}

				if (storeId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("store").get("storeId"),
							"%" + storeId + "%")));

				}

				if (regions != null) {
					predicates.add((root.join("store").join("region").get("regionId").in(regions)));

				}
				
				if (engineerName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("engineerName"),
							"%" + engineerName + "%")));

				}

				if (engineerCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("engineerCode"),
							"%" + engineerCode + "%")));

				}

				if (emailId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("emailId"),
							"%" + emailId + "%")));

				}

				if (mobileNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("mobileNo"),
							"%" + mobileNo + "%")));

				}

				if (status != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("status"),
							"%" + status + "%")));

				}

				if (region != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("store").join("region").get("regionName"),
							"%" + region + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	
	}
	
	
}
