package com.titan.irgs.inventory.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.titan.irgs.UserRoleServiceApplication;
import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.ImagePath;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.inventory.domain.AmcInventory;
import com.titan.irgs.inventory.domain.Inventory;
import com.titan.irgs.inventory.mapper.InventoryMapper;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.repository.AmcWarrantyRepository;
import com.titan.irgs.inventory.repository.InventoryQRUploadRepo;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.inventory.repository.MaintainanceRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.service.IInventoryService;
import com.titan.irgs.inventory.serviceImpl.InventoryQRServiceImpl;
import com.titan.irgs.inventory.vo.InventoryQRCodeUploadVO;
import com.titan.irgs.inventory.vo.InventoryVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.ClusterRepository;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IAssetService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@RestController
@RequestMapping(value = WebConstantUrl.INVENTORY_BASE_URL)
public class InventoryController {

	@Autowired
	private IInventoryService inventoryService;
	@Value("${mail.status}")
	private Boolean mailStatus;

	@Autowired
	 ImagePath imagePath;
	
	@Autowired
	MaintainanceAlertEmailImpl maintainanceAlertEmailImpl;
	
	@Autowired
	MaintainanceAlertEmailImpl1 maintainanceAlertEmailImpl1;
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	private InventoryMapper inventoryMapper;
	
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	AmcInventoryRepository  amcInventoryRepository;
	@Autowired
	AmcWarrantyRepository amcWarrantyRepository;
	@Autowired
	MaintainanceRepository maintainanceRepository;
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	AmcInventoryEmailImpl emailServiceImpl;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;
	
	@Autowired
	InventoryQRUploadRepo inventoryQRUploadRepo;
	
	@Autowired
	ClusterRepository clusterRepository;
	
	@Autowired
	InventoryQRServiceImpl inventoryQRServiceImpl;
	
	@Autowired
	IAssetService assetService;
	
	@Autowired
	private AmcInventoryService amcInventoryService;
	private static final String superAdmin = "superadmin";
	private final static String ITAdmin="ITAdmin";
	private static final String MANAGEMENT = "MANAGEMENT";
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";
	private static final String Vendor = "VENDOR";

	// :::::::::::: Inventory CRUD
	// operation----------------------------------------------

	@GetMapping(value = WebConstantUrl.GET_ALL_INVENTORY)
	@ResponseBody
	public ResponseEntity<?> getAllInventory(@RequestParam(required = false) String StoreCode,
			@RequestParam(required = false) String StoreName,
			@RequestParam(required = false) String BusinessVerticalName,
			@RequestParam(required = false) String VerticalName, @RequestParam(required = false) String AssetName,
			@RequestParam(required = false) String FarNo, @RequestParam(required = false) String ModelName,
			@RequestParam(required = false) String serialNumber,
			@RequestParam(required = false) String BrandName, @RequestParam(required = false) String erNo,
			@RequestParam(required = false) String inventoryStatus,
			@RequestParam(required = false) String equipmentName,
			@RequestParam(required = false) String regionName,
			@RequestParam(required = false) List<Long> region,
			@RequestParam(required = false) String vendorCode,@RequestParam(required = false) String BusinessVertical,
			@RequestParam(required = false) String departmentName,
			Pageable pageable,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
			Principal principal) {

	//	Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
	//			pageable.getPageSize());
		Pageable pages = PageRequest.of(page - 1, size == 0 ? Integer.MAX_VALUE : size, pageable.getSort());
//		Pageable pages = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
		
		Map<String, Object> map = new HashMap<>();

		User user = userRepo.findByUsername(principal.getName());
		// List<User> usersEmails = new ArrayList<>();

		String user1=null;
		String department=null;
		VerticalName=null;
		
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				VerticalName = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if( (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) && (!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD)) ) {
				user1 = user.getUsername();
			}else {
				VerticalName = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}
		
