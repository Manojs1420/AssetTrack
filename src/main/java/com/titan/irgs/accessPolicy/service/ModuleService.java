package com.titan.irgs.accessPolicy.service;

import java.util.List;

import com.titan.irgs.accessPolicy.domain.Module;

public interface ModuleService {

	Module save(Module model);

	List<Module> getAll();

	Module getById(Long id);

}
