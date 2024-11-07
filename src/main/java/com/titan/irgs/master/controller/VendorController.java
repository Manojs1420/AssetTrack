package com.titan.irgs.master.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.mapper.VendorMapper;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IVendorService;
import com.titan.irgs.master.vo.VendorVO;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.service.IRoleService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

/**
 * This is VendorController class which is responsible for handling all request(CRUD) for Vendor releated data
 * @author 
 *
 */
@RestController
@RequestMapping(value = WebConstantUrl.VENDOR_BASE_URL)
public class VendorController {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private IVendorService vendorService;
	
	@Autowired
	private VendorMapper vendorMapper;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	WebRoleRepo webRoleRepo;
	@Autowired
	VendorRepository vendorRepo;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	@Autowired
	UserRepo userRepo;

	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";
	
	/*@Autowired
	private SendMailService sendMailService;*/
	
	@GetMapping(value = WebConstantUrl.GET_ALL_VENDOR)
	@ResponseBody
	public ResponseEntity<?> getAllVendor(
			@RequestParam(required=false) String vendorName,
			@RequestParam(required=false) String vendorCode,
			@RequestParam(required=false) String vendorType,
			@RequestParam(required=false) String stateName,
			@RequestParam(required=false) String cityName,
			@RequestParam(required=false) String contactNumber,
			@RequestParam(required=false) String vendorStatus,
			@RequestParam(required=false) String webMasterName,
			Pageable pageable,Principal principal) {
		
		Pageable page=PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		Map<String,Object> map=new HashMap<>();
		User user = userRepo.findByUsername(principal.getName());


		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
			


		}	
		

		List<VendorVO> vendorVOs = new ArrayList<VendorVO>(0);
		Page<Vendor> vendors = vendorService.getAllVendor(vendorName,vendorCode,vendorType,stateName,cityName,contactNumber,vendorStatus,webMasterName,page);

		if (vendors.getContent().size() == 0) {
			map.put("userModels", vendorVOs);
			map.put("total_pages", vendors.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", vendors.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			vendors.forEach(vendor -> {
				vendorVOs.add(vendorMapper.getVoFromEntity(vendor));
			});
			
