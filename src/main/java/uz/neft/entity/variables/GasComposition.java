package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class GasComposition extends AbsEntityInteger {

    private String name;

    @Column(precision = 6, scale = 4)
    private Double criticalPressure;

    @Column(precision = 6, scale = 2)
    private Double criticalTemperature;


}
