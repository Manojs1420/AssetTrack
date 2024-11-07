package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.AssetSpecification;



@Repository
public interface AssetSpecificationRepository extends JpaRepository<AssetSpecification, Long>,JpaSpecificationExecutor<AssetSpecification>
{

	AssetSpecification findByAssetSpecificationName(String assetSpecificationName);

	@Query(value = "SELECT w.web_master_name,a.asset_specification_name FROM asset_specification a\\r\\n\" + \r\n" + 
			"			\"inner join web_master w on w.web_master_id=a.web_master_id where a.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAllForExcel();
	
	@Query(value = "SELECT w.web_master_name,a.asset_specification_name FROM asset_specification a\r\n" + 
			"inner join web_master w on w.web_master_id=a.web_master_id where a.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAllForExcel(@Param("id")Long id);	
	
	@Query(value="SELECT a.* FROM asset_specification a where web_master_id=:id",nativeQuery=true)
	List<AssetSpecification> getEquipmentByTypeVerticalId(@Param("id")Long id);
}
