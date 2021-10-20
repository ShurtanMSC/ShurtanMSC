package uz.neft.service.document;


import org.dhatim.fastexcel.Worksheet;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.service.document.fastexcel.ExcelTemplate;

import java.io.FileOutputStream;
import java.io.OutputStream;


public class Main {
    public static void main(String[] args) {
//        CellExcel cell=new CellExcel();
//        cell.add("СП, БТ");
//        cell.add("№№ п.п.");
//        cell.add("№№ скв.");
//        cell.add("Абсолютная температура \nгаза перед штуцером (на устье), \nТу, K");
//        cell.add("Давление перед \nштуцером (на устье), \nРу, МРа");
//        cell.add("Плотность газа \nпри стандартных условиях, \nρгаза, kg/m3");
//        cell.add("Плотность воздуха \nпри стандартных условиях, \nρвозд, kg/m3");
//        cell.add("Коэффициент \nсверхсжимаемости газа, \nz ");
//        cell.add("Псевдокритическая \nтемпература, \nТПКР, K");
//        cell.add("Псевдокритическое \nдавление \nРПКР, МРа");
//        cell.add("Приведенная \nтемпература, \nТПр, K");
//        cell.add("Приведенное \nдавление \nРПР, МРа");
//        cell.add("Шайбный \nкоэффициент расхода, \nС");
//        cell.add("Относительная \nплотность газа, \nρ отн");
//        cell.add("Поправочный коэффициент, \nзависящий от приведенных \nдавления и температуры, \nδ");
//        cell.add("Средний расчетный \nдебит скважин, \nDСКВ, 1·10³ m³");
//        cell.add("Поправочный \nкоэффициент \nК");
//        cell.add("Дебит скважин, \nQскв, 1·10³ m³");
//
//        RowExcel row=new RowExcel();
//        row.add(cell);
//        for (int i = 0; i < 10; i++) {
//            row.add(i+"");
//        }
//        Excel excel=new Excel();
//        excel.setRowExcelList(Collections.singletonList(row));
////        row.generate("salom");
//        excel.generate("","salom","salom");


//        MyRow row=new MyRow();
//        int k=0;
//        for (int i = 0; i <10 ; i++) {
//            for (int j = 0; j < 5; j++) {
//                MyCell cell=new MyCell();
//                cell.setText("index = "+i+":"+j);
//                cell.setX(j);
//                cell.setY(i);
//                row.add(cell);
//                k++;
//            }
//        }
//        row.generate("","salom","salom");

        Uppg uppg1= Uppg
                .builder()
                .name("uppg1")
                .build();

        Uppg uppg2= Uppg
                .builder()
                .name("uppg1")
                .build();


        CollectionPoint cp11=CollectionPoint
                .builder()
                .uppg(uppg1)
                .name("cp1")
                .build();

        CollectionPoint cp12=CollectionPoint
                .builder()
                .uppg(uppg1)
                .name("c2")
                .build();

        CollectionPoint cp21=CollectionPoint
                .builder()
                .uppg(uppg2)
                .name("cp3")
                .build();


        CollectionPoint cp22=CollectionPoint
                .builder()
                .uppg(uppg2)
                .name("cp4")
                .build();


        try (OutputStream os = new FileOutputStream("salom"+".xlsx")) {
            org.dhatim.fastexcel.Workbook wb = new org.dhatim.fastexcel.Workbook(os, "MyApplication", "1.0");
            Worksheet ws = wb.newWorksheet("Sheet 1");

            String[] array=ExcelTemplate.titles;
            ws.range(0,0,100,100).style().horizontalAlignment("center").verticalAlignment("center").set();
            for (int i = 0; i <array.length ; i++) {
                ws.value(0,i,array[i]);
                ws.style(0,i).wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();
            }
            ws.freezePane(18,1);
            ws.value(1,0,"Uppg");
//            ws.style(1,0).horizontalAlignment("center").verticalAlignment("center").set();
            ws.range(1,0,1,18).merge();

            ws.value(2,0,"SP");
            ws.range(2,0,8,0).merge();



            ws.value(2,1,1);
            ws.value(2,2,154);
            ws.style(2,2).fillColor("#8B0000").set();




//            ws.style(0,0).wrapText(true);
//            Workbook wb = new Workbook(os, "MyApplication", "1.0");
//            Worksheet ws = wb.newWorksheet("Sheet 1");
//            ws.value(0, 0, "This is a string in A1");
//            ws.value(0, 1, new Date());
//            ws.value(0, 2, 1234);
//            ws.value(0, 3, 123456L);
//            ws.value(0, 4, 1.234);
            wb.finish();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
