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

import com.titan.irgs.master.domain.Engineer;
import com.titan.irgs.master.service.IEngineerService;

@Component
public class EngineerProcessor extends UploadedDocProcessor {
	
	public static Logger logger = LoggerFactory.getLogger(EngineerProcessor.class);


	@Autowired
	private IEngineerService engineerService;



	public EngineerProcessor() 
	{

	}

	public EngineerProcessor(String templateName) 
	{
                super(templateName);
	}


	public List<String> importEngineerRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
                
                
                String engineerId = null;
                String engineerName = null;
                String emailId = null;
                String phoneNo = null;
                
                Engineer engineer = null;
                int rowCount = 0;

                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
                
                for (Row currRow : sheet) {
        			if (currRow.getPhysicalNumberOfCells() == 0)
        				return errors;
        			errorInRow = null;

        			if (currRow.getRowNum() != 0) {
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				engineerId = getValueBasedOnType(currRow.getCell(0));

        				if (engineerId != null && engineerId.isEmpty()) {
        					engineerId = null;
        				}
        				if (engineerId == null) {
        					errorInRow = "EngineerId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}
        				
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				engineerName = getValueBasedOnType(currRow.getCell(0));

        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				emailId = getValueBasedOnType(currRow.getCell(1));
        				
        				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				phoneNo = getValueBasedOnType(currRow.getCell(2));
                                                
                                                if (rowCount != 0) {
                                                                System.out.println(engineerId + "--" + engineerName );
                                                                
                                                                engineer = engineerService.findByEngineerName(engineerName);
                                                                if(engineer == null) {
                                                                                
                                                                                engineer = new Engineer();
                                                                                if(engineerName != null && phoneNo != null)
                                                                                { 
                                                                                                
                                                                                              
                                                                                                engineer.setEngineerName(engineerName);
                                                                                                engineer.setMobileNo(phoneNo);
                                                                                                engineer.setCreatedOn(new Date());
                                                                                                engineer.setEmailId(emailId);
                                                                                                engineer = engineerService.saveEngineer(engineer);
                                                                                }
                                                                
                                                                                
                                                                                
                                                                }
                                                                else
                                                                {
                                                                                if(engineer != null)
                                                                                {
                                                                                	 engineer.setEngineerName(engineerName);
                                                                                     engineer.setMobileNo(phoneNo);
                                                                                     engineer.setCreatedOn(new Date());
                                                                                     engineer.setEmailId(emailId);
                                                                                     engineer = engineerService.updateEngineer(engineer);
                                                                                }
                                                                }
                                                
                                                }
                                                rowCount++;

                                }
                                
                
   }
				return errors; 

  }

}
