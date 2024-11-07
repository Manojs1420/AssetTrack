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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.mapper.DepartmentMapper;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.service.DepartmentService;
import com.titan.irgs.master.vo.DepartmentVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping(value = WebConstantUrl.DEPARTMENT_BASE_URL)
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@Autowired
	DepartmentMapper departmentMapper;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	UserRepo userRepo;

	@PostMapping(value = WebConstantUrl.GET_ALL_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<?> getAllDepartment(@RequestBody DepartmentVO departmentVO,Principal principal)
	{
		Pageable page=PageRequest.of(departmentVO.getPage()==0?1:departmentVO.getPage()-1, departmentVO.getSize());
		Map<String,Object> map=new HashMap<>();

		User user=userRepo.findByUsername(principal.getName());
		//filtering 
		List<DepartmentVO> departmentVOs = new ArrayList<DepartmentVO>(0);
		Page<Department> departments = departmentService.getAllDepartment(departmentVO,page);
		if(departments.getContent().size() == 0) {


			map.put("departmentVOs", departmentVOs);
			map.put("total_pages", departments.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", departments.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			departments.forEach(department -> {
				departmentVOs.add(departmentMapper.getVoFromEntity(department));
			});	

		}

		map.put("departmentVOs", departmentVOs);
		map.put("total_pages", departments.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", departments.getTotalElements());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}


	@PostMapping(value = WebConstantUrl.GET_DEPARTMENT_BY_ID)
	@ResponseBody
	public ResponseEntity<DepartmentVO> getDepartmentById(@RequestBody DepartmentVO departmentVO)
	{
		Department department = departmentService.getDepartmentById(departmentVO.getDepartmentId());
		if (department == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<DepartmentVO>(departmentMapper.getVoFromEntity(department), HttpStatus.OK);
	}
	
	@PostMapping(value = WebConstantUrl.GET_DEPARTMENT_BY_WEBMASTER_ID)
	@ResponseBody
	public ResponseEntity<?> getDepartmentByWebMasterId(@RequestBody DepartmentVO departmentVO)
	{
		Pageable page=PageRequest.of(departmentVO.getPage()==0?1:departmentVO.getPage()-1, departmentVO.getSize());
		Page<Department> departments = departmentService.getDepartmentByWebMasterId(departmentVO.getWebMasterId(),page);
		Map<String,Object> map=new HashMap<>();

		List<DepartmentVO> departmentVOs = new ArrayList<DepartmentVO>(0);
		if(departments.getContent().size() == 0) {


			map.put("departmentVOs", departmentVOs);
			map.put("total_pages", departments.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", departments.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			departments.forEach(department -> {
				departmentVOs.add(departmentMapper.getVoFromEntity(department));
			});	
		}
		map.put("departmentVOs", departmentVOs);
		map.put("total_pages", departments.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", departments.getTotalElements());
		return new ResponseEntity<>(map, HttpStatus.OK);	
	}

	@PostMapping(WebConstantUrl.SAVE_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<?> saveDepartment(@RequestBody DepartmentVO departmentVO) {
		Map<String,Object> map = new HashMap<>();
		Department department = departmentMapper.getEntityFromVo(departmentVO);

		/*	Department departmentName = departmentRepo.findByDepartmentName(departmentVO.getDepartmentName());
	     if(departmentName != null) {

			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Department Name : " + departmentVO.getDepartmentName()
					+ " is already present. So Duplicate entry for 'Department Name'" + " is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);

		}
		 */
		Department departmentName = departmentRepo.findByWebMasterWebMasterIdAndDepartmentName(departmentVO.getWebMasterId(),departmentVO.getDepartmentName());
		if(departmentName != null) {

			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Department Name : " + departmentVO.getDepartmentName()
			+ " is already present in Vertical: " +departmentName.getWebMaster().getWebMasterName());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);

		}

		department = departmentService.saveDepartment(department);

		if (department == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<DepartmentVO>(departmentMapper.getVoFromEntity(department),HttpStatus.CREATED);
	}

	@PostMapping(value = WebConstantUrl.UPDATE_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<DepartmentVO> updateDepartment(@RequestBody DepartmentVO departmentVO)
	{
		Department department = departmentMapper.getEntityFromVo(departmentVO);
		department = departmentService.updateDepartment(department);
		if (department == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<DepartmentVO>(departmentMapper.getVoFromEntity(department), HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.DELETE_DEPARTMENT_BY_ID)
	@ResponseBody
	public void deleteDepartmentById(@RequestBody DepartmentVO departmentVO)
	{
		departmentService.deleteDepartmentById(departmentVO.getDepartmentId());
	}

}
