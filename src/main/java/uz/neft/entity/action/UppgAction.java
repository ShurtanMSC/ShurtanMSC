package uz.neft.entity.action;

import lombok.*;
import uz.neft.entity.Uppg;
import uz.neft.entity.User;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UppgAction extends AbsEntityLong {

    //Проектная производительность, м³/год
    private double designedPerformance;

    //Фактическая производителность, м³/год
    private double actualPerformance;

    // По газу, тыс.м³  // mln m^3/sutka
    private double expend;

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


    private double todayExpend;
    private double yesterdayExpend;
    private double thisMonthExpend;
    private double lastMonthExpend;
    @ManyToOne
    private User user;

    @ManyToOne
    private Uppg uppg;

    private double accumulatedVolumeYesterday;
    private double accumulatedVolumeToday;
    private double accumulatedVolumePreviousMonth;


//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;

}
