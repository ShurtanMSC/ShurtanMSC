package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.entity.enums.WellStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WellActionDto {

    // Quduq idsi
    private Integer wellId;

    // Bosim
    private double pressure;

    // Tempratura
    private double temperature;

    // Rpl
    private double rpl;

    @Enumerated(EnumType.STRING)
    private WellStatus status;

    // Vaqt
    private String date;
}
