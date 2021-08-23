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
    private int hourPerDay;
    private int dayPerWeek;
    private Integer miningSystemId;
}
