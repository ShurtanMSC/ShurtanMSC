package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import uz.neft.entity.enums.WellCategory;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellDto {

    private Integer id;
    private Integer number;
    private Integer collectionPointId;

//    @Column(name="manager_birthday", columnDefinition = "date")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date commissioningDate;

//    @Column(name="manager_birthday", columnDefinition = "date")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date drillingStartDate;

    private String horizon;
    private double altitude;
    private double depth;
    private double x;
    private double y;
    private double c;
    private WellCategory category;

}
