package com.titan.irgs.serviceRequest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.serviceRequest.domain.QuotationApprovalMetrix;

public interface IQuotationApprovalMetrixService {

	QuotationApprovalMetrix save(QuotationApprovalMetrix quotationApprovalMetrix);


	QuotationApprovalMetrix getById(Long id);

	QuotationApprovalMetrix update(QuotationApprovalMetrix quotationApprovalMetrix);

	void deleteById(Long id);

	QuotationApprovalMetrix findByWebRoleWebRoleId(Long webRoleId);

	Page<QuotationApprovalMetrix> getAll(Double quotationApprovalFrom, Double quotationApprovalTo, String roleName,
			String businessVerticalType, Pageable page);

}
