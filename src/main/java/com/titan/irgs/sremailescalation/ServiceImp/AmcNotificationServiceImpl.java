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
import com.titan.irgs.sremailescalation.Domain.AmcNotification;
import com.titan.irgs.sremailescalation.Repo.AmcNotificationRepo;
import com.titan.irgs.sremailescalation.Service.AmcNotificationService;

@Service
@EnableScheduling
public class AmcNotificationServiceImpl implements AmcNotificationService {

	@Autowired
	AmcNotificationRepo srNotificationEmailRepo;
	@Autowired
	IServiceRequestService iServiceRequestService;
	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Override
	public AmcNotification save(AmcNotification srNotificationEmail) {
		return srNotificationEmailRepo.save(srNotificationEmail);

	}

	@Override
	public AmcNotification getByactivityNameAndVerticalId(String activityName, Long webMasterId) {
		// TODO Auto-generated method stub
		return srNotificationEmailRepo.getByactivityNameAndVerticalId(activityName, webMasterId);
	}

	@SuppressWarnings("serial")
	@Override
	public Page<AmcNotification> getAllAmcNotifications(String verticalName, Long verticalId,
			String activityName, String activityId, Pageable page) {
		// TODO Auto-generated method stub
		return srNotificationEmailRepo.findAll(new Specification<AmcNotification>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<AmcNotification> root, CriteriaQuery<?> query,
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
	public AmcNotification getAmcNotificationById(Long id) { // TODO Auto-generated method stub
		return srNotificationEmailRepo.findByAmcNotificationId(id);
	}

	@Override
	public AmcNotification updateAmcNotification(AmcNotification srNotificationEmail) {
		// TODOAuto-generated method stub
		AmcNotification checkbackEndApis = srNotificationEmailRepo
				.getOne(srNotificationEmail.getAmcnotificationId());
		if (checkbackEndApis == null) {
			throw new ResourceNotFoundException("the requested object not found)");
		}
		srNotificationEmailRepo.updateByEmailId(checkbackEndApis.getAmcnotificationId(), srNotificationEmail.getVerticalId(), srNotificationEmail.getVerticalName(), srNotificationEmail.getActivityName());
		return srNotificationEmailRepo.save(srNotificationEmail);
	}

	@Override
	public AmcNotification getVerticalName(String verticalName) {
		return (AmcNotification) srNotificationEmailRepo.findByVerticalName(verticalName);
	}

	@Override
	public Page<AmcNotification> getAllAmcNotifications(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmcNotification findByActivityName(String activityName) {
		// TODO Auto-generated method stub
		return srNotificationEmailRepo.findByActivityName(activityName);
	}

}
