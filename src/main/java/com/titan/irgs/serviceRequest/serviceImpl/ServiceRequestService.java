package com.titan.irgs.serviceRequest.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.serviceRequest.controller.Mail;
import com.titan.irgs.serviceRequest.domain.ServiceRequest;
import com.titan.irgs.serviceRequest.mapper.ServiceRequestMapper;
import com.titan.irgs.serviceRequest.repository.ServiceRequestRepository;
import com.titan.irgs.serviceRequest.service.IServiceRequestService;
import com.titan.irgs.user.repository.UserRepo;


																								

@Service
public class ServiceRequestService implements IServiceRequestService {

	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	ServiceRequestMapper serviceRequestMapper;
	
	@Override
	public ServiceRequest save(ServiceRequest serviceRequest) {
		String src=null;
		Long val=serviceRequestRepository.getIncrementedServiceRequestCodeLastRow();
		
		if(val==null ) {
			val=1l;
		}
		int len=4-String.valueOf(val).length();
		
		
		StringBuffer stringBuffer=new StringBuffer(0);
		 
		while(stringBuffer.length()<len) {
		
			stringBuffer.append('0');
		}
		stringBuffer.append(val);
		
		
		if(serviceRequest.getInventory()!=null) {
		 src=serviceRequest.getInventory().getAsset().getAssetCode()+"/"+
				                   serviceRequest.getInventory().getUser().getUsername()+"/BD"+stringBuffer;
		}
		
		if(serviceRequest.getMiscellaneousTypeDomain()!=null) {
			 src=serviceRequest.getMiscellaneousTypeDomain().getMiscellaneousTypeCode()+"/"+
					 serviceRequest.getStore().getStoreCode()+"/MS"+stringBuffer;
			}
		serviceRequest.setServiceRequestCode(src);
		serviceRequest.setServiceUpload("false");
		
		return serviceRequestRepository.save(serviceRequest);
	}

	@SuppressWarnings("serial")
	@Override
	public List<ServiceRequest> getAll(String srNumber, String businessVerticalTypeName, String storeIds,
			String assetCode, String erNumber, String vendorName, String breakDownType, String urgency,
			Long vendorId, String serviceRequestType, String ticketStatus, String runningStatus,
			String serviceRequestDate, String serviceRequestClosedDate,String vendorCode,String serviceDocumentUploaded,String department, Pageable page) {

		
		
		int end_page=page.getPageNumber() * page.getPageSize();
		int start_page=end_page-(page.getPageSize()-1);

		return serviceRequestRepository.getFilteredUsingProcedure(srNumber,businessVerticalTypeName
				,storeIds,assetCode,erNumber,vendorName,breakDownType,urgency,vendorId,serviceRequestType, ticketStatus,  runningStatus,
				 serviceRequestDate,  serviceRequestClosedDate,start_page,end_page,vendorCode,serviceDocumentUploaded,department);
	}
	
	@Override
	public Long count(String srNumber, String businessVerticalTypeName, String storeIds, String assetCode,
			String erNumber, String vendorName, String breakDownType, Long vendorId, String urgency,
			String serviceRequestType, String ticketStatus, String runningStatus, String serviceRequestDate,
			String serviceRequestClosedDate,String department) {
		
		
 		return serviceRequestRepository.count(srNumber,businessVerticalTypeName
 				,storeIds,assetCode,erNumber,vendorName,breakDownType, vendorId,urgency,serviceRequestType, ticketStatus,  runningStatus,
 				 serviceRequestDate,  serviceRequestClosedDate,department);
	}

