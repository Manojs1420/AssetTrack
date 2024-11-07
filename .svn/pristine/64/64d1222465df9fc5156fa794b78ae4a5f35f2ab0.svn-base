package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.master.domain.ClusterUser;
import com.titan.irgs.master.service.IClusterService;
import com.titan.irgs.master.vo.ClusterUserVO;

@Component
public class ClusterUserMapper {
	
	@Autowired
	IClusterService clusterService;
	
	
	
	public ClusterUserVO getVoFromEntity(ClusterUser clusterUser) {
		
		ClusterUserVO clusterUserVo = null;
		ModelMapper clusterUserVoModelMapper = new ModelMapper();

		PropertyMap<ClusterUser, ClusterUserVO> clusterUserEntityToVOPropertyMap = new PropertyMap<ClusterUser, ClusterUserVO>() {
			@Override
			protected void configure() {
				map().setClusterUserId(source.getClusterUserId());
				
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUserId(source.getUserId());
				map().setUserName(source.getUserName());
				

			}
		};
		
		clusterUserVoModelMapper.addMappings(clusterUserEntityToVOPropertyMap);
		clusterUserVo = clusterUserVoModelMapper.map(clusterUser, ClusterUserVO.class);

		return clusterUserVo;

	}

	public ClusterUser getEntityFromVo(ClusterUserVO clusterUserVo) {
		
		ClusterUser clusterUser = null;
		ModelMapper clusterUserModelMapper = new ModelMapper();
		
	//	Region region = regionService.getRegionById(ClusterUserVo.getRegionId());

		PropertyMap<ClusterUserVO, ClusterUser> clusterUserVOToEntityPropertyMap = new PropertyMap<ClusterUserVO, ClusterUser>() {
			@Override
			protected void configure() {
				map().setClusterUserId(source.getClusterUserId());
			
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setUserId(source.getUserId());
				map().setUserName(source.getUserName());
			}
		};
		
		clusterUserModelMapper.addMappings(clusterUserVOToEntityPropertyMap);
		clusterUser = clusterUserModelMapper.map(clusterUserVo, ClusterUser.class);
		
		
		
		
	      Cluster cluster = clusterService.getClusterById(clusterUserVo.getClusterId());
		
	
		clusterUser.setCluster(cluster);
		
		return clusterUser;

	}

}
