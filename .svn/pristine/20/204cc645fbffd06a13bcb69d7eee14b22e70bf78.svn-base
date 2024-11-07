package com.titan.irgs.role.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.role.domain.ApplicationAccess;
import com.titan.irgs.role.mapper.ApplicationAccessMapper;
import com.titan.irgs.role.model.ApplicationAccessModel;
import com.titan.irgs.role.service.IApplicationAccess;

@RestController
@RequestMapping(WebConstantUrl.APPLICATIONACCESS)
public class ApplicationAcessController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationAcessController.class);
	
	@Autowired
	ApplicationAccessMapper applicationAccessMapper;
	
	@Autowired
	IApplicationAccess iApplicationAccess;

	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> saveApplicationAcesss(@RequestBody ApplicationAccessModel applicationAccessModel,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		ApplicationAccess applicationAccess=applicationAccessMapper.convertModeltoDomain(applicationAccessModel);
		applicationAccess=iApplicationAccess.save(applicationAccess);
		applicationAccessModel=applicationAccessMapper.convertDomaintoModel(applicationAccess);
		
		return new ResponseEntity<>(applicationAccessModel,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		List<ApplicationAccessModel> applicationAccessModels=iApplicationAccess.getAll()
																	.stream()
																		.map(applicationAccessMapper::convertDomaintoModel)
																			.collect(Collectors.toList());
		return new ResponseEntity<>(applicationAccessModels,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		ApplicationAccess applicationAccess=iApplicationAccess.getById(id);
		ApplicationAccessModel applicationAccessModel=applicationAccessMapper.convertDomaintoModel(applicationAccess);

				return new ResponseEntity<>(applicationAccessModel,HttpStatus.OK);
			}
	
	
	
    

}
