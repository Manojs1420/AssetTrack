package com.titan.irgs.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.inventory.domain.AssetPOUpload;
import com.titan.irgs.master.domain.Asset;

@Repository 
public interface AssetPOUploadRepo extends JpaRepository<AssetPOUpload, Long>,JpaSpecificationExecutor<AssetPOUpload> {

	@Query(value="SELECT m.* FROM assetpoupload m where m.asset_id=:id",nativeQuery = true)
	List<AssetPOUpload> getAssetPoUploadByAssetId(@PathVariable("id")Long id);

	
	@Query(value="SELECT m.* FROM assetpoupload m where m.asset_id=:id",nativeQuery = true)
	AssetPOUpload getByAssetId(@PathVariable("id")Long id);
	
	List<AssetPOUpload> findByAsset(Asset asset);
	
}