package com.titan.irgs.sremailescalation.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.titan.irgs.configuration.ConfigurationService;
import com.titan.irgs.configuration.EmailConfiguration;
import com.titan.irgs.serviceRequest.controller.EmailServiceImpl;
import com.titan.irgs.serviceRequest.mapper.ServiceRequestMapper;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Domain.Escalation;
import com.titan.irgs.sremailescalation.Domain.Escalation1;
import com.titan.irgs.sremailescalation.Domain.Sremailescalation;
import com.titan.irgs.sremailescalation.Mapper.SremailescalationMapper;
import com.titan.irgs.sremailescalation.Model.EscalationModel;
import com.titan.irgs.sremailescalation.Model.EscalationModel1;
import com.titan.irgs.sremailescalation.Model.SremailescalationModel;
import com.titan.irgs.sremailescalation.Repo.EscalationRepo;
import com.titan.irgs.sremailescalation.Repo.EscalationRepo1;
import com.titan.irgs.sremailescalation.Repo.SremailescalationRepo;
import com.titan.irgs.sremailescalation.Service.SremailescalationService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webRole.service.IWebRoleService;

@RestController
@RequestMapping(WebConstantUrl.SREMAILESCALATION)
@EnableScheduling
public class SremailescalationController {

	@Autowired
	EmailServiceImpl mailService;
	
	@Autowired
	EmailConfiguration emailConfiguration;

	@Value("${mail.status}")
	private Boolean mailStatus;
	
	@Autowired
    private ConfigurationService configurationService;

	@Autowired
	SremailescalationMapper sremailescalationMapper;

	@Autowired
	SremailescalationService sremailescalationService;

	@Autowired
	ServiceRequestMapper serviceRequestMapper;

	@Autowired
	IServiceRequestService iServiceRequestService;

	@Autowired
	IUserService iUserService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	EscalationRepo escalationRepo;

	@Autowired
	EscalationRepo1 escalationRepo1;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Autowired
	SremailescalationRepo sremailescalationRepo;

	@Autowired
	IWebRoleService webRoleService;

	@Autowired
	IWebMaster iWebMaster;
	private final static String OPEN = "OPEN";
	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String irsg="IRSG"; 


	private static final Logger logger = LoggerFactory.getLogger(SremailescalationController.class);

