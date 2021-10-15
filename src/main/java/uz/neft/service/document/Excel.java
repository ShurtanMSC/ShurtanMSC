package uz.neft.service.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.repository.WellRepository;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//@Component
@Data
@NoArgsConstructor
@Service
public class Excel {

    @Autowired
    private MiningSystemRepository miningSystemRepository;
    @Autowired
    private UppgRepository uppgRepository;
    @Autowired
    private CollectionPointRepository collectionPointRepository;
    @Autowired
    private WellRepository wellRepository;

    private String name;
    private List<RowExcel> rowExcelList=new ArrayList<>();
//    private List<CellExcel> cellExcelList=new ArrayList<>();



    public boolean generate(String path, String fileName,String sheetName){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.createFreezePane(0, 1);
//        sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
        for (int i = 0; i <rowExcelList.size() ; i++) {
            Row rowSheet = sheet.createRow(i);
            for (int j = 0; j <rowExcelList.get(i).getRow().size(); j++) {
                Cell cellSheet = rowSheet.createCell(j);
                cellSheet.setCellValue(rowExcelList.get(i).getRow().get(i).getCell().get(j));
            }

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
