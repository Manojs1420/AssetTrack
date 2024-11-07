package com.titan.irgs.sremailescalation.Repo;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.sremailescalation.Domain.AmcNotificationEmailTo;

@Repository
public interface AmcNotificationEmailToRepo extends JpaRepository<AmcNotificationEmailTo, Long> {
	
	  @Modifying
	  @Transactional
	  @Query(value="delete from amc_notification_email_to where amcnotificationemailto_id = ?1",nativeQuery = true) 
	  void deleteByAmcnotificationemailtoId(Long amcnotificationemailtoId);
	 
	
	}

	
	
	

