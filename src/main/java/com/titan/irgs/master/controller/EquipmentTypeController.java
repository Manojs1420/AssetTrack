package com.titan.irgs.master.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.mapper.EquipmentTypeMapper;
import com.titan.irgs.master.service.IEquipmentTypeService;
import com.titan.irgs.master.vo.EquipmentTypeVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@RestController
@RequestMapping(value = WebConstantUrl.EQUIPMENT_TYPE_BASE_URL)

public class EquipmentTypeController {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IEquipmentTypeService EquipmentTypeService;
	
	@Autowired
	private EquipmentTypeMapper EquipmentTypeMapper;
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	WebMasterRepo webMasterRepo;
	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";
	
	@GetMapping(value = WebConstantUrl.GET_ALL_EQUIPMENT_TYPE)
	@ResponseBody
	public ResponseEntity<?> getAllEquipmentType(@RequestParam(required=false) String equipmentType,
			@RequestParam(required = false) String webMasterId,
			@RequestParam(required = false) String webMasterName,
						 Pageable pageable,Principal principal) {
		
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map = new HashMap<>();
		User user = userRepo.findByUsername(principal.getName());


		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
			


		}
		List<EquipmentTypeVO> EquipmentTypeVOs = new ArrayList<EquipmentTypeVO>(0);
		Page<EquipmentType> EquipmentTypes = EquipmentTypeService.getAllEquipmentType(equipmentType,webMasterId,webMasterName,page);
		if(EquipmentTypes.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", EquipmentTypes.getTotalElements());
			map.put("total_pages",EquipmentTypes.getSize());
			map.put("EquipmentTypeVOs", EquipmentTypeVOs);
			
			return new ResponseEntity<>(map,HttpStatus.OK);
			
		}

		 else {
			EquipmentTypes.forEach(EquipmentType -> {
				EquipmentTypeVOs.add(EquipmentTypeMapper.getVoFromEntity(EquipmentType));
			});

			map.put("status_code", HttpStatus.OK);
			map.put("total_records", EquipmentTypes.getTotalElements());
			map.put("total_pages",EquipmentTypes.getTotalPages());
			map.put("EquipmentTypeVOs", EquipmentTypeVOs);
			
			return new ResponseEntity<>(map,HttpStatus.OK);
		}

	}

	@GetMapping(value = WebConstantUrl.GET_EQUIPMENT_TYPE_BY_ID)
	@ResponseBody
	public ResponseEntity<EquipmentTypeVO> getEquipmentTypeById(@PathVariable Long id) {
		EquipmentType EquipmentType = EquipmentTypeService.getEquipmentTypeById(id);
		if (EquipmentType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<EquipmentTypeVO>(EquipmentTypeMapper.getVoFromEntity(EquipmentType), HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.SAVE_EQUIPMENT_TYPE)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> addEquipmentType(@RequestBody EquipmentTypeVO EquipmentTypeVo) {
		Map<String,Object> map = new HashMap<>();
	    boolean EquipmentTypeCodeStatus = EquipmentTypeService.checkIfEquipmentTypeNameIsExit(EquipmentTypeVo.getEquipmentTypeName());
	    
	    if(EquipmentTypeCodeStatus) {
	    	map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "EquipmentType Code: " + EquipmentTypeVo.getEquipmentTypeName() +  " is already present. So Duplicate entry for 'EquipmentType Code'"
					+ " is not allowed.");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	    }   else {
	    	EquipmentTypeVo = EquipmentTypeMapper.getVoFromEntity(EquipmentTypeService.saveEquipmentType(EquipmentTypeMapper.getEntityFromVo(EquipmentTypeVo)));
	    	map.put("status code", 201);
			map.put("sucess msg", " EquipmentType created sucessfully.");
			map.put("EquipmentTypeVo", EquipmentTypeVo);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
	    }
		
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_EQUIPMENT_TYPE)
	@ResponseBody
	public ResponseEntity<EquipmentTypeVO> updateEquipmentType(@RequestBody EquipmentTypeVO EquipmentTypeVo) {
		EquipmentType EquipmentType = EquipmentTypeMapper.getEntityFromVo(EquipmentTypeVo);
		EquipmentType = EquipmentTypeService.updateEquipmentType(EquipmentType);
		if (EquipmentType == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<EquipmentTypeVO>(EquipmentTypeMapper.getVoFromEntity(EquipmentType), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_EQUIPMENT_TYPE_BY_ID)
	@ResponseBody
	public void deleteEquipmentTypeById(@PathVariable Long id) {
		EquipmentTypeService.deleteEquipmentTypeById(id);
	}
	
	@GetMapping("/exportExcel/{id}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("id") Long id) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=equipmentType.xlsx";
        response.setHeader(headerKey, headerValue);
         
         MasterHeaders masterHeaders=new MasterHeaders();
	 		List<Object[]> equipmentTypes = null;
			WebMaster name=webMasterRepo.findByWebMasterId(id);
			if(id !=18) {
				equipmentTypes = EquipmentTypeService.getAllForExcel(id);
			}else {
				equipmentTypes = EquipmentTypeService.getAllForExcel();}
   //     List<Object[]>equipmentTypes=EquipmentTypeService.getAllForExcel();
        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.equipmentType,equipmentTypes);
         
        excelExporter.export(response);    
    }  
	@GetMapping(value = WebConstantUrl.GET_EQUIPMENT_TYPE_BY_VERTICALID)
	@ResponseBody
	public List<EquipmentType> getEquipmentByTypeVerticalId(@PathVariable("id") Long id) {
		return EquipmentTypeService.getEquipmentByTypeVerticalId(id);
	}

}
