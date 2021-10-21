package uz.neft.service.document.model;

import lombok.*;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.enums.WellStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechReportModel {
//    private List<Well> wells;
//    private List<WellAction> wellActions;

    private String No;
    private String nomer;
    private String gorizont;
    private String D_eks_kol;
    private String iskus_zaboy;
    private String verx;
    private String nijn;
    private String c;
    private String d;
    private String L;

    private String D_sht1;
    private String R_tr1;
    private String R_ztr1;
    private String R_stat1;
    private String R_zab1;
    private String R_pl1;
    private String delta_R1;
    private String debit_gaz1;
    private String debit_kondensat1;
    private String ustevaya_skorost1;
    private String zaboynaya_skorost1;


    private String D_sht2;
    private String R_tr2;
    private String R_ztr2;
    private String R_stat2;
    private String R_zab2;
    private String R_pl2;
    private String delta_R2;
    private String debit_gaz2;
    private String debit_kondensat2;
    private String ustevaya_skorost2;
    private String zaboynaya_skorost2;

    private WellStatus status;

    public void transform(List<Well> wells,List<WellAction> wellActions){
        for (int i = 0; i <wellActions.size() ; i++) {
           setNo(String.valueOf(i+1));
           setNomer(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getWell().getNumber():wells.get(i).getNumber()));
           setGorizont(wellActions.get(i)!=null?wellActions.get(i).getWell().getHorizon():wells.get(i).getHorizon());

            //D екс.кол
           setD_eks_kol(String.valueOf(0));
            //Искусственный забой
           setIskus_zaboy(String.valueOf(0));

            if (wellActions.get(i)!=null){
                setVerx(String.valueOf(wellActions.get(i).getPerforation_max()));
                setNijn(String.valueOf(wellActions.get(i).getPerforation_min()));
                setStatus(wellActions.get(i).getStatus());
            }else {
               setVerx(String.valueOf(0));
               setNijn(String.valueOf(0));
               setStatus(WellStatus.IN_REPAIR);
            }

           setC(String.valueOf(wells.get(i).getC()));
           setD(String.valueOf(0));
           setL(String.valueOf(0));



           setD_sht1(String.valueOf(0));
           setR_tr1(String.valueOf(0));
           setR_ztr1(String.valueOf(0));
           setR_stat1(String.valueOf(0));
           setR_zab1(String.valueOf(0));
           setR_pl1(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getRpl():0));
           setDelta_R1(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getPressure():0));
           setDebit_gaz1(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getExpend()/1000:0));
           setDebit_kondensat1(String.valueOf(0));
           setUstevaya_skorost1(String.valueOf(0));
           setZaboynaya_skorost1(String.valueOf(0));



            setD_sht2(String.valueOf(0));
            setR_tr2(String.valueOf(0));
            setR_ztr2(String.valueOf(0));
            setR_stat2(String.valueOf(0));
            setR_zab2(String.valueOf(0));
            setR_pl2(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getRpl():0));
            setDelta_R2(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getPressure():0));
            setDebit_gaz2(String.valueOf(wellActions.get(i)!=null?wellActions.get(i).getExpend()/1000:0));
            setDebit_kondensat2(String.valueOf(0));
            setUstevaya_skorost2(String.valueOf(0));
            setZaboynaya_skorost2(String.valueOf(0));
        }
    }





}
