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

import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.master.domain.Engineer;
import com.titan.irgs.master.repository.EngineerRepository;
import com.titan.irgs.master.service.IEngineerService;

/**
 * Method Implementation for IEngineerService method
 * @author 
 *
 */
@Service
public class EngineerService implements IEngineerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EngineerRepository engineerRepository;

	/**
	 * getAllEngineer -> Method
	 * 
	 * @param ->none
	 * @return list of saved engineer entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Engineer> getAllEngineer(String engineerName, String emailId, String mobileNo, String vendor, Pageable page) {

		return engineerRepository.findAll(new Specification<Engineer>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Engineer> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();


				
				if (engineerName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("engineerName"),"%" + engineerName + "%")));

				}

				if (emailId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("emailId"), "%" + emailId + "%")));

				}
				
				if (mobileNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("mobileNo"), "%" + mobileNo + "%")));

				}

				if (vendor != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("vendorName"), "%" + vendor + "%")));

				}
				
				

				

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
	 * getEngineerById -> Method
	 * 
	 * @param engineerId
	 * @return saved engineer entity
	 */
	@Override
	public Engineer getEngineerById(Long engineerId) {

		Engineer engineer = engineerRepository.findById(engineerId).orElseThrow(()->new EntityNotFoundException("engineer with engineerId " + engineerId + " not found"));
		
		return engineer;
	}

	/**
	 * saveEngineer ->Method
	 * 
	 * @param engineer
	 *            entity
	 * @return saved engineer entity
	 */
	@Override
	public Engineer saveEngineer(Engineer engineer) {
		engineer.setCreatedOn(new Date());
		engineer.setUpdatedOn(null);
		return engineerRepository.save(engineer);
	}

	/**
	 * updateEngineer ->Method
	 * 
	 * @param engineer
	 *            entity
	 * @return updated engineer entity
	 */
	@Override
	public Engineer updateEngineer(Engineer engineer) {
		engineer.setUpdatedOn(new Date());
		Engineer engineer1 = engineerRepository.findById(engineer.getEngineerId()).orElseThrow(()->new EntityNotFoundException("engineer with engineerId " + engineer.getEngineerId() + " not found"));
	if(engineer.getEngineerName().equalsIgnoreCase(engineer1.getEngineerName())) {
		engineer1.setEngineerName(engineer.getEngineerName());

	}else {
		Engineer engineerName = engineerRepository.findByEngineerName(engineer.getEngineerName());
		if(engineerName!=null) {
			throw new ResourceAlreadyExitException("Already engineerName is Created");
		}
		else 	engineer1.setEngineerName(engineer.getEngineerName());

	}

		engineer1.setEngineerName(engineer.getEngineerName());
		engineer1.setEmailId(engineer.getEmailId());
		engineer1.setMobileNo(engineer.getMobileNo());
		engineer1.setUpdatedOn(new Date());
		engineer1.setVendorId(engineer.getVendorId());
		engineer1.setVendorName(engineer.getVendorName());
		return engineerRepository.save(engineer1);
	}

	/**
	 * deleteEngineerById ->Method
	 * 
	 * @param engineerId
	 * @return none
	 */
	@Override
	public void deleteEngineerById(Long engineerId) {
		Engineer engineer = engineerRepository.findById(engineerId).orElseThrow(()->new EntityNotFoundException("engineer with engineerId " + engineerId + " not found"));
	
		engineerRepository.deleteById(engineerId);

	}

	

	@Override
	public List<Engineer> findByVendorId(long vendorId) {
		
		return engineerRepository.findByVendorId(vendorId);
	}

	@Override
	public Engineer findByEngineerName(String engineerName) {
		
		return engineerRepository.findByEngineerName(engineerName);
	}

	@Override
	public List<Engineer> getAllEnginerByVendorId(Long id) {
		// TODO Auto-generated method stub
		return engineerRepository.findByVendorId(id);
	}
	
	
	
}
