package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.AssetSpecification;
import com.titan.irgs.master.repository.AssetSpecificationRepository;
import com.titan.irgs.master.service.IAssetSpecificationService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;


@Service
public class AssetSpecificationService implements IAssetSpecificationService
{

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AssetSpecificationRepository assetSpecificationRepository;
	@Autowired
	private WebMasterRepo webMasterRepo;
	

	@SuppressWarnings("serial")
	@Override
	public Page<AssetSpecification> getAllAssetSpecification(String assetSpecipicationName, String webMasterId,String webMasterName, Pageable page) {
		
		return assetSpecificationRepository.findAll((root, query,criteriaBuilder)->{
			List<Predicate> predicates = new ArrayList<>();

			if (assetSpecipicationName != null) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("assetSpecificationName"),"%" + assetSpecipicationName + "%")));

			}


				if (webMasterId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + webMasterId + "%")));

				}
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}, page);
				
			}
    

	@Override
	public AssetSpecification getAssetSpecificationById(Long id) {
		// TODO Auto-generated method stub
		return assetSpecificationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The id not found"));
	}

	@Override
	public AssetSpecification findByAssetSpecificationName(String assetSpecificationName) {
		// TODO Auto-generated method stub
		return assetSpecificationRepository.findByAssetSpecificationName(assetSpecificationName);
	}

	@Override
	public AssetSpecification save(AssetSpecification assetSpecification) {
		// TODO Auto-generated method stub
		return assetSpecificationRepository.save(assetSpecification);
	}

	@Override
	public AssetSpecification update(AssetSpecification assetSpecification) {
		// TODO Auto-generated method stub
		AssetSpecification assetSpecification1=assetSpecificationRepository.getOne(assetSpecification.getAssetSpecificationId());
		WebMaster webMaster = webMasterRepo.findByWebMasterId(assetSpecification.getWebMaster().getWebMasterId());
		assetSpecification1.setWebMaster(webMaster);
		assetSpecification1.setAssetSpecificationName(assetSpecification.getAssetSpecificationName());
		assetSpecification1.setSerialNoRequired(assetSpecification.getSerialNoRequired());
		
		if(assetSpecification1==null) {
			throw new ResourceNotFoundException("The id not found");
			
		}
		return assetSpecificationRepository.save(assetSpecification1);
	}

	@Override
	public void deleteById(Long assetId) {
		// TODO Auto-generated method stub
		assetSpecificationRepository.deleteById(assetId);
		
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return assetSpecificationRepository.getAllForExcel();
	}

	@Override
	public List<Object[]> getAllForExcel(Long id) {
		// TODO Auto-generated method stub
		return assetSpecificationRepository.getAllForExcel(id);
	}


	@Override
	public List<AssetSpecification> getAssetSpecByVerticalId(Long id) {
		// TODO Auto-generated method stub
		return assetSpecificationRepository.getEquipmentByTypeVerticalId(id);
	}
	
	
	
}
