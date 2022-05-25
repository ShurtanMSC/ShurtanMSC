package uz.neft.service.document;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.ElectricityDto;
import uz.neft.dto.MiningSystemDto;
import uz.neft.dto.StaffDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.WellAction;
import uz.neft.repository.*;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.service.ElectricityService;
import uz.neft.service.NumberOfStaffService;
import uz.neft.service.document.fastexcel.ExcelTemplate;
import uz.neft.service.document.model.ProductionReportModel;
import uz.neft.service.document.model.TechReportModel;
import uz.neft.service.document.report.Excel;
import uz.neft.service.document.report.Helper;
import uz.neft.utils.Converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

@Service
public class ReportService{

    private final MiningSystemRepository miningSystemRepository;
    private final MiningSystemActionRepository miningSystemActionRepository;
    private final UppgRepository uppgRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final WellRepository wellRepository;
    private final WellActionRepository wellActionRepository;
    private final Converter converter;
    private final NumberOfStaffService numberOfStaffService;
    private final NumberOfStaffRepository numberOfStaffRepository;
    private final ElectricityRepository electricityRepository;
    private final ElectricityService electricityService;
    private final ForecastGasRepository forecastGasRepository;

    public ReportService(MiningSystemRepository miningSystemRepository, MiningSystemActionRepository miningSystemActionRepository, UppgRepository uppgRepository, CollectionPointRepository collectionPointRepository, WellRepository wellRepository, WellActionRepository wellActionRepository, Converter converter, NumberOfStaffService numberOfStaffService, NumberOfStaffRepository numberOfStaffRepository, ElectricityRepository electricityRepository, ElectricityService electricityService, ForecastGasRepository forecastGasRepository) {
        this.miningSystemRepository = miningSystemRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.uppgRepository = uppgRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.wellRepository = wellRepository;
        this.wellActionRepository = wellActionRepository;
        this.converter = converter;
        this.numberOfStaffService = numberOfStaffService;
        this.numberOfStaffRepository = numberOfStaffRepository;
        this.electricityRepository = electricityRepository;
        this.electricityService = electricityService;
        this.forecastGasRepository = forecastGasRepository;
    }


//    public static void main(String[] args) {
//        System.out.println(new Date().getYear());
//    }

