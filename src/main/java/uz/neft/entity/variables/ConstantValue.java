package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConstantValue extends AbsEntityInteger {

    private Double value;

    @ManyToOne
    private Constant constant;

    @ManyToOne
    private MiningSystem miningSystem;

    @ManyToOne
    private Uppg uppg;

    @ManyToOne
    private CollectionPoint collectionPoint;

    @ManyToOne
    private Well well;



}
