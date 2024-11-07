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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.mapper.CityMapper;
import com.titan.irgs.master.repository.CityRepository;
import com.titan.irgs.master.service.ICityService;
import com.titan.irgs.master.vo.CityVO;

@RestController
@RequestMapping(value = WebConstantUrl.CITY_BASE_URL)


public class CityController {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private ICityService cityService;

	@Autowired
	private CityMapper cityMapper;
	
	@Autowired
	private CityRepository cityRepository;
	
	
	@PersistenceContext
	private EntityManager em;
	
	// ::::::::::::User

	@GetMapping(value = WebConstantUrl.GET_ALL_CITY)
	@ResponseBody
	public ResponseEntity<?> getAllState(String cityName/*,String stateName*/,Pageable page,HttpServletRequest request)
	
	{
		
	/*	logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
                + ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));*/

		
		Map<String,Object> map = new HashMap<>();
	
		
	
	    Integer nextId = (page.getPageNumber() == 0 ? new Integer(0) : page.getPageNumber());
		Integer limit = (page.getPageSize() == 0 ? new Integer(5) : page.getPageSize());
		
	
		if(cityName == null) {
			
			List<CityVO> cityVOs = new ArrayList<CityVO>(0);
			List<City> cities = cityService.getAllCity();

			if (cities.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				cities.forEach(city -> {
					cityVOs.add(cityMapper.getVoFromEntity(city));
				});

				
			}

			return new ResponseEntity<List<CityVO>>(cityVOs, HttpStatus.OK);
		}
		
        List<CityVO> cityVo = new ArrayList<CityVO>(0);
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
		

		Root<City> root = criteriaQuery.from(City.class);
		
      
		
		CriteriaQuery<City> select = criteriaQuery.select(root);
		
		List<Predicate> p = new ArrayList<Predicate>();
		
		
		if (cityName != null) {
			p.add(criteriaBuilder.like(root.get("cityName").as(String.class), cityName + "%"));
		}
		
		
		/*if (stateName != null) {
			p.add(criteriaBuilder.like(state.get("stateName").as(String.class), stateName + "%"));
		}*/
		
		
		
		
		
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

		
		Page<City> productPageList = new PageImpl<City>(resultList,page, t);
		
		
		
	    Long totalRecordSize = (long) resultList1.size();
		
		System.out.println(productPageList.getNumber()+"  "+productPageList.getNumberOfElements()+" "+productPageList.getTotalElements());

		int total_Pages =	productPageList.getTotalPages();
		
		for(  City city :productPageList ) {
			
        CityVO cityVo1 = new CityVO();
			
 
			BeanUtils.copyProperties(city, cityVo1);
		//	cityVo1.setStateId(city.getState().getStateId());
			//cityVo1.setStateName(city.getState().getStateName());
			cityVo1.setCityId(city.getCityId());
			cityVo1.setCityName(city.getCityName());
			cityVo1.setCreatedDate(city.getCreatedDate());
			cityVo1.setUpdatedDate(city.getUpdateDate());
			cityVo.add(cityVo1);
			
		}
		/*productPageList.forEach(city -> {
			CityVO cityVo1 = new CityVO();
			
			//BeanUtils.copyProperties(city, cityVo1);
			//cityVo1.setStateId(city.getState().getStateId());
			//cityVo1.setStateName(city.getState().getStateName());
			cityVo1.setCityId(city.getCityId());
			cityVo1.setCityName(city.getCityName());

			cityVo.add(cityVo1);
		});*/
		
		map.put("status_code", 200);
		map.put("total_records", totalRecordSize);
		map.put("total_pages",total_Pages );
		map.put("CityVo", cityVo);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	/*@GetMapping(value = WebConstantUrl.GET_ALL_CITY)
	@ResponseBody
	public ResponseEntity<List<CityVO>> getAllCity() {

		List<CityVO> cityVOs = new ArrayList<CityVO>(0);
		List<City> cities = cityService.getAllCity();

		if (cities.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			cities.forEach(city -> {
				cityVOs.add(cityMapper.getVoFromEntity(city));
			});

			return new ResponseEntity<List<CityVO>>(cityVOs, HttpStatus.OK);
		}

	}*/

	@GetMapping(value = WebConstantUrl.GET_CITY_BY_ID)
	@ResponseBody
	public ResponseEntity<CityVO> getCityById(@PathVariable Long cityId) {
		City city = cityRepository.getById(cityId);
		if (city == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CityVO>(cityMapper.getVoFromEntity(city), HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.SAVE_CITY)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> addCity(@RequestBody CityVO cityVo) {
		Map<String,Object> map = new HashMap<>();
	    boolean cityNameStatus = cityService.checkIfCityNameIsExit(cityVo.getCityName());
	    if(cityNameStatus) {
	    	map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "City Name: " + cityVo.getCityName() +  " is already present. So Duplicate entry for 'City Name"
					+ " is not allowed.");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	    } else{
	    	cityVo = cityMapper.getVoFromEntity(cityService.saveCity(cityMapper.getEntityFromVo(cityVo)));
	    	map.put("status code", 201);
			map.put("sucess msg", "City created sucessfully.");
			map.put("cityVo", cityVo);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
	    }
		
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_CITY)
	@ResponseBody
	public ResponseEntity<CityVO> updateCity(@RequestBody CityVO cityVo) {
		City city = cityMapper.getEntityFromVo(cityVo);
		city = cityService.updateCity(city);
		if (city == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CityVO>(cityMapper.getVoFromEntity(city), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_CITY_BY_ID)
	@ResponseBody
	public void deleteCity(@PathVariable Long cityId) {
		cityService.deleteCityById(cityId);
	}
/*	
	@GetMapping(value = WebConstantUrl.GET_ALL_CITY_ON_REGION_ID)
	@ResponseBody
	public ResponseEntity<List<CityVO>> getAllCityOnRegionId(@PathVariable(value = "regionId") Long regionId) {
		List<CityVO> cityVos = new ArrayList<CityVO>();
		List<City> cities = cityService.getAllCityOnRegionId(regionId);
		try {
			if (regionId == null) {
				return new ResponseEntity<List<CityVO>>(HttpStatus.NOT_FOUND);
			}
			if (cities == null || cities.isEmpty()) {
				return new ResponseEntity<List<CityVO>>(HttpStatus.NO_CONTENT);

			}
			cities.forEach(city -> {
				cityVos.add(cityMapper.getVoFromEntity(city));
			});
			return new ResponseEntity<List<CityVO>>(cityVos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<CityVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	*/
	
	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_CITY_EXCELSHEET)
	public ResponseEntity<byte[]> downloadsampleExcelSheet()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("City_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("CITY_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/exportExcel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=city.xlsx";
        response.setHeader(headerKey, headerValue);
         
         MasterHeaders masterHeaders=new MasterHeaders();
        List<Object[]> citys=cityService.getAllCitysForExcel();
        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.city,citys);
         
        excelExporter.export(response);    
    }  
}