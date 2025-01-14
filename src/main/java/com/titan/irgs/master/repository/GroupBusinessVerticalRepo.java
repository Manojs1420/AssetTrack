package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.master.domain.GroupBusinessVertical;
import com.titan.irgs.webMaster.domain.WebMaster;

@Repository
public interface GroupBusinessVerticalRepo extends JpaRepository<GroupBusinessVertical, Long>,JpaSpecificationExecutor<GroupBusinessVertical>{

	@Query(value="SELECT * FROM group_business_vertical d where group_business_vertical_id =?1",nativeQuery=true)
	List<GroupBusinessVertical> getByID(Long Id);
	
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM group_business_vertical where group_business_vertical_id=?1",nativeQuery=true)
	void deleteBusinessVerticalId(Long id);
	
	@Query(value="select * from group_business_vertical where business_vertical_id=?1",nativeQuery = true)
	GroupBusinessVertical findByBusinessVerticalId(Long Id);
	
	@Query(value="select * from group_business_vertical where business_vertical_id in (?1)",nativeQuery = true)
	List<GroupBusinessVertical> findByBusinessVerticalIds(Long Id);
	
	GroupBusinessVertical findByWebMaster(WebMaster webMaster);
}
