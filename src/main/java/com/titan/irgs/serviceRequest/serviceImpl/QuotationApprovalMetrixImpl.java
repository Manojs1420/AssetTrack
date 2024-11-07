package com.titan.irgs.serviceRequest.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.domain.QuotationApprovalMetrix;
import com.titan.irgs.serviceRequest.repository.QuotationApprovalMetrixRepo;
import com.titan.irgs.serviceRequest.service.IQuotationApprovalMetrixService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@Service
public class QuotationApprovalMetrixImpl implements IQuotationApprovalMetrixService{

	@Autowired
	QuotationApprovalMetrixRepo quotationApprovalMetrixRepo;
	@Autowired
	private WebMasterRepo webMasterRepo;
	@Autowired
	private WebRoleRepo webRoleRepo;
	
	
	@Override
	public QuotationApprovalMetrix save(QuotationApprovalMetrix quotationApprovalMetrix) {
		// TODO Auto-generated method stub
		return quotationApprovalMetrixRepo.save(quotationApprovalMetrix);
	}







	@Override
	public QuotationApprovalMetrix getById(Long id) {
		// TODO Auto-generated method stub
		return quotationApprovalMetrixRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("The requested not present"));
	}



	@Override
	public QuotationApprovalMetrix update(QuotationApprovalMetrix quotationApprovalMetrix) {
		QuotationApprovalMetrix approvalMetrix=quotationApprovalMetrixRepo.findById(quotationApprovalMetrix.getQuotationApprovalMetrixId())
		.orElseThrow(()->new ResourceNotFoundException("The requested not present"));
		WebMaster webMaster = webMasterRepo.findByWebMasterId(quotationApprovalMetrix.getWebMaster().getWebMasterId());
		WebRole webRole = webRoleRepo.findByWebRoleId(quotationApprovalMetrix.getWebRole().getWebRoleId());

		approvalMetrix.setQuotationApprovalFrom(quotationApprovalMetrix.getQuotationApprovalFrom());
		approvalMetrix.setQuotationApprovalTo(quotationApprovalMetrix.getQuotationApprovalTo());
		approvalMetrix.setUpdatedDate(new Date());
		approvalMetrix.setWebMaster(webMaster);
		approvalMetrix.setWebRole(webRole);
		
		
		return quotationApprovalMetrixRepo.save(quotationApprovalMetrix);
	}



	@Override
	public void deleteById(Long id) {
		quotationApprovalMetrixRepo.deleteById(id);
		
	}



	@Override
	public QuotationApprovalMetrix findByWebRoleWebRoleId(Long webRoleId) {
		// TODO Auto-generated method stub
		return quotationApprovalMetrixRepo.findByWebRoleWebRoleId(webRoleId);
	}






	@SuppressWarnings("serial")
	@Override
	public Page<QuotationApprovalMetrix> getAll(Double quotationApprovalFrom, Double quotationApprovalTo,
			String roleName, String businessVerticalType, Pageable page) {
		return quotationApprovalMetrixRepo.findAll(new Specification<QuotationApprovalMetrix>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<QuotationApprovalMetrix> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
			//	query.orderBy(criteriaBuilder.desc(root.get("quotationApprovalMetrixId")));

				/*
				 * if (quotationApprovalFrom != null) { predicates.add(
				 * criteriaBuilder.and(criteriaBuilder.like(root.get("quotationApprovalFrom"),
				 * "%" + quotationApprovalFrom + "%")));
				 * 
				 * }
				 * 
				 * if (quotationApprovalTo != null) { predicates.add(
				 * criteriaBuilder.and(criteriaBuilder.like(root.get("quotationApprovalTo"), "%"
				 * + quotationApprovalTo + "%")));
				 * 
				 * }
				 */

				if (roleName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("webRole").join("role").get("roleName"), "%" + roleName + "%")));

				}
				if (businessVerticalType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalType + "%")));

				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

			}
			}, page);
		}



	

	
	

}
