package com.titan.irgs.WebConstantUrl;

public interface WebConstantUrl {

	
	/*class level requestAccess APIS
	*/
	String CONFIG_KEY_REFRESH_RATE_CONFIG  = "ConfigRefreshRate";

	String CONTENT_TYPE = "application/vnd.ms-excel";
	String EMPTY = "";
	
	String WebRole ="/webRole";
	String APPLICATIONACCESS = "/applicationAccess";
	String MODULE="/module";
	String USER="/user";
	String ROLE = "/role";
	String WebMaster = "/webMaster";
	String SUBFUTURE="/subfuture";
	String FUTURE="/future";
	String SUBMODULE="/submodule";
	String ACCESSPOLICY="/accesspolicy";
	String SERVICE_REQUEST="/serviceRequest";
	String SERVICE_REQUEST_TYPE="/serviceRequestType";
	String MISCELLANEOUS_TYPE="miscellaneosType";
	String VENDOR_FILE_BASE_URL="/vendorFile";
	String SERVICE_REQUEST_PO_UPLOAD="/ServiceRequestPoUpload";
	String QUOTATION_APPROVAL_MATRIX="/QuotationApprovalMatrix";
	String SREMAILESCALATION="/sremailescalation";
	String BATCHSRESCALATION="/batchsrescalation";
	
	//QuotaionForMaintainance
	String QUOTATION_FOR_MAINTAINANCE="/QuotationForMaintainance";

	
	
	
	
	// Escalation API's
		String Schedule="/schedule";
		String SaveSchedule="/saveschedule";
		String RemoveSchedule="/remove/{jobid}";
		String SaveSremailescalation="/saveSremailescalation";
		String GetAllSremailescalation = "/getAllSremailescalation";
		String BatchSrescalation = "/batchSrescalation";
		String NOTIFY_SRESCALATION = "/notifySrescalation";
		String UpdateSremailescalation="/updateSremailescalation";
		String GetSremailescalationById="/getSremailescalationById/{id}";
		String GetByVerticalName="/getByVerticalName/{verticalName}";
		String GetByEscalationLevel="getByEscalationLevel/{escalationLevel}";
		String GetByDays="getByDays/{days}";

		String CONFIGURATION_BASE_URL = "/configuration";
		String CREATE_CONFIGURATION = "/createConfiguration";
		String GET_CONFIGURATION_BY_ID ="/getConfigurationById/{id}";
		String GET_ALL_CONFIGURATION = "/getAllConfiguration";
		String EDIT_CONFIGURATION = "/editConguration";
	
	
	/*method level requestAccess APIS
	*/
	
	String save = "/save";
	String getAll = "/getAll";
	String getById = "/getById/{id}";
	String getClosedServiceRequests="/getClosedServiceRequests";
	String getAllUsersByWebRoleId ="/getAllUsersOnWebRoleId/{id}";
	String getAllUsersByWebRoleIdAndDepartmentId="/getAllUsersByWebRoleIdAndDepartmentId";
	String getAllUsersByLoginUserName="/getAllUsersByLoginUserName";
	String getAllUsersByDepartmentId="/getAllUsersByDepartmentId";
	String UPDATE = "/update";
	String SIGNIN = "/login";
	String getAlRolesUsingBussinessverticalId = "/getAlRolesUsingBussinessverticalId/{id}";
	String GET_ROLE_BY_VERTICALID_AND_DEPARTMENT_ID="/getAlRolesUsingBussinessverticalIdAndDepartmentId";
	String SR_FILE_UPLOAD_BASE_URL="/srFileUploadBaseUrl";
	String CHANGE_PASSWORD = "/changePassword";
	String DELETE_BY_ID="/deleteById";
	String SR_FILE_UPLOAD_MAINTAINANCE="/srFileUploadMaintainance";

	
	
	
	
	
	

/*
 * krishna's apis
 * 
 */
	
	
	String COUNTRY_BASE_URL = "/countries";
	String STATE_BASE_URL = "/states";
	String CITY_BASE_URL = "/cities";
	String REGION_BASE_URL = "/regions";
	String CLUSTER_BASE_URL = "/clusters";
	String CLUSTER_USER_BASE_URL = "/clusterUsers";
	
	String VENDOR_TYPE_BASE_URL = "/vendorTypes";
	
