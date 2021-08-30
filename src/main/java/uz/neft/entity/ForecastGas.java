package uz.neft.entity;

import lombok.*;
import uz.neft.dto.ForecastDto;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Month;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"year", "month", "mining_system_id"})})
public class ForecastGas extends AbsEntityInteger {
    private int year;
    private Month month;
    private double amount;
    private double real;
    @ManyToOne
    private MiningSystem miningSystem;


    public void trans(ForecastDto dto){
        if (year==0&&month==null&&miningSystem==null){
            this.setMonth(dto.getMonth());
            this.setYear(dto.getYear());
        }
        this.amount=dto.getAmount();
    }
}
