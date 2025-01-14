package com.titan.irgs.master.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.inventory.service.IInventoryService;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.SerialNumber;
import com.titan.irgs.master.mapper.AssetMapper;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.repository.SerialNumberRepository;
import com.titan.irgs.master.service.IAssetService;
import com.titan.irgs.master.vo.AssetCountForDashboardVO;
import com.titan.irgs.master.vo.AssetTotalCountForDashboardVO;
import com.titan.irgs.master.vo.AssetUnAssignedCountForDashboardVO;
import com.titan.irgs.master.vo.AssetVO;
import com.titan.irgs.master.vo.AssignedAssetCountForDashboardVO;
import com.titan.irgs.master.vo.DashboardVO;
import com.titan.irgs.master.vo.ScrappedAssetCountForDashboardVO;
import com.titan.irgs.serviceRequest.domain.ServiceRequestCountDto;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * This is AssetController class which is responsible for handling all
 * request(CRUD) for Asset releted data
 * 
 * @author
 *
 */
@RestController
@RequestMapping(value = WebConstantUrl.ASSET_BASE_URL)

public class AssetController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IAssetService assetService;
	@Autowired
	WebMasterRepo webMasterRepo;
	@Autowired
	private AssetMapper assetMapper;

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	UserRepo userRepo;

	@Autowired
	private IInventoryService iInventoryService;

	@Autowired
	private SerialNumberRepository serialNumberRepository;

	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";
	
	private static final String MANAGER = "MANAGER";
	private static final String HEAD = "HEAD";
	private static final String VENDOR = "Vendor";
	// :::::::::::::::::::Asset CRUD operation

	@GetMapping(value = WebConstantUrl.GET_ALL_ASSET)
	@ResponseBody
	public ResponseEntity<?> getAllAsset(@RequestParam(required = false) String assetName,
			@RequestParam(required = false) String assetCode, @RequestParam(required = false) String fARNo,
			@RequestParam(required = false) String equipmentName, @RequestParam(required = false) String modelName,
			@RequestParam(required = false) String brandName,
			@RequestParam(required = false) String businessVerticalType,
			@RequestParam(required = false) String departmentName, @RequestParam(required = false) String amcStatus,
			Pageable pageable, Principal principal) {

		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();

		User user = userRepo.findByUsername(principal.getName());
		List<AssetVO> assetVos = new ArrayList<AssetVO>(0);
		// setting bussiness verticle with respective login user
		String user1=null;
		String department=null;
		String vendorCode=null;
		
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if( (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)) && (!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD)) ) {
				user1 = user.getUsername();
			}else {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}
		
		if (user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)) {
			vendorCode = user.getUsername();
			user1=null;
		 }

		/*
		 * if(user.getRoleWiseDepartments()!=null){ departmentName =
		 * user.getRoleWiseDepartments().getDepartment().getDepartmentName(); }
		 */
		Page<Asset> assets = assetService.getAllAsset(assetName, assetCode, fARNo, equipmentName, modelName, brandName,
				businessVerticalType, departmentName, amcStatus,department,vendorCode, page);
		if (assets.getContent().size() == 0) {
			map.put("assetVos", assetVos);
			map.put("total_pages", assets.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", assets.getTotalElements());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			assets.forEach(asset -> {
				assetVos.add(assetMapper.getVoFromEntity(asset));
			});
		}
		map.put("assetVos", assetVos);
		map.put("total_pages", assets.getTotalPages());
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", assets.getTotalElements());

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GET_ALL_ASSET_EXCEPT_ALREADYCREATED)
	@ResponseBody
	public ResponseEntity<?> getAllAssetExceptAlreadyCreated(@RequestParam(required = false) String assetName,
			@RequestParam(required = false) String assetCode, @RequestParam(required = false) String fARNo,
			@RequestParam(required = false) String equipmentName, @RequestParam(required = false) String modelName,
			@RequestParam(required = false) String brandName,
			@RequestParam(required = false) String businessVerticalType, Pageable pageable, Principal principal) {

		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());
		Map<String, Object> map = new HashMap<>();

		User user = userRepo.findByUsername(principal.getName());
		List<AssetVO> assetVos = new ArrayList<AssetVO>(0);
		// setting bussiness verticle with respective login user

		if (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)
				&& !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			businessVerticalType = user.getRole().getWebMaster().getWebMasterName();

		}
		Page<Asset> assets = null;
		// Page<Asset> assets =
		// assetService.getAllAssetExceptAlreadyCreated1(assetName,assetCode,fARNo,equipmentName,modelName,brandName,businessVerticalType,page);
		if (assetName != null) {
			assets = assetRepository.getAllAssetnotCreated1(assetName, page);

		} else if (assetCode != null) {
			assets = assetRepository.getAllAssetnotCreated2(assetCode, page);
		} else {
			assets = assetRepository.getAllAssetnotCreated1(page);
		}

		if (assets.getContent().size() == 0) {
			map.put("assetVos", assetVos);
			map.put("total_pages", assets.getTotalPages());
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", assets.getTotalElements());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			assets.forEach(asset -> {
				assetVos.add(assetMapper.getVoFromEntity(asset));
			});
		}
		map.put("assetVos", assetVos);
		map.put("total_pages", assets.getTotalPages());
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", assets.getTotalElements());

		/*
		 * List<Asset> assets =
		 * assetService.getAllAssetExceptAlreadyCreated(assetName,assetCode,fARNo,
		 * equipmentName,modelName,brandName,businessVerticalType,page);
		 * 
		 * Long count =
		 * assetService.count(assetName,assetCode,fARNo,equipmentName,modelName,
		 * brandName,businessVerticalType); if(assets.size() == 0) { map.put("assetVos",
		 * assetVos); map.put("total_pages", 0); map.put("status_code",
		 * HttpStatus.NO_CONTENT); map.put("total_records", count); return new
		 * ResponseEntity<>(map,HttpStatus.OK); } else { assets.forEach(asset -> {
		 * assetVos.add(assetMapper.getVoFromEntity(asset)); }); } int pages = (int)
		 * Math.ceil((double) count / (double) pageable.getPageSize());
		 * 
		 * map.put("assetVos", assetVos); map.put("total_pages", pages);
		 * map.put("status_code", HttpStatus.OK); map.put("total_records", count);
		 * 
		 */

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GET_ASSET_BY_ID)
	@ResponseBody
	public ResponseEntity<AssetVO> getAssetById(@PathVariable Long assetId) {
		Asset asset = assetService.getAssetById(assetId);
		if (asset == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<AssetVO>(assetMapper.getVoFromEntity(asset), HttpStatus.OK);
	}

	@PostMapping(WebConstantUrl.SAVE_ASSET)
	@ResponseBody
	public ResponseEntity<?> saveAsset(@RequestBody AssetVO assetVo) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Asset asset = assetMapper.getEntityFromVo(assetVo);

		// Asset asset1=null;
		/*
		 * Asset a = assetService.findByAssetCode(assetVo.getAssetCode());
		 * 
		 * if(a !=null) {
		 * 
		 * map.put("status code", 400); map.put("client status", "Bad Request");
		 * map.put("error msg"
		 * ,"Asset with given Asset Code  is already present,Please change Asset Code "+
		 * a.getAssetCode()+ "."); return new ResponseEntity<Map<String, Object>>(map,
		 * HttpStatus.BAD_REQUEST); }
		 */
		Asset farNo = assetRepository.findByFarNo(assetVo.getFarNo());

		if (farNo != null) {

			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg",
					"FarNo with given No is already present,Please change FAR_NO  " + assetVo.getFarNo() + ".");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		} else {

			asset = assetService.saveAsset(asset);
			if (!assetVo.getSerialNumbers().isEmpty()) {
				//String[] values = assetVo.getSerialNumbers().split(",\\s*");

				 List<String> stringList = Arrays.asList(assetVo.getSerialNumbers().split(",\\s*"));
				 String[] values = assetVo.getSerialNumbers().split(",\\s*");
					if(asset.getOriginalQty()!=values.length) {
						throw new ResourceAlreadyExitException(asset.getOriginalQty()+" SerialNumbers must be add");
					}
					else {
				    List<String> dps = stringList.stream().distinct().filter(entry -> Collections.frequency(stringList, entry) > 1).collect(Collectors.toList());
				    
				    if(!dps.isEmpty()) {
				    	throw new Exception("Duplicate Serial Numbers present");
				    }
				    
				for (String value : stringList) {
					SerialNumber serialNumber = new SerialNumber();

					serialNumber.setAsset(asset);
					serialNumber.setSerialNumber(value);
					serialNumberRepository.save(serialNumber);
				}
				}
			}

			return new ResponseEntity<AssetVO>(assetMapper.getVoFromEntity(asset), HttpStatus.CREATED);
		}

		// Asset farNo = assetRepository.findByFarNo(assetVo.getFarNo());
		/*
		 * Commeneted by madhu if(farNo !=null) {
		 * 
		 * map.put("status code", 400); map.put("client status", "Bad Request");
		 * map.put("error msg"
		 * ,"FarNo with given No  is already present,Please change FAR_NO  "+
		 * assetVo.getFarNo()+ "."); return new ResponseEntity<Map<String, Object>>(map,
		 * HttpStatus.BAD_REQUEST); }
		 * 
		 * 
		 * if (asset == null) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
		 * 
		 * if(a == null) {
		 * 
		 * //asset1 =
		 * assetRepository.findByAssetCode(assetVo.getAssetCode()+"/"+assetVo.getItemNo(
		 * )); asset1 = assetRepository.findByAssetCode(assetVo.getAssetCode()); }
		 * 
		 * 
		 * 
		 * if(a != null) {
		 * 
		 * if(assetVo.getItemNo() != null) { // asset =
		 * assetRepository.findByAssetCode(assetVo.getAssetCode()+"/"+assetVo.getItemNo(
		 * )); asset = assetRepository.findByAssetCode(assetVo.getAssetCode()); } }
		 * 
		 * if(asset1 != null) {
		 * 
		 * if(assetVo.getItemNo() != null) { //asset1 =
		 * assetRepository.findByAssetCode(assetVo.getAssetCode()+"/"+assetVo.getItemNo(
		 * )); asset1 = assetRepository.findByAssetCode(assetVo.getAssetCode()); } }
		 * 
		 * 
		 * if(asset1 !=null) {
		 * 
		 * map.put("status code", 400); map.put("client status", "Bad Request");
		 * map.put("error msg"
		 * ,"Asset with given Asset Code  is already present,Please change Asset Code "+
		 * asset1.getAssetCode()+ "."); return new ResponseEntity<Map<String,
		 * Object>>(map, HttpStatus.BAD_REQUEST); }
		 * 
		 */

	}

	@PutMapping(value = WebConstantUrl.UPDATE_ASSET)
	@ResponseBody
	public ResponseEntity<?> updateAsset(@RequestBody AssetVO assetVo) throws Exception {
		Asset asset = assetMapper.getEntityFromVo(assetVo);
		Map<String, Object> map = new HashMap<>();

		/*
		 * if(assetVo.getItemNo() != null) {
		 * 
		 * asset.setAssetCode(assetVo.getAssetCode()+"/"+assetVo.getItemNo()); }
		 */
		Asset a1 = assetRepository.findById(assetVo.getAssetId()).get();
		Asset a = assetRepository.findByAssetCode(assetVo.getAssetCode());

		if (asset == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		if (a == null || a.equals("")) {
			asset = assetService.updateAsset(asset);
			assetService.updateSerialNumbers(asset, assetVo.getSerialNumbers());

			return new ResponseEntity<AssetVO>(assetMapper.getVoFromEntity(asset), HttpStatus.OK);

		} else {
			if (a.getAssetCode().equalsIgnoreCase(a1.getAssetCode())) {
				asset = assetService.updateAsset(asset);
				assetService.updateSerialNumbers(asset, assetVo.getSerialNumbers());

				return new ResponseEntity<AssetVO>(assetMapper.getVoFromEntity(asset), HttpStatus.OK);
			} else {
				map.put("status code", 400);
				map.put("client status", "Bad Request");
				map.put("error msg", "Asset with given Asset Code  is already present,Please change Asset Code "
						+ a.getAssetCode() + ".");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			}

		}

		/*
		 * Inventory
		 * inventoryDetails=inventoryRepository.getOne(inventory.getInventoryId());
		 * List<AmcInventory>
		 * amcInventories2=amcInventoryRepository.getAmcByInventoryId(inventoryDetails.
		 * getInventoryId()); if(amcInventories2!=null) { if
		 * (inventory.getVendor().getVendorId()!=inventoryDetails.getVendor().
		 * getVendorId()) { List<AmcInventory>
		 * amcInventories1=amcInventoryRepository.getAmcByInventoryId(inventoryDetails.
		 * getInventoryId());
		 * 
		 * List<Object[]> amcInventoryList
		 * =amcInventoryRepository.VendorChange(amcInventories1.get(0).getAmcId()); for
		 * (Object[] rows : amcInventoryList) {
		 * 
		 * /* srNotificationEmail_id:- System.out.println(rows[0]); activity_Name:-
		 * System.out.println(rows[1]); service_request_id:-
		 * System.out.println(rows[2]); TOemail:- System.out.println(rows[3]); CCEmail:-
		 * System.out.println(rows[4]);
		 */
		/*
		 * AmcInventory amcInventories = amcInventoryService.findById(((BigInteger)
		 * rows[2]).longValue()); String mailSubjects = ""; String emailCcValue=(String)
		 * rows[4]; List<String> emailCc =
		 * Arrays.asList(emailCcValue.split(",")).stream().filter(str->!str.isEmpty()).
		 * collect(Collectors.toList()); System.out.println(emailCc); String
		 * emailTOValue=(String) rows[3]; List<String> emailTo =
		 * Arrays.asList(emailTOValue.split(",")).stream().filter(str->!str.isEmpty()).
		 * collect(Collectors.toList()); System.out.println(emailTo);
		 * 
		 * 
		 * Mail mail = amcInventoryService.templeteMail(amcInventories); Long id=
		 * amcInventories.getWebMaster().getWebMasterId(); mailSubjects =
		 * "Nu-Nxtwav  "+"– Vendor Changed"; mail.setMailTo(emailTo);
		 * mail.setMailCC(emailCc); mail.setMailSubject(mailSubjects); if (mailStatus) {
		 * try { emailServiceImpl.sendMultiPartEmail(mail); } catch (MessagingException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * 
		 * }
		 * 
		 * System.out.println("the task is completed"); }
		 * 
		 * 
		 * if(inventoryVo.getVendorId()!=inventoryDetails.getVendor().getVendorId()) {
		 * 
		 * List<AmcWarranty>
		 * amcWarranty=amcWarrantyRepository.FindByInventoryId(inventoryDetails.
		 * getInventoryId()); List<AmcInventory>
		 * amcInventories=amcInventoryRepository.getAmcByInventoryId(inventoryDetails.
		 * getInventoryId()); for (AmcInventory amcInventory2 : amcInventories) {
		 * AmcInventory
		 * amcInventory=amcInventoryRepository.findById(amcInventory2.getAmcId()).get();
		 * Vendor vendor=vendorRepository.findById(inventoryVo.getVendorId()).get();
		 * amcInventory.setVendor(vendor); amcInventoryRepository.save(amcInventory); }
		 * for (AmcWarranty amcWarranty2 : amcWarranty) { LocalDate to =
		 * LocalDate.now(); LocalDate from = amcWarranty2.getWarrantyFrom(); long result
		 * = ChronoUnit.DAYS.between(from, to); if(result>0){
		 * 
		 * if(maintainanceRepository.getMaintainanceByWarranyId(amcWarranty2.
		 * getWarrantyId())==null){
		 * 
		 * List<Long>
		 * warrantyIds=maintainanceRepository.getWarrantyNotOpen(inventoryDetails.
		 * getInventoryId()); for (Long id : warrantyIds) { AmcWarranty
		 * amcWarranty1=amcWarrantyRepository.findById(id).get();
		 * amcWarranty1.setVendorId(inventoryVo.getVendorId());
		 * amcWarrantyRepository.save(amcWarranty1); }
		 * 
		 * 
		 * } else { List<Long>
		 * warrantyIds=maintainanceRepository.getWarrantyNotOpen(inventoryDetails.
		 * getInventoryId()); for (Long id : warrantyIds) { AmcWarranty
		 * amcWarranty1=amcWarrantyRepository.findById(id).get();
		 * amcWarranty1.setVendorId(inventoryVo.getVendorId());
		 * amcWarrantyRepository.save(amcWarranty1);} Inventory
		 * inventoryupdate=inventoryRepository.findById(inventoryDetails.getInventoryId(
		 * )).get(); Vendor
		 * vendor=vendorRepository.findById(inventoryVo.getVendorId()).get();
		 * inventoryupdate.setVendor(vendor); inventoryRepository.save(inventoryupdate);
		 * throw new
		 * ResourceAlreadyExitException("Already Service is Created,except that service Remaining Services are Changed"
		 * ); }} else { List<Long>
		 * warrantyIds=maintainanceRepository.getWarrantyNotOpen(inventoryDetails.
		 * getInventoryId()); for (Long id : warrantyIds) { AmcWarranty
		 * amcWarranty1=amcWarrantyRepository.findById(id).get();
		 * amcWarranty1.setVendorId(inventoryVo.getVendorId());
		 * amcWarrantyRepository.save(amcWarranty1); } } }
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 */

	}

	@DeleteMapping(value = WebConstantUrl.DELETE_ASSET_BY_ID)
	@ResponseBody
	public void deleteAssetById(@PathVariable Long assetId) {

		assetService.deleteAssetById(assetId);

	}

	@GetMapping(WebConstantUrl.GET_BY_AMC_STATUS)
	public ResponseEntity<List<Asset>> getByAmcStatus(@RequestParam boolean amcStatus) {
		return new ResponseEntity<List<Asset>>(assetService.findByAmcStatus(amcStatus), HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GET_ALL_ASSET_ON_MODEL_ID)
	@ResponseBody
	public ResponseEntity<List<AssetVO>> getAllAssetOnModelId(@PathVariable(value = "modelId") Long modelId) {
		List<AssetVO> assetsVo = new ArrayList<AssetVO>();
		List<Asset> assets = assetService.getAllAssetOnModelId(modelId);

		try {
			if (modelId == null) {
				return new ResponseEntity<List<AssetVO>>(HttpStatus.NOT_FOUND);
			}
			if (assets == null || assets.isEmpty()) {
				return new ResponseEntity<List<AssetVO>>(HttpStatus.NO_CONTENT);

			}
			assets.forEach(asset -> {
				assetsVo.add(assetMapper.getVoFromEntity(asset));
			});
			return new ResponseEntity<List<AssetVO>>(assetsVo, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<AssetVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = WebConstantUrl.DOWNLOAD_SAMPLE_ASSET_EXCELSHEET)
	public ResponseEntity<byte[]> downloadSampleAssetCSV() {
		File resource = null;
		try {
			resource = new File(getClass().getClassLoader().getResource("Asset_ExcelSheet_sample.xlsx").getFile());

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition", resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource), responseHeaders, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ASSET_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ", e);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/exportExcel/{id}")
	public void exportToExcel(HttpServletResponse response, @PathVariable("id") Long id,Principal principal) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=asset.xlsx";
		response.setHeader(headerKey, headerValue);

		MasterHeaders masterHeaders = new MasterHeaders();
		// List<Object[]> assets=assetService.getAllCitysForExcel();
		List<Object[]> assets = null;
		WebMaster name = webMasterRepo.findByWebMasterId(id);
		User user=userRepo.findByUsername(principal.getName());
		String businessVerticalType=null;
		String department=null;
		String user1=null;
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if(user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)) {
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
		
		if (businessVerticalType!=null && department!=null) {
			assets = assetService.getAllCitysForExcel(businessVerticalType,department);
		} else if(businessVerticalType!=null){
			assets = assetService.getAllCitysForExcel(businessVerticalType);
		}else {
			assets = assetService.getAllCitysForExcel();
		}
		
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.asset, assets);

		excelExporter.export(response);
	}

	@GetMapping(value = WebConstantUrl.GET_ASSETS_BY_VERTICALID_AND_DEPARTMENT_ID)
	@ResponseBody
	public List<AssetVO> getAssetsByVerticalIdAndDepartmentId(@RequestParam(required = true) Long verticalId,
			@RequestParam(required = true) Long departmentId) {
		List<Object[]> assets = assetService.getAssetsByVerticalIdAndDepartmentId(verticalId, departmentId);

		List<AssetVO> assetVOs = new ArrayList<AssetVO>();
		for (Object[] asset : assets) {
			String assetcode = (String) asset[0];
			String assetname = (String) asset[1];
			BigInteger assetId = (BigInteger) asset[2];
			BigInteger department = (BigInteger) asset[3];
			String departmentname = (String) asset[4];
			BigInteger webmasterid = (BigInteger) asset[5];
			String webmastername = (String) asset[6];

			AssetVO assetVO = new AssetVO();
			assetVO.setAssetCode(assetcode);
			assetVO.setAssetName(assetname);
			assetVO.setAssetId(assetId.longValue());
			assetVO.setDepartmentId(department.longValue());
			assetVO.setDepartmentName(departmentname);
			assetVO.setWebMasterId(webmasterid.longValue());
			assetVO.setWebMasterName(webmastername);
			assetVOs.add(assetVO);
		}
		return assetVOs;
	}

    private void appendListToStringBuilder(List<Object[]> list, StringBuilder sb) {
        for (Object[] row : list) {
            sb.append(Arrays.toString(row)).append(" ");
        }
    }
    
	@PostMapping(value = WebConstantUrl.GET_ASSET_COUNT_FOR_DASHBOARD)
	@ResponseBody
	public ResponseEntity<?> getAssetCountForDashboard(@RequestBody(required = false) DashboardVO dashboardVO,
			Principal principal) {

		Map<String, Object> map = new HashMap<>();

		String businessVerticalType = null;

		User user = userRepo.findByUsername(principal.getName());

		String user1=null;
		String department=null;
		String vendorCode=null;
		// setting bussiness verticle with respective login user
		if ( !user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT) ) {

			if (user.getRole().getRole().getRoleName().contains(MANAGER) || user.getRole().getRole().getRoleName().contains(HEAD) ) {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				department=user.getRoleWiseDepartments().getDepartment().getDepartmentName();
			} else if( (!user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin)) && (!user.getRole().getRole().getRoleName().contains(MANAGER) || !user.getRole().getRole().getRoleName().contains(HEAD)) ) {
				user1 = user.getUsername();
			}else {
				businessVerticalType = user.getRole().getWebMaster().getWebMasterName();
				
			}
		}

		if (user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			businessVerticalType = dashboardVO.getWebMasterName();

		}

		if(user.getRole().getRole().getRoleName().equalsIgnoreCase(VENDOR)){			
			//store name and username are same
			vendorCode=user.getUsername();

		}

		/*
		 * if(user.getRoleWiseDepartments()!=null){ departmentName =
		 * user.getRoleWiseDepartments().getDepartment().getDepartmentName(); }
		 */
		List<Object[]> assets = assetService.getAssetCountForDashboard(businessVerticalType,department,user1,vendorCode);
		List<Object[]> inventories = iInventoryService.getInventoryCountForAssignedAssetList(businessVerticalType,department,user1);
		List<Object[]> unassignedassets = assetService.getUnassignedAssetCountForDashboard(businessVerticalType,department,user1);
		List<Object[]> scrappedAssets = assetService.getScrappedAssetCountForDashboard(businessVerticalType,department,user1);
		
 
        
		
		List<AssetTotalCountForDashboardVO> assetCountForDashboardVOs = new ArrayList<AssetTotalCountForDashboardVO>();

		if (assets == null || assets.isEmpty()) {
			return new ResponseEntity<List<ServiceRequestCountDto>>(HttpStatus.NO_CONTENT);

		}

		AssetCountForDashboardVO assetCountForDashboardVO = new AssetCountForDashboardVO();
		AssignedAssetCountForDashboardVO assignedAssetCountForDashboardVO = new AssignedAssetCountForDashboardVO();
		AssetUnAssignedCountForDashboardVO assetUnAssignedCountForDashboardVO = new AssetUnAssignedCountForDashboardVO();
		ScrappedAssetCountForDashboardVO scrappedAssetCountForDashboardVO = new ScrappedAssetCountForDashboardVO();

		List<Long> totalAssets = new ArrayList<>();
		List<String> webmasternames = new ArrayList<>();
		List<String> departmentNames = new ArrayList<>();
		for (Object[] asset : assets) {
			Long totalassets = 0L;
			if (asset[0] != null) {
				BigInteger totalasset = (BigInteger) asset[0];
				totalassets = totalasset.longValue();
			} else {
				totalassets = 0L;
			}

			String webmastername = "";

			if (asset[2] != null) {
				webmastername = (String) asset[2];
			}

			String departmentName = "";

			if (asset[4] != null) {
				departmentName = (String) asset[4];
			}
			
			totalAssets.add(totalassets);
			webmasternames.add(webmastername);
			departmentNames.add(departmentName);

		}
		assetCountForDashboardVO.setTotalAssets(totalAssets);
		assetCountForDashboardVO.setVertical(webmasternames);
		assetCountForDashboardVO.setDepartmentName(departmentNames);
		AssetTotalCountForDashboardVO assetTotalCountForDashboardVO = new AssetTotalCountForDashboardVO();

		assetTotalCountForDashboardVO.setAssetCountForDashboardVO(assetCountForDashboardVO);

		List<Long> asignedassetss = new ArrayList<>();
		List<String> webmasternamess = new ArrayList<>();
		List<String> departmentNamess = new ArrayList<>();
		for (Object[] inventory : inventories) {
			Long asignedassets = 0L;
			if (inventory[0] != null) {
				Integer totalasset = (Integer) inventory[0];
				asignedassets = totalasset.longValue();
			} else {
				asignedassets = 0L;
			}

			String webmastername = "";

			if (inventory[2] != null) {
				webmastername = (String) inventory[2];
			}

			String departmentName = "";

			if (inventory[4] != null) {
				departmentName = (String) inventory[4];
			}

			asignedassetss.add(asignedassets);
			webmasternamess.add(webmastername);
			departmentNamess.add(departmentName);

		}
		assignedAssetCountForDashboardVO.setAssignedAssets(asignedassetss);
		assignedAssetCountForDashboardVO.setDepartmentName(departmentNamess);
		assignedAssetCountForDashboardVO.setVertical(webmasternamess);

		assetTotalCountForDashboardVO.setAssignedAssetCountForDashboardVO(assignedAssetCountForDashboardVO);

		List<Long> unassignedAssets = new ArrayList<>();
		List<String> Webmasternames = new ArrayList<>();
		List<String> DepartmentNames = new ArrayList<>();
		for (Object[] asset : unassignedassets) {
			Long totalassets = 0L;
			if (asset[4] != null) {
				BigInteger totalasset = (BigInteger) asset[4];
				totalassets = totalasset.longValue();
			} else {
				totalassets = 0L;
			}

			String webmastername = "";

			if (asset[1] != null) {
				webmastername = (String) asset[1];
			}

			String departmentName = "";

			if (asset[3] != null) {
				departmentName = (String) asset[3];
			}

			unassignedAssets.add(totalassets);
			Webmasternames.add(webmastername);
			DepartmentNames.add(departmentName);

		}
		
		assetUnAssignedCountForDashboardVO.setUnAssignedAssets(unassignedAssets);
		assetUnAssignedCountForDashboardVO.setVertical(Webmasternames);
		assetUnAssignedCountForDashboardVO.setDepartmentName(DepartmentNames);

		assetTotalCountForDashboardVO.setAssetUnAssignedCountForDashboardVO(assetUnAssignedCountForDashboardVO);

		List<Long> scrappedAssetss = new ArrayList<>();
		List<String> Webmasternamess = new ArrayList<>();
		List<String> DepartmentNamess = new ArrayList<>();
		for (Object[] asset : scrappedAssets) {
			Long totalassets = 0L;
			if (asset[0] != null) {
				Integer totalasset = (Integer) asset[0];
				totalassets = totalasset.longValue();
			} else {
				totalassets = 0L;
			}

			String webmastername = "";

			if (asset[3] != null) {
				webmastername = (String) asset[3];
			}

			String departmentName = "";

			if (asset[2] != null) {
				departmentName = (String) asset[2];
			}

			scrappedAssetss.add(totalassets);
			Webmasternamess.add(webmastername);
			DepartmentNamess.add(departmentName);

		}
		scrappedAssetCountForDashboardVO.setScrapedAssets(scrappedAssetss);
		scrappedAssetCountForDashboardVO.setDepartmentName(DepartmentNamess);
		scrappedAssetCountForDashboardVO.setVertical(Webmasternamess);

		assetTotalCountForDashboardVO.setScrappedAssetCountForDashboardVO(scrappedAssetCountForDashboardVO);

		assetCountForDashboardVOs.add(assetTotalCountForDashboardVO);

		/*
		 * Long totalAssetCount = 0L; for (Asset asset : assets) {
		 * totalAssetCount+=asset.getOriginalQty();
		 * 
		 * AssetCountForDashboardVO assetCountForDashboardVO1=new
		 * AssetCountForDashboardVO();
		 * assetCountForDashboardVO1.setVertical(asset.getWebMaster().getWebMasterName()
		 * );
		 * 
		 * assetCountForDashboardVOs.add(assetCountForDashboardVO1); }
		 * assetCountForDashboardVO.setTotalAssets(totalAssetCount);
		 * assetCountForDashboardVO.setAssignedAssets((long)inventories.size());
		 * assetCountForDashboardVO.setUnAssignedAssets(totalAssetCount-((long)
		 * inventories.size()));
		 * 
		 * assetCountForDashboardVOs.add(assetCountForDashboardVO);
		 */
		return new ResponseEntity<>(assetCountForDashboardVOs, HttpStatus.OK);
	}

}
