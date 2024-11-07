package com.titan.irgs.master.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.titan.irgs.master.domain.StoreEngineer;
import com.titan.irgs.master.mapper.StoreEngineerMapper;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.service.IStoreEngineerService;
import com.titan.irgs.master.vo.StoreEngineerVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping(value = WebConstantUrl.STORE_ENGINEER_BASE_URL)
public class StoreEngineerController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	StoreEngineerMapper storeEngineerMapper;
	
	@Autowired
	IStoreEngineerService storeEngineerService;
	
	@Autowired
	ClusterRepository clusterRepository;
	
	private static final String superadmin = "superadmin";
	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String Store = "STORE";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@PostMapping(WebConstantUrl.SAVE_STORE_ENGINEER)
	@ResponseBody
	public ResponseEntity<StoreEngineerVO> saveInventory(@RequestBody StoreEngineerVO storeEngineerVO) {

		StoreEngineer storeEngineer = storeEngineerMapper.getEntityFromVo(storeEngineerVO);

		storeEngineer = storeEngineerService.saveInventory(storeEngineer);

		if (storeEngineer == null) {
			return new ResponseEntity<StoreEngineerVO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreEngineerVO>(storeEngineerMapper.getVoFromEntity(storeEngineer), HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = WebConstantUrl.UPDATE_STORE_ENGINEER)
	@ResponseBody
	public ResponseEntity<StoreEngineerVO> updateInventory(@RequestBody StoreEngineerVO storeEngineerVO) {

		StoreEngineer storeEngineer = storeEngineerMapper.getEntityFromVo(storeEngineerVO);
		storeEngineer = storeEngineerService.updateInventory(storeEngineer);
		if (storeEngineer == null) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StoreEngineerVO>(storeEngineerMapper.getVoFromEntity(storeEngineer), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_STORE_ENGINEER_BY_ID)
	public void deleteStoreEngineerById(@PathVariable(value = "id") Long id) {

		storeEngineerService.deleteStoreEngineerById(id);
	}
	
	@GetMapping(WebConstantUrl.GET_STORE_ENGINEER_BY_ID)
	public ResponseEntity<?> getStoreEngineerById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		StoreEngineer storeEngineer = storeEngineerService.getStoreEngineerById(id);

		StoreEngineerVO storeEngineerVO = storeEngineerMapper.getVoFromEntity(storeEngineer);

		return new ResponseEntity<>(storeEngineerVO, HttpStatus.OK);

	}
	
	@SuppressWarnings("static-access")
	@GetMapping(value = WebConstantUrl.GET_ALL_STORE_ENGINEER)
	@ResponseBody
	public ResponseEntity<?> getAllStoreEngineer(
			@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String businessVerticalTypeName1,
			@RequestParam(required = false) String storeCode,@RequestParam(required = false) List<Long> regions,
			@RequestParam(required = false) Long storeId,
			@RequestParam(required = false) String engineerName,
			@RequestParam(required = false) String engineerCode,
			@RequestParam(required = false) String emailId,
			@RequestParam(required = false) String mobileNo,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String region,
			Pageable pageable, Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();

		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Store)) {

				storeCode = user.getUsername();
			} else
				businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

		else if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {

			businessVerticalTypeName = user.getRole().getWebMaster().getWebMasterName();

		}

	/*	if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) {
			// store name and username are same
			vendorCode = user.getUsername();

		}
	*/
		List<StoreEngineerVO> storeEngineerVOs = new ArrayList<>();
		Page<StoreEngineer> storeEngineers = storeEngineerService.getAllStoreEngineer(businessVerticalTypeName,businessVerticalTypeName1,storeCode, regions,storeId, engineerName,engineerCode,emailId,mobileNo, status,region,page);

		if (storeEngineers.getContent().size() == 0) {
			map.put("storeEngineerVOs", storeEngineerVOs);
			map.put("total_pages", storeEngineers.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", storeEngineers.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);

		} else {
			storeEngineers.forEach(storeEngineer -> {	
					
				
				storeEngineerVOs.add(storeEngineerMapper.getVoFromEntity(storeEngineer));
				
			});

			map.put("storeEngineerVOs", storeEngineerVOs);
			map.put("total_pages", storeEngineers.getTotalPages());
			map.put("status_code", HttpStatus.OK);
			map.put("total_records", storeEngineers.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

}
