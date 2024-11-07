package com.titan.irgs.inventory.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.domain.Maintainance;
import com.titan.irgs.inventory.mapper.MaintainanceMapper;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.repository.MaintainanceUploadRepository;
import com.titan.irgs.inventory.service.MaintainanceService;
import com.titan.irgs.master.domain.Engineer;
import com.titan.irgs.master.repository.EngineerRepository;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.user.repository.UserRepo;

@Service
public class MaintainanceServiceImpl implements MaintainanceService{
	
	@Autowired
	MaintainanceRepository maintainanceRepository;
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;
	@Autowired
	MaintainanceMapper maintainanceMapper;
	@Autowired
	UserRepo userRepo;
	@Autowired
	EngineerRepository engineerRepo;
	
	@Autowired
	MaintainanceUploadRepository maintainanceUploadRepository;
	
	 @PersistenceContext
	 private EntityManager em;
	
	@Override
	public List<Maintainance> getAllmaintainance() {
		// TODO Auto-generated method stub
		return maintainanceRepository.findAll();
	}

	@Override
	public Maintainance saveMaintainance(Maintainance maintainance) {
		String src=null;
		Long val=maintainanceRepository.getIncrementedServiceRequestCodeLastRow();
		
		if(val==null ) {
			val=1l;
		}
		int len=4-String.valueOf(val).length();
		
		
		StringBuffer stringBuffer=new StringBuffer(0);
		 
		while(stringBuffer.length()<len) {
		
			stringBuffer.append('0');
		}
		stringBuffer.append(val);
		
		src=maintainance.getAmcInventory().getAsset().getEquipment().getEquipmentCode()+"/"+maintainance.getAmcInventory().getUser().getUsername()+"/AMC"+stringBuffer;
		maintainance.setServiceRequestCode(src);
		maintainance.setTicketStatus("OPEN");
		maintainance.setRunningStatus("SR created");
		maintainance.setServiceRequesTypetId(Long.parseLong("3"));
		maintainance.setMaintainanceDateTime(new Date());
		maintainance.setMaintainanceUpload("false");
		return maintainanceRepository.save(maintainance);
	}

	@Override
	public Maintainance getByMaintainanceId(Long maintainanceId) {
		// TODO Auto-generated method stub
		Maintainance maintainance=maintainanceRepository.findById(maintainanceId).get();
		return maintainance;
	}

	@Override
	public Maintainance deleteByMaintainanceId(Long maintainanceId) {
		// TODO Auto-generated method stub
		Maintainance maintainance=maintainanceRepository.findById(maintainanceId).get();
		maintainanceRepository.delete(maintainance);
		return null;
	}

	@Override
	public Maintainance updateMaintainance(Maintainance maintainance) {
		Maintainance maintainance1=maintainanceRepository.findByMaintainanceId(maintainance.getMaintainanceId());
		maintainance1.setAmcInventory(maintainance.getAmcInventory());
		maintainance1.setProposedVisitDate(maintainance.getProposedVisitDate());
		maintainance1.setServicedBy(maintainance.getServicedBy());
		maintainance1.setServicedByPhone(maintainance.getServicedByPhone());
		maintainance1.setVisitAcceptedBy(maintainance.getVisitAcceptedBy());
		maintainance1.setVisitAcceptedDateTime(maintainance.getVisitAcceptedDateTime());
		maintainance1.setApprovedBy(maintainance.getApprovedBy());
		maintainance1.setApprovedDateTime(maintainance.getApprovedDateTime());
		//maintainance1.setMaintainanceDateTime(maintainance.getMaintainanceDateTime());
		maintainance1.setServiceRequesTypetId(Long.parseLong("3"));
		maintainance1.setReportRef(maintainance.getReportRef());
		maintainance1.setSparesInvoiceRef(maintainance.getSparesInvoiceRef());
		
		maintainance1.setTicketStatus(maintainance.getTicketStatus());
		if(maintainance1.getTicketStatus().equalsIgnoreCase("CLOSE")) {
			maintainance1.setServiceRequestcloseDate(new Date());
		}
		maintainance1.setClousureDescription(maintainance.getClousureDescription());
		maintainance1.setClousureProblem(maintainance.getClousureProblem());
		maintainance1.setEngineerId(maintainance.getEngineerId());
		maintainance1.setStoreClosureBy(maintainance.getStoreClosureBy());
		maintainance1.setStoreClosureDate(maintainance.getStoreClosureDate());
		maintainance1.setVendorClosureby(maintainance.getVendorClosureby());
		maintainance1.setVendorClosureDate(maintainance.getVendorClosureDate());
	//	maintainance1.setRunningStatus(maintainance.getRunningStatus());
		maintainance1.setOngoingMRStatus(maintainance.getOngoingMRStatus());
		maintainance1.setQuoteInvoiceRef(maintainance.getQuoteInvoiceRef());
		maintainance1.setPoInvoiceRef(maintainance.getPoInvoiceRef());
		//maintainance1.setServiceRequestCode(maintainance.getServiceRequestCode());
		maintainance1.setEngineerComment(maintainance.getEngineerComment());
		//maintainance1.setWarrantyId(maintainance.getWarrantyId());
		maintainance1.setRunningStatus(maintainance.getRunningStatus());
		
		if(maintainance1.getWarrantyId()!=null) {
		AmcWarranty amcWarranty=amcWarrantyRepository.getOne(maintainance1.getWarrantyId());
		amcWarranty.setCreatedDate(maintainance1.getMaintainanceDateTime());
		amcWarranty.setTicketStatus(maintainance1.getTicketStatus());
		amcWarranty.setClosedDate(maintainance1.getServiceRequestcloseDate());
		amcWarrantyRepository.save(amcWarranty);
		}
		

		return maintainanceRepository.save(maintainance1);
	}
	
