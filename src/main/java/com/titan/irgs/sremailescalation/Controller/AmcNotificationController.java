package com.titan.irgs.sremailescalation.Controller;

/*
 * 
 * Author :Raghu S
 * 
 * 
 * 
 */

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.sremailescalation.Domain.AmcNotification;
import com.titan.irgs.sremailescalation.Domain.AmcNotificationEmailCc;
import com.titan.irgs.sremailescalation.Domain.AmcNotificationEmailTo;
import com.titan.irgs.sremailescalation.Mapper.AmcNotificationMapper;
import com.titan.irgs.sremailescalation.Model.AmcNotificationEmailCcModel;
import com.titan.irgs.sremailescalation.Model.AmcNotificationEmailToModel;
import com.titan.irgs.sremailescalation.Model.AmcNotificationModel;
import com.titan.irgs.sremailescalation.Repo.AmcNotificationEmailCcRepo;
import com.titan.irgs.sremailescalation.Repo.AmcNotificationEmailToRepo;
import com.titan.irgs.sremailescalation.Service.AmcNotificationService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(WebConstantUrl.AMCNOTIFICATION)
@EnableScheduling
public class AmcNotificationController {


	@Autowired
	AmcNotificationMapper amcNotificationMapper;

	@Autowired
	AmcNotificationService amcNotificationService;


	@Autowired
	IUserService iUserService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	AmcNotificationEmailToRepo amcNotificationEmailToRepo;

	@Autowired
	AmcNotificationEmailCcRepo amcNotificationEmailCcRepo;


	@Autowired
	IWebRoleService webRoleService;

	@Autowired
	IWebMaster iWebMaster;
	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String irsg="IRSG"; 

	private static final Logger logger = LoggerFactory.getLogger(AmcNotificationController.class);

	@PostMapping(WebConstantUrl.SaveAmcNotification)
	public ResponseEntity<?> saveAmcNotification(@RequestBody AmcNotificationModel amcNotificationModel,
			HttpServletRequest request, Principal principal) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		AmcNotification amcNotification = null;
//		SrNotificationEmail amcNotifications=amcNotificationService.findByActivityName(amcNotificationModel.getActivityName());

		 WebMaster webMaster=iWebMaster.getById(amcNotificationModel.getVerticalId());
		 
		 AmcNotification exists=amcNotificationService.getByactivityNameAndVerticalId(amcNotificationModel.getActivityName(), webMaster.getWebMasterId());
	     
	     if(exists!=null) {
	    	   
	    	   throw new ResourceAlreadyExitException("activity name "+amcNotificationModel.getActivityName()+" is present");
	    	   
	       }

		amcNotification = amcNotificationMapper.convertModeltoDomain(amcNotificationModel);
		
		amcNotification = amcNotificationService.save(amcNotification);
		
		webMaster.setCreatedOn(new Date());

		for (AmcNotificationEmailToModel amcNotificationEmailToModel : amcNotificationModel.getAmcNotificationEmailToModel()) {
			AmcNotificationEmailTo amcNotificationEmailTo = new AmcNotificationEmailTo();
			amcNotificationEmailTo.setRoleName(amcNotificationEmailToModel.getRoleName());
			amcNotificationEmailTo.setVerticalId(webMaster.getWebMasterId());
			amcNotificationEmailTo.setVerticalName(webMaster.getWebMasterName());
			amcNotificationEmailTo.setAmcNotification(amcNotification);
			amcNotificationEmailTo.setWebRole(webRoleService.getById(amcNotificationEmailToModel.getWebRoleId()));
			amcNotificationEmailTo = amcNotificationEmailToRepo.save(amcNotificationEmailTo);
		}

		for (AmcNotificationEmailCcModel amcNotificationEmailCcModel : amcNotificationModel.getAmcNotificationEmailCcModel()) {
			AmcNotificationEmailCc amcNotificationEmailCc = new AmcNotificationEmailCc();
			amcNotificationEmailCc.setRoleName(amcNotificationEmailCcModel.getRoleName());
			amcNotificationEmailCc.setVerticalId(webMaster.getWebMasterId());
			amcNotificationEmailCc.setVerticalName(webMaster.getWebMasterName());
			amcNotificationEmailCc.setAmcNotification(amcNotification);
			amcNotificationEmailCc.setWebRole(webRoleService.getById(amcNotificationEmailCcModel.getWebRoleId()));
			amcNotificationEmailCc = amcNotificationEmailCcRepo.save(amcNotificationEmailCc);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping(WebConstantUrl.GetAllAMCNOTIFICATION)
	public ResponseEntity<?> getAllAmcNotifications(@RequestParam(required = false) String verticalName,@RequestParam(required = false) Long verticalId,@RequestParam(required = false) String activityId,
			@RequestParam(required = false) String activityName,Pageable pageable, Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Map<String, Object> map = new HashMap<>();

		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		User user = userRepo.findByUsername(principal.getName());

		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(irsg))) {
			// store name and username are same
			
			verticalName = user.getRole().getWebMaster().getWebMasterName();

		}

