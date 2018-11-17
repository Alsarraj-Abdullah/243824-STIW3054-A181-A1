
package com.mycompany.converter_pw_to_ex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Excel {
    
    /**
     * Create a new Excel file
     * 
     * @param name The path of the file with the name it stores
     * @return FileOutputStream
     */
    public FileOutputStream createNewFileExcel(String name){
        
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(name));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        return out;
    }
    
    /**
     * Add content from the web page to the Excel file
     * 
     * @param out the Excel file
     * @param table Table extracted from web page
     */
    public void setContent(FileOutputStream out, Element table){
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
            
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);
        
        if(table != null) {
            HSSFRow exlrow = sheet.createRow(0);
            HSSFCell cell01 = exlrow.createCell(0);
            HSSFCell cell02 = exlrow.createCell(1);
            HSSFCell cell03 = exlrow.createCell(2);
            cell01.setCellStyle(cellStyle);
            cell02.setCellStyle(cellStyle);
            cell03.setCellStyle(cellStyle);
            cell01.setCellValue("No");
            cell02.setCellValue("Characteristics");
            cell03.setCellValue("Characteristics Values");
            int rownum = 1;
            for (Element row : table.select("tr")) {
                
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                
                Elements th = row.select("th");
                Elements td = row.select("td");
                if(!th.isEmpty() && !td.isEmpty()){
                    exlrow = sheet.createRow(rownum++);
                    HSSFCell cell = exlrow.createCell(0);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(rownum-1);
                    exlrow.createCell(1).setCellValue(th.get(0).text());
                    exlrow.createCell(2).setCellValue(td.get(0).text());
                }
                else{
                    if(td.size() > 1){
                        exlrow = sheet.createRow(rownum++);
                        HSSFCell cell = exlrow.createCell(0);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(rownum);
                        exlrow.createCell(1).setCellValue(td.get(0).text());
                        exlrow.createCell(2).setCellValue(td.get(1).text());
                    }
                }
            }
        }
        
        try {
            workbook.write(out);
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
