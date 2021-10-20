package uz.neft.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.stereotype.Service;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.document.fastexcel.ExcelTemplate;
import uz.neft.service.document.report.Excel;
import uz.neft.service.document.report.Helper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportService{

    private final MiningSystemRepository miningSystemRepository;
    private final UppgRepository uppgRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final WellRepository wellRepository;
    private final WellActionRepository wellActionRepository;

    public ReportService(MiningSystemRepository miningSystemRepository, UppgRepository uppgRepository, CollectionPointRepository collectionPointRepository, WellRepository wellRepository, WellActionRepository wellActionRepository) {
        this.miningSystemRepository = miningSystemRepository;
        this.uppgRepository = uppgRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.wellRepository = wellRepository;
        this.wellActionRepository = wellActionRepository;
    }


//    public void generate(Integer mining_system_id){
//
//        Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);
//        if (miningSystem.isPresent()){
//            CellExcel cell=new CellExcel();
//            RowExcel row=new RowExcel();
//            List<Uppg> uppgList = uppgRepository.findAllByMiningSystem(miningSystem.get());
//
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            XSSFSheet sheet = workbook.createSheet("salom");
//            sheet.createFreezePane(0, 1);
//
//
////        sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
//            for (int i = 0; i <row.size() ; i++) {
//                Row rowSheet = sheet.createRow(i);
//                for (int j = 0; j <row.get(i).getCell().size() ; j++) {
//                    Cell cellSheet = rowSheet.createCell(j);
//                    cellSheet.setCellValue(row.get(i).getCell().get(j));
//                }
//
//            }
//            try {
////            FileOutputStream outputStream = new FileOutputStream(path+ File.separator+fileName);
//                FileOutputStream outputStream = new FileOutputStream(fileName+".xlsx");
//                autoSizeColumns(workbook);
//                workbook.write(outputStream);
//                workbook.close();
//                return true;
//            }catch (Exception e){
//                e.printStackTrace();
//                return false;
//            }
//
//
//
//        }
//
//    }

    public void generate(Integer mining_system_id){
        Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);


        if (miningSystem.isPresent()){
            List<Uppg> uppgList = uppgRepository.findAllByMiningSystem(miningSystem.get());



            try (OutputStream os = new FileOutputStream("salom"+".xlsx")) {

                org.dhatim.fastexcel.Workbook wb = new org.dhatim.fastexcel.Workbook(os, "MyApplication", "1.0");
                Worksheet ws = wb.newWorksheet("Sheet 1");

                    int counter=0;
                    int well_counter=1;
                    String[] array= ExcelTemplate.titles;

                    for (int i = 0; i <array.length ; i++) {
                        ws.value(0,i,array[i]);
                    }

//                    ws.freezePane(18,1);
                    ws.range(0,0,0,array.length).style().bold().set();

                counter++;

                    for (Uppg uppg : uppgList) {
                        int row_counter = 0;
                        ws.value(counter, 0, uppg.getName());
                        ws.range(counter,0,counter,18).merge();
                        counter++;
                        List<CollectionPoint> collectionPointList = collectionPointRepository.findAllByUppg(uppg);

                        for (CollectionPoint collectionPoint : collectionPointList) {

                            ws.value(counter, 0, collectionPoint.getName());
                            List<Well> wellList = wellRepository.findAllByCollectionPoint(collectionPoint);
                            ws.range(counter, 0, counter + wellList.size(), 0).merge();

                            for (int k = 0; k < wellList.size(); k++) {

                                Optional<WellAction> action = wellActionRepository.findFirstByWellOrderByCreatedAtDesc(wellList.get(k));

//                                if (action.isPresent()) {
//                                ws.value();
                                ws.value(counter, 1, well_counter);
                                well_counter++;
                                ws.value(counter, 2, wellList.get(k).getNumber());
                                if (action.isPresent()){
                                    ws.value(counter, 3, action.get().getTemperature());
                                    ws.value(counter, 4, action.get().getPressure());
                                    ws.value(counter, 5, action.get().getRo_gas());
                                    ws.value(counter, 6, action.get().getRo_air());
                                    ws.value(counter, 7, action.get().getZ());

                                    ws.value(counter, 8, action.get().getT_pkr());
                                    ws.value(counter, 9, action.get().getP_pkr());
                                    ws.value(counter, 10, action.get().getT_pr());
                                    ws.value(counter, 11, action.get().getP_pr());
                                    ws.value(counter, 12, action.get().getC());
                                    ws.value(counter, 13, action.get().getRo_otn());
                                    ws.value(counter, 14, action.get().getDelta());
                                    ws.value(counter, 15, action.get().getAverage_expend());
                                    ws.value(counter, 16, "K");
                                    ws.value(counter, 17, action.get().getExpend());
                                }
//                                } else {
//                                    ws.value(counter + k, 1 , collectionPoint.getName());
//                                }
                                counter++;

                            }
                            counter++;
                        }

                        ws.value(counter,0,"Объем газа по показанию ХЗУ\n на "+uppg.getName()+", QХЗУ, 1·10³ m³");
                        counter++;
                        ws.value(counter,0,"Объем газа других м/р,\n Qдр, 1·10³ m³");
                        counter++;
                        ws.value(counter,0,"Объем газа м/р Шуртан,\n QШуртан, 1·10³ m³\n");
                        counter++;

                    }

                    ws.range(0,0,counter,20).style().wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();


                System.out.println(wb);;




                wb.finish();
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }

    }


    public OutputStream generateReport(Integer mining_system_id,String name) throws IOException {


        List<Well> wellList = wellRepository.findAllByMiningSystemIdSorted(mining_system_id);
        List<WellAction> actionList=new ArrayList<>();

        Excel excel=new Excel(name,name);
        Worksheet ws=excel.worksheet;
        ws= Helper.operatingModeWell(ws,wellList.size()+5,33);
        for (Well well : wellList) {
            Optional<WellAction> action = wellActionRepository.findFirstByWellOrderByCreatedAtDesc(well);
            if (action.isPresent()) {
                actionList.add(action.get());
            } else {
                actionList.add(null);
            }
        }


        for (int i = 0; i <actionList.size() ; i++) {
            ws.value(4+i,0,i+1);
            ws.value(4+i,1,actionList.get(i)!=null?actionList.get(i).getWell().getNumber():wellList.get(i).getNumber());
            ws.value(4+i,2,actionList.get(i)!=null?actionList.get(i).getWell().getHorizon():wellList.get(i).getHorizon());

            //D екс.кол
            ws.value(4+i,3,0);
            //Искусственный забой
            ws.value(4+i,4,0);

            if (actionList.get(i)!=null){
                if (actionList.get(i).getPerforation_max()==0||actionList.get(i).getPerforation_min()==0){
                    ws.value(4+i,5,"открытый стволь");
                    ws.range(4+i,5,4+i,6).merge();
                }else {
                    ws.value(4+i,5,actionList.get(i).getPerforation_max());
                    ws.value(4+i,6,actionList.get(i).getPerforation_min());
                }
            }else {
                ws.value(4+i,5,"открытый стволь");
                ws.range(4+i,5,4+i,6).merge();
            }

            ws.value(4+i,7,wellList.get(i).getC());
            ws.value(4+i,8,0);
            ws.value(4+i,9,0);
            ws.value(4+i,10,0);
            ws.value(4+i,11,0);
            ws.value(4+i,12,0);
            ws.value(4+i,13,0);
            ws.value(4+i,14,0);

            ws.value(4+i,15,actionList.get(i)!=null?actionList.get(i).getRpl():0);
            ws.value(4+i,16,actionList.get(i)!=null?actionList.get(i).getPressure():0);
            ws.value(4+i,17,actionList.get(i)!=null?actionList.get(i).getExpend()/1000:0);
            ws.value(4+i,18,0);
            ws.value(4+i,19,0);
            ws.value(4+i,20,0);
            ws.value(4+i,21,0);
            ws.value(4+i,22,0);
            ws.value(4+i,23,0);
            ws.value(4+i,24,0);
            ws.value(4+i,25,0);

            ws.value(4+i,26,actionList.get(i)!=null?actionList.get(i).getRpl():0);
            ws.value(4+i,27,actionList.get(i)!=null?actionList.get(i).getPressure():0);
            ws.value(4+i,28,actionList.get(i)!=null?actionList.get(i).getExpend()/1000:0);
            ws.value(4+i,29,0);
            ws.value(4+i,30,0);
            ws.value(4+i,31,0);
        }

        return excel.generate();

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
