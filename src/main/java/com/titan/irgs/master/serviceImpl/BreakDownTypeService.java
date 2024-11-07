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


import com.titan.irgs.master.repository.BreakDownTypeRepository;
import com.titan.irgs.master.service.IBreakDownTypeService;
import com.titan.irgs.serviceRequest.domain.BreakDownType;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * Method Implementation for IBreakDownTypeService method
 * @author birendra
 *
 */
@Service
public class BreakDownTypeService implements IBreakDownTypeService {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BreakDownTypeRepository breakDownTypeRepository;
	
	@Autowired
	private WebMasterRepo webMasterRepo;

    
	/**
	 * getAllBreakDownType -> Method
	 * @param ->none
	 * @return list of saved breakDownType entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<BreakDownType> getAllBreakDownType(String breakdownName,Long webMasterId,String webMasterName,String verticalName, Pageable page) {
		
		return breakDownTypeRepository.findAll(new Specification<BreakDownType>() {
			
			@Override
			public Predicate toPredicate(Root<BreakDownType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (breakdownName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("breakDownTypeName"),"%" + breakdownName + "%")));

				}


				if (webMasterId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + webMasterId + "%")));

				}
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

				}
				if (verticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + verticalName + "%")));

				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		},page);
	}

	/**
	 * getBreakDownTypeById -> Method
	 * @param breakDownTypeId
	 * @return saved breakDownType entity
	 */
	@Override
	public BreakDownType getBreakDownTypeById(Long breakDownTypeId) {
		
		BreakDownType breakDownTypem = breakDownTypeRepository.findById(breakDownTypeId).orElseThrow(()->new EntityNotFoundException("breakDownType with breakDownTypeId " + breakDownTypeId + " not found"));
		
		return breakDownTypem;
	}
    
	/**
	 * saveBreakDownType ->Method
	 * @param breakDownTypem entity
	 * @return saved breakDownType entity
	 */
	@Override
	public BreakDownType saveBreakDownType(BreakDownType breakDownTypem) {
//		breakDownTypem.setCreatedOn(new Date());
//		breakDownTypem.setUpdatedOn(null);
		return breakDownTypeRepository.save(breakDownTypem);
	}
    
	/**
	 * updateBreakDownType ->Method
	 * @param breakDownTypem entity
	 * @return updated breakDownType entity
	 */
	@Override
	public BreakDownType updateBreakDownType(BreakDownType breakDownTypem) {


		BreakDownType breakDownType1 = breakDownTypeRepository.findById(breakDownTypem.getBreakDownTypeId()).orElseThrow(()->new EntityNotFoundException("breakDownType with breakDownTypeId " + breakDownTypem.getBreakDownTypeId() + " not found"));
		
		WebMaster webMaster = webMasterRepo.findByWebMasterId(breakDownTypem.getWebMaster().getWebMasterId());
		breakDownType1.setBreakDownTypeName(breakDownTypem.getBreakDownTypeName());
		//breakDownType1.setBreakDownTypeCode(breakDownType.getBreakDownTypeCode());
		breakDownType1.setWebMaster(webMaster);
		return breakDownTypeRepository.save(breakDownType1);
	}
    
	/**
	 * deleteBreakDownTypeById ->Method
	 * @param breakDownTypeId
	 * @return none
	 */
	@Override
	public void deleteBreakDownTypeById(Long breakDownTypeId) {
		BreakDownType breakDownTypem = breakDownTypeRepository.findById(breakDownTypeId).orElseThrow(()->new EntityNotFoundException("breakDownType with breakDownTypeId " + breakDownTypeId + " not found"));
		if (breakDownTypem == null) {
			logger.error("breakDownType with breakDownTypeId {} not found", breakDownTypeId);
			throw new EntityNotFoundException("breakDownType with breakDownTypeId " + breakDownTypeId + " not found");
		}
		breakDownTypeRepository.deleteById(breakDownTypeId);

	}



	@Override
	public BreakDownType findByBreakDownTypeName(String breakDownTypeName) {
		BreakDownType breakDownTypem = breakDownTypeRepository.findByBreakDownTypeName(breakDownTypeName);

		return breakDownTypem;
	}
//
////	@Override
////	public BreakDownTypem findByBreakDownTypeCode(String breakDownTypeName) {
////		// TODO Auto-generated method stub
////		return breakDownTypeRepository.findByBreakDownTypeCode(breakDownTypeName);
////	}
//
	@Override
	public List<Object[]> getAll(Long id) {
		// TODO Auto-generated method stub
		return breakDownTypeRepository.getAll(id);
	}
//
	@Override
	public List<Object[]> getAll() {
		// TODO Auto-generated method stub
		return breakDownTypeRepository.getAll();
	}
//
	@Override
	public List<BreakDownType> getBreakDownTypeByWebMasterId(Long id) {
		// TODO Auto-generated method stub
		return breakDownTypeRepository.getBreakDownTypeByWebMasterId(id);
	}

}
