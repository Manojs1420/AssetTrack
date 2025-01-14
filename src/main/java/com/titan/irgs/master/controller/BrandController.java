package com.titan.irgs.master.controller;

import java.io.IOException;
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
import org.springframework.http.HttpStatus;
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

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.ExcelExporter;
import com.titan.irgs.application.util.MasterHeaders;
import com.titan.irgs.master.domain.Brand;
import com.titan.irgs.master.mapper.BrandMapper;
import com.titan.irgs.master.repository.BrandRepository;
import com.titan.irgs.master.service.IBrandService;
import com.titan.irgs.master.vo.BrandVO;
import com.titan.irgs.role.repository.RoleRepository;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;

/**
 * This is BrandController class which is responsible for handling all
 * request(CRUD) for Brand releted data
 * 
 * @author
 *
 */
@RestController
@RequestMapping(value = WebConstantUrl.BRAND_BASE_URL)
public class BrandController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IBrandService brandService;

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	UserRepo userRepo;
	@Autowired
	WebMasterRepo webMasterRepo;
	@Autowired
	RoleRepository roleRepository;

	// :::::::::::::::::::Brand CRUD operation
	private static final String superadmin = "superadmin";

	private static final String MANAGEMENT = "MANAGEMENT";

	@GetMapping(value = WebConstantUrl.GET_ALL_BRAND)
	@ResponseBody
	public ResponseEntity<?> getAllBrand(@RequestParam(required = false) String brandName,
			@RequestParam(required = false) String brandCode, @RequestParam(required = false) String webMasterId,
			@RequestParam(required = false) String webMasterName,
			@RequestParam(required=false) String verticalName,Pageable pageable, Principal principal) {
		Map<String, Object> map = new HashMap<>();
		Pageable page = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize());

		User user = userRepo.findByUsername(principal.getName());

//		WebMaster webMaster = webMasterRepo.findByWebMasterName(user.getRole().getWebMaster().getWebMasterName());
		
//		Role role=roleRepository.findByRoleName(user.getRole().getRole().getRoleName());
		// setting bussiness verticle with respective login user
		// Brand brand=new Brand();
		if(!user.getRole().getWebMaster().getWebMasterName().equalsIgnoreCase(MANAGEMENT)) {
			// store name and username are same
			
				webMasterName = user.getRole().getWebMaster().getWebMasterName();
			


		}


		List<BrandVO> brandVos = new ArrayList<BrandVO>(0);
		Page<Brand> brands = brandService.getAllBrand(brandName, brandCode, webMasterId, webMasterName,verticalName, page);

		if (brands.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", brands.getTotalElements());
			map.put("total_pages", brands.getSize());
			map.put("brandVos", brandVos);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			brands.forEach(brand -> {
				brandVos.add(brandMapper.getVoFromEntity(brand));
			});
		}
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", brands.getTotalElements());
		map.put("total_pages", brands.getSize());
		map.put("brandVos", brandVos);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = WebConstantUrl.GET_BRAND_BY_ID)
	@ResponseBody
	public ResponseEntity<BrandVO> getBrandById(@PathVariable Long brandId) {
		Brand brand = brandService.getBrandById(brandId);
		if (brand == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<BrandVO>(brandMapper.getVoFromEntity(brand), HttpStatus.OK);
	}

	@PostMapping(WebConstantUrl.SAVE_BRAND)
	@ResponseBody
	public ResponseEntity<?> saveBrand(@RequestBody BrandVO brandVo) {
		Map<String, Object> map = new HashMap<>();
		Brand brand = brandMapper.getEntityFromVo(brandVo);

		Brand b = brandRepository.findByBrandCodeAndWebMasterWebMasterId(brandVo.getBrandCode(),brandVo.getWebMasterId());

		if (b != null) {
			map.put("status code", 400);
			map.put("client status", "Bad Request");
			map.put("error msg",
					"Brand with given BrandCode  is already present,Please change BrandCode " + b.getBrandCode() + ".");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);

		}

		brand = brandService.saveBrand(brand);

		if (brand == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<BrandVO>(brandMapper.getVoFromEntity(brand), HttpStatus.CREATED);
	}

	@PutMapping(value = WebConstantUrl.UPDATE_BRAND)
	@ResponseBody
	public ResponseEntity<BrandVO> updateBrand(@RequestBody BrandVO brandVo) {
		Brand brand = brandMapper.getEntityFromVo(brandVo);
		brand = brandService.updateBrand(brand);
		if (brand == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<BrandVO>(brandMapper.getVoFromEntity(brand), HttpStatus.OK);
	}

	@DeleteMapping(value = WebConstantUrl.DELETE_BRAND_BY_ID)
	@ResponseBody
	public void deleteBrandById(@PathVariable Long brandId) {
		brandService.deleteBrandById(brandId);
	}

	/*
	 * @GetMapping(value=WebUrlConstants.DOWNLOAD_SAMPLE_BRAND_EXCELSHEET) public
	 * ResponseEntity<byte[]> downloadSampleExcelSheet() { File resource = null; try
	 * { resource = new
	 * File(getClass().getClassLoader().getResource("Brand_ExcelSheet_sample.xlsx").
	 * getFile());
	 * 
	 * HttpHeaders responseHeaders = new HttpHeaders();
	 * responseHeaders.setContentDispositionFormData("Content-Disposition",resource.
	 * getName()); responseHeaders.setContentType(new MediaType("text", "xlsx",
	 * Charset.forName("utf-8"))); return new
	 * ResponseEntity<byte[]>(Files.toByteArray(resource),responseHeaders,HttpStatus
	 * .OK);
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * logger.error("BRAND_xlsx_SAMPLE_DOWNLOAD ERROR PLEASE CHECK FILE -> ",e); }
	 * 
	 * return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
	 */

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/exportExcel/{id}")
	public void exportToExcel(HttpServletResponse response,@PathVariable("id")Long id) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=brand.xlsx";
		response.setHeader(headerKey, headerValue);

		MasterHeaders masterHeaders = new MasterHeaders();
		List<Object[]> brands = null;
		WebMaster name=webMasterRepo.findByWebMasterId(id);
		if(id !=18) {
		 brands = brandService.getAll(id);
		}else {
			 brands = brandService.getAll();
		}
		ExcelExporter excelExporter = new ExcelExporter(MasterHeaders.brands, brands);

		excelExporter.export(response);
	}
	
	@GetMapping(value = WebConstantUrl.GET_BRAND_BY_WEBMASTER_ID)
	@ResponseBody
	public ResponseEntity<?> getBrandByWebMasterId(@PathVariable(value = "id") Long id) {
		
		
		List<Brand> brand = brandService.getBrandByWebMasterId(id);
		
		if (brand.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<BrandVO> amcinventoryVOs=brand.stream().map(brandMapper::getVoFromEntity).collect(Collectors.toList());
		
		return new ResponseEntity<>(amcinventoryVOs, HttpStatus.OK);
	}

}
