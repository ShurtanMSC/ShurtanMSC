package uz.neft.entity;

import lombok.*;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Electricity extends AbsEntityInteger {
    private double hourly;
    private int hourPerDay;
    private int dayPerWeek;
    @ManyToOne
    private MiningSystem miningSystem;
}
