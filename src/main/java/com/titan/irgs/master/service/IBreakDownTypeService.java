package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.titan.irgs.serviceRequest.domain.BreakDownType;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, BreakDownTypem domain class).
 * This is BreakDownTypeService interface(i.e, custom interface) which has CRUD method specification for BreakDownTypem domain class.
 * It is responsible to provide service(i.e breakDownType related data) to breakDownType web page and vice-versa
 * 
 * @author
 *
 */
public interface IBreakDownTypeService {
	
	Page<BreakDownType> getAllBreakDownType(String breakDownName,Long webMasterId,String webMasterName, String verticalName, Pageable page);

	BreakDownType getBreakDownTypeById(Long breakDownTypeId);

	BreakDownType saveBreakDownType(BreakDownType breakDownTypem);

	BreakDownType updateBreakDownType(BreakDownType breakDownTypem);

	void deleteBreakDownTypeById(Long breakDownTypeId);
	
	BreakDownType findByBreakDownTypeName(String breakDownName);

//	BreakDownTypem findByBreakDownTypeCode(String breakDownName);

	List<Object[]> getAll(Long id);
	List<Object[]> getAll();

	List<BreakDownType> getBreakDownTypeByWebMasterId(Long id);

	

}
