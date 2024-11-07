package com.titan.irgs.webMaster.serviceImpl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.service.IWebMaster;

@Service
public class WebMasterService implements IWebMaster{
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	private static final Logger log = LoggerFactory.getLogger(WebMasterService.class);


	@Override
	public WebMaster save(WebMaster webMaster) {
		// TODO Auto-generated method stub
		return webMasterRepo.save(webMaster);
	}

	@Override
	public Collection<WebMaster> getAll() {
		// TODO Auto-generated method stub
		return webMasterRepo.findAll();
	}

	@Override
	public WebMaster getById(Long id) {
		// TODO Auto-generated method stub
		return webMasterRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("the request id not found"));
	}

	@Override
	public WebMaster update(WebMaster webMaster) {
		
		if(!webMasterRepo.existsById(webMaster.getWebMasterId())) 
			throw new ResourceNotFoundException("the request id {} not found"+webMaster.getWebMasterId());
		
		return webMasterRepo.save(webMaster);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