	String ENGINEER_BASE_URL = "/engineers";
	
	String DEPARTMENT_BASE_URL="/department";
	
	String CURRENCY_BASE_URL= "/currency";
	
	String ASSET_BASE_URL = "/assets";
	String ASSET_SPECIFICATION_BASE_URL="/assetSpecification";
    String MODEL_BASE_URL = "/models";
	String BRAND_BASE_URL = "/brands";
	String BREAK_DOWN_TYPE = "/breakdowns";
	
	String EQUIPMENT_BASE_URL = "/equipments";
	
	String EQUIPMENT_TYPE_BASE_URL ="/equipmentType";
	
	String VENDOR_BASE_URL = "/vendors";
	
	String STORE_TYPE_SERVICE_BASE_URL = "/storeServiceType";
	
	String STORE_BASE_URL="/store";
	
	String STORE_BUSINESS_TYPE_SERVICE_BASE_URL = "/storeBusinessServiceType";
	
	String OWNER_TYPE_BASE_URL = "/ownerType";
	
	String INVENTORY_BASE_URL ="/inventory";
	
	String INVENTORY_DETAIL_BASE_URL ="/inventoryDetails";
	
	String STORE_ALLOTED_BASE_URL="/storeAlloted";

	// Country Based Url's
	String GET_ALL_COUNTRY = "/getAllCountry";
	String GET_COUNTRY_BY_ID = "/getCountryById/{countryId}";
	String SAVE_COUNTRY = "/saveCountry";
	String UPDATE_COUNTRY = "/updateCountry";
	String DELETE_COUNTRY_BY_ID = "/deleteCountryById/{countryId}";
	
	// State Based Url's
		String GET_ALL_STATE = "/getAllState";
		String GET_STATE_BY_ID = "/getStateById/{stateId}";
		String SAVE_STATE = "/saveState";
		String UPDATE_STATE = "/updateState";
		String DELETE_STATE_BY_ID = "/deleteStateById/{stateId}";
		String GET_ALL_STATE_ON_COUNTRY_ID = "/getAllStateOnCountryId/{countryId}";
		
		// City Based Url's
		String GET_ALL_CITY = "/getAllCity";
		String GET_CITY_BY_ID = "/getCityById/{cityId}";
		String SAVE_CITY = "/saveCity";
		String UPDATE_CITY = "/updateCity";
		String DELETE_CITY_BY_ID = "/deleteCityById/{cityId}";
		String GET_ALL_CITY_ON_REGION_ID = "/getAllCityOnRegionId/{regionId}";
		
		// Region Based Url's
		String GET_ALL_REGION = "/getAllRegion";
		String GET_REGION_BY_ID = "/getRegionById/{regionId}";
		String SAVE_REGION = "/saveRegion";
		String UPDATE_REGION = "/updateRegion";
		String DELETE_REGION_BY_ID = "/deleteRegionById/{regionId}";
		String GET_ALL_REGION_ON_STATE_ID = "/getAllRegionOnStateId/{stateId}";
		String GET_ALL_REGION_IN_CLUSTER_BY_USING_WEBROLE_ID = "/getAllRegionsInClusterUsingWebRoleId/{id}";
		String GET_ALL_REGION_FOR_USER_CREATION_USING_WEB_ROLE_ID = "/getAllRegionForUserCreationUsingWebRoleId/{id}";
		String GET_ALL_REGION_IN_CLUSTER_BY_USING_USER_ID = "/getAllReagionsInClusterUsingUserId/{id}";



		
		// Cluster Based Url's
		String GET_ALL_CLUSTER = "/getAllCluster";
		String GET_CLUSTER_BY_ID = "/getClusterById/{clusterId}";
		String SAVE_CLUSTER = "/saveCluster";
		String UPDATE_CLUSTER = "/updateCluster";
		String DELETE_CLUSTER_BY_ID = "/deleteClusterById/{clusterId}";
		
