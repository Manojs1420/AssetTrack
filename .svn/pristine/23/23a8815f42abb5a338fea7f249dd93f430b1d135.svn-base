package com.titan.irgs.master.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.mapper.CountryMapper;
import com.titan.irgs.master.service.ICountryService;
import com.titan.irgs.master.vo.CountryVO;

@RestController
@RequestMapping(value = WebConstantUrl.COUNTRY_BASE_URL)

public class CountryController {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private ICountryService countryService;
	
	@Autowired
	private CountryMapper countryMapper;
	
	@GetMapping(value = WebConstantUrl.GET_ALL_COUNTRY)
	@ResponseBody
	public ResponseEntity<List<CountryVO>> getAllCountry()
	{
		List<CountryVO> countryVos = new ArrayList<CountryVO>(0);
		List<Country> countries = countryService.getAllCountry();
		if(countries.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			countries.forEach(country -> {
				countryVos.add(countryMapper.getVoFromEntity(country));
			});
		}
		return new ResponseEntity<List<CountryVO>>(countryVos, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_COUNTRY_BY_ID)
	@ResponseBody
	public ResponseEntity<CountryVO> getCountryById(@PathVariable Long countryId)
	{
		Country country = countryService.getCountryById(countryId);
		if (country == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CountryVO>(countryMapper.getVoFromEntity(country), HttpStatus.OK);
	}
	
	@PostMapping(value = WebConstantUrl.SAVE_COUNTRY)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> saveCountry(@RequestBody CountryVO countryVo)
	{   
		Map<String,Object> map = new HashMap<>();
	    boolean countryNameStatus = countryService.checkIfCountryNameIsExit(countryVo.getCountryName());
	    if(countryNameStatus) {
	    	map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Country Name: " + countryVo.getCountryName() +  " is already present. So Duplicate entry for Country Name is not allowed.");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	    } else{
	    	countryVo = countryMapper.getVoFromEntity(countryService.saveCountry(countryMapper.getEntityFromVo(countryVo)));
	    	map.put("status code", 201);
			map.put("sucess msg", "Area created sucessfully.");
			map.put("countryVo", countryVo);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
	    }
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_COUNTRY)
	@ResponseBody
	public ResponseEntity<CountryVO> updateCountry(@RequestBody CountryVO countryVo)
	{
		Country country = countryMapper.getEntityFromVo(countryVo);
		country = countryService.updateCountry(country);
		if (country == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CountryVO>(countryMapper.getVoFromEntity(country), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_COUNTRY_BY_ID)
	@ResponseBody
	public void deleteCountryById(@PathVariable Long countryId)
	{
		countryService.deleteCountryById(countryId);
	}

	
	

	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_COUNTRY_EXCELSHEET)
	public ResponseEntity<byte[]>  downloadsampleExcelSheet()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("Country_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("COUNTRY_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
