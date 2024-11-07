package com.titan.irgs.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.mapper.OwnerTypeMapper;
import com.titan.irgs.master.service.IOwnerTypeService;
import com.titan.irgs.master.vo.OwnerTypeVO;


@RestController
@RequestMapping(value = WebConstantUrl.OWNER_TYPE_BASE_URL)
public class OwnerTypeController {
	
	@Autowired
	private IOwnerTypeService OwnerTypeService;
	
	@Autowired
	private OwnerTypeMapper OwnerTypeMapper;
	
	//:::::::::::::::::::OwnerType CRUD operation
	
	@GetMapping(value = WebConstantUrl.GET_ALL_OWNER_TYPE)
	@ResponseBody
	public ResponseEntity<List<OwnerTypeVO>> getAllOwnerType()
	{
		List<OwnerTypeVO> OwnerTypeVos = new ArrayList<OwnerTypeVO>(0);
		List<OwnerType> OwnerTypes = OwnerTypeService.getAllOwnerType();
		if(OwnerTypes.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			OwnerTypes.forEach(OwnerType -> {
				OwnerTypeVos.add(OwnerTypeMapper.getVoFromEntity(OwnerType));
			});
		}
		return new ResponseEntity<List<OwnerTypeVO>>(OwnerTypeVos, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_OWNER_TYPE_BY_ID)
	@ResponseBody
	public ResponseEntity<OwnerTypeVO> getOwnerTypeById(@PathVariable Long id)
	{
		OwnerType OwnerType = OwnerTypeService.getOwnerTypeById(id);
		
		OwnerTypeVO OwnerTypeVO = new OwnerTypeVO();
		
		BeanUtils.copyProperties(OwnerType, OwnerTypeVO);
		if (OwnerType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<OwnerTypeVO>(OwnerTypeVO, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.SAVE_OWNER_TYPE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveBrand(@RequestBody OwnerTypeVO OwnerTypeVo) {
		Map<String, Object> map = new HashMap<>();
		boolean OwnerTypeNameStatus = OwnerTypeService.checkIfOwnerTypeNameIsExit(OwnerTypeVo.getOwnerTypeName());
		if (OwnerTypeNameStatus) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error_msg", "OwnerType Name: " + OwnerTypeVo.getOwnerTypeName()
					+ " is already present. So Duplicate entry for 'OwnerType Name' is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			OwnerTypeVo = OwnerTypeMapper.getVoFromEntity(OwnerTypeService.saveOwnerType(OwnerTypeMapper.getEntityFromVo(OwnerTypeVo)));
			map.put("status code", 201);
			map.put("sucess_msg", "OwnerType created sucessfully.");
			map.put("OwnerTypeVo", OwnerTypeVo);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}
	@PutMapping(value = WebConstantUrl.UPDATE_OWNER_TYPE)
	@ResponseBody
	public ResponseEntity<OwnerTypeVO> updateOwnerType(@RequestBody OwnerTypeVO OwnerTypeVo)
	{
		OwnerType OwnerType = OwnerTypeMapper.getEntityFromVo(OwnerTypeVo);
		OwnerType = OwnerTypeService.updateOwnerType(OwnerType);
		if (OwnerType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<OwnerTypeVO>(OwnerTypeMapper.getVoFromEntity(OwnerType), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_OWNER_TYPE_BY_ID)
	@ResponseBody
	public void deleteOwnerTypeById(@PathVariable Long id)
	{
		OwnerTypeService.deleteOwnerTypeById(id);
	}

	

}
