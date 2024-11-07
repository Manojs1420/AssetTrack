package com.titan.irgs.configuration;

import java.util.List;


public interface IConfigurationService {
	
	
	List<Configuration1> getAllConfiguration();

	Configuration1 getConfigurationById(Long configurationId);

	Configuration1 saveConfiguration(Configuration1 configuration);

	Configuration1 updateConfiguration(Configuration1 configuration);
	
	
	Configuration1 findByConfigValue(String configValue);

}
