
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

import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.service.ICountryService;
import com.titan.irgs.master.service.IStateService;

@Component
public class StateProcessor extends UploadedDocProcessor 
{

	public static Logger logger = LoggerFactory.getLogger(StateProcessor.class);


	@Autowired
	private IStateService stateService;
	
	@Autowired
	private ICountryService countryService;



	public StateProcessor() 
	{

	}
	
	public StateProcessor(String templateName) 
	{
                super(templateName);
	}
	

	public List<String> importStateRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
                
                
                String stateId = null;
                String stateName = null;
                String countryName = null;
                
                State state = null;
                Country country= null;
                int rowCount = 1;

                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
             
                for (Row currRow : sheet) {
        			if (currRow.getPhysicalNumberOfCells() == 0)
        				return errors;
        			errorInRow = null;

        			if (currRow.getRowNum() != 0) {
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				stateId = getValueBasedOnType(currRow.getCell(0));

        				if (stateId != null && stateId.isEmpty()) {
        					stateId = null;
        				}
        				if (stateId == null) {
        					errorInRow = "stateId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}
        				
        				
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				stateName = getValueBasedOnType(currRow.getCell(0));

        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				countryName = getValueBasedOnType(currRow.getCell(1));
        				
        				
        				if (countryName != null) {
    						if (countryName.length() != 0) {
    							  country = countryService.findByCountryName(countryName);
    						}
    					}  
        				state = stateService.findByStateName(stateName);
        				
        				if(state  == null) 
        				{
                             
        					
        				    state  = new State();
                                         if(stateName != null && countryName != null)
                                         { 
                                        	 if(stateName.equalsIgnoreCase(state.getStateName()))
                                        	 {
                                        		 
                                        		 errors.add("StateName is already present at line "+rowCount+" state name exist in previous record "+state.getStateName());
                                          		  return errors;
                                        	 }
                                             state.setStateName(stateName);
                                             state.setCountry(country);
                                        	 
                                          state = stateService.saveState(state);
                                         }
                         
                                  }
                         else
                         {
                                     
                        	 
                        	 if(stateName.equalsIgnoreCase(state.getStateName()))
                        	 {
                        		 errors.add("StateName is already present at line "+rowCount+" state name exist in previous record "+state.getStateName());
                          		  return errors;
                        	 }
                                        	 state.setStateName(stateName);
                                             state.setCountry(country);
                                        	 
                                          state = stateService.updateState(state);
                              
                         }
         
         }
         rowCount++;

}



return errors; 

}



}
