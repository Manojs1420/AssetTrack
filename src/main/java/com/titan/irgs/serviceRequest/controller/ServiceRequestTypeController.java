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
import com.titan.irgs.serviceRequest.domain.ServceRequestType;
import com.titan.irgs.serviceRequest.mapper.ServiceRequestTypeMapper;
import com.titan.irgs.serviceRequest.model.ServiceRequestTypeModel;
import com.titan.irgs.serviceRequest.service.ServiceRequestTypeService;

@RestController
@RequestMapping(WebConstantUrl.SERVICE_REQUEST_TYPE)
public class ServiceRequestTypeController {
	
	

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ServiceRequestTypeMapper serviceRequestTypeMapper;

	@Autowired
	ServiceRequestTypeService iserviceRequestTypeService;
	
    @PostMapping(WebConstantUrl.save)
	public ResponseEntity<?> save(@RequestBody ServiceRequestTypeModel serviceRequestTypeModel, HttpServletRequest request,
			Principal principal) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		ServceRequestType servceRequestType=serviceRequestTypeMapper.convertModeltoDomain(serviceRequestTypeModel);
		servceRequestType=iserviceRequestTypeService.save(servceRequestType);
		serviceRequestTypeModel=serviceRequestTypeMapper.convertDomaintoModel(servceRequestType);
		
		return new ResponseEntity<>(serviceRequestTypeModel,HttpStatus.OK);
    	
    	}

	@GetMapping(WebConstantUrl.getAll)
	public ResponseEntity<?> getAll(HttpServletRequest request) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		List<ServiceRequestTypeModel>serviceRequestTypeModels=iserviceRequestTypeService.getAll()
				                                        .stream().map(serviceRequestTypeMapper::convertDomaintoModel).collect(Collectors.toList());

		return new ResponseEntity<>(serviceRequestTypeModels,HttpStatus.OK);
	}

	@GetMapping(WebConstantUrl.getById)
	public ResponseEntity<?> getById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		ServceRequestType servceRequestType = iserviceRequestTypeService.getById(id);

		ServiceRequestTypeModel serviceRequestTypeModel = serviceRequestTypeMapper.convertDomaintoModel(servceRequestType);

		return new ResponseEntity<>(serviceRequestTypeModel, HttpStatus.OK);

	}

	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody ServiceRequestTypeModel serviceRequestTypeModel, HttpServletRequest request,
			Principal principal) {
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		ServceRequestType servceRequestType=serviceRequestTypeMapper.convertModeltoDomain(serviceRequestTypeModel);
		servceRequestType=iserviceRequestTypeService.update(servceRequestType);
		serviceRequestTypeModel=serviceRequestTypeMapper.convertDomaintoModel(servceRequestType);
		
		return new ResponseEntity<>(serviceRequestTypeModel,HttpStatus.OK);
		
		
		
		
		
	}

	



	
	

}
