package com.titan.irgs.webMaster.controller;

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
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.mapper.WebMasterMapper;
import com.titan.irgs.webMaster.model.WebMasterModel;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.service.IWebMaster;

@RestController
@RequestMapping(WebConstantUrl.WebMaster)
public class WebMasterController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(WebMasterController.class);
	@Autowired
	WebMasterRepo webMasterRepo;
	@Autowired
	WebMasterMapper webMasterMapper;
	
	@Autowired
	IWebMaster iWebMaster;

	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> saveWebMaster(@RequestBody WebMasterModel webMasterModel,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		WebMaster webMaster=webMasterMapper.convertModeltoDomain(webMasterModel);
		webMaster=iWebMaster.save(webMaster);
		webMasterRepo.insertByVerticalId(webMaster.getWebMasterId());

		webMasterModel=webMasterMapper.convertDomaintoModel(webMaster);
		
		return new ResponseEntity<>(webMasterModel,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		
		List<WebMasterModel> applicationAccessModels=iWebMaster.getAll()
																	.stream()
																		.map(webMasterMapper::convertDomaintoModel)
																			.collect(Collectors.toList());
		
		return new ResponseEntity<>(applicationAccessModels,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		WebMaster webMaster=iWebMaster.getById(id);
		WebMasterModel webMasterModel=webMasterMapper.convertDomaintoModel(webMaster);
		return new ResponseEntity<>(webMasterModel,HttpStatus.OK);
    	
    	}
	
	@PutMapping(WebConstantUrl.UPDATE)
    public ResponseEntity<?> update(@RequestBody WebMasterModel webMasterModel,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		WebMaster webMaster=webMasterMapper.convertModeltoDomain(webMasterModel);
		webMaster=iWebMaster.update(webMaster);
		webMasterModel=webMasterMapper.convertDomaintoModel(webMaster);
		
		return new ResponseEntity<>(webMasterModel,HttpStatus.OK);
    	
    	}
	
    

}
