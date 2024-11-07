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

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.service.ICityService;
import com.titan.irgs.master.service.IStateService;

@Component
public class CityProcessor extends UploadedDocProcessor 
{
	public static Logger logger = LoggerFactory.getLogger(CityProcessor.class);


	@Autowired
	private ICityService cityService;

	@Autowired
	private IStateService stateService;

	
	
public CityProcessor() 
{

}

public CityProcessor(String templateName) 
{
            super(templateName);
}

public List<String> importCityRecords(InputStream inp) throws Exception 
{
            
            
            List<String> errors = new ArrayList<>();
            Workbook wb;
            Sheet sheet;
            String errorInRow = null;
            
            
            String cityId = null;
            String cityName = null;
            String stateName = null;
        
            City city=null;
            State state=null;
            
            int rowCount = 1;

            wb = WorkbookFactory.create(inp);
            sheet = wb.getSheetAt(0);
            
            for (Row currRow : sheet) {
    			if (currRow.getPhysicalNumberOfCells() == 0)
    				return errors;
    			errorInRow = null;

    			if (currRow.getRowNum() != 0) {
    				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
    				cityId = getValueBasedOnType(currRow.getCell(0));

    				if (cityId != null && cityId.isEmpty()) {
    					cityId = null;
    				}
    				if (cityId == null) {
    					errorInRow = "cityId can not be empty.Row " + currRow.getRowNum();
    					errors.add(errorInRow);
    					return errors;
    				}
    				
    				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
    				cityName = getValueBasedOnType(currRow.getCell(0));

    				/*currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
    				stateName = getValueBasedOnType(currRow.getCell(1));*/
    				
    				
    			/*	
    				if (stateName != null) {
						if (stateName.length() != 0) {
							  state= stateService.findByStateName(stateName);
						}
						if(state==null)
						{
							errors.add("stateName is not present for "+cityName);
                    		  return errors;
                      		
						}
						
					}*/
    				
    				
    			
    				
    				city= cityService.findByCityName(cityName);
    				
    	
                    if(city == null) {
                        
                    	city = new City();

                    	
                    	City city1= cityService.findByCityName(cityName);
                    	
                                    if(cityName != null)
                                    { 
                                    	if(cityName.equalsIgnoreCase(city.getCityName()))
                                    	{
                                    		

                                       	 errors.add("cityName is already present at line "+rowCount+" cityname exist in previous record "+city.getCityName());
                                  		  return errors;
                                    		
                                    	}
                                    	
                                    	
                                    	
                                          
                                    	city.setCityName(cityName);
                                    	//city.setState(state);
                                    	
                                     city= cityService.saveCity(city);
                                    }
                    
                                    
                                    
                    }
                    else
                    {
                    	if(cityName.equalsIgnoreCase(city.getCityName()))
                    	{
                    		

                       	 errors.add("cityName is already present at line "+rowCount+ " cityname exist in previous record "+city.getCityName());
                  		  return errors;
                    		
                    	}
                           
                                    	city.setCityName(cityName);
                                    	//city.setState(state);
                                    	
                                     city= cityService.updateCity(city);
                                    
                    }
    
    }
    rowCount++;

}



return errors; 

}



}
