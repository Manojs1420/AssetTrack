package com.titan.irgs.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;



@Service
public class ConfigurationService  implements IConfigurationService{
	
	
	 private static final Logger  LOGGER  = LoggerFactory.getLogger(ConfigurationService.class);

	 
	    ConfigurationRepository configurationRepository;

	    private Map<String, Configuration1>  configurationList;

	    private List<String>                mandatoryConfigs;

	    @Autowired
	    public ConfigurationService(ConfigurationRepository configRepository) {
	        this.configurationRepository = configRepository;
	        this.configurationList = new ConcurrentHashMap<>();
	        this.mandatoryConfigs = new ArrayList<>();
	        
	        this.mandatoryConfigs.add(WebConstantUrl.CONFIG_KEY_REFRESH_RATE_CONFIG);
	  /*      this.mandatoryConfigs.add(Constants.CONFIG_KEY_REFRESH_RATE_CONFIG);
	        this.mandatoryConfigs.add(Constants.CONFIG_KEY_REFRESH_RATE_METRIC);
	        this.mandatoryConfigs.add(Constants.CONFIG_KEY_REFRESH_RATE_TOKEN);
	        this.mandatoryConfigs.add(Constants.CONFIG_KEY_REFRESH_RATE_USER);*/
	    }

	    /**
	     * Loads configuration parameters from Database
	     */
	  /*  @PostConstruct
	    public void loadConfigurations() {
	        LOGGER.debug("Scheduled Event: Configuration table loaded/updated from database");
	        StringBuilder sb = new StringBuilder();
	        sb.append("Configuration Parameters:");
	        List<Configuration> configs = configRepository.findAll();
	        for (Configuration configuration : configs) {
	            sb.append("\n" + configuration.getConfigKey() + ":" + configuration.getConfigValue());
	            this.configurationList.put(configuration.getConfigKey(), configuration);
	        }
	        LOGGER.debug(sb.toString());

	        checkMandatoryConfigurations();
	    }
*/
	    public Configuration1 getConfiguration(String key) {
	        return configurationList.get(key);
	    }

	    /**
	     * Checks if the mandatory parameters are exists in Database
	     */
	    public void checkMandatoryConfigurations() {
	        for (String mandatoryConfig : mandatoryConfigs) {
	            boolean exists = false;
	            for (Map.Entry<String, Configuration1> pair : configurationList.entrySet()) {
	                if (pair.getKey().equalsIgnoreCase(mandatoryConfig) && !pair.getValue().getConfigValue().isEmpty()) {
	                    exists = true;
	                }
	            }
	            if (!exists) {
	                String errorLog = String.format("A mandatory Configuration parameter is not found in DB: %s", mandatoryConfig);
	                LOGGER.error(errorLog);
	            }
	        }

	    }

		@Override
		public List<Configuration1> getAllConfiguration() {
		
			return configurationRepository.findAll();
		}

		@Override
		public Configuration1 getConfigurationById(Long configurationId) {
		
			Configuration1 c = configurationRepository.findByID(configurationId);
		
			
			if (c == null) {
				LOGGER.error("Configuration with id {} not found", configurationId);
				throw new EntityNotFoundException("configuration with id " + configurationId + " not found");
			}
			
			return c;
		}

		@Override
		public Configuration1 saveConfiguration(Configuration1 configuration) {
		
			return configurationRepository.save(configuration);
		}

		@Override
		public Configuration1 updateConfiguration(Configuration1 configuration) {
		
			Configuration1 c1 = configurationRepository.findByID(configuration.getConfigurationId());
			
			if (c1 == null) {
				LOGGER.error("Configuration with id {} not found", configuration.getConfigurationId());
				throw new EntityNotFoundException("configuration with id " + configuration.getConfigurationId() + " not found");
			}
			
			return configurationRepository.save(configuration);
		}

		@Override
		public Configuration1 findByConfigValue(String configValue) {
		
			return configurationRepository.findByConfigValue(configValue);
		}
	    
		
	    
	   /* public Configuration saveConfiguration(Configuration configuration) {
	    	
	    	CronSequenceGenerator generator = new CronSequenceGenerator(configuration.getConfigValue());
	    	Date nextRunDate= generator.next(new Date());	
	    	
	    	System.out.println(nextRunDate);
	    	//Configuration c= configRepository.save(configuration);
	    	
	    	
			return configRepository.save(configuration);
	    	
	    	
	    	
	    }*/

}
