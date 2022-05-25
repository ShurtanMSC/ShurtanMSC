package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiningSystemGasCompositionDto {

//    private String miningSystemName;

    private Integer id;

    private Integer miningSystemId;

    private Integer gasCompositionId;
    private String gasCompositionName;

    private Double molarFraction;

}
