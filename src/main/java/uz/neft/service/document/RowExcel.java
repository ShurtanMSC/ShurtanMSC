package uz.neft.service.document;

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
import java.util.*;

//@Component
@NoArgsConstructor
@Service
public class RowExcel {

    private List<CellExcel> row=new ArrayList<>();
    private String path="";
    private String fileName="";


    @Autowired
    private MiningSystemRepository miningSystemRepository;
    @Autowired
    private UppgRepository uppgRepository;
    @Autowired
    private CollectionPointRepository collectionPointRepository;
    @Autowired
    private WellRepository wellRepository;



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public RowExcel(String name) {
        this.row.add(new CellExcel(name));
    }

    public List<CellExcel> getRow() {
        return row;
    }

    public void setRow(List<CellExcel> row) {
        this.row = row;
    }

    public void add(String text){
        this.row.add(new CellExcel(text));
    }

    public void add(CellExcel cell){
        this.row.add(new CellExcel(cell.getCell()));
    }

    public boolean generate(String path, String fileName,String sheetName){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.createFreezePane(0, 1);
//        sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
        for (int i = 0; i <row.size() ; i++) {
            Row rowSheet = sheet.createRow(i);
            for (int j = 0; j <row.get(i).getCell().size() ; j++) {
                Cell cellSheet = rowSheet.createCell(j);
                cellSheet.setCellValue(row.get(i).getCell().get(j));
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






    public boolean generate(String fileName){
        return generate("",fileName,fileName);
    }

    public boolean generate(){
        return generate("", String.valueOf(new Date()),fileName);
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
