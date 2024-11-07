package com.titan.irgs.sremailescalation.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.sremailescalation.Domain.SrNotificationEmail;

public interface SrNotificationEmailService {

	SrNotificationEmail save(SrNotificationEmail srNotificationEmail);


	SrNotificationEmail updateSrNotificationEmail(SrNotificationEmail srNotificationEmail);

	Page<SrNotificationEmail> getAllSrNotificationEmails(Pageable page);





	SrNotificationEmail getSrNotificationEmailById(Long id);


	SrNotificationEmail getVerticalName(String verticalName);


	Page<SrNotificationEmail> getAllSrNotificationEmails(String verticalName, Long verticalId, String activityName,
			String activityId, Pageable page);


	SrNotificationEmail getByactivityNameAndVerticalId(String activityName, Long webMasterId);


	SrNotificationEmail findByActivityName(String activityName);
	
	

}

