package com.titan.irgs.serviceRequest.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class EmailServiceImplimentation {

	 @Autowired
	    private JavaMailSender emailSender;
	    @Autowired
	    private SpringTemplateEngine templateEngine;
	    public void sendMultiPartEmail(Mail mail) throws MessagingException, IOException {
	    	
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	        StandardCharsets.UTF_8.name());
	        
	      if(mail.getModel()!=null && !mail.getModel().isEmpty()) 
	      {
	        Context context = new Context();
	        context.setVariables(mail.getModel());
	    
	        String html = templateEngine.process("EscalationEmail", context);
	        helper.setText(html, true);
	      }else 
	      { 
	        
	        helper.setText(mail.getMailContent());
	        
	      }
	        
	        
	        helper.setTo(mail.getMailTo().stream().toArray(String[]::new));
	        System.out.println(mail.getMailTo());

	      if(mail.getMailCC()!=null &&!mail.getMailCC().isEmpty()) 
	      {
	        helper.setCc(mail.getMailCC().stream().toArray(String[]::new));
	        System.out.println(mail.getMailCC());

	      }
	        helper.setFrom("software_3dcad@3dcad-global.com");
	        
	     if(mail.getMailSubject()!=null) {
	        helper.setSubject(mail.getMailSubject());
	        System.out.println(mail.getMailSubject());
	     }
	        
	        emailSender.send(message);
	    }
	    
	    public void sendSimpleMessage(Mail mail) {
	    	        SimpleMailMessage message = new SimpleMailMessage(); 
	    	        message.setTo(mail.getMailTo().stream().toArray(String[]::new)); 
	    	        message.setFrom("software_3dcad@3dcad-global.com");

	    	        message.setText(mail.getMailContent());
	    	        emailSender.send(message);
	    	    }
}