		// Cluster Based Url's
		String GET_ALL_CLUSTER_USER = "/getAllClusterUsers";
		String GET_CLUSTER_USER_BY_ID = "/getClusterUserById/{clusterUserId}";
		String SAVE_CLUSTER_USER = "/saveClusteUserr";
		String UPDATE_CLUSTER_USER= "/updateClusterUser";
		String DELETE_CLUSTER_USER_BY_ID = "/deleteClusterUserById/{clusterUserId}";
		
		
		// VendorType Based Url's
		String GET_ALL_VENDOR_TYPE = "/getAllVendorType";
		String GET_VENDOR_TYPE_BY_ID = "/getVendorTypeById/{vendorTypeId}";
		String SAVE_VENDOR_TYPE = "/saveVendorType";
		String UPDATE_VENDOR_TYPE = "/updateVendorType";
		String DELETE_VENDOR_TYPE_BY_ID = "/deleteVendorTypeById/{vendorTypeId}";
		
		// Asset Rest End Points Url's
		String GET_ALL_ASSET = "/getAllAsset";
		String GET_ALL_ASSET_EXCEPT_ALREADYCREATED = "/getAllAssetExceptAlreadyCreated";
		String GET_ASSET_BY_ID = "/getAssetById/{assetId}";
		String SAVE_ASSET = "/saveAsset";
		String UPDATE_ASSET = "/updateAsset";
		String DELETE_ASSET_BY_ID = "/deleteAssetById/{assetId}";
		String GET_ALL_ASSET_ON_MODEL_ID = "/getAllAssetOnmodelId/{modelId}";
		String GET_ASSETS_BY_VERTICALID_AND_DEPARTMENT_ID="/getAssetsByVerticalIdAndDepartmentId";
		String GET_ASSET_COUNT_FOR_DASHBOARD="/getAssetCountForDashboard"; 
		
		
		// Brand Rest End Points Url's
		String GET_ALL_BRAND = "/getAllBrand";
		String GET_BRAND_BY_ID = "/getBrandById/{brandId}";
		String SAVE_BRAND = "/saveBrand";
		String UPDATE_BRAND = "/updateBrand";
		String DELETE_BRAND_BY_ID = "/deleteBrandById/{brandId}";
		
		// BreakDowns Rest End Points Url's
		String GET_ALL_BREAK_DOWN_TYPE = "/getAllBreakDownType";
		String GET_BREAK_DOWN_TYPE_BY_ID = "/getBreakDownTypeById/{breakDownId}";
		String SAVE_BREAK_DOWN_TYPE = "/saveBreakDownType";
		String UPDATE_BREAK_DOWN_TYPE = "/updateBreakDownType";
		String DELETE_BREAK_DOWN_TYPE_BY_ID = "/deleteBreakDownTypeById/{breakDownId}";
		String GET_BREAK_DOWN_TYPE_BY_WEBMASTER_ID = "/getBreakDownTypeByWebMasterId/{id}";
		
		
		// Model Rest End Points Url's
		String GET_ALL_MODEL = "/getAllModel";
		String GET_MODEL_BY_ID = "/getModelById/{modelId}";
		String GET_BRAND_BY_WEBMASTER_ID = "/getBrandByWebMasterId/{id}";
		String SAVE_MODEL = "/saveModel";
		String UPDATE_MODEL = "/updateModel";
		String DELETE_MODEL_BY_ID = "/deleteModelById/{modelId}";
		String GET_ALL_MODEL_ON_BRAND_ID = "/getAllModelOnbrandId/{brandId}";
		
		// Engineer Rest End Points Url's
		String GET_ALL_ENGINEER = "/getAllEngineer";
		String GET_ENGINEER_BY_ID = "/getEngineerById/{engineerId}";
		String SAVE_ENGINEER = "/saveEngineer";
		String UPDATE_ENGINEER = "/updateEngineer";
		String DELETE_ENGINEER_BY_ID = "/deleteEngineerById/{engineerId}";
		//String GET_ENGINEER_BY_VENDOR_ID = "/getEngineerByVendorId/{id}";
		String GET_ALL_ENGINEERS_BY_USING_VENDOR_ID = "/getAllEngineersByVendorId/{id}";

