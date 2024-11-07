package com.titan.irgs.accessPolicy.contoller;

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
import com.titan.irgs.accessPolicy.domain.Module;
import com.titan.irgs.accessPolicy.mapper.ModuleMapper;
import com.titan.irgs.accessPolicy.model.ModuleVo;
import com.titan.irgs.accessPolicy.service.ModuleService;


@RestController
@RequestMapping(WebConstantUrl.MODULE)
public class ModuleController {
	
	@Autowired
	ModuleService modelService;
	
	@Autowired
	ModuleMapper modelsMapper;
	
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	
	
	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> save(@RequestBody ModuleVo modelVo,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Module model=modelsMapper.convertModeltoDomain(modelVo);
		model.setCreatedBy(1l);
		model.setUpdatedBy(1l);
		model.setEnabledStatus(true);
		model=modelService.save(model);
		modelVo=modelsMapper.convertDomaintoModel(model);
	   return new ResponseEntity<>(modelVo,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ModuleVo> modelVos=modelService.getAll()
											.stream().map(modelsMapper::convertDomaintoModel)
																			       .collect(Collectors.toList());
		return new ResponseEntity<>(modelVos,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Module model=modelService.getById(id);
		ModuleVo modelVo=modelsMapper.convertDomaintoModel(model);
     return new ResponseEntity<>(modelVo,HttpStatus.OK);
    	
    	}
	
	@PutMapping(WebConstantUrl.UPDATE)
    public ResponseEntity<?> getById(@RequestBody ModuleVo modelVo,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Module model=modelsMapper.convertModeltoDomain(modelVo);
		model.setCreatedBy(1l);
		model.setUpdatedBy(1l);
		model.setEnabledStatus(true);
		model=modelService.save(model);
		modelVo=modelsMapper.convertDomaintoModel(model);
     return new ResponseEntity<>(modelVo,HttpStatus.OK);
    	
    	}
	}
