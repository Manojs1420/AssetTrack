package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.service.IRegionService;

@Service
public class RegionService implements IRegionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RegionRepository regionRepository;
	

	
	
	
	@Value("{region.regionIdNotFound}")
	String regionNotFoundException;
	
	/**
	 * getAllRegion -> Method
	 * @param ->none
	 * @return list of saved region entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Region> getAllRegion(String regionName, Pageable page) {
		return regionRepository.findAll(new Specification<Region>() {
			
			@Override
			public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				

				if (regionName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("regionName"),"%" + regionName + "%")));

				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		},page);
	}
    
	/**
	 * getRegionById -> Method
	 * @param regionId
	 * @return saved region entity
	 */
	@Override
	public Region getRegionById(Long regionId) {
		
		Region region = regionRepository.findById(regionId).orElseThrow(()->new EntityNotFoundException("Region with regionId " + regionId + " not found"));
		
		return region;
	}
    
	/**
	 * saveRegion ->Method
	 * @param region entity
	 * @return saved region entity
	 */
	@Override
	public Region saveRegion(Region region) {
		region.setCreatedDate(new Date());
		return regionRepository.save(region);
	}
    
	/**
	 * updateRegion ->Method
	 * @param region entity
	 * @return updated region entity
	 */
	@Override
	public Region updateRegion(Region region) {
		Region region1 = regionRepository.findById(region.getRegionId()).orElseThrow(()->new EntityNotFoundException("Region with regionId " + region.getRegionId() + " not found"));
		
		return regionRepository.save(region);
	}
    
	/**
	 * deleteRegionById ->Method
	 * @param regionId
	 * @return none
	 */
	@Override
	public void deleteRegionById(Long regionId) {
		Region region1 = regionRepository.getOne(regionId);
		if(region1 == null)
		{
			logger.info(regionNotFoundException);
			throw new EntityNotFoundException(regionNotFoundException);
		}
		
		regionRepository.deleteById(regionId);
	}
	
	/**
	 * checkIfRegionNameIsExit ->Method
	 * @param regionName
	 * @return boolean
	 */
//	@Override
//	public boolean checkIfRegionNameIsExit(String regionName) {
//		List<Region> region = regionRepository.findByRegionName(regionName);
//		if (region != null) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}

	

	@Override
	public boolean checkIfRegionNameIsExit(String regionName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Region> findByRegionName(String regionName) {
        List<Region> region= regionRepository.getByRegionName(regionName);
		
		/*if (region == null) {
			logger.error("region with regionName {} not found", regionName);
			throw new EntityNotFoundException("region with regionName " + regionName + " not found");
		}*/
		return region;
	}

	/*@Override
	public Region findByRegionNameAndCityId(String regionName, Long c) 
	{
		Region region1=regionRepository.findByRegionNameAndCityId(regionName, c);
		return region1;
		
	}


	@Override
	public List<Region> findByCityCityId(List<Long> cityIds) {
	
		return regionRepository.findByCityCityIdIn(cityIds);
	}*/

	@Override
	public List<Region> findByRegionIdIn(List<Long> regionIds) {
	
		return regionRepository.findByRegionIdIn(regionIds);
	}

	/*@Override
	public List<Region> findByCityCityId(Long cityId) {
		
		return regionRepository.findByCityCityId(cityId);
	}*/

	/*@Override
	public Region findByRegionNameAndStateId(String regionName, Long s) {
		
		Region region1=regionRepository.findByRegionNameAndStateId(regionName, s);
		
		return region1;
	}*/

	@Override
	public List<Region> getAllRegionsNotInRegionIds(List<Long> regionsIds) {
		// TODO Auto-generated method stub
		return regionRepository.findByRegionIdNotIn(regionsIds);
	}

	@Override
	public List<Region> getAllRegionsInClusterUsingWebRoleId(Long webRoleId) {
		// TODO Auto-generated method stub
		return regionRepository.getAllRegionsInClusterUsingWebRoleId(webRoleId);
	}

	@Override
	public List<Object[]> getAllRegionForUserCreationUsingWebRoleId(Long id) {
		// TODO Auto-generated method stub
		return regionRepository.getAllRegionForUserCreationUsingWebRoleId(id);
	}

	@Override
	public List<Region> getAllReagionsInClusterUsingUserId(Long id) {
		return regionRepository.getAllReagionsInClusterUsingUserId(id);
	}


	@Override
	public List<Object[]> getAllRegionsInCluster(Long id) {
		// TODO Auto-generated method stub
		return regionRepository.getAllRegionsInCluster(id);
	}


	@Override
	public Region findByRegionNameAndCityId(String regionName, Long c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Region findByRegionNameAndStateId(String regionName, Long s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> findByCityCityId(List<Long> cityIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> findByCityCityId(Long cityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> getAllRegions() {
		// TODO Auto-generated method stub
		return regionRepository.findAll();
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return regionRepository.getAllForExcel();
	}

	

	

}

