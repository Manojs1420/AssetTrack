package com.titan.irgs.master.service;

import java.util.List;

import com.titan.irgs.master.domain.Cluster;

public interface IClusterService {
	
	List<Cluster> getAllCluster();

	Cluster getClusterById(Long clusterId);

	Cluster saveCluster(Cluster Cluster);

	Cluster updateCluster(Cluster cluster);

	void deleteClusterById(Long clusterId);

	List<Long> getAllRegionIdsInClusterUsingWebRoleId(Long webRoleId);

	void deleteClusterByUsingWebRoleIdAndRegionId(Long webRoleId, Long regionid);

	Cluster findByRegionRegionIdAndWebRoleWebRoleId(Long regionId, Long webRoleId);

	List<Cluster> getClustersByUsingUserId(Long id);

}
