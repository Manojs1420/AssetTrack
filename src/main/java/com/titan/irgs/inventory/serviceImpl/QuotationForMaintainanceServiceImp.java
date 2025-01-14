package com.titan.irgs.inventory.serviceImpl;

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
import com.titan.irgs.inventory.domain.QuotationForMaintainance;
import com.titan.irgs.inventory.repository.QuotationForMaintainanaceRepo;
import com.titan.irgs.inventory.service.QuotationForMaintainanceService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

	@Service
	public class QuotationForMaintainanceServiceImp implements QuotationForMaintainanceService{

		@Autowired
		QuotationForMaintainanaceRepo quotationForMaintainanceRepo;
		@Autowired
		private WebMasterRepo webMasterRepo;
		@Autowired
		private WebRoleRepo webRoleRepo;
		
		
		@Override
		public QuotationForMaintainance save(QuotationForMaintainance quotationForMaintainance) {
			// TODO Auto-generated method stub
			return quotationForMaintainanceRepo.save(quotationForMaintainance);
		}







		@Override
		public QuotationForMaintainance getById(Long id) {
			// TODO Auto-generated method stub
			return quotationForMaintainanceRepo.findById(id)
					.orElseThrow(()->new ResourceNotFoundException("The requested not present"));
		}



		@Override
		public QuotationForMaintainance update(QuotationForMaintainance quotationForMaintainance) {
			QuotationForMaintainance approvalMetrix=quotationForMaintainanceRepo.findById(quotationForMaintainance.getQuotationApprovalMetrixId())
			.orElseThrow(()->new ResourceNotFoundException("The requested not present"));
			WebMaster webMaster = webMasterRepo.findByWebMasterId(quotationForMaintainance.getWebMaster().getWebMasterId());
			WebRole webRole = webRoleRepo.findByWebRoleId(quotationForMaintainance.getWebRole().getWebRoleId());

			approvalMetrix.setQuotationApprovalFrom(quotationForMaintainance.getQuotationApprovalFrom());
			approvalMetrix.setQuotationApprovalTo(quotationForMaintainance.getQuotationApprovalTo());
			approvalMetrix.setUpdatedDate(new Date());
			approvalMetrix.setWebMaster(webMaster);
			approvalMetrix.setWebRole(webRole);
			
			
			return quotationForMaintainanceRepo.save(quotationForMaintainance);
		}



		@Override
		public void deleteById(Long id) {
			quotationForMaintainanceRepo.deleteById(id);
			
		}



		@Override
		public QuotationForMaintainance findByWebRoleWebRoleId(Long webRoleId) {
			// TODO Auto-generated method stub
			return quotationForMaintainanceRepo.findByWebRoleWebRoleId(webRoleId);
		}






		@SuppressWarnings("serial")
		@Override
		public Page<QuotationForMaintainance> getAll(Double quotationApprovalFrom, Double quotationApprovalTo,
				String roleName, String businessVerticalType, Pageable page) {
			return quotationForMaintainanceRepo.findAll(new Specification<QuotationForMaintainance>() {
				@SuppressWarnings("rawtypes")
				@Override
				public Predicate toPredicate(Root<QuotationForMaintainance> root, CriteriaQuery<?> query,
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