		List<AmcNotificationModel> amcNotificationModel = new ArrayList<>();
		Page<AmcNotification> amcNotification = amcNotificationService.getAllAmcNotifications(verticalName,verticalId,activityId,activityName, page);

		if (amcNotification.getContent().size() == 0) {
			map.put("roleModels", amcNotification);
			map.put("total_pages", amcNotification.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", amcNotification.getTotalElements());
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		amcNotificationModel = amcNotification.stream().map(amcNotificationMapper::convertDomainToModel)
				.collect(Collectors.toList());
		map.put("roleModels", amcNotificationModel);
		map.put("total_pages", amcNotification.getTotalPages());
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", amcNotification.getTotalElements());

		return new ResponseEntity<>(map, HttpStatus.OK);

	}
	
	
	  @GetMapping(WebConstantUrl.GetByAmcNotificationId) 
	  public ResponseEntity<?> getAmcNotificationById(@PathVariable("id") Long verticalId, HttpServletRequest request) {

			logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

			AmcNotification amcNotification = amcNotificationService.getAmcNotificationById(verticalId);

			AmcNotificationModel amcNotificationModel = amcNotificationMapper.convertDomainToModel(amcNotification);

			return new ResponseEntity<>(amcNotificationModel, HttpStatus.OK);

		}


	
	  @PutMapping(WebConstantUrl.UpdateAmcNotification)  
	  public ResponseEntity<?> updateAmcNotification(@RequestBody AmcNotificationModel amcNotificationModel, HttpServletRequest
	  request, Principal principal) {
	  
	  logger.info("getConfirm: Received request: " +request.getRequestURL().toString() + ((request.getQueryString() == null) ? "": "?" + request.getQueryString().toString())); 
	  AmcNotification sr = amcNotificationService.getAmcNotificationById(amcNotificationModel.getAmcnotificationId()); 
	 // sremailescalationRepo.deleteByActivityName(sr.getSrnotificationemailId());
	  
	  for (AmcNotificationEmailTo es : sr.getAmcNotificationEmailTo()) {
		  amcNotificationEmailToRepo.deleteByAmcnotificationemailtoId(es.getAmcnotificationemailtoId()); }
	  for (AmcNotificationEmailCc es : sr.getAmcNotificationEmailCc()) {
		  amcNotificationEmailCcRepo.deleteByAmcnotificationemailccId(es.getAmcnotificationemailccId()); } 
	  WebMaster webMaster =iWebMaster.getById(amcNotificationModel.getVerticalId());
	  AmcNotification exists=amcNotificationService.getByactivityNameAndVerticalId(amcNotificationModel.getActivityName(), webMaster.getWebMasterId());

		/*
		 * if(exists!=null) {
		 * 
		 * throw new
		 * ResourceAlreadyExitException("activity name "+amcNotificationModel.
		 * getActivityName()+" is present");
		 * 
		 * }
		 */
		 
	     
	  AmcNotification amcNotification =amcNotificationMapper.convertModeltoDomain(amcNotificationModel);

	  amcNotification.setVerticalName(webMaster.getWebMasterName());
	  amcNotification =
			  amcNotificationService.updateAmcNotification(amcNotification);
	  
		for (AmcNotificationEmailToModel amcNotificationEmailToModel : amcNotificationModel.getAmcNotificationEmailToModel()) {
			AmcNotificationEmailTo amcNotificationEmailTo = null;
			if (amcNotificationEmailTo == null) {
				amcNotificationEmailTo = new AmcNotificationEmailTo();
				amcNotificationEmailTo.setRoleName(amcNotificationEmailToModel.getRoleName());
				amcNotificationEmailTo.setVerticalName(webMaster.getWebMasterName());
				amcNotificationEmailTo.setVerticalId(webMaster.getWebMasterId());
				amcNotificationEmailTo.setAmcNotification(amcNotification);
				amcNotificationEmailTo.setWebRole(webRoleService.getById(amcNotificationEmailToModel.getWebRoleId()));
				amcNotificationEmailTo = amcNotificationEmailToRepo.save(amcNotificationEmailTo);
			}
		}

		for (AmcNotificationEmailCcModel amcNotificationEmailCcModel : amcNotificationModel.getAmcNotificationEmailCcModel()) {
			AmcNotificationEmailCc amcNotificationEmailCc = null;
			if (amcNotificationEmailCc == null) {
				amcNotificationEmailCc = new AmcNotificationEmailCc();
				amcNotificationEmailCc.setRoleName(amcNotificationEmailCcModel.getRoleName());
				amcNotificationEmailCc.setVerticalName(webMaster.getWebMasterName());
				amcNotificationEmailCc.setVerticalId(webMaster.getWebMasterId());
				amcNotificationEmailCc.setAmcNotification(amcNotification);
				amcNotificationEmailCc.setWebRole(webRoleService.getById(amcNotificationEmailCcModel.getWebRoleId()));
				amcNotificationEmailCc = amcNotificationEmailCcRepo.save(amcNotificationEmailCc);
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
