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
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.service.IClusterService;
import com.titan.irgs.master.service.IRegionService;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.mapper.IRoleMapper;
import com.titan.irgs.role.model.RoleModel;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;
import com.titan.irgs.role.service.IRoleService;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(WebConstantUrl.ROLE)
public class RoleController {
	
	@Autowired
	IRoleService iRoleService;
	
	@Autowired
    IRoleMapper iRoleMapper;
	
	@Autowired
	IWebRoleService iWebRoleService;
	
	@Autowired
	IWebMaster iWebMaster;
	
	@Autowired
	IRegionService iRegionService;
	
	@Autowired
	IClusterService iClusterService;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	
	
	@PostMapping(WebConstantUrl.save)
	public ResponseEntity<?> saveRole(@RequestBody RoleModel roleModel,HttpServletRequest request){

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Role role=iRoleMapper.convertModeltoDomain(roleModel);
		role=iRoleService.save(role);

	/*	if(roleModel.getRoleWiseDepartmentsVOs().size()!=0) {
			for(RoleWiseDepartmentsVO roleWiseDepartmentsVO:roleModel.getRoleWiseDepartmentsVOs()) {
				RoleWiseDepartments roleWiseDepartments=new RoleWiseDepartments();
				roleWiseDepartments.setCreatedOn(new Date());

				Department department=departmentRepo.findByDepartmentId(roleWiseDepartmentsVO.getDepartmentId());
				roleWiseDepartments.setDepartment(department);
				roleWiseDepartments.setRole(role);
				roleWiseDepartmentsRepo.save(roleWiseDepartments);
			}
		}*/
		roleModel=iRoleMapper.convertDomaintoModel(role);
		return new ResponseEntity<>(roleModel,HttpStatus.OK);

	}

	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<RoleModel> roleModels=iRoleService.getAll()
											.stream().map(iRoleMapper::convertDomaintoModel)
																			       .collect(Collectors.toList());
		return new ResponseEntity<>(roleModels,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Role role=iRoleService.getById(id);
		RoleModel applicationAccessModel=iRoleMapper.convertDomaintoModel(role);
     return new ResponseEntity<>(applicationAccessModel,HttpStatus.OK);
    	
    	}
	
	



}
