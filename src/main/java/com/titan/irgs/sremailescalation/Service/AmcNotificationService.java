package com.titan.irgs.sremailescalation.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.sremailescalation.Domain.AmcNotification;

public interface AmcNotificationService {

	AmcNotification save(AmcNotification amcNotification);


	AmcNotification updateAmcNotification(AmcNotification amcNotification);

	Page<AmcNotification> getAllAmcNotifications(Pageable page);





	AmcNotification getAmcNotificationById(Long id);


	AmcNotification getVerticalName(String verticalName);


	Page<AmcNotification> getAllAmcNotifications(String verticalName, Long verticalId, String activityName,
			String activityId, Pageable page);


	AmcNotification getByactivityNameAndVerticalId(String activityName, Long webMasterId);


	AmcNotification findByActivityName(String activityName);
	
	

}

