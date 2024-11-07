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
import com.titan.irgs.master.domain.Model;
import com.titan.irgs.master.repository.BrandRepository;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.master.service.IModelService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class ModelProcessor extends UploadedDocProcessor  {
	
	public static Logger logger = LoggerFactory.getLogger(ModelProcessor.class);


	@Autowired
	private IModelService modelService;
	
	@Autowired
	private IBrandService brandService;
	
	@Autowired
	private WebMasterRepo webMasterRepo;
	
	@Autowired
	private BrandRepository brandRepository;


	public ModelProcessor() 
	{

	}

	public ModelProcessor(String templateName) 
	{
                super(templateName);
	}


	public List<String> importModelRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
                
                
                String modelId = null;
                String modelName = null;
                String modelNo = null;
                String brandName = null;
                String webMasterName = null;
                String remarks=null;
                
                Brand brand = null;
                Model model = null;
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
        				modelId = getValueBasedOnType(currRow.getCell(0));

        				if (modelId != null && modelId.isEmpty()) {
        					modelId = null;
        				}
        				if (modelId == null) {
        					errorInRow = "modelId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				webMasterName = getValueBasedOnType(currRow.getCell(0));
        				
        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				modelName = getValueBasedOnType(currRow.getCell(1));

        				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				modelNo = getValueBasedOnType(currRow.getCell(2));
        				
        				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				brandName = getValueBasedOnType(currRow.getCell(3));
        				
        				currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				remarks = getValueBasedOnType(currRow.getCell(4));
        				

        				if (webMasterName != null) {
    						if (webMasterName.length() != 0) {
    							webMaster = webMasterRepo.findByWebMasterName(webMasterName);
    						}
    					}
        				if (brandName != null) {
    						if (brandName.length() != 0) {
    							  brand = brandRepository.findByBrandNameAndWebMasterWebMasterId(brandName,webMaster.getWebMasterId());
    						}
    					}
        			//	model = modelService.findByModelName(modelName);
        				model = modelService.findByModelNoAndWebMaster(modelNo,webMaster.getWebMasterId());
                                                                if(model == null) {
                                                                                
                                                                	model = new Model();
                                                                                if(modelName != null && modelNo != null && brandName != null)
                                                                                { 
                                                                                                
                                                                                	model.setModelName(modelName);
                                                                                	model.setModelNo(modelNo);
                                                                                	model.setBrand(brand);
                                                                                	model.setWebMaster(webMaster);
                                                                                	model.setRemarks(remarks);
                                                                                                
                                                                                              
                                                                                                model = modelService.saveModel(model);
                                                                                }
                                                                
                                                                                
                                                                                
                                                                }
                                                                else
                                                                {
                                                                                if(model != null)
                                                                                {
                                                                                	model.setModelName(modelName);
                                                                                	model.setModelNo(modelNo);
                                                                                	model.setBrand(brand);
                                                                                	model.setWebMaster(webMaster);
                                                                                	model.setRemarks(remarks);
                                                                                	 model = modelService.saveModel(model);
                                                                                }
                                                                }
                                                
                                                }
                                                rowCount++;

                                }
                                
                
  
				return errors; 

  }
	
	

}
