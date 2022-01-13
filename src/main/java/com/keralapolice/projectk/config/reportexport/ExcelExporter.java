package com.keralapolice.projectk.config.reportexport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelExporter(){
        workbook = new XSSFWorkbook();
    }

      private void createHeaderLine(List<String> value){
        value.add(0, "SL No");
        if(value instanceof List){
        int count = value.size();

        Row row = sheet.createRow(1);

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(14);
            font.setFontName("Bookman Old Style");
            style.setFont(font);
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setFillBackgroundColor(IndexedColors.BLACK.index);
            style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setWrapText(true);
            for(int i=0;i<count;i++){
                sheet.setColumnWidth(i+1, 5000);
            Cell cell = row.createCell(i);
            if(((List<?>) value).get(i) instanceof Integer){
                cell.setCellValue((Integer) ((List<?>) value).get(i));
            }else if(((List<?>) value).get(i) instanceof String){
                cell.setCellValue((String) ((List<?>) value).get(i));
            }else if(((List<?>) value).get(i) instanceof Boolean){
                cell.setCellValue((Boolean) ((List<?>) value).get(i));
            }
            cell.setCellStyle(style);

            }

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

    private void createTitle(String title,String sheetName,int mergelength){
        try {
           sheet = workbook.createSheet(sheetName);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(14);
            font.setFontName("Bookman Old Style");
            style.setFont(font);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setWrapText(true);
            Row row = sheet.createRow(0);
            row.setHeight((short)+1000);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, mergelength));
            Cell cell = row.createCell(0);
            cell.setCellValue((String) title);
            cell.setCellStyle(style);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void writeDataLines(List<Map<String, Object>> data) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        font.setFontName("Bookman Old Style");
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        int rowsize = data.size();
        int slNo = 1;
        for(int i=0;i<rowsize;i++){
            Row row = sheet.createRow(i+2);
            Map<String,Object> m = data.get(i);
            Set<String> key =  m.keySet();
            int j=1;
            Cell slnocell = row.createCell(0);
            //System.out.println(m.get(keyName));
            slnocell.setCellValue(slNo);
            slnocell.setCellStyle(style);
            for (String keyName: key) {
                Cell cell = row.createCell(j);
                //System.out.println(m.get(keyName));
                if(m.get(keyName) instanceof Integer){
                   cell.setCellValue((Integer) m.get(keyName));
                }else if(m.get(keyName)instanceof String){
                    cell.setCellValue((String) m.get(keyName));
                }else if(m.get(keyName) instanceof Boolean){
                    cell.setCellValue((Boolean)  m.get(keyName));
                }else if(m.get(keyName) instanceof  Long){
                    cell.setCellValue((Long) m.get(keyName));
                }else if(m.get(keyName) instanceof  Date){
                    cell.setCellValue((Date) m.get(keyName));
                }else if(m.get(keyName) instanceof  Float){
                    cell.setCellValue((Float) m.get(keyName));
                }else if(m.get(keyName) instanceof  Double){
                    cell.setCellValue((Double) m.get(keyName));
                }else if(m.get(keyName) instanceof  List){
                    cell.setCellValue((String) m.get(keyName));
                }
                cell.setCellStyle(style);
                j++;
            }
            slNo++;
        }

    }

    public void export(HttpServletResponse response,List<String> heading,List<Map<String, Object>> datas,String fileName,String title,String sheetName) throws IOException ,IllegalAccessException{
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-YYYY_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
//            String headerKey = "Content-Disposition";
//            String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".xlsx";
//            response.setHeader(headerKey, headerValue);
            createTitle(title, sheetName, heading.size());
            createHeaderLine(heading);
            writeDataLines(datas);
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            //workbook.close();
            workbook.removeSheetAt(0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}