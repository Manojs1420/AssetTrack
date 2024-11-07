package com.titan.irgs.configuration;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.serviceRequest.controller.EmailServiceImplimentation;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Repo.SremailescalationRepo;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Configuration
@EnableScheduling
public class EmailConfiguration {
	
	@Autowired
	EmailServiceImplimentation mailService;

	@Value("${mail.status}")
	private Boolean mailStatus;
	
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
//	@Scheduled(cron = "* * 1 * * *")
	public void SR_Escalation_Email_Scheduler()  {

		List<Object[]> serviceRequestList1 =serviceRequestRepository.getValidServiceRequests();
		for (Object[] rows : serviceRequestList1) {
			/*
			 * service_request_id:- System.out.println(rows[0]);
			 * service_request_code:-  System.out.println(rows[1]);
			 * escalation_level:- System.out.println(rows[2]);
			 * TOemail:-  System.out.println(rows[3]);
			 * CCEmail:- System.out.println(rows[4]);
			 */
			String mailSubject = "";			
			String emailCcValue=(String) rows[4];
			List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
            System.out.println(emailCc);
			String emailTOValue=(String) rows[3];
			List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
			System.out.println(emailTo);
			ServiceRequest serviceRequest = iServiceRequestService.getById(((BigInteger) rows[0]).longValue());
	 

		    Mail mail = iServiceRequestService.templeteMail(serviceRequest);
		   Long id= serviceRequest.getAssetWebMaster().getWebMasterId();
		  mailSubject = "Nu-Nxtwav  "+serviceRequestRepository.getwebmasterName(id)+" " + serviceRequest.getServiceRequestCode()+"– Escalation";
		  mail.setMailTo(emailTo);
		  mail.setMailCC(emailCc); 
		  mail.setMailSubject(mailSubject);
		  if (mailStatus) { try {
			mailService.sendMultiPartEmail(mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} } 

		}
			
			System.out.println("the task is completed");

	}


	}

