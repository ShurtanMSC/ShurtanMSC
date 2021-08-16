package uz.neft.service.document;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class Excel {

    private String name;
    private List<RowExcel> rowExcelList=new ArrayList<>();
    private List<CellExcel> cellExcelList=new ArrayList<>();



}
