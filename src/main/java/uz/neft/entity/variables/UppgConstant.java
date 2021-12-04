package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UppgConstant extends AbsEntityInteger {

    @ManyToOne
    private Uppg uppg;

    @ManyToOne
    private Constant constant;

    private Double value;


}
