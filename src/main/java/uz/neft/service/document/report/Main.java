package uz.neft.service.document.report;

import org.dhatim.fastexcel.Worksheet;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Excel excel=new Excel("salom","salom");
        Worksheet ws=excel.worksheet;
        ws=Helper.operatingModeWell(ws,200,100);




        excel.generate();
    }



}
