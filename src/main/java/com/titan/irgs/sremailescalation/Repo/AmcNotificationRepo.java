package com.titan.irgs.sremailescalation.Repo;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.sremailescalation.Domain.AmcNotification;

@Repository
public interface AmcNotificationRepo extends JpaRepository<AmcNotification, Long>,JpaSpecificationExecutor<AmcNotification>{

	
	//SrNotificationEmail findBySrNotificationEmailId(Long findBySremailescalationId);
	@Query(value="SELECT * FROM amc_notification where amcnotification_id=?1",nativeQuery = true)
	AmcNotification findByAmcNotificationId(Long amcnotificationId);

	AmcNotification findByVerticalName(String verticalName);

	AmcNotification getByactivityNameAndVerticalId(String activityName, Long webMasterId);
	
	@Query(value="SELECT * FROM amc_notification where activity_name=?1",nativeQuery = true)
	AmcNotification findByActivityName(String activityName);
	
	@Modifying
	  @Transactional
	  @Query(value="UPDATE amc_notification \r\n" + 
	  		"SET activity_name=?4, vertical_id=?2,vertical_name=?3 \r\n" + 
	  		"WHERE amcnotification_id=?1 ",nativeQuery = true) 
	  void updateByEmailId(Long amcnotificationId,Long verticalId, String verticalName,String activityName);



	}
