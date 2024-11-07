package com.titan.irgs.master.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.master.domain.Cluster;
import com.titan.irgs.master.mapper.ClusterMapper;
import com.titan.irgs.master.service.IClusterService;
import com.titan.irgs.master.vo.ClusterVO;

@RestController
@RequestMapping(value = WebConstantUrl.CLUSTER_BASE_URL)

public class ClusterController {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private IClusterService clusterService;
	
	@Autowired
	private ClusterMapper clusterMapper;
	
	@GetMapping(value = WebConstantUrl.GET_ALL_CLUSTER)
	@ResponseBody
	public ResponseEntity<List<ClusterVO>> getAllCluster()
	{
		
		List<ClusterVO> clusterVos = clusterService.getAllCluster().stream().map(clusterMapper::getVoFromEntity).collect(Collectors.toList());
		return new ResponseEntity<List<ClusterVO>>(clusterVos, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = WebConstantUrl.GET_CLUSTER_BY_ID)
	@ResponseBody
	public ResponseEntity<ClusterVO> getClusterById(@PathVariable Long ClusterId)
	{
	Cluster cluster = clusterService.getClusterById(ClusterId);
		if (cluster == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ClusterVO>(clusterMapper.getVoFromEntity(cluster), HttpStatus.OK);
	}
	
	@PostMapping(value = WebConstantUrl.SAVE_CLUSTER)
	@ResponseBody
	public ResponseEntity<?> saveCluster(@RequestBody ClusterVO clusterVo)
	
	{
		Cluster cluster = clusterMapper.getEntityFromVo(clusterVo);
		cluster = clusterService.saveCluster(cluster);
		
		if(cluster == null)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<ClusterVO>(clusterMapper.getVoFromEntity(cluster),HttpStatus.CREATED);
	
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_CLUSTER)
	@ResponseBody
	public ResponseEntity<ClusterVO> updateCluster(@RequestBody ClusterVO clusterVo)
	{
		Cluster cluster = clusterMapper.getEntityFromVo(clusterVo);
		cluster = clusterService.updateCluster(cluster);
		if (cluster == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ClusterVO>(clusterMapper.getVoFromEntity(cluster), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_CLUSTER_BY_ID)
	@ResponseBody
	public void deleteClusterById(@PathVariable Long clusterId)
	{
		clusterService.deleteClusterById(clusterId);
	}


}
