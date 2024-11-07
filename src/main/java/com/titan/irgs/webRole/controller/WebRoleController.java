package com.titan.irgs.webRole.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.service.IClusterService;
import com.titan.irgs.master.service.IRegionService;
import com.titan.irgs.master.service.RoleWiseDepartmentsService;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.role.model.OperationTypeEnum;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;
import com.titan.irgs.role.service.IRoleService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.domain.OpertionType;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.mapper.WebRoleMapper;
import com.titan.irgs.webRole.model.WebRoleModel;
import com.titan.irgs.webRole.repository.OperationTyprRepo;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(value=WebConstantUrl.WebRole)
public class WebRoleController {
	

	
	@Autowired
	IWebRoleService iWebRoleService;
	
	@Autowired
    WebRoleMapper webRoleMapper;
	
	@Autowired
	IRoleService iRoleService;
	
	
	@Autowired
	IWebMaster iWebMaster;
	
	@Autowired
	IRegionService iRegionService;
	
	@Autowired
	IClusterService iClusterService;
	
	@Autowired
	OperationTyprRepo operationTyprRepo;
	
	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Autowired
	RoleWiseDepartmentsService roleWiseDepartmentsService;
	
	private static final Logger logger = LoggerFactory.getLogger(WebRoleController.class);

	
	
