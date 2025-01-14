package com.titan.irgs.sremailescalation.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.titan.irgs.sremailescalation.Domain.Sremailescalation;

@Repository
public interface SremailescalationRepo extends JpaRepository<Sremailescalation, Long>,JpaSpecificationExecutor<Sremailescalation>{
	
	Sremailescalation findBySremailescalationId(Long sremailescalationId);
//	List<Sremailescalation> findByVerticalId(Long verticalId);
	List<Sremailescalation> findByEscalationLevel(String escalationLevel);
	List<Sremailescalation> findByDays(Long days);
	List<Sremailescalation> findByVerticalName(String verticalName);

	}
