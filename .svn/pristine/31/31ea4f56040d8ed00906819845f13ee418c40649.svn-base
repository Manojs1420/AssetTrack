package com.titan.irgs.serviceRequest.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.serviceRequest.domain.MiscellaneousTypeDomain;
import com.titan.irgs.serviceRequest.model.MiscellaneousTypeModel;

@Component
public class MiscellaneousTypeMapper {

	public MiscellaneousTypeDomain convertModeltoDomain(MiscellaneousTypeModel miscellaneousTypeModel) {
		MiscellaneousTypeDomain miscellaneousTypeDomain=new MiscellaneousTypeDomain();
		BeanUtils.copyProperties(miscellaneousTypeModel, miscellaneousTypeDomain);
		return miscellaneousTypeDomain;
	}

	public MiscellaneousTypeModel convertDomaintoModel(MiscellaneousTypeDomain miscellaneousTypeDomain) {
		MiscellaneousTypeModel miscellaneousTypeModel=new MiscellaneousTypeModel();
		BeanUtils.copyProperties(miscellaneousTypeDomain, miscellaneousTypeModel);
		return miscellaneousTypeModel;
	}

}
