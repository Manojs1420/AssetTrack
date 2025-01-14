package com.titan.irgs.master.service;

import java.util.List;

import com.titan.irgs.master.domain.ClusterUser;

public interface IClusterUserService {
	
	List<ClusterUser> getAllClusterUser();

	ClusterUser getClusterUserById(Long clusterUserId);

	ClusterUser saveClusterUser(ClusterUser clusterUser);

	ClusterUser updateClusterUser(ClusterUser clusterUser);

	void deleteClusterUserById(Long clusterUserId);

	void deleteClusterUserByUserIdAndClusterIdIn(Long id, Long clusterId);

	List<ClusterUser> getClusterByUserById(Long id);

}
