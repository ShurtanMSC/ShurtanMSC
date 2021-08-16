package uz.neft.service.document;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class CellExcel {

    private List<String> cell = new ArrayList<>();


    public CellExcel(String text) {
        cell.add(text);
    }

    public CellExcel(List<String> cell) {
        this.cell = cell;
    }


    public List<String> getCell() {
        return cell;
    }

    public void setCell(List<String> cell) {
        this.cell = cell;
    }



    public void add(String text) {
        cell.add(text);
    }


    public void clear(){
        this.cell=new ArrayList<>();
    }
}
