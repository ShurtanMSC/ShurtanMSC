package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GasCompositionDto {
    private Integer Id;
    private String name;
    private Double criticalPressure;
    private Double criticalTemperature;

}
