package com.titan.irgs.accessPolicy.contoller;

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
import com.titan.irgs.accessPolicy.domain.SubModule;
import com.titan.irgs.accessPolicy.mapper.SubModuleMapper;
import com.titan.irgs.accessPolicy.model.SubModuleVo;
import com.titan.irgs.accessPolicy.service.SubModuleService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.service.IUserService;


@RestController
@RequestMapping(WebConstantUrl.SUBMODULE)
public class SubModuleController {
	
	@Autowired
	SubModuleService subModelService;
	
	@Autowired
	SubModuleMapper subModelsMapper;
	
	@Autowired
	IUserService iUserService;
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> saveRole(@RequestBody SubModuleVo subModuleVo,Principal principal,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		User user=iUserService.findByUserName(principal.getName());
		SubModule subModule=subModelsMapper.convertModeltoDomain(subModuleVo);
		subModule.setCreatedBy(user.getId());
		subModule.setUpdatedBy(user.getId());
		subModule.setEnabledStatus(true);
		subModule=subModelService.save(subModule);
		subModuleVo=subModelsMapper.convertDomaintoModel(subModule);
	   return new ResponseEntity<>(subModuleVo,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<SubModuleVo> modelVos=subModelService.getAll()
											.stream().map(subModelsMapper::convertDomaintoModel)
																			       .collect(Collectors.toList());
		return new ResponseEntity<>(modelVos,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		SubModule subModule=subModelService.getById(id);
		SubModuleVo modelVo=subModelsMapper.convertDomaintoModel(subModule);
     return new ResponseEntity<>(modelVo,HttpStatus.OK);
    	
    	}
	
	@PutMapping(WebConstantUrl.UPDATE)
    public ResponseEntity<?> update(@RequestBody SubModuleVo subModuleVo,Principal principal,HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		User user=iUserService.findByUserName(principal.getName());
		SubModule subModule=subModelsMapper.convertModeltoDomain(subModuleVo);
		subModule.setUpdatedBy(user.getId());
		subModule.setEnabledStatus(true);
		subModule=subModelService.update(subModule);
		subModuleVo=subModelsMapper.convertDomaintoModel(subModule);
	   return new ResponseEntity<>(subModuleVo,HttpStatus.OK);
    	
    	}


}
