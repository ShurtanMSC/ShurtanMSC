package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GasComposition extends AbsEntityInteger {

    private String name;

    @Column(precision = 6, scale = 4)
    private Double criticalPressure;

    @Column(precision = 6, scale = 2)
    private Double criticalTemperature;


}
