package com.titan.irgs.webMaster.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.repository.CurrencyRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.model.WebMasterModel;

/**
 * 
 * @author hari.k
 *
 */

@Component
public class WebMasterMapper {
	
	@Autowired
	CurrencyRepo currencyRepo;
	
	public WebMaster convertModeltoDomain(WebMasterModel webMasterModel) {
		WebMaster webMaster = new WebMaster();
		BeanUtils.copyProperties(webMasterModel, webMaster);
		
		if(webMasterModel.getCurrencyId()!=null) {
			webMaster.setCurrency(currencyRepo.findById(webMasterModel.getCurrencyId()).orElseThrow(()->new ResourceNotFoundException("The Currency Id  not found")));
		}
		
		return webMaster;
	}

	public WebMasterModel convertDomaintoModel(WebMaster webMaster) {
		
		WebMasterModel webMasterModel=new WebMasterModel();
		BeanUtils.copyProperties(webMaster, webMasterModel);

		if(webMaster.getCurrency()!=null) {
			webMasterModel.setCurrencyId(webMaster.getCurrency().getCurrencyId());
			webMasterModel.setCurrencyName(webMaster.getCurrency().getCurrencyName());
		}
		
		return webMasterModel;
	}

}
