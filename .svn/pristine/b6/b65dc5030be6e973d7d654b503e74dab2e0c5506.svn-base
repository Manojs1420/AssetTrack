package com.titan.irgs.fileupload.processor;

import java.io.InputStream;
import java.util.ArrayList;
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

import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.service.IAssetService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class AssetPoProcessor extends UploadedDocProcessor{

	public static Logger logger = LoggerFactory.getLogger(InventoryAmcExtnProcessor.class);

	@Autowired
	private VendorService vendorService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	WebMasterRepo webMasterRepo;

	@Autowired
	AssetRepository assetRepository;

	@Autowired
	IAssetService assetService;

	public AssetPoProcessor() {

	}

	public AssetPoProcessor(String templateName) {
		super(templateName);
	}

	public List<String> importAssetPoRecords(InputStream inp) throws Exception {

		List<String> errors = new ArrayList<>();
		String errorInRow = null;
		Workbook wb;
		Sheet sheet;

		String assetName = null;
		String poNo = null;
		String farNo=null;
		String value=null;

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
				assetName = getValueBasedOnType(currRow.getCell(0));

				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				farNo = getValueBasedOnType(currRow.getCell(1));

				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				poNo = getValueBasedOnType(currRow.getCell(2));

				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				value = getValueBasedOnType(currRow.getCell(3));

				if (farNo.equals("") || farNo.equals(null)) {
					errors.add("farNo cannot be empty at the line is " + rowCount); 
				}


				asset=assetRepository.findByAssetName(assetName);
				if (asset == null) {
					errors.add("asset Name is not available in the master dB at the line is" + rowCount);
				}

				asset = assetRepository.findByFarNo(farNo);
				if (asset == null) {
					errors.add("Far Number is not available in the master dB at the line is" + rowCount);
				}

				if(poNo == null) {
					errors.add("Po No Column cannot be empty at line "+rowCount);

				}

				if (!errors.isEmpty()) {
					continue;

				}

				if (asset != null) {

					asset.setPoNo(poNo);
					asset.setValue(value);
					asset=assetService.updateAsset(asset);
				}

				/*	else {

					asset=assetRepository.save(asset);

				}*/
			}
			rowCount++;
		}

		return errors;

	}

}
