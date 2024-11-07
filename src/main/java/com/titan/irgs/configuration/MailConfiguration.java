package com.titan.irgs.configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.titan.irgs.serviceRequest.controller.EmailServiceImpl;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Repo.SremailescalationRepo;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Configuration
@EnableScheduling
public class MailConfiguration {
	
	@Autowired
	EmailServiceImpl mailService;

	@Value("${mail.status}")
	private Boolean mailStatus;
	
	@Autowired
	EmailConfiguration emailConfiguration;
	
	@Autowired
	SremailescalationRepo sremailescalationRepo;
	
	@Autowired
	IServiceRequestService iServiceRequestService;
	
	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
    private ConfigurationService configurationService;
	
	@Transactional
	//@Scheduled(fixedRateString  ="10000") for 10 seconds	
	//@Scheduled(cron=" 0 * ? * * * ") //at 1am for everyday
	//@Scheduled(cron=" * * * * * * ") for every second
	@Scheduled(cron = "1 * * * * *")
	public void AUTO_AMCSR_Escalation_Email_Scheduler() throws ParseException,InvalidFormatException,IOException {
		Configuration1 c = configurationService.findByConfigValue(AutoTrigger.SR_Escalation_Email_Scheduler.toString());
		if(c!=null) {
			String s = c.getTimeFrom();
			Date date=new Date();
			
			SimpleDateFormat d1=new SimpleDateFormat("HH:mm");
			String date1=d1.format(date);
			
			if(date1.equals(s)){
				System.out.print(s);

				emailConfiguration.SR_Escalation_Email_Scheduler();


		
	}

}
	}
	
	
}
