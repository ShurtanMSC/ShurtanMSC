package uz.neft.entity;

import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import uz.neft.entity.template.AbsEntityShort;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Audited
//@AuditOverride(forClass = Auditable.class)
//@EntityListeners(AuditingEntityListener.class)
public class Role extends AbsEntityShort implements GrantedAuthority{


    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
