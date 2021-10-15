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

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
                ws.range(0,0,250,100).style().wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();
                for (int i = 0; i <array.length ; i++) {
                    ws.value(0,i,array[i]);
                }
                ws.freezePane(18,1);
                counter++;

                for (Uppg uppg : uppgList) {
                    int row_counter = 0;
                    ws.value(counter+1, 0, uppg.getName());
                    counter++;
                    List<CollectionPoint> collectionPointList = collectionPointRepository.findAllByUppg(uppg);

                    for (CollectionPoint collectionPoint : collectionPointList) {

                        ws.value(counter, 0, collectionPoint.getName());
                        List<Well> wellList = wellRepository.findAllByCollectionPoint(collectionPoint);
                        ws.range(counter, 0, counter + wellList.size(), 0).merge();

                        for (int k = 0; k < wellList.size(); k++) {

                            Optional<WellAction> action = wellActionRepository.findFirstByWellOrderByCreatedAtDesc(wellList.get(k));

                            if (action.isPresent()) {
//                                ws.value();
                                ws.value(counter + 1 + k, 1 + k, collectionPoint.getName());
                            } else {
                                ws.value(counter + 1 + k, 1 + k, collectionPoint.getName());
                            }
                            counter++;

                        }


                    }


//                    for (int j = 0; j <; j++) {
//
//                    }
                }



                wb.finish();
            }
            catch (Exception e){
                e.printStackTrace();
            }


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