		// Department Rest End Points Url's
		String GET_ALL_DEPARTMENT = "/getAllDepartment";
		String GET_DEPARTMENT_BY_ID = "/getDepartmentById";
		String GET_DEPARTMENT_BY_WEBMASTER_ID = "/getDepartmentByWebMasterId";
		String SAVE_DEPARTMENT = "/saveDepartment";
		String UPDATE_DEPARTMENT = "/updateDepartment";
		String DELETE_DEPARTMENT_BY_ID = "/deleteDepartmentById";

				
		// Equipment Based Url's
		String GET_ALL_EQUIPMENT = "/getAllEquipment";
		String GET_EQUIPMENT_BY_ID = "/getEquipmentById/{equipmentId}";
		//String GET_ALL_EQUIPMENT_INVENTORY_LOGS = "/getAllEquipmentLogs";
		//String GET_EQUIPMENT_INVENTORY_LOGS_BY_EQUIPMENT_ID = "/getEquipmentLogsByEquipmentIdId/{id}";
		//String GET_EQUIPMENT_INVENTORY_LOGS_BY_EQUIPMENT_INVENTORY_DETAIL_ID = "/getEquipmentLogsByEquipmentInventoryDetailId/{id}";
		String SAVE_EQUIPMENT = "/saveEquipment";
		String UPDATE_EQUIPMENT = "/updateEquipment";
		String DELETE_EQUIPMENT_BY_ID = "/deleteEquipmentById/{equipmentId}";
		
		// Vendor Based Url's
		String GET_ALL_VENDOR = "/getAllVendor";
		String GET_VENDOR_BY_ID = "/getVendorById/{vendorId}";
		String GET_VENDOR_BY_VenderCode = "/getVendorByVenderCode/{venderCode}";
		String SAVE_VENDOR = "/saveVendor";
		String UPDATE_VENDOR = "/updateVendor";
		String DELETE_VENDOR_BY_ID = "/deleteVendorById/{vendorId}";
		
		// Equipment Type Based Url's
				String GET_ALL_EQUIPMENT_TYPE = "/getAllEquipmentType";
				String GET_EQUIPMENT_TYPE_BY_ID = "/getEquipmentByTypeId/{id}";
				String SAVE_EQUIPMENT_TYPE = "/saveEquipmentType";
				String UPDATE_EQUIPMENT_TYPE = "/updateEquipmentType";
				String DELETE_EQUIPMENT_TYPE_BY_ID = "/deleteEquipmentByTypeId/{id}";
				String GET_EQUIPMENT_TYPE_BY_VERTICALID = "/getEquipmentByTypeVerticalId/{id}";
				String GET_VENDOR_BY_VERTICALID = "/getVendorByTypeVerticalId/{id}";
				String GET_ASSET_SPEC_BY_VERTICALID = "/getAssetSpecByVerticalId/{id}";
				String GET_EQUIPMENT_BY_VERTICALID = "/getEquipmentByVerticalId/{id}";
				String GET_EQUIPMENT_BY_VERTICALID_AND_DEPARTMENT_ID="/getEquipmentByVerticalIdAndDepartmentId";
				String GET_MODEL_BY_VERTICALID = "/getModelByVerticalId/{id}";
				
				//StoreTypeService Url's
			String GET_ALL_STORE_TYPE_SERVICE ="/getAllStoreTypeService";
		    String GET_STORE_TYPE_SERVICE_BY_ID ="/getStoreTypeServiceById/{id}";
		    String SAVE_STORE_TYPE_SERVICE="/saveStoreTypeService";
		    String UPDATE_STORE_TYPE_SERVICE ="/updateStoreTypeService";
		    String DELETE_STORE_TYPE_SERVICE_BY_ID ="/deleteStoreTypeServieById/{id}";
		    
		  //StoreBusinessTypeService Url's
			String GET_ALL_STORE_BUSINESS_TYPE_SERVICE ="/getAllStoreBusinessTypeService";
		    String GET_STORE_BUSINESS_TYPE_SERVICE_BY_ID ="/getStoreBusinessTypeServiceById/{id}";
		    String SAVE_STORE_BUSINESS_TYPE_SERVICE="/saveStoreBusinessTypeService";
		    String UPDATE_STORE_BUSINESS_TYPE_SERVICE ="/updateStoreBusinessTypeService";
		    String DELETE_STORE_BUSINESS_TYPE_SERVICE_BY_ID ="/deleteStoreBusinessTypeServiceById/{id}";
		    