		/*
		else if (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)
				) {

			VerticalName = user.getRole().getWebMaster().getWebMasterName();
			//department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
		}

		/*
		 * if (user.getRole().getRole().getRoleName().equalsIgnoreCase(Vendor)) { //
		 * store name and username are same vendorCode = user.getUsername();
		 * 
		 * }
		 */
		
		
		List<InventoryVO> inventoryVOs = new ArrayList<InventoryVO>(0);
	 	Page<Inventory> inventories = inventoryService.getAllInventory(user1,StoreCode, StoreName, BusinessVerticalName,
				VerticalName, AssetName, FarNo, ModelName, serialNumber,BrandName, erNo, inventoryStatus,equipmentName,regionName,region,vendorCode,departmentName,department, pages);

		if (inventories.getContent().size() == 0) {
			map.put("inventoryVOs", inventoryVOs);
			map.put("total_pages", inventories.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", inventories.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);

		} else {

			inventories.forEach(inventory -> {

				inventoryVOs.add(inventoryMapper.getVoFromEntity(inventory));
			});
			map.put("inventoryVOs", inventoryVOs);
			map.put("total_pages", inventories.getTotalPages());
			map.put("status_code", HttpStatus.OK);
			map.put("total_records", inventories.getTotalElements());

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@GetMapping(value = WebConstantUrl.GET_INVENTORY_BY_BUSSINESS_ID)
	@ResponseBody
	public ResponseEntity<?> getInventoryByBssinessId(@PathVariable(value = "id") Long id) {

		List<Inventory> inventories = inventoryService.getInventoryByBssinessId(id);

		if (inventories.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<InventoryVO> inventoryVOs = inventories.stream().map(inventoryMapper::getVoFromEntity)
				.collect(Collectors.toList());

		return new ResponseEntity<>(inventoryVOs, HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GET_INVENTORY_BY_ID)
	@ResponseBody
	public ResponseEntity<InventoryVO> getInventoryById(@PathVariable(value = "id") Long id) {

		Inventory inventory = inventoryService.getInventoryById(id);

		if (inventory == null) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InventoryVO>(inventoryMapper.getVoFromEntity(inventory), HttpStatus.OK);
	}

	@PostMapping(WebConstantUrl.SAVE_INVENTORY)
	@ResponseBody
	public ResponseEntity<InventoryVO> saveInventory(@RequestBody InventoryVO inventoryVo) {

		Inventory inventory=null;
		inventory = inventoryMapper.getEntityFromVo(inventoryVo);
		
		Asset asset=null;
		
		List<Inventory> inventorys=inventoryRepository.findInventoryByAssetCodes(inventory.getAsset().getAssetCode());
		if(!inventorys.isEmpty()) {
			
			asset=assetRepository.findByAssetCode(inventorys.get(0).getAsset().getAssetCode());
				
				if(inventorys.size()==asset.getOriginalQty()) {
					throw new  ResourceAlreadyExitException("Inventories limit is Crossed for the Asset Code : " + asset.getAssetCode());

				}
			
		}
		
		
		
	/*	Asset asset=assetRepository.findByAssetCodeAndOriginalQty(inventory.getAsset().getAssetCode(), inventory.getAsset().getOriginalQty());

		if(asset!=null) {
			if(asset.getOriginalQty()!=null) {
				asset.setOriginalQty(asset.getOriginalQty()-1); //Once Inventory created for that asset qty should reduce in asset.
				asset=assetService.updateAsset(asset);
			}
		}
	*/	
		inventory = inventoryService.saveInventory(inventory);

		if (inventory == null) {
			return new ResponseEntity<InventoryVO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InventoryVO>(inventoryMapper.getVoFromEntity(inventory), HttpStatus.CREATED);
	}

	@SuppressWarnings("deprecation")
	@PutMapping(value = WebConstantUrl.UPDATE_INVENTORY)
	@ResponseBody
	public ResponseEntity<InventoryVO> updateInventory(@RequestBody InventoryVO inventoryVo) {

		Inventory inventory = inventoryMapper.getEntityFromVo(inventoryVo);
		inventory = inventoryService.updateInventory(inventory);

	//	AmcInventory amcInventory=amcInventoryRepository.FindAmcByInventoryId(inventory.getInventoryId());
	//	amcInventory.setVendor(inventory.getVendor());
	
	//	amcInventoryRepository.save(amcInventory);

		if (inventory == null) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InventoryVO>(inventoryMapper.getVoFromEntity(inventory), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_INVENTORY_BY_ID)
	public void deleteInventoryById(@PathVariable(value = "id") Long id) {

		inventoryService.deleteInventoryById(id);
	}

	@GetMapping("/exportExcel/{id}")
	public void exportToExcel(HttpServletResponse response, @PathVariable("id") Long id,Principal principal) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=inventory.xlsx";
		response.setHeader(headerKey, headerValue);

		MasterHeaders masterHeaders = new MasterHeaders();
		User user=userRepo.findByUsername(principal.getName());
		String businessVerticalType=null;
		String department=null;
		String user1=null;
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if(user.getRole().getRole().getRoleName().equalsIgnoreCase(superAdmin)) {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
			}
			else if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if(!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD) ) {
				user1 = user.getUsername();
			}else {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}
		
		List<Object[]> inventory = null;
		if (businessVerticalType!=null && department!=null) {
			inventory = inventoryService.getAllExcel(businessVerticalType,department);
		} else if(user1!=null){
			inventory = inventoryService.getAllExcel(user1);
		}else if(businessVerticalType!=null) {
			inventory = inventoryService.getAllExcelForVertical(businessVerticalType);
		}else {
			inventory = inventoryService.getAllExcel();
		}
		
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.inventory, inventory);

		excelExporter.export(response);
	}


	@GetMapping("/exportExcels/{storecode}")
	public void exportToExcel(HttpServletResponse response, @PathVariable("storecode") String storecode, Principal principal)
			throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=inventory.xlsx";
		response.setHeader(headerKey, headerValue);

		User user = userRepo.findByUsername(principal.getName());
		List<Inventory> getall = inventoryRepository.findAll();

		MasterHeaders masterHeaders = new MasterHeaders(); 
		// List<Object[]>inventory = inventoryService.getAllExcel();
		List<Object[]> inventory = null;
		// WebMaster name = webMasterRepo.findByWebMasterId(id);
	
			inventory = inventoryService.getAllStoreCode(storecode);
	
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.inventory, inventory);

		excelExporter.export(response);
	}
	
	@SuppressWarnings({ "unused", "null" })
	@PostMapping(WebConstantUrl.CREATE_QRCODE_BY_INVENTORY_ID)
	@ResponseBody
	public ResponseEntity<?> createQRCodeByInventoryId(@PathVariable(value = "id") Long id) throws WriterException, IOException {
		Inventory inventory = inventoryService.getInventoryById(id);

		if (inventory == null) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<AmcInventory> amcinventories = amcInventoryService.getAmcByAssetId(inventory.getAsset().getAssetId());
		
		String strs="Inventory Details,\n\n";
		strs+="Vertical type : "+inventory.getWebMaster().getWebMasterName()+"\n";
		strs+="Store Code : "+inventory.getStore().getStoreCode()+"\n";
		strs+="Asset Name : "+inventory.getAsset().getAssetName()+"\n";
		strs+="Asset Specification Name : "+inventory.getAsset().getAssetSpecification().getAssetSpecificationName()+"\n";
		strs+="Brand Name : "+inventory.getAsset().getModel().getBrand().getBrandName()+"\n";
		strs+="Model Name : "+inventory.getAsset().getModel().getModelName()+"\n";
		strs+="FAR No : "+inventory.getFarNo()+"\n";
		strs+="ER No : "+inventory.getErNo()+"\n";
		strs+="Quantity : "+inventory.getQuantity()+"\n";
		
		if(!amcinventories.isEmpty()) {
			
			strs+="Maintainance type : "+amcinventories.get(0).getMaintainanceType()+"\n";
			strs+="Maintainance From Date : "+amcinventories.get(0).getMaintainanceStartDate()+"\n";
			strs+="Maintainance To Date : "+amcinventories.get(0).getMaintainanceEndDate()+"\n";
			strs+="Number of Services : "+amcinventories.get(0).getNumberOfService()+"\n";
			strs+="Maintainance Gap(day) : "+amcinventories.get(0).getMinMaintainanceGap()+"\n";
			strs+="Maintainance Period(day) : "+amcinventories.get(0).getMaintainancePeriod()+"\n";
			strs+="Maintainance Validity : "+amcinventories.get(0).getMaintainanceValidity()+"\n";
			strs+="Contact Number : "+amcinventories.get(0).getContractNumber()+"\n";
			strs+="Description : "+amcinventories.get(0).getDescription()+"\n";
			
		}
		//strs+="Description : "+inventory.getDescription()!=null?"Description : ":""+"\n";
		else {
			if(inventory.getDescription()!=null) {
				strs+="Description : "+inventory.getDescription()+"\n";
			}else {
			strs+="Description : \n";
			}
		}
	
	//	String str="HAI MADHU";
		
		String extension=".png";
		String fileName=inventory.getErNo();
		
		File filePath = new File(getAbsolutePath() + File.separator + "/images" + File.separator + "QRImage");
		Path path = Paths.get(filePath.getAbsolutePath() + "/" + fileName+ extension , new String[0]);
		
		 if (!filePath.exists())
		      filePath.mkdirs();
		
		String pathtoString = path.toString();  
		//Encoding charset to be used  
		String charset = "UTF-8";  
		Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();  
		//generates QR code with Low level(L) error correction capability  
		hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
		//invoking the user-defined method that creates the QR code  
		generateQRcode(strs, pathtoString, charset, hashMap, 200, 200);//increase or decrease height and width accodingly   
		//prints if the QR code is generated   
		System.out.println("QR Code created successfully.");  
		 
		
		InventoryQRCodeUpload inventoryQRCodeUpload=new InventoryQRCodeUpload();
		
		InventoryQRCodeUpload	inventoryQRCodeUpload1=inventoryQRUploadRepo.findByInventoryId(inventory.getInventoryId());
		if(inventoryQRCodeUpload1==null) {
		inventoryQRCodeUpload.setInventory(inventory);
		inventoryQRCodeUpload.setQrFilePath(fileName+extension);
		inventoryQRCodeUpload.setEndingPath(extension);
		
		inventoryQRCodeUpload=inventoryQRUploadRepo.save(inventoryQRCodeUpload);
		}else {
			inventoryQRCodeUpload.setInventoryQRCodeUpload(inventoryQRCodeUpload1.getInventoryQRCodeUpload());
			inventoryQRCodeUpload.setInventory(inventory);
			inventoryQRCodeUpload.setQrFilePath(fileName+extension);
			inventoryQRCodeUpload.setEndingPath(extension);
			
			inventoryQRCodeUpload=inventoryQRServiceImpl.updateInventory(inventoryQRCodeUpload);
					
		
		}
		
		
	      Inventory inventory2=inventoryService.getInventoryById(inventoryQRCodeUpload.getInventory().getInventoryId());
	      if(inventoryQRCodeUpload.getQrFilePath()!=null) {
	    	  inventory2.setUpdatedOn(new Date());
	    	  inventory2.setQRCreated("true");
	    	  inventoryService.updateInventory1(inventory2);
	      }
		
	//	inventory = inventoryService.saveInventory(inventory);

		
		Map<String,Object> map=new HashMap<>();
		map.put("status_code",  HttpStatus.CREATED);
		map.put("success_msg", " QR Code generated Successfully. ");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("deprecation")
	public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException  
	{  
	//the BitMatrix class represents the 2D matrix of bits  
	//MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.  
	BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);  
	MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));  
	}  
	
	private String getAbsolutePath() {
	    try {
	      String imgDir = (new ApplicationHome(UserRoleServiceApplication.class)).getDir().getCanonicalPath();
	      return imgDir;
	    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    } 
	  }
	
	
	@GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {
	   
		InventoryQRCodeUpload inventoryQRCodeUpload= inventoryQRUploadRepo.findByInventoryId(id);
		 
		if(inventoryQRCodeUpload == null)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		String filename=inventoryQRCodeUpload.getQrFilePath();
		File filePath = new File(getAbsolutePath() + File.separator + "/images" + File.separator + "QRImage");
		Path path = Paths.get(filePath.getAbsolutePath() + "/" + filename , new String[0]);
		
		  Resource resource = null;
		    try {
		    	resource = new UrlResource(path.toUri());
		        if (!resource.exists()) {
					resource = new UrlResource(Paths.get(new File("images").toString()+"/"+"noimage.jpg").toUri());
					}
	              
		    	
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    }
		  	
	    if(inventoryQRCodeUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
	    }
	    else if(inventoryQRCodeUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/jpeg"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else if(inventoryQRCodeUpload.getEndingPath().equalsIgnoreCase(".png")) {
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/png"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	    else{
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("application/pdf"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		    }
	 
	 }

/*	
	@SuppressWarnings("deprecation")
	@GetMapping({"/download/{id}"})
	  public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) throws IOException, MessagingException{
	   
		InventoryQRCodeUpload inventoryQRCodeUpload= inventoryQRUploadRepo.findByInventoryId(id);
		
		if(inventoryQRCodeUpload == null)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		String filename=inventoryQRCodeUpload.getQrFilePath();
		File filePath = new File(getAbsolutePath() + File.separator + "/images" + File.separator + "QRImage");
		Path path = Paths.get(filePath.getAbsolutePath() + "/" + filename , new String[0]);
		
		String pathname=path.toString();
		File file = new File(pathname);
	    FileInputStream fis = new FileInputStream(file);
	    byte[] byteArray = new byte[(int)file.length()];
	    fis.read(byteArray);
	    fis.close();
	    String imageData = Base64.getEncoder().encodeToString(byteArray);
	    System.out.println(imageData);
	    
		String content="<!DOCTYPE html>"
				+ "<html>"+"<head>"
			+ "<style>" 
					+ "table {width:100%;font-family: arial, sans-serif;border-collapse: collapse;}" 
			+ ".redText {color: red;font-size:20px;font-weight:bold}"
		//	+ "th {border: 1px solid black;padding:5px;background-color: white;	color: red;}" 
			+ "td {border: 1px solid black;text-align: left;padding: 8px;}" 
			+ "tr:nth-child(even) {background-color: aqua!important;}"
			+ "h1 {font-size:20px;font-weigth:bold;text-align:center}"
			+ "th {padding: 8px;border: 1px solid black;background-color: white;color: red;align: center;}"
			+ "</style>" 
					+ "</head>"+ 
			"<body><table>"+"<tbody>"
			 +" <tr>"
			+"<td>Store Code</td>"
			+"<td>"+inventoryQRCodeUpload.getInventory().getStore().getStoreCode()+"</td>"
		//	  + "<th rowspan='3'>"+"<img src='E:\\Titan_IRSG_Workspace\\ApplicationService\\target\\classes\\images\\QRImage\\10101.png'/>"+"</th>"
		//	+ "<th rowspan='5'>"+"<img src="+"https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Titan_Company_Logo.png/60px-Titan_Company_Logo.png"+" alt='Image'"+"/>"
		//	+"<img src="+path+" alt='Image'"+" />"+"</th>"
			+"<th rowspan='5'><img src="+imageData+"/>" +"</th>"
			+  "</tr>"
			+ " <tr>"
			  +"<td>Asset Code</td>"
			+"<td>"+inventoryQRCodeUpload.getInventory().getAsset().getAssetCode()+"</td>"
			+" </tr>"
			+"<tr><td>Equipment Name</td>"
			+"<td>"+inventoryQRCodeUpload.getInventory().getAsset().getEquipment().getEquipmentName()+"</td>"
			+" </tr>"
			+"<tr><td>Er No</td>"
			+"<td>"+inventoryQRCodeUpload.getInventory().getErNo()+"</td>"
			+" </tr>"
			+"<tr><td>Installation Date</td>"
			+"<td>"+inventoryQRCodeUpload.getInventory().getInstallationDate()+"</td>"
			+" </tr>"
			+"</tbody>"
            + "</table>"
                + "</body>" + "</html>" + " ";
		
		
		 Resource resource = null;
		    try {
		    	resource = new UrlResource(path.toUri());
		   
		        if (!resource.exists()) {
					resource = new UrlResource(Paths.get(new File("images").toString()+"/"+"noimage.jpg").toUri());
					}
	              
		    	
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    }
		  
		    File filePath1 = new File(getAbsolutePath() + File.separator + "/QRHtmlToImage");
		    
    		Path path1 = Paths.get(filePath1.getAbsolutePath() + "/" + resource.getFilename() , new String[0]);
    		
    		 if (!filePath1.exists())
	  		      filePath1.mkdirs();
		   
    		
		    HtmlImageGenerator hig = new HtmlImageGenerator();
            hig.loadHtml(content);
            int height=4500,width=4500;
            Dimension dimension=new Dimension(width,height);
            hig.setSize(dimension);
            hig.saveAsImage(path1.toString());		
            
           
    		
    		 Resource resource1 = null;
 		    try {
 		    	resource1 = new UrlResource(path1.toUri());
 		   
 		        if (!resource1.exists()) {
 					resource1 = new UrlResource(Paths.get(new File("images").toString()+"/"+"noimage.jpg").toUri());
 					}
 	              
 		    	
 		    } catch (MalformedURLException e) {
 		      e.printStackTrace();
 		    }
            
		
	    if(inventoryQRCodeUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
	    return ResponseEntity.ok()
	      .contentType(MediaType.parseMediaType("image/jpg"))
	      
	      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource1);
	    }
	    else if(inventoryQRCodeUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
		    return ResponseEntity.ok() 
		      .contentType(MediaType.parseMediaType("image/jpeg"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource1);
		    }
	    else if(inventoryQRCodeUpload.getEndingPath().equalsIgnoreCase(".png")) {
	    	
	 		
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("image/png"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource1);
		    
		    }
	    else{
		    return ResponseEntity.ok()
		      .contentType(MediaType.parseMediaType("application/pdf"))
		      
		      .header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource1);
		    }
	 
	 }
	*/
	@GetMapping("/getQRCodeImageByInventoryId/{id}")
	public ResponseEntity<?> getQRCodeImageByInventoryId(@PathVariable Long id)
	{
		InventoryQRCodeUpload inventoryQRCodeUpload= inventoryQRUploadRepo.findByInventoryId(id);
		
				
		if(inventoryQRCodeUpload == null)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		InventoryQRCodeUploadVO inventoryQRCodeUploadVO=new InventoryQRCodeUploadVO();
		 BeanUtils.copyProperties(inventoryQRCodeUpload, inventoryQRCodeUploadVO);
		 
		 inventoryQRCodeUploadVO.setInventoryId(inventoryQRCodeUpload.getInventory().getInventoryId());
		 inventoryQRCodeUploadVO.setEndingPath(inventoryQRCodeUpload.getEndingPath());
		 inventoryQRCodeUploadVO.setInventoryQRCodeUpload(inventoryQRCodeUpload.getInventoryQRCodeUpload());
		 inventoryQRCodeUploadVO.setQrFilePath(inventoryQRCodeUpload.getQrFilePath());
		 
		 Inventory inventory2=inventoryService.getInventoryById(inventoryQRCodeUpload.getInventory().getInventoryId());
	     
		 inventoryQRCodeUploadVO.setQRCreated(inventory2.getQRCreated());
		
		 return new ResponseEntity<InventoryQRCodeUploadVO>(inventoryQRCodeUploadVO,HttpStatus.OK);
		//}
	//	return new ResponseEntity<ComplaintVO>(complaintMapper.getVOFromEntity(complaint),HttpStatus.OK);	
    }

}
