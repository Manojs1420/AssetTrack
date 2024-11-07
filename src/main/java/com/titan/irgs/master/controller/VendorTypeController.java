package com.titan.irgs.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.titan.irgs.master.domain.VendorType;
import com.titan.irgs.master.mapper.VendorTypeMapper;
import com.titan.irgs.master.service.IVendorTypeService;
import com.titan.irgs.master.vo.VendorTypeVO;

/**
 * This is VendorTypeController class which is responsible for handling all
 * request(CRUD) for VendorType releted data
 * 
 * @author 
 *
 */

@RestController
@RequestMapping(value = WebConstantUrl.VENDOR_TYPE_BASE_URL)
public class VendorTypeController {

	@Autowired
	private IVendorTypeService vendorTypeService;

	@Autowired
	private VendorTypeMapper vendorTypeMapper;
	
	// ::::::::::::::Vendor Type CRUD operation---------------

	@GetMapping(value = WebConstantUrl.GET_ALL_VENDOR_TYPE)
	@ResponseBody
	public ResponseEntity<List<VendorTypeVO>> getAllVendorType() {
		List<VendorTypeVO> vendorTypeVOs = new ArrayList<VendorTypeVO>(0);
		List<VendorType> vendorTypes = vendorTypeService.getAllVendorType();
		if (vendorTypes.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			vendorTypes.forEach(vendorType -> {
				vendorTypeVOs.add(vendorTypeMapper.getVoFromEntity(vendorType));
			});

			return new ResponseEntity<List<VendorTypeVO>>(vendorTypeVOs, HttpStatus.OK);
		}
	}

	@GetMapping(value = WebConstantUrl.GET_VENDOR_TYPE_BY_ID)
	@ResponseBody
	public ResponseEntity<VendorTypeVO> getVendorTypeById(@PathVariable Long vendorTypeId) {
		VendorType vendorType = vendorTypeService.getVendorTypeById(vendorTypeId);
		if (vendorType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<VendorTypeVO>(vendorTypeMapper.getVoFromEntity(vendorType), HttpStatus.OK);

	}

	@PostMapping(value = WebConstantUrl.SAVE_VENDOR_TYPE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addArea(@RequestBody VendorTypeVO vendorTypeVo) {
		Map<String, Object> map = new HashMap<>();
		boolean vendorTypeNameStatus = vendorTypeService.checkIfVendorTypeNameIsExit(vendorTypeVo.getVendorTypeName());
		if (vendorTypeNameStatus) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error_msg", "VendorType Name: " + vendorTypeVo.getVendorTypeName()
					+ " is already present. So Duplicate entry for 'VendorType Name' is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			vendorTypeVo = vendorTypeMapper.getVoFromEntity(vendorTypeService.saveVendorType(vendorTypeMapper.getEntityFromVo(vendorTypeVo)));
			map.put("status code", 201);
			map.put("sucess msg", "VendorType created sucessfully.");
			map.put("vendorTypeVo", vendorTypeVo);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}

	@PutMapping(value = WebConstantUrl.UPDATE_VENDOR_TYPE)
	@ResponseBody
	public ResponseEntity<VendorTypeVO> updateVendorType(@RequestBody VendorTypeVO vendorTypeVo) {
		VendorType vendorType = vendorTypeMapper.getEntityFromVo(vendorTypeVo);
		vendorType = vendorTypeService.updateVendorType(vendorType);
		if (vendorType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<VendorTypeVO>(vendorTypeMapper.getVoFromEntity(vendorType), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_VENDOR_TYPE_BY_ID)
	@ResponseBody
	public void deleteVendorTypeById(@PathVariable Long vendorTypeId) {
		vendorTypeService.deleteVendorTypeById(vendorTypeId);
	}
}
