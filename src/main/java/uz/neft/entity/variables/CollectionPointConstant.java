package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class CollectionPointConstant extends AbsEntityInteger {

    @ManyToOne
    private CollectionPoint collectionPoint;

//(cascade = {CascadeType.MERGE, CascadeType.})

    @ManyToOne
    private Constant constant;

    private Double value;


}
