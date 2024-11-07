package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.AssetSpecification;




public interface IAssetSpecificationService 
{

	Page<AssetSpecification> getAllAssetSpecification(String assetSpecipicationName,String webMasterId,String webMasterName, Pageable page);

	AssetSpecification getAssetSpecificationById(Long assetId);

	AssetSpecification findByAssetSpecificationName(String assetSpecificationName);

	AssetSpecification save(AssetSpecification assetSpecification);

	AssetSpecification update(AssetSpecification assetSpecification);

	void deleteById(Long assetId);

	List<Object[]> getAllForExcel();
	List<Object[]> getAllForExcel(Long id);

	List<AssetSpecification> getAssetSpecByVerticalId(Long id);
	





}
