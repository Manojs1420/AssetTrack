package com.titan.irgs.inventory.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.inventory.domain.AutoAmcInventoryLog;

@Repository
public interface AutoAmcInventoryLogRepository extends JpaRepository<AutoAmcInventoryLog, Long>,JpaSpecificationExecutor<AutoAmcInventoryLog>{
	@Query(value="SELECT * FROM auto_amc_inventory_log v order by amc_log_id desc /*#pageable*/",
			countQuery = "SELECT * FROM auto_amc_inventory_log v order by amc_log_id desc",
			nativeQuery = true)
	Page<AutoAmcInventoryLog> getAll(Pageable pageable);
	
	@Query(value="SELECT amc_id FROM auto_amc_inventory_log where service_creation_status='No'",nativeQuery = true)
	List<Long> getAmcIdByNotCreateStatus();
	
	@Query(value="SELECT amc_log_id FROM auto_amc_inventory_log where amc_id= ?1",nativeQuery = true)
	AutoAmcInventoryLog getAmcLogIdByAmcId(Long amcId);
}
