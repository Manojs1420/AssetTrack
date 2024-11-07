package com.titan.irgs.master.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.master.domain.GroupBusinessVertical;
import com.titan.irgs.master.domain.GroupBusinessVerticalMaster;
import com.titan.irgs.master.mapper.GroupBusinessMapper;
import com.titan.irgs.master.mapper.GroupBusinessVerticalMapper;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.GroupBusinessVerticalMasterRepo;
import com.titan.irgs.master.repository.GroupBusinessVerticalRepo;
import com.titan.irgs.master.service.IGroupBusinessVerticalService;
import com.titan.irgs.master.vo.GroupBusinessVerticalMasterVO;
import com.titan.irgs.master.vo.GroupBusinessVerticalVO;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.service.IWebMaster;

@RestController
@RequestMapping(value = WebConstantUrl.GROUP_BUSINESS_VERTICAL_BASE_URL)
public class GroupBusinessVerticalController {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	GroupBusinessVerticalMapper groupBusinessVerticalMapper;
	
	@Autowired
	GroupBusinessMapper groupBusinessMapper;
	
	@Autowired
	IGroupBusinessVerticalService groupBusinessVerticalService;
	
	@Autowired
	ClusterRepository clusterRepository;
	
	@Autowired
	IWebMaster iWebMaster;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	GroupBusinessVerticalRepo groupBusinessVerticalRepo;
	
	@Autowired
	GroupBusinessVerticalMasterRepo groupBusinessVerticalMasterRepo;
	
