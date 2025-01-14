package com.titan.irgs.sremailescalation.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.sremailescalation.Domain.SrNotificationEmailCc;
@Repository
public interface SrNotificationEmailCcRepo extends JpaRepository<SrNotificationEmailCc, Long> {
	
	  @Modifying
	  @Transactional
	  @Query(value="delete from sr_notification_email_cc where srnotificationemailcc_id = ?1",nativeQuery = true )
	  void deleteBySrnotificationemailccId(Long srnotificationemailccId);
	 

	}

	
	
	

