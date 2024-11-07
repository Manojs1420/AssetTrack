package com.titan.irgs.role.service;

import java.util.List;

import com.titan.irgs.role.domain.ApplicationAccess;

public interface IApplicationAccess {

	ApplicationAccess save(ApplicationAccess applicationAccess);

	List<ApplicationAccess> getAll();

	ApplicationAccess getById(Long id);

}
