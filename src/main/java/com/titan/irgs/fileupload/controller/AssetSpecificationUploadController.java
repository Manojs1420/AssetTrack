package com.titan.irgs.fileupload.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.fileupload.domain.UploadedDocument;
import com.titan.irgs.fileupload.enums.FileUploadType;
import com.titan.irgs.fileupload.processor.AssetSpecificationProcessor;
import com.titan.irgs.fileupload.repository.UploadDocumentRepository;

@RestController
@RequestMapping(value=WebConstantUrl.ASSET_SPECIFICATION_FILE_BASE_URL)
public class AssetSpecificationUploadController
{
	

	
	
	private static Logger logger = LoggerFactory.getLogger( AssetSpecificationUploadController.class);
    
    @Autowired
     private AssetSpecificationProcessor  assetSpecificationProcessor;
                               
                  @Autowired
                   private UploadDocumentRepository uploadDocumentRepository;
               
               
               
               @SuppressWarnings("unused")
               @RequestMapping(value = WebConstantUrl.IMPORT_ASSET_FILE_UPLOAD ,method=RequestMethod.POST)
               public ResponseEntity<Map<String,Object>>testApi(@RequestParam("file") MultipartFile part) throws Exception
               {
                               UploadedDocument document = new UploadedDocument();
                               Map<String , Object> map= new HashMap<>();
                               List<String> result = null;
               
                                              

                                               document.setName("Asset = " + System.currentTimeMillis());
                                               document.setFileUploadType(FileUploadType.ASSETSPECIFICATION);

                                               document.setContentType(part.getContentType());
                                               document.setUploadedTime(new Date());
                                               document.setFilename(part.getOriginalFilename());
                                               document.setUploadedDate(new Date());
                                               document.setProcessed(false);
                                               
                                               System.out.println("document = " + document);
                                               
                                               uploadDocumentRepository.save(document);
                                               
                                               if (logger.isDebugEnabled()) {
                                                               logger.debug("Filename sent id :" + part.getName());
                                               }
                                               String fileName = document.getFilename();
                                               logger.debug("file Name = " + fileName);

                                               InputStream inputStream = part.getInputStream();
                                               byte[] bytes = new byte[inputStream.available()];
                                               inputStream.read(bytes, 0, bytes.length);
                                               inputStream.close();

                                         
                                               InputStream inp = new ByteArrayInputStream(bytes);

                                               result = assetSpecificationProcessor.importAssetRecords(inp);
                                           
                                                  map.put("success_msg", "ok");
                                                  map.put("error_msg", result);
                                                  return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
               }



}
