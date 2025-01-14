package com.titan.irgs.master.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.SerialNumber;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.SerialNumberRepository;
import com.titan.irgs.master.service.IAssetService;

/**
 * Method Implementation for IAssetService method
 * 
 * @author birendra
 *
 */
@Service
public class AssetService implements IAssetService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private SerialNumberRepository serialNumberRepository;

	/**
	 * getAllAsset -> Method
	 * 
	 * @param ->none
	 * @return list of saved asset entity
	 */

	@SuppressWarnings("serial")
	@Override
	public Page<Asset> getAllAsset(String assetName, String assetCode, String fARNo, String equipmentName,
			String modelName, String brandName, String businessVerticalType, String departmentName, String amcStatus,
			String department,String vendorCode,Pageable pageable) {
		return assetRepository.findAll(new Specification<Asset>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				query.orderBy(criteriaBuilder.desc(root.get("assetId")));
				if (assetName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("assetName"), "%" + assetName + "%")));

				}

				if (assetCode != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("assetCode"), "%" + assetCode + "%")));

				}
				/*
				 * if (businessVerticalType != null) {
				 * predicates.add(criteriaBuilder.and(criteriaBuilder
				 * .like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalType
				 * + "%")));
				 * 
				 * }
				 */

				if (equipmentName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("equipment").get("equipmentName"),
							"%" + equipmentName + "%")));

				}

				if (fARNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("farNo"), "%" + fARNo + "%")));

				}

				if (modelName != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.join("model").get("modelName"), "%" + modelName + "%")));

				}

				if (brandName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("model").join("brand").get("brandName"), "%" + brandName + "%")));

				}

				if (businessVerticalType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),
							"%" + businessVerticalType + "%")));

				}

				if (departmentName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("department").get("departmentName"), "%" + departmentName + "%")));

				}

				if (amcStatus != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("amcStatus"), AmcStatus.valueOf(amcStatus))));

				}

				if (department != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("department").get("departmentName"), "%" + department + "%")));

				}
				
				if(vendorCode!=null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("vendor").get("vendorCode"), "%" + vendorCode + "%")));

				}
				
				/*
				 * if (distributorName != null) {
				 * predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.join("item").
				 * join("").get(""), frameShapeName)));
				 * 
				 * }
				 */

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}

	@SuppressWarnings("serial")
	@Override
	public List<Asset> getAllAssetExceptAlreadyCreated(String assetName, String assetCode, String fARNo,
			String equipmentName, String modelName, String brandName, String businessVerticalType, Pageable page) {

		int end_page = page.getPageNumber() * page.getPageSize();
		int start_page = end_page - (page.getPageSize() - 1);

		return assetRepository.getAllAssetnotCreated(assetName, assetCode, fARNo, equipmentName, modelName, brandName,
				businessVerticalType, start_page, end_page);
	}

	@SuppressWarnings("serial")
	public Page<Asset> getAllAssetExceptAlreadyCreated1(String assetName, String assetCode, String fARNo,
			String equipmentName, String modelName, String brandName, String businessVerticalType, Pageable page) {
		return null;

		/*
		 * return assetRepository.getAllAssetnotCreated1(new Specification<Asset>() {
		 * 
		 * @SuppressWarnings("rawtypes")
		 * 
		 * @Override public Predicate toPredicate(Root<Asset> root, CriteriaQuery<?>
		 * query, CriteriaBuilder criteriaBuilder) { List<Predicate> predicates = new
		 * ArrayList<>();
		 * 
		 * query.orderBy(criteriaBuilder.desc(root.get("assetId"))); if (assetName !=
		 * null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("assetName")
		 * ,"%" + assetName + "%")));
		 * 
		 * }
		 * 
		 * if (assetCode != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("assetCode")
		 * , "%" + assetCode + "%")));
		 * 
		 * }
		 * 
		 * 
		 * if (equipmentName != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder
		 * .like(root.join("equipment").get("equipmentName"), "%" + equipmentName +
		 * "%")));
		 * 
		 * }
		 * 
		 * if (fARNo != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("farNo"),
		 * "%" + fARNo + "%")));
		 * 
		 * }
		 * 
		 * if (modelName != null) { predicates.add(criteriaBuilder.and(
		 * criteriaBuilder.like(root.join("model").get("modelName"), "%" + modelName +
		 * "%")));
		 * 
		 * }
		 * 
		 * if (brandName != null) { predicates.add(criteriaBuilder.and(criteriaBuilder
		 * .like(root.join("model").join("brand").get("brandName"), "%" + brandName +
		 * "%")));
		 * 
		 * }
		 * 
		 * if (businessVerticalType != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder
		 * .like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalType
		 * + "%")));
		 * 
		 * }
		 * 
		 * return criteriaBuilder.and(predicates.toArray(new
		 * Predicate[predicates.size()])); } }, page);
		 */
	}

	/**
	 * getAssetById -> Method
	 * 
	 * @param assetId
	 * @return saved asset entity
	 */
	@Override
	public Asset getAssetById(Long assetId) {
		Asset asset = assetRepository.findById(assetId)
				.orElseThrow(() -> new EntityNotFoundException("asset with assetId " + assetId + " not found"));

		return asset;
	}

	@Override
	public Asset updateAssetForPOUpload(Asset asset) {
		Asset asset2 = assetRepository.findByAssetId(asset.getAssetId());
		return assetRepository.save(asset);
	}

	/**
	 * saveAsset ->Method
	 * 
	 * @param asset entity
	 * @return saved asset entity
	 */

	@Override
	public Asset saveAsset(Asset asset) {

		asset.setCreatedOn(new Date());
		asset.setUpdatedOn(null);

		if (asset.getAmcStatus() != null) {
			if (asset.getAmcStatus().equals(AmcStatus.ACTIVE)) {
				asset.setAmcStatus(AmcStatus.ACTIVE);
			} else {
				asset.setAmcStatus(AmcStatus.INACTIVE);
			}
		}

		LocalDate date = asset.getInstallationDate();
		asset.setWarrantyEndDate(date.plusDays(asset.getWarrantyPeriod() - 1));

		return assetRepository.save(asset);
	}

	/**
	 * updateAsset->Method
	 * 
	 * @param asset entity
	 * @return updated asset entity
	 */

	@Override
	public Asset updateAsset(Asset asset) {

		Asset asset1 = assetRepository.findById(asset.getAssetId()).orElseThrow(
				() -> new EntityNotFoundException("asset with assetId " + asset.getAssetId() + " not found"));
		asset1.setAssetCode(asset.getAssetCode());
		asset1.setFarNo(asset.getFarNo());
		asset1.setOriginalQty(asset.getOriginalQty());
		asset1.setUpdatedOn(new Date());

		asset1.setAssetName(asset.getAssetName());
		asset1.setAssetSpecification(asset.getAssetSpecification());
		asset1.setEquipment(asset.getEquipment());
		if (asset.getModel() != null) {
			asset1.setModel(asset.getModel());
		}
		asset1.setVendor(asset.getVendor());

		asset1.setInstallationDate(asset.getInstallationDate());
		asset1.setWarrantyPeriod(asset.getWarrantyPeriod());
		LocalDate date = asset.getInstallationDate();

		asset1.setWarrantyEndDate(date.plusDays(asset.getWarrantyPeriod() - 1));

		asset1.setPoNo(asset.getPoNo());
		asset1.setValue(asset.getValue());
		asset1.setVendorInvoiceRef(asset.getVendorInvoiceRef());

		asset1.setDepartment(asset.getDepartment());
		asset1.setDescription(asset.getDescription());
		asset1.setIsScrap(asset.getIsScrap());
		
		if (asset.getAmcStatus() != null) {
			if (asset.getAmcStatus().equals(AmcStatus.ACTIVE)) {
				asset1.setAmcStatus(AmcStatus.ACTIVE);
			} else {
				asset1.setAmcStatus(AmcStatus.INACTIVE);
			}
		}
		
		if (asset1 == null) {
			logger.error("asset with assetId {} not found", asset.getAssetId());
			throw new EntityNotFoundException("asset with assetId " + asset.getAssetId() + " not found");
		}
		return assetRepository.save(asset1);
	}

	/**
	 * deleteAssetById ->Method
	 * 
	 * @param assetId
	 * @return none
	 */
	@Override
    @Transactional
	public void deleteAssetById(Long assetId) {
		Asset asset = assetRepository.findById(assetId)
				.orElseThrow(() -> new EntityNotFoundException("asset with assetId " + assetId + " not found"));
		
	List<SerialNumber> serialNumber=serialNumberRepository.findByAsset(asset);
		if(!serialNumber.isEmpty()) {
		
		serialNumberRepository.deleteByAsset(asset);}
		assetRepository.deleteById(asset.getAssetId());
	}

	@Override
	public List<Asset> findByAmcStatus(boolean amcStatus) {
		// TODO Auto-generated method stub
		return assetRepository.findByAmcStatus(amcStatus);
	}

	@Override
	public List<Asset> getAllAssetOnModelId(Long modelId) {
		return assetRepository.findAssetBymodelId(modelId);
	}

	@Override
	public Asset findByAssetName(String assetName) {
		// TODO Auto-generated method stub

		Asset asset = assetRepository.findByAssetName(assetName);

		return asset;
	}

	@Override
	public Asset findByAssetCode(String assetCode) {
		// TODO Auto-generated method stub
		Asset asset = assetRepository.findByAssetCode(assetCode);

		return asset;
	}

	@Override
	public List<Object[]> getAllCitysForExcel() {
		// TODO Auto-generated method stub
		return assetRepository.getAllCitysForExcel();
	}
	
	@Override
	public List<Object[]> getAllCitysForExcel(String businessVerticalType) {
		// TODO Auto-generated method stub
		return assetRepository.getAllCitysForExcel(businessVerticalType);
	}

	@Override
	public List<Object[]> getAllCitysForExcel(String businessVerticalType, String department) {
		// TODO Auto-generated method stub
		return assetRepository.getAllCitysForExcel(businessVerticalType,department);
	}

	@Override
	public Long count(String assetName, String assetCode, String fARNo, String equipmentName, String modelName,
			String brandName, String businessVerticalType) {
		return assetRepository.count(assetName, assetCode, fARNo, equipmentName, modelName, brandName,
				businessVerticalType);
	}

	@Override
	public List<Object[]> getAssetsByVerticalIdAndDepartmentId(Long verticalId, Long departmentId) {
		// TODO Auto-generated method stub
		return assetRepository.getAssetsByVerticalIdAndDepartmentId(verticalId, departmentId);
	}

	@SuppressWarnings("serial")
	@Override
	public List<Object[]> getAssetCountForDashboard(String businessVerticalType, String department, String user1,String vendorCode) {
		return assetRepository.getAssetCountForDashboard(businessVerticalType,department,user1,vendorCode);
		/*
		 * return assetRepository.findAll(new Specification<Asset>() {
		 * 
		 * @SuppressWarnings("rawtypes")
		 * 
		 * @Override public Predicate toPredicate(Root<Asset> root, CriteriaQuery<?>
		 * query, CriteriaBuilder criteriaBuilder) { List<Predicate> predicates = new
		 * ArrayList<>();
		 * 
		 * // query.orderBy(criteriaBuilder.desc(root.get("assetId"))); if
		 * (businessVerticalType != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder
		 * .like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalType
		 * + "%")));
		 * 
		 * }
		 * 
		 * return criteriaBuilder.and(predicates.toArray(new
		 * Predicate[predicates.size()])); } });
		 */
	}

	@Override
	public List<Object[]> getUnassignedAssetCountForDashboard(String businessVerticalType, String department, String user1) {
		return assetRepository.getUnassignedAssetCountForDashboard(businessVerticalType,department,user1);
	}

	@Override
	public List<Object[]> getScrappedAssetCountForDashboard(String businessVerticalType, String department, String user1) {
		return assetRepository.getScrappedAssetCountForDashboard(businessVerticalType,department,user1);
	}

	@Override
	public void updateSerialNumbers(Asset asset, String serialnumbers) throws Exception {
		List<SerialNumber> serialNumbers = serialNumberRepository.findByAssetId(asset.getAssetId());
		if (serialNumbers.isEmpty()) {
			if (serialnumbers != null) {
				String[] values = serialnumbers.split(",\\s*");
				if(asset.getOriginalQty()!=values.length) {
					throw new ResourceAlreadyExitException(asset.getOriginalQty()+" SerialNumbers must be add");
				}
				for (String value : values) {
					SerialNumber serialNumber = new SerialNumber();

					serialNumber.setAsset(asset);
					serialNumber.setSerialNumber(value);
					serialNumberRepository.save(serialNumber);
				}
			}
		} else {

			/*
			for (SerialNumber serialNumber : serialNumbers) {
				serialNumberRepository.deleteById(serialNumber.getSerialId());
			}*/


			if (serialnumbers != null) {
				List<String> stringList = Arrays.asList(serialnumbers.split(",\\s*"));
				if(asset.getOriginalQty()!=stringList.size()) {
					throw new ResourceAlreadyExitException(asset.getOriginalQty()+" SerialNumbers must be add");
				}
				List<String> dps = stringList.stream().distinct().filter(entry -> Collections.frequency(stringList, entry) > 1).collect(Collectors.toList());

				if(!dps.isEmpty()) {
					throw new ResourceAlreadyExitException("Duplicate Serial Numbers present");
				}

				List<String> stringList2 = serialNumbers.stream()
						.map(SerialNumber::getSerialNumber)
						.collect(Collectors.toList());

				List<String> notMatchedInList1 = new ArrayList<>(stringList);
				notMatchedInList1.removeAll(stringList2);

				List<String> notMatchedInList2 = new ArrayList<>(stringList2);
				notMatchedInList2.removeAll(stringList);
				 
				if(!notMatchedInList2.isEmpty()) {
						List<Long> ids= notMatchedInList2.stream()
								.map(sNumber -> serialNumberRepository.findBySerialNumberAndAssetId(sNumber, asset.getAssetId()).getSerialId())
								.collect(Collectors.toList());
						IntStream.range(0, ids.size())
						.forEach(i -> {
							SerialNumber serialNumber = serialNumberRepository.findById(ids.get(i)).orElse(null);
							if (serialNumber != null) {
								serialNumber.setAsset(asset);
								serialNumber.setSerialNumber(notMatchedInList1.get(i));
								serialNumberRepository.save(serialNumber);
							}else {
								serialNumber=new SerialNumber();
								serialNumber.setAsset(asset);
								serialNumber.setSerialNumber(notMatchedInList2.get(i));
								serialNumberRepository.save(serialNumber);
							}
						});
					}
				else if(!notMatchedInList1.isEmpty()) {
//					List<String> sNumbers = new ArrayList<>();
//					for (String element : notMatchedInList1) {
//						String result =element.replaceAll("\\[|\\]", ""); // Remove first and last characters
//						sNumbers.add(result);
//					}

					for(String sNumber:notMatchedInList1) {
						SerialNumber serialNumber = serialNumberRepository.findBySerialNumberAndAssetId(sNumber, asset.getAssetId());
						if (serialNumber != null) {
							serialNumber.setAsset(asset);
							serialNumber.setSerialNumber(sNumber);
							serialNumberRepository.save(serialNumber);
						}else {
							serialNumber=new SerialNumber();
							serialNumber.setAsset(asset);
							serialNumber.setSerialNumber(sNumber);
							serialNumberRepository.save(serialNumber);
						}
					}
				}

			}

				
			/*
			if (serialnumbers != null) {
				String[] values = serialnumbers.split(",\\s*");
				  for (String value : values) { SerialNumber serialNumber = new SerialNumber();
				  
				  serialNumber.setAsset(asset); serialNumber.setSerialNumber(value);
				  serialNumberRepository.save(serialNumber); }
				 
			}
			*/
		}
	}

}
