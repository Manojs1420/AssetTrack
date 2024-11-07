package com.titan.irgs.sremailescalation.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.sremailescalation.Domain.Escalation1;
@Repository
public interface EscalationRepo1 extends JpaRepository<Escalation1, Long> {

//	@Modifying
//	@Transactional
//	@Query("delete from Escalation1 where sremailescalationId = ?1")
//	void deleteBySremailescalationId(Long sremailescalationId);

	/*
	@Query (value="SELECT e1.*,s.sremailescalation_id FROM escalation1 e1" 
			+ "inner join sremailescalation s on s.sremailescalation_id=e1.sremailescalation_id"
			+ "where s.sremailescalation_id=:uri and web_role_id=:roleId",nativeQuery=true )
	Escalation1 checkApiAppendTorole(@Param("uri")Long uri,@Param("roleId") Long roleId);
	*/
	
	@Modifying
	@Transactional
	@Query("delete from Escalation1 where escalationId1 = ?1")
	void deleteByEscalationId1(Long escalationId1);

	}

	
	
	

