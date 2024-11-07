package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.repository.BrandRepository;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * Method Implementation for IBrandService method
 * @author birendra
 *
 */
@Service
public class BrandService implements IBrandService {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private WebMasterRepo webMasterRepo;

    
	/**
	 * getAllBrand -> Method
	 * @param ->none
	 * @return list of saved brand entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Brand> getAllBrand(String brandName, String brandCode,String webMasterId,String webMasterName,String verticalName, Pageable page) {
		
		return brandRepository.findAll(new Specification<Brand>() {
			
			@Override
			public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (brandName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("brandName"),"%" + brandName + "%")));

				}

				if (brandCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("brandCode"),"%" + brandCode + "%")));

				}
				if (webMasterId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterId"),"%" + webMasterId + "%")));

				}
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

				}
				if (verticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + verticalName + "%")));

				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		},page);
	}

	/**
	 * getBrandById -> Method
	 * @param brandId
	 * @return saved brand entity
	 */
	@Override
	public Brand getBrandById(Long brandId) {
		
		Brand brand = brandRepository.findById(brandId).orElseThrow(()->new EntityNotFoundException("brand with brandId " + brandId + " not found"));
		
		return brand;
	}
    
	/**
	 * saveBrand ->Method
	 * @param brand entity
	 * @return saved brand entity
	 */
	@Override
	public Brand saveBrand(Brand brand) {
		brand.setCreatedOn(new Date());
		brand.setUpdatedOn(null);
		return brandRepository.save(brand);
	}
    
	/**
	 * updateBrand ->Method
	 * @param brand entity
	 * @return updated brand entity
	 */
	@Override
	public Brand updateBrand(Brand brand) {
		brand.setUpdatedOn(new Date());

		Brand brand1 = brandRepository.findById(brand.getBrandId()).orElseThrow(()->new EntityNotFoundException("brand with brandId " + brand.getBrandId() + " not found"));
		
		WebMaster webMaster = webMasterRepo.findByWebMasterId(brand.getWebMaster().getWebMasterId());
		brand1.setBrandName(brand.getBrandName());
		brand1.setBrandCode(brand.getBrandCode());
		brand1.setWebMaster(webMaster);
		return brandRepository.save(brand1);
	}
    
	/**
	 * deleteBrandById ->Method
	 * @param brandId
	 * @return none
	 */
	@Override
	public void deleteBrandById(Long brandId) {
		Brand brand = brandRepository.findById(brandId).orElseThrow(()->new EntityNotFoundException("brand with brandId " + brandId + " not found"));
		if (brand == null) {
			logger.error("brand with brandId {} not found", brandId);
			throw new EntityNotFoundException("brand with brandId " + brandId + " not found");
		}
		brandRepository.deleteById(brandId);

	}

	@Override
	public Brand findByBrandName(String brandName) {
		Brand brand = brandRepository.findByBrandName(brandName);

		return brand;
	}

	@Override
	public Brand findByBrandCode(String brandName) {
		// TODO Auto-generated method stub
		return brandRepository.findByBrandCode(brandName);
	}

	@Override
	public List<Object[]> getAll(Long id) {
		// TODO Auto-generated method stub
		return brandRepository.getAll(id);
	}

	@Override
	public List<Object[]> getAll() {
		// TODO Auto-generated method stub
		return brandRepository.getAll();
	}

	@Override
	public List<Brand> getBrandByWebMasterId(Long id) {
		// TODO Auto-generated method stub
		return brandRepository.getBrandByWebMasterId(id);
	}

}
