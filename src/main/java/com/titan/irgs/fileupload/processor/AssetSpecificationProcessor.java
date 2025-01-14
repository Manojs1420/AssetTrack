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

import com.titan.irgs.master.domain.AssetSpecification;
import com.titan.irgs.master.service.IAssetSpecificationService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class AssetSpecificationProcessor extends UploadedDocProcessor
{
	public static Logger logger = LoggerFactory.getLogger(AssetSpecificationProcessor.class);


	@Autowired
	private IAssetSpecificationService iAssetSpecificationService;
	
	@Autowired
	private WebMasterRepo webMasterRepo;

	
	
	
	


	public AssetSpecificationProcessor() 
	{

	}

	public AssetSpecificationProcessor(String templateName) 
	{
                super(templateName);
	}


	@SuppressWarnings("unused")
	public List<String> importAssetRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
                String assetSpecId = null;
                String assetSpecName = null;
                String webMasterName = null;
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
        				assetSpecId = getValueBasedOnType(currRow.getCell(0));

        				if (assetSpecId != null && assetSpecId.isEmpty()) {
        					assetSpecId = null;
        				}
        				if (assetSpecId == null) {
        					errorInRow = "brandId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}
        				
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				webMasterName = getValueBasedOnType(currRow.getCell(0));
        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				assetSpecName = getValueBasedOnType(currRow.getCell(1));
        				
                        
             				if (webMasterName != null) {
         						if (webMasterName.length() != 0) {
         							webMaster = webMasterRepo.findByWebMasterName(webMasterName);
         						}
         					}
        				AssetSpecification assetSpecification=iAssetSpecificationService.findByAssetSpecificationName(assetSpecName);
        				
        				 if(assetSpecification == null) {
                             
        					 assetSpecification = new AssetSpecification();
                             if(assetSpecName != null && webMasterName != null)
                             { 
                                             
                                           
                            	 assetSpecification.setAssetSpecificationName(assetSpecName);
                            	 assetSpecification.setWebMaster(webMaster);
                            	 assetSpecification = iAssetSpecificationService.save(assetSpecification);
                             }
             
                             
                             
             }
             else
             {
                             if(assetSpecification != null)
                             {
                            	 assetSpecification.setAssetSpecificationName(assetSpecName);
                            	 assetSpecification.setWebMaster(webMaster);
                            	 assetSpecification = iAssetSpecificationService.save(assetSpecification);
                             }
             }

        				
        			}
        			 rowCount++;

                 }
                                               
                 return errors; 
  }
	

}
