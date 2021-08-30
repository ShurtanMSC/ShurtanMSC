package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForecastDto {
    private int year;
    private Month month;
    private double amount;
    private Integer mining_system_id;
}
