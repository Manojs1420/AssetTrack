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
import com.titan.irgs.master.domain.Currency;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.domain.VendorType;
import com.titan.irgs.master.enums.VendorStatus;
import com.titan.irgs.master.repository.CityRepository;
import com.titan.irgs.master.repository.CurrencyRepo;
import com.titan.irgs.master.repository.VendorTypeRepository;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.serviceImpl.RoleService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class VendorProcessor extends UploadedDocProcessor {

	


	public static Logger logger = LoggerFactory.getLogger(VendorProcessor.class);


	
	
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private VendorTypeRepository vendorTypeRepository;
    
	@Autowired
	private WebMasterRepo webMasterRepo;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IStateService iStateService;

	@Autowired
	CurrencyRepo currencyRepo; 

	public VendorProcessor() 
	{

	}

	public VendorProcessor(String templateName) 
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
			
			String vendorName = null;
			String vendorCode=null;
			String vendorTypeName = null;
			String businessVerticalName = null;
			String cityName = null;
			String pincode = null;
			String contactPersonName = null;
			String contactNumber= null;
			String billingAddress= null;
			String billingEmailId = null;
			String serviceAddress = null;
			String storeLocality = null;
			String serviceEmailId= null;
			String vendorStatus = null;
			String stateName = null;
			String vendorCurrency=null;
			
    
			int rowCount=1;
			wb = WorkbookFactory.create(inp);
			sheet = wb.getSheetAt(0);
    
    for (Row currRow : sheet) {
		if (currRow.getPhysicalNumberOfCells() == 0)
			return errors;

		if (currRow.getRowNum() != 0) {
			currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			vendorName = getValueBasedOnType(currRow.getCell(0));

			currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			vendorCode = getValueBasedOnType(currRow.getCell(1));
			
			currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			vendorTypeName = getValueBasedOnType(currRow.getCell(2));

			
			currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			businessVerticalName = getValueBasedOnType(currRow.getCell(3));
			
			currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			cityName = getValueBasedOnType(currRow.getCell(4));
			
			currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			pincode = getValueBasedOnType(currRow.getCell(5));
			
			currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			contactPersonName = getValueBasedOnType(currRow.getCell(6));
			
			
			currRow.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			contactNumber = getValueBasedOnType(currRow.getCell(7));
			
			currRow.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			billingAddress = getValueBasedOnType(currRow.getCell(8));
			
			currRow.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			billingEmailId = getValueBasedOnType(currRow.getCell(9));
			
			currRow.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			serviceAddress = getValueBasedOnType(currRow.getCell(10));
			
			currRow.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			serviceEmailId = getValueBasedOnType(currRow.getCell(11));
			
			currRow.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			vendorStatus = getValueBasedOnType(currRow.getCell(12));
			
			currRow.getCell(13, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			stateName = getValueBasedOnType(currRow.getCell(13));
			
			currRow.getCell(14, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			vendorCurrency = getValueBasedOnType(currRow.getCell(14));
			
			System.out.println(sheet.getPhysicalNumberOfRows());
			System.out.println(currRow.getRowNum());
			// check the last row is empty..
			if(sheet.getPhysicalNumberOfRows()!=currRow.getRowNum());
			{
				if(vendorName.equals("")&&vendorCode.equals("")&&vendorTypeName.equals("")&&businessVerticalName.equals("")
						&&cityName.equals("")&&pincode.equals("")&&contactPersonName.equals("")&&billingAddress.equals("")
						&&billingEmailId.equals("")&&serviceAddress.equals("")&&serviceEmailId.equals("")&&vendorCurrency.equals("")) {
					
					break;
				}
			}
			
			Vendor vendor=vendorService.findByVendorCode(vendorCode);
			
			City city=cityRepository.findByCityName(cityName);
			
			
			
			VendorType vendorType=vendorTypeRepository.findByVendorTypeName(vendorTypeName);
			
			WebMaster webMaster=webMasterRepo.findByWebMasterName(businessVerticalName);
			
			State state=iStateService.findByStateName(stateName);

                                            	   
			if(city==null) {
				errors.add("city not exist in master on row number "+rowCount);
				continue;
			}
			
			if(state==null) {
				errors.add("state not exist in master on row number "+rowCount);
				continue;
			}

			
			if(vendorType==null) {
				errors.add("vendorType not exist in master on row number "+vendorType);
				continue;
			}
			
			if(webMaster==null) {
				errors.add("webMaster not exist in master on row number "+webMaster);
				continue;
			}
			
			Currency currency=currencyRepo.findByCurrencyName(vendorCurrency);
		
			if(currency==null) {
				errors.add("vendor currency not exist in master on row number "+webMaster);
				continue;
			}
			
			if(vendor==null) {
				
				vendor=new Vendor();
				Role role  = roleService.findByRoleNameIgnoreCase("Vendor");

				vendor.setCreatedOn(new Date());
				vendor.setVendorCode(vendorCode);
				vendor.setVendorName(vendorName);
				vendor.setBillingAddress(billingAddress);
				vendor.setBillingEmailId(billingEmailId);
				vendor.setContactNumber(contactNumber);
				vendor.setContactPerson(contactPersonName);
				vendor.setServiceAddress1(serviceAddress);
				vendor.setServiceEmailId1(serviceEmailId);
				vendor.setCity(city);
				vendor.setVendorType(vendorType);
				if(vendorStatus.equals("A"))
					vendor.setVendorStatus(VendorStatus.valueOf("ACTIVE"));
				if(vendorStatus.equals("I"))
					vendor.setVendorStatus(VendorStatus.valueOf("INACTIVE"));
				vendor.setPinCode(pincode);
				vendor.setWebMaster(webMaster);
				vendor.setState(state);
				vendor.setCurrency(currency);
				vendorService.saveVendor(vendor, role.getRoleId());
			

				
			}
			else {
				
				vendor.setUpdatedOn(new Date());
				vendor.setVendorCode(vendorCode);
				vendor.setVendorName(vendorName);
				vendor.setBillingAddress(billingAddress);
				vendor.setBillingEmailId(billingEmailId);
				vendor.setContactNumber(contactNumber);
				vendor.setContactPerson(contactPersonName);
				vendor.setServiceAddress1(serviceAddress);
				vendor.setServiceEmailId1(serviceEmailId);
				vendor.setVendorType(vendorType);
				if(vendorStatus.equals("A")) {
					vendor.setVendorStatus(VendorStatus.valueOf("ACTIVE"));
				}
				if(vendorStatus.equals("I")) {
					vendor.setVendorStatus(VendorStatus.valueOf("INACTIVE"));
				}
				vendor.setPinCode(pincode);
				vendor.setCity(city);
				vendor.setState(state);

				vendor.setWebMaster(webMaster);
				vendor.setCurrency(currency);
				vendorService.updateVendor(vendor);
			
				
				
			}
			
		
		 }
                            
	     rowCount++;
                     
                                            
        }
                                                               
        
	return errors;
         
       

	}
	

	
}
