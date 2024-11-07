package com.titan.irgs.accessPolicy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.titan.irgs.accessPolicy.domain.BackEndApis;
import com.titan.irgs.accessPolicy.repository.BackEndApisRepo;
import com.titan.irgs.accessPolicy.service.BackendApiService;
import com.titan.irgs.customException.ResourceNotFoundException;

@Service
public class BackendApiServiceImpl implements BackendApiService{

	@Autowired
	BackEndApisRepo backEndApisRepo;
	
	@Override
	public BackEndApis save(BackEndApis backEndApis) {
		
		return backEndApisRepo.save(backEndApis);
	}

	@SuppressWarnings("serial")
	@Override
	public Page<BackEndApis> getAll(String url,String featureName, Pageable page) {
		// TODO Auto-generated method stub
		return backEndApisRepo.findAll((root,query,criteriaBuilder)-> {
				List<Predicate> predicates = new ArrayList<>();

				if (url != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("backEndApiIdUrl"),"%" + url + "%")));

					}
				if (featureName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("feature").get("featureName"),"%" + featureName + "%")));

				}
					
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		, page);
	}

	@Override
	public BackEndApis getById(Long id) {
		// TODO Auto-generated method stub
		return backEndApisRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("the requested id {} not found)" +id));
	}

	@Override
	public BackEndApis update(BackEndApis backEndApis) {
		// TODO Auto-generated method stub
		BackEndApis checkbackEndApis=backEndApisRepo.getOne(backEndApis.getBackEndApiId());
		if(checkbackEndApis==null) {
			throw new ResourceNotFoundException("the requested object not found)");
		}
		
		return backEndApisRepo.save(backEndApis);
	}

	

	@Override
	public BackEndApis findByBackEndApiIdUrl(String backEndApiIdUrl) {
		// TODO Auto-generated method stub
		return backEndApisRepo.findByBackEndApiIdUrl(backEndApiIdUrl);
	}

	@Override
	public Page<BackEndApis> getAll(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
