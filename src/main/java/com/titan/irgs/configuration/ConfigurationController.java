package com.titan.irgs.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;

@RestController
@RequestMapping(WebConstantUrl.CONFIGURATION_BASE_URL)
public class ConfigurationController {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	
	@Autowired
	ConfigurationRepository configRepository;
	
	@Autowired
	ConfigurationService configurationService;

	// ::::::::::::::::AccessPolicy
	
	@GetMapping(WebConstantUrl.GET_ALL_CONFIGURATION)
	@ResponseBody
	public List<Configuration1> getAllAccessPolicy() {
	
		List<Configuration1> configuration = configurationService.getAllConfiguration();

	
		return configuration;
	}

	@GetMapping(WebConstantUrl.GET_CONFIGURATION_BY_ID)
	@ResponseBody
	public Configuration1 getConfigurationById(@PathVariable Long id) {
		return configurationService.getConfigurationById(id);
	}

	@PostMapping(WebConstantUrl.CREATE_CONFIGURATION)
	@ResponseBody
	public Configuration1 saveConfiguration(@RequestBody Configuration1 configuration) {
		/*CronSequenceGenerator generator = new CronSequenceGenerator(configuration.getConfigValue());
    	Date nextRunDate= generator.next(new Date());	
    	
    	System.out.println(nextRunDate);*/
		
		return configRepository.save(configuration);
	}

	@PutMapping(WebConstantUrl.EDIT_CONFIGURATION)
	@ResponseBody
	public Configuration1 updateConfiguration(@RequestBody Configuration1 configuration) {
		return configurationService.updateConfiguration(configuration);
	}

	
}

