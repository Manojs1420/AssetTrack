package com.titan.irgs.application.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UploadedDocumentUtils {

	public static String getFileName(Part part) {
		String partHeader = part.getHeader("content-disposition");
		// String partHeader = part.getContentType();
		for (String cd : partHeader.split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static String getFileExtension(Part part) {
		String fileName = getFileName(part);
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		return extension;
	}

	public static String getFileExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		return extension;
	}

	// using MultipartFile
	public static String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "This excel sheet does not have any data.please upload valid excel sheet..!";
		}

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "product_excel_sheet";
	}

}
