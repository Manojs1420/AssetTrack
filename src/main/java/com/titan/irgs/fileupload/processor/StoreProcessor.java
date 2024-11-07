package com.titan.irgs.fileupload.processor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.domain.RegionDetails;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.master.enums.StoreStatus;
import com.titan.irgs.master.repository.RegionDetailsRepository;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.service.ICountryService;
import com.titan.irgs.master.serviceImpl.CityService;
import com.titan.irgs.master.serviceImpl.OwnerTypeService;
import com.titan.irgs.master.serviceImpl.RegionService;
import com.titan.irgs.master.serviceImpl.StateService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.master.serviceImpl.StoreServiceTypeService;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.repository.RoleRepository;
import com.titan.irgs.role.serviceImpl.RoleService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@Component
public class StoreProcessor extends UploadedDocProcessor {

	
	public static Logger logger = LoggerFactory.getLogger(StoreProcessor.class);


	@Autowired
	private ICountryService countryService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private OwnerTypeService ownerTypeService;
	
	@Autowired
	private WebMasterService webMasterService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StoreServiceTypeService storeServiceTypeService;
	
	@Autowired
	private WebMasterRepo webMasterRepo;

	
	@Autowired
	private WebRoleRepo webRoleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private RegionDetailsRepository regionDetailsRepository;
	
	@Autowired
	private RegionRepository regionRepository;



	public StoreProcessor() 
	{

	}

	public StoreProcessor(String templateName) 
	{
                super(templateName);
	}
	
