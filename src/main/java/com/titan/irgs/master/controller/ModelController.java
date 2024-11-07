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
import com.titan.irgs.master.domain.Model;
import com.titan.irgs.master.mapper.ModelMappers;
import com.titan.irgs.master.service.IModelService;
import com.titan.irgs.master.vo.ModelVO;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * This is ModelController class which is responsible for handling all request(CRUD) for Model releted data
 * @author 
 *
 */
@RestController
@RequestMapping(value = WebConstantUrl.MODEL_BASE_URL)

public class ModelController {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IModelService modelService;
	
	@Autowired
	private ModelMappers modelMappers;
	@Autowired
	UserRepo userRepo;
	@Autowired
	WebMasterRepo webMasterRepo;
	
	private static final String superadmin = "super";

	private static final String MANAGEMENT = "MANAGEMENT";
	
	//:::::::::::::::::::Model CRUD operation
	
	@GetMapping(value = WebConstantUrl.GET_ALL_MODEL)
	@ResponseBody
	public ResponseEntity<?> getAllModel(
			@RequestParam(required=false) String modelName,
			@RequestParam(required=false) String modelNo,
			@RequestParam(required=false) String brandName,
			@RequestParam(required=false) String webMasterId,
			@RequestParam(required=false) String webMasterName,
			@RequestParam(required=false) String verticalName,
			Pageable pageable,Principal principal)
	{
		Map<String,Object> map = new HashMap<>();
		Pageable page=PageRequest.of(pageable.getPageNumber()==0?0:pageable.getPageNumber()-1, pageable.getPageSize());
		User user = userRepo.findByUsername(principal.getName());


		if( ( !user.getRole().getRole().getRoleName().equalsIgnoreCase(superadmin))
				&& (!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT))) {
			// store name and username are same
			
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
			


		}
		
		List<ModelVO> modelVos = new ArrayList<ModelVO>(0);
		Page<Model> models = modelService.getAllModel(modelName,modelNo,brandName,webMasterId,webMasterName,verticalName,page);
		if(models.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", models.getTotalElements());
			map.put("total_pages",models.getSize());
			map.put("brandVos", modelVos);
			
			return new ResponseEntity<>(map,HttpStatus.OK);
			
		} else {
			models.forEach(model -> {
				modelVos.add(modelMappers.getVoFromEntity(model));
			});
		}
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", models.getTotalElements());
		map.put("total_pages",models.getSize());
		map.put("brandVos", modelVos);
		
		return new ResponseEntity<>(map,HttpStatus.OK);	}
	
	@GetMapping(value = WebConstantUrl.GET_MODEL_BY_ID)
	@ResponseBody
	public ResponseEntity<ModelVO> getModelById(@PathVariable Long modelId)
	{
		Model model = modelService.getModelById(modelId);
		if (model == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ModelVO>(modelMappers.getVoFromEntity(model), HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.SAVE_MODEL)
	@ResponseBody
	public ResponseEntity<?> saveModel(@RequestBody ModelVO modelVo) {
		Model model = modelMappers.getEntityFromVo(modelVo);
		
		Map<String, Object> map = new HashMap<>();
		Model m = modelService.findByModelNoAndWebMaster(modelVo.getModelNo(),modelVo.getWebMasterId());
	   if(m != null) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
		    map.put("error msg","Model with given Model No  is already present,Please change Model No "+ m.getModelNo()+ ".");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			
		}

		model = modelService.saveModel(model);

		if (model == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<ModelVO>(modelMappers.getVoFromEntity(model),HttpStatus.CREATED);
	}
	
	@PutMapping(value = WebConstantUrl.UPDATE_MODEL)
	@ResponseBody
	public ResponseEntity<ModelVO> updateModel(@RequestBody ModelVO modelVo)
	{
		Model model = modelMappers.getEntityFromVo(modelVo);
		model = modelService.updateModel(model);
		if (model == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ModelVO>(modelMappers.getVoFromEntity(model), HttpStatus.OK);
	}
	
	@DeleteMapping(value = WebConstantUrl.DELETE_MODEL_BY_ID)
	@ResponseBody
	public void deleteModelById(@PathVariable Long modelId)
	{
		modelService.deleteModelById(modelId);
	}
	
	
	@GetMapping(value = WebConstantUrl.GET_ALL_MODEL_ON_BRAND_ID)
	@ResponseBody
	public ResponseEntity<List<ModelVO>> getAllModelOnBrandId(@PathVariable(value="brandId") Long brandId)
	{
		List<ModelVO> modelsVo=new ArrayList<ModelVO>();
		List<Model> models=modelService.getAllModelOnBrandId(brandId);
		
		try {
			if(brandId == null) {
				return new ResponseEntity<List<ModelVO>>(HttpStatus.NOT_FOUND);
			}
			if (models == null || models.isEmpty()) {
				return new ResponseEntity<List<ModelVO>>(HttpStatus.NO_CONTENT);

			}
			models.forEach(model ->{
				modelsVo.add(modelMappers.getVoFromEntity(model));
			});
			return new ResponseEntity<List<ModelVO>>(modelsVo,HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<List<ModelVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	
		
	}
	

	@GetMapping(value=WebConstantUrl.DOWNLOAD_SAMPLE_MODEL_EXCELSHEET)
	public ResponseEntity<byte[]>  downloadsampleExcelSheet()
	{
		File resource = null;
		try
		{
			resource = new File(getClass().getClassLoader().getResource("Model_ExcelSheet_sample.xlsx").getFile());
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData("Content-Disposition",resource.getName());
			responseHeaders.setContentType(new MediaType("text", "xlsx", Charset.forName("utf-8")));
			return new ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("MODEL_XLSX_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e);
		} 
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	 @GetMapping("/exportExcel/{id}")
	    public void exportToExcel(HttpServletResponse response,@PathVariable("id")Long id) throws IOException {
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=model.xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	         MasterHeaders masterHeaders=new MasterHeaders();
	 		List<Object[]> brands = null;
			WebMaster name=webMasterRepo.findByWebMasterId(id);
			if(id !=18) {
			 brands = modelService.getAllForExcel(id);
			}else {
				 brands = modelService.getAllForExcel();
			}
	      
	        ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.models,brands);
	         
	        excelExporter.export(response);    
	    }  

		@GetMapping(value = WebConstantUrl.GET_MODEL_BY_VERTICALID)
		public List<Model> getModelByVerticalId(@PathVariable("id") Long id) {
			return modelService.getModelByVerticalId(id);
		}
	
}
