package com.titan.irgs.inventory.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.inventory.enums.MaintainanceType;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.AmcWarrantyService;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.user.repository.UserRepo;


@Service
public class AmcInventoryServiceImp implements AmcInventoryService {
	@Autowired
	private AmcInventoryRepository amcInventoryRepository;
	
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	AmcWarrantyService amcWarrantyService;
	@Autowired
	InventoryRepository inventoryRepository;

	@Autowired
	AssetRepository assetRepository;
	
	/*
	 * @Override public List<AmcInventory> getAllAmcInventories() { // TODO
	 * Auto-generated method stub return amcInventoryRepository.findAll(); }
	 */

	@SuppressWarnings("serial")
	public Page<AmcInventory> getAllAmcInventories(String businessVerticalName,
			String amcStatus, String maintainanceType, Long maintainancePeriod, String maintainanceStartDate,
			String maintainanceEndDate, Long minMaintainanceGap, String maintainanceValidity, Long numberOfService,
			String contractNumber, String vendorCode,Boolean activeStatus,
			String storeCode, String assetName,String erNo,String farNo,String modelName,List<Long> region,String VerticalName,String installationDate,Long fromYear,Long toYear,
			String department, Pageable pageable) {
		
		return amcInventoryRepository.findAll(new Specification<AmcInventory>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<AmcInventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				 
				
				
				if (storeCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("user").get("username"),
							"%" + storeCode + "%")));

				}
				if (installationDate != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("installationDate"),
					"%" + installationDate + "%")));

				}

				if (VerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").join("webMaster").get("webMasterName"),"%" + VerticalName + "%")));

				}
				if (businessVerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("inventory").join("webMaster").get("webMasterName"), "%" + businessVerticalName + "%")));

				}

				
				if (erNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("inventory").get("erNo"), "%" + erNo + "%")));

				}			


				if (assetName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").get("assetName"), "%" + assetName + "%")));

				}

				if (farNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("inventory").get("farNo"), "%" + farNo + "%")));

				}
				if (farNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("inventory").get("farNo"), "%" + farNo + "%")));

				}

				if (modelName != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.join("asset").join("model").get("modelName"), "%" + modelName + "%")));

				}


				if (vendorCode != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.join("vendor").get("vendorCode"), "%" + vendorCode + "%")));

				}
				if (activeStatus != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("activeStatus"), "%" + activeStatus + "%")));

				}


				if (amcStatus != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("amcStatus"), AmcStatus.valueOf(amcStatus))));

				}
				if (maintainanceType != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("maintainanceType"),MaintainanceType.valueOf(maintainanceType))));

				}
				if (maintainancePeriod != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("maintainancePeriod"),maintainancePeriod)));

				}
				if (maintainanceStartDate != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("maintainanceStartDate"),
							"%" + maintainanceStartDate + "%")));

				}
		
				if (maintainanceEndDate != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.<String>get("maintainanceEndDate"),maintainanceEndDate)));

				}
				if (minMaintainanceGap != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("minMaintainanceGap"),minMaintainanceGap)));

				}
				if (maintainanceValidity != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.get("maintainanceValidity"), "%" + maintainanceValidity + "%")));

				}


				if (numberOfService != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("numberOfService"),numberOfService)));
				}
				if (contractNumber != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("contractNumber"), "%" + contractNumber + "%")));

				}
				/*
				 * if (region != null) {
				 * predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.join("store").
				 * join("region").get("regionId"),region)));
				 * 
				 * }
				 */
				if (region != null) {
					predicates.add((root.join("store").join("region").get("regionId").in(region)));

				}
				if (fromYear != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Long.class, root.get("maintainanceStartDate") ),fromYear)));

				}
				if (toYear != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Long.class, root.get("maintainanceEndDate") ), toYear)));

				}
				
				if (department != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.join("asset").join("department").get("departmentName"), "%" + department + "%")));

				}

				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}



	@Override
	public AmcInventory saveAmcInventory(AmcInventory amcInventory) {
		// TODO Auto-generated method stub
		Asset asset=assetRepository.getOne(amcInventory.getAsset().getAssetId());
		asset.setVendor(amcInventory.getVendor());
		amcInventory.setInstallationDate(amcInventory.getAsset().getInstallationDate());
		amcInventory.setUser(amcInventory.getUser());
		LocalDate date=amcInventory.getMaintainanceStartDate();
		amcInventory.setMaintainancePeriod(amcInventory.getMinMaintainanceGap()*amcInventory.getNumberOfService());
		amcInventory.setMaintainanceEndDate(date.plusDays(amcInventory.getMaintainancePeriod()));
		amcInventory.setMaintainanceValidity(amcInventory.getMaintainanceEndDate());
		if (amcInventory.getAmcStatus().equals(AmcStatus.ACTIVE)) {
			amcInventory.setAmcStatus(AmcStatus.ACTIVE);
			asset.setAmcStatus(AmcStatus.ACTIVE);
		} else {
			amcInventory.setAmcStatus(AmcStatus.INACTIVE);
			asset.setAmcStatus(AmcStatus.ACTIVE);
		}			if (amcInventory.getMaintainanceType().equals(MaintainanceType.AMC)) {
			amcInventory.setMaintainanceType(MaintainanceType.AMC);
		}else {
			amcInventory.setMaintainanceType(MaintainanceType.WARRANTY);
		}



		assetRepository.save(asset);



		return amcInventoryRepository.save(amcInventory);

	}



	@Override
	public AmcInventory updateAmc(AmcInventory amcInventories) {
		// TODO Auto-generated method stub
		AmcInventory amcInventory = amcInventoryRepository.findById(amcInventories.getAmcId()).get();
		//  amcWarrantyRepository.deleteByAmcId(amcInventory.getAmcId());
		//	amcWarrantyRepository.deleteByAmcId(amcInventories.getAmcId());
		if(amcInventories.getMaintainanceStartDate()!=amcInventory.getMaintainanceStartDate()||amcInventories.getNumberOfService()!=amcInventory.getNumberOfService()||
				amcInventories.getMinMaintainanceGap()!=amcInventory.getMinMaintainanceGap()) {
			amcWarrantyService.updateWarrantyAmc(amcInventory);
			amcInventory.setMaintainanceStartDate(amcInventories.getMaintainanceStartDate());
			amcInventory.setNumberOfService(amcInventories.getNumberOfService());
			amcInventory.setMinMaintainanceGap(amcInventories.getMinMaintainanceGap());
			LocalDate date=amcInventories.getMaintainanceStartDate();
			amcInventory.setMaintainancePeriod(amcInventories.getMinMaintainanceGap()*amcInventory.getNumberOfService());
			amcInventory.setMaintainanceEndDate(date.plusDays(amcInventories.getMaintainancePeriod()));
			amcInventory.setMaintainanceValidity(amcInventories.getMaintainanceEndDate());
			amcWarrantyService.saveAmcExtension(amcInventory);


		}

		Asset asset=assetRepository.getOne(amcInventory.getAsset().getAssetId());

		if (amcInventories.getAmcStatus().equals(AmcStatus.ACTIVE)) {
			amcInventory.setAmcStatus(AmcStatus.ACTIVE);
			asset.setAmcStatus(AmcStatus.ACTIVE);
		} else {
			amcInventory.setAmcStatus(AmcStatus.INACTIVE);
			asset.setAmcStatus(AmcStatus.INACTIVE);
		}
		if (amcInventories.getMaintainanceType().equals(MaintainanceType.AMC)) {
			amcInventory.setMaintainanceType(MaintainanceType.AMC);
		}else {
			amcInventory.setMaintainanceType(MaintainanceType.WARRANTY);
		}
		amcInventory.setInstallationDate(amcInventories.getAsset().getInstallationDate());
		amcInventory.setContractNumber(amcInventories.getContractNumber());
		amcInventory.setDescription(amcInventories.getDescription());
		amcInventory.setVendor(amcInventories.getVendor());
		amcInventory.setUser(amcInventories.getUser());
		amcInventory.setAssetWebMaster(amcInventories.getAssetWebMaster());
		assetRepository.save(asset);


		return amcInventoryRepository.save(amcInventory);
	}

	@Override
	public List<AmcInventory> getAmcByAssetId(Long id) {

		return amcInventoryRepository.getAmcByAssetId(id);
	}

	@Override
	public List<AmcInventory> findByAmcId(Long id) {
		// TODO Auto-generated method stub
		return amcInventoryRepository.findByAmcId(id);
	}

	@Override
	public AmcInventory findById(Long id) {
		// TODO Auto-generated method stub
		return amcInventoryRepository.findById(id).get();
	}

	
	//AMC Inventory Template designing..
		@Override
		public Mail templeteMail(AmcInventory amcInventory) {
			Mail mail=new Mail();
			
			Map<String, Object> model = new HashMap<String, Object>();
			
			model.put("userName", amcInventory.getAsset() == null ? "" :amcInventory.getUser().getUsername());
			model.put("BusinessVerticalName", amcInventory.getAsset()==null?"":amcInventory.getAssetWebMaster().getWebMasterName());
			model.put("AssetName", amcInventory.getAsset() == null ? "" :amcInventory.getAsset().getAssetName());
			model.put("FARNo", amcInventory.getAsset() == null ? "" :amcInventory.getAsset().getFarNo());
			model.put("ModelName", amcInventory.getAsset() == null ? "" :amcInventory.getAsset().getModel().getModelName());
			model.put("VendorCode", amcInventory.getAsset() == null ? "" :amcInventory.getVendor().getVendorCode());
			model.put("VendorName", amcInventory.getAsset() == null ? "" :amcInventory.getAsset().getVendor().getVendorName());
			model.put("InstallationDate", amcInventory.getAsset() == null ? "" :amcInventory.getAsset().getInstallationDate());
			model.put("MaintenanceStartDate", amcInventory.getAsset() == null ? "" :amcInventory.getMaintainanceStartDate());
			model.put("MaintenanceEndDate", amcInventory.getAsset() == null ? "" :amcInventory.getMaintainanceEndDate());
			model.put("NumberOfService", amcInventory.getAsset() == null ? "" :amcInventory.getNumberOfService());
			model.put("MaintenanceGap", amcInventory.getAsset() == null ? "" :amcInventory.getMinMaintainanceGap());
			model.put("MaintenancePeriod", amcInventory.getAsset() == null ? "" :amcInventory.getMaintainancePeriod());
			model.put("MaintenanceValidity", amcInventory.getAsset() == null ? "" :amcInventory.getMaintainanceValidity());
			model.put("ContactNumber", amcInventory.getAsset() == null ? "" :amcInventory.getContractNumber());
			model.put("AMCStatus", amcInventory.getAsset() == null ? "" :amcInventory.getAmcStatus());
			
			mail.setModel(model);
			return mail;
		}


		@Override
		public List<AmcInventory> getAllAmc(String businessVerticalTypeName, String amcStatus,
				String stringStoreIds, String maintainancePeriod, String maintainanceStartDate,
				String maintainanceEndDate, String minMaintainanceGap, String maintainanceValidity,
				String numberOfService, String contractNumber, String vendorCode,
				String storeCode, String assetName, String erNo, String farNo,Long vendorId,
				Pageable page) {
			int end_page=page.getPageNumber() * page.getPageSize();
			int start_page=end_page-(page.getPageSize()-1);

			return amcInventoryRepository.getFilteredUsingProcedure( businessVerticalTypeName,  amcStatus,   stringStoreIds,
					 maintainancePeriod,  maintainanceStartDate,  maintainanceEndDate,
					 minMaintainanceGap,  maintainanceValidity,  numberOfService,  contractNumber,
					 vendorCode,  storeCode,  assetName,  erNo,  farNo,vendorId,start_page,end_page);
		}

		@Override
		public Long count(String businessVerticalTypeName, String amcStatus,
				String stringStoreIds, String maintainancePeriod, String maintainanceStartDate,
				String maintainanceEndDate, String minMaintainanceGap, String maintainanceValidity,
				String numberOfService, String contractNumber, String vendorCode,
				String storeCode, String assetName, String erNo, String farNo,Long vendorId) {
			// TODO Auto-generated method stub
			return amcInventoryRepository.count(businessVerticalTypeName,     stringStoreIds,
					  vendorCode, storeCode,  assetName,  erNo,  farNo,vendorId);
		}


		@Override
		public Mail templeteMailAlert(AmcInventory amcInventory) {
			Mail mail=new Mail();
			
			Map<String, Object> model = new HashMap<String, Object>();
			LocalDate date=amcInventory.getMaintainanceStartDate();
			String formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy",Locale.ENGLISH).format(date);
			String formatter2 = DateTimeFormatter.ofPattern("MMM-yyyy",Locale.ENGLISH).format(amcInventory.getMaintainanceStartDate());
			String formatter3 = DateTimeFormatter.ofPattern("MMM-yyyy",Locale.ENGLISH).format(amcInventory.getMaintainanceEndDate());
			
			model.put("MaintainanceFromDate", formatter1 == null ? "" :formatter1);
			model.put("EquipmentName", amcInventory.getAsset().getEquipment().getEquipmentName());
			model.put("userName", amcInventory.getUser().getUsername() == null ? "" :amcInventory.getUser().getUsername());
			model.put("Fromperiod", formatter2 == null ? "" :formatter2);
			model.put("ToPeriod", formatter3 == null ? "" :formatter3);
			System.out.println(model);
			mail.setModel(model);
			return mail;
		}



		@SuppressWarnings("serial")
		public List<Object[]> getTotalAmcInventory(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region) {
			// TODO Auto-generated method stub
			return amcInventoryRepository.getTotalAmcInventorys(businessVerticalTypeName,storeCode,vendorId,region);
			
			/*
			return amcInventoryRepository.getTotalAmcInventory(new Specification<AmcInventory>() {
				@Override
				public Predicate toPredicate(Root<AmcInventory> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();
								
						if (businessVerticalTypeName != null) {
										predicates.add(criteriaBuilder.and(criteriaBuilder
												.like(root.join("assetWebMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

						}
						
					if (vendorId != null) {
						predicates.add(criteriaBuilder
								.and(criteriaBuilder.like(root.join("vendor").get("vendorId"), "%" + vendorId + "%")));

					}
				
					if (storeCode != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.like(
								root.join("store").get("storeCode"),
								"%" + storeCode + "%")));

					}

					if (region != null) {
						predicates.add((root.join("store").join("region").get("regionId").in(region)));

					}

					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			});
			*/
		}
}
