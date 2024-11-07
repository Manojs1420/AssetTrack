package com.titan.irgs.accessPolicy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.titan.irgs.accessPolicy.domain.Feature;
import com.titan.irgs.accessPolicy.repository.FeatureRepo;
import com.titan.irgs.accessPolicy.service.FeatureService;
import com.titan.irgs.customException.ResourceNotFoundException;

@Service
public class FeatureServiceImpl implements FeatureService{

	@Autowired
	FeatureRepo futuresRepo;
	
	@Override
	public Page<Feature> getAll(String featureName, Pageable page) {
		// TODO Auto-generated method stub
		return futuresRepo.findAll((root,query, criteriaBuilder)-> {
			List<Predicate> predicates = new ArrayList<>();
			if (featureName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(
						root.get("featureName"),
						"%" + featureName + "%")));

			}
			query.orderBy(criteriaBuilder.asc(root.get("featureName")));
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		}
		, page);
	}

	@Override
	public Feature save(Feature futures) {
		// TODO Auto-generated method stub
		return futuresRepo.save(futures);
	}

	@Override
	public Feature getById(Long id) {
		// TODO Auto-generated method stub
		return futuresRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("The id is not found"));
	}

	@Override
	public Feature update(Feature feature) {
		
		if(futuresRepo.getOne(feature.getFeatureId())==null) {
			throw new ResourceNotFoundException("The id is not found");
		}
		
		return futuresRepo.save(feature);
	}

	

}
