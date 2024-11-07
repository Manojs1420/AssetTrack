package com.titan.irgs.accessPolicy.service;

import java.util.List;

import com.titan.irgs.accessPolicy.domain.SubModule;

public interface SubModuleService {

	List<SubModule> getAll();

	SubModule save(SubModule subModule);

	SubModule getById(Long id);

	SubModule update(SubModule subModule);

}