	private static final String superAdmin = "superadmin";
	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String Store = "STORE";
	private final static String ITAdmin = "ITAdmin";
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(WebConstantUrl.SAVE_GROUP_BUSINESS_VERTICAL)
    public ResponseEntity<?> saveGroupBusinessVertical(@RequestBody GroupBusinessVerticalMasterVO groupBusinessVerticalMasterVO,HttpServletRequest request,Principal principal){
				logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
				
		GroupBusinessVerticalMaster groupBusinessVerticalMaster=null;
	
		groupBusinessVerticalMaster=groupBusinessVerticalMapper.getEntityFromVo(groupBusinessVerticalMasterVO);
		
		
		if (groupBusinessVerticalMaster == null) {
			return new ResponseEntity<GroupBusinessVerticalMasterVO>(HttpStatus.NO_CONTENT);
		}
		
		GroupBusinessVerticalMaster groupName=groupBusinessVerticalMasterRepo.findByGroupName(groupBusinessVerticalMasterVO.getGroupBusinessVerticalName());
		
		if(groupName!=null) {
			Map<String,Object>map=new HashMap<>();
			
			
			map.put("status_code",  HttpStatus.BAD_REQUEST);
			map.put("error_msg", " Group Business name is already present ");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);

		}
		
	
		List<GroupBusinessVerticalVO> verticalIds=groupBusinessVerticalMasterVO.getGroupBusinessVerticalvo();
		for (Iterator iterator = verticalIds.iterator(); iterator.hasNext();) {
			GroupBusinessVerticalVO groupBusinessVerticalVO = (GroupBusinessVerticalVO) iterator.next();
			WebMaster webMaster1=webMasterRepo.findByWebMasterId(groupBusinessVerticalVO.getWebMasterId());
			GroupBusinessVertical verticalid=groupBusinessVerticalRepo.findByWebMaster(webMaster1);
			if(verticalid!=null) {
				Map<String,Object>map=new HashMap<>();
				
				
					map.put("status_code",  HttpStatus.BAD_REQUEST);
					map.put("error_msg", verticalid.getWebMaster().getWebMasterName()+" Vertical Name is already present in the Business Group");
					return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);

			}
			
		}
		
		groupBusinessVerticalMaster=groupBusinessVerticalService.saveGroupBusinessVertical(groupBusinessVerticalMaster);
		 

		for(GroupBusinessVerticalVO  groupBusinessVerticalVO:groupBusinessVerticalMasterVO.getGroupBusinessVerticalvo()) {
			GroupBusinessVertical groupBusinessVertical=new GroupBusinessVertical();
			groupBusinessVertical.setCreatedOn(new Date());
			//groupBusinessVertical.setGroupBusinessVerticalName(groupBusinessVerticalMaster.getGroupBusinessVerticalName());
			
			WebMaster webMaster=webMasterRepo.findByWebMasterId(groupBusinessVerticalVO.getWebMasterId());
				
			groupBusinessVertical.setWebMaster(webMaster);
			groupBusinessVertical.setGroupBusinessVerticalMaster(groupBusinessVerticalMaster);
			groupBusinessVerticalRepo.save(groupBusinessVertical);
			
		}
	   return new ResponseEntity<>(HttpStatus.CREATED);
		
    }
	
	@PutMapping(WebConstantUrl.UPDATE_GROUP_BUSINESS_VERTICAL)
	public ResponseEntity<?> update(@RequestBody GroupBusinessVerticalMasterVO groupBusinessVerticalMasterVO,HttpServletRequest request,Principal principal) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		GroupBusinessVerticalMaster groupBusinessVerticalMaster=groupBusinessVerticalMapper.getEntityFromVo(groupBusinessVerticalMasterVO);
	//	WebMaster webMaster=iWebMaster.getById(backEndApisModel.getVerticalId());

		groupBusinessVerticalMaster=groupBusinessVerticalService.updateGroupBusinessVertical(groupBusinessVerticalMaster);
		
		
		for(GroupBusinessVerticalVO  groupBusinessVerticalVO:groupBusinessVerticalMasterVO.getGroupBusinessVerticalvo()) {
			GroupBusinessVertical groupBusinessVertical=new GroupBusinessVertical();
	
			groupBusinessVertical.setUpdatedOn(new Date());
			groupBusinessVertical.setCreatedOn(groupBusinessVerticalMaster.getCreatedOn());
			
			/*
			GroupBusinessVerticalMaster groupName=groupBusinessVerticalMasterRepo.findByGroupName(groupBusinessVerticalMaster.getGroupBusinessVerticalName());
			
			if(groupName!=null) {
				Map<String,Object>map=new HashMap<>();
				
				
				map.put("status_code",  HttpStatus.BAD_REQUEST);
				map.put("error_msg", " Group Business name is already present ");
				return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);

			}
			*/
		//	groupBusinessVertical.setGroupBusinessVerticalName(groupBusinessVerticalMaster.getGroupBusinessVerticalName());
			WebMaster webMaster=webMasterRepo.findByWebMasterId(groupBusinessVerticalVO.getWebMasterId());
			
			GroupBusinessVertical verticalid=groupBusinessVerticalRepo.findByWebMaster(webMaster);
			if(verticalid!=null) {
					Map<String,Object>map=new HashMap<>();
				
					map.put("status_code",  HttpStatus.BAD_REQUEST);
					map.put("error_msg", verticalid.getWebMaster().getWebMasterName()+" Vertical Name is already present in the Business Group");
					return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);

			}
			groupBusinessVertical.setWebMaster(webMaster);
			
			groupBusinessVertical.setGroupBusinessVerticalMaster(groupBusinessVerticalMaster);
			groupBusinessVerticalRepo.save(groupBusinessVertical);
			
		}
		groupBusinessVerticalMasterRepo.save(groupBusinessVerticalMaster);
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
	/*
	@DeleteMapping(value = WebConstantUrl.DELETE_GROUP_BUSINESS_VERTICAL_BY_ID)
	public void deleteGroupBusinessVerticalById(@PathVariable(value = "id") Long id) {

		groupBusinessVerticalService.deleteGroupBusinessVerticalById(id);
	}
	*/
	@DeleteMapping(WebConstantUrl.DELETE_GROUP_BUSINESS_VERTICAL_BY_ID)
	public ResponseEntity<?> deleteGroupBusinessVerticalById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		GroupBusinessVerticalMaster groupBusinessVerticalMaster = groupBusinessVerticalService.getGroupBusinessVerticalById(id);

		groupBusinessVerticalMasterRepo.deleteById(groupBusinessVerticalMaster.getId());
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@GetMapping(WebConstantUrl.GET_GROUP_BUSINESS_VERTICAL_BY_ID)
	public ResponseEntity<?> getGroupBusinessVerticalById(@PathVariable("id") Long id, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		GroupBusinessVerticalMaster groupBusinessVerticalMaster = groupBusinessVerticalService.getGroupBusinessVerticalById(id);

		List<GroupBusinessVertical> groupBusinessVertical=groupBusinessVerticalRepo.getByID(groupBusinessVerticalMaster.getId());
		List<String> codes = new ArrayList<String>();
		List<Long> ids=new ArrayList<Long>();
			for(int i=0;i<groupBusinessVertical.size();i++) {
				List<WebMaster> webMasters=webMasterRepo.findByWebMasterIds(groupBusinessVertical.get(i).getWebMaster().getWebMasterId());
				
		for (WebMaster webMaster : webMasters) {
			codes.add(webMaster.getWebMasterName());
			ids.add(webMaster.getWebMasterId());
		}
		}
		GroupBusinessVerticalMasterVO groupBusinessVerticalMasterVO=groupBusinessVerticalMapper.getVoFromEntity(groupBusinessVerticalMaster);
		groupBusinessVerticalMasterVO.setWebMasterNames(codes);
		groupBusinessVerticalMasterVO.setWebMasterIds(ids);
		
		//GroupBusinessVerticalMasterVO groupBusinessVerticalMasterVO = groupBusinessVerticalMapper.getVoFromEntity(groupBusinessVerticalMaster);

		return new ResponseEntity<>(groupBusinessVerticalMasterVO, HttpStatus.OK);

	}

	/*
	@GetMapping(WebConstantUrl.GET_ALL_GROUP_BUSINESS_VERTICAL)
    public ResponseEntity<?> getAllGroupBusinessVertical(
    		@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String businessVerticalTypeName1,
			
			@RequestParam(required = false) Long groupBusinessVerticalId,
			@RequestParam(required = false) String groupBusinessVerticalName,
			@RequestParam(required = false) String createdOn,
			
    		Pageable pageable,Principal principal,HttpServletRequest request
    		){
		
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		
	
		
		Map<String,Object>map=new HashMap<>();
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		
		List<GroupBusinessVerticalVO> groupBusinessVerticalVOs=new ArrayList<>();
		Page<GroupBusinessVertical> groupBusinessVertical=groupBusinessVerticalService.getAllGroupBusinessVertical(businessVerticalTypeName,businessVerticalTypeName1, groupBusinessVerticalId,groupBusinessVerticalName,createdOn,page);

		if(groupBusinessVertical.getContent().size()==0) {
			map.put("groupBusinessVerticalVOs", groupBusinessVerticalVOs);
			map.put("total_pages", groupBusinessVertical.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", groupBusinessVertical.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
			}
		/*
		groupBusinessVertical.forEach(groupBusinessVerticalMaster1 -> {	
					List<GroupBusinessVertical> groupBusinessVerticalVO=groupBusinessVerticalRepo.getByID(groupBusinessVerticalMaster1.getId());
						List<String> codes = new ArrayList<String>();
						List<Long> ids = new ArrayList<Long>();
						
						for(int i=0;i<groupBusinessVerticalVO.size();i++) {
						List<WebMaster> webMasters =  webMasterRepo.findByWebMasterIds(groupBusinessVerticalVO.get(i).getWebMaster().getWebMasterId());
						
						for (WebMaster webMaster : webMasters) {
							codes.add(webMaster.getWebMasterName());
							ids.add(webMaster.getWebMasterId());
							}	
						}
						
						GroupBusinessVerticalVO groupBusinessVerticalVO=groupBusinessVerticalMapper.getVoFromEntity(groupBusinessVerticalMaster1);
						groupBusinessVerticalVO.setWebMasterNames(codes);
						groupBusinessVerticalMasterVO.setWebMasterIds(ids);
						groupBusinessVerticalMasterVOs.add(groupBusinessVerticalMasterVO);
						
				});
				*/
