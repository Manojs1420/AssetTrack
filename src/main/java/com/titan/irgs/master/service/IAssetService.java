package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Asset;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, Asset domain class).
 * This is AssetService interface(i.e, custom interface) which has CRUD method specification for Asset domain class.
 * It is responsible to provide service(i.e Asset related data) to Asset web page and vice-versa
 * 
 * @author 
 *
 */


public interface IAssetService 
{
	Page<Asset>	getAllAsset(String assetName, String assetCode, String fARNo, String equipmentName, String modelName, String brandName, String businessVerticalType, String departmentName, String amcStatus, String department, String vendorCode, Pageable pageable);

	Asset getAssetById(Long assetId);
	
	Asset saveAsset(Asset asset);
	
	Asset updateAsset(Asset asset);
	
	void deleteAssetById(Long assetId);
	
	List<Asset> getAllAssetOnModelId(Long modelId);

	Asset findByAssetName(String assetName);

	Asset findByAssetCode(String assetCode);

	List<Object[]> getAllCitysForExcel(String businessVerticalType, String department);

	List<Object[]> getAllCitysForExcel(String businessVerticalType);

	List<Object[]> getAllCitysForExcel();
			
	List<Asset> getAllAssetExceptAlreadyCreated(String assetName, String assetCode, String fARNo, String equipmentName,
			String modelName, String brandName, String businessVerticalType, Pageable pageable);

	Long count(String assetName, String assetCode, String fARNo, String equipmentName, String modelName,
			String brandName, String businessVerticalType);

	Page<Asset> getAllAssetExceptAlreadyCreated1(String assetName, String assetCode, String fARNo, String equipmentName,
			String modelName, String brandName, String businessVerticalType, Pageable page);

	List<Asset> findByAmcStatus(boolean amcStatus);

	List<Object[]> getAssetsByVerticalIdAndDepartmentId(Long verticalId, Long departmentId);

	Asset updateAssetForPOUpload(Asset asset);

	List<Object[]> getAssetCountForDashboard(String businessVerticalType, String department, String user1, String vendorCode);

	List<Object[]> getUnassignedAssetCountForDashboard(String businessVerticalType, String department, String user1);

	List<Object[]> getScrappedAssetCountForDashboard(String businessVerticalType, String department, String user1);

	void updateSerialNumbers(Asset asset, String serialNumbers) throws Exception;
	
}
