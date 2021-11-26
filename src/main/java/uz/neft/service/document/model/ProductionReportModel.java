package uz.neft.service.document.model;

import lombok.*;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.action.MiningSystemAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionReportModel {
    private String name;
    private double plan_m;
    private double fakt_m;
    private double vipolneniya_m;
    private double otstavanie_perevipolnenie_m;


    private double plan_g;
    private double fakt_g;

    private double proshlom_god;

    private double vipolneniya_g;
    private double otstavanie_perevipolnenie_g;


//    public List<ProductionReportModel> transform(List<MiningSystem> miningSystemList,
//                                                 List<MiningSystemAction> actionList,
//                                                 List<ForecastGas> forecastGasList){
//
//
//        List<TechReportModel> models=new ArrayList<>();
//
//
//
//
//    }


}
