package uz.neft.dto.action;

import lombok.*;
import uz.neft.entity.enums.WellStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class WellActionDto {

    private Long actionId;

    // Bosim
    private double pressure;

    // Tempratura
    private double temperature;

    //Sredniy rasxod D_skv
    private double average_expend;

    // Rasxod Q
    private double expend;

    // Rpl
    private double rpl;

    //Р_ПКР
    private double P_pkr;


    //T_ПКР
    private double T_pkr;


    //P_pr
    private double P_pr;

    //T_pr
    private double T_pr;

    //Относительная плотность газа ( ρ_отн )
    private double Ro_otn;


    //Z - Коэффициент сверхсжимаемости газа
    private double Z;


    //delta - Поправочный коэффициент, зависящий от приведенных давления и температуры (δ)
    private double delta;


    //Шайбный коэффициент расхода, С
    private double C;


    //ρ_газа – плотность газа при стандартных условиях
    private double ro_gas;


    //ρ_возд – плотность воздуха при стандартных условиях
    private double ro_air;

    @Enumerated(EnumType.STRING)
    private WellStatus status;

    // Vaqt
    private Date createdAt;


    private int perforation_min;
    private int perforation_max;

    // Quduq idsi
    private Integer wellId;
}
