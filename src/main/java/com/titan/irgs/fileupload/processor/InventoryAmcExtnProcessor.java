package com.titan.irgs.fileupload.processor;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.inventory.enums.MaintainanceType;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.AmcWarrantyService;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.serviceImpl.StoreService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class InventoryAmcExtnProcessor extends UploadedDocProcessor {

	public static Logger logger = LoggerFactory.getLogger(InventoryAmcExtnProcessor.class);

	@Autowired
	private StoreService storeService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	UserRepo userRepo;
	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private AmcInventoryRepository amcInventoryRepository;

	@Autowired
	private AmcInventoryService amcInventoryService;

	@Autowired
	AmcWarrantyService amcWarrantyService;
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;

	public InventoryAmcExtnProcessor() {

	}

	public InventoryAmcExtnProcessor(String templateName) {
		super(templateName);
	}

	public List<String> importInventoryAmcExtnRecords(InputStream inp) throws Exception {

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

				if (farNo.equals("") || farNo.equals(null)) {
					errors.add("Far No cannot be empty at the line is " + rowCount); 
				}
				  
				asset = assetRepository.findByFarNo(farNo);
				if (asset == null) {
					errors.add("Far Number is not available in the master dB at the line is" + rowCount);
				}
				
				Asset asset1 = assetRepository.findByAssetCode(assetCode);
				if (asset1 == null) {
					errors.add("asset code is not available in the master dB at the line is " + rowCount);
				}
			
				vendor = vendorService.findByVendorCode(vendorCode);
				if (vendor == null) {
					errors.add("vendor code is not available in the master dB at the line is" + rowCount);
				}

				if (asset.getAmcStatus() == AmcStatus.INACTIVE) {
					errors.add("Asset is INACTIVE ,Cant add AMCExtension at the line is" + rowCount);
				}
				
				List<AmcWarranty> amcWarranty=amcWarrantyRepository.FindByAssetId(asset.getAssetId());
				List<AmcWarranty> amcWarrantyTicketStatusOpen=amcWarranty.stream().filter(i->i.getTicketStatus().equalsIgnoreCase("OPEN")).collect(Collectors.toList());
				if(!amcWarrantyTicketStatusOpen.isEmpty()) {
					errors.add("Amc Service is opened, Can't extend until Close the Service at the line is" + rowCount);

				}
				
				List<AmcInventory> amcinventries = amcInventoryRepository
						.getAmcByAssetId(asset.getAssetId());

				AmcInventory amc = amcInventoryRepository
						.getOne(amcinventries.get(amcinventries.size() - 1).getAmcId());

				LocalDate to = LocalDate.now();
				LocalDate from = amc.getMaintainanceEndDate();
				long result = ChronoUnit.DAYS.between(from, to);
				if (result < 0) {
					errors.add("AmcInventory service is not complete,wait till " +amc.getMaintainanceEndDate()
							+ " to create AMCextension for Far No is "  + farNo);
				}

				if (!errors.isEmpty()) {
					continue;

				}


				if (asset.getAmcStatus() != AmcStatus.INACTIVE) {

					amcInventory = new AmcInventory();

					if (store != null && asset != null) {

						// inventory.setAmcStatus(AmcStatus.valueOf(amcStatus));
						asset.setVendor(vendor);
						amcInventory.setAsset(asset);
						amcInventory.setNumberOfService(Long.parseLong(numberOfService));
						amcInventory.setInstallationDate(asset.getInstallationDate());
						amcInventory.setMinMaintainanceGap(Long.parseLong(minMaintainanceGap));
						amcInventory.setContractNumber((contractNumber));
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  					   LocalDate localDate = LocalDate.parse(maintainanaceStartDate, formatter);                                                               				
  					 
  					   
					//	amcInventory.setMaintainanceStartDate(LocalDate.parse(maintainanaceStartDate));
  					   
  					 amcInventory.setMaintainanceStartDate(localDate);
  					 
						amcInventory.setMaintainancePeriod(
								amcInventory.getMinMaintainanceGap() * amcInventory.getNumberOfService());
						LocalDate date = amcInventory.getMaintainanceStartDate();
						amcInventory.setMaintainanceEndDate(date.plusDays(amcInventory.getMaintainancePeriod()));
						amcInventory.setMaintainanceValidity(amcInventory.getMaintainanceEndDate());
						amcInventory.setAmcStatus(AmcStatus.ACTIVE);
						amcInventory.setMaintainanceType(MaintainanceType.AMC);
						amcInventory.setAsset(asset);
						amcInventory.setWebMaster(asset.getWebMaster());
						amcInventory.setVendor(vendor);
						User user = userRepo.getOne(Long.parseLong("1"));
						amcInventory.setUser(user);
						WebMaster webmaster = webMasterRepo.getOne(Long.parseLong("1"));
						amcInventory.setAssetWebMaster(webmaster);
						asset = assetRepository.save(asset);
						amcInventory = amcInventoryRepository.save(amcInventory);
						amcWarrantyService.saveAmcExtension(amcInventory);
					}
				}

				else {

					asset.setVendor(vendor);
					asset=assetRepository.save(asset);

					amcInventoryService.updateAmc(amcInventory);
				}
			}
			rowCount++;
		}

		return errors;

	}

}
