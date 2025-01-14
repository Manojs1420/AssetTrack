package com.titan.irgs.sremailescalation.Repo;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.sremailescalation.Domain.Escalation;
@Repository
public interface EscalationRepo extends JpaRepository<Escalation, Long> {

	/*@Query (value="SELECT e.*,s.sremailescalation_id FROM escalation e" 
			+ "inner join sremailescalation s on s.sremailescalation_id=e.sremailescalation_id"
			+"where s.sremailescalation_id=:uri and web_role_id=:roleId",nativeQuery=true )
	Escalation checkApiAppendTorole(@Param("uri")Long uri,@Param("roleId") Long roleId);
	
	@Query(value = "SELECT p.*,b.back_end_api_id_url FROM permission p "
			+ "inner join back_end_apis b on b.back_end_api_id=p.back_end_api_id "
			+ "where b.back_end_api_id_url =:uri and web_role_id=:roleId",nativeQuery = true)
	Permission checkApiAppendTorole(@Param("uri")String uri,@Param("roleId") Long roleId);
*/
	//Escalation checkApiAppendTorole(String escalationLevel, Long webRoleId);
	@Modifying
	@Transactional
	@Query("delete from Escalation where escalationId = ?1")
	void deleteByEscalationId(Long escalationId);
	

	}

	
	
	

