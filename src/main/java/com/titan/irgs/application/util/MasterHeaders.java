package com.titan.irgs.application.util;

import java.util.Arrays;
import java.util.List;

public class MasterHeaders {

	public static final List<String> brands= Arrays.asList("Business Vertical Name", "Brand Name", "Brand Code" );
	
	public static final List<String> breakDownTypes= Arrays.asList("Business Vertical Name", "Brand Name", "Brand Code" );
	
	public static final List<String> models= Arrays.asList("Business Vertical Name","Model Name", "Model No","Brand","Remarks" );  
	
	public static final List<String> city= Arrays.asList("City Name"); 
	
	public static final List<String> assetSpecification= Arrays.asList("Business Vertical Name","Asset Specification Name");  

	public static final List<String> region= Arrays.asList("Region Name","State Name");  
	
	public static final List<String> state= Arrays.asList("State Name","Country Name"); 
	
	public static final List<String> equipmentType= Arrays.asList("Business Vertical Name","Equipment Type Master"); 
	
	public static final List<String> asset= Arrays.asList("Business Vertical Name","Department Name","Asset Name","Asset Code",
	                                                      "FAR No","Item No","Brand name","Brand Code","Equipment code",
	                                                      "Equipment Name","Model name","Model No","Asset Specification Name","Remarks"
	                                                      ,"Vendor Name","Vendor Code","Installation Date","Warranty Period","Warranty End Date"
	                                                      ,"Assigned","Un Assigned","Serial Numbers");

	public static final List<String> vendor=Arrays.asList("Vendor Name","Vendor Code","Vendor Type",
            											"City Name","Pincode","Contact Name","Contact Number",
            											"Business vertical Name","Billing Address","Billing EmailId",
            											"Service Address","Service EmailId","Vendor Status");



	public static final List<String> equipment= Arrays.asList("Business Vertical Name","Department Name","Equipment Name","Equipment Code","Cost","Equipment Type");
	
	public static final List<String> inventory= Arrays.asList("Business Vertical Name","Department Name","User Name","User Code","Asset Name",
																"Asset Code","Asset Specification","Brand name","Brand Code","Model No",
																"Model Name","Far No","ER No","Quantity","Serial Number","Allocation Start Date","Allotted Period","Allocation End Date","Inventory Status","Remarks");
	
	public static final List<String> stores= Arrays.asList("Store Name","Business Vertical Name","Store Code","Cost Centre","Owner Type"
															,"Email ID","Phone No 1","State","City","Region","Store Locality",
															"Reporting Role Name","Reporting To (User)","Store ServiceType",
															"Store business service type","Store allotted","Status","Address1",
															"Pin Code","Star Store Active");


	public static final List<String> users= Arrays.asList("User Name","Role Name","Reporting To","Business Vertical Type","Department Name","Operation type",
														 "Region Type","First Name","Last Name","Email Id","Mobile No","User Status"); 

	
	


	public static final List<String> servicerequest=Arrays.asList("SRN Number","Asset Code","ER Number", "Vendor Name","Ticket Status","Running Status","Created Date", "Closed Date","Age");

	public static final List<String> servicerequestReport=Arrays.asList("SRN Number","ER Number","User Code","Asset Code","User Name", "Equipments Name","Problem Statements","Maintenance engineer comment","Vendor Name","Created Date","Closed Date","Age","Running Status","Ticket Status");

	public static final List<String> AMCservicerequestReport=Arrays.asList("SRN Number","ER Number","Store code","Asset Code", "Region","Vendor Name","Created Date","Running Status","Ticket Status");

}
