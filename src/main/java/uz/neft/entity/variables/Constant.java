package uz.neft.entity.variables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "constant")
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class Constant extends AbsEntityInteger {

    @Enumerated(EnumType.STRING)
    private ConstantNameEnums name;
    private String description;

}
