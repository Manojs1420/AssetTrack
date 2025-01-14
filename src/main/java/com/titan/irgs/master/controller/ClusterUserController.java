package com.titan.irgs.master.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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

/*import org.springframework.web.reactive.function.client.WebClient;
*/
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
//import com.titan.irgs.externalservicemodels.UserModel;
import com.titan.irgs.master.domain.ClusterUser;
import com.titan.irgs.master.mapper.ClusterUserMapper;
import com.titan.irgs.master.service.IClusterUserService;
import com.titan.irgs.master.vo.ClusterUserVO;




@RestController
@RequestMapping(value = WebConstantUrl.CLUSTER_USER_BASE_URL)

public class ClusterUserController {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	
	@Autowired
	private IClusterUserService clusterUserService;
	
	@Autowired
	private ClusterUserMapper clusterUserMapper;
	
	/*
	 * @Autowired private WebClient.Builder webClientBuilder;
	 */
	
	/*@Value("${userService}")
	private String userService;*/
	
	@GetMapping(value = WebConstantUrl.GET_ALL_CLUSTER_USER)
	@ResponseBody
	public ResponseEntity<List<ClusterUserVO>> getAllClusterUser()
	{
		
		List<ClusterUserVO> clusterUserVos = clusterUserService.getAllClusterUser().stream().map(clusterUserMapper::getVoFromEntity).collect(Collectors.toList());
		return new ResponseEntity<List<ClusterUserVO>>(clusterUserVos, HttpStatus.OK);
	}
	
	@GetMapping(value = WebConstantUrl.GET_CLUSTER_USER_BY_ID)
	@ResponseBody
	public ResponseEntity<ClusterUserVO> getClusterUserById(@PathVariable Long clusterUserId)
	{
	ClusterUser clusterUser = clusterUserService.getClusterUserById(clusterUserId);
		if (clusterUser == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ClusterUserVO>(clusterUserMapper.getVoFromEntity(clusterUser), HttpStatus.OK);
	}
	
	@PostMapping(value = WebConstantUrl.SAVE_CLUSTER_USER)
	@ResponseBody
	public ResponseEntity<?> saveClusterUser(@RequestBody ClusterUserVO clusterUserVo,HttpServletRequest request)
	
	{
		
		
		System.out.println( request.getHeader("Authorization"));
	     /*    UserModel userModel=webClientBuilder.build().get()
			.uri(userService+"/getById/"+clusterUserVo.getUserId()).header("Authorization", request.getHeader("Authorization")).retrieve()
			.onStatus(HttpStatus::is4xxClientError,error->Mono.error(new EntityNotFoundException("request id not found")) )
			.bodyToMono(UserModel.class)
			.block();
	
	     clusterUserVo.setUserId(userModel.getId());
	     clusterUserVo.setUserName(userModel.getUsername());*/
		ClusterUser clusterUser = clusterUserMapper.getEntityFromVo(clusterUserVo);
		clusterUser = clusterUserService.saveClusterUser(clusterUser);
		
		if(clusterUser == null)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<ClusterUserVO>(clusterUserMapper.getVoFromEntity(clusterUser),HttpStatus.CREATED);
	
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_CLUSTER_USER)
	@ResponseBody
	public ResponseEntity<ClusterUserVO> updateClusterUser(@RequestBody ClusterUserVO clusterUserVo)
	{
		ClusterUser clusterUser = clusterUserMapper.getEntityFromVo(clusterUserVo);
		clusterUser = clusterUserService.updateClusterUser(clusterUser);
		if (clusterUser == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ClusterUserVO>(clusterUserMapper.getVoFromEntity(clusterUser), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_CLUSTER_USER_BY_ID)
	@ResponseBody
	public void deleteClusterUserById(@PathVariable Long clusterUserId)
	{
		clusterUserService.deleteClusterUserById(clusterUserId);
	}


}
