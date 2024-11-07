package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Region;

public interface IRegionService {

	Page<Region> getAllRegion(String regionName, Pageable page);

	Region getRegionById(Long regionId);

	Region saveRegion(Region region);

	Region updateRegion(Region region);

	void deleteRegionById(Long regionId);
	
	boolean checkIfRegionNameIsExit(String regionName);

	List<Region> findByRegionName(String regionName);

	Region findByRegionNameAndCityId(String regionName, Long c);
	
	Region findByRegionNameAndStateId(String regionName,Long s);

	
	//List<Region> findByCityCityId(Long cityId);
	
	List<Region> findByCityCityId(List<Long> cityIds);
	
	List<Region>findByRegionIdIn(List<Long> regionIds);
	
	List<Region> findByCityCityId(Long cityId);

	List<Region> getAllRegionsNotInRegionIds(List<Long> regionsIds);

	List<Region> getAllRegionsInClusterUsingWebRoleId(Long webRoleId);

	List<Object[]> getAllRegionForUserCreationUsingWebRoleId(Long id);

	List<Region> getAllReagionsInClusterUsingUserId(Long id);


	List<Object[]> getAllRegionsInCluster(Long id);

	List<Region> getAllRegions();

	List<Object[]> getAllForExcel();
	
}
