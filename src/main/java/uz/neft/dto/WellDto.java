package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.neft.entity.enums.WellCategory;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellDto {

    private Integer id;
    private Integer number;
    private Integer collectionPointId;

    private Timestamp commissioningDate;
    private Timestamp drillingStartDate;

    private String horizon;
    private double altitude;
    private double depth;
    private double x;
    private double y;
    private WellCategory category;

}
