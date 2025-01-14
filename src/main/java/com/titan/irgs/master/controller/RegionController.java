package com.titan.irgs.master.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.domain.RegionDetails;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.mapper.RegionMapper;
import com.titan.irgs.master.repository.CityRepository;
import com.titan.irgs.master.repository.RegionDetailsRepository;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.repository.StateRepository;
import com.titan.irgs.master.service.IRegionService;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.vo.RegionDetailsVO;
import com.titan.irgs.master.vo.RegionVO;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@RestController
@RequestMapping(value = WebConstantUrl.REGION_BASE_URL)

public class RegionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private IRegionService regionService;

	@Autowired
	private RegionMapper regionMapper;
	
	@Autowired
	IStateService stateService;
	
   @Autowired
   RegionRepository regionRepository;
   
   @Autowired
   CityRepository CityRepository;
   
   @Autowired
   StateRepository stateRepository;
  
   @Autowired
   WebRoleRepo webRoleRepo;
   
   @Autowired
   RegionDetailsRepository regionDetailsRepository;
	
	
	
	@PersistenceContext
	private EntityManager em;
	
	// ::::::::::::User

	/*@GetMapping(value = WebConstantUrl.GET_ALL_REGION)
	@ResponseBody
	public ResponseEntity<?> getAllState(String cityName,String stateName,String regionName,Pageable page)
	
	{
		
		Map<String,Object> map = new HashMap<>();
		
	    Integer nextId = (page.getPageNumber() == 0 ? new Integer(0) : page.getPageNumber());
		Integer limit = (page.getPageSize() == 0 ? new Integer(5) : page.getPageSize());
		
		if(stateName == null &&  regionName == null) {
			
			List<RegionVO> regionVOs = new ArrayList<RegionVO>(0);
			List<Region> regions = regionService.getAllRegion();
			if(regions.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				regions.forEach(region -> {
					regionVOs.add(regionMapper.getVoFromEntity(region));
				});
			}
			
			return new ResponseEntity<List<RegionVO>>(regionVOs, HttpStatus.OK);
		}
		
        List<RegionVO> regionVo = new ArrayList<RegionVO>(0);
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Region> criteriaQuery = criteriaBuilder.createQuery(Region.class);
		

		Root<Region> root = criteriaQuery.from(Region.class);
		
      
        
       Join<Region,State>state = root.join("state",JoinType.INNER);
        
       CriteriaQuery<Region> select = criteriaQuery.select(root);
		
		List<Predicate> p = new ArrayList<Predicate>();
		
		
		if (cityName != null) {
			p.add(criteriaBuilder.like(city.get("cityName").as(String.class), cityName + "%"));
		}
		
		if (stateName != null) {
			p.add(criteriaBuilder.like(state.get("stateName").as(String.class), stateName + "%"));
		}
		
		
		if (regionName != null) {
			p.add(criteriaBuilder.like(root.get("regionName").as(String.class), regionName + "%"));
		}
		
		if (!p.isEmpty()) {
			Predicate[] pr = new Predicate[p.size()];
			select.where(pr);
			p.toArray(pr);
			criteriaQuery.where(pr);
			
			criteriaQuery.select(root);
	
		}
		
		TypedQuery typedQuery1 = em.createQuery(select);
		
	    List resultList1 = typedQuery1.getResultList();
		
	//	nextId =nextId+1;
		TypedQuery typedQuery = em.createQuery(select);
		typedQuery.setFirstResult((nextId-1)*limit);
		typedQuery.setMaxResults(limit);
		

		List resultList = typedQuery.getResultList();
		
		int t =resultList1.size();

		
		Page<Region> productPageList = new PageImpl<Region>(resultList,page, t);
		
	 Long totalRecordSize = (long) resultList1.size();
		
		System.out.println(productPageList.getNumber()+"  "+productPageList.getNumberOfElements()+" "+productPageList.getTotalElements());

		int total_Pages =	productPageList.getTotalPages();
		
		for(Region region : productPageList) {
			
         RegionVO regionVo1 = new RegionVO();
			
			BeanUtils.copyProperties(region, regionVo1);
			regionVo1.setRegionId(region.getRegionId());
			regionVo1.setRegionName(region.getRegionName());
			//regionVo1.setStateId(region.getState().getStateId());
			//regionVo1.setStateName(region.getState().getStateName());

			regionVo.add(regionVo1);
		}
		productPageList.forEach(region -> {
			RegionVO regionVo1 = new RegionVO();
			
			BeanUtils.copyProperties(region, regionVo1);
			regionVo1.setRegionId(region.getRegionId());
			regionVo1.setRegionName(region.getRegionName());
			regionVo1.setCityId(region.getCity().getCityId());
			regionVo1.setCityName(region.getCity().getCityName());

			regionVo.add(regionVo1);
		});
		
		map.put("status_code", 200);
		map.put("total_records", totalRecordSize);
		map.put("total_pages",total_Pages );
		map.put("regionVo", regionVo);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	*/
	
	
	@GetMapping(value = WebConstantUrl.GET_ALL_REGION)
	@ResponseBody
	public ResponseEntity<?> getAllRegions(
			@RequestParam(required=false) String regionName,
			Pageable pageable)
	
	{
		
		Map<String,Object> map = new HashMap<>();
		
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		List<RegionVO> regionVos = new ArrayList<RegionVO>(0);
        Page<Region> regions = regionService.getAllRegion(regionName,page);
        if(regions.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", regions.getTotalElements());
			map.put("total_pages",regions.getSize());
			map.put("brandVos", regionVos);
			
			return new ResponseEntity<>(map,HttpStatus.OK);
			
		} else {
			regions.forEach(region -> {
				regionVos.add(regionMapper.getVoFromEntity(region));
				
			});
		}
		
		map.put("status_code", 200);
		map.put("total_records", regions.getTotalElements());
		map.put("total_pages",regions.getTotalPages() );
		map.put("regionVo", regionVos);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	

	@GetMapping(value = WebConstantUrl.GET_REGION_BY_ID)
	@ResponseBody
	public ResponseEntity<RegionVO> getRegionById(@PathVariable Long regionId) {
		Region region = regionService.getRegionById(regionId);
		
		RegionVO regionVO = new RegionVO();
		
		List<RegionDetailsVO> regionDetailsVOs = new ArrayList<RegionDetailsVO>();
		
		if (region == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<RegionDetails> regionDetailsList = regionDetailsRepository.findByRegionRegionId(region.getRegionId());
		
		if(regionDetailsList != null) {
			for(RegionDetails regionDetails2 : regionDetailsList) {
				regionVO.setRegionId(region.getRegionId());
				regionVO.setRegionName(region.getRegionName());
				
				RegionDetailsVO regionDetailsVO = new RegionDetailsVO();
				regionDetailsVO.setCreatedDate(regionDetails2.getCreatedDate());
				regionDetailsVO.setRegionDetailsId(regionDetails2.getRegionDetailsId());
				regionDetailsVO.setRegionId(regionDetails2.getRegion().getRegionId());
				regionDetailsVO.setRegionName(regionDetails2.getRegion().getRegionName());
				regionDetailsVO.setStateId(regionDetails2.getState().getStateId());
				regionDetailsVO.setStateName(regionDetails2.getState().getStateName());
				regionDetailsVO.setUpdatedDate(regionDetails2.getUpdateDate());
				regionDetailsVOs.add(regionDetailsVO);
				regionVO.setRegionInfo(regionDetailsVOs);
				
			}
		}
		
		return new ResponseEntity<RegionVO>(regionVO, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = WebConstantUrl.GET_ALL_REGION_FOR_USER_CREATION_USING_WEB_ROLE_ID)
	@ResponseBody
	public ResponseEntity<?> getAllRegionForUserCreationUsingWebRoleId(@PathVariable Long id) {
		List<RegionVO>regionVOs=new ArrayList<>();
		WebRole webRole=webRoleRepo.getOne(id);
		List<Object[]> regions = regionService.getAllRegionForUserCreationUsingWebRoleId(id);
		if (regions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		for(Object[] region:regions) {
			RegionVO regionVO=new RegionVO();
			regionVO.setRegionId(Long.parseLong(region[0].toString()));
			regionVO.setRegionName(region[3].toString());
			if(Integer.parseInt(region[6].toString())==0)
			  regionVO.setActiveRegion(false);
			else if(Integer.parseInt(region[6].toString())==1) {
			       
				if(webRole.getRole().getRoleName().equalsIgnoreCase("Store"))
				  regionVO.setActiveRegion(false);
				else
					  regionVO.setActiveRegion(true);


			}
			regionVOs.add(regionVO);
		
		}
		
		return new ResponseEntity<>(regionVOs, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_REGION_IN_CLUSTER_BY_USING_WEBROLE_ID)
	@ResponseBody
	public ResponseEntity<?> getAllRegionsInClusterByWeb_Role_Id(@PathVariable Long id) {
		List<Region> regions = regionService.getAllRegionsInClusterUsingWebRoleId(id);
		if (regions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(regions.stream().map(regionMapper::getVoFromEntity).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_REGION_BASED_ON_CLUSTER)
	@ResponseBody
	public ResponseEntity<?> getAllRegionsInCluster(@PathVariable Long id) {
		List<RegionVO> regionVOs=new ArrayList<>();

		List<Object[]> regions = regionService.getAllRegionsInCluster(id);
		if (regions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		for(Object[] region:regions) {
			RegionVO regionVO=new RegionVO();
			regionVO.setRegionId(Long.parseLong(region[0].toString()));
			regionVO.setRegionName(region[1].toString());
			if(region[2].toString().equalsIgnoreCase("true"))
			  regionVO.setActiveRegion(true);
			else if(region[2].toString().equalsIgnoreCase("false"))
				  regionVO.setActiveRegion(false);

			regionVOs.add(regionVO);
		
		}
		return new ResponseEntity<>(regionVOs, HttpStatus.OK);
	}
	
	
	@GetMapping(value = WebConstantUrl.GET_ALL_REGION_IN_CLUSTER_BY_USING_USER_ID)
	@ResponseBody
	public ResponseEntity<?> getAllReagionsInClusterUsingUserId(@PathVariable Long id) {
		List<Region> regions = regionService.getAllReagionsInClusterUsingUserId(id);
		if (regions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(regions.stream().map(regionMapper::getVoFromEntity).collect(Collectors.toList()), HttpStatus.OK);
	}

	/*@PostMapping(value = WebConstantUrl.SAVE_REGION)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addRegion(@RequestBody RegionVO regionVo) {
		Map<String, Object> map = new HashMap<>();
	//	boolean regionNameStatus = regionService.checkIfRegionNameIsExit(regionVo.getRegionName());
		Region  region = regionRepository.findByRegionIdAndStateStateId(regionVo.getRegionId(),regionVo.getStateId());
		if (region != null) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Region Name: " + regionVo.getRegionName()
					+ " is already present with the given State Name " +regionVo.getStateName() +". So Duplicate entry for Region Name is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			regionVo = regionMapper.getVoFromEntity(regionService.saveRegion(regionMapper.getEntityFromVo(regionVo)));
			map.put("status code", 201);
			map.put("sucess msg", "Region created sucessfully.");
			map.put("regionVo", regionVo);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}

	}*/
	
	@PostMapping(value = WebConstantUrl.SAVE_REGION)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addRegion(@RequestBody RegionVO regionVo) {
		Map<String, Object> map = new HashMap<>();
	
		List<RegionDetailsVO> regionDetailsVOs = new ArrayList<RegionDetailsVO>();
		
		Region  region = regionRepository.findByRegionNameIgnoreCase(regionVo.getRegionName());
		
		if(region == null) {
			 region = new Region();
			 BeanUtils.copyProperties(regionVo, region);
			 
			 region = regionRepository.save(region);
		}else {
			
			 
			 region.setRegionId(region.getRegionId());
			 region.setRegionName(region.getRegionName());
			 region.setCreatedDate(new Date());
			 region = regionRepository.save(region);
		}
		
		RegionVO regionVO2 = new RegionVO();
		BeanUtils.copyProperties(region, regionVO2);
		
		for(RegionDetailsVO regionDetailsVO : regionVo.getRegionInfo()) {
			
			
			RegionDetails regionDetail = regionDetailsRepository.findByRegionRegionNameAndStateStateId(region.getRegionName(),regionDetailsVO.getStateId());
			
			if(regionDetail != null) {
				
				map.put("status code", 400);
				map.put("client status", "Bad Request");
				map.put("error msg", "Region Name: " + regionVo.getRegionName()
						+ " is already present with the given State Name: " +regionDetail.getState().getStateName() +". So Duplicate entry for Region Name is not allowed.");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			}else {
				    regionDetail = new RegionDetails();
					
					State state = stateRepository.findByStateId(regionDetailsVO.getStateId());
					
					Region regions = regionRepository.findByRegionId(region.getRegionId());
					
					if(state != null && regions.getRegionId() != null) {
						
						regionDetail.setState(state);
						regionDetail.setRegion(regions);
						regionDetail.setCreatedDate(new Date());
						
						regionDetailsRepository.save(regionDetail);
						
						RegionDetailsVO regionDetailsVO1 = new RegionDetailsVO();
						
						regionDetailsVO1.setRegionDetailsId(regionDetail.getRegionDetailsId());
						
						regionDetailsVO1.setCreatedDate(regionDetail.getCreatedDate());
						regionDetailsVO1.setRegionId(regionDetail.getRegion().getRegionId());
						regionDetailsVO1.setRegionName(regionDetail.getRegion().getRegionName());
						regionDetailsVO1.setStateId(regionDetail.getState().getStateId());
						regionDetailsVO1.setStateName(regionDetail.getState().getStateName());
						regionDetailsVOs.add(regionDetailsVO1);
						
					}
				
			}
			
			
			
			regionVO2.setRegionInfo(regionDetailsVOs);			
		}
		
		
	        map.put("status code", 201);
			map.put("sucess msg", "Region created sucessfully.");
			map.put("regionVo", regionVO2);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		

	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_REGION)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateRegion(@RequestBody RegionVO regionVo) {
		Map<String, Object> map = new HashMap<>();
	
		List<RegionDetailsVO> regionDetailsVOs = new ArrayList<RegionDetailsVO>();
		
		Region  region = regionRepository.findByRegionNameIgnoreCase(regionVo.getRegionName());
		
		if(region == null) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Region Name: " + regionVo.getRegionName()+" is not present Region Master");
		}else {
			
		     region.setRegionId(region.getRegionId());
			 region.setRegionName(regionVo.getRegionName());
			 region.setCreatedDate(new Date());
			 region = regionRepository.save(region);
		}
		
		RegionVO regionVO2 = new RegionVO();
		BeanUtils.copyProperties(region, regionVO2);
		
		List<Long> statesIdsFromVo=regionVo.getRegionInfo().stream().map(m->m.getStateId()).collect(Collectors.toList());
		
		
		//below query will be delete unselected states
		regionDetailsRepository.deleteRegionDetailsByUsingRegionIdAndNotIn(region.getRegionId(),statesIdsFromVo);
		
		
	
		/*below logic will check the state is attached to region .
		 * 
		 * if state already attached to region  then skip 
		 * 
		 * else attach new state to region 
		 * 
		 * */
		
		for(RegionDetailsVO regionDetailsVO : regionVo.getRegionInfo()) {
			
			
			RegionDetails regionDetail = regionDetailsRepository.findByRegionRegionIdAndStateStateId(region.getRegionId(), regionDetailsVO.getStateId());
			
		    if(regionDetail==null) {
			
			State state = stateRepository.findByStateId(regionDetailsVO.getStateId());
			  Region regions = regionRepository.findByRegionId(region.getRegionId());
					
			  if(state != null && regions.getRegionId() != null) {
				        regionDetail=new RegionDetails();
						regionDetail.setState(state);
						regionDetail.setRegion(regions);
						regionDetail.setCreatedDate(new Date());
						regionDetailsRepository.save(regionDetail);
						
						RegionDetailsVO regionDetailsVO1 = new RegionDetailsVO();
						
						regionDetailsVO1.setRegionDetailsId(regionDetail.getRegionDetailsId());
						
						regionDetailsVO1.setCreatedDate(regionDetail.getCreatedDate());
						regionDetailsVO1.setRegionId(regionDetail.getRegion().getRegionId());
						regionDetailsVO1.setRegionName(regionDetail.getRegion().getRegionName());
						regionDetailsVO1.setStateId(regionDetailsVO.getStateId());
						regionDetailsVO1.setStateName(regionDetailsVO.getStateName());
						regionDetailsVOs.add(regionDetailsVO1);
						
				}
		    }
		    else {
		    	
		    	RegionDetailsVO regionDetailsVO1 = new RegionDetailsVO();
				
				regionDetailsVO1.setRegionDetailsId(regionDetail.getRegionDetailsId());
				
				regionDetailsVO1.setCreatedDate(regionDetail.getCreatedDate());
				regionDetailsVO1.setRegionId(regionDetail.getRegion().getRegionId());
				regionDetailsVO1.setRegionName(regionDetail.getRegion().getRegionName());
				regionDetailsVO1.setStateId(regionDetailsVO.getStateId());
				regionDetailsVO1.setStateName(regionDetailsVO.getStateName());
				regionDetailsVOs.add(regionDetailsVO1);		    	
		    	
		    }
		    
		    
		    
		    
			 regionVO2.setRegionInfo(regionDetailsVOs);			
		}
		
		
	        map.put("status code", 200);
			map.put("sucess msg", "Region Updated sucessfully.");
			map.put("regionVo", regionVO2);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		

	}

	/*@PutMapping(value = WebConstantUrl.UPDATE_REGION)
	@ResponseBody
	public ResponseEntity<RegionVO> updateRegion(@RequestBody RegionVO regionVo) {
		Region region = regionMapper.getEntityFromVo(regionVo);
		region = regionService.updateRegion(region);
		if (region == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<RegionVO>(regionMapper.getVoFromEntity(region), HttpStatus.OK);

	}*/

	@DeleteMapping(value = WebConstantUrl.DELETE_REGION_BY_ID)
	@ResponseBody
	public void deleteRegion(@PathVariable Long regionId) {
		regionService.deleteRegionById(regionId);
	}
	/*
	@GetMapping(value = WebConstantUrl.GET_ALL_REGION_ON_STATE_ID)
	@ResponseBody
	public ResponseEntity<List<RegionVO>> getAllRegionOnStateId(@PathVariable(value = "stateId") Long stateId) {
		List<RegionVO> regionVos = new ArrayList<RegionVO>();
		List<Region> regions = stateService.getAllRegionOnStateId(stateId);
		try {
			if (stateId == null) {
				return new ResponseEntity<List<RegionVO>>(HttpStatus.NOT_FOUND);
			}
			if (regions == null || regions.isEmpty()) {
				return new ResponseEntity<List<RegionVO>>(HttpStatus.NO_CONTENT);

			}
			regions.forEach(region -> {
				regionVos.add(regionMapper.getVoFromEntity(region));
			});
			return new ResponseEntity<List<RegionVO>>(regionVos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<RegionVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	*/
	

@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_REGION_EXCELSHEET)
public ResponseEntity<byte[]> downloadsampleExcelSheet()
{
	File resource = null;
	try
	{
		resource = new File(getClass().getClassLoader().getResource("Region_ExcelSheet_sample.xlsx").getFile());
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
		responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
		return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
		
	}
	catch (Exception e) 
	{
		e.printStackTrace();
		logger.error("REGION_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
	} 
	
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 }

@GetMapping("/exportExcel")
public void exportToExcel(HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());
     
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=Region.xlsx";
    response.setHeader(headerKey, headerValue);
     
     MasterHeaders masterHeaders=new MasterHeaders();
    List<Object[]>regions=regionService.getAllForExcel();
    ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.region,regions);
    excelExporter.export(response);    

}    
    
}