/*package com.titan.irgs.inventory.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.titan.irgs.inventory.domain.InventoryDetail;
import com.titan.irgs.inventory.mapper.InventoryDetailMapper;
import com.titan.irgs.inventory.service.IInventoryDetailService;
import com.titan.irgs.inventory.vo.InventoryDetailVO;


@RestController
@RequestMapping(value = WebConstantUrl.INVENTORY_DETAIL_BASE_URL)
public class InventoryDetailController {
	
	@Autowired
	private IInventoryDetailService inventoryDetailService;
	
	@Autowired
	private InventoryDetailMapper inventoryDetailMapper;
	
	// :::::::::::: InventoryDetail CRUD operation----------------------------------------------

	@GetMapping(value = WebConstantUrl.GET_ALL_INVENTORY_DETAIL)
	@ResponseBody
	public ResponseEntity<List<InventoryDetailVO>> getAllInventoryDetail() {

		List<InventoryDetailVO> InventoryDetailVOs = new ArrayList<InventoryDetailVO>(0);
		
		List<InventoryDetail> inventories = inventoryDetailService.getAllInventoryDetail();

		if (inventories.size() == 0) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} else {
			
			inventories.forEach(InventoryDetail -> {
				
				InventoryDetailVOs.add(inventoryDetailMapper.getVoFromEntity(InventoryDetail));
			});

			return new ResponseEntity<List<InventoryDetailVO>>(InventoryDetailVOs, HttpStatus.OK);
		}

	}

	@GetMapping(value = WebConstantUrl.GET_INVENTORY_DETAIL_BY_ID)
	@ResponseBody
	public ResponseEntity<InventoryDetailVO> getInventoryDetailById(@PathVariable(value = "id") Long id) {
		
		InventoryDetail inventoryDetail = inventoryDetailService.getInventoryDetailById(id);
		
		if (inventoryDetail == null) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InventoryDetailVO>(inventoryDetailMapper.getVoFromEntity(inventoryDetail), HttpStatus.OK);
	}

	@PostMapping(WebConstantUrl.SAVE_INVENTORY_DETAIL)
	@ResponseBody
	public ResponseEntity<InventoryDetailVO> saveInventoryDetail(@RequestBody InventoryDetailVO inventoryDetailVo) {
		
		InventoryDetail inventoryDetail = inventoryDetailMapper.getEntityFromVo(inventoryDetailVo);

		inventoryDetail = inventoryDetailService.saveInventoryDetail(inventoryDetail);

		if (inventoryDetail == null) {
			return new ResponseEntity<InventoryDetailVO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InventoryDetailVO>(inventoryDetailMapper.getVoFromEntity(inventoryDetail), HttpStatus.CREATED);
	}

	@PutMapping(value = WebConstantUrl.UPDATE_INVENTORY_DETAIL)
	@ResponseBody
	public ResponseEntity<InventoryDetailVO> updateInventoryDetail(@RequestBody InventoryDetailVO inventoryDetailVo) {
		
		InventoryDetail inventoryDetail = inventoryDetailMapper.getEntityFromVo(inventoryDetailVo);
		
		inventoryDetail = inventoryDetailService.updateInventoryDetail(inventoryDetail);
		
		if (inventoryDetail == null) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InventoryDetailVO>(inventoryDetailMapper.getVoFromEntity(inventoryDetail), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_INVENTORY_DETAIL_BY_ID)
	public void deleteInventoryDetailById(@PathVariable(value = "id") Long id) {
		
		inventoryDetailService.deleteInventoryDetailById(id);
	}
	

}
*/