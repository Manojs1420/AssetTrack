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
import com.titan.irgs.serviceRequest.mapper.ServiceRequestMapper;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Domain.SrNotificationEmail;
import com.titan.irgs.sremailescalation.Domain.SrNotificationEmailCc;
import com.titan.irgs.sremailescalation.Domain.SrNotificationEmailTo;
import com.titan.irgs.sremailescalation.Mapper.SrNotificationEmailMapper;
import com.titan.irgs.sremailescalation.Model.SrNotificationEmailCcModel;
import com.titan.irgs.sremailescalation.Model.SrNotificationEmailModel;
import com.titan.irgs.sremailescalation.Model.SrNotificationEmailToModel;
import com.titan.irgs.sremailescalation.Repo.SrNotificationEmailCcRepo;
import com.titan.irgs.sremailescalation.Repo.SrNotificationEmailRepo;
import com.titan.irgs.sremailescalation.Repo.SrNotificationEmailToRepo;
import com.titan.irgs.sremailescalation.Service.SrNotificationEmailService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(WebConstantUrl.SRNOTIFICATIONEMAIL)
@EnableScheduling
public class SrNotificationEmailController {


	@Autowired
	SrNotificationEmailMapper srNotificationEmailMapper;

	@Autowired
	SrNotificationEmailService srNotificationEmailService;

	@Autowired
	ServiceRequestMapper serviceRequestMapper;

	@Autowired
	IServiceRequestService iServiceRequestService;

	@Autowired
	IUserService iUserService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	SrNotificationEmailToRepo srNotificationEmailToRepo;

	@Autowired
	SrNotificationEmailCcRepo srNotificationEmailCcRepo;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Autowired
	SrNotificationEmailRepo sremailescalationRepo;

	@Autowired
	IWebRoleService webRoleService;

	@Autowired
	IWebMaster iWebMaster;

	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String irsg="IRSG"; 
	private static final Logger logger = LoggerFactory.getLogger(SrNotificationEmailController.class);

	@PostMapping(WebConstantUrl.SaveNotification)
	public ResponseEntity<?> saveSrNotificationEmail(@RequestBody SrNotificationEmailModel srNotificationEmailModel,
			HttpServletRequest request, Principal principal) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		SrNotificationEmail srNotificationEmail = null;
//		SrNotificationEmail srNotificationEmails=srNotificationEmailService.findByActivityName(srNotificationEmailModel.getActivityName());

		 WebMaster webMaster=iWebMaster.getById(srNotificationEmailModel.getVerticalId());
		 SrNotificationEmail exists=srNotificationEmailService.getByactivityNameAndVerticalId(srNotificationEmailModel.getActivityName(), webMaster.getWebMasterId());
	     
	     if(exists!=null) {
	    	   
	    	   throw new ResourceAlreadyExitException("activity name "+srNotificationEmailModel.getActivityName()+" is present");
	    	   
	       }

		srNotificationEmail = srNotificationEmailMapper.convertModeltoDomain(srNotificationEmailModel);
		
		srNotificationEmail = srNotificationEmailService.save(srNotificationEmail);
		
		webMaster.setCreatedOn(new Date());

		for (SrNotificationEmailToModel srNotificationEmailToModel : srNotificationEmailModel.getSrNotificationEmailToModel()) {
			SrNotificationEmailTo srNotificationEmailTo = new SrNotificationEmailTo();
			srNotificationEmailTo.setRoleName(srNotificationEmailToModel.getRoleName());
			srNotificationEmailTo.setVerticalId(webMaster.getWebMasterId());
			srNotificationEmailTo.setVerticalName(webMaster.getWebMasterName());
			srNotificationEmailTo.setSrNotificationEmail(srNotificationEmail);
			srNotificationEmailTo.setWebRole(webRoleService.getById(srNotificationEmailToModel.getWebRoleId()));
			srNotificationEmailTo = srNotificationEmailToRepo.save(srNotificationEmailTo);
		}

