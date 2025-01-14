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
import com.titan.irgs.master.domain.AssetSpecification;
import com.titan.irgs.master.mapper.AssetSpecificationMapper;
import com.titan.irgs.master.service.IAssetSpecificationService;
import com.titan.irgs.master.vo.AssetSpecificationVo;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

@RestController
@RequestMapping(value = WebConstantUrl.ASSET_SPECIFICATION_BASE_URL)
public class AssetSpecificationController {
	

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAssetSpecificationService assetSpecificationService;
	
	@Autowired
	private AssetSpecificationMapper assetSpecificationMapper;
	@Autowired
	UserRepo userRepo;
	@Autowired
	WebMasterRepo webMasterRepo;
	
	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";
	
	@GetMapping(value = WebConstantUrl.GET_ALL_ASSET_SPEC)
	@ResponseBody
	public ResponseEntity<?> getAll(
			@RequestParam(required=false) String assetSpecipicationName,
			@RequestParam(required = false) String webMasterId,
			@RequestParam(required = false) String webMasterName,
			Pageable pageable,Principal principal)
	{
		Map<String,Object> map=new HashMap<>();

		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		User user = userRepo.findByUsername(principal.getName());


		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
			


		}

		
		List<AssetSpecificationVo> assetSpecificationVos = new ArrayList<AssetSpecificationVo>(0);
		
				
		Page<AssetSpecification> assetSpecifications = assetSpecificationService.getAllAssetSpecification(assetSpecipicationName, webMasterId, webMasterName,page);
		if(assetSpecifications.getContent().size() == 0) {
			map.put("assetSpecificationVos", assetSpecificationVos);
			map.put("total_pages", assetSpecifications.getTotalPages());
			map.put("status_code",  HttpStatus.NO_CONTENT);
			map.put("total_records", assetSpecifications.getTotalElements());
			return new ResponseEntity<>(map,HttpStatus.OK);
		} else {
			assetSpecifications.forEach(assetSpecification -> {
				assetSpecificationVos.add(assetSpecificationMapper.getVoFromEntity(assetSpecification));
			});
		}
		map.put("assetSpecificationVos", assetSpecificationVos);
		map.put("total_pages", assetSpecifications.getTotalPages());
		map.put("status_code",  HttpStatus.OK);
		map.put("total_records", assetSpecifications.getTotalElements());
		
		
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	
	@GetMapping(value = WebConstantUrl.GET_ASSET_SPEC_BY_ID)
	@ResponseBody
	public ResponseEntity<?> getAssetById(@PathVariable Long id)
	{
		AssetSpecification assetSpecification = assetSpecificationService.getAssetSpecificationById(id);
		if (assetSpecification == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(assetSpecificationMapper.getVoFromEntity(assetSpecification), HttpStatus.OK);
	}
	
	
	
	@PostMapping(WebConstantUrl.SAVE_ASSET_SPEC)
	@ResponseBody
	public ResponseEntity<?> save(@RequestBody AssetSpecificationVo assetSpecificationVo) {
		Map<String, Object> map = new HashMap<>();
		AssetSpecification assetSpecification = assetSpecificationMapper.getEntityFromVo(assetSpecificationVo);

		AssetSpecification AssetSpecification1 = assetSpecificationService.findByAssetSpecificationName(assetSpecificationVo.getAssetSpecificationName());
		
		
		if(AssetSpecification1 !=null) {
			
			map.put("status code", 400);
			map.put("client status", "Bad Request");
		    map.put("error msg","AssetSpecificationName with given No  is already present,Please change AssetSpecificationName  "+ AssetSpecification1.getAssetSpecificationName()+ ".");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
		
		assetSpecification = assetSpecificationService.save(assetSpecification);
		
		

		if (assetSpecification == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(assetSpecificationMapper.getVoFromEntity(assetSpecification),HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = WebConstantUrl.UPDATE_ASSET_SPEC)
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody AssetSpecificationVo assetSpecificationVo)
	{
		AssetSpecification assetSpecification = assetSpecificationMapper.getEntityFromVo(assetSpecificationVo);
		assetSpecification = assetSpecificationService.update(assetSpecification);
		
		return new ResponseEntity<>(assetSpecificationMapper.getVoFromEntity(assetSpecification), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_ASSET_BY_ID)
	@ResponseBody
	public void deleteAssetById(@PathVariable Long assetId)
	{
		assetSpecificationService.deleteById(assetId);
	}
	
	

	

	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_ASSET_EXCELSHEET)
	public ResponseEntity<byte[]> downloadSampleAssetCSV()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("Asset_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("ASSET_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	 @GetMapping("/exportExcel/{id}")
	    public void exportToExcel(HttpServletResponse response,@PathVariable("id") Long id) throws IOException {
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=AssetSpecification.xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	         MasterHeaders masterHeaders=new MasterHeaders();
	 		List<Object[]> assetSpecification = null;
			WebMaster name=webMasterRepo.findByWebMasterId(id);
			if(id !=18) {
				assetSpecification = assetSpecificationService.getAllForExcel(id);
			}else {
				assetSpecification = assetSpecificationService.getAllForExcel();}
	//        List<Object[]>assetSpecification=assetSpecificationService.getAllForExcel();
	        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.assetSpecification,assetSpecification);
	         
	        excelExporter.export(response);    
	    }  
	
		@GetMapping(value = WebConstantUrl.GET_ASSET_SPEC_BY_VERTICALID)
		@ResponseBody
		public List<AssetSpecification> getAssetSpecByVerticalId(@PathVariable("id") Long id) {
			return assetSpecificationService.getAssetSpecByVerticalId(id);
		}
	
	
	
	
	 
}
