package com.titan.irgs.inventory.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.irgs.application.util.ImagePath;
import com.titan.irgs.inventory.domain.VendorInvoiceFileUpload;
import com.titan.irgs.inventory.repository.VendorInvoiceFileUploadRepo;
import com.titan.irgs.inventory.vo.VendorInvoiceFileUploadVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.service.IAssetService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping( {"/vendorInvoiceUpload"}) 
public class VendorInvoiceFileUploadController {

	@Value("${mail.status}")
	private Boolean mailStatus;

	@Autowired
	MaintainanceEmailImpl emailServiceImpl;

	@Autowired
	MaintainanceEmailImpl1 emailServiceImpl1;

	@Autowired
	ImagePath imagePath;

	@Autowired
	VendorInvoiceFileUploadRepo vendorInvoiceFileUploadRepo;

	@Autowired
	IAssetService assetService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	AssetRepository assetRepository;


	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@PostMapping({"/save"})
	public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file, 
			@RequestParam("invoicefileInfo") String invoicefileInfo,Principal principal){

		ObjectMapper mapper=new ObjectMapper();
		Map<String,String> map=new HashMap<>();
		User user=userRepo.findByUsername(principal.getName());

		VendorInvoiceFileUploadVO vendorInvoiceFileUploadVO=new VendorInvoiceFileUploadVO(); 
		try {
			vendorInvoiceFileUploadVO=mapper.readValue(invoicefileInfo,VendorInvoiceFileUploadVO.class); 

		}
		catch (Exception e)
		{ 
			System.out.println(e.getLocalizedMessage()); 
		}

		String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
				file.getOriginalFilename().length());

		if (!str.equalsIgnoreCase(".jpg") &&  !str.equalsIgnoreCase(".png") && !str.equalsIgnoreCase(".pdf") && !str.equalsIgnoreCase(".jpeg")) {
			return new ResponseEntity(file.getOriginalFilename() + " not ending with .jpg or .png or .pdf format", HttpStatus.BAD_REQUEST); 

		}

		String fileName = this.imagePath.saveImageOnDesk(file, "vendorInvoiceFile");
		Asset asset=assetService.getAssetById(vendorInvoiceFileUploadVO.getAssetId());

		if (!fileName.isEmpty()) {
			VendorInvoiceFileUpload vendorInvoiceFileUpload=new VendorInvoiceFileUpload();
			BeanUtils.copyProperties(vendorInvoiceFileUploadVO, vendorInvoiceFileUpload);
			vendorInvoiceFileUpload.setEndingPath(str);
			vendorInvoiceFileUpload.setVendorInvoiceUploadPath(fileName);
			vendorInvoiceFileUpload.setAsset(asset);
			vendorInvoiceFileUpload.setVendorId(asset.getVendor().getVendorId());
			vendorInvoiceFileUpload= vendorInvoiceFileUploadRepo.save(vendorInvoiceFileUpload);

			Asset asset1=assetService.getAssetById(vendorInvoiceFileUpload.getAsset().getAssetId());

			if(vendorInvoiceFileUpload.getVendorInvoiceUploadPath()!=null) {

				asset1.setVendorInvoiceUpload("true");

				assetService.updateAssetForPOUpload(asset1);
			}else{

				asset1.setVendorInvoiceUpload("false");

				assetService.updateAssetForPOUpload(asset1);
			}

			map.put("msg", "created successfully");

		}

		/*
		  if (user.getRole().getRole().getRoleName().equalsIgnoreCase("Vendor")
					|| user.getRole().getRole().getRoleName().equalsIgnoreCase("REGENGINEER")) {

			//  maintainance.setRunningStatus("Uploading Service Document");
						// Sr-Notification email Trigger start--------------->
						List<Object[]> amcInventoryList =assetRepository.UploadingPODocument(asset.getAssetId());
				for (Object[] rows : amcInventoryList) {


					Maintainance amcInventories = maintainanceService.getByMaintainanceId(((BigInteger) rows[2]).longValue());
							String mailSubjects = "";			
							String emailCcValue=(String) rows[4];
							List<String> emailCc = Arrays.asList(emailCcValue.split(",")).stream().filter(str1->!str1.isEmpty()).collect(Collectors.toList());			
				            System.out.println(emailCc);
							String emailTOValue=(String) rows[3];
							List<String> emailTo = Arrays.asList(emailTOValue.split(",")).stream().filter(str1->!str1.isEmpty()).collect(Collectors.toList());
							System.out.println(emailTo);


						  Mail mail = maintainanceService.templeteMail(amcInventories);
						  Long id= amcInventories.getAmcInventory().getWebMaster().getWebMasterId();
						  mailSubjects = "Nu-Nxtwav  "+ maintainance.getServiceRequestCode()+"– Uploading Service Document";
						  mail.setMailTo(emailTo);
						  mail.setMailCC(emailCc); 
						  mail.setMailSubject(mailSubjects);
						  if (mailStatus) { try {
							  emailServiceImpl1.sendMultiPartEmail(mail);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} } 

						}


				// Sr-Notification Email TriggerEnd    <-------------------------									
										System.out.println("Uploading Service Document is completed");


				}*/

		return new ResponseEntity<>(map,HttpStatus.OK);

	}


	@GetMapping({"/download/{id}"})
	public ResponseEntity<?> downloadFileFromLocal(@PathVariable("id") Long id) {

		VendorInvoiceFileUpload vendorInvoiceFileUpload= vendorInvoiceFileUploadRepo.getOne(id);

		Resource resource = this.imagePath.getImageByFileName("vendorInvoiceFile", vendorInvoiceFileUpload.getVendorInvoiceUploadPath());

		if(vendorInvoiceFileUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("image/jpg"))

					.header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		}
		else if(vendorInvoiceFileUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("image/jpeg"))

					.header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		}
		else if(vendorInvoiceFileUpload.getEndingPath().equalsIgnoreCase(".png")) {
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


	@GetMapping({"/getByAssetId/{id}"})
	public ResponseEntity<List<VendorInvoiceFileUploadVO>> getByServiceRequestId(@PathVariable("id") Long id) {
		List<VendorInvoiceFileUploadVO> vendorInvoiceFileUploadVOs=new ArrayList<>();
		List<VendorInvoiceFileUpload> vendorInvoiceFileUploads= vendorInvoiceFileUploadRepo.getVendorInvoiceFileUploadByAssetId(id);

		vendorInvoiceFileUploads.forEach(srf->{
			VendorInvoiceFileUploadVO vendorInvoiceFileUploadVO=new VendorInvoiceFileUploadVO();
			BeanUtils.copyProperties(srf, vendorInvoiceFileUploadVO);

			vendorInvoiceFileUploadVO.setAssetId(srf.getAsset().getAssetId()); 
			vendorInvoiceFileUploadVOs.add(vendorInvoiceFileUploadVO); 
		}); 

		return new ResponseEntity<>(vendorInvoiceFileUploadVOs,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUploadById(@PathVariable(value = "id") Long id) {
		VendorInvoiceFileUpload vendorInvoiceFileUpload=vendorInvoiceFileUploadRepo.findById(id).get();
		vendorInvoiceFileUploadRepo.deleteById(id);

		this.imagePath.deleteImage("vendorInvoiceFile", vendorInvoiceFileUpload.getVendorInvoiceUploadPath());
		
		Asset asset=assetRepository.findById(vendorInvoiceFileUpload.getAsset().getAssetId()).get();
		List<VendorInvoiceFileUpload> vendorInvoiceFileUploads=vendorInvoiceFileUploadRepo.findByAsset(asset);

		if(vendorInvoiceFileUploads.size()==0) {
			asset.setVendorInvoiceUpload("false");
			assetService.updateAssetForPOUpload(asset);
		}

	}


}


