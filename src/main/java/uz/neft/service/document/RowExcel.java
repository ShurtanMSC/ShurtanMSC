package uz.neft.service.document;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@NoArgsConstructor
public class RowExcel {

    private List<CellExcel> row=new ArrayList<>();
    private String path="";
    private String fileName="";


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
}