			map.put("userModels", vendorVOs);
			map.put("total_pages", vendors.getTotalPages());
			map.put("status_code",  HttpStatus.OK);
			map.put("total_records", vendors.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping(value = WebConstantUrl.GET_VENDOR_BY_ID)
	@ResponseBody
	public ResponseEntity<VendorVO> getVendorById(@PathVariable Long vendorId) {
		Vendor vendor = vendorService.getVendorById(vendorId);
		if (vendor == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<VendorVO>(vendorMapper.getVoFromEntity(vendor), HttpStatus.OK);
	}

	@PostMapping(value = WebConstantUrl.SAVE_VENDOR)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> addVendor(@RequestBody VendorVO vendorVo) {
		
	
		Map<String,Object> map = new HashMap<>();
		
		String roleName = "Vendor";
		Role role  = roleService.findByRoleNameIgnoreCase(roleName);
		
		if(role == null) {
			map.put("status code", 204);
			map.put("error msg", " Vendor role name  is not available in the Role Table");
		}
		
		WebRole webRole = webRoleRepo.findByRoleRoleIdAndWebMasterWebMasterId(role.getRoleId(),vendorVo.getWebMasterId());
		
		if(webRole == null) {
			map.put("status code", 204);
			map.put("error msg", " Vendor Role for given Business Vertical Name is not available in the WebRole Table");
		}
		
	
	    boolean vendorCodeStatus = vendorService.checkIfVendorCodeIsExit(vendorVo.getVendorCode());
	    if(vendorCodeStatus) {
	    	map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg", "Vendor Code: " + vendorVo.getVendorCode() +  " is already present. So Duplicate entry for 'Vendor Code'"
					+ " is not allowed.");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	    } else {
	    	
	    	//Role role = roleService.findByRoleNameIgnoreCase(roleName);
	    	vendorVo = vendorMapper.getVoFromEntity(vendorService.saveVendor(vendorMapper.getEntityFromVo(vendorVo),role.getRoleId()));
	    	map.put("status code", 201);
			map.put("sucess msg", " Vendor created sucessfully.");
			map.put("vendorVo", vendorVo);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
	    }
		
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_VENDOR)
	@ResponseBody
	public ResponseEntity<VendorVO> updateCity(@RequestBody VendorVO vendorVo) {
		Vendor vendor = vendorMapper.getEntityFromVo(vendorVo);
		vendor = vendorService.updateVendor(vendor);
		if (vendor == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		/*if(vendor.getVendorId() !=0 && vendor.getCreatedOn() != null){
			sendVendorCycleMail(vendor.getVendorId());
		}*/
		return new ResponseEntity<VendorVO>(vendorMapper.getVoFromEntity(vendor), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_VENDOR_BY_ID)
	@ResponseBody
	public void deleteVendorById(@PathVariable Long vendorId) {
		vendorService.deleteVendorById(vendorId);
	}
	
	
	
	/*public void sendVendorCycleMail(long vendorId) {
		
		
		
			Vendor vendor = vendorService.getVendorById(vendorId);

		

			//List<User> users = userService.findByLfsClusterIds(Arrays.asList(lfsStore.getLfsStoreCluster().getLfsClusterId()));
			List<User> users = userService.getAllUser();
			List<String> toMailIds = new ArrayList<String>(0);

			users.forEach(user -> {
				if ((user.getRole().getRoleName().trim().equalsIgnoreCase("Vendor".trim()))
						|| (user.getRole().getRoleName().trim().equalsIgnoreCase(" ".trim()))) {
					if (user.getEmailId() != null) {
						toMailIds.add(user.getEmailId());
					}
				}
			});
			if (toMailIds.size() != 0) {
				Mail mail = new Mail();
				
				String html =" <!DOCTYPE html>"  
	             		+ "<html>"  
	             		+ "<head>"  
	             		+ "<meta name='viewport' content='width=device-width, initial-scale=1'>"   
	             		+ "<style>"   
	             		+ "*{margin: 0; padding: 0;} "  
	             		+ "body{font-family: 'Franklin Gothic Book', helvetica, sans-serif; background: #efefef;}"  
	             		
	             		+".wrpcontr {max-width: 1100px; width: 100%; margin: 0px auto; background: #fff;padding: 15px;}"  
	             		+".wrpcontr p{font-size: 14px; padding-bottom: 12px; color: 333;}"  
	             		
	             		+".wrpcontr p > span {padding-right: 10px; font-size: 15px;}" 
	             		
	             		+".emiltxt {font-size: 14px; font-weight: 600;}"
	             		+"</style>"  
	             		+"</head>"  
	             		+"<body>"  
	             		
	             		+"<div class='wrpcontr'>"
	             		+" <p><span class='emiltxt'>From: </span> [titan.mpm@gmail.com]</p>"  
	             		+"<p><span class='emiltxt'>To: </span> ["+toMailIds+"]</p> " 
	             		//+"<p><span class='emiltxt'>cc: </span>["+tomaildIds1+"]</p>"  
	             		//+"<p><span class='emiltxt'>Subject:</span> Indent Information for ["+indent.+" ]</p>"  
	             		
	             		+"<div style='padding-top: 30px; padding-bottom: 20px;'>" 
	             		+" <p>Hi "+vendor.getVendorName()+",</p>" 
	             		
	             		+"<p>Vendor Infromation."  
	             		+"Please find the details below, </p>"  
	             		 
	             		+"<p>Vendor Name  -"+vendor.getVendorName() +" </p>"  
	             		+"<p> Date "+vendor.getCreatedOn() +"  </p>"  
	             		//+"<p>Indent Date -"+ indent.getDateOfIndent()+" </p> "  
	             		+"</div>"  
	             		+"<p><span class='emiltxt'>Thanks </span></p>"  
	             		+"<p>"+"Epd Team"+"</p> "  
	             		 
	             		 
	             		 
	             		+"</div>"  
	             		 
	             		+"</body>"  
	             		+"</html>"  
	             		+"  ";

				mail.setId(new Random().nextInt());
				mail.setMailSubject(
						"Mail Regarding Vendor  " );

				mail.setMailFrom("titan.cro@gmail.com");
				mail.setMailTo(toMailIds);

				mail.setMailContent("");
	            mail.setMailContent(html);
				sendMailService.sendMail(mail);
			}
		}*/
	
	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_VENDOR_EXCELSHEET)
	public ResponseEntity<byte[]> downloadsampleExcelSheet()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("Vendor_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("VENDOR_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(value = WebConstantUrl.GET_ALL_VENDORS_BY_USING_INVENTORY_ID)
	@ResponseBody
	public ResponseEntity<?> getAllVendorsByUsingAssetId(@PathVariable(value = "id") Long id) {
		
	List<Vendor> vendors = vendorService.getAllVendorsByUsingAssetId(id);
		
		/*
		 * if (vendors.isEmpty()) { vendors=vendorService.getAllVendors();
		 * if(vendors.isEmpty()) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
		 * }
		 */
		return new ResponseEntity<>(vendors.stream().map(vendorMapper::getVoFromEntity).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping("/exportExcel/{id}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("id") Long id) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vendor.xlsx";
        response.setHeader(headerKey, headerValue);
         
         MasterHeaders masterHeaders=new MasterHeaders();
    //    List<Object[]> vendors=vendorService.getAllForExcel();
 		List<Object[]> vendors = null;
		WebMaster name=webMasterRepo.findByWebMasterId(id);
		if(id !=18) {
			vendors = vendorService.getAllForExcel(id);
		}else {
			vendors = vendorService.getAllForExcel();}
        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.vendor,vendors);
         
        excelExporter.export(response);    
    }  
		
	@GetMapping(value = WebConstantUrl.GET_VENDOR_BY_VERTICALID)
	@ResponseBody
	public List<Vendor> getVendorByTypeVerticalId(@PathVariable("id") Long id) {
		return vendorService.getVendorByTypeVerticalId(id);
	}
	
	@GetMapping(value = WebConstantUrl.GET_VENDOR_BY_VenderCode)
	@ResponseBody
	public ResponseEntity<VendorVO> getVendorByVenderCode(@PathVariable String venderCode) {
		Vendor vendor=vendorRepo.findByVendorCode(venderCode);
		return new ResponseEntity<VendorVO>(vendorMapper.getVoFromEntity(vendor), HttpStatus.OK);
	}
}
