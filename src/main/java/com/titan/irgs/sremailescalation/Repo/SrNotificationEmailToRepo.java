package com.titan.irgs.sremailescalation.Repo;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.sremailescalation.Domain.SrNotificationEmailTo;
@Repository
public interface SrNotificationEmailToRepo extends JpaRepository<SrNotificationEmailTo, Long> {
	
	  @Modifying
	  @Transactional
	  @Query(value="delete from sr_notification_email_to where srnotificationemailto_id = ?1",nativeQuery = true) 
	  void deleteBySrnotificationemailtoId(Long srnotificationemailtoId);
	 
	
	}

	
	
	
