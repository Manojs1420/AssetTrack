package com.titan.irgs.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.RegionDetails;

@Repository
@Transactional
public interface RegionDetailsRepository extends JpaRepository<RegionDetails, Long>  {

	RegionDetails findByRegionRegionNameAndStateStateId(String regionName, Long stateId);

	RegionDetails findByRegionDetailsId(Long regionDetailsId);

	List<RegionDetails> findByRegionRegionId(Long regionId);

	RegionDetails findByRegionRegionIdAndStateStateId(Long regionId, Long stateId);

	@Modifying
	@Query(value = "delete FROM region_details where region_id=:regionId and state_id not in (:statesIdsFromVo)",nativeQuery = true)
    void deleteRegionDetailsByUsingRegionIdAndNotIn(Long regionId, List<Long> statesIdsFromVo);

}
