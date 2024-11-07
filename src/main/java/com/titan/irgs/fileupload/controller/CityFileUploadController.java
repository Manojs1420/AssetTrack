package com.titan.irgs.fileupload.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;
import com.titan.irgs.application.util.UploadedDocumentUtils;
import com.titan.irgs.fileupload.domain.UploadedDocument;
import com.titan.irgs.fileupload.enums.FileUploadType;
import com.titan.irgs.fileupload.processor.CityProcessor;
import com.titan.irgs.fileupload.repository.UploadDocumentRepository;

@RestController
@RequestMapping(value=WebConstantUrl.CITY_FILE_BASE_URL)
public class CityFileUploadController 
{
	
	
	private static Logger logger = LoggerFactory.getLogger( CityFileUploadController.class);
    
    @Autowired
     private CityProcessor  cityProcessor;
                               
    @Autowired
    private UploadDocumentRepository uploadDocumentRepository;
               
               
               
               @SuppressWarnings("unused")
               @RequestMapping(value = WebConstantUrl.IMPORT_CITY_FILE_UPLOAD ,method=RequestMethod.POST)
               public ResponseEntity<Map<String,Object>> testApi(@RequestParam("file") Part part) throws Exception
               {
                               UploadedDocument document = new UploadedDocument();
                               Map<String , Object> map= new HashMap<>();
                               List<String> result = null;
               
                                               String extension = UploadedDocumentUtils.getFileExtension((Part)part);
                                               logger.debug("extension = " + extension);

                                               document.setName("City = " + System.currentTimeMillis());
                                               document.setFileUploadType(FileUploadType.CITY);

                                               document.setContentType(part.getContentType());
                                               document.setUploadedTime(new Date());
                                               document.setFilename(UploadedDocumentUtils.getFileName((Part)part));
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

//                                           ExcelSheetProcessor excelSheetProcessor = beanFactory.getBean("excelSheetProcessor",
//                                                                           ExcelSheetProcessor.class);
                                               InputStream inp = new ByteArrayInputStream(bytes);

                                               result = cityProcessor.importCityRecords(inp);

                                               if(result.size() != 0) {
                                             	  map.put("error_msg", result.get(0) );
                                                   return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
                                               }
                                               map.put("success_msg", "ok");
                                               return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
               }



}