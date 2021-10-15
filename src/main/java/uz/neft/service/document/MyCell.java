package uz.neft.service.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCell {
    private String text;
    private CellStyle cellStyle;
    private CellUtil cellUtil;
    private int x;
    private int y;

    public MyCell(String text) {
        this.text = text;
    }

    public MyCell get(){
        return this;
    }
}
