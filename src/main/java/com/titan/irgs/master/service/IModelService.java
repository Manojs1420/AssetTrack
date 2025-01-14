package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Model;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, Model domain class).
 * This is ModelService interface(i.e, custom interface) which has CRUD method specification for Model domain class.
 * It is responsible to provide service(i.e model releated data) to model web page and vice-versa
 * 
 * @author 
 *
 */
public interface IModelService {
	
	Page<Model> getAllModel(String modelName, String modelNo, String brandName,String webMasterId,String webMasterName, String verticalName, Pageable page);

	Model getModelById(Long modelId);

	Model saveModel(Model model);

	Model updateModel(Model model);

	void deleteModelById(Long modelId);
	
	 Model findByModelName(String modelName);
		
	 Model findByModelNo(String modelNo);

	List<Model> getAllModelOnBrandId(Long brandId);
	
	Model findByModelNoAndBrandBrandName(String modelNo,String brandName);

	List<Object[]> getAllForExcel();

	List<Object[]> getAllForExcel(Long id);

	List<Model> getModelByVerticalId(Long id);

	Model findByModelNoAndWebMaster(String modelNo, Long webMasterId);
	
}
