package com.titan.irgs.fileupload.processor;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.AssetSpecification;
import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.Equipment;
import com.titan.irgs.master.domain.Model;
import com.titan.irgs.master.domain.SerialNumber;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.EquipmentRepository;
import com.titan.irgs.master.repository.SerialNumberRepository;
import com.titan.irgs.master.service.IAssetService;
import com.titan.irgs.master.service.IAssetSpecificationService;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.master.service.IModelService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class AssetProcessor extends UploadedDocProcessor
{
	public static Logger logger = LoggerFactory.getLogger(AssetProcessor.class);


	@Autowired
	private IAssetService assetService;

	@Autowired
	private IModelService modelService;

	@Autowired
	private IBrandService brandService;

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Autowired
	private WebMasterRepo webMasterRepo;

	@Autowired
	private StoreService storeService;

	@Autowired
	private IAssetSpecificationService iAssetSpecificationService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private SerialNumberRepository serialNumberRepository;

	public AssetProcessor() 
	{

	}

	public AssetProcessor(String templateName) 
	{
		super(templateName);
	}


	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	public List<String> importAssetRecords(InputStream inp) throws Exception 
	{


		List<String> errors = new ArrayList<>();
		Workbook wb;
		Sheet sheet;
		String errorInRow = null;


		String assetId = null;
		String assetCode = null;
		String businessVerticalName= null;
		String equipmentName = null;
		String assetName = null;
		String farNo = null;
		String itemNo = null;
		String modelName = null;
		// String modelNo = null;
		String brandName = null;
		String assetSpecName=null;
		String vendorCode = null;
		String installationDate= null;
		String warrantyPeriod=null;
		String warrantyEndDate=null;
		Asset asset= null;
		Vendor vendor = null;
		Model model = null;
		String vendorInvoiceRef=null;
		String poNo=null;
		String departmentName=null;
		String value=null;
		String serialNumbers=null;
		// Brand brand = null;
		int rowCount = 0;

		wb = WorkbookFactory.create(inp);
		sheet = wb.getSheetAt(0);

		for (Row currRow : sheet) {
			if (currRow.getPhysicalNumberOfCells() == 0)
				return errors;
			errorInRow = null;

			if (currRow.getRowNum() != 0) {
				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				assetId = getValueBasedOnType(currRow.getCell(0));

				if (assetId != null && assetId.isEmpty()) {
					assetId = null;
				}
				/*if (assetId == null) {
        					errorInRow = "assetId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}*/

				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				businessVerticalName = getValueBasedOnType(currRow.getCell(0));

				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				departmentName = getValueBasedOnType(currRow.getCell(1));

				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				assetName = getValueBasedOnType(currRow.getCell(2));
				/*	
        				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				assetCode = getValueBasedOnType(currRow.getCell(2));
				 */	
				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				brandName = getValueBasedOnType(currRow.getCell(3));

				currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				equipmentName = getValueBasedOnType(currRow.getCell(4));

				currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				farNo = getValueBasedOnType(currRow.getCell(5));

				currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				itemNo = getValueBasedOnType(currRow.getCell(6));

				currRow.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				modelName = getValueBasedOnType(currRow.getCell(7));

				currRow.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				assetSpecName = getValueBasedOnType(currRow.getCell(8));

				currRow.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				vendorCode = getValueBasedOnType(currRow.getCell(9));

				currRow.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				poNo = getValueBasedOnType(currRow.getCell(10));

				currRow.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				vendorInvoiceRef = getValueBasedOnType(currRow.getCell(11));

				currRow.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				installationDate = getValueBasedOnType(currRow.getCell(12));

				currRow.getCell(13, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				warrantyPeriod = getValueBasedOnType(currRow.getCell(13));

				currRow.getCell(14, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				value = getValueBasedOnType(currRow.getCell(14));
				
				currRow.getCell(15, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				serialNumbers = getValueBasedOnType(currRow.getCell(15));

				/*
				if(farNo.length()>10) {
					errors.add("Far Number has more than ten characters for "+farNo +"at line "+rowCount);

				}*/

				/*	Asset farno=assetRepository.findByFarNo(farNo);
				if(farno !=null) {
					errors.add("FarNo with given No  is already present,Please change FAR_NO "+farno.getFarNo());
				}*/

				/*
				if(itemNo.length() > 2) {
					errors.add("itemNo has more than 2 characters "+itemNo +"at line "+rowCount);

				}
				 */

				//	assetCode=farNo+"/"+itemNo;
				/*	Asset assetcode=assetService.findByAssetCode(assetCode);
				if(assetcode!=null) {
					errors.add("Asset with given Asset Code "+assetcode.getAssetCode() +" is already present,Please change Asset Code at line "+rowCount);
				}*/

				/*	if(!assetCode.equalsIgnoreCase(farNo+"/"+itemNo)) {
        					errors.add("Asset code Column should be combination of FAR No/Item No at line "+rowCount);	   
        				}*/

				if(farNo == null) {
					errors.add("Far Number Column cannot be empty at line "+rowCount);

				}

				if(assetSpecName == null && assetSpecName=="") {
					errors.add(" Asset Specification Name Column cannot be empty at line "+rowCount);

				}

				if(itemNo == null) {
					errors.add("Quantity Column cannot be empty at line "+rowCount);

				}

				if(modelName == null) {
					errors.add("modelName Column cannot be empty at line "+rowCount);

				}

				if(assetName == null) {
					errors.add("assetName Column cannot be empty at line "+rowCount);

				}

				if(businessVerticalName == null) {
					errors.add("businessVerticalName Column cannot be empty at line "+rowCount);

				}

				if(departmentName == null) {
					errors.add("departmentName Column cannot be empty at line "+rowCount);

				}

				if(equipmentName == null) {
					errors.add("equipmentCode Column cannot be empty at line "+rowCount);

				}

				if(brandName == null) {
					errors.add("brandName Column cannot be empty at line "+rowCount);

				}
				
				if (modelName != null) {
					if (modelName.length() != 0) {

						model = modelService.findByModelNo(modelName);
					}
				} 

				/*if (brandName != null) {
    						if (brandName.length() != 0) {
    							  brand= brandService.findByBrandName(brandName);
    						}

    						if(brand==null)
    						{
    							errors.add("brand Name is not present for "+modelNo);
                        		  return errors;

    						}

    					}*/

				if(modelName==null)
				{
					errors.add("Model No is not present for "+assetName+"at line "+rowCount);


				}

				/*if(assetCode.contains("/")) {

					String[] s = assetCode.split("/");

					assetCode=s[0];

				}
				 */
				/*	if(farNo.contains("/")) {

					String[] s = farNo.split("/");

					farNo=s[0];

				}
				 */
				/*
				if(asset4 == null) {

					asset4= assetService.findByAssetCode(farNo+"/"+itemNo);

				}
				 */


				/*     
        					Equipment equipment = equipmentRepository.findByEquipmentName(equipmentName);

        					if(equipment==  null) {
        						errors.add("The given equipmentName "+ equipmentName +"is not present in the Equipment Master");

        					}
				 */	


				WebMaster webMaster = webMasterRepo.findByWebMasterName(businessVerticalName);

				if(webMaster ==  null) {
					errors.add("The given businessVerticalName "+ businessVerticalName +"is not present in the BusinessVertical Master");

				}

				Department department=departmentRepo.findByWebMasterWebMasterIdAndDepartmentName(webMaster.getWebMasterId(),departmentName);

				if(department ==  null) {
					errors.add("The given departmentName "+ departmentName +"is not present in the department Master");

				}

				Equipment equipment1=equipmentRepository.findByEquipmentCode(equipmentName);
				if(equipment1==null) {
					errors.add("The given equipmentCode "+ equipmentName +" is not present in the Equipment Master");

				}

				Equipment equipment=null;
				if(webMaster!=null && department!=null) {
					List<Equipment> equipments = equipmentRepository.getEquipmentByVerticalIdAndDepartmentId(webMaster.getWebMasterId(),department.getDepartmentId());	
					if(equipments.isEmpty()) {
						errors.add("The given equipmentCode "+ equipmentName +" is not linked with the Vertical "+businessVerticalName+" and department "+departmentName);

					}else {
						for (Equipment edEquipment : equipments) {
							if(edEquipment.getEquipmentCode().equalsIgnoreCase(equipmentName)) {
								equipment=equipmentRepository.findByEquipmentCode(equipmentName);
							}
						}

					}
				}

				Brand brand = brandService.findByBrandCode(brandName);


				if(brand==  null) {
					errors.add("The given brandCode "+ brandName +"is not present in the Brand Master");

				}

				model = modelService.findByModelNo(modelName);

				if(model == null) {
					errors.add("The given modelNo "+ modelName +"is not present in the Model Master");
				}
				AssetSpecification assetSpecification=iAssetSpecificationService.findByAssetSpecificationName(assetSpecName);

				if(assetSpecification==null) {
					errors.add("The given assetSpecification "+ assetSpecification +"is not present in the AssetSpecification Master");


				}
				
				String[] values=null;
				if(assetSpecification!=null) {
					if(assetSpecification.getSerialNoRequired()!=null && assetSpecification.getSerialNoRequired().equalsIgnoreCase("true")) {
						if(serialNumbers.isEmpty()) {
							errors.add("SerialNumbers Column cannot be empty at line "+rowCount);

						}else {
							values = serialNumbers.split(",\\s*");
							if(Long.parseLong(itemNo)!=values.length) {
								errors.add(itemNo+" SerialNumbers must be add at the line "+rowCount);

							}
						}
					}
				}

				if(!errors.isEmpty()) {
					continue;

				}

				vendor = vendorService.findByVendorCode(vendorCode);

				Asset asset1=assetRepository.findByFarNo(farNo);
				if(asset1 == null) 

				{
					asset = new Asset();
					if(assetName != null && modelName != null && equipment != null && assetSpecification!=null)
					{ 
						asset.setOriginalQty(Long.parseLong(itemNo));
						asset.setEquipment(equipment);
						asset.setAssetName(assetName);
						asset.setModel(model);
						asset.setAssetCode(farNo+"/"+itemNo);
						asset.setFarNo(farNo);
						asset.setWebMaster(webMaster);
						asset.setDepartment(department);
						asset.setAssetSpecification(assetSpecification);

						asset.setAmcStatus(AmcStatus.INACTIVE);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate localDate = LocalDate.parse(installationDate, formatter);                                                               				


						asset.setInstallationDate(localDate);

						asset.setWarrantyPeriod(Long.parseLong(warrantyPeriod));
						LocalDate date=asset.getInstallationDate();
						asset.setWarrantyEndDate(date.plusDays(asset.getWarrantyPeriod()-1));



						if(vendor != null) {
							asset.setVendor(vendor);
							asset.setVendorInvoiceRef(vendorInvoiceRef);

						}else {
							asset.setVendor(null);

						}
						asset.setPoNo(poNo);
						asset.setValue(value);

						/*      if(asset.getAssetCode()==null) {
                                              	 errors.add("Asset with given Asset Code  couldn't be empty at line "+rowCount);
                                                  }                      	 
                                             else if(assetCode.matches(farNo+"/"+itemNo)) 
                                            	    {    
                                            	   errors.add("Asset with given Asset Code  should be FAR No/Item No at line "+rowCount);

                                               }

                                               else if(asset.getAssetCode().equalsIgnoreCase(assetCode)) {
                                        				errors.add("Asset with given Asset Code "+asset.getAssetCode() +" is already present,Please change Asset Code at line "+rowCount);
                                                        }
                                               else{

                                             }*/
						asset = assetService.saveAsset(asset);
						
						if(serialNumbers!=null) {
						//	values = serialNumbers.split(",\\s*");
							 List<String> stringList = Arrays.asList(serialNumbers.split(",\\s*"));

							    List<String> dps = stringList.stream().distinct().filter(entry -> Collections.frequency(stringList, entry) > 1).collect(Collectors.toList());
							    
							    if(!dps.isEmpty()) {
							    	throw new Exception("Duplicate Serial Numbers present");
							    }
							for(String value1:stringList) {
								SerialNumber serialNumber=new SerialNumber();
								
								serialNumber.setAsset(asset);
								serialNumber.setSerialNumber(value1);
								serialNumberRepository.save(serialNumber);
							}
						}

					}



				}
				else
				{

					asset1.setDepartment(department);
					asset1.setEquipment(equipment);
					asset1.setAssetCode(farNo+"/"+itemNo);    
					//	asset1.setOriginalQty(Long.parseLong(itemNo));
					asset1.setAssetName(assetName);
					asset1.setModel(model);
					asset1.setAssetSpecification(assetSpecification);
					//	asset1.setFarNo(farNo);
					asset1.setWebMaster(webMaster);

					/*	if(asset3.getAssetCode().equalsIgnoreCase(asset.getAssetCode())) {
							errors.add("Asset with given Asset Code "+asset3.getAssetCode() +" is already present,Please change Asset Code at line "+rowCount);
							return errors;
						}
					 */

					asset1.setAmcStatus(AmcStatus.INACTIVE);

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate localDate = LocalDate.parse(installationDate, formatter);                                                               				

					asset1.setInstallationDate(localDate);

					asset1.setWarrantyPeriod(Long.parseLong(warrantyPeriod));
					LocalDate date=asset1.getInstallationDate();
					asset1.setWarrantyEndDate(date.plusDays(asset1.getWarrantyPeriod()-1));

					if(vendor != null) {
						asset1.setVendor(vendor);
						asset1.setVendorInvoiceRef(vendorInvoiceRef);
					}else {
						asset1.setVendor(null);
					}
					asset1.setPoNo(poNo);
					asset1.setValue(value);

					asset1 = assetService.updateAsset(asset1);
					
					assetService.updateSerialNumbers(asset1,serialNumbers);
				}



			}
			rowCount++;

		}

		return errors; 
	}


}
