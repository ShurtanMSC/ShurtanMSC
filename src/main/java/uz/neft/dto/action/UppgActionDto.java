package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.entity.CollectionPoint;

import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UppgActionDto {

    private Long actionId;

    // По газу, тыс.м³  // mln m^3/sutka
    private double expend;

    //Проектная производительность, м³/год
    private double designedPerformance;

    //Фактическая производителность, м³/год
    private double actualPerformance;


    // По конденсату, тыс.т
    private double condensate;

    // По воде, тыс.т
    private double onWater;

    // (Входная температура, °C)
    private double incomeTemperature;

    // (Выходная температура, °C)
    private double exitTemperature;

    // (Входное давление, кгс/см²)
    private double incomePressure;

    // exit_pressure (Выходное давление, кгс/см²)
    private double exitPressure;

    private Integer uppgId;

    private Date createdAt;


//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;


}
