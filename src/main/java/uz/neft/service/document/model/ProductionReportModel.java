package uz.neft.service.document.model;

import lombok.*;
import uz.neft.entity.action.MiningSystemAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionReportModel {
    private String name;
    private double plan_m;
    private double fakt_m;
//    private double vipolneniya_m;
//    private double otstavanie_perevipolnenie_m;


    private double plan_g;
    private double fakt_g;

    private double proshlom_god;

//    private double vipolneniya_g;
//    private double otstavanie_perevipolnenie_g;


//    public List<ProductionReportModel> transform(List<MiningSystemAction> actionList,
//                                                 List<ForecastGas> forecastGasList){
//
//
//        List<TechReportModel> models=new ArrayList<>();
//        for (int i = 0; i <forecastGasList.size() ; i++) {
//
//        }
//
//
//
//
//    }

    public void transform(MiningSystemAction action){
        this.name=action.getMiningSystem().getName();
        this.plan_m=action.getPlanThisMonth();
        this.fakt_m= action.getThisMonthExpend();
        this.plan_g=action.getPlanThisYear();
        this.fakt_g=action.getLastYearExpend();
        this.proshlom_god=action.getLastYearExpend();
    }

    public ProductionReportModel(MiningSystemAction action){
        this.name=action.getMiningSystem().getName();
        this.plan_m=action.getPlanThisMonth();
        this.fakt_m= action.getThisMonthExpend();
        this.plan_g=action.getPlanThisYear();
        this.fakt_g=action.getLastYearExpend();
        this.proshlom_god=action.getLastYearExpend();
    }

}
