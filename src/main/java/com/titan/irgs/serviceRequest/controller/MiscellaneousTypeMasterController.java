package com.titan.irgs.serviceRequest.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.serviceRequest.domain.MiscellaneousTypeDomain;
import com.titan.irgs.serviceRequest.mapper.MiscellaneousTypeMapper;
import com.titan.irgs.serviceRequest.model.MiscellaneousTypeModel;
import com.titan.irgs.serviceRequest.service.IMiscellaneousTypeService;


@RestController
@RequestMapping(WebConstantUrl.MISCELLANEOUS_TYPE)
public class MiscellaneousTypeMasterController {
	

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MiscellaneousTypeMapper miscellaneousTypeMapper;

	@Autowired
	IMiscellaneousTypeService iMiscellaneousTypeService;
	
    @PostMapping(WebConstantUrl.save)
	public ResponseEntity<?> save(@RequestBody MiscellaneousTypeModel miscellaneousTypeModel, HttpServletRequest request,
			Principal principal) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		MiscellaneousTypeDomain miscellaneousTypeDomain=miscellaneousTypeMapper.convertModeltoDomain(miscellaneousTypeModel);
		miscellaneousTypeDomain=iMiscellaneousTypeService.save(miscellaneousTypeDomain);
		miscellaneousTypeModel=miscellaneousTypeMapper.convertDomaintoModel(miscellaneousTypeDomain);
		
		return new ResponseEntity<>(miscellaneousTypeModel,HttpStatus.OK);
    	
    	}

	@GetMapping(WebConstantUrl.getAll)
	public ResponseEntity<?> getAll(HttpServletRequest request) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		List<MiscellaneousTypeModel>miscellaneousTypeModels=iMiscellaneousTypeService.getAll()
				                                        .stream().map(miscellaneousTypeMapper::convertDomaintoModel).collect(Collectors.toList());

		return new ResponseEntity<>(miscellaneousTypeModels,HttpStatus.OK);
	}

	@GetMapping(WebConstantUrl.getById)
	public ResponseEntity<?> getById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		MiscellaneousTypeDomain miscellaneousTypeDomain = iMiscellaneousTypeService.getById(id);

		MiscellaneousTypeModel miscellaneousTypeModel = miscellaneousTypeMapper.convertDomaintoModel(miscellaneousTypeDomain);

		return new ResponseEntity<>(miscellaneousTypeModel, HttpStatus.OK);

	}

	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody MiscellaneousTypeModel miscellaneousTypeModel, HttpServletRequest request,Principal principal) {
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		MiscellaneousTypeDomain miscellaneousTypeDomain=miscellaneousTypeMapper.convertModeltoDomain(miscellaneousTypeModel);
		miscellaneousTypeDomain=iMiscellaneousTypeService.update(miscellaneousTypeDomain);
		
		miscellaneousTypeModel=miscellaneousTypeMapper.convertDomaintoModel(miscellaneousTypeDomain);
		
		return new ResponseEntity<>(miscellaneousTypeModel,HttpStatus.OK);
		
		
		
		
		
	}

	



	
	



}
