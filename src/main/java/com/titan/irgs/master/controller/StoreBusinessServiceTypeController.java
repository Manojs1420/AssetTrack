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
import com.titan.irgs.master.domain.StoreBusinessServiceType;
import com.titan.irgs.master.mapper.StoreBusinessServiceTypeMapper;
import com.titan.irgs.master.service.IStoreBusinessServiceTypeService;
import com.titan.irgs.master.vo.StoreBusinessServiceTypeVO;


@RestController
@RequestMapping(value = WebConstantUrl.STORE_BUSINESS_TYPE_SERVICE_BASE_URL)
public class StoreBusinessServiceTypeController {
	
	@Autowired
	private IStoreBusinessServiceTypeService StoreBusinessServiceTypeService;
	
	@Autowired
	private StoreBusinessServiceTypeMapper StoreBusinessServiceTypeMapper;
	
	//:::::::::::::::::::StoreBusinessServiceType CRUD operation
	
	@GetMapping(value = WebConstantUrl.GET_ALL_STORE_BUSINESS_TYPE_SERVICE)
	@ResponseBody
	public ResponseEntity<?> getAllStoreBusinessServiceType(
    		@RequestParam(required=false) String storeBusinessServiceTypeName,
    		Pageable pageable)
	{
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();
		List<StoreBusinessServiceTypeVO> StoreBusinessServiceTypeVos = new ArrayList<StoreBusinessServiceTypeVO>(0);
		Page<StoreBusinessServiceType> StoreBusinessServiceTypes = StoreBusinessServiceTypeService.getAllStoreBusinessServiceType(storeBusinessServiceTypeName,page);
		if(StoreBusinessServiceTypes.getContent().size() == 0) {
			map.put("StoreBusinessServiceTypeVos", StoreBusinessServiceTypeVos);
			map.put("total_pages", StoreBusinessServiceTypes.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", StoreBusinessServiceTypes.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
			} else {
			StoreBusinessServiceTypes.forEach(StoreBusinessServiceType -> {
				StoreBusinessServiceTypeVos.add(StoreBusinessServiceTypeMapper.getVoFromEntity(StoreBusinessServiceType));
			});
		}
		map.put("StoreBusinessServiceTypeVos", StoreBusinessServiceTypeVos);
		map.put("total_pages", StoreBusinessServiceTypes.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", StoreBusinessServiceTypes.getTotalElements());
		return new ResponseEntity<>(map,HttpStatus.OK);	}
	
	@GetMapping(value = WebConstantUrl.GET_STORE_BUSINESS_TYPE_SERVICE_BY_ID)
	@ResponseBody
	public ResponseEntity<StoreBusinessServiceTypeVO> getStoreBusinessServiceTypeById(@PathVariable Long id)
	{
		StoreBusinessServiceType StoreBusinessServiceType = StoreBusinessServiceTypeService.getStoreBusinessServiceTypeById(id);
		
		StoreBusinessServiceTypeVO StoreBusinessServiceTypeVO = new StoreBusinessServiceTypeVO();
		
		BeanUtils.copyProperties(StoreBusinessServiceType, StoreBusinessServiceTypeVO);
		if (StoreBusinessServiceType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreBusinessServiceTypeVO>(StoreBusinessServiceTypeVO, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.SAVE_STORE_BUSINESS_TYPE_SERVICE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveBrand(@RequestBody StoreBusinessServiceTypeVO StoreBusinessServiceTypeVo) {
		Map<String, Object> map = new HashMap<>();
		boolean StoreBusinessServiceTypeNameStatus = StoreBusinessServiceTypeService.checkIfStoreBusinessServiceTypeNameIsExit(StoreBusinessServiceTypeVo.getStoreBusinessServiceTypeName());
		if (StoreBusinessServiceTypeNameStatus) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error_msg", "StoreBusinessServiceType Name: " + StoreBusinessServiceTypeVo.getStoreBusinessServiceTypeName()
					+ " is already present. So Duplicate entry for 'StoreBusinessServiceType Name' is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			StoreBusinessServiceTypeVo = StoreBusinessServiceTypeMapper.getVoFromEntity(StoreBusinessServiceTypeService.saveStoreBusinessServiceType(StoreBusinessServiceTypeMapper.getEntityFromVo(StoreBusinessServiceTypeVo)));
			map.put("status code", 201);
			map.put("sucess_msg", "StoreBusinessServiceType created sucessfully.");
			map.put("StoreBusinessServiceTypeVo", StoreBusinessServiceTypeVo);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}
	@PutMapping(value = WebConstantUrl.UPDATE_STORE_BUSINESS_TYPE_SERVICE)
	@ResponseBody
	public ResponseEntity<StoreBusinessServiceTypeVO> updateStoreBusinessServiceType(@RequestBody StoreBusinessServiceTypeVO StoreBusinessServiceTypeVo)
	{
		StoreBusinessServiceType StoreBusinessServiceType = StoreBusinessServiceTypeMapper.getEntityFromVo(StoreBusinessServiceTypeVo);
		StoreBusinessServiceType = StoreBusinessServiceTypeService.updateStoreBusinessServiceType(StoreBusinessServiceType);
		if (StoreBusinessServiceType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreBusinessServiceTypeVO>(StoreBusinessServiceTypeMapper.getVoFromEntity(StoreBusinessServiceType), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_STORE_BUSINESS_TYPE_SERVICE_BY_ID)
	@ResponseBody
	public void deleteStoreBusinessServiceTypeById(@PathVariable Long StoreBusinessServiceTypeId)
	{
		StoreBusinessServiceTypeService.deleteStoreBusinessServiceTypeById(StoreBusinessServiceTypeId);
	}


}
