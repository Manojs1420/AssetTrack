package com.titan.irgs.master.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.GroupBusinessVertical;
import com.titan.irgs.master.domain.GroupBusinessVerticalMaster;
import com.titan.irgs.master.repository.GroupBusinessVerticalMasterRepo;
import com.titan.irgs.master.repository.GroupBusinessVerticalRepo;
import com.titan.irgs.master.service.IGroupBusinessVerticalService;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Service
@Transactional
public class GroupBusinessVerticalServiceImpl implements IGroupBusinessVerticalService{

	@Autowired
	GroupBusinessVerticalMasterRepo groupBusinessVerticalMasterRepo;
	
	@Autowired
	GroupBusinessVerticalRepo groupBusinessVerticalRepo;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Override
	public GroupBusinessVerticalMaster saveGroupBusinessVertical(
			GroupBusinessVerticalMaster groupBusinessVerticalMaster) {
		groupBusinessVerticalMaster.setCreatedOn(new Date());
		return groupBusinessVerticalMasterRepo.save(groupBusinessVerticalMaster);
	}

	@Override
	public GroupBusinessVerticalMaster updateGroupBusinessVertical(
			GroupBusinessVerticalMaster groupBusinessVerticalMaster) {
		// TODO Auto-generated method stub
		GroupBusinessVerticalMaster groupBusinessVerticalMaster1=groupBusinessVerticalMasterRepo.getOne(groupBusinessVerticalMaster.getId());
				if(groupBusinessVerticalMaster1==null) {
					throw new ResourceNotFoundException("the requested object not found)");
				}
				try {
					groupBusinessVerticalRepo.deleteBusinessVerticalId(groupBusinessVerticalMaster1.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				groupBusinessVerticalMaster1.setGroupBusinessVerticalName(groupBusinessVerticalMaster.getGroupBusinessVerticalName());
				groupBusinessVerticalMaster1.setUpdatedOn(new Date());
				
			//	return groupBusinessVerticalMasterRepo.save(groupBusinessVerticalMaster1);
				return groupBusinessVerticalMaster1;
			
	}
	
	@Override
	public GroupBusinessVerticalMaster getGroupBusinessVerticalById(Long id) {
		// TODO Auto-generated method stub
		return groupBusinessVerticalMasterRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("the requested id not found: " +id));
	}

	@Override
	public void deleteGroupBusinessVerticalById(Long id) {
		GroupBusinessVerticalMaster groupBusinessVertical = groupBusinessVerticalMasterRepo.findById(id).orElseThrow(()->new EntityNotFoundException("GroupBusinessVertical with groupBusinessVerticalId " + id + " not found"));
		
		groupBusinessVerticalMasterRepo.deleteById(id);
	}

	
	@SuppressWarnings("serial")
	@Override
	public Page<GroupBusinessVertical> getAllGroupBusinessVertical(String businessVerticalTypeName,
			String businessVerticalTypeName1,
			Long groupBusinessVerticalId, String groupBusinessVerticalName,String createdOn, Pageable page) {	
		return groupBusinessVerticalRepo.findAll(new Specification<GroupBusinessVertical>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<GroupBusinessVertical> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
						
					if (businessVerticalTypeName != null) {
							predicates.add(criteriaBuilder.and(criteriaBuilder
									.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));
						//	predicates.add(criteriaBuilder.and((root.join("groupBusinessVertical").on(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),  "%" + businessVerticalTypeName + "%")))));

					}
				
					if (businessVerticalTypeName1 != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder
								.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName1 + "%")));

					}
			
				if (groupBusinessVerticalId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("id"),
							"%" + groupBusinessVerticalId + "%")));

				}

				if (groupBusinessVerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("groupBusinessVerticalName"),
							"%" + groupBusinessVerticalName + "%")));

				}

				if(createdOn!=null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(createdOn, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("createdOn").as(LocalDate.class), date)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	
	}

	
	@SuppressWarnings("serial")
	@Override
	public Page<GroupBusinessVerticalMaster> getAllGroupBusinessVertical1(String businessVerticalTypeName,
			String businessVerticalTypeName1,
			Long groupBusinessVerticalId, String groupBusinessVerticalName,String createdOn, Pageable page) {	
		return groupBusinessVerticalMasterRepo.findAll(new Specification<GroupBusinessVerticalMaster>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<GroupBusinessVerticalMaster> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
						
					if (businessVerticalTypeName != null) {
							predicates.add(criteriaBuilder.and(criteriaBuilder
									.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));
						//	predicates.add(criteriaBuilder.and((root.join("groupBusinessVertical").on(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),  "%" + businessVerticalTypeName + "%")))));

					}
				
					if (businessVerticalTypeName1 != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder
								.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName1 + "%")));

					}
			
				if (groupBusinessVerticalId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("id"),
							"%" + groupBusinessVerticalId + "%")));

				}

				if (groupBusinessVerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.get("groupBusinessVerticalName"),
							"%" + groupBusinessVerticalName + "%")));

				}

				if(createdOn!=null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(createdOn, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("createdOn").as(LocalDate.class), date)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	
	}

	

}
