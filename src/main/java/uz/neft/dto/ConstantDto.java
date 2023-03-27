package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.neft.dto.constantValue.ConstantValueDto;
import uz.neft.entity.variables.ConstantNameEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstantDto implements Dto{

    private Integer Id;
    private ConstantNameEnums name;
    private String description;
    private ConstantValueDto constantValueDto;
}
