package com.titan.irgs.sremailescalation.Repo;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.sremailescalation.Domain.SrNotificationEmail;

@Repository
public interface SrNotificationEmailRepo extends JpaRepository<SrNotificationEmail, Long>,JpaSpecificationExecutor<SrNotificationEmail>{

	
	//SrNotificationEmail findBySrNotificationEmailId(Long findBySremailescalationId);
	@Query(value="SELECT * FROM sr_notification_email where srnotificationemail_id=?1",nativeQuery = true)
	SrNotificationEmail findBySrNotificationEmailId(Long srnotificationemailId);

	SrNotificationEmail findByVerticalName(String verticalName);

	SrNotificationEmail getByactivityNameAndVerticalId(String activityName, Long webMasterId);
	
	@Query(value="SELECT * FROM sr_notification_email where activity_name=?1",nativeQuery = true)
	SrNotificationEmail findByActivityName(String activityName);
	
	@Modifying
	  @Transactional
	  @Query(value="UPDATE sr_notification_email \r\n" + 
	  		"SET activity_name=?4, vertical_id=?2,vertical_name=?3 \r\n" + 
	  		"WHERE srnotificationemail_id=?1 ",nativeQuery = true) 
	  void updateByEmailId(Long srnotificationemailId,Long verticalId, String verticalName,String activityName);



	}