/*		
		groupBusinessVerticalVOs=groupBusinessVertical.stream().map(groupBusinessMapper::getVoFromEntity).collect(Collectors.toList());
		map.put("groupBusinessVerticalVOs", groupBusinessVerticalVOs);
		map.put("total_pages", groupBusinessVertical.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", groupBusinessVertical.getTotalElements());

		return new ResponseEntity<>(map,HttpStatus.OK);
    	
    	}
*/	

	@GetMapping(WebConstantUrl.GET_ALL_GROUP_BUSINESS_VERTICAL)
    public ResponseEntity<?> getAllGroupBusinessVertical(
    		@RequestParam(required = false) String businessVerticalTypeName,
			@RequestParam(required = false) String businessVerticalTypeName1,
			
			@RequestParam(required = false) Long groupBusinessVerticalId,
			@RequestParam(required = false) String groupBusinessVerticalName,
			@RequestParam(required = false) String createdOn,
			
    		Pageable pageable,Principal principal,HttpServletRequest request
    		){
		
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		
	
		
		Map<String,Object>map=new HashMap<>();
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		
		List<GroupBusinessVerticalMasterVO> groupBusinessVerticalMasterVOs=new ArrayList<>();
		Page<GroupBusinessVerticalMaster> groupBusinessVerticalMaster=groupBusinessVerticalService.getAllGroupBusinessVertical1(businessVerticalTypeName,businessVerticalTypeName1, groupBusinessVerticalId,groupBusinessVerticalName,createdOn,page);

		if(groupBusinessVerticalMaster.getContent().size()==0) {
			map.put("groupBusinessVerticalMasterVOs", groupBusinessVerticalMasterVOs);
			map.put("total_pages", groupBusinessVerticalMaster.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", groupBusinessVerticalMaster.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
			}
		
		groupBusinessVerticalMaster.forEach(groupBusinessVerticalMaster1 -> {	
					List<GroupBusinessVertical> groupBusinessVerticalVO=groupBusinessVerticalRepo.getByID(groupBusinessVerticalMaster1.getId());
						List<String> codes = new ArrayList<String>();
						List<Long> ids = new ArrayList<Long>();
						
						for(int i=0;i<groupBusinessVerticalVO.size();i++) {
						List<WebMaster> webMasters =  webMasterRepo.findByWebMasterIds(groupBusinessVerticalVO.get(i).getWebMaster().getWebMasterId());
						
						for (WebMaster webMaster : webMasters) {
							codes.add(webMaster.getWebMasterName());
							ids.add(webMaster.getWebMasterId());
							}	
						}
						
						GroupBusinessVerticalMasterVO groupBusinessVerticalMasterVO=groupBusinessVerticalMapper.getVoFromEntity(groupBusinessVerticalMaster1);
						groupBusinessVerticalMasterVO.setWebMasterNames(codes);
						groupBusinessVerticalMasterVO.setWebMasterIds(ids);
						groupBusinessVerticalMasterVOs.add(groupBusinessVerticalMasterVO);
						
				});
		
	//	groupBusinessVerticalMasterVOs=groupBusinessVerticalMaster.stream().map(groupBusinessVerticalMapper::getVoFromEntity).collect(Collectors.toList());
		map.put("groupBusinessVerticalMasterVOs", groupBusinessVerticalMasterVOs);
		map.put("total_pages", groupBusinessVerticalMaster.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", groupBusinessVerticalMaster.getTotalElements());

		return new ResponseEntity<>(map,HttpStatus.OK);
    	
    	}
	
}
