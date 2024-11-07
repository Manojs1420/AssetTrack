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
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.mapper.StateMapper;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.vo.StateVO;

@RestController
@RequestMapping(value = WebConstantUrl.STATE_BASE_URL)

public class StateController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private IStateService stateService;

	@Autowired
	private StateMapper stateMapper;
	
	
	
	@PersistenceContext
	private EntityManager em;
	
	// ::::::::::::User

	@GetMapping(value = WebConstantUrl.GET_ALL_STATE)
	@ResponseBody
	public ResponseEntity<?> getAllState(String stateName,Pageable page)
	
	{
		
		Map<String,Object> map = new HashMap<>();
	
		Integer nextId = (page.getPageNumber() == 0 ? new Integer(0) : page.getPageNumber());
		Integer limit = (page.getPageSize() == 0 ? new Integer(5) : page.getPageSize());
		
	if(stateName==null) {
		
		List<StateVO> stateVOs = new ArrayList<StateVO>(0);
		List<State> states = stateService.getAllState();
		
		if (states.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			states.forEach(state -> {
				stateVOs.add(stateMapper.getVoFromEntity(state));
			});
		}

		return new ResponseEntity<List<StateVO>>(stateVOs, HttpStatus.OK);
	}
		
        List<StateVO> stateVo = new ArrayList<StateVO>(0);
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<State> criteriaQuery = criteriaBuilder.createQuery(State.class);
		

		Root<State> root = criteriaQuery.from(State.class);
		
		
		CriteriaQuery<State> select = criteriaQuery.select(root);
		
		List<Predicate> p = new ArrayList<Predicate>();
		
		
		if (stateName != null) {
			p.add(criteriaBuilder.like(root.get("stateName").as(String.class), stateName + "%"));
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

		
		Page<State> productPageList = new PageImpl<State>(resultList,page, t);
		
		
		
	    Long totalRecordSize = (long) resultList1.size();
		
		System.out.println(productPageList.getNumber()+"  "+productPageList.getNumberOfElements()+" "+productPageList.getTotalElements());

		int total_Pages =	productPageList.getTotalPages();
		productPageList.forEach(state -> {
			StateVO stateVo1 = new StateVO();
			
			BeanUtils.copyProperties(state, stateVo1);
			stateVo1.setStateId(state.getStateId());
			stateVo1.setStateName(state.getStateName());
			stateVo1.setCountryId(state.getCountry().getCountryId());
			stateVo1.setCountryName(state.getCountry().getCountryName());

			stateVo.add(stateVo1);
		});
		
		map.put("status_code", 200);
		map.put("total_records", totalRecordSize);
		map.put("total_pages",total_Pages );
		map.put("stateVo", stateVo);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
    

	/*@GetMapping(value = WebConstantUrl.GET_ALL_STATE)
	@ResponseBody
	public ResponseEntity<List<StateVO>> getAllState() {
		
		List<StateVO> stateVOs = new ArrayList<StateVO>(0);
		List<State> states = stateService.getAllState();
		
		if (states.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			states.forEach(state -> {
				stateVOs.add(stateMapper.getVoFromEntity(state));
			});
		}

		return new ResponseEntity<List<StateVO>>(stateVOs, HttpStatus.OK);
	}*/

	@GetMapping(value = WebConstantUrl.GET_STATE_BY_ID)
	@ResponseBody
	public ResponseEntity<StateVO> getStateById(@PathVariable Long stateId) {
		State state = stateService.getStateById(stateId);
		if (state == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StateVO>(stateMapper.getVoFromEntity(state), HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.SAVE_STATE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addState(@RequestBody StateVO stateVO) {
		Map<String, Object> map = new HashMap<>();
		boolean stateNameStatus = stateService.checkIfStateNameIsExit(stateVO.getStateName());
		if (stateNameStatus) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "State Name: " + stateVO.getStateName()
					+ " is already present. So Duplicate entry for State Name is not allowed.");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {
			stateVO = stateMapper.getVoFromEntity(stateService.saveState(stateMapper.getEntityFromVo(stateVO)));
			map.put("status code", 201);
			map.put("sucess msg", "State created sucessfully.");
			map.put("stateVO", stateVO);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		}
	}

	@PutMapping(value = WebConstantUrl.UPDATE_STATE)
	@ResponseBody
	public ResponseEntity<StateVO> updateState(@RequestBody StateVO stateVo) {
		State state = stateMapper.getEntityFromVo(stateVo);
		state = stateService.updateState(state);
		if (state == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StateVO>(stateMapper.getVoFromEntity(state), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_STATE_BY_ID)
	@ResponseBody
	public void deleteState(@PathVariable Long stateId) {
		stateService.deleteStateById(stateId);
	}
	
	@GetMapping(value = WebConstantUrl.GET_ALL_STATE_ON_COUNTRY_ID)
	@ResponseBody
	public ResponseEntity<List<StateVO>> getAllStateOnCountryId(@PathVariable(value="countryId") Long countryId) 
	{
		List<StateVO> statesVo = new ArrayList<StateVO>();
		List<State> states = stateService.getAllStateOnCountryId(countryId);
		try {
			if(countryId == null) {
				return new ResponseEntity<List<StateVO>>(HttpStatus.NOT_FOUND);
			}
			if (states == null || states.isEmpty()) {
				return new ResponseEntity<List<StateVO>>(HttpStatus.NO_CONTENT);

			}
			states.forEach(state ->{
				statesVo.add(stateMapper.getVoFromEntity(state));
			});
			return new ResponseEntity<List<StateVO>>(statesVo,HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<List<StateVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_STATE_EXCELSHEET)
	public ResponseEntity<byte[]> downloadsampleExcelSheet()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("State_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("STATE_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/exportExcel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());
	     
	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=State.xlsx";
	    response.setHeader(headerKey, headerValue);
	     
	     MasterHeaders masterHeaders=new MasterHeaders();
	    List<Object[]>states=stateService.getAllForExcel();
	    ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.state,states);
	    excelExporter.export(response);    

	}    
	

}