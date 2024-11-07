package com.titan.irgs.accessPolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.accessPolicy.domain.BackEndApis;

public interface BackendApiService {

	BackEndApis save(BackEndApis backEndApis);

	Page<BackEndApis> getAll(Pageable page);

	BackEndApis getById(Long id);

	BackEndApis update(BackEndApis backEndApis);

	Page<BackEndApis> getAll(String url,String featureName,Pageable page);

	BackEndApis findByBackEndApiIdUrl(String backEndApiIdUrl);

}