	@Override
	public Maintainance updateMaintainanceForFileUpload(Maintainance maintainance) {
		Maintainance maintainance1=maintainanceRepository.findByMaintainanceId(maintainance.getMaintainanceId());
		return maintainanceRepository.save(maintainance1);
	}

	@Override
	public List<Maintainance> getAll(String serviceRequestCode, String businessVerticalTypeName, String stringStoreIds,
			String assetCode, String erNo, String vendorName, String farNo,  String serviceRequestType,
			String vendorCode, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate, Pageable page) {
		// TODO Auto-generated method stub
		
		
		int end_page=page.getPageNumber() * page.getPageSize();
		int start_page=end_page-(page.getPageSize()-1);

		return maintainanceRepository.getFilteredUsingProcedure(serviceRequestCode, businessVerticalTypeName,  stringStoreIds,
				 assetCode,  erNo,  vendorName,  farNo,  serviceRequestType,
				 vendorCode,  ticketStatus,  runningStatus,  serviceRequestDate,
				 serviceRequestClosedDate,start_page,end_page);

	}

	@Override
	public Long count(String serviceRequestCode, String businessVerticalTypeName, String stringStoreIds,
			String assetCode, String erNo, String vendorName, String farNo,  String serviceRequestType,
			String vendorCode, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate) {
		// TODO Auto-generated method stub
		return maintainanceRepository.count(serviceRequestCode, businessVerticalTypeName,  stringStoreIds,
				 assetCode,  erNo,  vendorName,  farNo,    serviceRequestType,
				 vendorCode,  ticketStatus,  runningStatus,  serviceRequestDate,
				 serviceRequestClosedDate);

	}

