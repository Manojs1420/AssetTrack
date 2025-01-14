package com.titan.irgs.fileupload.processor;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.inventory.enums.MaintainanceType;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.AmcWarrantyService;
import com.titan.irgs.inventory.serviceImpl.InventoryService;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.serviceImpl.AssetService;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class InventoryAmcProcessor extends UploadedDocProcessor {

	public static Logger logger = LoggerFactory.getLogger(InventoryAmcProcessor.class);

	@Autowired
	private StoreService storeService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private WebMasterRepo webMasterRepo;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private AmcInventoryRepository amcInventoryRepository;

	@Autowired
	private AmcInventoryService amcInventoryService;

	@Autowired
	AmcWarrantyService amcWarrantyService;
	@Autowired
	UserRepo userRepo;

	@Autowired
	AssetRepository assetRepository;

	public InventoryAmcProcessor() {

	}

	public InventoryAmcProcessor(String templateName) {
		super(templateName);
	}

	public List<String> importInventoryAmcRecords(InputStream inp) throws Exception {

		List<String> errors = new ArrayList<>();
		String errorInRow = null;
		Workbook wb;
		Sheet sheet;

		String assetCode = null;
		String farNo = null;
		String vendorCode = null;
		String maintainanaceStartDate = null;
		String numberOfService = null;
		String minMaintainanceGap = null;
		String contractNumber = null;
		String amcStatus = null;
		Long vendors = null;

		AmcInventory amcInventory = null;
		Vendor vendor = null;
		Store store = null;
		Asset asset=null;
		int rowCount = 0;

		wb = WorkbookFactory.create(inp);
		sheet = wb.getSheetAt(0);

		for (Row currRow : sheet) {
			if (currRow.getPhysicalNumberOfCells() == 0)
				return errors;
			errorInRow = null;

			if (currRow.getRowNum() != 0) {

				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				assetCode = getValueBasedOnType(currRow.getCell(0));

				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				farNo = getValueBasedOnType(currRow.getCell(1));

				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				vendorCode = getValueBasedOnType(currRow.getCell(2));

				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				maintainanaceStartDate = getValueBasedOnType(currRow.getCell(3));

				currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				numberOfService = getValueBasedOnType(currRow.getCell(4));

				currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				minMaintainanceGap = getValueBasedOnType(currRow.getCell(5));

				currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				contractNumber = getValueBasedOnType(currRow.getCell(6));

				currRow.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				amcStatus = getValueBasedOnType(currRow.getCell(7));

				// if (rowCount != 0) {

				if (farNo.equals("") || farNo.equals(null)) {

					errors.add("Far No cannot be empty in the excel column " + rowCount);

				}
				asset = assetRepository.findByFarNo(farNo);
				if (asset == null) {
					errors.add("Far Number is not available in the master dB at the line is" + rowCount);
				}
				
				Asset asset1 = assetRepository.findByAssetCode(assetCode);
				if (asset1 == null) {
					errors.add("asset code is not available in the master dB at the line is " + rowCount);
				}
				
			/*	Date installationDate=inventoryRepository.getInstallationDateByErno(erNo);

				if (installationDate==null) {
					errors.add("Installation Date and Warranty Period is null for the ErNumber is " + erNo);
				}else {
					Date warrantyDate=inventoryRepository.getWarrantyDateByErno(erNo);

					LocalDate to = LocalDate.parse(warrantyDate.toString());
					LocalDate from = LocalDate.parse(maintainanaceStartDate);
					long result = ChronoUnit.DAYS.between(from, to);
					if(result>0) {
						errors.add("Warranty End Date is not completed, please change Maintainance Start Date For the ErNo is" + erNo);

					}
				}
				*/

				Date installationDate=assetRepository.getInstallationDateByFarno(farNo);

				if (installationDate==null) {
					errors.add("Installation Date and Warranty Period is null for the FarNumber is " + farNo);
				}else {
					Date warrantyDate=assetRepository.getWarrantyDateByFarno(farNo);

			        LocalDate to = LocalDate.parse(warrantyDate.toString());
			        LocalDate from = LocalDate.parse(maintainanaceStartDate);
			        long result = ChronoUnit.DAYS.between(from, to);
			        if(result>0) {
						errors.add("Warranty End Date is not completed, please change Maintainance Start Date For the FarNo is" + farNo);

			        }
				}

				vendor = vendorService.findByVendorCode(vendorCode);
				
				if (vendor == null) {
					errors.add("vendor code is not available in the master, for the FarNumber is " + farNo);
				}

				/*
				 * vendors = inventoryRepository
				 * .getVendorByEquipmentId(inventory.getAsset().getEquipment().getEquipmentId())
				 * ;
				 * 
				 * 
				 * if (vendors == null) { errors.add("vendor code is not assign to equipment  "+
				 * rowCount); }
				 */

				amcInventory = amcInventoryRepository.FindByFarNo(farNo);

				if (!errors.isEmpty()) {
					continue;

				}

				if (amcInventory == null) {

					amcInventory = new AmcInventory();

					if (store != null && asset != null) {

						asset.setAmcStatus(AmcStatus.valueOf(amcStatus));
						asset.setVendor(vendor);
					//	amcInventory.setInventory(inventory);
						amcInventory.setNumberOfService(Long.parseLong(numberOfService));
						amcInventory.setInstallationDate(asset.getInstallationDate());
						amcInventory.setMinMaintainanceGap(Long.parseLong(minMaintainanceGap));
						amcInventory.setContractNumber((contractNumber));
						amcInventory.setMaintainanceStartDate(LocalDate.parse(maintainanaceStartDate));
						amcInventory.setMaintainancePeriod(
								amcInventory.getMinMaintainanceGap() * amcInventory.getNumberOfService());
						LocalDate date = amcInventory.getMaintainanceStartDate();
						amcInventory.setMaintainanceEndDate(date.plusDays(amcInventory.getMaintainancePeriod()));
						amcInventory.setMaintainanceValidity(amcInventory.getMaintainanceEndDate());
						amcInventory.setAmcStatus(AmcStatus.valueOf(amcStatus));
						amcInventory.setMaintainanceType(MaintainanceType.AMC);
						amcInventory.setAsset(asset);
					//	amcInventory.setStore(store);
						amcInventory.setWebMaster(asset.getWebMaster());
						amcInventory.setVendor(vendor);
						User user = userRepo.findById((long) 1).get();
						amcInventory.setUser(user);
						WebMaster assetWebMaster = webMasterRepo.getOne((long) 1);
						amcInventory.setAssetWebMaster(assetWebMaster);
						asset=assetRepository.save(asset);
						amcInventory = amcInventoryRepository.save(amcInventory);
						amcWarrantyService.saveAmcExtension(amcInventory);
					}
				}

				else {

					asset.setAmcStatus(AmcStatus.valueOf(amcStatus));
					asset.setVendor(vendor);
					// inventory.setInstallationDate(installationDate);
					asset=assetRepository.save(asset);
					amcInventoryService.updateAmc(amcInventory);
					// if (amcInventory != null) {
					/*
					 * AmcInventory
					 * amcInventory1=amcInventoryRepository.findById(amcInventory.getAmcId()).get();
					 * amcWarrantyService.updateWarrantyAmc(amcInventory1);
					 * amcInventory1.setInventory(inventory);
					 * amcInventory1.setNumberOfService(Long.parseLong(numberOfService));
					 * amcInventory1.setInstallationDate(LocalDate.parse(installationDate));
					 * amcInventory1.setMinMaintainanceGap(Long.parseLong(minMaintainanceGap));
					 * amcInventory1.setContractNumber(Long.parseLong(contractNumber));
					 * amcInventory1.setMaintainanceStartDate(LocalDate.parse(installationDate));
					 * amcInventory1.setMaintainancePeriod(amcInventory.getMinMaintainanceGap()*
					 * amcInventory.getNumberOfService()); LocalDate
					 * date=amcInventory.getMaintainanceStartDate();
					 * amcInventory1.setMaintainanceEndDate(date.plusDays(amcInventory.
					 * getMaintainancePeriod()));
					 * amcInventory1.setMaintainanceValidity(amcInventory.getMaintainanceEndDate());
					 * amcInventory1.setAmcStatus(AmcStatus.valueOf(amcStatus));
					 * amcInventory1.setMaintainanceType(MaintainanceType.AMC);
					 * amcInventory1.setAsset(inventory.getAsset()); amcInventory1.setStore(store);
					 * amcInventory1.setWebMaster(inventory.getWebMaster());
					 * amcInventory1.setVendor(vendor);
					 * amcWarrantyService.saveAmcExtension(amcInventory1); //inventory =
					 * inventoryService.updateInventory(inventory); amcInventory1 =
					 * amcInventoryRepository.save(amcInventory1);
					 */
				}
			}
			//			}
			rowCount++;

		}

		return errors;

	}

}
