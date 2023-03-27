package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.dto.Dto;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiningSystemActionDto implements Dto{

    private Long actionId;

    // Rasxod
    private double expend;

    private double lastYearExpend;
    private double todayExpend;
    private double yesterdayExpend;
    private double thisMonthExpend;
    private double lastMonthExpend;
    private double planThisMonth;
    private double planThisYear;

    private Date createdAt;

    private Integer miningSystemId;

}