	@SuppressWarnings("unused")
	public List<String> importStoreRecords(InputStream inp) throws Exception 
	{
      
		List<String> errors = new ArrayList<>();
			Workbook wb;
			Sheet sheet;
			String errorInRow = null;
			
		

    
			String storeId = null;
			String storeName = null;
			String businessVerticalName = null;
			String storeCode = null;
			String costCentre = null;
			String ownerType = null;
			String emailId = null;
			String phoneNo1 = null;
			String state = null;
			String city = null;
			String region = null;
			String storeLocality = null;
			String  reportingTo= null;
			String storeType = null;
			String status = null;
			//String role = null;
			
			Store store= null;
			OwnerType ownerTypeObj = null;
			City cityObj = null;
			State stateObj = null;
			
			Region regionMasterObj = null;
			RegionDetails regionObj = null;
			User user = null;
			WebMaster webMaster = null;
			StoreServiceType storeServiceType = null;
			WebRole webRole = null;
			Role role = null;
    
    Country country= null;
    int rowCount=1;
    

    wb = WorkbookFactory.create(inp);
    sheet = wb.getSheetAt(0);
    
    for (Row currRow : sheet) {
		if (currRow.getPhysicalNumberOfCells() == 0)
			return errors;
		    errorInRow = null;

		if (currRow.getRowNum() != 0) {
			currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			storeId = getValueBasedOnType(currRow.getCell(0));

			if (storeId != null && storeId.isEmpty()) {
				storeId = null;
			}
			if (storeId == null) {
				errorInRow = "storeId can not be empty.Row " + currRow.getRowNum();
				errors.add(errorInRow);
				return errors;
			}
			
			currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			storeName = getValueBasedOnType(currRow.getCell(0));
			
			currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			businessVerticalName = getValueBasedOnType(currRow.getCell(1));
			
			currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			storeCode = getValueBasedOnType(currRow.getCell(2));
			
			currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			costCentre = getValueBasedOnType(currRow.getCell(3));
			
			currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			ownerType = getValueBasedOnType(currRow.getCell(4));
			
			currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			emailId = getValueBasedOnType(currRow.getCell(5));
			
			currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			phoneNo1 = getValueBasedOnType(currRow.getCell(6));
			
			currRow.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			state = getValueBasedOnType(currRow.getCell(7));
			
			currRow.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			city = getValueBasedOnType(currRow.getCell(8));
			
			currRow.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			region = getValueBasedOnType(currRow.getCell(9));
			
			currRow.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			storeLocality = getValueBasedOnType(currRow.getCell(10));
			
			currRow.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			reportingTo = getValueBasedOnType(currRow.getCell(11));
			
			currRow.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			storeType = getValueBasedOnType(currRow.getCell(12));
			
			currRow.getCell(13, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			status = getValueBasedOnType(currRow.getCell(13));
    
      
			
							
                  role = roleService.findByRoleNameIgnoreCase("Store");
                  if(role == null) {
                	  
                	  errors.add("role name Store not available in Role Master");
                  	break;

                  }
                  
                 if( role != null){
                	 
                	 WebMaster 	 webMasterStore = webMasterRepo.findByWebMasterName(businessVerticalName);
                 	
                	 if(webMasterStore != null) {
                  webRole = webRoleRepo.findByRoleRoleIdAndWebMasterWebMasterId(role.getRoleId(),webMasterStore.getWebMasterId());
                 	}
                if(webRole == null) {
                	  
                	errors.add("Web Role Id for Store not available in WebRole Master");
                  	break;

                  }
              
                 }
                  
                   
			
			if(costCentre.equals("")|| costCentre.equals(null)) {
				
				errors.add("costCentre cannot be empty and CostCenter is Unique  ");
            	break;

			}
			
			
		           Store	costStore = storeRepository.findByCostcentre(costCentre);
		           
		           if(costStore != null) {
		        	   errors.add("CostCenter is already present  ");
                   	    break;

		           }
                            
                           
                            	
                            if(!ownerType.equals("") ) {
                            	  ownerTypeObj = ownerTypeService.findByOwnerTypeName(ownerType);
                           
                          
                            
                            if(ownerTypeObj == null) {
                            	errors.add("OwnerType " +ownerType + "is not available in the master dB  ");
                            	break;

                            }
                            }  
                            
                            if(!storeType.equals("")) {
                            storeServiceType = storeServiceTypeService.findByStoreServiceTypeName(storeType);
                          }
                          
                            
                            if( storeServiceType == null) {
                            	errors.add("Store Type "+ storeType+" is not available in the master dB  ");
                            	break;
                            }
                            
                           
                            
                            if(!businessVerticalName.equals("")) {
                            	
                            	webMaster = webMasterRepo.findByWebMasterName(businessVerticalName);
                            }
                            	 
                            if( webMaster == null) {
                            	errors.add("BusinessVerticalName " +businessVerticalName+" is not available in the master dB  ");
                            	break;
                              }
                            
                            
                            if(!reportingTo.equals("")) {
                            	
                            	 user = userRepo.findByUsername(reportingTo);
                            }
                           
                            
                            if(user == null) {
                            	
                            	errors.add("Reporting to User "+ reportingTo+"  is not available in the User dB  ");
                            	break;
                            	
                            }
                           
                            
                            
                            if(!state.equals("") ) {
                            	 stateObj = stateService.findByStateName(state);
                            }
                           
                            
                            if(stateObj == null) {
                            	errors.add("State name "+state +"  is not available in the State dB  ");
                            	break;
                            }
                             
                          
                            
                            if(!region.equals("") ) {
                            	
                            	regionMasterObj = regionRepository.findByRegionNameIgnoreCase(region);
                            }
                            
                            if(regionMasterObj == null) {
                            	
                            	errors.add("Region name "+region +" is not available in the Region dB  ");
                            	break;

                            }
                           
                            if(regionMasterObj != null) {
                           
                            if(!region.equals("") ) {
                             regionObj = regionDetailsRepository.findByRegionRegionNameAndStateStateId(region, stateObj.getStateId());
                            }
                          
                            if(regionObj == null) {
                            	errors.add("Region name for the given State "+state+"  is not available in the "+ region +"Region Details dB  ");
                            	break;

                            }
                           }
                            
                          
                            if(!city.equals("") ) {
                            	  cityObj = cityService.findByCityName(city);
                            }
                            if(cityObj == null) {
                            	errors.add("City name " + city+ " is not available in the City dB  ");
                            	break;

                            }
                           
                            store = storeService.findByStoreCode(storeCode);
            
                            if(store == null) {
                                            
                            				store = new Store();
                                            if(storeCode != null && webRole != null && user != null)
                                            { 
                                                   store.setStoreLocality(storeLocality);
                                                   store.setStoreCode(storeCode);
                                                   
                                                   store.setCostcentre(costCentre);
                                                 
                                                  
                                                   if(cityObj != null) {
                                                	   store.setCity(cityObj);
                                                   }else {
                                                	   store.setCity(null);
                                                   }
                                                   if(stateObj != null) {
                                                	   store.setState(stateObj);
                                                   }else {
                                                	   
                                                	   store.setState(null);
                                                   }
                                                   
                                                   if(regionObj != null) {
                                                	   store.setRegion(regionMasterObj);
                                                   }else {
                                                	   store.setRegion(null);
                                                   }
                                                   
                                                   if(ownerTypeObj != null) {
                                                	   store.setOwnerType(ownerTypeObj);
                                                   }else {
                                                	   store.setOwnerType(null);
                                                   }
                                                   
                                                   if(webMaster != null) {
                                                   store.setWebMaster(webMaster);
                                                   }else {
                                                	   store.setWebMaster(null); 
                                                   }
                                                   
                                                   if(storeServiceType != null) {
                                                	   store.setStoreServiceType(storeServiceType);
                                                   }else {
                                                	   
                                                	   store.setStoreServiceType(null);
                                                   }
                                                   
                                                   if(status.equals("")|| status.equals(null)) {
                                                	   store.setStoreStatus(null); 
                                                	   
                                                   }
                                                  
                                                   if(status.equals("ACTIVE")|| status.equals("Active")) {
                                                 	  store.setStoreStatus(StoreStatus.ACTIVE);
                                                   }else {
                                                 	  store.setStoreStatus(StoreStatus.INACTIVE);
                                                   }
                                                  
                                                   store.setCreatedOn(new Date());
                                                   
                                                   store.setReportingTo(reportingTo);
                                                   store.setReportingToId(user.getId());
                                                 
                                            	   store = storeService.saveStore(store,user.getId());
                                            	   
                                            	   
                                            }
                            
                              
                                            
                            }
                            else
                            {
                                  
                            	 store.setStoreLocality(storeLocality);
                                 store.setStoreCode(storeCode);
                                
                                 store.setCostcentre(costCentre);
                                 if(cityObj != null) {
                              	   store.setCity(cityObj);
                                 }else {
                              	   store.setCity(null);
                                 }
                                 if(stateObj != null) {
                              	   store.setState(stateObj);
                                 }else {
                              	   
                              	   store.setState(null);
                                 }
                                 
                                 if(regionObj != null) {
                              	   store.setRegion(regionMasterObj);
                                 }else {
                              	   store.setRegion(null);
                                 }
                                 
                                 if(ownerTypeObj != null) {
                              	   store.setOwnerType(ownerTypeObj);
                                 }else {
                              	   store.setOwnerType(null);
                                 }
                                 
                                 if(webMaster != null) {
                                 store.setWebMaster(webMaster);
                                 }else {
                              	   store.setWebMaster(null); 
                                 }
                                 
                                 if(storeServiceType != null) {
                              	   store.setStoreServiceType(storeServiceType);
                                 }else {
                              	   
                              	   store.setStoreServiceType(null);
                                 }
                                 
                                 if(status.equals("")|| status.equals(null)) {
                              	   store.setStoreStatus(null); 
                              	   
                                 }
                                
                                 if(status.equals("ACTIVE")|| status.equals("Active")) {
                               	  store.setStoreStatus(StoreStatus.ACTIVE);
                                 }else 
                                 {
                               	  store.setStoreStatus(StoreStatus.INACTIVE);
                                 }
                                 
                                 store.setCreatedOn(new Date());
                                 
                                 store.setReportingTo(reportingTo);
                                
                         	    store = storeService.updateStore(store);
                                    
                            }
            
            }
           
           rowCount++;
       }
return errors; 

	}
	
}
