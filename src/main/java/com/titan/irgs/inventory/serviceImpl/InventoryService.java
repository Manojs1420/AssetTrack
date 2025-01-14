package com.titan.irgs.inventory.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.enumUtils.InventoryStatus;
import com.titan.irgs.inventory.controller.MaintainanceAlertEmailImpl;
import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.Inventory;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.IInventoryService;
import com.titan.irgs.serviceRequest.controller.Mail;



@Service
@Transactional
public class InventoryService implements IInventoryService {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Value("${mail.status}")
	private Boolean mailStatus;
	
	@Autowired
	AmcInventoryRepository amcInventoryRepository;
	
	@Autowired
	private AmcInventoryService amcInventoryService;
    
	@Autowired
	MaintainanceAlertEmailImpl maintainanceAlertEmailImpl;
	/**
	 * getAllInventory -> Method
	 * @param ->none
	 * @return list of saved Inventory entity
	 */
	@SuppressWarnings("serial")
	public Page<Inventory> getAllInventory(String store,String storeCode, String storeName,String businessVerticalName,String VerticalName, String assetName,
			String farNo, String model,String serialNumber, String brandName, String erNo,String amcStatus,String equipmentName,String regionName,List<Long> region,String vendorCode,String departmentName,String department, Pageable pageable) {
		return inventoryRepository.findAll(new Specification<Inventory>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();


				if (store != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
							root.join("user").get("username"),
							store )));

				}
				
 				if (storeCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("user").get("username"),
							"%" + storeCode + "%")));

				}
				if (storeName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("user").get("firstName"),
							"%" + storeName + "%")));

				}
				if (regionName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("user").join("role").join("Cluster").join("region").get("regionName"),
							"%" + regionName + "%")));

				}

				if (businessVerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalName + "%")));

				}
				if (VerticalName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset") .join("webMaster").get("webMasterName"), "%" + VerticalName + "%")));

				}
				
				if (erNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("erNo"), "%" + erNo + "%")));

				}
				if (amcStatus != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("inventoryStatus"), InventoryStatus.valueOf(amcStatus).toString())));

				}				


				if (assetName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").get("assetName"), "%" + assetName + "%")));

				}

				if (farNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("farNo"), "%" + farNo + "%")));

				}

				if (model != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.join("asset").join("model").get("modelName"), "%" + model + "%")));

				}
				
				if (serialNumber != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.join("serialNumber").get("serialNumber"), "%" + serialNumber + "%")));

				}

				if (brandName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").join("model").join("brand").get("brandName"), "%" + brandName + "%")));

				}
				if (equipmentName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").join("equipment").get("equipmentName"), "%" + equipmentName + "%")));

				}
				
				if (region != null) {
					predicates.add((root.join("store").join("region").get("regionId").in(region)));

				}
				
				if (departmentName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").join("department").get("departmentName"), "%" + departmentName + "%")));

				}
				
				if (department != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset").join("department").get("departmentName"), "%" + department + "%")));

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
    
	/**
	 * getInventoryById -> Method
	 * @param InventoryId
	 * @return saved Inventory entity
	 */
	@Override
	public Inventory getInventoryById(Long InventoryId) {
		Inventory inventory = inventoryRepository.findById(InventoryId).orElseThrow(()->new EntityNotFoundException("Inventory with InventoryId " + InventoryId + " not found"));
		
		return inventory;
	}
    
	/**
	 * saveInventory ->Method
	 * @param Inventory entity
	 * @return saved Inventory entity
	 */
	@Override
	public Inventory saveInventory(Inventory inventory) {
		if(inventoryRepository.findByErNo(inventory.getErNo())!=null) {
			throw new ResourceAlreadyExitException("er number already exists");
		
		
		}
			
		inventory.setCreatedOn(new Date());
		inventory.setUpdatedOn(null);
		inventory.setFarNo(inventory.getFarNo());
		
		inventory.setQRCreated("false");
		
		if(inventory.getAllocationStartDate()!=null) {
			LocalDate date=inventory.getAllocationStartDate();

			inventory.setAllocationEndDate(date.plusDays(inventory.getAllottedPeriod()-1));
		}
		
		inventory.setInventoryStatus(InventoryStatus.ACTIVE.toString());
		
		return inventoryRepository.save(inventory);
	}
    
	/**
	 * updateInventory ->Method
	 * @param Inventory entity
	 * @return updated Inventory entity
	 */
	@Override
	public Inventory updateInventory(Inventory inventory) {
		Inventory Inventory1 = inventoryRepository.findById(inventory.getInventoryId()).orElseThrow(()->new EntityNotFoundException("Inventory with InventoryId " + inventory.getInventoryId() + " not found"));
	//	AmcInventory amcInventory=amcInventoryRepository.FindAmcByInventoryId(Inventory1.getInventoryId());
		Inventory1.setQuantity(inventory.getQuantity());
	//	Inventory1.setStore(inventory.getStore());
		Inventory1.setUser(inventory.getUser());
		Inventory1.setWebMaster(inventory.getWebMaster());
		Inventory1.setDepartmentId(inventory.getDepartmentId());
		Inventory1.setAsset(inventory.getAsset());
		Inventory1.setUser(inventory.getUser());
		Inventory1.setUpdatedOn(new Date());
		
		Inventory1.setDescription(inventory.getDescription());
		Inventory1.setErNo(inventory.getErNo());
		Inventory1.setFarNo(inventory.getFarNo());
		
		Inventory1.setQRCreated("false");
		
		if(inventory.getAllocationStartDate()!=null) {
			LocalDate date=inventory.getAllocationStartDate();

			Inventory1.setAllocationEndDate(date.plusDays(inventory.getAllottedPeriod()-1));
		}
		
		if(inventory.getInventoryStatus()!=null) {
			if(inventory.getInventoryStatus().equalsIgnoreCase(InventoryStatus.INACTIVE.toString())) {
				Inventory1.setInventoryStatus(InventoryStatus.INACTIVE.toString());
			}
		}else{
			Inventory1.setInventoryStatus(InventoryStatus.ACTIVE.toString());
		}
		
		if(inventory.getSerialNumber()!=null) {
			Inventory1.setSerialNumber(inventory.getSerialNumber());
		}
		
	//	amcInventory.setAmcStatus(Inventory1.getAmcStatus());
		//amcInventoryRepository.save(amcInventory);
		return inventoryRepository.save(Inventory1);
	}
	
	@Override
	public Inventory updateInventory1(Inventory inventory) {
		Inventory Inventory1 = inventoryRepository.findById(inventory.getInventoryId()).orElseThrow(()->new EntityNotFoundException("Inventory with InventoryId " + inventory.getInventoryId() + " not found"));
	
		return inventoryRepository.save(inventory);
	}
    
	/**
	 * deleteInventoryById ->Method
	 * @param InventoryId
	 * @return none
	 */
	@Override
	public void deleteInventoryById(Long inventoryId) {
		Inventory Inventory = inventoryRepository.findById(inventoryId).orElseThrow(()->new EntityNotFoundException("Inventory with InventoryId " + inventoryId + " not found"));
	
		inventoryRepository.deleteById(inventoryId);
	}

	@Override
	public Inventory getAllVendorsByUsingInventoryById(Long id) {
		
		return null;
	}

	@Override
	public List<Inventory> getInventoryByBssinessId(Long id) {
		// TODO Auto-generated method stub
		return inventoryRepository.getInventoryByBssinessId(id);
	}

	@Override
	public List<Object[]> getAllExcel(String businessVerticalType, String department) {
		// TODO Auto-generated method stub
		return inventoryRepository.getAllExcel(businessVerticalType,department);
	}

	@Override
	public List<Object[]> getAllExcel() {
		// TODO Auto-generated method stub
		return inventoryRepository.getAllExcel();
	}

	@Override
	public List<Object[]> getAllExcel(String user1) {
		// TODO Auto-generated method stub
		return inventoryRepository.getAllExcel(user1);
	}
	
	@Override
	public List<Object[]> getAllExcelForVertical(String businessVerticalType) {
		// TODO Auto-generated method stub
		return inventoryRepository.getAllExcelForVertical(businessVerticalType);
	}

	@Override
	public List<Object[]> getAllStoreCode(String storecode) {
		// TODO Auto-generated method stub
		return inventoryRepository.getAllStoreCode(storecode);
	}

	@Override
	public Long getWebmasterByInventoryId(Long id) {
		// TODO Auto-generated method stub
		return inventoryRepository.getWebmasterByInventoryId(id);
	}


	@Scheduled(cron = "0 0 9 * * *")//9 am 
//	@Scheduled(cron="0 */2 * * * *") //for every 5 min
	public void MaintainancAlert() {
		// Sr-Notification email Trigger start--------------->
		List<Long> maintainaceIds=amcInventoryRepository.getAllAmcIds();
		
		for (Iterator iterator = maintainaceIds.iterator(); iterator.hasNext();) {
			Long long1 = (Long) iterator.next();
			AmcInventory amcInventory=amcInventoryRepository.findById(long1).get();
			
			//maintainance.getAmcInventory().getMaintainanceStartDate()
			LocalDate date=LocalDate.now();
			LocalDate date1=amcInventory.getMaintainanceStartDate().minusDays(5);
			
			if(date1.equals(date))
			{
				// Sr-Notification email Trigger start--------------->
				List<Object[]> amcInventoryList =amcInventoryRepository.AmcMaintainanceAlert(long1);
		for (Object[] rows : amcInventoryList) {
					
					/*
					 * srNotificationEmail_id:- System.out.println(rows[0]);
					 * activity_Name:-  System.out.println(rows[1]);
					 * service_request_id:- System.out.println(rows[2]);
					 * TOemail:-  System.out.println(rows[3]);
					 * CCEmail:- System.out.println(rows[4]);
					 */
			AmcInventory amcInventories = amcInventoryService.findById(((BigInteger) rows[2]).longValue());
					String mailSubjects = "";			
					String emailCcValue=(String) rows[4];
					List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());			
		            System.out.println(emailCc);
					String emailTOValue=(String) rows[3];
					List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
					System.out.println(emailTo);
			 

				  Mail mail = amcInventoryService.templeteMailAlert(amcInventories);
				  
				  Long id= amcInventories.getWebMaster().getWebMasterId();
				  mailSubjects = "Service Due Alert for the Equipment as per the AMC";
				  mail.setMailTo(emailTo);
				  mail.setMailCC(emailCc); 
				  mail.setMailSubject(mailSubjects);
				  if (mailStatus) { try {
					  maintainanceAlertEmailImpl.sendMultiPartEmail(mail);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} } 

				}
					
					System.out.println("the task is completed");

		// Sr-Notification Email TriggerEnd    <-------------------------


			
		}

		
	}

	}

	@SuppressWarnings("serial")
	public List<Object[]> getTotalInventory(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region) {
		// TODO Auto-generated method stub
		return inventoryRepository.getAllInventorys( businessVerticalTypeName, storeCode,region);
		/*
		return inventoryRepository.getAllInventory(new Specification<Inventory>() {
			@Override
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
							
					if (businessVerticalTypeName != null) {
									predicates.add(criteriaBuilder.and(criteriaBuilder
											.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

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

	@SuppressWarnings("serial")
	@Override
	public List<Object[]> getInventoryCountForAssignedAssetList(String businessVerticalType, String department, String user1) {
		return inventoryRepository.getInventoryCountForAssignedAssetList(businessVerticalType,department,user1);
		/*
		return inventoryRepository.findAll(new Specification<Inventory>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				//	query.orderBy(criteriaBuilder.desc(root.get("assetId")));
				if (businessVerticalType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("asset") .join("webMaster").get("webMasterName"), "%" + businessVerticalType + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		 */
	}



}
