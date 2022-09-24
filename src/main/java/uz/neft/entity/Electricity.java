package uz.neft.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class Electricity extends AbsEntityInteger {
    private double hourly;
    private int hourPerDay=24;
    private int dayPerWeek=7;
    private int dayPerMonth=30;
    private int dayPerYear=365;
    @ManyToOne
    private MiningSystem miningSystem;

}
