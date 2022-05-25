package uz.neft.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.neft.dto.StaffDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class NumberOfStaff extends AbsEntityInteger {

    @ManyToOne
    private MiningSystem miningSystem;

    // В работе
    private int atWork;

    // В отпуске
    private int onVacation;

    // На больничном
    private int onSickLeave;


    // Б/С
    private int withoutContent;


    @Column(columnDefinition = "date")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date date;

    public StaffDto toDto(){
        return StaffDto
                .builder()
                .miningSystemId(miningSystem!=null?miningSystem.getId():null)
                .id(getId())
                .atWork(atWork)
                .onVacation(onVacation)
                .onSickLeave(onSickLeave)
                .withoutContent(withoutContent)
                .date(date)
                .build();
    }

    public ObjectWithActionsDto toWithActionsDto(){
        return ObjectWithActionsDto
                .builder()
                .objectActionDto(toDto())
                .objectDto(miningSystem.toDto())
                .build();
    }

}