		 // OwnerType Based Url's
			String GET_ALL_OWNER_TYPE = "/getAllOwnerType";
			String GET_OWNER_TYPE_BY_ID = "/getOwnerTypeById/{id}";
			String SAVE_OWNER_TYPE = "/saveOwnerType";
			String UPDATE_OWNER_TYPE = "/updateOwnerType";
			String DELETE_OWNER_TYPE_BY_ID = "/deleteOwnerTypeById/{id}";
			
			
			//  Store Based Url's
			String GET_ALL_STORE = "/getAllStore";
			String GET_STORE_BY_ID = "/getStoreById/{id}";
		/*	String GET_EP_STORE_LIST_WITH_NO_OPTO = "/getEpStoreListWithNoOpto";
			String GET_EP_STORE__LIST_BY_REGIONAL_OPTOMETRIST_ID ="/getEpStoreListOnRegionalOptometrist/{id}";
			String GET_EP_STORE_LIST_BY_AREA_MANAGER_ID ="/getEpStoreListOnAreaManager/{id}";
			String GET_EP_STORE_LOGS_ON_EP_STORE_ID ="/getEpStoreLogById/{epStoreId}";
			String GET_EP_STORE_LIST_BY_FILTERS = "/getAllFilteredEpStoreList";
			String GET_EP_STORE_LIST_BY_DATE = "/getAllFilteredEpStoreListWithDate";*/
			String SAVE_STORE = "/saveStore";
			//String SHIFT_EP_STORE ="/shiftEpStore";
			String UPDATE_STORE = "/updateStore";
			//String UPDATE_EP_STORE_LOG = "/updateEpStoreLog";
			String DELETE_STORE_BY_ID = "/deleteStoreById/{id}";
			
			// Inventory Based Url's
			String GET_ALL_INVENTORY = "/getAllInventory";
			String GET_INVENTORY_BY_ID = "/getInventoryById/{id}";
			String SAVE_INVENTORY = "/saveInventory";
			String UPDATE_INVENTORY = "/updateInventory";
			String DELETE_INVENTORY_BY_ID = "/deleteInventoryById/{id}";
			String GET_ALL_VENDORS_BY_USING_INVENTORY_ID = "/getAllVendorsByUsingInventoryId/{id}";
			String GET_BY_AMC_STATUS="/getByAmcStatus/amcStatus";
			String CREATE_QRCODE_BY_INVENTORY_ID="/createQRCodeByInventoryID/{id}";
			// Inventory Detail Based Url's
			/*String GET_ALL_INVENTORY_DETAIL = "/getAllInventoryDetail";
			String GET_INVENTORY_DETAIL_BY_ID = "/getInventoryDetailById/{id}";
			String SAVE_INVENTORY_DETAIL = "/saveInventoryDetail";
			String UPDATE_INVENTORY_DETAIL = "/updateInventoryDetail";
			String DELETE_INVENTORY_DETAIL_BY_ID = "/deleteInventoryDetailById/{id}";*/
			
			//StoreTypeService Url's
			String GET_ALL_STORE_ALLOTED ="/getAllStoreAlloted";
		    String GET_STORE_ALLOTED_BY_ID ="/getStoreAllotedById/{id}";
		    String SAVE_STORE_ALLOTED ="/saveStoreTypeService";
		    String UPDATE_STORE_ALLOTED ="/updateStoreTypeService";
		    String DELETE_STORE_ALLOTED_BY_ID ="/deleteStoreTypeServieById/{id}";
			

		
		// country file upload Url's
		  String COUNTRY_FILE_BASE_URL ="/countryFile";
		  String IMPORT_COUNTRY_FILE_UPLOAD = "/importCountryFile";
		  
		// State file upload Url's
		  String STATE_FILE_BASE_URL ="/stateFile";
		  String IMPORT_STATE_FILE_UPLOAD = "/importStateFile";
		
		  //City file upload Url's
		  String CITY_FILE_BASE_URL ="/cityFile";
		  String IMPORT_CITY_FILE_UPLOAD = "/importCityFile";
		  
		  
		  //region file upload Url's
		  String REGION_FILE_BASE_URL ="/regionFile";
		  String IMPORT_REGION_FILE_UPLOAD = "/importRegionFile";
		  
