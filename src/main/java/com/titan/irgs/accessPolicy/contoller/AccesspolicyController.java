package com.titan.irgs.accessPolicy.contoller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.titan.irgs.accessPolicy.domain.AccesspolicyDomain;
import com.titan.irgs.accessPolicy.mapper.AccesspolicyMapper;
import com.titan.irgs.accessPolicy.model.AccesspolicyModel;
import com.titan.irgs.accessPolicy.service.AccesspolicyService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.service.IUserService;

@RestController
@RequestMapping(WebConstantUrl.ACCESSPOLICY)
public class AccesspolicyController {
	
	@Autowired
	AccesspolicyMapper accesspolicyMapper;
	
	@Autowired
	AccesspolicyService accesspolicyService;
	
	@Autowired
	IUserService iUserService;
	
	private static final Logger logger = LoggerFactory.getLogger(AccesspolicyController.class);

	
	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> saveAccesspolicy(@RequestBody AccesspolicyModel accesspolicyModel,HttpServletRequest request,Principal principal){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Map<String, Object> map=new HashMap<String, Object>();
		User user=iUserService.findByUserName(principal.getName());
		if(!accesspolicyModel.getSubModuleIds().isEmpty()) {
			for(Long submoduleId:accesspolicyModel.getSubModuleIds()) {
				AccesspolicyDomain checkIfExists=accesspolicyService.getByAccessPolicyByModuleIdAndSubModuleIdAndWebRoleId(accesspolicyModel.getModuleId(),
					submoduleId,accesspolicyModel.getWebRoleId());
				if(checkIfExists==null)	{
					accesspolicyModel.setSubModuleId(submoduleId);
					AccesspolicyDomain accesspolicyDomain=accesspolicyMapper.convertModeltoDomain(accesspolicyModel);
					accesspolicyDomain.setCreatedBy(user.getId());
					accesspolicyDomain.setUpdatedBy(user.getId());
					accesspolicyDomain=accesspolicyService.save(accesspolicyDomain);
				}
				else {
						map.put("msg", "The given accesspolicy is attached to roleName");
					   return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
				}
			}
		}
		else {
			
			AccesspolicyDomain accesspolicyDomain=accesspolicyMapper.convertModeltoDomain(accesspolicyModel);
			accesspolicyDomain.setCreatedBy(user.getId());
			accesspolicyDomain.setUpdatedBy(user.getId());
			accesspolicyDomain=accesspolicyService.save(accesspolicyDomain);
			
		}
	   return new ResponseEntity<>(HttpStatus.OK);
    	
    }
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(@RequestParam(required=false) String businessVerticalName,
    								@RequestParam(required=false) String moduleName,
    								@RequestParam(required=false) String roleName,

    								HttpServletRequest request){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		Map<String,Object>map=new HashMap<>();
		//Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		
		List<AccesspolicyDomain> accesspolicyDomains=accesspolicyService.getAll(businessVerticalName,moduleName,roleName);
		
		if(accesspolicyDomains.size()==0) {
			map.put("roleModels", accesspolicyDomains);
			//map.put("total_pages", accesspolicyDomains.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			//map.put("total_records", accesspolicyDomains.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
			}
		
		List<AccesspolicyModel> accesspolicyModels=accesspolicyDomains.stream()
			.map(accesspolicyMapper::convertDomaintoModel).collect(Collectors.toList());
		
		map.put("roleModels", accesspolicyModels);
		//map.put("total_pages", accesspolicyDomains.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		//map.put("total_records", accesspolicyDomains.getTotalElements());
		
		return new ResponseEntity<>(map,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.GETBYMODULEIDANDWEBROLEID)
    public ResponseEntity<?> getByModuleIdAndWebRoleId(@RequestParam("moduleId") Long moduleId,@RequestParam("webRoleId") Long webRoleId,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<AccesspolicyDomain> accesspolicyDomains=accesspolicyService.getByModuleIdAndWebRoleId(moduleId,webRoleId);
		List<AccesspolicyModel> accesspolicyModels=accesspolicyDomains.stream()
				.map(accesspolicyMapper::convertDomaintoModel).collect(Collectors.toList());;
		
     return new ResponseEntity<>(accesspolicyModels,HttpStatus.OK);
    	
    	}
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		AccesspolicyDomain accesspolicyDomain=accesspolicyService.getById(id);
		AccesspolicyModel accesspolicyModel=accesspolicyMapper.convertDomaintoModel(accesspolicyDomain);
     return new ResponseEntity<>(accesspolicyModel,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.GETACCESSPOLICYBYUSINGROLEID)
    public ResponseEntity<?> getAccesspolicyByUsingRoleId(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<AccesspolicyDomain> accesspolicyDomains=accesspolicyService.getAccesspolicyByUsingRoleId(id);
		List<AccesspolicyModel> accesspolicyModels=accesspolicyDomains.stream()
					.map(accesspolicyMapper::convertDomaintoModel).collect(Collectors.toList());
     return new ResponseEntity<>(accesspolicyModels,HttpStatus.OK);
    	
    	}

	@DeleteMapping(WebConstantUrl.DELETE_ACCESSPOLICY_BY_ID)
	public ResponseEntity<?> deleteAccesspolicyById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		accesspolicyService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@PutMapping(WebConstantUrl.UPDATE)
	public ResponseEntity<?> update(@RequestBody AccesspolicyModel accesspolicyModel,HttpServletRequest request,Principal principal) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		User user=iUserService.findByUserName(principal.getName());

		if(!accesspolicyModel.getSubModuleIds().isEmpty()) {

		for(Long submoduleId:accesspolicyModel.getSubModuleIds()) {
			
			AccesspolicyDomain checkIfExists=accesspolicyService.getByAccessPolicyByModuleIdAndSubModuleIdAndWebRoleId(accesspolicyModel.getModuleId(),
					submoduleId,accesspolicyModel.getWebRoleId());
			if(checkIfExists==null) {
		    accesspolicyModel.setSubModuleId(submoduleId);
		    AccesspolicyDomain accesspolicyDomain=accesspolicyMapper.convertModeltoDomain(accesspolicyModel);
		    accesspolicyDomain.setAccesspolicyId(null);
		    accesspolicyDomain.setCreatedBy(user.getId());
			accesspolicyDomain.setUpdatedBy(user.getId());
			accesspolicyDomain=accesspolicyService.save(accesspolicyDomain);
			}
			
		  }
		}
         else {
			
			AccesspolicyDomain accesspolicyDomain=accesspolicyMapper.convertModeltoDomain(accesspolicyModel);
			accesspolicyDomain.setCreatedBy(user.getId());
			accesspolicyDomain.setUpdatedBy(user.getId());
			accesspolicyDomain=accesspolicyService.save(accesspolicyDomain);
			
			
			
	    }
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	

}
