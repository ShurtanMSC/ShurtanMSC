package uz.neft.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.NumberOfStaff;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StaffDto {

    private Integer id;

    private Integer miningSystemId;
    // В работе
    private int atWork;

    // В отпуске
    private int onVacation;

    // На больничном
    private int onSickLeave;


    // Б/С
    private int withoutContent;

    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date date;

    public NumberOfStaff toEntity(){
        return NumberOfStaff
                .builder()
                .atWork(atWork)
                .onVacation(onVacation)
                .onSickLeave(onSickLeave)
                .withoutContent(withoutContent)
                .date(date)
                .build();
    }

    public NumberOfStaff toEntity(MiningSystem miningSystem){
        NumberOfStaff numberOfStaff = toEntity();
        numberOfStaff.setMiningSystem(miningSystem);
        return numberOfStaff;
    }

}
