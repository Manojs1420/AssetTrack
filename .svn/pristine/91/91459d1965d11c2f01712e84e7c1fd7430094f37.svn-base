package com.titan.irgs.accessPolicy.contoller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.accessPolicy.domain.Feature;
import com.titan.irgs.accessPolicy.mapper.FeatureMapper;
import com.titan.irgs.accessPolicy.model.FeatureVo;
import com.titan.irgs.accessPolicy.service.FeatureService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.service.IUserService;


@RestController
@RequestMapping(WebConstantUrl.FUTURE)
public class FutureController {
	
	@Autowired
	FeatureService futuresService;
	
	@Autowired
	FeatureMapper featureMapper;
	
	@Autowired
	IUserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(FutureController.class);

	@PostMapping(WebConstantUrl.save)
    public ResponseEntity<?> save(@RequestBody FeatureVo futuresVo,HttpServletRequest request,Principal principal){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		User user=userService.findByUserName(principal.getName());
		Feature Feature=featureMapper.convertModeltoDomain(futuresVo);
		Feature.setCreatedBy(user.getId());
		Feature.setUpdatedBy(user.getId());
		Feature.setEnabledStatus(true);
		Feature=futuresService.save(Feature);
		futuresVo=featureMapper.convertDomaintoModel(Feature);
	   return new ResponseEntity<>(futuresVo,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getAll)
    public ResponseEntity<?> getAll(HttpServletRequest request,@RequestParam(required=false) String featureName,Pageable pageable){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();
		
		Page<Feature> futures=futuresService.getAll(featureName,page);
		
		if (futures.getContent().size() == 0) {
			map.put("futuresVos", futures);
			map.put("total_pages", futures.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", futures.getTotalElements());
			
			return new ResponseEntity<>(map,HttpStatus.OK);
			
		}
		
		List<FeatureVo> futuresVos=futures.stream().map(featureMapper::convertDomaintoModel)
																			       .collect(Collectors.toList());
		
		
		map.put("futuresVos", futuresVos);
		map.put("total_pages", futures.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", futures.getTotalElements());
		
		
		
		return new ResponseEntity<>(map,HttpStatus.OK);
    	
    	}
	
	@GetMapping(WebConstantUrl.getById)
    public ResponseEntity<?> getById(@PathVariable("id") Long id,HttpServletRequest request){
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Feature future=futuresService.getById(id);
		FeatureVo futuresVo=featureMapper.convertDomaintoModel(future);
     return new ResponseEntity<>(futuresVo,HttpStatus.OK);
    	
    	}
	
	@PutMapping(WebConstantUrl.UPDATE)
    public ResponseEntity<?> update(@RequestBody FeatureVo futuresVo,HttpServletRequest request,Principal principal){
		
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		User user=userService.findByUserName(principal.getName());
		Feature feature=featureMapper.convertModeltoDomain(futuresVo);
		feature.setUpdatedBy(user.getId());
		feature.setEnabledStatus(true);
		feature=futuresService.update(feature);
		futuresVo=featureMapper.convertDomaintoModel(feature);
	   return new ResponseEntity<>(futuresVo,HttpStatus.OK);
    	
    	}




	
	
	




	
	
	
	

}