	@PostMapping(WebConstantUrl.SaveSremailescalation)
	public ResponseEntity<?> saveSremailescalation(@RequestBody SremailescalationModel sremailescalationModel,
			HttpServletRequest request, Principal principal) {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Sremailescalation sremailescalation = null;

		sremailescalation = sremailescalationMapper.convertModeltoDomain(sremailescalationModel);

		sremailescalation = sremailescalationService.save(sremailescalation);
		WebMaster webMaster = iWebMaster.getById(sremailescalationModel.getVerticalId());

		webMaster.setCreatedOn(new Date());

		for (EscalationModel escalationVo : sremailescalationModel.getEscalation()) {
			Escalation escalation = new Escalation();
			escalation.setRoleName(escalationVo.getRoleName());
			escalation.setVerticalName(webMaster.getWebMasterName());
			escalation.setSremailescalation(sremailescalation);
			escalation.setVerticalId(webMaster.getWebMasterId());
			escalation.setWebRole(webRoleService.getById(escalationVo.getWebRoleId()));
			escalation = escalationRepo.save(escalation);
		}

		for (EscalationModel1 escalationVo1 : sremailescalationModel.getEscalation1()) {
			Escalation1 escalation1 = new Escalation1();
			escalation1.setRoleName(escalationVo1.getRoleName());
			escalation1.setSremailescalation1(sremailescalation);
			escalation1.setVerticalName(webMaster.getWebMasterName());
			escalation1.setVerticalId(webMaster.getWebMasterId());
			escalation1.setWebRole(webRoleService.getById(escalationVo1.getWebRoleId()));
			escalation1 = escalationRepo1.save(escalation1);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping(WebConstantUrl.GetAllSremailescalation)
	public ResponseEntity<?> getAllSremailescalation(
			@RequestParam(required = false) String days,
			@RequestParam(required = false) String escalationLevel,
			@RequestParam(required = false) String verticalId,
			@RequestParam(required = false) String verticalName, 


			Pageable pageable, Principal principal, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();

		List<SremailescalationModel> sremailescalationModel = new ArrayList<>();
		User user = userRepo.findByUsername(principal.getName());
		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
			verticalName = user.getRole().getWebMaster().getWebMasterName();

		}
		Page<Sremailescalation> sremailescalation = sremailescalationService.getAllSremailescalation(days, escalationLevel,verticalId, verticalName, page);



		if (sremailescalation.getContent().size() == 0) {
			map.put("sremailescalationModel", sremailescalationModel);
			map.put("total_pages", sremailescalation.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", sremailescalation.getTotalElements());
			return new ResponseEntity<>(map, HttpStatus.OK);
		}else {
			sremailescalation.forEach(sremailescalations -> {
				sremailescalationModel.add(sremailescalationMapper.convertDomainToModel(sremailescalations));
			});
		
		map.put("sremailescalationModel", sremailescalationModel);
		map.put("total_pages", sremailescalation.getTotalPages());
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", sremailescalation.getTotalElements());

		return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping(WebConstantUrl.GetSremailescalationById)
	public ResponseEntity<?> getSremailescalationById(@PathVariable("id") Long verticalId, HttpServletRequest request) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

		Sremailescalation sremailescalation = sremailescalationService.getSremailescalationById(verticalId);

		SremailescalationModel sremailescalationModel = sremailescalationMapper.convertDomainToModel(sremailescalation);

		return new ResponseEntity<>(sremailescalationModel, HttpStatus.OK);

	}

	@GetMapping(WebConstantUrl.GetByVerticalName)
	public ResponseEntity<List<Sremailescalation>> getByVerticalName(@RequestParam String verticalName) {
		return new ResponseEntity<List<Sremailescalation>>(sremailescalationRepo.findByVerticalName(verticalName),
				HttpStatus.OK);
	}

	@GetMapping(WebConstantUrl.GetByEscalationLevel)
	public ResponseEntity<List<Sremailescalation>> getByEscalationLevel(@RequestParam String escalationLevel) {
		return new ResponseEntity<List<Sremailescalation>>(sremailescalationRepo.findByEscalationLevel(escalationLevel),
				HttpStatus.OK);
	}

	@GetMapping(WebConstantUrl.GetByDays)
	public ResponseEntity<List<Sremailescalation>> getByDays(@RequestParam Long days) {
		return new ResponseEntity<List<Sremailescalation>>(sremailescalationRepo.findByDays(days), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@PostMapping(WebConstantUrl.NOTIFY_SRESCALATION)
	ResponseEntity notifySrEscalation(HttpServletRequest request, Principal principal) {
		try {
			logger.info("send email based on escalation level" + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));

			User user = userRepo.findByUsername(principal.getName());
			
			emailConfiguration.SR_Escalation_Email_Scheduler();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>("Service Request Escalation notification mails sent successfully.", HttpStatus.OK);
	}
	

	@PutMapping(WebConstantUrl.UpdateSremailescalation)

	public ResponseEntity<?> update(@RequestBody SremailescalationModel sremailescalationModel,
			HttpServletRequest request, Principal principal) {

		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Sremailescalation sr = sremailescalationService
				.getSremailescalationById(sremailescalationModel.getSremailescalationId());
		for (Escalation es : sr.getEscalations()) {
			escalationRepo.deleteByEscalationId(es.getEscalationId());
		}
		for (Escalation1 es : sr.getEscalations1()) {
			escalationRepo1.deleteByEscalationId1(es.getEscalationId1());
		}
		Sremailescalation sremailescalation = sremailescalationMapper.convertModeltoDomain(sremailescalationModel);
		WebMaster webMaster = iWebMaster.getById(sremailescalationModel.getVerticalId());

		sremailescalation = sremailescalationService.update(sremailescalation);

		for (EscalationModel escalationVo : sremailescalationModel.getEscalation()) {
			Escalation escalation = null;
			System.out.println(sremailescalation.getEscalationLevel());
			if (escalation == null) {
				escalation = new Escalation();
				escalation.setRoleName(escalationVo.getRoleName());
				escalation.setSremailescalation(sremailescalation);
				escalation.setVerticalName(webMaster.getWebMasterName());
				escalation.setVerticalId(webMaster.getWebMasterId());
				escalation.setWebRole(webRoleService.getById(escalationVo.getWebRoleId()));
				escalation = escalationRepo.save(escalation);
			}
		}

		for (EscalationModel1 escalationVo1 : sremailescalationModel.getEscalation1()) {
			Escalation1 escalation1 = null;
			System.out.println(sremailescalation.getEscalationLevel());
			if (escalation1 == null) {
				escalation1 = new Escalation1();
				escalation1.setRoleName(escalationVo1.getRoleName());
				escalation1.setSremailescalation(sremailescalation);
				escalation1.setVerticalName(webMaster.getWebMasterName());
				escalation1.setVerticalId(webMaster.getWebMasterId());
				escalation1.setWebRole(webRoleService.getById(escalationVo1.getWebRoleId()));
				escalation1 = escalationRepo1.save(escalation1);
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
