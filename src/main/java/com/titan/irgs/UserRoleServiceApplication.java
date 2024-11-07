package com.titan.irgs;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.titan.irgs.serviceRequest.controller.EmailServiceImpl;
import com.titan.irgs.serviceRequest.controller.Mail;

@SpringBootApplication
public class UserRoleServiceApplication implements CommandLineRunner
{
	@Autowired
	EmailServiceImpl mailService;
	
	@Value("${mail.status}")
	private Boolean mailStatus;
	 
	public static void main(String[] args) 
	{
		SpringApplication.run(UserRoleServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(mailStatus) {
		try {	
			Mail mail=new Mail();
			mail.setMailTo(Arrays.asList("sandeep@rim-global.com"));
			mail.setMailSubject("ABOUT JAR UPDATE");
			mail.setMailContent("HI SANDEEP JAR HAS BEEN UPDATED IN SERVER AT "+new Date());
			mailService.sendMultiPartEmail(mail);
		
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	
	}
}
