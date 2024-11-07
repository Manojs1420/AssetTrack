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
import com.titan.irgs.inventory.domain.AssetPOUpload;
import com.titan.irgs.inventory.repository.AssetPOUploadRepo;
import com.titan.irgs.inventory.vo.AssetPOUploadVO;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.repository.AssetRepository;
import com.titan.irgs.master.service.IAssetService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;

@RestController
@RequestMapping( {"/assetPOUpload"}) 
public class AssetPOUploadController {

	@Value("${mail.status}")
	private Boolean mailStatus;

	@Autowired
	MaintainanceEmailImpl emailServiceImpl;

	@Autowired
	MaintainanceEmailImpl1 emailServiceImpl1;

	@Autowired
	ImagePath imagePath;

	@Autowired
	AssetPOUploadRepo assetPOUploadRepo;

	@Autowired
	IAssetService assetService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	AssetRepository assetRepository;


	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@PostMapping({"/save"})
	public ResponseEntity<?> upload(@RequestPart(required = true)@RequestParam(value = "file") MultipartFile file, 
			@RequestParam("pofileInfo") String pofileInfo,Principal principal){

		ObjectMapper mapper=new ObjectMapper();
		Map<String,String> map=new HashMap<>();
		User user=userRepo.findByUsername(principal.getName());

		AssetPOUploadVO assetPOUploadVO=new AssetPOUploadVO(); 
		try {
			assetPOUploadVO=mapper.readValue(pofileInfo,AssetPOUploadVO.class); 

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

		String fileName = this.imagePath.saveImageOnDesk(file, "assetPOFile");
		Asset asset=assetService.getAssetById(assetPOUploadVO.getAssetId());

		if (!fileName.isEmpty()) {
			AssetPOUpload assetPOUpload=new AssetPOUpload();
			BeanUtils.copyProperties(assetPOUploadVO, assetPOUpload);
			assetPOUpload.setEndingPath(str);
			assetPOUpload.setAssetPoUploadPath(fileName);
			assetPOUpload.setAsset(asset);
			assetPOUpload= assetPOUploadRepo.save(assetPOUpload);

			Asset asset1=assetService.getAssetById(assetPOUpload.getAsset().getAssetId());

			if(assetPOUpload.getAssetPoUploadPath()!=null) {

				asset1.setPoUpload("true");

				assetService.updateAssetForPOUpload(asset1);
			}else{

				asset1.setPoUpload("false");

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

		AssetPOUpload assetPOUpload= assetPOUploadRepo.getOne(id);

		Resource resource = this.imagePath.getImageByFileName("assetPOFile", assetPOUpload.getAssetPoUploadPath());

		if(assetPOUpload.getEndingPath().equalsIgnoreCase(".jpg")) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("image/jpg"))

					.header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		}
		else if(assetPOUpload.getEndingPath().equalsIgnoreCase(".jpeg")) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("image/jpeg"))

					.header("Content-Disposition", new String[] { "attachment; filename=\"" + resource.getFilename() + "\"" }).body(resource);
		}
		else if(assetPOUpload.getEndingPath().equalsIgnoreCase(".png")) {
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
	public ResponseEntity<List<AssetPOUploadVO>> getByServiceRequestId(@PathVariable("id") Long id) {
		List<AssetPOUploadVO> assetPOUploadVOs=new ArrayList<>();
		List<AssetPOUpload> assetPOUploads= assetPOUploadRepo.getAssetPoUploadByAssetId(id);

		assetPOUploads.forEach(srf->{
			AssetPOUploadVO assetPOUploadVO=new AssetPOUploadVO();
			BeanUtils.copyProperties(srf, assetPOUploadVO);

			assetPOUploadVO.setAssetId(srf.getAsset().getAssetId()); 
			assetPOUploadVOs.add(assetPOUploadVO); 
		}); 

		return new ResponseEntity<>(assetPOUploadVOs,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUploadById(@PathVariable(value = "id") Long id) {
		AssetPOUpload assetPOUpload=assetPOUploadRepo.findById(id).get();
		assetPOUploadRepo.deleteById(id);

		this.imagePath.deleteImage("assetPOFile", assetPOUpload.getAssetPoUploadPath());
		
		Asset asset=assetRepository.findById(assetPOUpload.getAsset().getAssetId()).get();
		List<AssetPOUpload> assetPOUploads=assetPOUploadRepo.findByAsset(asset);

		if(assetPOUploads.size()==0) {
			asset.setPoUpload("false"); 
			assetService.updateAssetForPOUpload(asset);
		}

	}


}



