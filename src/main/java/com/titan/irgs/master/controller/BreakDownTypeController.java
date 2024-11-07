//package com.titan.irgs.master.controller;
//
//import java.io.IOException;
//import java.security.Principal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.titan.irgs.WebConstantUrl.WebConstantUrl;
//import com.titan.irgs.application.util.ExcelExporter;
//import com.titan.irgs.application.util.MasterHeaders;
//import com.titan.irgs.master.domain.BreakDownTypem;
//import com.titan.irgs.master.mapper.BreakDownTypeMapper;
//import com.titan.irgs.master.repository.BreakDownTypeRepository;
//import com.titan.irgs.master.service.IBreakDownTypeService;
//import com.titan.irgs.master.vo.BreakDownTypeVO;
//import com.titan.irgs.role.repository.RoleRepository;
//import com.titan.irgs.serviceRequest.domain.BreakDownType;
//import com.titan.irgs.user.domain.User;
//import com.titan.irgs.user.repository.UserRepo;
//import com.titan.irgs.webMaster.domain.WebMaster;
//import com.titan.irgs.webMaster.repository.WebMasterRepo;
//
///**
// * This is BreakDownTypeController class which is responsible for handling all
// * request(CRUD) for BreakDownTypem releted data
// * 
// * @author
// *
// */
//@RestController
//@RequestMapping(value = WebConstantUrl.BREAK_DOWN_TYPE)
//public class BreakDownTypeController {
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private IBreakDownTypeService breakDownTypeService;
//
//	@Autowired
//	private BreakDownTypeMapper breakDownTypeMapper;
//
//	@Autowired
//	private BreakDownTypeRepository breakDownTypeRepository;
//	@Autowired
//	UserRepo userRepo;
//	@Autowired
//	WebMasterRepo webMasterRepo;
//	@Autowired
//	RoleRepository roleRepository;
//
//	// :::::::::::::::::::BreakDownTypem CRUD operation
//	private static final String superadmin = "superadmin";
//
//	private static final String MANAGEMENT = "MANAGEMENT";
//
//	@GetMapping(value = WebConstantUrl.GET_ALL_BREAK_DOWN_TYPE)
//	@ResponseBody
//	public ResponseEntity<?> getAllBreakDownType(@RequestParam(required = false) String breakDownTypeName,
//			@RequestParam(required = false) String breakDownTypeCode, @RequestParam(required = false) String webMasterId,
//			@RequestParam(required = false) String webMasterName,
//			@RequestParam(required=false) String verticalName,Pageable pageable, Principal principal) {
//		Map<String, Object> map = new HashMap<>();
//		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
//				pageable.getPageSize());
//
//		User user = userRepo.findByUsername(principal.getName());
//
////		WebMaster webMaster = webMasterRepo.findByWebMasterName(user.getRole().getWebMaster().getWebMasterName());
//		
////		Role role=roleRepository.findByRoleName(user.getRole().getRole().getRoleName());
//		// setting bussiness verticle with respective login user
//		// BreakDownTypem breakDownType=new BreakDownTypem();
//		if(!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
//			// store name and username are same
//			
//				webMasterName = user.getRole().getWebMaster().getWebMasterName();
//			
//
//
//		}
//
//
//		List<BreakDownTypeVO> breakDownTypeVos = new ArrayList<BreakDownTypeVO>(0);
//		Page<BreakDownType> breakDownTypems = breakDownTypeService.getAllBreakDownType(breakDownTypeName, breakDownTypeCode, webMasterId, webMasterName,verticalName, page);
//
//		if (breakDownTypems.getContent().size() == 0) {
//			map.put("status_code", HttpStatus.NO_CONTENT);
//			map.put("total_records", breakDownTypems.getTotalElements());
//			map.put("total_pages", breakDownTypems.getSize());
//			map.put("breakDownTypeVos", breakDownTypeVos);
//
//			return new ResponseEntity<>(map, HttpStatus.OK);
//		} else {
//			breakDownTypems.forEach(breakDownType -> {
//				breakDownTypeVos.add(breakDownTypeMapper.getVoFromEntity(breakDownType));
//			});
//		}
//		map.put("status_code", HttpStatus.OK);
//		map.put("total_records", breakDownTypems.getTotalElements());
//		map.put("total_pages", breakDownTypems.getSize());
//		map.put("breakDownTypeVos", breakDownTypeVos);
//		return new ResponseEntity<>(map, HttpStatus.OK);
//	}
//
//	@GetMapping(value = WebConstantUrl.GET_BREAK_DOWN_TYPE_BY_ID)
//	@ResponseBody
//	public ResponseEntity<BreakDownTypeVO> getBreakDownTypeById(@PathVariable Long breakDownId) {
//		BreakDownType breakDownTypem = breakDownTypeService.getBreakDownTypeById(breakDownId);
//		if (breakDownTypem == null) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<BreakDownTypeVO>(breakDownTypeMapper.getVoFromEntity(breakDownTypem), HttpStatus.OK);
//	}
//
//	@PostMapping(WebConstantUrl.SAVE_BREAK_DOWN_TYPE)
//	@ResponseBody
//	public ResponseEntity<?> saveBreakDownType(@RequestBody BreakDownTypeVO breakDownTypeVo) {
//		Map<String, Object> map = new HashMap<>();
//		BreakDownType breakDownTypem = breakDownTypeMapper.getEntityFromVo(breakDownTypeVo);
//
//		BreakDownType b = breakDownTypeRepository.findByBreakdownNameAndWebMasterWebMasterId(breakDownTypeVo.getBreakdownName(),breakDownTypeVo.getWebMasterId());
//
//		if (b != null ) {
//		    map.put("status code", 400);
//		    map.put("client status", "Bad Request");
//		    map.put("error msg",
//		            "BreakDownTypem with given BreakDownTypeCode is already present, Please change BreakDownTypeCode.");
//		    return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
//		}
//
//		breakDownTypem = breakDownTypeService.saveBreakDownType(breakDownTypem);
//
//		if (breakDownTypem == null) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//
//		return new ResponseEntity<BreakDownTypeVO>(breakDownTypeMapper.getVoFromEntity(breakDownTypem), HttpStatus.CREATED);
//	}
//
//	@PutMapping(value = WebConstantUrl.UPDATE_BREAK_DOWN_TYPE)
//	@ResponseBody
//	public ResponseEntity<BreakDownTypeVO> updateBreakDownType(@RequestBody BreakDownTypeVO breakDownTypeVo) {
//		BreakDownType breakDownTypem = breakDownTypeMapper.getEntityFromVo(breakDownTypeVo);
//		breakDownTypem = breakDownTypeService.updateBreakDownType(breakDownTypem);
//		if (breakDownTypem == null) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<BreakDownTypeVO>(breakDownTypeMapper.getVoFromEntity(breakDownTypem), HttpStatus.OK);
//	}
//
//	@DeleteMapping(value = WebConstantUrl.DELETE_BREAK_DOWN_TYPE_BY_ID)
//	@ResponseBody
//	public void deleteBreakDownTypeById(@PathVariable Long breakDownId) {
//		breakDownTypeService.deleteBreakDownTypeById(breakDownId);
//	}
//
//	/*
//	 * @GetMapping(value=WebUrlConstants.DOWNLOAD_SAMPLE_BRAND_EXCELSHEET) public
//	 * ResponseEntity<byte[]> downloadSampleExcelSheet() { File resource = null; try
//	 * { resource = new
//	 * File(getClass().getClassLoader().getResource("BreakDownType_ExcelSheet_sample.xlsx").
//	 * getFile());
//	 * 
//	 * HttpHeaders responseHeaders = new HttpHeaders();
//	 * responseHeaders.setContentDispositionFormData("Content-Disposition",resource.
//	 * getName()); responseHeaders.setContentType(new MediaType("text", "xlsx",
//	 * Charset.forName("utf-8"))); return new
//	 * ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus
//	 * .OK);
//	 * 
//	 * } catch (Exception e) { e.printStackTrace();
//	 * logger.error("BRAND_xlsx_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e); }
//	 * 
//	 * return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
//	 */
//
//	@SuppressWarnings("unlikely-arg-type")
//	@GetMapping("/exportExcel/{id}")
//	public void exportToExcel(HttpServletResponse response,@PathVariable("id")Long id) throws IOException {
//		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//		String currentDateTime = dateFormatter.format(new Date());
//
//		String headerKey = "Content-Disposition";
//		String headerValue = "attachment; filename=breakDownType.xlsx";
//		response.setHeader(headerKey, headerValue);
//
//		MasterHeaders masterHeaders = new MasterHeaders();
//		List<Object[]> breakDownTypes = null;
//		WebMaster name=webMasterRepo.findByWebMasterId(id);
//		if(id !=18) {
//		 breakDownTypes = breakDownTypeService.getAll(id);
//		}else {
//			 breakDownTypes = breakDownTypeService.getAll();
//		}
//		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.breakDownTypes, breakDownTypes);
//
//		excelExporter.export(response);
//	}
//	
//	@GetMapping(value = WebConstantUrl.GET_BREAK_DOWN_TYPE_BY_WEBMASTER_ID)
//	@ResponseBody
//	public ResponseEntity<?> getBreakDownTypeByWebMasterId(@PathVariable(value = "id") Long id) {
//		
//		
//		List<BreakDownType> breakDownTypem = breakDownTypeService.getBreakDownTypeByWebMasterId(id);
//		
//		if (breakDownTypem.isEmpty()) {
//			
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//		
//		List<BreakDownTypeVO> amcinventoryVOs=breakDownTypem.stream().map(breakDownTypeMapper::getVoFromEntity).collect(Collectors.toList());
//		
//		return new ResponseEntity<>(amcinventoryVOs, HttpStatus.OK);
//	}
//
//}
