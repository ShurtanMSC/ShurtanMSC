package uz.neft.entity.action;

import lombok.*;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MiningSystemAction extends AbsEntityLong {

    private double expend;
    private double lastYearExpend;
    private double todayExpend;
    private double yesterdayExpend;
    private double thisMonthExpend;
    private double lastMonthExpend;
    private double planThisMonth;
    private double planThisYear;


    @ManyToOne
    private MiningSystem miningSystem;


    public static MiningSystemAction nuller(MiningSystem miningSystem){
        return MiningSystemAction
                .builder()
                .miningSystem(miningSystem)
                .build();

    }
//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;
}
