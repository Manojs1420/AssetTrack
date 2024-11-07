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
import com.titan.irgs.master.service.ICountryService;

@Component
public class CountryProcessor extends UploadedDocProcessor 
{
	public static Logger logger = LoggerFactory.getLogger(CountryProcessor.class);


	@Autowired
	private ICountryService countryService;



	public CountryProcessor() 
	{

	}

	public CountryProcessor(String templateName) 
	{
                super(templateName);
	}
	
	public List<String> importCountryRecords(InputStream inp) throws Exception 
	{
      
		List<String> errors = new ArrayList<>();
			Workbook wb;
			Sheet sheet;
			String errorInRow = null;
    
			String CountryId = null;
			String countryName = null;
    
    Country country= null;
    int rowCount=1;
    

    wb = WorkbookFactory.create(inp);
    sheet = wb.getSheetAt(0);
    
    for (Row currRow : sheet) {
		if (currRow.getPhysicalNumberOfCells() == 0)
			return errors;
		errorInRow = null;

		if (currRow.getRowNum() != 0) {
			currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			CountryId = getValueBasedOnType(currRow.getCell(0));

			if (CountryId != null && CountryId.isEmpty()) {
				CountryId = null;
			}
			if (CountryId == null) {
				errorInRow = "countryId can not be empty.Row " + currRow.getRowNum();
				errors.add(errorInRow);
				return errors;
			}
			
			currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			countryName = getValueBasedOnType(currRow.getCell(0));
    
      
							System.out.println(CountryId + "--" + countryName );
                            
                            country = countryService.findByCountryName(countryName);
            
                            if(country == null) {
                                            
                            				country = new Country();
                                            if(countryName != null)
                                            { 
                                                     if(countryName.equalsIgnoreCase(country.getCountryName()))
                                                     {
                                                    	 errors.add("CountryName is already present at line "+rowCount+"alredady country name exist in previous record "+country.getCountryName());
                                               		  return errors;
                                                     }
                                            	country.setCountryName(countryName);
                                            	country=countryService.saveCountry(country);
                                            }
                            
                              
                                            
                            }
                            else
                            {
                                         
                                            	 if(countryName.equalsIgnoreCase(country.getCountryName()))
                                                 {
                                            		 errors.add("CountryName is already present at line "+rowCount+" alredady country name exist in previous record as "+country.getCountryName());
                                              		  return errors;
                                                 }
                                            	country.setCountryName(countryName);
                                            	country = countryService.updateCountry(country);
                                    
                            }
            
            }
           
           rowCount++;
       }
return errors; 

	}
}

    
    
    
    
    
    
    
