package com.titan.irgs.master.service;

import java.util.List;

import com.titan.irgs.master.domain.OwnerType;

public interface IOwnerTypeService {
	
	List<OwnerType> getAllOwnerType();

	OwnerType getOwnerTypeById(Long OwnerTypeId);

	OwnerType saveOwnerType(OwnerType OwnerType);

	OwnerType updateOwnerType(OwnerType OwnerType);

	void deleteOwnerTypeById(Long OwnerTypeId);
	
	OwnerType findByOwnerTypeName(String OwnerTypeName);
	
	boolean checkIfOwnerTypeNameIsExit(String OwnerTypeName);

}
