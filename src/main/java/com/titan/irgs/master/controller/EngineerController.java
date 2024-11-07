package com.titan.irgs.master.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.master.domain.Engineer;
import com.titan.irgs.master.mapper.EngineerMapper;
import com.titan.irgs.master.repository.EngineerRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IEngineerService;
import com.titan.irgs.master.vo.EngineerVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

/**
 * This is EngineerController class which is responsible for handling all request(CRUD) for Engineer related data
 * @author 
 *
 */
@RestController
@RequestMapping(value = WebConstantUrl.ENGINEER_BASE_URL)

public class EngineerController {
	
	@Autowired
	private IEngineerService engineerService;
	
	@Autowired
	private EngineerMapper engineerMapper;
	
	@Autowired
	private EngineerRepository engineerRepository;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private VendorRepository vendorRepository;
	
	private final static String VENDOR = "Vendor";

	

	
	//:::::::::::::::::::Engineer CRUD operation
	
	@GetMapping(value = WebConstantUrl.GET_ALL_ENGINEER)
	@ResponseBody
	public ResponseEntity<?> getAllEngineer(
			@RequestParam(required=false) String engineerName,
			@RequestParam(required=false) String emailId,
			@RequestParam(required=false) String mobileNo,
			@RequestParam(required=false) String vendor,
			Pageable pageable,Principal principal
			)
	{
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?1:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();
		
		User user=userRepo.findByUsername(principal.getName());
		//filtering 
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {

			vendor = vendorRepository.findByVendorCode(user.getUsername()).getVendorName();

		}
		
		List<EngineerVO> engineerVos = new ArrayList<EngineerVO>(0);
		Page<Engineer> engineers = engineerService.getAllEngineer(engineerName,emailId,mobileNo,vendor,page);
		if(engineers.getContent().size() == 0) {
			
			
			map.put("engineerVos", engineerVos);
			map.put("total_pages", engineers.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", engineers.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			engineers.forEach(brand -> {
				engineerVos.add(engineerMapper.getVoFromEntity(brand));
			});
			
			
		}
		
		map.put("engineerVos", engineerVos);
		map.put("total_pages", engineers.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", engineers.getTotalElements());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_ENGINEERS_BY_USING_VENDOR_ID)
	@ResponseBody
	public ResponseEntity<List<EngineerVO>> getAllEnginerByVendorId(@PathVariable("id") Long id)
	
	{
		List<EngineerVO> engineerVos = new ArrayList<EngineerVO>(0);
		List<Engineer> engineers = engineerService.getAllEnginerByVendorId(id);
		if(engineers.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			engineers.forEach(brand -> {
				engineerVos.add(engineerMapper.getVoFromEntity(brand));
			});
		}
		return new ResponseEntity<List<EngineerVO>>(engineerVos, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ENGINEER_BY_ID)
	@ResponseBody
	public ResponseEntity<EngineerVO> getEngineerById(@PathVariable Long engineerId)
	{
		Engineer engineer = engineerService.getEngineerById(engineerId);
		if (engineer == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<EngineerVO>(engineerMapper.getVoFromEntity(engineer), HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.SAVE_ENGINEER)
	@ResponseBody
	public ResponseEntity<?> saveEngineer(@RequestBody EngineerVO engineerVo) {
		Map<String,Object> map = new HashMap<>();
		Engineer engineer = engineerMapper.getEntityFromVo(engineerVo);

		Engineer engineerName = engineerRepository.findByEngineerName(engineerVo.getEngineerName());
	     if(engineerName != null) {
			
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Engineer Name : " + engineerVo.getEngineerName()
					+ " is already present. So Duplicate entry for 'Engineer Name'" + " is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			
		}
	     
		/*
		 * Engineer emailId = engineerRepository.findByEmailId(engineerVo.getEmailId());
		 * 
		 * if(emailId != null) {
		 * 
		 * map.put("status code", 400); map.put("client status", "Bad Request");
		 * map.put("error msg", " Email Id: " + engineerVo.getEmailId() +
		 * " is already present. So Duplicate entry for 'Email Id'" +
		 * " is not allowed."); return new ResponseEntity<Map<String, Object>>(map,
		 * HttpStatus.BAD_REQUEST);
		 * 
		 * }
		 * 
		 * Engineer mobileNo =
		 * engineerRepository.findByMobileNo(engineerVo.getMobileNo());
		 * 
		 * if(mobileNo != null) {
		 * 
		 * map.put("status code", 400); map.put("client status", "Bad Request");
		 * map.put("error msg", " Mobile No: " + engineerVo.getMobileNo() +
		 * " is already present. So Duplicate entry for 'Mobile No'" +
		 * " is not allowed."); return new ResponseEntity<Map<String, Object>>(map,
		 * HttpStatus.BAD_REQUEST);
		 * 
		 * }
		 * 
		 */		engineer = engineerService.saveEngineer(engineer);

		if (engineer == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<EngineerVO>(engineerMapper.getVoFromEntity(engineer),HttpStatus.CREATED);
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_ENGINEER)
	@ResponseBody
	public ResponseEntity<EngineerVO> updateEngineer(@RequestBody EngineerVO engineerVo)
	{
		Engineer engineer = engineerMapper.getEntityFromVo(engineerVo);
		engineer = engineerService.updateEngineer(engineer);
		if (engineer == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<EngineerVO>(engineerMapper.getVoFromEntity(engineer), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_ENGINEER_BY_ID)
	@ResponseBody
	public void deleteEngineerById(@PathVariable Long engineerId)
	{
		engineerService.deleteEngineerById(engineerId);
	}
	
	
	
	
	
	
	
	
	
	
	
	/*@GetMapping(value = WebConstantUrl.GET_ENGINEER_BY_VENDOR_ID)
	@ResponseBody
	public  List<EngineerVO> getEngineerByVendorId(@PathVariable Long id)
	{
		List<EngineerVO> engineersVo = new ArrayList<EngineerVO>(0);
		List<Engineer> engineers = engineerService.findByVendorId(id);
		
		engineers.forEach(engineer1 ->{
			engineersVo.add(engineerMapper.getVoFromEntity(engineer1));
		});
		
		return engineersVo;
		
	}*/
	
	

	/*@GetMapping(value = WebUrlConstants.GET_ENGINEER_BY_USER_ID)
	@ResponseBody
	public  ResponseEntity<EngineerVO> getEngineerByuserId(@PathVariable Long userId)
	{
		Engineer engineer = engineerService.getEngineerById(userId);
		if (engineer == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<EngineerVO>(engineerMapper.getVoFromEntity(engineer), HttpStatus.OK);
		
	}*/
}
