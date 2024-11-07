package com.titan.irgs.accessPolicy.contoller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.accessPolicy.domain.BackEndApis;
import com.titan.irgs.accessPolicy.domain.Permission;
import com.titan.irgs.accessPolicy.mapper.BackendApiMapper;
import com.titan.irgs.accessPolicy.model.BackEndApisModel;
import com.titan.irgs.accessPolicy.model.PermissionModel;
import com.titan.irgs.accessPolicy.repository.PermissionRepo;
import com.titan.irgs.accessPolicy.service.BackendApiService;
import com.titan.irgs.accessPolicy.service.FeatureService;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(WebConstantUrl.BACKENDAPIS)
public class BackendApisController {

	
	@Autowired
	BackendApiMapper backendApiMapper;
	
	@Autowired
	BackendApiService backendApiService;
	
	@Autowired
	IUserService iUserService;
	
	@Autowired
	PermissionRepo permissionRepo;
	
	@Autowired
	IWebRoleService webRoleService;
	
	@Autowired
	IWebMaster iWebMaster;
	
	@Autowired
	FeatureService featureService;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(AccesspolicyController.class);

	
	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> saveBackendApi(@RequestBody BackEndApisModel backEndApisModel,HttpServletRequest request,Principal principal){
				logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		BackEndApis backEndApis=null;
		backEndApis=backendApiService.findByBackEndApiIdUrl(backEndApisModel.getBackEndApiIdUrl());
		if(backEndApis!=null) {
			throw new ResourceAlreadyExitException(backEndApisModel.getBackEndApiIdUrl()+" is already exists");
		}
	
		backEndApis=backendApiMapper.convertModeltoDomain(backEndApisModel);
		  
		backEndApis=backendApiService.save(backEndApis);
		 
		WebMaster webMaster=iWebMaster.getById(backEndApisModel.getVerticalId());
		webMaster.setCreatedOn(new Date());

		for(PermissionModel  permissionVo:backEndApisModel.getPermissions()) {
			Permission permission=new Permission();
			permission.setRoleName(permissionVo.getRoleName());
			permission.setBackEndApis(backEndApis);
			permission.setVerticalName(webMaster.getWebMasterName());
			permission.setCreatedOn(new Date());
			permission.setWebRole(webRoleService.getById(permissionVo.getWebRoleId()));
			permissionRepo.save(permission);
			
		}
	   return new ResponseEntity<>(HttpStatus.CREATED);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(
    		@RequestParam(required=false) String url,
    		@RequestParam(required=false) String featureName,
    		Pageable pageable,Principal principal,HttpServletRequest request
    		){
		
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		
	
		
		Map<String,Object>map=new HashMap<>();
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		
		List<BackEndApisModel> backEndApisModel=new ArrayList<>();
		Page<BackEndApis> backEndApis=backendApiService.getAll(url,featureName,page);
		
		if(backEndApis.getContent().size()==0) {
			map.put("roleModels", backEndApis);
			map.put("total_pages", backEndApis.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", backEndApis.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
			}
		
		
		backEndApisModel=backEndApis.stream().map(backendApiMapper::convertDomainToModel).collect(Collectors.toList());
		map.put("roleModels", backEndApisModel);
		map.put("total_pages", backEndApis.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", backEndApis.getTotalElements());

		return new ResponseEntity<>(map,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
	public ResponseEntity<?> getById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		BackEndApis backEndApis = backendApiService.getById(id);

		BackEndApisModel backEndApisModel = backendApiMapper.convertDomainToModel(backEndApis);

		return new ResponseEntity<>(backEndApisModel, HttpStatus.OK);

	}
	
	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody BackEndApisModel backEndApisModel,HttpServletRequest request,Principal principal) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		BackEndApis backEndApis=backendApiMapper.convertModeltoDomain(backEndApisModel);
		WebMaster webMaster=iWebMaster.getById(backEndApisModel.getVerticalId());

		backEndApis=backendApiService.update(backEndApis);
		for(PermissionModel  permissionVo:backEndApisModel.getPermissions()) {
			Permission permission=null; 
			System.out.println(backEndApis.getBackEndApiIdUrl());
			permission=permissionRepo.checkApiAppendTorole(backEndApis.getBackEndApiIdUrl(), permissionVo.getWebRoleId());
			if(permission==null) {
				permission=new Permission();
				permission.setRoleName(permissionVo.getRoleName());
				permission.setBackEndApis(backEndApis);
				permission.setVerticalName(webMaster.getWebMasterName());
				permission.setCreatedOn(new Date());
				permission.setWebRole(webRoleService.getById(permissionVo.getWebRoleId()));
				permissionRepo.save(permission);
			}
			
		}

		
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@DeleteMapping(WebConstantUrl.DELETEPERMISSIONBYPERMISSIONID)
	public ResponseEntity<?> deletePermissionByPermissionId(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		permissionRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	



}