	@SuppressWarnings("serial")
	@Override
	public Page<Maintainance> getAllMaintainance(String srNumber, String businessVerticalTypeName,
			 String assetCode, String erNo, String vendorName, String farNo,
			String serviceRequestType, String vendorCode, String ticketStatus, String runningStatus,
			String serviceRequestDate, String serviceRequestClosedDate, String storeCode, List<Long> region,
			String serviceDocumentUploaded,String department,Pageable pageable) {

		return maintainanceRepository.findAll(new Specification<Maintainance>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Maintainance> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
							if (srNumber != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("serviceRequestCode"), "%" + srNumber + "%")));

				}
					if (businessVerticalTypeName != null) {
									predicates.add(criteriaBuilder.and(criteriaBuilder
											.like(root.join("amcInventory").join("asset").join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

					}
					if (erNo != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder
								.like(root.join("amcInventory").join("inventory").get("erNo"), "%"+  erNo +"%" )));

					}

				if (assetCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("amcInventory").join("asset").get("assetCode"), "%" + assetCode + "%")));

				}
				

				if (vendorCode != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.join("amcInventory").join("vendor").get("vendorCode"), "%" + vendorCode + "%")));

				}
				if (ticketStatus != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("ticketStatus"), "%" + ticketStatus + "%")));

				}
				if (runningStatus != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("runningStatus"), "%" + runningStatus + "%")));

				}
				if (storeCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							root.join("amcInventory").join("user").get("username"),
							"%" + storeCode + "%")));

				}

				if (region != null) {
					predicates.add((root.join("amcInventory").join("store").join("region").get("regionId").in(region)));

				}
				if (vendorName != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.join("amcInventory").join("vendor").get("vendorName"), "%" + vendorName + "%")));

				}
				if(serviceRequestDate!=null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(serviceRequestDate, formatter);
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("maintainanceDateTime").as(LocalDate.class), date)));
				}
				if(serviceRequestClosedDate!=null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(serviceRequestClosedDate, formatter);
					
				
					   predicates.add(criteriaBuilder.and(criteriaBuilder
							.equal(root.get("serviceRequestcloseDate").as(LocalDate.class),date)));
				}
				
				if(serviceDocumentUploaded!=null) {
					
					
						predicates.add(criteriaBuilder
								.and(criteriaBuilder.equal(root.get("maintainanceUpload"), serviceDocumentUploaded)));

				}
				
				if (department != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("amcInventory").join("asset").join("department").get("departmentName"), "%" + department + "%")));

				}
				
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}
	@Override
	public Mail templeteMail(Maintainance maintainance) {
		Mail mail=new Mail();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ServiceRequestCode", maintainance.getAmcInventory() == null ? "" :maintainance.getServiceRequestCode());
		model.put("userName", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getUser().getUsername());
		//	String 		geoLoc=maintainance.getAmcInventory().getStore().getLatitude()+","+maintainance.getAmcInventory().getStore().getLongitude();
	//	String geoLocPath="https://www.google.com/maps/dir//"+geoLoc;
	//	model.put("GeoLoc", geoLoc);
	//	model.put("GeoLocPath", geoLocPath);
		model.put("EquipmentName", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getAsset().getEquipment().getEquipmentName());
		model.put("BrandName", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getAsset().getModel().getBrand().getBrandName());
		model.put("Model", maintainance.getAmcInventory()==null?"":maintainance.getAmcInventory().getAsset().getModel().getModelNo());
		model.put("Asset", maintainance.getAmcInventory().getAsset().getAssetCode());
		model.put("BusinessVerticalName", maintainance.getAmcInventory()==null?"":maintainance.getAmcInventory().getWebMaster().getWebMasterName());
		model.put("AssetName", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getAsset().getAssetName());
		model.put("FARNo", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getAsset().getFarNo());
		model.put("ModelName", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getAsset().getModel().getModelName());
		model.put("VendorCode", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getVendor().getVendorCode());
		model.put("VendorName", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getVendor().getVendorName());
		model.put("VendorContact",maintainance.getAmcInventory() == null ? "" : maintainance.getAmcInventory().getVendor().getContactNumber());
		model.put("InstallationDate", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getInstallationDate());
		model.put("MaintenanceStartDate", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getMaintainanceStartDate());
		model.put("MaintenanceEndDate", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getMaintainanceEndDate());
		model.put("NumberOfService", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getNumberOfService());
		model.put("MaintenanceGap", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getMinMaintainanceGap());
		model.put("MaintenancePeriod", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getMaintainancePeriod());
		model.put("MaintenanceValidity", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getMaintainanceValidity());
		model.put("ContactNumber", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getContractNumber());
		model.put("AMCStatus", maintainance.getAmcInventory() == null ? "" :maintainance.getAmcInventory().getAmcStatus());
		model.put("Issueraiseddate",maintainance.getMaintainanceDateTime());
		model.put("ServiceAge",maintainanceMapper.getDiffDays(maintainance).getCountServiceRequestdays());
		if(maintainance.getRunningStatus()!=null) {
		model.put("engineerAssign",maintainance.getRunningStatus().equalsIgnoreCase("Assigning Engineer")?true:false);
		}
		if(maintainance.getRunningStatus()!=null && maintainance.getRunningStatus().equalsIgnoreCase("Assigning Engineer")) {
		Engineer engineer=engineerRepo.getOne(maintainance.getEngineerId());
		model.put("engineer",engineer.getEngineerName());
		model.put("engineerContact",engineer.getMobileNo());
		model.put("engineerEmail",engineer.getEmailId());}
	
		model.put("engineerVisit",maintainance.getVisitAcceptedDateTime());
		model.put("RegionalEngineercomment",maintainance.getClousureDescription()==null?"":maintainance.getClousureDescription());
		model.put("Howtheproblemsolved",maintainance.getClousureProblem()==null?"":maintainance.getClousureProblem());
		model.put("IssueCloseddate",maintainance.getServiceRequestcloseDate()==null?"":maintainance.getServiceRequestcloseDate());
		String name=maintainance.getClosedBy()==null?"":userRepo.getOne(maintainance.getClosedBy()).getUsername();
		String phNo=maintainance.getClosedBy()==null?"":userRepo.getOne(maintainance.getClosedBy()).getPhoneNo();
		model.put("Closedby",name+"   "+phNo);
		mail.setModel(model);
		return mail;
	}

	@Override
	public List<Object[]> getAllForExportAMCServiceRequest(String fromDate, String toDate, String status,
			String breakDownType, String stringStoreIds, String businessVerticalTypeName, Long vendorId,String region) {
		// TODO Auto-generated method stub
		return maintainanceRepository.getAllForExportAMCServiceRequest(fromDate, toDate, status, breakDownType, stringStoreIds, businessVerticalTypeName, vendorId,region);
	}

	@Override
	public List<Object[]> getAllForExportAutoAMCServiceRequest(boolean status, String businessVerticalTypeName,
			Long vendorId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@SuppressWarnings("serial")
	@Override
	public List<Maintainance> getAllMaintainanceforauto(String serviceRequestCode, String businessVerticalTypeName,
			 String assetCode, String erNo, String vendorName, String farNo,
			String serviceRequestType, String vendorCode, String ticketStatus, String runningStatus,
			String serviceRequestDate, String serviceRequestClosedDate, String storeCode, List<Long> region,String department,
			Pageable page) {

		int end_page=page.getPageNumber() * page.getPageSize();
		int start_page=end_page-(page.getPageSize()-1);
		return maintainanceRepository.getAllMaintainanceforauto(serviceRequestCode, businessVerticalTypeName, storeCode, 
				assetCode, erNo, vendorName, farNo, serviceRequestType, vendorCode, ticketStatus, runningStatus, serviceRequestDate, serviceRequestClosedDate, region, start_page, end_page,department);
	}

	/*
	 * @Override public Maintainance updateByMaintainanceId(Long maintainanceId,
	 * Maintainance maintainance) { // TODO Auto-generated method stub Maintainance
	 * maintainances=maintainanceRepository.findById(maintainanceId).get();
	 * maintainances.setMaintainanceStartDate(maintainance.getMaintainanceStartDate(
	 * ));
	 * maintainances.setMaintainanceEndDate(maintainance.getMaintainanceEndDate());
	 * maintainances.setMaintainanceValidity(maintainance.getMaintainanceValidity())
	 * ; maintainances.setContractNumber(maintainance.getContractNumber());
	 * maintainances.setMinMaintainanceGap(maintainance.getMinMaintainanceGap());
	 * maintainances.setNumberOfService(maintainance.getNumberOfService()); return
	 * maintainanceRepository.save(maintainances); }
	 */

	@SuppressWarnings("serial")
	@Override
	public List<Object[]> getAllAmcSrCount(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region) {
	
		return maintainanceRepository.getAllAmcSrCounts(businessVerticalTypeName,storeCode,vendorId,region);
	
		/*
		 * return maintainanceRepository.getAllAmcSrCountwithNew(new
		 * Specification<Maintainance>() {
		 * 
		 * @Override public Predicate toPredicate(Root<Maintainance> root,
		 * CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) { List<Predicate>
		 * predicates = new ArrayList<>();
		 * 
		 * if (businessVerticalTypeName != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder
		 * .like(root.join("amcInventory").join("assetWebMaster").get("webMasterName"),
		 * "%" + businessVerticalTypeName + "%")));
		 * 
		 * }
		 * 
		 * if (vendorId != null) { predicates.add(criteriaBuilder
		 * .and(criteriaBuilder.like(root.join("amcInventory").join("vendor").get(
		 * "vendorId"), "%" + vendorId + "%")));
		 * 
		 * }
		 * 
		 * if (storeCode != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(
		 * root.join("amcInventory").join("store").get("storeCode"), "%" + storeCode +
		 * "%")));
		 * 
		 * }
		 * 
		 * if (region != null) {
		 * predicates.add((root.join("amcInventory").join("store").join("region").get(
		 * "regionId").in(region)));
		 * 
		 * }
		 * 
		 * return criteriaBuilder.and(predicates.toArray(new
		 * Predicate[predicates.size()])); } });
		 */
	 
		
	}

	/*
	@SuppressWarnings("serial")
	@Override
	public List<Maintainance> getAllAmcSrCountNewOne(String businessVerticalTypeName, String storeCode, Long vendorId, List<Long> region) {
	    List<Maintainance> result = maintainanceRepository.findAll((root, query, criteriaBuilder) -> {
	        List<Predicate> predicates = new ArrayList<>();

	        if (businessVerticalTypeName != null) {
	            predicates.add(criteriaBuilder.like(root.join("amcInventory").join("assetWebMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%"));
	        }

	        if (vendorId != null) {
	            predicates.add(criteriaBuilder.like(root.join("amcInventory").join("vendor").get("vendorId"), "%" + vendorId + "%"));
	        }

	        if (storeCode != null) {
	            predicates.add(criteriaBuilder.like(root.join("amcInventory").join("store").get("storeCode"), "%" + storeCode + "%"));
	        }

	        if (region != null) {
	            predicates.add(root.join("amcInventory").join("store").join("region").get("regionId").in(region));
	        }

	        query.multiselect((criteriaBuilder.construct(
	        	AmcSrCountDTO.class,
	            criteriaBuilder.count(
	                criteriaBuilder.selectCase()
	                    .when(criteriaBuilder.equal(root.get("ticket_status"), "OPEN"), 1)
	                    .otherwise(0)
	            ).alias("openCount"),
	            criteriaBuilder.count(
	                criteriaBuilder.selectCase()
	                    .when(criteriaBuilder.equal(root.get("ticket_status"), "CLOSE"), 1)
	                    .otherwise(0)
	            ).alias("closeCount"),
	            criteriaBuilder.count(root.get("maintainance_id")).alias("totalCount")
	       )));

	     //   query.groupBy(root.get("some_grouping_column")); // You need to specify a grouping column here

	        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	    });

	    return result;
	}
*/

	@Override
	public List<Object[]> getAllAmcSrCountForAllVendors(String businessVerticalTypeName,
			String storeCode, Long vendorId, List<Long> region) {
		// TODO Auto-generated method stub
	
		return maintainanceRepository.getAllAmcSrCountForAllVendor(businessVerticalTypeName,storeCode,vendorId,region);
		
	}
	
	@Override
	public List<Object[]> getAllAmcSrCountForAllRegion(String businessVerticalTypeName, 
			String storeCode, Long vendorId, List<Long> region) {
		// TODO Auto-generated method stub
		return maintainanceRepository.getAllAmcSrCountForAllRegions(businessVerticalTypeName,storeCode,vendorId,region);
	}
	
	@Override
	public List<Object[]> getAllAmcSrCountForAllEquipment(String businessVerticalTypeName, 
			String storeCode, Long vendorId, List<Long> region) {
		// TODO Auto-generated method stub
		return maintainanceRepository.getAllAmcSrCountForAllEquipments(businessVerticalTypeName,storeCode,vendorId,region);
	}
	
	public Date StringToDate(String s){

	    Date result = null;
	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        result  = dateFormat.parse(s);
	    }

	    catch(ParseException e){
	        e.printStackTrace();

	    }
	    return result ;
	}

	@Override
	public List<Object[]> getAMCServiceRequestCountForDashboard(String businessVerticalType,String department, String vendorCode) {
		// TODO Auto-generated method stub
		return maintainanceRepository.getAMCServiceRequestCountForDashboard(businessVerticalType,department,vendorCode);
	}
	
}
