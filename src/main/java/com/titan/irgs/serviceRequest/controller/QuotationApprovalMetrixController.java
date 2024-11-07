package com.titan.irgs.serviceRequest.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.domain.QuotationApprovalMetrix;
import com.titan.irgs.serviceRequest.mapper.QuotationApprovalMetrixMapper;
import com.titan.irgs.serviceRequest.model.QuotationApprovalMetrixVo;
import com.titan.irgs.serviceRequest.service.IQuotationApprovalMetrixService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;



@RestController
@RequestMapping(WebConstantUrl.QUOTATION_APPROVAL_MATRIX)
public class QuotationApprovalMetrixController {
	
  
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	QuotationApprovalMetrixMapper quotationApprovalMetrixMapper;


	@Autowired
	IQuotationApprovalMetrixService iQuotationApprovalMetrixService;
	
	@Autowired
	WebRoleRepo webRoleRepo;
	@Autowired
	UserRepo userRepo;

	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";

	@PostMapping(WebConstantUrl.save)
	public ResponseEntity<?> save(@RequestBody QuotationApprovalMetrixVo quotationApprovalMetrixVo, HttpServletRequest request,
			Principal principal) {
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		WebRole webRole=webRoleRepo.findById(quotationApprovalMetrixVo.getWebRoleId())
		        .orElseThrow(()->new ResourceNotFoundException("the role not attached with bussiness vertical"));
		
		QuotationApprovalMetrix checkQuotationApprovalMetrixForWebRole=iQuotationApprovalMetrixService
				                                            .findByWebRoleWebRoleId(webRole.getWebRoleId());
		
		
		if(checkQuotationApprovalMetrixForWebRole!=null)
			throw new ResourceAlreadyExitException("QuotationApprovalMetrix already has been assigned to role user..");
		
		
		
		
		QuotationApprovalMetrix quotationApprovalMetrix = quotationApprovalMetrixMapper.convertModeltoDomain(quotationApprovalMetrixVo);
		quotationApprovalMetrix.setWebRole(webRole);
		quotationApprovalMetrix.setCreatedDate(new Date());
		quotationApprovalMetrix=iQuotationApprovalMetrixService.save(quotationApprovalMetrix);
		
		
		return new ResponseEntity<>(quotationApprovalMetrixMapper.convertDomaintoModel(quotationApprovalMetrix),HttpStatus.OK);
	}
	
	
	

	@GetMapping(WebConstantUrl.getAll)
	public ResponseEntity<?> getAll(@RequestParam(required=false) Double quotationApprovalFrom,
			@RequestParam(required = false) Double quotationApprovalTo,
			@RequestParam(required = false) String roleName,
			@RequestParam(required=false) String businessVerticalType,
						 Pageable pageable,HttpServletRequest request,Principal principal) {
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		Pageable page=PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize()); 
		Map<String,Object> map=new HashMap<>();
		User user = userRepo.findByUsername(principal.getName());


		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
			businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
			


		}
		Page<QuotationApprovalMetrix> quotationApprovalMetrixs=iQuotationApprovalMetrixService.getAll(quotationApprovalFrom,quotationApprovalTo,roleName,businessVerticalType,page);
		List<QuotationApprovalMetrixVo> QuotationApprovalMetrixVos = new ArrayList<QuotationApprovalMetrixVo>(0);
		

		if(quotationApprovalMetrixs.getContent().size() == 0) {
			map.put("QuotationApprovalMetrixVos", QuotationApprovalMetrixVos);
			map.put("total_pages", quotationApprovalMetrixs.getTotalPages());
			map.put("status_code", HttpStatus.OK);
			map.put("total_records", quotationApprovalMetrixs.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			quotationApprovalMetrixs.forEach(quotationApprovalMetrix -> {
				QuotationApprovalMetrixVos.add(quotationApprovalMetrixMapper.convertDomaintoModel(quotationApprovalMetrix));
			});
		}
		map.put("QuotationApprovalMetrixVos", QuotationApprovalMetrixVos);
		map.put("total_pages", quotationApprovalMetrixs.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", quotationApprovalMetrixs.getTotalElements());
		
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	@GetMapping(WebConstantUrl.getById)
	public ResponseEntity<?> getById(@PathVariable("id") Long id, HttpServletRequest request) {
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		QuotationApprovalMetrix quotationApprovalMetrix=iQuotationApprovalMetrixService.getById(id);
		
		return new ResponseEntity<>(quotationApprovalMetrixMapper.convertDomaintoModel(quotationApprovalMetrix),HttpStatus.OK);
	}

	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody QuotationApprovalMetrixVo quotationApprovalMetrixVo, HttpServletRequest request,
			Principal principal) throws MessagingException, IOException {
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		QuotationApprovalMetrix quotationApprovalMetrix = quotationApprovalMetrixMapper.convertModeltoDomain(quotationApprovalMetrixVo);
		WebRole webRole=webRoleRepo.findById(quotationApprovalMetrixVo.getWebRoleId())
		        .orElseThrow(()->new ResourceNotFoundException("the role not attached with bussiness vertical"));
		
		quotationApprovalMetrix.setWebRole(webRole);
		quotationApprovalMetrix.setUpdatedDate(new Date());
		quotationApprovalMetrix=iQuotationApprovalMetrixService.update(quotationApprovalMetrix);
		
		
		return new ResponseEntity<>(quotationApprovalMetrixMapper.convertDomaintoModel(quotationApprovalMetrix),HttpStatus.OK);
	}
	
	@GetMapping(WebConstantUrl.DELETE_BY_ID)
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id, HttpServletRequest request) {
		
		iQuotationApprovalMetrixService.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
 

}
