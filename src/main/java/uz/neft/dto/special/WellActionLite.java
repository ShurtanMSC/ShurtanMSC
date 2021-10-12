package uz.neft.dto.special;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.neft.entity.enums.WellStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellActionLite {
    private Integer wellId;
    private double pressure;
    private double temperature;
    private double rpl;
    private WellStatus status;
}
