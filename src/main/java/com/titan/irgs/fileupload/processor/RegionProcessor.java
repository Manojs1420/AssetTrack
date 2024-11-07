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

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.domain.Region;
import com.titan.irgs.master.domain.RegionDetails;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.repository.RegionDetailsRepository;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.service.ICityService;
import com.titan.irgs.master.service.IRegionService;
import com.titan.irgs.master.service.IStateService;

@Component
public class RegionProcessor extends UploadedDocProcessor
{
	

	public static Logger logger = LoggerFactory.getLogger(RegionProcessor.class);


	@Autowired
	private IRegionService regionService;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private IStateService stateService;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private RegionDetailsRepository regionDetailsRepository;



	public RegionProcessor() 
	{

	}

	public RegionProcessor(String templateName) 
	{
                super(templateName);
	}
	
	
	public List<String> importRegionRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
                
                
                String regionId = null;
                String regionName = null;
                String cityName = null;
                String stateName = null;
                
                Region region= null;
                RegionDetails regionDetails = null;
                City city= null;
                State state = null;
                int rowCount = 0;

                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
                
                for (Row currRow : sheet) {
        			if (currRow.getPhysicalNumberOfCells() == 0)
        				return errors;
        			errorInRow = null;

        			if (currRow.getRowNum() != 0) {
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				regionId = getValueBasedOnType(currRow.getCell(0));

        				if (regionId != null && regionId.isEmpty()) {
        					regionId = null;
        				}
        				if (regionId == null) {
        					errorInRow = "regionId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					
        				}
        				
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				regionName = getValueBasedOnType(currRow.getCell(0));

        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				stateName = getValueBasedOnType(currRow.getCell(1));
        				
        				if (stateName != null) {
    						if (stateName.length() != 0) {
    							state = stateService.findByStateName(stateName);
    						}
    					}    
       
        			 state = stateService.findByStateName(stateName);
        			
        				  if(state == null) {
        					
        					 errors.add("The given StateName is not present in Master dB "+stateName);
        					
        				   }
        				  
        				
        				  if(state != null) {
        					  
        				  
        			
        regionDetails = regionDetailsRepository.findByRegionRegionNameAndStateStateId(regionName, state.getStateId());
        			
        		
        		if(regionDetails==null)
                             
         				{
        				region = regionRepository.findByRegionNameIgnoreCase(regionName);                                                   
        					
                             if(region == null )
                                 { 
                            	     region = new Region();
                                                                             
                                     region.setRegionName(regionName);
                                     region = regionService.saveRegion(region);
                                                                                
                           }
                          else {
                        	   
                        	   region.setRegionId(region.getRegionId());
                        	   region.setRegionName(regionName);
                               region = regionService.saveRegion(region);
                           }
                             
                             regionDetails = new RegionDetails();
                             regionDetails.setCreatedDate(new Date());
                             regionDetails.setRegion(region);
                             regionDetails.setState(state);
                             regionDetailsRepository.save(regionDetails);
                                                                 
                      }else 
                      
                      
                      {
                    	  region = regionRepository.findByRegionNameIgnoreCase(regionName) ;   
                    	  regionDetails.setRegionDetailsId(regionDetails.getRegionDetailsId());
                    	  regionDetails.setCreatedDate(new Date());
                          regionDetails.setRegion(region);
                          regionDetails.setState(state);
                          regionDetailsRepository.save(regionDetails);
                    	  
                      }
        			
        			
        				  }
                                                 
                                                 
        			}
                                           rowCount++;

                               
                }
                                 
                 
    
 				return errors; 

   
        }
 }
