package uz.neft.service.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyRow {
    private List<MyCell> cellList=new ArrayList<>();




    public void add(String text){
        cellList.add(new MyCell(text));
    }

    public void add(MyCell cell){
        cellList.add(cell);
    }

    public boolean generate(String path, String fileName,String sheetName){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
//        sheet.createFreezePane(0, 1);
//        sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
//        for (int i = 0; i <row.size() ; i++) {
//            Row rowSheet = sheet.createRow(i);
//            for (int j = 0; j <row.get(i).getCell().size() ; j++) {
//                Cell cellSheet = rowSheet.createCell(j);
//                cellSheet.setCellValue(row.get(i).getCell().get(j));
//            }
//
//        }
        for (MyCell cell : cellList) {
            Row rowSheet = sheet.createRow(cell.getX());
            Cell cellSheet = rowSheet.createCell((cell.getY()));
            cellSheet.setCellValue(cell.getText());
        }



        try {
//            FileOutputStream outputStream = new FileOutputStream(path+ File.separator+fileName);
            FileOutputStream outputStream = new FileOutputStream(fileName+".xlsx");
            autoSizeColumns(workbook);
            workbook.write(outputStream);
            workbook.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }


    public void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellStyle(cs);
                    CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
                    CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                    int currentColumnWidth = sheet.getColumnWidth(columnIndex);
                    sheet.setColumnWidth(columnIndex, (currentColumnWidth + 2500));
                }
            }
        }
    }



}