    public HttpEntity<?> productionReport(Date start, Date end){
        try {
            List<MiningSystem> all = miningSystemRepository.findAll();
            List<ProductionReportModel> reportModels=new ArrayList<>();
            for (MiningSystem miningSystem : all) {
                Optional<MiningSystemAction> action = miningSystemActionRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem);
                if (action.isPresent()) {
                    reportModels.add(new ProductionReportModel(action.get()));
                } else {
                    reportModels.add(new ProductionReportModel(MiningSystemAction.nuller(miningSystem)));
                }

            }
            return converter.apiSuccess200(reportModels);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> electricityReport(Date start, Date end) {
        try {
            List<ElectricityDto> all = electricityService.allFirstByHelper();
            return converter.apiSuccess200(all);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> staffReport(Date start, Date end) {
        try {
            List<ObjectWithActionsDto> all = numberOfStaffService.getAll();
            return converter.apiSuccess200(all);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> techReport(Integer mining_system_id, Date start,Date end){
        try {
            if (mining_system_id==null) return converter.apiError400("Id is null");
            Optional<MiningSystem> byId = miningSystemRepository.findById(mining_system_id);
            if (!byId.isPresent()) return converter.apiError404("Mining system not found");
            List<Well> wells=wellRepository.findAllByMiningSystemIdSorted(mining_system_id);
            List<WellAction> wellActions=new ArrayList<>();
            for (Well well : wells) {
                Optional<WellAction> first = wellActionRepository.findFirstByWellOrderByCreatedAtDesc(well);
                first.ifPresent(wellActions::add);
            }
            TechReportModel model=new TechReportModel();
            return converter.apiSuccess200(model.transform(wells,wellActions));
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
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
                            List<Well> wellList = wellRepository.findAllByCollectionPointOrderByIdAsc(collectionPoint);
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



    public OutputStream generateProductionReport(String name,Date start, Date end) throws Exception {
        List<ElectricityDto> dtoList = electricityService.allFirstByHelper();
        System.out.println(dtoList.size());
        System.out.println(dtoList);
        Excel excel=new Excel(name,name);
        Worksheet ws=excel.worksheet;
        Helper.operatingProduction(ws, dtoList.size() + 5, 10);

        List<MiningSystem> all = miningSystemRepository.findAll();
        List<ProductionReportModel> reportModels=new ArrayList<>();
        for (MiningSystem miningSystem : all) {
            Optional<MiningSystemAction> action = miningSystemActionRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem);
            if (action.isPresent()) {
                reportModels.add(new ProductionReportModel(action.get()));
            } else {
                reportModels.add(new ProductionReportModel(MiningSystemAction.nuller(miningSystem)));
            }

        }
        for (int i = 0; i <reportModels.size() ; i++) {
            ws.value(2+i,0,reportModels.get(i).getName());
            ws.value(2+i,1,reportModels.get(i).getPlan_m());
            ws.value(2+i,2,reportModels.get(i).getFakt_m());

//            double a1=reportModels.get(i).getFakt_m();
//            double a2=reportModels.get(i).getPlan_m();
//            double a3=a1/a2;
//            double a4=a3*100;
//            System.out.println(a3);
//            System.out.println(a4);

            ws.value(2+i,3,reportModels.get(i).getPlan_m()==0?"0%":(int)(100*(reportModels.get(i).getFakt_m()/reportModels.get(i).getPlan_m()))+"%");

            double a=reportModels.get(i).getPlan_m()-reportModels.get(i).getFakt_m();
            if (a>0)
                ws.value(2+i,4,"-"+(int)Math.abs(a));
            else
                ws.value(2+i,4,a==0?"0":"+"+(int)Math.abs(a));


            ws.value(2+i,5,reportModels.get(i).getPlan_g());
            ws.value(2+i,6,reportModels.get(i).getFakt_g());
            ws.value(2+i,7,reportModels.get(i).getProshlom_god());

//            int k=(int)(reportModels.get(i).getFakt_g()/reportModels.get(i).getPlan_g())*100;
//            System.out.println(k);
            ws.value(2+i,8,reportModels.get(i).getPlan_g()==0?"0%":(int)(100*(reportModels.get(i).getFakt_g()/reportModels.get(i).getPlan_g()))+"%");

            double b=reportModels.get(i).getPlan_g()-reportModels.get(i).getFakt_g();
            if (b>0)
                ws.value(2+i,9,"-"+(int)Math.abs(b));
            else
                ws.value(2+i,9,b==0?"0":"+"+(int)Math.abs(b));
        }
//        return converter.apiSuccess200(reportModels);
//
//        for (int i = 0; i <dtoList.size() ; i++) {
//
//            ws.value(1+i,0,dtoList.get(i).getMiningSystemName());
//            if (dtoList.get(i)!=null){
//                ws.value(1+i,1,dtoList.get(i).getHourly());
//                ws.value(1+i,2,dtoList.get(i).getDaily());
//                ws.value(1+i,3,dtoList.get(i).getMonthly());
//                ws.value(1+i,4,dtoList.get(i).getYearly());
//            }else {
//                ws.value(1+i,1,0);
//                ws.value(1+i,2,0);
//                ws.value(1+i,3,0);
//                ws.value(1+i,4,0);
//            }
//        }
        OutputStream outputStream = excel.generate();
        tpPdf(name+".xlsx");
        return outputStream;
    }

    public OutputStream generateElectricityReport(String name,Date start, Date end) throws Exception {
        List<ElectricityDto> dtoList = electricityService.allFirstByHelper();
        System.out.println(dtoList.size());
        System.out.println(dtoList);
        Excel excel=new Excel(name,name);
        Worksheet ws=excel.worksheet;
        Helper.operatingElectricity(ws, dtoList.size() + 5, 5);


        for (int i = 0; i <dtoList.size() ; i++) {

            ws.value(1+i,0,dtoList.get(i).getMiningSystemName());
            if (dtoList.get(i)!=null){
                ws.value(1+i,1,dtoList.get(i).getHourly());
                ws.value(1+i,2,dtoList.get(i).getDaily());
                ws.value(1+i,3,dtoList.get(i).getMonthly());
                ws.value(1+i,4,dtoList.get(i).getYearly());
            }else {
                ws.value(1+i,1,0);
                ws.value(1+i,2,0);
                ws.value(1+i,3,0);
                ws.value(1+i,4,0);
            }
        }
        OutputStream outputStream = excel.generate();
        tpPdf(name+".xlsx");
        return outputStream;
    }

    public OutputStream generateStaffReport(String name,Date start, Date end) throws Exception {
        List<ObjectWithActionsDto> all = numberOfStaffService.getAll();
        Excel excel=new Excel(name,name);
        Worksheet ws=excel.worksheet;
        Helper.operatingStaff(ws, all.size() + 5, 5);

        for (int i = 0; i <all.size() ; i++) {
            MiningSystemDto dtoMining= (MiningSystemDto) all.get(i).getObjectDto();
            StaffDto dtoStaff= (StaffDto) all.get(i).getObjectActionDto();
            ws.value(1+i,0,dtoMining.getName());
            if (dtoStaff!=null){
                ws.value(1+i,1,dtoStaff.getAtWork());
                ws.value(1+i,2,dtoStaff.getOnVacation());
                ws.value(1+i,3,dtoStaff.getOnSickLeave());
                ws.value(1+i,4,dtoStaff.getWithoutContent());
            }else {
                ws.value(1+i,1,0);
                ws.value(1+i,2,0);
                ws.value(1+i,3,0);
                ws.value(1+i,4,0);
            }
        }
        OutputStream outputStream = excel.generate();
        tpPdf(name+".xlsx");
        return outputStream;
    }

    public OutputStream generateTechReport(Integer mining_system_id,String name) throws Exception {


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


        for (int i = 0; i <actionList.size(); i++) {
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
        OutputStream outputStream = excel.generate();
        tpPdf(name+".xlsx");
        return outputStream;


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



    public static void tpPdf(String path) throws Exception{
            //First we read the Excel file in binary format into FileInputStream
            FileInputStream input_document = new FileInputStream(new File(path));
            // Read workbook into HSSFWorkbook
            XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
            // Read worksheet into HSSFSheet
            XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
            // To iterate over the rows
            Iterator<Row> rowIterator = my_worksheet.iterator();
            //We will create output PDF document objects at this point
            Document iText_xls_2_pdf = new Document();
            PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream("Excel2PDF_Output.pdf"));
            iText_xls_2_pdf.open();
            //we have two columns in the Excel sheet, so we create a PDF table with two columns
            //Note: There are ways to make this dynamic in nature, if you want to.
            PdfPTable my_table = new PdfPTable(32);
            //We will use the object below to dynamically add new data to the table
            PdfPCell table_cell;
            //Loop through rows.
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next(); //Fetch CELL
                    //Identify CELL type
                    //you need to add more code here based on
                    //your requirement / transformations
                    if (cell.getCellType() == CellType.STRING) {//Push the data from Excel to PDF Cell
                        table_cell = new PdfPCell(new Phrase(cell.getStringCellValue()));
                        my_table.addCell(table_cell);
                    } else if (cell.getCellType() == CellType.NUMERIC){
                        table_cell = new PdfPCell(new Phrase((float) cell.getNumericCellValue()));
                        my_table.addCell(table_cell);
                    }

                    //next line
                }

            }
            //Finally add the table to PDF document
            iText_xls_2_pdf.add(my_table);
            iText_xls_2_pdf.close();
            //we created our pdf file..
            input_document.close(); //close xls
        }



}
