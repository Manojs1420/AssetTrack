package com.titan.irgs.sremailescalation.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.sremailescalation.Domain.AmcNotificationEmailCc;
@Repository
public interface AmcNotificationEmailCcRepo extends JpaRepository<AmcNotificationEmailCc, Long> {
	
	  @Modifying
	  @Transactional
	  @Query(value="delete from amc_notification_email_cc where amcnotificationemailcc_id = ?1",nativeQuery = true )
	  void deleteByAmcnotificationemailccId(Long amcnotificationemailccId);
	 

	}

