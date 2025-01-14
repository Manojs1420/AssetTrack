package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.domain.Model;
import com.titan.irgs.master.repository.BrandRepository;
import com.titan.irgs.master.repository.ModelRepository;
import com.titan.irgs.master.service.IModelService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * Method Implementation for IModelService method
 * @author 
 *
 */
@Service
public class ModelService implements IModelService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private WebMasterRepo webMasterRepo;
	@Autowired
	private BrandRepository brandRepo;
    
	/**
	 * getAllModel -> Method
	 * @param ->none
	 * @return list of saved model entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Model> getAllModel(String modelName, String modelNo, String brandName,String webMasterId,String webMasterName,String verticalName, Pageable page) {
		
		return modelRepository.findAll(new Specification<Model>() {
			
			@Override
			public Predicate toPredicate(Root<Model> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (modelName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("modelName"),"%" + modelName + "%")));

				}

				if (modelNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("modelNo"),"%" + modelNo + "%")));

				}
				
				if (brandName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("brand").get("brandName"),"%" + brandName + "%")));

				}
				if (webMasterId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + webMasterId + "%")));

				}
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

				}
				
				if (verticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + verticalName + "%")));

				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		},page);
	}
    
	/**
	 * getModelById -> Method
	 * @param modelId
	 * @return saved model entity
	 */
	@Override
	public Model getModelById(Long modelId) {
		
		Model model = modelRepository.findById(modelId).orElseThrow(()->new EntityNotFoundException("model with modelId " + modelId + " not found"));
		
		return model;
	}
    
	/**
	 * saveModel ->Method
	 * @param model entity
	 * @return saved model entity
	 */
	@Override
	public Model saveModel(Model model) {
		model.setCreatedOn(new Date());
		model.setUpdatedOn(null);
		return modelRepository.save(model);
	}
    
	/**
	 * updateModel ->Method
	 * @param model entity
	 * @return updated model entity
	 */
	@Override
	public Model updateModel(Model model) {
		model.setUpdatedOn(new Date());
		Model model1 = modelRepository.findById(model.getModelId()).orElseThrow(()->new EntityNotFoundException("model with modelId " + model.getModelId()+ " not found"));
		WebMaster webMaster = webMasterRepo.findByWebMasterId(model.getWebMaster().getWebMasterId());
		Brand brand=brandRepo.findById(model.getBrand().getBrandId()).get();
		model1.setModelName(model.getModelName());
		model1.setModelNo(model.getModelNo());
		model1.setWebMaster(webMaster);
		model1.setBrand(brand);
		model1.setRemarks(model.getRemarks());

		return modelRepository.save(model1);
	}
    
	/**
	 * deleteModelById ->Method
	 * @param modelId
	 * @return none
	 */
	@Override
	public void deleteModelById(Long modelId) {
		Model model = modelRepository.findById(modelId).orElseThrow(()->new EntityNotFoundException("model with modelId " + modelId + " not found"));
	
		modelRepository.deleteById(modelId);

	}

	@Override
	public Model findByModelName(String modelName) {
		
		Model model = modelRepository.findByModelName(modelName);
		/*if (model == null) {
			logger.error("model with modelName {} not found", modelName);
			throw new EntityNotFoundException("model with modelName " + modelName + " not found");
		}*/
		return model;
	} 
	
	@Override
	public Model findByModelNo(String modelNo) {
		Model model = modelRepository.findByModelNo(modelNo);
		/*if (model == null) {
			logger.error("model with modelNo {} not found", modelNo);
			throw new EntityNotFoundException("model with modelNo " + modelNo + " not found");
		}*/
		return model;
	}
	@Override
	public Model findByModelNoAndWebMaster(String modelNo,Long webMasterId) {
		Model model = modelRepository.findByModelNoAndWebMasterWebMasterId(modelNo,webMasterId);
		/*if (model == null) {
			logger.error("model with modelNo {} not found", modelNo);
			throw new EntityNotFoundException("model with modelNo " + modelNo + " not found");
		}*/
		return model;
	}

	@Override
	public List<Model> getAllModelOnBrandId(Long brandId) {
		return modelRepository.findModelByBrandId(brandId);
	}

	@Override
	public Model findByModelNoAndBrandBrandName(String modelNo, String brandName) {
		
		return modelRepository.findByModelNoAndBrandBrandName(modelNo, brandName);
	}

	@Override
	public List<Object[]> getAllForExcel(Long id) {
		// TODO Auto-generated method stub
		return modelRepository.getAllForExcel(id);
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return modelRepository.getAllForExcel();
	}

	@Override
	public List<Model> getModelByVerticalId(Long id) {
		// TODO Auto-generated method stub
		return modelRepository.getModelByVerticalId(id);
	}



	
}
