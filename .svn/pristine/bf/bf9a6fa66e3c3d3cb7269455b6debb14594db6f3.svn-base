package com.titan.irgs.master.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.master.domain.Currency;
import com.titan.irgs.master.mapper.CurrencyMapper;
import com.titan.irgs.master.repository.CurrencyRepo;
import com.titan.irgs.master.service.CurrencyService;
import com.titan.irgs.master.vo.CurrencyVO;

@RestController
@RequestMapping(value = WebConstantUrl.CURRENCY_BASE_URL)
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;
	
	@Autowired
	CurrencyRepo currencyRepo;
	
	@Autowired
	CurrencyMapper currencyMapper;
	
	@PostMapping(WebConstantUrl.SAVE_CURRENCY)
	@ResponseBody
	public ResponseEntity<?> saveCurrency(@RequestBody CurrencyVO currencyVO) {
		Map<String,Object> map = new HashMap<>();
		Currency currency = currencyMapper.getEntityFromVo(currencyVO);

		Currency currencyName = currencyRepo.findByCurrencyName(currencyVO.getCurrencyName());
		if(currencyName != null) {

			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Currnecy Name : " + currencyVO.getCurrencyName() + " is already present");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);

		}

		currency = currencyService.saveCurrency(currency);

		if (currency == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<CurrencyVO>(currencyMapper.getVoFromEntity(currency),HttpStatus.CREATED);
	}
	
	@PostMapping(value = WebConstantUrl.UPDATE_CURRENCY)
	@ResponseBody
	public ResponseEntity<CurrencyVO> updateCurrency(@RequestBody CurrencyVO currencyVO)
	{
		Currency currency = currencyMapper.getEntityFromVo(currencyVO);
		currency = currencyService.updateCurrency(currency);
		if (currency == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CurrencyVO>(currencyMapper.getVoFromEntity(currency), HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.DELETE_CURRENCY_BY_ID)
	@ResponseBody
	public void deleteCurrencyById(@RequestBody CurrencyVO currencyVO)
	{
		currencyService.deleteCurrencyById(currencyVO.getCurrencyId());
	}
	
	@PostMapping(value = WebConstantUrl.GET_ALL_CURRENCY)
	@ResponseBody
	public ResponseEntity<?> getAllCurrency(@RequestBody CurrencyVO currencyVO,Principal principal)
	{
		Pageable page=PageRequest.of(currencyVO.getPage()==0?1:currencyVO.getPage()-1, currencyVO.getSize());
		Map<String,Object> map=new HashMap<>();

		//filtering 
		List<CurrencyVO> currencyVOs = new ArrayList<CurrencyVO>(0);
		Page<Currency> currencys = currencyService.getAllCurrency(currencyVO,page);
		if(currencys.getContent().size() == 0) {

			map.put("currencyVOs", currencyVOs);
			map.put("total_pages", currencys.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", currencys.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			currencys.forEach(currency -> {
				currencyVOs.add(currencyMapper.getVoFromEntity(currency));
			});	

		}

		map.put("currencyVOs", currencyVOs);
		map.put("total_pages", currencys.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", currencys.getTotalElements());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping(value = WebConstantUrl.GET_CURRENCY_BY_ID)
	@ResponseBody
	public ResponseEntity<CurrencyVO> getCurrencyById(@RequestBody CurrencyVO currencyVO)
	{
		Currency currency = currencyService.getCurrencyById(currencyVO.getCurrencyId());
		if (currency == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CurrencyVO>(currencyMapper.getVoFromEntity(currency), HttpStatus.OK);
	}
	
}
