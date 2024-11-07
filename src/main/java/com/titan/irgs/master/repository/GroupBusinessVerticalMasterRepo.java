package com.titan.irgs.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.GroupBusinessVerticalMaster;

@Repository
public interface GroupBusinessVerticalMasterRepo extends JpaRepository<GroupBusinessVerticalMaster, Long>,JpaSpecificationExecutor<GroupBusinessVerticalMaster>{

	
	@Query(value="select * from group_business_vertical_master where group_business_vertical_name=?1",nativeQuery=true)
	GroupBusinessVerticalMaster findByGroupName(String name);
}