		  //asset file upload Url's
		  String ASSET_FILE_BASE_URL="/assetFile";
		  String ASSET_SPECIFICATION_FILE_BASE_URL="/assetSpecificationFile";
		  String IMPORT_ASSET_FILE_UPLOAD = "/importAssetFile";
		  String Asset_Po_file_Base_url="/assetPoFile";
		  String IMPORT_Asset_PO_FILE_UPLOAD = "/importAssetPoFile";

		  String Asset_Vendor_Invoice_ref_file_Base_url="/assetVendorInvoiceReferenceFile";
		  String IMPORT_Asset_VENDOR_INVOICE_FILE_UPLOAD = "/importAssetVendorInvoiceFile";
		  
		  //brand file upload Url's
		  String BRAND_FILE_BASE_URL ="/brandFile";
		  String IMPORT_BRAND_FILE_UPLOAD = "/importBrandFile";
		  
		  //model file upload url's
		  String MODEL_FILE_BASE_URL ="/modelFile";
		  String IMPORT_MODEL_FILE_UPLOAD = "/importModelFile";
		  
		  //equipment file upload url's
		  String EQUIPMENT_FILE_BASE_URL="/equipmentFile";
		  String IMPORT_EQUIPMENT_FILE_UPLOAD = "/importEquipmentFile";
		  
		  //equipment file upload url's
		  String ENGINEER_FILE_BASE_URL="/engineerFile";
		  String IMPORT_ENGINEER_FILE_UPLOAD = "/importEngineerFile";
		  
		  //inventory file upload Url's
		  String INVENTORY_FILE_BASE_URL="/inventoryFile";
		  String IMPORT_INVENTORY_FILE_UPLOAD = "/importInventoryFile";
		  
		  //AmcInventory file upload Url's
		  String INVENTORY_AMC_FILE_BASE_URL="/inventoryAmcFile";
		  String INVENTORY_AMC_Extn_FILE_BASE_URL="/inventoryAmcExtnFile";
		  String IMPORT_INVENTORY_AMC_FILE_UPLOAD = "/importInventoryAmcFile";
		  String IMPORT_INVENTORY_AMC_Extn_FILE_UPLOAD = "/importInventoryAmcExtnFile";

		  
		// Store file upload Url's
		  String STORE_FILE_BASE_URL ="/storeFile";
		  String USER_FILE_BASE_URL="/userFile";
		  String IMPORT_USER_FILE_UPLOAD="/importUserFile";
		  String IMPORT_STORE_FILE_UPLOAD = "/importStoreFile";
		  
		 
	
		  // download sample
	    String DOWNLOAD_SAMPLE_COUNTRY_EXCELSHEET = "/downloadCountrysampleExcelsheet";
        String DOWNLOAD_SAMPLE_STATE_EXCELSHEET = "/downloadStatesampleExcelsheet";
        String DOWNLOAD_SAMPLE_CITY_EXCELSHEET = "/downloadCitysampleExcelsheet";
        String DOWNLOAD_SAMPLE_REGION_EXCELSHEET = "/downloadRegionsampleExcelsheet";
    	String DOWNLOAD_SAMPLE_MODEL_EXCELSHEET = "/downloadModelsampleExcelsheet";
    	String DOWNLOAD_SAMPLE_ASSET_EXCELSHEET = "/downloadAssetsampleExcelsheet";
     	String DOWNLOAD_SAMPLE_BRAND_EXCELSHEET = "/downloadBrandsampleExcelsheet";
     	String DOWNLOAD_SAMPLE_EQUIPMENT_EXCELSHEET = "/downloadEquipmentsampleExcelsheet";
     	String DOWNLOAD_SAMPLE_VENDOR_EXCELSHEET = "/downloadVendorsampleExcelsheet";
		String quatationUpload = "/quatationUpload";
		String GET_ALL_REGION_BASED_ON_CLUSTER = "/getAllRegionBasedOnCluster/{id}";
		String GET_ALL_STORE_NOT_PRESENT_IN_USER_MASTER = "/getAllStoreNotPresentInUserByWebRole/{id}";
		
