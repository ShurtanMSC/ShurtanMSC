package uz.neft.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.dto.ForecastDto;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.*;
import java.time.Month;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"year", "month", "mining_system_id"})})
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class ForecastGas extends AbsEntityInteger {
    private int year;

    @Enumerated(EnumType.STRING)
    private Month month;
    private double amount;
    private double real;
    private double expected;
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
