package com.titan.irgs.fileupload.processor;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.titan.irgs.enumUtils.InventoryStatus;
import com.titan.irgs.inventory.domain.Inventory;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.SerialNumber;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.SerialNumberRepository;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.master.serviceImpl.AssetService;
import com.titan.irgs.master.serviceImpl.ModelService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class InventoryProcessor extends UploadedDocProcessor {


	public static Logger logger = LoggerFactory.getLogger(InventoryProcessor.class);


	@Autowired
	private IBrandService brandService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private WebMasterService webMasterService;

	@Autowired
	private WebMasterRepo webMasterRepo;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private InventoryRepository inventoryRepository;


	@Autowired
	DepartmentRepo departmentRepo;


	@Autowired
	AssetRepository assetRepository;


	@Autowired
	UserRepo userRepo;
	
	@Autowired
	SerialNumberRepository serialNumberRepository;
	
	public InventoryProcessor() 
	{

	}

	public InventoryProcessor(String templateName) 
	{
		super(templateName);
	}


	@SuppressWarnings({ "static-access", "unlikely-arg-type" })
	public List<String> importInventoryRecords(InputStream inp) throws Exception 
	{
		List<String> errors = new ArrayList<>();
		Workbook wb;
		Sheet sheet;
		String errorInRow = null;
		String inventoryId = null;
		String businessVerticalName = null;
		String storeCode = null;
		String assetName = null;
		String allocationStartDate=null;
		String allottedPeriod=null;
		// String brandName = null;
		//String modelName = null;
		Inventory inventorys=null;
		String farN = null;
		String erNo = null;
		String quantity = null;
		String departmentName=null;
		String desc=null;
		Inventory inventory = null;
		// Brand brand = null;
		Asset asset = null;
	//	Store store = null;
		User user=null;
		// Model model = null;
		WebMaster webMaster = null;
		
		String serialNumber=null;
		
		int rowCount = 0;

		wb = WorkbookFactory.create(inp);
		sheet = wb.getSheetAt(0);

		for (Row currRow : sheet) {
			if (currRow.getPhysicalNumberOfCells()==0)
				return errors;
			errorInRow = null;
			//  rowCount++;

			if (currRow.getRowNum() != 0) {



				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				businessVerticalName = getValueBasedOnType(currRow.getCell(0));
				
				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				departmentName = getValueBasedOnType(currRow.getCell(1));

				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				storeCode = getValueBasedOnType(currRow.getCell(2));


				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				assetName = getValueBasedOnType(currRow.getCell(3));

				currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				erNo = getValueBasedOnType(currRow.getCell(4));

				currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				quantity = getValueBasedOnType(currRow.getCell(5));

				currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				allocationStartDate = getValueBasedOnType(currRow.getCell(6));

				currRow.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				allottedPeriod = getValueBasedOnType(currRow.getCell(7));
				
				currRow.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				serialNumber = getValueBasedOnType(currRow.getCell(8));

				currRow.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				desc = getValueBasedOnType(currRow.getCell(9));

				//     if (rowCount != 0) {

				if(erNo.equals("") || erNo.equals(null)) {

					errors.add(" Er No cannot be empty in the excel column "+rowCount );

				}


				webMaster = webMasterRepo.findByWebMasterName(businessVerticalName);
				if( webMaster == null)  {
					errors.add("webMaster Name is not available in the master dB  "+rowCount);
				}

				Department department=departmentRepo.findByWebMasterWebMasterIdAndDepartmentName(webMaster.getWebMasterId(),departmentName);

				if(department ==  null) {
					errors.add("The given departmentName "+ departmentName +"is not present in the department Master");
					return errors;
				}

				asset = assetService.findByAssetCode(assetName);
				if(asset == null) {

					errors.add("asset Code is not available in the master dB  "+rowCount);

				}else {
					List<Inventory> inventorys1=inventoryRepository.findInventoryByAssetCodes(asset.getAssetCode());
					if(!inventorys1.isEmpty()) {
						
						asset=assetRepository.findByAssetCode(inventorys1.get(0).getAsset().getAssetCode());
							
							if(inventorys1.size()==asset.getOriginalQty()) {
								errors.add("Inventories limit is Crossed for the Asset Code "+asset.getAssetCode()+" on line: "+rowCount);
							
							}
						
					}
				}

				user=userRepo.findByUsername(storeCode);

			//	store = storeService.findByStoreCode(storeCode);
				if(user == null) {

					errors.add("Username is not available in the master dB  "+rowCount);
				}else {

					user=userRepo.getUsersByUserNameAndVerticalName(user.getUsername(), businessVerticalName);
					if(user == null) {

						errors.add(storeCode+" is not available in the Vertical "+businessVerticalName+ " on line "+rowCount);
					}
				}

				inventorys=inventoryRepository.findByErNo(erNo);
				if(inventorys!=null) {
					//if(inventorys.getWebMaster().getWebMasterName()!=businessVerticalName && inventorys.getStore().getStoreCode()!=storeCode && inventorys.getAsset().getAssetCode()!=assetName && inventorys.getQuantity()!=Long.parseLong(quantity)) {
					errors.add("er number already exists on line "+rowCount);
					//}

				}
				
				/*	inventorys=inventoryRepository.findByAssetCode(assetName);
				if(inventorys!=null) {
					//if(inventorys.getWebMaster().getWebMasterName()!=businessVerticalName && inventorys.getStore().getStoreCode()!=storeCode && inventorys.getAsset().getAssetCode()!=assetName && inventorys.getQuantity()!=Long.parseLong(quantity)) {
					errors.add("Asset code already exists on line "+rowCount);
					//}

				}*/
				
				
				/*
				List<User> users = userRepo.getAllInventoryUsersByDepartmentId(department.getDepartmentId());
						
				String user1=storeCode;
				List<User> userExist =  users.stream().filter(p ->p.getUsername().equalsIgnoreCase(user1)).collect(Collectors.toList());
			
				if(userExist.isEmpty()) {
					errors.add("User "+storeCode+" is not linked in the department "+department.getDepartmentName()+" on line: "+rowCount);
					
				}*/
				
				
				List<Object[]> assets = assetRepository.getAssetsByVerticalIdAndDepartmentId(webMaster.getWebMasterId(),department.getDepartmentId());
				
				if(assets.isEmpty()) {
					errors.add("The given assetCode "+ assetName +" is not linked with the Vertical "+businessVerticalName+" and department "+departmentName);

				}else {
					for (Object[] asset1 : assets) {
						String assetcode=(String)asset1[0];
						if(assetcode.equalsIgnoreCase(assetName)) {
							asset=assetRepository.findByAssetCode(assetName);
						}
					}

				}
				
				SerialNumber serialnumber=null;	
				if(!serialNumber.equals("") || !serialNumber.equals(null)) {
					if(asset!=null) {
						 serialnumber=serialNumberRepository.findBySerialNumberAndAssetId(serialNumber,asset.getAssetId());
						if(serialnumber==null){
							errors.add(serialNumber+" Serial number is not there in DB at line "+rowCount);
						}
					}
				}
				
//				SerialNumber serialnumber=null;	
//				if(serialNumber != null && !serialNumber.isEmpty()) {
//				    if(asset != null) {
//				        serialnumber = serialNumberRepository.findBySerialNumberAndAssetId(serialNumber, asset.getAssetId());
//				        if(serialnumber != null) {
//				            errors.add(serialNumber + " is duplicated in DB check at line " + rowCount);
//				        } else {
//				            serialnumber = serialNumberRepository.findBySerialNumberAndAssetId(serialNumber, asset.getAssetId());
//				            if(serialnumber == null) {
//				                errors.add(serialNumber + " Serial number is not there in DB at line " + rowCount);
//				            }
//				        }
//				    }
//				} else {
//				    errors.add("Serial number is empty or null at line " + rowCount);
//				}


				/* if(vendor == null) {
                                                    	 errors.add("vendor code is not available in the master dB  ");
                                                     }*/

				inventory = inventoryRepository.findByErNo(erNo);

				if(!errors.isEmpty()) {
					continue;

				}

				if(inventory == null) {

					inventory = new Inventory();

					if(user != null && asset != null )  {                

						inventory.setCreatedOn(new Date());
						inventory.setErNo(erNo);
						inventory.setQuantity(Long.parseLong(quantity));
						inventory.setFarNo(asset.getFarNo());

						inventory.setAsset(asset);
						inventory.setUser(user);
						inventory.setWebMaster(webMaster);
						inventory.setDepartmentId(department.getDepartmentId());
						inventory.setInventoryStatus(InventoryStatus.ACTIVE.toString());
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate localDate = LocalDate.parse(allocationStartDate, formatter);                                                               				


						inventory.setAllocationStartDate(localDate);

						inventory.setAllottedPeriod(Long.parseLong(allottedPeriod));
						
						LocalDate date=inventory.getAllocationStartDate();
						inventory.setAllocationEndDate(date.plusDays(inventory.getAllottedPeriod()-1));
						
						inventory.setDescription(desc);
						
						if(serialnumber!=null) {
							inventory.setSerialNumber(serialnumber);
						}
						
						inventory = inventoryRepository.save(inventory);
					}
				}




				else
				{
					if(inventory != null)
					{
						inventory.setCreatedOn(new Date());
						inventory.setErNo(erNo);
						inventory.setQuantity(Long.parseLong(quantity));
						inventory.setFarNo(asset.getFarNo());

						inventory.setAsset(asset);
						inventory.setUser(user);
						inventory.setWebMaster(webMaster);
						inventory.setDepartmentId(department.getDepartmentId());
						inventory.setInventoryStatus(InventoryStatus.ACTIVE.toString());
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate localDate = LocalDate.parse(allocationStartDate, formatter);                                                               				


						inventory.setAllocationStartDate(localDate);

						inventory.setAllottedPeriod(Long.parseLong(allottedPeriod));
						
						LocalDate date=inventory.getAllocationStartDate();
						inventory.setAllocationEndDate(date.plusDays(inventory.getAllottedPeriod()-1));
						
						inventory.setDescription(desc);

						if(serialnumber!=null) {
							inventory.setSerialNumber(serialnumber);
						}
						
						inventory = inventoryService.updateInventory(inventory);
					}
				}

			}
			rowCount++;
		}

		//  }


		return errors; 

	}

}