		String BACKENDAPIS="/backendApis";
		String DELETEPERMISSIONBYPERMISSIONID = "/deletePermissionByPermissionId/{id}";
		String GETACCESSPOLICYBYUSINGROLEID = "/getAccesspolicyByUsingRoleId/{id}";
		String DELETE_ACCESSPOLICY_BY_ID = "/deleteAccesspolicyById/{id}";
		String GETBYMODULEIDANDWEBROLEID = "/getByModuleIdAndWebRoleId";
		String GET_INVENTORY_BY_BUSSINESS_ID = "/getInventoryByBussinessId/{id}";
		String GET_STORE_BY_BUSSINESS_ID = "/getStoreByBussinessId/{id}";
		String IMPORT_VENDOR_FILE_UPLOAD = "/importVendorFileUpload";
		String FORGOT_PASSWORD = "/forgotPassword";

		String UPDATE_COMMENTS_FOR_SERVICE_REQUEST = "/updateCommentsForServiceRequest";
		String UPDATE_SR_COMMENTS_FILE_UPLOAD="/updateCommentsFileUpload";
		String GET_SR_COMMENTS_SERVICE_REQUEST_ID = "/getSRCommentsForServiceRequestId/{serviceRequestId}";
		String GET_SERVICE_REQUEST_COMMENTS_UPLOAD="/getSRCommentsUploadFiles/{fileName:.+}";

		String GET_ALL_SERVICE_REQUEST_ON_START_DATE_AND_END_DATE_AND_REQUEST_TYPE="/getAllServiceRequestOnStartDateAndEndDateAndRequestType";
	
		String GET_ALL_AMC_SERVICE_REQUEST_ON_START_DATE_AND_END_DATE_AND_REQUEST_TYPE="/getAllAMCServiceRequestOnStartDateAndEndDateAndRequestType";

		String GET_ALL_AMC_SERVICE_REQUEST_AUTO_CREATED_Export="/getAllAMCServiceRequestAutoCreatedExport";

 	/*String DOWNLOAD_SAMPLE_ASSET_EXCELSHEET = "/downloadAssetsampleExcelsheet";
 	String DOWNLOAD_SAMPLE_BRAND_EXCELSHEET = "/downloadBrandsampleExcelsheet";
 	String DOWNLOAD_SAMPLE_MODEL_EXCELSHEET = "/downloadModelsampleExcelsheet";
    String DOWNLOAD_SAMPLE_EPSTORE_EXCELSHEET = "/downloadEpstoresampleExcelsheet";
 	
 	
 	String DOWNLOAD_SAMPLE_EQUIPMENTINVENTORYDETAIL_EXCELSHEET="/downloadEquipmentInventoryDetailsampleExcelsheet";*/
//SR NOTIFICATIONEMAIL
		String SRNOTIFICATIONEMAIL="/srnotificationemail";
		String GetAllSRNOTIFICATIONEMAIL="/getAllSrNotificationEmails";
		String GetBySrNotificationId="/getSrNotificationEmailId/{id}";
		String SaveNotification="/saveSrNotificationEmail";
		String UpdateNotification="updateSrNotificationEmail";
		String DeleteNotification="deleteById/{id}";
		
		// AMC Inventory 
		String AMCInventory="/amcinventory";
		String AutoAMCService="/AutoAmcServicecreate";
		String GetAllAMCInventories="/getAllAmcInventories";
		String SaveAmcInventory="/saveAmcInventory";
		String GetByAmcId="/getByAmcid/{id}";
		String UpdateAmc="/updateAmc";
		String DeleteById="/deleteByAmcId/{amcId}";
		String SaveAmcExtendValidation="/saveAmcExtendValidation";
		String SaveExtendAmc="/saveExtendAmc";

		
		// Maintainance 

		String Maintainance="/maintainance";
		String SaveMaintainance="/saveMaintainance";
		  String UpdateMaintainance="/updateMaintainance"; 

		
	
	  String GetAllMaintainance="/getAllMaintainance";
	  
	  String GetAllMaintainanceAutoLog="/getAllMaintainanceAutoLog";
	  String GetAllMaintainanceAutoLogWithPagination="/getAllMaintainanceAutoLogWithPagination";
		 
	  String GetByMaintainanceId="/getByMaintainanceid/{maintainanceId}"; 
	  String UpdateMaintainanceId="/updateByMaintainanceId/{maintainanceId}"; 
	  String DeleteByMaintainanceId="/deleteByMaintainanceId/{maintainanceId}"; 
	 
