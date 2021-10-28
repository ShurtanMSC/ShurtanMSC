package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricityDto {
    private Integer id;
    private double hourly;
    private double daily;
    private double monthly;
    private double yearly;
    private Integer miningSystemId;
    private String miningSystemName;
}
