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

import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.Equipment;
import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.domain.EquipmentWiseDepartments;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.EquipmentRepository;
import com.titan.irgs.master.repository.EquipmentWiseDepartmentsRepo;
import com.titan.irgs.master.repository.VendorEquipmentRepository;
import com.titan.irgs.master.service.IEquipmentService;
import com.titan.irgs.master.service.IEquipmentTypeService;
import com.titan.irgs.master.service.IVendorService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@Component
public class EquipmentProcessor extends UploadedDocProcessor
{

	public static Logger logger = LoggerFactory.getLogger(EquipmentProcessor.class);

	@Autowired
	private IEquipmentService  equipmentService;
	
	@Autowired
	private IEquipmentTypeService equipmentTypeService;
	
	@Autowired
	EquipmentRepository equipmentRepository;
	/*@Autowired
	private IEquipmentDetailService equipmentDetailService;*/
	
	
	@Autowired
	private IVendorService vendorService;
	
	@Autowired
	private WebMasterRepo webMasterRepo;
	@Autowired
	private VendorEquipmentRepository vendorEquipmentRepository;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Autowired
	EquipmentWiseDepartmentsRepo equipmentWiseDepartmentsRepo;
	
	public EquipmentProcessor() 
	{

	}

	public EquipmentProcessor(String templateName) 
	{
                super(templateName);
	}
	
	
	@SuppressWarnings("null")
	public List<String> importEquipmentRecords(InputStream inp) throws Exception 
	{
                
                
                List<String> errors = new ArrayList<>();
                Workbook wb;
                Sheet sheet;
                String errorInRow = null;
			
                
                String equipmentId = null;
                String equipmentName=null;
                String equipmentCost = null;
                String equipmentCode=null;
                String vendorCode=null;
                String equipmentType=null;
                String webMasterName=null;
            String departmentName=null;
                Equipment equipment= null;
                WebMaster webMaster=null;

              //  EquipmentDetail equipmentDetail=null;

                
                int rowCount = 1;
                
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
                
                for (Row currRow : sheet) 
                {
        			if (currRow.getPhysicalNumberOfCells() == 0)
        				return errors;
        			errorInRow = null;
                
        			
        			if (currRow.getRowNum() != 0) 
        			{
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				equipmentId = getValueBasedOnType(currRow.getCell(0));

        				if (equipmentId != null && equipmentId.isEmpty())
        				{
        					equipmentId = null;
        				}
        				if (equipmentId == null) 
        				{
        					errorInRow = "equipmentId can not be empty.Row " + currRow.getRowNum();
        					errors.add(errorInRow);
        					return errors;
        				}
        				currRow.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				webMasterName = getValueBasedOnType(currRow.getCell(0));
        				
        				currRow.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				departmentName = getValueBasedOnType(currRow.getCell(1));
        			
        				currRow.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				equipmentName = getValueBasedOnType(currRow.getCell(2));
        				
        				
        				currRow.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				equipmentCode = getValueBasedOnType(currRow.getCell(3));
        			
        				
        				currRow.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				equipmentCost = getValueBasedOnType(currRow.getCell(4));
        				
        			     currRow.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				vendorCode = getValueBasedOnType(currRow.getCell(5));
        				System.out.println("vendorCode==>"+vendorCode);
        				
        				currRow.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
        				equipmentType = getValueBasedOnType(currRow.getCell(6));
        				
        				
        				Vendor vendor=null;
        				
        			/*	if (equipmentName != null)
    					{
    						if (equipmentName.length() != 0) 
    						{
    							equipment = equipmentService.findByEquipmentName(equipmentName);
    							
    						}
    					
    					}
        			*/	
        				if (equipmentCode != null)
    					{
    						if (equipmentCode.length() != 0) 
    						{
    							equipment = equipmentRepository.findByEquipmentCode(equipmentCode);
    							
    						}
    					
    					}
    		
        			/*	if(vendorCode==null)
                        {
                       	 errors.add("vendorCode is empty at the line "+rowCount);
                  		  return errors;
                        }
				*/
        				if(webMasterName==null) {
       					 errors.add("webMasterName is empty at the line "+rowCount);
                    		  return errors;
       				}
        				  if (webMasterName != null) {
        					  if (webMasterName.length() != 0) {
        						  webMaster = webMasterRepo.findByWebMasterName(webMasterName);
        						  } 
        					  }
        				  
        					if(webMaster==null) {
              					 errors.add("webMaster is empty in the master dB at the line "+rowCount);
                           		  return errors;
              				}
        					
        				Department department=departmentRepo.findByWebMasterWebMasterIdAndDepartmentName(webMaster.getWebMasterId(),departmentName);

        				if(department ==  null) {
        					errors.add("The given departmentName "+ departmentName +"is not present in the department Master");
        					return errors;
        				}
        				
        				
					 
        			//	webMaster = webMasterRepo.findByWebMasterName(webMasterName);
        					
        				if(equipmentType==null) {
        					 errors.add("equipmentType is empty at the line "+rowCount);
                     		  return errors;
        				}
        				
        				
        				EquipmentType equipmentTypeObj = equipmentTypeService.findByEquipmentTypeName(equipmentType);
        				if(equipmentTypeObj==null) {
       					 errors.add("equipmentType is empty in the master dB at the line "+rowCount);
                    		  return errors;
       				}
        				
        					if ((equipmentName != null)  && (equipmentCost != null) ) 
        					{
        						
        					  if(equipment == null)
        						
        					{
        					
        						equipment = new Equipment();
        						
        					/*	 if(equipmentName.equalsIgnoreCase(equipment.getEquipmentName()))
                                 {
                                	 errors.add("equipmentName is already present at line "+rowCount+"equipmentName exist in previous record "+equipment.getEquipmentName());
                           		  return errors;
                                 }
        					*/	 
        						 /*	 vendor = vendorService.findByVendorCode(vendorCode);
        						 
        						 if(vendor == null) {
        							 
        						 if(vendorCode.equals("")  || vendorCode.equals(null)) {
        							
        								 
        								    equipment.setEquipmentName(equipmentName);
        	        						equipment.setEquipmentCode(equipmentCode);
        	        						equipment.setEquipmentCost(Double.parseDouble(equipmentCost));
        	        						equipment.setEquipmentType(equipmentTypeObj);
         	        						equipment.setWebMaster(webMaster);
        	        						equipment = equipmentService.saveEquipment(equipment);	
        							 
        							 
        						   }
        						 }else {*/
        							 equipment.setEquipmentType(equipmentTypeObj);
        							equipment.setEquipmentName(equipmentName);
 	        						equipment.setEquipmentCode(equipmentCode);
 	        						equipment.setEquipmentCost(equipmentCost);
 	        						equipment.setWebMaster(webMaster);
 	        							
 	        						equipment = equipmentService.saveEquipment(equipment);	
 	        							
	        							EquipmentWiseDepartments equipmentWiseDepartment=new EquipmentWiseDepartments();
	        							equipmentWiseDepartment.setCreatedOn(new Date());
	        							equipmentWiseDepartment.setDepartment(department);
	        							equipmentWiseDepartment.setEquipment(equipment);
	        							equipmentWiseDepartment=equipmentWiseDepartmentsRepo.save(equipmentWiseDepartment);
	        							
 	        						/*
 	        						VendorEquipment vendorEquipment = new VendorEquipment();
 	        						vendorEquipment.setCreatedOn(new Date());
 	        						vendorEquipment.setEquipmentId(equipment.getEquipmentId());
 	        						vendorEquipment.setVendor(vendor);
 	        						
 	        						vendorEquipment= vendorEquipmentRepository.save(vendorEquipment);
 	        						*/
        							 
        						// }
        						
        						/*if(equipment!=null){
        							equipment = equipmentService.updateEquipment(equipment);
        						}*/
        					
        					}else {
        						
        					/*	 vendor = vendorService.findByVendorCode(vendorCode);
        						 
        						 if(vendor == null) {
        								equipment.setEquipmentName(equipmentName);
                						equipment.setEquipmentCode(equipmentCode);
                						equipment.setEquipmentCost(Double.parseDouble(equipmentCost));
                						equipment.setEquipmentType(equipmentTypeObj);
     	        						equipment.setWebMaster(webMaster);
                						equipment = equipmentService.updateEquipment(equipment);
        						 }else {
        							 
        						 */
        							equipment.setEquipmentName(equipmentName);
  	        						equipment.setEquipmentCode(equipmentCode);
  	        						equipment.setEquipmentCost(equipmentCost);
  	        						equipment.setEquipmentType(equipmentTypeObj);
 	        						equipment.setWebMaster(webMaster);
  	        						equipment = equipmentService.saveEquipment(equipment);
  	        						
  	        						EquipmentWiseDepartments equipmentWiseDepartment=new EquipmentWiseDepartments();
        							equipmentWiseDepartment.setCreatedOn(new Date());
        							equipmentWiseDepartment.setDepartment(department);
        							equipmentWiseDepartment.setEquipment(equipment);
        							equipmentWiseDepartment=equipmentWiseDepartmentsRepo.save(equipmentWiseDepartment);
        				
  	        						
  	        					/*	
  	        						VendorEquipment vendorEquipment = vendorEquipmentRepository.findByEquipmentIdAndVendorVendorId(equipment.getEquipmentId(),vendor.getVendorId());
  	        						if(vendorEquipment != null){
  	        							vendorEquipment.setVendorEquipmentId(vendorEquipment.getVendorEquipmentId());
  	  	        						vendorEquipment.setCreatedOn(new Date());
  	  	        						vendorEquipment.setEquipmentId(equipment.getEquipmentId());
  	  	        						vendorEquipment.setVendor(vendor);
  	  	        						
  	  	        						vendorEquipment= vendorEquipmentRepository.save(vendorEquipment);
  	        						}else {
  	        							vendorEquipment = new VendorEquipment();
  	        							vendorEquipment.setVendorEquipmentId(vendorEquipment.getVendorEquipmentId());
  	  	        						vendorEquipment.setCreatedOn(new Date());
  	  	        						vendorEquipment.setEquipmentId(equipment.getEquipmentId());
  	  	        						vendorEquipment.setVendor(vendor);
  	  	        						
  	  	        						vendorEquipment= vendorEquipmentRepository.save(vendorEquipment);
  	        							
  	        						}
  	        						
        							 
        						 }
        					*/
        						
        					}
        						  
    							
    						
        				
    					
    						
    							
    						
        			
    					}
        				
    				}
    	
    	}
				
     return errors;
  }
}
