package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.CollectionPoint;
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
public class CollectionPointConstant extends AbsEntityInteger {

    @ManyToOne
    private CollectionPoint collectionPoint;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Constant constant;

    private Double value;


}
