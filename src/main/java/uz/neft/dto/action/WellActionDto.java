package uz.neft.dto.action;

import lombok.*;
import uz.neft.entity.enums.WellStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellActionDto {

    // Quduq idsi
    private Integer wellId;

    // Bosim
    private double pressure;

    // Tempratura
    private double temperature;

    // Rasxod
    private double expend;

    // Rpl
    private double rpl;

    //C
    private double c;

    @Enumerated(EnumType.STRING)
    private WellStatus status;

    // Vaqt
    private String date;

    private int perforation_min;
    private int perforation_max;
}
