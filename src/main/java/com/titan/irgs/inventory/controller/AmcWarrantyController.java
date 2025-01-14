
package com.titan.irgs.inventory.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.mapper.AmcWarrantyMapper;
import com.titan.irgs.inventory.service.AmcWarrantyService;
import com.titan.irgs.inventory.vo.AmcWarrantyVO;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping(value = WebConstantUrl.AMCWarranty)
public class AmcWarrantyController {

	@Autowired
	AmcWarrantyService amcExtensionService;

	@Autowired
	AmcWarrantyMapper amcExtensionMapper;

	@Autowired
	UserRepo userRepo;

	private static final Logger logger = LoggerFactory.getLogger(AmcInventoryController.class);

	private static final String superadmin = "superAdmin";
	private static final String Vendor = "VENDOR";


	// get By Id from AmcInventory
	@GetMapping(value = WebConstantUrl.GetWarrantyByAmcId)
	@ResponseBody
	public ResponseEntity<?> getWarrantyByAmcId(@PathVariable(value = "id") Long id) {
		
		
		List<AmcWarranty> amcWarranty = amcExtensionService.findWarrantyByAmcId(id);
		
		if (amcWarranty.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<AmcWarrantyVO> amcWarrantyVOs=amcWarranty.stream().map(amcExtensionMapper::getVoFromEntity).collect(Collectors.toList());
		
		return new ResponseEntity<>(amcWarrantyVOs, HttpStatus.OK);
	}	
	
	@GetMapping(value = WebConstantUrl.GetWarrantyByAssetId)
	@ResponseBody
	public ResponseEntity<?> getWarrantyByAssetid(@PathVariable(value = "id") Long id) {
		
		
		List<AmcWarranty> amcWarranty = amcExtensionService.findWarrantyByAssetId(id);
		
		if (amcWarranty.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<AmcWarrantyVO> amcWarrantyVOs=amcWarranty.stream().map(amcExtensionMapper::getVoFromEntity).collect(Collectors.toList());
		
		return new ResponseEntity<>(amcWarrantyVOs, HttpStatus.OK);
	}	
	/*
	 * @PostMapping(value = WebConstantUrl.SaveAmcExtension)
	 * 
	 * @ResponseBody public ResponseEntity<AmcWarrantyVO>
	 * saveAmcExtension(@RequestBody AmcWarrantyVO amcExtensionVO) {
	 * 
	 * AmcWarranty amcExtension =
	 * amcExtensionMapper.getEntityFromVo(amcExtensionVO); amcExtension =
	 * amcExtensionService.saveAmcExtension(amcExtension);
	 * 
	 * return new
	 * ResponseEntity<AmcWarrantyVO>(amcExtensionMapper.getVoFromEntity(amcExtension
	 * ), HttpStatus.CREATED); }
	 */
	/*
	 * @GetMapping(value = WebConstantUrl.GetAllAMCExtension)
	 * 
	 * @ResponseBody public ResponseEntity<?> getAllAmcExtension(
	 * 
	 * @RequestParam(required=false) String vendorCode,
	 * 
	 * @RequestParam(required=false) String businessVerticalName,
	 * 
	 * @RequestParam(required=false) Boolean activeStatus,
	 * 
	 * @RequestParam(required=false) String extendDate,
	 * 
	 * @RequestParam(required=false) String maintainanceDates,
	 * 
	 * @RequestParam(required=false) String maintainancePeriod,
	 * 
	 * @RequestParam(required=false) String maintainanceStartDate,
	 * 
	 * @RequestParam(required=false) String maintainanceEndDate,
	 * 
	 * @RequestParam(required=false) String minMaintainanceGap,
	 * 
	 * 
	 * 
	 * Pageable pageable,Principal principal,HttpServletRequest request) {
	 * 
	 * logger.info("getConfirm: Received request: " +
	 * request.getRequestURL().toString() + ((request.getQueryString() == null) ? ""
	 * : "?" + request.getQueryString().toString()));
	 * 
	 * 
	 * Pageable
	 * page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1,
	 * pageable.getPageSize()); Map<String,Object> map=new HashMap<>();
	 * 
	 * User user=userRepo.findByUsername(principal.getName());
	 * 
	 * //setting bussiness verticle with respective login user
	 * if(!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)) {
	 * businessVerticalName=user.getRole().getWebMaster().getWebMasterName();
	 * 
	 * }
	 * 
	 * if(user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)){ //store
	 * name and username are same vendorCode=user.getUsername();
	 * 
	 * }
	 * 
	 * 
	 * List<AmcWarrantyVO> amcExtensionVOs = new ArrayList<AmcWarrantyVO>();
	 * Page<AmcWarranty> amcExtension =
	 * amcExtensionService.getAllAmcExtension(vendorCode,businessVerticalName,
	 * activeStatus,extendDate, maintainanceDates,maintainancePeriod,
	 * maintainanceStartDate, maintainanceEndDate, minMaintainanceGap,page);
	 * 
	 * if (amcExtension.getContent().size() == 0) { map.put("amcExtensionVOs",
	 * amcExtensionVOs); map.put("total_pages", amcExtension.getTotalPages());
	 * map.put("status_code", HttpStatus.NO_CONTENT); map.put("total_records",
	 * amcExtension.getTotalElements());
	 * 
	 * return new ResponseEntity<>(map,HttpStatus.OK);
	 * 
	 * } else { amcExtension.forEach(amcinventory -> {
	 * 
	 * amcExtensionVOs.add(amcExtensionMapper.getVoFromEntity(amcinventory)); });
	 * 
	 * map.put("amcExtensionVOs", amcExtensionVOs); map.put("total_pages",
	 * amcExtension.getTotalPages()); map.put("status_code", HttpStatus.OK);
	 * map.put("total_records", amcExtension.getTotalElements());
	 * 
	 * return new ResponseEntity<>(map, HttpStatus.OK); }
	 * 
	 * }
	 */

}