	@Override
	public ServiceRequest getById(Long id) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("the requested id {} not found)" +id));
	}

	@Override
	public ServiceRequest update(ServiceRequest serviceRequest) {
		
		ServiceRequest serviceRequest1=serviceRequestRepository.getOne(serviceRequest.getServiceRequestId());
		if(serviceRequest1==null) {
			throw new ResourceNotFoundException("the requested id {} not found)" +serviceRequest.getServiceRequestId());
		}
		return serviceRequestRepository.save(serviceRequest);
	}

	
	
	//templete designing..
		@Override
		public Mail templeteMail(ServiceRequest serviceRequest) {
			Mail mail=new Mail();
			
			Map<String, Object> model = new HashMap<String, Object>();
		//	String 		geoLoc=serviceRequest.getStore().getLatitude()+","+serviceRequest.getStore().getLongitude();
		//	String geoLocPath="https://www.google.com/maps/dir//"+geoLoc;
			
		//	String geoLoc1=serviceRequest.getStore().getLongitude();

			String name=serviceRequest.getClosedBy()==null?"":userRepo.getOne(serviceRequest.getClosedBy()).getUsername();
			String phNo=serviceRequest.getClosedBy()==null?"":userRepo.getOne(serviceRequest.getClosedBy()).getPhoneNo();
			
			
		/*
		 * if(serviceRequest.getStore().getLatitude()!=null &&
		 * serviceRequest.getStore().getLongitude()!=null) {
		 * geoLoc=serviceRequest.getStore().getLatitude()+" / "+serviceRequest.getStore(
		 * ).getLongitude();
		 * 
		 * }
		 */
					
			model.put("Request", serviceRequest.getServiceRequestCode());
			model.put("Asset", serviceRequest.getAssetCode());
			
			model.put("Model", serviceRequest.getInventory()==null?"":serviceRequest.getInventory().getAsset().getModel().getModelNo());
			model.put("userName", serviceRequest.getInventory() == null ? "" :serviceRequest.getInventory().getUser().getUsername());
			model.put("BrandName", serviceRequest.getInventory() == null ? "" :serviceRequest.getInventory().getAsset().getModel().getBrand().getBrandName());
			model.put("EquipmentName", serviceRequest.getInventory() == null ? "" :serviceRequest.getInventory().getAsset().getEquipment().getEquipmentName());
			model.put("EquipmentName", serviceRequest.getInventory() == null ? "" :serviceRequest.getInventory().getAsset().getEquipment().getEquipmentName());
	model.put("ERNo", serviceRequest.getErNumber());
			model.put("VendorName",
					serviceRequest.getServiceVendorName() == null ? "" : serviceRequest.getServiceVendorName());
			model.put("VendorContact",
					serviceRequest.getServiceVendorName() == null ? "" : vendorRepository.getOne(serviceRequest.getServiceVendorId()).getContactNumber());
			model.put("ProblemDescription",
					serviceRequest.getProblemDescription() == null ? "" : serviceRequest.getProblemDescription());
			//		model.put("GeoLoc", geoLoc);
	//		model.put("GeoLocPath", geoLocPath);

			model.put("condition",(serviceRequest.getTicketStatus()!=null && serviceRequest.getTicketStatus().equalsIgnoreCase("CLOSE"))?true:false);
			model.put("condition",(serviceRequest.getTicketStatus()!=null && serviceRequest.getTicketStatus().equalsIgnoreCase("CLOSE"))?true:false);
			model.put("ReopenCondition",(serviceRequest.getTicketStatus()!=null && serviceRequest.getTicketStatus().equalsIgnoreCase("REOPEN"))?true:false);

			model.put("Issueraiseddate",serviceRequest.getServiceRequestDate());
			model.put("ServiceAge",serviceRequestMapper.getDiffDays(serviceRequest).getCountServiceRequestdays());
			model.put("IssueCloseddate",serviceRequest.getServiceRequestClosedDate()==null?"":serviceRequest.getServiceRequestClosedDate());
			model.put("IssueReopendate",serviceRequest.getReOpenDate()==null?"":serviceRequest.getReOpenDate());
			model.put("RegionForOpen",serviceRequest.getRegionForOpen()==null?"":serviceRequest.getRegionForOpen());
			model.put("Urgency",serviceRequest.getUrgency()==null?"":serviceRequest.getUrgency().getUrgencyName());
			model.put("Howtheproblemsolved",serviceRequest.getClousureProblem()==null?"":serviceRequest.getClousureProblem());
			model.put("RegionalEngineercomment",serviceRequest.getClousureDescription()==null?"":serviceRequest.getClousureDescription());
//			model.put("openForServiceAge", serviceRequest.getUpdatedOn()==null?false:true);
			model.put("Closedby",name+"   "+phNo);
            

			
			
			
			
			
			/*
		 * model.put("",serviceRequest.get); model.put("",); model.put("",);
		 * model.put("",); model.put("",); model.put("",);
		 */
			mail.setModel(model);
			return mail;
		}

		@Override
		public boolean checkIfErnumberAndStatusClosed(String erNumber) {
			// TODO Auto-generated method stub
			return serviceRequestRepository.checkIfErnumberAndStatusClosed(erNumber);
		}

		@Override
		public ServiceRequest findByErNumber(String erNumber) {
			// TODO Auto-generated method stub
			return serviceRequestRepository.findByErNumber(erNumber);
		}

	/*
	 * @Override public void batchEscalation(long countdays) {
	 * List<Sremailescalation> list
	 * =serviceRequestRepository.getSremailescalation(countdays); for
	 * (Sremailescalation sremailescalation : list) {
	 * System.out.println("--------------- "+sremailescalation.getEscalations(); }
	 * 
	 * }
	 */
	

		@Override
		public List<Object[]> getAllForExportServiceRequest(String fromDate, String toDate, String status, String breakDownType, String stringStoreIds, String businessVerticalTypeName, Long vendorId
				,String department) {
			return serviceRequestRepository.getAllForExportServiceRequest(fromDate, toDate, status, breakDownType, stringStoreIds, businessVerticalTypeName, vendorId,department);
		}	
	
		@SuppressWarnings("serial")
		@Override
		public List<Object[]> getAllSrCount(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region) {
		
		//	return serviceRequestRepository.getAllSrCount(storeCode, businessVerticalTypeName, vendorId, region);
			return serviceRequestRepository.getAllSrCountS(businessVerticalTypeName,storeCode,vendorId,region);
			
		/*
		 * return serviceRequestRepository.getAllSrCount(new
		 * Specification<ServiceRequest>() {
		 * 
		 * @Override public Predicate toPredicate(Root<ServiceRequest> root,
		 * CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) { List<Predicate>
		 * predicates = new ArrayList<>();
		 * 
		 * if (businessVerticalTypeName != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder
		 * .like(root.join("webMaster").get("webMasterName"), "%" +
		 * businessVerticalTypeName + "%")));
		 * 
		 * }
		 * 
		 * if (vendorId != null) { predicates.add(criteriaBuilder
		 * .and(criteriaBuilder.like(root.get("serviceVendorId"), "%" + vendorId +
		 * "%")));
		 * 
		 * }
		 * 
		 * if (storeCode != null) {
		 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(
		 * root.join("store").get("storeCode"), "%" + storeCode + "%")));
		 * 
		 * }
		 * 
		 * if (region != null) {
		 * predicates.add((root.join("store").join("region").get("regionId").in(region))
		 * );
		 * 
		 * }
		 * 
		 * return criteriaBuilder.and(predicates.toArray(new
		 * Predicate[predicates.size()])); } });
		 */		
		}
	
		@SuppressWarnings("serial")
		@Override
		public List<Object[]> getTotalSrAndAmcSrCount(String businessVerticalTypeName, String storeCode,Long vendorId,List<Long> region) {
		
			return serviceRequestRepository.getTotalSrAndAmcSrCount();
		
		}

		@SuppressWarnings("serial")
		@Override
		public List<Object[]> getSrCountForAllVendors(String businessVerticalTypeName, 
				String storeCode, Long vendorId, List<Long> region) {
			return serviceRequestRepository.getSrCountForAllVendor(businessVerticalTypeName,storeCode,vendorId,region);
			
	/*		return serviceRequestRepository.getSrCountForAllVendors(new Specification<ServiceRequest>() {
				@Override
				public Predicate toPredicate(Root<ServiceRequest> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();
								
						if (businessVerticalTypeName != null) {
										predicates.add(criteriaBuilder.and(criteriaBuilder
												.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

						}
						
					if (vendorId != null) {
						predicates.add(criteriaBuilder
								.and(criteriaBuilder.like(root.get("serviceVendorId"), "%" + vendorId + "%")));

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
		public List<Object[]> getAllSrCountForAllRegion(String businessVerticalTypeName,
				String storeCode, Long vendorId, List<Long> region) {
			// TODO Auto-generated method stub
			return serviceRequestRepository.getAllSrCountForAllRegions(businessVerticalTypeName,storeCode,vendorId,region);
		/*	List<Object[]> counts= serviceRequestRepository.getAllSrCountForAllRegion(new Specification<ServiceRequest>(){
			@Override
			public Predicate toPredicate(Root<ServiceRequest> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
							
					if (businessVerticalTypeName != null) {
									predicates.add(criteriaBuilder.and(criteriaBuilder
											.like(root.join("webMaster").get("webMasterName"), "%" + businessVerticalTypeName + "%")));

					}
					
				if (vendorId != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("serviceVendorId"), "%" + vendorId + "%")));

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
		return counts;
		*/
			
		}
		
		@Override
		public List<Object[]> getAllSrCountForAllEquipment(String businessVerticalTypeName,
				String storeCode, Long vendorId, List<Long> region) {
			// TODO Auto-generated method stub
			return serviceRequestRepository.getAllSrCountForAllEquipments(businessVerticalTypeName,storeCode,vendorId,region);
		}

		@SuppressWarnings("serial")
		@Override
		public List<ServiceRequest> getClosedServiceRequests(String serviceRequestCode) {
			return serviceRequestRepository.findAll(new Specification<ServiceRequest>() {
				public Predicate toPredicate(Root<ServiceRequest> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();

					String status="CLOSE";
					predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("ticketStatus"),"%" + status + "%")));

					if (serviceRequestCode != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("serviceRequestCode"),"%" + serviceRequestCode + "%")));

					}

					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			});
		}

		@Override
		public List<Object[]> getServiceRequestCountForDashboard(String businessVerticalType,String user1,String department,String vendorCode) {
			// TODO Auto-generated method stub
			return serviceRequestRepository.getServiceRequestCountForDashboard(businessVerticalType,user1,department,vendorCode);
		}
	
}

