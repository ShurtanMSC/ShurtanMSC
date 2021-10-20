package uz.neft.service.document.report;

import lombok.*;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@AllArgsConstructor
@Builder
public class Excel {

    public String file;
    public String name;

    private Workbook workbook;

    public Worksheet worksheet;

    public Excel(String excel_file_name, String sheet_name) throws FileNotFoundException {
        this.file = excel_file_name;
        this.name = sheet_name;
        OutputStream os = new FileOutputStream(file+".xlsx");
        workbook=new Workbook(os,sheet_name,"1.0");
        worksheet=workbook.newWorksheet(sheet_name);
    }

    public void generate() throws IOException {
        workbook.finish();
    }



}
