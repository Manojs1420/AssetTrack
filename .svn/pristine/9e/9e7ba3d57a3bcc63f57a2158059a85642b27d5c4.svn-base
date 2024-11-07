package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Brand;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, Brand domain class).
 * This is BrandService interface(i.e, custom interface) which has CRUD method specification for Brand domain class.
 * It is responsible to provide service(i.e brand related data) to brand web page and vice-versa
 * 
 * @author
 *
 */
public interface IBrandService {
	
	Page<Brand> getAllBrand(String brandName, String brandCode,String webMasterId,String webMasterName, String verticalName, Pageable page);

	Brand getBrandById(Long brandId);

	Brand saveBrand(Brand brand);

	Brand updateBrand(Brand brand);

	void deleteBrandById(Long brandId);
	
	Brand findByBrandName(String brandName);

	Brand findByBrandCode(String brandName);

	List<Object[]> getAll(Long id);
	List<Object[]> getAll();

	List<Brand> getBrandByWebMasterId(Long id);

	

}
