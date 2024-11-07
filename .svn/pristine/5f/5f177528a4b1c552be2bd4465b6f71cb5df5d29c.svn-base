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
import com.titan.irgs.master.domain.StoreAlloted;
import com.titan.irgs.master.mapper.StoreAllotedMapper;
import com.titan.irgs.master.service.IStoreAllotedService;
import com.titan.irgs.master.vo.StoreAllotedVO;


@RestController
@RequestMapping(value = WebConstantUrl.STORE_ALLOTED_BASE_URL)
public class StoreAllotedController {
	
	
	@Autowired
	private IStoreAllotedService StoreAllotedService;
	
	@Autowired
	private StoreAllotedMapper StoreAllotedMapper;
	
	//:::::::::::::::::::StoreAlloted CRUD operation
	
	@GetMapping(value = WebConstantUrl.GET_ALL_STORE_ALLOTED)
	@ResponseBody
	public ResponseEntity<List<StoreAllotedVO>> getAllStoreAlloted()
	{
		List<StoreAllotedVO> StoreAllotedVos = new ArrayList<StoreAllotedVO>(0);
		List<StoreAlloted> StoreAlloteds = StoreAllotedService.getAllStoreAlloted();
		if(StoreAlloteds.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			StoreAlloteds.forEach(StoreAlloted -> {
				StoreAllotedVos.add(StoreAllotedMapper.getVoFromEntity(StoreAlloted));
			});
		}
		return new ResponseEntity<List<StoreAllotedVO>>(StoreAllotedVos, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_STORE_ALLOTED_BY_ID)
	@ResponseBody
	public ResponseEntity<StoreAllotedVO> getStoreAllotedById(@PathVariable Long id)
	{
		StoreAlloted StoreAlloted = StoreAllotedService.getStoreAllotedById(id);
		
		StoreAllotedVO StoreAllotedVO = new StoreAllotedVO();
		
		BeanUtils.copyProperties(StoreAlloted, StoreAllotedVO);
		if (StoreAlloted == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreAllotedVO>(StoreAllotedVO, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.SAVE_STORE_ALLOTED)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveBrand(@RequestBody StoreAllotedVO StoreAllotedVo) {
		Map<String, Object> map = new HashMap<>();
		boolean StoreAllotedNameStatus = StoreAllotedService.checkIfStoreAllotedNameIsExit(StoreAllotedVo.getStoreAllotedType());
		if (StoreAllotedNameStatus) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error_msg", "StoreAlloted Name: " + StoreAllotedVo.getStoreAllotedType()
					+ " is already present. So Duplicate entry for 'StoreAlloted Name' is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			StoreAllotedVo = StoreAllotedMapper.getVoFromEntity(StoreAllotedService.saveStoreAlloted(StoreAllotedMapper.getEntityFromVo(StoreAllotedVo)));
			map.put("status code", 201);
			map.put("sucess_msg", "StoreAlloted created sucessfully.");
			map.put("StoreAllotedVo", StoreAllotedVo);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}
	@PutMapping(value = WebConstantUrl.UPDATE_STORE_ALLOTED)
	@ResponseBody
	public ResponseEntity<StoreAllotedVO> updateStoreAlloted(@RequestBody StoreAllotedVO StoreAllotedVo)
	{
		StoreAlloted StoreAlloted = StoreAllotedMapper.getEntityFromVo(StoreAllotedVo);
		StoreAlloted = StoreAllotedService.updateStoreAlloted(StoreAlloted);
		if (StoreAlloted == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreAllotedVO>(StoreAllotedMapper.getVoFromEntity(StoreAlloted), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_STORE_ALLOTED_BY_ID)
	@ResponseBody
	public void deleteStoreAllotedById(@PathVariable Long id)
	{
		StoreAllotedService.deleteStoreAllotedById(id);
	}


}
