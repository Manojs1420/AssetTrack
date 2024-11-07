package com.titan.irgs.master.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.google.common.io.Files;
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.Equipment;
import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.domain.EquipmentWiseDepartments;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.domain.VendorEquipment;
import com.titan.irgs.master.mapper.EquipmentMapper;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.EquipmentWiseDepartmentsRepo;
import com.titan.irgs.master.repository.VendorEquipmentRepository;
import com.titan.irgs.master.service.IEquipmentService;
import com.titan.irgs.master.service.IEquipmentTypeService;
import com.titan.irgs.master.service.IVendorService;
import com.titan.irgs.master.vo.EquipmentVO;
import com.titan.irgs.master.vo.VendorEquipmentVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

/**
 * This is EquipmentController class which is responsible for handling all request(CRUD) for Equipment releated data
 * @author 
 *
 */
@RestController
@RequestMapping(value = WebConstantUrl.EQUIPMENT_BASE_URL)

public class EquipmentController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IEquipmentService equipmentService;
	
	@Autowired
	private IEquipmentTypeService equipmentTypeService;
	
	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Autowired 
	private IVendorService vendorService;
	
	@Autowired
	private VendorEquipmentRepository vendorEquipmentRepository;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	EquipmentWiseDepartmentsRepo equipmentWiseDepartmentsRepo;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";

	
	//@Autowired
	/*private EquipmentInventoryLogRepository equipmentInventoryLogRepository;
	
	@Autowired
	private EquipmentInventoryDetailRepository equipmentInventoryDetailRepository;
	
	@Autowired
	private EquipmentInventoryDetailService equipmentInventoryDetailService;*/
	
	@GetMapping(value = WebConstantUrl.GET_ALL_EQUIPMENT)
	@ResponseBody
	public ResponseEntity<?> getAllEquipment(
			@RequestParam(required=false) String equipmentName,
			@RequestParam(required=false) String equipmentCode,
			@RequestParam(required=false) String cost,
			@RequestParam(required=false) String equipmentType,
			@RequestParam(required = false) String webMasterId,
			@RequestParam(required = false) String webMasterName,
			@RequestParam(required = false) String vendorCode,

			
			Pageable pageable,Principal principal) {
		
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();
		
		User user=userRepo.findByUsername(principal.getName());
		
		String department=null;
		String user1=null;
		
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if( (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)) && (!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD)) ) {
				user1 = user.getUsername();
			}else {
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}
				

		List<EquipmentVO> equipmentVOs = new ArrayList<EquipmentVO>(0);
		Page<Equipment> equipments = equipmentService.getAllEquipment(equipmentName,equipmentCode,cost,equipmentType,webMasterId, webMasterName,
				vendorCode,department,page);
		
		
		EquipmentVO equipmentVO1= null;
		
		if (equipments.getContent().size() == 0) {
			
			map.put("equipmentVOs", equipmentVOs);
			map.put("total_pages", equipments.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", equipments.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			
				for(Equipment equipment :equipments) {
					equipmentVO1= new EquipmentVO();
				
		    	List<VendorEquipmentVO> vendorEquipmentVOs = new ArrayList<VendorEquipmentVO>();
				List<VendorEquipment> vendorEquipments= vendorEquipmentRepository.findByEquipmentId(equipment.getEquipmentId());
				for(VendorEquipment vendorEquipment: vendorEquipments ) {
					VendorEquipmentVO vendorEquipmentVO = new VendorEquipmentVO();
					BeanUtils.copyProperties(vendorEquipment, vendorEquipmentVO);
					Vendor vendor = vendorService.getVendorById(vendorEquipment.getVendor().getVendorId());
					vendorEquipmentVO.setVendorId(vendor.getVendorId());
					vendorEquipmentVO.setVendorName(vendor.getVendorName());
					vendorEquipmentVO.setVendorCode(vendor.getVendorCode());
					vendorEquipmentVOs.add(vendorEquipmentVO);
				}
                BeanUtils.copyProperties(equipment, equipmentVO1);
		        EquipmentType e =equipmentTypeService.getEquipmentTypeById(equipment.getEquipmentType().getEquipmentTypeId());
		    	equipmentVO1.setEquipmentTypeId(e.getEquipmentTypeId());
		    	equipmentVO1.setEquipmentTypeName(e.getEquipmentTypeName());
				equipmentVO1.setVendorEquipmentInfo(vendorEquipmentVOs);
				equipmentVO1.setWebMasterId(e.getWebMaster().getWebMasterId());
				equipmentVO1.setWebMasterName(e.getWebMaster().getWebMasterName());
			
				List<EquipmentWiseDepartments> equipmentWiseDepartments=equipmentWiseDepartmentsRepo.getByEquipmentId(equipment.getEquipmentId());
				List<String> names = new ArrayList<String>();
				List<Long> ids = new ArrayList<Long>();
				for(EquipmentWiseDepartments equipmentWiseDepartment:equipmentWiseDepartments) {
					if(equipmentWiseDepartment.getDepartment().getDepartmentId()!=null) {
					Department departments=departmentRepo.findByDepartmentId(equipmentWiseDepartment.getDepartment().getDepartmentId());
					
					ids.add(departments.getDepartmentId());
					names.add(departments.getDepartmentName());
					}
				}
				equipmentVO1.setDepartmentIds(ids);
				equipmentVO1.setDepartmentNames(names);
				
				equipmentVOs.add(equipmentVO1);
				
				
			}
				
				map.put("equipmentVOs", equipmentVOs);
				map.put("total_pages", equipments.getTotalPages());
				map.put("status_code",  HttpStatus.OK);
				map.put("total_records", equipments.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@GetMapping(value = WebConstantUrl.GET_EQUIPMENT_BY_ID)
	@ResponseBody
	public ResponseEntity<EquipmentVO> getEquipmentById(@PathVariable Long equipmentId) {
		Equipment equipment = equipmentService.getEquipmentById(equipmentId);
		
		if (equipment == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<VendorEquipmentVO> vendorEquipmentVOs = new ArrayList<VendorEquipmentVO>();
		List<VendorEquipment> vendorEquipments= vendorEquipmentRepository.findByEquipmentId(equipment.getEquipmentId());
		for(VendorEquipment vendorEquipment: vendorEquipments ) {
			VendorEquipmentVO vendorEquipmentVO = new VendorEquipmentVO();
			BeanUtils.copyProperties(vendorEquipment, vendorEquipmentVO);
			Vendor vendor = vendorService.getVendorById(vendorEquipment.getVendor().getVendorId());
			vendorEquipmentVO.setVendorId(vendor.getVendorId());
			vendorEquipmentVO.setVendorName(vendor.getVendorName());
			vendorEquipmentVO.setVendorCode(vendor.getVendorCode());

			vendorEquipmentVOs.add(vendorEquipmentVO);
		}
		EquipmentVO equipmentVO= new EquipmentVO();
        BeanUtils.copyProperties(equipment, equipmentVO);
		Equipment equipments = equipmentService.getEquipmentById(equipmentId);
		equipmentVO.setEquipmentCode(equipments.getEquipmentCode());
		equipmentVO.setEquipmentCost(equipments.getEquipmentCost());
		equipmentVO.setEquipmentName(equipments.getEquipmentName());
		equipmentVO.setEquipmentTypeName(equipments.getEquipmentType().getEquipmentTypeName());
        EquipmentType e =equipmentTypeService.getEquipmentTypeById(equipment.getEquipmentType().getEquipmentTypeId());
    	equipmentVO.setEquipmentTypeId(e.getEquipmentTypeId());
    	equipmentVO.setEquipmentTypeName(e.getEquipmentTypeName());
		equipmentVO.setVendorEquipmentInfo(vendorEquipmentVOs);
		equipmentVO.setWebMasterId(e.getWebMaster().getWebMasterId());
		
		List<EquipmentWiseDepartments> equipmentWiseDepartments=equipmentWiseDepartmentsRepo.getByEquipmentId(equipmentId);
		List<String> names = new ArrayList<String>();
		List<Long> ids = new ArrayList<Long>();
		for(EquipmentWiseDepartments equipmentWiseDepartment:equipmentWiseDepartments) {
			if(equipmentWiseDepartment.getDepartment().getDepartmentId()!=null) {
			Department departments=departmentRepo.findByDepartmentId(equipmentWiseDepartment.getDepartment().getDepartmentId());
			
			ids.add(departments.getDepartmentId());
			names.add(departments.getDepartmentName());
			}
		}
		equipmentVO.setDepartmentIds(ids);
		equipmentVO.setDepartmentNames(names);
		
		
		return new ResponseEntity<EquipmentVO>(equipmentVO, HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.SAVE_EQUIPMENT)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> addEquipment(@RequestBody EquipmentVO equipmentVo) {
		Map<String,Object> map = new HashMap<>();
	    boolean equipmentCodeStatus = equipmentService.checkIfEquipmentCodeIsExit(equipmentVo.getEquipmentCode());
	    
	  
	    
	    if(equipmentCodeStatus) {
	    	map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Equipment Code: " + equipmentVo.getEquipmentCode() +  " is already present. So Duplicate entry for 'Equipment Code'"
					+ " is not allowed.");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	    }   else {
	    	
	    	Equipment equipment = new Equipment();
	    	
	    	BeanUtils.copyProperties(equipmentVo, equipment);
	    	equipment.setEquipmentType(equipmentTypeService.getEquipmentTypeById(equipmentVo.getEquipmentTypeId()));
	    	equipment.setWebMaster(webMasterService.getById(equipmentVo.getWebMasterId()));
	    	equipment = equipmentService.saveEquipment(equipment);
	    	
	    	EquipmentVO equipmentVO2 = new EquipmentVO();
	    	
	        BeanUtils.copyProperties(equipment, equipmentVO2);
	    	equipmentVO2.setEquipmentId(equipment.getEquipmentId());
	    	equipmentVO2.setEquipmentTypeId(equipmentVo.getEquipmentTypeId());
	    	EquipmentType e =equipmentTypeService.getEquipmentTypeById(equipmentVo.getEquipmentTypeId());
	    	equipmentVO2.setEquipmentTypeName(e.getEquipmentTypeName());
	    	
	    	
	    	List<VendorEquipmentVO> vendorEquipmentVOs = new ArrayList<VendorEquipmentVO>();
	    	if(!equipmentVo.getVendorEquipmentInfo().isEmpty()) {
	    	for(VendorEquipmentVO vendorEquipmentInfoVO : equipmentVo.getVendorEquipmentInfo()) {
	    		
	    		VendorEquipment vendorEquipment = new VendorEquipment();
	    		
	    		if(vendorEquipmentInfoVO.getVendorId() !=0) {
	    		
	    		Vendor vendor = vendorService.getVendorById(vendorEquipmentInfoVO.getVendorId());
	    		if(vendor != null) {
	    			
	    	    vendorEquipment.setEquipmentId(equipment.getEquipmentId());
	    		vendorEquipment.setVendor(vendor);
	    		VendorEquipmentVO vendorEquipmentVO = new VendorEquipmentVO();
	    		
	    		vendorEquipmentVO.setVendorId(vendor.getVendorId());
	    		vendorEquipmentVO.setVendorName(vendor.getVendorName());
	    		vendorEquipmentRepository.save(vendorEquipment);
	    		
	    		
	    		vendorEquipmentVOs.add(vendorEquipmentVO);
	    		
	    		}
	    		}
	    	  }
	    	}
	    	equipmentVO2.setVendorEquipmentInfo(vendorEquipmentVOs);
	    	
			if(equipmentVo.getDepartmentIds()!=null) {
				List<EquipmentWiseDepartments> equipmentWiseDepartments=new ArrayList<>();
				for(Long departmentIds:equipmentVo.getDepartmentIds()) {
					Department department=departmentRepo.findByDepartmentId(departmentIds);

					EquipmentWiseDepartments equipmentWiseDepartment=new EquipmentWiseDepartments();
					equipmentWiseDepartment.setCreatedOn(new Date());
					equipmentWiseDepartment.setDepartment(department);
					equipmentWiseDepartment.setEquipment(equipment);
					equipmentWiseDepartment=equipmentWiseDepartmentsRepo.save(equipmentWiseDepartment);
					equipmentWiseDepartments.add(equipmentWiseDepartment);
				}
				equipmentVo.setEquipmentWiseDepartments(equipmentWiseDepartments);
			}
	    	
	    	map.put("status code", 201);
			map.put("sucess msg", " Equipment created sucessfully.");
			map.put("equipmentVo", equipmentVO2);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
	    }
		
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_EQUIPMENT)
	@ResponseBody
	public ResponseEntity<EquipmentVO> updateEquipment(@RequestBody EquipmentVO equipmentVo) {
		Equipment equipment = equipmentMapper.getEntityFromVo(equipmentVo);
		equipment = equipmentService.updateEquipment(equipment);
		if (equipment == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		    EquipmentVO equipmentVO2 = new EquipmentVO();
	        BeanUtils.copyProperties(equipment, equipmentVO2);
	    	equipmentVO2.setEquipmentId(equipment.getEquipmentId());
	    	equipmentVO2.setEquipmentTypeId(equipmentVo.getEquipmentTypeId());
			Equipment equipments = equipmentService.getEquipmentById(equipmentVo.getEquipmentId());

	    	EquipmentType e =equipmentTypeService.getEquipmentTypeById(equipmentVo.getEquipmentTypeId());
	    	equipmentVO2.setEquipmentTypeName(e.getEquipmentTypeName());
	    	equipmentVO2.setWebMasterId(e.getWebMaster().getWebMasterId());

	    	
	    	List<VendorEquipmentVO> vendorEquipmentVOs = new ArrayList<VendorEquipmentVO>();
	   
	    if(!equipmentVo.getVendorEquipmentInfo().isEmpty()) {
	    	
	    	List<Long> vendorIds=equipmentVo.getVendorEquipmentInfo().stream().map(f->f.getVendorId()).collect(Collectors.toList());
	    	
	    	vendorEquipmentRepository.deleteVendors(vendorIds,equipmentVo.getEquipmentId());
	    	
	    	for(VendorEquipmentVO vendorEquipmentInfoVO : equipmentVo.getVendorEquipmentInfo()) {
	    		
	    		VendorEquipment vendorEquipment = vendorEquipmentRepository
	    									.findByEquipmentIdAndVendorVendorId(equipmentVo.getEquipmentId(), vendorEquipmentInfoVO.getVendorId());
	    		if(vendorEquipment != null) {
	    			
	    			if(vendorEquipmentInfoVO.getVendorId() !=0) {
	    			Vendor vendor = vendorService.getVendorById(vendorEquipmentInfoVO.getVendorId());
		    		if(vendor != null) {
		    		
		    	    vendorEquipment.setEquipmentId(equipment.getEquipmentId());
		    		vendorEquipment.setVendor(vendor);
		    		VendorEquipmentVO vendorEquipmentVO = new VendorEquipmentVO();
		    		vendorEquipmentVO.setVendorEquipmentId(vendorEquipment.getVendorEquipmentId());
		    		vendorEquipmentVO.setVendorId(vendor.getVendorId());
		    		vendorEquipmentVO.setVendorName(vendor.getVendorName());
		    		vendorEquipmentRepository.save(vendorEquipment);
		    		vendorEquipmentVOs.add(vendorEquipmentVO);
	    		}
	    		
	    	    }
	    		
	    		
	    		}
	    		else {
	    			vendorEquipment = new VendorEquipment();
	    			if(vendorEquipmentInfoVO.getVendorId() !=0) {
	    			Vendor vendor = vendorService.getVendorById(vendorEquipmentInfoVO.getVendorId());
		    		if(vendor != null) {
		    		
		    	    vendorEquipment.setEquipmentId(equipment.getEquipmentId());
		    		vendorEquipment.setVendor(vendor);
		    		VendorEquipmentVO vendorEquipmentVO = new VendorEquipmentVO();
		    		vendorEquipmentVO.setVendorEquipmentId(vendorEquipment.getVendorEquipmentId());
		    		vendorEquipmentVO.setVendorId(vendor.getVendorId());
		    		vendorEquipmentVO.setVendorName(vendor.getVendorName());
		    		vendorEquipment=vendorEquipmentRepository.save(vendorEquipment);
		    		
		    		
		    		vendorEquipmentVOs.add(vendorEquipmentVO);
	    		}
	    		}
	    			
	    		}
	    	  }
	    	}
	    else {
	    	
	    	
	    	vendorEquipmentRepository.deleteByEquipmentId(equipmentVo.getEquipmentId());
	    	
	    	
	    }
	    	
	    equipmentVO2.setVendorEquipmentInfo(vendorEquipmentVOs);
		
	    if(equipmentVo.getDepartmentIds()!=null) {
	    	List<Long> departmentIds=equipmentWiseDepartmentsRepo.getAllDepartmentIdsInEquipmentWiseUsingEquipmentId(equipment.getEquipmentId());

	    	if(!departmentIds.isEmpty()) {
	    		List<Long> departmentIdsCopy=new ArrayList<Long>();
	    		departmentIdsCopy.addAll(departmentIds);
	    		departmentIdsCopy.removeAll(equipmentVo.getDepartmentIds());
	    		if(!departmentIdsCopy.isEmpty()) {
	    			for(Long departmentid:departmentIdsCopy) {
	    				equipmentWiseDepartmentsRepo.deleteEquipmentWiseDepartmentsByUsingEquipmentIdAndDepartmentId(equipment.getEquipmentId(),departmentid);
	    			}

	    		}

	    		List<Long> departmentIdsCopy1=new ArrayList<Long>();
	    		departmentIdsCopy1.addAll(equipmentVo.getDepartmentIds());
	    		departmentIdsCopy1.removeAll(departmentIds);
	    		if(!departmentIdsCopy1.isEmpty()) {
	    			for(Long departmentid:departmentIdsCopy1) {
	    				Department department=departmentRepo.findByDepartmentId(departmentid);

	    				EquipmentWiseDepartments equipmentWiseDepartments=new EquipmentWiseDepartments();
	    				equipmentWiseDepartments.setCreatedOn(new Date());
	    				equipmentWiseDepartments.setDepartment(department);
	    				equipmentWiseDepartments.setEquipment(equipments);

	    				equipmentWiseDepartments=equipmentWiseDepartmentsRepo.save(equipmentWiseDepartments);
	    				//roleWiseDepartments.add(roleWiseDepartment);

	    			}
	    		}

	    	}
	    	else {

	    		for(Long departmentId:equipmentVo.getDepartmentIds()) {
	    			Department department=departmentRepo.findByDepartmentId(departmentId);

	    			EquipmentWiseDepartments equipmentWiseDepartments=new EquipmentWiseDepartments();
	    			equipmentWiseDepartments.setCreatedOn(new Date());
	    			equipmentWiseDepartments.setDepartment(department);
	    			equipmentWiseDepartments.setEquipment(equipments);

	    			equipmentWiseDepartments=equipmentWiseDepartmentsRepo.save(equipmentWiseDepartments);
	    		}

	    	}


	    }
	    
		return new ResponseEntity<EquipmentVO>(equipmentVO2, HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_EQUIPMENT_BY_ID)
	@ResponseBody
	public void deleteEquipmentById(@PathVariable Long equipmentId) {
		equipmentService.deleteEquipmentById(equipmentId);
	}

	

	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_EQUIPMENT_EXCELSHEET)
	public ResponseEntity<byte[]> downloadsampleExcelSheet()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("Equipment_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("EQUIPMENT_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
    /*@GetMapping(value = WebUrlConstantUrl.GET_EQUIPMENT_INVENTORY_LOGS_BY_EQUIPMENT_ID)
	@ResponseBody
	public ResponseEntity<List<EquipmentInventoryLogDTO>> getEquipmentInventoryLogOnEquipmentId(@PathVariable(value = "id") Long id) {
		
		List<EquipmentInventoryLogDTO> equipmentInventoryLogDTOs = new ArrayList<EquipmentInventoryLogDTO>();
		
		List<EquipmentInventoryLog> equipmentInventoryLogs = equipmentInventoryLogRepository.findByEquipmentId(id);
		
		try {
			
			if (id == null) {
				return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.NOT_FOUND);
			}
			
			if (equipmentInventoryLogs == null || equipmentInventoryLogs.isEmpty()) {
				
				return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.NO_CONTENT);
			}
			
			equipmentInventoryLogs.forEach(equipmentInventoryLog -> {
	
				EquipmentInventoryDetail equipmentInventoryDetail = equipmentInventoryDetailService.getEquipmentInventoryDetailById(equipmentInventoryLog.getEquipmentInventoryDetailId());
  				
  			     EquipmentInventoryLogDTO equipmentInventoryLogDTO = new EquipmentInventoryLogDTO();
  				
  				Equipment equipment = equipmentService.getEquipmentById(equipmentInventoryLog.getEquipmentId());
  				
  				equipmentInventoryLogDTO.setErNum(equipmentInventoryDetail.getEquipmentSerialNum());
  				
  				equipmentInventoryLogDTO.setEquipmentName(equipment.getEquipmentName());
  				
  				equipmentInventoryLogDTO.setInstallationDate(equipmentInventoryDetail.getInstallationDate());
  				
				equipmentInventoryLogDTOs.add(equipmentInventoryLogDTO);
			});
			return new ResponseEntity<List<EquipmentInventoryLogDTO>>(equipmentInventoryLogDTOs, HttpStatus.OK);

		} catch (Exception e) {
			
			return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    */
    
   /* @GetMapping(value = WebUrlConstants.GET_EQUIPMENT_INVENTORY_LOGS_BY_EQUIPMENT_INVENTORY_DETAIL_ID)
  	@ResponseBody
  	public ResponseEntity<List<EquipmentInventoryLogDTO>> getEquipmentInventoryLogOnEquipmentIneventoryDetailId(@PathVariable(value = "id") Long id) {
  		
  		List<EquipmentInventoryLogDTO> equipmentInventoryLogDTOs = new ArrayList<EquipmentInventoryLogDTO>();
  		
  		List<EquipmentInventoryLog> equipmentInventoryLogs = equipmentInventoryLogRepository.findByEquipmentInventoryDetailId(id);
  		
  		try {
  			
  			if (id == null) {
  				return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.NOT_FOUND);
  			}
  			
  			if (equipmentInventoryLogs == null || equipmentInventoryLogs.isEmpty()) {
  				
  				return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.NO_CONTENT);
  			}
  			
  			equipmentInventoryLogs.forEach(equipmentInventoryLog -> {
  				
  			EquipmentInventoryDetail equipmentInventoryDetail = equipmentInventoryDetailService.getEquipmentInventoryDetailById(equipmentInventoryLog.getEquipmentInventoryDetailId());
  				
  		    EquipmentInventoryLogDTO equipmentInventoryLogDTO = new EquipmentInventoryLogDTO();
  				
  				Equipment equipment = equipmentService.getEquipmentById(equipmentInventoryLog.getEquipmentId());
  				
  				equipmentInventoryLogDTO.setErNum(equipmentInventoryDetail.getEquipmentSerialNum());
  				
  				equipmentInventoryLogDTO.setEquipmentName(equipment.getEquipmentName());
  				
  				equipmentInventoryLogDTO.setInstallationDate(equipmentInventoryDetail.getInstallationDate());
  				
  				BeanUtils.copyProperties(equipmentInventoryLog, equipmentInventoryLogDTO);
  				
  				
  				
  				equipmentInventoryLogDTOs.add(equipmentInventoryLogDTO);
  			});
  			return new ResponseEntity<List<EquipmentInventoryLogDTO>>(equipmentInventoryLogDTOs, HttpStatus.OK);

  		} catch (Exception e) {
  			
  			return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
  		}
  	}*/
    
    
   /* @GetMapping(value = WebUrlConstants.GET_ALL_EQUIPMENT_INVENTORY_LOGS)
   	@ResponseBody
   	public ResponseEntity<List<EquipmentInventoryLogDTO>> getAllEquipmentInventorys() {
   		
   		List<EquipmentInventoryLogDTO> equipmentInventoryLogDTOs = new ArrayList<EquipmentInventoryLogDTO>();
   		
   		List<EquipmentInventoryLog> equipmentInventoryLogs = equipmentInventoryLogRepository.findAll();
   		
   		Comparator<EquipmentInventoryLog> titleComparator = (c1, c2) -> c1.getEquipmentInventoryLogId().compareTo(c2.getEquipmentInventoryLogId()); 
   		
   		equipmentInventoryLogs.sort(Collections.reverseOrder(titleComparator));
   		try {
   			
   		   if (equipmentInventoryLogs == null || equipmentInventoryLogs.isEmpty()) {
   				
   				return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.NO_CONTENT);
   			}
   			
   			equipmentInventoryLogs.forEach(equipmentInventoryLog -> {
   	
   				EquipmentInventoryDetail equipmentInventoryDetail = equipmentInventoryDetailService.getEquipmentInventoryDetailById(equipmentInventoryLog.getEquipmentInventoryDetailId());
     				
     			     EquipmentInventoryLogDTO equipmentInventoryLogDTO = new EquipmentInventoryLogDTO();
     			     if(equipmentInventoryDetail != null) {
     			    	Equipment equipment = equipmentService.getEquipmentById(equipmentInventoryLog.getEquipmentId());
         				if(equipment != null) {
         				equipmentInventoryLogDTO.setEquipmentName(equipment.getEquipmentName());
         				}
         				equipmentInventoryLogDTO.setErNum(equipmentInventoryDetail.getEquipmentSerialNum());
         				
         				
         				
         				equipmentInventoryLogDTO.setInstallationDate(equipmentInventoryDetail.getInstallationDate());
         				
         				equipmentInventoryLogDTO.setEquipmentType(equipment.getEquipmentType());
         				BeanUtils.copyProperties(equipmentInventoryLog, equipmentInventoryLogDTO);
         				
     			     }
     				
     				
     				//BeanUtils.copyProperties(equipmentInventoryLog, equipmentInventoryLogDTO);
     				
   				equipmentInventoryLogDTOs.add(equipmentInventoryLogDTO);
   				
   			 
   			});
   			
   		 
   			return new ResponseEntity<List<EquipmentInventoryLogDTO>>(equipmentInventoryLogDTOs, HttpStatus.OK);

   		} catch (Exception e) {
   			
   			return new ResponseEntity<List<EquipmentInventoryLogDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
   		}
   	}*/
	
	
	@GetMapping("/exportExcel/{id}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("id") Long id) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=equipment.xlsx";
        response.setHeader(headerKey, headerValue);
         
         MasterHeaders masterHeaders=new MasterHeaders();
     //   List<Object[]> equipments=equipmentService.getAllExcel();
        List<Object[]> equipments = null;
		WebMaster name=webMasterRepo.findByWebMasterId(id);
		if(id !=18) {
			equipments = equipmentService.getAllExcel(id);
		}else {
			equipments = equipmentService.getAllExcel();}

        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.equipment,equipments);
         
        excelExporter.export(response);    
    }  
	@GetMapping(value = WebConstantUrl.GET_EQUIPMENT_BY_VERTICALID)
	@ResponseBody
	public List<Equipment> getEquipmentByVerticalId(@PathVariable("id") Long id) {
		return equipmentService.getEquipmentByVerticalId(id);
	}
	
	@GetMapping(value = WebConstantUrl.GET_EQUIPMENT_BY_VERTICALID_AND_DEPARTMENT_ID)
	@ResponseBody
	public List<Equipment> getEquipmentByVerticalIdAndDepartmentId(@RequestParam(required = true) Long verticalId,
			@RequestParam(required = true) Long departmentId) {
		return equipmentService.getEquipmentByVerticalIdAndDepartmentId(verticalId,departmentId);
	}
	
  }

