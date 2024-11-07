package com.titan.irgs.inventory.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.service.AmcWarrantyService;

@Service
public class AmcWarrantyServiceImp implements AmcWarrantyService {
	
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;
	


	@Override
	public void saveAmcExtension(AmcInventory amcInventory) {
		
		//AmcInventory amcInventory=new AmcInventory();
		
		LocalDate date=amcInventory.getMaintainanceStartDate();

		List<AmcWarranty> amcWarrantyList = new ArrayList<AmcWarranty>();
		for(int i=0;i<amcInventory.getNumberOfService();i++)	{
			AmcWarranty amcWarranty = new AmcWarranty();
			if(i==0) {
			amcWarranty.setWarrantyFrom(date.plusDays(amcInventory.getMinMaintainanceGap()*(i)));}
			else {amcWarranty.setWarrantyFrom(date.plusDays(amcInventory.getMinMaintainanceGap()*(i)+1));}
			amcWarranty.setWarrantyTo(date.plusDays((amcInventory.getMinMaintainanceGap()*(i+1))));
			amcWarranty.setAmcId(amcInventory.getAmcId());
			amcWarranty.setAssetId(amcInventory.getAsset().getAssetId());
			amcWarranty.setCreatedOn(new Date());
			amcWarranty.setVendorId(amcInventory.getVendor().getVendorId());
			amcWarranty.setTicketStatus("Upcoming");
			amcWarrantyList.add(amcWarranty);
		}
		
		amcWarrantyRepository.saveAll(amcWarrantyList);
	}
	
	@Override
	public void  updateWarrantyAmc(AmcInventory amcInventory) {
		
		try {
			List<AmcWarranty> a=amcWarrantyRepository.FindByAmcId(amcInventory.getAmcId());
			a.forEach(amcWarrantyRepository.deleteByAmcId(amcInventory.getAmcId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	

			
			
			
		

	}

	
	
	
	
	
	@SuppressWarnings("serial")
	public Page<AmcWarranty> getAllAmcExtension(String vendorCode,String businessVerticalName,Boolean activeStatus,
			String extendDate, String maintainanceDates, String maintainancePeriod, 
			String maintainanceStartDate,String maintainanceEndDate, String minMaintainanceGap,  Pageable pageable) {
		
		return amcWarrantyRepository.findAll(new Specification<AmcWarranty>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<AmcWarranty> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				


				if (vendorCode != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.join("vendor").get("vendorCode"), "%" + vendorCode + "%")));

				}


				if (businessVerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),
							"%" + businessVerticalName + "%")));

				}
				if (extendDate != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("extendDate"), "%" + extendDate + "%")));

				}
				if (maintainanceDates != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("maintainanceDates"), "%" + maintainanceDates + "%")));

				}
				if (activeStatus != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("activeStatus"), "%" + activeStatus + "%")));

				}

				if (maintainancePeriod != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("maintainancePeriod"), "%" + maintainancePeriod + "%")));

				}
				if (maintainanceStartDate != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("maintainanceStartDate"),
							"%" + maintainanceStartDate + "%")));

				}
				if (maintainanceEndDate != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.get("maintainanceEndDate"), "%" + maintainanceEndDate + "%")));

				}
				if (minMaintainanceGap != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("minMaintainanceGap"), "%" + minMaintainanceGap + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}
	@Override
	public List<AmcWarranty> findWarrantyByAmcId(Long id) {
		// TODO Auto-generated method stub
		return amcWarrantyRepository.findWarrantyByAmcId(id);
	}

	@Override
	public void saveAmcValidation(AmcWarranty amcWarranty) {
		
			amcWarranty.setCreatedOn(new Date());
		amcWarrantyRepository.save(amcWarranty);
	


}

	@Override
	public List<AmcWarranty> findWarrantyByAssetId(Long id) {
		// TODO Auto-generated method stub
		return amcWarrantyRepository.FindByAssetId(id);
	}
}