	@SuppressWarnings("unused")
	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> saveRole(@RequestBody WebRoleModel webRoleModel,HttpServletRequest request,Principal principal){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		

		
	   Role role=iRoleService.findByRoleName(webRoleModel.getRoleName());
	 
       
       

		if(role==null) 
		{
		role=new Role();
		role.setRoleName(webRoleModel.getRoleName());
		role.setEnabledStatus(true);
		role.setCreatedBy(1);
		role.setUpdatedBy(1);
		role=iRoleService.save(role);

		}
		//getting Bussiness Verticle webMaster
		
		 WebMaster webMaster=iWebMaster.getById(webRoleModel.getBussinessVerticaLId());
	     WebRole exists=iWebRoleService.getByWebRoleUsingRoleIdAndVerticalId(role.getRoleId(),webMaster.getWebMasterId());
	     
	     if(exists!=null) {
	    	   
	    	   throw new ResourceAlreadyExitException("role name "+webRoleModel.getRoleName()+" is present");
	    	   
	       }
		
		//getting reportingto role
		Role reportingTo=iRoleService.getById(webRoleModel.getReportingId());

		
		//saving bussinessvertical and role in webRole table
		
		WebRole webRole=webRoleMapper.convertModeltoDomain(webRoleModel);
		webRole.setWebMaster(webMaster);
		webRole.setRole(role);
		webRole.setReporting(reportingTo);
		webRole=iWebRoleService.save(webRole);
		
		
		if(OperationTypeEnum.Country.equals(webRoleModel.getOperationType())) {
			OpertionType opertionType=new OpertionType();
			opertionType.setWebRole(webRole);
			opertionType.setOpertionType(webRoleModel.getOperationType());
			opertionType=operationTyprRepo.save(opertionType);
			webRole.setOpertionType(opertionType);
			
			List<Region> regions=iRegionService.getAllRegions();
			List<Cluster>clusters=new ArrayList<>();
			for(Region region:regions) {
				Cluster cluster=new Cluster();
				cluster.setRegion(region);
				cluster.setWebRole(webRole);
				cluster=iClusterService.saveCluster(cluster);
				clusters.add(cluster);

				}
			webRole.setCluster(clusters);

		}
		
		else {
			OpertionType opertionType=new OpertionType();
			opertionType.setWebRole(webRole);
			opertionType.setOpertionType(webRoleModel.getOperationType());
			operationTyprRepo.save(opertionType);
			List<Cluster>clusters=new ArrayList<>();
			for(Long regionId:webRoleModel.getRegionIds()) {
				Region region=iRegionService.getRegionById(regionId);

				Cluster cluster=new Cluster();
				cluster.setRegion(region);
				cluster.setWebRole(webRole);
				cluster=iClusterService.saveCluster(cluster);
				clusters.add(cluster);
				
			}
			webRole.setCluster(clusters);

		}
		
		if(webRoleModel.getDepartmentIds()!=null) {
			List<RoleWiseDepartments> roleWiseDepartments=new ArrayList<>();
			for(Long departmentIds:webRoleModel.getDepartmentIds()) {
				Department department=departmentRepo.findByDepartmentId(departmentIds);

				RoleWiseDepartments roleWiseDepartment=new RoleWiseDepartments();
				roleWiseDepartment.setDepartment(department);
				roleWiseDepartment.setWebRole(webRole);
				roleWiseDepartment.setCreatedOn(new Date());

				roleWiseDepartment=roleWiseDepartmentsRepo.save(roleWiseDepartment);
				roleWiseDepartments.add(roleWiseDepartment);
			}
			webRole.setRoleWiseDepartments(roleWiseDepartments);
		}
		
		webRoleModel=webRoleMapper.convertDomaintoModel(webRole);
	   return new ResponseEntity<>(webRoleModel,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(
    		@RequestParam(required=false) String roleName,
			@RequestParam(required=false) String businessVerticalTypeName,
			@RequestParam(required=false) String reportingTo,
			@RequestParam(required=false) String operationType,
    		Pageable pageable,Principal principal,HttpServletRequest request
    		){
		
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		Map<String,Object>map=new HashMap<>();
		List<WebRoleModel> roleModels=new ArrayList<>();
		List<WebRole> webRoles=iWebRoleService.getAll(roleName,businessVerticalTypeName,reportingTo,operationType,pageable);
		Long count=iWebRoleService.count(roleName,businessVerticalTypeName,reportingTo,operationType);
		
		
		if(webRoles.size()==0) {
			map.put("roleModels", roleModels);
			map.put("total_pages", 0);
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", 0);
			return new ResponseEntity<>(map,HttpStatus.OK);
			}
		
		
		webRoles.forEach(webRole -> {
			
			List<RoleWiseDepartments> roleWiseDepartments=roleWiseDepartmentsRepo.getByWebRoleId(webRole.getWebRoleId());
			List<String> names = new ArrayList<String>();
			List<Long> ids = new ArrayList<Long>();
			for(RoleWiseDepartments roleWiseDepartment:roleWiseDepartments) {
				Department departments=departmentRepo.findByDepartmentId(roleWiseDepartment.getDepartment().getDepartmentId());
				
				ids.add(departments.getDepartmentId());
				names.add(departments.getDepartmentName());
			}
			
			WebRoleModel webRoleModel=webRoleMapper.convertDomaintoModel(webRole);
			webRoleModel.setDepartmentIds(ids);
			webRoleModel.setDepartmentNames(names);
			
			roleModels.add(webRoleModel);
		});
		
	//	roleModels=webRoles.stream().map(webRoleMapper::convertDomaintoModel).collect(Collectors.toList());
		
		int page = (int) Math.ceil((double) count / (double) pageable.getPageSize());
		map.put("roleModels", roleModels);
		map.put("total_pages", page);
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", count);

		return new ResponseEntity<>(map,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		WebRole webRole=iWebRoleService.getById(id);
		
		List<RoleWiseDepartments> roleWiseDepartments=roleWiseDepartmentsRepo.getByWebRoleId(webRole.getWebRoleId());
			List<String> names = new ArrayList<String>();
			List<Long> ids = new ArrayList<Long>();
			for(RoleWiseDepartments roleWiseDepartment:roleWiseDepartments) {
				Department departments=departmentRepo.findByDepartmentId(roleWiseDepartment.getDepartment().getDepartmentId());
				
				ids.add(departments.getDepartmentId());
				names.add(departments.getDepartmentName());
			}
			
			WebRoleModel webRoleModel=webRoleMapper.convertDomaintoModel(webRole);
			webRoleModel.setDepartmentIds(ids);
			webRoleModel.setDepartmentNames(names);
			

	//	WebRoleModel applicationAccessModel=webRoleMapper.convertDomaintoModel(webRole);
     return new ResponseEntity<>(webRoleModel,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAlRolesUsingBussinessverticalId)
    public ResponseEntity<?> getAlRolesUsingBussinessverticalId(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		/*
		 * List<WebRoleModel>
		 * webRoleModels=iWebRoleService.getAlRolesUsingBussinessverticalId(id)
		 * .stream().filter(f->!f.getRole().getRoleName().equalsIgnoreCase("Vendor")).
		 * map(webRoleMapper::convertDomaintoModel) .collect(Collectors.toList());
		 */
		
		List<WebRoleModel> webRoleModels=iWebRoleService.getAlRolesUsingBussinessverticalId(id)
                .stream().map(webRoleMapper::convertDomaintoModel).collect(Collectors.toList());
		return new ResponseEntity<>(webRoleModels,HttpStatus.OK);
    	
    	}
	
	@GetMapping(value = WebConstantUrl.GET_ROLE_BY_VERTICALID_AND_DEPARTMENT_ID)
	@ResponseBody
	public  ResponseEntity<?> getAlRolesUsingBussinessverticalIdAndDepartmentId(@RequestParam(required = true) Long verticalId,
			@RequestParam(required = true) Long departmentId) {
		List<WebRoleModel> webRoleModels=iWebRoleService.getAlRolesUsingBussinessverticalIdAndDepartmentId(verticalId,departmentId).stream().map(webRoleMapper::convertDomaintoModel).collect(Collectors.toList());
		return new ResponseEntity<>(webRoleModels,HttpStatus.OK);
	}
	
	
	@PutMapping(WebConstantUrl.UPDATE)
    public ResponseEntity<?> update(@RequestBody WebRoleModel webRoleModel,HttpServletRequest request,Principal principal){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		
	   Role role=iRoleService.findByRoleName(webRoleModel.getRoleName());
       WebRole webRole=iWebRoleService.getById(webRoleModel.getWebRoleId());
		
	   if(role==null) 
		{
		role=new Role();
		role.setRoleName(webRoleModel.getRoleName());
		role.setEnabledStatus(true);
		role.setUpdatedBy(1);
		role=iRoleService.save(role);
		}
		//getting Bussiness Verticle webMaster
		WebMaster webMaster=iWebMaster.getById(webRoleModel.getBussinessVerticaLId());
		
		//getting reportingto role
		Role reportingTo=iRoleService.getById(webRoleModel.getReportingId());

		
		//saving bussinessvertical and role in webRole table
		
		//WebRole webRole=webRoleMapper.convertModeltoDomain(webRoleModel);
		webRole.setWebMaster(webMaster);
		webRole.setRole(role);
		webRole.setReporting(reportingTo);
		webRole=iWebRoleService.save(webRole);
		
		if(webRole.getOpertionType()==null) {
			
			if(OperationTypeEnum.Country.equals(webRoleModel.getOperationType())) {
				
				List<Long> regionsIds=iClusterService.getAllRegionIdsInClusterUsingWebRoleId(webRole.getWebRoleId());
				List<Region> regions=null;
				if(regionsIds.isEmpty()) {
					regions=iRegionService.getAllRegions();

				}else {
				     regions=iRegionService.getAllRegionsNotInRegionIds(regionsIds);
				}
				
				OpertionType opertionType=new OpertionType();
				opertionType.setWebRole(webRole);
				opertionType.setOpertionType(webRoleModel.getOperationType());
				operationTyprRepo.save(opertionType);
				
				for(Region region:regions) {
					Cluster cluster=new Cluster();
					cluster.setRegion(region);
					cluster.setWebRole(webRole);
					iClusterService.saveCluster(cluster);
					}
				}
			else if(OperationTypeEnum.Region.equals(webRoleModel.getOperationType())) {

				
				OpertionType opertionType=new OpertionType();
				opertionType.setWebRole(webRole);
				opertionType.setOpertionType(webRoleModel.getOperationType());
				operationTyprRepo.save(opertionType);
				
				
				
				List<Long> regionsIds=iClusterService.getAllRegionIdsInClusterUsingWebRoleId(webRole.getWebRoleId());
				if(!regionsIds.isEmpty()) {
				List<Long> regionsIdsCopy=new ArrayList<Long>();
				regionsIdsCopy.addAll(regionsIds);
				regionsIdsCopy.removeAll(webRoleModel.getRegionIds());
				  if(!regionsIdsCopy.isEmpty()) {
					  for(Long regionid:regionsIdsCopy) {
					  iClusterService.deleteClusterByUsingWebRoleIdAndRegionId(webRole.getWebRoleId(),regionid);
					  }
					  
				  }
				
				List<Long> regionsIdsCopy1=new ArrayList<Long>();
				regionsIdsCopy1.addAll(webRoleModel.getRegionIds());
				regionsIdsCopy1.removeAll(regionsIds);
				 if(!regionsIdsCopy1.isEmpty()) {
					  for(Long regionid:regionsIdsCopy1) {
						  Region region=iRegionService.getRegionById(regionid);
						  Cluster cluster=new Cluster();
						  	cluster.setRegion(region);
							cluster.setWebRole(webRole);
							iClusterService.saveCluster(cluster);
					  
					  }
					  
				  }
			
			}
				else {
					
					
					
					List<Region>regions=iRegionService.getAllRegions();
					
					for(Region region:regions) {
						Cluster cluster=new Cluster();
						cluster.setRegion(region);
						cluster.setWebRole(webRole);
						iClusterService.saveCluster(cluster);
						}
					
					}
				
			}
			
			
			
		}
		
		else {
		if(!webRole.getOpertionType().getOpertionType().equals(webRoleModel.getOperationType())) {
			
			if(OperationTypeEnum.Country.equals(webRoleModel.getOperationType())) {
				
				List<Long> regionsIds=iClusterService.getAllRegionIdsInClusterUsingWebRoleId(webRole.getWebRoleId());
				List<Region> regions=iRegionService.getAllRegionsNotInRegionIds(regionsIds);

				
				OpertionType opertionType=operationTyprRepo.getOne(webRole.getOpertionType().getOpertionTypeId());
				opertionType.setWebRole(webRole);
				opertionType.setOpertionType(webRoleModel.getOperationType());
				operationTyprRepo.save(opertionType);
				
				for(Region region:regions) {
					Cluster cluster=new Cluster();
					cluster.setRegion(region);
					cluster.setWebRole(webRole);
					iClusterService.saveCluster(cluster);
					}
			
			
			
			}
			}
			 if(OperationTypeEnum.Region.equals(webRoleModel.getOperationType())){
				
				OpertionType opertionType=operationTyprRepo.getOne(webRole.getOpertionType().getOpertionTypeId());
				opertionType.setWebRole(webRole);
				opertionType.setOpertionType(webRoleModel.getOperationType());
				operationTyprRepo.save(opertionType);
				
				
				
				List<Long> regionsIds=iClusterService.getAllRegionIdsInClusterUsingWebRoleId(webRole.getWebRoleId());
				List<Long> regionsIdsCopy=new ArrayList<Long>();
				regionsIdsCopy.addAll(regionsIds);
				regionsIdsCopy.removeAll(webRoleModel.getRegionIds());
				  if(!regionsIdsCopy.isEmpty()) {
					  for(Long regionid:regionsIdsCopy) {
					  iClusterService.deleteClusterByUsingWebRoleIdAndRegionId(webRole.getWebRoleId(),regionid);
					  }
					  
				  }
				
				List<Long> regionsIdsCopy1=new ArrayList<Long>();
				regionsIdsCopy1.addAll(webRoleModel.getRegionIds());
				regionsIdsCopy1.removeAll(regionsIds);
				 if(!regionsIdsCopy1.isEmpty()) {
					  for(Long regionid:regionsIdsCopy1) {
						  Region region=iRegionService.getRegionById(regionid);
						  Cluster cluster=new Cluster();
						  	cluster.setRegion(region);
							cluster.setWebRole(webRole);
							iClusterService.saveCluster(cluster);
					  
					  }
					  
				  }
			}
		}
		
		
		if(webRoleModel.getDepartmentIds()!=null) {
			List<Long> departmentIds=roleWiseDepartmentsService.getAllDepartmentIdsInRoleWiseUsingWebRoleId(webRole.getWebRoleId());
			if(!departmentIds.isEmpty()) {
				List<Long> departmentIdsCopy=new ArrayList<Long>();
				departmentIdsCopy.addAll(departmentIds);
				departmentIdsCopy.removeAll(webRoleModel.getDepartmentIds());
				if(!departmentIdsCopy.isEmpty()) {
					for(Long departmentid:departmentIdsCopy) {
						roleWiseDepartmentsService.deleteRoleWiseDepartmentsByUsingWebRoleIdAndDepartmentId(webRole.getWebRoleId(),departmentid);
					}

				}

				List<Long> departmentIdsCopy1=new ArrayList<Long>();
				departmentIdsCopy1.addAll(webRoleModel.getDepartmentIds());
				departmentIdsCopy1.removeAll(departmentIds);
				if(!departmentIdsCopy1.isEmpty()) {
					for(Long departmentid:departmentIdsCopy1) {
						Department department=departmentRepo.findByDepartmentId(departmentid);

						RoleWiseDepartments roleWiseDepartment=new RoleWiseDepartments();
						roleWiseDepartment.setDepartment(department);
						roleWiseDepartment.setWebRole(webRole);
						roleWiseDepartment.setCreatedOn(new Date());

						roleWiseDepartment=roleWiseDepartmentsRepo.save(roleWiseDepartment);
						//roleWiseDepartments.add(roleWiseDepartment);
						
					}

				}

			}
			else {

				for(Long departmentId:webRoleModel.getDepartmentIds()) {
					Department department=departmentRepo.findByDepartmentId(departmentId);

					RoleWiseDepartments roleWiseDepartment=new RoleWiseDepartments();
					roleWiseDepartment.setDepartment(department);
					roleWiseDepartment.setWebRole(webRole);
					roleWiseDepartment.setCreatedOn(new Date());

					roleWiseDepartment=roleWiseDepartmentsRepo.save(roleWiseDepartment);
					
				}

			}
		}
		
		webRoleModel=webRoleMapper.convertDomaintoModel(webRole);
	   return new ResponseEntity<>(webRoleModel,HttpStatus.OK);
    	
    	}



	
	
	

}