		for (SrNotificationEmailCcModel srNotificationEmailCcModel : srNotificationEmailModel.getSrNotificationEmailCcModel()) {
			SrNotificationEmailCc srNotificationEmailCc = new SrNotificationEmailCc();
			srNotificationEmailCc.setRoleName(srNotificationEmailCcModel.getRoleName());
			srNotificationEmailCc.setVerticalId(webMaster.getWebMasterId());
			srNotificationEmailCc.setVerticalName(webMaster.getWebMasterName());
			srNotificationEmailCc.setSrNotificationEmail(srNotificationEmail);
			srNotificationEmailCc.setWebRole(webRoleService.getById(srNotificationEmailCcModel.getWebRoleId()));
			srNotificationEmailCc = srNotificationEmailCcRepo.save(srNotificationEmailCc);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping(WebConstantUrl.GetAllSRNOTIFICATIONEMAIL)
	public ResponseEntity<?> getAllSrNotificationEmails(@RequestParam(required = false) String verticalName,@RequestParam(required = false) Long verticalId,@RequestParam(required = false) String activityId,
			@RequestParam(required = false) String activityName,Pageable pageable, Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Map<String, Object> map = new HashMap<>();
		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		User user = userRepo.findByUsername(principal.getName());

		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
			verticalName = user.getRole().getWebMaster().getWebMasterName();

		}

		List<SrNotificationEmailModel> srNotificationEmailModel = new ArrayList<>();
		Page<SrNotificationEmail> srNotificationEmail = srNotificationEmailService.getAllSrNotificationEmails(verticalName,verticalId,activityId,activityName, page);

		if (srNotificationEmail.getContent().size() == 0) {
			map.put("roleModels", srNotificationEmail);
			map.put("total_pages", srNotificationEmail.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", srNotificationEmail.getTotalElements());
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		srNotificationEmailModel = srNotificationEmail.stream().map(srNotificationEmailMapper::convertDomainToModel)
				.collect(Collectors.toList());
		map.put("roleModels", srNotificationEmailModel);
		map.put("total_pages", srNotificationEmail.getTotalPages());
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", srNotificationEmail.getTotalElements());

		return new ResponseEntity<>(map, HttpStatus.OK);

	}
	
	
	  @GetMapping(WebConstantUrl.GetBySrNotificationId) 
	  public ResponseEntity<?> getSrNotificationEmailById(@PathVariable("id") Long verticalId, HttpServletRequest request) {

			logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

			SrNotificationEmail srNotificationEmail = srNotificationEmailService.getSrNotificationEmailById(verticalId);

			SrNotificationEmailModel srNotificationEmailModel = srNotificationEmailMapper.convertDomainToModel(srNotificationEmail);

			return new ResponseEntity<>(srNotificationEmailModel, HttpStatus.OK);

		}


	
	  @PutMapping(WebConstantUrl.UpdateNotification)  
	  public ResponseEntity<?> updateSrNotificationEmail(@RequestBody SrNotificationEmailModel srNotificationEmailModel, HttpServletRequest
	  request, Principal principal) {
	  
	  logger.info("getConfirm: Received request: " +request.getRequestURL().toString() + ((request.getQueryString() == null) ? "": "?" + request.getQueryString().toString())); 
	  SrNotificationEmail sr = srNotificationEmailService.getSrNotificationEmailById(srNotificationEmailModel.getSrnotificationemailId()); 
	 // sremailescalationRepo.deleteByActivityName(sr.getSrnotificationemailId());
	  
	  for (SrNotificationEmailTo es : sr.getSrNotificationEmailTo()) {
		  srNotificationEmailToRepo.deleteBySrnotificationemailtoId(es.getSrnotificationemailtoId()); }
	  for (SrNotificationEmailCc es : sr.getSrNotificationEmailCc()) {
		  srNotificationEmailCcRepo.deleteBySrnotificationemailccId(es.getSrnotificationemailccId()); } 
	  WebMaster webMaster =iWebMaster.getById(srNotificationEmailModel.getVerticalId());
	  SrNotificationEmail exists=srNotificationEmailService.getByactivityNameAndVerticalId(srNotificationEmailModel.getActivityName(), webMaster.getWebMasterId());

		/*
		 * if(exists!=null) {
		 * 
		 * throw new
		 * ResourceAlreadyExitException("activity name "+srNotificationEmailModel.
		 * getActivityName()+" is present");
		 * 
		 * }
		 */
		 
	     
	  SrNotificationEmail srNotificationEmail =srNotificationEmailMapper.convertModeltoDomain(srNotificationEmailModel);

	  srNotificationEmail.setVerticalName(webMaster.getWebMasterName());
	  srNotificationEmail =
			  srNotificationEmailService.updateSrNotificationEmail(srNotificationEmail);
	  
		for (SrNotificationEmailToModel srNotificationEmailToModel : srNotificationEmailModel.getSrNotificationEmailToModel()) {
			SrNotificationEmailTo srNotificationEmailTo = null;
			if (srNotificationEmailTo == null) {
				srNotificationEmailTo = new SrNotificationEmailTo();
				srNotificationEmailTo.setRoleName(srNotificationEmailToModel.getRoleName());
				srNotificationEmailTo.setVerticalName(webMaster.getWebMasterName());
				srNotificationEmailTo.setVerticalId(webMaster.getWebMasterId());
				srNotificationEmailTo.setSrNotificationEmail(srNotificationEmail);
				srNotificationEmailTo.setWebRole(webRoleService.getById(srNotificationEmailToModel.getWebRoleId()));
				srNotificationEmailTo = srNotificationEmailToRepo.save(srNotificationEmailTo);
			}
		}

		for (SrNotificationEmailCcModel srNotificationEmailCcModel : srNotificationEmailModel.getSrNotificationEmailCcModel()) {
			SrNotificationEmailCc srNotificationEmailCc = null;
			if (srNotificationEmailCc == null) {
				srNotificationEmailCc = new SrNotificationEmailCc();
				srNotificationEmailCc.setRoleName(srNotificationEmailCcModel.getRoleName());
				srNotificationEmailCc.setVerticalName(webMaster.getWebMasterName());
				srNotificationEmailCc.setVerticalId(webMaster.getWebMasterId());
				srNotificationEmailCc.setSrNotificationEmail(srNotificationEmail);
				srNotificationEmailCc.setWebRole(webRoleService.getById(srNotificationEmailCcModel.getWebRoleId()));
				srNotificationEmailCc = srNotificationEmailCcRepo.save(srNotificationEmailCc);
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
