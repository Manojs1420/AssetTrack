package com.titan.irgs.sremailescalation.ServiceImp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.sremailescalation.Domain.SrNotificationEmail;
import com.titan.irgs.sremailescalation.Repo.SrNotificationEmailRepo;
import com.titan.irgs.sremailescalation.Service.SrNotificationEmailService;

@Service
@EnableScheduling
public class SrNotificationEmailServiceImpl implements SrNotificationEmailService {

	@Autowired
	SrNotificationEmailRepo srNotificationEmailRepo;
	@Autowired
	IServiceRequestService iServiceRequestService;
	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Override
	public SrNotificationEmail save(SrNotificationEmail srNotificationEmail) {
		return srNotificationEmailRepo.save(srNotificationEmail);

	}

	@Override
	public SrNotificationEmail getByactivityNameAndVerticalId(String activityName, Long webMasterId) {
		// TODO Auto-generated method stub
		return srNotificationEmailRepo.getByactivityNameAndVerticalId(activityName, webMasterId);
	}

	@SuppressWarnings("serial")
	@Override
	public Page<SrNotificationEmail> getAllSrNotificationEmails(String verticalName, Long verticalId,
			String activityName, String activityId, Pageable page) {
		// TODO Auto-generated method stub
		return srNotificationEmailRepo.findAll(new Specification<SrNotificationEmail>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<SrNotificationEmail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (verticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("verticalName"),"%" + verticalName + "%")));

				}
				if (verticalId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("verticalId"),"%" + verticalId + "%")));

				}
				if (activityName != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("activityName"), "%" + activityName + "%")));

				}
				if (activityId != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("activityId"), "%" + activityId + "%")));

				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}

	@Override
	public SrNotificationEmail getSrNotificationEmailById(Long id) { // TODO Auto-generated method stub
		return srNotificationEmailRepo.findBySrNotificationEmailId(id);
	}

	@Override
	public SrNotificationEmail updateSrNotificationEmail(SrNotificationEmail srNotificationEmail) {
		// TODOAuto-generated method stub
		SrNotificationEmail checkbackEndApis = srNotificationEmailRepo
				.getOne(srNotificationEmail.getSrnotificationemailId());
		if (checkbackEndApis == null) {
			throw new ResourceNotFoundException("the requested object not found)");
		}
		srNotificationEmailRepo.updateByEmailId(checkbackEndApis.getSrnotificationemailId(), srNotificationEmail.getVerticalId(), srNotificationEmail.getVerticalName(), srNotificationEmail.getActivityName());
		return srNotificationEmailRepo.save(srNotificationEmail);
	}

	@Override
	public SrNotificationEmail getVerticalName(String verticalName) {
		return (SrNotificationEmail) srNotificationEmailRepo.findByVerticalName(verticalName);
	}

	@Override
	public Page<SrNotificationEmail> getAllSrNotificationEmails(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SrNotificationEmail findByActivityName(String activityName) {
		// TODO Auto-generated method stub
		return srNotificationEmailRepo.findByActivityName(activityName);
	}

}
