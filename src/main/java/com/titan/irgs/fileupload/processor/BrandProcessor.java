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

import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.repository.BrandRepository;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class BrandProcessor extends UploadedDocProcessor 
{

	public static Logger logger = LoggerFactory.getLogger(BrandProcessor.class);


	@Autowired
	private IBrandService brandService;
	
	@Autowired
	private WebMasterRepo webMasterRepo;

	
	@Autowired
	private BrandRepository brandRepository;

	public BrandProcessor() 
	{

	}

	public BrandProcessor(String templateName) 
	{
                super(templateName);
	}


	public List<String> importBrandRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
                
                
                String brandId = null;
                String brandName = null;
                String brandCode = null;
                String webMasterName = null;
                
                Brand brand = null;
                WebMaster webMaster=null;
                int rowCount = 0;

                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
                
                for (Row currRow : sheet) {
        			if (currRow.getPhysicalNumberOfCells() == 0)
        				return errors;
        			errorInRow = null;

        			if (currRow.getRowNum() != 0) {
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				brandId = getValueBasedOnType(currRow.getCell(0));

        				if (brandId != null && brandId.isEmpty()) {
        					brandId = null;
        				}
        				if (brandId == null) {
        					errorInRow = "brandId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				webMasterName = getValueBasedOnType(currRow.getCell(0));
        				
        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				brandName = getValueBasedOnType(currRow.getCell(1));

        				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				brandCode = getValueBasedOnType(currRow.getCell(2));
        				
        				
                                                
                                               
                                    				if (webMasterName != null) {
                                						if (webMasterName.length() != 0) {
                                							webMaster = webMasterRepo.findByWebMasterName(webMasterName);
                                						}
                                					}
                                    				
                                                          //      brand = brandService.findByBrandName(brandName);
                                    				brand = brandRepository.findByBrandCodeAndWebMasterWebMasterId(brandCode,webMaster.getWebMasterId());
                                                                if(brand == null) {
                                                                                
                                                                                brand = new Brand();
                                                                                if(brandName != null && brandCode != null)
                                                                                { 
                                                                                                
                                                                                              
                                                                                                brand.setBrandName(brandName);
                                                                                                brand.setBrandCode(brandCode);
                                                                                                brand.setWebMaster(webMaster);
                                                                                                brand = brandService.saveBrand(brand);
                                                                                }
                                                                
                                                                                
                                                                                
                                                                }
                                                                else
                                                                {
                                                                                if(brand != null)
                                                                                {
                                                                                                brand.setBrandName(brandName);
                                                                                                brand.setBrandCode(brandCode);
                                                                                                brand.setWebMaster(webMaster);
                                                                                                brand = brandService.updateBrand(brand);
                                                                                }
                                                                }
                                                
                                                }
                                                rowCount++;

                                }
                                
                
   
				return errors; 

  }
}
