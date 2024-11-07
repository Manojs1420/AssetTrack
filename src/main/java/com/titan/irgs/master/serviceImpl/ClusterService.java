package com.titan.irgs.master.serviceImpl;

import java.util.Date;
/**
 * 
 */
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.service.IClusterService;

@Service
public class ClusterService implements IClusterService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClusterRepository clusterRepository;
    
	/**
	 * getAllCluster -> Method
	 * @param ->none
	 * @return list of saved Cluster entity
	 */
	@Override
	public List<Cluster> getAllCluster() {
		return clusterRepository.findAll();
	}
    
	/**
	 * getClusterById -> Method
	 * @param ClusterId
	 * @return saved Cluster entity
	 */
	@Override
	public Cluster getClusterById(Long clusterId) {
		Cluster cluster = clusterRepository.findById(clusterId).orElseThrow(()->new EntityNotFoundException("Cluster with ClusterId " + clusterId + " not found"));
		
		return cluster;
	}
    
	/**
	 * saveCluster ->Method
	 * @param Cluster entity
	 * @return saved Cluster entity
	 */
	@Override
	public Cluster saveCluster(Cluster cluster) {
		cluster.setCreatedDate(new Date());
		return clusterRepository.save(cluster);
	}
    
	/**
	 * updateCluster ->Method
	 * @param Cluster entity
	 * @return updated Cluster entity
	 */
	@Override
	public Cluster updateCluster(Cluster cluster) {
		Optional<Cluster> Cluster1 = clusterRepository.findById(cluster.getClusterId());
		if (Cluster1 == null) {
			logger.error("Cluster with ClusterId {} not found", cluster.getClusterId());
			throw new EntityNotFoundException("Cluster with ClusterId " + cluster.getClusterId() + " not found");
		}

		return clusterRepository.save(cluster);
	}
    
	
	@Override
	public void deleteClusterById(Long clusterId) {
		Optional<Cluster> Cluster = clusterRepository.findById(clusterId);
		if (Cluster == null) {
			logger.error("Cluster with ClusterId {} not found", clusterId);
			throw new EntityNotFoundException("Cluster with ClusterId " + clusterId + " not found");
		}
		clusterRepository.deleteById(clusterId);
	}

	
	@Override
	public List<Long> getAllRegionIdsInClusterUsingWebRoleId(Long webRoleId) {
		// TODO Auto-generated method stub
		return clusterRepository.getAllRegionIdsInClusterUsingWebRoleId(webRoleId);
	}

	@Override
	public void deleteClusterByUsingWebRoleIdAndRegionId(Long webRoleId, Long regionid) {
		// TODO Auto-generated method stub
		clusterRepository.deleteClusterByUsingWebRoleIdAndRegionId(webRoleId, regionid);
		
	}

	@Override
	public Cluster findByRegionRegionIdAndWebRoleWebRoleId(Long regionId, Long webRoleId) {
		// TODO Auto-generated method stub
		return clusterRepository.findByRegionRegionIdAndWebRoleWebRoleId(regionId,webRoleId);
	}

	@Override
	public List<Cluster> getClustersByUsingUserId(Long id) {
		return clusterRepository.getClustersByUsingUserId(id);
	}

	public Cluster findByRegionRegionId(Long regionId) {
		// TODO Auto-generated method stub
		return clusterRepository.findByRegionRegionId(regionId);
	}
    
	
	

	
	



}
