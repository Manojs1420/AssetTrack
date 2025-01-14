package com.titan.irgs.application.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.titan.irgs.UserRoleServiceApplication;
import com.titan.irgs.customException.ResourceAlreadyExitException;

@Component
public class ImagePath {
	

	
	private String getAbsolutePath() {
	    try {
	      String imgDir = (new ApplicationHome(UserRoleServiceApplication.class)).getDir().getCanonicalPath();
	      return imgDir;
	    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    } 
	  }
	  
	  public String saveImageOnDesk(MultipartFile file, String imagesFolder) {
	    File filePath = new File(getAbsolutePath() + File.separator + "/images" + File.separator + imagesFolder);
	    Path path = Paths.get(filePath.getAbsolutePath() + "/" + file.getOriginalFilename(), new String[0]);
	    if (!filePath.exists())
	      filePath.mkdirs(); 
	    try {
	      Files.copy(file.getInputStream(), path, new java.nio.file.CopyOption[0]);
	    } 
	    catch(FileAlreadyExistsException e) {
	    	throw new ResourceAlreadyExitException("The "+file.getOriginalFilename()+" is already exists");
	    	
	    }
	    catch (IOException e) {
	    
	    	
	    	throw new ResourceAlreadyExitException("The "+file.getOriginalFilename()+" is already exists");
	    } 
	    return file.getOriginalFilename();
	  }
	  
	  public Resource getImageByFileName(String imageFolder, String fileName) {
		  
	    File filePath = new File(getAbsolutePath() + File.separator + "/images" + File.separator + imageFolder);
	    Path path = Paths.get(filePath.getAbsolutePath() + "/" + fileName, new String[0]);
		  
		  
		  
		/*
		 * File filePath = new File(getAbsolutePath() + File.separator + "/images" +
		 * File.separator + imageFolder + File.separator + fileName); Path path =
		 * Paths.get(filePath.toString(), new String[0]);
		 */
	    Resource resource = null;
	    try {
	    	resource = new UrlResource(path.toUri());
	        if (!resource.exists()) {
				resource = new UrlResource(Paths.get(new File("images").toString()+"/"+"noimage.jpg").toUri());
				}
              
	    	
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } 
	    return resource;
	  }
	  
	  public void deleteImage(String imageFolder, String fileName) {
		File filePath = new File(getAbsolutePath() + File.separator + "/images" + File.separator + imageFolder + File.separator + fileName);
		
		if(filePath.delete()) 
        { 
            System.out.println("File deleted successfully"); 
        } 
        else
        { 
            System.out.println("Failed to delete the file"); 
        } 
		  
	  }
	  
	  public byte[] getFilesResourceOnFileName(String fileName, String folderName) {
			Path path = Paths.get(getAbsolutePath());

			String strPath = path.toAbsolutePath().toString();

			try {
				return Files.readAllBytes(Paths.get("" + strPath + File.separator + "/images" + File.separator + folderName
						+ File.separator + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

}
