package com.codeTest.studentReg.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codeTest.studentReg.entity.Student;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExportUtil {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Student> studentList;

    public ExcelExportUtil(List<Student> studentList) {
        this.studentList = studentList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        
        Cell cell = row.createCell(columnCount);
        
        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double){
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        
        cell.setCellStyle(style);
    }

    private void createHeaderRow(){
        sheet = workbook.createSheet("Student Information");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeight(20);
        
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        createCell(row, 0, "Student Information", style);
        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Age", style);
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "Address", style);
    }

    private void writeStudentData(){
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        
        font.setFontHeight(14);
        style.setFont(font);

        for (Student student : studentList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            createCell(row, columnCount++, student.getId(), style);
            createCell(row, columnCount++, student.getName(), style);
            createCell(row, columnCount++, student.getAge(), style);
            createCell(row, columnCount++, student.getPhone(), style);
            createCell(row, columnCount++, student.getAddress(), style);
        }
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeStudentData();
        
        ServletOutputStream outputStream = response.getOutputStream();
        
        workbook.write(outputStream);
        
        workbook.close();
        outputStream.close();
    }
    
    public ByteArrayInputStream exportExcelToEmail(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeStudentData();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        workbook.write(out);
        
        workbook.close();
        
        return new ByteArrayInputStream(out.toByteArray());
    }
}