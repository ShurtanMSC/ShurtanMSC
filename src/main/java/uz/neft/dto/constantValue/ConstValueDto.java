package uz.neft.dto.constantValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.neft.entity.variables.ConstantNameEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstValueDto {
    private Integer Id;

    private Double value;

    private Integer constantId;

    private Integer mSystemId;
    private Integer UppgId;
    private Integer CpointId;
    private Integer WellId;

}
