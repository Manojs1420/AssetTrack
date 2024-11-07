package com.titan.irgs.inventory.repository;

import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.inventory.domain.AmcWarranty;

@Repository
public interface AmcWarrantyRepository extends JpaRepository<AmcWarranty, Long>,JpaSpecificationExecutor<AmcWarranty> {


	@Query(value = " DELETE FROM amc_warranty_details where amc_id=:id",nativeQuery = true)
	 Consumer<? super AmcWarranty> deleteByAmcId(@Param("id")Long id);
	
	@Query(value = "SELECT a.* FROM amc_warranty_details a where amc_id=:id",nativeQuery = true)
	List<AmcWarranty> findWarrantyByAmcId(@Param("id")Long id);
	
	@Modifying
	@Transactional
	@Query("delete from AmcWarranty where warrantyId = ?1")
	void deleteByWarrantyId(Long warrantyId);
	
	@Query(value = " SELECT a.* FROM amc_warranty_details a where a.amc_id=:id",nativeQuery = true)
	List<AmcWarranty> FindByAmcId(@Param("id")Long id);

	@Query(value = " SELECT a.* FROM amc_warranty_details a where a.asset_id=:id",nativeQuery = true)
	List<AmcWarranty> FindByAssetId(@Param("id")Long id);
	//List<AmcWarranty> deleteAmcIdInBatch(Long amcId);
	
	@Query(value = "SELECT a.* FROM amc_warranty_details a where amc_id=:id",nativeQuery = true)
	AmcWarranty findWarrantyByAmcIds(@Param("id")Long id);
	
	@Query(value="select ticket_status from amc_warranty_details where amc_id = ?1",nativeQuery = true)
	List<String> findTicketStatusByAmcID(Long amcId);
	
}
