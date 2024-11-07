package com.titan.irgs.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.master.mapper.StoreServiceTypeMapper;
import com.titan.irgs.master.service.IStoreServiceTypeService;
import com.titan.irgs.master.vo.StoreServiceTypeVO;

@RestController
@RequestMapping(value = WebConstantUrl.STORE_TYPE_SERVICE_BASE_URL)
public class StoreServiceTypeController {
	
	
	@Autowired
	private IStoreServiceTypeService StoreServiceTypeService;
	
	@Autowired
	private StoreServiceTypeMapper StoreServiceTypeMapper;
	
	//:::::::::::::::::::StoreServiceType CRUD operation
	
	@GetMapping(value = WebConstantUrl.GET_ALL_STORE_TYPE_SERVICE)
	@ResponseBody
	public ResponseEntity<?> getAllStoreServiceType(
			@RequestParam(required=false) String storeServiceTypeName,
			@RequestParam(required=false) String slaNo,
			Pageable pageable)
	{
		
		Map<String,Object> map = new HashMap<>();
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		List<StoreServiceTypeVO> StoreServiceTypeVos = new ArrayList<StoreServiceTypeVO>(0);
		Page<StoreServiceType> StoreServiceTypes = StoreServiceTypeService.getAllStoreServiceType(storeServiceTypeName,slaNo,page);
		if(StoreServiceTypes.getContent().size() == 0) {
			map.put("StoreServiceTypeVos", StoreServiceTypeVos);
			map.put("total_pages", StoreServiceTypes.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", StoreServiceTypes.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			StoreServiceTypes.forEach(StoreServiceType -> {
				StoreServiceTypeVos.add(StoreServiceTypeMapper.getVoFromEntity(StoreServiceType));
			});
		}
		map.put("StoreServiceTypeVos", StoreServiceTypeVos);
		map.put("total_pages", StoreServiceTypes.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", StoreServiceTypes.getTotalElements());
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_STORE_TYPE_SERVICE_BY_ID)
	@ResponseBody
	public ResponseEntity<StoreServiceTypeVO> getStoreServiceTypeById(@PathVariable Long id)
	{
		StoreServiceType StoreServiceType = StoreServiceTypeService.getStoreServiceTypeById(id);
		
		StoreServiceTypeVO StoreServiceTypeVO = new StoreServiceTypeVO();
		
		BeanUtils.copyProperties(StoreServiceType, StoreServiceTypeVO);
		if (StoreServiceType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreServiceTypeVO>(StoreServiceTypeVO, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.SAVE_STORE_TYPE_SERVICE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveBrand(@RequestBody StoreServiceTypeVO StoreServiceTypeVo) {
		Map<String, Object> map = new HashMap<>();
		boolean StoreServiceTypeNameStatus = StoreServiceTypeService.checkIfStoreServiceTypeNameIsExit(StoreServiceTypeVo.getStoreServiceTypeName());
		if (StoreServiceTypeNameStatus) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error_msg", "StoreServiceType Name: " + StoreServiceTypeVo.getStoreServiceTypeName()
					+ " is already present. So Duplicate entry for 'StoreServiceType Name' is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			StoreServiceTypeVo = StoreServiceTypeMapper.getVoFromEntity(StoreServiceTypeService.saveStoreServiceType(StoreServiceTypeMapper.getEntityFromVo(StoreServiceTypeVo)));
			map.put("status code", 201);
			map.put("sucess_msg", "StoreServiceType created sucessfully.");
			map.put("StoreServiceTypeVo", StoreServiceTypeVo);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}
	@PutMapping(value = WebConstantUrl.UPDATE_STORE_TYPE_SERVICE)
	@ResponseBody
	public ResponseEntity<StoreServiceTypeVO> updateStoreServiceType(@RequestBody StoreServiceTypeVO StoreServiceTypeVo)
	{
		StoreServiceType StoreServiceType = StoreServiceTypeMapper.getEntityFromVo(StoreServiceTypeVo);
		StoreServiceType = StoreServiceTypeService.updateStoreServiceType(StoreServiceType);
		if (StoreServiceType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreServiceTypeVO>(StoreServiceTypeMapper.getVoFromEntity(StoreServiceType), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_STORE_TYPE_SERVICE_BY_ID)
	@ResponseBody
	public void deleteStoreServiceTypeById(@PathVariable Long id)
	{
		StoreServiceTypeService.deleteStoreServiceTypeById(id);
	}

	
	

}
