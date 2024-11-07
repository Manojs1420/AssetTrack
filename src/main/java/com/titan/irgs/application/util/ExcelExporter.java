package com.titan.irgs.application.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

public class ExcelExporter {
	
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<String> headers;
    private List<Object[]> classVar;
    private Page<?> classVariables;
    
    public ExcelExporter(){
    	
    }
    public ExcelExporter(List<String> excelHeaders,List<Object[]> classVar){
    	this.headers=excelHeaders;
    	this.classVar=classVar;
    	workbook = new XSSFWorkbook();

    	
    }
    public ExcelExporter(List<String> excelHeaders,Page<?> classVariables){
    	this.headers=excelHeaders;
    	this.classVariables=classVariables;
    	workbook = new XSSFWorkbook();

    	
    }

 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        int columnCount = 0;

       for(String header:headers) {
        createCell(row, columnCount, header, style);  
        columnCount=columnCount+1;
       }
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        
        for (Object[] excel : classVar) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            for(int i=0;i<headers.size();i++) {
               
                createCell(row, columnCount++, excel[i]==null?"":excel[i].toString(), style);
            }
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);      
        outputStream.close();
         
    }

}
