package com.titan.irgs.configuration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scheduler")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "configurationId", scope = Long.class)
public class Configuration1 {
	    
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private long  configurationId;
	   
	   private String scheduleType;
	   
	   private String configValue;
	   
	   private String timeFrom;
	   	   
	   private String thirdOccurence;

	public long getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(long configurationId) {
		this.configurationId = configurationId;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getThirdOccurence() {
		return thirdOccurence;
	}

	public void setThirdOccurence(String thirdOccurence) {
		this.thirdOccurence = thirdOccurence;
	}   
	    
}

