package com.titan.irgs.sremailescalation.ServiceImp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.controller.EmailServiceImpl;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Domain.Sremailescalation;
import com.titan.irgs.sremailescalation.Repo.SremailescalationRepo;
import com.titan.irgs.sremailescalation.Service.SremailescalationService;


@Service
@EnableScheduling
public class SremailescalationServiceImpl implements SremailescalationService{

	@Autowired
	EmailServiceImpl mailService;

	@Value("${mail.status}")
	private Boolean mailStatus;
	@Autowired
	SremailescalationRepo sremailescalationRepo;
	@Autowired
	IServiceRequestService iServiceRequestService;
	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Override
	public Sremailescalation save(Sremailescalation sremailescalation) {
		
		return sremailescalationRepo.save(sremailescalation);
	}

	@Override
	public Page<Sremailescalation> getAllSremailescalation(String days,String escalationLevel,String verticalId,String verticalName,Pageable pageable) {
		// TODO Auto-generated method stub
		return sremailescalationRepo.findAll((root, query,criteriaBuilder)->{
			List<Predicate> predicates = new ArrayList<>();

			if (days != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("days"),"%" + days + "%")));

			}
			if (escalationLevel != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("escalationLevel"),"%" + escalationLevel + "%")));

			}
			if (verticalId != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + verticalId + "%")));

			}
			if (verticalName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + verticalName + "%")));

			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageable);
		
	}

	@Override
	public Sremailescalation getSremailescalationById(Long id) {
		// TODO Auto-generated method stub
		return sremailescalationRepo.findBySremailescalationId(id);
	}
	@Override
	public Sremailescalation getVerticalId(Long verticalId) {
	//return sremailescalationRepo.findByVerticalId(verticalId);
		return null;
	}
	@Override
	public Sremailescalation getVerticalName(String verticalName) {
	return (Sremailescalation) sremailescalationRepo.findByVerticalName(verticalName);
	}

	
	@Override
	public Sremailescalation update(Sremailescalation sremailescalation) {
		// TODO Auto-generated method stub
		Sremailescalation checkbackEndApis=sremailescalationRepo.getOne(sremailescalation.getSremailescalationId());
		if(checkbackEndApis==null) {
			throw new ResourceNotFoundException("the requested object not found)");
		}
		
		return sremailescalationRepo.save(sremailescalation);
	}
	
	
	@Override
	public Page<Sremailescalation> getAllSremailescalation(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	
	}


