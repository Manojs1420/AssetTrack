package com.titan.irgs.master.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.ClusterUser;
import com.titan.irgs.master.repository.ClusterUserRepository;
import com.titan.irgs.master.service.IClusterUserService;

@Service
public class ClusterUserService implements IClusterUserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClusterUserRepository clusterUserRepository;
    
	/**
	 * getAllClusterUser -> Method
	 * @param ->none
	 * @return list of saved ClusterUser entity
	 */
	@Override
	public List<ClusterUser> getAllClusterUser() {
		return clusterUserRepository.findAll();
	}
    
	/**
	 * getClusterUserById -> Method
	 * @param ClusterUserId
	 * @return saved ClusterUser entity
	 */
	@Override
	public ClusterUser getClusterUserById(Long clusterUserId) {
		ClusterUser clusterUser = clusterUserRepository.findById(clusterUserId).orElseThrow(()->new EntityNotFoundException("ClusterUser with ClusterUserId " + clusterUserId + " not found"));
	
		
		return clusterUser;
	}
    
	/**
	 * saveClusterUser ->Method
	 * @param ClusterUser entity
	 * @return saved ClusterUser entity
	 */
	@Override
	public ClusterUser saveClusterUser(ClusterUser clusterUser) {
		clusterUser.setCreatedDate(new Date());
		return clusterUserRepository.save(clusterUser);
	}
    
	/**
	 * updateClusterUser ->Method
	 * @param ClusterUser entity
	 * @return updated ClusterUser entity
	 */
	@Override
	public ClusterUser updateClusterUser(ClusterUser clusterUser) {
		Optional<ClusterUser> clusterUser1 = clusterUserRepository.findById(clusterUser.getClusterUserId());
		if (clusterUser1 == null) {
			logger.error("ClusterUser with ClusterUserId {} not found", clusterUser.getClusterUserId());
			throw new EntityNotFoundException("ClusterUser with ClusterUserId " + clusterUser.getClusterUserId() + " not found");
		}

		return clusterUserRepository.save(clusterUser);
	}
    
	
	@Override
	public void deleteClusterUserById(Long clusterUserId) {
		ClusterUser clusterUser = clusterUserRepository.findById(clusterUserId).
				orElseThrow(()-> new EntityNotFoundException("ClusterUser with ClusterUserId " + clusterUserId + " not found"));
		
		clusterUserRepository.deleteById(clusterUser.getClusterUserId());
	}

	@Override
	public void deleteClusterUserByUserIdAndClusterIdIn(Long id, Long clusterId) {
		clusterUserRepository.deleteClusterUserByUserIdAndClusterIdIn(id,clusterId);
	}

	@Override
	public List<ClusterUser> getClusterByUserById(Long id) {
		// TODO Auto-generated method stub
		return clusterUserRepository.getClusterByUserById(id);
	}
}
