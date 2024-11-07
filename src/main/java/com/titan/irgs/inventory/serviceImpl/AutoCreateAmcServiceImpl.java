package com.titan.irgs.inventory.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.titan.irgs.inventory.domain.AutoAmcInventoryLog;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.AutoAmcInventoryLogRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.service.AutoCreateAmcService;

@Service
public class AutoCreateAmcServiceImpl implements AutoCreateAmcService{

	@Autowired
	AutoAmcInventoryLogRepository autoAmcInventoryLogRepository;
	
	@Autowired
	MaintainanceRepository maintainanceRepository;
	
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;
	@Override
	public AutoAmcInventoryLog findById(Long amcId) {
		// TODO Auto-generated method stub
		AutoAmcInventoryLog maintainance=autoAmcInventoryLogRepository.findById(amcId).get();
		return maintainance;
	}

	@SuppressWarnings("serial")
	public Page<AutoAmcInventoryLog> getAllAutoAmcInventoryLog(String businessVerticalTypeName,String serviceRequestCode,String erno,
			String vendorCode, String storeCode, List<Long> region, String serviceRequestDate,String maintainanceStartDate, String maintainanceEndDate,
			 String serviceCreationStatus,Long numberOfService,String isMailSent,String fromDate, String toDate,String department,Pageable pageable) {
		// TODO Auto-generated method stub
		return autoAmcInventoryLogRepository.findAll(new Specification<AutoAmcInventoryLog>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<AutoAmcInventoryLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
							if (serviceRequestCode != null) {
							//	AmcWarranty amcWarranty=amcWarrantyRepository.findWarrantyByAmcIds(numberOfService);
							//	Maintainance maintainance=maintainanceRepository.findByMaintainanceId(numberOfService)
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("serviceRequestCode"), "%" + serviceRequestCode + "%")));
				
							}
					if (businessVerticalTypeName != null) {
									predicates.add(criteriaBuilder.and(criteriaBuilder
											.like(root.join("amcInventory").join("asset").join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

					}
					if (erno != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder
								.like(root.join("amcInventory").join("inventory").get("erNo"), "%"+  erno +"%" )));

					}
				

				if (vendorCode != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.join("amcInventory").join("vendor").get("vendorCode"), "%" + vendorCode + "%")));

				}
			
				if (storeCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("amcInventory").join("user").get("username"),
							"%" + storeCode + "%")));

				}
				
				if (department != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("amcInventory").join("asset").join("department").get("departmentName"),
							"%" + department + "%")));

				}

				if (region != null) {
					predicates.add((root.join("amcInventory").join("store").join("region").get("regionId").in(region)));

				}
				if (serviceRequestDate != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					   LocalDate localDate = LocalDate.parse(serviceRequestDate, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("createdDate"), localDate )));
					
				}
				 if(fromDate!="" && fromDate!=null && toDate!=null && toDate!="") {
				//	  Date fromdate=null;
				//	  fromdate=StringToDate(maintainanceEndDate);
				//	  Date enddate=null;
					  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					   LocalDate fromdate = LocalDate.parse(fromDate, formatter);
					   LocalDate enddate = LocalDate.parse(toDate, formatter);
				//	  enddate=StringToDate(maintainanceEndDate);
					  predicates.add(criteriaBuilder.between(root.get("createdDate"), fromdate,enddate));
				  }
				if (maintainanceStartDate != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					   LocalDate localDate = LocalDate.parse(maintainanceStartDate, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.greaterThanOrEqualTo(root.join("amcInventory").get("maintainanceStartDate"), localDate )));
					
				}
				
				if (maintainanceEndDate != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					   LocalDate localDate = LocalDate.parse(maintainanceEndDate, formatter);
					   predicates.add(criteriaBuilder.and(criteriaBuilder
								.lessThanOrEqualTo(root.join("amcInventory").get("maintainanceEndDate"), localDate )));
				//	predicates.add(criteriaBuilder.and(criteriaBuilder
				//			.equal(root.join("maintainance").join("amcInventory").get("maintainanceEndDate"), localDate )));
					
				}
				
				if (numberOfService != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
							root.join("amcInventory").get("numberOfService"),
							numberOfService )));

				}
				if (serviceCreationStatus != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("serviceCreationStatus"),"%" + serviceCreationStatus + "%")));

				}
				if (isMailSent != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("isMailSent"),"%" + isMailSent + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	

	}
	public List<AutoAmcInventoryLog> getAllmaintainance() {
		// TODO Auto-generated method stub
		return autoAmcInventoryLogRepository.findAll();
	}

	@SuppressWarnings("serial")
	@Override
	public Page<AutoAmcInventoryLog> getAllMaintainanceAutoLogWithPagination(Pageable page) {
		
	//		return  autoAmcInventoryLogRepository.getAll(page);

	 return autoAmcInventoryLogRepository.findAll(new Specification<AutoAmcInventoryLog>() {
		
		@Override
		public Predicate toPredicate(Root<AutoAmcInventoryLog> root, CriteriaQuery<?> query,
		 CriteriaBuilder criteriaBuilder) {
		 List<Predicate> predicates = new ArrayList<>();
		/*
	    if (designationName != null) {
		                    predicates.add(
		                            criteriaBuilder.and(criteriaBuilder.like(root.get("designationName"), "%" + designationName + "%")));
		 
		                }
		 */
      return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		}
		}, page);
		}

	@SuppressWarnings("serial")
	public List<AutoAmcInventoryLog> getAllForExportAutoAMCServiceRequest(String fromDate, String toDate, String stringStoreIds,
			String businessVerticalTypeName,  String vendorCode, List<Long> region,String department) {
		// TODO Auto-generated method stub
		return autoAmcInventoryLogRepository.findAll(new Specification<AutoAmcInventoryLog>() {
			@SuppressWarnings({ "rawtypes", "static-access" })
			@Override
			public Predicate toPredicate(Root<AutoAmcInventoryLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
			/*
				if (fromDate != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					   LocalDate localDate = LocalDate.parse(fromDate, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.join("maintainance").join("amcInventory").get("maintainanceStartDate"), localDate )));
					
				}
				
				if (toDate != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					   LocalDate localDate = LocalDate.parse(toDate, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.join("maintainance").join("amcInventory").get("maintainanceEndDate"), localDate )));
					
				}
				*/
				 if(fromDate!="" && fromDate!=null && toDate!=null && toDate!="") {
						//	  Date fromdate=null;
						//	  fromdate=StringToDate(maintainanceEndDate);
						//	  Date enddate=null;
							  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							   LocalDate fromdate = LocalDate.parse(fromDate, formatter);
							   LocalDate enddate = LocalDate.parse(toDate, formatter);
						//	  enddate=StringToDate(maintainanceEndDate);
							  predicates.add(criteriaBuilder.between(root.get("createdDate"), fromdate,enddate));
						  }
					
					if (businessVerticalTypeName != null) {
									predicates.add(criteriaBuilder.and(criteriaBuilder
											.like(root.join("amcInventory").join("asset").join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

					}
					
					if (vendorCode != null) {
						predicates.add(criteriaBuilder
								.and(criteriaBuilder.like(root.join("amcInventory").join("vendor").get("vendorCode"), "%" + vendorCode + "%")));

		}
					
				if (region != null) {
					predicates.add((root.join("amcInventory").join("store").join("region").get("regionId").in(region)));

				}
				//This is for array of stringStoreIds ids
				  if(stringStoreIds!=null) {
				  predicates.add(criteriaBuilder.and(root.join("amcInventory").join("user").get("id").in(stringStoreIds))); }
				 

				  if (department != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.like(
								root.join("amcInventory").join("asset").join("department").get("departmentName"),
								"%" + department + "%")));

					}
				  
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});

	}

	public Date StringToDate(String s){

	    Date result = null;
	    try{
	    	if(s!="") {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    //		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        result  = dateFormat.parse(s);
	    	}else if(s=="") {
	    		 SimpleDateFormat dateFormat = new SimpleDateFormat("");
	 	        result  = dateFormat.parse(s);
	    	}
	    }

	    catch(ParseException e){
	        e.printStackTrace();

	    }
	    return result ;
	}

}
