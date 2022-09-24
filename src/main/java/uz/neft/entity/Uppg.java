package uz.neft.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "uppg")
@Builder
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class Uppg extends AbsEntityInteger {

    @NotNull
    private String name;

    @ManyToOne
    private MiningSystem miningSystem;


}
