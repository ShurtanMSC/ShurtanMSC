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
    private int hourPerDay=24;
    private int dayPerWeek=7;
    private int dayPerMonth=30;
    private int dayPerYear=365;
    @ManyToOne
    private MiningSystem miningSystem;

}
