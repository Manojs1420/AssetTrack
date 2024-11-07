package com.titan.irgs.fileupload.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;


public class UploadedDocProcessor implements BeanFactoryAware
{
	
    
private static Logger logger = LoggerFactory.getLogger(UploadedDocProcessor.class);
    
    private BeanFactory beanFactory;

    private String templateName;
    
    private String contentType = WebConstantUrl.CONTENT_TYPE;

    public UploadedDocProcessor() {
    }

    public UploadedDocProcessor(String templateName) {
                    this.templateName = templateName;
    }
    
    protected void printCellStringType(Sheet sheet, int rowIndex,
                                    int columnIndex, String data) {
                    Row row = sheet.getRow(rowIndex);
                    Cell cell = row.getCell(columnIndex);
                    if (cell == null)
                                    cell = row.createCell(columnIndex);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(data);
    }
    
    protected String getCellValueAsString(Cell cell) {
                    DataFormatter formatter = new DataFormatter();
                    String value = formatter.formatCellValue(cell);
                    return value;
    }

    protected void printCellDateType(Workbook wb, Sheet sheet, int rowIndex,
                                    int columnIndex, Date data) {
                    Row dateRow = sheet.getRow(rowIndex);
                    CellStyle cellStyle = wb.createCellStyle();
                    CreationHelper createHelper = wb.getCreationHelper();
                    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
                                                    "d/mmm/yyyy h:mm"));

                    Cell dateCell = dateRow.getCell(columnIndex);
                    if (dateCell == null) {
                                    dateCell = dateRow.createCell(columnIndex);
                    }
                    dateCell.setCellValue(data);
                    dateCell.setCellStyle(cellStyle);
    }

    protected void printCellNumeric(Sheet sheet, int rowIndex, int columnIndex,
                                    double data) {
                    Row row = sheet.getRow(rowIndex);
                    Cell cell = row.getCell(columnIndex);
                    if (cell == null)
                                    cell = row.createCell(columnIndex);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(data);
    }

    protected String getFileName() {
                    String fileName = templateName;
                    fileName = fileName.substring(0, fileName.indexOf("Template"));
                    Date dt = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                    String dtStr = format.format(dt);
                    fileName = fileName + "-" + dtStr + ".xls";
                    return fileName;
    }

    public DataValidation getDecimalRangeConstraint(float startVal,
                                    float endVal, CellRangeAddressList addressList, String errMsg) {
                    DVConstraint constraint = DVConstraint.createNumericConstraint(
                                                    DVConstraint.ValidationType.DECIMAL,
                                                    DVConstraint.OperatorType.BETWEEN, "" + startVal, "" + endVal);
                    DataValidation validation = new HSSFDataValidation(addressList,
                                                    constraint);
                    validation.setSuppressDropDownArrow(true);
                    validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                    validation.createErrorBox("Error", errMsg + " must be between "
                                                    + startVal + " and " + endVal);
                    if(logger.isDebugEnabled()){
                                      logger.debug("{}",errMsg);       
                    }
                    return validation;
    }

    public DataValidation getIntRangeConstraint(int startVal, int endVal,
                                    CellRangeAddressList addressList, String errMsg) {
                    DVConstraint constraint = DVConstraint.createNumericConstraint(
                                                    DVConstraint.ValidationType.INTEGER,
                                                    DVConstraint.OperatorType.BETWEEN, "" + startVal, "" + endVal);
                    DataValidation validation = new HSSFDataValidation(addressList,
                                                    constraint);
                    validation.setSuppressDropDownArrow(true);
                    validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                    validation.createErrorBox("Error", errMsg + " must be between "
                                                    + startVal + " and " + endVal);
                    if(logger.isDebugEnabled()){
                      logger.debug("{}",errMsg);       
                    }
                    return validation;
    }

    public DataValidation getDropDownConstraint(
                                    CellRangeAddressList addressList, String[] list) {
                    DVConstraint dvConstraint = DVConstraint
                                                    .createExplicitListConstraint(list);
                    DataValidation dataValidation = new HSSFDataValidation(addressList,
                                                    dvConstraint);
                    dataValidation.setSuppressDropDownArrow(false);
                    return dataValidation;
    }
    
    protected String getValueBasedOnType(Cell cell) {
                    if(cell==null)
                                    return WebConstantUrl.EMPTY;
                    switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell))
                                                    return cell.getDateCellValue() + WebConstantUrl.EMPTY;
                                    return cell.getNumericCellValue() + WebConstantUrl.EMPTY;
                    case Cell.CELL_TYPE_STRING:
                                    return cell.getStringCellValue();
                    case Cell.CELL_TYPE_FORMULA:
                                    if (cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC) {
                                                    return cell.getNumericCellValue() + WebConstantUrl.EMPTY;
                                    } else if(cell.getCachedFormulaResultType()==Cell.CELL_TYPE_ERROR){
                                                    return cell.getErrorCellValue()+ WebConstantUrl.EMPTY;
                                    }else{
                                                    return cell.getStringCellValue();
                                    }
                    default:
                                    return WebConstantUrl.EMPTY;
                    }
    }



	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}
	

}