	  String GET_AMC_BY_Asset_ID="/getAmcByAssetId/{id}";
	  String GET_Warrany_BY_Year="/getWarrantyByYear";

	 		
		//Amc Extension
		String SaveAmcExtension="/saveAmcExtension";
		String GetAllAMCExtension="/getAllAmcExtension";
		String AMCWarranty="/amcWarranty";
		String GetWarrantyByAmcId="/getWarrantyByAmcid/{id}";
		//String GetWarrantyByInventoryId="/getWarrantyByInventoryid/{id}";
		String GetWarrantyByAssetId="/getWarrantyByAssetid/{id}";


		//SR NOTIFICATIONEMAIL
				String AMCNOTIFICATION="/amcnotification";
				String GetAllAMCNOTIFICATION="/getAllAmcNotifications";
				String GetByAmcNotificationId="/getAmcNotificationId/{id}";
				String SaveAmcNotification="/saveAmcNotification";
				String UpdateAmcNotification="/updateAmcNotification";

				// Asset specification End Points Url's
				String GET_ALL_ASSET_SPEC = "/getAll";
				String GET_ASSET_SPEC_BY_ID = "/getById/{id}";
				String SAVE_ASSET_SPEC = "/save";
				String UPDATE_ASSET_SPEC = "/update";
				String DELETE_ASSET_SPEC_BY_ID = "/deleteById/{assetId}";
				
				//for dashboard 
				String GET_SERVICE_REQUEST_COUNT_FOR_DASHBOARD="/getServiceRequestCountForDashboard";
				String GET_AMC_SERVICE_REQUEST_COUNT_FOR_DASHBOARD="/getAMCServiceRequestCountForDashboard";
				String GET_ALL_SERVICE_REQUEST_COUNT="/getTotalServiceRequestCount";
				String GET_ALL_SERVICE_REQUEST_COUNT_FOR_VENDOR="/getAllSrCountForAllVendors";
				String GET_ALL_SERVICE_REQUEST_COUNT_FOR_REGION="/getAllSrCountForAllRegion";
				String GET_ALL_SERVICE_REQUEST_COUNT_FOR_EQUIPMENT="/getAllSrCountForAllEquipment";
				String GET_ALL_AMC_SERVICE_REQUEST_COUNT_FOR_VENDOR="/getAllAmcSrCountForAllVendors";
				String GET_ALL_AMC_SERVICE_REQUEST_COUNT_FOR_REGION="/getAllAmcSrCountForAllRegion";
				String GET_ALL_AMC_SERVICE_REQUEST_COUNT_FOR_EQUIPMENT="/getAllAmcSrCountForAllEquipment";
				
		//For Store Engineer
				String STORE_ENGINEER_BASE_URL="/storeEngineer";
				String SAVE_STORE_ENGINEER="/saveStoreEngineer";
				String UPDATE_STORE_ENGINEER="/updateStoreEngineer";
				String DELETE_STORE_ENGINEER_BY_ID="/deleteStoreEngineerById/{id}";
				String GET_ALL_STORE_ENGINEER="/getAllStoreEngineer";
				String GET_STORE_ENGINEER_BY_ID = "/getStoreEngineerById/{id}";
				
				//For Group Business Vertical
				String GROUP_BUSINESS_VERTICAL_BASE_URL = "/groupBusinessVertical";
				String SAVE_GROUP_BUSINESS_VERTICAL="/saveGroupBusinessVertical";
				String UPDATE_GROUP_BUSINESS_VERTICAL="/updateGroupBusinessVertical";
				String DELETE_GROUP_BUSINESS_VERTICAL_BY_ID="/deleteGroupBusinessVerticalById/{id}";
				String GET_ALL_GROUP_BUSINESS_VERTICAL="/getAllGroupBusinessVertical";
				String GET_GROUP_BUSINESS_VERTICAL_BY_ID = "/getGroupBusinessVerticalById/{id}";
				
				// Currency Rest End Points Url's
				String GET_ALL_CURRENCY = "/getAllCurrency";
				String GET_CURRENCY_BY_ID = "/getCurrencyById";
				String SAVE_CURRENCY = "/saveCurrency";
				String UPDATE_CURRENCY = "/updateCurrency";
				String DELETE_CURRENCY_BY_ID = "/deleteCurrencyById";
}
