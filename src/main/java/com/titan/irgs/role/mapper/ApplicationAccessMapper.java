package com.titan.irgs.role.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.role.domain.ApplicationAccess;
import com.titan.irgs.role.model.ApplicationAccessModel;

/**
 * 
 * @author hari.k
 *
 */

@Component
public class ApplicationAccessMapper {
	
	
	public ApplicationAccess convertModeltoDomain(ApplicationAccessModel applicationAccessModel) {
		ApplicationAccess applicationAccess = new ApplicationAccess();
		BeanUtils.copyProperties(applicationAccessModel, applicationAccess);
		
		return applicationAccess;
	}

	public ApplicationAccessModel convertDomaintoModel(ApplicationAccess applicationAccess) {
		
		ApplicationAccessModel applicationAccessModel=new ApplicationAccessModel();
		BeanUtils.copyProperties(applicationAccess, applicationAccessModel);

		return applicationAccessModel;
	}

}
